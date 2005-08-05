<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="projectRequirementsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="initPopupMenu.jsp" %>
<%@ include file="projects_center_requirements_menu.jsp" %>
<%-- Preload image rollovers --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select_<%= SKIN %>');
</script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img border="0" src="images/icons/stock_list_bullet2-16.gif" align="absmiddle">
      <dhv:label name="project.outlines">Outlines</dhv:label>
    </td>
  </tr>
</table>
<br>
<dhv:evaluate if="<%= !Project.isTrashed() %>" >
  <zeroio:permission name="project-plan-outline-add">
  <img border="0" src="images/icons/stock_new_bullet-16.gif" align="absmiddle">
  <a href="ProjectManagementRequirements.do?command=Add&pid=<%= Project.getId() %>"><dhv:label name="project.newOutline">New Outline</dhv:label></a><br>
  &nbsp;<br>
  </zeroio:permission>
</dhv:evaluate>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr>
    <form name="reqView" method="post" action="ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=<%= Project.getId() %>">
    <td align="left">
      <img alt="" src="images/icons/stock_filter-data-by-criteria-16.gif" align="absmiddle">
      <select name="listView" onChange="javascript:document.forms['reqView'].submit();">
        <option <%= projectRequirementsInfo.getOptionValue("open") %>><dhv:label name="project.openOutlines">Open Outlines</dhv:label></option>
        <option <%= projectRequirementsInfo.getOptionValue("closed") %>><dhv:label name="project.closedOutlines">Closed Outlines</dhv:label></option>
        <option <%= projectRequirementsInfo.getOptionValue("all") %>><dhv:label name="project.allOutlines">All Outlines</dhv:label></option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="projectRequirementsInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8" nowrap>&nbsp;</th>
    <th width="86" nowrap><strong><dhv:label name="documents.details.startDate">Start Date</dhv:label></strong></th>
    <th width="100%" nowrap><strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong></th>
    <th nowrap><strong><dhv:label name="project.progress">Progress</dhv:label></strong></th>
    <th width="118"><strong><dhv:label name="project.effort">Effort</dhv:label></strong></th>
  </tr>
<%    
  RequirementList requirements = Project.getRequirements();
  if (requirements.size() == 0) {
%>
  <tr class="row2">
    <td colspan="5"><dhv:label name="project.noOutlinesToDisplay">No outlines to display.</dhv:label></td>
  </tr>
<%
  }
  int rowid = 0;
  int count = 0;
  Iterator i = requirements.iterator();
  while (i.hasNext()) {
    rowid = (rowid != 1?1:2);
    ++count;
    Requirement thisRequirement = (Requirement) i.next();
%>    
  <tr class="row<%= rowid %>">
    <td valign="top" align="center" nowrap>
      <a href="javascript:displayMenu('select_<%= SKIN %><%= count %>', 'menuItem', <%= thisRequirement.getId() %>, <%= Project.getId() %>,'<%= Project.isTrashed() %>');"
         onMouseOver="over(0, <%= count %>)"
         onmouseout="out(0, <%= count %>); hideMenu('menuItem');"><img 
         src="images/select_<%= SKIN %>.gif" name="select_<%= SKIN %><%= count %>" id="select_<%= SKIN %><%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td valign="top" align="center" nowrap>
      <% if(!User.getTimeZone().equals(thisRequirement.getStartDateTimeZone())){%>
      <zeroio:tz timestamp="<%= thisRequirement.getStartDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisRequirement.getStartDate() %>" dateOnly="true" timeZone="<%= thisRequirement.getStartDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } %>
    </td>
    <td valign="top">
      <table border="0" cellpadding="0" cellspacing="0" class="empty">
        <tr>
          <td valign="top" nowrap>
            &nbsp;<%= thisRequirement.getStatusGraphicTag() %> 
          </td>
          <td valign="top">
            <a href="ProjectManagement.do?command=ProjectCenter&section=Assignments&rid=<%= thisRequirement.getId() %>&pid=<%= Project.getId() %>"><%= toHtml(thisRequirement.getShortDescription()) %></a>
            <a href="javascript:popURL('ProjectManagementRequirements.do?command=Details&pid=<%= Project.getId() %>&rid=<%= thisRequirement.getId() %>&popup=true','Outline_Details','650','375','yes','yes');"><img src="images/icons/stock_insert-note-16.gif" border="0" align="absbottom" /></a><br />
            <i>
            <dhv:evaluate if="<%= hasText(thisRequirement.getSubmittedBy()) || hasText(thisRequirement.getDepartmentBy()) %>">
              <dhv:label name="documents.details.requestedBy">Requested By</dhv:label>
            </dhv:evaluate>
            <dhv:evaluate if="<%= hasText(thisRequirement.getSubmittedBy()) %>">
              <%= toHtml(thisRequirement.getSubmittedBy()) %>
            </dhv:evaluate>
            <dhv:evaluate if="<%= hasText(thisRequirement.getSubmittedBy()) && hasText(thisRequirement.getDepartmentBy()) %>">
              /
            </dhv:evaluate>
            <dhv:evaluate if="<%= hasText(thisRequirement.getDepartmentBy()) %>">
              <%= toHtml(thisRequirement.getDepartmentBy()) %>
            </dhv:evaluate>
            </i>
          </td>
        </tr>
      </table>
    </td>
    <td valign="top" align="right" nowrap>
      <table cellpadding="1" cellspacing="1" class="empty">
        <td><dhv:label name="project.progress">Progress</dhv:label>:</td>
        <dhv:evaluate if="<%= thisRequirement.getPlanActivityCount() == 0 %>">
          <td width="<%= thisRequirement.getPercentClosed() %>" bgColor="#CCCCCC" nowrap class="progressCell"></td>
        </dhv:evaluate>
        <dhv:evaluate if="<%= thisRequirement.getPlanActivityCount() > 0 %>">
          <dhv:evaluate if="<%= thisRequirement.getPercentClosed() > 0 %>">
            <td width="<%= thisRequirement.getPercentClosed()  %>" bgColor="green" nowrap class="progressCell"></td>
          </dhv:evaluate>
          <dhv:evaluate if="<%= thisRequirement.getPercentUpcoming() > 0 %>">
            <td width="<%= thisRequirement.getPercentUpcoming() %>" bgColor="#99CC66" nowrap class="progressCell"></td>
          </dhv:evaluate>
          <dhv:evaluate if="<%= thisRequirement.getPercentOverdue() > 0 %>">
            <td width="<%= thisRequirement.getPercentOverdue() %>" bgColor="red" nowrap class="progressCell"></td>
          </dhv:evaluate>
        </dhv:evaluate>
      </table>
      <dhv:evaluate if="<%= thisRequirement.getPlanActivityCount() == 0 %>">
        <dhv:label name="project.zeroActivities">(0 activities)</dhv:label>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisRequirement.getPlanActivityCount() > 0 %>">
        <% if(thisRequirement.getPlanActivityCount() == 1) {%>
          <dhv:label name="project.numberOfActivityComplete" param="<%= "complete="+thisRequirement.getPlanClosedCount() +"|total="+ thisRequirement.getPlanActivityCount() %>">(<%= thisRequirement.getPlanClosedCount() %> of <%= thisRequirement.getPlanActivityCount() %> activity is complete)</dhv:label>
        <%} else {%>
          <dhv:label name="project.numberOfActivitiesComplete" param="<%= "complete="+thisRequirement.getPlanClosedCount() +"|total="+ thisRequirement.getPlanActivityCount() %>">(<%= thisRequirement.getPlanClosedCount() %> of <%= thisRequirement.getPlanActivityCount() %> activities are complete)</dhv:label>
        <%}%>
      </dhv:evaluate>
    </td>
    <td valign="top" nowrap>
      <% if(!User.getTimeZone().equals(thisRequirement.getDeadlineTimeZone())){%>
        <dhv:label name="project.due.colon" param="<%= "date="+getTime(pageContext,thisRequirement.getDeadline(),User.getTimeZone(),DateFormat.SHORT,true,false,false,"&nbsp;") %>">Due: <zeroio:tz timestamp="<%= thisRequirement.getDeadline() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/></dhv:label>
      <% } else { %>
        <dhv:label name="project.due.colon" param="<%= "date="+getTime(pageContext,thisRequirement.getDeadline(),thisRequirement.getDeadlineTimeZone(),DateFormat.SHORT,true,false,false,"&nbsp;") %>">Due: <zeroio:tz timestamp="<%= thisRequirement.getDeadline() %>" timeZone="<%= thisRequirement.getDeadlineTimeZone() %>" showTimeZone="true" default="&nbsp;"/></dhv:label>
      <% } %><br />
      <dhv:label name="project.loe.colon" param="<%= "date="+thisRequirement.getEstimatedLoeString() %>">LOE: <%= thisRequirement.getEstimatedLoeString() %></dhv:label>
    </td>
  </tr>
<%    
    if (thisRequirement.isTreeOpen() && thisRequirement.getAssignments().size() > 0) {
      Iterator assignments = thisRequirement.getAssignments().iterator();
      while (assignments.hasNext()) {
        Assignment thisAssignment = (Assignment)assignments.next();
%>
  <tr class="row<%= rowid %>">
    <td valign="top" colspan="2">
      &nbsp;
    </td>
    <td valign="top">
      <img border="0" src="images/treespace.gif" align="absmiddle">
      <%= thisAssignment.getStatusGraphicTag() %>
      <a href="javascript:popURL('ProjectManagementAssignments.do?command=Modify&pid=<%= Project.getId() %>&aid=<%= thisAssignment.getId() %>&popup=true&return=ProjectRequirements&param=<%= Project.getId() %>','CFS_Assignment','650','475','yes','yes');" style="text-decoration:none;color:black;" onMouseOver="this.style.color='blue';window.status='Update this assignment';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><%= toHtml(thisAssignment.getRole()) %></a>
      (<dhv:username id="<%= thisAssignment.getUserAssignedId() %>"/>)
    </td>
    <td valign="top" nowrap>
      <dhv:label name="project.due.colon" param="<%= "date="+thisAssignment.getRelativeDueDateString(User.getTimeZone(), User.getLocale()) %>">Due: <%= thisAssignment.getRelativeDueDateString(User.getTimeZone(), User.getLocale()) %></dhv:label><br />
      <dhv:label name="project.loe.colon" param="<%= "date="+thisAssignment.getEstimatedLoeString() %>">LOE: <%= thisAssignment.getEstimatedLoeString() %></dhv:label>
    </td>
  </tr>
<%
      }
    }
  }
%>
</table>
<br>
<dhv:pagedListControl object="projectRequirementsInfo"/>
<br>
<table border="0" width="100%">
  <tr>
    <td>
      <img border="0" src="images/box.gif" alt="<dhv:label name='quotes.incomplete'>Incomplete</dhv:label>" align="absmiddle" />
      <dhv:label name="project.itemIsIncomplete">Item is incomplete</dhv:label><br />
      <img border="0" src="images/box-checked.gif" alt="<dhv:label name='alt.completed'>Completed</dhv:label>" align="absmiddle" />
      <dhv:label name="project.itemHasBeenCompleted">Item has been completed</dhv:label><br />
      <img border="0" src="images/box-hold.gif" alt="<dhv:label name='alt.onHold'>On Hold</dhv:label>" align="absmiddle" />
      <dhv:label name="project.itemNotApproved">Item has not been approved</dhv:label>
    </td>
  </tr>
</table>


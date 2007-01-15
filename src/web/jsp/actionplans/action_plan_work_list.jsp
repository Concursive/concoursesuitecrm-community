<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: action_plan_work_list.jsp
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.actionplans.base.*"%>
<jsp:useBean id="accountActionPlanWorkList" class="org.aspcfs.modules.actionplans.base.ActionPlanWorkList" scope="request"/>
<jsp:useBean id="actionPlanWorkListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="actionPlanWorkListView" class="java.lang.String" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="action_plan_work_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  
  function reassignPlan(userId, actionPlanWork) {
    window.location.href = "MyActionPlans.do?command=Reassign&actionPlanId=" + actionPlanWork + "&userId=" + userId + "&return=list";
  }
  
  function changeView(view) {
    window.location.href = 'MyActionPlans.do?command=View&planView=' + view;
  }
</SCRIPT>
<form name="actionPlanListView" method="post" action="MyActionPlans.do?command=List">
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
      <dhv:label name="sales.actionPlans">Action Plans</dhv:label>  
    </td>
  </tr>
</table>
<%-- End Trails --%>
<input type="button" value="<dhv:label name="actionPlan.dashboardView">Dashboard View</dhv:label>" onClick="javascript:changeView('planDashboardView');"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
      <select size="1" name="listFilter1" onChange="javascript:document.actionPlanListView.submit();">
        <option value="myviewpoint" <%= actionPlanWorkListInfo.getFilterValue("listFilter1").equalsIgnoreCase("my")?" selected":"" %>>My Action Plans Viewpoint</option>
        <option value="my" <%= actionPlanWorkListInfo.getFilterValue("listFilter1").equalsIgnoreCase("my")?" selected":"" %>><dhv:label name="actionPlan.myActionPlans">My Action Plans</dhv:label></option>
        <option value="mywaiting" <%= actionPlanWorkListInfo.getFilterValue("listFilter1").equalsIgnoreCase("mywaiting")?" selected":"" %>><dhv:label name="actionPlan.myWaitingActionPlans">Action Plans Waiting on Me</dhv:label></option>
        <option value="mymanaged" <%= actionPlanWorkListInfo.getFilterValue("listFilter1").equalsIgnoreCase("mymanaged")?" selected":"" %>><dhv:label name="actionPlan.myManagedActionPlans">Action Plans Managed By Me</dhv:label></option>
      </select>
      <select size="1" name="listFilter2" onChange="javascript:document.actionPlanListView.submit();">
        <option value="true" <%= actionPlanWorkListInfo.getFilterValue("listFilter2").equalsIgnoreCase("true")?" selected":"" %>><dhv:label name="actionPlan.activePlans">Active Plans</dhv:label></option>
        <option value="false" <%= actionPlanWorkListInfo.getFilterValue("listFilter2").equalsIgnoreCase("false")?" selected":"" %>><dhv:label name="actionPlan.inactivePlans">Inactive Plans</dhv:label></option>
        <option value="all" <%= actionPlanWorkListInfo.getFilterValue("listFilter2").equalsIgnoreCase("all")?" selected":"" %>><dhv:label name="actionPlan.anyStatusPlans">Any Status Plans</dhv:label></option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="actionPlanWorkListInfo"/>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th valign="middle">
      &nbsp;
    </th>
    <th style="text-align: center !important" valign="middle">
      <strong><dhv:label name="actionPlan.actionRequired">Action Required</dhv:label></strong>
    </th>
    <th valign="middle">
      <strong><dhv:label name="sales.assigned">Assigned</dhv:label></strong>
    </th>
    <th valign="middle" width="100%">
      <strong><dhv:label name="actionPlan.prospectName">Prospect Name</dhv:label></strong>
    </th>
    <th style="text-align: center !important">
      <strong><dhv:label name="actionPlan.weeklyPotential">Weekly Potential</dhv:label></strong>
    </th>
    <th align="center" nowrap>
      <strong><dhv:label name="actionPlan.currentPhase">Current Phase</dhv:label></strong>
    </th>
    <th style="text-align: center !important">
      <strong><dhv:label name="actionPlan.daysInPhase">Days in Phase</dhv:label></strong>
    </th>
    <th align="center" nowrap>
      <strong><dhv:label name="actionPlan.daysActive">Days Active</dhv:label></strong>
    </th>
    <th valign="middle" nowrap>
      <strong><a href="MyActionPlans.do?command=List&column=apw.modified"><dhv:label name="actionList.lastUpdated">Last Updated</dhv:label></a></strong>
      <%= actionPlanWorkListInfo.getSortIcon("apw.modified") %>
    </th>
  </tr>
 <%
  Iterator j = accountActionPlanWorkList.iterator();
  if ( j.hasNext() ) {
  int rowid = 0;
  int i =0;
  while (j.hasNext()) {
      i++;
      rowid = (rowid != 1 ? 1 : 2);
      ActionPlanWork thisWork = (ActionPlanWork) j.next();
%>
  <tr
      <dhv:evaluate if="<%= thisWork.getCurrentStep() != null && thisWork.getCurrentStep().getOwnerId() == User.getUserRecord().getId() %>">
        class="highlightRed"
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisWork.getCurrentStep() == null || thisWork.getCurrentStep().getOwnerId() != User.getUserRecord().getId() %>">
        class="row<%= rowid %>"
      </dhv:evaluate>
      >
    <td nowrap>
     <%-- Use the unique id for opening the menu, and toggling the graphics --%>
     <dhv:evaluate if="<%= thisWork.getOrganization() != null %>">
       <a href="javascript:displayMenu('select<%= i %>','menuActionPlan','<%= thisWork.getId() %>','<%= thisWork.getOrganization().getOrgId() %>','<%= thisWork.getManagerId() %>','<%= thisWork.getEnabled() ? 1 : 0 %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuActionPlan');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
     </dhv:evaluate>
     <dhv:evaluate if="<%= thisWork.getOrganization() == null %>">
       &nbsp;
     </dhv:evaluate>
    </td>
    <td align="center" nowrap>
      <dhv:evaluate if="<%= thisWork.getCurrentPhase().allStepsComplete() %>">
        <dhv:evaluate if="<%= thisWork.getCurrentPhase().requiresUserAttention(User.getUserRecord().getId(), request) %>">
          <dhv:label name="account.yes">Yes</dhv:label>
        </dhv:evaluate>
        <dhv:evaluate if="<%= !thisWork.getCurrentPhase().requiresUserAttention(User.getUserRecord().getId(), request) %>">
          <dhv:label name="account.no">No</dhv:label>
        </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= !thisWork.getCurrentPhase().allStepsComplete() %>">
        <dhv:label name="account.no">No</dhv:label>
      </dhv:evaluate>
    </td>
    <td nowrap>
      <dhv:username id="<%= thisWork.getAssignedTo() %>"/>
    </td>
    <td nowrap>
      <dhv:evaluate if="<%= thisWork.getOrganization() != null && hasText(thisWork.getOrganization().getName()) %>">
        <a href="MyActionPlans.do?command=Details&actionPlanId=<%= thisWork.getId() %>"><%= toHtml(thisWork.getOrganization().getName()) %></a>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisWork.getOrganization() == null && thisWork.getContact() != null && hasText(thisWork.getContact().getNameFirstLast()) %>">
        <a href="MyActionPlans.do?command=Details&actionPlanId=<%= thisWork.getId() %>"><%= toHtml(thisWork.getContact().getNameFirstLast()) %></a>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisWork.getOrganization() == null %>">
        &nbsp;
      </dhv:evaluate>
    </td>
    <td align="center">
      <dhv:evaluate if="<%= thisWork.getOrganization() != null %>">
        <zeroio:currency value="<%= thisWork.getOrganization().getPotential() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisWork.getContact() != null %>">
        <zeroio:currency value="<%= thisWork.getContact().getPotential() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </dhv:evaluate>
    </td>
    <td align="center">
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() != null %>">
        <%= toHtml(thisWork.getCurrentPhase().getPhaseName()) %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() == null %>">
        <dhv:label name="sales.actionPlan">Action Plan</dhv:label>
      </dhv:evaluate>
    </td>
    <td nowrap align="center">
      &nbsp;
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() != null %>">
        <%= thisWork.getCurrentPhase().getDaysInPhase() %>
      </dhv:evaluate>
    </td>
    <td nowrap align="center">
      <%= thisWork.getDaysActive() %>
    </td>
    <td nowrap align="center">
      <zeroio:tz timestamp="<%= thisWork.getModified() %>" timeZone="<%= User.getUserRecord().getTimeZone() %>"/>
    </td>
  </tr>
<%}
}else{%>
      <tr>
        <td class="containerBody" colspan="9" valign="center">
          <dhv:label name="actionPlan.noActionPlansFound">No Action Plans found in this view.</dhv:label>
        </td>
      </tr>
<%}%>
</table>
</form>
&nbsp;<br>
<dhv:pagedListControl object="actionPlanWorkListInfo" tdClass="row1"/>
  

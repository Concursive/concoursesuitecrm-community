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
<%@ page import="java.util.*" %>
<%@ page import="com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.utils.web.*" %>
<%@ page import="org.aspcfs.modules.troubletickets.base.TicketLog" %>
<%@ page import="org.aspcfs.modules.admin.base.User" %>
<%@ page import="org.aspcfs.modules.contacts.base.Contact" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="ticket" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<%--
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
--%>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/spanDisplay.js"></script>
<script language="JavaScript">
<!-- Begin
function updateSubList1() {
  var sel = document.forms['ticketForm'].elements['catCode'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=CategoryJSList&catCode=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
function updateSubList2() {
  var sel = document.forms['ticketForm'].elements['subCat1'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=CategoryJSList&subCat1=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
function updateSubList3() {
  var sel = document.forms['ticketForm'].elements['subCat2'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=CategoryJSList&subCat2=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
function updateUserList() {
  var sel = document.forms['ticketForm'].elements['departmentCode'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=DepartmentJSList&departmentCode=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
//  End -->
</script>
<body onLoad="document.ticketForm.problem.focus();">
<form name="ticketForm" action="ProjectManagementTickets.do?command=Save&auto-populate=true" method="post">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_macro-organizer-16.gif" border="0" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Tickets&pid=<%= Project.getId() %>">Tickets</a> >
      <%= ticket.getId() == -1 ? "Add" : "Update" %>
    </td>
  </tr>
</table>
<br>
<% if (ticket.getClosed() != null) { %>
  <input type="submit" value="Re-open" onClick="javascript:this.form.action='ProjectManagementTickets.do?command=Reopen&pid=<%= Project.getId() %>&id=<%= ticket.getId() %>'">
<%} else {%>
  <input type="submit" value="Save">
<%}%>
<% if ("list".equals(request.getParameter("return")) || ticket.getId() == -1) {%>
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='ProjectManagement.do?command=ProjectCenter&section=Tickets&pid=<%= Project.getId() %>'">
<%} else {%> 
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='ProjectManagementTickets.do?command=Details&pid=<%= Project.getId() %>&id=<%= ticket.getId() %>'">
<%}%>
<br />
<dhv:formMessage />
<dhv:evaluate if="<%= ticket.getId() > -1 %>">
  <br>
  <strong>Ticket #<%= ticket.getProjectTicketCount() %></strong>
  <dhv:evaluate if="<%= ticket.getClosed() != null %>">
    (<font color="red">This ticket was closed on <%= toHtml(ticket.getClosedString()) %></font>)
  </dhv:evaluate>
  <dhv:evaluate if="<%= ticket.getClosed() == null %>">
    (<font color="green">Open</font>)
  </dhv:evaluate>
  <br>
</dhv:evaluate>
&nbsp;<br>
<%--
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th colspan="2">
		<strong>Ticket Information</strong>
		</th>
  </tr>
  <tr class="containerBody">
		<td class="formLabel">
      Ticket Source
		</td>
		<td>
      <%= SourceList.getHtmlSelect("sourceCode",  ticket.getSourceCode()) %>
		</td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Organization
    </td>
    <td>
      <%= toHtml(ticket.getCompanyName()) %>
    </td>
  </tr>
  <tr class="containerBody">
		<td class="formLabel">
      Contact
		</td>
    <td>
      if ( ticket.getThisContact() == null ) {
         ContactList.getHtmlSelect("contactId", 0 ) 
      } else {
        ContactList.getHtmlSelect("contactId", ticket.getContactId() ) 
      }
      <font color="red">*</font> <%= showAttribute(request, "contactIdError") %>
		</td>
  </tr>
</table>
<br>
--%>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th colspan="2">
      <strong>Classification</strong>
		</th>
  </tr>
  <tr class="containerBody">
		<td class="formLabel" valign="top">
      Issue
    </td>
		<td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="problem" cols="55" rows="8"><%= toString(ticket.getProblem()) %></textarea>
          </td>
          <td valign="top">
            <font color="red">*</font> <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
      </table>
		</td>
  </tr>
<%--
	<tr class="containerBody">
		<td class="formLabel">
      Category
		</td>
		<td>
      <%= CategoryList.getHtmlSelect("catCode", ticket.getCatCode()) %>
<% if (ticket.getCatCode() == 0) { %>
			<input type="checkbox" name="newCat0chk" onClick="javascript:showSpan('new0')">add new<span name="new0" ID="new0" style="display:none">&nbsp;<input type="text" size="25" name="newCat0"></span>
<%}%>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Sub-level 1
		</td>
    <td>
      <%= SubList1.getHtmlSelect("subCat1", ticket.getSubCat1()) %>
<% if (ticket.getCatCode() != 0 && ticket.getSubCat1() == 0) { %>
			<input type="checkbox" name="newCat1chk" onClick="javascript:showSpan('new1')">add new<span name="new1" ID="new1" style="display:none">&nbsp;<input type="text" size="25" name="newCat1"></span>
<%}%>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Sub-level 2
		</td>
		<td>
      <%= SubList2.getHtmlSelect("subCat2", ticket.getSubCat2()) %>
<% if (ticket.getSubCat1() != 0 && ticket.getCatCode() != 0 && ticket.getSubCat2() == 0) { %>
			<input type="checkbox" name="newCat2chk" onClick="javascript:showSpan('new2')">add new<span name="new2" ID="new2" style="display:none">&nbsp;<input type="text" size="25" name="newCat2"></span>
<%}%>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Sub-level 3
		</td>
		<td>
      <%= SubList3.getHtmlSelect("subCat3", ticket.getSubCat3()) %>
<% if (ticket.getSubCat2() != 0 && ticket.getCatCode() != 0 && ticket.getSubCat1() != 0) { %>
			<input type="checkbox" name="newCat3chk" onClick="javascript:showSpan('new3')">add new<span name="new3" ID="new3" style="display:none">&nbsp;<input type="text" size="25" name="newCat3"></span>
<%}%>
		</td>
  </tr>
--%>
  <tr class="containerBody">
		<td class="formLabel">
      Severity
		</td>
		<td>
      <%= SeverityList.getHtmlSelect("severityCode", ticket.getSeverityCode()) %>
		</td>
  </tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th colspan="2">
      <strong>Assignment</strong>
		</th>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Priority
		</td>
		<td>
      <%= PriorityList.getHtmlSelect("priorityCode", ticket.getPriorityCode()) %>
		</td>
  </tr>
<%--
	<tr class="containerBody">
		<td class="formLabel">
      Department
		</td>
		<td>
      <%= DepartmentList.getHtmlSelect("departmentCode", ticket.getDepartmentCode()) %>
		</td>
  </tr>
--%>
	<tr class="containerBody">
		<td nowrap class="formLabel">
      <dhv:evaluate if="<%= ticket.getId() == -1 %>">Assign To</dhv:evaluate>
      <dhv:evaluate if="<%= ticket.getId() > -1 %>">Reassign To</dhv:evaluate>
		</td>
		<td valign="center">
<%
    TeamMemberList thisTeam = Project.getTeam();
    HtmlSelect team = new HtmlSelect();
    team.addItem(-1, "-- None Selected --");
    Iterator iTeam = thisTeam.iterator();
    while (iTeam.hasNext()) {
      TeamMember thisMember = (TeamMember) iTeam.next();
      team.addItem(thisMember.getUserId(), 
           ((User) thisMember.getUser()).getContact().getNameLastFirst());
    }
%>
      <%= team.getHtml("assignedTo", ticket.getAssignedTo()) %>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel" valign="top">
      User Comments
    </td>
		<td>
      <textarea name="comment" cols="55" rows="5"><%= toString(ticket.getComment()) %></textarea>
		</td>
  </tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th colspan="2">
      <strong>Resolution</strong>
		</th>
  </tr>
	<tr class="containerBody">
		<td class="formLabel" valign="top">
      Solution
		</td>
		<td>
      <textarea name="solution" cols="55" rows="8"><%= toString(ticket.getSolution()) %></textarea><br>
        <input type="checkbox" name="closeNow">Close ticket <%= showAttribute(request, "closedError") %>
<%--
        <br><input type="checkbox" name="kbase">Add this solution to Knowledge Base &nbsp;
--%>
      </td>
		</tr>
</table>
<br>
<% if (ticket.getClosed() != null) { %>
  <input type="submit" value="Reopen" onClick="javascript:this.form.action='ProjectManagementTickets.do?command=Reopen&pid=<%= Project.getId() %>&id=<%= ticket.getId() %>'">
<%} else {%>
  <input type="submit" value="Save">
<%}%>
<% if ("list".equals(request.getParameter("return")) || ticket.getId() == -1) {%>
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='ProjectManagement.do?command=ProjectCenter&section=Tickets&pid=<%= Project.getId() %>'">
<%} else {%> 
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='ProjectManagementTickets.do?command=Details&pid=<%= Project.getId() %>&id=<%= ticket.getId() %>'">
<%}%>
<input type="hidden" name="modified" value="<%= ticket.getModified() %>">
<input type="hidden" name="pid" value="<%= Project.getId() %>">
<input type="hidden" name="id" value="<%= ticket.getId() %>">
<input type="hidden" name="orgId" value="<%= ticket.getOrgId() %>">
<input type="hidden" name="contactId" value="<%= ticket.getContactId() %>">
<input type="hidden" name="companyName" value="<%= toHtmlValue(ticket.getCompanyName()) %>">
<input type="hidden" name="close" value="">
<input type="hidden" name="return" value="<%= toHtmlValue(request.getParameter("return")) %>">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0" width="0" border="0" frameborder="0"></iframe>
</form>
</body>

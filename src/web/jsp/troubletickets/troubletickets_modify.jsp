<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<%@ page import="org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.communications.base.Campaign,org.aspcfs.modules.communications.base.CampaignList,org.aspcfs.utils.web.HtmlSelect" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript">
function updateSubList1() {
  var sel = document.forms['details'].elements['catCode'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=CategoryJSList&catCode=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
function updateSubList2() {
  var sel = document.forms['details'].elements['subCat1'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=CategoryJSList&subCat1=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
function updateSubList3() {
  var sel = document.forms['details'].elements['subCat2'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=CategoryJSList&subCat2=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
function updateUserList() {
  var sel = document.forms['details'].elements['departmentCode'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=DepartmentJSList&departmentCode=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
</SCRIPT>
<body>
<form name="details" action="TroubleTickets.do?command=Update&auto-populate=true" method="post">
<a href="TroubleTickets.do">Tickets</a> > 
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="TroubleTickets.do?command=Home">View Tickets</a> >
  <%}%>
<%} else {%>
<a href="TroubleTickets.do?command=Home">View Tickets</a> >
<a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>">Ticket Details</a> >
<%}%>
Modify Ticket<br>
<hr color="#BFBFBB" noshade>
<strong>Ticket # <%= TicketDetails.getPaddedId() %><br>
<%= toHtml(TicketDetails.getCompanyName()) %></strong>
<dhv:evaluate if="<%= !(TicketDetails.getCompanyEnabled()) %>">
  <font color="red">(account disabled)</font>
</dhv:evaluate>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<% String param1 = "id=" + TicketDetails.getId(); %>
<dhv:container name="tickets" selected="details" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
  	<td class="containerBack">
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
        <font color="red">This ticket was closed on <%= toHtml(TicketDetails.getClosedString()) %></font><br>
        &nbsp;<br>
      </dhv:evaluate>
      <% if (TicketDetails.getClosed() != null) { %>
        <input type="button" value="Reopen">
        <input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>'">
      <%} else {%>
        <input type="submit" value="Update">
        <% if ("list".equals(request.getParameter("return"))) {%>
          <input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Home'">
        <%} else {%>
          <input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>'">
        <%}%>
      <%}%>
      <br>
<%= showError(request, "actionError") %>
<% if (request.getAttribute("closedError") != null) { %>
  <%= showAttribute(request, "closedError") %>
<%}%>
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
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
        <%= SourceList.getHtmlSelect("sourceCode",  TicketDetails.getSourceCode()) %>
      </td>
		</tr>
		<tr class="containerBody">
      <td class="formLabel">
        Contact
      </td>
      <td>
      <% if ( TicketDetails.getThisContact() == null ) {%>
        <%= ContactList.getHtmlSelect("contactId", 0 ) %>
      <%} else {%>
        <%= ContactList.getHtmlSelect("contactId", TicketDetails.getContactId() ) %>
      <%}%>
        <font color="red">*</font> <%= showAttribute(request, "contactIdError") %>
      </td>
		</tr>
  </table>
  <br>
  <a name="categories"></a> 
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
		<tr>
      <th colspan="2">
        <strong>Classification</strong>
      </th>
		</tr>
		<tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="tickets-problem">Issue</dhv:label>
      </td>
      <td>
        <table border="0" cellspacing="0" cellpadding="0" class="empty">
          <tr>
            <td>
              <textarea name="problem" cols="55" rows="3"><%= toString(TicketDetails.getProblem()) %></textarea>
            </td>
            <td valign="top">
              <font color="red">*</font> <%= showAttribute(request, "problemError") %>
              <input type="hidden" name="modified" value="<%= TicketDetails.getModified() %>">
              <input type="hidden" name="orgId" value="<%=TicketDetails.getOrgId()%>">
              <input type="hidden" name="id" value="<%=TicketDetails.getId()%>">
              <input type="hidden" name="companyName" value="<%=toHtml(TicketDetails.getCompanyName())%>">
              <input type="hidden" name="refresh" value="-1">
            </td>
          </tr>
        </table>
		<% if (request.getParameter("return") != null) {%>
			<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
		<%}%>
      </td>
		</tr>
  <dhv:include name="tickets-code" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Category
      </td>
      <td>
        <%= CategoryList.getHtmlSelect("catCode", TicketDetails.getCatCode()) %>
      </td>
		</tr>
  </dhv:include>
  <dhv:include name="tickets-subcat1" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Sub-level 1
      </td>
      <td>
        <%= SubList1.getHtmlSelect("subCat1", TicketDetails.getSubCat1()) %>
      </td>
		</tr>
  </dhv:include>
  <dhv:include name="tickets-subcat2" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Sub-level 2
      </td>
      <td>
        <%= SubList2.getHtmlSelect("subCat2", TicketDetails.getSubCat2()) %>
      </td>
		</tr>
  </dhv:include>
  <dhv:include name="tickets-subcat3" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Sub-level 3
      </td>
      <td>
        <%= SubList3.getHtmlSelect("subCat3", TicketDetails.getSubCat3()) %>
      </td>
		</tr>
  </dhv:include>
  </table>
  <br>
  <a name="department"></a> 
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong>Assignment</strong>
      </th>
		</tr>
  <dhv:include name="tickets-severity" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Severity
      </td>
      <td>
        <%= SeverityList.getHtmlSelect("severityCode", TicketDetails.getSeverityCode()) %>
      </td>
		</tr>
  </dhv:include>
  <dhv:include name="tickets-priority" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Priority
      </td>
      <td>
        <%= PriorityList.getHtmlSelect("priorityCode", TicketDetails.getPriorityCode()) %>
      </td>
		</tr>
  </dhv:include>
		<tr class="containerBody">
      <td class="formLabel">
        Department
      </td>
      <td>
        <%= DepartmentList.getHtmlSelect("departmentCode", TicketDetails.getDepartmentCode()) %>
      </td>
		</tr>
		<tr class="containerBody">
      <td nowrap class="formLabel">
        Reassign To
      </td>
      <td>
        <%= UserList.getHtmlSelect("assignedTo", TicketDetails.getAssignedTo() ) %>
      </td>
		</tr>
		<tr class="containerBody">
      <td valign="top" class="formLabel">
        User Comments
      </td>
      <td>
        <textarea name="comment" cols="55" rows="3"><%= toString(TicketDetails.getComment()) %></textarea>
      </td>
		</tr>
  </table>
  <br>
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong>Resolution</strong>
      </th>
		</tr>
		<tr class="containerBody">
      <td valign="top" class="formLabel">
        Solution
      </td>
      <td>
        <textarea name="solution" cols="55" rows="3"><%= toString(TicketDetails.getSolution()) %></textarea><br>
        <input type="checkbox" name="closeNow">Close ticket<br>
        <input type="checkbox" name="kbase">Add this solution to Knowledge Base
<%-- Added for voice demo, will show a list of surveys that can be emailed... --%>
<%--
<%
        CampaignList campaignList = (CampaignList) request.getAttribute("CampaignList");
        if (campaignList != null && campaignList.size() > 0) {
          HtmlSelect campaignSelect = new HtmlSelect();
          campaignSelect.addItem(-1, "-- None --");
          Iterator campaigns = campaignList.iterator();
          while (campaigns.hasNext()) {
            Campaign thisCampaign = (Campaign)campaigns.next();
            campaignSelect.addItem(thisCampaign.getId(), thisCampaign.getName());
          }
%>
        <br>Send contact a follow-up message: <%= campaignSelect.getHtml("campaignId", TicketDetails.getCampaignId()) %>
<%
        }
%>
--%>
<%-- End voice demo --%>
      </td>
		</tr>
	</table>
&nbsp;<br>
<% if (TicketDetails.getClosed() != null) { %>
  <input type="button" value="Reopen">
<%} else {%>
  <input type="submit" value="Update">
<%}%>
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Home'">
	<%}%>
<%} else {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>'">
<%}%>
  </td>
  </tr>
</table>
</form>
</body>

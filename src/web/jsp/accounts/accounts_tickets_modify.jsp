<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.troubletickets.base.TicketLog" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
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
<script language="JavaScript">
<!-- Begin
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
  function checkForm(form) {
    formTest = true;
    message = "";
    if (form.problem.value == "") { 
      message += "- Check that <dhv:label name="tickets-problem">Issue</dhv:label> is entered\r\n";
      formTest = false;
    }
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    } else {
      return true;
    }
  }
//  End -->
</script>
<body>
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%>">Tickets</a> >
<% if (request.getParameter("return") == null) {%>
<a href="AccountTickets.do?command=TicketDetails&id=<%=TicketDetails.getPaddedId()%>">Ticket Details</a> >
<%}%>
Modify Ticket<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <%@ include file="accounts_details_header_include.jsp" %>
      <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
    </td>
  </tr>
  <% if (TicketDetails.getClosed() != null) { %>  
  <tr>
    <td bgColor="#F1F0E0">
      <font color="red">This ticket was closed on <%=toHtml(TicketDetails.getClosedString())%></font>
    </td>
  </tr>
<%}%>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="tickets" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
          <% String param2 = "id=" + TicketDetails.getId(); %>
          <strong>Ticket # <%=TicketDetails.getPaddedId()%>:</strong>
          [ <dhv:container name="accountstickets" selected="details" param="<%= param2 %>"/> ]
          <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
            <br><font color="red">This ticket was closed on <%= toHtml(TicketDetails.getClosedString()) %></font>
          </dhv:evaluate>
          <br><br>
        <form name="details" action="AccountTickets.do?command=UpdateTicket&auto-populate=true" onSubmit="return checkForm(this);" method="post">    
        <% if (TicketDetails.getClosed() != null) { %>
              <input type="submit" value="Reopen" onClick="javascript:this.form.action='AccountTickets.do?command=ReopenTicket&id=<%=TicketDetails.getId()%>'">
            <% if ("list".equals(request.getParameter("return"))) {%>
              <input type="submit" value="Cancel" onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%>'">
            <%} else {%> 
              <input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountTickets.do?command=TicketDetails&id=<%= TicketDetails.getId() %>'">
            <%}%>
        <%} else {%>
              <input type="submit" value="Update">
            <% if ("list".equals(request.getParameter("return"))) {%>
              <input type="submit" value="Cancel" onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%>'">
            <%} else {%> 
              <input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountTickets.do?command=TicketDetails&id=<%= TicketDetails.getId() %>'">
            <%}%>
              <%= showAttribute(request, "closedError") %>
        <%}%>
              <br>
              <%= showError(request, "actionError") %>
        <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
          <tr class="title">
            <td colspan="2">
            <strong>Ticket Information</strong>
            </td>     
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
            <td nowrap class="formLabel">
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
        <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
          <tr class="title">
            <td colspan="2">
              <strong>Classification</strong>
            </td>     
          </tr>
          <tr class="containerBody">
            <td class="formLabel" valign="top">
              <dhv:label name="tickets-problem">Issue</dhv:label>
            </td>
            <td>
              <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                    <textarea name="problem" cols="55" rows="3"><%= toString(TicketDetails.getProblem()) %></textarea>
                  </td>
                  <td valign="top">
                    <font color="red">*</font> <%= showAttribute(request, "problemError") %>
                  </td>
                </tr>
              </table>
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
        <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
          <tr class="title">
            <td colspan="2">
              <strong>Assignment</strong>
            </td>     
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
            <td valign=center>
              <%= UserList.getHtmlSelect("assignedTo", TicketDetails.getAssignedTo() ) %>
            </td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel" valign="top">
              User Comments
            </td>
            <td>
              <textarea name="comment" cols="55" rows="3"><%= toString(TicketDetails.getComment()) %></textarea>
            </td>
          </tr>
        </table>
        <br>
        <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
          <tr class="title">
            <td colspan="2">
              <strong>Resolution</strong>
            </td>     
          </tr>
          <tr class="containerBody">
            <td class="formLabel" valign="top">
              Solution
            </td>
            <td>
              <textarea name="solution" cols="55" rows="3"><%= toString(TicketDetails.getSolution()) %></textarea><br>
                <input type="checkbox" name="closeNow">Close ticket
                <br><input type="checkbox" name="kbase">Add this solution to Knowledge Base &nbsp;
              </td>
            </tr>
        </table>
        &nbsp;<br>
        <% if (TicketDetails.getClosed() != null) { %>
              <input type="submit" value="Reopen" onClick="javascript:this.form.action='AccountTickets.do?command=ReopenTicket&id=<%=TicketDetails.getId()%>'">
            <% if ("list".equals(request.getParameter("return"))) {%>
              <input type="submit" value="Cancel" onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%>'">
            <%} else {%> 
              <input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountTickets.do?command=TicketDetails&id=<%= TicketDetails.getId() %>'">
            <%}%>
        <%} else {%>
              <input type="submit" value="Update">
            <% if ("list".equals(request.getParameter("return"))) {%>
              <input type="submit" value="Cancel" onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%>'">
            <%} else {%> 
              <input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountTickets.do?command=TicketDetails&id=<%= TicketDetails.getId() %>'">
            <%}%>
        <%}%>
     </td>
 </tr>
<input type="hidden" name="modified" value="<%= TicketDetails.getModified() %>">
<input type="hidden" name="orgId" value="<%=TicketDetails.getOrgId()%>">
<input type="hidden" name="id" value="<%= TicketDetails.getId() %>">
<input type="hidden" name="companyName" value="<%= toHtml(TicketDetails.getCompanyName()) %>">
<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
</form>
</table>
</body>

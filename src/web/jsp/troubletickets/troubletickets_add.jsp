<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="DepartmentList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="com.darkhorseventures.cfsbase.TicketCategoryList" scope="request"/>
<jsp:useBean id="TicketDetails" class="com.darkhorseventures.cfsbase.Ticket" scope="request"/>
<jsp:useBean id="PriorityList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="OrgList" class="com.darkhorseventures.cfsbase.OrganizationList" scope="request"/>
<jsp:useBean id="SubList1" class="com.darkhorseventures.cfsbase.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="com.darkhorseventures.cfsbase.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="com.darkhorseventures.cfsbase.TicketCategoryList" scope="request"/>
<jsp:useBean id="UserList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="ContactList" class="com.darkhorseventures.cfsbase.ContactList" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript">
  function updateSubList1() {
    var sel = document.forms['addticket'].elements['catCode'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets.do?command=CategoryJSList&catCode=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  function updateSubList2() {
    var sel = document.forms['addticket'].elements['subCat1'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets.do?command=CategoryJSList&subCat1=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  function updateSubList3() {
    var sel = document.forms['addticket'].elements['subCat2'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets.do?command=CategoryJSList&subCat2=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  function updateUserList() {
    var sel = document.forms['addticket'].elements['departmentCode'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets.do?command=DepartmentJSList&departmentCode=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  function updateContactList() {
    var sel = document.forms['addticket'].elements['orgId'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets.do?command=OrganizationJSList&orgId=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
</script>
<form name="addticket" action="/TroubleTickets.do?command=Insert&auto-populate=true" method="post">
<input type="submit" value="Insert" name="Save">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/TroubleTickets.do?command=Home'">
<input type="reset" value="Reset">	
<%= showAttribute(request, "closedError") %>
<br>
&nbsp;

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

	<tr bgcolor="#DEE0FA">
	<td colspan="2" valign="center" align="left">
	<strong>Add a new Ticket</strong>
	</td>     
	</tr>
	
	<tr>
	<td width="100" class="formLabel">
	Ticket Source
	</td>
	<td bgColor="white">
	<%= SourceList.getHtmlSelect("sourceCode",  TicketDetails.getSourceCode()) %>
	</td>
	</tr>	
	
	<tr>
	<td width="100" class="formLabel">
	Organization
	</td>
	<td bgColor="white">
	<% if (TicketDetails == null || TicketDetails.getOrgId() == -1) { %>
	<%= OrgList.getHtmlSelectDefaultNone("orgId")%>
	<%} else {%>
	<%= OrgList.getHtmlSelect("orgId", TicketDetails.getOrgId()) %>
	<%}%>
	<font color="red">*</font> <%= showAttribute(request, "orgIdError") %>
	</td>
	</tr>	
	
		
	<tr>
	<td nowrap class="formLabel">
	Contact
	</td>
	<td valign="center">
	<% if (TicketDetails == null || TicketDetails.getOrgId() == -1 || ContactList.size() == 0) { %>
	<%= ContactList.getEmptyHtmlSelect("contactId") %>
	<%} else {%>
	<%= ContactList.getHtmlSelect("contactId", TicketDetails.getContactId() ) %>
	<%}%>
	<font color="red">*</font><%= showAttribute(request, "contactIdError") %>
	<input type="checkbox" name="contact" 
	
	<% if ( request.getParameter("contact") != null ) {%>
	checked
	<%}%>
	
	onClick="javascript:this.form.action='/TroubleTickets.do?command=Add&auto-populate=true#newcontact';this.form.submit()">Add new
	<a name="newcontact"></a> 
	
	</td>
	</tr>
	
	</table>
	
	<% if ( request.getParameter("contact") != null ) {%>
	<br>
	<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
	
	<tr bgcolor="#DEE0FA">
	<td colspan="2" valign="center" align="left">
	<strong>New Contact</strong>
	</td>     
	</tr>
	
	<tr>
    <td width="100" class="formLabel">
      First Name
    </td>
    <td valign="center">
      <input type=text size=35 name="thisContact_nameFirst" value="<%=TicketDetails.getThisContact().getNameFirst()%>">
    </td>
  </tr>

  <tr>
    <td width="100" class="formLabel">
      Last Name
    </td>
    <td valign="center">
      <input type=text size=35 name="thisContact_nameLast" value="<%=TicketDetails.getThisContact().getNameLast()%>">
      <font color="red">*</font> <%= showAttribute(request, "nameLastError") %>
    </td>
  </tr>
  
  <tr>
    <td width="100" class="formLabel">
      Title
    </td>
    <td valign="center">
      <input type=text size=35 name="thisContact_title" value="<%=TicketDetails.getThisContact().getTitle()%>">
    </td>
  </tr>
  
    <tr>
    <td width="100" class="formLabel">
      Email
    </td>
    <td>
      <input type="hidden" name="email1type" value="1">
      <input type="text" size="40" name="email1address" maxlength="255">
    </td>
  </tr>
  
    <tr>
    <td width="100" class="formLabel">
      Phone
    </td>
    <td>
      <input type="hidden" name="phone1type" value="1">
      <input type="text" size="3" name="phone1ac" maxlength="3">-
      <input type="text" size="3" name="phone1pre" maxlength="3">-
      <input type="text" size="4" name="phone1number" maxlength="4">ext.
      <input type="text" size="5" name="phone1ext" maxlength="10">
    </td>
  </tr>
  
</table>
	<%}%>

	
	<br>
	<a name="categories"></a> 
	<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
	
	<tr bgcolor="#DEE0FA">
    <td colspan="2" valign="center" align="left">
      <strong>Classification</strong>
    </td>     
	</tr>
	
	<tr>
    <td width="100" valign="top" class="formLabel">
      <dhv:label name="tickets-problem">Issue</dhv:label>
    </td>
    <td bgColor="white" valign="top">
      <textarea name="problem" cols=55 rows=3><%=TicketDetails.getProblem()%></textarea>
      <font color="red">*</font> <%= showAttribute(request, "problemError") %>
      <input type=hidden name=refresh value="-1">
    </td>
	</tr>
	
  <dhv:include name="tickets-code" none="true">
	<tr>
    <td width="100" class="formLabel">
      Category
    </td>
    <td bgColor="white">
      <%= CategoryList.getHtmlSelect("catCode", TicketDetails.getCatCode()) %>
    </td>
	</tr>
  </dhv:include>
	
  <dhv:include name="tickets-subcat1" none="true">
	<tr>
    <td width="100" class="formLabel">
      Sub-level 1
    </td>
    <td bgColor="white">
      <%= SubList1.getHtmlSelect("subCat1", TicketDetails.getSubCat1()) %>
      <input type=hidden name="close" value="">
    </td>
	</tr>
  </dhv:include>
	
  <dhv:include name="tickets-subcat2" none="true">
	<tr>
    <td width="100" class="formLabel">
      Sub-level 2
    </td>
    <td bgColor="white">
      <%= SubList2.getHtmlSelect("subCat2", TicketDetails.getSubCat2()) %>
    </td>
	</tr>
  </dhv:include>
	
  <dhv:include name="tickets-subcat3" none="true">
	<tr>
    <td width="100" class="formLabel">
      Sub-level 3
    </td>
    <td bgColor="white">
      <%= SubList3.getHtmlSelect("subCat3", TicketDetails.getSubCat3()) %>
    </td>
	</tr>
  </dhv:include>
	
	</table>
	<br>
	<a name="department"></a> 
	<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
	<tr bgcolor="#DEE0FA">
	<td colspan="2" valign="center" align="left">
	<strong>Assignment</strong>
	</td>     
	</tr>
	
  <dhv:include name="tickets-severity" none="true">
	<tr>
	<td width=100 class="formLabel">
	Severity
	</td>
	<td bgColor="white">
	<%= SeverityList.getHtmlSelect("severityCode",  TicketDetails.getSeverityCode()) %>
	</td>
	</tr>
  </dhv:include>
	
  <dhv:include name="tickets-priority" none="true">
	<tr>
	<td width=100 class="formLabel">
	Priority
	</td>
	<td bgColor="white">
	<%= PriorityList.getHtmlSelect("priorityCode", TicketDetails.getPriorityCode()) %>
	</td>
	</tr>
  </dhv:include>
	
	<tr>
	<td width=100 class="formLabel">
	Department
	</td>
	<td bgColor="white">
	<%= DepartmentList.getHtmlSelect("departmentCode", TicketDetails.getDepartmentCode()) %>
	</td>
	</tr>

	<tr>
	<td nowrap class="formLabel">
	Assign To
	</td>
	<td valign="center">
	<%= UserList.getHtmlSelect("assignedTo", TicketDetails.getAssignedTo() ) %>
	</td>
	</tr>
	
	<tr>
	<td width="100" valign="top" class="formLabel">
	Entry Comments
	</td>
	
	<td bgColor="white">
	<textarea name="comment" cols="55" rows="3"><%=TicketDetails.getComment()%></textarea>
	</td>
	</tr>
	
	</table>
	<br>
	<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
	<tr bgcolor="#DEE0FA">
	<td colspan="2" valign="center" align="left">
	<strong>Resolution</strong>
	</td>     
	</tr>
	
	<tr>
	<td width="100" valign="top" class="formLabel">
	Solution
	</td>
	
	<td bgColor="white">
	<textarea name="solution" cols="55" rows="3"><%=TicketDetails.getSolution()%></textarea><br>
	<input type=checkbox name="closeNow">Close ticket
	</td>
	</tr>
	
		<tr>
	<td width="100" class="formLabel">
	Knowledge Base
	</td>
	
	<td bgColor="white">
	<input type="checkbox" name="kbase" checked>Include this solution
	</td>
	</tr>

	
</table>
<br>
<input type="submit" value="Insert" name="Save">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/TroubleTickets.do?command=Home'">
<input type="reset" value="Reset">

</form>

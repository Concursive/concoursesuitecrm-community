<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<form name="details" action="Accounts.do?command=Fields&orgId=<%= OrgDetails.getOrgId() %>&catId=<%= Category.getId() %>" method="post">
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<dhv:evaluate if="<%= (Category.getAllowMultipleRecords()) %>">
  <a href="Accounts.do?command=Fields&orgId=<%= OrgDetails.getOrgId() %>&catId=<%= Category.getId() %>">List of Folder Records</a> >
</dhv:evaluate>
<% if (request.getParameter("return") == null) {%>
	<a href="Accounts.do?command=Fields&orgId=<%=OrgDetails.getOrgId()%>&catId=<%= Category.getId() %>&recId=<%= Category.getRecordId() %>">Folder Record Details</a> >
<%}%>
Modify Folder Record
<hr color="#BFBFBB" noshade>
<%@ include file="accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="folders" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      Folder: <strong><%= Category.getName() %></strong><br>
      &nbsp;<br>
      <input type="submit" value="Update" onClick="javascript:this.form.action='Accounts.do?command=UpdateFields&orgId=<%= OrgDetails.getOrgId() %>&catId=<%= Category.getId() %>&recId=<%= Category.getRecordId() %>'">
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='Accounts.do?command=Fields&orgId=<%= OrgDetails.getOrgId() %>&catId=<%= Category.getId() %>'"><br>
      &nbsp;<br>
<%
  Iterator groups = Category.iterator();
  while (groups.hasNext()) {
    CustomFieldGroup thisGroup = (CustomFieldGroup)groups.next();
%>    
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><%= thisGroup.getName() %></strong>
	  </th>
  </tr>
<%  
  Iterator fields = thisGroup.iterator();
  if (fields.hasNext()) {
    while (fields.hasNext()) {
      CustomField thisField = (CustomField)fields.next();
%>
    <tr class="containerBody">
      <%-- Do not use toHtml() here, it's done by CustomField --%>
      <td valign="top" nowrap class="formLabel">
        <%= thisField.getNameHtml() %>
      </td>
      <td valign="top">
        <%= thisField.getHtmlElement() %> <font color="red"><%= (thisField.getRequired()?"*":"") %></font>
        <font color='#006699'><%= toHtml(thisField.getError()) %></font>
        <%= toHtml(thisField.getAdditionalText()) %>
      </td>
    </tr>
<%    
    }
  } else {
%>
    <tr class="containerBody">
      <td colspan="2">
        <font color="#9E9E9E">No fields available.</font>
      </td>
    </tr>
<%}%>
</table>
&nbsp;
<%}%>
<br>
<input type="submit" value="Update" onClick="javascript:this.form.action='Accounts.do?command=UpdateFields&orgId=<%= OrgDetails.getOrgId() %>&catId=<%= Category.getId() %>&recId=<%= Category.getRecordId() %>'">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='Accounts.do?command=Fields&orgId=<%= OrgDetails.getOrgId() %>&catId=<%= Category.getId() %>'">
</td></tr>
</table>
</form>

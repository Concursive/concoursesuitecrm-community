<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="Record" class="org.aspcfs.modules.base.CustomFieldRecord" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<form name="details" action="Accounts.do?command=Fields&orgId=<%= OrgDetails.getOrgId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<dhv:evaluate if="<%= (Category.getAllowMultipleRecords() && Record == null) %>">
List of Folder Records
</dhv:evaluate>
<dhv:evaluate if="<%= (Category.getAllowMultipleRecords() && Record != null) %>">
<a href="Accounts.do?command=Fields&orgId=<%= OrgDetails.getOrgId() %>&catId=<%= Category.getId() %>">List of Folder Records</a> >
Folder Record Details
</dhv:evaluate>
<dhv:evaluate if="<%= (!Category.getAllowMultipleRecords()) %>">
Folder Record Details
</dhv:evaluate>
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="folders" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<% if (!Category.getAllowMultipleRecords()) {
     CategoryList.setJsEvent("ONCHANGE=\"javascript:document.forms[0].submit();\"");
%>
  Folder: <%= CategoryList.getHtmlSelect("catId", (String)request.getAttribute("catId")) %><%= (Category.getReadOnly()?"&nbsp;<img border='0' valign='absBottom' src='images/lock.gif' alt='Folder is read-only'>":"") %><br>
  &nbsp;<br>
  This folder can have only one record...<br>
  &nbsp;<br>
<% } %>
<dhv:evaluate exp="<%= (!Category.getReadOnly()) %>">
<dhv:permission name="accounts-accounts-folders-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='Accounts.do?command=ModifyFields&orgId=<%= OrgDetails.getOrgId()%>&catId=<%= (String)request.getAttribute("catId") %>&recId=<%= Category.getRecordId() %>';submit();"></dhv:permission>
<dhv:permission name="accounts-accounts-folders-delete"><input type="button" value="Delete Folder Record" onClick="javascript:this.form.action='Accounts.do?command=DeleteFields&orgId=<%= OrgDetails.getOrgId()%>&catId=<%= (String)request.getAttribute("catId") %>&recId=<%= Category.getRecordId() %>';confirmSubmit(this.form);"></dhv:permission>
<dhv:permission name="accounts-accounts-folders-edit,accounts-accounts-folders-delete">
<br>&nbsp;<br>
</dhv:permission>
</dhv:evaluate>
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
        <%= thisField.getValueHtml() %>
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
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Record Information</strong>
    </th>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Entered
    </td>
    <td>
      <dhv:username id="<%= Record.getEnteredBy() %>" />
      <zeroio:tz timestamp="<%= Record.getEntered() %>" />
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Modified
    </td>
    <td>
      <dhv:username id="<%= Record.getModifiedBy() %>" />
      <zeroio:tz timestamp="<%= Record.getModified() %>" />
    </td>
  </tr>
</table>
&nbsp;
<br>
<dhv:evaluate exp="<%= (!Category.getReadOnly()) %>">
<dhv:permission name="accounts-accounts-folders-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='Accounts.do?command=ModifyFields&orgId=<%= OrgDetails.getOrgId()%>&catId=<%= (String)request.getAttribute("catId") %>&recId=<%= Category.getRecordId() %>';submit();"></dhv:permission>
<dhv:permission name="accounts-accounts-folders-delete"><input type="button" value="Delete Folder Record" onClick="javascript:this.form.action='Accounts.do?command=DeleteFields&orgId=<%= OrgDetails.getOrgId()%>&catId=<%= (String)request.getAttribute("catId") %>&recId=<%= Category.getRecordId() %>';confirmSubmit(this.form);"></dhv:permission>
</dhv:evaluate>
</td></tr>
</table>
</form>

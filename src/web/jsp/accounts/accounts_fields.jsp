<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="CategoryList" class="com.darkhorseventures.cfsbase.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="com.darkhorseventures.cfsbase.CustomFieldCategory" scope="request"/>
<jsp:useBean id="Record" class="com.darkhorseventures.cfsbase.CustomFieldRecord" scope="request"/>
<%@ include file="initPage.jsp" %>
<form name="details" action="/Accounts.do?command=Fields&orgId=<%= OrgDetails.getOrgId() %>" method="post">
<a href="/Accounts.do?command=View">Back to Account List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <a href="/Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Details</font></a><dhv:permission name="accounts-accounts-folders-view"> | 
      <a href="/Accounts.do?command=Fields&orgId=<%= OrgDetails.getOrgId() %>"><font color="#0000FF">Folders</font></a></dhv:permission><dhv:permission name="accounts-accounts-contacts-view"> |
      <a href="Contacts.do?command=View&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Contacts</font></a></dhv:permission><dhv:permission name="accounts-accounts-opportunities-view"> | 
      <a href="Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Opportunities</font></a></dhv:permission><dhv:permission name="accounts-accounts-tickets-view"> | 
      <a href="Accounts.do?command=ViewTickets&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Tickets</font></a></dhv:permission><dhv:permission name="accounts-accounts-documents-view"> |
      <a href="AccountsDocuments.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><font color="#000000">Documents</font></a></dhv:permission>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<% if (Category.getAllowMultipleRecords()) { %>
<a href="/Accounts.do?command=Fields&orgId=<%= OrgDetails.getOrgId() %>&catId=<%= Category.getId() %>">Back to <%= Category.getName() %> Records</a>
<% } else { 
     CategoryList.setJsEvent("ONCHANGE=\"javascript:document.forms[0].submit();\"");
%>
  <%= CategoryList.getHtmlSelect("catId", (String)request.getAttribute("catId")) %><%= (Category.getReadOnly()?"&nbsp;<img border='0' valign='absBottom' src='images/lock.gif' alt='Folder is read-only'>":"") %>
<% } %>
<br>
&nbsp;<br>
<dhv:evaluate exp="<%= (!Category.getReadOnly()) %>">
<dhv:permission name="accounts-accounts-folders-edit"><input type="submit" value="Modify" onClick="javascript:this.form.action='/Accounts.do?command=ModifyFields&orgId=<%= OrgDetails.getOrgId()%>&catId=<%= (String)request.getAttribute("catId") %>&recId=<%= Category.getRecordId() %>'"></dhv:permission>
<dhv:permission name="accounts-accounts-folders-delete"><input type="submit" value="Delete Folder Record" onClick="javascript:this.form.action='/Accounts.do?command=DeleteFields&orgId=<%= OrgDetails.getOrgId()%>&catId=<%= (String)request.getAttribute("catId") %>&recId=<%= Category.getRecordId() %>'"></dhv:permission>
<dhv:permission name="accounts-accounts-folders-edit,accounts-accounts-folders-delete">
<br>&nbsp;<br>
</dhv:permission>
</dhv:evaluate>
<%
  Iterator groups = Category.iterator();
  while (groups.hasNext()) {
    CustomFieldGroup thisGroup = (CustomFieldGroup)groups.next();
%>    
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
	    <strong><%= thisGroup.getName() %></strong>
	  </td>
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
      <td valign="top" width="100%">
        <%= thisField.getValueHtml() %>
      </td>
    </tr>
<%    
    }
  } else {
%>
    <tr class="containerBody">
      <td colspan=2>
        <font color="#9E9E9E">No fields available.</font>
      </td>
    </tr>
<%}%>
</table>
&nbsp;
<%}%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Record Information</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Entered
    </td>
    <td>
      <dhv:username id="<%= Record.getEnteredBy() %>" />&nbsp;-&nbsp;<%= Record.getEnteredString() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Modified
    </td>
    <td>
      <dhv:username id="<%= Record.getModifiedBy() %>" />&nbsp;-&nbsp;<%= toHtml(Record.getModifiedDateTimeString()) %>
    </td>
  </tr>
</table>
&nbsp;
<br>
<dhv:evaluate exp="<%= (!Category.getReadOnly()) %>">
<dhv:permission name="accounts-accounts-folders-edit"><input type="submit" value="Modify" onClick="javascript:this.form.action='/Accounts.do?command=ModifyFields&orgId=<%= OrgDetails.getOrgId()%>&catId=<%= (String)request.getAttribute("catId") %>&recId=<%= Category.getRecordId() %>'"></dhv:permission>
<dhv:permission name="accounts-accounts-folders-delete"><input type="submit" value="Delete Folder Record" onClick="javascript:this.form.action='/Accounts.do?command=DeleteFields&orgId=<%= OrgDetails.getOrgId()%>&catId=<%= (String)request.getAttribute("catId") %>&recId=<%= Category.getRecordId() %>'"></dhv:permission>
</dhv:evaluate>
</td></tr>
</table>
</form>

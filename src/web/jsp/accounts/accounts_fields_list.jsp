<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="CategoryList" class="com.darkhorseventures.cfsbase.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="com.darkhorseventures.cfsbase.CustomFieldCategory" scope="request"/>
<jsp:useBean id="Records" class="com.darkhorseventures.cfsbase.CustomFieldRecordList" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
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
<%
  CategoryList.setJsEvent("ONCHANGE=\"javascript:document.forms[0].submit();\"");
  if (CategoryList.size() > 0) {
%>
    <%= CategoryList.getHtmlSelect("catId", (String)request.getAttribute("catId")) %><br>
    &nbsp;<br>
    <dhv:permission name="accounts-accounts-folders-add"><a href="/Accounts.do?command=AddFolderRecord&orgId=<%= OrgDetails.getOrgId() %>&catId=<%= (String)request.getAttribute("catId") %>">Add a record to this folder</a><br>&nbsp;<br></dhv:permission>
    <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
      <tr class="title">
        <dhv:permission name="accounts-accounts-folders-edit,accounts-accounts-folders-delete">
          <td valign=center align=left bgcolor="#DEE0FA">
            <strong>Action</strong>
          </td>
        </dhv:permission>
        <td align="left">
          <strong>Record</strong>
        </td>
        <td align="left">
          <strong>Entered</strong>
        </td>
        <td align="left">
          <strong>Modified By</strong>
        </td>
        <td align="left">
          <strong>Last Modified</strong>
        </td>
      </tr>
<%
    if (Records.size() > 0) {
      int rowid = 0;
      Iterator records = Records.iterator();
      while (records.hasNext()) {
        if (rowid != 1) {
          rowid = 1;
        } else {
          rowid = 2;
        }
        CustomFieldRecord thisRecord = (CustomFieldRecord)records.next();
%>    
      <tr class="containerBody">
      
        <dhv:permission name="accounts-accounts-folders-edit,accounts-accounts-folders-delete">
        <td width="8" valign="center" nowrap class="row<%= rowid %>">
          <dhv:permission name="accounts-accounts-folders-edit"><a href="/Accounts.do?command=ModifyFields&orgId=<%= OrgDetails.getOrgId() %>&catId=<%= Category.getId() %>&recId=<%= thisRecord.getId() %>&return=list">Edit</a></dhv:permission><dhv:permission name="accounts-accounts-folders-edit,accounts-accounts-folders-delete" all="true">|</dhv:permission><dhv:permission name="accounts-accounts-folders-delete"><a href="javascript:confirmDelete('/Accounts.do?command=DeleteFields&orgId=<%= OrgDetails.getOrgId() %>&catId=<%= Category.getId() %>&recId=<%= thisRecord.getId() %>');">Del</a></dhv:permission>
          </td>
        </dhv:permission>
      
        <td align="left" width="100%" nowrap class="row<%= rowid %>">
          <a href="/Accounts.do?command=Fields&orgId=<%= OrgDetails.getOrgId() %>&catId=<%= Category.getId() %>&recId=<%= thisRecord.getId() %>"><%= Category.getName() %> #<%= thisRecord.getId() %></a>
        </td>
        <td nowrap class="row<%= rowid %>">
          <%= toHtml(thisRecord.getEnteredString()) %>
        </td>
        <td nowrap class="row<%= rowid %>">
          <dhv:username id="<%= thisRecord.getModifiedBy() %>" />
        </td>
        <td nowrap class="row<%= rowid %>">
          <%= toHtml(thisRecord.getModifiedDateTimeString()) %>
        </td>
      </tr>
<%    
      }
    } else {
%>
      <tr class="containerBody">
        <td colspan="5">
          <font color="#9E9E9E">No records have been entered.</font>
        </td>
      </tr>
<%  }  %>
<%} else {%>
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="containerBody">
      <td>
        No custom folders available.
      </td>
    </tr>
<%}%>
  </table>
</td></tr>
</table>
</form>

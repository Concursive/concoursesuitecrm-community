<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="CategoryList" class="com.darkhorseventures.cfsbase.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="com.darkhorseventures.cfsbase.CustomFieldCategory" scope="request"/>
<jsp:useBean id="Record" class="com.darkhorseventures.cfsbase.CustomFieldRecord" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<form name="details" action="/Accounts.do?command=Fields&orgId=<%= OrgDetails.getOrgId() %>" method="post">
<a href="/Accounts.do">Account Management</a> > 
<a href="/Accounts.do?command=View">View Accounts</a> >
<a href="/Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<% if (Record == null) { %>
Folders<br>
<%} else {%>
<a href="/Accounts.do?command=Fields&orgId=<%=OrgDetails.getOrgId()%>">Folders</a> >
Record Details<br>
<%}%>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="folders" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      &nbsp;<br>
      There are currently no custom folders configured for this module.<br>
      Custom folders can be configured by an administrator.<br>
      &nbsp;
    </td>
  </tr>
</table>
</form>

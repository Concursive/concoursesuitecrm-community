<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.PermissionCategory, java.util.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="PermissionCategoryList" class="org.aspcfs.modules.admin.base.PermissionCategoryList" scope="request"/>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Admin.do">Admin</a> >
Configure Modules
</td>
</tr>
</table>
<%-- End Trails --%>
<table class="note">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td>Setup Dark Horse CRM to meet the specific needs of your organization, 
    including configuration of lookup lists and custom fields.  Choose
    a Dark Horse CRM module to proceed.</td></tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Modules</strong>
    </th>
  </tr>
<%
  int column = 0;
  Iterator i = PermissionCategoryList.iterator();
  while (i.hasNext()) {
    column = (column != 1 ? 1 : 2);
    PermissionCategory thisPermissionCat = (PermissionCategory) i.next();
%>
  <tr class="row<%= column %>">
    <td>
      <a href="Admin.do?command=ConfigDetails&moduleId=<%= thisPermissionCat.getId() %>"><%= toHtml(thisPermissionCat.getCategory()) %></a>
    </td>
  </tr>
<%}%>
</table>


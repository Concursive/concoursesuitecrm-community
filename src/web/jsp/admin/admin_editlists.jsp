<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.Iterator,org.aspcfs.modules.admin.base.PermissionCategory, org.aspcfs.utils.web.LookupList,org.aspcfs.utils.web.LookupListElement" %>
<jsp:useBean id="LookupLists" class="org.aspcfs.utils.web.LookupListList" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<a href="Admin.do">Setup</a> >
<a href="Admin.do?command=Config">Configure Modules</a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
Lookup Lists<br> 
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td width="35" align="center">
      <strong>Action</strong>
    </td>
    <td width="200">
      <strong>List Name</strong>
    </td>
    <td width="35" align="center">
      <strong>Items</strong>
    </td>
    <td>
      <strong>Preview</strong>
    </td>
  </tr>
<%
  Iterator i = LookupLists.iterator();
  if (i.hasNext()){
    while (i.hasNext()) {
    LookupListElement thisElement = (LookupListElement)i.next();
%>
    <tr>
      <dhv:permission name="admin-sysconfig-lists-edit"><td align="center"><a href="Admin.do?command=ModifyList&module=<%= thisElement.getModuleId() %>&sublist=<%= thisElement.getLookupId() %>">Edit</a></td></dhv:permission>
      <td valign="center" width="200"><%= toHtml(thisElement.getDescription()) %></td>
      <td width="35" valign="center" align="center"><%= thisElement.getLookupList().getEnabledElementCount() %></td>
      <td valign="center"><%= thisElement.getLookupList().getHtmlSelect("list", 0) %></td>
    </tr>
<%
     }
   } else {%>
  <tr>
    <td valign="center" colspan="4">No custom lookup lists to configure.</td>
  </tr>  
<%}%>
</table>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.Iterator,org.aspcfs.modules.admin.base.PermissionCategory, org.aspcfs.utils.web.LookupList,org.aspcfs.utils.web.LookupListElement" %>
<jsp:useBean id="LookupLists" class="org.aspcfs.utils.web.LookupListList" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Admin.do">Admin</a> >
<a href="Admin.do?command=Config">Configure Modules</a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
Lookup Lists
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="35" align="center">
      <strong>Action</strong>
    </th>
    <th width="200">
      <strong>List Name</strong>
    </th>
    <th width="35" align="center">
      <strong>Items</strong>
    </th>
    <th>
      <strong>Preview</strong>
    </th>
  </tr>
<%
  int rowid = 0;
  Iterator i = LookupLists.iterator();
  while (i.hasNext()) {
    rowid = (rowid != 1 ? 1 : 2);
    LookupListElement thisElement = (LookupListElement)i.next();
%>
    <tr class="row<%= rowid %>">
      <dhv:permission name="admin-sysconfig-lists-edit"><td align="center"><a href="Admin.do?command=ModifyList&module=<%= thisElement.getModuleId() %>&sublist=<%= thisElement.getLookupId() %>">Edit</a></td></dhv:permission>
      <td valign="center" width="200"><%= toHtml(thisElement.getDescription()) %></td>
      <td width="35" valign="center" align="center"><%= thisElement.getLookupList().getEnabledElementCount() %></td>
      <td valign="center"><%= thisElement.getLookupList().getHtmlSelect("list", 0) %></td>
    </tr>
<%
     }
%>
<dhv:evaluate if="<%= LookupLists.size() == 0 %>">
  <tr>
    <td valign="center" colspan="4">No custom lookup lists to configure.</td>
  </tr>
</dhv:evaluate>
</table>

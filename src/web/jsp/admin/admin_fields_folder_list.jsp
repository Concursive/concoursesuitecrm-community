<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,java.text.*,org.aspcfs.modules.admin.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="details" action="AdminFieldsFolder.do?command=ListFolders" method="post">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Admin.do">Admin</a> >
<a href="Admin.do?command=Config">Configure Modules</a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%=PermissionCategory.getId()%>"><%= PermissionCategory.getCategory() %></a> >
Custom Folders
</td>
</tr>
</table>
<%-- End Trails --%>
  <dhv:permission name="admin-sysconfig-folders-add">
    <a href="AdminFieldsFolder.do?command=AddFolder&modId=<%= PermissionCategory.getId() %>">Add a Folder to this Module</a><br>
    <% if (request.getAttribute("actionError") == null) { %>
    &nbsp;<br>
    <%}%>
  </dhv:permission>
<% if (request.getAttribute("actionError") != null) { %>
<%= showError(request, "actionError") %>
<%}%>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <dhv:permission name="admin-sysconfig-folders-edit">
      <th align="center">
        <strong>Action</strong>
      </th>
      </dhv:permission>
      <th width="100%">
        <strong>Custom Folders</strong>
      </th>
      <th align="center">
        <strong>Enabled</strong>
      </th>
      <th align="center" nowrap>
        <strong>Active Date</strong>
      </th>
      <th align="center" nowrap>
        <strong>End Date</strong>
      </th>
    </tr>
<%
  if (CategoryList.size() > 0) {
    int rowId = 0;
    Iterator records = CategoryList.iterator();
    while (records.hasNext()) {
      rowId = (rowId == 1 ? 2 : 1);
      CustomFieldCategory thisCategory = (CustomFieldCategory) records.next();
%>    
    <tr class="row<%= rowId %>">
      <dhv:permission name="admin-sysconfig-folders-edit">
      <td align="center">
        <a href="AdminFieldsFolder.do?command=ModifyFolder&modId=<%= PermissionCategory.getId() %>&catId=<%= thisCategory.getId() %>">Edit</a>
      </td>
      </dhv:permission>
      <td width="100%">
        <dhv:permission name="admin-sysconfig-folders-view"><a href="AdminFieldsGroup.do?command=ListGroups&modId=<%= PermissionCategory.getId() %>&catId=<%= thisCategory.getId() %>"><%= toHtml(thisCategory.getName()) %></a><%= (thisCategory.getReadOnly()?"&nbsp;<img border='0' valign='absBottom' src='images/lock.gif'>":"") %></dhv:permission>
      </td>
      <td align="center" nowrap>
        <dhv:permission name="admin-sysconfig-folders-edit">
          <a href="AdminFieldsFolder.do?command=ToggleFolder&modId=<%= PermissionCategory.getId() %>&catId=<%= thisCategory.getId() %>"></dhv:permission>
          <%= (thisCategory.getEnabled()? "Yes" : "No") %>
          <dhv:permission name="admin-sysconfig-folders-edit"></a></dhv:permission>
      </td>
      <td align="center" nowrap>
        <dhv:tz timestamp="<%= thisCategory.getStartDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
      </td>
      <td align="center" nowrap>
        <dhv:tz timestamp="<%= thisCategory.getEndDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
      </td>
    </tr>
<%    
    }
  } else {
%>
    <tr class="containerBody">
      <td colspan="5">
        <font color="#9E9E9E">No folders have been entered.</font>
      </td>
    </tr>
<%}%>
  </table>
</form>

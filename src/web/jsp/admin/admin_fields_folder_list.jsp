<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="ModuleList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="com.darkhorseventures.cfsbase.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="com.darkhorseventures.cfsbase.CustomFieldCategory" scope="request"/>
<%@ include file="initPage.jsp" %>
<form name="details" action="/AdminFieldsFolder.do?command=ListFolders" method="post">
<a href="/Admin.do">Setup</a> >
<a href="/Admin.do?command=Config">System Configuration</a> >
Custom Folders<br>
&nbsp;<br>
<%
  ModuleList.setJsEvent("onChange=\"javascript:document.forms[0].submit();\"");
%>
  Module: <%= ModuleList.getHtmlSelect("modId", ModuleList.getSelectedKey()) %><br>
  &nbsp;<br>
  <dhv:permission name="admin-sysconfig-folders-add">
    <a href="/AdminFieldsFolder.do?command=AddFolder&modId=<%= ModuleList.getSelectedKey() %>">Add a Folder to this Module</a><br>
    &nbsp;<br>
  </dhv:permission>
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <dhv:permission name="admin-sysconfig-folders-edit">
      <td width="8" align="center">
        <strong>Action</strong>
      </td>
      </dhv:permission>
      <td align="left">
        <strong>Custom Folders</strong>
      </td>
      <td align="center">
        <strong>Enabled</strong>
      </td>
      <td align="center" nowrap>
        <strong>Active Date</strong>
      </td>
      <td align="center" nowrap>
        <strong>End Date</strong>
      </td>
    </tr>
<%
  if (CategoryList.size() > 0) {
    int rowId = 0;
    Iterator records = CategoryList.iterator();
    while (records.hasNext()) {
      rowId = (rowId == 1 ? 2 : 1);
      CustomFieldCategory thisCategory = (CustomFieldCategory)records.next();
%>    
    <tr class="row<%= rowId %>">
      <dhv:permission name="admin-sysconfig-folders-edit">
      <td align="center">
        <a href="/AdminFieldsFolder.do?command=ModifyFolder&modId=<%= ModuleList.getSelectedKey() %>&catId=<%= thisCategory.getId() %>">Details</a>
      </td>
      </dhv:permission>
      <td align="left" width="100%" nowrap>
        <dhv:permission name="admin-sysconfig-folders-view"><a href="/AdminFieldsGroup.do?command=ListGroups&modId=<%= ModuleList.getSelectedKey() %>&catId=<%= thisCategory.getId() %>"><%= toHtml(thisCategory.getName()) %></a></dhv:permission>
      </td>
      <td align="center" nowrap>
        <dhv:permission name="admin-sysconfig-folders-edit">
          <a href="/AdminFieldsFolder.do?command=ToggleFolder&modId=<%= ModuleList.getSelectedKey() %>&catId=<%= thisCategory.getId() %>"></dhv:permission>
          <%= (thisCategory.getEnabled()? "Yes" : "No") %>
          <dhv:permission name="admin-sysconfig-folders-edit"></a></dhv:permission>
      </td>
      <td align="center" nowrap>
        <%= toHtml(toDateString(thisCategory.getStartDate())) %>
      </td>
      <td align="center" nowrap>
        <%= toHtml(toDateString(thisCategory.getEndDate())) %>
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

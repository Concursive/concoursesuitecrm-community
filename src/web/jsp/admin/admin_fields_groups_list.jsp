<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="CategoryList" class="com.darkhorseventures.cfsbase.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="com.darkhorseventures.cfsbase.CustomFieldCategory" scope="request"/>
<jsp:useBean id="ModId" class="java.lang.String" scope="request"/>
<jsp:useBean id="PermissionCategory" class="com.darkhorseventures.cfsbase.PermissionCategory" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    
    if (confirm("Are you sure you want to delete this folder " +
              "and any associated data that may have been entered?")) {
      form.action = "/AdminFieldsFolder.do?command=DeleteFolder&modId=<%= ModId %>&catId=<%= Category.getId() %>&auto-populate=true";
      return true;
    } else {
      return false;
    }
  }
  
  function confirmDeleteGroup(url) {
    if (confirm('Are you sure you want to delete the group, the fields in this group, and any associated data entered in the module records?')) {
      window.location = url;
    }
  }
  
  function confirmDeleteField(url) {
    if (confirm('Are you sure you want to delete the field, and any associated data entered in the module records?')) {
      window.location = url;
    }
  }
</script>
<form name="details" action="/AdminFieldsGroup.do?command=ListGroups&modId=<%= ModId %>" method="post" onSubmit="return checkForm(this);">
<a href="/Admin.do">Setup</a> >
<a href="/Admin.do?command=Config&modId=<%= ModId %>">System Configuration</a> >
<a href="/Admin.do?command=ConfigDetails&moduleId=<%=ModId%>">Configuration Options</a> >
<a href="/AdminFieldsFolder.do?command=ListFolders&modId=<%= ModId %>">Custom Folders</a> > 
Folder<br>
<hr color="#BFBFBB" noshade>

<% if (request.getAttribute("actionError") != null) { %>
<%= showAttribute(request, "actionError") %><br>
<%}%>

<%
  CategoryList.setJsEvent("onChange=\"javascript:this.form.dosubmit.value='false';document.forms[0].submit();\"");
%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong>Module: <%=PermissionCategory.getCategory()%></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      Folder: <%= CategoryList.getHtmlSelect("catId", Category.getId()) %>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <dhv:permission name="admin-sysconfig-folders-add">
        <a href="/AdminFieldsGroup.do?command=AddGroup&modId=<%= ModId %>&catId=<%= Category.getId() %>">Add a Group to this Folder</a><br>
        &nbsp;<br>
      </dhv:permission>
<%
  if (Category.size() > 0) {
    int rowId = 0;
    Iterator groups = Category.iterator();
    int groupLevel = 0;
    while (groups.hasNext()) {
      rowId = 0;
      ++groupLevel;
      CustomFieldGroup thisGroup = (CustomFieldGroup)groups.next();
%>
  <dhv:permission name="admin-sysconfig-folders-edit,admin-sysconfig-folders-add,admin-sysconfig-folders-delete">
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td>
        <dhv:permission name="admin-sysconfig-folders-edit">
          <a href="/AdminFieldsGroup.do?command=ModifyGroup&modId=<%= ModId %>&catId=<%= Category.getId() %>&grpId=<%= thisGroup.getId() %>">Edit</a> |
        </dhv:permission>
        <dhv:permission name="admin-sysconfig-folders-delete">
          <a href="javascript:confirmDeleteGroup('/AdminFieldsGroup.do?command=DeleteGroup&modId=<%= ModId %>&catId=<%= Category.getId() %>&groupId=<%= thisGroup.getId() %>&auto-populate=true');">Del</a> |
        </dhv:permission>
        <dhv:permission name="admin-sysconfig-folders-edit,admin-sysconfig-folders-add">
          <a href="/AdminFieldsGroup.do?command=MoveGroup&modId=<%= ModId %>&catId=<%= Category.getId() %>&chg=<%= groupLevel %>|U">Up</a> |
          <a href="/AdminFieldsGroup.do?command=MoveGroup&modId=<%= ModId %>&catId=<%= Category.getId() %>&chg=<%= groupLevel %>|D">Down</a> 
          <dhv:permission name="admin-sysconfig-folders-add">|</dhv:permission>
        </dhv:permission>
        <dhv:permission name="admin-sysconfig-folders-add">
          <a href="/AdminFields.do?command=AddField&modId=<%= ModId %>&catId=<%= Category.getId() %>&grpId=<%= thisGroup.getId() %>">Add a Custom Field</a>
        </dhv:permission>
      </td>
    </tr>
  </table>
  </dhv:permission>
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td <dhv:permission name="admin-sysconfig-folders-edit,admin-sysconfig-folders-delete,admin-sysconfig-folders-add">colspan="3" </dhv:permission><dhv:permission name="admin-sysconfig-folders-edit,admin-sysconfig-folders-delete" none="true">colspan="2" </dhv:permission>width="100%" nowrap>
        <strong><%= thisGroup.getName() %></strong>
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
      Iterator fields = thisGroup.iterator();
      int fieldLevel = 0;
      while (fields.hasNext()) {
        rowId = (rowId == 1 ? 2 : 1);
        ++fieldLevel;
        CustomField thisField = (CustomField)fields.next();
%>    
    <tr class="row<%= rowId %>">
      <dhv:permission name="admin-sysconfig-folders-edit,admin-sysconfig-folders-delete,admin-sysconfig-folders-add">
      <td align="left" nowrap>
        <dhv:permission name="admin-sysconfig-folders-edit">
          <a href="/AdminFields.do?command=ModifyField&id=<%= thisField.getId() %>&modId=<%= ModId %>&catId=<%= Category.getId() %>&grpId=<%= thisField.getGroupId() %>">Edit</a>
        </dhv:permission>
        <dhv:permission name="admin-sysconfig-folders-edit,admin-sysconfig-folders-delete" all="true">|</dhv:permission>
        <dhv:permission name="admin-sysconfig-folders-delete">
          <a href="javascript:confirmDeleteField('/AdminFields.do?command=DeleteField&id=<%= thisField.getId() %>&modId=<%= ModId %>&catId=<%= Category.getId() %>&groupId=<%= thisField.getGroupId() %>&auto-populate=true');">Del</a>
        </dhv:permission>
        <dhv:permission name="admin-sysconfig-folders-edit,admin-sysconfig-folders-delete">|</dhv:permission>
        <dhv:permission name="admin-sysconfig-folders-edit,admin-sysconfig-folders-add">
          <a href="/AdminFields.do?command=MoveField&modId=<%= ModId %>&catId=<%= Category.getId() %>&chg=<%= groupLevel %>|<%= fieldLevel %>|U">Up </a>| <a href="/AdminFields.do?command=MoveField&modId=<%= ModId %>&catId=<%= Category.getId() %>&chg=<%= groupLevel %>|<%= fieldLevel %>|D">Down</a>
        </dhv:permission>
      </td>
      </dhv:permission>
      <td width="100%" nowrap>
        <%= thisField.getName() %><font color="red"><%= thisField.getRequired()?"*":"" %></font>
      </td>
      <td width="30%" nowrap>
        <%= thisField.getTypeString() %>
      </td>
      <td width="10%" align="center" nowrap>
        <%= (thisField.getEnabled()? "Yes" : "No") %>
      </td>
      <td width="10%" align="center" nowrap>
        <%= toHtml(toDateString(thisField.getStartDate())) %>
      </td>
      <td width="10%" align="center" nowrap>
        <%= toHtml(toDateString(thisField.getEndDate())) %>
      </td>
    </tr>
<%    
      }
%>
  </table>
  &nbsp;<br>
<%      
    }
  } else {
%>
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="containerBody">
      <td colspan="6">
        <font color="#9E9E9E">No groups have been added.</font>
      </td>
    </tr>
  </table>
  &nbsp;<br>
<%}%>
      <input type="hidden" name="dosubmit" value="true">
      <input type="hidden" name="moduleId" value="<%= ModId %>">
      <input type="hidden" name="categoryId" value="<%= Category.getId() %>">
      <dhv:permission name="admin-sysconfig-folders-delete">
        <input type='submit' value='Delete this folder and all fields' onClick="javascript:this.form.dosubmit.value='true';">
      </dhv:permission>
    </td>
  </tr>
</table>
</form>

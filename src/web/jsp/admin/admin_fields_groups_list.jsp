<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="ModuleList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="com.darkhorseventures.cfsbase.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="com.darkhorseventures.cfsbase.CustomFieldCategory" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    
    if (confirm("Are you sure you want to delete this folder " +
              "and any associated data that may have been entered?")) {
      form.action = "/AdminFieldsFolder.do?command=DeleteFolder&modId=<%= ModuleList.getSelectedKey() %>&catId=<%= Category.getId() %>&auto-populate=true";
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
<form name="details" action="/AdminFieldsGroup.do?command=ListGroups&modId=<%= ModuleList.getSelectedKey() %>" method="post" onSubmit="return checkForm(this);">
<a href="/Admin.do">Setup</a> >
<a href="/Admin.do?command=Config&modId=<%= ModuleList.getSelectedKey() %>">System Configuration</a> >
<a href="/AdminFieldsFolder.do?command=ListFolders&modId=<%= ModuleList.getSelectedKey() %>">Custom Folders</a> > 
Folder<br>
<%= showAttribute(request, "actionError") %><br>
<%
  CategoryList.setJsEvent("onChange=\"javascript:this.form.dosubmit.value='false';document.forms[0].submit();\"");
%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong>Module: <%= toHtml(ModuleList.getSelectedValue(ModuleList.getSelectedKey())) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      Folder: <%= CategoryList.getHtmlSelect("catId", Category.getId()) %>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <a href="/AdminFieldsGroup.do?command=AddGroup&modId=<%= ModuleList.getSelectedKey() %>&catId=<%= Category.getId() %>">Add a Group to this Folder</a><br>
      &nbsp;<br>
<%
  if (Category.size() > 0) {
    int rowId = 0;
    Iterator groups = Category.iterator();
    while (groups.hasNext()) {
      rowId = 0;
      //rowId = (rowId == 1 ? 2 : 1);
      CustomFieldGroup thisGroup = (CustomFieldGroup)groups.next();
%>      
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td>
        <a href="/AdminFieldsGroup.do?command=ModifyGroup&modId=<%= ModuleList.getSelectedKey() %>&catId=<%= Category.getId() %>&grpId=<%= thisGroup.getId() %>">Edit</a> |
        <a href="javascript:confirmDeleteGroup('/AdminFieldsGroup.do?command=DeleteGroup&modId=<%= ModuleList.getSelectedKey() %>&catId=<%= Category.getId() %>&groupId=<%= thisGroup.getId() %>&auto-populate=true');">Del</a> | 
        Up | 
        Down | 
        <a href="/AdminFields.do?command=AddField&modId=<%= ModuleList.getSelectedKey() %>&catId=<%= Category.getId() %>&grpId=<%= thisGroup.getId() %>">Add a Custom Field</a>
      </td>
    </tr>
  </table>
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td colspan="4" width="100%" nowrap>
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
      while (fields.hasNext()) {
        rowId = (rowId == 1 ? 2 : 1);
        CustomField thisField = (CustomField)fields.next();
%>    
    <tr class="row<%= rowId %>">
      <td align="left" width="8" nowrap>
        Edit|<a href="javascript:confirmDeleteField('/AdminFields.do?command=DeleteField&id=<%= thisField.getId() %>&modId=<%= ModuleList.getSelectedKey() %>&catId=<%= Category.getId() %>&groupId=<%= thisField.getGroupId() %>&auto-populate=true');">Del</a>
      </td>
      <td align="left" width="8" nowrap>
        Up|Down
      </td>
      <td width="30%" nowrap>
        <%= thisField.getName() %>
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
      <input type="hidden" name="dosubmit" value="false">
      <input type="hidden" name="moduleId" value="<%= ModuleList.getSelectedKey() %>">
      <input type="hidden" name="categoryId" value="<%= Category.getId() %>">
      <input type='submit' value='Delete this folder and all fields' onClick="javascript:this.form.dosubmit.value='true';">
    </td>
  </tr>
</table>
</form>

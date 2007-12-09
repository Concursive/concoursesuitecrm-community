<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.admin.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="CustomField" class="org.aspcfs.modules.base.CustomField" scope="request"/>
<jsp:useBean id="ModId" class="java.lang.String" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/editListForm.js"></script>
<script language="JavaScript" type="text/javascript">
  function doCheck() {
    if (document.modifyList.dosubmit.value == "false") {
      return true;
    }
    var test = document.modifyList.selectedList;
    if (test != null) {
      return selectAllOptions(document.modifyList.selectedList);
    }
  }

  function removeDefaultValue() {
    var fieldType = document.modifyList.type.value;
    if (fieldType == 1 || fieldType == 9 || fieldType == 10 || fieldType == 12) {
      if (document.modifyList.defaultValue.value.length > document.modifyList.maxLength.value) {
        alert("The default value must be removed against the field length");
        document.modifyList.defaultValue.value = "";
      }
    }
  }

  function removeSelectedValue() {
    var defaultValue = document.modifyList.defaultValue.value;
    var selectedValue = document.modifyList.selectedList.value;
    if (defaultValue == selectedValue) {
      alert("The default value must be removed against the removed item ");
      document.modifyList.defaultValue.value = "-1";
    }
    removeValues();
  }
</script>

<body<% if (CustomField.getName() == null) { %> onLoad="document.modifyList.name.focus();"<%}%>>
<form name="modifyList"
      action="AdminFields.do?command=ModifyField&modId=<%= ModId %>&catId=<%= Category.getId() %>&grpId=<%= (String)request.getParameter("grpId") %>&auto-populate=true"
      onSubmit="return doCheck();" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
      <a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
      <a href="Admin.do?command=ConfigDetails&moduleId=<%=ModId%>"><%= PermissionCategory.getCategory() %></a> >
      <a href="AdminFieldsFolder.do?command=ListFolders&modId=<%= ModId %>"><dhv:label name="admin.customFolders">Custom
        Folders</dhv:label></a> >
      <a href="AdminFieldsGroup.do?command=ListGroups&modId=<%= ModId %>&catId=<%= Category.getId() %>"><dhv:label
              name="accounts.accounts_documents_folders_add.Folder">Folder</dhv:label></a> >
      <dhv:label name="admin.existingField">Existing Field</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<dhv:formMessage showSpace="false"/>
<%
  CategoryList.setJsEvent("ONCHANGE=\"javascript:this.form.dosubmit.value='false';document.modifyList.submit();\"");
%>
<strong><dhv:label name="admin.module.colon">
  Module:</dhv:label></strong> <%= toHtml(PermissionCategory.getCategory()) %><br/>
<strong><dhv:label name="accounts.accounts_documents_folders_add.Folder.colon">
  Folder:</dhv:label></strong> <%= toHtml(Category.getName()) %><br/>
<br/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<tr>
  <th colspan="2">
    <dhv:label name="admin.modifyCustomField">Modify a Custom Field</dhv:label>
  </th>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="admin.fieldLabel">Field Label</dhv:label>
  </td>
  <td>
    <input type="text" name="name" maxlength="200" value="<%= toHtmlValue(CustomField.getName()) %>"><font color="red">
    *</font>
    <%= showAttribute(request, "nameError") %>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="admin.fieldType">Field Type</dhv:label>
  </td>
  <td>
    <%= CustomField.getHtmlSelect("type", "onChange=\"javascript:document.modifyList.defaultValue.value ='';document.modifyList.submit();\"") %>
    <font color="red">*</font>
    <%= showAttribute(request, "typeError") %>
  </td>
</tr>
<%
  if (CustomField.getLengthRequired()) {
%>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="admin.fieldLength">Field Length</dhv:label>
  </td>
  <td>
    <input type="text" name="maxLength" maxlength="3" value="<%= CustomField.getParameter("maxLength") %>" size="5">
    <font color="red">*</font>
    <%= showAttribute(request, "maxLengthError") %>
  </td>
</tr>
<%
  }
  if (CustomField.getLookupListRequired()) {
    LookupList SelectedList = (LookupList) CustomField.getElementData();
    SelectedList.setSelectSize(8);
    SelectedList.setMultiple(true);
%>
<tr class="containerBody">
  <td valign="top" class="formLabel">
    <dhv:label name="admin.lookupList">Lookup List</dhv:label>
  </td>
  <td>
    <%= showAttribute(request, "lookupListError") %>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="empty">
      <tr>
        <td width="50%">
          <table width="100%" cellspacing="0" cellpadding="2" border="0">
            <tr>
              <td valign="center">
                <dhv:label name="admin.newOption">New Option</dhv:label>
              </td>
            </tr>
            <tr>
              <td valign="center">
                <input type="text" name="newValue" id="newValue" value="" size="25" maxlength="125">
              </td>
            </tr>
            <tr>
              <td valign="center">
                <input type="button" name="addButton" id="addButton"
                       value="<dhv:label name="accounts.accounts_reports_generate.AddR">Add ></dhv:label>"
                       onclick="javascript:addValues()">
              </td>
            </tr>
          </table>
        </td>
        <td width="25">
          <table width="100%" cellspacing="0" cellpadding="2" border="0">
            <tr><td valign="center">
              <input type="button" value="<dhv:label name="global.button.Up">Up</dhv:label>"
                     onclick="javascript:moveOptionUp(document.modifyList.selectedList)">
            </td></tr>
            <tr><td valign="center">
              <input type="button" value="<dhv:label name="global.button.Down">Down</dhv:label>"
                     onclick="javascript:moveOptionDown(document.modifyList.selectedList)">
            </td></tr>
            <tr><td valign="center">
              <input type="button" value="<dhv:label name="button.remove">Remove</dhv:label>"
                     onclick="javascript:removeSelectedValue()">
            </td></tr>
            <tr><td valign="center">
              <input type="button" value="<dhv:label name="button.sort">Sort</dhv:label>"
                     onclick="javascript:sortSelect(document.modifyList.selectedList)">
            </td></tr>
            <tr><td valign="center">
              <input type="button" value="<dhv:label name="accounts.Rename">Rename</dhv:label>"
                     onclick="javascript:editValues();">
            </td></tr>
          </table>
        </td>
        <td width="50%">
          <%
            int count = 0;
            HtmlSelect itemListSelect = SelectedList.getHtmlSelectObj(0);
            itemListSelect.setSelectSize(10);
            Iterator i = SelectedList.iterator();
            if (i.hasNext()) {
              while (i.hasNext()) {
                LookupElement thisElement = (LookupElement) i.next();
                if (!thisElement.isGroup()) {
          %>
          <script>itemList[<%= count %>] = new category(<%= thisElement.getCode() %>, "<%= thisElement.getDescription() %>", '<%= thisElement.getEnabled() ? "true" : "false" %>');</script>
          <%
                count++;
              }
            }%>
          <% SelectedList.setJsEvent("onChange=\"javascript:resetOptions();\""); %>
          <%= SelectedList.getHtmlSelect("selectedList", 0) %>
          <% } else {%>
          <select name="selectedList" id="selectedList" size="10" multiple onChange="javascript:resetOptions();">
            <option value="-1"><dhv:label name="admin.itemList">--------Item List-------</dhv:label></option>
          </select>
          <%}%>
        </td>
        <input type="hidden" name="selectNames" value="">
      </tr>
    </table>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="documents.documents.displayType">Display Type</dhv:label>
  </td>
  <td>
    <dhv:label name="admin.dropdownBox">Drop-down box</dhv:label>
    <dhv:label name="admin.listBox">List box</dhv:label>
    <dhv:label name="admin.multipleSelectionListBox">Multiple selection list box</dhv:label>
  </td>
</tr>
<%
  }
%>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="admin.additionalTextDisplay">Additional Text to Display</dhv:label>
  </td>
  <td>
    <input type="text" name="additionalText" maxlength="255" width="50"
           value="<%= toHtmlValue(CustomField.getAdditionalText()) %>">
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="admin.requiredAtDataEntry">Required at Data Entry</dhv:label>
  </td>
  <td>
    <input type="checkbox" value="ON" name="required" <%= CustomField.getRequired()?"checked":"" %>>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="sales.defaultValue">Default Value</dhv:label>
  </td>
  <td>
    <%= toHtml(CustomField.getDefaultValueString()) %> &nbsp;
  </td>
</tr>
</table>
<br/>
<input type="hidden" name="defaultValue" value="<%= CustomField.getDefaultValue() %>">
<input type="hidden" name="id" value="<%= (String)request.getParameter("id") %>">
<input type="hidden" name="groupId" value="<%= (String)request.getParameter("grpId") %>">
<input type="hidden" name="dosubmit" value="true">
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>"
       onClick="javascript:removeDefaultValue();this.form.action='AdminFields.do?command=UpdateField&modId=<%= ModId %>&catId=<%= Category.getId() %>&grpId=<%= (String)request.getParameter("grpId") %>&auto-populate=true';this.form.dosubmit.value='true';">
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
       onClick="javascript:this.form.action='AdminFieldsGroup.do?command=ListGroups&modId=<%= ModId %>&catId=<%= Category.getId() %>';this.form.dosubmit.value='false';">
</form>
</body>

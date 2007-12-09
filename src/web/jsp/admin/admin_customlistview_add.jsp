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
  - Version: $Id: admin_display_customlistviews.jsp
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.Iterator,org.aspcfs.modules.admin.base.PermissionCategory, org.aspcfs.modules.admin.base.CustomListViewEditor" %>
<jsp:useBean id="customListViewEditor" class="org.aspcfs.modules.admin.base.CustomListViewEditor" scope="request"/>
<jsp:useBean id="customListView" class="org.aspcfs.modules.admin.base.CustomListView" scope="request"/>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= permissionCategory.getId() %>"><%= toHtml(permissionCategory.getCategory()) %></a> >
<a href="AdminCustomListViews.do?command=ListViews&moduleId=<%= permissionCategory.getId() %>&constantId=<%= customListViewEditor.getConstantId() %>"><dhv:label name="admin.customListViews">Custom List Views</dhv:label></a> >
<dhv:label name="admin.addCustomListView">Add Custom List View</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<script language="JavaScript">
 function checkForm(form) {
   formTest = true;
   message = "";
   if (form.problem.value == "") {
     message += label("check.issue.required","- Issue is required\r\n");
     formTest = false;
   }
   if (formTest == false) {
     alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
     return false;
   } else {
     return true;
   }
 }
 
 function setField(formField,thisValue,thisForm) {
  var frm = document.forms[thisForm];
  var len = document.forms[thisForm].elements.length;
  var i=0;
  for( i=0 ; i<len ; i++) {
    if (frm.elements[i].name.indexOf(formField)!=-1) {
      if(thisValue){
        frm.elements[i].value = "1";
      } else {
        frm.elements[i].value = "0";
      }
    }
  }
 }
</script>
<form name="addCustomView" action="AdminCustomListViews.do?command=Insert&auto-populate=true" method="post">
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form);">
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="AdminCustomListViews.do?command=ListViews&moduleId=<%= permissionCategory.getId() %>&constantId=<%= customListViewEditor.getConstantId() %>"/>
&nbsp;<br>
<dhv:formMessage />
 <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="admin.addCustomListView">Add Custom List View</dhv:label></strong>
    </th>
	</tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="dynamicForm.name">Name</dhv:label>
    </td>
    <td>
      <input type="text" name="name" value="<%= toHtmlValue(customListView.getName()) %>" size="50" maxlength="256" />&nbsp;<font color="red">*</font><%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
    </td>
    <td>
      <textarea name="description" cols="50" rows="3"><%= toHtmlValue(customListView.getDescription()) %></textarea><br />
      <input type="checkbox" name="chk1" value="true" onclick="javascript:setField('isDefault',document.addCustomView.chk1.checked,'addCustomView');" <%= customListView.getIsDefault() ? " checked" : ""%>><dhv:label name="admin.defaultView">Default View</dhv:label>
      <input type="hidden" name="isDefault" value="" />
    </td>
  </tr>
 </table>
 <input type="hidden" name="editorId" value="<%= customListViewEditor.getId() %>">
 <input type="hidden" name="moduleId" value="<%= permissionCategory.getId() %>">
&nbsp;<br>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="AdminCustomListViews.do?command=ListViews&moduleId=<%= permissionCategory.getId() %>&constantId=<%= customListViewEditor.getConstantId() %>"/>


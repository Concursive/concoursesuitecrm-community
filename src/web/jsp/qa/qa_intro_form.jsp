<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.help.base.*" %>
<jsp:useBean id="Help" class="org.aspcfs.modules.help.base.HelpItem" scope="request"/>
<jsp:useBean id="permissionCategoryList" class="org.aspcfs.modules.admin.base.PermissionCategoryList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body bgcolor='#FFFFFF' onLoad="document.inputForm.description.focus();">
<script language="JavaScript">
  function checkForm(form) {
    var formTest = true;
    var message = "";
    //Check required fields
    if (form.categoryId.value == -1) {    
      message += label("check.module","Module is required\n");
      formTest = false;
    }
    if (form.description.value == "") {    
      message += label("description.required",'Description is required');
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="post" name="inputForm" action="QA.do?command=SaveIntro&auto-populate=true" onSubmit="return checkForm(this);">
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
<tr>
  <th colspan="2">
    <strong><dhv:label name="global.button.Help">Help</dhv:label></strong>
  </th>
</tr>
<tr class="containerBody">
  <td class="formLabel" valign="top">
    <dhv:label name="qa.module">Module</dhv:label>
  </td>
  <td>
      <%= permissionCategoryList.getHtmlSelect("categoryId", Help.getCategoryId() ) %>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel" valign="top">
    <dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label>
  </td>
  <td>
    <input type="text" name="title" value="<%= toHtmlValue(Help.getTitle()) %>" />
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel" valign="top">
    <dhv:label name="qa.introduction">Introduction</dhv:label>
  </td>
  <td>
    <textarea rows="6" name="description" cols="50"><%= toString(Help.getDescription()) %></textarea>
  </td>
</tr>
</table><br>
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" />
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();" />
<dhv:evaluate if="<%= Help.getId() > 0 %>">
  <input type="hidden" name="modified" value="<%= Help.getModified() %>" />
</dhv:evaluate>
  <input type="hidden" name="id" value="<%= Help.getId() %>">
  <input type="hidden" name="module" value="<%= toString(Help.getModule()) %>">
  <dhv:evaluate if='<%= !"".equals(toString(Help.getSection())) %>'>
    <input type="hidden" name="section" value="<%= Help.getSection() %>">
  </dhv:evaluate>
  <dhv:evaluate if='<%= !"".equals(toString(Help.getSubsection())) %>'>
    <input type="hidden" name="subsection" value="<%= toString(Help.getSubsection()) %>">
  </dhv:evaluate>
  <%= addHiddenParams(request, "popup") %>
</form>  
</body>

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
      message += "Module is required\n";
      formTest = false;
    }
    if (form.description.value == "") {    
      message += "Description is required\n";
      formTest = false;
    }
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
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
    <strong>Help</strong>
  </th>
</tr>
<tr class="containerBody">
  <td class="formLabel" valign="top">
    Module
  </td>
  <td>
      <%= permissionCategoryList.getHtmlSelect("categoryId", Help.getCategoryId() ) %>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel" valign="top">
    Title
  </td>
  <td>
    <input type="text" name="title" value="<%= toHtmlValue(Help.getTitle()) %>" />
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel" valign="top">
    Introduction
  </td>
  <td>
    <textarea rows="6" name="description" cols="50"><%= toString(Help.getDescription()) %></textarea>
  </td>
</tr>
</table><br>
<input type="submit" value="Update" />
<input type="submit" value="Cancel" onClick="javascript:window.close();" />
<dhv:evaluate if="<%= Help.getId() > 0 %>">
  <input type="hidden" name="modified" value="<%= Help.getModified() %>" />
</dhv:evaluate>
  <input type="hidden" name="id" value="<%= Help.getId() %>">
  <input type="hidden" name="module" value="<%= toString(Help.getModule()) %>">
  <dhv:evaluate if="<%= !"".equals(toString(Help.getSection())) %>">
    <input type="hidden" name="section" value="<%= Help.getSection() %>">
  </dhv:evaluate>
  <dhv:evaluate if="<%= !"".equals(toString(Help.getSubsection())) %>">
    <input type="hidden" name="subsection" value="<%= toString(Help.getSubsection()) %>">
  </dhv:evaluate>
  <%= addHiddenParams(request, "popup") %>
</form>  
</body>

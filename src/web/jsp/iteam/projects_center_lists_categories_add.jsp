<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ page import="java.util.*,org.aspcfs.modules.tasks.base.*,com.zeroio.iteam.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="category" class="org.aspcfs.modules.tasks.base.TaskCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.inputForm.description.focus();">
<script language="JavaScript">
function checkForm(form) {
  if (form.dosubmit.value == "false") {
    return true;
  }
  var formTest = true;
  var messageText = "";
  //Check required fields
  if (form.description.value == "") {    
    messageText += "- Name is a required field\r\n";
    formTest = false;
  }
  if (formTest == false) {
    messageText = "The form could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
    form.dosubmit.value = "true";
    alert(messageText);
    return false;
  } else {
    return true;
  }
}
</script>
<form method="POST" name="inputForm" action="ProjectManagementListsCategory.do?command=<%= category.getId()!=-1?"UpdateCategory":"InsertCategory" %>&id=<%= category.getId() %>&auto-populate=true" onSubmit="return checkForm(this);">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img border="0" src="images/icons/stock_list_enum2-16.gif" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Lists_Categories&pid=<%= Project.getId() %>">Lists</a> >
      <%= category.getId() == -1 ? "Add" : "Update" %>
    </td>
  </tr>
</table>
<br>
  <input type="submit" value=" Save ">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Lists_Categories&pid=<%= Project.getId() %>';"><br>
  <%= showError(request, "actionError") %>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <strong><%= category.getId()==-1?"Add":"Update" %> List</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">Name</td>
      <td>
        <input type="text" name="description" size="57" maxlength="80" value="<%= toHtmlValue(category.getDescription()) %>"><font color=red>*</font> <%= showAttribute(request, "descriptionError") %>
      </td>
    </tr>
  </table>
  <br>
  <input type="submit" value=" Save ">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Lists_Categories&pid=<%= Project.getId() %>';">
  <input type="hidden" name="level" value="<%= category.getLevel() %>">
  <input type="hidden" name="enabled" value="<%= category.getEnabled() %>">
  <input type="hidden" name="defaultItem" value="<%= category.getDefaultItem() %>">
  <input type="hidden" name="pid" value="<%= Project.getId() %>">
  <input type="hidden" name="dosubmit" value="true">
</form>  
</body>

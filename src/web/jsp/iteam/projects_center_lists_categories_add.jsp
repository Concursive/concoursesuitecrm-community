<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="category" class="org.aspcfs.modules.tasks.base.TaskCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body bgcolor='#FFFFFF' onLoad="document.inputForm.description.focus();">
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    
    //Check required fields
    if (form.description.value == "") {    
      messageText += "- Description is a required field\r\n";
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
  <% if (request.getAttribute("actionError") != null) { %>
    <%= showError(request, "actionError") %>
  <%}%>
  <table border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='100%' colspan='2' bgcolor='#808080' rowspan='2'>
        <font color='#FFFFFF'><b>&nbsp;<%= category.getId()==-1?"Add":"Update" %> Item</b></font>
      </td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='100%' colspan='2' valign='center'>
        &nbsp;<br>
        &nbsp;Category<br>
        &nbsp;
      </td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='100%' colspan='2'>
        &nbsp;Category Name:<br>
        &nbsp;&nbsp;<input type="text" name="description" size="57" maxlength="80" value="<%= toHtmlValue(category.getDescription()) %>"><font color=red>*</font> <%= showAttribute(request, "descriptionError") %><br>
        &nbsp;
      </td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='50%' bgcolor='#808080' height='30'>
        <p align='right'>
          &nbsp;
          <input type="submit" value=" Save ">&nbsp;&nbsp;
        </p>
      </td>
      <td width='50%' bgcolor='#808080' height='30'>
        <p align='left'>
          &nbsp;&nbsp;
          <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Lists_Categories&pid=<%= Project.getId() %>';">
        </p>
      </td>
      <td width="2" bgcolor="#808080">&nbsp;</td>
    </tr>
  </table>
  <input type="hidden" name="level" value="<%= category.getLevel() %>">
  <input type="hidden" name="enabled" value="<%= category.getEnabled() %>">
  <input type="hidden" name="defaultItem" value="<%= category.getDefaultItem() %>">
  <input type="hidden" name="pid" value="<%= Project.getId() %>">
  <input type="hidden" name="dosubmit" value="true">
</form>  
</body>

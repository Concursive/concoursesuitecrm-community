<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.help.base.*" %>
<jsp:useBean id="Help" class="org.aspcfs.modules.help.base.HelpItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body bgcolor='#FFFFFF' onLoad="document.inputForm.description.focus();">
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
  
    //Check required fields
    if (form.description.value == "") {    
      form.dosubmit.value = "true";
      alert("Description is required.");
      formTest = false;
    }
  
    if (formTest == false) {
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
    Introduction
  </td>
  <td>
      <textarea rows='6' name='description' cols='50'><%= toString(Help.getDescription()) %></textarea>
  </td>
</tr>
</table><br>
<input type="submit" value="Update">
<input type="submit" value="Cancel" onClick="javascript:window.close();">
<dhv:evaluate if="<%= Help.getId() > 0 %>">
  <input type="hidden" name="modified" value="<%= Help.getModified() %>">
</dhv:evaluate>
  <input type="hidden" name="id" value="<%= Help.getId() %>">
  <input type="hidden" name="module" value="<%= toString(Help.getModule()) %>">
  <input type="hidden" name="section" value="<%= toString(Help.getSection()) %>">
  <input type="hidden" name="subsection" value="<%= toString(Help.getSubsection()) %>">
  <%= addHiddenParams(request, "popup") %>
</form>  
</body>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="ImportDetails" class="org.aspcfs.modules.contacts.base.ContactImport" scope="request"/>
<jsp:useBean id="SourceTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function doCheck(form) {
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  function checkForm(form) {
    formTest = true;
    message = "";
    if(document.forms[0].name.value == '') {
       message += "- Name is a required field.\r\n";
			 formTest = false;
    }
    
    if (form.id.value.length < 5) {
      message += "- File is required\r\n";
      formTest = false;
    }
    
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    }else{
      if (form.upload.value != 'Please Wait...') {
        form.upload.value='Please Wait...';
        return true;
      } else {
        return false;
      }
    }
  }
</script>
<body onLoad="javascript:document.forms[0].name.focus();">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
  <td>
    <a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> >
    <a href="AccountContactsImports.do?command=View">View Imports</a> >
    New Import
  </td>
</tr>
</table>
<%-- End Trails --%>
<form method="post" name="inputForm" action="AccountContactsImports.do?command=Save" enctype="multipart/form-data" onSubmit="return doCheck(this);">
<%= showError(request, "actionError") %>

<%--  include basic contact form --%>
<%@ include file="../import_include.jsp" %>

<br>
<input type="submit" value="Save" name="upload" onClick="this.form.dosubmit.value='true';">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountContactsImports.do?command=View';this.form.dosubmit.value='false';">
  <input type="hidden" name="dosubmit" value="true">
</form>
</body>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body bgcolor='#FFFFFF' onLoad="document.inputForm.subject.focus();">
<script language="JavaScript">
  function checkFileForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    
    var formTest = true;
    var messageText = "";

    if (form.subject.value == "") {
      messageText += "- Subject is required\r\n";
      formTest = false;
    }
    
    if ((form.clientFilename.value) == "") {
      messageText += "- Filename is required\r\n";
      formTest = false;
    }
    
    if (formTest == false) {
      form.dosubmit.value = "true";
      messageText = "The file information could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
<form method="POST" name="inputForm" action="ProjectManagementFiles.do?command=Update" onSubmit="return checkFileForm(this);">
  <tr>
    <td width='2' bgcolor='#000000'>&nbsp;</td>
    <td width="100%" bgcolor="#000000" rowspan="2" valign="middle">
      <font color="#FFFFFF">&nbsp;<img border="0" src="images/file.gif" align="absmiddle"><b>File Sharing - Update file information</b></font>
    </td>
    <td width='2' bgcolor='#000000'>&nbsp;</td>
  </tr>
  <tr>
    <td width='2' bgcolor='#000000'>&nbsp;</td>
    <td width='2' bgcolor='#000000'>&nbsp;</td>
  </tr>
  <tr>
    <td width="2" bgcolor="#000000">&nbsp;</td>
    <td width="100%" bgcolor="#B9D3FF" align="center">
    
      <input type="hidden" name="folderId" value="<%= FileItem.getFolderId() %>">
    
      <table border="0" width="300" cellspacing="0" cellpadding="0">
        <tr>
          <td width="100%" align="left">
            &nbsp;<br>
            &nbsp;Subject of file:<br>
            &nbsp;&nbsp;<input type="text" name="subject" size="59" maxlength="255" value="<%= FileItem.getSubject() %>">
          </td>
        </tr>
        <tr>
          <td width="100%" align="left">
            &nbsp;<br>
            &nbsp;Filename:<br>
            &nbsp;&nbsp;<input type="text" name="clientFilename" size="59" maxlength="255" value="<%= FileItem.getClientFilename() %>">
          </td>
        </tr>
        <tr>
          <td width="100%" align="left">
            &nbsp;<br>
            &nbsp;Current Version: <%= FileItem.getVersion() %>
          </td>
        </tr>
      </table>
    </td>
    <td width="2" bgcolor="#000000">&nbsp;</td>
  </tr>
  <tr>
    <td width="2" bgcolor="#000000">&nbsp;</td>
    <td width="100%" bgcolor="#B9D3FF">&nbsp;</td>
    <td width="2" bgcolor="#000000">&nbsp;</td>
  </tr>
  
  <tr>
    <td width="2" bgcolor="#000000">&nbsp;</td>
    <td width="100%" bgcolor="#B9D3FF">&nbsp;</td>
    <td width="2" bgcolor="#000000">&nbsp;</td>
  </tr>
        
  <tr bgcolor="#000000">
    <td width="2">&nbsp;</td>
    <td width="100%" height="30" align="center">
      <p align="center">
        <input type="submit" value=" Update " name="update">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=File_Library&pid=<%= Project.getId() %>';">
        <input type="hidden" name="dosubmit" value="true">
        <input type="hidden" name="pid" value="<%= Project.getId() %>">
        <input type="hidden" name="fid" value="<%= FileItem.getId() %>">
      </p>
    </td>
    <td width="2">&nbsp;</td>
  </tr>
</form>
</table>
</body>

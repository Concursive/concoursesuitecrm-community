<%@ page import="java.util.*,org.aspcfs.modules.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body bgcolor="#FFFFFF" onLoad="document.inputForm.subject.focus();">
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
    
    if (form.id<%= Project.getId() %>.value.length < 5) {
      messageText += "- File is required\r\n";
      formTest = false;
    }
    
    if (formTest == false) {
      messageText = "The file could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      if (form.upload.value != 'Please Wait...') {
        form.upload.value='Please Wait...';
        return true;
      } else {
        return false;
      }
    }
  }
</script>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
<form method="POST" name="inputForm" action="ProjectManagementFiles.do?command=Upload" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
  <tr>
    <td width='2' bgcolor='#000000'>&nbsp;</td>
    <td width="100%" bgcolor="#000000" rowspan="2" valign="middle">
      <font color="#FFFFFF">&nbsp;<img border="0" src="images/file.gif" align="absmiddle"><b>File Sharing</b></font>
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
    
      <input type="hidden" name="folderId" value="<%= request.getParameter("folderId") %>">
    
      <table border="0" width="300" cellspacing="0" cellpadding="0">
        <tr>
          <td width="100%" align="left">
            &nbsp;<br>
            &nbsp;Subject of file:<br>
            &nbsp;&nbsp;<input type="text" name="subject" size="59" maxlength="255">
          </td>
        </tr>
              
        <tr>
          <td width="100%" align="left">
            &nbsp;<br>
            &nbsp;File:<br>
            &nbsp;&nbsp;<input type="file" name="id<%= Project.getId() %>" size="45">
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
    <td width="100%" bgcolor="#B9D3FF">
      <p align="center">* Large files may take a while to upload.<br>
                 Wait for file completion message when upload is complete.
      </p>
    </td>
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
        <input type="submit" value=" Upload " name="upload">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=File_Library&pid=<%= Project.getId() %>';">
        <input type="hidden" name="dosubmit" value="true">
        <input type="hidden" name="pid" value="<%= Project.getId() %>">
      </p>
    </td>
    <td width="2">&nbsp;</td>
  </tr>
</form>
</table>
</body>

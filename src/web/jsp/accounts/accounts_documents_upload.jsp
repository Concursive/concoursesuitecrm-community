<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<%@ include file="initPage.jsp" %>
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
    
    if (form.id<%= OrgDetails.getOrgId() %>.value.length < 5) {
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
<body onLoad="document.inputForm.subject.focus();">

<a href="/Accounts.do">Account Management</a> > 
<a href="/Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="/AccountsDocuments.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Documents</a> >
Upload Document<br>
<hr color="#BFBFBB" noshade>

<a href="Accounts.do?command=View">Back to Account List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <form method="post" name="inputForm" action="AccountsDocuments.do?command=Upload" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="documents" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <a href="AccountsDocuments.do?command=View&orgId=<%= OrgDetails.getOrgId() %>">Back to Documents List</a><br>
      <%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <img border="0" src="images/file.gif" align="absmiddle"><b>Upload a New Document</b>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Subject
    </td>
    <td>
      <input type="hidden" name="folderId" value="<%= request.getParameter("folderId") %>">
      <input type="text" name="subject" size="59" maxlength="255"><font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      File
    </td>
    <td>
      <input type="file" name="id<%= OrgDetails.getOrgId() %>" size="45">
    </td>
  </tr>
</table>
  <p align="center">
    * Large files may take a while to upload.<br>
    Wait for file completion message when upload is complete.
  </p>
  <input type='submit' value=' Upload ' name="upload">
  <input type='submit' value='Cancel' onClick="javascript:this.form.dosubmit.value='false';this.form.action='AccountsDocuments.do?command=View&orgId=<%= OrgDetails.getOrgId() %>';">
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="id" value="<%= OrgDetails.getOrgId() %>">
</td>
</tr>
</form>
</table>
</body>

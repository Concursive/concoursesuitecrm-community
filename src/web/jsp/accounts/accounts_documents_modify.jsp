<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
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
    
    if ((form.clientFilename.value) == "") {
      messageText += "- Filename is required\r\n";
      formTest = false;
    }
    
    if (formTest == false) {
      messageText = "The file information could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<body onLoad="document.inputForm.subject.focus();">
<a href="Accounts.do?command=View">Back to Account List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <form method="post" name="inputForm" action="AccountsDocuments.do?command=Update" onSubmit="return checkFileForm(this);">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
      <td>
      <a href="/Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Details</font></a><dhv:permission name="accounts-accounts-folders-view"> | 
      <a href="/Accounts.do?command=Fields&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Folders</font></a></dhv:permission><dhv:permission name="accounts-accounts-contacts-view"> |
      <a href="Contacts.do?command=View&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Contacts</font></a></dhv:permission><dhv:permission name="accounts-accounts-opportunities-view"> | 
      <a href="Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Opportunities</font></a></dhv:permission><dhv:permission name="accounts-accounts-tickets-view"> | 
      <a href="Accounts.do?command=ViewTickets&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Tickets</font></a></dhv:permission><dhv:permission name="accounts-accounts-documents-view"> |
      <a href="AccountsDocuments.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><font color="#0000FF">Documents</font></a></dhv:permission>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <a href="AccountsDocuments.do?command=View&orgId=<%= OrgDetails.getOrgId() %>">Back to Documents List</a><br>
      <%= showAttribute(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <img border="0" src="images/file.gif" align="absmiddle"><b>Modify Document Information</b>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Subject of file
    </td>
    <td>
      <input type="hidden" name="folderId" value="<%= request.getParameter("folderId") %>">
      <input type="text" name="subject" size="59" maxlength="255" value="<%= FileItem.getSubject() %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Filename
    </td>
    <td>
      <input type="text" name="clientFilename" size="59" maxlength="255" value="<%= FileItem.getClientFilename() %>">
    </td>
  </tr>
	<tr class="containerBody">
    <td class="formLabel">
      Version
    </td>
    <td>
      <%= FileItem.getVersion() %>
    </td>
  </tr>
</table>
  &nbsp;<br>
  <input type='submit' value=' Update ' name="update" onClick="javascript:this.form.dosubmit.value='true';">
  <input type='submit' value='Cancel' onClick="javascript:this.form.dosubmit.value='false';this.form.action='AccountsDocuments.do?command=View&orgId=<%= OrgDetails.getOrgId() %>';">
  <input type="hidden" name="dosubmit" value="false">
  <input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>">
	<input type="hidden" name="fid" value="<%= FileItem.getId() %>">
</td>
</tr>
</form>
</table>
</body>

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
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function checkFileForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    if (form.subject.value == "") {
      messageText += label("Subject.required", "- Subject is required\r\n");
      formTest = false;
    }
    if ((form.clientFilename.value) == "") {
      messageText += label("Filename.required", "- Filename is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      messageText = label("Fileinfo.not.submitted", "The file information could not be submitted.          \r\nPlease verify the following items:\r\n\r\n") + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<body onLoad="document.inputForm.subject.focus();">
<form method="post" name="inputForm" action="CampaignDocuments.do?command=Update" onSubmit="return checkFileForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<a href="CampaignManager.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<a href="CampaignManager.do?command=Details&id=<%= Campaign.getId() %>"><dhv:label name="campaign.campaignDetails">Campaign Details</dhv:label></a> >
<a href="CampaignDocuments.do?command=View&id=<%= Campaign.getId() %>"><dhv:label name="accounts.accounts_documents_details.Documents">Documents</dhv:label></a> >
<dhv:label name="accounts.accounts_documents_modify.ModifyDocument">Modify Document</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="communications" selected="documents" object="Campaign" param="<%= "id=" + Campaign.getId() %>">
  <dhv:formMessage showSpace="false"/>
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <img border="0" src="images/file.gif" align="absmiddle" /><b><dhv:label name="accounts.accounts_documents_modify.ModifyDocumentInformation">Modify Document Information</dhv:label></b>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_documents_modify.SubjectOfFile">Subject of file</dhv:label>
      </td>
      <td>
        <input type="hidden" name="folderId" value="<%= request.getParameter("folderId") %>">
        <input type="text" name="subject" size="59" maxlength="255" value="<%= FileItem.getSubject() %>">
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_documents_modify.Filename">Filename</dhv:label>
      </td>
      <td>
        <input type="text" name="clientFilename" size="59" maxlength="255" value="<%= FileItem.getClientFilename() %>">
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_documents_details.Version">Version</dhv:label>
      </td>
      <td>
        <%= FileItem.getVersion() %>
      </td>
    </tr>
  </table>
  <br />
  <input type="submit" value=" <dhv:label name="global.button.update">Update</dhv:label> " name="update">
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='CampaignDocuments.do?command=View&id=<%= Campaign.getId() %>';">
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="id" value="<%= Campaign.getId() %>">
	<input type="hidden" name="fid" value="<%= FileItem.getId() %>">
</dhv:container>
</form>
</body>

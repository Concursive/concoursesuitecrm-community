<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function checkFileForm(form) {
    var formTest = true;
    var messageText = "";
    if (form.file.value.length < 5) {
      messageText += "- File is required\r\n";
      formTest = false;
    }
    if (formTest == false) {
      messageText = "The file could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
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
<form method="POST" name="workflowForm" action="AdminConfig.do?command=ImportWorkflow" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
  <a href="AdminConfig.do?command=ListGlobalParams"><dhv:label name="admin.configureSystem">Configure System</dhv:label></a> >
  <dhv:label name="admin.config.workflow">Workflow</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:formMessage showSpace="false" />
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2" align="left">
          <dhv:label name="admin.config.submitWorkflowFile">Submit Workflow File</dhv:label>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">&nbsp;</td>
      <td width="100%"><input type="checkbox" name="delete" id="delete" value="true"> <dhv:label name="admin.config.deleteEventsAndProcesses.text">Delete existing events and business processes</dhv:label></td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel"><dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label></td>
      <td  width="100%" nowrap><input type="file" name="file" size="45">
      </td>
    </tr>
  </table>
  <p align="center"><dhv:label name="documents.documents.largeFileUploadMessage" param="break=<br>">* Large files may take awhile to upload.<br />
     Wait for file completion message when upload finishes.</dhv:label>
  </p>
  <input type="submit" value="Upload" name="upload">
</form>


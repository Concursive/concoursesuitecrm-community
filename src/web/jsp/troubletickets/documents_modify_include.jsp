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
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <img border="0" src="images/file.gif" align="absmiddle"><b>Modify Document Information</b>
    </th>
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


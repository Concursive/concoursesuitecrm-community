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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="kb" class="org.aspcfs.modules.troubletickets.base.KnowledgeBase" scope="request"/>
<jsp:useBean id="fileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="thisCategory" class="org.aspcfs.modules.troubletickets.base.TicketCategory" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript">
  function checkFileForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    if (checkNullString(form.title.value)) {
      messageText += label("title.required", "- Title is a required field\r\n");
      formTest = false;
    }
    if (extract(form.itemId1.value) != '') {
      form.fileText.value= extract(form.itemId1.value);
    }
    if (formTest == false) {
      messageText = label("File.not.submitted", "The file could not be submitted.          \r\nPlease verify the following items:\r\n\r\n") + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      if (form.save1.value != label("button.pleasewait","Please Wait...") || form.save2.value != label("button.pleasewait","Please Wait...") ) {
        form.save2.value=label("button.pleasewait","Please Wait...");
        form.save1.value=label("button.pleasewait","Please Wait...");
        return true;
      } else {
        return false;
      }
    }
  }

function extract(what) {
  var answer = '';
    if (what.indexOf('/') > -1) {
        answer = what.substring(what.lastIndexOf('/')+1,what.length);
    } else {
        answer = what.substring(what.lastIndexOf('\\')+1,what.length);
    }
   return answer;
}

</script>
<body onLoad="document.addKb.title.focus();">
<form method="post" name="addKb" action="KnowledgeBaseManager.do?command=Save<%= isPopup(request)?"&popup=true":"" %>" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
<input type="hidden" name="categoryId" value="<%= (request.getParameter("categoryId") != null && !"-1".equals(request.getParameter("categoryId")) && !"".equals(request.getParameter("categoryId")) && !"0".equals(request.getParameter("categoryId"))?request.getParameter("categoryId"):String.valueOf(kb.getCategoryId())) %>" />
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<dhv:evaluate if="<%= !isPopup(request) %>">
  <a href="TroubleTickets.do"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> >
</dhv:evaluate>
  <a href="KnowledgeBaseManager.do?command=Search<%= isPopup(request)?"&popup=true":"" %>"><dhv:label name="tickets.viewKnowledgeBase">View Knowledge Base</dhv:label></a> >
<dhv:evaluate if="<%= kb.getId() == -1 %>">
  <dhv:label name="account.ticket.addDocument">Add Document</dhv:label>
</dhv:evaluate><dhv:evaluate if="<%= kb.getId() != -1 %>">
  <a href="KnowledgeBaseManager.do?command=Details&kbId=<%= kb.getId() %><%= isPopup(request)?"&popup=true":"" %>"><dhv:label name="accounts.accounts_documents_details.DocumentDetails">Document Details</dhv:label></a> >
  <dhv:label name="accounts.accounts_documents_modify.ModifyDocument">Modify Document</dhv:label>
</dhv:evaluate>
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" name="save1" value="<dhv:label name="button.save">Save</dhv:label>" />
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='KnowledgeBaseManager.do?command=<%= kb.getId() > -1? "Details&kbId="+ kb.getId():"Search" %><%= isPopup(request)?"&popup=true":"" %>';" />
<input type="hidden" name="dosubmit" value="true" />
<input type="hidden" name="id" value="<%= kb.getId() %>" />
<input type="hidden" name="fid" value="<%= fileItem.getId() %>">
<input type="hidden" name="categoryId" value="<%= thisCategory.getId() %>">
<input type="hidden" name="fileText" value="" />
<input type="hidden" value="<%= fileItem.getVersionNextMajor() %>" name="versionId" />
<br /><dhv:formMessage showSpace="true"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <img border="0" src="images/file.gif" align="absmiddle" /><strong>
<dhv:evaluate if="<%= kb.getId() == -1 %>">
  <dhv:label name="tickets.addDocumentToKnowledgeBase.text">Add new Document to the Knowledge Base</dhv:label>
</dhv:evaluate><dhv:evaluate if="<%= kb.getId() != -1 %>">
  <dhv:label name="tickets.modifyDocumentOrAppendNewFileVersion.text">Modify Document or append a new File Version</dhv:label>
</dhv:evaluate>
      </strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Category">Category</dhv:label>
    </td>
    <td><%= thisCategory.getDescription() %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label>
    </td>
    <td>
      <input type="text" name="title" size="58" maxlength="255" value="<%= toHtmlValue(kb.getTitle()) %>"><font color="red">*</font>
      <%= showAttribute(request, "titleError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
    </td>
    <td>
      <textarea rows="8" cols="40" name="description"><%= toString(kb.getDescription()) %></textarea>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
    </td>
    <td>
      <input type="file" name="itemId1" size="45" ERROR="<%= showAttribute(request, "ItemId1Error") %>">
    </td>
  </tr>
</table>
  <p align="center">
    * <dhv:label name="accounts.accounts_documents_upload.LargeFilesUpload">Large files may take a while to upload.</dhv:label><br>
    <dhv:label name="accounts.accounts_documents_upload.WaitForUpload">Wait for file completion message when upload is complete.</dhv:label>
  </p>
  <input type="submit" name="save2" value="<dhv:label name="button.save">Save</dhv:label>"/>
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='KnowledgeBaseManager.do?command=<%= kb.getId() > -1? "Details&kbId="+ kb.getId():"Search" %><%= isPopup(request)?"&popup=true":"" %>';" />
</form>
</body>

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
  - Author(s): 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*, org.aspcfs.modules.documents.base.*" %>
<jsp:useBean id="documentStore" class="org.aspcfs.modules.documents.base.DocumentStore" scope="request"/>
<jsp:useBean id="fileFolder" class="com.zeroio.iteam.base.FileFolder" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.inputForm.subject.focus();">
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    if (form.subject.value == "") {
      messageText += label("name.required","- Name is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      messageText = label("Form.not.submitted","The form could not be submitted.          \r\nPlease verify the following:\r\n\r\n") + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    }
    return true;
  }
</script>
<form method="POST" name="inputForm" action="DocumentStoreManagementFileFolders.do?command=Save&auto-populate=true" onSubmit="return checkForm(this);">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <% String documentLink = "DocumentManagement.do?command=DocumentStoreCenter&section=File_Library&documentStoreId="+documentStore.getId(); %>
      <zeroio:folderHierarchy module="Documents" link="<%= documentLink %>" showLastLink="true"/> >
    </td>
  </tr>
</table>
<br>
  <input type="submit" value="<dhv:label name="documents.documents.save">Save</dhv:label>" name="save">
  <input type="submit" value="<dhv:label name="documents.documents.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='DocumentManagement.do?command=DocumentStoreCenter&section=File_Library';"><br>
  <dhv:formMessage />
  <br />
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <strong>
          <% if(fileFolder.getId() > -1) {%>
            <dhv:label name="accounts.accounts_documents_list_menu.RenameFolder">Rename Folder</dhv:label>
          <%} else {%>
            <dhv:label name="documents.documents.newFolder">New Folder</dhv:label>
          <%}%>
        </strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel"><dhv:label name="documents.documents.name">Name</dhv:label></td>
      <td>
        <input type="text" name="subject" size="59" maxlength="255" value="<%= toHtmlValue(fileFolder.getSubject()) %>">
        <font color="red">*</font> <%= showAttribute(request, "subjectError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel"><dhv:label name="documents.documents.displayType">Display Type</dhv:label></td>
      <td>
        <dhv:label name="documents.documents.iconLayout">Icon Layout</dhv:label>
        <select size="1" name="display">
          <option value="-1" <%= fileFolder.getDisplay() == -1 ? "selected" : "" %>><dhv:label name="project.listView">List View</dhv:label></option>
<%--
          <option value="1" <%= fileFolder.getDisplay() == 1 ? "selected" : "" %>>Image View</option>
          <option value="2" <%= fileFolder.getDisplay() == 2 ? "selected" : "" %>>Slideshow View</option>
--%>
        </select>
      </td>
    </tr>
  </table>
  <br>
  <input type="hidden" name="modified" value="<%= fileFolder.getModifiedString() %>">
  <input type="hidden" name="documentStoreId" value="<%= documentStore.getId() %>">
  <input type="hidden" name="id" value="<%= fileFolder.getId() %>">
  <input type="hidden" name="parentId" value="<%= fileFolder.getParentId() %>">
  <input type="hidden" name="folderId" value="<%= request.getParameter("folderId") %>">
  <input type="hidden" name="dosubmit" value="true">
  <input type="submit" value="<dhv:label name="button.save">Save</dhv:label>" name="save">
  <input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='DocumentManagement.do?command=DocumentStoreCenter&section=File_Library';"><br>
</form>
</body>

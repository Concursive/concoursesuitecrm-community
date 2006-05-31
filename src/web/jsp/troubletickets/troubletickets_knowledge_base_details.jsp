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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*, org.aspcfs.modules.base.EmailAddress " %>
<jsp:useBean id="kb" class="org.aspcfs.modules.troubletickets.base.KnowledgeBase" scope="request"/>
<jsp:useBean id="thisCategory" class="org.aspcfs.modules.troubletickets.base.TicketCategory" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script type="text/javascript">
  function download() {
    window.location.href='KnowledgeBaseManager.do?command=Download&kbId=<%= kb.getId() %>&fid=<%= kb.getItemId() %>&folderId=-1';
  }

  function view() {
    popURL('KnowledgeBaseManager.do?command=Download&kbId=<%= kb.getId() %>&fid=<%= kb.getItemId() %>&view=true', 'Content', 640,480, 1, 1);
  }

</script>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<dhv:evaluate if="<%= !isPopup(request) %>">
  <a href="TroubleTickets.do"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> >
</dhv:evaluate>
  <a href="KnowledgeBaseManager.do?command=Search<%= isPopup(request)?"&popup=true":"" %>"><dhv:label name="tickets.viewKnowledgeBase">View Knowledge Base</dhv:label></a> >
  <dhv:label name="accounts.accounts_documents_details.DocumentDetails">Document Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="tickets-knowledge-base-edit"><input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='KnowledgeBaseManager.do?command=Modify&kbId=<%= kb.getId() %>&categoryId=<%= kb.getCategoryId() %><%= isPopup(request)?"&popup=true":"" %>';"></dhv:permission>
<dhv:permission name="tickets-knowledge-base-delete">
  <input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURL('KnowledgeBaseManager.do?command=ConfirmDelete&kbId=<%= kb.getId() %><%= isPopup(request)?"&isSourcePopup=true":"" %>&popup=true', 'DeleteKBEntry','320','200','yes','no');">
</dhv:permission>
<dhv:evaluate if="<%= kb.getItemId() != -1 %>">
 <dhv:permission name="tickets-knowledge-base-view"><input type="button" value="<dhv:label name="accounts.accounts_contacts_detailsimport.DownloadFile">Download File</dhv:label>" onClick="javascript:download();"></dhv:permission>
 <dhv:permission name="tickets-knowledge-base-view"><input type="button" value="<dhv:label name="accounts.accounts_documents_list_menu.ViewFileContents">View File Contents</dhv:label>" onClick="javascript:view();"></dhv:permission>
</dhv:evaluate>
<br />&nbsp;<br />
<%-- Defect Information --%>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="contacts.details">Details</dhv:label></strong>
    </th>
  </tr>
	<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Category">Category</dhv:label>
    </td>
    <td>
      <%= toHtml(thisCategory.getDescription()) %>
    </td>
  </tr>
	<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label>
    </td>
    <td>
      <%= toHtml(kb .getTitle()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
    </td>
    <td><%= toHtml(kb.getDescription()) %></td>
  </tr>
</table>
<dhv:evaluate if="<%= kb.getItemId() != -1 %>"><br />
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr><th colspan="2"><strong><dhv:label name="tickets.fileDetails">File Details</dhv:label></strong></th></tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="project.document">Document</dhv:label>
    </td>
    <td><%= toHtml(kb.getItem().getClientFilename()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_documents_details.Version">Version</dhv:label>
    </td>
    <td><%= kb.getItem().getVersion() %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="documents.documents.size">Size</dhv:label>
    </td>
    <td><%= kb.getItem().getRelativeSize() %><dhv:label name="admin.oneThousand.abbreviation">k</dhv:label></td>
  </tr>
</table>
</dhv:evaluate>
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr><th colspan="2"><strong><dhv:label name="contact.recordDetails">Record Details</dhv:label></strong></th></tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= kb.getEnteredBy() %>"/>
      <zeroio:tz timestamp="<%= kb.getEntered()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= kb.getModifiedBy() %>"/>
      <zeroio:tz timestamp="<%= kb.getModified()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
    </td>
  </tr>
</table>

<br />
<dhv:permission name="tickets-knowledge-base-edit"><input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='KnowledgeBaseManager.do?command=Modify&kbId=<%= kb.getId() %>&categoryId=<%= kb.getCategoryId() %><%= isPopup(request)?"&popup=true":"" %>';"></dhv:permission>
<dhv:permission name="tickets-knowledge-base-delete">
  <input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURL('KnowledgeBaseManager.do?command=ConfirmDelete&kbId=<%= kb.getId() %><%= isPopup(request)?"&isSourcePopup=true":"" %>&popup=true', 'DeleteKBEntry','320','200','yes','no');">
</dhv:permission>
<dhv:evaluate if="<%= kb.getItemId() != -1 %>">
 <dhv:permission name="tickets-knowledge-base-view"><input type="button" value="<dhv:label name="accounts.accounts_contacts_detailsimport.DownloadFile">Download File</dhv:label>" onClick="javascript:download();"></dhv:permission>
 <dhv:permission name="tickets-knowledge-base-view"><input type="button" value="<dhv:label name="accounts.accounts_documents_list_menu.ViewFileContents">View File Contents</dhv:label>" onClick="javascript:view();"></dhv:permission>
</dhv:evaluate>


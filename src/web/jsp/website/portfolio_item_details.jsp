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
  - Version: $Id:  $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.website.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="item" class="org.aspcfs.modules.website.base.PortfolioItem" scope="request"/>
<jsp:useBean id="category" class="org.aspcfs.modules.website.base.PortfolioCategory" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script type="text/javascript">
  function download() {
    window.location.href='PortfolioItemEditor.do?command=Download&itemId=<%= item.getId() %>&fid=<%= item.getImageId() %>&folderId=-1';
  }
  function view() {
    popURL('PortfolioItemEditor.do?command=Download&itemId=<%= item.getId() %>&fid=<%= item.getImageId() %>&view=true', 'Content', 640,480, 1, 1);
  }
</script>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Website.do"><dhv:label name="website.website">Website</dhv:label></a> >
      <a href="PortfolioEditor.do?command=List&categoryId=<%= category.getId() %>"><dhv:label name="product.categoryDetails">Category Details</dhv:label></a> >
      <dhv:label name="product.itemDetails">Item Details</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<%-- Category Trails --%>
<table border="0" cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td>
      <dhv:productCategoryHierarchy link="PortfolioEditor.do?command=List" showLastLink="true"/> >
      <dhv:label name="">Item</dhv:label>
    </td>
  </tr>
</table><br />
<%-- End Category Trails --%>
<%--<dhv:permission name="website-portfolio-edit"><input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='PortfolioItemEditor.do?command=ModifyItem&itemId=<%= item.getId() %>&categoryId=<%= item.getCategoryId() %>';"></dhv:permission>
<dhv:permission name="website-portfolio-delete">
  <input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURL('PortfolioItemEditor.do?command=ConfirmDeleteItem&itemId=<%= item.getId() %>&categoryId=<%= item.getCategoryId() %>&popup=true', 'DeleteKBEntry','320','200','yes','no');">
</dhv:permission>
<dhv:evaluate if="<%= item.getImageId() != -1 %>">
 <dhv:permission name="website-portfolio-view"><input type="button" value="<dhv:label name="accounts.accounts_contacts_detailsimport.DownloadFile">Download File</dhv:label>" onClick="javascript:download();"></dhv:permission>
 <dhv:permission name="website-portfolio-view"><input type="button" value="<dhv:label name="accounts.accounts_documents_list_menu.ViewFileContents">View File Contents</dhv:label>" onClick="javascript:view();"></dhv:permission>
</dhv:evaluate>
<br />&nbsp;<br /> --%>
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
      <%= toHtml(category.getName()) %>
    </td>
  </tr>
	<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="quotes.productName">Name</dhv:label>
    </td>
    <td>
      <%= toHtml(item.getName()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
    </td>
    <td><%= toHtml(item.getDescription()) %></td>
  </tr>
</table>
<dhv:evaluate if="<%= item.getImageId() != -1 %>"><br />
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr><th colspan="2"><strong><dhv:label name="tickets.fileDetails">File Details</dhv:label></strong></th></tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="project.document">Document</dhv:label>
    </td>
    <td><%= toHtml(item.getImage().getClientFilename()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_documents_details.Version">Version</dhv:label>
    </td>
    <td><%= item.getImage().getVersion() %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Caption</dhv:label>
    </td>
    <td><%= toHtml(item.getCaption()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="documents.documents.size">Size</dhv:label>
    </td>
    <td><%= item.getImage().getRelativeSize() %><dhv:label name="admin.oneThousand.abbreviation">k</dhv:label></td>
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
      <dhv:username id="<%= item.getEnteredBy() %>"/>
      <zeroio:tz timestamp="<%= item.getEntered()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= item.getModifiedBy() %>"/>
      <zeroio:tz timestamp="<%= item.getModified()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
    </td>
  </tr>
</table>

<br />
<dhv:permission name="website-portfolio-edit"><input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='PortfolioItemEditor.do?command=ModifyItem&itemId=<%= item.getId() %>&categoryId=<%= item.getCategoryId() %>';"></dhv:permission>
<dhv:permission name="website-portfolio-delete">
  <input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURL('PortfolioItemEditor.do?command=ConfirmDeleteItem&itemId=<%= item.getId() %>&categoryId=<%= item.getCategoryId() %>&popup=true', 'DeleteKBEntry','320','200','yes','no');">
</dhv:permission>
<dhv:evaluate if="<%= item.getImageId() != -1 %>">
 <dhv:permission name="website-portfolio-view"><input type="button" value="<dhv:label name="accounts.accounts_contacts_detailsimport.DownloadFile">Download File</dhv:label>" onClick="javascript:download();"></dhv:permission>
 <dhv:permission name="website-portfolio-view"><input type="button" value="<dhv:label name="accounts.accounts_documents_list_menu.ViewFileContents">View File Contents</dhv:label>" onClick="javascript:view();"></dhv:permission>
</dhv:evaluate>


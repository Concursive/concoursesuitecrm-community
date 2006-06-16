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
  - Version: $Id: product_catalog_listimports_menu.jsp 11310 2005-04-13 20:05:00 +0000 (Ср, 13 апр 2005) mrajkowski $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="products.newImport">New Import</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="products.name">Name</dhv:label>
  </td>
  <td class="containerBody">
    <input type="text" name="newImport" value="<%= toString(ImportDetails.getName()) %>" maxlength="250" size="65"><font color="red">*</font>
    <%= showAttribute(request, "nameError") %>
  </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="product.description">Description</dhv:label>
    </td>
    <td>
      <TEXTAREA NAME="description" ROWS="3" COLS="50"><%= toString(ImportDetails.getDescription()) %></TEXTAREA>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="product.comments">Comments</dhv:label>
    </td>
    <td>
      <TEXTAREA NAME="comments" ROWS="3" COLS="50"><%= toString(ImportDetails.getComments()) %></TEXTAREA>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="products.images">Images</dhv:label>
    </td>  
    <td valign="top">
      <table border="0" cellpadding="0" cellspacing="0" class="empty">
        <tr>
          <td valign="top">
            <input type="file" name="imageId" size="45">
            <%= showError(request, "imagesError") %>
            <%= showWarning(request, "imagesWarning") %>
          </td>
        </tr>
        <tr>
          <td valign="top">
            <br><dhv:label name="products.fileShouldBeInZIPformat.text">* File should be in ZIP format.</dhv:label><br /> <dhv:label name="products.largeFilesUpload.text">Large files may take a while to upload.</dhv:label>
          </td>
        </tr>
      </table>       
	</tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="products.file">File</dhv:label>
    </td>
    <td>
      <table border="0" cellpadding="0" cellspacing="0" class="empty">
        <tr>
          <td valign="top">
            <input type="file" name="id" size="45"><font color="red">*</font>
            <%= showError(request, "fileError", true) %>
          </td>
        </tr>
        <tr>
          <td valign="top">
            <br><dhv:label name="products.fileShouldBeInCSVformat.text">* File should be in CSV format.</dhv:label><br /> <dhv:label name="products.largeFilesUpload.text">Large files may take a while to upload.</dhv:label>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>


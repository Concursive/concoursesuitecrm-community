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
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="fileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/confirmDelete.js"></script>
<script language="JavaScript">
  function checkFileForm(form) {
    var formTest = true;
    var messageText = "";
    if (form.id<%= Campaign.getId() %>.value.length < 5) {
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
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do">Communications</a> >
<a href="CampaignManager.do?command=View">Campaign List</a> >
<a href="CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>">Campaign Details</a> >
<a href="CampaignManager.do?command=ViewAttachmentsOverview&id=<%= Campaign.getId() %>">Attachments</a> >
File Attachments
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" style="border: 1px solid #000;">
  <tr class="containerHeader">
    <td style="border-bottom: 1px solid #000;">
      <strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr>
    <td width="100%" class="containerBack">
      <input type="button" value="Back to Attachment Overview" onClick="javascript:window.location.href='CampaignManager.do?command=ViewAttachmentsOverview&id=<%= Campaign.getId() %>'"><br>
      &nbsp;<br>
      <%-- List of Documents --%>
      <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
        <tr>
          <th colspan="4">
            <strong>Attached files...</strong>
          </th>
        </tr>
        <tr>
          <th width="10" style="text-align: center;">Action</th>
          <th>File Name</th>
          <th style="text-align: center;">Size</th>
          <th style="text-align: center;">Date</th>
        </tr>
      <%
        Iterator j = fileItemList.iterator();
        if ( j.hasNext() ) {
          int rowid = 0;
          while (j.hasNext()) {
            rowid = (rowid != 1?1:2);
            FileItem thisFile = (FileItem)j.next();
      %>      
          <tr class="row<%= rowid %>">
            <td width="10" valign="middle" style="text-align: center;" nowrap>
              <a href="CampaignManager.do?command=DownloadFile&id=<%= Campaign.getId() %>&fid=<%= thisFile.getId() %>">Download</a>
              <dhv:permission name="campaign-campaigns-edit"><a href="javascript:confirmDelete('CampaignManager.do?command=RemoveFile&fid=<%= thisFile.getId() %>&id=<%= Campaign.getId()%>');"><br>Remove</a></dhv:permission>
            </td>
            <td valign="middle" width="100%">
              <%= thisFile.getImageTag() %><%= toHtml(thisFile.getClientFilename()) %>
            </td>
            <td style="text-align: center;" valign="middle" nowrap>
              <%= thisFile.getRelativeSize() %> k&nbsp;
            </td>
            <td nowrap>
              <zeroio:tz timestamp="<%= thisFile.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/><br />
              <dhv:username id="<%= thisFile.getEnteredBy() %>"/>
            </td>
          </tr>
        <%}%>
      <%} else {%>
          <tr class="containerBody">
            <td colspan="4">
              No files attached.
            </td>
          </tr>
      <%}%>
      </table>
      <%-- File upload form, if permission --%>
      <dhv:permission name="campaign-campaigns-edit">
      &nbsp;<br>
      <table cellpadding="4" cellspacing="0" width="100%" class="details">
        <tr>
          <th>
            <strong>Attach a file</strong>
          </th>
        </tr>
        <tr class="containerBody">
        <dhv:formMessage />
          <form method="post" name="inputForm" action="CampaignManager.do?command=UploadFile&id=<%= Campaign.getId() %>" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
            <td width="100%">
              <center>
                <input type="file" name="id<%= Campaign.getId() %>" size="30"><br>
                * Large files may take a while to upload.<br>
                * Remember: This file will eventually be e-mailed, so limit file size to under 2 Megabytes<br>
                Wait for file completion message when upload is complete.<br>
                <input type="submit" value=" Upload File " name="upload">
              </center>
            </td>
          </form>
        </tr>
      </table>
      </dhv:permission>
      &nbsp;<br>
      <input type="button" value="Back to Attachment Overview" onClick="javascript:window.location.href='CampaignManager.do?command=ViewAttachmentsOverview&id=<%= Campaign.getId() %>'">
    </td>
  </tr>
</table>

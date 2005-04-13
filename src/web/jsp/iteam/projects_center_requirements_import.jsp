<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%-- Note: When cancel is submitted, the encoding of the form has to be processed differently
     or the id can be added to the action --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="Requirement" class="com.zeroio.iteam.base.Requirement" scope="request"/>
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
<form method="POST" name="inputForm" action="ProjectManagementRequirements.do?command=Import&pid=<%= Project.getId() %>&rid=<%= Requirement.getId() %>" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
  A project plan can be imported from either a Microsoft Excel spreadsheet or
  from an Omni Outliner template.<br />
  <br />
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2" align="left">
          Upload File
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">File</td>
      <td>
        <input type="file" name="file" size="45">
      </td>
    </tr>
  </table>
  <p align="center">* Large files may take awhile to upload.<br />
     Wait for file completion message when upload finishes.
  </p>
  <input type="submit" value="Upload" name="upload">
  <input type="hidden" name="pid" value="<%= Project.getId() %>">
  <input type="hidden" name="rid" value="<%= Requirement.getId() %>">
</form>


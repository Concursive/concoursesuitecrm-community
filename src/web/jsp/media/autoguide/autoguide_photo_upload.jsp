<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.media.autoguide.base.*" %>
<jsp:useBean id="InventoryItem" class="org.aspcfs.modules.media.autoguide.base.Inventory" scope="request"/>
<%@ include file="../../initPage.jsp" %>
<link rel="stylesheet" href="css/photolist.css" type="text/css">
<script language="JavaScript">
  function checkFileForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    if (form.id<%= InventoryItem.getId() %>.value.length < 5) {
      messageText += "- File is required\r\n";
      formTest = false;
    }
    if (formTest == false) {
      messageText = "The file could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      form.dosubmit.value = "true";
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
<link rel="stylesheet" href="css/photolist.css" type="text/css">
<form name="uploadPhoto" action="AutoGuide.do?command=Upload&id=<%= InventoryItem.getId() %>&return=<%= java.net.URLEncoder.encode(request.getParameter("return")) %>" enctype="multipart/form-data" method='post' onSubmit="return checkFileForm(this);">
<table cellpadding="10" cellspacing="0" border="0" width="100%">
  <tr class="title">
    <td>
      Photo Upload
    </td>
  </tr>
  <tr>
    <td class="PhotoDetail">
      <span>
        <img src="<%= (InventoryItem.hasPictureId()?"AutoGuide.do?command=ShowImage&id=" + InventoryItem.getId() + "&fid=" + InventoryItem.getPictureId():"images/vehicle_unavailable.gif") %>" border="0"/><br>
        &nbsp;<br>
<dhv:evaluate exp="<%= hasText(InventoryItem.getStockNo()) %>">
        #<%= toHtml(InventoryItem.getStockNo()) %><br>
</dhv:evaluate>
        <%= InventoryItem.getVehicle().getYear() %> <%= toHtml(InventoryItem.getVehicle().getMake().getName()) %> <%= toHtml(InventoryItem.getVehicle().getModel().getName()) %>
<dhv:evaluate exp="<%= (InventoryItem.getSellingPrice() > 0) %>">
        <br><%= InventoryItem.getSellingPriceString() %>
</dhv:evaluate>
        <br>&nbsp;
        <br><input type="file" name="id<%= InventoryItem.getId() %>" size="45">
        <br>
<dhv:permission name="accounts-autoguide-inventory-edit">
        <input type='submit' value=' Upload ' name="upload">
</dhv:permission>
        <input type='submit' value='Cancel' onClick="javascript:this.form.dosubmit.value='false';window.close();">
      </span>
    </td>
  </tr>
</table>
<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="id" value="<%= InventoryItem.getId() %>">
</form>


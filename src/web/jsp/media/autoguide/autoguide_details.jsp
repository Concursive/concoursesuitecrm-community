<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.autoguide.base.*" %>
<jsp:useBean id="InventoryItem" class="com.darkhorseventures.autoguide.base.Inventory" scope="request"/>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.UserBean" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<link rel="stylesheet" href="css/photolist.css" type="text/css">
<a href="AutoGuide.do?command=List">Back to Vehicle List</a><p>
<form action='/AutoGuide.do?command=Details&id=<%= InventoryItem.getId() %>&action=modify' method='post'>
<%--
<dhv:permission name="autoguide-inventory-edit"><input type='submit' value='Modify' name='Modify'></dhv:permission>
<dhv:permission name="autoguide-inventory-delete"><input type="submit" value="Delete" onClick="javascript:this.form.action='AutoGuide.do?command=Delete&id=<%=InventoryItem.getId() %>'"></dhv:permission>
<dhv:permission name="autoguide-inventory-edit,autoguide-inventory-delete"><br>&nbsp;</dhv:permission>
--%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr>
    <td width="100%" valign="top">

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2" valign="center" align="center">
	    <strong><%= InventoryItem.getVehicle().getYear() %> <%= toHtml(InventoryItem.getVehicle().getMake().getName()) %> <%= toHtml(InventoryItem.getVehicle().getModel().getName()) %></strong>
	  </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Organization</td>
    <td width="100%">
      <%= toHtml(InventoryItem.getOrganization().getName()) %>
      (<%= toHtml(InventoryItem.getOrganization().getAccountNumber()) %>)<br>
      <%= toHtml(InventoryItem.getOrganization().getPhoneNumber("Main")) %>
    </td>
  </tr>
<dhv:evaluate exp="<%= hasText(InventoryItem.getStockNo()) %>">
  <tr>
    <td nowrap class="formLabel">Stock No</td>
    <td><%= toHtml(InventoryItem.getStockNo()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= (InventoryItem.getMileage() > -1) %>">
  <tr>
    <td nowrap class="formLabel">Mileage</td>
    <td><%= InventoryItem.getMileageString() %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= hasText(InventoryItem.getVin()) %>">
  <tr>
    <td nowrap class="formLabel">VIN</td>
    <td><%= toHtml(InventoryItem.getVin()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= (InventoryItem.getSellingPrice() > 0) %>">
  <tr>
    <td nowrap class="formLabel">Selling Price</td>
    <td><%= InventoryItem.getSellingPriceString() %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= hasText(InventoryItem.getExteriorColor()) %>">
  <tr>
    <td nowrap class="formLabel">Exterior Color</td>
    <td><%= toHtml(InventoryItem.getExteriorColor()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= hasText(InventoryItem.getCondition()) %>">
  <tr>
    <td nowrap class="formLabel">Condition</td>
    <td><%= toHtml(InventoryItem.getCondition()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= hasText(InventoryItem.getComments()) %>">
  <tr>
    <td nowrap class="formLabel">Additional Text</td>
    <td><%= toHtml(InventoryItem.getComments()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= InventoryItem.hasOptions() %>">
  <tr>
    <td nowrap class="formLabel">Options</td>
    <td>
<%
      Iterator options = InventoryItem.getOptions().iterator();
      while (options.hasNext()) {
        Option thisOption = (Option)options.next();
%>
      <%= toHtml(thisOption.getName()) %><%= (options.hasNext()?", ":"") %>
<%
      }
%>
    </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= InventoryItem.hasAdRuns() %>">
  <tr>
    <td nowrap class="formLabel" valign="top">Ad Runs</td>
    <td>
      <table cellpadding="4" cellspacing="0" border="0" width="100%">
<%
      Iterator adruns = InventoryItem.getAdRuns().iterator();
      while (adruns.hasNext()) {
        AdRun thisAdRun = (AdRun)adruns.next();
%>
  <tr>
    <td class="rowUnderline" width="10" nowrap align="left">
      <img border="0" src="<%= (thisAdRun.isComplete()?"images/box-checked.gif":"images/box.gif") %>" alt="" align="absmiddle">
    </td>
    <td class="rowUnderline" width="10%" nowrap align="center">
      <%= toDateString(thisAdRun.getRunDate()) %>
    </td>
    <td class="rowUnderline" width="10%" nowrap align="center">
      <%= toHtml(thisAdRun.getAdTypeName()) %>
    </td>
    <td class="rowUnderline" width="10%" nowrap align="center">
      <%= (thisAdRun.getIncludePhoto()?"Include Photo":"No Photo") %>
    </td>
    <td class="rowUnderline" width="90%" nowrap>
      &nbsp;
    </td>
    <td class="rowUnderline" width="10%" nowrap align="right">
<dhv:evaluate exp="<%= !thisAdRun.isComplete() %>">
      <a href="javascript:confirmForward('AutoGuide.do?command=MarkComplete&id=<%= InventoryItem.getId() %>&adId=<%= thisAdRun.getId() %>');">Set this item as completed</a>
</dhv:evaluate>
<dhv:evaluate exp="<%= thisAdRun.isComplete() && thisAdRun.getCompletedBy() == User.getUserId() %>">
      <a href="javascript:confirmForward('AutoGuide.do?command=MarkIncomplete&id=<%= InventoryItem.getId() %>&adId=<%= thisAdRun.getId() %>');"><font color="#8F8F8F">Set this item as incomplete</font></a>
</dhv:evaluate>
      &nbsp;
    </td>
  </tr>
<%
      }
%>
      </table>
    </td>
  </tr>
</dhv:evaluate>
</table>
<dhv:evaluate exp="<%= InventoryItem.hasAdRuns() %>">
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr>
    <td>
<font color="#8F8F8F">Status Icons:<br>
<img border="0" src="images/box.gif" alt="" align="absmiddle"> Ad Run has not been processed by Graphic Designer<br>
<img border="0" src="images/box-checked.gif" alt="" align="absmiddle"> Ad Run has been processed by Graphic Designer
</font> 
    </td>
  </tr>
</table>
</dhv:evaluate>
    </td>
    <td class="PhotoDetail">
      <span>
<dhv:evaluate exp="<%= InventoryItem.hasPictureId() %>">
        <a href="javascript:popURL('autoguide_popup_photo.jsp?id=<%= InventoryItem.getId() %>&fid=<%= InventoryItem.getPictureId() %>&ver=1.0','Photo','760','550','yes','yes');"><img src="AutoGuide.do?command=ShowImage&id=<%= InventoryItem.getId() %>&fid=<%= InventoryItem.getPictureId() %>" border="0"/></a>
</dhv:evaluate>
<dhv:evaluate exp="<%= !InventoryItem.hasPictureId() %>">
        <img src="images/vehicle_unavailable.gif" border="0"/>
</dhv:evaluate>
        <br>
      </span>
<dhv:evaluate exp="<%= InventoryItem.hasPictureId() %>">   
      <br><a href="AutoGuide.do?command=DownloadImage&id=<%= InventoryItem.getId() %>&fid=<%= InventoryItem.getPictureId() %>">D/L Hi-Res</a> (<%= InventoryItem.getPicture().getVersion(1.0d).getRelativeSize() %>k)<br>
</dhv:evaluate>
      <br><a href="AutoGuide.do?command=DownloadText&id=<%= InventoryItem.getId() %>">D/L Text</a>
    </td>
  </tr>
</table>
<%--
<dhv:permission name="autoguide-inventory-edit,autoguide-inventory-delete"><br></dhv:permission>
<dhv:permission name="autoguide-inventory-edit"><input type='submit' value='Modify' name='Modify'></dhv:permission>
<dhv:permission name="autoguide-inventory-delete"><input type="submit" value="Delete" onClick="javascript:this.form.action='AutoGuide.do?command=Delete&id=<%= InventoryItem.getId() %>'"></dhv:permission>
--%>
</form>


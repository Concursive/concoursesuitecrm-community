<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.autoguide.base.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="InventoryItem" class="com.darkhorseventures.autoguide.base.Inventory" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popURL.js"></script>
<link rel="stylesheet" href="css/photolist.css" type="text/css">
<form name="modInventory" action='/AccountsAutoGuide.do?command=AccountModify&id=<%= InventoryItem.getId() %>&orgId=<%= OrgDetails.getOrgId() %>' method='post'>
<a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>">Back to Vehicle List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="vehicles" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="autoguide-inventory-edit"><input type='submit' value='Modify' name='Modify'></dhv:permission>
<dhv:permission name="autoguide-inventory-delete"><input type="submit" value="Delete" onClick="javascript:this.form.action='AccountsAutoGuide.do?command=Delete&id=<%=InventoryItem.getId() %>&orgId=<%= OrgDetails.getOrgId() %>'"></dhv:permission>
<dhv:permission name="autoguide-inventory-edit,autoguide-inventory-delete"><br>&nbsp;</dhv:permission>

<table cellpadding="4" cellspacing="0" border="0" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr>
    <td width="100%" valign="top">

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2" valign="center" align="center">
	    <strong><%= InventoryItem.getVehicle().getYear() %> <%= toHtml(InventoryItem.getVehicle().getMake().getName()) %> <%= toHtml(InventoryItem.getVehicle().getModel().getName()) %></strong>
	  </td>
  </tr>
<dhv:evaluate exp="<%= hasText(InventoryItem.getStockNo()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">Stock No</td>
    <td><%= toHtml(InventoryItem.getStockNo()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= (InventoryItem.getMileage() > -1) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">Mileage</td>
    <td><%= InventoryItem.getMileageString() %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= hasText(InventoryItem.getVin()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">VIN</td>
    <td><%= toHtml(InventoryItem.getVin()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= (InventoryItem.getSellingPrice() > 0) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">Selling Price</td>
    <td><%= InventoryItem.getSellingPriceString() %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= hasText(InventoryItem.getExteriorColor()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">Exterior Color</td>
    <td><%= toHtml(InventoryItem.getExteriorColor()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= hasText(InventoryItem.getCondition()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">Condition</td>
    <td><%= toHtml(InventoryItem.getCondition()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= hasText(InventoryItem.getComments()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">Additional Text</td>
    <td><%= toHtml(InventoryItem.getComments()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= InventoryItem.hasOptions() %>">
  <tr class="containerBody">
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
  <tr class="containerBody">
    <td nowrap class="formLabel">Ad Run Dates</td>
    <td>
<%
      Iterator adruns = InventoryItem.getAdRuns().iterator();
      while (adruns.hasNext()) {
        AdRun thisAdRun = (AdRun)adruns.next();
%>
      <img border="0" src="<%= (thisAdRun.isComplete()?"images/box-checked.gif":"images/box.gif") %>" alt="" align="absmiddle"><%= toDateString(thisAdRun.getRunDate()) %>
      (<%= toHtml(thisAdRun.getAdTypeName()) %> - with<%= (thisAdRun.getIncludePhoto()?"":"out") %> photo)<%= (adruns.hasNext()?"<br>":"") %>
<%
      }
%>
    </td>
  </tr>
</dhv:evaluate>
</table>
    </td>
    <td class="PhotoDetail">
      <span>
        <img src="<%= (InventoryItem.hasPictureId()?"AutoGuide.do?command=ShowImage&id=" + InventoryItem.getId() + "&fid=" + InventoryItem.getPictureId():"images/vehicle_unavailable.gif") %>" border="0"/>
        <br>
      </span>
      <a href="javascript:popURL('AutoGuide.do?command=UploadForm&id=<%= InventoryItem.getId() %>&orgId=<%= OrgDetails.getOrgId() %>&return=<%= java.net.URLEncoder.encode("AccountsAutoGuide.do?command=Details&orgId=" + OrgDetails.getOrgId() + "&id=" + InventoryItem.getId()) %>','Photo Upload','500','300','no','no');">Upload Photo</a>
    </td>
  </tr>
</table>
<dhv:permission name="autoguide-inventory-edit,autoguide-inventory-delete"><br></dhv:permission>
<dhv:permission name="autoguide-inventory-edit"><input type='submit' value='Modify' name='Modify'></dhv:permission>
<dhv:permission name="autoguide-inventory-delete"><input type="submit" value="Delete" onClick="javascript:this.form.action='AccountsAutoGuide.do?command=Delete&id=<%= InventoryItem.getId() %>&orgId=<%= OrgDetails.getOrgId() %>'"></dhv:permission>
  </td>
  </tr>
</table>
</form>


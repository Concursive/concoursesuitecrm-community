<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.autoguide.base.*" %>
<jsp:useBean id="InventoryItem" class="com.darkhorseventures.autoguide.base.AccountInventory" scope="request"/>
<%@ include file="initPage.jsp" %>
<a href="AutoGuide.do?command=List">Back to Vehicle List</a><p>
<form action='/AutoGuide.do?command=Details&id=<%= InventoryItem.getId() %>&action=modify' method='post'>
<dhv:permission name="autoguide-inventory-edit"><input type='submit' value='Modify' name='Modify'></dhv:permission>
<dhv:permission name="autoguide-inventory-delete"><input type="submit" value="Delete" onClick="javascript:this.form.action='AutoGuide.do?command=Delete&id=<%=InventoryItem.getId() %>'"></dhv:permission>
<dhv:permission name="autoguide-inventory-edit,autoguide-inventory-delete"><br>&nbsp;</dhv:permission>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2" valign="center" align="center">
	    <strong><%= InventoryItem.getVehicle().getYear() %> <%= toHtml(InventoryItem.getVehicle().getMake().getName()) %> <%= toHtml(InventoryItem.getVehicle().getModel().getName()) %></strong>
	  </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Organization</td>
    <td width="100%"><%= toHtml(InventoryItem.getOrganization().getName()) %>&nbsp;</td>
  </tr>
<dhv:evaluate exp="<%= (InventoryItem.getStockNo() != null) %>">
  <tr>
    <td nowrap class="formLabel">Stock No</td>
    <td><%= toHtml(InventoryItem.getStockNo()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= (InventoryItem.getVin() != null) %>">
  <tr>
    <td nowrap class="formLabel">VIN</td>
    <td><%= toHtml(InventoryItem.getVin()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= (InventoryItem.getMileage() > -1) %>">
  <tr>
    <td nowrap class="formLabel">Mileage</td>
    <td><%= InventoryItem.getMileage() %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= (InventoryItem.getCondition() != null) %>">
  <tr>
    <td nowrap class="formLabel">Condition</td>
    <td><%= toHtml(InventoryItem.getCondition()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= (InventoryItem.getSellingPrice() > 0) %>">
  <tr>
    <td nowrap class="formLabel">Selling Price</td>
    <td><%= InventoryItem.getSellingPriceString() %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= (InventoryItem.getExteriorColor() != null) %>">
  <tr>
    <td nowrap class="formLabel">Exterior Color</td>
    <td><%= toHtml(InventoryItem.getExteriorColor()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= (InventoryItem.getInteriorColor() != null) %>">
  <tr>
    <td nowrap class="formLabel">Interior Color</td>
    <td><%= toHtml(InventoryItem.getInteriorColor()) %>&nbsp;</td>
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
</table>
<dhv:permission name="autoguide-inventory-edit,autoguide-inventory-delete"><br></dhv:permission>
<dhv:permission name="autoguide-inventory-edit"><input type='submit' value='Modify' name='Modify'></dhv:permission>
<dhv:permission name="autoguide-inventory-delete"><input type="submit" value="Delete" onClick="javascript:this.form.action='AutoGuide.do?command=Delete&id=<%= InventoryItem.getId() %>'"></dhv:permission>
</form>


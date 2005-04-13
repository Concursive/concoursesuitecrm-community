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
<%@ page import="java.util.*,org.aspcfs.modules.media.autoguide.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="InventoryItem" class="org.aspcfs.modules.media.autoguide.base.Inventory" scope="request"/>
<%@ include file="../../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<link rel="stylesheet" href="css/photolist.css" type="text/css">
<form name="modInventory" action="AccountsAutoGuide.do?command=AccountModify&id=<%= InventoryItem.getId() %>&orgId=<%= OrgDetails.getOrgId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="AccountsAutoGuide.do?command=AccountList&orgId=<%=OrgDetails.getOrgId()%>">Vehicle Inventory List</a> >
Vehicle Details
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="accounts" selected="vehicles" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>">
  <dhv:permission name="accounts-autoguide-inventory-edit"><input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:this.form.action='AccountsAutoGuide.do?command=AccountModify&id=<%= InventoryItem.getId() %>&orgId=<%= OrgDetails.getOrgId() %>';submit();"></dhv:permission>
  <dhv:permission name="accounts-autoguide-inventory-delete"><input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:this.form.action='AccountsAutoGuide.do?command=Delete&id=<%=InventoryItem.getId() %>&orgId=<%= OrgDetails.getOrgId() %>';confirmSubmit(this.form);"></dhv:permission>
  <dhv:permission name="accounts-autoguide-inventory-edit,accounts-autoguide-inventory-delete"><br>&nbsp;</dhv:permission>
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td width="100%" valign="top">
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <th colspan="2" valign="center" style="text-align: center;">
        <strong><%= InventoryItem.getVehicle().getYear() %>
        <%= toHtml(InventoryItem.getVehicle().getMake().getName()) %>
        <%= toHtml(InventoryItem.getVehicle().getModel().getName()) %>
        <%= toHtml(InventoryItem.getStyle()) %></strong>
      </th>
    </tr>
  <dhv:evaluate if="<%= hasText(InventoryItem.getStockNo()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">Stock No</td>
      <td><%= toHtml(InventoryItem.getStockNo()) %>&nbsp;</td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= (InventoryItem.getMileage() > -1) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">Mileage</td>
      <td><%= InventoryItem.getMileageString() %>&nbsp;</td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(InventoryItem.getVin()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">VIN</td>
      <td><%= toHtml(InventoryItem.getVin()) %>&nbsp;</td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= (InventoryItem.getSellingPrice() > 0) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">Selling Price</td>
      <td><%= InventoryItem.getSellingPriceString() %>&nbsp;</td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(InventoryItem.getSellingPriceText()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">Selling Price</td>
      <td><%= toHtml(InventoryItem.getSellingPriceText()) %>&nbsp;</td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(InventoryItem.getExteriorColor()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">Exterior Color</td>
      <td><%= toHtml(InventoryItem.getExteriorColor()) %>&nbsp;</td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(InventoryItem.getCondition()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">Condition</td>
      <td><%= toHtml(InventoryItem.getCondition()) %>&nbsp;</td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(InventoryItem.getComments()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">Additional Text</td>
      <td><%= toHtml(InventoryItem.getComments()) %>&nbsp;</td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= InventoryItem.hasOptions() %>">
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
  <dhv:evaluate if="<%= InventoryItem.hasAdRuns() %>">
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">Ad Runs</td>
      <td>
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="empty">
  <%
        Iterator adruns = InventoryItem.getAdRuns().iterator();
        while (adruns.hasNext()) {
          AdRun thisAdRun = (AdRun)adruns.next();
  %>
    <tr>
      <td class="rowUnderline" width="10" nowrap style="text-align: left;">
        <img border="0" src="<%= (thisAdRun.isComplete()?"images/box-checked.gif":"images/box.gif") %>" alt="" align="absmiddle">
      </td>
      <td class="rowUnderline" width="10%" nowrap style="text-align: center;">
        <%= toDateString(thisAdRun.getRunDate()) %>
      </td>
      <td class="rowUnderline" width="10%" nowrap style="text-align: center;">
        <%= toHtml(thisAdRun.getAdTypeName()) %>
      </td>
      <td class="rowUnderline" width="10%" nowrap style="text-align: center;">
        <%= (thisAdRun.getIncludePhoto()?"Include Photo":"No Photo") %>
      </td>
      <td class="rowUnderline" width="10%" nowrap style="text-align: left;">
        &nbsp;
      </td>
      <td class="rowUnderline" width="90%" nowrap>
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
  <dhv:evaluate if="<%= InventoryItem.hasAdRuns() %>">
  &nbsp;<br>
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td>
  <font color="#8F8F8F">Status Icons:<br>
  <img border="0" src="images/box.gif" alt="" align="absmiddle">
  Ad Run has not been processed by Graphic Designer<br>
  <img border="0" src="images/box-checked.gif" alt="" align="absmiddle">
  Ad Run has been processed by Graphic Designer
  </font>
      </td>
    </tr>
  </table>
  </dhv:evaluate>
      </td>
      <td class="PhotoDetail">
        <span>
  <dhv:evaluate if="<%= InventoryItem.hasPictureId() %>">
          <a href="javascript:popURL('media/autoguide/autoguide_popup_photo.jsp?id=<%= InventoryItem.getId() %>&fid=<%= InventoryItem.getPictureId() %>&ver=1.0&popup=true','Photo','760','550','yes','yes');"><img src="AutoGuide.do?command=ShowImage&id=<%= InventoryItem.getId() %>&fid=<%= InventoryItem.getPictureId() %>" border="0"/></a>
  </dhv:evaluate>
  <dhv:evaluate if="<%= !InventoryItem.hasPictureId() %>">
          <img src="images/vehicle_unavailable.gif" border="0"/>
  </dhv:evaluate>
        </span>
  <dhv:permission name="accounts-autoguide-inventory-edit">
        <br><a href="javascript:popURLReturn('AutoGuide.do?command=UploadForm&id=<%= InventoryItem.getId() %>&orgId=<%= OrgDetails.getOrgId() %>&popup=true', 'AccountsAutoGuide.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>&id=<%= InventoryItem.getId() %>', 'Photo_Upload','500','300','no','yes');">Upload Photo</a>
  </dhv:permission>
      </td>
    </tr>
  </table>
  <dhv:permission name="accounts-autoguide-inventory-edit,accounts-autoguide-inventory-delete"><br></dhv:permission>
  <dhv:permission name="accounts-autoguide-inventory-edit"><input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:this.form.action='AccountsAutoGuide.do?command=AccountModify&id=<%= InventoryItem.getId() %>&orgId=<%= OrgDetails.getOrgId() %>';submit();"></dhv:permission>
  <dhv:permission name="accounts-autoguide-inventory-delete"><input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:this.form.action='AccountsAutoGuide.do?command=Delete&id=<%=InventoryItem.getId() %>&orgId=<%= OrgDetails.getOrgId() %>';confirmSubmit(this.form);"></dhv:permission>
</dhv:container>
</form>


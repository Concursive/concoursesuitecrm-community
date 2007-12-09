<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.media.autoguide.base.*" %>
<jsp:useBean id="InventoryItem" class="org.aspcfs.modules.media.autoguide.base.Inventory" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../../initPage.jsp" %>
<%@ include file="../../initPageIsManagerOf.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<link rel="stylesheet" href="css/photolist.css" type="text/css">
<a href="AutoGuide.do?command=List">Back to Vehicle List</a><p>
<form action='AutoGuide.do?command=Details&id=<%= InventoryItem.getId() %>&action=modify' method='post'>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td width="100%" valign="top">
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2" valign="center" style="text-align: center;">
	    <strong><%= toHtml(InventoryItem.getVehicle().getMake().getName()) %>
      <%= toHtml(InventoryItem.getVehicle().getModel().getName()) %><dhv:evaluate if="<%= hasText(InventoryItem.getStyle()) %>"> <%= toHtml(InventoryItem.getStyle()) %></dhv:evaluate>
      <%= InventoryItem.getVehicle().getYear() %></strong>
	  </th>
  </tr>
  <tr>
    <td nowrap class="formLabel">Organization</td>
    <td width="100%">
      <%= toHtml(InventoryItem.getOrganization().getName()) %>
      (<%= toHtml(InventoryItem.getOrganization().getAccountNumber()) %>)<br>
      <%= toHtml(InventoryItem.getOrganization().getPhoneNumber("Main")) %>
    </td>
  </tr>
<dhv:evaluate if="<%= hasText(InventoryItem.getStockNo()) %>">
  <tr>
    <td nowrap class="formLabel">Stock No</td>
    <td><%= toHtml(InventoryItem.getStockNo()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%= (InventoryItem.getMileage() > -1) %>">
  <tr>
    <td nowrap class="formLabel">Mileage</td>
    <td><%= InventoryItem.getMileageString() %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%= hasText(InventoryItem.getVin()) %>">
  <tr>
    <td nowrap class="formLabel">VIN</td>
    <td><%= toHtml(InventoryItem.getVin()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%= (InventoryItem.getSellingPrice() > 0) %>">
  <tr>
    <td nowrap class="formLabel">Selling Price</td>
    <td><%= InventoryItem.getSellingPriceString() %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%= hasText(InventoryItem.getSellingPriceText()) %>">
  <tr>
    <td nowrap class="formLabel">Selling Price</td>
    <td><%= toHtml(InventoryItem.getSellingPriceText()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%= hasText(InventoryItem.getExteriorColor()) %>">
  <tr>
    <td nowrap class="formLabel">Exterior Color</td>
    <td><%= toHtml(InventoryItem.getExteriorColor()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%= hasText(InventoryItem.getCondition()) %>">
  <tr>
    <td nowrap class="formLabel">Condition</td>
    <td><%= toHtml(InventoryItem.getCondition()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%= hasText(InventoryItem.getComments()) %>">
  <tr>
    <td nowrap class="formLabel">Additional Text</td>
    <td><%= toHtml(InventoryItem.getComments()) %>&nbsp;</td>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%= InventoryItem.hasOptions() %>">
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
<dhv:evaluate if="<%= InventoryItem.hasAdRuns() %>">
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
    <td class="rowUnderline" width="10" nowrap>
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
    <td class="rowUnderline" width="90%" nowrap>
      &nbsp;
    </td>
    <td class="rowUnderline" width="10%" nowrap style="text-align: right;">
<dhv:permission name="autoguide-adruns-edit">
<dhv:evaluate if="<%= !thisAdRun.isComplete() %>">
      <a href="javascript:confirmForward('AutoGuide.do?command=MarkComplete&id=<%= InventoryItem.getId() %>&adId=<%= thisAdRun.getId() %>');">Set this item as completed</a>
</dhv:evaluate>
<dhv:evaluate if="<%= thisAdRun.isComplete() && (thisAdRun.getCompletedBy() == User.getUserId() || isManagerOf(pageContext, User.getUserId(), thisAdRun.getCompletedBy())) %>">
      <a href="javascript:confirmForward('AutoGuide.do?command=MarkIncomplete&id=<%= InventoryItem.getId() %>&adId=<%= thisAdRun.getId() %>');"><font color="#8F8F8F">Set this item as incomplete</font></a>
</dhv:evaluate>
</dhv:permission>
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
&nbsp;<br>
<%-- The following is specially formatted to look correctly in an input field and cannot have line breaks --%><% String phoneType = "Main"; %>
Ad Text:
<input type="text" name="adtext" size="80" value="<%= toHtml(InventoryItem.getVehicle().getMake().getName()) %> <%= toHtml(InventoryItem.getVehicle().getModel().getName()) %><dhv:evaluate if="<%= hasText(InventoryItem.getStyle()) %>"> <%= toHtml(InventoryItem.getStyle()) %></dhv:evaluate> <%= InventoryItem.getVehicle().getYear() %><dhv:evaluate if="<%= hasText(InventoryItem.getExteriorColor()) %>"> <%= toHtml(InventoryItem.getExteriorColor()) %></dhv:evaluate><dhv:evaluate if="<%= (InventoryItem.getMileage() > -1) %>"> <%= InventoryItem.getMileageString() %></dhv:evaluate><dhv:evaluate if="<%= InventoryItem.hasOptions() %>"> <%
      Iterator options = InventoryItem.getOptions().iterator();
      while (options.hasNext()) {
        Option thisOption = (Option)options.next();
%><%= toHtml(thisOption.getName()) %><%= (options.hasNext()?", ":"") %><%
      }
%></dhv:evaluate><dhv:evaluate if="<%= hasText(InventoryItem.getComments()) %>"> <%= toHtml(InventoryItem.getComments()) %></dhv:evaluate><dhv:evaluate if="<%= hasText(InventoryItem.getCondition()) %>"> <%= toHtml(InventoryItem.getCondition()) %></dhv:evaluate><dhv:evaluate if="<%= (InventoryItem.getSellingPrice() > 0) %>"> <%= InventoryItem.getSellingPriceString() %></dhv:evaluate><dhv:evaluate if="<%= hasText(InventoryItem.getSellingPriceText()) %>"> <%= toHtml(InventoryItem.getSellingPriceText()) %></dhv:evaluate><dhv:evaluate if="<%= hasText(InventoryItem.getOrganization().getPhoneNumber(phoneType)) %>"> <%= toHtml(InventoryItem.getOrganization().getPhoneNumber("Main")) %></dhv:evaluate>"><br>
<dhv:evaluate if="<%= InventoryItem.hasAdRuns() %>">
&nbsp;<br><%-- End formatting --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="empty">
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
<dhv:evaluate if="<%= InventoryItem.hasPictureId() %>">
        <a href="javascript:popURL('media/autoguide/autoguide_popup_photo.jsp?id=<%= InventoryItem.getId() %>&fid=<%= InventoryItem.getPictureId() %>&ver=1.0&popup=true','Photo','760','550','yes','yes');"><img src="AutoGuide.do?command=ShowImage&id=<%= InventoryItem.getId() %>&fid=<%= InventoryItem.getPictureId() %>" border="0"/></a>
</dhv:evaluate>
<dhv:evaluate if="<%= !InventoryItem.hasPictureId() %>">
        <img src="images/vehicle_unavailable.gif" border="0"/>
</dhv:evaluate>
      </span>
<dhv:evaluate if="<%= InventoryItem.hasPictureId() %>">
      <br><a href="AutoGuide.do?command=DownloadImage&id=<%= InventoryItem.getId() %>&fid=<%= InventoryItem.getPictureId() %>">D/L Hi-Res</a> (<%= InventoryItem.getPicture().getVersion(1.0d).getRelativeSize() %>k)<br>
</dhv:evaluate>
      <br><a href="AutoGuide.do?command=DownloadText&id=<%= InventoryItem.getId() %>">D/L Text</a>
    </td>
  </tr>
</table>
</form>


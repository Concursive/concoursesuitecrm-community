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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,java.text.DateFormat" %>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.modules.contacts.base.ContactTypeList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="assetContact" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="asset" class="org.aspcfs.modules.assets.base.Asset" scope="request"/>
<jsp:useBean id="serviceContract" class="org.aspcfs.modules.servicecontracts.base.ServiceContract" scope="request"/>
<jsp:useBean id="responseModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="phoneModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="onsiteModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="emailModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="categoryList1" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<jsp:useBean id="categoryList2" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<jsp:useBean id="categoryList3" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<jsp:useBean id="assetStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<form name="viewAccountAsset" action="AccountsAssets.do?command=Modify&auto-populate=true&id=<%=asset.getId()%>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
  <a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
  <a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
  <a href="AccountsAssets.do?command=List&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.Assets">Assets</dhv:label></a> >
  <dhv:label name="accounts.accounts_asset_details.AssetDetails">Asset Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="accounts" selected="assets" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>">
  <dhv:container name="accountsassets" selected="details" object="asset" param="<%= "id=" + asset.getId() %>">
  <dhv:evaluate if="<%= !OrgDetails.isTrashed() || !asset.isTrashed()%>">
    <dhv:permission name="accounts-assets-edit"><input type=submit value="<dhv:label name="global.button.modify">Modify</dhv:label>"></dhv:permission>
    <dhv:permission name="accounts-assets-delete"><input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('AccountsAssets.do?command=ConfirmDelete&orgId=<%=OrgDetails.getOrgId()%>&id=<%=asset.getId()%>&popup=true','AccountsServiceContracts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>&id=<%=serviceContract.getId()%>', 'Delete_asset','320','200','yes','no');"></dhv:permission>
  </dhv:evaluate>
    <input type=hidden name="orgId" value = <%= OrgDetails.getOrgId() %> >
    <input type=hidden name="id" value = <%= asset.getOrgId() %> >
    <br /><br />
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <tr>
        <th colspan="2">
          <strong><dhv:label name="accounts.accountasset_include.SpecificInformation">Specific Information</dhv:label></strong>
        </th>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.Vendor">Vendor</dhv:label>
        </td>
        <td>
          <%= toHtml(asset.getVendor()) %>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.Manufacturer">Manufacturer</dhv:label>
        </td>
        <td>
          <%= toHtml(asset.getManufacturer()) %>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.SerialNumber">Serial Number</dhv:label>
        </td>
        <td>
          <%= toHtml(asset.getSerialNumber()) %>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.ModelVersion">Model/Version</dhv:label>
        </td>
        <td>
          <%= toHtml(asset.getModelVersion()) %>
        </td>
      </tr>
      <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
        </td>
        <td>
          <%= toHtml(asset.getDescription()) %>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.DateListed">Date Listed</dhv:label>
        </td>
        <td>
          <zeroio:tz timestamp="<%= asset.getDateListed() %>" dateOnly="true" timeZone="<%= asset.getDateListedTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
          <% if(!User.getTimeZone().equals(asset.getDateListedTimeZone())){%>
          <br />
          <zeroio:tz timestamp="<%= asset.getDateListed() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
          <% } %>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.Location">Location</dhv:label>
        </td>
        <td>
          <%= toHtml(asset.getLocation()) %>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.AssetTag">Asset Tag</dhv:label>
        </td>
        <td>
          <%= toHtml(asset.getAssetTag()) %>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.Status">Status</dhv:label>
        </td>
        <td>
          <dhv:evaluate if="<%= asset.getStatus() > 0 %>">
            <%= toHtml(assetStatusList.getSelectedValue(asset.getStatus())) %>
          </dhv:evaluate>&nbsp;
        </td>
      </tr>
    </table>
    <br>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <tr>
        <th colspan="2">
          <strong><dhv:label name="accounts.accountasset_include.Category">Category</dhv:label></strong>
        </th>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.Level1">Level 1</dhv:label>
        </td>
        <td>
          <dhv:evaluate if="<%= asset.getLevel1() > 0 %>">
            <%= toHtml(categoryList1.getSelectedValue(asset.getLevel1())) %>
          </dhv:evaluate>&nbsp;
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.Level2">Level 2</dhv:label>
        </td>
        <td>
          <dhv:evaluate if="<%= asset.getLevel2() > 0 %>">
            <%= toHtml(categoryList2.getSelectedValue(asset.getLevel2())) %>
          </dhv:evaluate>&nbsp;
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.Level3">Level 3</dhv:label>
        </td>
        <td>
          <dhv:evaluate if="<%= asset.getLevel3() > 0 %>">
            <%= toHtml(categoryList3.getSelectedValue(asset.getLevel3())) %>
          </dhv:evaluate>&nbsp;
        </td>
      </tr>
    </table>
    <br>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <tr>
        <th colspan="2">
          <strong><dhv:label name="accounts.accountasset_include.ServiceContract">Service Contract</dhv:label></strong>
        </th>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.ServiceContractNumber">Service Contract Number</dhv:label>
        </td>
        <td>
        <% if(asset.getContractId() != -1) {%>
            <%= toHtml(asset.getServiceContractNumber()) %>
          <%} else {%>
            <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
          <%}%>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label>
        </td>
        <td>
          <dhv:evaluate if="<%= assetContact.getId() > 0 %>">
            <dhv:permission name="contacts-external_contacts-view"><a href="javascript:popURL('ExternalContacts.do?command=ContactDetails&id=<%= assetContact.getId() %>&popup=true&popupType=inline','Details','650','500','yes','yes');"></dhv:permission><%= toHtml(assetContact.getNameLastFirst()) %><dhv:permission name="contacts-external_contacts-view"></a></dhv:permission>
          </dhv:evaluate>
          &nbsp;
        </td>
      </tr>
    </table>
    <br>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <tr>
        <th colspan="2">
          <strong><dhv:label name="accounts.accountasset_include.ServiceModelOptions">Service Model Options</dhv:label></strong>
        </th>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.ResponseTime">Response Time</dhv:label>
        </td>
        <td>
          <%= asset.getResponseTime() == -1 ? "Default - " + toHtml(responseModelList.getSelectedValue(serviceContract.getResponseTime())) : toHtml(responseModelList.getSelectedValue(asset.getResponseTime())) %>
        </td>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          <dhv:label name="accounts.accountasset_include.TelephoneService">Telephone Service</dhv:label>
        </td>
        <td>
          <%= asset.getTelephoneResponseModel() == -1 ? "Default - " + toHtml(phoneModelList.getSelectedValue(serviceContract.getTelephoneResponseModel())) : toHtml(phoneModelList.getSelectedValue(asset.getTelephoneResponseModel())) %>
        </td>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          <dhv:label name="accounts.accountasset_include.OnsiteService">Onsite Service</dhv:label>
        </td>
        <td>
        <%= asset.getOnsiteResponseModel() == -1 ? "Default - " + toHtml(onsiteModelList.getSelectedValue(serviceContract.getOnsiteResponseModel())) : toHtml(onsiteModelList.getSelectedValue(asset.getOnsiteResponseModel())) %>
        </td>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          <dhv:label name="accounts.accountasset_include.EmailServiceModel">Email Service Model</dhv:label>
        </td>
        <td>
        <%= asset.getEmailResponseModel() == -1 ? "Default - " + toHtml(emailModelList.getSelectedValue(serviceContract.getEmailResponseModel())) : toHtml(emailModelList.getSelectedValue(asset.getEmailResponseModel())) %>
        </td>
      </tr>
    </table>
    <br>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <tr>
        <th colspan="2">
          <strong><dhv:label name="accounts.accountasset_include.WarrantyInformation">Warranty Information</dhv:label></strong>
        </th>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label>
        </td>
        <td>
          <zeroio:tz timestamp="<%= asset.getExpirationDate() %>" dateOnly="true" timeZone="<%= asset.getExpirationDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
          <% if(!User.getTimeZone().equals(asset.getExpirationDateTimeZone())){%>
          <br />
          <zeroio:tz timestamp="<%= asset.getExpirationDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
          <% } %>
        </td>
      </tr>
      <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="accounts.accountasset_include.Inclusions">Inclusions</dhv:label>
        </td>
        <td>
          <%= toHtml(asset.getInclusions()) %>
        </td>
      </tr>
      <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="accounts.accountasset_include.Exclusions">Exclusions</dhv:label>
        </td>
        <td>
          <%= toHtml(asset.getExclusions()) %>
        </td>
      </tr>
    </table>
    <br>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <tr>
        <th colspan="2">
          <strong><dhv:label name="accounts.accountasset_include.FinancialInformation">Financial Information</dhv:label></strong>
        </th>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.PurchaseDate">Purchase Date</dhv:label>
        </td>
        <td>
          <zeroio:tz timestamp="<%=asset.getPurchaseDate()%>" dateOnly="true" timeZone="<%= asset.getPurchaseDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
          <% if(!User.getTimeZone().equals(asset.getPurchaseDateTimeZone())){%>
          <br />
          <zeroio:tz timestamp="<%= asset.getPurchaseDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
          <% } %>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.PurchaseCost">Purchase Cost</dhv:label>
        </td>
        <td>
          <zeroio:currency value="<%= asset.getPurchaseCost() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.PONumber">P.O. Number</dhv:label>
        </td>
        <td>
          <%= toHtml(asset.getPoNumber()) %>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.PurchasedFrom">Purchased From</dhv:label>
        </td>
        <td>
          <%= toHtml(asset.getPurchasedFrom()) %>
        </td>
      </tr>
    </table>
    <br>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <tr>
        <th colspan="2">
          <strong><dhv:label name="accounts.accountasset_include.OtherInformation">Other Information</dhv:label></strong>
        </th>
      </tr>
      <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
        </td>
        <td>
          <%= toHtml(asset.getNotes()) %>
        </td>
      </tr>
    </table>
    <%= addHiddenParams(request, "popup|popupType|actionId") %>
    <%-- end details --%>
    <br />
  <dhv:evaluate if="<%= !OrgDetails.isTrashed() || !asset.isTrashed() %>">
    <dhv:permission name="accounts-assets-edit"><input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>" /></dhv:permission>
    <dhv:permission name="accounts-assets-delete"><input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('AccountsAssets.do?command=ConfirmDelete&orgId=<%=OrgDetails.getOrgId()%>&id=<%=asset.getId()%>&popup=true','AccountsServiceContracts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>&id=<%=serviceContract.getId()%>', 'Delete_asset','320','200','yes','no');"></dhv:permission>
  </dhv:evaluate>
</dhv:container>
</dhv:container>
</form>

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
  <a href="Accounts.do?command=Search">Search Results</a> >
  <a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
  <a href="AccountsAssets.do?command=List&orgId=<%= OrgDetails.getOrgId() %>">Assets</a> >
  Asset Details
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="assets" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td class="containerBack">
      <% String param2 = "id=" + asset.getId(); %>
      [ <dhv:container name="accountsassets" selected="details" param="<%= param2 %>"/> ]
      <br /><br />
      <dhv:permission name="accounts-assets-edit"><input type=submit value="Modify"></dhv:permission>
      <dhv:permission name="accounts-assets-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('AccountsAssets.do?command=ConfirmDelete&orgId=<%=OrgDetails.getOrgId()%>&id=<%=asset.getId()%>&popup=true','AccountsServiceContracts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>&id=<%=serviceContract.getId()%>', 'Delete_asset','320','200','yes','no');"></dhv:permission>
      <input type=hidden name="orgId" value = <%= OrgDetails.getOrgId() %> >
      <input type=hidden name="id" value = <%= asset.getOrgId() %> >
      <br /><br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Specific Information</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Vendor
    </td>
    <td>
      <%= toHtml(asset.getVendor()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Manufacturer
    </td>
    <td>
      <%= toHtml(asset.getManufacturer()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Serial Number
    </td>
    <td>
      <%= toHtml(asset.getSerialNumber()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Model/Version
    </td>
    <td>
      <%= toHtml(asset.getModelVersion()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Description
    </td>
    <td>
      <%= toHtml(asset.getDescription()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Date Listed
    </td>
    <td>
      <zeroio:tz timestamp="<%= asset.getDateListed() %>" dateOnly="true" timeZone="<%= asset.getDateListedTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(asset.getDateListedTimeZone())){%>
      <br />
      <zeroio:tz timestamp="<%= asset.getDateListed() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Location
    </td>
    <td>
      <%= toHtml(asset.getLocation()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Asset Tag
    </td>
    <td>
      <%= toHtml(asset.getAssetTag()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Status
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
	    <strong>Category</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Level 1
    </td>
    <td>
      <dhv:evaluate if="<%= asset.getLevel1() > 0 %>">
        <%= toHtml(categoryList1.getSelectedValue(asset.getLevel1())) %>
      </dhv:evaluate>&nbsp;
    </td>
	</tr>
  <tr class="containerBody">
    <td class="formLabel">
      Level 2
    </td>
    <td>
      <dhv:evaluate if="<%= asset.getLevel2() > 0 %>">
        <%= toHtml(categoryList2.getSelectedValue(asset.getLevel2())) %>
      </dhv:evaluate>&nbsp;
    </td>
	</tr>
  <tr class="containerBody">
    <td class="formLabel">
      Level 3
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
	    <strong>Service Contract</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Service Contract Number
    </td>
    <td>
    <%= (asset.getContractId() != -1) ? toHtml(asset.getServiceContractNumber()) :"None Selected" %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Contact
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
      <strong>Service Model Options</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Response Time
    </td>
    <td>
      <%= asset.getResponseTime() == -1 ? "Default - " + toHtml(responseModelList.getSelectedValue(serviceContract.getResponseTime())) : toHtml(responseModelList.getSelectedValue(asset.getResponseTime())) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Telephone Service Model
    </td>
    <td>
      <%= asset.getTelephoneResponseModel() == -1 ? "Default - " + toHtml(phoneModelList.getSelectedValue(serviceContract.getTelephoneResponseModel())) : toHtml(phoneModelList.getSelectedValue(asset.getTelephoneResponseModel())) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Onsite Service Model
    </td>
    <td>
    <%= asset.getOnsiteResponseModel() == -1 ? "Default - " + toHtml(onsiteModelList.getSelectedValue(serviceContract.getOnsiteResponseModel())) : toHtml(onsiteModelList.getSelectedValue(asset.getOnsiteResponseModel())) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Email Service Model
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
      <strong>Warranty Information</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Expiration Date
    </td>
    <td>
      <zeroio:tz timestamp="<%= asset.getExpirationDate() %>" dateOnly="true" timeZone="<%= asset.getExpirationDateTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(asset.getExpirationDateTimeZone())){%>
      <br />
      <zeroio:tz timestamp="<%= asset.getExpirationDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Inclusions
    </td>
    <td>
      <%= toHtml(asset.getInclusions()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Exclusions
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
      <strong>Financial Information</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Purchase Date
    </td>
    <td>
      <zeroio:tz timestamp="<%=asset.getPurchaseDate()%>" dateOnly="true" timeZone="<%= asset.getPurchaseDateTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(asset.getPurchaseDateTimeZone())){%>
      <br />
      <zeroio:tz timestamp="<%= asset.getPurchaseDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Purchase Cost
    </td>
    <td>
      <zeroio:currency value="<%= asset.getPurchaseCost() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      P.O. Number
    </td>
    <td>
      <%= toHtml(asset.getPoNumber()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Purchased From
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
      <strong>Other Information</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Notes
    </td>
    <td>
      <%= toHtml(asset.getNotes()) %>
    </td>
  </tr>
</table>
<%= addHiddenParams(request, "popup|popupType|actionId") %>
<%-- end details --%>
<br />
  <dhv:permission name="accounts-assets-edit"><input type="submit" value="Modify" /></dhv:permission>
  <dhv:permission name="accounts-assets-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('AccountsAssets.do?command=ConfirmDelete&orgId=<%=OrgDetails.getOrgId()%>&id=<%=asset.getId()%>&popup=true','AccountsServiceContracts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>&id=<%=serviceContract.getId()%>', 'Delete_asset','320','200','yes','no');"></dhv:permission>
  </td>
  </tr>
</table>
</form>

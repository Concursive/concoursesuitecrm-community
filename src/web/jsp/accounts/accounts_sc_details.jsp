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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="OrgDetails"
             class="org.aspcfs.modules.accounts.base.Organization"
             scope="request"/>
<jsp:useBean id="serviceContract"
             class="org.aspcfs.modules.servicecontracts.base.ServiceContract"
             scope="request"/>
<jsp:useBean id="serviceContractContact"
             class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="serviceContractSubmitter"
             class="org.aspcfs.modules.accounts.base.Organization"
             scope="request"/>
<jsp:useBean id="serviceContractHoursHistory"
             class="org.aspcfs.modules.servicecontracts.base.ServiceContractHoursList"
             scope="request"/>
<jsp:useBean id="serviceContractProductList"
             class="org.aspcfs.modules.servicecontracts.base.ServiceContractProductList"
             scope="request"/>
<jsp:useBean id="serviceContractCategoryList"
             class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="serviceContractTypeList"
             class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="responseModelList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="phoneModelList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="onsiteModelList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="emailModelList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
             scope="session"/>
<jsp:useBean id="applicationPrefs"
             class="org.aspcfs.controller.ApplicationPrefs"
             scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/popURL.js"></script>
<form name="viewServiceContract"
      action="AccountsServiceContracts.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %>&id=<%=serviceContract.getId()%>&return=single<%= addLinkParams(request, "popup|popupType|actionId") %>"
      method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
  <%-- Trails --%>
  <table class="trails" cellspacing="0">
    <tr>
      <td width="100%">
        <a href="Accounts.do"><dhv:label
            name="accounts.accounts">Accounts</dhv:label></a> >
        <a href="Accounts.do?command=Search"><dhv:label
            name="accounts.SearchResults">Search Results</dhv:label></a> >
        <a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label
            name="accounts.details">Account Details</dhv:label></a> >
        <a href="AccountsServiceContracts.do?command=List&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label
            name="accounts.accounts_sc_add.ServiceContracts">Service
          Contracts</dhv:label></a> >
        <dhv:label name="accounts.accounts_sc_add.ContractDetails">Contract
          Details</dhv:label>
      </td>
    </tr>
  </table>
  <%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="servicecontracts" object="OrgDetails"
               param='<%= "orgId=" + OrgDetails.getOrgId() %>'
               appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<dhv:evaluate
    if="<%= !OrgDetails.isTrashed() || !serviceContract.isTrashed() %>">
  <dhv:permission name="accounts-service-contracts-edit">
    <input type="submit"
           value="<dhv:label name="global.button.modify">Modify</dhv:label>"/>
  </dhv:permission>
  <dhv:permission name="accounts-service-contracts-delete">
    <input type="button"
           value="<dhv:label name="global.button.delete">Delete</dhv:label>"
           onClick="javascript:popURLReturn('AccountsServiceContracts.do?command=ConfirmDelete&orgId=<%=OrgDetails.getOrgId()%>&id=<%=serviceContract.getId()%>&popup=true<%= isPopup(request)?"&popupType=inline":"" %>','AccountsServiceContracts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>&id=<%=serviceContract.getId()%>', 'Delete_servicecontract','320','200','yes','no');"/>
  </dhv:permission>
</dhv:evaluate>
<input type="hidden" name="orgId" value=<%= OrgDetails.getOrgId() %>/>
<input type="hidden" name="id" value=<%= serviceContract.getId() %>/>
<br/>
<br/>
<%-- Start details --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<tr>
  <th colspan="2">
    <strong><dhv:label name="documents.details.generalInformation">General
      Information</dhv:label></strong>
  </th>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="accounts.accountasset_include.ServiceContractNumber">Service
      Contract Number</dhv:label>
  </td>
  <td>
    <%= toHtml(serviceContract.getServiceContractNumber()) %>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="account.sc.contractValue">Contract Value</dhv:label>
  </td>
  <td>
    <zeroio:currency value="<%= serviceContract.getContractValue() %>"
                     code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>'
                     locale="<%= User.getLocale() %>" default="&nbsp;"/>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="account.sc.reseller">Reseller</dhv:label>
  </td>
  <td>
    <% if (User.getUserRecord().isPortalUser()) {%>
    <%= toHtml(serviceContractSubmitter.getName()) %>
    <%} else { %>
    <a href="Accounts.do?command=Details&orgId=<%= serviceContractSubmitter.getId()%>"><%= toHtml(serviceContractSubmitter.getName()) %>
    </a>
    <%} %>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="account.sc.initialContractDate">Initial Contract
      Date</dhv:label>
  </td>
  <td>
    <zeroio:tz timestamp="<%=serviceContract.getInitialStartDate()%>"
               dateOnly="true"
               timeZone="<%=serviceContract.getInitialStartDateTimeZone()%>"
               showTimeZone="true" default="&nbsp;"/>
    <% if (!User.getTimeZone().equals(serviceContract.getInitialStartDateTimeZone())) {%>
    <br/>
    <zeroio:tz timestamp="<%= serviceContract.getInitialStartDate() %>"
               timeZone="<%= User.getTimeZone() %>" showTimeZone="true"
               default="&nbsp;"/>
    <% } %>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="account.sc.currentContractDate">Current Contract
      Date</dhv:label>
  </td>
  <td>
    <zeroio:tz timestamp="<%=serviceContract.getCurrentStartDate()%>"
               dateOnly="true"
               timeZone="<%=serviceContract.getCurrentStartDateTimeZone()%>"
               showTimeZone="true" default="&nbsp;"/>
    <% if (!User.getTimeZone().equals(serviceContract.getCurrentStartDateTimeZone())) {%>
    <br/>
    <zeroio:tz timestamp="<%= serviceContract.getCurrentStartDate() %>"
               timeZone="<%= User.getTimeZone() %>" showTimeZone="true"
               default="&nbsp;"/>
    <% } %>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="account.sc.currentEndDate">Current End Date</dhv:label>
  </td>
  <td>
    <zeroio:tz timestamp="<%= serviceContract.getCurrentEndDate() %>"
               dateOnly="true"
               timeZone="<%=serviceContract.getCurrentEndDateTimeZone()%>"
               showTimeZone="true" default="&nbsp;"/>
    <% if (!User.getTimeZone().equals(serviceContract.getCurrentEndDateTimeZone())) {%>
    <br/>
    <zeroio:tz timestamp="<%= serviceContract.getCurrentEndDate() %>"
               timeZone="<%= User.getTimeZone() %>" showTimeZone="true"
               default="&nbsp;"/>
    <% } %>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label
        name="accounts.accountasset_include.Category">Category</dhv:label>
  </td>
  <td>
    <dhv:evaluate if="<%= serviceContract.getCategory() > 0 %>">
      <%= toHtml(serviceContractCategoryList.getSelectedValue(serviceContract.getCategory())) %>
    </dhv:evaluate>&nbsp;
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
  </td>
  <td>
    <dhv:evaluate if="<%= serviceContract.getType() > 0 %>">
      <%= toHtml(serviceContractTypeList.getSelectedValue(serviceContract.getType())) %>
    </dhv:evaluate>&nbsp;
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="account.sc.laborCategories">Labor Categories</dhv:label>
  </td>
  <td>
    <dhv:evaluate if="<%= serviceContractProductList.size() > 0 %>">
      <%

 Iterator itr = serviceContractProductList.iterator();
 int count = 0;
 while (itr.hasNext()){
   ServiceContractProduct scp = (ServiceContractProduct)itr.next();
   if (count != 0){
      %>
      , &nbsp;
      <%}%>
      <%=toHtml(scp.getProductName())%>
      <%

         count++;
       }

      %>
    </dhv:evaluate>&nbsp;
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label>
  </td>
  <td>
    <dhv:evaluate if="<%= serviceContract.getContactId() > -1 %>">
      <dhv:permission name="contacts-external_contacts-view"><a
        href="javascript:popURL('ExternalContacts.do?command=ContactDetails&id=<%= serviceContractContact.getId() %>
        &popup=true&popupType=inline','Details','650','500','yes','yes');"></dhv:permission><%= toHtml(serviceContractContact.getNameLastFirst()) %><dhv:permission
        name="contacts-external_contacts-view"></a></dhv:permission>
    </dhv:evaluate>
    &nbsp;
  </td>
</tr>
<tr class="containerBody">
  <td valign="top" class="formLabel">
    <dhv:label
        name="accounts.accountasset_include.Description">Description</dhv:label>
  </td>
  <td>
    <%= toHtml(serviceContract.getDescription()) %>
  </td>
</tr>
<tr class="containerBody">
  <td valign="top" class="formLabel">
    <dhv:label name="account.sc.billingNotes">Billing Notes</dhv:label>
  </td>
  <td>
    <%= toHtml(serviceContract.getContractBillingNotes()) %>
  </td>
</tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="account.sc.blockHourInformation">Block Hour
        Information</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.totalHoursRemaining">Total Hours
        Remaining</dhv:label>
    </td>
    <td>
      <%= serviceContract.getTotalHoursRemaining() %>
      <dhv:evaluate if="<%= serviceContractHoursHistory.size() > 0 %>">
        [<a
        href="javascript:popURL('AccountsServiceContracts.do?command=HoursHistory&id=<%= serviceContract.getId() %>
        &popup=true&popupType=inline','Details','650','500','yes','yes');"><dhv:label
          name="accountsassets.history.long_html">History</dhv:label></a>]
      </dhv:evaluate>&nbsp;
    </td>
  </tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label
          name="accounts.accountasset_include.ServiceModelOptions">Service Model
        Options</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.ResponseTime">Response
        Time</dhv:label>
    </td>
    <td>
      <%= toHtml(responseModelList.getSelectedValue(serviceContract.getResponseTime())) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.TelephoneService">Telephone
        Service</dhv:label>
    </td>
    <td>
      <%= toHtml(phoneModelList.getSelectedValue(serviceContract.getTelephoneResponseModel())) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.OnsiteService">Onsite
        Service</dhv:label>
    </td>
    <td>
      <%= toHtml(onsiteModelList.getSelectedValue(serviceContract.getOnsiteResponseModel())) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="account.sc.emailSercive">Email Service</dhv:label>
    </td>
    <td>
      <%= toHtml(emailModelList.getSelectedValue(serviceContract.getEmailResponseModel())) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="account.sc.serviceModelNotes">Service Model
        Notes</dhv:label>
    </td>
    <td>
      <%= toHtml(serviceContract.getServiceModelNotes()) %>
    </td>
  </tr>
</table>
<%= addHiddenParams(request, "popup|popupType|actionId") %>
<%-- End Details --%>
<br/>
<dhv:evaluate
    if="<%= !OrgDetails.isTrashed() || !serviceContract.isTrashed() %>">
  <dhv:permission name="accounts-service-contracts-edit">
    <input type="submit"
           value="<dhv:label name="global.button.modify">Modify</dhv:label>"/>
  </dhv:permission>
  <dhv:permission name="accounts-service-contracts-delete">
    <input type="button"
           value="<dhv:label name="global.button.delete">Delete</dhv:label>"
           onClick="javascript:popURLReturn('AccountsServiceContracts.do?command=ConfirmDelete&orgId=<%=OrgDetails.getOrgId()%>&id=<%=serviceContract.getId()%>&popup=true<%= isPopup(request)?"&popupType=inline":"" %>','AccountsServiceContracts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>&id=<%=serviceContract.getId()%>', 'Delete_servicecontract','320','200','yes','no');"/>
  </dhv:permission>
</dhv:evaluate>
</dhv:container>
</form>

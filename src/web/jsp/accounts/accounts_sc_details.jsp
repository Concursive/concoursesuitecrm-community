<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,java.text.DateFormat" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="serviceContract" class="org.aspcfs.modules.servicecontracts.base.ServiceContract" scope="request"/>
<jsp:useBean id="serviceContractContact" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="serviceContractHoursHistory" class="org.aspcfs.modules.servicecontracts.base.ServiceContractHoursList" scope="request"/>
<jsp:useBean id="serviceContractProductList" class="org.aspcfs.modules.servicecontracts.base.ServiceContractProductList" scope="request"/>
<jsp:useBean id="serviceContractCategoryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="serviceContractTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="responseModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="phoneModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="onsiteModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="emailModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<form name="viewServiceContract" action="AccountsServiceContracts.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %>&id=<%=serviceContract.getId()%>&return=single" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
  <a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
  <a href="Accounts.do?command=Search">Search Results</a> >
  <a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
  <a href="AccountsServiceContracts.do?command=List&orgId=<%= OrgDetails.getOrgId() %>">Service Contracts</a> >
  Contract Details
</td>
</tr>
</table>
<%-- End Trails --%>
  <%@ include file="accounts_details_header_include.jsp" %>
    <dhv:container name="accounts" selected="servicecontracts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td class="containerBack">
      <dhv:permission name="accounts-service-contracts-edit">
      <input type=submit value="Modify" />
      </dhv:permission>
      <dhv:permission name="accounts-service-contracts-delete">
      <input type="button" value="Delete" onClick="javascript:popURLReturn('AccountsServiceContracts.do?command=ConfirmDelete&orgId=<%=OrgDetails.getOrgId()%>&id=<%=serviceContract.getId()%>&popup=true','AccountsServiceContracts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>&id=<%=serviceContract.getId()%>', 'Delete_servicecontract','320','200','yes','no');" />
      </dhv:permission>
      <input type="hidden" name="orgId" value = <%= OrgDetails.getOrgId() %> />
      <input type="hidden" name="id" value = <%= serviceContract.getId() %> />
      <br /> <br />
<%-- Start details --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>General Information</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Service Contract Number
    </td>
    <td>
      <%= toHtml(serviceContract.getServiceContractNumber()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
    Contract Value
    </td>
    <td>
      <zeroio:currency value="<%= serviceContract.getContractValue() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
    </td>
  </tr>
   <tr class="containerBody">
    <td class="formLabel">
      Initial Contract Date
    </td>
    <td>
      <zeroio:tz timestamp="<%=serviceContract.getInitialStartDate()%>" dateOnly="true" timeZone="<%=serviceContract.getInitialStartDateTimeZone()%>" showTimeZone="yes" default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(serviceContract.getInitialStartDateTimeZone())){%>
      <br />
      <zeroio:tz timestamp="<%= serviceContract.getInitialStartDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Current Contract Date
    </td>
    <td>
      <zeroio:tz timestamp="<%=serviceContract.getCurrentStartDate()%>" dateOnly="true" timeZone="<%=serviceContract.getCurrentStartDateTimeZone()%>" showTimeZone="yes" default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(serviceContract.getCurrentStartDateTimeZone())){%>
      <br />
      <zeroio:tz timestamp="<%= serviceContract.getCurrentStartDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Current End Date
    </td>
    <td>
      <zeroio:tz timestamp="<%= serviceContract.getCurrentEndDate() %>" dateOnly="true" timeZone="<%=serviceContract.getCurrentEndDateTimeZone()%>" showTimeZone="yes" default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(serviceContract.getCurrentEndDateTimeZone())){%>
      <br />
      <zeroio:tz timestamp="<%= serviceContract.getCurrentEndDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Category
    </td>
    <td>
      <dhv:evaluate if="<%= serviceContract.getCategory() > 0 %>">
        <%= toHtml(serviceContractCategoryList.getSelectedValue(serviceContract.getCategory())) %>
      </dhv:evaluate>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Type
    </td>
    <td>
      <dhv:evaluate if="<%= serviceContract.getType() > 0 %>">
        <%= toHtml(serviceContractTypeList.getSelectedValue(serviceContract.getType())) %>
      </dhv:evaluate>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel">
    Labor Categories
  </td>
  <td>
      <dhv:evaluate if="<%= serviceContractProductList.size() > 0 %>">
      <%
        Iterator itr = serviceContractProductList.iterator();
        int count = 0;
        while (itr.hasNext()){
          ServiceContractProduct scp = (ServiceContractProduct)itr.next();
          if (count != 0){%>
            , &nbsp;
          <%}%>
        <%=toHtml(scp.getProductSku())%>
       <%
          count++;
        }
       %>
      </dhv:evaluate>&nbsp;
  </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Contact
    </td>
    <td>
      <dhv:evaluate if="<%= serviceContract.getContactId() > -1 %>">
        <dhv:permission name="contacts-external_contacts-view"><a href="javascript:popURL('ExternalContacts.do?command=ContactDetails&id=<%= serviceContractContact.getId() %>&popup=true&popupType=inline','Details','650','500','yes','yes');"></dhv:permission><%= toHtml(serviceContractContact.getNameLastFirst()) %><dhv:permission name="contacts-external_contacts-view"></a></dhv:permission>
      </dhv:evaluate>
      &nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Description
    </td>
    <td>
      <%= toHtml(serviceContract.getDescription()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Billing Notes
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
	    <strong>Block Hour Information</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
    Total Hours Remaining
    </td>
    <td>
      <%= serviceContract.getTotalHoursRemaining() %>
      <dhv:evaluate if="<%= serviceContractHoursHistory.size() > 0 %>">
      [<a href="javascript:popURL('AccountsServiceContracts.do?command=HoursHistory&id=<%= serviceContract.getId() %>&popup=true&popupType=inline','Details','650','500','yes','yes');">History</a>]
      </dhv:evaluate>&nbsp;
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
    <%= toHtml(responseModelList.getSelectedValue(serviceContract.getResponseTime())) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Telephone Service
    </td>
    <td>
    <%= toHtml(phoneModelList.getSelectedValue(serviceContract.getTelephoneResponseModel())) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Onsite Service
    </td>
    <td>
    <%= toHtml(onsiteModelList.getSelectedValue(serviceContract.getOnsiteResponseModel())) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Email Service
    </td>
    <td>
    <%= toHtml(emailModelList.getSelectedValue(serviceContract.getEmailResponseModel())) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Service Model Notes
    </td>
    <td>
    <%= toHtml(serviceContract.getServiceModelNotes()) %>
    </td>
  </tr>
</table>
<%= addHiddenParams(request, "popup|popupType|actionId") %>
<%-- End Details --%>
<br />
  <dhv:permission name="accounts-service-contracts-edit">
  <input type="submit" value="Modify" />
  </dhv:permission>
  <dhv:permission name="accounts-service-contracts-delete">
  <input type="button" value="Delete" onClick="javascript:popURLReturn('AccountsServiceContracts.do?command=ConfirmDelete&orgId=<%=OrgDetails.getOrgId()%>&id=<%=serviceContract.getId()%>&popup=true','AccountsServiceContracts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>&id=<%=serviceContract.getId()%>', 'Delete_servicecontract','320','200','yes','no');" />
  </dhv:permission>
  </td>
  </tr>
</table>
</form>

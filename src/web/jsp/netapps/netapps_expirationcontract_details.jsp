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
<jsp:useBean id="contractExpiration" class="org.aspcfs.modules.netapps.base.ContractExpiration" scope="request"/>
<jsp:useBean id="ImportDetails" class="org.aspcfs.modules.base.Import" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*" %>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript">
  function confirmDeleteContract() {
    url = '';
    <%if (contractExpiration.isApproved()){%>
      url = 'NetworkApplications.do?command=Delete&expirationId=<%= contractExpiration.getId() %>&return=<%=request.getParameter("return")%>';
    <%}else{%>
      url = 'NetworkApplicationsImports.do?command=DeleteContractExpiration&expirationId=<%= contractExpiration.getId() %>&importId=<%= contractExpiration.getImportId() %>&return=<%=request.getParameter("return")%>';
    <%}%>
    confirmDelete(url);
  }
</script>
<form name="viewContractExpiration" action="NetworkApplications.do?command=Modify&expirationId=<%= contractExpiration.getId() %>&return=single" method="post">
<table class="trails" cellspacing="0">
<tr>
  <td>
    <a href="NetworkApplications.do">NetApp</a> >
    <% if (contractExpiration.isApproved()){%>
        <a href="NetworkApplications.do?command=Search">Contract Expiration List</a> >
    <%}else{%>
        <a href="NetworkApplicationsImports.do?command=View">View Imports</a> >
        <a href="NetworkApplicationsImports.do?command=Details&importId=<%= contractExpiration.getImportId() %>">Import Details</a> >
        <a href="NetworkApplicationsImports.do?command=ViewResults&importId=<%= contractExpiration.getImportId() %>">View Results</a> >
    <%}%>
    Contract Expiration Details
  </td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="netapps" selected="details" param="<%= "expirationId=" + contractExpiration.getId() %>" style="tabs"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td class="containerBack">
<dhv:permission name="netapps_expiration_contracts-edit">
  <dhv:evaluate if="<%= contractExpiration.isApproved()%>">
    <input type="submit" value="Modify" />
  </dhv:evaluate>
</dhv:permission>
<dhv:permission name="netapps_expiration_contracts_imports-delete,netapps_expiration_contracts-delete">
  <input type="button" value="Delete" onClick="javascript:confirmDeleteContract();">
</dhv:permission>
<br /><br />
<dhv:formMessage showSpace="false" />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>CSV Details</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Serial Number</td>
    <td><%= toHtml(contractExpiration.getSerialNumber()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Agreement Number</td>
    <td><%= toHtml(contractExpiration.getAgreementNumber()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Services</td>
    <td><%= toHtml(contractExpiration.getServices()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Start Date</td>
    <td><zeroio:tz timestamp="<%= contractExpiration.getStartDate()  %>" dateOnly="true" default="&nbsp" /></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>End Date</td>
    <td><zeroio:tz timestamp="<%= contractExpiration.getEndDate()  %>" dateOnly="true" default="&nbsp" /></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Installed-at Company Name</td>
    <td><%= toHtml(contractExpiration.getInstalledAtCompanyName()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Installed at Site Name</td>
    <td><%= toHtml(contractExpiration.getInstalledAtSiteName()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Group</td>
    <td><%= toHtml(contractExpiration.getGroupName()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Product Number</td>
    <td><%= toHtml(contractExpiration.getProductNumber()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>OS</td>
    <td><%= toHtml(contractExpiration.getOperatingSystem()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap># of Shelves</td>
    <td>
      <dhv:evaluate if="<%= contractExpiration.getNoOfShelves() > -1 %>">
      <%= contractExpiration.getNoOfShelves() %>
      </dhv:evaluate>&nbsp;
   </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap># of Disks</td>
    <td>
      <dhv:evaluate if="<%= contractExpiration.getNoOfDisks() > -1 %>">
        <%= contractExpiration.getNoOfDisks() %>
      </dhv:evaluate>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>NVRAM</td>
    <td>
      <dhv:evaluate if="<%= contractExpiration.getNvram() > -1 %>">
        <%= contractExpiration.getNvram() %>
      </dhv:evaluate>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Memory</td>
    <td>
      <dhv:evaluate if="<%= contractExpiration.getMemory() > -1 %>">
        <%= contractExpiration.getMemory() %>
      </dhv:evaluate>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Autosupport Status</td>
    <td><%= toHtml(contractExpiration.getAutosupportStatus()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Installed-at Address</td>
    <td><%= toHtml(contractExpiration.getInstalledAtAddress()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>City</td>
    <td><%= toHtml(contractExpiration.getCity()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>State/Province</td>
    <td><%= toHtml(contractExpiration.getStateProvince()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Postal Code</td>
    <td><%= toHtml(contractExpiration.getPostalCode()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Country</td>
    <td><%= toHtml(contractExpiration.getCountry()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Installed-at Contact First Name</td>
    <td><%= toHtml(contractExpiration.getInstalledAtContactFirstName()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Contact Last Name</td>
    <td><%= toHtml(contractExpiration.getContactLastName()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Contact Email</td>
    <td><%= toHtml(contractExpiration.getContactEmail()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Agreement Company</td>
    <td><%= toHtml(contractExpiration.getAgreementCompany()) %></td>
  </tr>
</table>
<%--
<dhv:evaluate if="<%= contractExpiration.isApproved() %>">
  &nbsp;
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong>Quote Information</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>Quote Amount</td>
      <td>
        <zeroio:currency value="<%= contractExpiration.getQuoteAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>Quote Generated</td>
      <td><zeroio:tz timestamp="<%= contractExpiration.getQuoteGeneratedDate()  %>" dateOnly="true" default="&nbsp" /></td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>Quote Accepted</td>
      <td><zeroio:tz timestamp="<%= contractExpiration.getQuoteAcceptedDate()  %>" dateOnly="true" default="&nbsp" /></td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>Quote Rejected</td>
      <td><zeroio:tz timestamp="<%= contractExpiration.getQuoteRejectedDate()  %>" dateOnly="true" default="&nbsp" /></td>
    </tr>
  </table>
</dhv:evaluate>
--%>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Record Information</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Entered
    </td>
    <td>
      <dhv:username id="<%= contractExpiration.getEnteredBy() %>"/>
      <zeroio:tz timestamp="<%= contractExpiration.getEntered()  %>" />
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Modified
    </td>
    <td>
      <dhv:username id="<%= contractExpiration.getModifiedBy() %>"/>
      <zeroio:tz timestamp="<%= contractExpiration.getModified()  %>" />
    </td>
   </tr>
  </table>
<br />
<dhv:permission name="netapps_expiration_contracts-edit">
  <dhv:evaluate if="<%= contractExpiration.isApproved()%>">
    <input type="submit" value="Modify" />
  </dhv:evaluate>
</dhv:permission>
<dhv:permission name="netapps_expiration_contracts_imports-delete,netapps_expiration_contracts-delete">
  <input type="button" value="Delete" onClick="javascript:confirmDeleteContract();">
</dhv:permission>
</td>
  </tr>
</table>
</form>

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
<jsp:useBean id="myCompany"
             class="org.aspcfs.modules.accounts.base.Organization"
             scope="request"/>
<jsp:useBean id="hooks" class="java.lang.String" scope="request"/>
<jsp:useBean id="clients" class="java.lang.String" scope="request"/>
<jsp:useBean id="processes" class="java.lang.String" scope="request"/>
<jsp:useBean id="Timeout" class="java.lang.String" scope="request"/>
<jsp:useBean id="APP_TEXT" class="java.lang.String" scope="application"/>
<jsp:useBean id="merchantPaymentGateway" class="org.aspcfs.modules.admin.base.MerchantPaymentGateway" scope="request"/>
<jsp:useBean id="countrySelect" class="org.aspcfs.utils.web.CountrySelect"
             scope="request"/>
<%@ page import="org.aspcfs.utils.web.HtmlSelectCurrency" %>
<%@ page import="org.aspcfs.utils.web.HtmlSelectLanguage" %>
<%@ page import="org.aspcfs.utils.web.HtmlSelectTimeZone" %>
<%@ page import="org.aspcfs.modules.admin.base.MerchantPaymentGateway" %>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
      <dhv:label name="admin.configureSystem">Configure System</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<% int count = 1; %>
<dhv:permission name="admin-sysconfig-view">
<table cellpadding="4" cellspacing="0" border="0" width="100%"
       class="pagedList">
<tr>
  <dhv:permission name="admin-sysconfig-edit">
    <th width="8">
      &nbsp;
    </th>
  </dhv:permission>
  <th>
    <strong><dhv:label name="admin.parameter">Parameter</dhv:label></strong>
  </th>
  <th>
    <strong><dhv:label name="admin.currentValue">Current
      Value</dhv:label></strong>
  </th>
</tr>
<tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
  <dhv:permission name="admin-sysconfig-edit">
    <td align="center">
      <a href="AdminConfig.do?command=Modify&param=COMPANYINFO"><dhv:label
          name="accounts.accounts_contacts_oppcomponent_list.Edit">
        Edit</dhv:label></a>
    </td>
  </dhv:permission>
  <td>
    <dhv:label name="admin.companyInformation">Company Information</dhv:label>
  </td>
  <td>
    <%= toHtml(myCompany.getName()) %>
  </td>
</tr>
<tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
  <td align="center">
    <a href="AdminConfig.do?command=Modify&param=WORKFLOW"><dhv:label
        name="accounts.accounts_contacts_oppcomponent_list.Edit">
      Edit</dhv:label></a>
  </td>
  <td>
    <dhv:label name="admin.config.businessProcessManagement">Business Process
      Management</dhv:label>
  </td>
  <td>
    <dhv:label name="admin.config.numberOfEvents.text"
               param="<%= "eventNumber="+hooks %>"><%= hooks %> Object Events
    </dhv:label> / <dhv:label name="admin.config.numberOfProcesses.text"
                              param="<%= "processNumber="+processes %>"><%= processes %>
    Business Processes</dhv:label>
  </td>
</tr>
<tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
  <td align="center">
    <a href="AdminClientManager.do?command=ShowClients"><dhv:label
        name="accounts.accounts_contacts_oppcomponent_list.Edit">
      Edit</dhv:label></a>
  </td>
  <td>
    <dhv:label name="admin.hTTP-XMLClientManager">HTTP-XML API Client
      Manager</dhv:label>
  </td>
  <td>
    <dhv:label name="admin.config.httpXmlApiClients.value" param="<%= "clients="+clients %>">Total # of enabled clients <%= clients %></dhv:label>
  </td>
</tr>
<dhv:evaluate
    if="<%= getPref(getServletConfig().getServletContext(), "WEBSERVER.ASPMODE") == null || !"true".equals(getPref(getServletConfig().getServletContext(), "WEBSERVER.ASPMODE")) %>">
<dhv:evaluate if="<%= hasText(APP_TEXT) %>">
  <tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
    <dhv:permission name="admin-sysconfig-edit">
      <td align="center">
        <a href="AdminConfig.do?command=Modify&param=LICENSE"><dhv:label
            name="accounts.accounts_contacts_oppcomponent_list.Edit">
          Edit</dhv:label></a>
      </td>
    </dhv:permission>
    <td>
      <dhv:label name="admin.licenseInfo.text">License Information and
        Details</dhv:label>
    </td>
    <td>
      <%= toHtml(APP_TEXT) %>
    </td>
  </tr>
</dhv:evaluate>
<tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
  <dhv:permission name="admin-sysconfig-edit">
    <td align="center">
      <a href="AdminConfig.do?command=Modify&param=MAILSERVER"><dhv:label
          name="accounts.accounts_contacts_oppcomponent_list.Edit">
        Edit</dhv:label></a>
    </td>
  </dhv:permission>
  <td>
    <dhv:label name="admin.emailServer">Email Server</dhv:label>
  </td>
  <td>
    <%= toHtml(getPref(getServletConfig().getServletContext(), "MAILSERVER")) %>
  </td>
</tr>
<tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
  <dhv:permission name="admin-sysconfig-edit">
    <td align="center">
      <a href="AdminConfig.do?command=Modify&param=EMAILADDRESS"><dhv:label
          name="accounts.accounts_contacts_oppcomponent_list.Edit">
        Edit</dhv:label></a>
    </td>
  </dhv:permission>
  <td>
    <dhv:label name="documents.team.emailAddress">Email Address</dhv:label>
  </td>
  <td>
    <%= toHtml(getPref(getServletConfig().getServletContext(), "EMAILADDRESS")) %>
  </td>
</tr>
<tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
  <dhv:permission name="admin-sysconfig-edit">
    <td align="center">
      <a href="AdminConfig.do?command=Modify&param=WEBSERVER.URL"><dhv:label
          name="accounts.accounts_contacts_oppcomponent_list.Edit">
        Edit</dhv:label></a>
    </td>
  </dhv:permission>
  <td>
    <dhv:label name="admin.centricCrmUrl">Centric CRM URL</dhv:label>
  </td>
  <td>
    <%= toHtml(getPref(getServletConfig().getServletContext(), "WEBSERVER.URL")) %>
  </td>
</tr>
<tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
  <dhv:permission name="admin-sysconfig-edit">
    <td align="center">
      <a href="AdminConfig.do?command=Modify&param=FAXSERVER"><dhv:label
          name="accounts.accounts_contacts_oppcomponent_list.Edit">
        Edit</dhv:label></a>
    </td>
  </dhv:permission>
  <td>
    <dhv:label name="admin.faxServer">Fax Server</dhv:label>
  </td>
  <td>
    <%= toHtml(getPref(getServletConfig().getServletContext(), "FAXSERVER")) %>
  </td>
</tr>
<tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
  <dhv:permission name="admin-sysconfig-edit">
    <td align="center">
      <a href="AdminConfig.do?command=Modify&param=ASTERISKSERVER"><dhv:label
          name="accounts.accounts_contacts_oppcomponent_list.Edit">
        Edit</dhv:label></a>
    </td>
  </dhv:permission>
  <td>
    <dhv:label name="setup.asterisk">Asterisk Server</dhv:label>
  </td>
  <td>
    <%= toHtml(getPref(getServletConfig().getServletContext(), "ASTERISK.URL")) %>
    <%= "true".equals(getPref(getServletConfig().getServletContext(), "ASTERISK.OUTBOUND.ENABLED")) ? "Outbound" : "" %>
    <%= "true".equals(getPref(getServletConfig().getServletContext(), "ASTERISK.INBOUND.ENABLED")) ? "Inbound" : "" %>
  </td>
</tr>
<tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
  <dhv:permission name="admin-sysconfig-edit">
    <td align="center">
      <a href="AdminConfig.do?command=Modify&param=XMPPSERVER"><dhv:label
          name="accounts.accounts_contacts_oppcomponent_list.Edit">
        Edit</dhv:label></a>
    </td>
  </dhv:permission>
  <td>
    <dhv:label name="setup.xmpp">XMPP Server</dhv:label>
  </td>
  <td>
    <%= toHtml(getPref(getServletConfig().getServletContext(), "XMPP.CONNECTION.URL")) %>
    <%= "true".equals(getPref(getServletConfig().getServletContext(), "XMPP.ENABLED")) ? "Enabled" : "Disabled" %>
  </td>
</tr>
<tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
  <dhv:permission name="admin-sysconfig-edit">
    <td align="center">
      <a href="AdminConfig.do?command=Modify&param=LDAPSERVER"><dhv:label
          name="accounts.accounts_contacts_oppcomponent_list.Edit">
        Edit</dhv:label></a>
    </td>
  </dhv:permission>
  <td>
    <dhv:label name="setup.ldap">LDAP Server</dhv:label>
  </td>
  <td>
    <%= toHtml(getPref(getServletConfig().getServletContext(), "LDAP.SERVER")) %>
    <%= "true".equals(getPref(getServletConfig().getServletContext(), "LDAP.ENABLED")) ? "Enabled" : "Disabled" %>
  </td>
</tr>
<tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
  <dhv:permission name="admin-sysconfig-edit">
    <td align="center">
      <a href="AdminConfig.do?command=Modify&param=SYSTEM.TIMEZONE"><dhv:label
          name="accounts.accounts_contacts_oppcomponent_list.Edit">
        Edit</dhv:label></a>
    </td>
  </dhv:permission>
  <td>
    <dhv:label name="admin.defaultTimeZone.text">Default Time Zone for new
      users</dhv:label>
  </td>
  <td>
    <%= HtmlSelectTimeZone.getSelect("timeZone", getPref(getServletConfig().getServletContext(), "SYSTEM.TIMEZONE")).getValueFromId(getPref(getServletConfig().getServletContext(), "SYSTEM.TIMEZONE")) %>
  </td>
</tr>
<tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
  <dhv:permission name="admin-sysconfig-edit">
    <td align="center">
      <a href="AdminConfig.do?command=Modify&param=SYSTEM.CURRENCY"><dhv:label
          name="accounts.accounts_contacts_oppcomponent_list.Edit">
        Edit</dhv:label></a>
    </td>
  </dhv:permission>
  <td>
    <dhv:label name="admin.defaultCurrenty.text">Default currency for
      system</dhv:label>
  </td>
  <td>
    <%= HtmlSelectCurrency.getSelect("currency", getPref(getServletConfig().getServletContext(), "SYSTEM.CURRENCY")).getValueFromId(getPref(getServletConfig().getServletContext(), "SYSTEM.CURRENCY")) %>
  </td>
</tr>
<tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
  <dhv:permission name="admin-sysconfig-edit">
    <td align="center">
      <a href="AdminConfig.do?command=Modify&param=SYSTEM.LANGUAGE"><dhv:label
          name="accounts.accounts_contacts_oppcomponent_list.Edit">
        Edit</dhv:label></a>
    </td>
  </dhv:permission>
  <td>
    <dhv:label name="admin.defaultLanguage.text">Default language/locale for
      system</dhv:label>
  </td>
  <td>
    <%= HtmlSelectLanguage.getSelect("language", getPref(getServletConfig().getServletContext(), "SYSTEM.LANGUAGE")).getValueFromId(getPref(getServletConfig().getServletContext(), "SYSTEM.LANGUAGE")) %>
  </td>
</tr>
<tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
  <dhv:permission name="admin-sysconfig-edit">
    <td align="center">
      <a href="AdminConfig.do?command=Modify&param=SYSTEM.COUNTRY"><dhv:label
          name="accounts.accounts_contacts_oppcomponent_list.Edit">
        Edit</dhv:label></a>
    </td>
  </dhv:permission>
  <td>
    <dhv:label name="admin.defaultCountry.text">Default country for
      system</dhv:label>
  </td>
  <td>
    <%= countrySelect.getValueFromId(getPref(getServletConfig().getServletContext(), "SYSTEM.COUNTRY")) %>
  </td>
</tr>
<tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
  <dhv:permission name="admin-sysconfig-edit">
    <td align="center">
      <a href="AdminConfig.do?command=Modify&param=PAYMENTGATEWAY"><dhv:label
          name="accounts.accounts_contacts_oppcomponent_list.Edit">
        Edit</dhv:label></a>
    </td>
  </dhv:permission>
  <td>
    <dhv:label name="admin.PaymentGateway">Payment Gateway</dhv:label>
  </td>
  <td>
    <%=toHtml(merchantPaymentGateway.getGatewayName())%>
  </td>
</tr>
<%--
<tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
  <td align="center">
    <a href="AdminConfig.do?command=ModifyTimeout&timeout=<%= Timeout %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.Edit">Edit</dhv:label></a>
  </td>
  <td>
     <dhv:label name="admin.webSessionTimeout.text">Web Session Timeout (temporary)</dhv:label>
  </td>
  <td>
     <%= Timeout %> <dhv:label name="admin.minutes">minutes</dhv:label>
  </td>
</tr>
--%>
</dhv:evaluate>
</table>
&nbsp;
</dhv:permission>

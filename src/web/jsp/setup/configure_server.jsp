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
<%@ page import="org.aspcfs.utils.web.HtmlSelectTimeZone" %>
<%@ page import="org.aspcfs.utils.web.HtmlSelectLanguage" %>
<%@ page import="org.aspcfs.utils.web.HtmlSelectCurrency" %>
<%@ page import="org.aspcfs.utils.web.HtmlSelect" %>
<jsp:useBean id="server" class="org.aspcfs.modules.setup.beans.ServerBean" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<dhv:formMessage showSpace="false" />
<form name="configure" action="SetupServerDetails.do?command=ConfigureServer&auto-populate=true" method="post">
<input type="hidden" name="configured" value="1"/>
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      <dhv:label name="setup.centricCRM.step2of4">Centric CRM Configuration (Step 2 of 4)<br />Server Settings</dhv:label>
    </th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.serversServicesMustBeDefined.text">Centric CRM interacts with various servers and services that must be defined.</dhv:label><br>
      &nbsp;<br>
    </td>
  </tr>
  <%-- Time Zone --%>
  <tr class="sectionTitle">
    <th>Default Time Zone</th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.defaultTimezone.question">Each user in Centric CRM can configure the time zone in which they are currently in.<br /> What should the default time zone be set to for new users?</dhv:label><br>
      <dhv:label name="setup.defaultTimezone.text"><ul><li>Users will still be able to change their time zone, but for new users this setting will be used as the default</li><li>Dates and times will be displayed according to the user's configured time zone</li></ul></dhv:label>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="accounts.accounts_contacts_communication_preference_add.TimeZone.colon">Time Zone:</dhv:label>
          </td>
          <td>
            <% HtmlSelect select = HtmlSelectTimeZone.getSelect("timeZone", server.getTimeZoneDefault());   
            select.addItem(-1, "None Selected", 0); %><%= select.getHtml() %><font color="red">*</font>
          </td>
        </tr>
      </table>
      <br>
    </td>
  </tr>
  <%-- Locale --%>
  <tr class="sectionTitle">
    <th><dhv:label name="setup.defaultLanguageLocale">Default Language/Locale</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.defaultLocale.question">All locale formatting in Centric CRM is currently defaulted to a single locale. Which language and locale should Centric CRM use?</dhv:label>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.languageLocale.colon">Language/Locale:</dhv:label>
          </td>
          <td>
            <% HtmlSelect selectLanguage = HtmlSelectLanguage.getSelect("language", server.getLanguageDefault());
            selectLanguage.addItem(-1, "None Selected", 0); %><%= selectLanguage.getHtml("language", server.getLanguageDefault()) %><font color="red">*</font>
          </td>
        </tr>
      </table>
      <br>
    </td>
  </tr>
  <%-- Country --%>
  <tr class="sectionTitle">
    <th><dhv:label name="setup.defaultCountry">Default Country</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.defaultCountry.question">Which country should Centric CRM use as the default for mailing addresses?</dhv:label>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="setup.country.colon">Country:</dhv:label>
          </td>
          <td>
            <%= CountrySelect.getHtml("country", server.getCountryDefault()) %>
          </td>
        </tr>
      </table>
      <br>
    </td>
  </tr>
  <%-- Currency --%>
  <tr class="sectionTitle">
    <th><dhv:label name="setup.defaultCurrency">Default Currency</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.defaultCurrency.question">All money in Centric CRM is currently defaulted to a single currency. Which currency should Centric CRM use?</dhv:label>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.currency.colon">Currency:</dhv:label>
          </td>
          <td>
            <% HtmlSelect selectCurrency = HtmlSelectCurrency.getSelect("currency", server.getCurrencyDefault());
            selectCurrency.addItem(-1, "None Selected", 0); %><%= selectCurrency.getHtml() %><font color="red">*</font>
          </td>
        </tr>
      </table>
      <br>
    </td>
  </tr>
  <%-- Email Server --%>
  <tr class="sectionTitle">
    <th><dhv:label name="admin.emailServer">Email Server</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.emailServer.question">Centric CRM sends various notifications to users by email.  Centric CRM can also be used to send email to contacts that have been entered into Centric CRM.<br />Which email server should Centric CRM use?</dhv:label><br>
      <ul>
      <li><dhv:label name="setup.specifiedServerRelayEmail.text">The specified server must allow this server to relay email</dhv:label></li>
      </ul>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.emailServer.colon">Email Server:</dhv:label>
          </td>
          <td>
            <input type="text" size="30" name="email" value="<%= toHtmlValue(server.getEmail()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "emailError") %><br>
            <dhv:label name="admin.emailServer.example">(ex: mail.yourcompany.com or 127.0.0.1)</dhv:label>
          </td>
        </tr>
      </table>
      <br>
    </td>
  </tr>
  <%-- Email Address --%>
  <tr class="sectionTitle">
    <th><dhv:label name="setup.defaultEmailAddress">Default Email Address</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.forSystemEmails.question">For system emails, what email address should be in the FROM field of the email?</dhv:label><br>
      <br>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="documents.team.emailAddress.colon">Email Address:</dhv:label>
          </td>
          <td>
            <input type="text" size="40" name="emailAddress" value="<%= toHtmlValue(server.getEmailAddress()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "emailAddressError") %><br>
            <dhv:label name="admin.emailAddress.example">(ex: centric_crm@yourcompany.com)</dhv:label>
          </td>
        </tr>
      </table>
      &nbsp;<br>
    </td>
  </tr>
  <%-- URL --%>
  <tr class="sectionTitle">
    <th><dhv:label name="admin.serverURL">Server URL</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.systemEmailURL.question">For system emails, what URL should be included to allow the user to return back to the site?</dhv:label><br>
      <dhv:label name="setup.systemEmailURL.text"><ul><li>The URL must exist in your DNS so that users can connect</li><li>Optionally, you can specify the IP address</li><li>Include the full URL path to get to the Centric CRM web application, excluding HTTP:// or HTTPS://</li><li>Clicking the Test button will attempt to bring up a Centric CRM confirmation page located on this server</li></ul></dhv:label>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.centricCrmUrl">Centric CRM URL</dhv:label>:
          </td>
          <td>
            http:// <input type="text" size="40" name="url" value="<%= toHtmlValue(server.getUrl()) %>"/><font color="red">*</font>
            <input type="button" value="Test" onClick="javascript:popURL('<%= request.getScheme() %>://' + document.configure.url.value + '/setup/testpage_ok.jsp','CRM_UrlTest','500','325','yes','yes');"/>
            <%= showAttribute(request, "urlError") %>
            <br />
            <% String temp = "contextPath="+request.getContextPath(); %>
            <dhv:label name="setup.relativeURL.example" param="<%= temp %>">(ex: crm.yourcompany.com<%= request.getContextPath() %>)</dhv:label>
          </td>
        </tr>
      </table>
      &nbsp;<br>
    </td>
  </tr>

  <%-- LDAP Server --%>
  <tr class="sectionTitle">
    <th><dhv:label name="setup.ldap">LDAP Server</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.ldap.question">For authenticating users, should an LDAP server be used?</dhv:label><br>
      <dhv:label name="setup.ldap.text"><ul><li>A properly configured LDAP server is required</li>
        <li>If searching for users by attribute, the LDAP administrator should create an LDAP user with permissions on the LDAP server to search for users by given attribute; access to other attributes is not necessary</li>
      </ul></dhv:label>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.ldap.enabled">LDAP Enabled:</dhv:label>
          </td>
          <td>
            <dhv:checkbox name="ldapEnabled" value="true" checked="<%= server.getLdapEnabled() %>" />
            <dhv:label name="admin.ldap.authenticateUsers">Authenticate users against LDAP server</dhv:label>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.ldap.userMapping">User Mapping:</dhv:label>
          </td>
          <td>
            <input type="radio" name="ldapCentricCRMField" value="username" <%= ("username".equals(server.getLdapCentricCRMField()) ? "checked" : "") %> /><dhv:label name="admin.ldap.useCentricLogin">Use user's Centric CRM login</dhv:label><br />
            <input type="radio" name="ldapCentricCRMField" value="email" <%= ("email".equals(server.getLdapCentricCRMField()) ? "checked" : "") %> /><dhv:label name="admin.ldap.useUserEmail">Lookup user's primary email from Centric CRM records</dhv:label><br />
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.ldap.factory">LDAP Factory:</dhv:label>
          </td>
          <td>
            <input type="text" size="40" name="ldapFactory" value="<%= toHtmlValue(server.getLdapFactory()) %>"/>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.ldap.url">LDAP Server URL:</dhv:label>
          </td>
          <td>
            <input type="text" size="40" name="ldapUrl" value="<%= toHtmlValue(server.getLdapUrl()) %>"/>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.ldap.type">Type:</dhv:label>
          </td>
          <td>
            <table class="empty">
              <tr>
                <td nowrap valign="top">
                  <table class="empty">
                    <tr>
                      <td colspan="2">
                        <input type="radio" name="ldapSearchByAttribute" value="true" <%= (server.getLdapSearchByAttribute() ? "checked" : "") %> /><dhv:label name="admin.ldap.searchByAttribute">Search for user in LDAP by Attribute</dhv:label>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <dhv:label name="admin.ldap.username">Username:</dhv:label>
                      </td>
                      <td>
                        <input type="text" size="30" name="ldapSearchUsername" value="<%= toHtmlValue(server.getLdapSearchUsername()) %>" />
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <dhv:label name="admin.ldap.password">Password:</dhv:label>
                      </td>
                      <td>
                        <input type="text" size="30" name="ldapSearchPassword" value="<%= toHtmlValue(server.getLdapSearchPassword()) %>"/>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <dhv:label name="admin.ldap.container">Container:</dhv:label>
                      </td>
                      <td nowrap>
                        <input type="text" size="30" name="ldapSearchContainer" value="<%= toHtmlValue(server.getLdapSearchContainer()) %>"/>
                        <dhv:checkbox name="ldapSearchSubtree" value="true" checked="<%= server.getLdapSearchSubtree() %>"/>Search subtree
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <dhv:label name="admin.ldap.objectClass">Object Class:</dhv:label>
                      </td>
                      <td>
                        <input type="text" size="30" name="ldapSearchOrgPerson" value="<%= toHtmlValue(server.getLdapSearchOrgPerson()) %>"/>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <dhv:label name="admin.ldap.attribute">Attribute:</dhv:label>
                      </td>
                      <td>
                        <input type="text" size="30" name="ldapSearchAttribute" value="<%= toHtmlValue(server.getLdapSearchAttribute()) %>"/>
                      </td>
                    </tr>
                  </table>
                </td>
                <td nowrap valign="top">
                  <table class="empty">
                    <tr>
                      <td colspan="2">
                        <input type="radio" name="ldapSearchByAttribute" value="false" <%= (!server.getLdapSearchByAttribute() ? "checked" : "") %> /><dhv:label name="admin.ldap.useComposite">Use LDAP Composite DN</dhv:label>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <dhv:label name="admin.ldap.compositePrefix">Composite DN Prefix:</dhv:label>
                      </td>
                      <td>
                        <input type="text" size="30" name="ldapSearchPrefix" value="<%= toHtmlValue(server.getLdapSearchPrefix()) %>" />
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <dhv:label name="admin.ldap.compositePostfix">Composite DN Postfix:</dhv:label>
                      </td>
                      <td>
                        <input type="text" size="30" name="ldapSearchPostfix" value="<%= toHtmlValue(server.getLdapSearchPostfix()) %>" />
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      &nbsp;<br>
    </td>
  </tr>


  <%-- Asterisk Server --%>
  <tr class="sectionTitle">
    <th><dhv:label name="setup.asterisk">Asterisk Server</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.asterisk.question">For inbound call monitoring and outbound call dialing, which Asterisk server should be used?</dhv:label><br>
      <dhv:label name="setup.asterisk.text"><ul><li>A properly configured Asterisk server is required</li></ul></dhv:label>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.asterisk.inbound">Inbound:</dhv:label>
          </td>
          <td>
            <dhv:checkbox name="asteriskInbound" value="true" checked="<%= server.getAsteriskInbound() %>"/>Monitor inbound calls
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.asterisk.outbound">Outbound:</dhv:label>
          </td>
          <td>
            <dhv:checkbox name="asteriskOutbound" value="true" checked="<%= server.getAsteriskOutbound() %>"/>Enable outbound call dialing
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.asterisk.server">Asterisk Server:</dhv:label>
          </td>
          <td>
            <input type="text" size="30" name="asteriskUrl" value="<%= toHtmlValue(server.getAsteriskUrl()) %>"/>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.asterisk.username">Asterisk Username:</dhv:label>
          </td>
          <td>
            <input type="text" size="30" name="asteriskUsername" value="<%= toHtmlValue(server.getAsteriskUsername()) %>"/>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.asterisk.password">Asterisk Password:</dhv:label>
          </td>
          <td>
            <input type="text" size="30" name="asteriskPassword" value="<%= toHtmlValue(server.getAsteriskPassword()) %>"/>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.asterisk.context">Asterisk Context:</dhv:label>
          </td>
          <td>
            <input type="text" size="30" name="asteriskContext" value="<%= toHtmlValue(server.getAsteriskContextDefault()) %>"/>
          </td>
        </tr>
      </table>
      &nbsp;<br>
    </td>
  </tr>

  <%-- XMPP Server --%>
  <tr class="sectionTitle">
    <th><dhv:label name="setup.xmpp">XMPP Server</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.xmpp.question">For monitoring user presence and performing inbound screen-pops for Asterisk calls, which XMPP server should be used?</dhv:label><br>
      <dhv:label name="setup.xmpp.text"><ul><li>Centric CRM will use the following user to subscribe and communicate with users using instant messaging</li></ul></dhv:label>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.xmpp.status">XMPP Status:</dhv:label>
          </td>
          <td>
            <dhv:checkbox name="xmppEnabled" value="true" checked="<%= server.getXmppEnabled() %>"/>Monitor user presence and send instant messages
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.xmpp.server">XMPP Server:</dhv:label>
          </td>
          <td>
            <input type="text" size="30" name="xmppUrl" value="<%= toHtmlValue(server.getXmppUrl()) %>"/>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.xmpp.port">XMPP Port:</dhv:label>
          </td>
          <td>
            <input type="text" size="30" name="xmppPort" value="<%= server.getXmppPort() %>"/>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.xmpp.ssl">XMPP SSL:</dhv:label>
          </td>
          <td>
            <dhv:checkbox name="xmppSSL" value="true" checked="<%= server.getXmppSSL() %>"/>Use SSL connection to XMPP server
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.xmpp.username">XMPP Username:</dhv:label>
          </td>
          <td>
            <input type="text" size="30" name="xmppUsername" value="<%= toHtmlValue(server.getXmppUsername()) %>"/>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.xmpp.password">XMPP Password:</dhv:label>
          </td>
          <td>
            <input type="text" size="30" name="xmppPassword" value="<%= toHtmlValue(server.getXmppPassword()) %>"/>
          </td>
        </tr>
      </table>
      &nbsp;<br>
    </td>
  </tr>

  <%-- Fax Server --%>
  <tr class="sectionTitle">
    <th><dhv:label name="setup.faxes">Faxes</dhv:label></th>
  </tr>
  <tr>
    <td>
      <font color="red">* </font><b><dhv:label name="setup.hylafaxServerRequirements.text">The HylaFax server application requires Linux or Unix and has further software and hardware requirements.</dhv:label></b><br>
      <dhv:label name="setup.userCapabilitySendFaxes.text">Users may have the capability to send faxes using Centric CRM.</dhv:label><br>
      <dhv:label name="setup.configureServer.faxingComponent.text" param="hylafax=<a href=\"http://www.hylafax.org\" target=\"_new\">|end=</a>">The faxing component requires a properly configured <a href="http://www.hylafax.org" target="_new">HylaFax</a> server including fax hardware.</dhv:label>
      <dhv:label name="setup.faxServer.question">Which fax server should Centric CRM use?</dhv:label><br>
      <dhv:label name="setup.faxServer.text"><ul><li>The specified server must allow this server to send faxes</li><li>Leave blank if faxing will not be used</li></ul></dhv:label>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            <dhv:label name="admin.faxServer.colon">Fax Server:</dhv:label>
          </td>
          <td>
            <input type="text" size="30" name="fax" value="<%= toHtmlValue(server.getFax()) %>"/>
            <dhv:label name="reports.optional.brackets">(optional)</dhv:label>
            <%= showAttribute(request, "faxError") %><br>
            <dhv:label name="setup.faxServer.example">(ex: fax.yourcompany.com)</dhv:label>
          </td>
        </tr>
      </table>
      &nbsp;<br>
    </td>
  </tr>

</table>
<input type="submit" value="<dhv:label name="button.continueR">Continue ></dhv:label>" />
</form>

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
<body onLoad="javascript:document.forms[0].email.focus();">
<dhv:formMessage showSpace="false" />
<form name="configure" action="SetupServerDetails.do?command=ConfigureServer&auto-populate=true" method="post">
<input type="hidden" name="configured" value="1"/>
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      Centric CRM Configuration (Step 2 of 4)<br>
      Server Settings
    </th>
  </tr>
  <tr>
    <td>
      Centric CRM interacts with various servers and services that must be defined.<br>
      &nbsp;<br>
    </td>
  </tr>
  <%-- Time Zone --%>
  <tr class="sectionTitle">
    <th>Default Time Zone</th>
  </tr>
  <tr>
    <td>
      Each user in Centric CRM can configure the time zone in which they are currently in.<br>
      What should the default time zone be set to for new users?<br>
      <ul>
      <li>Users will still be able to change their time zone, but for new users this setting 
      will be used as the default</li>
      <li>Dates and times will be displayed according to the user's configured time zone</li>
      </ul>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            Time Zone:
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
    <th>Default Location/Locale</th>
  </tr>
  <tr>
    <td>
      All locale formatting in Centric CRM is currently defaulted to a single locale.
      Which locale should Centric CRM use?
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            Locale:
          </td>
          <td>
            <% HtmlSelect selectLanguage = HtmlSelectLanguage.getSelect("language", server.getLanguageDefault());
            selectLanguage.addItem(-1, "None Selected", 0); %><%= selectLanguage.getHtml() %><font color="red">*</font>
          </td>
        </tr>
      </table>
      <br>
    </td>
  </tr>
  <%-- Country --%>
  <tr class="sectionTitle">
    <th>Default Country</th>
  </tr>
  <tr>
    <td>
      Which country should Centric CRM use as the default for mailing addresses?
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            Country:
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
    <th>Default Currency</th>
  </tr>
  <tr>
    <td>
      All money in Centric CRM is currently defaulted to a single currency.
      Which currency should Centric CRM use?
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            Currency:
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
    <th>Email Server</th>
  </tr>
  <tr>
    <td>
      Centric CRM sends various notifications to users by email.  Centric CRM can also be used
      to send email to contacts that have been entered into Centric CRM.<br>
      Which email server should Centric CRM use?<br>
      <ul>
      <li>The specified server must allow this server to relay email</li>
      </ul>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            Email Server:
          </td>
          <td>
            <input type="text" size="30" name="email" value="<%= toHtmlValue(server.getEmail()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "emailError") %><br>
            (ex: mail.yourcompany.com or 127.0.0.1)
          </td>
        </tr>
      </table>
      <br>
    </td>
  </tr>
  <%-- Email Address --%>
  <tr class="sectionTitle">
    <th>Default Email Address</th>
  </tr>
  <tr>
    <td>
      For system emails, what email address should be in the FROM field of the email?<br>
      <br>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            Email Address:
          </td>
          <td>
            <input type="text" size="40" name="emailAddress" value="<%= toHtmlValue(server.getEmailAddress()) %>"/><font color="red">*</font>
            <%= showAttribute(request, "emailAddressError") %><br>
            (ex: crm_system@yourcompany.com)
          </td>
        </tr>
      </table>
      &nbsp;<br>
    </td>
  </tr>
  <%-- URL --%>
  <tr class="sectionTitle">
    <th>Server URL</th>
  </tr>
  <tr>
    <td>
      For system emails, what URL should be included to allow the user to return back to the site?<br>
      <ul>
      <li>The URL must exist in your DNS so that users can connect</li>
      <li>Optionally, you can specify the IP address</li>
      <li>Include the full URL path to get to the Centric CRM web application, 
      excluding HTTP:// or HTTPS://</li>
      <li>Clicking the Test button will attempt to bring up a Centric CRM confirmation
      page located on this server</li>
      </ul>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            Centric CRM URL:
          </td>
          <td>
            <input type="text" size="40" name="url" value="<%= toHtmlValue(server.getUrl()) %>"/><font color="red">*</font>
            <input type="button" value="Test" onClick="javascript:popURL('<%= request.getScheme() %>://' + document.forms[0].url.value + '/setup/testpage_ok.jsp','CRM_UrlTest','500','325','yes','yes');"/>
            <%= showAttribute(request, "urlError") %>
            <br>
            (ex: crm.yourcompany.com<%= request.getContextPath() %>)
          </td>
        </tr>
      </table>
      &nbsp;<br>
    </td>
  </tr>
  <%-- Fax Server --%>
  <tr class="sectionTitle">
    <th>Faxes</th>
  </tr>
  <tr>
    <td>
      <font color="red">* </font><b>The HylaFax server application requires Linux or Unix and has further software and hardware requirements.</b><br>
      Users may have the capability to send faxes using Centric CRM.<br>
      The faxing component requires a properly configured
      <a href="http://www.hylafax.org" target="_new">HylaFax</a> server including fax hardware.<br>
      Which fax server should Centric CRM use?<br>
      <ul>
      <li>The specified server must allow this server to send faxes</li>
      <li>Leave blank if faxing will not be used</li>
      </ul>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            Fax Server:
          </td>
          <td>
            <input type="text" size="30" name="fax" value="<%= toHtmlValue(server.getFax()) %>"/>
            (optional)
            <%= showAttribute(request, "faxError") %><br>
            (ex: fax.yourcompany.com)
          </td>
        </tr>
      </table>
      &nbsp;<br>
      <input type="submit" value="Continue >"/>
    </td>
  </tr>
</table>
</form>
</body>

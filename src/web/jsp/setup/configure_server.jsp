<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.utils.web.HtmlSelectTimeZone" %>
<%@ page import="org.aspcfs.utils.web.HtmlSelect" %>
<jsp:useBean id="server" class="org.aspcfs.modules.setup.beans.ServerBean" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<body onLoad="javascript:document.forms[0].email.focus();">
<%= showError(request, "actionError", false) %>
<form name="configure" action="SetupServerDetails.do?command=ConfigureServer&auto-populate=true" method="post">
<input type="hidden" name="configured" value="1"/>
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      Dark Horse CRM Configuration (Step 2 of 4)<br>
      Server Settings
    </th>
  </tr>
  <tr>
    <td>
      Dark Horse CRM interacts with various servers and services that must be defined.<br>
      &nbsp;<br>
    </td>
  </tr>
  <%-- Time Zone --%>
  <tr class="sectionTitle">
    <th>Default Time Zone</th>
  </tr>
  <tr>
    <td>
      Every user in Dark Horse CRM can configure the time zone in which they are currently in.<br>
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
  <%-- Email Server --%>
  <tr class="sectionTitle">
    <th>Email Server</th>
  </tr>
  <tr>
    <td>
      Dark Horse CRM sends various notifications to users by email.  Dark Horse CRM can also be used
      to send email to contacts that have been entered into Dark Horse CRM.<br>
      Which email server should Dark Horse CRM use?<br>
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
      <li>Include the full URL path to get to the Dark Horse CRM web application, 
      excluding HTTP:// or HTTPS://</li>
      <li>Clicking the Test button will attempt to bring up a Dark Horse CRM confirmation
      page located on this server</li>
      </ul>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            Dark Horse CRM URL:
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
      Users may have the capability to send faxes using Dark Horse CRM.<br>
      The faxing component requires a properly configured
      <a href="http://www.hylafax.org" target="_new">HylaFax</a> server including fax hardware.<br>
      Which fax server should Dark Horse CRM use?<br>
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

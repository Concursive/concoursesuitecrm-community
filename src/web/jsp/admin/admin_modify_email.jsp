<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<form name="modify" action="AdminConfig.do?command=Update" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do">Admin</a> >
<a href="AdminConfig.do?command=ListGlobalParams">Configure System</a> >
Modify Setting
</td>
</tr>
</table>
<%-- End Trails --%>
<table class="note" cellspacing="0">
<tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><b>Which 
email server should Dark Horse CRM use?</b><br />
Dark Horse CRM sends various notifications to users by email.  Dark Horse CRM can also be used
to send email to contacts that have been entered into the Contacts and Accounts modules.
<ul><li>The specified server must allow this server to relay email</li></ul>
</td></tr></table>
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong>Modify Email Server</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        Email Server
      </td>
      <td>
         <input type="text" size="30" name="email" value="<%= toHtmlValue(getPref(getServletContext(), "MAILSERVER")) %>"/><font color="red">*</font>
         <%= showAttribute(request, "emailError") %><br>
         (ex: mail.yourcompany.com or 127.0.0.1)
      </td>
    </tr>
  </table>
  <br>
  <input type="submit" value="Update">
  <input type="button" value="Cancel" onClick="javascript:window.location.href='AdminConfig.do?command=ListGlobalParams';">
</dhv:permission>
</form>

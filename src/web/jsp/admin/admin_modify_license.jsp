<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="APP_TEXT" class="java.lang.String" scope="application"/>
<jsp:useBean id="APP_ORGANIZATION" class="java.lang.String" scope="application"/>
<%@ include file="../initPage.jsp" %>
<form name="modify" action="AdminConfig.do?command=UpdateLicense" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do">Admin</a> >
<a href="AdminConfig.do?command=ListGlobalParams">Configure System</a> >
License
</td>
</tr>
</table>
<%-- End Trails --%>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><b>Have
you upgraded your license?</b><br />
Every Dark Horse CRM application requires a license agreement from Dark Horse Ventures.
If changes have been made to the license, for example, 
additional seats have been purchased, then the installed license should be updated to
reflect those changes.
Contact <a href="http://www.darkhorsecrm.com" target="_blank">www.darkhorsecrm.com</a> for
upgrade information
</td></tr></table>
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong>License</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        Edition
      </td>
      <td>
         <%= toHtml(APP_TEXT) %>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        Licensed To
      </td>
      <td>
         <%= toHtml(APP_ORGANIZATION) %>
      </td>
    </tr>
  </table>
  <br />
  <input type="radio" name="doLicense" value="internet" checked/>
  Automatically have this system remotely check and download an updated license<br />
  <input type="radio" name="doLicense" value="email"/>
  Manually enter an updated license from an email message<br />
  <br />
  <input type="submit" value="Update">
  <input type="button" value="Cancel" onClick="javascript:window.location.href='AdminConfig.do?command=ListGlobalParams';">
</dhv:permission>
</form>

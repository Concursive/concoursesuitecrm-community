<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<form name="modifyTimeout" action="AdminConfig.do?command=Update" method="post">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Admin.do">Setup</a> >
<a href="AdminConfig.do?command=ListGlobalParams">Configure System</a> >
Modify Setting
</td>
</tr>
</table>
<%-- End Trails --%>
For system emails, what URL should be included to allow the user to return back to the site?<br>
The URL must be specific and must exist in your DNS.<br>
If a domain name is not configured, you can specify the IP address, include the full URL to
get to the Dark Horse CRM web application.<br>
&nbsp;<br>
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong>Server URL</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        Dark Horse CRM URL
      </td>
      <td>
         <input type="text" size="40" name="url" value="<%= toHtmlValue(getPref(getServletContext(), "WEBSERVER.URL")) %>"/><font color="red">*</font>
         <input type="button" value="Test" onClick="javascript:popURL('<%= request.getScheme() %>://' + document.forms[0].url.value + '/setup/testpage_ok.jsp','CRM_UrlTest','500','325','yes','yes');"/>
         <%= showAttribute(request, "urlError") %>
         <br>
         (ex: www.yourcompany.com<%= request.getContextPath() %> or
         crm.yourcompany.com)
      </td>
    </tr>
  </table>
  <br>
  <input type="submit" value="Update">
  <input type="button" value="Cancel" onClick="javascript:window.location.href='AdminConfig.do?command=ListGlobalParams';">
</dhv:permission>
</form>

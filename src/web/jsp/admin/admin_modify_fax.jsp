<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
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
<font color="red">* </font><b>The HylaFax server application requires Linux or Unix.</b><br>
Users will have the capability to send faxes using Dark Horse CRM.<br>
The faxing component requires a properly configured
<a href="http://www.hylafax.org" target="_new">HylaFax</a> server including fax hardware.<br>
Which fax server should Dark Horse CRM use?<br>
- The specified server must allow this server to send faxes<br>
- Leave blank if faxing will not be used<br>
&nbsp;<br>
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong>Modify Fax Server</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        Fax Server
      </td>
      <td>
         <input type="text" size="30" name="fax" value="<%= toHtmlValue(getPref(getServletContext(), "FAXSERVER")) %>"/>
      </td>
    </tr>
  </table>
  <br>
  <input type="submit" value="Update">
  <input type="button" value="Cancel" onClick="javascript:window.location.href='AdminConfig.do?command=ListGlobalParams';">
</dhv:permission>
</form>

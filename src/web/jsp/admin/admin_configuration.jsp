<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<a href="/Admin.do">Setup</a> > 
System Configuration<br>
<hr color="#BFBFBB" noshade>
Setup CFS to meet the specific needs of your organization, including setting up modules and custom fields.<br>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td>
      <strong>Customize CFS</strong>
    </td>
  </tr>
  <dhv:permission name="admin-sysconfig-lists-view">
  <tr>
    <td><a href="/Admin.do?command=EditLists">Configure Lookup Lists</a></td>
  </tr>
  </dhv:permission>
  <dhv:permission name="admin-sysconfig-folders-view">
  <tr>
    <td><a href="/AdminFieldsFolder.do">Configure Custom Folders &amp; Fields</a></td>
  </tr>
  </dhv:permission>
</table>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Timeout" class="java.lang.String" scope="request"/>
<a href="Admin.do">Setup</a> >
Global Parameters<br>
<hr color="#BFBFBB" noshade>
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th width="8">
        <strong>Action</strong>
      </th>
      <th>
        <strong>Parameter</strong>
      </th>
      <th>
        <strong>Current Value</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td align="center">
        <a href="Admin.do?command=ModifyTimeout&timeout=<%= Timeout %>"> Edit </a>
      </td>
      <td>
         Session Timeout
      </td>
      <td>
         <%= Timeout %> minutes
      </td>
    </tr>
  </table>
  &nbsp;
</dhv:permission>

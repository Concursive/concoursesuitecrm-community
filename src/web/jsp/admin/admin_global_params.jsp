<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Timeout" class="java.lang.String" scope="request"/>

<a href="Admin.do">Setup</a> >
Global Parameters<br>
<hr color="#BFBFBB" noshade>

<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td width="8">
        <strong>Action</strong>
      </td>
      <td align="left">
        <strong>Parameter</strong>
      </td>
      <td align="left">
        <strong>Current Value</strong>
      </td>
    </tr>
    <tr class="containerBody">
      <td align="center">
        <a href="Admin.do?command=ModifyTimeout&timeout=<%=Timeout%>"> Edit </a>
      </td>
      <td align="left">
         Session Timeout
      </td>
      <td align="left">
         <%= Timeout %> minutes
      </td>
    </tr>
  </table>
  &nbsp;
</dhv:permission>

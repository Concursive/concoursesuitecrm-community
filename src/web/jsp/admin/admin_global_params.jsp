<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Timeout" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<a href="Admin.do">Setup</a> >
Configure System<br>
<hr color="#BFBFBB" noshade>
<% int count = 1; %>
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
    <tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
      <td align="center">
        <a href="AdminConfig.do?command=ModifyTimeout&timeout=<%= Timeout %>">Edit</a>
      </td>
      <td>
         Session Timeout
      </td>
      <td>
         <%= Timeout %> minutes
      </td>
    </tr>
<dhv:evaluate if="<%= getPref(getServletContext(), "WEBSERVER.ASPMODE") == null || !"true".equals(getPref(getServletContext(), "WEBSERVER.ASPMODE")) %>">
    <tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
      <td align="center">
        <a href="AdminConfig.do?command=Modify&param=MAILSERVER">Edit</a>
      </td>
      <td>
         Email Server
      </td>
      <td>
         <%= toHtml(getPref(getServletContext(), "MAILSERVER")) %>
      </td>
    </tr>
    <tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
      <td align="center">
        <a href="AdminConfig.do?command=Modify&param=EMAILADDRESS">Edit</a>
      </td>
      <td>
         Email Address
      </td>
      <td>
         <%= toHtml(getPref(getServletContext(), "EMAILADDRESS")) %>
      </td>
    </tr>
    <tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
      <td align="center">
        <a href="AdminConfig.do?command=Modify&param=WEBSERVER.URL">Edit</a>
      </td>
      <td>
         CFS URL
      </td>
      <td>
         <%= toHtml(getPref(getServletContext(), "WEBSERVER.URL")) %>
      </td>
    </tr>
    <tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
      <td align="center">
        <a href="AdminConfig.do?command=Modify&param=FAXSERVER">Edit</a>
      </td>
      <td>
         Fax Server
      </td>
      <td>
         <%= toHtml(getPref(getServletContext(), "FAXSERVER")) %>
      </td>
    </tr>
</dhv:evaluate>
  </table>
  &nbsp;
</dhv:permission>

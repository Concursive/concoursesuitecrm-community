<%--
  - Copyright
  -
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="callClient" class="org.aspcfs.modules.dialer.beans.CallClient" scope="session"/>
<%@ include file="../initPage.jsp" %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="button.calling.dots">Calling...</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="calls.fromExtension">From Extension</dhv:label>
    </td>
    <td>
      <%= toHtml(callClient.getExtension()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="quotes.phoneNumber">Phone Number</dhv:label>
    </td>
    <td>
      <%= toHtml(callClient.getNumber()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Status">Status</dhv:label>
    </td>
    <td>
      <%= toHtml(callClient.getLastResponse()) %>
    </td>
  </tr>

</table>
<br />
<input type="button" value="<dhv:label name="button.close">Close</dhv:label>" onClick="javascript:window.close();">

<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="com.darkhorseventures.cfsbase.PermissionCategory, java.util.*" %>
<jsp:useBean id="usageList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="usageList2" class="java.util.ArrayList" scope="request"/>
<%@ include file="initPage.jsp" %>
<a href="Admin.do">Setup</a> > 
Usage<br>
<hr color="#BFBFBB" noshade>
Current Usage and Billing Usage Information<br>
&nbsp;<br>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td>
      <strong>Current system resources:</strong>
    </td>
  </tr>
<%
  Iterator items = usageList.iterator();
  while (items.hasNext()) {
  String thisItem = (String)items.next();
%>
  <tr class="containerBody">
    <td>
      <%= thisItem %>
    </td>
  </tr>
<%
  }
%>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td>
      <strong>Today's Usage:</strong>
    </td>
  </tr>
<%
  Iterator items2 = usageList2.iterator();
  while (items2.hasNext()) {
  String thisItem2 = (String)items2.next();
%>
  <tr class="containerBody">
    <td>
      <%= thisItem2 %>
    </td>
  </tr>
<%
  }
%>
</table>


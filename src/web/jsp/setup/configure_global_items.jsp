<%-- Displays any global items for this setup page --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="ModuleBean" class="org.aspcfs.modules.beans.ModuleBean" scope="request"/>
<%
  String image = "<img alt=\"Current Step\" src=\"images/bullet.gif\" align=\"absmiddle\"/>&nbsp;";
%>
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th colspan="2">
      You are here...
    </th>
  </tr>
  <tr>
    <td><dhv:evaluate if="<%= "Welcome".equals(ModuleBean.getCurrentAction()) %>"><%= image %></dhv:evaluate></td>
    <td width="100%">Welcome</td>
  </tr>
  <tr>
    <td><dhv:evaluate if="<%= "Register".equals(ModuleBean.getCurrentAction()) %>"><%= image %></dhv:evaluate></td>
    <td>Registration</td>
  </tr>
  <tr>
    <td><dhv:evaluate if="<%= "Storage".equals(ModuleBean.getCurrentAction()) %>"><%= image %></dhv:evaluate></td>
    <td nowrap>Configure Storage</td>
  </tr>
  <tr>
    <td><dhv:evaluate if="<%= "Servers".equals(ModuleBean.getCurrentAction()) %>"><%= image %></dhv:evaluate></td>
    <td nowrap>Configure Servers</td>
  </tr>
  <tr>
    <td><dhv:evaluate if="<%= "Database".equals(ModuleBean.getCurrentAction()) %>"><%= image %></dhv:evaluate></td>
    <td nowrap>Configure Database</td>
  </tr>
  <tr>
    <td><dhv:evaluate if="<%= "CRMSetup".equals(ModuleBean.getCurrentAction()) %>"><%= image %></dhv:evaluate></td>
    <td nowrap>Configure CRM</td>
  </tr>
</table>
<br>
<%-- info tab --%>
<dhv:evaluate if="<%= "Welcome".equals(ModuleBean.getCurrentAction()) %>">
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th>
      Welcome
    </th>
  </tr>
  <tr>
    <td>
      Dark Horse CRM<br>
      saves time and money...<br>
      <br>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="http://www.darkhorsecrm.com" target="_blank">Learn more about our products</a><br>
      &nbsp;
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- info tab --%>
<dhv:evaluate if="<%= "Register".equals(ModuleBean.getCurrentAction()) %>">
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th>
      Registration
    </th>
  </tr>
  <tr>
    <td>
      Privacy is extremely important to us.<br>
      <br>
      Your information will not be provided to others.<br>
      <br>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="#">Review Privacy Statement</a><br>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="#">Review Security Statement</a><br>
      &nbsp;
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- info tab --%>
<dhv:evaluate if="<%= "Storage".equals(ModuleBean.getCurrentAction()) %>">
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th>
      Storage Resources
    </th>
  </tr>
  <tr>
    <td>
      Remember to make backups of your data.<br>
      <br>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="#">File Backup Strategy</a><br>
      &nbsp;
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- info tab --%>
<dhv:evaluate if="<%= "Servers".equals(ModuleBean.getCurrentAction()) %>">
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th>
      Server Resources
    </th>
  </tr>
  <tr>
    <td>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="#">SendMail Tutorial</a><br>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="#">HylaFax Tutorial</a><br>
      &nbsp;
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- info tab --%>
<dhv:evaluate if="<%= "Database".equals(ModuleBean.getCurrentAction()) %>">
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th>
      Database Resources
    </th>
  </tr>
  <tr>
    <td>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="#">PostgreSQL Tutorial</a><br>
      &nbsp;
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- info tab --%>
<dhv:evaluate if="<%= "CRMSetup".equals(ModuleBean.getCurrentAction()) %>">
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th>
      Dark Horse CRM Resources
    </th>
  </tr>
  <tr>
    <td>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="#">Dark Horse CRM Tutorial</a><br>
      &nbsp;
    </td>
  </tr>
</table>
</dhv:evaluate>

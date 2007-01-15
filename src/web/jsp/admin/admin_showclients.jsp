<%-- 
  - Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: admin_showclients.jsp 13721 2005-12-29 20:43:02 -0500 (Thu, 29 Dec 2005) ananth $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page
    import="org.aspcfs.modules.service.base.SyncClient,java.util.Iterator" %>
<jsp:useBean id="syncClientList"
             class="org.aspcfs.modules.service.base.SyncClientList"
             scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
             scope="session"/>
<jsp:useBean id="SyncListInfo" class="org.aspcfs.utils.web.PagedListInfo"
             scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
        SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" type="text/javascript"
        src="javascript/preloadImages.js"></script>
<script language="JavaScript" type="text/javascript"
        src="javascript/ypSlideOutMenusC.js"></script>
<%@ include file="admin_showclients_menu.jsp" %>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
      <a href="AdminConfig.do?command=ListGlobalParams"><dhv:label
          name="admin.configureSystem">Configure System</dhv:label></a> >
      <dhv:label name="admin.hTTP-XMLClientManager">HTTP-XML Client
        Manager</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<dhv:permission name="admin-sysconfig-edit"><a
    href="AdminClientManager.do?command=AddClientForm"><dhv:label
    name="admin.addClient">Add Client</dhv:label></a></dhv:permission>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>'
                     object="SyncListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
       class="pagedList">
  <tr>
    <th align="center">
      &nbsp;
    </th>
    <th nowrap>
      <b><dhv:label name="contacts.id">Id</dhv:label></b>
    </th>
    <th nowrap>
      <b><dhv:label name="accounts.name">Name</dhv:label></b>
    </th>
    <th nowrap>
      <b><dhv:label name="project.version">Version</dhv:label></b>
    </th>
    <th nowrap>
      <b><dhv:label name="admin.enabled">Enabled</dhv:label></b>
    </th>
    <th nowrap>
      <b><dhv:label name="admin.date entered">Date Entered</dhv:label></b>
    </th>
    <th nowrap>
      <b><dhv:label name="admin.date modified">Date Modified</dhv:label></b>
    </th>
  </tr>
  <%
    Iterator i = syncClientList.iterator();
    if (i.hasNext()) {
      int rowid = 0;
      int count = 0;
      while (i.hasNext()) {
        count++;
        rowid = (rowid != 1 ? 1 : 2);
        SyncClient thisSyncClient = (SyncClient) i.next();
  %>
  <tr class="row<%= rowid %>" width="8">
    <td valign="center" align="center" nowrap>
      <% int status = thisSyncClient.getEnabled() ? 1 : 0; %>
      <dhv:permission name="admin-sysconfig-edit" none="true"><% status = -1; %>
      </dhv:permission>
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
      <a href="javascript:displayMenu('select<%= count %>','menuClient', '<%= thisSyncClient.getId() %>','<%= status %>','<%= !thisSyncClient.getEnabled() %>');"
         onMouseOver="over(0, <%= count %>)"
         onMouseOut="out(0, <%= count %>); hideMenu('menuClient');"><img
          src="images/select.gif" name="select<%= count %>"
          id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td width="20%">
      <a href="AdminClientManager.do?command=ModifyClientForm&id=<%= thisSyncClient.getId() %>"><%= thisSyncClient.getId() %></a>
    </td>
    <td width="60%" nowrap>
      <%= toHtml(thisSyncClient.getType()) %>
    </td>
    <td nowrap>
      <%= toHtml(thisSyncClient.getVersion()) %>
    </td>
    <td nowrap>
      <%= thisSyncClient.getEnabled() ? "yes" : "no" %>
    </td>
    <td nowrap>
      <zeroio:tz timestamp="<%= thisSyncClient.getEntered()  %>"
                 timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
    <td nowrap>
      <zeroio:tz timestamp="<%= thisSyncClient.getModified()  %>"
                 timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
  </tr>
  <%
    }
  } else {
  %>
  <tr>
    <td class="containerBody" valign="center" colspan="7">
      <dhv:label name="admin.noClientsFound">No clients found.</dhv:label>
    </td>
  </tr>
  <%
    }
  %>
</table>
<br>
<dhv:pagedListControl object="SyncListInfo" tdClass="row1"/>



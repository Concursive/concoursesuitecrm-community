<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.actionlist.base.*, org.aspcfs.modules.base.Constants"%>
<%@ page import="org.aspcfs.modules.admin.base.*" %>
<jsp:useBean id="ActionLists" class="org.aspcfs.modules.actionlist.base.ActionLists" scope="request"/>
<jsp:useBean id="ActionListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="viewUser" class="java.lang.String" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="action_lists_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" src="javascript/popContacts.js?v=20070827"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  function selectUser(form) {
    popContactsListSingle('viewUserId','changeUser', 'usersOnly=true&hierarchy=<%= User.getUserRecord().getId() %><%= User.getUserRecord().getSiteId() == -1?"&includeAllSites=true&siteId=-1":"&mySiteOnly=true&siteId="+ User.getUserRecord().getSiteId() %>&reset=true&filters=employees|accountcontacts');
  }
  function resetUser(form) {
    window.location.href='MyActionLists.do?command=List&linkModuleId=2&viewUserId=<%= User.getUserRecord().getId() %>';
  }
  function changeDivContent(divName, divContents) {
    window.location.href='MyActionLists.do?command=List&linkModuleId=2&viewUserId='+document.getElementById('viewUserId').value;
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
<dhv:label name="myitems.actionLists">Action Lists</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="myhomepage-action-lists-add">
<a href="javascript:window.location.href='MyActionLists.do?command=Add&return=list&params=' + escape('reset=true&filters=all|mycontacts|accountcontacts');"><dhv:label name="actionList.addAnActionList">Add an Action List</dhv:label></a><br>
</dhv:permission>
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <form name="listView" method="post" action="MyActionLists.do?command=List&linkModuleId=<%= Constants.ACTIONLISTS_CONTACTS %>">
    <td nowrap>
      <select size="1" name="listView" onChange="javascript:document.listView.submit();">
        <option <%= ActionListInfo.getOptionValue("inprogress") %>><dhv:label name="actionList.allInProgressLists">All In Progress Lists</dhv:label></option>
        <option <%= ActionListInfo.getOptionValue("complete") %>><dhv:label name="actionList.allCompleteLists">All Complete Lists</dhv:label></option>
        <option <%= ActionListInfo.getOptionValue("all") %>><dhv:label name="actionList.allLists">All Lists</dhv:label></option>
      </select>
      <input type="hidden" name="viewUserId" id="viewUserId" value="<%= (viewUser != null && !"".equals(viewUser)? viewUser:""+User.getUserRecord().getId()) %>" />
      <% UserList childList = User.getUserRecord().getChildUsers();
        if (childList != null && childList.size() > 0) { %>
        <input type="text" disabled="true" size="15" value="<dhv:username id="<%= ActionLists.getOwner() %>" lastFirst="true" />"/>
        [<a href="javascript:selectUser(this.form);"><dhv:label name="actionList.changeUser">Change User</dhv:label></a>]
        [<a href="javascript:resetUser(this.form);"><dhv:label name="actionList.resetUser">Reset User</dhv:label></a>]
        <%--
        [<a href="javascript:popURL('UsersList.do?command=ListUsers&amp;hidden_0=<%= User.getUserRecord().getId() %>','Users','500','400','yes','yes');">View User Hierarchy</a>]
        --%>
      <% } %>
    </td>
    <td>
      <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="ActionListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th rowspan="2" valign="middle">
      &nbsp;
    </th>
    <th rowspan="2" valign="middle" width="100%">
      <strong><a href="MyActionLists.do?command=List&linkModuleId=<%= Constants.ACTIONLISTS_CONTACTS %>&viewUserId=<%= viewUser %>&column=al.description"><dhv:label name="contacts.name">Name</dhv:label></a></strong>
      <%= ActionListInfo.getSortIcon("al.description") %>
    </th>
    <th colspan="2" align="center">
      <strong><dhv:label name="project.progress">Progress</dhv:label></strong>
    </th>
    <th rowspan="2" valign="middle" nowrap>
      <strong><a href="MyActionLists.do?command=List&linkModuleId=<%= Constants.ACTIONLISTS_CONTACTS %>&viewUserId=<%= viewUser %>&column=al.modified"><dhv:label name="actionList.lastUpdated">Last Updated</dhv:label></a></strong>
      <%= ActionListInfo.getSortIcon("al.modified") %>
    </th>
  </tr>
  <tr>
    <th>
      <strong><dhv:label name="global.button.complete">Complete</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="accounts.accounts_contacts_listimports.Total">Total</dhv:label></strong>
    </th>
  </tr>
<%
  Iterator j = ActionLists.iterator();
  if ( j.hasNext() ) {
  int rowid = 0;
  int i =0;
  while (j.hasNext()) {
      i++;
      rowid = (rowid != 1 ? 1 : 2);
      ActionList thisList = (ActionList) j.next();
%>
  <tr class="row<%= rowid %>">
    <td nowrap>
     <%-- Use the unique id for opening the menu, and toggling the graphics --%>
      <a href="javascript:displayMenu('select<%= i %>','menuAction','<%= thisList.getId() %>','<%=  toHtmlValue(request.getParameter("linkModuleId")) %>', '<%= viewUser %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuAction');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%">
      <a href="MyActionContacts.do?command=List&actionId=<%= thisList.getId() %>&viewUserId=<%= viewUser %>&reset=true"><%= toHtmlValue(thisList.getDescription()) %></a>
    </td>
    <td nowrap align="center">
      <%= thisList.getTotalComplete() %>
    </td>
    <td nowrap align="center">
      <%= thisList.getTotal() %>
    </td>
    <td nowrap align="center">
      <zeroio:tz timestamp="<%= thisList.getModified() %>" timeZone="<%= User.getUserRecord().getTimeZone() %>" showTimeZone="true" />
    </td>
  </tr>
<%}
}else{%>
      <tr>
        <td class="containerBody" colspan="5" valign="center">
          <dhv:label name="actionList.noActionListsFound">No Action Lists found in this view.</dhv:label>
        </td>
      </tr>
<%}%>
</table>
&nbsp;<br>
<dhv:pagedListControl object="ActionListInfo" tdClass="row1"/>

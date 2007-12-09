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
  - Version: $Id:  $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.admin.base.*,org.aspcfs.modules.contacts.base.Contact" %>
<%@ page import="org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="groupList" class="org.aspcfs.modules.admin.base.UserGroupList" scope="request"/>
<jsp:useBean id="completeGroupList" class="org.aspcfs.modules.admin.base.UserGroupList" scope="request"/>
<jsp:useBean id="campaignUserGroupListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/> 
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="campaign_user_group_maps_view_active_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  var userGroups = '';
  var currentUserGroupNames = '';
  var currentUserGroupIds = '';
<%
  Iterator iter = (Iterator) completeGroupList.iterator();
  while (iter.hasNext()) {
    UserGroup group = (UserGroup) iter.next();
%>
    userGroups = userGroups + '<%= group.getId() +","+ StringUtils.jsStringEscape(group.getName()) %>|';
    currentUserGroupIds = currentUserGroupIds +'<%= group.getId() %>|';
    currentUserGroupNames = currentUserGroupNames + '<%= StringUtils.jsStringEscape(group.getName()) %>|';
<%}%>

  function reopen() {
    window.location.href='CampaignUserGroups.do?command=ViewActive&id=<%= campaign.getId() %>';
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<a href="CampaignManager.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<a href="CampaignManager.do?command=Details&id=<%=campaign.getId()%>"><dhv:label name="campaign.campaignDetails">Campaign Details</dhv:label></a> >
<dhv:label name="usergroups.activeUserGroups">Active User Groups</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="communications" selected="access" object="campaign" param='<%= "id=" + campaign.getId() %>'>
<dhv:hasAuthority owner="<%= campaign.getEnteredBy() %>"><dhv:permission name="campaign-dashboard-view">
  <a href="javascript:popUserGroupsSelectMultiple('campaign','1','lookup_quote_remarks','<%= campaign.getId() %>',currentUserGroupIds, currentUserGroupNames,'active');"><dhv:label name="campaigns.chooseActiveUserGroups">Choose Active User Groups</dhv:label></a>
</dhv:permission></dhv:hasAuthority>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="campaignUserGroupListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th align="center">
      &nbsp;
    </th>
    <th nowrap>
      <strong><a href="CampaignUserGroups.do?command=ViewActive&id=<%= campaign.getId() %>&column=ug.group_name"><dhv:label name="quotes.productName">Name</dhv:label></a></strong>
      <%= campaignUserGroupListInfo.getSortIcon("ug.group_name") %>
    </th>
    <th align="center" nowrap>
      <strong><dhv:label name="admin.user.site">Site</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="dependency.usersingroup">Users</dhv:label></strong>
    </th>
  </tr>
<%
  Iterator i = groupList.iterator();
  if (i.hasNext()) {
    int rowid = 0;
    int count = 0;
    while (i.hasNext()) {
      count++;
      rowid = (rowid != 1?1:2);
      UserGroup thisGroup = (UserGroup) i.next();
%>
      <tr class="row<%= rowid %>" width="8">
        <td valign="center" align="center" nowrap>
          <dhv:hasAuthority owner="<%= campaign.getEnteredBy() %>">
            <a href="javascript:displayMenu('select<%= count %>','menuUserGroup','<%= campaign.getId() %>', '<%= thisGroup.getId() %>','true');" 
              onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuUserGroup');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
          </dhv:hasAuthority>
        </td>
        <td width="100%" valign="top">
          <%-- <a href="UserGroups.do?command=Details&groupId=<%= thisGroup.getId() %>"><%= toHtml(thisGroup.getName()) %></a> --%>
          <%= toHtml(thisGroup.getName()) %>
        </td>
        <td valign="top" align="center" nowrap>
          <%= SiteIdList.getSelectedValue(thisGroup.getSiteId()) %>
        </td>
        <td nowrap>
          <%= toHtml(thisGroup.getGroupUsers() != null?String.valueOf(thisGroup.getGroupUsers().size()):"") %>
        </td>
      </tr>
<%
    }
  } else {
%>
<tr>
    <td class="containerBody" valign="center" colspan="4">
      <dhv:label name="campaigns.noActiveUserGroupsSelected.text">No active user groups selected for the campaign.</dhv:label>
    </td>
  </tr>
<%  
  }
%>
</table>
<br>
<dhv:pagedListControl object="campaignUserGroupListInfo" tdClass="row1"/>
</dhv:container>

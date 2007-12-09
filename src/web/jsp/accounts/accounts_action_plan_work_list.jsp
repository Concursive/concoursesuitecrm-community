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
  - Version: $Id: accounts_action_plan_work_list.jsp
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.actionplans.base.*, org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="actionPlanWorkList" class="org.aspcfs.modules.actionplans.base.ActionPlanWorkList" scope="request"/>
<jsp:useBean id="accountActionPlanWorkListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="orgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js?v=20070827"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_action_plan_work_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  
  function reassignPlan(userId, actionPlanWork) {
    window.location.href = "AccountActionPlans.do?command=Reassign&orgId=<%= orgDetails.getOrgId() %>&actionPlanId=" + actionPlanWork + "&userId=" + userId + "&return=list<%= addLinkParams(request, "popup|popupType|actionId") %>";
  }
</SCRIPT>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%= orgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<dhv:label name="sales.actionPlans">Action Plans</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="actionplans" object="orgDetails" param='<%= "orgId=" + orgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<form name="actionPlanListView" method="post" action="AccountActionPlans.do?command=View&orgId=<%= orgDetails.getOrgId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
<dhv:permission name="accounts-action-plans-add"><a href="AccountActionPlans.do?command=Add&orgId=<%=request.getParameter("orgId")%><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="actionPlan.addActionPlan">Add Action Plan</dhv:label></a></dhv:permission>
<center><dhv:pagedListAlphabeticalLinks object="accountActionPlanWorkListInfo"/></center>
&nbsp;<br />
	<%@ include file="accounts_action_plan_work_list_include.jsp" %>
</dhv:container>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>


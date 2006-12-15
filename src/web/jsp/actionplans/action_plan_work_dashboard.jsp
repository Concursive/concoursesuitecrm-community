<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: action_plan_work_dashboard.jsp
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.actionplans.base.*"%>
<jsp:useBean id="shortChildList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="currentUser" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="actionPlan" class="org.aspcfs.modules.actionplans.base.ActionPlan" scope="request"/>
<jsp:useBean id="actionPlanList" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="actionPlanWorkList" class="org.aspcfs.modules.actionplans.base.ActionPlanWorkList" scope="request"/>
<jsp:useBean id="actionPlanWorkDashboardInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="userPhaseMap" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="actionPlanWorkListView" class="java.lang.String" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="action_plan_work_list_menu.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
  function reassignPlan(userId, actionPlanWork) {
    window.location.href = "MyActionPlans.do?command=Reassign&actionPlanId=" + actionPlanWork + "&userId=" + userId + "&return=list";
  }
  
  function changeView(view) {
    window.location.href = 'MyActionPlans.do?command=View&planView=' + view;
  }
  
  function updateGraph(select) {
    document.actionPlanDashboardView.submit();
  }
</SCRIPT>
<form name="actionPlanDashboardView" method="post" action="MyActionPlans.do?command=Dashboard">
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
      <dhv:label name="sales.actionPlans">Action Plans</dhv:label>  
    </td>
  </tr>
</table>
<%-- End Trails --%>
<table width="100%" border="0" cellspacing="0" cellpadding="3">
  <tr>
    <%-- Left Column --%>
    <td width="100%" valign="top">
      <%-- Graphic --%>
      <table width="100%" cellpadding="2" cellspacing="0" border="0" class="pagedList">
        <%-- Graph Header --%>
        <tr>
          <th valign="top" style="text-align: center;" nowrap colspan="<%= (actionPlan.getPhases().size() + 1) %>">
          <% if (request.getSession().getAttribute("plansoverride") != null) { %>
            <dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label>: <%= toHtml((String)request.getSession().getAttribute("plansothername")) %>
          <%} else {%>
            <dhv:label name="accounts.accounts_revenue_dashboard.MyDashboard">My Dashboard</dhv:label>
          <%}%>
          </th>
        </tr>
        <%-- Graph Columns --%>
        <tr class="row1">
          <td align="center" valign="middle" rowspan="2" width="50%">
            <strong><dhv:label name="contacts.name">Name</dhv:label></strong>
          </td>
          <td align="center" colspan="<%= actionPlan.getPhases().size() %>" width="50%" nowrap>
            <img src="images/icons/stock_chart-reorganize-16.gif" align="absMiddle" alt="" />
            <dhv:label name="actionPlan.numberPhasePlans">Number of plans in each phase</dhv:label>
          </td>
        </tr>
        <tr class="row1">
          <% for (int i = 1; i <= actionPlan.getPhases().size(); ++i) { %>
            <td align="center" <%= (i != actionPlan.getPhases().size() ? "style=\"border-right: 0px;border-top: 0px;\"" : "") %>><strong><%= i %></strong></td>
          <% } %>
        </tr>
        <%-- Display Current User in the dashboard graph --%>
        <tr class="sectionrow2">
          <td nowrap style="<%= shortChildList.size() != 0 ? "background-image: none;" : "" %>"><%= toHtml(currentUser.getContact().getNameFirstLast()) %>
            <dhv:evaluate if="<%= currentUser.getContact().getTitle()!= null %>">
              <dhv:evaluate if='<%= !"".equals(currentUser.getContact().getTitle().trim()) %>'>
              <%= ", " + toHtml(currentUser.getContact().getTitle()) %>
              </dhv:evaluate>
            </dhv:evaluate>
          </td>
          <% 
            HashMap phaseMap = (HashMap) userPhaseMap.get(new Integer(currentUser.getId()));
            Iterator v = actionPlan.getPhases().iterator();
            while (v.hasNext()) {
              ActionPhase thisPhase = (ActionPhase) v.next();
              int phaseCount = 0;
              if (phaseMap != null && phaseMap.get(new Integer(thisPhase.getId())) != null) {
                Integer phaseCnt = (Integer) phaseMap.get(new Integer(thisPhase.getId()));
                if (phaseCnt != null) {
                  phaseCount = phaseCnt.intValue();
                }
              }
          %>
            <td align="center" style="<%= (v.hasNext() ? "border-right: 0px;" : "") %><%= shortChildList.size() != 0 ? "background-image: none;" : "" %>"><%= phaseCount %></td>
          <%
            } 
          %>
        </tr>
        <%-- Display Child Users in the dashboard graph --%>
        <%
          Iterator p = shortChildList.iterator();
          int classCount = 0;
          while (p.hasNext()) {
            classCount = (classCount == 1 ? 2 : 1);
            User thisRec = (User) p.next();
            phaseMap = (HashMap) userPhaseMap.get(new Integer(thisRec.getId()));
        %>
        <tr class="sectionrow<%= classCount %>">
          <td nowrap <dhv:evaluate if="<%= p.hasNext() %>">style="background-image: none;"</dhv:evaluate>>
            <a href="MyActionPlans.do?command=Dashboard&oid=<%=thisRec.getId()%>"><%= toHtml(thisRec.getContact().getNameFirstLast()) %>
            <dhv:evaluate if="<%= thisRec.getContact().getTitle()!= null %>">
              <dhv:evaluate if="<%= !"".equals(thisRec.getContact().getTitle().trim()) %>">
              <%= ", " + toHtml(thisRec.getContact().getTitle()) %>
              </dhv:evaluate>
            </dhv:evaluate>
            </a>

            <dhv:evaluate if="<%=!thisRec.getEnabled() || (thisRec.getExpires() != null && thisRec.getExpires().before(new Timestamp(Calendar.getInstance().getTimeInMillis())))%>"><font color="red">*</font></dhv:evaluate>
          </td>
          <% 
            Iterator z = actionPlan.getPhases().iterator();
            while (z.hasNext()) {
              ActionPhase thisPhase = (ActionPhase) z.next();
              int phaseCount = 0;
              if (phaseMap != null && phaseMap.get(new Integer(thisPhase.getId())) != null) {
                Integer phaseCnt = (Integer) phaseMap.get(new Integer(thisPhase.getId()));
                if (phaseCnt != null) {
                  phaseCount = phaseCnt.intValue();
                }
              }
          %>
            <td align="center" style="<%= (z.hasNext() ? "border-right: 0px;" : "") %><%= (p.hasNext() ? "background-image:none;" : "") %>"><%= phaseCount %></td>
          <%
            } 
          %>
        </tr>
        <%
          }
        %>
        <dhv:evaluate if="<%= actionPlanList.size() > 1 %>">
        <tr>
          <td style="text-align: center;" nowrap colspan="<%= (actionPlan.getPhases().size() + 1) %>">
            <strong><dhv:label name="project.plan">Plan</dhv:label></strong>: <%= actionPlanList.getHtmlSelectGroup(systemStatus, "planId", actionPlan.getId()) %>
          </td>
        </tr>
        </dhv:evaluate>
      </table>
      <%-- Up a level --%>
      <table width="285" border="0" cellspacing="0" cellpadding="3">
        <tr>
          <td style="text-align: left;" width="100%" nowrap>
            <% if (!(((String)request.getSession().getAttribute("plansoverride")) == null)) {
              int prevId =  Integer.parseInt((String)request.getSession().getAttribute("planspreviousId"));
              %>
            <input type="hidden" name="oid" value="<%=((String)request.getSession().getAttribute("plansoverride"))%>">
            <img src="images/icons/stock_left-16.gif" align="absMiddle" />
            <a href="MyActionPlans.do?command=Dashboard&oid=<%=((String)request.getSession().getAttribute("planspreviousId"))%><%= ((String)request.getSession().getAttribute("planspreviousId")).equals(String.valueOf(User.getUserId())) ? "&reset=true" : ""%>">Back to Previous User</a> |
            <a href="MyActionPlans.do?command=Dashboard&reset=true"><dhv:label name="accounts.accounts_revenue_dashboard.BackMyDashboard">Back to My Dashboard</dhv:label></a>
            <% } else {%>
                &nbsp;
            <%}%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<input type="hidden" name="reset" value="false">
</form>
<form name="actionPlanListView" method="post" action="MyActionPlans.do?command=Dashboard">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
      <select size="1" name="listFilter1" onChange="javascript:document.actionPlanListView.submit();">
        <option value="true" <%= actionPlanWorkDashboardInfo.getFilterValue("listFilter1").equalsIgnoreCase("true")?" selected":"" %>><dhv:label name="actionPlan.activePlans">Active Plans</dhv:label></option>
        <option value="false" <%= actionPlanWorkDashboardInfo.getFilterValue("listFilter1").equalsIgnoreCase("false")?" selected":"" %>><dhv:label name="actionPlan.inactivePlans">Inactive Plans</dhv:label></option>
        <option value="all" <%= actionPlanWorkDashboardInfo.getFilterValue("listFilter1").equalsIgnoreCase("all")?" selected":"" %>><dhv:label name="actionPlan.anyStatusPlans">Any Status Plans</dhv:label></option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="actionPlanWorkDashboardInfo"/>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th valign="middle">
      &nbsp;
    </th>
    <th valign="middle" width="100%">
      <strong><dhv:label name="dynamicForm.name">Name</dhv:label></strong>
    </th>
    <th style="text-align: center !important" valign="middle">
      <strong><dhv:label name="actionPlan.actionRequired">Action Required</dhv:label></strong>
    </th>
    <th valign="middle">
      <strong><dhv:label name="sales.assigned">Assigned</dhv:label></strong>
    </th>
    <th valign="middle" nowrap>
      <strong><dhv:label name="actionPlan.prospectName">Prospect Name</dhv:label></strong>
    </th>
    <th style="text-align: center !important">
      <strong><dhv:label name="actionPlan.weeklyPotential">Weekly Potential</dhv:label></strong>
    </th>
    <th align="center" nowrap>
      <strong><dhv:label name="actionPlan.currentPhase">Current Phase</dhv:label></strong>
    </th>
    <th style="text-align: center !important">
      <strong><dhv:label name="actionPlan.daysInPhase">Days in Phase</dhv:label></strong>
    </th>
    <th align="center" nowrap>
      <strong><dhv:label name="actionPlan.daysActive">Days Active</dhv:label></strong>
    </th>
    <th valign="middle" nowrap>
      <strong><a href="MyActionPlans.do?command=View&column=apw.modified"><dhv:label name="actionList.lastUpdated">Last Updated</dhv:label></a></strong>
      <%= actionPlanWorkDashboardInfo.getSortIcon("apw.modified") %>
    </th>
  </tr>
 <%
  Iterator j = actionPlanWorkList.iterator();
  if ( j.hasNext() ) {
  int rowid = 0;
  int i =0;
  while (j.hasNext()) {
      i++;
      rowid = (rowid != 1 ? 1 : 2);
      ActionPlanWork thisWork = (ActionPlanWork) j.next();
%>
  <tr
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() != null && thisWork.getCurrentPhase().requiresUserAttention(User.getUserRecord().getId(), request) %>">
        class="highlightRed"
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() == null || !thisWork.getCurrentPhase().requiresUserAttention(User.getUserRecord().getId(), request) %>">
        class="row<%= rowid %>"
      </dhv:evaluate>
      >
    <td nowrap>
     <%-- Use the unique id for opening the menu, and toggling the graphics --%>
     <dhv:evaluate if="<%= thisWork.getOrganization() != null %>">
       <a href="javascript:displayMenu('select<%= i %>','menuActionPlan','<%= thisWork.getId() %>','<%= thisWork.getOrganization().getOrgId() %>','<%= thisWork.getManagerId() %>','<%= thisWork.getEnabled() ? 1 : 0 %>', <%= thisWork.getPlanSiteId() %>);" 
          onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuActionPlan');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
     </dhv:evaluate>
     <dhv:evaluate if="<%= thisWork.getOrganization() == null %>">
       &nbsp;
     </dhv:evaluate>
    </td>
    <td width="100%"><%= toHtml(thisWork.getPlanName()) %></td>
    <td align="center" nowrap>
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() != null %>">
        <dhv:evaluate if="<%= thisWork.getCurrentPhase().requiresUserAttention(User.getUserRecord().getId(), request) %>">
          <dhv:label name="account.yes">Yes</dhv:label>
        </dhv:evaluate>
        <dhv:evaluate if="<%= !thisWork.getCurrentPhase().requiresUserAttention(User.getUserRecord().getId(), request) %>">
          <dhv:label name="account.no">No</dhv:label>
        </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() == null %>">
        <dhv:label name="account.no">No</dhv:label>
      </dhv:evaluate>
    </td>
    <td nowrap>
      <dhv:username id="<%= thisWork.getAssignedTo() %>"/>
    </td>
    <td valign="top">
      <dhv:evaluate if="<%= thisWork.getOrganization() != null && hasText(thisWork.getOrganization().getName()) %>">
        <a href="MyActionPlans.do?command=Details&actionPlanId=<%= thisWork.getId() %>"><%= toHtml(thisWork.getOrganization().getName()) %></a>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisWork.getOrganization() == null && thisWork.getContact() != null && hasText(thisWork.getContact().getNameFirstLast()) %>">
        <a href="MyActionPlans.do?command=Details&actionPlanId=<%= thisWork.getId() %>"><%= toHtml(thisWork.getContact().getNameFirstLast()) %></a>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisWork.getOrganization() == null %>">
        &nbsp;
      </dhv:evaluate>
    </td>
    <td align="center">
      <dhv:evaluate if="<%= thisWork.getOrganization() != null %>">
        <zeroio:currency value="<%= thisWork.getOrganization().getPotential() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisWork.getOrganization() == null %>">
        <dhv:evaluate if="<%= thisWork.getContact() != null %>">
          <zeroio:currency value="<%= thisWork.getContact().getPotential() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
        </dhv:evaluate>
      </dhv:evaluate>
    </td>
    <td align="center">
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() != null %>">
        <%= toHtml(thisWork.getCurrentPhase().getPhaseName()) %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() == null %>">
        <dhv:label name="sales.actionPlan">Action Plan</dhv:label>
      </dhv:evaluate>
    </td>
    <td nowrap align="center">
      &nbsp;
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() != null %>">
        <%= thisWork.getCurrentPhase().getDaysInPhase() %>
      </dhv:evaluate>
    </td>
    <td nowrap align="center">
      <%= thisWork.getDaysActive() %>
    </td>
    <td nowrap align="center">
      <zeroio:tz timestamp="<%= thisWork.getModified() %>" timeZone="<%= User.getUserRecord().getTimeZone() %>"/>
    </td>
  </tr>
<%}
}else{%>
      <tr>
        <td class="containerBody" colspan="10" valign="center">
          <dhv:label name="actionPlan.noActionPlansFound">No Action Plans found in this view.</dhv:label>
        </td>
      </tr>
<%}%>
</table>
</form>
&nbsp;<br>
<dhv:pagedListControl object="actionPlanWorkDashboardInfo" tdClass="row1"/>

<%-- 
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: accounts_action_plan_work_list.jsp
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.actionplans.base.*, org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="actionPlanWorkList" class="org.aspcfs.modules.actionplans.base.ActionPlanWorkList" scope="request"/>
<jsp:useBean id="accountActionPlanWorkListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<script language="JavaScript" TYPE="text/javascript" src="javascript/popContacts.js?v=20070827"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="sales_work_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  
  function reassignPlan(userId, actionPlanWork) {
    window.location.href = "SalesActionPlans.do?command=Reassign&contactId=<%= ContactDetails.getId() %>&actionPlanId=" + actionPlanWork + "&userId=" + userId + "&return=list<%= addLinkParams(request, "popup|popupType|actionId") %>";
  }
</SCRIPT>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Sales.do"><dhv:label name="accounts.accounts1">Leads</dhv:label></a> > 
<a href="Sales.do?command=Details&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.deta324ils">Leads Details</dhv:label></a> >
<dhv:label name="sales.actionPlans">Action Plans</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="leads" selected="actionPlans" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<form name="actionPlanListView" method="post" action="SalesActionPlans.do?command=View&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
<dhv:permission name="sales-leads-action-plans-add"><a href="SalesActionPlans.do?command=Add&contactId=<%=request.getParameter("contactId")%><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="actionPlan.addAcgjstionPlan">Add Action Plan</dhv:label></a></dhv:permission>
<center><dhv:pagedListAlphabeticalLinks object="accountActionPlanWorkListInfo"/></center>
&nbsp;<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
      <select size="1" name="listFilter1" onChange="javascript:document.actionPlanListView.submit();">
        <option value="myhierarchy" <%= accountActionPlanWorkListInfo.getFilterValue("listFilter1").equalsIgnoreCase("myhierarchy")?" selected":"" %>><dhv:label name="actionPlan.myHierarchyPlans">Action Plans In My Hierarchy</dhv:label></option>
        <option value="my" <%= accountActionPlanWorkListInfo.getFilterValue("listFilter1").equalsIgnoreCase("my")?" selected":"" %>><dhv:label name="actionPlan.myActionPlans">My Action Plans</dhv:label></option>
        <option value="mymanaged" <%= accountActionPlanWorkListInfo.getFilterValue("listFilter1").equalsIgnoreCase("mymanaged")?" selected":"" %>><dhv:label name="actionPlan.myManagedActionPlans">Action Plans Managed By Me</dhv:label></option>
        <option value="mywaiting" <%= accountActionPlanWorkListInfo.getFilterValue("listFilter1").equalsIgnoreCase("mywaiting")?" selected":"" %>><dhv:label name="actionPlan.myWaitingActionPlans">Action Plans Waiting on Me</dhv:label></option>
      </select>
      <select size="1" name="listFilter2" onChange="javascript:document.actionPlanListView.submit();">
        <option value="true" <%= accountActionPlanWorkListInfo.getFilterValue("listFilter2").equalsIgnoreCase("true")?" selected":"" %>><dhv:label name="actionPlan.activePlans">Active Plans</dhv:label></option>
        <option value="false" <%= accountActionPlanWorkListInfo.getFilterValue("listFilter2").equalsIgnoreCase("false")?" selected":"" %>><dhv:label name="actionPlan.inactivePlans">Inactive Plans</dhv:label></option>
        <option value="all" <%= accountActionPlanWorkListInfo.getFilterValue("listFilter2").equalsIgnoreCase("all")?" selected":"" %>><dhv:label name="actionPlan.anyStatusPlans">Any Status Plans</dhv:label></option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="accountActionPlanWorkListInfo"/>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th valign="middle">
      &nbsp;
    </th>
    <th valign="middle">
      <strong><dhv:label name="sales.assigned">Assigned</dhv:label></strong>
    </th>
    <th valign="middle" width="25%">
      <strong><dhv:label name="dynamicForm.name">Name</dhv:label></strong>
    </th>
    <th valign="middle" nowrap>
      <strong><dhv:label name="actionPlan.actionRequired">Action Required</dhv:label></strong>
    </th>
    <dhv:include name="actionPlan.weeklyPotential" none="true">
    <th align="center" nowrap>
      <strong><dhv:label name="actionPlan.weeklyPotential">Weekly Potential</dhv:label></strong>
    </th>
    </dhv:include>
    <th align="center" nowrap>
      <strong><dhv:label name="actionPlan.currentPhase">Current Phase</dhv:label></strong>
    </th>
    <th align="center" nowrap>
      <strong><dhv:label name="actionPlan.daysInPhase">Days in Phase</dhv:label></strong>
    </th>
    <th align="center" nowrap>
      <strong><dhv:label name="actionPlan.daysActive">Days Active</dhv:label></strong>
    </th>
    <dhv:evaluate if="<%=actionPlanWorkList.getDisplayInPlanStepsCount()>0 %>">
      <th align="center" nowrap>
        <strong><dhv:label name="actionPlan.valuesFromPlan">Values from Plan</dhv:label></strong>
      </th>
    </dhv:evaluate>
    <th valign="middle" nowrap>
      <strong><a href="SalesActionPlans.do?command=View&contactId=<%= ContactDetails.getId() %>&column=apw.modified<%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="actionList.lastUpdated">Last Updated</dhv:label></a></strong>
      <%= accountActionPlanWorkListInfo.getSortIcon("apw.modified") %>
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
    <td>
     <%-- Use the unique id for opening the menu, and toggling the graphics --%>
      <a href="javascript:displayMenu('select<%= i %>','menuActionPlan','<%= thisWork.getId() %>','<%= thisWork.getManagerId() %>','<%= thisWork.getEnabled() ? 1 : 0 %>', <%= thisWork.getPlanSiteId() %>);"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuActionPlan');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td>
      <a href="SalesActionPlans.do?command=Details&actionPlanId=<%= thisWork.getId() %>&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:username id="<%= thisWork.getAssignedTo() %>"/></a>
    </td>
    <td width="25%"><%= toHtml(thisWork.getPlanName()) %></td>
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
    <dhv:include name="actionPlan.weeklyPotential" none="true">
    <td align="center">
      <dhv:evaluate if="<%= thisWork.getOrganization() != null %>">
        <zeroio:currency value="<%= thisWork.getOrganization().getPotential() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisWork.getContact() != null %>">
        <zeroio:currency value="<%= thisWork.getContact().getPotential() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </dhv:evaluate>
    </td>
    </dhv:include>
    <td align="center">
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() != null %>">
        <%= toHtml(thisWork.getCurrentPhase().getPhaseName()) %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() == null %>">
        <dhv:label name="sales.actionPlan">Action Plan</dhv:label>
      </dhv:evaluate>
    </td>
    <td align="center">
      &nbsp;
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() != null %>">
        <%= thisWork.getCurrentPhase().getDaysInPhase() %>
      </dhv:evaluate>
    </td>
    <td align="center">
      <%= thisWork.getDaysActive() %>
    </td>
    <dhv:evaluate if="<%=actionPlanWorkList.getDisplayInPlanStepsCount()>0 %>">
      <td>
        <%
            Iterator steps = thisWork.getSteps().iterator();
            while (steps.hasNext()) {
              ActionItemWork thisItemWork = (ActionItemWork) steps.next();
              ActionStep thisStep = thisItemWork.getStep();
              if (thisStep!=null && thisStep.getDisplayInPlanList()){
                if (steps.hasNext()) { %>         
                  <%@ include file="../actionplans/action_plan_work_display_in_plan_include.jsp" %> <br />
                <%}else{%>
                  <%@ include file="../actionplans/action_plan_work_display_in_plan_include.jsp" %> 
                <%} 
              }
           }%>
      </td>
    </dhv:evaluate>
    <td align="center">
      <zeroio:tz timestamp="<%= thisWork.getModified() %>" timeZone="<%= User.getUserRecord().getTimeZone() %>"/>
    </td>
  </tr>
<%}
}else{%>
      <tr>
        <td class="containerBody" colspan="9" valign="center">
          <dhv:label name="actionPlan.noActionPlansFound">No Action Plans found in this view.</dhv:label>
        </td>
      </tr>
<%}%>
</table>
</form>
&nbsp;<br>
<dhv:pagedListControl object="accountActionPlanWorkListInfo"/>
<input type="hidden" name="orgId" value="<%= ContactDetails.getId() %>">
</dhv:container>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>


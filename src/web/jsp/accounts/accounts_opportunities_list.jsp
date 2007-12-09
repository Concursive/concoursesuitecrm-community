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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.pipeline.base.*,org.aspcfs.modules.pipeline.beans.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="OpportunityHeaderList" class="org.aspcfs.modules.pipeline.base.OpportunityHeaderList" scope="request"/>
<jsp:useBean id="OpportunityPagedInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="accessTypeList" class="org.aspcfs.modules.admin.base.AccessTypeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_opportunities_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');

  function reopenOpportunity(id) {
    scrollReload('Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %><%= isPopup(request)?"&popup=true":"" %>');
    return id;
  }
</script>
<%
  boolean allowMultiple = allowMultipleComponents(pageContext, OpportunityComponent.MULTPLE_CONFIG_NAME, "multiple");
%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="opportunities" hideContainer='<%="true".equals(request.getParameter("actionplan")) %>' object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>'>
  <dhv:evaluate if="<%= !OrgDetails.isTrashed() %>" >
    <dhv:permission name="accounts-accounts-opportunities-add">
      <a href="Opportunities.do?command=Add&orgId=<%= request.getParameter("orgId") %><%= isPopup(request)?"&popup=true&popupType=inline":"" %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.AddAnOpportunity">Add an Opportunity</dhv:label></a>
    </dhv:permission>
  </dhv:evaluate>
  <dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
  <center><dhv:pagedListAlphabeticalLinks object="OpportunityPagedInfo"/></center></dhv:include>
  <table width="100%" border="0">
    <tr>
      <form name="listView" method="post" action="Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
      <td align="left">
        <select size="1" name="listView" onChange="javascript:document.listView.submit();">
          <option <%= OpportunityPagedInfo.getOptionValue("all") %>><dhv:label name="accounts.accounts_contacts_oppcomponent_list.AllOpenOpportunities">All Open Opportunities</dhv:label></option>
          <option <%= OpportunityPagedInfo.getOptionValue("closed") %>><dhv:label name="accounts.accounts_contacts_oppcomponent_list.AllClosedOpportunities">All Closed Opportunities</dhv:label></option>
          <option <%= OpportunityPagedInfo.getOptionValue("my") %>><dhv:label name="accounts.accounts_contacts_oppcomponent_list.MyOpenOpportunities">My Open Opportunities</dhv:label> </option>
        </select>
      </td>
      <td>
        <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="OpportunityPagedInfo"/>
      </td>
      </form>
    </tr>
  </table>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th width="8" nowrap>
        &nbsp;
      </th>
      <th width="100%" nowrap>
        <strong><a href="Opportunities.do?command=View&orgId=<%= OrgDetails.getId() %>&column=x.description<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.OpportunityName">Opportunity Name</dhv:label></a></strong>
        <%= OpportunityPagedInfo.getSortIcon("x.description") %>
      </th>
      <th nowrap>
        <strong><dhv:label name="account.opportunities.associatedWith">Associated With</dhv:label></strong>
      </th>
      <dhv:evaluate if="<%=!allowMultiple%>" >
        <th nowrap>
          <strong><dhv:label name="accounts.accounts_contacts_oppcomponent_list.stage">Stage</dhv:label></strong>
        </th>
        <th nowrap>
          <strong><a href="Opportunities.do?command=View&orgId=<%= OrgDetails.getId() %>&column=oc.closedate<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>"><dhv:label name="accounts.accounts_contacts_opps_details.CloseDate">Close Date</dhv:label></strong>
          <%= OpportunityPagedInfo.getSortIcon("oc.closedate") %>
        </th>
        <th nowrap>
          <strong><dhv:label name="accounts.accounts_contacts_oppcomponent_list.BestGuessTotal">Best Guess Total</dhv:label></strong>
        </th>
      </dhv:evaluate>
      <dhv:include name="pipeline-custom1Integer" none="true">
        <th nowrap>
          <strong><dhv:label name="pipeline.custom1Integer">Custom1 Integer</dhv:label></strong>
        </th>
      </dhv:include>
      <th nowrap>
        <strong><a href="Opportunities.do?command=View&orgId=<%= OrgDetails.getId() %>&column=x.modified<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.LastModified">Last Modified</dhv:label></a></strong>
        <%= OpportunityPagedInfo.getSortIcon("x.modified") %>
      </th>
    </tr>
  <%
    Iterator j = OpportunityHeaderList.iterator();
    FileItem thisFile = new FileItem();
    if ( j.hasNext() ) {
      int rowid = 0;
      int i = 0;
      HashMap headersListed = new HashMap();
      while (j.hasNext()) {
        i++;
        rowid = (rowid != 1?1:2);
        OpportunityHeader oppHeader = (OpportunityHeader)j.next();
        boolean hasPermission = false;
  %>
  <dhv:hasAuthority owner="<%= oppHeader.getManager() %>">
    <% hasPermission = true; %>
  </dhv:hasAuthority>
    <tr class="row<%= rowid %>">
      <td width="8" valign="center" nowrap>
        <% if(!oppHeader.getLock()){%>
          <%-- Use the unique id for opening the menu, and toggling the graphics --%>
          <%-- To display the menu, pass the actionId, accountId and the contactId--%>
          <a href="javascript:displayMenu('select<%= i %>','menuOpp','<%= OrgDetails.getId() %>','<%= oppHeader.getId() %>','<%= oppHeader.isTrashed() %>','<%= hasPermission %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuOpp');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
         <% }else{ %>
          <font color="red"><dhv:label name="pipeline.locked">Locked</dhv:label></font>
         <% } %>
      </td>
      <td valign="center">
          <a href="Opportunities.do?command=Details&headerId=<%= oppHeader.getId() %>&orgId=<%= OrgDetails.getId() %>&reset=true<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>">
          <%= toHtml(oppHeader.getDescription()) %></a>
          (<%= oppHeader.getComponentCount() %>)
        <dhv:evaluate if="<%= oppHeader.hasFiles() %>">
        <%= thisFile.getImageTag("-23") %>
        </dhv:evaluate>
      </td>
      <dhv:evaluate if="<%=oppHeader.getAccountLink() != -1 %>">
        <td valign="center" align="right" nowrap>
          <%= toHtml(oppHeader.getAccountName()) %>
        </td>
      </dhv:evaluate>
      <dhv:evaluate if="<%=oppHeader.getContactLink() != -1 %>">
        <td valign="center" align="right" nowrap>
          <%= toHtml(oppHeader.getContactName()) %>
        </td>
      </dhv:evaluate>
      <dhv:evaluate if="<%=!allowMultiple%>" >
        <td valign="center" nowrap>
          <%= toHtml(oppHeader.getComponent().getStageName()) %>&nbsp;
        </td>
      </dhv:evaluate>
      <dhv:evaluate if="<%=!allowMultiple%>" >
        <td valign="center" nowrap>
          <zeroio:tz timestamp="<%= oppHeader.getComponent().getCloseDate() %>" dateOnly="true" timeZone="<%= oppHeader.getComponent().getCloseDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
          <% if(!User.getTimeZone().equals(oppHeader.getComponent().getCloseDateTimeZone())){%>
          <br />
          <zeroio:tz timestamp="<%= oppHeader.getComponent().getCloseDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
          <% } %>
        </td>
        <td valign="top" align="right" nowrap>
          <zeroio:currency value="<%= oppHeader.getComponent().getGuess() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
        </td>
      </dhv:evaluate>
      <dhv:include name="pipeline-custom1Integer" none="true">
        <td valign="top" align="right" class="row<%= rowid %>" nowrap>
          <%= oppHeader.getCustom1Integer() %>&nbsp;
        </td>
      </dhv:include>
      <td valign="center" nowrap>
        <zeroio:tz timestamp="<%= oppHeader.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
      </td>
    </tr>
  <%}%>
  <%} else {%>
    <tr class="containerBody">
      <dhv:include name="pipeline-custom1Integer" none="true">
        <td colspan="8">
      </dhv:include>
      <dhv:include name="pipeline-custom1Integer">
        <td colspan="6">
      </dhv:include>
        <dhv:label name="accounts.accounts_contacts_oppcomponent_list.NoOpportunitiesFound">No opportunities found.</dhv:label>
      </td>
    </tr>
  <%}%>
  </table>
  <br>
  <dhv:pagedListControl object="OpportunityPagedInfo"/>
</dhv:container>

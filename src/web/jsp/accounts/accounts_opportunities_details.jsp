<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.pipeline.base.OpportunityComponent,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="ComponentList" class="org.aspcfs.modules.pipeline.base.OpportunityComponentList" scope="request"/>
<jsp:useBean id="AccountsComponentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_opportunities_details_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>">Opportunities</a> >
Opportunity Details
</td>
</tr>
</table>
<%-- End Trails --%>
<%-- Begin container --%>
<%@ include file="accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="opportunities" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <%-- Begin container content --%>
      <img src="images/icons/stock_form-currency-field-16.gif" border="0" align="absmiddle">
      <strong><%= toHtml(opportunityHeader.getDescription()) %></strong>
      <% FileItem thisFile = new FileItem(); %>
      <dhv:evaluate if="<%= opportunityHeader.hasFiles() %>">
        <%= thisFile.getImageTag("-23") %>
      </dhv:evaluate>
      <br>
      <dhv:permission name="accounts-accounts-opportunities-add">
        <br>
        <a href="OpportunitiesComponents.do?command=Prepare&headerId=<%= opportunityHeader.getId() %>&orgId=<%= OrgDetails.getOrgId() %>">Add a Component</a><br>
      </dhv:permission>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AccountsComponentListInfo"/>
 <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
   <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th nowrap>
      <strong><a href="Opportunities.do?command=Details&headerId=<%= opportunityHeader.getId() %>&orgId=<%= OrgDetails.getId() %>&column=oc.description">Component</a></strong>
      <%= AccountsComponentListInfo.getSortIcon("oc.description") %>
    </th>
    <th nowrap>
      <strong><a href="Opportunities.do?command=Details&headerId=<%= opportunityHeader.getId() %>&orgId=<%= OrgDetails.getId() %>&column=oc.closed">Status</a></strong>
      <%= AccountsComponentListInfo.getSortIcon("oc.closed") %>
    </th>
    <th nowrap>
      <strong><a href="Opportunities.do?command=Details&headerId=<%= opportunityHeader.getId() %>&orgId=<%= OrgDetails.getId() %>&column=oc.guessvalue">Guess Amount</a></strong>
      <%= AccountsComponentListInfo.getSortIcon("oc.guessvalue") %>
    </th>
    <th nowrap>
      <strong><a href="Opportunities.do?command=Details&headerId=<%= opportunityHeader.getId() %>&orgId=<%=OrgDetails.getId()%>&column=oc.closedate">Close Date</a></strong>
      <%= AccountsComponentListInfo.getSortIcon("oc.closedate") %>
    </th>
    <th nowrap>
      <strong><a href="Opportunities.do?command=Details&headerId=<%= opportunityHeader.getId() %>&orgId=<%=OrgDetails.getId()%>&column=stagename">Current Stage</a></strong>
      <%= AccountsComponentListInfo.getSortIcon("stagename") %>
    </th>
    <th>
      <strong>Owner</strong>
    </th>
  </tr>
<%
	Iterator j = ComponentList.iterator();
	if (j.hasNext()) {
		int rowid = 0;
    int i = 0;
	    while (j.hasNext()) {
        i++;
        rowid = (rowid != 1?1:2);
        OpportunityComponent oppComponent = (OpportunityComponent)j.next();
%>
  <tr class="row<%= rowid %>">
    <td width="8" valign="top" align="center" nowrap>
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
      <%-- To display the menu, pass the actionId, accountId and the contactId--%>
      <a href="javascript:displayMenu('select<%= i %>','menuOpp','<%= OrgDetails.getId() %>','<%= oppComponent.getId() %>', '<%= opportunityHeader.getId() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuOpp');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%" valign="top">
      <a href="OpportunitiesComponents.do?command=DetailsComponent&orgId=<%= OrgDetails.getId() %>&id=<%=oppComponent.getId()%>">
      <%= toHtml(oppComponent.getDescription()) %></a>
    </td>
    <td valign="top" align="center" nowrap>
      <%= oppComponent.getClosed() != null ? "<font color=\"red\">closed</font>" : "<font color=\"green\">open</font>" %>
    </td>
    <td valign="top" align="right" nowrap>
      <zeroio:currency value="<%= oppComponent.getGuess() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
    </td>
    <td valign="top" align="center" nowrap>
      <zeroio:tz timestamp="<%= oppComponent.getCloseDate() %>" dateOnly="true" default="&nbsp;"/>
    </td>
    <td valign="top" align="center" nowrap>
      <%= toHtml(oppComponent.getStageName()) %>
    </td>
    <td valign="top" align="center" nowrap>
      <dhv:username id="<%= oppComponent.getOwner() %>"/>
    </td>
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="7">
      No opportunity components found.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="AccountsComponentListInfo"/>
&nbsp;<br>
<dhv:permission name="accounts-accounts-opportunities-edit"><input type="button" value="Rename Opportunity" onClick="javascript:window.location.href='Opportunities.do?command=Modify&headerId=<%= opportunityHeader.getId() %>&orgId=<%= OrgDetails.getId() %>';"></dhv:permission>
<dhv:permission name="accounts-accounts-opportunities-delete"><input type="button" value="Delete Opportunity" onClick="javascript:popURLReturn('Opportunities.do?command=ConfirmDelete&orgId=<%= OrgDetails.getId() %>&headerId=<%= opportunityHeader.getId() %>&popup=true','Opportunities.do?command=View&orgId=<%= OrgDetails.getId() %>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
</td>
</tr>
</table>


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
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.HtmlSelect, org.aspcfs.modules.base.Constants" %>
<%@ page import="org.aspcfs.modules.accounts.base.*" %>
<%@ page import="org.aspcfs.modules.pipeline.base.*" %>
<%@ page import="com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.modules.pipeline.beans.OpportunityBean" %>
<jsp:useBean id="OpportunityList" class="org.aspcfs.modules.pipeline.base.OpportunityList" scope="request"/>
<jsp:useBean id="SearchOppListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="disabledOrgs" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="LeadsViewOpp_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');

  function reopenOpportunity(id) {
    scrollReload('Leads.do?command=Search');
    return id;
  }
</script>
<%-- Trails --%>
<%
  boolean allowMultiple = allowMultipleComponents(pageContext, OpportunityComponent.MULTPLE_CONFIG_NAME, "multiple");
%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Leads.do"><dhv:label name="pipeline.pipeline">Pipeline</dhv:label></a> >
  <a href="Leads.do?command=SearchForm"><dhv:label name="">Search Form</dhv:label></a> >
  <dhv:label name="accounts.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <dhv:label name="pipeline.viewpoint.colon" param='<%= "username="+PipelineViewpointInfo.getVpUserName() %>'><b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b></dhv:label><br />
  &nbsp;<br />
</dhv:evaluate>
<dhv:permission name="pipeline-opportunities-add"><a href="Leads.do?command=Prepare&source=list"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.AddAnOpportunity">Add an Opportunity</dhv:label></a></dhv:permission>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="SearchOppListInfo"/></center></dhv:include>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOppListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th valign="center">
      &nbsp;
    </th>
    <th valign="center" nowrap>
      <strong><a href="Leads.do?command=Search&column=oc.description"><dhv:label name="accounts.accounts_contacts_opps_details.Component">Component</dhv:label></a></strong>
      <%= SearchOppListInfo.getSortIcon("oc.description") %>
    </th>
    <dhv:include name="opportunity.singleComponent">
      <th valign="center" nowrap>
        <strong><a href="Leads.do?command=Search&column=highvalue"><dhv:label name="pipeline.highAmount">High Amount</dhv:label></a></strong>
        <%= SearchOppListInfo.getSortIcon("highvalue") %>
      </th>
    </dhv:include>
    <th valign="center" nowrap>
      <strong><a href="Leads.do?command=Search&column=guessvalue"><dhv:label name="accounts.accounts_contacts_opps_details.GuessAmount">Guess Amount</dhv:label></a></strong>
      <%= SearchOppListInfo.getSortIcon("guessvalue") %>
    </th>
    <th valign="center" nowrap>
      <strong><a href="Leads.do?command=Search&column=closeprob"><dhv:label name="reports.pipeline.probability">Probability</dhv:label></a></strong>
      <%= SearchOppListInfo.getSortIcon("closeprob") %>
    </th>
    <th valign="center" nowrap>
      <strong><a href="Leads.do?command=Search&column=closedate"><dhv:label name="accounts.accounts_contacts_opps_details.CloseDate">Close Date</dhv:label></a></strong>
      <%= SearchOppListInfo.getSortIcon("closedate") %>
    </th>
    <dhv:include name="opportunity.termsAndUnits" none="true">
      <th valign="center" nowrap>
        <strong><a href="Leads.do?command=Search&column=terms"><dhv:label name="reports.pipeline.term">Term</dhv:label></a></strong>
        <%= SearchOppListInfo.getSortIcon("terms") %>
      </th>
    </dhv:include>
    <th valign="center" nowrap>
      <strong><%--<a href="Leads.do?command=Search&column=owner">--%><dhv:label name="accounts.accounts_contacts_detailsimport.Owner">Owner</dhv:label><%--</a>--%></strong>
<%--        <%= SearchOppListInfo.getSortIcon("owner") %> --%>
    </th>
    <th valign="center" nowrap>
      <strong>Organization</strong>
    </th>
    <th valign="center" nowrap>
      <strong><a href="Leads.do?command=Search&column=ct.namelast"><dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label></a></strong>
      <%= SearchOppListInfo.getSortIcon("ct.namelast") %>
    </th>
  </tr>
<%
	Iterator j = OpportunityList.iterator();
  FileItem thisFile = new FileItem();
	if ( j.hasNext() ) {
		int rowid = 0;
    int i =0;
    while (j.hasNext()) {
      i++;
		  rowid = (rowid != 1?1:2);
      OpportunityBean thisOpp = (OpportunityBean) j.next();
      Organization disabledOrg = null;
      if (thisOpp.getHeader().getAccountLink() > -1) {
        disabledOrg = disabledOrgs.getOrgById(thisOpp.getHeader().getAccountLink());
      }
      boolean hasPermission = false;
%>
  <dhv:hasAuthority owner="<%= thisOpp.getHeader().getManager() %>">
    <% hasPermission = true;%>
  </dhv:hasAuthority>
	<tr bgcolor="white">
    <td width="8" valign="top" nowrap class="row<%= rowid %>">
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <% if(!thisOpp.getHeader().getLock()){%>
         <a href="javascript:displayMenu('select<%= i %>','menuOpp', '<%= thisOpp.getHeader().getId() %>','<%= thisOpp.getComponent().getId() %>', '<%= thisOpp.getComponent().isTrashed() %>', '<%= hasPermission %>');"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuOpp');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
       <% }else{ %>
        <font color="red"><dhv:label name="pipeline.locked">Locked</dhv:label></font>
       <% } %>
    </td>
    <td width="33%" valign="top" class="row<%= rowid %>">
      <a href="Leads.do?command=DetailsOpp&headerId=<%= thisOpp.getHeader().getId() %>&reset=true">
      <dhv:evaluate if="<%=allowMultiple%>" >
        <%= toHtml(thisOpp.getComponent().getDescription()) %></a>
      </dhv:evaluate>
      <dhv:evaluate if="<%=!allowMultiple%>" >
        <%= toHtml(thisOpp.getHeader().getDescription()) %></a>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisOpp.getHeader().hasFiles() %>">
        <%= thisFile.getImageTag("-23")%>
      </dhv:evaluate>
    </td>
    <dhv:include name="opportunity.singleComponent">
      <td valign="top" align="right" nowrap class="row<%= rowid %>">
        <zeroio:currency value="<%= thisOpp.getComponent().getHigh() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </dhv:include>
    <td valign="top" align="right" nowrap class="row<%= rowid %>">
      <zeroio:currency value="<%= thisOpp.getComponent().getGuess() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
    </td>
    <td valign="top" align="center" nowrap class="row<%= rowid %>">
      <%= thisOpp.getComponent().getCloseProbValue() %>%
    </td>
    <td valign="top" align="center" nowrap class="row<%= rowid %>">
      <zeroio:tz timestamp="<%= thisOpp.getComponent().getCloseDate() %>" dateOnly="true" timeZone="<%= thisOpp.getComponent().getCloseDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(thisOpp.getComponent().getCloseDateTimeZone())){%>
      <br />
      <zeroio:tz timestamp="<%= thisOpp.getComponent().getCloseDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } %>
    </td>
    <dhv:include name="opportunity.termsAndUnits" none="true">
      <td valign="top" align="center" nowrap class="row<%= rowid %>">
        <%= toHtml(thisOpp.getComponent().getTermsString()) %>
        <dhv:evaluate if='<%= thisOpp.getComponent().getUnits().equals("M") %>'>
           <dhv:label name="accounts.accounts_contacts_oppcomponent_details.months">months</dhv:label>
        </dhv:evaluate><dhv:evaluate if='<%= thisOpp.getComponent().getUnits().equals("W") %>'>
          <dhv:label name="accounts.accounts_contacts_oppcomponent_details.weeks">weeks</dhv:label>
        </dhv:evaluate>
      </td>
    </dhv:include>
    <td valign="top" align="center" nowrap class="row<%= rowid %>">
      <dhv:username id="<%= thisOpp.getComponent().getOwner() %>" />
    </td>
    <td width="33%" valign="top" class="row<%= rowid %>">
      <dhv:evaluate if="<%= thisOpp.getHeader().getAccountLink() > -1 %>">
        <a href="Accounts.do?command=Details&orgId=<%= thisOpp.getHeader().getAccountLink() %>">
          <%= toHtml(thisOpp.getHeader().getAccountName()+(disabledOrg != null && !disabledOrg.getEnabled() ?" (X)":"")) %>
        </a>
      </dhv:evaluate>
      &nbsp;
    </td>
    <td width="33%" valign="top" class="row<%= rowid %>">
      <dhv:evaluate if="<%= thisOpp.getHeader().getContactLink() > -1 %>">
        <a href="ExternalContacts.do?command=ContactDetails&id=<%= thisOpp.getHeader().getContactLink() %>">
          <%= toHtml(thisOpp.getHeader().getDisplayName()) %></a>
      </dhv:evaluate>
      &nbsp;
    </td>
  </tr>
<%
    }
  } else {%>
  <tr class="containerBody">
    <td colspan="10" valign="center">
      <dhv:label name="pipeline.noOpportunitiesFound.text">No opportunities found with the specified search parameters.</dhv:label><br />
      <a href="Leads.do?command=SearchForm"><dhv:label name="accounts.accounts_list.ModifySearch">Modify Search</dhv:label></a>.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="SearchOppListInfo" tdClass="row1"/>

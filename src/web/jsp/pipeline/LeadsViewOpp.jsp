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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.HtmlSelect" %>
<%@ page import="org.aspcfs.modules.pipeline.base.*" %>
<%@ page import="com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.modules.pipeline.beans.OpportunityBean" %>
<jsp:useBean id="OpportunityList" class="org.aspcfs.modules.pipeline.base.OpportunityList" scope="request"/>
<jsp:useBean id="SearchOppListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
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
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Leads.do">Pipeline</a> >
Search Results
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
</dhv:evaluate>
<dhv:permission name="pipeline-opportunities-add"><a href="Leads.do?command=Prepare&source=list">Add an Opportunity</a></dhv:permission>
<center><%= SearchOppListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="SearchOppListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th valign="center">
      <strong>Action</strong>
    </th>
    <th valign="center" nowrap>
      <strong><a href="Leads.do?command=Search&column=x.description">Component</a></strong>
      <%= SearchOppListInfo.getSortIcon("x.description") %>
    </th>
    <th valign="center" nowrap>
      <strong><a href="Leads.do?command=Search&column=guessvalue">Amount</a></strong>
      <%= SearchOppListInfo.getSortIcon("guessvalue") %>
    </th>
    <th valign="center" nowrap>
      <strong><a href="Leads.do?command=Search&column=closeprob">Prob.</a></strong>
      <%= SearchOppListInfo.getSortIcon("closeprob") %>
    </th>
    <th valign="center" nowrap>
      <strong><a href="Leads.do?command=Search&column=closedate">Close Date</a></strong>
      <%= SearchOppListInfo.getSortIcon("closedate") %>
    </th>
    <th valign="center" nowrap>
      <strong><a href="Leads.do?command=Search&column=terms">Term</a></strong>
      <%= SearchOppListInfo.getSortIcon("terms") %>
    </th>
    <th valign="center" nowrap>
      <strong>Organization</strong>
    </th>
    <th valign="center" nowrap>
      <strong><a href="Leads.do?command=Search&column=ct.namelast">Contact</a></strong>
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
%>      
	<tr bgcolor="white">
    <td width="8" valign="top" nowrap class="row<%= rowid %>">
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenu('select<%= i %>','menuOpp', '<%= thisOpp.getHeader().getId() %>','<%= thisOpp.getComponent().getId() %>');"
       onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuOpp');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td width="33%" valign="top" class="row<%= rowid %>">
      <a href="Leads.do?command=DetailsOpp&headerId=<%= thisOpp.getHeader().getId() %>&reset=true">
      <%= toHtml(thisOpp.getComponent().getDescription()) %></a>
      <dhv:evaluate if="<%= thisOpp.getHeader().hasFiles() %>">
        <%= thisFile.getImageTag("-23")%>
      </dhv:evaluate>
    </td>
    <td valign="top" align="right" nowrap class="row<%= rowid %>">
      <zeroio:currency value="<%= thisOpp.getComponent().getGuess() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
    </td>
    <td valign="top" align="center" nowrap class="row<%= rowid %>">
      <%= thisOpp.getComponent().getCloseProbValue() %>%
    </td>
    <td valign="top" align="center" nowrap class="row<%= rowid %>">
      <zeroio:tz timestamp="<%= thisOpp.getComponent().getCloseDate() %>" dateOnly="true" timeZone="<%= thisOpp.getComponent().getCloseDateTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(thisOpp.getComponent().getCloseDateTimeZone())){%>
      <br />
      <zeroio:tz timestamp="<%= thisOpp.getComponent().getCloseDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% } %>
    </td>
    <td valign="top" align="center" nowrap class="row<%= rowid %>">
      <%= toHtml(thisOpp.getComponent().getTermsString()) %>
    </td>
    <td width="33%" valign="top" class="row<%= rowid %>">
      <dhv:evaluate if="<%= thisOpp.getHeader().getAccountLink() > -1 %>">
        <a href="Opportunities.do?command=View&orgId=<%= thisOpp.getHeader().getAccountLink() %>">
          <%= toHtml(thisOpp.getHeader().getAccountName()) %>
        </a>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisOpp.getHeader().getContactLink() > -1 && hasText(thisOpp.getHeader().getContactCompanyName()) %>">
        <a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= thisOpp.getHeader().getContactLink() %>">
          <%= toHtml(thisOpp.getHeader().getContactCompanyName()) %>
        </a>
      </dhv:evaluate>
      &nbsp;
    </td>
    <td width="33%" valign="top" class="row<%= rowid %>">
      <dhv:evaluate if="<%= thisOpp.getHeader().getContactLink() > -1 %>">
        <a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= thisOpp.getHeader().getContactLink() %>">
          <%= toHtml(thisOpp.getHeader().getContactName()) %>
        </a>
      </dhv:evaluate>
      &nbsp;
    </td>
  </tr>
<%
    }
  } else {%>
  <tr class="containerBody">
    <td colspan="8" valign="center">
      No opportunities found with the specified search parameters.<br />
      <a href="Leads.do?command=SearchForm">Modify Search</a>.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="SearchOppListInfo" tdClass="row1"/>


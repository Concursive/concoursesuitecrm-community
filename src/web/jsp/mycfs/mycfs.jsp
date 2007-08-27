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
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*,org.aspcfs.modules.accounts.base.NewsArticle,org.aspcfs.modules.mycfs.beans.*" %>
<%@ page import="org.aspcfs.modules.quotes.base.*" %>
<%@ page import="org.aspcfs.modules.troubletickets.base.*" %>
<%@page import="org.aspcfs.modules.sync.utils.SyncUtils"%>
<jsp:useBean id="NewsList" class="org.aspcfs.modules.accounts.base.NewsArticleList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="NewUserList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="IndSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="syncClient" class="org.aspcfs.modules.service.base.SyncClient" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application" />

<jsp:useBean id="historyList" class="org.aspcfs.modules.contacts.base.ContactHistoryList" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="contactHistoryListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></script>
<%@ include file="../initPage.jsp" %>
<%
  //returnPage specifies the source of the request ( Accounts/ Home Page ) 
  String returnPage = request.getParameter("return");
   CalendarBean CalendarInfo = (CalendarBean) session.getAttribute(returnPage != null ?returnPage + "CalendarInfo" :"CalendarInfo");
   if(CalendarInfo==null){
       CalendarInfo = new CalendarBean();
       session.setAttribute(returnPage!=null?returnPage + "CalendarInfo":"CalendarInfo",CalendarInfo);
   }
%>
<script type="text/javascript">
  function fillFrame(frameName,sourceUrl){
    window.frames[frameName].location.href=sourceUrl;
  }

  function reopen(){
    window.location.href='MyCFS.do?command=Home';
  }

  function reloadFrames(){
    window.frames['calendar'].location.href='MyCFS.do?command=MonthView&source=Calendar&inline=true&reloadCalendarDetails=true';
  }
</script>

<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <dhv:evaluate if="<%= SyncUtils.isOfflineMode(applicationPrefs) %>">
    <tr>
      <td>
        <table class="note" cellspacing="0"><tr>
          <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
          <td>
            <dhv:evaluate if="<%= SyncUtils.isSyncConflict(applicationPrefs) %>">
              <dhv:label name="login.offline.invalid.state">
                The Offline Centric CRM system is in an invalid state. Your prior sync was not successful
                and the database might be in an un-reliable state. You can continue to review your
                offline data, but will not be able to perform any future syncs and add, edit or delete
                data. You need to re-install the system by clicking on "Reload" on your Desktop Client.
	            </dhv:label>
            </dhv:evaluate>
            <dhv:evaluate if="<%= !SyncUtils.isSyncConflict(applicationPrefs) %>">
              <dhv:label name="login.offline.msg">
	              You are currently logged in using offline mode. Some of the features have been turned off intentionally and may
	              not be available during offline mode. When you regain connectivity, you can perform a sync with your Centric CRM
	              Server to be up-to-date.
	            </dhv:label><br /><br />
	          <dhv:label name="login.offline.lastSyncedOn">Last Synced on</dhv:label>: <%= syncClient.getAnchor() %>&nbsp;&nbsp;
	          <input type="button" value="<dhv:label name="global.button.Sync">Sync</dhv:label>" onClick="javascript:window.location.href='RequestSyncUpdates.do?command=Default'"/>
           </dhv:evaluate>
          </td>
        </tr></table>
      </td>
    </tr>
  </dhv:evaluate>
    <tr>
      <td>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
  <%-- User Selected Info --%>
  <dhv:permission name="products-view" none="true">
  <tr>
    <td width="100%" valign="top" colspan="2" height="20" style="text-align: center !important">
      <strong><div id="userName"><dhv:label name="accounts.accounts_dashboard.ScheduledActionsFor">Scheduled Actions for</dhv:label> <%= CalendarInfo.getSelectedUserId()!=-1?toHtml(CalendarInfo.getSelectedUserName()) : toHtml(User.getUserRecord().getContact().getNameLastFirst())%></div></strong>
    </td>
  </tr>
  </dhv:permission>
  <%-- AdsJet users only --%>
  <dhv:permission name="products-view">
  <tr>
    <td colspan="2">
      <table class="pagedListHeader" cellspacing="0">
        <tr>
          <td align="center">
            <strong>Welcome to AdsJet.com.</strong>
          </td>
        </tr>
      </table>
      <table class="pagedListHeader2" cellspacing="0">
        <tr>
          <th>
            You are currently at the "My Home Page" tab in which you can 
            review the status of your pending ad requests and orders.
            From the "Products &amp; Services" tab you can review 
            publication information, as well as manage and place ads.
          </th>
        </tr>
      </table>
    </td>
  </tr>
  </dhv:permission>
  <%-- Calendar and Details --%>
  <tr valign="top">
    <%-- Left cell --%>
    <td valign="top" width="320">
      <iframe id="calendarid" name="calendar" frameborder="0" marginwidth="0" marginheight="0" width="320" height="400" src="MyCFS.do?command=MonthView&source=Calendar<%= returnPage != null ? "&return="+returnPage : "" %>&reloadCalendarDetails=true">
      </iframe>
    </td>
    <%-- Right cell --%>
    <td valign="top" height="380" width="100%"><%-- Change height to 100% once Safari works in all places --%>
      <%-- Calendar details --%>
      <table height="380" width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="100%" class="cellBox">
            <table width="100%" cellspacing="4" cellpadding="0" border="0" class="empty">
              <tr>
                <td valign="center" nowrap>
                 <select id="alerts" size="1" name="alertsView" onChange="javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=calendardetails&inline=true&alertsView='+document.getElementById('alerts').value);">
                  <option value="all" <%= CalendarInfo.getCalendarDetailsView().equalsIgnoreCase("all") ? " selected":"" %>><dhv:label name="calendar.pendingItems">Pending Items</dhv:label></option>
                   <%
                    Iterator alertTypes = CalendarInfo.getAlertTypes().iterator();
                    while(alertTypes.hasNext()){
                    AlertType thisAlert = (AlertType)alertTypes.next();
                    boolean isSelected = CalendarInfo.getCalendarDetailsView().equalsIgnoreCase(thisAlert.getName());
                   %>
                      <option value="<%= thisAlert.getName() %>" <%= isSelected ? " selected":"" %>><%= thisAlert.getDisplayName() %></option>
                   <%
                    }
                   %>
                 </select>
                <% if(NewUserList.size()>1){%>
                  <%= NewUserList.getHtml("userId",CalendarInfo.getSelectedUserId()) %>
                <%}%>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td width="100%" valign="top" height="380">
            <iframe id="calendardetailsid" name="calendardetails" frameborder="0" marginheight="0" width="100%" height="380" src="empty.html">
            </iframe>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

    </td>
  </tr>
</table>
<%-- Next section --%>
<dhv:permission name="myhomepage-miner-view">
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <td>
      <input type="hidden" name="command" value="Home">
      <table cellpadding="0" cellspacing="0" border="0" width="100%">
        <form name="miner_select" type="get" action="MyCFS.do">
          <tr>
            <th width="60%" valign="center">
              <strong><dhv:label name="calendar.personalizedNewsAndEvents" param="amp=&amp;">Personalized Industry News &amp; Events</dhv:label></strong>
            </th>
            <td width="40%" style="text-align: right;" valign="center">
    	<% if (request.getParameter("industry") == null || request.getParameter("industry").equals("")) { %>
              <%=IndSelect.getHtmlSelect("industry",1)%>
      <% } else { %>
              <%=IndSelect.getHtmlSelect("industry",Integer.parseInt(request.getParameter("industry")))%>
       <%}%>
            </td>
          </tr>
        </form>
      </table>
    </td>
  </tr>
<%
	Iterator j = NewsList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
		while (j.hasNext()) {
      rowid = (rowid == 1?2:1);
      NewsArticle thisNews = (NewsArticle)j.next();
		%>      
  <tr class="row<%= rowid %>">
    <td width="11%" valign="center">
      <%= thisNews.getDateEntered() %>
    </td>
    <td width="100%" valign="center">
      <a href="<%= thisNews.getUrl() %>" target="_new"><%= thisNews.getHeadline() %></a>
    </td>
  </tr>
		<%}
	} else {%>
  <tr class="containerBody"><td><dhv:label name="calendar.noNewsFound">No news found.</dhv:label></td></tr>
	<%}%>
</table>
</dhv:permission>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="contactHistoryListInfo"/>
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <!-- <th width="8" nowrap>&nbsp;</th> -->
    <th nowrap>
      <strong><a href="MyCFS.do?command=Home&&column=type<%= isPopup(request)?"&popup=true":"" %>"><dhv:label name="reports.accounts.type">Type</dhv:label></a></strong>
        <%= contactHistoryListInfo.getSortIcon("type") %>
    </th>
    <th width="100%">
      <strong><dhv:label name="reports.helpdesk.ticket.maintenance.partDescription">Description</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><a href="MyCFS.do?command=Home&column=entered<%= isPopup(request)?"&popup=true":"" %>"><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></a></strong>
        <%= contactHistoryListInfo.getSortIcon("entered") %>
    </th>    
  </tr>
<%
  Iterator iterator	 = historyList.iterator();
  if ( iterator.hasNext() ) {
  int rowid = 0;
  int i = 0;
  boolean popup = isPopup(request);
  while (iterator.hasNext()) {
    i++;
        rowid = (rowid != 1?1:2);
    ContactHistory thisHistoryElement = (ContactHistory) iterator.next();
    String modify = thisHistoryElement.getPermission(true, (ContactDetails.getOrgId() == -1));
    String view = thisHistoryElement.getPermission(false,  (ContactDetails.getOrgId() == -1));
    String delete = thisHistoryElement.getDeletePermission((ContactDetails.getOrgId() == -1));
    String historyPermission = thisHistoryElement.getViewOrModifyOrDeletePermission((ContactDetails.getOrgId() == -1));    
    boolean canView = false;
    boolean canModify = false;
    boolean canDelete = false;
%>
    <tr class="row<%= rowid %>">
    <!--  Contect menu is hidden now, it can be used later if required -->
    <!-- <td valign="top" nowrap>
          <a href="javascript:displayMenu('select<%= i %>','menuContactHistory', '<%= thisHistoryElement.getId() %>', '<%= thisHistoryElement.getContactId() %>', '<%= thisHistoryElement.getLinkObjectId() %>', '<%= thisHistoryElement.getLinkItemId() %>', true, true, true);"
          onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuContactHistory');">
          <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0" /></a>
    </td> -->
    <td valign="top" nowrap>
      <dhv:evaluate if="<%= !thisHistoryElement.getEnabled() %>"><font color="red"><%= toHtml(thisHistoryElement.getType()) %></font></dhv:evaluate>
      <dhv:evaluate if="<%= thisHistoryElement.getEnabled() %>"><%= toHtml(thisHistoryElement.getType()) %></dhv:evaluate>
    </td>
    <td valign="top" width="100%">
      <dhv:evaluate if="<%= !thisHistoryElement.getEnabled() %>"><font color="red"><%= toHtml(thisHistoryElement.getDescription()) %></font></dhv:evaluate>
      <dhv:evaluate if="<%= thisHistoryElement.getEnabled() %>"><%= toHtml(thisHistoryElement.getDescription()) %></dhv:evaluate>
    </td>
    <td valign="top" nowrap>
      <dhv:evaluate if="<%= !thisHistoryElement.getEnabled() %>"><font color="red"><zeroio:tz timestamp="<%= thisHistoryElement.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /></font></dhv:evaluate>
      <dhv:evaluate if="<%= thisHistoryElement.getEnabled() %>"><zeroio:tz timestamp="<%= thisHistoryElement.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /></dhv:evaluate>
        <%-- <zeroio:tz timestamp="<%= thisHistoryElement.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /> --%>
    </td>    
    </tr>
<%}%>
<%} else { %>
      <tr class="containerBody">
    <td colspan="5">
      <dhv:label name="accounts.accountHistory.noHistoryFound">No history found.</dhv:label>
    </td>
  </tr>
<%}%>
  </table>
  
  
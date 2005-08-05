<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<%@ page import="com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.modules.troubletickets.base.*" %>
<%@ page import="java.text.DateFormat" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="overviewListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="assignmentList" class="com.zeroio.iteam.base.AssignmentList" scope="request"/>
<jsp:useBean id="overviewAssignmentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="newsList" class="com.zeroio.iteam.base.NewsArticleList" scope="request"/>
<jsp:useBean id="overviewNewsListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="issueList" class="com.zeroio.iteam.base.IssueList" scope="request"/>
<jsp:useBean id="overviewIssueListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="fileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<jsp:useBean id="overviewFileItemListListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="ticketList" class="org.aspcfs.modules.troubletickets.base.TicketList" scope="request"/>
<jsp:useBean id="overviewTicketListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ProjectManagement.do"><dhv:label name="Projects" mainMenuItem="true">Projects</dhv:label></a> >
<dhv:label name="Dashboard" subMenuItem="true">Dashboard</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table class="note" cellspacing="0">
<tr>
  <th>
    <img src="images/icons/stock_form-open-in-design-mode-16.gif" border="0" align="absmiddle" />
  </th>
  <td>
    <dhv:label name="project.latestItems.text">The following items are the latest from each of your projects.</dhv:label>
  </td>
</tr>
</table>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="ProjectManagement.do?command=Overview">
    <td align="left" valign="bottom">
      <img src="images/icons/stock_filter-data-by-criteria-16.gif" border="0" align="absmiddle" />
      <select size="1" name="listView" onChange="javascript:document.forms['listView'].submit();">
        <option <%= overviewListInfo.getOptionValue("today") %>><dhv:label name="calendar.Today">Today</dhv:label></option>
        <option <%= overviewListInfo.getOptionValue("24hours") %>><dhv:label name="project.lastTwentyFourHours">Last 24 Hours</dhv:label></option>
        <option <%= overviewListInfo.getOptionValue("48hours") %>><dhv:label name="project.lastFortyEightHours">Last 48 Hours</dhv:label></option>
        <option <%= overviewListInfo.getOptionValue("7days") %>><dhv:label name="project.lastSevenDays">Last 7 Days</dhv:label></option>
        <option <%= overviewListInfo.getOptionValue("14days") %>><dhv:label name="project.lastFourteenDays">Last 14 Days</dhv:label></option>
        <option <%= overviewListInfo.getOptionValue("30days") %>><dhv:label name="project.lastThirtyDays">Last 30 Days</dhv:label></option>
      </select>
    </td>
    </form>
  </tr>
</table>
<% int rowid = 0; %>

<table cellpadding="0" cellspacing="0" width="100%" border="0">
<tr>
<td valign="top">

<%-- Start news --%>
<dhv:evaluate if="<%= !overviewAssignmentListInfo.getExpandedSelection() && !overviewIssueListInfo.getExpandedSelection() && !overviewTicketListInfo.getExpandedSelection() && !overviewFileItemListListInfo.getExpandedSelection() %>">
<dhv:pagedListStatus tableClass="pagedListTab" showExpandLink="true" title="News" type="project.news" object="overviewNewsListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%">
    <dhv:evaluate if="<%= newsList.isEmpty() %>">
    <tr>
      <td>
        <dhv:label name="project.noNewsFound">No News found.</dhv:label>
      </td>
    </tr>
    </dhv:evaluate>
<%
    rowid = 0;
    Iterator newsIterator = newsList.iterator();
    while (newsIterator.hasNext()) {
      rowid = (rowid != 1?1:2);
      NewsArticle thisArticle = (NewsArticle) newsIterator.next();
%>
    <tr class="overviewrow<%= rowid %>">
      <td width="65%">
        <table border="0" cellpadding="2" cellspacing="0">
          <tr>
            <td valign="top">
              <img src="images/icons/stock_announcement-16.gif" border="0" align="absmiddle" />
            </td>
            <td>
              <a href="javascript:popURL('ProjectManagementNews.do?command=Details&pid=<%= thisArticle.getProjectId() %>&id=<%= thisArticle.getId() %>&popup=true','ITEAM_News','600','300','yes','yes');"><%= toHtml(thisArticle.getSubject()) %></a><br />
              <a class="searchLink" href="ProjectManagement.do?command=ProjectCenter&pid=<%= thisArticle.getProjectId() %>&section=News"><zeroio:project id="<%= thisArticle.getProjectId() %>"/></a>
            </td>
          </tr>
        </table>
      </td>
      <td colspan="2" width="35%">
        <dhv:username id="<%= thisArticle.getEnteredBy() %>"/><br />
        <zeroio:tz timestamp="<%= thisArticle.getStartDate() %>" dateOnly="true" default="&nbsp;"/>
      </td>
    </tr>
<%
    }
%>
</table>
<br />
</dhv:evaluate>
<%-- Start discussion topics --%>
<dhv:evaluate if="<%= !overviewAssignmentListInfo.getExpandedSelection() && !overviewNewsListInfo.getExpandedSelection() && !overviewTicketListInfo.getExpandedSelection() && !overviewFileItemListListInfo.getExpandedSelection() %>">
<dhv:pagedListStatus tableClass="pagedListTab" showExpandLink="true" title="Discussion" type="project.discussion" object="overviewIssueListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%">
    <dhv:evaluate if="<%= issueList.isEmpty() %>">
    <tr>
      <td>
        <dhv:label name="project.noTopicsFound">No Topics found.</dhv:label>
      </td>
    </tr>
    </dhv:evaluate>
<%
    rowid = 0;
    Iterator issueIterator = issueList.iterator();
    while (issueIterator.hasNext()) {
      rowid = (rowid != 1?1:2);
      Issue thisIssue = (Issue) issueIterator.next();
%>
    <tr class="overviewrow<%= rowid %>">
      <td width="65%">
        <table border="0" cellpadding="2" cellspacing="0">
          <tr>
            <td valign="top">
              <img src="images/icons/stock_draw-callouts-16.gif" border="0" align="absmiddle" />
            </td>
            <td>
              <a href="ProjectManagementIssues.do?command=Details&pid=<%= thisIssue.getProjectId() %>&iid=<%= thisIssue.getId() %>&cid=<%= thisIssue.getCategoryId() %>&resetList=true"><%= toHtml(thisIssue.getSubject()) %></a><br />
              <a class="searchLink" href="ProjectManagement.do?command=ProjectCenter&pid=<%= thisIssue.getProjectId() %>&section=Issues_Categories"><zeroio:project id="<%= thisIssue.getProjectId() %>"/></a>
            </td>
          </tr>
        </table>
      </td>
      <td colspan="2" width="35%">
        <dhv:evaluate if="<%= thisIssue.getReplyBy() > -1 %>">
          <dhv:username id="<%= thisIssue.getReplyBy() %>" />
        </dhv:evaluate>
        <dhv:evaluate if="<%= thisIssue.getReplyBy() == -1 %>">
          <dhv:username id="<%= thisIssue.getEnteredBy() %>" />
        </dhv:evaluate>
        <br />
          <zeroio:tz timestamp="<%= thisIssue.getReplyDate() %>" dateOnly="true" default="&nbsp;" />
          <dhv:evaluate if="<%= thisIssue.getReplyCount() > 0 %>">
          (<%= thisIssue.getReplyCount() %> repl<%= thisIssue.getReplyCount() > 1 ? "ies" : "y" %>)
        </dhv:evaluate>
      </td>
    </tr>
<%
    }
%>
</table>
<br />
</dhv:evaluate>
<%-- Start documents --%>
<dhv:evaluate if="<%= !overviewAssignmentListInfo.getExpandedSelection() && !overviewIssueListInfo.getExpandedSelection() && !overviewTicketListInfo.getExpandedSelection() && !overviewNewsListInfo.getExpandedSelection() %>">
<dhv:pagedListStatus tableClass="pagedListTab" showExpandLink="true" title="Documents" type="project.documents" object="overviewFileItemListListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%">
    <dhv:evaluate if="<%= fileItemList.isEmpty() %>">
    <tr>
      <td>
        <dhv:label name="project.noDocumentsFound">No Documents found.</dhv:label>
      </td>
    </tr>
    </dhv:evaluate>
<%
    rowid = 0;
    Iterator documentIterator = fileItemList.iterator();
    while (documentIterator.hasNext()) {
      rowid = (rowid != 1?1:2);
      FileItem thisFile = (FileItem) documentIterator.next();
%>
    <tr class="overviewrow<%= rowid %>">
      <td width="65%">
        <table border="0" cellpadding="2" cellspacing="0">
          <tr>
            <td valign="top">
              <%= thisFile.getImageTag("-23") %>
            </td>
            <td>
              <a href="ProjectManagementFiles.do?command=Details&pid=<%= thisFile.getLinkItemId() %>&fid=<%= thisFile.getId() %>&folderId=<%= thisFile.getFolderId() %>"><%= toHtml(thisFile.getSubject()) %></a><br />
              <a class="searchLink" href="ProjectManagement.do?command=ProjectCenter&pid=<%= thisFile.getLinkItemId() %>&section=File_Library&folderId=<%= thisFile.getFolderId() %>"><zeroio:project id="<%= thisFile.getLinkItemId() %>"/></a>
            </td>
          </tr>
        </table>
      </td>
      <td colspan="2" width="35%">
        <dhv:username id="<%= thisFile.getModifiedBy() %>" /><br />
        <zeroio:tz timestamp="<%= thisFile.getModified() %>" dateOnly="true" default="&nbsp;"/>
      </td>
    </tr>
<%
    }
%>
</table>
<br />
</dhv:evaluate>

</td>
<%-- gutter --%>
<td width="8" nowrap>&nbsp;</td>
<%-- 2nd column --%>
<td valign="top">
<%-- Start assignments --%>
<dhv:evaluate if="<%= !overviewNewsListInfo.getExpandedSelection() && !overviewIssueListInfo.getExpandedSelection() && !overviewTicketListInfo.getExpandedSelection() && !overviewFileItemListListInfo.getExpandedSelection() %>">
<dhv:pagedListStatus tableClass="pagedListTab" showExpandLink="true" title="Activities" type="project.activities" object="overviewAssignmentListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%">
    <dhv:evaluate if="<%= assignmentList.isEmpty() %>">
    <tr>
      <td>
        <dhv:label name="project.noActivitiesFound">No Activities found.</dhv:label>
      </td>
    </tr>
    </dhv:evaluate>
<%
    rowid = 0;
    Iterator assignmentIterator = assignmentList.iterator();
    while (assignmentIterator.hasNext()) {
      rowid = (rowid != 1?1:2);
      Assignment thisAssignment = (Assignment) assignmentIterator.next();
%>
    <zeroio:debug value="<%= "Assignment: " + thisAssignment.getId() %>" />
    <tr class="row<%= rowid %>">
      <td width="90%">
        <table border="0" cellpadding="2" cellspacing="0">
          <tr>
            <td valign="top">
              <%= thisAssignment.getStatusGraphicTag() %>
            </td>
            <td>
              <a href="javascript:popURL('ProjectManagementAssignments.do?command=Modify&pid=<%= thisAssignment.getProjectId() %>&aid=<%= thisAssignment.getId() %>&popup=true','ITEAM_Activity','650','475','yes','yes');"><%= toHtml(thisAssignment.getRole()) %></a>
              <dhv:evaluate if="<%= thisAssignment.hasNotes() %>">
                <a href="javascript:popURL('ProjectManagementAssignments.do?command=ShowNotes&pid=<%= thisAssignment.getProjectId() %>&aid=<%= thisAssignment.getId() %>&popup=true','ITEAM_Assignment_Notes','400','500','yes','yes');"><img src="images/icons/stock_insert-note-16.gif" border="0" align="absmiddle" alt="Review all notes"/></a>
                <%= thisAssignment.getNoteCount() %>
              </dhv:evaluate>
              <br />
              <a class="searchLink" href="ProjectManagement.do?command=ProjectCenter&pid=<%= thisAssignment.getProjectId() %>&section=Requirements"><zeroio:project id="<%= thisAssignment.getProjectId() %>"/></a>
            </td>
          </tr>
        </table>
      </td>
      <td colspan="2" width="10%" nowrap>
        <%= thisAssignment.getRelativeDueDateString(User.getTimeZone(), User.getLocale()) %>
      </td>
    </tr>
<%
    }
%>
</table>
<br />
</dhv:evaluate>

<%-- Start tickets --%>
<dhv:evaluate if="<%= !overviewAssignmentListInfo.getExpandedSelection() && !overviewNewsListInfo.getExpandedSelection() && !overviewIssueListInfo.getExpandedSelection() && !overviewFileItemListListInfo.getExpandedSelection() %>">
<dhv:pagedListStatus tableClass="pagedListTab" showExpandLink="true" title="Tickets" type="project.tickets" object="overviewTicketListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%">
    <dhv:evaluate if="<%= ticketList.isEmpty() %>">
    <tr>
      <td>
        <dhv:label name="accounts.tickets.search.notFound">No Tickets found.</dhv:label>
      </td>
    </tr>
    </dhv:evaluate>
<%
    rowid = 0;
    Iterator ticketIterator = ticketList.iterator();
    while (ticketIterator.hasNext()) {
      rowid = (rowid != 1?1:2);
      Ticket thisTicket = (Ticket) ticketIterator.next();
%>
    <tr class="overviewrow<%= rowid %>">
      <td width="90%">
        <table border="0" cellpadding="2" cellspacing="0">
          <tr>
            <td valign="top">
              <img src="images/icons/stock_macro-organizer-16.gif" border="0" align="absmiddle" />
            </td>
            <td>
              <a href="ProjectManagementTickets.do?command=Details&pid=<%= thisTicket.getProjectId() %>&id=<%= thisTicket.getId() %>&return=details"><%= toHtml(thisTicket.getProblemHeader()) %></a><br />
              <a class="searchLink" href="ProjectManagement.do?command=ProjectCenter&pid=<%= thisTicket.getProjectId() %>&section=Tickets"><zeroio:project id="<%= thisTicket.getProjectId() %>"/></a>
            </td>
          </tr>
        </table>
      </td>
      <td colspan="2" width="10%" nowrap><%= toHtml(thisTicket.getAgeOf()) %></td>
    </tr>
<%
    }
%>
</table>
<br />
</dhv:evaluate>
</td>
</tr>
</table>

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
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<%@ page import="com.zeroio.utils.SearchHTMLUtils" %>
<%@ page import="org.aspcfs.utils.FileUtils" %>
<%@ page import="org.apache.lucene.search.Hits" %>
<%@ page import="org.apache.lucene.document.Document" %>
<jsp:useBean id="searchBean" class="com.zeroio.iteam.beans.IteamSearchBean" scope="session" />
<jsp:useBean id="searchBeanInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ProjectManagement.do"><dhv:label name="Projects" mainMenuItem="true">Projects</dhv:label></a> >
<a href="ProjectManagementSearch.do?command=ShowForm"><dhv:label name="Search" subMenuItem="true">Search</dhv:label></a> >
<dhv:label name="accounts.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%
Hits hits = (Hits) request.getAttribute("hits");
Long duration = (Long) request.getAttribute("duration");
StringBuffer title = new StringBuffer();
title.append(hits.length() + " result");
if (hits.length() != 1) {
  title.append("s");
}
if (hits.length() > 1) {
  title.append(", sorted by relevance");
}
title.append(" for " + toHtml(searchBean.getQuery()));
title.append(" (" + duration + " ms)");
// Temp. fix for Weblogic
String pagedListTitle = "<b>" + title.toString() + "</b>";
%>
<dhv:pagedListStatus label="Results" title="<%= pagedListTitle %>" object="searchBeanInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" border="0">
<%
for (int i = searchBeanInfo.getCurrentOffset() ; i < searchBeanInfo.getPageSize() ; i++) {
  Document document = hits.doc(i);
  java.util.Date modified = null;
  try {
    modified = new java.util.Date(Long.parseLong(document.get("modified")));
  } catch (Exception e) {
  }
  String size = null;
  try {
    size = FileUtils.getRelativeSize(Float.parseFloat(document.get("size")), null);
  } catch (Exception e) {
  }
  String type = document.get("type");
  // Temp. fix for Weblogic
  boolean activityFolderType = "activityfolder".equals(type);
  boolean activityType = "activity".equals(type);
  boolean activityNoteType = "activitynote".equals(type);
  boolean fileType = "file".equals(type);
  boolean issueCategoryType = "issuecategory".equals(type);
  boolean issueType = "issue".equals(type);
  boolean issueReplyType = "issuereply".equals(type);
  boolean newsType = "news".equals(type);
  boolean newsStatusDraft = "-1".equals(document.get("newsStatus"));
  boolean newsStatusUnapproved = "1".equals(document.get("newsStatus"));
  boolean projectType = "project".equals(type);
  boolean outlineType = "outline".equals(type);
  boolean listCategoryType = "listcategory".equals(type);
  boolean listType = "list".equals(type);
  boolean ticketType = "ticket".equals(type);
  boolean hasFilename = hasText(document.get("filename"));
%>
  <tr>
    <td class="searchCount" valign="top" align="right" nowrap><%= i + 1 %>.</td>
    <td width="100%">
      <dhv:evaluate if="<%= activityFolderType %>"><a class="search" href="ProjectManagementAssignmentsFolder.do?command=FolderDetails&pid=<%= document.get("projectId") %>&folderId=<%= document.get("assignmentFolderId") %>"><dhv:label name="project.activityFolder">Activity Folder</dhv:label></a></dhv:evaluate>
      <dhv:evaluate if="<%= activityType %>"><a class="search" href="ProjectManagementAssignments.do?command=Details&pid=<%= document.get("projectId") %>&aid=<%= document.get("assignmentId") %>"><dhv:label name="project.activity">Activity</dhv:label></a></dhv:evaluate>
      <dhv:evaluate if="<%= activityNoteType %>"><a class="search" href="ProjectManagementAssignments.do?command=ShowNotes&pid=<%= document.get("projectId") %>&aid=<%= document.get("assignmentId") %>">Activity Note</a></dhv:evaluate>
      <dhv:evaluate if="<%= fileType %>"><a class="search" href="ProjectManagementFiles.do?command=Details&pid=<%= document.get("projectId") %>&fid=<%= document.get("fileId") %>&folderId=<%= document.get("folderId") %>"><dhv:label name="project.document">Document</dhv:label></a></dhv:evaluate>
      <dhv:evaluate if="<%= issueCategoryType %>"><a class="search" href="ProjectManagement.do?command=ProjectCenter&section=Issues&pid=<%= document.get("projectId") %>&cid=<%= document.get("issueCategoryId") %>&resetList=true"><dhv:label name="project.discussionForm">Discussion Forum</dhv:label></a></dhv:evaluate>
      <dhv:evaluate if="<%= issueType %>"><a class="search" href="ProjectManagementIssues.do?command=Details&pid=<%= document.get("projectId") %>&iid=<%= document.get("issueId") %>&resetList=true"><dhv:label name="project.discussionTopic">Discussion Topic</dhv:label></a></dhv:evaluate>
      <dhv:evaluate if="<%= issueReplyType %>"><a class="search" href="ProjectManagementIssues.do?command=Details&pid=<%= document.get("projectId") %>&iid=<%= document.get("issueId") %>&cid=<%= document.get("issueCategoryId") %>&resetList=true"><dhv:label name="project.discussionReply">Discussion Reply</dhv:label></a></dhv:evaluate>
      <dhv:evaluate if="<%= newsType %>"><a class="search" href="ProjectManagementNews.do?command=Details&pid=<%= document.get("projectId") %>&id=<%= document.get("newsId") %>"><dhv:label name="project.newsArticle">News Article</dhv:label></a></dhv:evaluate>
      <dhv:evaluate if="<%= newsType %>">
        <dhv:evaluate if="<%= newsStatusDraft  %>">(<dhv:label name="project.draft">Draft</dhv:label>)</dhv:evaluate>
        <dhv:evaluate if="<%= newsStatusUnapproved %>">(<dhv:label name="project.unapproved">Unapproved</dhv:label>)</dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= projectType %>"><a class="search" href="ProjectManagement.do?command=ProjectCenter&section=Details&pid=<%= document.get("projectId") %>"><dhv:label name="project.project">Project</dhv:label></a></dhv:evaluate>
      <dhv:evaluate if="<%= outlineType %>"><a class="search" href="ProjectManagement.do?command=ProjectCenter&section=Assignments&rid=<%= document.get("requirementId") %>&pid=<%= document.get("projectId") %>"><dhv:label name="project.plan">Plan</dhv:label></a></dhv:evaluate>
      <dhv:evaluate if="<%= listCategoryType %>"><a class="search" href="ProjectManagement.do?command=ProjectCenter&section=Lists&pid=<%= document.get("projectId") %>&cid=<%= document.get("listCategoryId") %>"><dhv:label name="project.list">List</dhv:label></a></dhv:evaluate>
      <dhv:evaluate if="<%= listType %>"><a class="search" href="ProjectManagementLists.do?command=Details&pid=<%= document.get("projectId") %>&id=<%= document.get("listId") %>"><dhv:label name="project.listItem">List Item</dhv:label></a></dhv:evaluate>
      <dhv:evaluate if="<%= ticketType %>"><a class="search" href="ProjectManagementTickets.do?command=Details&pid=<%= document.get("projectId") %>&id=<%= document.get("ticketId") %>&return=details"><dhv:label name="project.ticket">Ticket</dhv:label></a></dhv:evaluate>
      <%--<dhv:evaluate if="<%= hasFilename %>">[<%= document.get("filename") %>]</dhv:evaluate>--%>
      <dhv:evaluate if="<%= hasText(document.get("title")) %>">
        [<%= toHtml(document.get("title")) %>]
      </dhv:evaluate>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>
<%
    String highlightedText = SearchHTMLUtils.highlightText(searchBean.getTerms(), document.get("contents"));
    boolean hasHighlightedText = hasText(highlightedText);
%>
      <dhv:evaluate if="<%= hasHighlightedText %>">
        <%= toHtml(highlightedText) %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= !hasHighlightedText %>">
        <%= toHtml(document.get("title")) %>
      </dhv:evaluate>
    </td>
  </tr>
<%-- Temp. fix for Weblogic --%>
<%
boolean hasSize = (size != null);
boolean hasModified = (modified != null);
String projectId = document.get("projectId");
%>
  <tr>
    <td>&nbsp;</td>
    <td class="searchItem">
      <a class="searchLink" 
         href="ProjectManagement.do?command=ProjectCenter&pid=<%= document.get("projectId") %>"><zeroio:project id="<%= projectId %>"/></a>
      <dhv:evaluate if="<%= hasSize %>">
      -
      <%= size %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= hasModified %>">
      -
      <zeroio:tz timestamp="<%= modified %>" dateOnly="true" dateFormat="<%= DateFormat.LONG %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
      </dhv:evaluate>
      <%--
      -
      <%= hits.score(i) %>
      --%>
      <hr color="#BFBFBB" noshade>
    </td>
  </tr>
<%
}
%>
</table>
<dhv:evaluate if="<%= hits.length() > 0 %>">
<dhv:pagedListControl object="searchBeanInfo"/>
</dhv:evaluate>
<%--
<br />
<br />
<font color="#999999">
Parsed: <%= searchBean.getParsedQuery() %><br />
Actual Query: <%= toHtml((String) request.getAttribute("queryString")) %><br />
</font>
--%>

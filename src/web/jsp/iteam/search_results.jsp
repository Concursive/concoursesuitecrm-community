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
<jsp:useBean id="searchBean" class="com.zeroio.iteam.beans.SearchBean" scope="session" />
<jsp:useBean id="searchBeanInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
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
title.append(" (" + duration + " ms)");
%>
<dhv:pagedListStatus label="Results" title="<%= "<b>" + title.toString() + "</b>" %>" object="searchBeanInfo"/>
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
%>
  <tr>
    <td class="searchCount" valign="top" align="right" nowrap><%= i + 1 %>.</td>
    <td width="100%">
      <dhv:evaluate if="<%= "activityFolder".equals(type) %>"><a class="search" href="ProjectManagementAssignmentsFolder.do?command=FolderDetails&pid=<%= document.get("projectId") %>&folderId=<%= document.get("assignmentFolderId") %>">Activity Folder</a></dhv:evaluate>
      <dhv:evaluate if="<%= "activity".equals(type) %>"><a class="search" href="ProjectManagementAssignments.do?command=Details&pid=<%= document.get("projectId") %>&aid=<%= document.get("assignmentId") %>">Activity</a></dhv:evaluate>
      <dhv:evaluate if="<%= "file".equals(type) %>"><a class="search" href="ProjectManagementFiles.do?command=Details&pid=<%= document.get("projectId") %>&fid=<%= document.get("fileId") %>&folderId=<%= document.get("folderId") %>">Document</a></dhv:evaluate>
      <dhv:evaluate if="<%= "issueCategory".equals(type) %>"><a class="search" href="ProjectManagement.do?command=ProjectCenter&section=Issues&pid=<%= document.get("projectId") %>&cid=<%= document.get("issueCategoryId") %>&resetList=true">Discussion Forum</a></dhv:evaluate>
      <dhv:evaluate if="<%= "issue".equals(type) %>"><a class="search" href="ProjectManagementIssues.do?command=Details&pid=<%= document.get("projectId") %>&iid=<%= document.get("issueId") %>&resetList=true">Discussion Topic</a></dhv:evaluate>
      <dhv:evaluate if="<%= "issueReply".equals(type) %>"><a class="search" href="ProjectManagementIssues.do?command=Details&pid=<%= document.get("projectId") %>&iid=<%= document.get("issueId") %>&cid=<%= document.get("issueCategoryId") %>&resetList=true">Discussion Reply</a></dhv:evaluate>
      <dhv:evaluate if="<%= "news".equals(type) %>"><a class="search" href="ProjectManagementNews.do?command=Details&pid=<%= document.get("projectId") %>&id=<%= document.get("newsId") %>">News Article</a></dhv:evaluate>
      <dhv:evaluate if="<%= "news".equals(type) %>">
        <dhv:evaluate if="<%= "-1".equals(document.get("newsStatus"))  %>">(Draft)</dhv:evaluate>
        <dhv:evaluate if="<%= "1".equals(document.get("newsStatus"))  %>">(Unapproved)</dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= "project".equals(type) %>"><a class="search" href="ProjectManagement.do?command=ProjectCenter&section=Details&pid=<%= document.get("projectId") %>">Project</a></dhv:evaluate>
      <dhv:evaluate if="<%= "outline".equals(type) %>"><a class="search" href="ProjectManagement.do?command=ProjectCenter&section=Assignments&rid=<%= document.get("requirementId") %>&pid=<%= document.get("projectId") %>">Plan</a></dhv:evaluate>
      <dhv:evaluate if="<%= "listCategory".equals(type) %>"><a class="search" href="ProjectManagement.do?command=ProjectCenter&section=Lists&pid=<%= document.get("projectId") %>&cid=<%= document.get("listCategoryId") %>">List</a></dhv:evaluate>
      <dhv:evaluate if="<%= "list".equals(type) %>"><a class="search" href="ProjectManagementLists.do?command=Details&pid=<%= document.get("projectId") %>&id=<%= document.get("listId") %>">List Item</a></dhv:evaluate>
      <dhv:evaluate if="<%= "ticket".equals(type) %>"><a class="search" href="ProjectManagementTickets.do?command=Details&pid=<%= document.get("projectId") %>&id=<%= document.get("ticketId") %>">Ticket</a></dhv:evaluate>
      <dhv:evaluate if="<%= hasText(document.get("filename")) %>">[<%= document.get("filename") %>]</dhv:evaluate>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>
      <%--<dhv:evaluate if="<%= hasText(document.get("title")) %>">  
        <%= toHtml(document.get("title")) %>
        <hr color="#BFBFBB" noshade>
      </dhv:evaluate>--%>
      
<%
    String highlightedText = SearchHTMLUtils.highlightText(searchBean.getTerms(), document.get("contents"));
%>
      <dhv:evaluate if="<%= hasText(highlightedText) %>">
        <%= toHtml(highlightedText) %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= !hasText(highlightedText) %>">
        <%= toHtml(document.get("title")) %>
      </dhv:evaluate>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td class="searchItem">
      <a class="searchLink" 
         href="ProjectManagement.do?command=ProjectCenter&pid=<%= document.get("projectId") %>"><zeroio:project id="<%= document.get("projectId") %>"/></a>
      <dhv:evaluate if="<%= size != null %>">
      -
      <%= size %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= modified != null %>">
      -
      <zeroio:tz timestamp="<%= modified %>" dateOnly="true" dateFormat="<%= DateFormat.LONG %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes"/>
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

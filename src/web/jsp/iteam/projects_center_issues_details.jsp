<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="IssueCategory" class="com.zeroio.iteam.base.IssueCategory" scope="request"/>
<jsp:useBean id="Issue" class="com.zeroio.iteam.base.Issue" scope="request"/>
<jsp:useBean id="projectIssueRepliesInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img border="0" src="images/icons/stock_data-explorer-16.gif" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Issues_Categories&pid=<%= Project.getId() %>">Forums</a> >
      <img border="0" src="images/icons/stock_draw-callouts2-16.gif" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Issues&pid=<%= Project.getId() %>&cid=<%= IssueCategory.getId() %>"><%= toHtml(IssueCategory.getSubject()) %></a> >
      <img border="0" src="images/icons/stock_draw-callouts-16.gif" align="absmiddle">
      <%= toHtml(Issue.getSubject()) %>
    </td>
  </tr>
</table>
<br>
<zeroio:permission name="project-discussion-messages-reply">
<img src="images/icons/16_add_comment.gif" border="0" align="absmiddle">
<a href="ProjectManagementIssues.do?command=Reply&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>">Post Reply</a><br>
</zeroio:permission>
<dhv:pagedListStatus label="Replies" title="<%= showError(request, "actionError") %>" object="projectIssueRepliesInfo"/>
<%
  int rowid = 1;
%>
<dhv:evaluate if="<%= projectIssueRepliesInfo.getCurrentOffset() == 0 %>">
<table cellpadding="4" cellspacing="0" width="100%" style="border: 1px solid #000;">
  <tr class="pagedList">
    <th width="100%">
      <img border="0" src="images/icons/stock_draw-callouts-16.gif" align="absmiddle">
      <strong><%= toHtml(Issue.getSubject()) %></strong>
    </th>
  </tr>
  <tr class="row1">
    <td valign="middle" nowrap style="border-bottom: 1px solid #000">
      <table border="0" cellpadding="0" cellspacing="0" width="100%" class="empty">
        <tr>
          <td width="100%">
            By <dhv:username id="<%= Issue.getEnteredBy() %>"/>
            -
            Posted on
            <zeroio:tz timestamp="<%= Issue.getEntered() %>"/>
            <dhv:evaluate if="<%= !(Issue.getModified().equals(Issue.getEntered())) %>">
            (edited)
            </dhv:evaluate>
          </td>
          <td valign="top" align="right" nowrap>
            <zeroio:permission name="project-discussion-messages-reply">
            <img src="images/icons/16_add_comment.gif" border="0" align="absmiddle">
            <a href="ProjectManagementIssues.do?command=Reply&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>&quote=true">Quote this message</a>
            </zeroio:permission>
            <zeroio:permission name="project-discussion-topics-edit">
            <img src="images/icons/16_edit_comment.gif" border="0" align="absmiddle">
            <a href="ProjectManagementIssues.do?command=Edit&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>">Edit topic</a>
            </zeroio:permission>
            <zeroio:permission name="project-discussion-topics-delete">
            <img src="images/icons/16_delete_comment.gif" border="0" align="absmiddle">
            <a href="javascript:confirmDelete('ProjectManagementIssues.do?command=Delete&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>');">Delete topic</a>
            </zeroio:permission>
            &nbsp;
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="row2">
    <td>
      <%= toHtml(Issue.getBody()) %>
    </td>
  </tr>
</table>
&nbsp;<br>
</dhv:evaluate>
<%
  int replyCount = 0;
  IssueReplyList replyList = Issue.getReplyList();
  Iterator i = replyList.iterator();
  while (i.hasNext()) {
    rowid = (rowid != 1?1:2);
    IssueReply thisReply = (IssueReply) i.next();
    ++replyCount;
%>
<table cellpadding="4" cellspacing="0" width="100%" style="border: 1px solid #000;">
  <tr class="row1">
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <td width="100%">
          <img border="0" src="images/icons/stock_draw-callouts-16.gif" align="absmiddle">
          <strong><%= toHtml(thisReply.getSubject()) %></strong>&nbsp;
        </td>
        <td valign="top" align="right" nowrap>
          Reply <%= replyCount %> of <%= replyList.size() %>&nbsp;
        </td>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <td width="100%">
          By <dhv:username id="<%= thisReply.getEnteredBy() %>"/>
          -
          Posted on
          <zeroio:tz timestamp="<%= thisReply.getEntered() %>"/>
          <dhv:evaluate if="<%= !(thisReply.getModified().equals(thisReply.getEntered())) %>">
          (edited)
          </dhv:evaluate>
          &nbsp;
        </td>
        <td valign="top" align="right" nowrap>
          <zeroio:permission name="project-discussion-messages-reply">
          <img src="images/icons/16_add_comment.gif" border="0" align="absmiddle">
          <a href="ProjectManagementIssues.do?command=Reply&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>&rid=<%= thisReply.getId() %>&quote=true">Quote this message</a>
          </zeroio:permission>
          <zeroio:permission name="project-discussion-messages-edit">
          <img src="images/icons/16_edit_comment.gif" border="0" align="absmiddle">
          <a href="ProjectManagementIssues.do?command=EditReply&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>&rid=<%= thisReply.getId() %>">Edit message</a>
          </zeroio:permission>
          <zeroio:permission name="project-discussion-messages-delete">
          <img src="images/icons/16_delete_comment.gif" border="0" align="absmiddle">
          <a href="javascript:confirmDelete('ProjectManagementIssueReply.do?command=DeleteReply&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>&rid=<%= thisReply.getId() %>');">Delete message</a>
          </zeroio:permission>
          &nbsp;
        </td>
      </table>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td>
      <%= toHtml(thisReply.getBody()) %>
    </td>
  </tr>
</table>
&nbsp;
<%
  }
%>
<br>
<dhv:pagedListControl object="projectIssueRepliesInfo"/>
<br>
<%
  if (request.getParameter("popup") != null) {
%>
  [<a href="javascript:window.close();">Close Window</a>]
<%  
  } else {
%>
  
  <img border="0" src="images/icons/stock_draw-callouts2-16.gif" align="absmiddle">
  <a href="ProjectManagement.do?command=ProjectCenter&section=Issues&pid=<%= Project.getId() %>&cid=<%= IssueCategory.getId() %>">Back to Forum</a>
  <zeroio:permission name="project-discussion-messages-reply">
  <img src="images/icons/16_add_comment.gif" border="0" align="absmiddle">
  <a href="ProjectManagementIssues.do?command=Reply&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>">Post Reply</a>
  </zeroio:permission>
<%
  }
%>

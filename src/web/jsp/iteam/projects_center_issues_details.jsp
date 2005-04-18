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
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="IssueCategory" class="com.zeroio.iteam.base.IssueCategory" scope="request"/>
<jsp:useBean id="Issue" class="com.zeroio.iteam.base.Issue" scope="request"/>
<jsp:useBean id="projectIssueRepliesInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img border="0" src="images/icons/stock_data-explorer-16.gif" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Issues_Categories&pid=<%= Project.getId() %>"><dhv:label name="project.forums">Forums</dhv:label></a> >
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
<a href="ProjectManagementIssues.do?command=Reply&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>"><dhv:label name="project.postReply">Post Reply</dhv:label></a><br>
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
      <%= toHtml(Issue.getSubject()) %>
    </th>
  </tr>
  <tr class="row1">
    <td valign="middle" nowrap style="border-bottom: 1px solid #000">
      <table border="0" cellpadding="0" cellspacing="0" width="100%" class="empty">
        <tr>
          <td width="100%">
            <dhv:label name="project.by.postedOn" param="<%= "username="+getUsername(pageContext,Issue.getEnteredBy(),false,false,"&nbsp;")+"|time="+getTime(pageContext,Issue.getEntered(),User.getTimeZone(),DateFormat.SHORT,true,false,false,"&nbsp;") %>">
            By <dhv:username id="<%= Issue.getEnteredBy() %>"/>
            -
            Posted on
            <zeroio:tz timestamp="<%= Issue.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label>
            <dhv:evaluate if="<%= !(Issue.getModified().equals(Issue.getEntered())) %>">
            <dhv:evaluate if="<%= Issue.getModifiedBy() != Issue.getEnteredBy() %>">
              <dhv:label name="project.editedby.braces" param="<%= "username="+getUsername(pageContext,Issue.getModifiedBy(),false,false,"&nbsp;") %>">(edited by <dhv:username id="<%= Issue.getModifiedBy() %>" />)</dhv:label>
            </dhv:evaluate>
            <dhv:evaluate if="<%= Issue.getModifiedBy() == Issue.getEnteredBy() %>">
              <dhv:label name="project.edited.braces">(edited)</dhv:label>
            </dhv:evaluate>
            </dhv:evaluate>
          </td>
          <td valign="top" align="right" nowrap>
            <%-- Quote this message --%>
            <zeroio:permission name="project-discussion-messages-reply">
              <img src="images/icons/16_add_comment.gif" border="0" align="absmiddle" />
              <a href="ProjectManagementIssues.do?command=Reply&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>&quote=true"><dhv:label name="project.quoteThisMessage">Quote this message</dhv:label></a>
            </zeroio:permission>
            <%-- Edit Topic: Either have permission to make changes, or you are the user that wrote the article --%>
            <zeroio:permission name="project-discussion-topics-edit">
              <img src="images/icons/16_edit_comment.gif" border="0" align="absmiddle" />
              <a href="ProjectManagementIssues.do?command=Edit&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>"><dhv:label name="project.editTopic">Edit topic</dhv:label></a>
            </zeroio:permission>
            <zeroio:permission name="project-discussion-topics-edit" if="none">
              <dhv:evaluate if="<%= Issue.getEnteredBy() == User.getUserId() %>">
                <img src="images/icons/16_edit_comment.gif" border="0" align="absmiddle" />
                <a href="ProjectManagementIssues.do?command=Edit&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>"><dhv:label name="project.editTopic">Edit topic</dhv:label></a>
              </dhv:evaluate>
            </zeroio:permission>
            <%-- Delete Topic --%>
            <zeroio:permission name="project-discussion-topics-delete">
              <img src="images/icons/16_delete_comment.gif" border="0" align="absmiddle" />
              <a href="javascript:confirmDelete('ProjectManagementIssues.do?command=Delete&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>');"><dhv:label name="project.deleteTopic">Delete topic</dhv:label></a>
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
  <%-- file list --%>
  <dhv:evaluate if="<%= Issue.hasFiles() %>">
  <%
    Iterator issueFiles = Issue.getFiles().iterator();
    while (issueFiles.hasNext()) {
      FileItem thisFile = (FileItem) issueFiles.next();
  %>
      <tr class="row2">
        <td valign="top" nowrap>
          <%= thisFile.getImageTag("-23") %>
          <a href="Discussion.do?command=Download&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&fid=<%= thisFile.getId() %>"><%= toHtml(thisFile.getClientFilename()) %></a>
        </td>
      </tr>
  <%
    }
  %>
  </dhv:evaluate>
</table>
<br>
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
          <dhv:label name="project.replyNumberofTotal" param="<%= "number="+ replyCount +"|total="+ replyList.size() %>">Reply <%= replyCount %> of <%= replyList.size() %></dhv:label>&nbsp;
        </td>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <td width="100%">
          <dhv:label name="project.by.postedOn" param="<%= "username="+getUsername(pageContext,thisReply.getEnteredBy(),false,false,"&nbsp;")+"|time="+getTime(pageContext,thisReply.getEntered(),User.getTimeZone(),DateFormat.SHORT,true,false,false,"&nbsp;") %>">By <dhv:username id="<%= thisReply.getEnteredBy() %>"/> - Posted on <zeroio:tz timestamp="<%= thisReply.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label>
          <dhv:evaluate if="<%= !(thisReply.getModified().equals(thisReply.getEntered())) %>">
            <dhv:evaluate if="<%= thisReply.getModifiedBy() != thisReply.getEnteredBy() %>">
              <dhv:label name="project.editedby.braces" param="<%= "username="+getUsername(pageContext,thisReply.getModifiedBy(),false,false,"&nbsp;") %>">(edited by <dhv:username id="<%= thisReply.getModifiedBy() %>" />)</dhv:label>
            </dhv:evaluate>
            <dhv:evaluate if="<%= thisReply.getModifiedBy() == thisReply.getEnteredBy() %>">
              <dhv:label name="project.edited.braces">(edited)</dhv:label>
            </dhv:evaluate>
          </dhv:evaluate>
          &nbsp;
        </td>
        <td valign="top" align="right" nowrap>
          <%-- Quote this message --%>
          <zeroio:permission name="project-discussion-messages-reply">
            <img src="images/icons/16_add_comment.gif" border="0" align="absmiddle" />
            <a href="ProjectManagementIssues.do?command=Reply&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>&rid=<%= thisReply.getId() %>&quote=true"><dhv:label name="project.quoteThisMessage">Quote this message</dhv:label></a>
          </zeroio:permission>
          <%-- Edit Message --%>
          <zeroio:permission name="project-discussion-messages-edit">
            <img src="images/icons/16_edit_comment.gif" border="0" align="absmiddle" />
            <a href="ProjectManagementIssues.do?command=EditReply&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>&rid=<%= thisReply.getId() %>"><dhv:label name="project.editMessage">Edit message</dhv:label></a>
          </zeroio:permission>
          <zeroio:permission name="project-discussion-messages-edit" if="none">
            <dhv:evaluate if="<%= thisReply.getEnteredBy() == User.getUserId() %>">
              <img src="images/icons/16_edit_comment.gif" border="0" align="absmiddle" />
              <a href="ProjectManagementIssues.do?command=EditReply&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>&rid=<%= thisReply.getId() %>"><dhv:label name="project.editMessage">Edit message</dhv:label></a>
            </dhv:evaluate>
          </zeroio:permission>
          <%-- Delete Message --%>
          <zeroio:permission name="project-discussion-messages-delete">
            <img src="images/icons/16_delete_comment.gif" border="0" align="absmiddle" />
            <a href="javascript:confirmDelete('ProjectManagementIssueReply.do?command=DeleteReply&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>&rid=<%= thisReply.getId() %>');"><dhv:label name="project.deleteMessage">Delete message</dhv:label></a>
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
<%-- file list --%>
<dhv:evaluate if="<%= thisReply.hasFiles() %>">
  <table cellpadding="2" cellspacing="0" border="0">
<%
  Iterator files = thisReply.getFiles().iterator();
  while (files.hasNext()) {
    FileItem thisFile = (FileItem) files.next();
%>
    <tr>
      <td valign="top" nowrap>
        <%= thisFile.getImageTag("-23") %>&nbsp;
      </td>
      <td valign="top">
        <a href="Discussion.do?command=Download&pid=<%= Project.getId() %>&rid=<%= thisReply.getId() %>&fid=<%= thisFile.getId() %>"><%= toHtml(thisFile.getClientFilename()) %></a>
      </td>
    </tr>
<%
  }
%>
  </table>
</dhv:evaluate>
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
  [<a href="javascript:window.close();"><dhv:label name="button.closeWindow">Close Window</dhv:label></a>]
<%  
  } else {
%>
  <img border="0" src="images/icons/stock_draw-callouts2-16.gif" align="absmiddle">
  <a href="ProjectManagement.do?command=ProjectCenter&section=Issues&pid=<%= Project.getId() %>&cid=<%= IssueCategory.getId() %>"><dhv:label name="project.backToForum">Back to Forum</dhv:label></a>
  <zeroio:permission name="project-discussion-messages-reply">
  <img src="images/icons/16_add_comment.gif" border="0" align="absmiddle">
  <a href="ProjectManagementIssues.do?command=Reply&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>"><dhv:label name="project.postReply">Post Reply</dhv:label></a>
  </zeroio:permission>
<%
  }
%>

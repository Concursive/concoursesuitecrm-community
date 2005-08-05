<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="newsList" class="com.zeroio.iteam.base.NewsArticleList" scope="request"/>
<jsp:useBean id="projectNewsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="newsArticleCategoryList" class="com.zeroio.iteam.base.NewsArticleCategoryList" scope="request"/>
<jsp:useBean id="taskCategoryList" class="org.aspcfs.modules.tasks.base.TaskCategoryList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="initPopupMenu.jsp" %>
<%@ include file="projects_center_news_email_menu.jsp" %>
<%-- begin articles --%>
<%
  Iterator i = newsList.iterator();
  while (i.hasNext()) {
    NewsArticle thisArticle = (NewsArticle) i.next();
%>
<table cellpadding="4" cellspacing="0" width="100%" style="border: 1px solid #000;">
  <tr class="newsArticle">
    <th width="100%">
      <img alt="" src="images/icons/stock_news-16.gif" align="absmiddle" />
      <font size="2"><%= toHtml(thisArticle.getSubject()) %></font>
      <font color="red">
      <dhv:evaluate if="<%= thisArticle.getStatus() == NewsArticle.DRAFT %>">(Draft)</dhv:evaluate>
      <dhv:evaluate if="<%= thisArticle.getStatus() == NewsArticle.UNAPPROVED %>">(Unapproved)</dhv:evaluate>
      </font>
    </th>
  </tr>
  <tr class="row1">
    <td valign="middle" nowrap style="border-bottom: 1px solid #000">
      <table border="0" cellpadding="0" cellspacing="0" width="100%" class="empty">
        <tr>
          <td width="100%">
            By <dhv:username id="<%= thisArticle.getEnteredBy() %>"/>
            -
            Posted on
            <zeroio:tz timestamp="<%= thisArticle.getStartDate() %>"/>
          </td>
          <td align="right" nowrap valign="top">
            <dhv:evaluate if="<%= !Project.isTrashed() %>" >
            <zeroio:permission name="project-news-edit">
              <%-- send email to team members --%>
              <img src="images/icons/stock_mail-16.gif" border="0" align="absmiddle">
              <a href="javascript:displayMenu('select_<%= thisArticle.getId() %>', 'menuItem', <%= thisArticle.getId() %>);" name="select_<%= thisArticle.getId() %>" id="select_<%= thisArticle.getId() %>">Email</a>
              <%-- edit message --%>
              <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle">
              <a href="ProjectManagementNews.do?command=Edit&pid=<%= Project.getId() %>&id=<%= thisArticle.getId() %>">Edit</a>
            </zeroio:permission>
            <zeroio:permission name="project-news-delete">
              <%-- delete message --%>
              <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle">
              <a href="javascript:confirmDelete('ProjectManagementNews.do?command=Delete&pid=<%= Project.getId() %>&id=<%= thisArticle.getId() %>');">Delete</a>
            </zeroio:permission>
            </dhv:evaluate>
            &nbsp;
          </td>
        </tr>
      </table>
    </td>
  </tr>
<dhv:evaluate if="<%= thisArticle.getCategoryId() > -1 %>">
  <tr class="row1">
    <td>
      Category: <%= toHtml(newsArticleCategoryList.getValueFromId(thisArticle.getCategoryId())) %>
    </td>
  </tr>
</dhv:evaluate>
  <tr class="row2">
    <td>
      <%= thisArticle.getIntro() %>
      <dhv:evaluate if="<%= thisArticle.getMessage() != null && thisArticle.getMessage().length() > 0 %>">
        <br>
        <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle">
        <a href="javascript:popURL('ProjectManagementNews.do?command=Details&pid=<%= Project.getId() %>&id=<%= thisArticle.getId() %>&popup=true','News_Details','600','500','yes','yes');">Read more</a>
      </dhv:evaluate>
    </td>
  </tr>
<%-- attachments --%>
<dhv:evaluate if="<%= thisArticle.getTaskCategoryId() > -1 %>">
  <tr class="row2">
    <td>
      Linked List: <a href="ProjectManagement.do?command=ProjectCenter&section=Lists&pid=<%= thisArticle.getProjectId() %>&cid=<%= thisArticle.getTaskCategoryId() %>"><%= toHtml(taskCategoryList.getValueFromId(thisArticle.getTaskCategoryId())) %></a>
    </td>
  </tr>
</dhv:evaluate>
</table>
<br />
<%
  }
%>


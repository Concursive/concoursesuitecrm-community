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
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="newsList" class="com.zeroio.iteam.base.NewsArticleList" scope="request"/>
<jsp:useBean id="projectNewsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_announcement-16.gif" border="0" align="absmiddle">
      <zeroio:tabLabel name="News" object="Project"/>
    </td>
  </tr>
</table>
<br>
<zeroio:permission name="project-news-add">
<img src="images/icons/stock_new-news-16.gif" border="0" align="absmiddle">
<a href="ProjectManagementNews.do?command=Add&pid=<%= Project.getId() %>">Add News Article</a><br>
&nbsp;<br>
</zeroio:permission>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr>
    <form name="newsView" method="post" action="ProjectManagement.do?command=ProjectCenter&section=News&pid=<%= Project.getId() %>">
    <td align="left">
      <zeroio:permission name="project-news-view-archived,project-news-view-unreleased" if="any">
      <img alt="" src="images/icons/stock_filter-data-by-criteria-16.gif" align="absmiddle">
      <select name="listView" onChange="javascript:document.forms['newsView'].submit();">
        <option <%= projectNewsInfo.getOptionValue("current") %>>Current News</option>
      <zeroio:permission name="project-news-view-archived">
        <option <%= projectNewsInfo.getOptionValue("archived") %>>Archived News</option>
      </zeroio:permission>
      <zeroio:permission name="project-news-view-unreleased">
        <option <%= projectNewsInfo.getOptionValue("unreleased") %>>Unreleased News</option>
      </zeroio:permission>
      <zeroio:permission name="project-news-view-unreleased">
        <option <%= projectNewsInfo.getOptionValue("drafts") %>>News Drafts</option>
      </zeroio:permission>
      </select>
      </zeroio:permission>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="projectNewsInfo"/>
    </td>
    </form>
  </tr>
</table>
<dhv:evaluate if="<%= newsList.isEmpty() %>">
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th>
        <strong>News</strong>
      </th>
    </tr>
    <tr class="row2">
      <td>
        No news articles to display.
      </td>
    </tr>
  </table>
</dhv:evaluate>
<%
  Iterator i = newsList.iterator();
  while (i.hasNext()) {
    NewsArticle thisArticle = (NewsArticle) i.next();
%>
<table cellpadding="4" cellspacing="0" width="100%" style="border: 1px solid #000;">
  <tr class="pagedList">
    <th width="100%">
      <img alt="" src="images/icons/stock_news-16.gif" align="absmiddle">
      <font size="2"><strong><%= toHtml(thisArticle.getSubject()) %></strong></font>
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
            <zeroio:tz timestamp="<%= thisArticle.getStartDate() %>" dateOnly="true" timeZone="<%= thisArticle.getStartDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
            <% if(!User.getTimeZone().equals(thisArticle.getStartDateTimeZone())){%>
            <br />
            <zeroio:tz timestamp="<%= thisArticle.getStartDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
            <% } %>
          </td>
          <td align="right" nowrap valign="top">
            <zeroio:permission name="project-news-edit">
              <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle">
              <a href="ProjectManagementNews.do?command=Edit&pid=<%= Project.getId() %>&id=<%= thisArticle.getId() %>">Edit this article</a>
            </zeroio:permission>
            <zeroio:permission name="project-news-delete">
              <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle">
              <a href="javascript:confirmDelete('ProjectManagementNews.do?command=Delete&pid=<%= Project.getId() %>&id=<%= thisArticle.getId() %>');">Delete this article</a>
            </zeroio:permission>
            &nbsp;
          </td>
        </tr>
      </table>
    </td>
  </tr>
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
</table>
&nbsp;<br>
<%
  }
%>
<br>
<dhv:pagedListControl object="projectNewsInfo"/>

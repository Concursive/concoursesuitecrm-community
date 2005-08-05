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
<jsp:useBean id="newsArticleCategoryList" class="com.zeroio.iteam.base.NewsArticleCategoryList" scope="request"/>
<jsp:useBean id="taskCategoryList" class="org.aspcfs.modules.tasks.base.TaskCategoryList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_announcement-16.gif" border="0" align="absmiddle">
      <zeroio:tabLabel name="News" type="project.news" object="Project"/>
    </td>
  </tr>
</table>
<br />
<% if (!Project.isTrashed()){ %>
  <zeroio:permission name="project-news-add">
    <img src="images/icons/stock_new-news-16.gif" border="0" align="absmiddle">
    <a href="ProjectManagementNews.do?command=Add&pid=<%= Project.getId() %>"><dhv:label name="project.addNewsArticle">Add News Article</dhv:label></a><br>
    &nbsp;<br />
  </zeroio:permission>
<% } %>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr>
    <form name="newsView" method="post" action="ProjectManagement.do?command=ProjectCenter&section=News&pid=<%= Project.getId() %>">
    <td align="left">
      <%-- filters --%>
      <zeroio:permission name="project-news-view-archived,project-news-view-unreleased" if="any">
      <img alt="" src="images/icons/stock_filter-data-by-criteria-16.gif" align="absmiddle" />
      <select name="listView" onChange="javascript:document.forms['newsView'].submit();">
        <option <%= projectNewsInfo.getOptionValue("current") %>><dhv:label name="project.currentNews">Current News</dhv:label></option>
      <zeroio:permission name="project-news-view-archived">
        <option <%= projectNewsInfo.getOptionValue("archived") %>><dhv:label name="project.archivedNews">Archived News</dhv:label></option>
      </zeroio:permission>
      <zeroio:permission name="project-news-view-unreleased">
        <option <%= projectNewsInfo.getOptionValue("unreleased") %>>Unreleased News</option>
      </zeroio:permission>
      <zeroio:permission name="project-news-view-unreleased">
        <option <%= projectNewsInfo.getOptionValue("drafts") %>><dhv:label name="project.newsDrafts">News Drafts</dhv:label></option>
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
        <strong><dhv:label name="project.news">News</dhv:label></strong>
      </th>
    </tr>
    <tr class="row2">
      <td>
        <dhv:label name="project.noNewsArticlesToDisplay">No news articles to display.</dhv:label>
      </td>
    </tr>
  </table>
</dhv:evaluate>
<%
String subSection = (String) request.getAttribute("IncludeSubSection");
String includeSubSection = "projects_center_" + subSection + ".jsp";
%>
<jsp:include page="<%= includeSubSection %>" flush="true"/>
<br />
<dhv:pagedListControl object="projectNewsInfo"/>

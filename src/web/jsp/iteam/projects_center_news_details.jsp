<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="newsArticle" class="com.zeroio.iteam.base.NewsArticle" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<dhv:evaluate if="<%= !isPopup(request) %>">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_announcement-16.gif" border="0" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=News&pid=<%= Project.getId() %>"><zeroio:tabLabel name="News" object="Project"/></a> >
      Article Details
    </td>
  </tr>
</table>
<br>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" width="100%" style="border: 1px solid #000;">
  <tr class="pagedList">
    <th width="100%">
      <img alt="" src="images/icons/stock_news-16.gif" align="absmiddle">
      <font size="2"><strong><%= toHtml(newsArticle.getSubject()) %></strong></font>
    </th>
  </tr>
  <tr class="row1">
    <td valign="middle" nowrap style="border-bottom: 1px solid #000">
      <table border="0" cellpadding="0" cellspacing="0" width="100%" class="empty">
        <tr>
          <td width="100%">
            By <dhv:username id="<%= newsArticle.getEnteredBy() %>"/>
            -
            Posted on
            <zeroio:tz timestamp="<%= newsArticle.getStartDate() %>"/>
          </td>
          <td align="right" valign="top" nowrap>
            <dhv:evaluate if="<%= !isPopup(request) %>">
            <zeroio:permission name="project-news-edit">
              <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle">
              <a href="ProjectManagementNews.do?command=Edit&pid=<%= Project.getId() %>&id=<%= newsArticle.getId() %>">Edit this article</a>
            </zeroio:permission>
            <zeroio:permission name="project-news-delete">
              <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle">
              <a href="javascript:confirmDelete('ProjectManagementNews.do?command=Delete&pid=<%= Project.getId() %>&id=<%= newsArticle.getId() %>');">Delete this article</a>
            </zeroio:permission>
            </dhv:evaluate>
            &nbsp;
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="row2">
    <td colspan="2">
      <%= newsArticle.getIntro() %>
    <dhv:evaluate if="<%= hasText(newsArticle.getMessage()) %>">
      <br>
      <%= newsArticle.getMessage() %><br>
    </dhv:evaluate>
    <dhv:evaluate if="<%= !isPopup(request) %>">
      <br>
      <img src="images/icons/stock_left-16.gif" border="0" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=News&pid=<%= Project.getId() %>">Back to list</a>
    </dhv:evaluate>
    </td>
  </tr>
</table>
<dhv:evaluate if="<%= isPopup(request) %>">
<br>
<input type="button" value="Close" onClick="javascript:window.close()"/>
</dhv:evaluate>

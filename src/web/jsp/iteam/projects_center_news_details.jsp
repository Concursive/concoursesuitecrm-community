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
<jsp:useBean id="newsArticle" class="com.zeroio.iteam.base.NewsArticle" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
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
            <zeroio:tz timestamp="<%= newsArticle.getStartDate() %>" dateOnly="true" timeZone="<%= newsArticle.getStartDateTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
            <% if(!User.getTimeZone().equals(newsArticle.getStartDateTimeZone())){%>
            <br />
            <zeroio:tz timestamp="<%= newsArticle.getStartDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
            <% } %>
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

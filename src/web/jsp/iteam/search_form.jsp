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
<%@ page import="com.zeroio.iteam.beans.SearchBean" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="searchBean" class="com.zeroio.iteam.beans.SearchBean" scope="session" />
<jsp:useBean id="searchBeanInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%!
  public static String selected(SearchBean search, int scope, int section) {
    if (search.getScope() == scope && search.getSection() == section) {
      return "selected";
    }
    return "";
  }
%>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<table border="0" cellspacing="0" cellpadding="4" width="100%">
  <tr class="shadow">
    <td align="center" style="border-top: 1px solid #000; border-left: 1px solid #000; border-right: 1px solid #000" nowrap width="100%">
      <b>Search</b>
    </td>
  </tr>
  <tr bgColor="#FFFFFF">
    <td style="border-left: 1px solid #000; border-right: 1px solid #000; border-bottom: 1px solid #000;" width="100%">
      <table border="0" cellpadding="2" cellspacing="0" width="100%">
        <form name="search" action="ProjectManagementSearch.do?auto-populate=true" method="post">
        <tr>
          <td>
            <select name="scope" onChange="this.form.query.focus()">
            <dhv:evaluate if="<%= request.getAttribute("Project") != null && ((Project) request.getAttribute("Project")).getId() > -1 %>">
                <option value="this" <%= selected(searchBean, SearchBean.THIS, SearchBean.UNDEFINED) %>>This Project</option>
                <option value="thisNews" <%= selected(searchBean, SearchBean.THIS, SearchBean.NEWS) %>>&nbsp; News</option>
                <option value="thisDiscussion" <%= selected(searchBean, SearchBean.THIS, SearchBean.DISCUSSION) %>>&nbsp; Discussion</option>
                <option value="thisDocuments" <%= selected(searchBean, SearchBean.THIS, SearchBean.DOCUMENTS) %>>&nbsp; Documents</option>
                <option value="thisLists" <%= selected(searchBean, SearchBean.THIS, SearchBean.LISTS) %>>&nbsp; Lists</option>
                <option value="thisPlan" <%= selected(searchBean, SearchBean.THIS, SearchBean.PLAN) %>>&nbsp; Plan</option>
                <option value="thisTickets" <%= selected(searchBean, SearchBean.THIS, SearchBean.TICKETS) %>>&nbsp; Tickets</option>
                <option value="thisDetails" <%= selected(searchBean, SearchBean.THIS, SearchBean.DETAILS) %>>&nbsp; Details</option>
            </dhv:evaluate>
                <option value="all" <%= selected(searchBean, SearchBean.ALL, SearchBean.UNDEFINED) %>>All Projects</option>
                <option value="allNews" <%= selected(searchBean, SearchBean.ALL, SearchBean.NEWS) %>>&nbsp; News</option>
                <option value="allDiscussion" <%= selected(searchBean, SearchBean.ALL, SearchBean.DISCUSSION) %>>&nbsp; Discussion</option>
                <option value="allDocuments" <%= selected(searchBean, SearchBean.ALL, SearchBean.DOCUMENTS) %>>&nbsp; Documents</option>
                <option value="allLists" <%= selected(searchBean, SearchBean.ALL, SearchBean.LISTS) %>>&nbsp; Lists</option>
                <option value="allPlan" <%= selected(searchBean, SearchBean.ALL, SearchBean.PLAN) %>>&nbsp; Plan</option>
                <option value="allTickets" <%= selected(searchBean, SearchBean.ALL, SearchBean.TICKETS) %>>&nbsp; Tickets</option>
                <option value="allDetails" <%= selected(searchBean, SearchBean.ALL, SearchBean.DETAILS) %>>&nbsp; Details</option>
            </select>
            <dhv:evaluate if="<%= request.getAttribute("Project") != null && ((Project) request.getAttribute("Project")).getId() > -1 %>">
                <input type="hidden" name="projectId" value="<%= ((Project) request.getAttribute("Project")).getId() %>" />
            </dhv:evaluate>
            <dhv:evaluate if="<%= request.getAttribute("Project") == null || ((Project) request.getAttribute("Project")).getId() == -1 %>">
              <input type="hidden" name="projectId" value="-1" />
            </dhv:evaluate>
            <a href="javascript:popURL('ProjectManagementSearch.do?command=Tips&popup=true','Search_Tips','350','375','yes','yes');">tips</a>
          </td>
        </tr>
        <tr>
          <td nowrap>
            <input type="text" size="15" name="query" value="<%= toHtmlValue(searchBean.getQuery()) %>" />
            <input type="image" src="images/icons/stock_zoom-16.gif" systran="yes" border="0" alt="Search" name="Search" value="Search" align="absMiddle" />
          </td>
        </tr>
        <%--
        <tr>
          <td nowrap>
            <a href="Search.do?command=Advanced">Advanced search</a>
          </td>
        </tr>
        --%>
        </form>
      </table>
    </td>
  </tr>
</table>

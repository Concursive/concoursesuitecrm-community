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
<%@ page import="com.zeroio.iteam.beans.IteamSearchBean" %>
<%@ page import="com.darkhorseventures.framework.beans.SearchBean" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="searchBean" class="com.zeroio.iteam.beans.IteamSearchBean" scope="session" />
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
<script language="JavaScript">
  function checkForm(form) {
    var formTest = true;
    var messageText = "";
    //Check required fields
    if (form.query.value == "") {
      messageText += label("description.required","- Description is a required field\r\n");
      formTest = false;
    }
    if (formTest == false) {
      messageText = label("Form.not.submitted","The form could not be submitted.          \r\nPlease verify the following items:\r\n\r\n") + messageText;
      alert(messageText);
      form.query.focus();
      return false;
    } else {
      return true;
    }
  }
</script>
<body onLoad="document.search.query.focus()">
<form name="search" action="ProjectManagementSearch.do?auto-populate=true" method="post" onSubmit="return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ProjectManagement.do"><dhv:label name="Projects" mainMenuItem="true">Projects</dhv:label></a> >
<dhv:label name="Search" subMenuItem="true">Search</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <b><dhv:label name="button.search">Search</dhv:label></b>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="documents.search.scope">Scope</dhv:label>
    </td>
    <td>
      <select name="scope" onChange="this.form.query.focus()">
      <dhv:evaluate if='<%= request.getAttribute("Project") != null && ((Project) request.getAttribute("Project")).getId() > -1 %>'>
          <option value="this" <%= selected(searchBean, SearchBean.THIS, SearchBean.UNDEFINED) %>><dhv:label name="project.thisProject">This Project</dhv:label></option>
          <option value="thisNews" <%= selected(searchBean, SearchBean.THIS, IteamSearchBean.NEWS) %>>&nbsp; <dhv:label name="project.news">News</dhv:label></option>
          <option value="thisDiscussion" <%= selected(searchBean, SearchBean.THIS, IteamSearchBean.DISCUSSION) %>>&nbsp; <dhv:label name="project.discussion">Discussion</dhv:label></option>
          <option value="thisDocuments" <%= selected(searchBean, SearchBean.THIS, IteamSearchBean.DOCUMENTS) %>>&nbsp; <dhv:label name="accounts.accounts_documents_details.Documents">Documents</dhv:label></option>
          <option value="thisLists" <%= selected(searchBean, SearchBean.THIS, IteamSearchBean.LISTS) %>>&nbsp; <dhv:label name="project.lists">Lists</dhv:label></option>
          <option value="thisPlan" <%= selected(searchBean, SearchBean.THIS, IteamSearchBean.PLAN) %>>&nbsp; <dhv:label name="project.plan">Plan</dhv:label></option>
          <option value="thisTickets" <%= selected(searchBean, SearchBean.THIS, IteamSearchBean.TICKETS) %>>&nbsp; <dhv:label name="dependency.tickets">Tickets</dhv:label></option>
          <option value="thisDetails" <%= selected(searchBean, SearchBean.THIS, IteamSearchBean.DETAILS) %>>&nbsp; <dhv:label name="accounts.details.long_html">Details</dhv:label></option>
      </dhv:evaluate>
          <option value="all" <%= selected(searchBean, SearchBean.ALL, SearchBean.UNDEFINED) %>><dhv:label name="project.allProjectData">All Project Data</dhv:label></option>
          <option value="allNews" <%= selected(searchBean, SearchBean.ALL, IteamSearchBean.NEWS) %>>&nbsp; <dhv:label name="project.news">News</dhv:label></option>
          <option value="allDiscussion" <%= selected(searchBean, SearchBean.ALL, IteamSearchBean.DISCUSSION) %>>&nbsp; <dhv:label name="project.discussion">Discussion</dhv:label></option>
          <option value="allDocuments" <%= selected(searchBean, SearchBean.ALL, IteamSearchBean.DOCUMENTS) %>>&nbsp; <dhv:label name="accounts.accounts_documents_details.Documents">Documents</dhv:label></option>
          <option value="allLists" <%= selected(searchBean, SearchBean.ALL, IteamSearchBean.LISTS) %>>&nbsp; <dhv:label name="project.lists">Lists</dhv:label></option>
          <option value="allPlan" <%= selected(searchBean, SearchBean.ALL, IteamSearchBean.PLAN) %>>&nbsp; <dhv:label name="project.plan">Plan</dhv:label></option>
          <option value="allTickets" <%= selected(searchBean, SearchBean.ALL, IteamSearchBean.TICKETS) %>>&nbsp; <dhv:label name="dependency.tickets">Tickets</dhv:label></option>
          <option value="allDetails" <%= selected(searchBean, SearchBean.ALL, IteamSearchBean.DETAILS) %>>&nbsp; <dhv:label name="accounts.details.long_html">Details</dhv:label></option>
      </select>
      <dhv:evaluate if='<%= request.getAttribute("Project") != null && ((Project) request.getAttribute("Project")).getId() > -1 %>'>
          <input type="hidden" name="projectId" value="<%= ((Project) request.getAttribute("Project")).getId() %>" />
      </dhv:evaluate>
      <dhv:evaluate if='<%= request.getAttribute("Project") == null || ((Project) request.getAttribute("Project")).getId() == -1 %>'>
        <input type="hidden" name="projectId" value="-1" />
      </dhv:evaluate>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="documents.search.for">For</dhv:label>
    </td>
    <td>
      <input type="text" size="30" name="query" value="<%= toHtmlValue(searchBean.getQuery()) %>" />
      <font color="red">*</font>
    </td>
  </tr>
</table>
<br />
<input type="submit" name="Search" value="<dhv:label name="button.search">Search</dhv:label>" />
</form>
</body>
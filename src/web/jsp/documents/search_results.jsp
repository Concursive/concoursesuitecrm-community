<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*, org.aspcfs.modules.documents.base.*" %>
<%@ page import="com.zeroio.utils.SearchHTMLUtils" %>
<%@ page import="org.aspcfs.utils.FileUtils" %>
<%@ page import="org.apache.lucene.search.Hits" %>
<%@ page import="org.apache.lucene.document.Document" %>
<jsp:useBean id="documentsSearchBean" class="org.aspcfs.modules.documents.beans.DocumentsSearchBean" scope="session" />
<jsp:useBean id="searchBeanInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="DocumentManagement.do"><dhv:label name="Documents" mainMenuItem="true">Documents</dhv:label></a> >
<a href="DocumentManagementSearch.do?command=ShowForm"><dhv:label name="Search" subMenuItem="true">Search</dhv:label></a> >
<dhv:label name="accounts.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
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
      <dhv:evaluate if="<%= "file".equals(type) %>"><a class="search" href="DocumentStoreManagementFiles.do?command=Details&documentStoreId=<%= document.get("documentStoreId") %>&fid=<%= document.get("fileId") %>&folderId=<%= document.get("folderId") %>"><dhv:label name="project.document">Document</dhv:label></a></dhv:evaluate>
      <dhv:evaluate if="<%= "documentStoreDetails".equals(type) %>"><a class="search" href="DocumentManagement.do?command=DocumentStoreCenter&section=Details&documentStoreId=<%= document.get("documentStoreId") %>"><dhv:label name="documents.documentStoreDetails">Document Store Details</dhv:label></a></dhv:evaluate>
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
    String highlightedText = SearchHTMLUtils.highlightText(documentsSearchBean.getTerms(), document.get("contents"));
%>
     <dhv:evaluate if="<%=!"".equals(document.get("trashed"))%>">
       <font color="red">(<%= toHtml(document.get("trashed")) %>)</font><br />
     </dhv:evaluate>
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
    <%--
      <a class="searchLink" 
         href="DocumentManagement.do?command=DocumentStoreCenter&pid=<%= document.get("documentStoreId") %>"><zeroio:project id="<%= document.get("documentStoreId") %>"/></a>
    --%>
      <a class="searchLink" 
         href="DocumentManagement.do?command=DocumentStoreCenter&documentStoreId=<%= document.get("documentStoreId") %>"><%=document.get("title")%></a>
    <dhv:evaluate if="<%= size != null %>">
      -
      <%= size %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= modified != null %>">
      -
      <zeroio:tz timestamp="<%= modified %>" dateOnly="true" dateFormat="<%= DateFormat.LONG %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
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

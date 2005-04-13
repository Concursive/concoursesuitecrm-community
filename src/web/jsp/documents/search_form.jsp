<%-- 
  - 
  - Author(s): 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*, org.aspcfs.modules.documents.base.*, com.zeroio.iteam.beans.*" %>
<%@ page import="org.aspcfs.modules.documents.beans.DocumentsSearchBean" %>
<%@ page import="com.darkhorseventures.framework.beans.SearchBean" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="documentsSearchBean" class="org.aspcfs.modules.documents.beans.DocumentsSearchBean" scope="session" />
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
<body onLoad="document.search.query.focus()">
<form name="search" action="DocumentManagementSearch.do?auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="DocumentManagement.do"><dhv:label name="Documents" mainMenuItem="true">Documents</dhv:label></a> >
<dhv:label name="Search" subMenuItem="true">Search</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <b><dhv:label name="documents.search.search">Search</dhv:label></b>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="documents.search.scope">Scope</dhv:label>
    </td>
    <td>
      <select name="scope" onChange="this.form.query.focus()">
      <dhv:evaluate if="<%= request.getAttribute("DocumentStore") != null && ((DocumentStore) request.getAttribute("DocumentStore")).getId() > -1 %>">
          <option value="this" <%= selected(documentsSearchBean, SearchBean.THIS, SearchBean.UNDEFINED) %>><dhv:label name="documents.search.thisDocumentStore">This Document Store</dhv:label></option>
          <option value="thisDocuments" <%= selected(documentsSearchBean, SearchBean.THIS, SearchBean.DOCUMENTS) %>>&nbsp; <dhv:label name="documents.search.documents">Documents</dhv:label></option>
          <option value="thisDetails" <%= selected(documentsSearchBean, SearchBean.THIS, SearchBean.DETAILS) %>>&nbsp; <dhv:label name="documents.search.details">Details</dhv:label></option>
      </dhv:evaluate>
          <option value="all" <%= selected(documentsSearchBean, SearchBean.ALL, SearchBean.UNDEFINED) %>><dhv:label name="documents.search.allDocumentStoreData">All Document store data</dhv:label></option>
          <option value="allDocuments" <%= selected(documentsSearchBean, SearchBean.ALL, SearchBean.DOCUMENTS) %>>&nbsp; <dhv:label name="documents.search.documents">Documents</dhv:label></option>
          <option value="allDetails" <%= selected(documentsSearchBean, SearchBean.ALL, SearchBean.DETAILS) %>>&nbsp; <dhv:label name="documents.search.details">Details</dhv:label></option>
      </select>
      <dhv:evaluate if="<%= request.getAttribute("DocumentStore") != null && ((DocumentStore) request.getAttribute("DocumentStore")).getId() > -1 %>">
          <input type="hidden" name="documentStoreId" value="<%= ((DocumentStore) request.getAttribute("DocumentStore")).getId() %>" />
      </dhv:evaluate>
      <dhv:evaluate if="<%= request.getAttribute("DocumentStore") == null || ((DocumentStore) request.getAttribute("DocumentStore")).getId() == -1 %>">
        <input type="hidden" name="documentStoreId" value="-1" />
      </dhv:evaluate>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="documents.search.for">For</dhv:label>
    </td>
    <td>
      <input type="text" size="30" name="query" value="<%= toHtmlValue(documentsSearchBean.getQuery()) %>" />
    </td>
  </tr>
</table>
<br />
<input type="submit" name="Search" value="<dhv:label name="button.search">Search</dhv:label>" />
</form>
</body>

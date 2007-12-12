<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*, org.aspcfs.modules.documents.base.* " %>
<jsp:useBean id="documentStore" class="org.aspcfs.modules.documents.base.DocumentStore" scope="request"/>
<jsp:useBean id="currentMember" class="org.aspcfs.modules.documents.base.DocumentStoreTeamMember" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_macro-objects-16.gif" border="0" align="absmiddle">
      <dhv:label name="documents.details.overview">Overview</dhv:label>
    </td>
  </tr>
</table>
<br>
<dhv:evaluate if="<%= currentMember.getRoleId() <= DocumentStoreTeamMember.DOCUMENTSTORE_MANAGER %>">
<dhv:evaluate if="<%= !documentStore.isTrashed() %>">
<a href="DocumentManagement.do?command=ModifyDocumentStore&documentStoreId=<%= documentStore.getId() %>&return=DocumentStoreCenter">
  <dhv:label name="documents.details.modifyDocumentStore">Modify Document Store</dhv:label></a>
|
<a href="javascript:confirmDelete('DocumentManagement.do?command=TrashDocumentStore&documentStoreId=<%= documentStore.getId() %>');">
  <dhv:label name="documents.details.deleteDocumentStore">Delete Document Store</dhv:label></a>
</dhv:evaluate>
<dhv:evaluate if="<%= documentStore.isTrashed() %>">
<a href="javascript:confirmDelete('DocumentManagement.do?command=RestoreDocumentStore&documentStoreId=<%= documentStore.getId() %>');">
  <dhv:label name="documents.details.restoreDocumentStore">Restore Document Store</dhv:label></a>
</dhv:evaluate>
<br /><br />
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2">
      <strong>
        <dhv:label name="documents.details.generalInformation">General Information</dhv:label>
       </strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.status">Status</dhv:label>
    </td>
    <td>
      <dhv:evaluate if="<%= documentStore.getCloseDate() == null && documentStore.getApprovalDate() == null %>">
        <img border="0" src="images/box-hold.gif" alt="<dhv:label name='alt.onHold'>On Hold</dhv:label>" align="absmiddle">
      </dhv:evaluate>
      <dhv:evaluate if="<%= documentStore.getCloseDate() != null %>">
        <font color="blue">
        <dhv:label name="documents.details.archivedMessage" param='<%= "time="+getTime(pageContext,documentStore.getCloseDate(),User.getTimeZone(),DateFormat.SHORT,true,false,false,"&nbsp;") %>'>This document store was archived on&nbsp;<zeroio:tz timestamp="<%= documentStore.getCloseDate() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label>
        </font>
      </dhv:evaluate>
      <dhv:evaluate if="<%= documentStore.getCloseDate() == null%>">
        <dhv:evaluate if="<%= documentStore.getApprovalDate() == null %>">
          <font color="red"><dhv:label name="documents.details.unapprovedMessage">This document store is currently under review and has not been approved</dhv:label></font>
        </dhv:evaluate>
        <dhv:evaluate if="<%= documentStore.getApprovalDate() != null %>">
          <font color="darkgreen">
            <dhv:label name="documents.details.approvedMessage" param='<%= "time="+getTime(pageContext,documentStore.getApprovalDate(),User.getTimeZone(),DateFormat.SHORT,true,false,false,"&nbsp;")  %>'>This document store was approved on&nbsp; <zeroio:tz timestamp="<%= documentStore.getApprovalDate() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label>
          </font>
        </dhv:evaluate>
      </dhv:evaluate>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.title">Title</dhv:label>
    </td>
    <td>
      <%= toHtml(documentStore.getTitle()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.shortDescription">Short Description</dhv:label>
    </td>
    <td>
      <%= toHtml(documentStore.getShortDescription()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.startDate">Start Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= documentStore.getRequestDate() %>" dateOnly="true" timeZone="<%= documentStore.getRequestDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(documentStore.getRequestDateTimeZone())){%>
      <br />
      <zeroio:tz timestamp="<%= documentStore.getRequestDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.requestedBy">Requested By</dhv:label>
    </td>
    <td>
      <%= toHtml(documentStore.getRequestedBy()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.organization">Organization</dhv:label>
    </td>
    <td>
      <%= toHtml(documentStore.getRequestedDept()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.entered">Entered</dhv:label>
     </td>
    <td>
      <dhv:username id="<%= documentStore.getEnteredBy() %>"/>
      <zeroio:tz timestamp="<%= documentStore.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.modified">Modified</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= documentStore.getModifiedBy() %>"/>
      <zeroio:tz timestamp="<%= documentStore.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
  </tr>
</table>


<%-- 
  - Copyright(c) 2005 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="OpportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="Record" class="org.aspcfs.modules.base.CustomFieldRecord" scope="request"/>
<%@ include file="../initPage.jsp" %>
<<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Leads.do?command=Dashboard"><dhv:label name="pipeline.pipeline">PIPELINE</dhv:label></a> >
<a href="Leads.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>">Opportunity Details</a> >
<a href="LeadsFolders.do?command=FolderList&headerId=<%= OpportunityHeader.getId() %>"><dhv:label name="accounts.Folders">Folders</dhv:label></a> > 
<dhv:evaluate if="<%= (Category.getAllowMultipleRecords() && Record == null) %>">
  <dhv:label name="accounts.accounts_fields.ListOfFolderRecords">List of Folder Records</dhv:label>
</dhv:evaluate>
<dhv:evaluate if="<%= (Category.getAllowMultipleRecords() && Record != null) %>">
<a href="ExternalContacts.do?command=Fields&contactId=<%=OpportunityHeader.getId()%>&catId=<%= Category.getId() %>"><dhv:label name="accounts.accounts_fields.ListOfFolderRecords">List of Folder Records</dhv:label></a> >
  <dhv:label name="accounts.accounts_fields.FolderRecordDetails">Folder Record Details</dhv:label>
</dhv:evaluate>
<dhv:evaluate if="<%= (!Category.getAllowMultipleRecords()) %>">
  <dhv:label name="accounts.accounts_fields.FolderRecordDetails">Folder Record Details</dhv:label>
</dhv:evaluate>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="opportunities" selected="folders" object="OpportunityHeader" param='<%= "id=" + OpportunityHeader.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:label name="accounts.accounts_nofields.NoCustomFoldersConfigured">There are currently no custom folders configured for this module.</dhv:label><br>
  <dhv:label name="accounts.accounts_nofields.CustomfoldersConfiguredAdministrator">Custom folders can be configured by an administrator.</dhv:label><br>
</dhv:container>

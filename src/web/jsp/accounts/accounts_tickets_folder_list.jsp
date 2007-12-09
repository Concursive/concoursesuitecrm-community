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
--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.base.*" %>
<%@ page import="java.text.DateFormat" %>
<jsp:useBean id="orgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%= ticketDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%= ticketDetails.getOrgId() %>"><dhv:label name="accounts.tickets.tickets">Tickets</dhv:label></a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%= ticketDetails.getId() %>"><dhv:label name="accounts.tickets.details">Ticket Details</dhv:label></a> >
<dhv:label name="accounts.Folders">Folders</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="tickets" object="orgDetails" param='<%= "orgId=" + orgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:container name="accountstickets" selected="folders" object="ticketDetails" param='<%= "id=" + ticketDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
    <%@ include file="accounts_ticket_header_include.jsp" %>
    &nbsp;<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th>
        <dhv:label name="accounts.accounts_documents_folders_add.Folder">Folder</dhv:label>
      </th>
      <th>
        <dhv:label name="accounts.accounts_documents_folders_add.Records">Records</dhv:label>
      </th>
    </tr>
    <%
      Iterator j = categoryList.iterator();
      if ( j.hasNext() ) {
        int rowid = 0;
        int i = 0;
        while (j.hasNext()) {
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        CustomFieldCategory thisCategory = (CustomFieldCategory) j.next();
    %>
    <tr class="containerBody">
      <td class="row<%= rowid %>">
        <a href="AccountTicketFolders.do?command=Fields&ticketId=<%= ticketDetails.getId() %>&catId=<%= thisCategory.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= toHtml(thisCategory.getName()) %></a>
      </td>
      <td class="row<%= rowid %>">
        <%= thisCategory.getNumberOfRecords() %>
      </td>
    </tr>
    <% } %>
 <% } else { %>
    <tr class="row2">
      <td colspan="2">
        <dhv:label name="accounts.accounts_fields_list.NoCustomFoldersAvailable">No custom folders available.</dhv:label>
      </td>
    </tr>
    <% } %>
  </table>
</dhv:container>
</dhv:container>


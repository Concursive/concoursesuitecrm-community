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
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="TicketDocumentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="troubletickets_documents_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/confirmDelete.js"></script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTickets.do"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> > 
<% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
  <a href="TroubleTickets.do?command=SearchTicketsForm">Search Form</a> >
  <a href="TroubleTickets.do?command=SearchTickets">Search Results</a> >
<%}else{%> 
  <a href="TroubleTickets.do?command=Home"><dhv:label name="tickets.view">View Tickets</dhv:label></a> >
<%}%>
<a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>"><dhv:label name="tickets.details">Ticket Details</dhv:label></a> >
Documents
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="ticket_header_include.jsp" %>
<% String param1 = "id=" + TicketDetails.getId(); %>
<dhv:container name="tickets" selected="documents" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
		<td class="containerBack">
      <%
        String permission_doc_folders_add = "tickets-tickets-edit";
        String permission_doc_files_upload = "tickets-tickets-edit";
        String permission_doc_folders_edit = "tickets-tickets-edit";
        String documentFolderAdd ="TroubleTicketsDocumentsFolders.do?command=Add&tId="+TicketDetails.getId()+"&column=subject";
        String documentFileAdd = "TroubleTicketsDocuments.do?command=Add&tId="+TicketDetails.getId();
        String documentFolderModify = "TroubleTicketsDocumentsFolders.do?command=Add&tId="+TicketDetails.getId();
        String documentFolderList = "TroubleTicketsDocuments.do?command=View&tId="+TicketDetails.getId()+"&column=subject";
        String documentFileDetails = "TroubleTicketsDocuments.do?command=Details&tId="+TicketDetails.getId();
        String documentModule = "HelpDesk";
        String specialID = ""+TicketDetails.getId();
      %>
      <%@ include file="../accounts/documents_list_include.jsp" %>&nbsp;
    </td>
  </tr>
</table>
<%--
    <dhv:permission name="tickets-tickets-edit"><a href="TroubleTicketsDocuments.do?command=Add&tId=<%= TicketDetails.getId() %>&folderId=<%= FileItemList.getFolderId() %>">Add a Document</a><br></dhv:permission>
    <center><%= TicketDocumentListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="TicketDocumentListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="10" align="center">Action</th>
    <th>
      <strong><a href="TroubleTicketsDocuments.do?command=View&tId=<%= TicketDetails.getId() %>&column=subject">Item</a></strong>
      <%= TicketDocumentListInfo.getSortIcon("subject") %>
    </th>
    <th align="center">Ext</th>
    <th align="center">Size</th>
    <th align="center">Version</th>
    <dhv:permission name="tickets-tickets-edit">
      <th>&nbsp;</th>
    </dhv:permission>
    <th align="center">Submitted</th>
  </tr>
    <%
      Iterator j = FileItemList.iterator();
      if ( j.hasNext() ) {
        int rowid = 0;
        int i = 0;
        while (j.hasNext()) {
          i++;
          rowid = (rowid != 1?1:2);
          FileItem thisFile = (FileItem)j.next();
    %>      
        <tr class="row<%= rowid %>">
          <td width="10" valign="middle" align="center" nowrap>
            <%-- Use the unique id for opening the menu, and toggling the graphics -- %>
            <a href="javascript:displayMenu('select<%= i %>','menuFile', '<%= TicketDetails.getId() %>','<%= thisFile.getId() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuFile');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
          </td>
          <td valign="middle" width="100%">
            <a href="TroubleTicketsDocuments.do?command=Details&tId=<%= TicketDetails.getId() %>&fid=<%= thisFile.getId() %>"><%= thisFile.getImageTag() %><%= toHtml(thisFile.getSubject()) %></a>
          </td>
          <td align="center"><%= toHtml(thisFile.getExtension()) %>&nbsp;</td>
          <td align="center" valign="middle" nowrap>
            <%= thisFile.getRelativeSize() %> k&nbsp;
          </td>
          <td align="right" valign="middle" nowrap>
            <%= thisFile.getVersion() %>&nbsp;
          </td>
        <dhv:permission name="tickets-tickets-edit">
          <td align="right" valign="middle" nowrap>
            [<a href="TroubleTicketsDocuments.do?command=AddVersion&tId=<%= TicketDetails.getId() %>&fid=<%= thisFile.getId() %>">Add Version</a>]
          </td>
        </dhv:permission>
          <td nowrap>
            <zeroio:tz timestamp="<%= thisFile.getModified() %>" /><br>
            <dhv:username id="<%= thisFile.getEnteredBy() %>"/>
          </td>
        </tr>
    <%}%>
      </table>
      <br>
      <dhv:pagedListControl object="TicketDocumentListInfo"/>
    <%} else {%>
        <tr class="containerBody">
          <td colspan="7">
            No documents found.
          </td>
        </tr>
      </table>
    <%}%>
    </td>
  </tr>
</table>
--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="AccountTicketDocumentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="FileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_tickets_documents_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/confirmDelete.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>">Account Details</a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=TicketDetails.getOrgId()%>">Tickets</a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%=TicketDetails.getId()%>">Ticket Details</a> >
Documents
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + TicketDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="tickets" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
  	<td class="containerBack">
      <%@ include file="accounts_ticket_header_include.jsp" %>
      <% String param2 = "id=" + TicketDetails.getId(); %>
      [ <dhv:container name="accountstickets" selected="documents" param="<%= param2 %>"/> ]
      <br><br>
      <dhv:permission name="accounts-accounts-tickets-documents-add"><a href="AccountTicketsDocuments.do?command=Add&tId=<%= TicketDetails.getId() %>&folderId=<%= FileItemList.getFolderId() %>">Add a Document</a><br></dhv:permission>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AccountTicketDocumentListInfo"/>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
        <th width="10" align="center"><strong>Action</strong></th>
        <th>
          <strong><a href="AccountTicketsDocuments.do?command=View&tId=<%= TicketDetails.getId() %>&column=subject">Item</a></strong>
          <%= AccountTicketDocumentListInfo.getSortIcon("subject") %>
        </th>
        <th align="center"><strong>Ext</strong></th>
        <th align="center"><strong>Size</strong></th>
        <th align="center"><strong>Version</strong></th>
        <dhv:permission name="accounts-accounts-tickets-documents-add">
          <th>&nbsp;</th>
        </dhv:permission>
        <th align="center"><strong>Submitted</strong></th>
      </tr>
        <%
          Iterator j = FileItemList.iterator();
          if ( j.hasNext() ) {
            int rowid = 0;
            int i =0;
            while (j.hasNext()) {
              i++;
              rowid = (rowid != 1?1:2);
              FileItem thisFile = (FileItem)j.next();
        %>      
            <tr class="row<%= rowid %>">
              <td width="10" valign="middle" align="center" nowrap>
                <%-- Use the unique id for opening the menu, and toggling the graphics --%>
                <a href="javascript:displayMenu('select<%= i %>','menuFile','<%= TicketDetails.getId() %>','<%= thisFile.getId() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuFile');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
              </td>
              <td valign="middle" width="100%">
                <a href="AccountTicketsDocuments.do?command=Details&tId=<%= TicketDetails.getId() %>&fid=<%= thisFile.getId() %>"><%= thisFile.getImageTag() %><%= toHtml(thisFile.getSubject()) %></a>
              </td>
              <td align="center"><%= toHtml(thisFile.getExtension()) %>&nbsp;</td>
              <td align="center" valign="middle" nowrap>
                <%= thisFile.getRelativeSize() %> k&nbsp;
              </td>
              <td align="right" valign="middle" nowrap>
                <%= thisFile.getVersion() %>&nbsp;
              </td>
            <dhv:permission name="accounts-accounts-tickets-documents-add">
              <td align="right" valign="middle" nowrap>
                [<a href="AccountTicketsDocuments.do?command=AddVersion&tId=<%= TicketDetails.getId() %>&fid=<%= thisFile.getId() %>">Add Version</a>]
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
          <dhv:pagedListControl object="AccountTicketDocumentListInfo"/>
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

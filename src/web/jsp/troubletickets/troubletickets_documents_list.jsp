<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="TicketDocumentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="FileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="troubletickets_documents_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/confirmDelete.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="TroubleTickets.do">Tickets</a> > 
<a href="TroubleTickets.do?command=Home">View Tickets</a> >
<a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>">Ticket Details</a> >
Documents
</td>
</tr>
</table>
<%-- End Trails --%>
<strong>Ticket # <%= TicketDetails.getPaddedId() %><br>
<%= toHtml(TicketDetails.getCompanyName()) %></strong>
<dhv:evaluate exp="<%= !(TicketDetails.getCompanyEnabled()) %>"><font color="red">(account disabled)</font></dhv:evaluate>
<% String param1 = "id=" + TicketDetails.getId(); %>
<dhv:container name="tickets" selected="documents" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
		<td class="containerBack">
    <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
      <font color="red">This ticket was closed on <%= toHtml(TicketDetails.getClosedString()) %></font><br>
      &nbsp;<br>
    </dhv:evaluate>
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
            <%-- Use the unique id for opening the menu, and toggling the graphics --%>
            <a href="javascript:displayMenu('menuFile', '<%= TicketDetails.getId() %>','<%= thisFile.getId() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
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
            <dhv:tz timestamp="<%= thisFile.getModified() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/><br>
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

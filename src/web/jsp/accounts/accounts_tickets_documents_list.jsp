<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="AccountTicketDocumentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="FileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/confirmDelete.js"></script>
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>">Account Details</a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=TicketDetails.getOrgId()%>">Tickets</a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%=TicketDetails.getId()%>">Ticket Details</a> >
Documents<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%=toHtml(TicketDetails.getCompanyName())%></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + TicketDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="tickets" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
  	<td class="containerBack">
      <% String param2 = "id=" + TicketDetails.getId(); %>
      <strong>Ticket # <%=TicketDetails.getPaddedId()%>:</strong>&nbsp;[ <dhv:container name="accountstickets" selected="documents" param="<%= param2 %>"/> ]
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
      <font color="red">This ticket was closed on <%= toHtml(TicketDetails.getClosedString()) %></font>
      </dhv:evaluate>
      <br><br>
      <dhv:permission name="accounts-accounts-tickets-edit"><a href="AccountTicketsDocuments.do?command=Add&tId=<%= TicketDetails.getId() %>&folderId=<%= FileItemList.getFolderId() %>">Add a Document</a><br></dhv:permission>
      <center><%= AccountTicketDocumentListInfo.getAlphabeticalPageLinks() %></center>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AccountTicketDocumentListInfo"/>
      <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
      <tr class="title">
        <td width="10" align="center"><strong>Action</strong></td>
        <td>
          <strong><a href="AccountTicketsDocuments.do?command=View&tId=<%= TicketDetails.getId() %>&column=subject">Item</a></strong>
          <%= AccountTicketDocumentListInfo.getSortIcon("subject") %>
        </td>
        <td align="center"><strong>Ext</strong></td>
        <td align="center"><strong>Size</strong></td>
        <td align="center"><strong>Version</strong></td>
        <dhv:permission name="accounts-accounts-tickets-edit">
          <td>&nbsp;</td>
        </dhv:permission>
        <td align="center"><strong>Submitted</strong></td>
      </tr>
        <%
          Iterator j = FileItemList.iterator();
          if ( j.hasNext() ) {
            int rowid = 0;
            while (j.hasNext()) {
              rowid = (rowid != 1?1:2);
              FileItem thisFile = (FileItem)j.next();
        %>      
            <tr class="row<%= rowid %>">
              <td width="10" valign="middle" align="center" nowrap>
                <a href="AccountTicketsDocuments.do?command=Download&tId=<%= TicketDetails.getId() %>&fid=<%= thisFile.getId() %>">Download</a><br>
                <dhv:permission name="accounts-accounts-tickets-edit"><a href="AccountTicketsDocuments.do?command=Modify&fid=<%= thisFile.getId() %>&tId=<%= TicketDetails.getId()%>">Edit</a></dhv:permission><dhv:permission name="accounts-accounts-tickets-edit" all="true">|</dhv:permission><dhv:permission name="accounts-accounts-tickets-edit"><a href="javascript:confirmDelete('AccountTicketsDocuments.do?command=Delete&fid=<%= thisFile.getId() %>&tId=<%= TicketDetails.getId()%>');">Del</a></dhv:permission>
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
            <dhv:permission name="accounts-accounts-tickets-edit">
              <td align="right" valign="middle" nowrap>
                [<a href="AccountTicketsDocuments.do?command=AddVersion&tId=<%= TicketDetails.getId() %>&fid=<%= thisFile.getId() %>">Add Version</a>]
              </td>
            </dhv:permission>
              <td nowrap>
                <%= thisFile.getModifiedDateTimeString() %><br>
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

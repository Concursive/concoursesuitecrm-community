<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.inputForm.subject.focus();">
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>">Account Details</a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=TicketDetails.getOrgId()%>">Tickets</a>
<a href="AccountTicketsDocuments.do?command=View&tId=<%=TicketDetails.getId()%>">Documents</a> >
Details<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%=toHtml(TicketDetails.getCompanyName())%> - Ticket # <%=TicketDetails.getPaddedId()%></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <%-- submenu for accounts --%>
      <% String param1 = "orgId=" + TicketDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="tickets" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
  	<td class="containerBack">
      <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr class="containerMenu">
          <td>
            <%-- submenu for tickets --%>
            <% String param2 = "id=" + TicketDetails.getId(); %>
            <dhv:container name="accountstickets" selected="history" param="<%= param2 %>"/>
            <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
            <font color="red">This ticket was closed on <%= toHtml(TicketDetails.getClosedString()) %></font>
            </dhv:evaluate>
         </td>
        </tr>
        <tr>
          <td>
            <%= showError(request, "actionError") %>
            <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
              <tr class="title">
                <td colspan="7">
                  <strong>All Versions of this Document</strong>
                </td>
              </tr>
              <tr class="title2">
                <td width="10" align="center">Action</td>
                <td>Item</td>
                <td>Size</td>
                <td>Version</td>
                <td>Submitted</td>
                <td>Sent By</td>
                <td>D/L</td>
              </tr>
            <%
              Iterator versionList = FileItem.getVersionList().iterator();
              int rowid = 0;
              while (versionList.hasNext()) {
                rowid = (rowid != 1?1:2);
                FileItemVersion thisVersion = (FileItemVersion)versionList.next();
            %>      
                <tr class="row<%= rowid %>">
                  <td width="10" align="center" rowspan="2" nowrap>
                    <a href="TroubleTicketsDocuments.do?command=Download&tId=<%= TicketDetails.getId() %>&fid=<%= FileItem.getId() %>&ver=<%= thisVersion.getVersion() %>">Download</a>
                  </td>
                  <td width="100%">
                    <%= FileItem.getImageTag() %><%= thisVersion.getClientFilename() %>
                  </td>
                  <td align="right" nowrap>
                    <%= thisVersion.getRelativeSize() %> k&nbsp;
                  </td>
                  <td align="right" nowrap>
                    <%= thisVersion.getVersion() %>&nbsp;
                  </td>
                  <td nowrap>
                    <%= thisVersion.getEnteredDateTimeString() %>
                  </td>
                  <td>
                    <dhv:username id="<%= thisVersion.getEnteredBy() %>"/>
                  </td>
                  <td align="right">
                    <%= thisVersion.getDownloads() %>
                  </td>
                </tr>
                <tr class="row<%= rowid %>">
                  <td colspan="6">
                    <i><%= thisVersion.getSubject() %></i>
                  </td>
                </tr>
              <%}%>
            </table>
          </td>
         </tr>
        <%-- ticket container end --%>
      </table>
    </td>
  </tr>
  <%-- account container end --%>
</table>
</body>

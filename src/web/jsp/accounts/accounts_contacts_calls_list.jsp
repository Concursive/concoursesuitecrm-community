<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CallList" class="org.aspcfs.modules.contacts.base.CallList" scope="request"/>
<jsp:useBean id="AccountContactCallsListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_contacts_calls_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Accounts.do?command=Search">Search Results</a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard">Dashboard</a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Contacts</a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Contact Details</a> >
Calls
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="contacts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
    <% String param1 = "id=" + ContactDetails.getId(); 
    %>
        <strong><%= ContactDetails.getNameLastFirst() %>:</strong>
        [ <dhv:container name="accountscontacts" selected="calls" param="<%= param1 %>"/> ]
      <br>
      <br>
      
      <dhv:permission name="accounts-accounts-contacts-calls-add"><a href="AccountContactsCalls.do?command=Add&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">Add a Call</a><br></dhv:permission>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AccountContactCallsListInfo"/>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
          <th>
            <strong>Action</strong>
          </th>
          <th>
            <strong>Subject</strong>
          </th>
          <th>
            <strong>Type</strong>
          </th>
          <th>
            <strong>Length</strong>
          </th>
          <th>
            <strong>Date</strong>
          </th>
        </tr>
    <%
      Iterator j = CallList.iterator();
      if ( j.hasNext() ) {
        int rowid = 0;
        int i =0;
          while (j.hasNext()) {
          i++;
            rowid = (rowid != 1?1:2);
            Call thisCall = (Call)j.next();
    %>      
        <tr class="containerBody">
          <td width="8" valign="center" nowrap class="row<%= rowid %>">
            <%-- Use the unique id for opening the menu, and toggling the graphics --%>
             <a href="javascript:displayMenu('select<%= i %>','menuCall', '<%= ContactDetails.getId() %>', '<%= thisCall.getId() %>');"
             onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuCall');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
          </td>
          <td width="100%" valign="center" class="row<%= rowid %>">
            <a href="AccountContactsCalls.do?command=Details&id=<%= thisCall.getId() %>&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
            <%= toHtml(thisCall.getSubject()) %>
            </a>
          </td>
          <td valign="center" nowrap class="row<%= rowid %>">
            <%= toHtml(thisCall.getCallType()) %>
          </td>
          <td align="center" valign="center" nowrap class="row<%= rowid %>">
            <%= toHtml(thisCall.getLengthText()) %>
          </td>
          <td valign="center" nowrap class="row<%= rowid %>">
            <zeroio:tz timestamp="<%= thisCall.getEntered() %>" />
          </td>
        </tr>
     <%}%>
    <%} else {%>
        <tr class="containerBody">
          <td colspan="5">
            No calls found.
          </td>
        </tr>
    <%}%>
    </table>
    <br>
    <dhv:pagedListControl object="AccountContactCallsListInfo"/>
   </td>
 </tr>
</table>



<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CallList" class="org.aspcfs.modules.contacts.base.CallList" scope="request"/>
<jsp:useBean id="CompletedCallList" class="org.aspcfs.modules.contacts.base.CallList" scope="request"/>
<jsp:useBean id="AccountContactCallsListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AccountContactCompletedCallsListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="CallTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="callResultList" class="org.aspcfs.modules.contacts.base.CallResultList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_contacts_calls_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<%-- Preload image rollovers for drop-down menu --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select');
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Accounts.do?command=Search">Search Results</a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard">Dashboard</a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Contacts</a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Contact Details</a> >
Activities
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="contacts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <%-- include contact menu --%>
      <% 
      int i = 0;
      String param1 = "id=" + ContactDetails.getId(); 
    %>
        <strong><%= ContactDetails.getNameLastFirst() %>:</strong>
        [ <dhv:container name="accountscontacts" selected="calls" param="<%= param1 %>"/> ]
        <br>
        <br>
      <dhv:evaluate if="<%= !isPopup(request) %>">
      <dhv:permission name="accounts-accounts-contacts-calls-add"><a href="AccountContactsCalls.do?command=Add&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>&return=list">Add an Activity</a><br><br></dhv:permission>
      </dhv:evaluate>
<% if ((request.getParameter("pagedListSectionId") == null && !AccountContactCompletedCallsListInfo.getExpandedSelection()) || AccountContactCallsListInfo.getExpandedSelection()) { %>
      <%-- Pending list --%>
      <dhv:pagedListStatus showExpandLink="true" title="Pending Activities" object="AccountContactCallsListInfo"/>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
          <th>
            <strong>Action</strong>
          </th>
          <th nowrap="true">
            <strong>Due Date</strong>
          </th>
          <th nowrap="true">
            <strong>Assigned To</strong>
          </th>
          <th>
            <strong>Type</strong>
          </th>
          <th width="100%">
            <strong>Description</strong>
          </th>
        </tr>
<%
      Iterator j = CallList.iterator();
      if ( j.hasNext() ) {
        int rowid = 0;
          while (j.hasNext()) {
          i++;
            rowid = (rowid != 1?1:2);
            Call thisCall = (Call) j.next();
%>
        <tr class="row<%= rowid %>">
          <td <%= AccountContactCallsListInfo.getExpandedSelection() && !"".equals(toString(thisCall.getFollowupNotes())) ? "rowspan=\"2\"" : ""%> width="8" valign="top" nowrap>
            <%-- Use the unique id for opening the menu, and toggling the graphics --%>
             <a href="javascript:displayMenu('select<%= i %>','menuCall', '<%= ContactDetails.getId() %>', '<%= thisCall.getId() %>', 'pending');"
             onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>);hideMenu('menuCall');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
          </td>
          <td valign="top" nowrap>
            <zeroio:tz timestamp="<%= thisCall.getAlertDate() %>"/>
          </td>
          <td valign="top" nowrap>
            <dhv:username id="<%= thisCall.getOwner() %>" firstInitialLast="true"/>
          </td>
          <td valign="top" nowrap>
            <%= toHtml(CallTypeList.getSelectedValue(thisCall.getAlertCallTypeId())) %>
          </td>
          <td width="100%" valign="top">
            <a href="AccountContactsCalls.do?command=Details&id=<%= thisCall.getId() %>&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>&view=pending">
            <%= toHtml(thisCall.getAlertText()) %>
            </a>
          </td>
        </tr>
        <dhv:evaluate if="<%= AccountContactCallsListInfo.getExpandedSelection() && !"".equals(toString(thisCall.getFollowupNotes())) %>">
            <tr class="row<%= rowid %>">
              <td colspan="5" valign="top">
                <%= toHtmlValue(thisCall.getFollowupNotes()) %>
              </td>
            </tr>
        </dhv:evaluate>
     <%}%>
    <%} else {%>
        <tr class="containerBody">
          <td colspan="5">
            No activities found.
          </td>
        </tr>
    <%}%>
     </table>
     <br>
<%}%>
<% if ((request.getParameter("pagedListSectionId") == null && !AccountContactCallsListInfo.getExpandedSelection()) || AccountContactCompletedCallsListInfo.getExpandedSelection()) { %>
     <%-- Completed/Canceled list --%>
      <dhv:pagedListStatus showExpandLink="true" title="Completed/Canceled Activities" object="AccountContactCompletedCallsListInfo"/>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
          <dhv:evaluate if="<%= !isPopup(request) %>">
          <th>
            <strong>Action</strong>
          </th>
          </dhv:evaluate>
          <th>
            Status
          </th>
          <th>
            <strong>Type</strong>
          </th>
          <th width="100%">
            <strong>Subject</strong>
          </th>
          <th>
            Result
          </th>
          <th nowrap="true">
            <strong>Entered By</strong>
          </th>
          <th nowrap="true">
            Entered
          </th>
        </tr>
<%
      Iterator jc = CompletedCallList.iterator();
      if ( jc.hasNext() ) {
        int rowid = 0;
          while (jc.hasNext()) {
          i++;
            rowid = (rowid != 1?1:2);
            Call thisCall = (Call) jc.next();
%>
        <tr class="row<%= rowid %>">
        <dhv:evaluate if="<%= !isPopup(request) %>">
          <td <%= AccountContactCompletedCallsListInfo.getExpandedSelection() && !"".equals(toString(thisCall.getNotes())) ? "rowspan=\"2\"" : ""%> width="8" valign="top" nowrap>
             <%-- Use the unique id for opening the menu, and toggling the graphics --%>
             <a href="javascript:displayMenu('select<%= i %>','menuCall','<%= ContactDetails.getId() %>','<%= thisCall.getId() %>','<%= thisCall.getStatusId() == Call.CANCELED ? "cancel" : "" %>');"
             onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>);hideMenu('menuCall');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
          </td>
          </dhv:evaluate>
          <td valign="top" nowrap>
            <%= thisCall.getStatusString() %>
          </td>
          <td valign="top" nowrap>
            <%= toHtml(CallTypeList.getSelectedValue(thisCall.getCallTypeId())) %>
          </td>
          <td width="100%" valign="top">
            <dhv:evaluate if="<%= !isPopup(request) %>">
            <a href="AccountContactsCalls.do?command=Details&id=<%= thisCall.getId() %>&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
              <%= toHtml(thisCall.getSubject()) %>
            </a>
            </dhv:evaluate>
            <dhv:evaluate if="<%= isPopup(request) %>">
              <%= toHtml(thisCall.getSubject()) %>
            </dhv:evaluate>
          </td>
          <td nowrap valign="top">
            <%= toHtml(callResultList.getLookupList(thisCall.getResultId()).getSelectedValue(thisCall.getResultId())) %>
          </td>
          <td valign="top" nowrap>
            <dhv:username id="<%= thisCall.getEnteredBy() %>" firstInitialLast="true"/>
          </td>
          <td valign="top" nowrap>
            <zeroio:tz timestamp="<%= thisCall.getEntered() %>" />
          </td>
        </tr>
        <dhv:evaluate if="<%= AccountContactCompletedCallsListInfo.getExpandedSelection()  && !"".equals(toString(thisCall.getNotes()))%>">
        <tr class="row<%= rowid %>">
          <td colspan="8" valign="top">
            <%= toHtmlValue(thisCall.getNotes()) %>
          </td>
        </tr>
        </dhv:evaluate>
     <%}%>
    <%} else {%>
        <tr class="containerBody">
          <td colspan="8">
            No activities found.
          </td>
        </tr>
    <%}%>
     </table>
<%}%>
     <%-- End Container --%>
   </td>
 </tr>
</table>



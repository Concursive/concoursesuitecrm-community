<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="CallList" class="org.aspcfs.modules.contacts.base.CallList" scope="request"/>
<jsp:useBean id="CallListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="companydirectory_listcalls_menu.jsp" %>
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
<a href="ExternalContacts.do">Contacts</a> > 
<a href="ExternalContacts.do?command=SearchContacts">Search Results</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>">Contact Details</a> >
Calls
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%@ include file="contact_details_header_include.jsp" %>
<% String param1 = "id=" + ContactDetails.getId(); 
    String param2 = addLinkParams(request, "popup|popupType|actionId"); %>
<dhv:container name="contacts" selected="calls" param="<%= param1 %>" appendToUrl="<%= param2 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<% if(ContactDetails.getOrgId() < 0){ %>
<dhv:permission name="contacts-external_contacts-calls-add">
<a href="ExternalContactsCalls.do?command=Add&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">Add a Call</a><br></dhv:permission>
<% }else{ %>
<dhv:permission name="contacts-external_contacts-calls-add,accounts-accounts-contacts-calls-add" all="true">
<a href="ExternalContactsCalls.do?command=Add&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">Add a Call</a><br></dhv:permission>
<% } %>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="CallListInfo"/>
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
    int count  =0;
	    while (j.hasNext()) {
        count++;
		    rowid = (rowid != 1?1:2);
        Call thisCall = (Call)j.next();
%>      
    <tr class="containerBody">
      <td width="8" valign="center" nowrap class="row<%= rowid %>">
        <%-- check if user has edit or delete based on the type of contact --%>
          <%
            int hasEditPermission = 0;
            int hasDeletePermission = 0;
            if(ContactDetails.getOrgId() < 0){ %>
            <dhv:permission name="contacts-external_contacts-calls-edit">
              <% hasEditPermission = 1; %>
            </dhv:permission>
            <dhv:permission name="contacts-external_contacts-calls-delete">
             <%  hasDeletePermission = 1; %>
            </dhv:permission>
          <% }else{ %>
            <dhv:permission name="contacts-external_contacts-calls-edit,accounts-accounts-contacts-calls-edit"  all="true">
             <% hasEditPermission = 1; %>
            </dhv:permission>
            <dhv:permission name="contacts-external_contacts-calls-delete,,accounts-accounts-contacts-calls-delete" all="true">
             <% hasDeletePermission = 1; %>
            </dhv:permission>
          <% } %>
          
          <%-- Use the unique id for opening the menu, and toggling the graphics --%>
           <a href="javascript:displayMenu('select<%= count %>','menuCall','<%= ContactDetails.getId() %>', '<%= thisCall.getId() %>', '<%= hasEditPermission %>', '<%= hasDeletePermission %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuCall');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
      </td>
      <td width="100%" valign="center" class="row<%= rowid %>">
        <a href="ExternalContactsCalls.do?command=Details&id=<%= thisCall.getId() %>&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
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
      <zeroio:tz timestamp="<%= thisCall.getEntered()  %>" />
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
<dhv:pagedListControl object="CallListInfo"/>
</td>
</tr>
</table>


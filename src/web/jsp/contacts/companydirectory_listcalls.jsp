<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="CallList" class="org.aspcfs.modules.contacts.base.CallList" scope="request"/>
<jsp:useBean id="CallListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<dhv:evaluate if="<%= !isPopup(request) %>">
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>">Contact Details</a> >
Calls<br>
<hr color="#BFBFBB" noshade>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + ContactDetails.getId(); 
          String param2 = addLinkParams(request, "popup|popupType|actionId"); %>
      <dhv:container name="contacts" selected="calls" param="<%= param1 %>" appendToUrl="<%= param2 %>"/>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="contacts-external_contacts-calls-add"><a href="ExternalContactsCalls.do?command=Add&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">Add a Call</a></dhv:permission>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="CallListInfo"/>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
  <dhv:permission name="contacts-external_contacts-calls-edit,contacts-external_contacts-calls-delete">
    <td>
      <strong>Action</strong>
    </td>
   </dhv:permission> 
    <td>
      <strong>Subject</strong>
    </td>
    <td>
      <strong>Type</strong>
    </td>
    <td>
      <strong>Length</strong>
    </td>
    <td>
      <strong>Date</strong>
    </td>
  </tr>
<%
	Iterator j = CallList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
	    while (j.hasNext()) {
		    rowid = (rowid != 1?1:2);
        Call thisCall = (Call)j.next();
%>      
    <tr class="containerBody">
      <dhv:permission name="contacts-external_contacts-calls-edit,contacts-external_contacts-calls-delete">
      <td width="8" valign="center" nowrap class="row<%= rowid %>">
          <dhv:permission name="contacts-external_contacts-calls-edit"><a href="ExternalContactsCalls.do?command=Modify&id=<%= thisCall.getId() %>&contactId=<%= ContactDetails.getId()%>&return=list<%= addLinkParams(request, "popup|popupType|actionId") %>">Edit</a></dhv:permission><dhv:permission name="contacts-external_contacts-calls-edit,contacts-external_contacts-calls-delete" all="true">|</dhv:permission><dhv:permission name="contacts-external_contacts-calls-delete"><a href="javascript:popURL('ExternalContactsCalls.do?command=ConfirmDelete&id=<%= thisCall.getId() %>&contactId=<%= ContactDetails.getId()%>&popup=true<%= addLinkParams(request, "popupType|actionId") %>', 'CONFIRM_DELETE','320','200','yes','no');">Del</a></dhv:permission>
      </td>
      </dhv:permission>
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
        <%= toHtml(thisCall.getEnteredString()) %>
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


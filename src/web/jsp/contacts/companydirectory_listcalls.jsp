<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="CallList" class="org.aspcfs.modules.contacts.base.CallList" scope="request"/>
<jsp:useBean id="CallListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
Calls<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + ContactDetails.getId(); %>      
      <dhv:container name="contacts" selected="calls" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="contacts-external_contacts-calls-add"><a href="ExternalContactsCalls.do?command=Add&contactId=<%= ContactDetails.getId() %>">Add a Call</a></dhv:permission>
<center><%= CallListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="CallListInfo"/>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
  <dhv:permission name="contacts-external_contacts-calls-edit,contacts-external_contacts-calls-delete">
    <td valign=center align=left>
      <strong>Action</strong>
    </td>
   </dhv:permission> 
    <td valign=center align=left>
      <strong>Subject</strong>
    </td>
    <td valign=center align=left>
      <strong>Type</strong>
    </td>
    <td valign=center align=center>
      <strong>Length</strong>
    </td>
    <td valign=center align=center>
      <strong>Date</strong>
    </td>
  </tr>
<%
	Iterator j = CallList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
	    while (j.hasNext()) {
		
        if (rowid != 1) {
          rowid = 1;
        } else {
          rowid = 2;
        }
		
		Call thisCall = (Call)j.next();
%>      
    <tr class="containerBody">
      <dhv:permission name="contacts-external_contacts-calls-edit,contacts-external_contacts-calls-delete">
      <td width=8 valign=center nowrap class="row<%= rowid %>">
          <dhv:permission name="contacts-external_contacts-calls-edit"><a href="ExternalContactsCalls.do?command=Modify&id=<%= thisCall.getId() %>&contactId=<%= ContactDetails.getId()%>&return=list">Edit</a></dhv:permission><dhv:permission name="contacts-external_contacts-calls-edit,contacts-external_contacts-calls-delete" all="true">|</dhv:permission><dhv:permission name="contacts-external_contacts-calls-delete"><a href="javascript:confirmDelete('ExternalContactsCalls.do?command=Delete&id=<%= thisCall.getId() %>&contactId=<%= ContactDetails.getId()%>');">Del</a></dhv:permission>
      </td>
      </dhv:permission>
      <td width="100%" valign=center class="row<%= rowid %>">
        <a href="ExternalContactsCalls.do?command=Details&id=<%= thisCall.getId() %>&contactId=<%= ContactDetails.getId() %>">
        <%= toHtml(thisCall.getSubject()) %>
        </a>
      </td>
      <td valign=center nowrap class="row<%= rowid %>">
        <%= toHtml(thisCall.getCallType()) %>
      </td>
      <td align=center valign=center nowrap class="row<%= rowid %>">
        <%= toHtml(thisCall.getLengthText()) %>
      </td>
      <td valign=center nowrap class="row<%= rowid %>">
        <%= toHtml(thisCall.getEnteredString()) %>
      </td>
    </tr>
 <%}%>
<%} else {%>
		<tr class="containerBody">
      <td colspan=5 valign=center>
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


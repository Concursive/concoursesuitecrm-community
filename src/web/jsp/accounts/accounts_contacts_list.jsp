<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.Organization" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
Contacts<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="contacts" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="accounts-accounts-contacts-add"><a href="Contacts.do?command=Add&orgId=<%=request.getParameter("orgId")%>">Add a Contact</a></dhv:permission>
<center><%= ContactListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ContactListInfo"/>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
<tr class="title">
  <dhv:permission name="accounts-accounts-contacts-edit,accounts-accounts-contacts-delete">
  <td valign=center align=left>
    <strong>Action</strong>
  </td>
  </dhv:permission>
  <td width=30% valign=center align=left>
    <strong>Name</strong>
  </td>  
  <td width=20% valign=center align=left>
    <strong>Title</strong>
  </td>   
  <td width=20% valign=center align=left>
    <strong>Phone</strong>
  </td>
  <td width=30% valign=center align=left>
    <strong>Email</strong>
  </td>
</tr>
<%
	Iterator j = ContactList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
	       	while (j.hasNext()) {
		
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
		
		Contact thisContact = (Contact)j.next();
%>      
		<tr class="containerBody">
      <dhv:permission name="accounts-accounts-contacts-edit,accounts-accounts-contacts-delete">
      <td width=8 valign=center nowrap class="row<%= rowid %>">
        <dhv:permission name="accounts-accounts-contacts-edit"><a href="Contacts.do?command=Modify&orgId=<%= OrgDetails.getOrgId()%>&id=<%=thisContact.getId()%>&return=list">Edit</a></dhv:permission><dhv:permission name="accounts-accounts-contacts-edit,accounts-accounts-contacts-delete" all="true">|</dhv:permission><dhv:permission name="accounts-accounts-contacts-delete"><a href="javascript:popURLReturn('Contacts.do?command=ConfirmDelete&orgId=<%=OrgDetails.getId()%>&id=<%=thisContact.getId()%>','Contacts.do?command=View', 'Delete_contact','330','200','yes','no');">Del</a></dhv:permission>
      </td>
      </dhv:permission>
      <td width=30% valign=center class="row<%= rowid %>">
        <a href="Contacts.do?command=Details&id=<%=thisContact.getId()%>"><%= toHtml(thisContact.getNameLastFirst()) %></a>
      </td>
      <td width=20% valign=center class="row<%= rowid %>">
        <%= toHtml(thisContact.getTitle()) %>
      </td>
      <td width=20% valign=center class="row<%= rowid %>" nowrap>
        <%= toHtml(thisContact.getPhoneNumber("Business")) %>
      </td>
      <td width=30% valign=center class="row<%= rowid %>">
        <a href="mailto:<%= toHtml(thisContact.getEmailAddress("Business")) %>"><%= toHtml(thisContact.getEmailAddress("Business")) %></a>
      </td>
		</tr>
<%}%>
<%} else {%>
		<tr class="containerBody">
      <td colspan=5 valign=center>
        No contacts found.
      </td>
    </tr>
<%}%>
	</table>
	<br>
  <dhv:pagedListControl object="ContactListInfo"/>
</td>
</tr>
</table>


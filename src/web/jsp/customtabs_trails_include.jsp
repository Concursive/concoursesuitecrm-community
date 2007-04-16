<%-- 
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: admin_fields_folder_list.jsp 11310 2005-04-13 20:05:00Z mrajkowski $
  - Description: 
  --%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="containerName" class="java.lang.String" scope="request" />

<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<% if ("accounts".equals(containerName)){ %>
		<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
		<% if (request.getParameter("return") == null) { %>
		<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
		<%} else if (request.getParameter("return").equals("dashboard")) {%>
		<a href="Accounts.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
		<%}%>
<%} else
	 if ("contacts".equals(containerName)){
	 		Contact contact = (Contact)((ContainerMenuClass)dhvcontainers.get(0)).getItem(); %>
				<a href="ExternalContacts.do"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> > 
				<a href="ExternalContacts.do?command=SearchContacts"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
				<a href="ExternalContacts.do?command=ContactDetails&id=<%=contact.getId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<%
						} else
						if ("accountscontacts".equals(containerName)) {
					Organization org = (Organization)((ContainerMenuClass)dhvcontainers.get(1)).getItem();
					Contact contact = (Contact)((ContainerMenuClass)dhvcontainers.get(0)).getItem();
				%>
			<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
			<%
			if (request.getParameter("return") == null) {
			%>
			<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
			<%
			} else if (request.getParameter("return").equals("dashboard")) {
			%>
			<a href="Accounts.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
			<%
			}
			%>
			<a href="Accounts.do?command=Details&orgId=<%=org.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
			<a href="Contacts.do?command=View&orgId=<%=org.getOrgId()%>"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> >
			<a href="Contacts.do?command=Details&id=<%=contact.getId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
		<%
					 } else
					 if ("accountcontactopportunities".equals(containerName)) {
					 	Organization org = (Organization)((ContainerMenuClass)dhvcontainers.get(2)).getItem();
						Contact contact = (Contact)((ContainerMenuClass)dhvcontainers.get(1)).getItem();
			%>
				<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
				<% if (request.getParameter("return") == null) { %>
				<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
				<%} else if (request.getParameter("return").equals("dashboard")) {%>
				<a href="Accounts.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
				<%}%>
				<a href="Accounts.do?command=Details&orgId=<%=org.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
				<a href="Contacts.do?command=View&orgId=<%=org.getOrgId()%>"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> >
				<a href="Contacts.do?command=Details&id=<%=contact.getId()%>&orgId=<%=org.getOrgId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
				<a href="AccountContactsOpps.do?command=ViewOpps&contactId=<%= contact.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> >
		 <%
		 }%>
<dhv:label name=""><%=Page.getName()%></dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="ContactList" class="com.darkhorseventures.cfsbase.ContactList" scope="request"/>
<jsp:useBean id="ContactTypeList" class="com.darkhorseventures.cfsbase.ContactTypeList" scope="request"/>
<jsp:useBean id="ExternalContactsInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>

<form name="listView" method="post" action="/ExternalContacts.do?command=ListContacts">
<dhv:permission name="contacts-external_contacts-add">
<a href="/ExternalContacts.do?command=InsertContactForm">Add a Contact</a>
</dhv:permission>

<dhv:permission name="contacts-external_contacts-add" none="true">
<br>
</dhv:permission>

<center><%= ExternalContactsInfo.getAlphabeticalPageLinks() %></center>

<table width="100%" border="0">
  <tr>
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= ExternalContactsInfo.getOptionValue("my") %>>My Contacts </option>
        <option <%= ExternalContactsInfo.getOptionValue("all") %>>All Contacts</option>
      </select>
			<% ContactTypeList.setJsEvent("onChange=\"javascript:document.forms[0].submit();\""); %>
			<%= ContactTypeList.getHtmlSelect("listFilter1", ExternalContactsInfo.getFilterKey("listFilter1")) %>
      <%= showAttribute(request, "actionError") %>
    </td>
  </tr>
</table>

<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="contacts-external_contacts-edit,contacts-external_contacts-delete">
    <td valign=center align=left class="title">
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td>
      <strong><a href="/ExternalContacts.do?command=ListContacts&column=c.namelast">Name</a></strong>
      <%= ExternalContactsInfo.getSortIcon("c.namelast") %>
    </td>
    <td>
      <strong><a href="/ExternalContacts.do?command=ListContacts&column=c.company">Company</a></strong>
      <%= ExternalContactsInfo.getSortIcon("c.company") %>
    </td>
    <td>
      <strong>Phone: Business</strong>
    </td>
    <td>
      <strong>Phone: Mobile</strong>
    </td>
  </tr>
<%    
	Iterator i = ContactList.iterator();
	
	if (i.hasNext()) {
	int rowid = 0;
	
		while (i.hasNext()) {
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
			
		Contact thisContact = (Contact)i.next();
%>      
      <tr>
        <dhv:permission name="contacts-external_contacts-edit,contacts-external_contacts-delete">
        <td width=8 valign=center nowrap class="row<%= rowid %>">
          <dhv:permission name="contacts-external_contacts-edit"><a href="/ExternalContacts.do?command=ContactDetails&id=<%= thisContact.getId()%>&action=modify&return=list">Edit</a></dhv:permission><dhv:permission name="contacts-external_contacts-edit,contacts-external_contacts-delete" all="true">|</dhv:permission><dhv:permission name="contacts-external_contacts-delete"><a href="javascript:confirmDelete('/ExternalContacts.do?command=DeleteContact&id=<%= thisContact.getId() %>');">Del</a></dhv:permission>
        </td>
	</dhv:permission>
        <td class="row<%= rowid %>" nowrap>
          <a href="/ExternalContacts.do?command=ContactDetails&id=<%= thisContact.getId() %>"><%= toHtml(thisContact.getNameLastFirst()) %></a>
          <%= thisContact.getEmailAddressTag("Business", "<img border=0 src=\"images/email.gif\" alt=\"Send email\" align=\"absmiddle\">", "") %>
          <dhv:permission name="accounts-view,accounts-accounts-view"><%= ((thisContact.getOrgId() > 0)?"<a href=\"/Accounts.do?command=Details&orgId=" + thisContact.getOrgId() + "\">[Account]</a>":"")%></dhv:permission>
        </td>
        <td class="row<%= rowid %>">
          <%= toHtml(thisContact.getCompany()) %>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= toHtml(thisContact.getPhoneNumber("Business")) %>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= toHtml(thisContact.getPhoneNumber("Mobile")) %>
        </td>
      </tr>
<%
    }
  } else {%>  
  <tr>
    <td class="row2" valign="center" colspan="5">
      No contacts found.
    </td>
  </tr>
<%}%>
</table>
<br>
[<%= ExternalContactsInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %> <%= ExternalContactsInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>] <%= ExternalContactsInfo.getNumericalPageLinks() %>
</form>

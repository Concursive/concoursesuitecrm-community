<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_contacts_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  
  function reopen() {
    scrollReload('Accounts.do?command=Search');
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<dhv:label name="accounts.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="accounts-accounts-add"><a href="Accounts.do?command=Add">Add an Account</a></dhv:permission>
<dhv:permission name="accounts-accounts-add" none="true"><br></dhv:permission>
<center><%= SearchOrgListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="SearchOrgListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
<tr>
  <th width="8">
      &nbsp;
  </th>
  <th width="30%" nowrap="true">
    <strong><a href="Accounts.do?command=Search&column=c.namelast">Name</a></strong>
    <%= SearchOrgListInfo.getSortIcon("c.namelast") %>
  </th>
  <th width="10%" >
    <strong><dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label></strong>
  </th>   
  <th width="20%" nowrap="true">
    <strong><a href="Accounts.do?command=Search&column=c.org_name"><dhv:label name="organization.name">Account Name</dhv:label></a></strong>
    <%= SearchOrgListInfo.getSortIcon("c.org_name") %>
  </th>
  <dhv:evaluate if="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeSiteId") == String.valueOf(Constants.INVALID_SITE) %>">
  <th width="10%">
    <strong><dhv:label name="accounts.site">Site</dhv:label></strong>
  </th>
  </dhv:evaluate>
  <th width="30%">
        <strong><dhv:label name="account.phoneFax">Phone/Fax</dhv:label></strong>
  </th>
  <th>
        <strong><dhv:label name="accounts.accounts_add.Email">Email</dhv:label></strong>
  </th>
</tr>
<%
	Iterator j = ContactList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
      i++;
		  rowid = (rowid != 1?1:2);
      Contact thisContact = (Contact)j.next();
%>      
		<tr class="row<%= rowid %>">
      <td valign="center" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= i %>','menuContact', '<%= thisContact.getOrgId() %>', '<%= thisContact.getId() %>', '<%= thisContact.getPrimaryContact() %>','<%= (((thisContact.getTrashedDate() != null) || !thisContact.getEnabled()) || ((thisContact.getOrgTrashedDate() != null) || !thisContact.getOrgEnabled()))%>');"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
      <td valign="center">
        <a href="Contacts.do?command=Details&id=<%=thisContact.getId()%>"><%= toHtml(thisContact.getNameLastFirst()) %></a>
      </td>
      <td valign="center">
        <%= toHtml(thisContact.getTitle()) %>
      </td>
      <td valign="center" nowrap>
        <a href="Accounts.do?command=Details&orgId=<%= thisContact.getOrgId() %>"><%= toHtml(thisContact.getOrgName()) %></a>
      </td>
      <dhv:evaluate if="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeSiteId") == String.valueOf(Constants.INVALID_SITE) %>">
        <td valign="top"><%= SiteIdList.getSelectedValue(thisContact.getSiteId()) %></td>
      </dhv:evaluate>
      <td valign="center" nowrap>
        <% if (thisContact.getPhoneNumberList().size() > 1) { %>
            <%= thisContact.getPhoneNumberList().getHtmlSelect("contactphone", -1) %>
        <% } else if (thisContact.getPhoneNumberList().size() == 1) { 
             PhoneNumber thisNumber = (PhoneNumber) thisContact.getPhoneNumberList().get(0);
         %>
             <%= String.valueOf(thisNumber.getTypeName().charAt(0)) + ":" + toHtml(thisNumber.getNumber()) %>
        <%}%>
        &nbsp;
      </td>
      <td valign="center" nowrap>
        <% if (thisContact.getEmailAddressList().size() > 1) { %>
            <%= thisContact.getEmailAddressList().getHtmlSelect("contactemail", -1) %>
        <% } else if (thisContact.getEmailAddressList().size() == 1) { 
             EmailAddress thisAddress = (EmailAddress) thisContact.getEmailAddressList().get(0);
         %>
             <%= String.valueOf(thisAddress.getTypeName().charAt(0)) + ":" + toHtml(thisAddress.getEmail()) %>
        <%}%>
        &nbsp;
      </td>
		</tr>
<%}%>
<%} else {%>
		<tr class="containerBody">
      <td colspan="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeSiteId") == String.valueOf(Constants.INVALID_SITE)?"7":"6" %>">
        No contacts found.
      </td>
    </tr>
<%}%>
	</table>
<br>
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>

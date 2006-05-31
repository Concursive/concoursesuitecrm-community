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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_contacts_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/executeFunction.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/moveContact.js"></script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<script language="JavaScript" TYPE="text/javascript">
function reopen() {
  window.location.href='Contacts.do?command=View&orgId=<%= OrgDetails.getOrgId() %>';
}
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<dhv:label name="accounts.Contacts">Contacts</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="accounts" selected="contacts" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>" hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>">
  <dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
    <dhv:permission name="accounts-accounts-contacts-add"><a href="Contacts.do?command=Prepare&orgId=<%=request.getParameter("orgId")%>"><dhv:label name="accounts.accounts_contacts_list.AddAContact">Add a Contact</dhv:label></a></dhv:permission>
  </dhv:evaluate>
  <dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
  <center><dhv:pagedListAlphabeticalLinks object="ContactListInfo"/></center></dhv:include>
  <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ContactListInfo"/>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th width="8">
        &nbsp;
      </th>
      <th>
        <strong><a href="Contacts.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=c.namelast"><dhv:label name="contacts.name">Name</dhv:label></a></strong>
        <%= ContactListInfo.getSortIcon("c.namelast") %>
      </th>
      <th>
        <strong><dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label></strong>
      </th>
      <th>
        <strong><dhv:label name="accounts.accounts_add.Phone">Phone</dhv:label></strong>
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
         <a href="javascript:displayMenu('select<%= i %>', 'menuContact', '<%= OrgDetails.getOrgId() %>', '<%= thisContact.getId() %>', '<%= thisContact.getPrimaryContact() %>','<%= thisContact.isTrashed()%>');"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuContact');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
      <td valign="center" width="50%">
        <a href="Contacts.do?command=Details&id=<%=thisContact.getId()%>"><%= toHtml(thisContact.getNameLastFirst()) %></a>
      </td>
      <td valign="center" width="50%">
        <%= toHtml(thisContact.getTitle()) %>
      </td>
      <td valign="center" nowrap>
        <% if (thisContact.getPhoneNumberList().size() > 1) { %>
           <%-- TODO: use drop-down with Outbound dialer --%>
            <%= thisContact.getPhoneNumberList().getHtmlSelect("contactphone", -1) %>
        <% } else if (thisContact.getPhoneNumberList().size() == 1) { 
             PhoneNumber thisNumber = (PhoneNumber) thisContact.getPhoneNumberList().get(0);
         %>
            <%= String.valueOf(thisNumber.getTypeName().charAt(0)) + ":" + toHtml(thisContact.getPrimaryPhoneNumber()) %>
            <dhv:evaluate if="<%= "true".equals(applicationPrefs.get("ASTERISK.OUTBOUND.ENABLED")) %>">
              <dhv:evaluate if="<%= hasText(thisContact.getPrimaryPhoneNumber()) %>">
                <a href="javascript:popURL('OutboundDialer.do?command=Call&auto-populate=true&number=<%= StringUtils.jsStringEscape(thisContact.getPrimaryPhoneNumber()) %>','OUTBOUND_CALL','400','200','yes','yes');"><img src="images/icons/stock_call-16.gif" align="absMiddle" title="Call number" border="0"/></a>
              </dhv:evaluate>
            </dhv:evaluate>
        <%}%>
      </td>
      <td valign="center" nowrap>
        <% if (thisContact.getEmailAddressList().size() > 1) { %>
            <%= thisContact.getEmailAddressList().getHtmlSelect("contactemail", -1) %>
        <% } else if (thisContact.getEmailAddressList().size() == 1) {
             EmailAddress thisAddress = (EmailAddress) thisContact.getEmailAddressList().get(0);
         %>
          <a href="mailto:<%= toHtml(thisContact.getPrimaryEmailAddress()) %>"><%= toHtml(thisContact.getPrimaryEmailAddress()) %></a>
        <%}%>
       &nbsp;
      </td>
		</tr>
<%}%>
<%} else {%>
		<tr class="containerBody">
      <td colspan="5">
        <dhv:label name="accounts.accounts_contacts_detailsimport.NoContactsFound">No contacts found.</dhv:label>
      </td>
    </tr>
<%}%>
	</table>
	<br>
  <dhv:pagedListControl object="ContactListInfo"/>
</dhv:container>

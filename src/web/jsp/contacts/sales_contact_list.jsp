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
<%@ page import="java.io.*,java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.modules.contacts.base.ContactTypeList" scope="request"/>
<jsp:useBean id="SalesContactListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="SalesContactListInfo"/></center></dhv:include>
<table width="100%" border="0">
  <tr>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="SalesContactListInfo"/>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th nowrap>
      <strong><a href="Sales.do?command=ContactList&column=c.namelast"><dhv:label name="contacts.name">Name</dhv:label></a></strong>
      <%= SalesContactListInfo.getSortIcon("c.namelast") %>
    </th>
    <th nowrap>
      <strong><a href="Sales.do?command=ContactList&column=c.org_name"><dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label></a></strong>
      <%= SalesContactListInfo.getSortIcon("c.org_name") %>
    </th>
    <th>
      <strong><dhv:label name="account.emails">Email(s)</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="account.phones">Phone(s)</dhv:label></strong>
    </th>
    <dhv:evaluate if="<%= !"".equals(SalesContactListInfo.getListView()) %>">
      <th>
        <strong><dhv:label name="accounts.accounts_contacts_detailsimport.Owner">Owner</dhv:label></strong>
      </th>
    </dhv:evaluate>
  </tr>
<%    
	Iterator i = ContactList.iterator();
	if (i.hasNext()) {
	int rowid = 0;
  int count  =0;
		while (i.hasNext()) {
      count++;
      rowid = (rowid != 1 ? 1 : 2);
      Contact thisContact = (Contact)i.next();
%>    
      <tr>
        <td class="row<%= rowid %>" <%= "".equals(toString(thisContact.getNameFull())) ? "width=\"10\"" : ""  %> nowrap>
          <% if(!"".equals(toString(thisContact.getNameFull()))){ %>
          <a href="javascript:window.location.href='ExternalContacts.do?command=ContactDetails&id=<%= thisContact.getId() %>&popup=true&popupType=inline';"><%= toHtml(thisContact.getNameFull()) %></a>
          <%= thisContact.getEmailAddressTag("", "<img border=0 src=\"images/icons/stock_mail-16.gif\" alt=\"Send email\" align=\"absmiddle\">", "") %>
          <dhv:permission name="accounts-view,accounts-accounts-view">
            <dhv:evaluate if="<%= thisContact.getOrgId() > 0 %>" >[<dhv:label name="accounts.account">Account</dhv:label>]</dhv:evaluate>
          </dhv:permission>
          <% }else{ %>
            &nbsp;
          <%}%>
        </td>
        <td class="row<%= rowid %>">
          <% if(!"".equals(toString(thisContact.getNameFull()))){ %>
            <%= toHtml(thisContact.getOrgName()) %>
          <%}else{%>
            <%= toHtml(thisContact.getOrgName()) %>
            <%= thisContact.getEmailAddressTag("", "<img border=0 src=\"images/icons/stock_mail-16.gif\" alt=\"Send email\" align=\"absmiddle\">", "") %>
          <%}%>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%
            Iterator emailItr = thisContact.getEmailAddressList().iterator();
            while (emailItr.hasNext()) {
              EmailAddress email = (EmailAddress) emailItr.next(); %>
              <%= email.getEmail() %>(<%= email.getTypeName() %>)
              <%= (emailItr.hasNext()?"<br />":"") %>
           <%}%>&nbsp;
          </td>
        <td class="row<%= rowid %>" nowrap>
          <%
            Iterator phoneItr = thisContact.getPhoneNumberList().iterator();
            while (phoneItr.hasNext()) {
              PhoneNumber phoneNumber = (PhoneNumber)phoneItr.next(); %>
              <%= phoneNumber.getPhoneNumber()%>(<%=phoneNumber.getTypeName()%>)
              <%=(phoneItr.hasNext()?"<br />":"")%>
           <%}%>&nbsp;
          </td>
        <dhv:evaluate if="<%= !"".equals(SalesContactListInfo.getListView()) %>">
          <td class="row<%= rowid %>" nowrap>
            <dhv:username id="<%= thisContact.getOwner() %>"/>
          </td>
        </dhv:evaluate>
      </tr>
<%
    }
  } else {%>  
  <tr>
    <td class="containerBody" colspan="5">
      <dhv:label name="contact.noContactsFound.text">No contacts found with the specified search parameters.</dhv:label>
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="SalesContactListInfo" tdClass="row1"/>

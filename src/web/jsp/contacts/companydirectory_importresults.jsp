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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.PhoneNumber" %>
<jsp:useBean id="ImportResults" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ImportDetails" class="org.aspcfs.modules.base.Import" scope="request"/>
<jsp:useBean id="ExternalContactsImportResultsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="companydirectory_importresults_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
  <td>
    <a href="ExternalContacts.do"><dhv:label name="Contacts" mainMenuItem="true">Contacts</dhv:label></a> >
    <a href="ExternalContactsImports.do?command=View"><dhv:label name="accounts.ViewImports">View Imports</dhv:label></a> >
    <a href="ExternalContactsImports.do?command=Details&importId=<%= ImportDetails.getId() %>"><dhv:label name="accounts.ImportDetails">Import Details</dhv:label></a> >
    <dhv:label name="global.button.ViewResults">View Results</dhv:label>
  </td>
</tr>
</table>
<%-- End Trails --%>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="ExternalContactsImportResultsInfo"/></center></dhv:include>
<table width="100%" border="0">
  <tr>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ExternalContactsImportResultsInfo"/>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="8">&nbsp;</th>
    <th nowrap>
      <strong><a href="ExternalContactsImports.do?command=ViewResults&importId=<%= ImportDetails.getId() %>&column=c.namelast"><dhv:label name="contacts.name">Name</dhv:label></a></strong>
      <%= ExternalContactsImportResultsInfo.getSortIcon("c.namelast") %>
    </th>
    <th nowrap>
      <strong><a href="ExternalContactsImports.do?command=ViewResults&importId=<%= ImportDetails.getId() %>&column=c.org_name"><dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label></a></strong>
      <%= ExternalContactsImportResultsInfo.getSortIcon("c.org_name") %>
    </th>
    <th>
      <strong><dhv:label name="account.phones.colon">Phone(s):</dhv:label></strong>
    </th>
    <dhv:evaluate if="<%= !"my".equals(ExternalContactsImportResultsInfo.getListView()) && !"".equals(ExternalContactsImportResultsInfo.getListView()) %>">
      <th>
        <strong><dhv:label name="accounts.accounts_contacts_detailsimport.Owner">Owner</dhv:label></strong>
      </th>
    </dhv:evaluate>
  </tr>
<%    
	Iterator i = ImportResults.iterator();
	if (i.hasNext()) {
	int rowid = 0;
  int count  =0;
		while (i.hasNext()) {
      count++;
      rowid = (rowid != 1 ? 1 : 2);
      Contact thisContact = (Contact)i.next();
%>    
      <tr>
        <td width="8" class="row<%= rowid %>" nowrap>
         <%-- check if user has edit or delete based on the type of contact --%>
        <%
          int hasDeletePermission = 0;
          if(!thisContact.isApproved()){
            if(thisContact.getOrgId() < 0){ %>
            <dhv:permission name="contacts-external_contacts-delete">
              <% hasDeletePermission = 1; %>
            </dhv:permission>
          <% }else{ %>
              <dhv:permission name="contacts-external_contacts-delete,accounts-accounts-contacts-delete" all="true">
                <% hasDeletePermission = 1; %>
              </dhv:permission>
          <% } %>
        <% } %>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= count %>','menuContact','<%= thisContact.getId() %>','<%= hasDeletePermission %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuContact');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
        </td>
        <td class="row<%= rowid %>" <%= "".equals(toString(thisContact.getNameLastFirst())) ? "width=\"10\"" : ""  %> nowrap>
          <% if(!"".equals(toString(thisContact.getNameLastFirst()))){ %>
          <a href="ExternalContactsImports.do?command=ContactDetails&contactId=<%= thisContact.getId() %>"><%= toHtml(thisContact.getNameLastFirst()) %></a>
          <%= thisContact.getEmailAddressTag("", "<img border=0 src=\"images/icons/stock_mail-16.gif\" alt=\"Send email\" align=\"absmiddle\">", "") %>
          <dhv:permission name="accounts-view,accounts-accounts-view"><%= ((thisContact.getOrgId() > 0 )?"<a href=\"Accounts.do?command=Details&orgId=" + thisContact.getOrgId() + "\">[Account]</a>":"")%></dhv:permission>
          <% }else{ %>
            &nbsp;
          <%}%>
        </td>
        <td class="row<%= rowid %>">
          <% if(!"".equals(toString(thisContact.getNameLastFirst()))){ %>
            <%= toHtml(thisContact.getOrgName()) %>
          <%}else{%>
            <a href="ExternalContacts.do?command=ContactDetails&id=<%= thisContact.getId() %>"><%= toHtml(thisContact.getOrgName()) %></a>
            <%= thisContact.getEmailAddressTag("", "<img border=0 src=\"images/icons/stock_mail-16.gif\" alt=\"Send email\" align=\"absmiddle\">", "") %>
          <%}%>
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
        <dhv:evaluate if="<%= !"my".equals(ExternalContactsImportResultsInfo.getListView()) && !"".equals(ExternalContactsImportResultsInfo.getListView()) %>">
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
      <dhv:label name="accounts.accounts_contacts_detailsimport.NoContactsFound">No contacts found.</dhv:label>
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="ExternalContactsImportResultsInfo" tdClass="row1"/>

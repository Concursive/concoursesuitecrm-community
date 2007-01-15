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
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.modules.contacts.base.ContactTypeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="SearchContactsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="companydirectory_listcontacts_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  function reopen() {
    scrollReload('ExternalContacts.do?command=SearchContacts');
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do"><dhv:label name="Contacts" mainMenuItem="true">Contacts</dhv:label></a> >
<dhv:label name="accounts.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="contacts-external_contacts-add">
  <a href="ExternalContacts.do?command=Prepare"><dhv:label name="accounts.accounts_contacts_list.AddAContact">Add a Contact</dhv:label></a>
</dhv:permission>
<dhv:permission name="contacts-external_contacts-add" none="true">
  <br>
</dhv:permission>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="SearchContactsInfo"/></center></dhv:include>
<table width="100%" border="0">
  <tr>
    <td>
      <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchContactsInfo"/>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="8">&nbsp;</th>
    <th nowrap>
      <strong><a href="ExternalContacts.do?command=SearchContacts&column=c.namelast"><dhv:label name="contacts.name">Name</dhv:label></a></strong>
      <%= SearchContactsInfo.getSortIcon("c.namelast") %>
    </th>
    <th nowrap>
      <strong><a href="ExternalContacts.do?command=SearchContacts&column=c.org_name"><dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label></a></strong>
      <%= SearchContactsInfo.getSortIcon("c.org_name") %>
    </th>
<dhv:evaluate if='<%= SearchContactsInfo.getSearchOptionValue("searchcodeSiteId").equals(String.valueOf(Constants.INVALID_SITE)) %>'>
    <th nowrap>
      <strong><a href="ExternalContacts.do?command=SearchContacts&column=lsi.description"><dhv:label name="accounts.site">Site</dhv:label></a></strong>
      <%= SearchContactsInfo.getSortIcon("lsi.description") %>
    </th>
</dhv:evaluate>
    <th>
      <strong><dhv:label name="account.phones">Phone(s)</dhv:label></strong>
    </th>
    <dhv:evaluate if='<%= !"my".equals(SearchContactsInfo.getListView()) && !"".equals(SearchContactsInfo.getListView()) %>'>
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
      <tr class="row<%= rowid %>">
        <td width="8" nowrap>
         <%-- check if user has edit or delete based on the type of contact --%>
        <%
          int hasEditPermission = 0;
          int hasDeletePermission = 0;
          int hasClonePermission = 0;
          int hasAddressRequestPermission = 0;
          if(thisContact.getOrgId() < 0){ %>
          <dhv:permission name="contacts-external_contacts-edit">
            <% hasEditPermission = 1; %>
          </dhv:permission>
          <dhv:permission name="contacts-external_contacts-delete">
            <% hasDeletePermission = 1; %>
          </dhv:permission>
          <dhv:permission name="contacts-external_contacts-add">
            <% hasClonePermission = 1; %>
          </dhv:permission>
          <dhv:permission name="contacts-external-contact-updater-view">
            <% hasAddressRequestPermission = 1; %>
          </dhv:permission>
        <% }else{ %>
          <dhv:permission name="contacts-external_contacts-edit,accounts-accounts-contacts-edit"  all="true">
            <% hasEditPermission = 1; %>
          </dhv:permission>
          <dhv:permission name="contacts-external_contacts-delete,accounts-accounts-contacts-delete" all="true">
            <% hasDeletePermission = 1; %>
          </dhv:permission>
          <dhv:permission name="contacts-external_contacts-add,accounts-accounts-contacts-add" all="true">
            <% hasClonePermission = 1; %>
          </dhv:permission>
          <dhv:permission name="contacts-external-contact-updater-view,accounts-accounts-contact-updater-view" all="true">
            <% hasAddressRequestPermission = 1; %>
          </dhv:permission>
        <% } %>
        
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= count %>','menuContact','<%= thisContact.getId() %>','<%= hasEditPermission %>', '<%= hasDeletePermission %>', '<%= hasClonePermission %>', '<%= hasAddressRequestPermission %>' ,'<%= thisContact.getOrgId() %>','<%= thisContact.isTrashed() %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuContact');">
         <img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
        </td>
        <td <%= "".equals(toString(thisContact.getNameFull())) ? "width=\"10\"" : ""  %> nowrap>
          <% if(!"".equals(toString(thisContact.getNameFull()))){ %>
          <a href="ExternalContacts.do?command=ContactDetails&id=<%= thisContact.getId() %>"><%= toHtml(thisContact.getNameFull()) %></a>
          <%= thisContact.getEmailAddressTag("", "<img border=0 src=\"images/icons/stock_mail-16.gif\" alt=\"Send email\" align=\"absmiddle\">", "") %>
          <dhv:permission name="accounts-view,accounts-accounts-view"><%= ((thisContact.getOrgId() > 0 )?"<a href=\"Accounts.do?command=Details&orgId=" + thisContact.getOrgId() + "\">[Account]</a>":"")%></dhv:permission>
          <% }else{ %>
            &nbsp;
          <%}%>
        </td>
        <td>
          <% if(!"".equals(toString(thisContact.getNameFull()))){ %>
            <%= toHtml(thisContact.getOrgName()) %>
          <%}else{%>
            <a href="ExternalContacts.do?command=ContactDetails&id=<%= thisContact.getId() %>"><%= toHtml(thisContact.getOrgName()) %></a>
            <%= thisContact.getEmailAddressTag("", "<img border=0 src=\"images/icons/stock_mail-16.gif\" alt=\"Send email\" align=\"absmiddle\">", "") %>
          <%}%>
        </td>
<dhv:evaluate if='<%= SearchContactsInfo.getSearchOptionValue("searchcodeSiteId").equals(String.valueOf(Constants.INVALID_SITE)) %>'>
        <td valign="top"><%= SiteIdList.getSelectedValue(thisContact.getSiteId()) %></td>
</dhv:evaluate>
        <td nowrap>
          <%
            Iterator phoneItr = thisContact.getPhoneNumberList().iterator();
            while (phoneItr.hasNext()) {
              PhoneNumber phoneNumber = (PhoneNumber)phoneItr.next(); %>
              <%= phoneNumber.getPhoneNumber() %> (<%=phoneNumber.getTypeName()%>)
              <dhv:evaluate if='<%= "true".equals(applicationPrefs.get("ASTERISK.OUTBOUND.ENABLED")) %>'>
                <dhv:evaluate if="<%= hasText(phoneNumber.getPhoneNumber()) %>">
                  <a href="javascript:popURL('OutboundDialer.do?command=Call&auto-populate=true&number=<%= StringUtils.jsStringEscape(phoneNumber.getPhoneNumber()) %>','OUTBOUND_CALL','400','200','yes','yes');"><img src="images/icons/stock_call-16.gif" align="absMiddle" title="Call number" border="0"/></a>
                </dhv:evaluate>
              </dhv:evaluate>
              <%=(phoneItr.hasNext()?"<br />":"")%>
           <%}%>&nbsp;
          </td>
        <dhv:evaluate if='<%= !"my".equals(SearchContactsInfo.getListView()) && !"".equals(SearchContactsInfo.getListView()) %>'>
          <td nowrap>
            <dhv:username id="<%= thisContact.getOwner() %>"/>
          </td>
        </dhv:evaluate>
      </tr>
<%
    }
  } else {%>
  <tr>
    <td class="containerBody" colspan="<%= SearchContactsInfo.getSearchOptionValue("searchcodeSiteId").equals(String.valueOf(Constants.INVALID_SITE)) ? "6":"5" %>">
      <dhv:label name="contact.noContactsFound.text">No contacts found with the specified search parameters.</dhv:label><br />
      <a href="ExternalContacts.do?command=SearchContactsForm"><dhv:label name="accounts.accounts_list.ModifySearch">Modify Search</dhv:label></a>.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="SearchContactsInfo" tdClass="row1"/>
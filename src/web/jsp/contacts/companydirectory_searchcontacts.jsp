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
<%@ page import="org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.modules.contacts.base.ContactTypeList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SearchContactsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function clearForm() {
    document.forms['searchContact'].searchFirstName.value="";
    document.forms['searchContact'].searchMiddleName.value="";
    document.forms['searchContact'].searchLastName.value="";
    document.forms['searchContact'].searchCompany.value="";
    document.forms['searchContact'].searchTitle.value="";
    document.forms['searchContact'].listView.options.selectedIndex = 0;
    document.forms['searchContact'].listFilter1.options.selectedIndex = 0;
    document.forms['searchContact'].searchFirstName.focus();
    <dhv:evaluate if="<%=User.getSiteId() == -1 %>" >
      document.forms['searchContact'].searchcodeSiteId.options.selectedIndex = 0;
    </dhv:evaluate>
  }
</script>
<body onLoad="javascript:document.searchContact.searchFirstName.focus();">
<form name="searchContact" action="ExternalContacts.do?command=SearchContacts&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do"><dhv:label name="Contacts" mainMenuItem="true">Contacts</dhv:label></a> > 
<dhv:label name="contact.searchContacts">Search Contacts</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="contact.searchContacts">Search Contacts</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.FirstName">First Name</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="searchFirstName" value="<%= SearchContactsInfo.getSearchOptionValue("searchFirstName") %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.MiddleName">Middle Name</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="searchMiddleName" value="<%= SearchContactsInfo.getSearchOptionValue("searchMiddleName") %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.LastName">Last Name</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="searchLastName" value="<%= SearchContactsInfo.getSearchOptionValue("searchLastName") %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="searchCompany" value="<%= SearchContactsInfo.getSearchOptionValue("searchCompany") %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="searchTitle" value="<%= SearchContactsInfo.getSearchOptionValue("searchTitle") %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_add.ContactType">Contact Type</dhv:label>
    </td>
    <td>
      <%= ContactTypeList.getHtmlSelect(systemStatus, "listFilter1", SearchContactsInfo.getFilterKey("listFilter1")) %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="contact.source">Source</dhv:label>
    </td>
    <td>
      <select size="1" name="listView">
        <option <%= SearchContactsInfo.getOptionValue("my") %>><dhv:label name="contact.myContacts">My Contacts</dhv:label></option>
        <option <%= SearchContactsInfo.getOptionValue("all") %>><dhv:label name="actionList.allContacts">All Contacts</dhv:label></option>
        <option <%= SearchContactsInfo.getOptionValue("hierarchy") %>><dhv:label name="contact.controlledHierarchyContacts">Controlled-Hierarchy Contacts</dhv:label></option>
      </select>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.site">Site</dhv:label>
    </td>
    <td>
    <dhv:evaluate if="<%=User.getUserRecord().getSiteId() == -1 %>" >
      <%= SiteList.getHtmlSelect("searchcodeSiteId", ("".equals(SearchContactsInfo.getSearchOptionValue("searchcodeSiteId")) ? String.valueOf(Constants.INVALID_SITE) : SearchContactsInfo.getSearchOptionValue("searchcodeSiteId"))) %>
    </dhv:evaluate>
    <dhv:evaluate if="<%=User.getUserRecord().getSiteId() != -1 %>" >
        <input type="hidden" name="searchcodeSiteId" value="<%= User.getUserRecord().getSiteId() %>">
        <%= SiteList.getSelectedValue(User.getUserRecord().getSiteId()) %>
    </dhv:evaluate>
    <input type="hidden" name="searchcodeExclusiveToSite" value="true"/>
    </td>
  </tr>
  <%--
  <tr>
    <td class="formLabel">
      <dhv:label name="global.trashed">Trashed</dhv:label>
    </td>
    <td>
      <input type="checkbox" name="searchcodeIncludeOnlyTrashed" value="true" <%= "true".equals(SearchContactsInfo.getSearchOptionValue("searchcodeIncludeOnlyTrashed"))? "checked":""%> />
    </td>
	</tr>
  --%>
</table>
<br>
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
<input type="hidden" name="doSearch" value="true">
<input type="hidden" name="source" value="searchForm">
</form>
</body>

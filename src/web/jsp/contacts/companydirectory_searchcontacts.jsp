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
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.modules.contacts.base.ContactTypeList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SearchContactsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
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
  }
</script>
<body onLoad="javascript:document.forms[0].searchFirstName.focus();">
<form name="searchContact" action="ExternalContacts.do?command=SearchContacts&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do">Contacts</a> > 
Search Contacts
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Search Contacts</strong>
    </th>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      First Name
    </td>
    <td>
      <input type="text" size="35" name="searchFirstName" value="<%= SearchContactsInfo.getSearchOptionValue("searchFirstName") %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Middle Name
    </td>
    <td>
      <input type="text" size="35" name="searchMiddleName" value="<%= SearchContactsInfo.getSearchOptionValue("searchMiddleName") %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Last Name
    </td>
    <td>
      <input type="text" size="35" name="searchLastName" value="<%= SearchContactsInfo.getSearchOptionValue("searchLastName") %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Company
    </td>
    <td>
      <input type="text" size="35" name="searchCompany" value="<%= SearchContactsInfo.getSearchOptionValue("searchCompany") %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Title
    </td>
    <td>
      <input type="text" size="35" name="searchTitle" value="<%= SearchContactsInfo.getSearchOptionValue("searchTitle") %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Contact Type
    </td>
    <td>
      <%= ContactTypeList.getHtmlSelect("listFilter1", SearchContactsInfo.getFilterKey("listFilter1")) %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Source
    </td>
    <td>
      <select size="1" name="listView">
        <option <%= SearchContactsInfo.getOptionValue("my") %>>My Contacts</option>
        <option <%= SearchContactsInfo.getOptionValue("all") %>>All Contacts</option>
        <option <%= SearchContactsInfo.getOptionValue("hierarchy") %>>Controlled-Hierarchy Contacts</option>
        <option <%= SearchContactsInfo.getOptionValue("archived") %>>Archived Contacts</option>
      </select>
    </td>
  </tr>
</table>
<br>
<input type="submit" value="Search">
<input type="button" value="Clear" onClick="javascript:clearForm();">
<input type="hidden" name="doSearch" value="true">
<input type="hidden" name="source" value="searchForm">
</form>
</body>

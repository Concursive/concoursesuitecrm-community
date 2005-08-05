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
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<script language="JavaScript">
  function clearForm() {
    document.forms['searchAccount'].searchName.value="";
    document.forms['searchAccount'].searchAccountNumber.value="";
    document.forms['searchAccount'].searchPostalCode.value="";
    document.forms['searchAccount'].searchcodeAssetSerialNumber.value="";
    document.forms['searchAccount'].listView.options.selectedIndex = 0;
    document.forms['searchAccount'].listFilter1.options.selectedIndex = 0;
    document.forms['searchAccount'].listFilter2.options.selectedIndex = 0;
    document.forms['searchAccount'].searchName.focus();
  }
</script>
<body onLoad="javascript:document.searchAccount.searchName.focus();">
<form name="searchAccount" action="Accounts.do?command=Search" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<dhv:label name="accounts.search">Search Accounts</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.search">Search Accounts</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="organization.name">Account Name</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="searchName" value="<%= SearchOrgListInfo.getSearchOptionValue("searchName") %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="organization.accountNumber">Account Number</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="searchAccountNumber" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountNumber") %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.ZipPostalCode">Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" maxlength="12" name="searchPostalCode" value="<%= SearchOrgListInfo.getSearchOptionValue("searchPostalCode") %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.assetSerial.number.symbol">Asset Serial #</dhv:label>
    </td>
    <td>
      <input type="text" size="20" maxlength="30" name="searchcodeAssetSerialNumber" value="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeAssetSerialNumber") %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.type">Account Type</dhv:label>
    </td>
    <td>
      <%= TypeSelect.getHtmlSelect("listFilter1", SearchOrgListInfo.getFilterKey("listFilter1")) %>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accountSource">Account Source</dhv:label>
    </td>
    <td align="left" valign="bottom">
      <select size="1" name="listView">
        <option <%= SearchOrgListInfo.getOptionValue("all") %>><dhv:label name="accounts.all.accounts">All Accounts</dhv:label></option>
        <option <%= SearchOrgListInfo.getOptionValue("my") %>><dhv:label name="accounts.my.accounts">My Accounts</dhv:label></option>
      </select>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accountStatus">Account Status</dhv:label>
    </td>
    <td align="left" valign="bottom">
      <select size="1" name="listFilter2">
        <option value="-1" <%=(SearchOrgListInfo.getFilterKey("listFilter2") == -1)?"selected":""%>><dhv:label name="accounts.any">Any</dhv:label></option>
        <option value="1" <%=(SearchOrgListInfo.getFilterKey("listFilter2") == 1)?"selected":""%>><dhv:label name="accounts.active.accounts">Active</dhv:label></option>
        <option value="0" <%=(SearchOrgListInfo.getFilterKey("listFilter2") == 0)?"selected":""%>><dhv:label name="accounts.disabled.accounts">Inactive</dhv:label></option>
      </select>
    </td>
  </tr>
  <%--
  <tr>
    <td class="formLabel">
      <dhv:label name="global.trashed">Trashed</dhv:label>
    </td>
    <td>
      <input type="checkbox" name="searchcodeIncludeOnlyTrashed" value="true" <%= "true".equals(SearchOrgListInfo.getSearchOptionValue("searchcodeIncludeOnlyTrashed"))? "checked":""%> />
    </td>
	</tr>
  --%>
</table>
&nbsp;<br>
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
<input type="hidden" name="source" value="searchForm">
</form>
</body>

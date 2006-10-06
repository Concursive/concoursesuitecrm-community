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
<%@ page import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*" %>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AccountStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="ContactStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function clearForm() {
    <dhv:include name="accounts-search-name" none="true">
      document.forms['searchAccount'].searchAccountName.value="";
    </dhv:include>
    <dhv:include name="accounts-search-number" none="true">
      document.forms['searchAccount'].searchAccountNumber.value="";
    </dhv:include>
      document.forms['searchAccount'].searchAccountPostalCode.value="";
      document.forms['searchAccount'].searchAccountCity.value="";
      continueUpdateState('2','true');
      document.forms['searchAccount'].searchcodeAccountState.options.selectedIndex = 0;
      document.forms['searchAccount'].searchcodeAccountOtherState.value = '';
      document.forms['searchAccount'].searchcodeAccountCountry.options.selectedIndex = 0;
      document.forms['searchAccount'].searchcodeAssetSerialNumber.value="";
    <dhv:include name="accounts-search-source" none="true">
      document.forms['searchAccount'].listView.options.selectedIndex = 0;
    </dhv:include>
    <dhv:include name="accounts-search-type" none="true">
      document.forms['searchAccount'].listFilter1.options.selectedIndex = 0;
    </dhv:include>
    document.forms['searchAccount'].listFilter2.options.selectedIndex = 0;
    document.forms['searchAccount'].searchContactPhoneNumber.value="";
    document.forms['searchAccount'].searchLastName.value="";
    document.forms['searchAccount'].searchFirstName.value="";
    document.forms['searchAccount'].searchContactCity.value="";
    continueUpdateState('1','true');
    document.forms['searchAccount'].searchcodeContactState.options.selectedIndex = 0;
    document.forms['searchAccount'].searchcodeContactOtherState.value = '';
    document.forms['searchAccount'].searchcodeContactCountry.options.selectedIndex = 0;
    <dhv:evaluate if="<%=User.getUserRecord().getSiteId() == -1 && SiteList.size() > 1 %>" >
      document.forms['searchAccount'].searchcodeOrgSiteId.options.selectedIndex = 0;
    </dhv:evaluate>
    <dhv:include name="accounts-search-name" none="true">
      document.forms['searchAccount'].searchAccountName.focus();
    </dhv:include>
    <dhv:evaluate if="<%= SegmentList.size() > 1 %>">
      document.forms['searchAccount'].viewOnlySegmentId.options.selectedIndex = 0;
    </dhv:evaluate>
    document.forms['searchAccount'].searchAccountSegment.value = "";
    document.forms['searchAccount'].searchContacts.checked = false;
  }
  function fillAccountSegmentCriteria(){
    var index = document.forms['searchAccount'].viewOnlySegmentId.selectedIndex;
    var text = document.forms['searchAccount'].viewOnlySegmentId.options[index].text;
    if (index == 0){
      text = "";
    }
    document.forms['searchAccount'].searchAccountSegment.value = text;
  }

  function updateContacts(countryObj, stateObj, selectedValue) {

    var country = document.forms['searchAccount'].elements[countryObj].value;
    var url = "ExternalContacts.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=searchAccount&stateObj=searchcodeContactState";
    window.frames['server_commands'].location.href=url;
  }

  function updateAccounts(countryObj, stateObj, selectedValue) {
    var country = document.forms['searchAccount'].elements[countryObj].value;
    var url = "Accounts.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=searchAccount&stateObj=searchcodeAccountState";
    window.frames['server_commands'].location.href=url;
  }

  function continueUpdateState(stateObj, showText) {
	switch(stateObj){
      case '2':
        if(showText == 'true'){
          hideSpan('state31');
          showSpan('state41');
          document.forms['searchAccount'].searchcodeAccountState.options.selectedIndex = 0;
        } else {
          hideSpan('state41');
          showSpan('state31');
          document.forms['searchAccount'].searchcodeAccountOtherState.value = '';
        }
        break;
	  case '1':
      default:
        if(showText == 'true'){
          hideSpan('state11');
          showSpan('state21');
          document.forms['searchAccount'].searchcodeContactState.options.selectedIndex = 0;
        } else {
          hideSpan('state21');
          showSpan('state11');
          document.forms['searchAccount'].searchcodeContactOtherState.value = '';
        }
        break;
    }
  }

</script>
<dhv:include name="accounts-search-name" none="true">
  <body onLoad="javascript:document.searchAccount.searchAccountName.focus();">
</dhv:include>
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
<table cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="50%" valign="top">

      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="accounts.accountInformationFilters">Account Information Filters</dhv:label></strong>
          </th>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="organization.name">Account Name</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchAccountName" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountName") %>">
          </td>
        </tr>
        <dhv:include name="accounts-search-number" none="true">
        <tr>
          <td class="formLabel">
            <dhv:label name="organization.accountNumber">Account Number</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchAccountNumber" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountNumber") %>">
          </td>
        </tr>
        </dhv:include>
        <tr>
          <td class="formLabel">
            <dhv:label name="accounts.type">Account Type</dhv:label>
          </td>
          <td>
            <%= TypeSelect.getHtmlSelect("listFilter1", SearchOrgListInfo.getFilterKey("listFilter1")) %>
          </td>
        </tr>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_search.accountSegment">Account Segment</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchAccountSegment" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountSegment") %>">
            <dhv:evaluate if="<%= SegmentList.size() > 1 %>">
              <% SegmentList.setJsEvent("onchange=\"javascript:fillAccountSegmentCriteria();\"");%>
              <%= SegmentList.getHtmlSelect("viewOnlySegmentId", -1) %>
            </dhv:evaluate>
          </td>
        </tr>
        <dhv:include name="accounts-search-source" none="true">
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
        </dhv:include>
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
        <tr>
          <td class="formLabel">
            <dhv:label name="accounts.accounts_add.ZipPostalCode">Postal Code</dhv:label>
          </td>
          <td>
            <input type="text" size="10" maxlength="12" name="searchAccountPostalCode" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountPostalCode") %>">
          </td>
        </tr>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_add.City">City</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchAccountCity" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountCity") %>">
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_add.StateProvince">State/Province</dhv:label>
          </td>
          <td>
            <span name="state31" ID="state31" style="<%= AccountStateSelect.hasCountry(SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry"))? "" : " display:none" %>">
              <%= AccountStateSelect.getHtmlSelect("searchcodeAccountState", SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry"),SearchOrgListInfo.getSearchOptionValue("searchcodeAccountState")) %>
            </span>
            <%-- If selected country is not US/Canada use textfield --%>
            <span name="state41" ID="state41" style="<%= !AccountStateSelect.hasCountry(SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry")) ? "" : " display:none" %>">
              <input type="text" size="23" name="searchcodeAccountOtherState"  value="<%= toHtmlValue(SearchOrgListInfo.getSearchOptionValue("searchcodeAccountOtherState")) %>">
            </span>
          </td>
        </tr>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_add.Country">Country</dhv:label>
          </td>
          <td>
            <% CountrySelect.setJsEvent("onChange=\"javascript:updateAccounts('searchcodeAccountCountry','2','"+ SearchOrgListInfo.getSearchOptionValue("searchcodeAccountOtherState") +"');\""); %>
            <%= CountrySelect.getHtml("searchcodeAccountCountry", SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry")) %>
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
      <dhv:evaluate if="<%= SiteList.size() > 1 %>">
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.site">Site</dhv:label>
          </td>
          <td>
           <dhv:evaluate if="<%=User.getUserRecord().getSiteId() == -1 %>" >
            <%= SiteList.getHtmlSelect("searchcodeOrgSiteId", ("".equals(SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId")) ? String.valueOf(Constants.INVALID_SITE) : SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId"))) %>
           </dhv:evaluate>
           <dhv:evaluate if="<%=User.getUserRecord().getSiteId() != -1 %>" >
              <input type="hidden" name="searchcodeOrgSiteId" value="<%= User.getUserRecord().getSiteId() %>">
              <%= SiteList.getSelectedValue(User.getUserRecord().getSiteId()) %>
           </dhv:evaluate>
          </td>
        </tr>
      </dhv:evaluate> 
      <dhv:evaluate if="<%= SiteList.size() <= 1 %>">
        <input type="hidden" name="searchcodeOrgSiteId" id="searchcodeOrgSiteId" value="-1" />
      </dhv:evaluate>
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
    </td>
<dhv:include name="accounts-contact-information-filters" none="true">
    <td width="50%" valign="top">
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="accounts.contactInformationFilters">Contact Information Filters</dhv:label></strong>
          </th>
        </tr>
        <dhv:include name="accounts-search-name" none="true">
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_add.FirstName">First Name</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchFirstName" value="<%= SearchOrgListInfo.getSearchOptionValue("searchFirstName") %>">
          </td>
        </tr>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_add.LastName">Last Name</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchLastName" value="<%= SearchOrgListInfo.getSearchOptionValue("searchLastName") %>">
          </td>
        </tr>
        </dhv:include>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_add.Phone">Phone</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchContactPhoneNumber" value="<%= SearchOrgListInfo.getSearchOptionValue("searchContactPhoneNumber") %>">
          </td>
        </tr>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_add.City">City</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchContactCity" value="<%= SearchOrgListInfo.getSearchOptionValue("searchContactCity") %>">
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_add.StateProvince">State/Province</dhv:label>
          </td>
          <td>
            <span name="state11" ID="state11" style="<%= ContactStateSelect.hasCountry(SearchOrgListInfo.getSearchOptionValue("searchcodeContactCountry"))? "" : " display:none" %>">
              <%= ContactStateSelect.getHtmlSelect("searchcodeContactState", SearchOrgListInfo.getSearchOptionValue("searchcodeContactCountry"),SearchOrgListInfo.getSearchOptionValue("searchcodeContactState")) %>
            </span>
            <%-- If selected country is not US/Canada use textfield --%>
            <span name="state21" ID="state21" style="<%= !ContactStateSelect.hasCountry(SearchOrgListInfo.getSearchOptionValue("searchcodeContactCountry")) ? "" : " display:none" %>">
              <input type="text" size="23" name="searchcodeContactOtherState"  value="<%= toHtmlValue(SearchOrgListInfo.getSearchOptionValue("searchcodeContactOtherState")) %>">
            </span>
          </td>
        </tr>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_add.Country">Country</dhv:label>
          </td>
          <td>
            <% CountrySelect.setJsEvent("onChange=\"javascript:updateContacts('searchcodeContactCountry','1','"+ SearchOrgListInfo.getSearchOptionValue("searchcodeContactOtherState") +"');\""); %>
            <%= CountrySelect.getHtml("searchcodeContactCountry", SearchOrgListInfo.getSearchOptionValue("searchcodeContactCountry")) %>
          </td>
        </tr>
      </table>
    </td>
</dhv:include>
  </tr>
</table>
<dhv:include name="accounts-search-contacts" none="true">
  <input type="checkbox" name="searchContacts" value="true" <%= "true".equals(SearchOrgListInfo.getCriteriaValue("searchContacts"))? "checked":""%> />
  <dhv:label name="accounts.search.includeContactsInSearchResults">Include contacts in search results</dhv:label><br />
  <br />
</dhv:include>
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
<input type="hidden" name="source" value="searchForm">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<script type="text/javascript">
  </script>
</form>
</body>


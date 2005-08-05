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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.HtmlSelect, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="SearchOppListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/div.js"></script>
<script language="JavaScript">
  function clearForm() {
    document.forms['searchLeads'].searchDescription.value="";
    document.forms['searchLeads'].searchcodeOrgId.value="";
    changeDivContent('changeaccount', label('label.all','All'));
    document.forms['searchLeads'].searchcodeContactId.value="";
    changeDivContent('changecontact', label('label.any','Any'));
    document.forms['searchLeads'].searchcodeStage.options.selectedIndex = 0;
    document.forms['searchLeads'].searchdateCloseDateStart.value="";
    document.forms['searchLeads'].searchdateCloseDateEnd.value="";
    document.forms['searchLeads'].listView.options.selectedIndex = 0;
    document.forms['searchLeads'].listFilter1.options.selectedIndex = 0;
    document.forms['searchLeads'].listFilter2.options.selectedIndex = 0;
    document.forms['searchLeads'].searchDescription.focus();
    updateOwnedBy();
  }
  
  function updateOwnedBy(){
    if(document.searchLeads.listView.value == "all" || document.searchLeads.listView.value == "closed"){
      showSpan('ownedby');
    }else{
      document.forms['searchLeads'].listFilter2.options.selectedIndex = 0;
      hideSpan('ownedby');
    }
  }
  
  function checkOwnedBy(){
    <%
      if("all".equals(SearchOppListInfo.getListView()) || "closed".equals(SearchOppListInfo.getListView())){
    %>
      showSpan('ownedby');
    <% }else{ %>
      hideSpan('ownedby');
    <% } %>
  }

 function resetFieldValue(fieldId){
  document.getElementById(fieldId).value = "";
 }

  
</script>
<body onLoad="javascript:document.searchLeads.searchDescription.focus();checkOwnedBy();">
<form name="searchLeads" action="Leads.do?command=Search" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Leads.do"><dhv:label name="pipeline.pipeline">Pipeline</dhv:label></a> > 
<dhv:label name="pipeline.searchOpportunities">Search Opportunities</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:formMessage />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="pipeline.searchPipeline">Search Pipeline</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="pipeline.opportunityDescription">Opportunity Description</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="searchDescription" value="<%= SearchOppListInfo.getSearchOptionValue("searchDescription") %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="pipeline.accounts.bracket">Account(s)</dhv:label>
    </td>
    <td>
      <table cellspacing="0" cellpadding="0" border="0" class="empty">
        <tr>
          <td>
            <div id="changeaccount">
              <%if("".equals(SearchOppListInfo.getSearchOptionValue("searchcodeOrgId")) || "-1".equals(SearchOppListInfo.getSearchOptionValue("searchcodeOrgId"))){ %>
                &nbsp;<dhv:label name="quotes.all">All</dhv:label>&nbsp;
              <% }else{ %>
                <%= toHtmlValue(OrgDetails.getName()) %>
              <% } %>
            </div>
          </td>
          <td>
            <input type="hidden" name="searchcodeOrgId" id="searchcodeOrgId" value="<%= SearchOppListInfo.getSearchOptionValue("searchcodeOrgId") %>">
            &nbsp;[<a href="javascript:popAccountsListSingle('searchcodeOrgId','changeaccount', 'filters=all|my|disabled');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
            &nbsp [<a href="javascript:changeDivContent('changeaccount',label('quotes.all','All'));javascript:resetFieldValue('searchcodeOrgId');"><dhv:label name="button.clear">Clear</dhv:label></a>] 
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label>
    </td>
    <td>
      <table class="empty">
        <tr>
          <td>
            <div id="changecontact">
            <%if("".equals(SearchOppListInfo.getSearchOptionValue("searchcodeContactId")) || "-1".equals(SearchOppListInfo.getSearchOptionValue("searchcodeContactId"))){ %>
              &nbsp;<dhv:label name="pipeline.any">Any</dhv:label>&nbsp;
            <% }else{ %>
              <%= toHtmlValue(ContactDetails.getValidName()) %>
            <% } %>
            </div>
          </td>
          <td>
            <input type="hidden" id="contactId" name="searchcodeContactId" value="<%= SearchOppListInfo.getSearchOptionValue("searchcodeContactId") %>">
            &nbsp;[<a href="javascript:popContactsListSingle('contactId','changecontact', 'reset=true');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
            &nbsp [<a href="javascript:changeDivContent('changecontact',label('pipeline.any','Any'));javascript:resetFieldValue('contactId');"><dhv:label name="button.clear">Clear</dhv:label></a>] 
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_oppcomponent_details.CurrentStage">Current Stage</dhv:label>
    </td>
    <td>
      <%= StageList.getHtmlSelect("searchcodeStage", SearchOppListInfo.getSearchOptionValue("searchcodeStage")) %>
    </td>
  </tr>
  <tr>
    <td valign="top" class="formLabel">
      <dhv:label name="pipeline.estimatedCloseDateBetween">Estimated Close Date between</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="searchLeads" field="searchdateCloseDateStart" timestamp="<%= SearchOppListInfo.getSearchOptionValue("searchdateCloseDateStart") %>" />
      &nbsp;<dhv:label name="admin.and.lowercase">and</dhv:label> <%=showAttribute(request,"searchdateCloseDateStartError")%><br>
      <zeroio:dateSelect form="searchLeads" field="searchdateCloseDateEnd" timestamp="<%= SearchOppListInfo.getSearchOptionValue("searchdateCloseDateEnd") %>" />
      &nbsp;<%=showAttribute(request,"searchdateCloseDateEndError")%>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="pipeline.opportunityType">Opportunity Type</dhv:label>
    </td>
    <td>
      <%= TypeSelect.getHtmlSelect("listFilter1", SearchOppListInfo.getFilterKey("listFilter1")) %>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="contact.source">Source</dhv:label>
    </td>
    <td align="left" valign="bottom">
      <select size="1" name="listView" onChange="javascript:updateOwnedBy();">
        <option <%= SearchOppListInfo.getOptionValue("my") %>><dhv:label name="accounts.accounts_contacts_oppcomponent_list.MyOpenOpportunities">My Open Opportunities</dhv:label></option>
        <option <%= SearchOppListInfo.getOptionValue("all") %>><dhv:label name="accounts.accounts_contacts_oppcomponent_list.AllOpenOpportunities">All Open Opportunities</dhv:label></option>
        <option <%= SearchOppListInfo.getOptionValue("closed") %>><dhv:label name="accounts.accounts_contacts_oppcomponent_list.AllClosedOpportunities">All Closed Opportunities</dhv:label></option>
      </select>
    </td>
  </tr>
  <tr id="ownedby" style="display:none">
    <td class="formLabel">
      <dhv:label name="reports.accounts.contacts.ownedBy">Owned By</dhv:label>
    </td>
    <td>
      <% 
         HtmlSelect userSelect = UserList.getHtmlSelectObj("listFilter2", SearchOppListInfo.getFilterKey("listFilter2"));
         userSelect.addItem(-1, "All Users", 0);
      %>
      <%= userSelect.getHtml("listFilter2", SearchOppListInfo.getFilterKey("listFilter2")) %>
    </td>
  </tr>
  <%--
  <tr>
    <td class="formLabel">
      <dhv:label name="global.trashed">Trashed</dhv:label>
    </td>
    <td>
      <input type="checkbox" name="searchcodeIncludeOnlyTrashed" value="true" <%= "true".equals(SearchOppListInfo.getSearchOptionValue("searchcodeIncludeOnlyTrashed"))? "checked":""%> />
    </td>
	</tr>
  --%>
</table>
&nbsp;<br>
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
<input type="hidden" name="source" value="searchForm">
</form>
</body>

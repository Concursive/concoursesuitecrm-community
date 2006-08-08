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
<jsp:useBean id="accessTypeList" class="org.aspcfs.modules.admin.base.AccessTypeList" scope="request"/>
<jsv:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
    <dhv:include name="opportunity.currentStage" none="true">
      document.forms['searchLeads'].searchcodeStage.options.selectedIndex = 0;
    </dhv:include>
    document.forms['searchLeads'].searchdateCloseDateStart.value="";
    document.forms['searchLeads'].searchdateCloseDateEnd.value="";
    <dhv:include name="opportunity.openOrClosed" none="true">
      document.forms['searchLeads'].listView.options.selectedIndex = 0;
    </dhv:include>
    <dhv:include name="opportunity.componentTypes" none="true">
      document.forms['searchLeads'].listFilter1.options.selectedIndex = 0;
    </dhv:include>
    document.forms['searchLeads'].listFilter2.options.selectedIndex = 0;
    <dhv:include name="opportunity.source" none="true">
      document.forms['searchLeads'].searchcodeAccessType.options.selectedIndex = 0;
    </dhv:include>
    document.forms['searchLeads'].searchDescription.focus();
    <dhv:evaluate if="<%=User.getSiteId() == -1 %>" >
      document.forms['searchLeads'].searchcodeSiteId.options.selectedIndex = 0;
    </dhv:evaluate>
    updateOwnedBy();
  }
  
  function resetSiteData(form) {
    document.forms['searchLeads'].searchcodeOrgId.value="";
    changeDivContent('changeaccount', label('label.all','All'));
    document.forms['searchLeads'].searchcodeContactId.value="";
    changeDivContent('changecontact', label('label.any','Any'));
  }
  
  function getSiteId() {
    var site = document.forms['searchLeads'].searchcodeSiteId;
    var siteId = '';
    if ('<%= User.getUserRecord().getSiteId() == -1 || SiteIdList.size() > 1 %>' == 'true') {
      siteId = site.options[site.options.selectedIndex].value;
    } else {
      siteId = site.value;
    }
    if (siteId == '<%= Constants.INVALID_SITE %>') {
      siteId = '&includeAllSites=true';
    } else {
      siteId = siteId + '&thisSiteIdOnly=true';
    }
    return siteId
  }
  
  function updateOwnedBy() {
    <dhv:include name="opportunity.source" none="true">
    if(document.searchLeads.searchcodeAccessType.options[document.searchLeads.searchcodeAccessType.options.selectedIndex].value != -1){
      showSpan('ownedby');
    }else{
      document.forms['searchLeads'].listFilter2.options.selectedIndex = 0;
      hideSpan('ownedby');
    }
    </dhv:include>
    <dhv:include name="opportunity.source">
      showSpan('ownedby');
    </dhv:include>
  }
  
  function checkOwnedBy(){
    <%  //systemStatus.getLabel("pipeline.myOpportunities",
      if(!"My Opportunities".equals(SearchOppListInfo.getSearchOptionValue("searchcodeAccessType"))){
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
            &nbsp;[<a href="javascript:popAccountsListSingle('searchcodeOrgId','changeaccount', 'siteId='+getSiteId()+'&filters=all|my|disabled');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
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
            &nbsp;[<a href="javascript:popContactsListSingle('contactId','changecontact', 'siteId='+getSiteId()+'&filters=accountcontacts&reset=true');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
            &nbsp [<a href="javascript:changeDivContent('changecontact',label('pipeline.any','Any'));javascript:resetFieldValue('contactId');"><dhv:label name="button.clear">Clear</dhv:label></a>] 
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <dhv:include name="opportunity.currentStage" none="true">
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_oppcomponent_details.CurrentStage">Current Stage</dhv:label>
    </td>
    <td>
      <%= StageList.getHtmlSelect("searchcodeStage", SearchOppListInfo.getSearchOptionValue("searchcodeStage")) %>
    </td>
  </tr>
  </dhv:include>
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
  <dhv:include name="opportunity.componentTypes" none="true">
  <tr>
    <td class="formLabel">
      <dhv:label name="pipeline.opportunityType">Opportunity Type</dhv:label>
    </td>
    <td>
      <%= TypeSelect.getHtmlSelect("listFilter1", SearchOppListInfo.getFilterKey("listFilter1")) %>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="opportunity.openOrClosed" none="true">
  <tr>
    <td class="formLabel"><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></td>
    <td align="left" valign="bottom">
      <select size="1" name="listView">
        <option <%= SearchOppListInfo.getOptionValue("open") %>><dhv:label name="quotes.open">Open</dhv:label></option>
        <option <%= SearchOppListInfo.getOptionValue("closed") %>><dhv:label name="quotes.closed">Closed</dhv:label></option>
        <option <%= SearchOppListInfo.getOptionValue("any") %>><dhv:label name="pipeline.any">Any</dhv:label></option>
      </select>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="opportunity.openOrClosed">
    <input type="hidden" name="listView" value="<%= SearchOppListInfo.getOptionValue("open") %>"/>
  </dhv:include>
  <dhv:include name="opportunity.source" none="true">
  <tr>
    <td class="formLabel">
      <dhv:label name="contact.source">Source</dhv:label>
    </td>
    <td>
        <select name="searchcodeAccessType" onChange="updateOwnedBy();">
          <option value="<%= accessTypeList.getCode(AccessType.PUBLIC) %>" <%= SearchOppListInfo.getSearchOptionValue("searchcodeAccessType").equals(""+accessTypeList.getCode(AccessType.PUBLIC)) ?"selected":"" %>><dhv:label name="pipeline.allOpportunities">All Opportunities</dhv:label></option>
          <option value="<%= accessTypeList.getCode(AccessType.CONTROLLED_HIERARCHY) %>" <%= SearchOppListInfo.getSearchOptionValue("searchcodeAccessType").equals(""+accessTypeList.getCode(AccessType.CONTROLLED_HIERARCHY)) ?"selected":"" %>><dhv:label name="pipeline.controlledHierarchyOpportunities">Controlled Hierarchy Opportunities</dhv:label></option>
          <option value="-1" <%= SearchOppListInfo.getSearchOptionValue("searchcodeAccessType").equals("-1") ?"selected":"" %>><dhv:label name="pipeline.myOpportunities">My Opportunities</dhv:label></option>
        </select>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="opportunity.source">
    <input type="hidden" name="searchcodeAccessType" value="<%= accessTypeList.getCode(AccessType.CONTROLLED_HIERARCHY) %>"/>
  </dhv:include>
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
  <dhv:include name="pipeline.search.sites" none="true">
  <dhv:evaluate if="<%= SiteIdList.size() > 2 %>">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.site">Site</dhv:label>
      </td>
      <td>
       <dhv:evaluate if="<%=User.getUserRecord().getSiteId() == -1 %>" >
        <%= SiteIdList.getHtmlSelect("searchcodeSiteId", ("".equals(SearchOppListInfo.getSearchOptionValue("searchcodeSiteId")) ? String.valueOf(Constants.INVALID_SITE) : SearchOppListInfo.getSearchOptionValue("searchcodeSiteId"))) %>
       </dhv:evaluate>
       <dhv:evaluate if="<%=User.getUserRecord().getSiteId() != -1 %>" >
          <input type="hidden" name="searchcodeSiteId" value="<%= User.getUserRecord().getSiteId() %>">
          <%= SiteIdList.getSelectedValue(User.getUserRecord().getSiteId()) %>
       </dhv:evaluate>
       <input type="hidden" name="searchcodeExclusiveToSite" value="true"/>
      </td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= SiteIdList.size() <= 2 %>">
    <input type="hidden" name="searchcodeSiteId" id="searchcodeSiteId" value="-1" />
  </dhv:evaluate>
  </dhv:include>
</table>
&nbsp;<br>
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
<input type="hidden" name="source" value="searchForm">
</form>
<script type="text/javascript">
  document.searchLeads.searchDescription.focus();
  checkOwnedBy();
</script>

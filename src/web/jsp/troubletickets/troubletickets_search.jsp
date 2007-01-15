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
<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TicketTypeSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TicListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="userGroup" class="org.aspcfs.modules.admin.base.UserGroup" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script type="text/javascript">
  function checkForm(form) {
    formTest = true;
    message = "";
    if (!checkNullString(form.searchcodeId.value) && !checkNaturalNumber(form.searchcodeId.value)) {
      message += label("check.number.invalid","- Please enter a valid Number\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.campaign.criteria","Criteria could not be processed, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
  
  function getSiteId() {
    var site = document.forms['searchTicket'].searchcodeSiteId;
    var siteId = '';
    if ('<%= User.getUserRecord().getSiteId() == -1 && SiteList.size() > 2 %>' == 'true') {
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
  
  function clearSiteSpecificInfo() {
    document.forms['searchTicket'].searchcodeOrgId.value="-1";
    changeDivContent('changeaccount',label('label.all','All'));
    document.forms['searchTicket'].searchcodeAssignedTo.value="-1";
    changeDivContent('changeowner',label('label.anyone','Anyone'));
    document.forms['searchTicket'].searchcodeUserGroupId.value="-1";
    changeDivContent('changegroup',label('label.any','Any'));
  }

  function clearForm() {
    document.forms['searchTicket'].searchcodeId.value="";
    document.forms['searchTicket'].searchDescription.value="";
    document.forms['searchTicket'].searchcodeOrgId.value="-1";
    changeDivContent('changeaccount',label('label.all','All'));
    document.forms['searchTicket'].searchcodeSeverity.options.selectedIndex = 0;
    document.forms['searchTicket'].searchcodePriority.options.selectedIndex = 0;
    document.forms['searchTicket'].listFilter1.options.selectedIndex = 0;
    document.forms['searchTicket'].searchcodeEscalationLevel.options.selectedIndex = 0;
    document.forms['searchTicket'].searchcodeAssignedTo.value="-1";
    changeDivContent('changeowner',label('label.anyone','Anyone'));
    document.forms['searchTicket'].searchcodeUserGroupId.value="-1";
    changeDivContent('changegroup',label('label.any','Any'));
    <dhv:evaluate if="<%=User.getSiteId() == -1 && SiteList.size() > 2 %>" >
    document.forms['searchTicket'].searchcodeSiteId.options.selectedIndex = 0;
    </dhv:evaluate>
  }
  
  function clearAccount(){
    document.forms['searchTicket'].searchcodeOrgId.value="-1";
    changeDivContent('changeaccount',label('label.all','All'));
  }
  
  function clearOwner(){
    document.forms['searchTicket'].searchcodeAssignedTo.value="-1";
    changeDivContent('changeowner',label('label.anyone','Anyone'));
  }
</script>
<body onLoad="javascript:document.searchTicket.searchcodeId.focus();">
<form name="searchTicket" action="TroubleTickets.do?command=SearchTickets" method="post" onSubmit="javascript:return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTickets.do"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> > 
<dhv:label name="tickets.searchForm">Search Form</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="tickets.search">Search Tickets</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="tickets.number">Ticket Number</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="searchcodeId" value="<%= TicListInfo.getSearchOptionValue("searchcodeId") %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="ticket.issue">Issue</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="searchDescription" value="<%= TicListInfo.getSearchOptionValue("searchDescription") %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accounts">Account(s)</dhv:label>
    </td>
    <td>
      <table cellspacing="0" cellpadding="0" border="0" class="empty">
        <tr>
          <td>
            <div id="changeaccount">
            <%if("".equals(TicListInfo.getSearchOptionValue("searchcodeOrgId")) || "-1".equals(TicListInfo.getSearchOptionValue("searchcodeOrgId"))){ %>
                <dhv:label name="quotes.all">All</dhv:label>
              <% }else{ %>
                <%= toHtmlValue(OrgDetails.getName()) %>
              <% } %>
            </div>
          </td>
          <td>
            <input type="hidden" name="searchcodeOrgId" id="searchcodeOrgId" value="<%= TicListInfo.getSearchOptionValue("searchcodeOrgId") %>">
            &nbsp;[<a href="javascript:popAccountsListSingle('searchcodeOrgId','changeaccount', 'showMyCompany=true&siteId='+getSiteId()+'&filters=all|my|disabled');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
            &nbsp;[<a href="javascript:clearAccount();"><dhv:label name="button.clear">Clear</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
<dhv:include name="ticket.severity" none="true">
  <tr>
    <td class="formLabel">
      <dhv:label name="project.severity">Severity</dhv:label>
    </td>
    <td>
      <%= SeverityList.getHtmlSelect("searchcodeSeverity", TicListInfo.getSearchOptionValue("searchcodeSeverity")) %>
    </td>
	</tr>
</dhv:include>
<dhv:include name="ticket.priority" none="true">
	<tr>
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label>
    </td>
    <td>
      <%= PriorityList.getHtmlSelect("searchcodePriority", TicListInfo.getSearchOptionValue("searchcodePriority")) %>
    </td>
	</tr>
</dhv:include>
	<tr>
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Status">Status</dhv:label>
    </td>
    <td>
      <%= TicketTypeSelect.getHtml("listFilter1", TicListInfo.getFilterKey("listFilter1")) %>
      <input type="hidden" name="search" value="1">
    </td>
	</tr>
	<tr>
    <td class="formLabel">
      <dhv:label name="tickets.escalationLevel">Escalation Level</dhv:label>
    </td>
    <td>
      <%= EscalationList.getHtmlSelect("searchcodeEscalationLevel", TicListInfo.getSearchOptionValue("searchcodeEscalationLevel")) %>
      <input type="hidden" name="search" value="1">
    </td>
	</tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="project.resourceAssigned">Resource Assigned</dhv:label>
    </td>
    <td>
      <table class="empty">
        <tr>
          <td>
            <div id="changeowner">
            <%if("".equals(TicListInfo.getSearchOptionValue("searchcodeAssignedTo")) || "-1".equals(TicListInfo.getSearchOptionValue("searchcodeAssignedTo"))){ %>
                &nbsp;<dhv:label name="tickets.anyone">Anyone</dhv:label>&nbsp;
              <% }else{ %>
                <dhv:username id='<%= TicListInfo.getSearchOptionValue("searchcodeAssignedTo") %>'/>
              <% } %>
            </div>
          </td>
          <td>
            <input type="hidden" name="searchcodeAssignedTo" id="ownerid" value="<%= TicListInfo.getSearchOptionValue("searchcodeAssignedTo") %>">
            &nbsp;[<a href="javascript:popContactsListSingle('ownerid','changeowner', 'listView=employees&usersOnly=true&siteId='+getSiteId()+'&reset=true');"><dhv:label name="accounts.accounts_contacts_validateimport.ChangeOwner">Change Owner</dhv:label></a>]
            &nbsp;[<a href="javascript:clearOwner();"><dhv:label name="button.clear">Clear</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
	</tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="usergroup.assignedGroup">Assigned Group</dhv:label>
    </td>
    <td>
      <table class="empty">
        <tr>
          <td>
            <div id="changegroup">
            <%if("".equals(TicListInfo.getSearchOptionValue("searchcodeUserGroupId")) || "-1".equals(TicListInfo.getSearchOptionValue("searchcodeUserGroupId"))){ %>
                &nbsp;<dhv:label name="pipeline.any">Any</dhv:label>
              <% }else{ %>
                <%= toHtml(userGroup.getName()) %>
              <% } %>
            </div>
          </td>
          <td>
            <input type="hidden" name="searchcodeUserGroupId" id="searchcodeUserGroupId" value="<%= TicListInfo.getSearchOptionValue("searchcodeUserGroupId") %>"/>
            [<a href="javascript:popUserGroupsListSingle('searchcodeUserGroupId','changegroup', 'siteId='+getSiteId());"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>] &nbsp;
            [<a href="javascript:document.forms['searchTicket'].searchcodeUserGroupId.value='-1';javascript:changeDivContent('changegroup',label('label.any','Any'));"><dhv:label name="button.clear">Clear</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
	</tr>
  <dhv:evaluate if="<%= SiteList.size() > 2 %>">
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.site">Site</dhv:label>
    </td>
    <td>
     <dhv:evaluate if="<%=User.getSiteId() == -1 %>" >
      <% SiteList.setJsEvent("onChange=\"clearSiteSpecificInfo();\""); %>
      <%= SiteList.getHtmlSelect("searchcodeSiteId", ("".equals(TicListInfo.getSearchOptionValue("searchcodeSiteId")) ? String.valueOf(Constants.INVALID_SITE) : TicListInfo.getSearchOptionValue("searchcodeSiteId"))) %>
     </dhv:evaluate>
     <dhv:evaluate if="<%=User.getSiteId() > -1 %>" >
      <input type="hidden" name="searchcodeSiteId" value="<%= User.getSiteId() %>">
      <%= SiteList.getSelectedValue(User.getSiteId()) %>
     </dhv:evaluate>
    </td>
  </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteList.size() <= 2 %>">
    <input type="hidden" name="searchcodeSiteId" id="searchcodeSiteId" value="-1" />
  </dhv:evaluate>
  <%--
  <tr>
    <td class="formLabel">
      <dhv:label name="global.trashed">Trashed</dhv:label>
    </td>
    <td>
      <input type="checkbox" name="searchcodeIncludeOnlyTrashed" value="true" <%= "true".equals(TicListInfo.getSearchOptionValue("searchcodeIncludeOnlyTrashed"))? "checked":""%> />
    </td>
	</tr>
  --%>
</table>
<br>
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
</form>
</body>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TicketTypeSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TicListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<script language="JavaScript">
  function clearForm() {
    document.forms['searchTicket'].searchcodeId.value="";
    document.forms['searchTicket'].searchDescription.value="";
    document.forms['searchTicket'].searchcodeOrgId.value="-1";
    changeDivContent('changeaccount','All');
    document.forms['searchTicket'].searchcodeSeverity.options.selectedIndex = 0;
    document.forms['searchTicket'].searchcodePriority.options.selectedIndex = 0;
    document.forms['searchTicket'].listFilter1.options.selectedIndex = 0;
    document.forms['searchTicket'].searchcodeAssignedTo.value="-1";
    changeDivContent('changeowner',' Anyone ');
  }
  
  function clearAccount(){
    document.forms['searchTicket'].searchcodeOrgId.value="-1";
    changeDivContent('changeaccount','All');
  }
  
  function clearOwner(){
    document.forms['searchTicket'].searchcodeAssignedTo.value="-1";
    changeDivContent('changeowner',' Anyone ');
  }
</script>
<body onLoad="javascript:document.forms[0].searchcodeId.focus();">
<form name="searchTicket" action="TroubleTickets.do?command=SearchTickets" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTickets.do"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> > 
Search Form
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
                All
              <% }else{ %>
                <%= toHtmlValue(OrgDetails.getName()) %>
              <% } %>
            </div>
          </td>
          <td>
            <input type="hidden" name="searchcodeOrgId" id="searchcodeOrgId" value="<%= TicListInfo.getSearchOptionValue("searchcodeOrgId") %>">
            &nbsp;[<a href="javascript:popAccountsListSingle('searchcodeOrgId','changeaccount', 'filters=all|my|disabled');">Select</a>]
            &nbsp;[<a href="javascript:clearAccount();">Clear</a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
<dhv:include name="ticket.severity" none="true">
  <tr>
    <td class="formLabel">
      Severity
    </td>
    <td>
      <%= SeverityList.getHtmlSelect("searchcodeSeverity", TicListInfo.getSearchOptionValue("searchcodeSeverity")) %>
    </td>
	</tr>
</dhv:include>
<dhv:include name="ticket.priority" none="true">
	<tr>
    <td class="formLabel">
      Priority
    </td>
    <td>
      <%= PriorityList.getHtmlSelect("searchcodePriority", TicListInfo.getSearchOptionValue("searchcodePriority")) %>
    </td>
	</tr>
</dhv:include>
	<tr>
    <td class="formLabel">
      Status
    </td>
    <td>
      <%= TicketTypeSelect.getHtml("listFilter1", TicListInfo.getFilterKey("listFilter1")) %>
      <input type="hidden" name="search" value="1">
    </td>
	</tr>
  <tr>
    <td class="formLabel">
      Resource Assigned
    </td>
    <td>
      <table class="empty">
        <tr>
          <td>
            <div id="changeowner">
            <%if("".equals(TicListInfo.getSearchOptionValue("searchcodeAssignedTo")) || "-1".equals(TicListInfo.getSearchOptionValue("searchcodeAssignedTo"))){ %>
                &nbsp;Anyone&nbsp;
              <% }else{ %>
                <dhv:username id="<%= TicListInfo.getSearchOptionValue("searchcodeAssignedTo") %>"/>
              <% } %>
            </div>
          </td>
          <td>
            <input type="hidden" name="searchcodeAssignedTo" id="ownerid" value="<%= TicListInfo.getSearchOptionValue("searchcodeAssignedTo") %>">
            &nbsp;[<a href="javascript:popContactsListSingle('ownerid','changeowner', 'listView=employees&usersOnly=true&reset=true');">Change Owner</a>]
            &nbsp;[<a href="javascript:clearOwner();">Clear</a>]
          </td>
        </tr>
      </table>
    </td>
	</tr>
</table>
<br>
<input type="submit" value="Search">
<input type="button" value="Clear" onClick="javascript:clearForm();">
</form>
</body>

<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TicketTypeSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<body onLoad="javascript:document.forms[0].searchcodeId.focus();">
<form name="searchTicket" action="TroubleTickets.do?command=SearchTickets" method="post">
<a href="TroubleTickets.do">Tickets</a> > 
Search Form<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Search Tickets</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      Number
    </td>
    <td>
      <input type="text" size="10" name="searchcodeId" value="">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Description
    </td>
    <td>
      <input type="text" size="40" name="searchDescription" value="">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Organization
    </td>
    <td>
      <table cellspacing="0" cellpadding="0" border="0" class="empty">
        <tr>
          <td>
            <div id="changeaccount">None Selected</div>
          </td>
          <td>
            <input type="hidden" name="searchcodeOrgId" id="searchcodeOrgId">
            &nbsp;[<a href="javascript:popAccountsListSingle('searchcodeOrgId','changeaccount', 'filters=all|my|disabled');">Select</a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
<dhv:include name="tickets-severity" none="true">
  <tr>
    <td class="formLabel">
      Severity
    </td>
    <td>
      <%= SeverityList.getHtmlSelect("searchcodeSeverity", 0) %>
    </td>
	</tr>
</dhv:include>
<dhv:include name="tickets-priority" none="true">
	<tr>
    <td class="formLabel">
      Priority
    </td>
    <td>
      <%= PriorityList.getHtmlSelect("searchcodePriority",0) %>
    </td>
	</tr>
</dhv:include>
	<tr>
    <td class="formLabel">
      Status
    </td>
    <td>
      <%= TicketTypeSelect.getHtml() %>
      <input type="hidden" name="search" value="1">
    </td>
	</tr>
</table>
<br>
<input type="submit" value="Search">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Home'">
<input type="reset" value="Reset">
</form>
</body>

<jsp:useBean id="OrgList" class="com.darkhorseventures.cfsbase.OrganizationList" scope="request"/>
<jsp:useBean id="PriorityList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="TicketTypeSelect" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<%@ include file="initPage.jsp" %>
<body onLoad="javascript:document.forms[0].searchId.focus();">
<form name="searchTicket" action="/TroubleTickets.do?command=Home&auto-populate=true" method="post">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
      <strong>Search Tickets</strong>
    </td>     
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Ticket Number
    </td>
    <td>
      <input type=text size=10 name="searchId" value="">
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Organization
    </td>
	<td bgColor="white">
	<%= OrgList.getHtmlSelectDefaultNone("searchOrgId") %>
	</td>
  </tr>
  
  <tr>
	<td width=100 class="formLabel">
	Severity
	</td>
	<td bgColor="white">
	<%= SeverityList.getHtmlSelect("searchSeverity",0) %>
	</td>
	</tr>
	
	<tr>
	<td width=100 class="formLabel">
	Priority
	</td>
	<td bgColor="white">
	<%= PriorityList.getHtmlSelect("searchPriority",0) %>
	</td>
	</tr>
	
	<tr>
	<td width=100 class="formLabel">
	Status
	</td>
	<td bgColor="white">
	<%= TicketTypeSelect.getHtml() %>
	<input type=hidden name="search" value="1">
   	</td>
	</tr>
	
</table>
<br>
<input type=submit value="Search">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/TroubleTickets.do?command=Home'">
<input type="reset" value="Reset">
</form>
</body>

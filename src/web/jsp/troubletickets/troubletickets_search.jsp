<jsp:useBean id="OrgList" class="com.darkhorseventures.cfsbase.OrganizationList" scope="request"/>
<jsp:useBean id="PriorityList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="TicketTypeSelect" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<%@ include file="initPage.jsp" %>

<body onLoad="javascript:document.forms[0].searchcodeId.focus();">
<form name="searchTicket" action="/TroubleTickets.do?command=SearchTickets&auto-populate=true" method="post">
<a href="/TroubleTickets.do">Tickets</a> > 
Search Tickets<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
      <strong>Search Tickets</strong>
    </td>     
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Number
    </td>
    <td>
      <input type=text size=10 name="searchcodeId" value="">
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Description
    </td>
    <td>
      <input type=text size=40 name="searchDescription" value="">
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Organization
    </td>
	<td bgColor="white">
	<%= OrgList.getHtmlSelectDefaultNone("searchcodeOrgId") %>
	</td>
  </tr>
  
  <dhv:include name="tickets-severity" none="true">
  <tr>
	<td width=100 class="formLabel">
	Severity
	</td>
	<td bgColor="white">
	<%= SeverityList.getHtmlSelect("searchcodeSeverity",0) %>
	</td>
	</tr>
	</dhv:include>
  
  <dhv:include name="tickets-priority" none="true">
	<tr>
	<td width=100 class="formLabel">
	Priority
	</td>
	<td bgColor="white">
	<%= PriorityList.getHtmlSelect("searchcodePriority",0) %>
	</td>
	</tr>
  </dhv:include>
	
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

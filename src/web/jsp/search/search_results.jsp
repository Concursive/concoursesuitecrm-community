<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.search.base.*" %>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="EmployeeList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="OrganizationList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="OpportunityList" class="org.aspcfs.modules.pipeline.base.OpportunityList" scope="request"/>
<jsp:useBean id="TicketList" class="org.aspcfs.modules.troubletickets.base.TicketList" scope="request"/>
<jsp:useBean id="SearchSiteInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<!--b>Search Results</b-->
Your search for <b><%= request.getParameter("search") %></b> returned:
<br>&nbsp;<br> 
<dhv:permission name="contacts-external_contacts-view">
<strong><%=ContactList.size()%></strong> result(s) in <strong>External Contacts</strong>.
<%
Iterator i = ContactList.iterator();
if (i.hasNext()) {
%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td>
      <strong><a href="ExternalContacts.do?command=ListContacts&column=c.namelast">Name</a></strong>
    </td>
    <td>
      <strong><a href="ExternalContacts.do?command=ListContacts&column=company">Company</a></strong>
    </td>
    <td width=100>
      <strong>Phone: Business</strong>
    </td>
    <td width=100>
      <strong>Phone: Mobile</strong>
    </td>
  </tr>
<%    

	int rowid = 0;
	
		while (i.hasNext()) {
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
			
		Contact thisContact = (Contact)i.next();
%>      
      <tr>
        <td class="row<%= rowid %>" nowrap>
          <a href="ExternalContacts.do?command=ContactDetails&id=<%= thisContact.getId() %>"><%= toHtml(thisContact.getNameLastFirst()) %></a>
          <%= thisContact.getEmailAddressTag("Business", "<img border=0 src=\"images/email.gif\" alt=\"Send email\" align=\"absmiddle\">", "") %>
          <%= ((thisContact.getOrgId() > 0)?"<a href=\"Accounts.do?command=Details&orgId=" + thisContact.getOrgId() + "\">[Account]</a>":"") %>
        </td>
        <td width=175 class="row<%= rowid %>">
          <%= toHtml(thisContact.getAffiliation()) %>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= toHtml(thisContact.getPhoneNumber("Business")) %>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= toHtml(thisContact.getPhoneNumber("Mobile")) %>
        </td>
      </tr>
<%}%>
</table>
<br>
<%} else {%>
	<br>&nbsp;<br>
<%}%>
</dhv:permission>
<dhv:permission name="contacts-internal_contacts-view">
<strong><%=EmployeeList.size()%></strong> result(s) in <strong>Employees</strong>.
<% 
  Iterator j = EmployeeList.iterator();
  if (j.hasNext()) {
    int rowid = 0;
%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td>
      <a href="CompanyDirectory.do?command=ListEmployees&column=c.namelast">
        <strong>Name</strong>
      </a>
    </td>
    <td width=175>
      <a href="CompanyDirectory.do?command=ListEmployees&column=departmentname">
        <strong>Department</strong>
      </a>
    </td>
    <td width=100>
      <a href="CompanyDirectory.do?command=ListEmployees&column=title">
        <strong>Title</strong>
      </a>
    </td>
    <td width=100 nowrap>
      <strong>Phone: Business</strong>
    </td>
  </tr>
<%    
    while (j.hasNext()) {
      if (rowid != 1) {
        rowid = 1;
      } else {
        rowid = 2;
      }
      Contact thisEmployee = (Contact)j.next();
%>      
      <tr>
        <td class="row<%= rowid %>"><font class="columntext1">
          <a href="CompanyDirectory.do?command=EmployeeDetails&empid=<%= thisEmployee.getId() %>"><%= toHtml(thisEmployee.getNameLastFirst()) %></a></font>
          <%= thisEmployee.getEmailAddressTag("Business", "<img border=0 src=\"images/email.gif\" alt=\"Send email\" align=\"absmiddle\">", "") %>
        </td>
        <td class="row<%= rowid %>">
          <%= toHtml(thisEmployee.getDepartmentName()) %>
        </td>
        <td class="row<%= rowid %>">
          <%= toHtml(thisEmployee.getTitle()) %>
        </td>
        <td class="row<%= rowid %>">
          <%= toHtml(thisEmployee.getPhoneNumber("Business")) %>
        </td>
      </tr>
<%      
    }
%>
</table><br>
<%
  } else {
%>
	<br>&nbsp;<br>  
<%}%>
</dhv:permission>
<dhv:permission name="accounts-accounts-view">
<strong><%=OrganizationList.size()%></strong> result(s) in <strong>Accounts</strong>.
<% 
  Iterator k = OrganizationList.iterator();
  if (k.hasNext()) {
    int rowid = 0;
%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left>
      <strong><a href="Accounts.do?command=View&column=o.name">Account Name</a></strong>
    </td>
    <td width=175 valign=center align=left nowrap>
      <strong>Email</strong>
    </td>
        <td width=100 valign=center align=left nowrap>
      <strong>Phone</strong>
    </td>
    <td width=100 valign=center align=left nowrap>
      <strong>Fax</strong>
    </td>
  </tr>
<%    
    while (k.hasNext()) {
      if (rowid != 1) {
        rowid = 1;
      } else {
        rowid = 2;
      }
      Organization thisOrg = (Organization)k.next();
%>      
  <tr>
		<td class="row<%= rowid %>">
      <a href="Accounts.do?command=Details&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getName()) %></a>
		</td>
		<td valign=center class="row<%= rowid %>" nowrap><a href="mailto:<%= toHtml(thisOrg.getEmailAddress("Primary")) %>"><%= toHtml(thisOrg.getEmailAddress("Primary")) %></a></td>
		<td valign=center class="row<%= rowid %>" nowrap><%= toHtml(thisOrg.getPhoneNumber("Main")) %></td>
		<td valign=center class="row<%= rowid %>" nowrap><%= toHtml(thisOrg.getPhoneNumber("Fax")) %></td>
		
  </tr>
<%      
    }
%>
</table><br>
<%
  } else {
%>
	<br>&nbsp;<br>  
<%}%>
</dhv:permission>
<dhv:permission name="pipeline-opportunities-view">
<strong><%=OpportunityList.size()%></strong> result(s) in <strong>Opportunities</strong>.
<% 
  Iterator m = OpportunityList.iterator();
  if (m.hasNext()) {
    int rowid = 0;
%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
<tr bgcolor="#DEE0FA">
    <td valign=center>
      <strong><a href="Leads.do?command=ViewOpp&column=description">Opportunity</a></strong>
    </td>
    <td width=175 valign=center>
      <strong><a href="Leads.do?command=ViewOpp&column=acct_name">Organization</a></strong>
    </td>
    <td width=100 valign=center>
      <strong><a href="Leads.do?command=ViewOpp&column=guessvalue">Amount</a></strong>
    </td>
    <td width=100 valign=center nowrap>
      <strong><a href="Leads.do?command=ViewOpp&column=closedate">Revenue Start</a></strong>
    </td>
  </tr>
  <%
    while (m.hasNext()) {
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
		Opportunity thisOpp = (Opportunity)m.next();
%>      
	<tr bgcolor="white">
      
      <td valign=center class="row<%= rowid %>">
        <a href="Leads.do?command=DetailsOpp&id=<%=thisOpp.getId()%>">
        <%= toHtml(thisOpp.getDescription()) %></a>
      </td>
      
      <td valign=center class="row<%= rowid %>">
<%
      if (thisOpp.getAccountLink() > -1) {
%>        
        <a href="Opportunities.do?command=View&orgId=<%= thisOpp.getAccountLink() %>">
<%
      }
%>        
        <%= toHtml(thisOpp.getAccountName()) %>
<%
      if (thisOpp.getAccountLink() > -1) {
%>     
        </a>
<%
      }
%>        
      </td>
      
      <td valign=center nowrap class="row<%= rowid %>">
        $<%= thisOpp.getGuessCurrency() %>
      </td>
      
      <td valign=center nowrap class="row<%= rowid %>">
        <%= toHtml(thisOpp.getCloseDateString()) %>
      </td>

    </tr>
<%}%>
</table>
<br>
<%} else {%>
  <br>&nbsp;<br> 
<%}%>
</dhv:permission>
<dhv:permission name="tickets-tickets-view">
<strong><%=TicketList.size()%></strong> result(s) in <strong>Tickets</strong>.
<% 
  Iterator n = TicketList.iterator();
  if (n.hasNext()) {
    int rowid = 0;
%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr>
    <td valign=center align=left bgcolor="#DEE0FA">
      <strong>Number</strong>
    </td>
    <td bgcolor="#DEE0FA"><b>Priority</b></td>
    <td bgcolor="#DEE0FA"><b>Age</b></td>
    <td bgcolor="#DEE0FA"><b>Company</b></td>
    <td bgcolor="#DEE0FA"><b>Problem</b></td>
  </tr>
  <%
  	while (n.hasNext()) {
		if (rowid != 1) {
			rowid = 1;
		} else {
			rowid = 2;
		}
	
		Ticket thisTic = (Ticket)n.next();
%>   
  <tr>
		<td width=15 valign=center nowrap class="row<%= rowid %>">
      <a href="TroubleTickets.do?command=Details&id=<%=thisTic.getId()%>"><%=thisTic.getPaddedId()%></a>
		</td>
		<td width=10 valign=center nowrap class="row<%= rowid %>">
      <%=toHtml(thisTic.getPriorityName())%>
		</td>
		<td width=8% valign=center nowrap class="row<%= rowid %>">
      <%=thisTic.getAgeOf()%>
		</td>
		<td width=40% valign=center nowrap class="row<%= rowid %>">
      <%=toHtml(thisTic.getCompanyName())%>
		</td>
		<td width=52% valign=center class="row<%= rowid %>">
      <%=toHtml(thisTic.getProblem())%> <% if (thisTic.getCategoryName() != null) { %>[<%=toHtml(thisTic.getCategoryName())%>]<%}%>
		</td>
  </tr>
<%}%>
</table>
<br>
<%} else {%>
  <br>&nbsp;<br> 
<%}%>
</dhv:permission>

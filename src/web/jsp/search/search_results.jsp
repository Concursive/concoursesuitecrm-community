<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %> 
<%@ page import="org.aspcfs.modules.accounts.base.Organization" %> 
<%@ page import="org.aspcfs.modules.contacts.base.Contact" %> 
<%@ page import="org.aspcfs.modules.troubletickets.base.Ticket" %> 
<%@ page import="org.aspcfs.modules.pipeline.beans.OpportunityBean" %>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="EmployeeList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="OrganizationList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="OpportunityList" class="org.aspcfs.modules.pipeline.base.OpportunityList" scope="request"/>
<jsp:useBean id="TicketList" class="org.aspcfs.modules.troubletickets.base.TicketList" scope="request"/>
<jsp:useBean id="SearchSiteInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<!--b>Search Results</b-->
Your search for <b><%= request.getParameter("search") %></b> returned:
<br>&nbsp;<br> 
<dhv:permission name="contacts-external_contacts-view">
<strong><%= ContactList.size() %></strong> result(s) in <strong>General Contacts</strong>.
<%
Iterator i = ContactList.iterator();
if (i.hasNext()) {
%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td>
      <strong>Name</strong>
    </td>
    <td>
      <strong>Company</strong>
    </td>
    <td width="100">
      <strong>Phone: Business</strong>
    </td>
    <td width="100">
      <strong>Phone: Mobile</strong>
    </td>
  </tr>
<%    
	int rowid = 0;
		while (i.hasNext()) {
      rowid = (rowid != 1?1:2);
      Contact thisContact = (Contact)i.next();
%>      
      <tr class="row<%= rowid %>">
        <td nowrap>
          <a href="ExternalContacts.do?command=ContactDetails&id=<%= thisContact.getId() %>"><%= toHtml(thisContact.getNameLastFirst()) %></a>
          <%= thisContact.getEmailAddressTag("Business", "<img border=0 src=\"images/email.gif\" alt=\"Send email\" align=\"absmiddle\">", "") %>
          <%= ((thisContact.getOrgId() > 0)?"<a href=\"Accounts.do?command=Details&orgId=" + thisContact.getOrgId() + "\">[Account]</a>":"") %>
        </td>
        <td width="175">
          <%= toHtml(thisContact.getAffiliation()) %>
        </td>
        <td nowrap>
          <%= toHtml(thisContact.getPhoneNumber("Business")) %>
        </td>
        <td nowrap>
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
<strong><%= EmployeeList.size() %></strong> result(s) in <strong>Employees</strong>.
<% 
  Iterator j = EmployeeList.iterator();
  if (j.hasNext()) {
    int rowid = 0;
%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td>
      <strong>Name</strong>
    </td>
    <td width="175">
      <strong>Department</strong>
    </td>
    <td width="100">
      <strong>Title</strong>
    </td>
    <td width="100" nowrap>
      <strong>Phone: Business</strong>
    </td>
  </tr>
<%    
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      Contact thisEmployee = (Contact)j.next();
%>      
      <tr class="row<%= rowid %>">
        <td>
          <a href="CompanyDirectory.do?command=EmployeeDetails&empid=<%= thisEmployee.getId() %>"><%= toHtml(thisEmployee.getNameLastFirst()) %></a>
          <%= thisEmployee.getEmailAddressTag("Business", "<img border=0 src=\"images/email.gif\" alt=\"Send email\" align=\"absmiddle\">", "") %>
        </td>
        <td>
          <%= toHtml(thisEmployee.getDepartmentName()) %>
        </td>
        <td>
          <%= toHtml(thisEmployee.getTitle()) %>
        </td>
        <td>
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
<strong><%= OrganizationList.size() %></strong> result(s) in <strong>Accounts</strong>.
<% 
  Iterator k = OrganizationList.iterator();
  if (k.hasNext()) {
    int rowid = 0;
%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td>
      <strong>Account Name</strong>
    </td>
    <td width="175">
      <strong>Email</strong>
    </td>
        <td width="100">
      <strong>Phone</strong>
    </td>
    <td width="100">
      <strong>Fax</strong>
    </td>
  </tr>
<%    
    while (k.hasNext()) {
      rowid = (rowid != 1?1:2);
      Organization thisOrg = (Organization)k.next();
%>
  <tr class="row<%= rowid %>">
		<td>
      <a href="Accounts.do?command=Details&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getName()) %></a>
		</td>
		<td valign="center" nowrap><a href="mailto:<%= toHtml(thisOrg.getEmailAddress("Primary")) %>"><%= toHtml(thisOrg.getEmailAddress("Primary")) %></a></td>
		<td valign="center" nowrap><%= toHtml(thisOrg.getPhoneNumber("Main")) %></td>
		<td valign="center" nowrap><%= toHtml(thisOrg.getPhoneNumber("Fax")) %></td>
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
<strong><%= OpportunityList.size() %></strong> result(s) in <strong>Opportunities</strong>.
<% 
  Iterator m = OpportunityList.iterator();
  if (m.hasNext()) {
    int rowid = 0;
%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
<tr class="title">
    <td valign="center">
      <strong>Opportunity</strong>
    </td>
    <td width="175" valign="center">
      <strong>Organization</strong>
    </td>
    <td width="100" valign="center">
      <strong>Amount</strong>
    </td>
    <td width="100" valign="center" nowrap>
      <strong>Revenue Start</strong>
    </td>
  </tr>
  <%
    while (m.hasNext()) {
			rowid = (rowid != 1?1:2);
      OpportunityBean thisOpp = (OpportunityBean) m.next();
%>      
	<tr class="row<%= rowid %>">
      <td valign="center">
        <a href="Leads.do?command=DetailsOpp&headerId=<%= thisOpp.getHeader().getId() %>">
        <%= toHtml(thisOpp.getHeader().getDescription()) %>: <%= toHtml(thisOpp.getComponent().getDescription()) %></a>
      </td>
      <td valign="center">
<%
      if (thisOpp.getHeader().getAccountLink() > -1) {
%>        
        <a href="Opportunities.do?command=View&orgId=<%= thisOpp.getHeader().getAccountLink() %>">
<%
      }
%>        
        <%= toHtml(thisOpp.getHeader().getAccountName()) %>
<%
      if (thisOpp.getHeader().getAccountLink() > -1) {
%>     
        </a>
<%
      }
%>        
      </td>
      <td valign="center" nowrap>
        $<%= thisOpp.getComponent().getGuessCurrency() %>
      </td>
      <td valign="center" nowrap>
        <%= toHtml(thisOpp.getComponent().getCloseDateString()) %>
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
<strong><%= TicketList.size() %></strong> result(s) in <strong>Tickets</strong>.
<% 
  Iterator n = TicketList.iterator();
  if (n.hasNext()) {
    int rowid = 0;
%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign="center">
      <strong>Number</strong>
    </td>
    <td><b>Priority</b></td>
    <td><b>Age</b></td>
    <td><b>Company</b></td>
    <td><b>Assigned&nbsp;To</b></td>
  </tr>
  <%
  	while (n.hasNext()) {
      rowid = (rowid != 1?1:2);
      Ticket thisTic = (Ticket) n.next();
%>   
  <tr class="row<%= rowid %>">
		<td rowspan="2" width="15" valign="top" nowrap>
      <a href="TroubleTickets.do?command=Details&id=<%=thisTic.getId()%>"><%=thisTic.getPaddedId()%></a>
		</td>
		<td width="10" valign="top" nowrap>
      <%= toHtml(thisTic.getPriorityName()) %>
		</td>
		<td width="8%" align="right" valign="top" nowrap>
      <%= thisTic.getAgeOf() %>
		</td>
		<td width="90%" valign="top">
      <%= toHtml(thisTic.getCompanyName()) %>
		</td>
    <td width="150" nowrap valign="top">
      <%= toHtml(thisTic.getOwnerName()) %>
    </td>
  </tr>
  <tr class="row<%= rowid %>">
		<td colspan="4" valign="top">
      <%= toHtml(thisTic.getProblemHeader()) %>
		</td>
  </tr>
<%}%>
</table>
<br>
<%} else {%>
  <br>&nbsp;<br> 
<%}%>
</dhv:permission>

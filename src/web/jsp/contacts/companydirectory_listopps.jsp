<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="ContactDetails" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<jsp:useBean id="OpportunityList" class="com.darkhorseventures.cfsbase.OpportunityList" scope="request"/>
<jsp:useBean id="OpportunityPagedListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<a href="ExternalContacts.do">Contacts &amp; Resources</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
Opportunities<br>
<hr color="#BFBFBB" noshade>
<a href="ExternalContacts.do?command=ListContacts">Back to Contact List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + ContactDetails.getId(); %>      
      <dhv:container name="contacts" selected="opportunities" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="contacts-external_contacts-opportunities-add"><a href="ExternalContactsOpps.do?command=AddOpp&contactId=<%= ContactDetails.getId() %>">Add an Opportunity</a></dhv:permission>
<center><%= OpportunityPagedListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="OpportunityPagedListInfo"/>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete">
    <td valign=center align=left>
      <strong>Action</strong>
    </td>
    </dhv:permission>
  
    <td valign=center align=left>
      <strong>Opportunity Name</strong>
    </td>
    
    <td valign=center align=left>
      <strong>Best Guess Amount</strong>
    </td>
    
    <td valign=center align=left>
      <strong>Close Date</strong>
    </td>
      
    <td valign=center align=left>
      <strong>Current Stage</strong>
    </td>  
  </tr>
<%
	Iterator j = OpportunityList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
	    while (j.hasNext()) {
		
        if (rowid != 1) {
          rowid = 1;
        } else {
          rowid = 2;
        }
		
		Opportunity thisOpp = (Opportunity)j.next();
%>      
    <tr class="containerBody">
      <dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete">
      <td width=8 valign=center nowrap class="row<%= rowid %>">
      <dhv:permission name="contacts-external_contacts-opportunities-edit"><a href="ExternalContactsOpps.do?command=ModifyOpp&id=<%= thisOpp.getId() %>&orgId=<%= thisOpp.getAccountLink()%>&contactId=<%= thisOpp.getContactLink() %>&return=list">Edit</a></dhv:permission><dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="contacts-external_contacts-opportunities-delete"><a href="javascript:popURLReturn('ExternalContactsOpps.do?command=ConfirmDelete&contactId=<%=ContactDetails.getId()%>&id=<%=thisOpp.getId()%>','ExternalContactsOpps.do?command=ViewOpps&contactId=<%=ContactDetails.getId()%>', 'Delete_opp','320','200','yes','no');">Del</a></dhv:permission>
      </td>
      </dhv:permission>
      <td width=40% valign=center class="row<%= rowid %>">
        <a href="ExternalContactsOpps.do?command=DetailsOpp&id=<%=thisOpp.getId()%>&contactId=<%= ContactDetails.getId() %>">
        <%= toHtml(thisOpp.getDescription()) %></a>
      </td>
      <td width=20% valign=center nowrap class="row<%= rowid %>">
        $<%= thisOpp.getGuessCurrency() %>
      </td>
      <td width=20% valign=center nowrap class="row<%= rowid %>">
        <%= toHtml(thisOpp.getCloseDateString()) %>
      </td>
      <td width=20% valign=center class="row<%= rowid %>">
        <%= thisOpp.getStageName() %>
      </td>		
    </tr>
<%
    }
	} else {
%>
    <tr class="containerBody">
      <td colspan=5 valign=center>
        No opportunities found.
      </td>
    </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="OpportunityPagedListInfo"/>
</td>
</tr>
</table>


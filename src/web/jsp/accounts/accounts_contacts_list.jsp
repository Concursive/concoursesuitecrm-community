<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="ContactList" class="com.darkhorseventures.cfsbase.ContactList" scope="request"/>
<jsp:useBean id="ContactListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<a href="/Accounts.do?command=View">Back to Account List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <a href = "Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><font color="#000000">Details</font></a> | 
      <a href="/Accounts.do?command=Fields&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Folders</font></a> |
      <font color="#787878">Activities</font> | 
      <a href = "Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><font color="#0000FF">Contacts</font></a> | 
      <a href = "Opportunities.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><font color="#000000">Opportunities</font></a> | 
      <a href="Accounts.do?command=ViewTickets&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Tickets</font></a> |
      <font color="#787878">Documents</font> 
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<a href="Contacts.do?command=Add&orgId=<%=request.getParameter("orgId")%>">Add a Contact</a>
<center><%= ContactListInfo.getAlphabeticalPageLinks() %></center>


<%= showAttribute(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
<tr class="title">
  <td valign=center align=left>
    <strong>Action</strong>
  </td>
  <td width=30% valign=center align=left>
    <strong>Name</strong>
  </td>  
  <td width=20% valign=center align=left>
    <strong>Title</strong>
  </td>   
  <td width=20% valign=center align=left>
    <strong>Phone</strong>
  </td>
  <td width=30% valign=center align=left>
    <strong>Email</strong>
  </td>
</tr>
<%
	Iterator j = ContactList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
	       	while (j.hasNext()) {
		
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
		
		Contact thisContact = (Contact)j.next();
%>      
		<tr class="containerBody">
      <td width=8 valign=center nowrap class="row<%= rowid %>">
        <a href="/Contacts.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %>&id=<%=thisContact.getId()%>&return=list">Edit</a>|<a href="javascript:confirmDelete('/Contacts.do?command=Delete&orgId=<%= OrgDetails.getOrgId() %>&id=<%=thisContact.getId()%>');">Del</a>
      </td>
      <td width=30% valign=center class="row<%= rowid %>">
        <a href="/Contacts.do?command=Details&id=<%=thisContact.getId()%>"><%= toHtml(thisContact.getNameLast()) %>, <%= toHtml(thisContact.getNameFirst()) %></a>
      </td>
      <td width=20% valign=center class="row<%= rowid %>">
        <%= toHtml(thisContact.getTitle()) %>
      </td>
      <td width=20% valign=center class="row<%= rowid %>" nowrap>
        <%= toHtml(thisContact.getPhoneNumber("Business")) %>
      </td>
      <td width=30% valign=center class="row<%= rowid %>">
        <a href="mailto:<%= toHtml(thisContact.getEmailAddress("Business")) %>"><%= toHtml(thisContact.getEmailAddress("Business")) %></a>
      </td>
		</tr>
<%}%>
	</table>
	<br>
	[<%= ContactListInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %> <%= ContactListInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>]
<%} else {%>
		<tr class="containerBody">
      <td colspan=5 valign=center>
        No contacts found.
      </td>
    </tr>
		</table>
<%}%>
</td>
</tr>
</table>


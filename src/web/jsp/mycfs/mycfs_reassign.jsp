<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="UserList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="UserSelectList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="SourceUser" class="com.darkhorseventures.cfsbase.User" scope="request"/>
<jsp:useBean id="SourceAccounts" class="com.darkhorseventures.cfsbase.OrganizationList" scope="request"/>
<jsp:useBean id="SourceContacts" class="com.darkhorseventures.cfsbase.ContactList" scope="request"/>
<jsp:useBean id="SourceUsers" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="SourceOpenTickets" class="com.darkhorseventures.cfsbase.TicketList" scope="request"/>
<jsp:useBean id="SourceRevenue" class="com.darkhorseventures.cfsbase.RevenueList" scope="request"/>
<jsp:useBean id="SourceOpportunities" class="com.darkhorseventures.cfsbase.OpportunityList" scope="request"/>
<jsp:useBean id="SourceOpenOpportunities" class="com.darkhorseventures.cfsbase.OpportunityList" scope="request"/>
<jsp:useBean id="SourceAssignments" class="com.zeroio.iteam.base.AssignmentList" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<a href="MyCFS.do?command=Home">My Home Page</a> > 
Re-assignments<br>
<hr color="#BFBFBB" noshade>
<% if (request.getAttribute("actionError") != null) { %>
<%= showError(request, "actionError") %>
<%}%>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="Reassignments.do?command=Reassign">
    <td align="left" valign="bottom">
    <% UserSelectList.setJsEvent("onChange=\"javascript:document.forms['listView'].submit();\""); %>
    User to re-assign data from:<br>
    <%= UserSelectList.getHtmlSelect("userId", SourceUser.getId()) %>
    </td>
    </form>
  </tr>
</table>

<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
<form name="adminReassign" action="Reassignments.do?command=DoReassign" method="post">
  <tr class="title">
    <td valign=center align=left>
      <strong>Re-assign</strong>
    </td>
    <td width=150 valign=center align=left nowrap>
      <strong>From User</strong>
    </td>
    <td width=150 valign=center align=left nowrap>
      <strong>To User</strong>
    </td>
  </tr>
  
  <tr>
		<td class="row1">
    Accounts (<%=SourceAccounts.size()%>)
		</td>
    
    <% if (SourceUser.getId() > -1) { %>
		  <td width=150 valign=center class="row1" nowrap><%=SourceUser.getContact().getNameLastFirst()%>
      </td>
    <%} else {%>
		  <td width=150 valign=center class="row1" nowrap>None Selected</td>
    <%}%>
    
		<td width=150 valign=center class="row1" nowrap><%= UserList.getHtmlSelect("ownerToAccounts") %></td>
  </tr>
  
  <tr>
		<td class="row2">
    Contacts (<%=SourceContacts.size()%>)
		</td>
		<% if (SourceUser.getId() > -1) { %>
		  <td width=150 valign=center class="row2" nowrap><%=SourceUser.getContact().getNameLastFirst()%>
      </td>
    <%} else {%>
		  <td width=150 valign=center class="row2" nowrap>None Selected</td>
    <%}%>
		<td width=150 valign=center class="row2" nowrap><%= UserList.getHtmlSelect("ownerToContacts") %></td>
  </tr>  
  
  <tr>
		<td class="row1">
    Opportunities (Open Only) (<%=SourceOpenOpportunities.size()%>)
		</td> 
		<% if (SourceUser.getId() > -1) { %>
		  <td width=150 valign=center class="row1" nowrap><%=SourceUser.getContact().getNameLastFirst()%>
      </td>
    <%} else {%>
		  <td width=150 valign=center class="row1" nowrap>None Selected</td>
    <%}%>
		<td width=150 valign=center class="row1" nowrap><%= UserList.getHtmlSelect("ownerToOpenOpps") %></td>
  </tr>  
  
  <tr>
		<td class="row2">
    Opportunities (Open &amp; Closed) (<%=SourceOpportunities.size()%>)
		</td>
		<% if (SourceUser.getId() > -1) { %>
		  <td width=150 valign=center class="row2" nowrap><%=SourceUser.getContact().getNameLastFirst()%>
      </td>
    <%} else {%>
		  <td width=150 valign=center class="row2" nowrap>None Selected</td>
    <%}%>
		<td width=150 valign=center class="row2" nowrap><%= UserList.getHtmlSelect("ownerToOpenClosedOpps") %></td>
  </tr>  
  
  <tr>
		<td class="row1">
    Project Activities (Incomplete) (<%=SourceAssignments.size()%>)
		</td>
		<% if (SourceUser.getId() > -1) { %>
		  <td width=150 valign=center class="row1" nowrap><%=SourceUser.getContact().getNameLastFirst()%>
      </td>
    <%} else {%>
		  <td width=150 valign=center class="row1" nowrap>None Selected</td>
    <%}%>
		<td width=150 valign=center class="row1" nowrap><%= UserList.getHtmlSelect("ownerToActivities") %></td>
  </tr>  
  
  <tr>
		<td class="row2">
    Account Revenue (<%=SourceRevenue.size()%>)
		</td>
		<% if (SourceUser.getId() > -1) { %>
		  <td width=150 valign=center class="row2" nowrap><%=SourceUser.getContact().getNameLastFirst()%>
      </td>
    <%} else {%>
		  <td width=150 valign=center class="row2" nowrap>None Selected</td>
    <%}%>
		<td width=150 valign=center class="row2" nowrap><%= UserList.getHtmlSelect("ownerToRevenue") %></td>
  </tr>  
  
  <tr>
		<td class="row1">
    Tickets (Open) (<%=SourceOpenTickets.size()%>)
		</td>
		<% if (SourceUser.getId() > -1) { %>
		  <td width=150 valign=center class="row1" nowrap><%=SourceUser.getContact().getNameLastFirst()%>
      </td>
    <%} else {%>
		  <td width=150 valign=center class="row1" nowrap>None Selected</td>
    <%}%>
		<td width=150 valign=center class="row1" nowrap><%= UserList.getHtmlSelect("ownerToOpenTickets") %></td>
  </tr> 

  <tr>
		<td class="row2">
    Users Reporting-to (<%=SourceUsers.size()%>)
		</td>
		<% if (SourceUser.getId() > -1) { %>
		  <td width=150 valign=center class="row2" nowrap><%=SourceUser.getContact().getNameLastFirst()%>
      </td>
    <%} else {%>
		  <td width=150 valign=center class="row2" nowrap>None Selected</td>
    <%}%>
		<td width=150 valign=center class="row2" nowrap><%= UserList.getHtmlSelect("ownerToUsers") %></td>
  </tr>  
  
</table>
&nbsp;<br>
<dhv:permission name="admin-reassign-edit">
<input type="submit" value="Update">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='MyCFS.do?command=Home'">
<input type="reset" value="Reset">
<input type="hidden" name="userId" value="<%=SourceUser.getId()%>">
</dhv:permission>
</form>
</body>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.*,java.util.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="ManagerUser" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="disableUser" action="Users.do?command=DisableUser" method="POST">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Admin.do">Admin</a> >
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	  <a href="Users.do?command=ListUsers">View Users</a> >
  <%}%>
<%} else {%>
  <a href="Users.do?command=ListUsers">View Users</a> >
  <a href="Users.do?command=UserDetails&id=<%= User.getId() %>">User Details</a> >
<%}%>
Disable User Account
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Disable a User account</strong>
    </th>
  </tr>
  <tr class="containerBack">
    <td align="left">
      You have requested to disable the Dark Horse CRM user account <b><%=User.getUsername()%></b> (<%=User.getContact().getNameLastFirst()%>).<p>
      Nearly every object within Dark Horse CRM ultimately depends on a user account, so it is imperative that 
      any data directly associated with the user account you wish to disable gets re-assigned to an active user within the system.
      <p>
      If you click "Proceed", the following actions will occur:
      <ul>
      <li><b><%= User.getUsername() %></b> will be immediately disabled.
      <li>A Ticket will be created and assigned to the direct manager (<%= ManagerUser.getContact().getNameLastFirst() %>) of the newly disabled User in reference to any data re-assignment that needs to occur.
      <li>The direct manager will be notified via email.
      </ul>
<dhv:permission name="myhomepage-reassign-edit">
      If you are also responsible for the data re-assignments of this user, you can go to "Re-Assignments" in My Home Page, or just <a href="Reassignments.do?command=Reassign&userId=<%=User.getId()%>">click here</a> to go to
      that module and make those changes.<br><br>
</dhv:permission>
      <input type="checkbox" name="disablecontact"> Check this box if you want to archive the contact associated with this user.<br>&nbsp;
    </td>
  </tr>
</table>
<br>
<input type="submit" value="Proceed">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
      <input type="button" value="Cancel" onClick="javascript:this.form.action='Users.do?command=ListUsers';this.form.submit()">
	<%}%>
<%} else {%>
      <input type="button" value="Cancel" onClick="javascript:this.form.action='Users.do?command=UserDetails';this.form.submit()">
<%}%>
      <input type="hidden" name="id" value="<%= User.getId() %>">
</form>

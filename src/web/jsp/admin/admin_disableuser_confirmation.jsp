<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.*,java.util.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="ManagerUser" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<%@ include file="../initPage.jsp" %>
<a href="Admin.do">Setup</a> >
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	  <a href="Users.do?command=ListUsers">View Users</a> >
  <%}%>
<%} else {%>
  <a href="Users.do?command=ListUsers">View Users</a> >
  <a href="Users.do?command=UserDetails&id=<%=User.getId()%>">User Details</a> >
<%}%>

Disable User Account
<hr color="#BFBFBB" noshade>

<table cellpadding=6 cellspacing=0 border=1 width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr>
    <td align="left" bgcolor="#DEE0FA">
    <strong>Disable a User account</strong>
    </td>
  </tr>
  <tr>
    <td align="left">
You have requested to disable the CFS User account <b><%=User.getUsername()%></b> (<%=User.getContact().getNameLastFirst()%>).<p>
  Nearly every object within CFS ultimately depends on a User account, so it is imperative that 
any data directly associated with the account you wish to disable gets re-assigned to an active User within the system.
<p>
If you click "Proceed", the following actions will occur:
<ul>
<li><b><%=User.getUsername()%></b> will be immediately disabled.
<li>A Ticket will be created and assigned to the direct manager (<%=ManagerUser.getContact().getNameLastFirst()%>) of the newly disabled User in reference to any data re-assignment that needs to occur.
<li>The direct manager will be notified via email.
</ul>

<dhv:permission name="admin-reassign-edit">
If you are also responsible for the data re-assignments of this User, you can goto "Re-Assignments" above, or just <a href="Users.do?command=Reassign&userId=<%=User.getId()%>">click here</a>  to make
those changes.
</dhv:permission>
<br>&nbsp;
</td></tr>

  <tr>
    <td align="left">
<form name="disableUser" action="Users.do?command=DisableUser" method="POST">
<input type="submit" value="Proceed">

<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="button" value="Cancel" onClick="javascript:this.form.action='Users.do?command=ListUsers';this.form.submit()">
	<%}%>
<%} else {%>
<input type="button" value="Cancel" onClick="javascript:this.form.action='Users.do?command=UserDetails';this.form.submit()">
<%}%>
<input type="hidden" name="id" value="<%=User.getId()%>">
</td>
</tr>
</table>
</form>
  

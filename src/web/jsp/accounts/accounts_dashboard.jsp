<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="MyOrgList" class="com.darkhorseventures.cfsbase.OrganizationList" scope="request"/>
<jsp:useBean id="ShortChildList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="GraphTypeList" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<%@ include file="initPage.jsp" %>

<form name=Dashboard action="/Accounts.do?command=Dashboard" method=POST>

<table width=100% border=0 cellspacing=0 cellpadding=3>
<tr>
<td width=350 valign=top>

	<table width=100% cellpadding=3 cellspacing=0 border=1 bordercolorlight="#000000" bordercolor="#FFFFFF">
	<tr bgcolor="#DEE0FA">
	<td valign=center colspan=1 align=center>
		<strong>
		<% if (request.getAttribute("override") == null || request.getAttribute("override").equals("null")) {%>
		My Dashboard
		<%} else if (!(request.getAttribute("othername") == null) && !(request.getAttribute("othername").equals("null"))) {%>
		Dashboard - <%=request.getAttribute("othername")%>
		<%}%>
		</strong>
	</td>
	</tr>
	<tr>
	<td>
		<img border=0 width="300" height="200" src="/images/acct_dashboard_tmp.jpg">
	</td>
	</tr>
	</table>
	&nbsp;
	<center>
	<%= GraphTypeList.getHtml() %>&nbsp;<input type=submit value="Show">
	</center>
</td>
	
<td valign=top width="100%">

	<table width=100% cellpadding=3 cellspacing=0 border=1 bordercolorlight="#000000" bordercolor="#FFFFFF">
	<tr bgcolor="#DEE0FA">
	<td valign=center>
		<strong>Account</strong>
	</td>
		<td valign=center width=161>
		<strong>Industry</strong>
	</td>
	</tr>
		<%
		Iterator n = MyOrgList.iterator();
	
		if ( n.hasNext() ) {
			int rowid = 0;
			while (n.hasNext()) {
	
				if (rowid != 1) {
					rowid = 1;
				} else {
					rowid = 2;
				}
		
				Organization thisOrg = (Organization)n.next();
		%>    
				<tr>
				<td class="row<%= rowid %>" valign=center><a href="/Accounts.do?command=Details&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getName()) %></a></td>
				<td class="row<%= rowid %>" valign=center width=161><%= toHtml(thisOrg.getIndustryName()) %></td>
				</tr>
		<%}
		} else {%>
		<tr>
		<td valign=center colspan=2>No Accounts at this time.</td>
		</tr>
		<%}%>
	</table>
	<br>
	<table width=100% cellpadding=3 cellspacing=0 border=1 bordercolorlight="#000000" bordercolor="#FFFFFF">
	<tr bgcolor="#DEE0FA">
	<td valign=center colspan=1>
		<strong>Reporting Staff</strong>
	</td>
	
	<td valign=center colspan=1 width=161>
		<strong>Department</strong>
	</td>
	</tr>
		<%
		Iterator x = ShortChildList.iterator();
		if ( x.hasNext() ) {
		int rowid = 0;
		while (x.hasNext()) {
	
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
			User thisRec = (User)x.next();
		%>    
		<tr>
			<td class="row<%= rowid %>" valign=center><a href="/Accounts.do?command=Dashboard&oid=<%=thisRec.getId()%>"><%= toHtml(thisRec.getContact().getNameLast()) %>, <%= toHtml(thisRec.getContact().getNameFirst()) %></a></td>
			<td class="row<%= rowid %>" valign=center width=161><%= toHtml(thisRec.getContact().getDepartmentName()) %></td>
		</tr>
	
		<%    }
		} else {%>
		<tr>
			<td valign=center colspan=3>No Reporting staff.</td>
		</tr>
		<%}%>
	</table>
	
	<br>
	<% if (!(request.getAttribute("override") == null) && !(request.getAttribute("override").equals("null"))) {%>
	<input type=hidden name="oid" value="<%=request.getAttribute("override")%>">
	<a href = "Accounts.do?command=Dashboard&oid=<%=request.getAttribute("previousId")%>">Up One Level</a> |
	<a href = "Accounts.do?command=Dashboard">Back to My Dashboard</a>
	<%}%>
</td>
</tr>
</table>	   
</form>
</body>

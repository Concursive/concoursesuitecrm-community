<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OrgList" class="com.darkhorseventures.cfsbase.OrganizationList" scope="request"/>
<jsp:useBean id="OrgListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<form name="listView" method="post" action="/Accounts.do?command=View">
<a href="/Accounts.do?command=Add">Add an Account</a>
<center><%= OrgListInfo.getAlphabeticalPageLinks() %></center>

<table width="100%" border="0">
  <tr>
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= OrgListInfo.getOptionValue("all") %>>All Accounts</option>
        <option <%= OrgListInfo.getOptionValue("my") %>>My Accounts </option>
      </select>
      <%= showAttribute(request, "actionError") %>
    </td>
  </tr>
</table>

<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left bgcolor="#DEE0FA">
      <strong>Action</strong>
    </td>
    <td width=30% valign=center align=left>
      <strong><a href="/Accounts.do?command=View&column=o.name">Account Name</a></strong>
      <%= OrgListInfo.getSortIcon("o.name") %>
    </td>
    <td width=20% valign=center align=left nowrap>
      <strong>Phone</strong>
    </td>
    <td width=20% valign=center align=left nowrap>
      <strong>Fax</strong>
    </td>
    <td width=30% valign=center align=left nowrap>
      <strong>Email</strong>
    </td>
  </tr>
<%
	Iterator j = OrgList.iterator();
	
	if ( j.hasNext() ) {
    int rowid = 0;
    while (j.hasNext()) {
    if (rowid != 1) {
      rowid = 1;
    } else {
      rowid = 2;
    }
    Organization thisOrg = (Organization)j.next();
%>      
  <tr>
    <td width=8 valign=center nowrap class="row<%= rowid %>">
      <a href="/Accounts.do?command=Modify&orgId=<%= thisOrg.getOrgId() %>&return=list">Edit</a>|<a href="javascript:confirmDelete('/Accounts.do?command=Delete&orgId=<%= thisOrg.getOrgId() %>');">Del</a>
    </td>
		<td width="30%" class="row<%= rowid %>">
      <a href="/Accounts.do?command=Details&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getName()) %></a>
		</td>
		<td width=20% valign=center class="row<%= rowid %>" nowrap><%= toHtml(thisOrg.getPhoneNumber("Main")) %></td>
		<td width=20% valign=center class="row<%= rowid %>" nowrap><%= toHtml(thisOrg.getPhoneNumber("Fax")) %></td>
		<td width=30% valign=center class="row<%= rowid %>" nowrap><a href="mailto:<%= toHtml(thisOrg.getEmailAddress("Primary")) %>"><%= toHtml(thisOrg.getEmailAddress("Primary")) %></a></td>
  </tr>
<%}%>
<%} else {%>
  <tr bgcolor="white"><td colspan=5 valign=center>No accounts found.</td></tr>
<%}%>
</table>
<br>
[<%= OrgListInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %> <%= OrgListInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>] <%= OrgListInfo.getNumericalPageLinks() %>
</form>

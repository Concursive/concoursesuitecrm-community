<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="RevenueList" class="com.darkhorseventures.cfsbase.RevenueList" scope="request"/>
<jsp:useBean id="RevenueListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<a href="/Accounts.do?command=View">Back to Account List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <!--tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="contacts" param="<%= param1 %>" />
    </td>
  </tr-->
  <tr>
    <td class="containerBack">
<a href="RevenueManager.do?command=Add&orgId=<%=request.getParameter("orgId")%>">Add a new Revenue</a>
<center><%= RevenueListInfo.getAlphabeticalPageLinks() %></center>


<%= showAttribute(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
<tr class="title">
  <td valign=center align=left>
    <strong>Action</strong>
  </td>
  <td width=30% valign=center align=left>
    <strong>Description</strong>
  </td>  
  <td width=20% valign=center align=left>
    <strong>Month</strong>
  </td>   
  <td width=20% valign=center align=left>
    <strong>Year</strong>
  </td>
  <td width=30% valign=center align=left>
    <strong>Amount</strong>
  </td>
</tr>
<%
	Iterator j = RevenueList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
	       	while (j.hasNext()) {
		
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
		
		Revenue thisRevenue = (Revenue)j.next();
%>      
		<tr class="containerBody">
      <td width=8 valign=center nowrap class="row<%= rowid %>">
        <a href="/RevenueManager.do?command=Modify&orgId=<%= OrgDetails.getOrgId()%>&id=<%=thisRevenue.getId()%>&return=list">Edit</a>|<a href="javascript:confirmDelete('/RevenueManager.do?command=Delete&orgId=<%= OrgDetails.getOrgId() %>&id=<%=thisRevenue.getId()%>');">Del</a>
      </td>
      <td width=30% valign=center class="row<%= rowid %>">
        <a href="/RevenueManager.do?command=Details&id=<%=thisRevenue.getId()%>"><%= toHtml(thisRevenue.getDescription()) %></a>
      </td>
      <td width=20% valign=center class="row<%= rowid %>">
        <%= thisRevenue.getMonth() %>
      </td>
      <td width=20% valign=center class="row<%= rowid %>" nowrap>
        <%= thisRevenue.getYear() %>
      </td>
      <td width=30% valign=center class="row<%= rowid %>">
        <%=thisRevenue.getAmountValue()%>
      </td>
		</tr>
<%}%>
<%} else {%>
		<tr class="containerBody">
      <td colspan=5 valign=center>
        No revenue found.
      </td>
    </tr>
<%}%>
	</table>
	<br>
  <dhv:pagedListControl object="RevenueListInfo"/>
</td>
</tr>
</table>


<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.webutils.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="HeaderDetails" class="com.darkhorseventures.cfsbase.OpportunityHeader" scope="request"/>
<%@ include file="initPage.jsp" %>
<form name="modifyOpp" action="Opportunities.do?command=Update&orgId=<%= OrgDetails.getId() %>&auto-populate=true" method="post">
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="Opportunities.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Opportunities</a> >

<% if (request.getParameter("return") == null) {%>
	<a href="Opportunities.do?command=Details&oppId=<%=HeaderDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Opportunity Details</a> >
<%}%>

Modify Opportunity<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="opportunities" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
    
<input type="hidden" name="oppId" value="<%= HeaderDetails.getId() %>">
<input type="hidden" name="modified" value="<%= HeaderDetails.getModified() %>">

<% if (request.getParameter("return") != null) {%>
  <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>

<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">

<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='Opportunities.do?command=View&orgId=<%= HeaderDetails.getAccountLink() %>';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='Opportunities.do?command=Details&oppId=<%= HeaderDetails.getId() %>&orgId=<%= HeaderDetails.getAccountLink() %>';this.form.dosubmit.value='false';">
<%}%>

<input type="reset" value="Reset">
<br>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
  <td colspan=2 valign=center align=left>
    <strong><%=HeaderDetails.getDescription()%></strong>
  </td>     
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Description
  </td>
  <td valign=center>
    <input type=text size=50 name="description" value="<%= toHtmlValue(HeaderDetails.getDescription()) %>">
    <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
  </td>
</tr>
</table>
&nbsp;
<br>
<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">

<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='Opportunities.do?command=View&orgId=<%= HeaderDetails.getAccountLink() %>';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='Opportunities.do?command=Details&oppId=<%= HeaderDetails.getId() %>&orgId=<%= HeaderDetails.getAccountLink() %>';this.form.dosubmit.value='false';">
<%}%>

<input type="reset" value="Reset">
<input type="hidden" name="dosubmit" value="true">
  </td>
  </tr>
  </table>
</form>
</body>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="contactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="headerDetails" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="modifyOpp" action="ExternalContactsOpps.do?command=UpdateOpp&contactId=<%= contactDetails.getId() %>&auto-populate=true" method="post">
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= contactDetails.getId() %>">Contact Details</a> >
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	  <a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= contactDetails.getId() %>">Opportunities</a> >
  <%}%>
<%} else {%>
<a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= contactDetails.getId() %>">Opportunities</a> >
<a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= headerDetails.getId() %>&contactId=<%= contactDetails.getId() %>">Opportunity Details</a> >
<%}%>
Modify Opportunity<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(contactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + contactDetails.getId(); %>      
      <dhv:container name="contacts" selected="opportunities" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
    
<input type="hidden" name="headerId" value="<%= headerDetails.getId() %>">
<input type="hidden" name="modified" value="<%= headerDetails.getModified() %>">

<% if (request.getParameter("return") != null) {%>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=ViewOpps&contactId=<%= contactDetails.getId() %>';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= headerDetails.getId() %>&contactId=<%= contactDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>
<br>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">

<tr class="title">
  <td colspan=2 valign=center align=left>
    <strong><%= headerDetails.getDescription() %></strong>
  </td>     
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Description
  </td>
  <td valign=center>
    <input type=text size=50 name="description" value="<%= toHtmlValue(headerDetails.getDescription()) %>">
    <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
  </td>
</tr>
</table>
&nbsp;
<br>
<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=ViewOpps&contactId=<%= contactDetails.getId() %>';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= headerDetails.getId() %>&contactId=<%= contactDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>
<input type="hidden" name="dosubmit" value="true">
  </td>
  </tr>
  </table>
</form>

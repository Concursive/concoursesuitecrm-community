<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="modifyOpp" action="ExternalContactsOpps.do?command=UpdateOpp&contactId=<%= ContactDetails.getId() %>&auto-populate=true" method="post">
<dhv:evaluate exp="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do">Contacts</a> > 
<a href="ExternalContacts.do?command=SearchContacts">Search Results</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>">Contact Details</a> >
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	  <a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>">Opportunities</a> >
  <%}%>
<%} else {%>
<a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>">Opportunities</a> >
<a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>">Opportunity Details</a> >
<%}%>
Modify Opportunity
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%@ include file="contact_details_header_include.jsp" %>
<% String param1 = "id=" + ContactDetails.getId(); 
   String param2 = addLinkParams(request, "popup|popupType|actionId"); %>
<dhv:container name="contacts" selected="opportunities" param="<%= param1 %>" appendToUrl="<%= param2 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <input type="hidden" name="headerId" value="<%= opportunityHeader.getId() %>">
      <input type="hidden" name="modified" value="<%= opportunityHeader.getModified() %>">
<% if (request.getParameter("return") != null) {%>
      <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
      <input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>
<br>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><%= opportunityHeader.getDescription() %></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Description
    </td>
    <td>
      <input type="text" size="50" name="description" value="<%= toHtmlValue(opportunityHeader.getDescription()) %>">
      <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
    </td>
  </tr>
</table>
&nbsp;
<br>
<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>
<input type="hidden" name="dosubmit" value="true">
<%= addHiddenParams(request, "popup|popupType|actionId") %>
  </td>
  </tr>
  </table>
</form>

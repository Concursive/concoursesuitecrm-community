<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="modifyOpp" action="ExternalContactsOpps.do?command=UpdateOpp&contactId=<%= ContactDetails.getId() %>&auto-populate=true" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> > 
<a href="ExternalContacts.do?command=SearchContacts"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	  <a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> >
  <%}%>
<%} else {%>
<a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> >
<a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> >
<%}%>
<dhv:label name="accounts.accounts_contacts_opps_modify.ModifyOpportunity">Modify Opportunity</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="contacts" selected="opportunities" object="ContactDetails" param="<%= "id=" + ContactDetails.getId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
  <input type="hidden" name="headerId" value="<%= opportunityHeader.getId() %>">
  <input type="hidden" name="modified" value="<%= opportunityHeader.getModified() %>">
  <% if (request.getParameter("return") != null) {%>
    <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
  <%}%>
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="this.form.dosubmit.value='true';">
  <% if (request.getParameter("return") != null) {%>
    <% if (request.getParameter("return").equals("list")) {%>
        <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
    <%}%>
  <%} else {%>
        <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
  <%}%>
  <br />
  <dhv:formMessage />
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><%= opportunityHeader.getDescription() %></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="description" value="<%= toHtmlValue(opportunityHeader.getDescription()) %>">
        <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
      </td>
    </tr>
  </table>
  &nbsp;
  <br>
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>
  <input type="hidden" name="dosubmit" value="true">
  <%= addHiddenParams(request, "popup|popupType|actionId") %>
</dhv:container>
</form>

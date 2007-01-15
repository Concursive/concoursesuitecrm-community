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
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.HtmlSelect" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="relationshipTypeSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="objectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="objectSubSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do"><dhv:label name="Contacts" mainMenuItem="true">Contacts</dhv:label></a> >
<a href="ExternalContacts.do?command=SearchContacts"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<a href="ExternalContactsPrototype.do?module=ExternalContacts&include=contacts/companydirectory_relationships_view.jsp&contactId=<%=ContactDetails.getId()%>"><dhv:label name="accounts.accounts_relationships_add.Relationships">Relationships</dhv:label></a> >
<dhv:label name="contact.buildRelationship">Build Relationship</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="contacts" selected="relationships" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <%= toHtml(ContactDetails.getNameFull()) %> <dhv:label name="contact.subjectIsAAn">(Subject) is a/an:</dhv:label> <%= relationshipTypeSelect.getHtml() %> (<dhv:label name="accounts.accounts_relationships_add.RelationshipType">Relationship Type</dhv:label>)<br>
  &nbsp;<br>
  &nbsp;&nbsp;&nbsp;&nbsp;<%= objectSelect.getHtml() %> <dhv:label name="contact.objectType.brackets">(Object Type)</dhv:label><br>
  &nbsp;<br>
  &nbsp;&nbsp;&nbsp;&nbsp;<%= objectSubSelect.getHtml() %> <dhv:label name="contact.objectSubType.brackets">(Object Sub-Type)</dhv:label><br>
  &nbsp;<br>
  <dhv:label name="contact.chooseSpecificObject.colon">Choose specific object:</dhv:label><br />
  <iframe src="ExternalContactsPrototype.do?include=companydirectory_relationships_viewopportunities.jsp&inline=true" frameborder="0" <dhv:browser id="ie" include="false">width="100%" height="150"</dhv:browser> <dhv:browser id="ie">style="border: 1px solid #cccccc; width: 100%; height: 200;"</dhv:browser>>
    <dhv:label name="contact.browserDoesNotSupportView">browser doesn't support this view</dhv:label>
  </iframe>
  <input type="button" value="<dhv:label name="global.button.save">Save</dhv:label>" onclick="window.location.href='ExternalContactsPrototype.do?module=ExternalContacts&include=contacts/companydirectory_relationships_view.jsp&contactId=<%= ContactDetails.getId() %>'">
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onclick="window.location.href='ExternalContactsPrototype.do?module=ExternalContacts&include=contacts/companydirectory_relationships_view.jsp&contactId=<%= ContactDetails.getId() %>'">
</dhv:container>

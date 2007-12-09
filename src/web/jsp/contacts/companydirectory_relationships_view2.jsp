<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="howDirectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do"><dhv:label name="Contacts" mainMenuItem="true">Contacts</dhv:label></a> > 
<a href="ExternalContacts.do?command=SearchContacts"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<dhv:label name="accounts.accounts_relationships_add.Relationships">Relationships</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="contacts" selected="relationships" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:permission name="demo-add"><a href="ExternalContactsPrototype.do?module=ExternalContacts&include=contacts/companydirectory_relationships_add.jsp&contactId=<%= ContactDetails.getId() %>">Build New Relationship</a></dhv:permission><br>
  &nbsp;<br>
  <input type="BUTTON" value="<dhv:label name="button.relationshipWith">Relationship with...</dhv:label>" onclick="javascript:window.location.href='ExternalContactsPrototype.do?module=ExternalContacts&include=contacts/companydirectory_relationships_view.jsp&contactId=<%= ContactDetails.getId() %>'">
  <input type="BUTTON" value="<dhv:label name="button.reachedBy">...reached by</dhv:label>" onclick="javascript:window.location.href='ExternalContactsPrototype.do?module=ExternalContacts&include=contacts/companydirectory_relationships_view2.jsp&contactId=<%= ContactDetails.getId() %>'">
  <input type="BUTTON" value="<dhv:label name="contact.buildGroup">Build Group</dhv:label>" onclick=""><br>
  &nbsp;<br>
  <dhv:label name="contact.howDirect.question">How Direct?</dhv:label> <%= howDirectSelect.getHtml() %><br>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th valign="center" align="left">
        <strong><dhv:label name="accounts.accounts_relationships_add.Relationships">Relationships</dhv:label></strong>
      </th>
      <th valign="center" align="left">
        <strong><dhv:label name="contact.index">Index</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td width="100%" valign="center" class="row1">
        <dhv:label name="contact.name.spouseTo.text" param='<%= "linkStart=<a href=\"test\">|linkEnd=</a>|contact.fullName="+toHtml(ContactDetails.getNameFull()) %>'><a href="#"><dhv:label name="contacts.name">Name</dhv:label></a> (Spouse) to <%= toHtml(ContactDetails.getNameFull()) %></dhv:label>
      </td>
      <td width="10" valign="center" align="right" nowrap class="row1">
        1.00
      </td>
    </tr>
    <tr class="row2">
      <td width="100%" valign="center">
        <dhv:label name="contact.name.friendTo.text" param='<%= "linkStart=<a href=\"test\">|linkEnd=</a>|contact.fullName="+toHtml(ContactDetails.getNameFull()) %>'><a href="#"><dhv:label name="contacts.name">Name</dhv:label></a> (Friend) to <%= toHtml(ContactDetails.getNameFull()) %></dhv:label>
      </td>
      <td width="10" valign="center" align="right" nowrap>
        .90
      </td>
    </tr>
    <tr class="row1">
      <td width="100%" valign="center">
        <dhv:label name="contact.name.coworkerTo.text" param='<%= "linkStart=<a href=\"test\">|linkEnd=</a>|contact.fullName="+toHtml(ContactDetails.getNameFull()) %>'><a href="#"><dhv:label name="contacts.name">Name</dhv:label></a> (Co-Worker) to <%= toHtml(ContactDetails.getNameFull()) %></dhv:label>
      </td>
      <td width="10" valign="center" align="right" nowrap>
        .60
      </td>
    </tr>
  </table>
</dhv:container>

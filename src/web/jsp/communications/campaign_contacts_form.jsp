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
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,org.aspcfs.modules.contacts.base.Contact,org.aspcfs.modules.contacts.base.ContactList" %>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="CampaignGroupsContactsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<form>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="3">
      <strong><dhv:label name="campaign.listOfContacts">List of Contacts</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <th width="8">&nbsp;</th>
    <th width="50%" nowrap>
      <dhv:label name="contacts.name">Name</dhv:label>
    </th>
    <th width="50%" nowrap>
      <dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label>
    </th>
  </tr>
<%
	Iterator j = ContactList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
		int count = 0;
	  while (j.hasNext()) {
			count++;		
			rowid = (rowid != 1?1:2);
      Contact thisContact = (Contact)j.next();
%>      
  <tr>
    <td align="center" nowrap>
      <a href="javascript:window.opener.addValueFromChild('<%=thisContact.getId()%>', '<%=thisContact.getNameLastFirst()%>');javascript:window.focus();"><dhv:label name="button.add">Add</dhv:label></a>
    </td>
    <td nowrap>
      <%= toHtml(thisContact.getNameLastFirst()) %>
    </td>
    <td nowrap>
      <%= toHtml(thisContact.getAffiliation()) %>
    </td>
  </tr>
  <%}%>
</table>
<br>
<dhv:pagedListControl object="CampaignGroupsContactsInfo" tdClass="row1"/>
<%} else {%>
  <tr class="containerBody">
    <td colspan="3">
      <dhv:label name="campaign.noContactsMatchedQuery">No contacts matched query.</dhv:label>
    </td>
  </tr>
</table>
<%}%>
<p>
<input type="button" value="<dhv:label name="button.close">Close</dhv:label>" onClick="javascript:window.close();">
</form>

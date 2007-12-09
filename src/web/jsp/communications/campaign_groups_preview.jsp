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
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,org.aspcfs.modules.contacts.base.Contact" %>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="CampaignGroupsPreviewInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="scl" class="org.aspcfs.modules.communications.base.SearchCriteriaList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<dhv:evaluate if='<%= !"true".equals(request.getParameter("popup")) %>'>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<a href="CampaignManagerGroup.do?command=View"><dhv:label name="campaign.viewGroups">View Groups</dhv:label></a> >
<a href="CampaignManagerGroup.do?command=Details&id=<%= request.getAttribute("id") %>"><dhv:label name="campaign.groupDetails">Group Details</dhv:label></a> >
<dhv:label name="campaign.contactList">Contact List</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="button" name="cmd" value="<dhv:label name="campaign.backToCriteria">Back to Criteria</dhv:label>" onClick="window.location.href='CampaignManagerGroup.do?command=Details&id=<%= request.getAttribute("id") %>&return=<%= request.getParameter("return") %>'">
</dhv:evaluate>
<dhv:pagedListStatus object="CampaignGroupsPreviewInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="4">
      <strong><%= scl.getGroupName() %></strong>
    </th>
  </tr>
  <tr>
    <th>
      &nbsp;
    </th>
    <th width="33%">
      <dhv:label name="contacts.name">Name</dhv:label>
    </th>
    <th width="33%">
      <dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label>
    </th>
    <th width="34%">
      <dhv:label name="accounts.accounts_add.Email">Email</dhv:label>
    </th>
  </tr>
<%
	Iterator j = ContactList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
		int count = CampaignGroupsPreviewInfo.getCurrentOffset();
	  while (j.hasNext()) {
			count++;		
			rowid = (rowid != 1?1:2);
      Contact thisContact = (Contact)j.next();
%>      
  <tr class="row<%= rowid %>">
    <td align="right" nowrap>
      <%= count %>.
    </td>
    <td nowrap>
      <%= toHtml(thisContact.getNameFull()) %>
    </td>
    <td>
      <%= toHtml(thisContact.getAffiliation()) %>
    </td>
    <td>
      <%= toHtml(thisContact.getPrimaryEmailAddress()) %>
    </td>
  </tr>
  <%}%>
</table>
<br>
<dhv:pagedListControl object="CampaignGroupsPreviewInfo" tdClass="row1"/>
<%} else {%>
  <tr bgcolor="white">
    <td colspan="4">
      <dhv:evaluate if='<%= request.getAttribute("textError") != null && !"".equals((String)request.getAttribute("textError")) %>'>
        <%= toHtml((String) request.getAttribute("errorString")) %><br />
        <%= showAttribute(request, "textError") %>
      </dhv:evaluate>
      <dhv:evaluate if='<%= request.getAttribute("textError") == null && "".equals((String) request.getAttribute("textError")) %>'>
        <dhv:label name="campaign.noContactsMatchedQuery">No contacts matched query.</dhv:label>
      </dhv:evaluate>
    </td>
  </tr>
</table>
<%}%>
<br>
<dhv:evaluate if='<%= !"true".equals(request.getParameter("popup")) %>'>
<input type="button" name="cmd" value="<dhv:label name="campaign.backToCriteria">Back to Criteria</dhv:label>" onClick="window.location.href='CampaignManagerGroup.do?command=Details&id=<%= request.getAttribute("id") %>&return=<%= request.getParameter("return") %>'">
</dhv:evaluate>
<dhv:evaluate if='<%= "true".equals(request.getParameter("popup")) %>'>
<input type="button" name="cmd" value="<dhv:label name="button.close">Close</dhv:label>" onClick="window.close()">
</dhv:evaluate>

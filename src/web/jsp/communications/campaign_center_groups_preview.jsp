<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,org.aspcfs.modules.contacts.base.Contact,org.aspcfs.utils.web.CustomForm" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="SCL" class="org.aspcfs.modules.communications.base.SearchCriteriaList" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="CampaignCenterPreviewInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/div.js"></script>
<script language="JavaScript">
  function toggleRecipient(id) {
    //Send request to iframe
    var url = "CampaignManager.do?command=ToggleRecipient&scl=<%= SCL.getId() %>&id=<%= Campaign.getId() %>&contactId=" + id + "&popup=true";
    window.frames['server_commands'].location.href=url;
    //Returning iframe will set the change+id to the new value (Yes/No)
  }
</script>
<table cellpadding="4" cellspacing="0" width="100%" style="border: 1px solid #000;">
  <tr class="containerHeader">
    <td style="border-bottom: 1px solid #000;">
      <strong>Group Preview: </strong><%= toHtml(SCL.getGroupName()) %>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <ul>
        <li>A recipient can be removed from this campaign</li>
        <li>The removed recipient will not be deleted from the group or from other campaigns</li>
      </ul>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="5">
      <strong>Contacts in this group</strong>
    </th>
  </tr>
  <tr>
    <th style="text-align: right;">
      Count
    </th>
    <th width="33%">
      Name
    </th>
    <th width="33%">
      Company
    </th>
    <th width="34%">
      Email
    </th>
    <th align="center">
      Included
    </th>
  </tr>
<%
	Iterator j = ContactList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
		int count = CampaignCenterPreviewInfo.getCurrentOffset();
	  while (j.hasNext()) {
			count++;		
			rowid = (rowid != 1?1:2);
      Contact thisContact = (Contact)j.next();
%>      
  <tr class="containerBody">
    <td align="right" nowrap>
      <%= count %>
    </td>
    <td>
      <%= toHtml(thisContact.getNameLastFirst()) %>
    </td>
    <td>
      <%= toHtml(thisContact.getAffiliation()) %>
    </td>
    <td nowrap>
      <%= toHtml(thisContact.getEmailAddress("Business")) %>
    </td>
    <td align="center" nowrap>
      <div id="change<%= thisContact.getId() %>">
        <dhv:permission name="campaign-campaigns-edit"><a href="javascript:toggleRecipient(<%= thisContact.getId()%>)"></dhv:permission><%= (thisContact.excludedFromCampaign()? "No" : "Yes") %><dhv:permission name="campaign-campaigns-edit"></a></dhv:permission>
      </div>
    </td>
  </tr>
  <%}%>
  <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
  <input type="hidden" name="id" value="<%= Campaign.getId() %>">
  <input type="hidden" name="scl" value="<%= SCL.getId() %>">
</table>
<br>
<dhv:pagedListControl object="CampaignCenterPreviewInfo"/>
<%} else {%>
  <tr class="containerBody">
    <td colspan="5">
      No contacts matched query.
    </td>
  </tr>
</table>
<%}%>
  </td>
  </tr>
</table>
<input type="button" value="Close Window" onClick="javascript:window.close()">

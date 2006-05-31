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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.communications.base.*,org.aspcfs.modules.contacts.base.Contact" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="RecipientList" class="org.aspcfs.modules.communications.base.RecipientList" scope="request"/>
<jsp:useBean id="CampaignDashboardRecipientInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<script type="text/javascript">
function reopen() {
 window.location.href='CampaignManager.do?command=PreviewRecipients&id=<%= Campaign.getId() %>';
}
function addRecipient() {
  popContactsListSingle('tempId','contactId','<%= User.getUserRecord().getSiteId() == -1?"includeAllSites=true&siteId=-1":"mySiteOnly=true&siteId="+User.getUserRecord().getSiteId() %>&listView=accountcontacts&reset=true&recipient=true&hiddensource=recipients<%= request.getParameter("params") != null ?  "&" + request.getParameter("params") + "" : ""%>');
}
function continueAddRecipient(contactId, allowDuplicates) {
  var url = "CampaignManager.do?command=AddRecipient&id=<%= Campaign.getId() %>&contactId="+contactId+'&allowDuplicates=true';
  window.frames['server_commands'].location.href=url;
}
</script>
<input type="hidden" name="tempId" id="tempId" value="-1"/>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<a href="CampaignManager.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<a href="CampaignManager.do?command=Details&id=<%=Campaign.getId()%>"><dhv:label name="campaign.campaignDetails">Campaign Details</dhv:label></a> >
<dhv:label name="Recipients">Recipients</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="communications" selected="recipients" object="Campaign" param="<%= "id=" + Campaign.getId() %>">
  <dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
	<table cellpadding="4" cellspacing="0" width="100%" class="empty">
  <tr><td nowrap><a href="javascript:addRecipient();"><dhv:label name="campaigns.addRecipient">Add Recipient</dhv:label></a></td>
  <td width="100%"><center><dhv:pagedListAlphabeticalLinks object="CampaignDashboardRecipientInfo"/></dhv:include></center></td>
  </tr></table>
  <dhv:pagedListStatus title="<%= showAttribute(request, "actionError") %>" object="CampaignDashboardRecipientInfo"/>
	<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
	  <tr>
	    <th colspan="6">
	      <strong><dhv:label name="campaign.listOfRecipients">List of Recipients</dhv:label></strong>
	    </th>     
	  </tr>
	  <tr>
	    <th width="24" align="center" nowrap>
	      <dhv:label name="campaign.count">Count</dhv:label>
	    </th>
	    <th>
	      <a href="CampaignManager.do?command=PreviewRecipients&id=<%= Campaign.getId() %>&column=c.namelast"><strong><dhv:label name="contacts.name">Name</dhv:label></strong></a>
        <%= CampaignDashboardRecipientInfo.getSortIcon("c.namelast") %>
	    </th>
	    <th width="100%">
	      <dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label>
	    </th>
	    <th align="center" nowrap>
	      <a href="CampaignManager.do?command=PreviewRecipients&id=<%= Campaign.getId() %>&column=r.sent_date"><strong><dhv:label name="campaign.sentDate">Sent Date</dhv:label></strong></a>
        <%= CampaignDashboardRecipientInfo.getSortIcon("r.sent_date") %>
	    </th>
	    <th align="center" nowrap>
	      <a href="CampaignManager.do?command=PreviewRecipients&id=<%= Campaign.getId() %>&column=r.status"><strong><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></strong></a>
        <%= CampaignDashboardRecipientInfo.getSortIcon("r.status") %>
	    </th>
	  </tr>
	<%
		Iterator j = RecipientList.iterator();
		if ( j.hasNext() ) {
			int rowid = 0;
			int count = CampaignDashboardRecipientInfo.getCurrentOffset();
		  while (j.hasNext()) {
				count++;		
				rowid = (rowid != 1?1:2);
	      Recipient thisRecipient = (Recipient)j.next();
	      Contact thisContact = thisRecipient.getContact();
	%>      
	  <tr class="row<%= rowid %>">
	    <td align="right" nowrap>
	      <%= count %>
	    </td>
	    <td nowrap>
	      <%= toHtml(thisContact.getNameFull()) %>
	    </td>
	    <td width="100%">
	      <%= toHtml(thisContact.getCompany()) %>
	    </td>
	    <td align="center" nowrap>
        <zeroio:tz timestamp="<%= thisRecipient.getSentDate() %>" dateOnly="true" default="&nbsp;"/>
	    </td>
	    <td align="center" nowrap>
	      <%= toHtml(thisRecipient.getStatus()) %>
	    </td>
	  </tr>
	  <%}%>
	</table>
	<br>
	<dhv:pagedListControl object="CampaignDashboardRecipientInfo" />
	<%} else {%>
	  <tr class="containerBody">
	    <td colspan="6">
	      <dhv:label name="campaign.noRecipientsFound">No recipients found.</dhv:label>
	    </td>
	  </tr>
	</table>
	<%}%>
</dhv:container>
<iframe src="../empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Campaign" class="com.darkhorseventures.cfsbase.Campaign" scope="request"/>
<jsp:useBean id="RecipientList" class="com.darkhorseventures.cfsbase.RecipientList" scope="request"/>
<jsp:useBean id="CampaignDashboardRecipientInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<a href="/CampaignManager.do?command=Dashboard">Back to Dashboard</a>
<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2" valign="center" align="left">
      <strong>Campaign Details</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Name
    </td>
    <td width="100%">
      <%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Description
    </td>
    <td width="100%">
      <%= toHtml(Campaign.getDescription()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Groups
    </td>
    <td width="100%">
      <font color='green'><%= Campaign.getGroupCount() %> selected</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Message
    </td>
    <td width="100%">
      <font color='green'><%= Campaign.getMessageName() %></font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Schedule
    </td>
    <td width="100%">
      <font color='green'>Scheduled to run on <%= Campaign.getActiveDateString() %></font>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel">
      Entered
    </td>
    <td width="100%">
      <dhv:username id="<%= Campaign.getEnteredBy() %>" /> - <%= Campaign.getEnteredString() %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel">
      Modified
    </td>
    <td width="100%">
      <dhv:username id="<%= Campaign.getModifiedBy() %>" /> - <%= Campaign.getModifiedString() %>
    </td>
  </tr>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="6" valign="center" align="left">
      <strong>List of Recipients</strong>
    </td>     
  </tr>
  <tr class="title">
    <td width="8" align="right" nowrap>
      Count
    </td>
    <td nowrap>
      Name
    </td>
    <td nowrap width="100%">
      Company
    </td>
    <td nowrap>
      Sent Date
    </td>
    <td nowrap>
      Reply Date
    </td>
		<td nowrap>
      Status
    </td>
  </tr>
<%
	Iterator j = RecipientList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
		int count = CampaignDashboardRecipientInfo.getCurrentOffset();
		
	  while (j.hasNext()) {
			count++;		
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
		
    Recipient thisRecipient = (Recipient)j.next();
		Contact thisContact = thisRecipient.getContact();
%>      
  <tr>
    <td align="right" nowrap>
      <%= count %>
    </td>
    <td width="50%" nowrap>
      <%= toHtml(thisContact.getNameLast()) %>, <%= toHtml(thisContact.getNameFirst()) %>
    </td>
    <td width="50%" nowrap>
      <%= toHtml(thisContact.getCompany()) %>
    </td>
    <td nowrap>
      <%= toHtml(thisRecipient.getSentDateString()) %>
    </td>
    <td nowrap>
      <%= toHtml(thisRecipient.getReplyDateString()) %>
    </td>
		<td nowrap>
		  <%= toHtml(thisRecipient.getStatus()) %>
	  </td>
  </tr>
  <%}%>
</table>
<br>
[<%= CampaignDashboardRecipientInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %> <%= CampaignDashboardRecipientInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>] <%= CampaignDashboardRecipientInfo.getNumericalPageLinks() %>
<%} else {%>
  <tr bgcolor="white">
    <td colspan="6" valign="center">
      No recipients found.
    </td>
  </tr>
</table>
<%}%>

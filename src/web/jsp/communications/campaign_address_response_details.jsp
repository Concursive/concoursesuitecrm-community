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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.lang.String, java.text.DateFormat,org.aspcfs.modules.communications.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.contacts.base.Contact" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="YesResponseDetailsListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="yesAddressUpdateResponseList" class="org.aspcfs.modules.communications.base.SurveyResponseList" scope="request"/>
<jsp:useBean id="NoResponseDetailsListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="noAddressUpdateResponseList" class="org.aspcfs.modules.communications.base.SurveyResponseList" scope="request"/>
<jsp:useBean id="CampaignRecipientInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="recipientList" class="org.aspcfs.modules.communications.base.RecipientList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript">
  function requestInformation(section, id){
    var url = "CampaignManager.do?command=AddressUpdateResponseDetails&section="+ section.value +"&id=" + id;
    window.frames['server_commands'].location.href=url;
  }
</script>
<%@ include file="../initPage.jsp" %>
<table cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td valign="top">
      <dhv:label name="campaign.campaign.colon" param='<%= "name="+toHtml(Campaign.getName()) %>'><strong>Campaign:</strong> <%= toHtml(Campaign.getName()) %></dhv:label>
     </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td>
<dhv:evaluate if='<%= (String.valueOf(SurveyResponse.ADDRESS_UPDATED)).equals(request.getAttribute("section")) %>'>
<table cellpadding="4" cellspacing="0" width="100%" border="0">
<tr align="left">
  <td>
    <h3><img src="images/icons/stock_form-open-in-design-mode-16.gif" border="0" align="absmiddle" />
    &nbsp;<dhv:label name="campaign.recipientUpdatedAddress.text">The following recipients updated their address</dhv:label></h3>
  </td>
</tr>
</table>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="YesResponseDetailsListInfo"/></center></dhv:include>
<dhv:pagedListStatus title='<%= showAttribute(request, "actionError") %>' object="YesResponseDetailsListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="20%" nowrap>
      <strong><dhv:label name="contacts.name">Name</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="accounts.accounts_documents_details.Submitted">Submitted</dhv:label></strong>
    </th>
    <th nowrap><strong><dhv:label name="campaign.ipAddress">IP Address</dhv:label></strong></th>
    <th nowrap><strong><dhv:label name="documents.team.emailAddress">Email Address</dhv:label></strong></th>
    <th nowrap><strong><dhv:label name="quotes.phoneNumber">Phone Number</dhv:label></strong></th>
  </tr>
<%
  Iterator j = yesAddressUpdateResponseList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      SurveyResponse thisResponse = (SurveyResponse) j.next();
%>      
    <tr class="row<%= rowid %>">
      <td width="30" nowrap>
        <%= toHtml(thisResponse.getContact().getNameLastFirst()) %>
      </td>
      <td nowrap>
        <zeroio:tz timestamp="<%= thisResponse.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
      </td>
      <td>
        <%= toHtml(thisResponse.getIpAddress()) %>&nbsp;
      </td>
      <td nowrap>
        <%= thisResponse.getContact().getPrimaryEmailAddress() %>&nbsp;
      </td>
      <td nowrap>
        <%= thisResponse.getContact().getPrimaryPhoneNumber() %>&nbsp;
      </td>
    </tr>
<%}%>
  </table>
  <br>
  <dhv:pagedListControl object="YesResponseDetailsListInfo"/>
<%} else {%>
    <tr class="containerBody">
      <td colspan="7">
        <dhv:label name="campaign.noEntriesFound">No entries found</dhv:label>
      </td>
    </tr>
  </table>
<%}%>
</dhv:evaluate>
<dhv:evaluate if='<%= (String.valueOf(SurveyResponse.ADDRESS_VALID)).equals(request.getAttribute("section")) %>'>
<table cellpadding="4" cellspacing="0" width="100%" border="0">
<tr align="left">
  <td>
    <h3><img src="images/icons/stock_form-open-in-design-mode-16.gif" border="0" align="absmiddle" />
    &nbsp;<dhv:label name="campaign.recipientsValidAddress.text">The following recipients found their address valid</dhv:label></h3>
  </td>
</tr>
</table>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="NoResponseDetailsListInfo"/></center></dhv:include>
<dhv:pagedListStatus title='<%= showAttribute(request, "actionError") %>' object="NoResponseDetailsListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="20%" nowrap>
      <strong><dhv:label name="contacts.name">Name</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="accounts.accounts_documents_details.Submitted">Submitted</dhv:label></strong>
    </th>
    <th nowrap><strong><dhv:label name="campaign.ipAddress">IP Address</dhv:label></strong></th>
    <th nowrap><strong><dhv:label name="documents.team.emailAddress">Email Address</dhv:label></strong></th>
    <th nowrap><strong><dhv:label name="quotes.phoneNumber">Phone Number</dhv:label></strong></th>
  </tr>
<%
  Iterator j = noAddressUpdateResponseList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      SurveyResponse thisResponse = (SurveyResponse) j.next();
%>      
    <tr class="row<%= rowid %>">
      <td width="30" nowrap>
        <%= toHtml(thisResponse.getContact().getNameLastFirst()) %>
      </td>
      <td nowrap>
        <zeroio:tz timestamp="<%= thisResponse.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
      </td>
      <td>
        <%= toHtml(thisResponse.getIpAddress()) %>&nbsp;
      </td>
      <td nowrap>
        <%= thisResponse.getContact().getPrimaryEmailAddress() %>&nbsp;
      </td>
      <td nowrap>
        <%= thisResponse.getContact().getPrimaryPhoneNumber() %>&nbsp;
      </td>
    </tr>
<%}%>
  </table>
  <br>
  <dhv:pagedListControl object="NoResponseDetailsListInfo"/>
<%} else {%>
    <tr class="containerBody">
      <td colspan="7">
        <dhv:label name="campaign.noEntriesFound">No entries found</dhv:label>
      </td>
    </tr>
  </table>
<%}%>
</dhv:evaluate>
<dhv:evaluate if='<%= (String.valueOf(SurveyResponse.ADDRESS_NO_RESPONSE)).equals(request.getAttribute("section")) %>'>
<table cellpadding="4" cellspacing="0" width="100%" border="0">
<tr align="left">
  <td>
    <h3><img src="images/icons/stock_form-open-in-design-mode-16.gif" border="0" align="absmiddle" />
    &nbsp;The following recipients did not respond.</h3>
  </td>
</tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
    <center><dhv:pagedListAlphabeticalLinks object="CampaignRecipientInfo"/></center></dhv:include>
    <dhv:pagedListStatus title='<%= showAttribute(request, "actionError") %>' object="CampaignRecipientInfo"/>
	<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
	  <tr>
	    <th width="70%">
	      <dhv:label name="contacts.name">Name</dhv:label>
	    </th>
	    <th align="center" nowrap>
	      Sent Date
	    </th>
	    <th align="center" nowrap>
	      <dhv:label name="accounts.accountasset_include.Status">Status</dhv:label>
	    </th>
	  </tr>
	<%
		Iterator j = recipientList.iterator();
		if ( j.hasNext() ) {
			int rowid = 0;
		  while (j.hasNext()) {
				rowid = (rowid != 1?1:2);
	      Recipient thisRecipient = (Recipient)j.next();
	      Contact thisContact = thisRecipient.getContact();
	%>      
	  <tr class="row<%= rowid %>">
	    <td nowrap>
	      <%= toHtml(thisContact.getNameFull()) %>
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
	<dhv:pagedListControl object="CampaignRecipientInfo" />
	<%} else {%>
	  <tr class="containerBody">
	    <td colspan="6">
	      No recipients found (or) all recipients have responded
	    </td>
	  </tr>
	</table>
	<%}%>
</dhv:evaluate>  
</td>
</tr>
</table>


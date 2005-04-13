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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.communications.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="SurveyResponseListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SurveyResponseList" class="org.aspcfs.modules.communications.base.SurveyResponseList" scope="request"/>
<jsp:useBean id="AddressUpdateResponseListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AddressUpdateResponseList" class="org.aspcfs.modules.communications.base.SurveyResponseList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<a href="CampaignManager.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<a href="CampaignManager.do?command=Details&id=<%=Campaign.getId()%>"><dhv:label name="campaign.campaignDetails">Campaign Details</dhv:label></a> >
<dhv:label name="campaign.response">Response</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="communications" selected="response" object="Campaign" param="<%= "id=" + Campaign.getId() %>">
  <dhv:evaluate if="<%= !Campaign.getHasSurvey() && !Campaign.getHasAddressRequest()%>">
    <table cellpadding="4" cellspacing="0" width="100%" border="0">
      <tr>
        <td>
            <strong><dhv:label name="campaign.noSurveyOrAddressUpdateRequest.text">This campaign does not have a survey or address update request.</dhv:label></strong>
        </td>
      </tr>
    </table>
  </dhv:evaluate>
  <dhv:evaluate if="<%= Campaign.getHasSurvey() %>">
  <table cellpadding="4" cellspacing="0" width="100%" border="0">
  <tr>
    <td>
      <h3><img src="images/icons/stock_form-open-in-design-mode-16.gif" border="0" align="absmiddle" />
      &nbsp;<dhv:label name="campaign.surveyResponses">Survey Responses</dhv:label></h3>
    </td>
  </tr>
  </table>
  <br />
  <dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
  <center><dhv:pagedListAlphabeticalLinks object="SurveyResponseListInfo"/></center></dhv:include>
  <dhv:pagedListStatus title="<%= showAttribute(request, "actionError") %>" object="SurveyResponseListInfo"/>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th width="20%" nowrap>
        <strong><a href="CampaignManager.do?command=ViewResponse&id=<%= Campaign.getId() %>&column=c.namelast"><dhv:label name="contacts.name">Name</dhv:label></a></strong>
        <%= SurveyResponseListInfo.getSortIcon("c.namelast") %>
      </th>
      <th nowrap>
        <strong><a href="CampaignManager.do?command=ViewResponse&id=<%= Campaign.getId() %>&column=sr.entered"><dhv:label name="accounts.accounts_documents_details.Submitted">Submitted</dhv:label></a></strong>
        <%= SurveyResponseListInfo.getSortIcon("sr.entered") %>
      </th>
      <th nowrap><strong><dhv:label name="campaign.ipAddress">IP Address</dhv:label></strong></th>
      <th nowrap><strong><dhv:label name="documents.team.emailAddress">Email Address</dhv:label></strong></th>
      <th nowrap><strong><dhv:label name="quotes.phoneNumber">Phone Number</dhv:label></strong></th>
    </tr>
  <%
    Iterator j = SurveyResponseList.iterator();
    if ( j.hasNext() ) {
      int rowid = 0;
      while (j.hasNext()) {
        rowid = (rowid != 1?1:2);
        SurveyResponse thisResponse = (SurveyResponse) j.next();
  %>
      <tr class="row<%= rowid %>">
        <td width="30" nowrap>
          <a href="CampaignManager.do?command=ResponseDetails&id=<%= Campaign.getId() %>&contactId=<%= thisResponse.getContactId() %>&responseId=<%= thisResponse.getId() %>"><%= toHtml(thisResponse.getContact().getNameLastFirst()) %></a>
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
    <dhv:pagedListControl object="SurveyResponseListInfo"/>
  <%} else {%>
      <tr class="containerBody">
        <td colspan="7">
          <dhv:label name="campaign.noResponseFound">No Response Found.</dhv:label>
        </td>
      </tr>
    </table>
  <%}%>
  <br />
  </dhv:evaluate>
  <%-- address request table --%>
  <dhv:evaluate if="<%= Campaign.getHasAddressRequest() %>">
  <table cellpadding="4" cellspacing="0" width="100%" border="0">
  <tr>
    <td>
      <h3><img src="images/icons/stock_form-open-in-design-mode-16.gif" border="0" align="absmiddle" />
      &nbsp;<dhv:label name="campaign.addressUpdateResponses">Address Update Responses</dhv:label></h3>
    </td>
  </tr>
  </table>
  <dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
  <center><dhv:pagedListAlphabeticalLinks object="AddressUpdateResponseListInfo"/></center></dhv:include>
  <dhv:pagedListStatus title="<%= showAttribute(request, "actionError") %>" object="AddressUpdateResponseListInfo"/>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th width="20%" nowrap>
        <strong><a href="CampaignManager.do?command=ViewResponse&id=<%= Campaign.getId() %>&column=c.namelast"><dhv:label name="contacts.name">Name</dhv:label></a></strong>
        <%= AddressUpdateResponseListInfo.getSortIcon("c.namelast") %>
      </th>
      <th nowrap>
        <strong><a href="CampaignManager.do?command=ViewResponse&id=<%= Campaign.getId() %>&column=sr.entered"><dhv:label name="accounts.accounts_documents_details.Submitted">Submitted</dhv:label></a></strong>
        <%= AddressUpdateResponseListInfo.getSortIcon("sr.entered") %>
      </th>
      <th nowrap><strong><dhv:label name="campaign.ipAddress">IP Address</dhv:label></strong></th>
      <th nowrap><strong><dhv:label name="documents.team.emailAddress">Email Address</dhv:label></strong></th>
      <th nowrap><strong><dhv:label name="quotes.phoneNumber">Phone Number</dhv:label></strong></th>
    </tr>
  <%
    Iterator j = AddressUpdateResponseList.iterator();
    if ( j.hasNext() ) {
      int rowid = 0;
      while (j.hasNext()) {
        rowid = (rowid != 1?1:2);
        SurveyResponse thisResponse = (SurveyResponse) j.next();
  %>
      <tr class="row<%= rowid %>">
        <td width="30" nowrap>
          <a href="CampaignManager.do?command=ResponseDetails&id=<%= Campaign.getId() %>&contactId=<%= thisResponse.getContactId() %>&responseId=<%= thisResponse.getId() %>"><%= toHtml(thisResponse.getContact().getNameLastFirst()) %></a>
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
  <%  }  %>
    </table>
    <br />
    <dhv:pagedListControl object="AddressUpdateResponseListInfo"/>
  <%} else {%>
      <tr class="containerBody">
        <td colspan="7">
          <dhv:label name="campaign.noAddressUpdateResponseFound">No Address Update Response Found.</dhv:label>
        </td>
      </tr>
    </table>
  <%}%>
  </dhv:evaluate>
</dhv:container>

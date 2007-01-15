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
  <%--  Start details --%>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="documents.details.generalInformation">General Information</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="accounts.accountasset_include.ServiceContractNumber">Service Contract Number</dhv:label>
    </td>
    <td>
    <%=toHtml(ticketDetails.getServiceContractNumber())%>
    </td>
  </tr>
</table>
<br />
  <%
    if (activityDetails != null){
   %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="4">
	    <strong><dhv:label name="tickets.perDayDescription">Per Day Description of Service</dhv:label></strong>
	  </th>
  </tr>
  <tr bgcolor="#E8E8E8">
    <td nowrap>
      <b><dhv:label name="tickets.activityDate">Activity Date</dhv:label></b>
    </td>
    <td nowrap>
      <b><dhv:label name="reports.helpdesk.ticket.activity.travelTime">Travel Time</dhv:label></b><br />
      <b>[<dhv:label name="tickets.towardsContract">Towards Contract</dhv:label>:</b>
      <b>
        <% if(activityDetails.getTravelTowardsServiceContract()) {%>
          <dhv:label name="account.yes">Yes</dhv:label>
        <%} else {%>
          <dhv:label name="account.no">No</dhv:label>
        <%}%>
      </b>
      <b>]</b>
    </td>
    <td nowrap>
      <b><dhv:label name="reports.helpdesk.ticket.activity.laborTime">Labor Time</dhv:label></b><br />
      <b>[<dhv:label name="tickets.towardsContract">Towards Contract</dhv:label>:</b>
      <b>
        <% if(activityDetails.getLaborTowardsServiceContract()) {%>
          <dhv:label name="account.yes">Yes</dhv:label>
        <%} else {%>
          <dhv:label name="account.no">No</dhv:label>
        <%}%>
      </b>
      <b>]</b>
    </td>
    <td width="100%">
      <b><dhv:label name="reports.helpdesk.ticket.activity.descOfService">Description of Service</dhv:label></b>
    </td>
  </tr>
  <%
      Iterator inumber = activityDetails.getTicketPerDayDescriptionList().iterator();
      if(inumber.hasNext()){
      int icount = 0;
      while (inumber.hasNext()){
        TicketPerDayDescription thisDayDescription = (TicketPerDayDescription) inumber.next();
  %>
        <tr valign="top" class="containerBody">
          <td align="center" nowrap>
            <zeroio:tz timestamp="<%= thisDayDescription.getActivityDate() %>" dateOnly="true" timeZone="<%= thisDayDescription.getActivityDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
            <% if(!User.getTimeZone().equals(thisDayDescription.getActivityDateTimeZone())){%>
            <br>
            <zeroio:tz timestamp="<%= thisDayDescription.getActivityDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
            <% } %>
          <td align="right" nowrap>
            <dhv:label name="tickets.timeMeasureInHrsMin.text" param='<%= "hours="+thisDayDescription.getTravelHours()+"|minutes="+thisDayDescription.getTravelMinutes() %>'><%= thisDayDescription.getTravelHours() %> hrs &nbsp <%= thisDayDescription.getTravelMinutes() %> min</dhv:label>
          </td>
          <td align="right" nowrap>
            <dhv:label name="tickets.timeMeasureInHrsMin.text" param='<%= "hours="+thisDayDescription.getLaborHours()+"|minutes="+thisDayDescription.getLaborMinutes() %>'><%= thisDayDescription.getLaborHours() %> hrs &nbsp <%= thisDayDescription.getLaborMinutes() %> min</dhv:label>
          </td>
          <td>
            <%=toHtml(thisDayDescription.getDescriptionOfService())%>
          </td>
        </tr>
      <%}%>
      <tr class="containerBody">
          <td align="center" bgcolor="#E8E8E8">
            <strong><dhv:label name="accounts.accounts_contacts_listimports.Total">Total</dhv:label></strong>
          </td>
          <td align="right" bgcolor="#E8E8E8">
          <strong>
            <dhv:label name="tickets.timeMeasureInHrsMin.text" param='<%= "hours="+activityDetails.getTotalTravelHours()+"|minutes="+activityDetails.getTotalTravelMinutes() %>'><%= activityDetails.getTotalTravelHours() %> hrs &nbsp <%= activityDetails.getTotalTravelMinutes() %> min</dhv:label>
           </strong>
        </td>
        <td align="right" bgcolor="#E8E8E8">
          <strong>
            <dhv:label name="tickets.timeMeasureInHrsMin.text" param='<%= "hours="+activityDetails.getTotalLaborHours()+"|minutes="+activityDetails.getTotalLaborMinutes() %>'><%= activityDetails.getTotalLaborHours() %> hrs &nbsp <%= activityDetails.getTotalLaborMinutes() %> min</dhv:label>
          </strong>
        </td>
        <td bgcolor="#E8E8E8">&nbsp;</td>
      </tr>
      <%}else{ %>
     <tr class="containerBody"> 
          <td colspan="4">
            <dhv:label name="tickets.noActivitiesRecorded">No activities recorded.</dhv:label>
          </td>
     </tr>
     <%}%>
</table>
  <br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="tickets.additionalInformation">Additional Information</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="tickets.followupRequired.question">Follow-up Required?</dhv:label>
    </td>
    <td>
    <%if (activityDetails.getFollowUpRequired()){%>
      <dhv:label name="account.yes">Yes</dhv:label>
    <%}else{%>
      <dhv:label name="account.no">No</dhv:label>
    <%}%>
    </td>
  </tr>
  <dhv:evaluate if="<%= activityDetails.getAlertDate() != null %>">
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="accounts.accounts_add.AlertDate">Alert Date</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= activityDetails.getAlertDate() %>" dateOnly="true" timeZone="<%= activityDetails.getAlertDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% if(!User.getTimeZone().equals(activityDetails.getAlertDateTimeZone())){%>
        <br>
        <zeroio:tz timestamp="<%= activityDetails.getAlertDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } %>
      </td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(activityDetails.getFollowUpDescription()) %>">
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="reports.helpdesk.ticket.activity.followUpDesc">Follow-up Description</dhv:label>
      </td>
      <td>
        <%= toHtml(activityDetails.getFollowUpDescription()) %>
      </td>
    </tr>
  </dhv:evaluate>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="reports.helpdesk.ticket.activity.phoneResponseTime">Phone Response Time</dhv:label>
    </td>
    <td>
      <%=toHtml(activityDetails.getPhoneResponseTime())%>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="tickets.engineerResponseTime">Engineer Response Time</dhv:label>
    </td>
    <td>
      <%=toHtml(activityDetails.getEngineerResponseTime())%>
    </td>
  </tr>
</table>
  <%}%>


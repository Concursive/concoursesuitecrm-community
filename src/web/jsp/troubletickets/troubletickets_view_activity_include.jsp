<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
  <%--  Start details --%>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>General Information</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Service Contract Number
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
	    <strong>Per Day Description of Service</strong>
	  </th>
  </tr>
  <tr bgcolor="#E8E8E8">
    <td nowrap>
      <b>Activity Date</b>
    </td>
    <td nowrap>
      <b>Travel Time</b><br />
      <b>[Towards Contract:</b>
      <b><%= ((activityDetails.getTravelTowardsServiceContract()) ? "Yes" : "No") %></b>
      <b>]</b>
    </td>
    <td nowrap>
      <b>Labor Time</b><br />
      <b>[Towards Contract:</b>
      <b><%= ((activityDetails.getLaborTowardsServiceContract()) ? "Yes" : "No") %></b>
      <b>]</b>
    </td>
    <td width="100%">
      <b>Description of Service</b>
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
            <zeroio:tz timestamp="<%= thisDayDescription.getActivityDate() %>" dateOnly="true" timeZone="<%= thisDayDescription.getActivityDateTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
            <% if(!User.getTimeZone().equals(thisDayDescription.getActivityDateTimeZone())){%>
            <br>
            <zeroio:tz timestamp="<%= thisDayDescription.getActivityDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
            <% } %>
          <td align="right" nowrap>
            <%= thisDayDescription.getTravelHours() %> hrs &nbsp <%= thisDayDescription.getTravelMinutes() %> min 
          </td>
          <td align="right" nowrap>
            <%= thisDayDescription.getLaborHours() %> hrs &nbsp <%= thisDayDescription.getLaborMinutes() %> min 
          </td>
          <td>
            <%=toHtml(thisDayDescription.getDescriptionOfService())%>
          </td>
        </tr>
      <%}%>
      <tr class="containerBody">
          <td align="center" bgcolor="#E8E8E8">
            <strong>Total</strong>
          </td>
          <td align="right" bgcolor="#E8E8E8">
          <strong>
            <%= activityDetails.getTotalTravelHours() %>hrs &nbsp <%= activityDetails.getTotalTravelMinutes() %>min
           </strong>
        </td>
        <td align="right" bgcolor="#E8E8E8">
          <strong>
           <%= activityDetails.getTotalLaborHours() %>hrs &nbsp <%= activityDetails.getTotalLaborMinutes() %>min
          </strong>
        </td>
        <td bgcolor="#E8E8E8">&nbsp;</td>
      </tr>
      <%}else{ %>
     <tr class="containerBody"> 
          <td colspan="4">
            No activities recorded.
          </td>
     </tr>
     <%}%>
</table>
  <br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Additional Information</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Follow-up Required?
    </td>
    <td>
    <%if (activityDetails.getFollowUpRequired()){%>
      Yes
    <%}else{%>
      No
    <%}%>
    </td>
  </tr>
  <dhv:evaluate if="<%= activityDetails.getAlertDate() != null %>">
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        Alert Date
      </td>
      <td>
        <zeroio:tz timestamp="<%= activityDetails.getAlertDate() %>" dateOnly="true" timeZone="<%= activityDetails.getAlertDateTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
        <% if(!User.getTimeZone().equals(activityDetails.getAlertDateTimeZone())){%>
        <br>
        <zeroio:tz timestamp="<%= activityDetails.getAlertDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
        <% } %>
      </td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(activityDetails.getFollowUpDescription()) %>">
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        Follow-up Description
      </td>
      <td>
        <%= toHtml(activityDetails.getFollowUpDescription()) %>
      </td>
    </tr>
  </dhv:evaluate>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Phone Response Time
    </td>
    <td>
      <%=toHtml(activityDetails.getPhoneResponseTime())%>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Engineer Response Time
    </td>
    <td>
      <%=toHtml(activityDetails.getEngineerResponseTime())%>
    </td>
  </tr>
</table>
  <%}%>


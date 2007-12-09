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
<%-- draws the call events for a specific day --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="org.aspcfs.modules.contacts.base.Call,org.aspcfs.modules.base.PhoneNumber" %>
<%
  CallEventList callEventList = (CallEventList) thisDay.get(category);
%>
                            
<%-- include pending activities --%>
<dhv:evaluate if="<%= callEventList.getPendingCalls().size() > 0 %>">
<table border="0" id="pendingcalldetails<%= toFullDateString(thisDay.getDate()) %>" width="100%">
  <%-- title row --%>
  <tr>
    <td>&nbsp;</td>
    <td colspan="5" nowrap class="eventName">
      <img border="0" src="images/box-hold.gif" align="absmiddle" title="Activities" />
      <dhv:label name="myitems.pendingActivities">Pending Activities</dhv:label>
      (<%= callEventList.getPendingCalls().size() %>)
    </td>
  </tr>
  <%-- include call details --%>
  <%
    Iterator j = callEventList.getPendingCalls().iterator();
    if(j.hasNext()){
  %>
    <tr>
      <th>&nbsp;</th>
      <th class="weekSelector" nowrap>
        <strong><dhv:label name="calendar.due">Due</dhv:label></strong>
      </th>
      <th class="weekSelector" nowrap>
        <strong><dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label></strong>
      </th>
      <th class="weekSelector" nowrap>
        <strong><dhv:label name="accounts.accounts_add.Phone">Phone</dhv:label></strong>
      </th>
      <th class="weekSelector" width="100%">
        <strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong>
      </th>
      <th class="weekSelector" nowrap>
        <strong><dhv:label name="project.pri">Pri</dhv:label></strong>
      </th>
    </tr>
  <%  
      while(j.hasNext()){
      Call pendingCall = (Call) j.next();
      menuCount++;
    %>
    <tr <%= toString(pendingCall.getPriorityString()).startsWith("H") ? "class=\"highlightRow\"" : ""%>>
     <td valign="top">
       <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayCallMenu('select-arrow<%= menuCount %>','menuCall','<%= pendingCall.getContactOrgId() %>', '<%= pendingCall.getFollowupContactId() %>', '<%= pendingCall.getId() %>','pending',<%=User.getUserId()== pendingCall.getOwner()%>);" 
          onMouseOver="over(0, <%= menuCount %>);" 
          onmouseout="out(0, <%= menuCount %>);hideMenu('menuCall');"><img
          src="images/select-arrow.gif" name="select-arrow<%= menuCount %>" id="select-arrow<%= menuCount %>" align="absmiddle" border="0" /></a>
     </td>
     <td nowrap valign="top">
       <zeroio:tz timestamp="<%= pendingCall.getAlertDate() %>" timeZone="<%= User.getTimeZone() %>" timeOnly="true"/>
     </td>
     <td nowrap valign="top">
     <% if(pendingCall.getFollowupContactName()!=null){ %>
      <%= toString(pendingCall.getFollowupContactName()) %>
       <%} else {%>
      <%= StringUtils.trimToSize(toString(pendingCall.getOrgName()), 20) %>
       <%}%>
     </td>
     <td nowrap valign="top">
        <% if (pendingCall.getFollowupContact().getPhoneNumberList().size() > 1) { %>
                <%= pendingCall.getFollowupContact().getPhoneNumberList().getHtmlSelect("contactphone", -1) %>
          <% } else if (pendingCall.getFollowupContact().getPhoneNumberList().size() == 1) { 
             PhoneNumber thisNumber = (PhoneNumber) pendingCall.getFollowupContact().getPhoneNumberList().get(0);
         %>
             <%= String.valueOf(thisNumber.getTypeName().charAt(0)) + ":" + thisNumber.getNumber() %>
        <%}%>
        &nbsp;
      </td>
     <td valign="top">
       <%= StringUtils.trimToSize(toHtml(pendingCall.getAlertText()), 30) %>
     </td>
     <td nowrap valign="top">
       <%= StringUtils.trimToSizeNoDots(toString(pendingCall.getPriorityString()), 1) %>
     </td>
    </tr>
   <% }
   } %>
</table>
</dhv:evaluate>

<%-- include completed activities --%>
<dhv:evaluate if="<%= callEventList.getCompletedCalls().size() > 0 %>">
<table border="0" id="completedcalldetails<%= toFullDateString(thisDay.getDate()) %>" width="100%">
  <%-- title row --%>
  <tr>
    <td>&nbsp;</td>
    <td colspan="3" nowrap class="eventName">
      <img border="0" src="images/alertcall.gif" align="absmiddle" title="Completed Activities" />
      <dhv:label name="calendar.completedActivities">Completed Activities</dhv:label>
      (<%= callEventList.getCompletedCalls().size() %>)
    </td>
  </tr>
  <%-- include call details --%>
  <%
    Iterator k = callEventList.getCompletedCalls().iterator();
    if(k.hasNext()){
  %>
    <tr>
      <th>
        &nbsp;
      </th>
      <th class="weekSelector">
        <strong><dhv:label name="calendar.time">Time</dhv:label></strong>
      </th>
      <th class="weekSelector">
        <strong><dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label></strong>
      </th>
      <th class="weekSelector" width="100%">
        <strong><dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label></strong>
      </th>
    </tr>
  <%
    while(k.hasNext()){
    Call completedCall = (Call) k.next();
    menuCount++;
  %>
  <tr>
   <td valign="top">
     <%-- Use the unique id for opening the menu, and toggling the graphics --%>
     <a href="javascript:displayCallMenu('select-arrow<%= menuCount %>','menuCall','<%= completedCall.getContactOrgId() %>','<%= completedCall.getContactId() %>', '<%= completedCall.getId() %>', '',<%=User.getUserId()== completedCall.getOwner()%>);" 
        onMouseOver="over(0, <%= menuCount %>)" 
        onMouseOut="out(0, <%= menuCount %>);hideMenu('menuCall');"><img 
        src="images/select-arrow.gif" name="select-arrow<%= menuCount %>" id="select-arrow<%= menuCount %>" align="absmiddle" border="0"></a>
   </td>
   <td nowrap valign="top">
     <zeroio:tz timestamp="<%= completedCall.getCallStartDate() %>" timeOnly="true"/>
   </td>
   <td nowrap valign="top">
         <% if(completedCall.getContactName()!=null){ %>
      <%= toString(completedCall.getContactName()) %>
       <%} else {%>
      <%= StringUtils.trimToSize(toString(completedCall.getOrgName()), 20) %>
       <%}%>
   </td>
   <td valign="top">
     <%= StringUtils.trimToSize(toHtml(completedCall.getSubject()), 30) %>
   </td>
  </tr>
  <% 
     }
   }
  %>
</table>
</dhv:evaluate>


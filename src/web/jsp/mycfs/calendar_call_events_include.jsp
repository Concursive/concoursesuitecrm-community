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
<%-- draws the call events for a specific day --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="org.aspcfs.modules.contacts.base.Call,org.aspcfs.modules.base.PhoneNumber" %>
<%
  CallEventList callEventList = (CallEventList) thisDay.get(category);
%>
                            
<%-- include pending activities --%>
<dhv:evaluate if="<%= callEventList.getPendingCalls().size() > 0 %>">
<table border="0">
  <tr>
    <td colspan="6" nowrap>
      <%-- event name --%>
      <img border="0" src="images/box-hold.gif" align="texttop" title="Activities"><a href="javascript:changeImages('pendingcallsimage<%=toFullDateString(thisDay.getDate()) %>','images/arrowdown.gif','images/arrowright.gif');javascript:switchStyle(document.getElementById('pendingcalldetails<%=toFullDateString(thisDay.getDate()) %>'));" onMouseOver="window.status='View Details';return true;" onMouseOut="window.status='';return true;"><img src="<%= firstEvent ? "images/arrowdown.gif" : "images/arrowright.gif"%>" name="pendingcallsimage<%=toFullDateString(thisDay.getDate())%>" id="<%= firstEvent ? "0" : "1"%>" border="0" title="Click To View Details">Pending Activities</a>&nbsp;(<%= callEventList.getPendingCalls().size() %>)
    </td>
  </tr>
</table>
<table border="0" id="pendingcalldetails<%= toFullDateString(thisDay.getDate()) %>" style="<%= firstEvent ? "display:" : "display:none"%>">
  <%-- include call details --%>
  <%
    Iterator j = callEventList.getPendingCalls().iterator();
    if(j.hasNext()){
  %>
    <tr>
      <th>
        &nbsp
      </th>
      <th class="weekSelector" nowrap>
        <strong>Due</strong>
      </th>
      <th class="weekSelector" nowrap>
        <strong><dhv:label name="accounts.account">Account</dhv:label></strong>
      </th>
      <th class="weekSelector" nowrap>
        <strong>Contact</strong>
      </th>
      <th class="weekSelector" nowrap>
        <strong>Phone</strong>
      </th>
      <th class="weekSelector" width="100%">
        <strong>Description</strong>
      </th>
      <th class="weekSelector" nowrap>
        <strong>Pri</strong>
      </th>
    </tr>
  <%  
      while(j.hasNext()){
      Call pendingCall = (Call) j.next();
      menuCount++;
    %>
    <tr <%= toString(pendingCall.getPriorityString()).startsWith("H") ? "class=\"highlightRow\"" : ""%>>
     <td>
       <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayCallMenu('select<%= menuCount %>','menuCall','<%= pendingCall.getContactOrgId() %>', '<%= pendingCall.getContactId() %>', '<%= pendingCall.getId() %>','pending');" 
          onMouseOver="over(0, <%= menuCount %>);" 
          onmouseout="out(0, <%= menuCount %>);hideMenu('menuCall');">
          <img src="images/select.gif" name="select<%= menuCount %>" id="select<%= menuCount %>" align="absmiddle" border="0"></a>
     </td>
     <td nowrap>
       <zeroio:tz timestamp="<%= pendingCall.getAlertDate() %>" timeOnly="true"/>
     </td>
     <td nowrap>
       <%= StringUtils.trimToSize(toString(pendingCall.getOrgName()), 20) %>
     </td>
     <td nowrap>
       <%= toString(pendingCall.getContactName()) %>
     </td>
     <td nowrap>
        <% if (pendingCall.getContact().getPhoneNumberList().size() > 1) { %>
            <%= pendingCall.getContact().getPhoneNumberList().getHtmlSelect("contactphone", -1) %>
        <% } else if (pendingCall.getContact().getPhoneNumberList().size() == 1) { 
             PhoneNumber thisNumber = (PhoneNumber) pendingCall.getContact().getPhoneNumberList().get(0);
         %>
             <%= String.valueOf(thisNumber.getTypeName().charAt(0)) + ":" + thisNumber.getNumber() %>
        <%}%>
        &nbsp;
      </td>
     <td nowrap>
       <%= StringUtils.trimToSize(toHtml(pendingCall.getAlertText()), 30) %>
     </td>
     <td nowrap>
       <%= StringUtils.trimToSizeNoDots(toString(pendingCall.getPriorityString()), 1) %>
     </td>
    </tr>
   <% }
   } %>
</table>
</dhv:evaluate>

<%-- include completed activities --%>
<dhv:evaluate if="<%= callEventList.getCompletedCalls().size() > 0 %>">
<table border="0">
  <tr>
    <td colspan="6" nowrap>
      <%-- event name --%>
      <img border="0" src="images/alertcall.gif" align="texttop" title="Activities"><a href="javascript:changeImages('completedcallsimage<%=toFullDateString(thisDay.getDate()) %>','images/arrowdown.gif','images/arrowright.gif');javascript:switchStyle(document.getElementById('completedcalldetails<%=toFullDateString(thisDay.getDate()) %>'));" onMouseOver="window.status='View Details';return true;" onMouseOut="window.status='';return true;"><img src="<%= firstEvent ? "images/arrowdown.gif" : "images/arrowright.gif"%>" name="completedcallsimage<%=toFullDateString(thisDay.getDate())%>" id="<%= firstEvent ? "0" : "1"%>" border="0" title="Click To View Details">Completed Activities</a>&nbsp;(<%= callEventList.getCompletedCalls().size() %>)
    </td>
  </tr>
</table>
<table border="0" id="completedcalldetails<%= toFullDateString(thisDay.getDate()) %>" style="<%= firstEvent ? "display:" : "display:none"%>">
  <%-- include call details --%>
  <%
    Iterator k = callEventList.getCompletedCalls().iterator();
    if(k.hasNext()){
  %>
    <tr>
      <th>
        &nbsp;
      </th>
      <th class="weekSelector" nowrap>
        <strong>Time</strong>
      </th>
      <td class="weekSelector" nowrap>
       <strong><dhv:label name="accounts.account">Account</dhv:label></strong>
     </td>
     <th class="weekSelector" nowrap>
        <strong>Contact</strong>
      </th>
      <th class="weekSelector" width="100%">
        <strong>Subject</strong>
      </th>
    </tr>
  <%
    while(k.hasNext()){
    Call completedCall = (Call) k.next();
    menuCount++;
  %>
  <tr>
   <td>
     <%-- Use the unique id for opening the menu, and toggling the graphics --%>
     <a href="javascript:displayCallMenu('select<%= menuCount %>','menuCall','<%= completedCall.getContactOrgId() %>','<%= completedCall.getContactId() %>', '<%= completedCall.getId() %>', '');" 
        onMouseOver="over(0, <%= menuCount %>)" 
        onMouseOut="out(0, <%= menuCount %>);hideMenu('menuCall');"><img 
        src="images/select.gif" name="select<%= menuCount %>" id="select<%= menuCount %>" align="absmiddle" border="0"></a>
   </td>
   <td nowrap>
     <zeroio:tz timestamp="<%= completedCall.getCompleteDate() %>" timeOnly="true"/>
   </td>
   <td nowrap>
       <%= StringUtils.trimToSize(toString(completedCall.getOrgName()), 20) %>
   </td>
   <td nowrap>
     <%= toString(completedCall.getContactName()) %>
   </td>
   <td nowrap>
   <%= StringUtils.trimToSize(toHtml(completedCall.getSubject()), 30) %>
   </td>
  </tr>
  <% 
     }
   }
  %>
</table>
</dhv:evaluate>


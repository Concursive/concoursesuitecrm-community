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
<%-- draws all the events for a specific day --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%
  //NOTE: The entries of this array should exactly match the entries of the EVENT_TYPES array in CalendarEventList object, the display is based on the order specified in this jsp's array
  String[] EVENT_TYPES = {"Tasks", "Calls", "Opportunities", "Account Alerts", "Account Contract Alerts", "Contact Calls", "Opportunity Calls", "Holiday", "Assignments", "System Alerts", "Quotes", "Tickets","Ticket Requests","Pending Calls"};
%>

<table>
<%-- Iterate through all event types in order specified in the EVENT_TYPES --%>
<%
  for (int i = 0; i< Array.getLength(EVENT_TYPES); i++) {
  String category = EVENT_TYPES[i]; 
  if(thisDay.containsKey(category)){
%>
 <tr>
  <td>
    <%-- include jsp for event adding based on event type --%>
    <%  if (category.equals("Calls")) { %>
        <%@ include file="calendar_call_events_include.jsp" %>
    <% } else if (category.equals("Tasks")) { %>
        <%@ include file="calendar_task_events_include.jsp" %>
    <%  } else if (category.equals("Opportunities")) { %>
        <%@ include file="calendar_opportunity_events_include.jsp" %>
    <%  } else if (category.equals("Account Alerts")) { %>
        <%@ include file="calendar_account_events_include.jsp" %>
    <%  }  else if (category.equals("Account Contract Alerts")) { %>
        <%@ include file="calendar_account_events_include.jsp" %>
    <%  } else if (category.equals("Assignments")) {
  
        } else if (category.equals("Quotes")) { %>
       <%@ include file="calendar_quotes_events_include.jsp" %>
    <%  }else if(category.equals("Tickets")){ %>
      <%@ include file="calendar_ticket_events_include.jsp" %>
    <%  }else if(category.equals("Ticket Requests")){ %>
      <%@ include file="calendar_ticket_events_due_today_include.jsp" %>
    <%  }else if (category.equals("Holiday")) {
        ArrayList tmpList = (ArrayList) thisDay.get(category);
        CalendarEvent thisEvent = (CalendarEvent) tmpList.get(0);
    %>
        <img border="0" src="images/event-holiday.gif" align="texttop" title="Holidays"><%= "US Bank Holiday: " + thisEvent.getSubject() %>
    <% }else if (category.equals("System Alerts")) { %>
        <img border="0" src="images/box-hold.gif" align="texttop" title="System Alerts">User login expires
    <% }  %>
  </td>
 </tr>
 <% 
      if(firstEvent){
        firstEvent = false;
      }
    } 
   }
 %>
</table>

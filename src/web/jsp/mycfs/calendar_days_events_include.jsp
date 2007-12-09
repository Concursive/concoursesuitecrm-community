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
<%-- draws all the events for a specific day --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%
  //NOTE: The entries of this array should exactly match the entries of the EVENT_TYPES array in CalendarEventList object, the display is based on the order specified in this jsp's array
  String[] EVENT_TYPES = {"Tasks", 
                          "Activities",
                          "Opportunities",
                          "Account Alerts",
                          "Account Contract Alerts",
                          "Contact Activities",
                          "Opportunity Activities",
                          "Holiday",
                          "Assignments",
                          "System Alerts",
                          "Quotes", "Tickets",
                          "Ticket Requests",
                          "Pending Activities",
                          "Project Tickets"};
%>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
<%-- Iterate through all event types in order specified in the EVENT_TYPES --%>
<%
  for (int i = 0; i< java.lang.reflect.Array.getLength(EVENT_TYPES); i++) {
  String category = EVENT_TYPES[i]; 
  if(thisDay.containsKey(category)){
%>
 <tr>
  <td>
    <%-- include jsp for event adding based on event type --%>
    <%  if (category.equals(EVENT_TYPES[1])) { %>
        <%@ include file="calendar_call_events_include.jsp" %>
    <%  } else if (category.equals(EVENT_TYPES[0])) { %>
        <%@ include file="calendar_task_events_include.jsp" %>
    <%  } else if (category.equals(EVENT_TYPES[2])) { %>
        <%@ include file="calendar_opportunity_events_include.jsp" %>
    <%  } else if (category.equals(EVENT_TYPES[3])) { %>
        <%@ include file="calendar_account_events_include.jsp" %>
    <%  }  else if (category.equals(EVENT_TYPES[4])) { %>
        <%@ include file="calendar_account_events_include.jsp" %>
    <%  } else if (category.equals(EVENT_TYPES[8])) { %>
        <%@ include file="calendar_project_assignment_events_include.jsp" %>
    <%  } else if (category.equals(EVENT_TYPES[10])) { %>
       <%@ include file="calendar_quotes_events_include.jsp" %>
    <%  } else if(category.equals(EVENT_TYPES[11])){ %>
      <%@ include file="calendar_ticket_events_include.jsp" %>
    <%  } else if(category.equals(EVENT_TYPES[12])){ %>
      <%@ include file="calendar_ticket_events_due_today_include.jsp" %>
    <%  } else if(category.equals(EVENT_TYPES[14])){ %>
      <%@ include file="calendar_project_ticket_events_due_today_include.jsp" %>
    <%  } else if (category.equals(EVENT_TYPES[7])) {
        ArrayList tmpList = (ArrayList) thisDay.get(category);
        CalendarEvent thisEvent = (CalendarEvent) tmpList.get(0);
    %>
      <table border="0" width="100%">
        <tr>
          <td>
            <img src="images/select-arrow-trans.gif" border="0" />
          </td>
          <td width="100%">
            <img border="0" src="images/event-star12.gif" align="absmiddle" />
            <dhv:label name="calendar.Holiday">Holiday</dhv:label>:
            <%= toHtml(thisEvent.getSubject()) %>
          </td>
        </tr>
      </table>
    <% }else if (category.equals("System Alerts")) { %>
      <table border="0" width="100%">
        <tr>
          <td>
            <img src="images/select-arrow-trans.gif" border="0" />
          </td>
          <td width="100%">
            <img border="0" src="images/box-hold.gif" align="absmiddle" />
            <dhv:label name="calendar.userLoginExpires">User login expires</dhv:label>
          </td>
        </tr>
      </table>
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

<%-- draws all the events for a specific day --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%
  //NOTE: The entries of this array should exactly match the entries of the EVENT_TYPES array in CalendarEventList object, the display is based on the order specified in this jsp's array
  String[] EVENT_TYPES = {"Tasks", "Calls", "Opportunities", "Account Alerts", "Account Contract Alerts", "Contact Calls", "Opportunity Calls", "Holiday", "Assignments", "System Alerts", "Quotes", "Tickets"};
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
    <%  }else if (category.equals("Holiday")) {
        ArrayList tmpList = (ArrayList) thisDay.get(category);
        CalendarEvent thisEvent = (CalendarEvent) tmpList.get(0);
    %>
        <img border="0" src="images/event-holiday.gif" align="texttop" title="Holidays"><%= "Holiday: " + thisEvent.getSubject() %>
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

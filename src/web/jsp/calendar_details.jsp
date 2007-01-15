<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,java.lang.reflect.*, org.aspcfs.modules.mycfs.beans.CalendarBean, org.aspcfs.modules.mycfs.base.*,org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="CompanyCalendar" class="org.aspcfs.utils.web.CalendarView" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/trackMouse.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
function reopen() {
  window.parent.reopen();
}

function initialize_menus(){
  new ypSlideOutMenu("menuAccount", "down", 0, 0, 170, getHeight("menuAccountTable"));
  new ypSlideOutMenu("menuCall", "down", 0, 0, 170, getHeight("menuCallTable"));
  new ypSlideOutMenu("menuOpp", "down", 0, 0, 170, getHeight("menuOppTable"));
  new ypSlideOutMenu("menuTask", "down", 0, 0, 170, getHeight("menuTaskTable"));
  new ypSlideOutMenu("menuTicket", "down", 0, 0, 170, getHeight("menuTicketTable"));
  new ypSlideOutMenu("menuProjectTicket", "down", 0, 0, 170, getHeight("menuProjectTicketTable"));
  new ypSlideOutMenu("menuProject", "down", 0, 0, 170, getHeight("menuProjectTable"));
}

</SCRIPT>
<%@ include file="initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="initPopupMenu.jsp" %>
<%-- include the select menu jsp's for all events --%>
<%@ include file="mycfs/calendar_task_events_menu.jsp" %>
<%@ include file="mycfs/calendar_calls_events_menu.jsp" %>
<%@ include file="mycfs/calendar_account_events_menu.jsp" %>
<%@ include file="mycfs/calendar_opportunity_events_menu.jsp" %>
<%@ include file="mycfs/calendar_ticket_events_menu.jsp" %>
<%@ include file="mycfs/calendar_project_ticket_events_menu.jsp" %>
<%@ include file="mycfs/calendar_project_assignment_events_menu.jsp" %>
<% 
   String returnPage = request.getParameter("return");
   CalendarBean CalendarInfo = (CalendarBean) session.getAttribute(returnPage!=null?returnPage + "CalendarInfo" :"CalendarInfo");
   TimeZone timeZone = Calendar.getInstance().getTimeZone();
   if(User.getUserRecord().getTimeZone() != null){
    timeZone = TimeZone.getTimeZone(User.getUserRecord().getTimeZone());
   }
%>
<%-- Preload image rollovers for drop-down menu --%>
<script type="text/javascript">
loadImages('select-arrow');

function changeUserName(id){
  alert(window.parent.getElementById(id).innerHtml);
  userName = window.parent.getElementById(id).innerHtml;
  window.parent.getElementById('changeUserName').innerHtml = userName;
}
function switchStyle(E){
  if(E.style.display == "none"){
    E.style.display = '';
  }
  else{
    E.style.display = 'none';
  }
}
function reloadCalendar(){
  window.parent.frames['calendar'].location.href='MyCFS.do?command=MonthView&inline=true&month=<%= CalendarInfo.getMonthSelected() %>&year=<%= CalendarInfo.getYearSelected() %>&source=calendar<%=returnPage!=null?"&return="+returnPage:""%>';
}
</script>
<dhv:evaluate if='<%= "true".equals(request.getParameter("reloadCalendar")) %>'>
<body onLoad="javascript:reloadCalendar();">
</dhv:evaluate>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<%-- Display header label --%>
  <tr>
    <td align="center" valign="top" width="100%" nowrap>
      <strong>
        <dhv:evaluate if="<%= CalendarInfo.isAgendaView() %>">
          <dhv:label name="calendar.next7daysView">Next 7 Days View</dhv:label><br />
          &nbsp;
        </dhv:evaluate>
        <dhv:evaluate if="<%= !CalendarInfo.isAgendaView() %>">
          <dhv:evaluate if='<%= "week".equalsIgnoreCase(CalendarInfo.getCalendarView()) %>'>
            <dhv:label name="calendar.weekView.colon" param='<%= "startTime="+getTime(pageContext,new Timestamp(CompanyCalendar.getCalendarInfo().getStartOfWeekDate().getTime()),"&nbsp;",DateFormat.MEDIUM,false,false,true,"&nbsp;")+"|endTime="+getTime(pageContext,new Timestamp(CompanyCalendar.getCalendarInfo().getEndOfWeekDate().getTime()),"&nbsp;",DateFormat.MEDIUM,false,false,true,"&nbsp;") %>'>Week View: <zeroio:tz timestamp="<%= CompanyCalendar.getCalendarInfo().getStartOfWeekDate() %>" dateOnly="true" dateFormat="<%= DateFormat.MEDIUM %>" userTimeZone="false" default="&nbsp;"/> - <zeroio:tz timestamp="<%= CompanyCalendar.getCalendarInfo().getEndOfWeekDate() %>" dateOnly="true" dateFormat="<%= DateFormat.MEDIUM %>" userTimeZone="false" default="&nbsp;"/></dhv:label>
          </dhv:evaluate>
          <dhv:evaluate if='<%= "day".equalsIgnoreCase(CalendarInfo.getCalendarView()) %>'>
            <dhv:label name="calendar.dayView">Day View</dhv:label>
          </dhv:evaluate>
        </dhv:evaluate>
      </strong>
    </td>
  </tr>
<%-- Display back link --%>
<dhv:evaluate if="<%= !CalendarInfo.isAgendaView() %>">
  <tr>
    <td valign="top" align="center" width="100%" nowrap>
      <a href="javascript:window.parent.frames['calendar'].resetCalendar();javascript:window.location.href='MyCFS.do?command=AgendaView&inline=true&&source=calendardetails<%=returnPage != null ? "&return=" + returnPage : "" %>';"><dhv:label name="calendar.backToNext7daysView">Back To Next 7 Days View</dhv:label></a>
    </td>
  </tr>
  <tr>
    <td><table style="visibility:none" border="0" height="6"><tr height='2' style="visibility:none"><td></td></tr></table></td>
  </tr>
</dhv:evaluate>
<%-- Display the days, always starting with today in Agenda View --%>
  <tr>
    <td width="100%" valign="top" align="left">
      <table width="100%" cellspacing="0" cellpadding="0" border="0">
<dhv:evaluate if="<%= CalendarInfo.isAgendaView() %>">
        <tr>
          <td colspan="2">
            <table border="0" width="100%">
              <tr>
                <td width="100%" class="dayName">
                    <strong><%
                      Calendar tmpCal = Calendar.getInstance();
                      tmpCal.setTimeZone(timeZone);
                       %>
                    <zeroio:tz timestamp="<%= tmpCal %>" dateFormat="<%= DateFormat.FULL %>" dateOnly="true" default="&nbsp;"/>
                    <font color="#006699">(<dhv:label name="calendar.Today">Today</dhv:label>)</font></strong>
                </td>
              </tr>
            </table>
          </td>
        </tr>
</dhv:evaluate>
<%
   Calendar today = Calendar.getInstance();
   today.setTimeZone(timeZone);
   Iterator days = (CompanyCalendar.getEvents(100)).iterator();
   if (days.hasNext()) {
     boolean showToday = false;
     boolean firstEvent = true;
     int count = 0;
     int menuCount = 0;
     while (days.hasNext()) {
       CalendarEventList thisDay = (CalendarEventList) days.next();
       Calendar thisCal = Calendar.getInstance();
       //thisCal.setTimeZone(timeZone);
       thisCal.setTime(thisDay.getDate());
       boolean isToday = 
          ((thisCal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) &&
          (thisCal.get(Calendar.YEAR) == today.get(Calendar.YEAR)));
%>
    <dhv:evaluate if="<%= (CalendarInfo.isAgendaView() && !isToday && count == 0) %>">
        <tr>
          <td>
            <img src="images/select-arrow-trans.gif"  border="0" />
          </td>
          <td valign="top">
            <dhv:label name="calendar.noItemsPending">There are currently no items pending for you.</dhv:label>
          </td>
        </tr>
        <tr style="visibility:none">
          <td style="visibility:none" colspan="2">
            <br />
          </td>
        </tr>
    </dhv:evaluate>
    <dhv:evaluate if="<%= (!isToday && CalendarInfo.isAgendaView()) || !CalendarInfo.isAgendaView() %>">
        <tr>
          <td colspan="2">
            <table border="0" width="100%">
              <tr>
                <td width="100%" class="dayName">
                  <%-- The dates are already using the user's timezone, so do not use again --%>
                  <strong><zeroio:tz timestamp="<%= thisCal %>" dateFormat="<%= DateFormat.FULL %>" dateOnly="true" userTimeZone="false" default="&nbsp;"/></strong>
                </td>
              </tr>
            </table>
          </td>
        </tr>
    </dhv:evaluate>
    <tr>
      <td colspan="2" width="100%">
        <%-- draw the events for the day --%>
        <%@ include file="mycfs/calendar_days_events_include.jsp" %>
      </td>
    </tr>
    <dhv:evaluate if="<%= days.hasNext() %>">
    <tr style="visibility:none">
      <td style="visibility:none" colspan="2">
        <br />
      </td>
    </tr>
    </dhv:evaluate>
<%    
      count++;
     }
   } else {
%>
        <tr>
          <td>
            <img src="images/select-arrow-trans.gif"  border="0" />
          </td>
          <td valign="top">
            <dhv:label name="calendar.noItemsPending">There are currently no items pending for you.</dhv:label>
          </td>
        </tr>
<%
   }
%>
      </table>
    </td>
  </tr>
</table>
<dhv:evaluate if='<%= "true".equals(request.getParameter("reloadCalendar")) %>'>
</body>
</dhv:evaluate>

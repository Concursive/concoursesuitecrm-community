<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,java.lang.reflect.*, org.aspcfs.modules.mycfs.beans.CalendarBean, org.aspcfs.modules.mycfs.base.*,org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="CompanyCalendar" class="org.aspcfs.utils.web.CalendarView" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/trackMouse.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
function initialize_menus(){
  new ypSlideOutMenu("menuAccount", "down", 0, 0, 170, getHeight("menuAccountTable"));
  new ypSlideOutMenu("menuCall", "down", 0, 0, 170, getHeight("menuCallTable"));
  new ypSlideOutMenu("menuOpp", "down", 0, 0, 170, getHeight("menuOppTable"));
  new ypSlideOutMenu("menuTask", "down", 0, 0, 170, getHeight("menuTaskTable"));
  new ypSlideOutMenu("menuTicket", "down", 0, 0, 170, getHeight("menuTicketTable"));
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
<dhv:evaluate if="<%= "true".equals(request.getParameter("reloadCalendar")) %>">
<body onLoad="javascript:reloadCalendar();">
</dhv:evaluate>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<%-- Display header label --%>
  <tr>
    <td align="center" valign="top" width="100%" nowrap>
      <strong><%= CalendarInfo.isAgendaView()?"Next 7 Days View": Character.toUpperCase(CalendarInfo.getCalendarView().charAt(0)) + CalendarInfo.getCalendarView().substring(1) + " View " + (CalendarInfo.getCalendarView().equalsIgnoreCase("week")?" : " + toMediumDateString(CompanyCalendar.getCalendarInfo().getStartOfWeekDate()) + " - " + toMediumDateString(CompanyCalendar.getCalendarInfo().getEndOfWeekDate()):"")%></strong>
      <dhv:evaluate if="<%= CalendarInfo.isAgendaView() %>"><br>&nbsp;</dhv:evaluate>
    </td>
  </tr>
<%-- Display back link --%>
<dhv:evaluate exp="<%= !CalendarInfo.isAgendaView() %>">
  <tr>
    <td valign="top" align="center" width="100%" nowrap>
      <a href="javascript:window.parent.frames['calendar'].resetCalendar();javascript:window.location.href='MyCFS.do?command=AgendaView&inline=true&&source=calendardetails<%=returnPage != null ? "&return=" + returnPage : "" %>';">Back To Next 7 Days View</a>
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
<dhv:evaluate exp="<%= CalendarInfo.isAgendaView() %>">      
        <tr>
          <td colspan="2">
            <table border="0" width="100%">
              <tr>
                <td width="100%" class="dayName">
                    <strong><%
                      Calendar tmpCal = Calendar.getInstance();
                      tmpCal.setTimeZone(timeZone);
                      int currDay = tmpCal.get(Calendar.DAY_OF_MONTH);
                      int currMonth = tmpCal.get(Calendar.MONTH) + 1;
                      int currYear = tmpCal.get(Calendar.YEAR);
                      DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT);
                       %>
                    <%= toFullDateString(formatter.parse(currMonth  + "/" + currDay + "/" + currYear)) %> <font color="#006699">(Today)</font></strong>
                </td>
              </tr>
            </table>
          </td>
        </tr>
</dhv:evaluate>
<%
   Iterator days = (CompanyCalendar.getEvents(100)).iterator();
   if (days.hasNext()) {
     boolean showToday = false;
     boolean firstEvent = true;
     int count = 0;
     int menuCount = 0;
     Calendar today = Calendar.getInstance();
     today.setTimeZone(timeZone);
     while (days.hasNext()) {
       CalendarEventList thisDay = (CalendarEventList)days.next();
       Calendar thisCal = Calendar.getInstance();
       thisCal.setTime(thisDay.getDate());
       boolean isToday = 
          ((thisCal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) &&
          (thisCal.get(Calendar.YEAR) == today.get(Calendar.YEAR)));
%>
    <dhv:evaluate exp="<%= (CalendarInfo.isAgendaView() && !isToday && count == 0) %>">
        <tr>
          <td>
            <img src="images/select-arrow-trans.gif"  border="0" />
          </td>
          <td valign="top">
            There are currently no items pending for you.
          </td>
        </tr>
        <tr style="visibility:none">
          <td style="visibility:none" colspan="2">
            <br />
          </td>
        </tr>
    </dhv:evaluate>
    <dhv:evaluate exp="<%= (!isToday && CalendarInfo.isAgendaView()) || !CalendarInfo.isAgendaView() %>">
        <tr>
          <td colspan="2">
            <table border="0" width="100%">
              <tr>
                <td width="100%" class="dayName">
                  <strong><%= toFullDateString(thisDay.getDate()) %></strong>
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
            There are currently no items pending for you.
          </td>
        </tr>
<%
   }
%>
      </table>
    </td>
  </tr>
</table>
<dhv:evaluate if="<%= "true".equals(request.getParameter("reloadCalendar")) %>">
</body>
</dhv:evaluate>

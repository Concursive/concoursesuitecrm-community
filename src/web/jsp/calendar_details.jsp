<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.lang.reflect.*, org.aspcfs.modules.mycfs.beans.CalendarBean, org.aspcfs.modules.mycfs.base.*" %>
<jsp:useBean id="CompanyCalendar" class="org.aspcfs.utils.web.CalendarView" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<%@ include file="../initPage.jsp" %>
<% 
   String returnPage = request.getParameter("return");
   CalendarBean CalendarInfo = (CalendarBean) session.getAttribute(returnPage!=null?returnPage + "CalendarInfo" :"CalendarInfo");
%>
<script type="text/javascript">
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
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<%-- Display header label --%>
  <tr>
    <td align="center" valign="top" width="100%" nowrap>
      <strong><%= CalendarInfo.isAgendaView()?"Next 7 Days View": Character.toUpperCase(CalendarInfo.getCalendarView().charAt(0)) + CalendarInfo.getCalendarView().substring(1) + " View " + (CalendarInfo.getCalendarView().equalsIgnoreCase("week")?" : " + toMediumDateString(CompanyCalendar.getStartOfWeekDate()) + " - " + toMediumDateString(CompanyCalendar.getEndOfWeekDate()):"")%></strong>
      <dhv:evaluate if="<%= CalendarInfo.isAgendaView() %>"><br>&nbsp;</dhv:evaluate>
    </td>
  </tr>
<%-- Display back link --%>
<dhv:evaluate exp="<%= !CalendarInfo.isAgendaView() %>">
  <tr>
    <td valign="top" align="center" width="100%" nowrap>
      <a href="javascript:window.parent.frames['calendar'].resetCalendar();javascript:window.location.href='MyCFS.do?command=AgendaView&inline=true&&source=calendardetails<%=returnPage != null ? "&return=" + returnPage : "" %>';">Back To Next 7 Days View</a>
    </td>
    <table style="visibility:none" border="0" height="6"><tr height='2' style="visibility:none"><td></td></tr></table>
  </tr>
</dhv:evaluate>
<%-- Display the days, always starting with today in Agenda View --%>
  <tr>
    <td width="100%" valign="top">
      <table width="100%" cellspacing="0" cellpadding="0" border="0">
<dhv:evaluate exp="<%= CalendarInfo.isAgendaView() %>">      
        <tr class="weekSelector">
          <td colspan="2" width="100%">
            <strong><%= toFullDateString(Calendar.getInstance().getTime()) %>
            <font color="#006699">(Today)</font></strong>
          </td>
        </tr>
</dhv:evaluate>
<%
   Iterator days = (CompanyCalendar.getEvents(100)).iterator();
   if (days.hasNext()) {
     boolean showToday = false;
     int count = 0;
     Calendar today = Calendar.getInstance();
     today.setTime(new java.util.Date());
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
          <td valign="top" nowrap>
            &nbsp;
          </td>
          <td valign="top">
            No scheduled actions found.
          </td>
        </tr>
        <tr style="visibility:none">
          <td style="visibility:none">
            <br>
          </td>
        </tr>
    </dhv:evaluate>
    <dhv:evaluate exp="<%= (!isToday && CalendarInfo.isAgendaView()) || !CalendarInfo.isAgendaView() %>">
        <tr class="weekSelector">
          <td colspan="2" width="100%">
            <strong><%= toFullDateString(thisDay.getDate()) %></strong>
          </td>
        </tr>
    </dhv:evaluate>
<%
       boolean firstTime = true;
       for (int i = 0; i< Array.getLength(CalendarEventList.EVENT_TYPES); i++) {
       firstTime = true;
       int categoryCount = CompanyCalendar.getEventCount(thisCal.get(Calendar.MONTH) + 1, thisCal.get(Calendar.DAY_OF_MONTH), thisCal.get(Calendar.YEAR), CalendarEventList.EVENT_TYPES[i]);
       Iterator eventList = thisDay.iterator();
       while (eventList.hasNext()) {
         CalendarEvent thisEvent = (CalendarEvent)eventList.next();
         if (thisEvent.getCategory().toUpperCase().equals(CalendarEventList.EVENT_TYPES[i].toUpperCase())){
           if (firstTime) {
             firstTime = false;
%>
      <tr>
        <td colspan="2" width="100%">
          <table cellspacing="0" cellpadding="0" border="0" marginheight="0" marginwidth="0">
            <tr>
              <td><%= thisEvent.getIcon(CalendarEventList.EVENT_TYPES[i])%><a href="javascript:changeImages('detailsimage<%=toFullDateString(thisDay.getDate()) + i%>','images/arrowdown.gif','images/arrowright.gif');javascript:switchStyle(document.getElementById('alertdetails<%=toFullDateString(thisDay.getDate()) + i%>'));" onMouseOver="window.status='View Details';return true;" onMouseOut="window.status='';return true;"><img src="<%=count==0?"images/arrowdown.gif":"images/arrowright.gif"%>" name="detailsimage<%=toFullDateString(thisDay.getDate()) + i%>" id="<%=count==0?"0":"1"%>" border=0 title="Click To View Details"><%=CalendarEvent.getNamePlural(CalendarEventList.EVENT_TYPES[i]) %></a>&nbsp;(<%= categoryCount %>)</td>
            </tr>
          </table>
          <table cellspacing="0" cellpadding="0" marginheight="0" marginwidth="0" border="0" id="alertdetails<%= toFullDateString(thisDay.getDate()) + i %>" style="<%=count==0?"display:":"display:none"%>">
<%
           }
%>
            <tr>
              <td style="visibility:hidden" width="18">&nbsp;</td>
              <td>
                <li>
                  <%=thisEvent.getCategory().equalsIgnoreCase("task")?thisEvent.getIcon():""%>&nbsp;<%= thisEvent.getLink() %><%= thisEvent.getSubject() %></a><%= thisEvent.getRelatedLinks() != null && thisEvent.getRelatedLinks().size() > 0 ? "&nbsp;" : "" %>
                  <%-- Display all related links --%>
                   <dhv:evaluate exp="<%= thisEvent.getRelatedLinks() != null %>">
                   <%
                    Iterator linkList = thisEvent.getRelatedLinks().iterator();
                    while (linkList.hasNext()) {
                   %>
                      <%= linkList.next().toString() %>
                   <%}%>
                   </dhv:evaluate>
                </li>
              </td>
            </tr>
<%
          }
        }
       if(!firstTime) {
%>
          </table>
        </td>
      </tr>
<%
         }
       }
%>
        </tr>
        <tr style="visibility:none">
          <td style="visibility:none">
            <br>
          </td>
        </tr>
<%
      ++count;
     }
   } else {
%>
        <tr>
          <td valign="top" nowrap>
            &nbsp;
          </td>
          <td valign="top">
            No scheduled actions found.
          </td>
        </tr>
<%
   }
%>
      </table>
    </td>
  </tr>
</table>

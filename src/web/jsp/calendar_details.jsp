<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.lang.reflect.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="CompanyCalendar" class="com.darkhorseventures.utils.CalendarView" scope="request"/>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.UserBean" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/popURL.js"></SCRIPT>
<%@ include file="initPage.jsp" %>

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

<table bgcolor="#FFFFFF" width="100%" border="0" cellpadding="0" cellspacing="0" bordercolorlight="#000000" bordercolor="#FFFFFF">
      <tr>
         <td align="center" valign="top" nowrap><strong><%= CalendarInfo.isAgendaView()?"Next 7 Days View": Character.toUpperCase(CalendarInfo.getCalendarView().charAt(0)) + CalendarInfo.getCalendarView().substring(1) + " View " + (CalendarInfo.getCalendarView().equalsIgnoreCase("week")?" : " + toMediumDateString(CompanyCalendar.getStartOfWeekDate()) + " - " + toMediumDateString(CompanyCalendar.getEndOfWeekDate()):"")%> </strong></td>
      </tr>
      <dhv:evaluate exp="<%= !CalendarInfo.isAgendaView() %>">
      <tr>
        <td valign="top" align="left" nowrap><a href="javascript:window.parent.frames['calendar'].resetCalendar();javascript:window.location.href='MyCFS.do?command=AgendaView&source=calendardetails<%=returnPage!=null?"&return="+returnPage:""%>';">Back To Agenda View</a></td>
        <table style="visibility:none" border="0" height="6"><tr height='2' style="visibility:none"><td></td></tr></table>
      </tr>
      </dhv:evaluate>
      
      <tr>
        <td height="100%" width="100%" valign="top">
          <table width="100%" cellspacing="0" cellpadding="0" border="0">
          <tr>
            <td colspan="2">
              <strong>
                <%= CompanyCalendar.getToday() %> (Today)
              </strong>
             </td>
          </tr>
<%
   Iterator days = (CompanyCalendar.getEvents(20)).iterator();
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
    <dhv:evaluate exp="<%= !isToday %>">
       <tr>
         <td colspan="2">
           <strong>
             <%=  toFullDateString(thisDay.getDate()) %>
           </strong>
         </td>
       </tr>
     </dhv:evaluate>
<%
       Iterator eventList ;
       boolean firstTime = true;
       for (int i = 0; i< Array.getLength(CalendarEventList.EVENT_TYPES); i++) {
       firstTime = true;
       int categoryCount = 0 ;
       eventList = thisDay.iterator();
       while (eventList.hasNext()) {
        CalendarEvent thisEvent = (CalendarEvent)eventList.next();
        if(thisEvent.getCategory().toUpperCase().startsWith(CalendarEventList.EVENT_TYPES[i].toUpperCase())){
        categoryCount++;
        if(firstTime){
          firstTime = false;
        %>
        <table cellspacing="0" cellpadding="0" border="0" marginheight="0" marginwidth="0">
        <tr>
          <td nowrap><%= thisEvent.getIcon(CalendarEventList.EVENT_TYPES[i])%><a href="javascript:changeImages('detailsimage<%=toFullDateString(thisDay.getDate()) + i%>','images/arrowdown.gif','images/arrowright.gif');javascript:switchStyle(document.getElementById('alertdetails<%=toFullDateString(thisDay.getDate()) + i%>'));" onMouseOver="window.status='View Details';return true;" onMouseOut="window.status='';return true;"><img src="<%=count==0?"images/arrowdown.gif":"images/arrowright.gif"%>" name="detailsimage<%=toFullDateString(thisDay.getDate()) + i%>" id="<%=count==0?"0":"1"%>" border=0 title="Click To View Details"><%=CalendarEvent.getNamePlural(CalendarEventList.EVENT_TYPES[i]) %></a>&nbsp;<font id="category<%=i%>"></font></td>
        </tr>
        </table>
        <table width="100%" cellspacing="0" cellpadding="0" marginheight="0" marginwidth="0" border="0" id="alertdetails<%= toFullDateString(thisDay.getDate()) + i %>" style="<%=count==0?"display:":"display:none"%>">
      <%}%>
      <tr>
      <td style="visibility:hidden" width="18">i</td>
      <td nowrap>
                  <li><%=thisEvent.getCategory().equalsIgnoreCase("task")?thisEvent.getIcon():""%>&nbsp;<%= thisEvent.getLink() %><%= thisEvent.getSubject() %></a></li>
      </td></tr>
       <%}%>
     <%}
      if(!firstTime){
        %>
        <script type="text/javascript">document.getElementById('category<%=i%>').innerHTML = '(<%=categoryCount%>)';</script>
        </table>
      <%}
     }%>
     
     </tr>
     <tr style="visibility:none"><td style="visibility:none"><br></td></tr>
<%
      ++count;
       }%>
       <%}
    else {
         Calendar thisCal = Calendar.getInstance();
         if(CalendarInfo.getCalendarView().equalsIgnoreCase("day")){
         thisCal.set(CalendarInfo.getYearSelected(),CalendarInfo.getMonthSelected()-1,CalendarInfo.getDaySelected());
         %>
       <tr>
         <td colspan="2">
           <strong><%= toFullDateString(thisCal.getTime()) %> </strong>
         </td>
       </tr>
       <%}%>
       <tr>
         <td valign="top" nowrap>
           &nbsp;&nbsp;
         </td>
         <td width="100%" valign="top">
           No alerts found.
         </td>
       </tr>
<%
   }
%>

        </table>
      </td>
    </tr>
  </table>
<!-- End Table:Alerts -->

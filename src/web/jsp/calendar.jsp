<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.mycfs.beans.CalendarBean" %>
<jsp:useBean id="CompanyCalendar" class="org.aspcfs.utils.web.CalendarView" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:include page="templates/cssInclude.jsp" flush="true"/>
<%@ include file="../initPage.jsp" %>
<% 
   String returnPage = (String)request.getParameter("return");
   CalendarBean CalendarInfo = (CalendarBean) session.getAttribute(returnPage!=null?returnPage + "CalendarInfo" :"CalendarInfo");
%>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<script type="text/javascript">
  function showDayEvents(year, month,day){
    window.parent.frames['calendardetails'].location.href='MyCFS.do?command=DayView&inline=true&year='+year+'&month='+month+'&day='+day+'&source=calendardetails<%=returnPage!=null?"&return="+returnPage:""%>';
  }
  function showToDaysEvents(thisMonth,thisDay,thisYear){
   window.parent.frames['calendardetails'].location.href='MyCFS.do?command=TodaysView&inline=true&month=' + thisMonth + '&day=' + thisDay + '&year=' + thisYear + '&source=calendardetails&reloadCalendar=true<%= returnPage!=null ? "&return="+returnPage : "" %>';
  }
  
  function reloadCalendarDetails(){
  
  <% int selYear = CompanyCalendar.getCalendarInfo().getYearSelected();
     int selMonth = CompanyCalendar.getCalendarInfo().getMonthSelected();
     int selDay = CompanyCalendar.getCalendarInfo().getDaySelected();
  %>

  <%if("".equals(toString(CompanyCalendar.getCalendarInfo().getCalendarView())) || CompanyCalendar.getCalendarInfo().isAgendaView()){%>
    window.parent.frames['calendardetails'].location.href='MyCFS.do?command=AgendaView&source=calendarDetails<%=returnPage!=null?"&return="+returnPage:""%>';
    <% }else if("day".equals(toString(CompanyCalendar.getCalendarInfo().getCalendarView()))){ %>
    window.parent.frames['calendardetails'].location.href='MyCFS.do?command=DayView&inline=true&month=<%= selMonth %>&day=<%= selDay %>&year=<%= selYear %>&source=calendardetails<%=returnPage!=null?"&return="+returnPage:""%>';
    <% }else if("week".equals(toString(CompanyCalendar.getCalendarInfo().getCalendarView()))){ %>
    window.parent.frames['calendardetails'].location.href='MyCFS.do?command=WeekView&inline=true&startMonthOfWeek=<%= CompanyCalendar.getCalendarInfo().getStartMonthOfWeek() %>&startDayOfWeek=<%= CompanyCalendar.getCalendarInfo().getStartDayOfWeek() %>&year=<%= selYear %>&source=calendardetails<%=returnPage!=null?"&return="+returnPage:""%>';
    <% }else{ %>
    window.parent.frames['calendardetails'].location.href='MyCFS.do?command=ToDaysView&inline=true&month=<%= selMonth %>&day=<%= selDay %>&year=<%= selYear %>&source=calendardetails<%=returnPage!=null?"&return="+returnPage:""%>';
    <% } %>
  }

  function showWeekEvents(startYear,startMonth,startDay){
    window.parent.frames['calendardetails'].location.href='MyCFS.do?command=WeekView&inline=true&year='+ startYear +'&month='+startMonth+'&startMonthOfWeek='+startMonth+'&startDayOfWeek='+startDay+'&source=calendarDetails<%=returnPage!=null?"&return="+returnPage:""%>';
  }
  
  function switchTableClass(E,className,rowOrCell,browser){
    if(rowOrCell == "cell"){
      tdToChange = E;
    }
    if (browser=="ie"){
        while (E.tagName!="TR"){
          E=E.parentElement;
        }
        E.className = className;
    } else {
      while (E.tagName!="TR") {
        E=E.parentNode;
      }
      rowToChange = E;
      resetCalendar();
      if (rowOrCell == "cell") {
        tdToChange.className = className;
        return;
      }
      for (i=0;i<rowToChange.childNodes.length;i++) {
        if (rowToChange.childNodes.item(i).tagName == "TD") {
          rowToChange.childNodes.item(i).className = className;
        }
      }
    }
  }
  function resetCalendar() {
    tableElement = document.getElementById('calendarTable');
    E = tableElement.childNodes.item(0);
    for (i=0;i<E.childNodes.length;i++) {
      if (E.childNodes.item(i).tagName == "TR" && ! (E.childNodes.item(i).getAttribute('name') == "staticrow")) {
        tmpTR = E.childNodes.item(i);
        for (j=0;j<tmpTR.childNodes.length;j++) {
          if (tmpTR.childNodes.item(j).tagName == "TD") {
            tmpTR.childNodes.item(j).className = tmpTR.childNodes.item(j).getAttribute('name');
          }
        }
      }
    }
  }
</script>
<dhv:evaluate if="<%= "true".equals(request.getParameter("reloadCalendarDetails")) %>">
<body onLoad="javascript:reloadCalendarDetails();">
</dhv:evaluate>
<form name="monthBean" action="MyCFS.do?command=MonthView&source=calendar&resetView=true<%= returnPage!=null?"&return="+returnPage:"" %>" method="post">
<%
      CompanyCalendar.setCellPadding(4);
      CompanyCalendar.setCellSpacing(0);
      CompanyCalendar.setSortEvents(true);
      CompanyCalendar.addHolidays();
      CompanyCalendar.setMonthArrows(true);
      CompanyCalendar.setFrontPageView(true);
      CompanyCalendar.setShowSubject(false);
%>
<%= CompanyCalendar.getHtml() %>
<dhv:include name="calendar.legend" none="true">
<table bgcolor="#FFFFFF" width="98%" border="0" cellpadding="4" cellspacing="0">
  <tr>
    <td>
    <table bgcolor="#FFFFFF" width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td nowrap>
          <img border='0' src='images/accounts.gif'> -- Accounts
        </td>
        <td nowrap>
          <img border='0' src='images/alertcall.gif'><img border='0' src='images/box-hold.gif' align="ABSBOTTOM"> -- Calls
        </td>
        <td nowrap>
          <img border='0' src='images/event-holiday.gif'> -- Holidays
        </td>
      </tr>
      <tr>
        <td nowrap>
          <img border='0' src='images/alertopp.gif'> -- Opportunities
        </td>
        <td nowrap>
          <img border='0' src='images/box.gif'> -- Tasks
        </td>
        <td>
          <img border='0' src='images/tree0.gif'> -- <dhv:label name="tickets.tickets">Tickets</dhv:label>
        </td>
      </tr>
      <tr>
        <td colspan="3">
          <img border='0' src='images/box-checked.gif'> -- Complete <dhv:label name="tickets.tickets">Tickets</dhv:label>
        </td>
      </tr>
    </table>
    </td>
  </tr>
</table>
</dhv:include>
</form>
<dhv:evaluate if="<%= "true".equals(request.getParameter("reloadCalendarDetails")) %>">
</body>
</dhv:evaluate>

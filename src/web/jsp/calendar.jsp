<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="com.darkhorseventures.cfsbase.*" %> 
<jsp:useBean id="CompanyCalendar" class="com.darkhorseventures.utils.CalendarView" scope="request"/>
<%--<jsp:useBean id="CalendarInfo" class="com.darkhorseventures.cfsbase.CalendarBean" scope="session"/>--%>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.UserBean" scope="session"/>
<link rel="stylesheet" href="css/template0<%= User.getBrowserIdAndOS() %>.css" type="text/css">
<link rel="stylesheet" href="css/template0.css" type="text/css">

<% 
   String returnPage = (String)request.getParameter("return");
   CalendarBean CalendarInfo = (CalendarBean) session.getAttribute(returnPage!=null?returnPage + "CalendarInfo" :"CalendarInfo");
%>


<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/images.js"></SCRIPT>
<script type="text/javascript">
  function showDayEvents(month,day){
    window.parent.frames['calendardetails'].location.href='MyCFS.do?command=DayView&month='+month+'&day='+day+'&source=calendardetails<%=returnPage!=null?"&return="+returnPage:""%>';
  }
  function showToDaysEvents(thisMonth,thisDay,thisYear){
    window.parent.frames['calendardetails'].location.href='MyCFS.do?command=DayView&month='+thisMonth+'&day='+thisDay+'&year='+thisYear+'&source=calendardetails<%=returnPage!=null?"&return="+returnPage:""%>';
    window.location.href='MyCFS.do?command=MonthView&month='+thisMonth+'&year='+thisYear+'&source=calendar<%=returnPage!=null?"&return="+returnPage:""%>';
  }
  function showWeekEvents(startMonth,startDay){
    window.parent.frames['calendardetails'].location.href='MyCFS.do?command=WeekView&month='+startMonth+'&startMonthOfWeek='+startMonth+'&startDayOfWeek='+startDay+'&source=calendarDetails<%=returnPage!=null?"&return="+returnPage:""%>';
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
      }
      else{
        while (E.tagName!="TR"){
          E=E.parentNode;
          }
          rowToChange = E;
          resetCalendar();
          if(rowOrCell == "cell"){
            tdToChange.className = className;
            return;
          }
          for(i=0;i<rowToChange.childNodes.length;i++){
            if(rowToChange.childNodes.item(i).tagName == "TD"){
              rowToChange.childNodes.item(i).className = className;
            }
          }
      }
    }
    
    
    function resetCalendar(){
      tableElement = document.getElementById('calendarTable');
      E = tableElement.childNodes.item(0);
          for(i=0;i<E.childNodes.length;i++){
            if(E.childNodes.item(i).tagName == "TR" && ! (E.childNodes.item(i).getAttribute('name') == "staticrow")){
              tmpTR = E.childNodes.item(i);
              for(j=0;j<tmpTR.childNodes.length;j++){
                if(tmpTR.childNodes.item(j).tagName == "TD"){
                  tmpTR.childNodes.item(j).className = tmpTR.childNodes.item(j).getAttribute('name');
                }
             }
           }
        }
    }
</script>

<form name="monthBean" action="MyCFS.do?command=MonthView&source=calendar<%=returnPage!=null?"&return="+returnPage:""%>" method="post">
  <%
      CompanyCalendar.setBorderSize(1);
      CompanyCalendar.setCellPadding(4);
      CompanyCalendar.setCellSpacing(0);
      CompanyCalendar.setSortEvents(true);
      CompanyCalendar.addHolidays();
      CompanyCalendar.setMonthArrows(true);
      CompanyCalendar.setFrontPageView(true);
      CompanyCalendar.setShowSubject(false);
  %>
    <%= CompanyCalendar.getHtml() %>
</form>

<%@ page import="org.aspcfs.modules.mycfs.base.CalendarEvent"%>
<jsp:useBean id="cal" class="org.aspcfs.utils.web.CalendarView" scope="page"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<html>
<head>
<title>Calendar</title>
<link rel="stylesheet" href="css/template0<%= User.getBrowserIdAndOS() %>.css" type="text/css">
<link rel="stylesheet" href="css/template0.css" type="text/css">

<% String formName = request.getParameter("form"); %>
<% String element = request.getParameter("element"); %>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
  function openWindow(month, day, year) {
    width = 600;
    height = 400;
    url='schedule.jsp?month=' + month + 
          '&&year=' + year + '&&day=' + day;
    Win = open(url, 'as_events', 'toolbar=0,location=0,directories=0,status=0,menubar=0,resizable=1,width=' + width + ',height=' + height + ',scrollbars=yes');
    Win.focus();
  }

  function returnDate(dayVal, monVal, yearVal) {
    opener.document.<%= formName %>.<%= element %>.value = monVal + '/' + dayVal + '/' + yearVal; 
    window.close();
  }
</script>
</head>

<body>
<form name="monthBean" action="month.jsp">
<%
  //Retrieve the parameters
  String year = request.getParameter("year");
  String month = request.getParameter("month");
  String day = request.getParameter("day");
  String origDay = request.getParameter("origDay");
  String origYear = request.getParameter("origYear");
  String origMonth = request.getParameter("origMonth");
  String dateString = request.getParameter("date");

  //If the user clicks the next/previous arrow, increment/decrement the month
  //Range checking is not necessary on the month.  The calendar object automatically
  //increments the year when necessary
  if (month != null) {
    try {
      int monthTmp = Integer.parseInt(month);
      if (request.getParameter("next.x") != null) { ++monthTmp; }
      if (request.getParameter("prev.x") != null) { monthTmp += -1; }
      month = "" + monthTmp;
    } catch(NumberFormatException e) {
    }
  }
  
  //Check to see if this should be a popup window
  String action = request.getParameter("action");
  if ((action != null) && (action.equals("popup"))) {
    out.println("<input type=\"hidden\" name=\"action\" value=\"popup\">");
    cal.setPopup(true);
    cal.setBorderSize(0);
  } else {
    cal.setHeaderSpace(true);
    cal.setMonthArrows(true);
    cal.setBorderSize(1);
    //cal.showWeekSelector(false);    //Not implemented yet
  }
  
  if (dateString != null) {
  
    String tmp1;
    String tmp2;
    String tmp3;
  
    if (dateString.indexOf("/") > 0) {
      java.util.StringTokenizer ds = new java.util.StringTokenizer(dateString, "/");
      if (ds.countTokens() == 3) {
        tmp1 = ds.nextToken();
        tmp2 = ds.nextToken();
        tmp3 = ds.nextToken();
        month = tmp1; 
        day = tmp2;
        year = tmp3;
      }
    } else if (dateString.indexOf("-") > 0) {
      java.util.StringTokenizer ds = new java.util.StringTokenizer(dateString, "-");
      if (ds.countTokens() == 3) {
        tmp1 = ds.nextToken();
        tmp2 = ds.nextToken();
        tmp3 = ds.nextToken();
        if (tmp1.length() == 4) {        
          year = tmp1;
          month = tmp2; 
          day = tmp3;
        } else {
          month = tmp1;
          day = tmp2;
          year = tmp3;
        }
      }
    }
    
  } 

  cal.setYear(year);
  cal.setMonth(month);
  cal.setDay(day);
  
  //Configure the month to highlight a date that was passed in
  String origStatus = request.getParameter("origStatus");
  if ((origStatus == null) && ((day == null) || (day.startsWith("undefined")))) {
    origStatus = "blank";
  } else if (origStatus == null) {
    origStatus = "highlight";
  }
  //Restore the original request date from the calling app,
  //this allows the calling date to be highlighted differently from
  //other dates
  if ((origYear != null) && (origMonth != null) && (origDay != null)) {
  } else {
    origYear = cal.getYear();
    origMonth = cal.getMonth();
    origDay = cal.getDay();
  }
  cal.addEvent(origMonth + "/" + origDay + "/" + origYear, "", origStatus);
%>
<input type="hidden" name="origYear" value="<%= origYear %>">
<input type="hidden" name="origMonth" value="<%= origMonth %>">
<input type="hidden" name="origDay" value="<%= origDay %>">
<input type="hidden" name="origStatus" value="<%= origStatus %>">
<input type="hidden" name="form" value="<%= formName %>">
<input type="hidden" name="element" value="<%= element %>">
<%= cal.getHtml() %>
</form>
</body>
</html>

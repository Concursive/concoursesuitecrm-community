<html>
<head>
<title>Calendar</title>

<style type="text/css">
  A:link { color: blue }       /* unvisited link */
  A:visited { color: blue }   /* visited links */
  A:active { color: red }    /* active links */
  body {background: white; font-family: arial,helvetica; }
  .calendar { background: #BFBFBB; }
  .monthName { background: #3366CC; color: #FFFFFF; font-size: 12pt; font-weight: bold; text-align: center; }
  .monthArrowPrev { background: #3366CC; color: #FFFFFF; font-size: 12pt; font-weight: bold; text-align: center; }
  .monthArrowNext { background: #3366CC; color: #FFFFFF; font-size: 12pt; font-weight: bold; text-align: center; }
  .weekName { background: #CCCCCC; color: #000000; font-size: 8pt; text-align: center; }
  .day { background: white; color: #000000; font-size: 8pt; font-weight: normal; text-align: left; }
  .highlightday { background: #66FF66; color: #000000; font-size: 8pt; font-weight: normal; text-align: left; }
  .noday { background: #FEF8DE; color: #000000; font-size: 8pt; font-weight: normal; text-align: left; }
  .today { background: #FFFFA6; color: #000000; font-size: 8pt; font-weight: bold; text-align: left; }

  .smallcalendar { background: #FFFFFF; }
  .smallmonthName { background: #3366CC; color: #FFFFFF; font-size: 8pt; font-weight: bold; text-align: center; }
  .smallmonthArrowPrev { background: #3366CC; color: #FFFFFF; font-size: 8pt; font-weight: bold; text-align: center; }
  .smallmonthArrowNext { background: #3366CC; color: #FFFFFF; font-size: 8pt; font-weight: bold; text-align: center; }
  .smallweekName { background: #CCCCCC; color: #000000; font-size: 8pt; text-align: center; }  
  .smallday { background: white; color: #000000; font-size: 8pt; font-weight: normal; text-align: right; }
  .smallhighlightday { background: white; color: #FF0000; font-size: 8pt; font-weight: normal; text-align: right; }
  .smallnoday { background: #EEEEEE; color: #000000; font-size: 8pt; font-weight: normal; text-align: right; }
  .smalltoday { background: #FFFFA6; color: #000000; font-size: 8pt; font-weight: bold; text-align: right; }
  
  .date { color: #3366CC; font-size: 8pt; font-weight: bold; }
  .time { color: #000000; font-size: 8pt; }
  .appt { color: #000000; font-size: 8pt; }
  .underline { text-decoration: underline; }
  .smallfooter { color: #000000; font-size: 8pt; text-align: center; }
</style>

<jsp:useBean id="cal" class="com.darkhorseventures.utils.CalendarView" scope="page"/>

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

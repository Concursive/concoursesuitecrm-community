<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%
  String month = request.getParameter("month");
  String day = request.getParameter("day");
  String year = request.getParameter("year");
  String language = request.getParameter("language");
  String country = request.getParameter("country");
  if (language == null) {
    language = "en";
    country = "US";
  }
  Locale locale = new Locale(language, country);
  SimpleDateFormat formatter = (SimpleDateFormat)SimpleDateFormat.getDateInstance(DateFormat.SHORT, locale);
  formatter.applyPattern(formatter.toPattern() + "yy");
  Calendar cal = Calendar.getInstance();
  cal.set(Calendar.YEAR, Integer.parseInt(year));
  cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
  cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
  String formattedDate = formatter.format(cal.getTime());
%>
<html>
<head>
</head>
<body onload='page_init();'>
<script language='Javascript'>
function page_init() {
  parent.formatDate('<%= formattedDate %>');
}
</script>
</body>
</html>

<jsp:useBean id="CompanyCalendar" class="org.aspcfs.utils.web.CalendarView" scope="request"/>
<%@ include file="../initPage.jsp" %>
<b>Company Calendar</b>
<hr color="#BFBFBB" noshade>
<a href="#">Month View</a>&nbsp;&nbsp;<a href="#">Add Event</a><p>
<form action="CompanyDirectory.do?command=ViewCalendar" method="post">
<%= CompanyCalendar.getHtml() %>
</form>


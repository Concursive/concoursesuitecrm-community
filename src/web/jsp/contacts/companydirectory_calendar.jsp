<%-- Import required libraries --%>
<jsp:useBean id="CompanyCalendar" class="com.darkhorseventures.utils.CalendarView" scope="request"/>
<%@ include file="initPage.jsp" %>
<b>Company Calendar</b>
<hr color="#BFBFBB" noshade>
<a href="#">Month View</a>&nbsp;&nbsp;<a href="#">Add Event</a><p>
<form action='/CompanyDirectory.do?command=ViewCalendar' method='post'>
<%= CompanyCalendar.getHtml() %>
</form>


<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="CompanyCalendar" class="com.darkhorseventures.utils.CalendarView" scope="request"/>
<jsp:useBean id="NewUserList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="orgAlertPaged" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<a href="/Accounts.do">Account Management</a> > 
Dashboard<br>
<hr color="#BFBFBB" noshade>
<table bgcolor="#FFFFFF" border="0" width="100%">
<tr>
<form name="monthBean" action="Accounts.do?command=Dashboard" method="post">
<td valign="top" bgcolor="#FFFFFF" width="300">
<%  
      CompanyCalendar.setBorderSize(1);
      CompanyCalendar.setCellPadding(4);
      CompanyCalendar.setCellSpacing(0);
      CompanyCalendar.setSortEvents(true);
      CompanyCalendar.setMonthArrows(true);
      CompanyCalendar.setFrontPageView(true);
      CompanyCalendar.setShowSubject(false);
%>
      <%= CompanyCalendar.getHtml() %>
</td>
  
<td bgcolor="#FFFFFF" valign="top" height="100%" width="100%">
	<table bgcolor="#FFFFFF" height="100%" width="100%" border="1" cellpadding="1" cellspacing="0" bordercolorlight="#000000" bordercolor="#FFFFFF">
	<tr>
	<td width="100%">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
		<tr class="title">
       		<td width="60%" valign="center">
                <strong>Alerts</strong>
              	</td>
              	<td valign="center" align="right">
		<% if (request.getParameter("userId") == null || request.getParameter("userId").equals("")) { %>
			<%= NewUserList.getHtmlSelect("userId",0) %>
		<% } else { %>
			<%=NewUserList.getHtmlSelect("userId",Integer.parseInt(request.getParameter("userId")))%>
		<%}%>
              	</td>
            	</tr>
          	</table>
        </td>
      	</tr>
      	<tr>
        <td height="100%" width="100%" valign="top">
	<table width="100%" cellspacing="1" cellpadding="0" border="0">
	
	<%    
   	Iterator days = (CompanyCalendar.getEvents(5)).iterator();
   	if (days.hasNext()) {
		boolean showToday = false;
		Calendar today = Calendar.getInstance();
		today.setTime(new java.util.Date());
		
		while (days.hasNext()) {
       		CalendarEventList thisDay = (CalendarEventList)days.next();
		Calendar thisCal = Calendar.getInstance();
		thisCal.setTime(thisDay.getDate());
		boolean isToday = 
			((thisCal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) &&
			(thisCal.get(Calendar.YEAR) == today.get(Calendar.YEAR)));
	  
		if (showToday == false && !isToday) {
		%>       
		<tr>
		<td colspan="2">
		<strong><%= toFullDateString(new java.util.Date()) %> (Today)</strong>
		</td>
		</tr>
		<tr>
		<td valign="top" nowrap>
		&nbsp;&nbsp;
		</td>
		<td width="100%" valign="top">
		No alerts found.<br>&nbsp;
		</td>
		</tr>
		<%     }
		showToday = true;
		%>
		
		<tr>
		<td colspan="2">
		<strong>
		<%= toFullDateString(thisDay.getDate()) %>
		<dhv:evaluate exp="<%= isToday %>">(Today)</dhv:evaluate>
		</strong>
		</td>
		</tr>
       
		<%
       		Iterator eventList = thisDay.iterator();
      		while (eventList.hasNext()) {
         	CalendarEvent thisEvent = (CalendarEvent)eventList.next();
		%>
       		<tr>
         	<td valign="top" nowrap>
           	<%= thisEvent.getIcon() %>&nbsp;
         	</td>
         	<td width="100%" valign="top">
           	<%= thisEvent.getLink() %><%= thisEvent.getSubject() %></a><%= ((!eventList.hasNext() && days.hasNext())?"<br>&nbsp;":"") %>
         	</td>
       		</tr>
		<%
       		}
     		}
   } else {
%>
       <tr>
         <td colspan="2">
           <strong><%= toFullDateString(new java.util.Date()) %> (Today)</strong>
         </td>
       </tr>
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
</form>
</tr>
</table>
</body>

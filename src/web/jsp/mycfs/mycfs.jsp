<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="NewsList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.UserBean" scope="session"/>
<jsp:useBean id="NewUserList" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<jsp:useBean id="IndSelect" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/tasks.js"></script>
<%@ include file="initPage.jsp" %>

<% 
  String returnPage = request.getParameter("return");
   CalendarBean CalendarInfo = (CalendarBean) session.getAttribute(returnPage!=null?returnPage + "CalendarInfo" :"CalendarInfo");
   if(CalendarInfo==null){
       CalendarInfo = new CalendarBean();
       session.setAttribute(returnPage!=null?returnPage + "CalendarInfo":"CalendarInfo",CalendarInfo);
   }
%>


<script type="text/javascript">
  function fillFrame(frameName,sourceUrl){
    window.frames[frameName].location.href=sourceUrl;
  }
  function reloadFrames(){
    window.frames['calendar'].location.href='MyCFS.do?command=MonthView&source=Calendar';
    window.frames['calendardetails'].location.href='MyCFS.do?command=Alerts&source=calendarDetails';
  }
</script>

<table bgcolor="#FFFFFF" border="0" width="100%" class="tableBorder" cellspacing="0">
<tr valign="top" class="title">
   <td width="100%" align="center" colspan="2"> <strong><div id="userName"> User: <%= CalendarInfo.getSelectedUserId()!=-1?toHtml(CalendarInfo.getSelectedUserName()) : toHtml(User.getUserRecord().getContact().getNameLast() + (User.getUserRecord().getContact().getNameFirst().equals("")?"":" ,"+User.getUserRecord().getContact().getNameFirst().equals("")))%></div></strong></td>
</tr>
<tr style="visibility:hidden"><td></td></tr>
<tr valign="top">
  <td valign="top" bgcolor="#FFFFFF" width="300">
    <iframe id="calendarid" name="calendar" frameborder="0" marginwidth="0" marginheight="0" width="310" height="360" src="MyCFS.do?command=MonthView&source=Calendar<%=returnPage!=null?"&return="+returnPage:""%>">
    </iframe>
  </td>
<td bgcolor="#FFFFFF" valign="top" height="100%" width="100%">
  <table bgcolor="#FFFFFF" height="100%" width="99%" border="1" cellpadding="0" cellspacing="0" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr height="6%" class="title">
        <td width="100%">
          <table width="100%" cellspacing="0" cellpadding="0" border="0">
            <tr>
              <td width="60%" valign="center">
               <select id="alerts" size="1" name="alertsView" onChange="javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=calendardetails&alertsView='+document.getElementById('alerts').value);">
                <option  value="All"      <%= CalendarInfo.getCalendarDetailsView().equalsIgnoreCase("all")?" selected":"" %>>All Scheduled Actions</option>
                <option  value="Call"    <%= CalendarInfo.getCalendarDetailsView().equalsIgnoreCase("call")?" selected":"" %>>Calls</option>
                <option  value="Task"    <%= CalendarInfo.getCalendarDetailsView().equalsIgnoreCase("task")?" selected":"" %>>Tasks</option>
                <option  value="Project" <%= CalendarInfo.getCalendarDetailsView().equalsIgnoreCase("project")?" selected":"" %>>Projects</option>
                <option  value="Opportunity"     <%= CalendarInfo.getCalendarDetailsView().equalsIgnoreCase("opportunity")?" selected":"" %>>Opportunities</option>
                <option  value="Accounts" <%= CalendarInfo.getCalendarDetailsView().equalsIgnoreCase("accounts")?" selected":"" %>>Accounts</option>
               </select>
              </td>
              <td valign="center" align="right">
              <% if(NewUserList.size()!=0){%>
              <%
                  int userId = CalendarInfo.getSelectedUserId();
              %>
                  <%= NewUserList.getHtml("userId",userId)%>
              <%}%>
              </td>
            </tr>
          </table>
        </td>
  </tr>
  <tr>
  <td width="100%" valign="top" height="90%">
    <iframe id="calendardetailsid" name="calendardetails" frameborder="0" marginheight="0" width="99%" height="88%" src="MyCFS.do?command=Alerts&source=calendarDetails<%=returnPage!=null?"&return="+returnPage:""%>">
    </iframe>
    </td>
  </tr>
  </table>
</td>
<!-- end ZZ:row one -->
</tr>
</table>
<br>
<dhv:permission name="myhomepage-miner-view">

<!-- ZZ:row two (spans both row-one columns) -->
<table bgcolor="#FFFFFF" border="0" width="100%" class="tableBorder" cellpadding="0" cellspacing="0">
<tr>
<td colspan=2>
<%
  Iterator j = NewsList.iterator();
%>
	<form name="miner_select" type="get" action="MyCFS.do">
	<input type=hidden name="command" value="Home">
	<table cellpadding="3" cellspacing="0" border="0" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
	<tr bgcolor="#DEE0FA"><td valign=center colspan=2>

	<table bgcolor="#DEE0FA" width=100% cellspacing="0" cellpadding="0" border="0">
	<tr><td width=60% valign=center>
	<strong>Personalized Industry News &amp; Events</strong>
	</td>
	<td width= 40% align="right" valign=center>
    
    	<% if (request.getParameter("industry") == null || request.getParameter("industry").equals("")) { %>
    		<%=IndSelect.getHtmlSelect("industry",1)%>
	<% } else { %>
		<%=IndSelect.getHtmlSelect("industry",Integer.parseInt(request.getParameter("industry")))%>
	<%}%>
	
	</td>
	</tr>
	</table>
	</td>
</tr>
<%
	if ( j.hasNext() ) {
		while (j.hasNext()) {
      			NewsArticle thisNews = (NewsArticle)j.next();
		%>      

		<tr bgcolor="white">
		<td width=11% valign=center>
		<%= thisNews.getDateEntered() %>
		</td>

		<td valign=center><a href="<%= thisNews.getUrl() %>" target="_new"><%= thisNews.getHeadline() %></a></td></tr>
		<%}
	} else {%>
		<tr bgcolor="white"><td width=100% valign=center>No news found.</td></tr>
	<%}%>
			
	</table>
	</form>

<%  
%>
<!-- end ZZ:row two -->
</tr>

</dhv:permission>

<!-- End Table ZZ -->
</table>



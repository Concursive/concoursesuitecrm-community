<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*,org.aspcfs.modules.accounts.base.NewsArticle,org.aspcfs.modules.mycfs.beans.*" %>
<jsp:useBean id="NewsList" class="org.aspcfs.modules.accounts.base.NewsArticleList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="NewUserList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="IndSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></script>
<%@ include file="../initPage.jsp" %>
<%
  //returnPage specifies the source of the request ( Accounts/ Home Page ) 
  String returnPage = request.getParameter("return");
   CalendarBean CalendarInfo = (CalendarBean) session.getAttribute(returnPage != null ?returnPage + "CalendarInfo" :"CalendarInfo");
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
    window.frames['calendar'].location.href='MyCFS.do?command=MonthView&source=Calendar&inline=true';
    window.frames['calendardetails'].location.href='MyCFS.do?command=Alerts&source=calendarDetails&inline=true';
  }
</script>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr>
    <td>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
  <%-- User Selected Info --%>
  <tr>
    <td width="100%" align="center" valign="top" colspan="2" height="20">
      <strong><div id="userName">Scheduled Actions for <%= CalendarInfo.getSelectedUserId()!=-1?toHtml(CalendarInfo.getSelectedUserName()) : toHtml(User.getUserRecord().getContact().getNameLastFirst())%></div></strong>
    </td>
  </tr>
  <%-- Calendar and Details --%>
  <tr valign="top">
    <td valign="top" width="320">
      <iframe id="calendarid" name="calendar" frameborder="0" marginwidth="0" marginheight="0" width="320" height="400" src="MyCFS.do?command=MonthView&source=Calendar<%= returnPage != null ? "&return="+returnPage : "" %>">
      </iframe>
    </td>
    <td valign="top" height="100%" width="100%">
      <table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title">
          <td width="100%">
          
          <table width="100%" cellspacing="0" cellpadding="0" border="1" bordercolorlight="#000000" bordercolor="#FFFFFF">
            <tr>
              <td>
            <table width="100%" cellspacing="4" cellpadding="0" border="0" class="title">
              <tr class="title">
                <td width="60%" valign="center">
                 <select id="alerts" size="1" name="alertsView" onChange="javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=calendardetails&inline=true&alertsView='+document.getElementById('alerts').value);">
                  <option value="all" <%= CalendarInfo.getCalendarDetailsView().equalsIgnoreCase("all") ? " selected":"" %>>All Scheduled Actions</option>
                   <% 
                    Iterator alertTypes = CalendarInfo.getAlertTypes().iterator();
                    
                    while(alertTypes.hasNext()){
                    AlertType thisAlert = (AlertType)alertTypes.next();
                    boolean isSelected = CalendarInfo.getCalendarDetailsView().equalsIgnoreCase(thisAlert.getName());
                   %>
                      <option value="<%= thisAlert.getName() %>" <%= isSelected ? " selected":"" %>><%= thisAlert.getDisplayName() %></option>
                   <%
                    }
                   %>
                 </select>
                </td>
                <td valign="center" align="right">
                <% if(NewUserList.size()!=0){%>
                  <%= NewUserList.getHtml("userId",CalendarInfo.getSelectedUserId()) %>
                <%}%>
                </td>
              </tr>
            </table>
              </td>
            </tr>
          </table>
            
          </td>
        </tr>
        <tr>
          <td width="100%" valign="top" height="100%">
            <iframe id="calendardetailsid" name="calendardetails" frameborder="0" marginheight="0" width="100%" height="100%" src="MyCFS.do?command=Alerts&source=calendarDetails<%=returnPage!=null?"&return="+returnPage:""%>">
            </iframe>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

    </td>
  </tr>
</table>
<%-- Next section --%>
<br>
<dhv:permission name="myhomepage-miner-view">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td>
      <input type="hidden" name="command" value="Home">
      <table cellpadding="0" cellspacing="0" border="0" width="100%">
        <form name="miner_select" type="get" action="MyCFS.do">
          <tr class="title">
            <td width="60%" valign="center">
              <strong>Personalized Industry News &amp; Events</strong>
            </td>
            <td width="40%" align="right" valign="center">
    	<% if (request.getParameter("industry") == null || request.getParameter("industry").equals("")) { %>
              <%=IndSelect.getHtmlSelect("industry",1)%>
      <% } else { %>
              <%=IndSelect.getHtmlSelect("industry",Integer.parseInt(request.getParameter("industry")))%>
       <%}%>
            </td>
          </tr>
        </form>
      </table>
    </td>
  </tr>
<%
	Iterator j = NewsList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
		while (j.hasNext()) {
      rowid = (rowid == 1?2:1);
      NewsArticle thisNews = (NewsArticle)j.next();
		%>      
  <tr class="row<%= rowid %>">
    <td width="11%" valign="center">
      <%= thisNews.getDateEntered() %>
    </td>
    <td width="100%" valign="center">
      <a href="<%= thisNews.getUrl() %>" target="_new"><%= thisNews.getHeadline() %></a>
    </td>
  </tr>
		<%}
	} else {%>
  <tr class="row2"><td>No news found.</td></tr>
	<%}%>
</table>
</dhv:permission>


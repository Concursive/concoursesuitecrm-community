<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*,org.aspcfs.modules.accounts.base.NewsArticle,org.aspcfs.modules.mycfs.beans.*" %>
<%@ page import="org.aspcfs.modules.quotes.base.*" %>
<%@ page import="org.aspcfs.modules.troubletickets.base.*" %>
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

  function reopen(){
    window.location.href='MyCFS.do?command=Home';
  }

  function reloadFrames(){
    window.frames['calendar'].location.href='MyCFS.do?command=MonthView&source=Calendar&inline=true';
    window.frames['calendardetails'].location.href='MyCFS.do?command=Alerts&source=calendarDetails&inline=true';
  }
</script>

<table cellpadding="4" cellspacing="0" border="0" width="100%" style="border: 1px solid #000">
  <tr>
    <td>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
  <%-- User Selected Info --%>
  <dhv:permission name="products-view" none="true">
  <tr>
    <td width="100%" valign="top" colspan="2" height="20" style="text-align: center !important">
      <strong><div id="userName">Scheduled Actions for <%= CalendarInfo.getSelectedUserId()!=-1?toHtml(CalendarInfo.getSelectedUserName()) : toHtml(User.getUserRecord().getContact().getNameLastFirst())%></div></strong>
    </td>
  </tr>
  </dhv:permission>
  <%-- AdsJet users only --%>
  <dhv:permission name="products-view">
  <tr>
    <td colspan="2">
      <table class="pagedListHeader" cellspacing="0">
        <tr>
          <td align="center">
            <strong>Welcome to AdsJet.com.</strong>
          </td>
        </tr>
      </table>
      <table class="pagedListHeader2" cellspacing="0">
        <tr>
          <th>
            You are currently at the "My Home Page" tab in which you can 
            review the status of your pending ad requests and orders.
            From the "Products &amp; Services" tab you can review 
            publication information, as well as manage and place ads.
          </th>
        </tr>
      </table>
    </td>
  </tr>
  </dhv:permission>
  <%-- Calendar and Details --%>
  <tr valign="top">
    <%-- Left cell --%>
    <td valign="top" width="320">
      <iframe id="calendarid" name="calendar" frameborder="0" marginwidth="0" marginheight="0" width="320" height="400" src="MyCFS.do?command=MonthView&source=Calendar<%= returnPage != null ? "&return="+returnPage : "" %>&reloadCalendarDetails=true">
      </iframe>
    </td>
    <%-- Right cell --%>
    <td valign="top" height="380" width="100%"><%-- Change height to 100% once Safari works in all places --%>
      <%-- Calendar details --%>
      <table height="380" width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="100%" class="cellBox">
            <table width="100%" cellspacing="4" cellpadding="0" border="0" class="empty">
              <tr>
                <td valign="center" nowrap>
                 <select id="alerts" size="1" name="alertsView" onChange="javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=calendardetails&inline=true&alertsView='+document.getElementById('alerts').value);">
                  <option value="all" <%= CalendarInfo.getCalendarDetailsView().equalsIgnoreCase("all") ? " selected":"" %>>Pending Items</option>
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
                <% if(NewUserList.size()>1){%>
                  <%= NewUserList.getHtml("userId",CalendarInfo.getSelectedUserId()) %>
                <%}%>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td width="100%" valign="top" height="380">
            <iframe id="calendardetailsid" name="calendardetails" frameborder="0" marginheight="0" width="100%" height="380" src="empty.html">
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
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <td>
      <input type="hidden" name="command" value="Home">
      <table cellpadding="0" cellspacing="0" border="0" width="100%">
        <form name="miner_select" type="get" action="MyCFS.do">
          <tr>
            <th width="60%" valign="center">
              <strong>Personalized Industry News &amp; Events</strong>
            </th>
            <td width="40%" style="text-align: right;" valign="center">
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
  <tr class="containerBody"><td>No news found.</td></tr>
	<%}%>
</table>
</dhv:permission>


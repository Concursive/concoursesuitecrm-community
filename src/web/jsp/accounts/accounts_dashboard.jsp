<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="NewUserList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/tasks.js"></script>
<%@ include file="../initPage.jsp" %>
<% 
  String returnPage = (String)request.getAttribute("Return");
   CalendarBean CalendarInfo = (CalendarBean) session.getAttribute(returnPage!=null?returnPage + "CalendarInfo" :"CalendarInfo");
%>
<script type="text/javascript">
  function fillFrame(frameName,sourceUrl){
    window.frames[frameName].location.href=sourceUrl;
  }
  function reloadFrames(){
    window.frames['calendar'].location.href='MyCFS.do?command=MonthView&source=Calendar&return=<%=returnPage%>&inline=true';
    window.frames['calendardetails'].location.href='MyCFS.do?command=Alerts&source=calendarDetails&return=<%=returnPage%>&inline=true';
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
      <iframe id="calendarid" name="calendar" frameborder="0" marginwidth="0" marginheight="0" width="320" height="400" src="MyCFS.do?command=MonthView&source=Calendar<%=returnPage!=null?"&return="+returnPage:""%>">
      </iframe>
    </td>
    <td valign="top" height="100%" width="100%">
      <table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title">
          <td width="100%">
          
          <table width="100%" cellspacing="0" cellpadding="0" border="1" class="pagedList" bordercolorlight="#000000" bordercolor="#FFFFFF">
            <tr>
              <td>
            <table width="100%" cellspacing="4" cellpadding="0" border="0" class="title">
              <tr class="title">
                <td width="60%" valign="center">
                  <select id="alerts" size="1" name="alertsView" onChange="javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=calendardetails&inline=true&return=Accounts&alertsView='+document.getElementById('alerts').value);">
                    <option  value="AccountsAll" <%= CalendarInfo.getCalendarDetailsView().equalsIgnoreCase("AccountsAll")?" selected":"" %>>All Scheduled Actions</option>
                    <option  value="AccountsContractEndDates" <%= CalendarInfo.getCalendarDetailsView().equalsIgnoreCase("AccountsEndDates")?" selected":"" %>>Contract End Dates</option>
                    <option  value="AccountsAlertDates" <%= CalendarInfo.getCalendarDetailsView().equalsIgnoreCase("AccountsAlertDates")?" selected":"" %>>Alert Dates</option>
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


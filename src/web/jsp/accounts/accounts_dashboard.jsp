<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.mycfs.beans.CalendarBean" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="NewUserList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></script>
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
<table cellpadding="4" cellspacing="0" border="0" width="100%" style="border: 1px solid #000">
  <tr>
    <td>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
  <%-- User Selected Info --%>
  <tr>
    <td width="100%" valign="top" colspan="2" height="20" style="text-align: center !important">
      <strong><div id="userName">Scheduled Actions for <%= CalendarInfo.getSelectedUserId()!=-1?toHtml(CalendarInfo.getSelectedUserName()) : toHtml(User.getUserRecord().getContact().getNameLastFirst())%></div></strong>
    </td>
  </tr>
  <%-- Calendar and Details --%>
  <tr valign="top">
    <td valign="top" width="320">
      <iframe id="calendarid" name="calendar" frameborder="0" marginwidth="0" marginheight="0" width="320" height="400" src="MyCFS.do?command=MonthView&source=Calendar<%=returnPage!=null?"&return="+returnPage:""%>&reloadCalendarDetails=true">
      </iframe>
    </td>
    <td valign="top" height="380" width="100%"><%-- Change height 380 to 100% once Safari is udpated --%>
      <table height="380" width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="100%" style="border: 1px solid #000; background-color: #DEE0FA;">
            <table width="100%" cellspacing="4" cellpadding="0" border="0">
              <tr>
                <td valign="center" nowrap>
                  <select id="alerts" size="1" name="alertsView" onChange="javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=calendardetails&inline=true&return=Accounts&alertsView='+document.getElementById('alerts').value);">
                    <option value="AccountsAll" <%= CalendarInfo.getCalendarDetailsView().equalsIgnoreCase("AccountsAll")?" selected":"" %>>All Sched. Actions</option>
                    <option value="AccountsContractEndDates" <%= CalendarInfo.getCalendarDetailsView().equalsIgnoreCase("AccountsEndDates")?" selected":"" %>>Contract End Dates</option>
                    <option value="AccountsAlertDates" <%= CalendarInfo.getCalendarDetailsView().equalsIgnoreCase("AccountsAlertDates")?" selected":"" %>>Alert Dates</option>
                  </select>
                <% if(NewUserList.size()!=0){%>
                  <%= NewUserList.getHtml("userId",CalendarInfo.getSelectedUserId()) %>
                <%}%>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td width="100%" valign="top" height="380">
            <iframe id="calendardetailsid" name="calendardetails" frameborder="0" marginheight="0" width="100%" height="380">
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


<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.UserBean" scope="session"/>
<jsp:useBean id="NewUserList" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/tasks.js"></script>
<%@ include file="initPage.jsp" %>

<% 
  String returnPage = (String)request.getAttribute("Return");
   CalendarBean CalendarInfo = (CalendarBean) session.getAttribute(returnPage!=null?returnPage + "CalendarInfo" :"CalendarInfo");
%>


<script type="text/javascript">
  function fillFrame(frameName,sourceUrl){
    window.frames[frameName].location.href=sourceUrl;
  }
  function reloadFrames(){
    window.frames['calendar'].location.href='MyCFS.do?command=MonthView&source=Calendar&return=<%=returnPage%>';
    window.frames['calendardetails'].location.href='MyCFS.do?command=Alerts&source=calendarDetails&return=<%=returnPage%>';
  }
</script>

<table bgcolor="#FFFFFF" border="0" width="100%" class="tableBorder" cellspacing="0">
<tr valign="top" class="title">
   <td width="100%" align="center" colspan="2"> <strong><div id="userName"> User: <%= CalendarInfo.getSelectedUserId()!=-1?toHtml(CalendarInfo.getSelectedUserName()) : toHtml(User.getUserRecord().getContact().getNameLast() + "," + User.getUserRecord().getContact().getNameFirst()) %></div></strong></td>
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
               <strong>Account Alerts</strong>
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
    <%--<iframe id="calendardetailsid" name="calendardetails" frameborder="0" marginheight="0" width="99%" height="88%" src="images/arrowright.gif" onblur="return false">--%>
    </iframe>
    </td>
  </tr>
  </table>
</td>
<!-- end ZZ:row one -->
</tr>
</table>
<br>


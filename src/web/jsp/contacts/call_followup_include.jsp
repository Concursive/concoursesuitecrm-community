<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat,org.aspcfs.utils.DatabaseUtils" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><%= CallDetails.getAlertDate() == null ? "Follow-up Activity Reminder" : "Modify Activity"%></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Type
    </td>
    <td>
      <%= CallTypeList.getHtmlSelect("alertCallTypeId", CallDetails.getAlertCallTypeId()) %><font color="red">*</font><%= showAttribute(request, "followupTypeError") %>
    </td>
  </tr>
    <tr class="containerBody">
    <td class="formLabel">
      Date
    </td>
    <td>
      <%-- TODO: If no time set, default to 8:30 AM, or user's daily start time --%>
      <zeroio:dateSelect form="addCall" field="alertDate" timestamp="<%= CallDetails.getAlertDate() %>" />
      at
      <zeroio:timeSelect baseName="alertDate" value="<%= CallDetails.getAlertDate() %>" timeZone="<%= User.getTimeZone() %>"/>
      <zeroio:tz timestamp="<%= new java.util.Date() %>" pattern="z"/>
      <font color="red">*</font><%= showAttribute(request, "alertDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Description
    </td>
    <td>
      <input type="text" size="50" maxlength="255" name="alertText" value="<%= toHtmlValue(CallDetails.getAlertText()) %>"><font color="red">*</font><%= showAttribute(request, "descriptionError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      Notes
    </td>
    <td>
      <TEXTAREA NAME="followupNotes" ROWS="3" COLS="50"><%= toString(CallDetails.getFollowupNotes()) %></TEXTAREA>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      Priority
    </td>
    <td>
       <%= PriorityList.getHtmlSelect("priorityId", CallDetails.getPriorityId()) %><font color="red">*</font><%= showAttribute(request, "priorityError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      Reminder
    </td>
    <td>
      <input type="radio" name="reminder" value="0" <%= CallDetails.getReminderId() > 0 ? "" : " checked"%>>Do not send a reminder<br>
      <input type="radio" name="reminder" value="1" <%= CallDetails.getReminderId() > 0 ? " checked" : ""%>>Send a reminder <input type="text" size="2" name="reminderId" value="<%= CallDetails.getReminderId() > -1 ? CallDetails.getReminderId() : 5 %>">
      <%= ReminderTypeList.getHtmlSelect("reminderTypeId", CallDetails.getReminderTypeId()) %> before the due date
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Assigned to...
    </td>
    <td>
      <table class="empty">
        <tr>
          <td>
            <div id="changeowner">
            <%if(CallDetails.getOwner() > 0){ %>
              <dhv:username id="<%= CallDetails.getOwner() %>"/>
            <% }else{ %>
               <dhv:username id="<%= User.getUserId() %>"/>
            <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="owner" id="ownerid" value="<%= CallDetails.getOwner() == -1 ? User.getUserRecord().getId() : CallDetails.getOwner() %>">
            &nbsp;[<a href="javascript:popContactsListSingle('ownerid','changeowner', 'usersOnly=true&reset=true&filters=employees|accountcontacts|mycontacts|myprojects|all');">Change Owner</a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

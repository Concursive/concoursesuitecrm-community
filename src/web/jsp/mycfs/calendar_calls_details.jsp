<%-- 
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: calendar_calls_details.jsp 15115 2006-05-31 16:47:51Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="ReminderTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CallResult" class="org.aspcfs.modules.contacts.base.CallResult" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<form name="addCall" action="javascript:popURL('CalendarCalls.do?command=Modify&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %>&orgId=<%= CallDetails.getOrgId() %><%= "pending".equals(request.getParameter("view"))?"&view=pending":"" %>&popup=true', null,'630','425','yes','yes')" method="post">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<%
  String trailSource = request.getParameter("trailSource");
%>
<script type="text/javascript">
  function reloadFrames(){
    window.location=window.location;
  }
</script>
<dhv:container name="contacts" selected="calls" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' hideContainer="<%= isPopup(request) %>">
    <dhv:evaluate if="<%=ContactDetails.getEnabled() && !ContactDetails.isTrashed() && !CallDetails.isTrashed() && User.getUserId() == CallDetails.getOwner()%>">
      <dhv:evaluate if='<%= "pending".equals(request.getParameter("view")) && User.getUserId() == CallDetails.getOwner() %>'>
        <dhv:permission name="accounts-accounts-contacts-calls-edit"><input type="button" value="<dhv:label name="global.button.complete">Complete</dhv:label>" onClick="javascript:window.location.href='CalendarCalls.do?command=Complete&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %><%= "pending".equals(request.getParameter("view"))?"&view=pending":"" %>&popup=true<%= addLinkParams(request, "view|trailSource|action") %>'"/></dhv:permission>
      </dhv:evaluate>
      <% if("pending".equals(request.getParameter("view"))){ %>
        <dhv:permission name="accounts-accounts-contacts-calls-edit">
          <input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>"/>
        </dhv:permission>
      <%}else if(CallDetails.getStatusId() != Call.CANCELED){%>
        <dhv:permission name="accounts-accounts-contacts-calls-edit">
          <input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>"/>
        </dhv:permission>
      <%}%>
      <dhv:evaluate if='<%= "pending".equals(request.getParameter("view"))%>'>
        <dhv:permission name="accounts-accounts-contacts-calls-delete">
          <input type="button" value="<dhv:label name="global.button.CancelpendingActivity">Cancel Pending Activity</dhv:label>" onClick="javascript:window.location.href='CalendarCalls.do?command=Cancel&contactId=<%= CallDetails.getContactId() %>&id=<%= CallDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %>&action=cancel<%= "pending".equals(request.getParameter("view"))?"&view=pending":"" %>&popup=true<%= addLinkParams(request, "popup|popupType|actionId|view|trailSource") %>'"/>
        </dhv:permission>
      </dhv:evaluate>
      <dhv:evaluate if="<%= !isPopup(request) %>">
        <dhv:permission name="myhomepage-inbox-view">
          <input type="button" name="action" value="<dhv:label name="accounts.accounts_calls_list_menu.Forward">Forward</dhv:label>" onClick="javascript:window.location.href='CalendarCalls.do?command=ForwardCall&contactId=<%= ContactDetails.getId() %>&forwardType=<%= Constants.TASKS %>&orgId=<%=OrgDetails.getOrgId() %>&id=<%= CallDetails.getId() %><%= "pending".equals(request.getParameter("view"))?"&view=pending":"" %>&popup=true<%= addLinkParams(request, "popup|popupType|actionId|view|trailSource|action") %>'"/>
        </dhv:permission>
      </dhv:evaluate>
    </dhv:evaluate>
    <input type="button" name="close" value="<dhv:label name="button.close">Close</dhv:label>" onClick="window.close()"/> 
    <dhv:permission name="accounts-accounts-contacts-calls-edit,accounts-accounts-contacts-calls-delete,myhomepage-inbox-view"><br>&nbsp;</dhv:permission>
    <% if("pending".equals(request.getParameter("view"))){ %>
      <dhv:evaluate if="<%= CallDetails.getAlertDate() != null %>">
        <%-- include follow up activity details --%>
        <%@ include file="../accounts/accounts_contacts_calls_details_followup_include.jsp" %>
      </dhv:evaluate>
      &nbsp;
      <%-- include completed activity details --%>
      <%@ include file="../accounts/accounts_calls_details_include.jsp" %>
    <% }else{ %>
      <%-- include completed activity details --%>
      <%@ include file="../accounts/accounts_calls_details_include.jsp" %>
      &nbsp;
      <dhv:evaluate if="<%= CallDetails.getAlertDate() != null %>">
        <%-- include follow up activity details --%>
        <%@ include file="../accounts/accounts_calls_details_followup_include.jsp" %>
      </dhv:evaluate>
    <% } %>
    &nbsp;
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <tr>
        <th colspan="2">
          <strong><dhv:label name="accounts.accounts_contacts_calls_details.RecordInformation">Record Information</dhv:label></strong>
        </th>
      </tr>
      <tr class="containerBody">
        <td class="formLabel" valign="top" nowrap>
          <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
        </td>
        <td>
          <dhv:username id="<%= CallDetails.getEnteredBy() %>"/>
          <zeroio:tz timestamp="<%= CallDetails.getEntered() %>" timeZone="<%= User.getTimeZone() %>"/>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel" nowrap>
          <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
        </td>
        <td>
          <dhv:username id="<%= CallDetails.getModifiedBy() %>"/>
          <zeroio:tz timestamp="<%= CallDetails.getModified() %>" timeZone="<%= User.getTimeZone() %>"/>
        </td>
      </tr>
    </table>
    <br />
    <% if (User.getUserId() == CallDetails.getOwner()) { %>
    <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() && !CallDetails.isTrashed()%>">
      <dhv:evaluate if='<%= "pending".equals(request.getParameter("view")) && User.getUserId() == CallDetails.getOwner() %>'>
        <dhv:permission name="accounts-accounts-contacts-calls-edit"><input type="button" value="<dhv:label name="global.button.complete">Complete</dhv:label>" onClick="javascript:window.location.href='CalendarCalls.do?command=Complete&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %><%= "pending".equals(request.getParameter("view"))?"&view=pending":"" %>&popup=true<%= addLinkParams(request, "view|trailSource|action") %>'"/></dhv:permission>
      </dhv:evaluate>
      <% if("pending".equals(request.getParameter("view")) && User.getUserId() == CallDetails.getOwner()){ %>
        <dhv:permission name="accounts-accounts-contacts-calls-edit"><input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>"></dhv:permission>
      <%}else if(CallDetails.getStatusId() != Call.CANCELED){%>
        <dhv:permission name="accounts-accounts-contacts-calls-edit"><input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>"></dhv:permission>
      <%}%>
      <dhv:evaluate if='<%= "pending".equals(request.getParameter("view")) && User.getUserId() == CallDetails.getOwner() %>'>
        <dhv:permission name="accounts-accounts-contacts-calls-delete"><input type="button" value="<dhv:label name="global.button.CancelPendingActivity">Cancel Pending Activity</dhv:label>" onClick="javascript:window.location.href='CalendarCalls.do?command=Cancel&contactId=<%= CallDetails.getContactId() %>&id=<%= CallDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %>&action=cancel<%= "pending".equals(request.getParameter("view"))?"&view=pending":"" %>&popup=true<%= addLinkParams(request, "popup|popupType|actionId|view|trailSource") %>'"></dhv:permission>
      </dhv:evaluate>
      <dhv:evaluate if="<%= !isPopup(request) %>">
        <dhv:permission name="myhomepage-inbox-view"><input type="button" name="action" value="<dhv:label name="accounts.accounts_calls_list_menu.Forward">Forward</dhv:label>" onClick="javascript:window.location.href='CalendarCalls.do?command=ForwardCall&contactId=<%= ContactDetails.getId() %>&forwardType=<%= Constants.TASKS %>&orgId=<%=OrgDetails.getOrgId() %>&id=<%= CallDetails.getId() %><%= "pending".equals(request.getParameter("view"))?"&view=pending":"" %>&popup=true<%= addLinkParams(request, "popup|popupType|actionId|view|trailSource|action") %>'"></dhv:permission>
      </dhv:evaluate>
    </dhv:evaluate>
    <% } %>
    <input type="button" name="close" value="<dhv:label name="button.close">Close</dhv:label>" onClick="window.close()"/> 
    <dhv:permission name="accounts-accounts-contacts-calls-edit,accounts-accounts-contacts-calls-delete"><br>&nbsp;</dhv:permission>
</dhv:container>   
<%= addHiddenParams(request, "popup|popupType|actionId|view|trailSource") %>
</form>

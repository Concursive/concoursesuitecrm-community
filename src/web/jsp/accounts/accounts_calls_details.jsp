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
  - Version: $Id: accounts_contacts_calls_details.jsp 15115 2006-05-31 16:47:51Z matt $
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
<form name="addCall" action="AccountsCalls.do?command=Modify&id=<%= CallDetails.getId() %>&orgId=<%= CallDetails.getOrgId() %>" method="post">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<%
  String trailSource = request.getParameter("trailSource");
%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<% if("accounts".equals(trailSource)){ %>
<a href="AccountsCalls.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.accounts_calls_list.Activities">Activities</dhv:label></a> >
<% }else{ %>
<a href="AccountsCalls.do?command=View&contactId=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.accounts_calls_list.Activities">Activities</dhv:label></a> >
<% } %>
<dhv:label name="accounts.accounts_contacts_calls_add.ActivityDetails">Activity Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="activities" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
    <dhv:evaluate if="<%=ContactDetails.getEnabled() && !ContactDetails.isTrashed() && !CallDetails.isTrashed()%>">
      <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
        <dhv:permission name="accounts-accounts-contacts-calls-edit"><input type="button" value="<dhv:label name="global.button.complete">Complete</dhv:label>" onClick="javascript:window.location.href='AccountsCalls.do?command=Complete&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %><%= addLinkParams(request, "view|trailSource|action") %>'"></dhv:permission>
      </dhv:evaluate>
      <% if("pending".equals(request.getParameter("view"))){ %>
        <dhv:permission name="accounts-accounts-contacts-calls-edit">
          <input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>">
        </dhv:permission>
      <%}else if(CallDetails.getStatusId() != Call.CANCELED){%>
        <dhv:permission name="accounts-accounts-contacts-calls-edit">
          <input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>">
        </dhv:permission>
      <%}%>
      <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
        <dhv:permission name="accounts-accounts-contacts-calls-delete">
          <input type="button" value="<dhv:label name="global.button.CancelpendingActivity">Cancel Pending Activity</dhv:label>" onClick="javascript:window.location.href='AccountsCalls.do?command=Cancel&contactId=<%= CallDetails.getContactId() %>&id=<%= CallDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %>&action=cancel<%= addLinkParams(request, "popup|popupType|actionId|view|trailSource") %>';">
        </dhv:permission>
      </dhv:evaluate>
      <dhv:evaluate if="<%= !isPopup(request) %>">
        <dhv:permission name="myhomepage-inbox-view">
          <input type="button" name="action" value="<dhv:label name="accounts.accounts_calls_list_menu.Forward">Forward</dhv:label>" onClick="javascript:window.location.href='AccountsCalls.do?command=ForwardCall&contactId=<%= ContactDetails.getId() %>&forwardType=<%= Constants.TASKS %>&orgId=<%=OrgDetails.getOrgId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|view|trailSource|action") %>'">
        </dhv:permission>
      </dhv:evaluate>
    </dhv:evaluate>
    <dhv:permission name="accounts-accounts-contacts-calls-edit,accounts-accounts-contacts-calls-delete,myhomepage-inbox-view"><br>&nbsp;</dhv:permission>
    <% if("pending".equals(request.getParameter("view"))){ %>
      <dhv:evaluate if="<%= CallDetails.getAlertDate() != null %>">
        <%-- include follow up activity details --%>
        <%@ include file="accounts_calls_details_followup_include.jsp" %>
      </dhv:evaluate>
      &nbsp;
      <%-- include completed activity details --%>
      <%@ include file="accounts_calls_details_include.jsp" %>
    <% }else{ %>
      <%-- include completed activity details --%>
      <%@ include file="accounts_calls_details_include.jsp" %>
      &nbsp;
      <dhv:evaluate if="<%= CallDetails.getAlertDate() != null %>">
        <%-- include follow up activity details --%>
        <%@ include file="accounts_calls_details_followup_include.jsp" %>
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
    <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() && !CallDetails.isTrashed()%>">
      <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
        <dhv:permission name="accounts-accounts-contacts-calls-edit"><input type="button" value="<dhv:label name="global.button.complete">Complete</dhv:label>" onClick="javascript:window.location.href='AccountsCalls.do?command=Complete&contactId=<%= ContactDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "view|trailSource|action") %>'"></dhv:permission>
      </dhv:evaluate>
      <% if("pending".equals(request.getParameter("view"))){ %>
        <dhv:permission name="accounts-accounts-contacts-calls-edit"><input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>"></dhv:permission>
      <%}else if(CallDetails.getStatusId() != Call.CANCELED){%>
        <dhv:permission name="accounts-accounts-contacts-calls-edit"><input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>"></dhv:permission>
      <%}%>
      <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
        <dhv:permission name="accounts-accounts-contacts-calls-delete"><input type="button" value="<dhv:label name="global.button.CancelPendingActivity">Cancel Pending Activity</dhv:label>" onClick="javascript:window.location.href='AccountsCalls.do?command=Cancel&contactId=<%= CallDetails.getContactId() %>&orgId=<%=OrgDetails.getOrgId() %>&id=<%= CallDetails.getId() %>&action=cancel<%= isPopup(request) ? "&popup=true" : "" %><%= addLinkParams(request, "popupType|actionId|view|action") %>';"></dhv:permission>
      </dhv:evaluate>
      <dhv:evaluate if="<%= !isPopup(request) %>">
        <dhv:permission name="myhomepage-inbox-view"><input type="button" name="action" value="<dhv:label name="accounts.accounts_calls_list_menu.Forward">Forward</dhv:label>" onClick="javascript:window.location.href='AccountsCalls.do?command=ForwardCall&contactId=<%= ContactDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %>&forwardType=<%= Constants.TASKS %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|view|trailSource|action") %>'"></dhv:permission>
      </dhv:evaluate>
    </dhv:evaluate>
    <dhv:permission name="accounts-accounts-contacts-calls-edit,accounts-accounts-contacts-calls-delete"><br>&nbsp;</dhv:permission>
  </dhv:container>
<%= addHiddenParams(request, "popup|popupType|actionId|view|trailSource") %>
</form>

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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="PreviousCallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%
  String trailSource = request.getParameter("trailSource");
%>
<body onLoad="javascript:document.addCall.callTypeId.focus();">
<form name="addCall" action="AccountContactsCalls.do?command=Save&auto-populate=true" onSubmit="return doCheck(this);" method="post">
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
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<a href="AccountContactsCalls.do?command=View&contactId=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.accounts_calls_list.Activities">Activities</dhv:label></a> >
<% } %>
<% if(PreviousCallDetails.getId() > 0 && !"cancel".equals(request.getParameter("action"))){ %>
  <% if (!"list".equals(request.getParameter("return"))){ %>
    <a href="AccountContactsCalls.do?command=Details&id=<%= (PreviousCallDetails.getId() > -1 ? PreviousCallDetails.getId() : CallDetails.getId()) %>&contactId=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "view|trailSource") %>"><dhv:label name="accounts.accounts_contacts_calls_add.ActivityDetails">Activity Details</dhv:label></a> >
 <% } %>
 <dhv:label name="accounts.accounts_calls_list_menu.CompleteActivity">Complete Activity</dhv:label>
<% }else if(PreviousCallDetails.getId() > 0 && "cancel".equals(request.getParameter("action"))){ %>
  <% if (!"list".equals(request.getParameter("return"))){ %>
    <a href="AccountContactsCalls.do?command=Details&id=<%= (PreviousCallDetails.getId() > -1 ? PreviousCallDetails.getId() : CallDetails.getId()) %>&contactId=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "view|trailSource") %>"><dhv:label name="accounts.accounts_contacts_calls_add.ActivityDetails">Activity Details</dhv:label></a> >
  <% } %>
  <dhv:label name="accounts.accounts_calls_list_menu.CancelActivity">Cancel Activity</dhv:label>
<% }else{ %>
 <dhv:label name="accounts.accounts_contacts_calls_add.AddActivity">Add Activity</dhv:label>
<% } %>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="contacts" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>">
  <dhv:container name="accountscontacts" selected="calls" object="ContactDetails" param="<%= "id=" + ContactDetails.getId() %>">
    <%-- include call add form --%>
    <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="this.form.dosubmit.value='true';">
    <% if ("list".equals(request.getParameter("return"))) {%>
    <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="window.location.href='AccountContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|view|popupType") %>';this.form.dosubmit.value='false';">
    <% }else{ %>
    <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="window.location.href='AccountContactsCalls.do?command=Details&id=<%= PreviousCallDetails.getId() %>&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|view|popupType") %>';this.form.dosubmit.value='false';">
    <%}%>
    <br />
    <dhv:formMessage />
    <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
    <%@ include file="../contacts/call_include.jsp" %>
    <br>
    <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="this.form.dosubmit.value='true';">
    <% if ("list".equals(request.getParameter("return"))) {%>
      <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="window.location.href='AccountContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
    <% }else{ %>
      <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="window.location.href='AccountContactsCalls.do?command=Details&id=<%= PreviousCallDetails.getId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
    <%}%>
    <input type="hidden" name="dosubmit" value="true">
    <input type="hidden" name="contactId" value="<%= ContactDetails.getId() %>">
    <dhv:evaluate if="<%= PreviousCallDetails.getId() > -1 %>">
      <input type="hidden" name="parentId" value="<%= PreviousCallDetails.getId() %>">
    </dhv:evaluate>
    <%= addHiddenParams(request, "action|return|trailSource|view") %>
  </dhv:container>
</dhv:container>
</form>
</body>

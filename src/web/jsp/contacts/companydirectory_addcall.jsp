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
<jsp:useBean id="actionSource" class="java.lang.String" scope="request"/>
<jsp:useBean id="Completed" class="java.lang.String" scope="request"/>
<jsp:useBean id="Log" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
function reopenContact(id) {
  if (id == '<%= ContactDetails.getId() %>') {
    scrollReload('ExternalContacts.do?command=SearchContacts');
    return -1;
  } else {
    return '<%= ContactDetails.getId() %>';
  }
}
</script>
<body onLoad="javascript:if(document.addCall.callTypeId){document.addCall.callTypeId.focus();}">
<form name="addCall" action="ExternalContactsCalls.do?command=Save&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> > 
<a href="ExternalContacts.do?command=SearchContacts"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<a href="ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_calls_list.Activities">Activities</dhv:label></a> >
<% if(PreviousCallDetails.getId() > 0 && !"cancel".equals(request.getParameter("action"))){ %>
  <% if (!"list".equals(request.getParameter("return"))){ %>
    <a href="ExternalContactsCalls.do?command=Details&id=<%= (PreviousCallDetails.getId() > -1 ? PreviousCallDetails.getId() : CallDetails.getId()) %>&contactId=<%=ContactDetails.getId()%><%= addLinkParams(request, "view|popupType") %>"><dhv:label name="accounts.accounts_contacts_calls_add.ActivityDetails">Activity Details</dhv:label></a> >
  <% } %>
  <dhv:label name="accounts.accounts_calls_list_menu.CompleteActivity">Complete Activity</dhv:label>
<% }else if(PreviousCallDetails.getId() > 0 && "cancel".equals(request.getParameter("action"))){ %>
  <% if (!"list".equals(request.getParameter("return"))){ %>
    <a href="ExternalContactsCalls.do?command=Details&id=<%= (PreviousCallDetails.getId() > -1 ? PreviousCallDetails.getId() : CallDetails.getId()) %>&contactId=<%=ContactDetails.getId()%><%= addLinkParams(request, "view|popupType") %>"><dhv:label name="accounts.accounts_contacts_calls_add.ActivityDetails">Activity Details</dhv:label></a> >
  <% } %>
  <dhv:label name="accounts.accounts_calls_list_menu.CancelActivity">Cancel Activity</dhv:label>
<% }else{ %>
<dhv:label name="accounts.accounts_contacts_calls_add.LogActivity">Log an Activity</dhv:label>
<% } %>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="contacts" selected="calls" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <% if ("Log".equals(Log)) { %>
  	<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="this.form.dosubmit.value='true';">
  <% } %>
  <% if ("completed".equals(Completed)) { %>
  	<input type="submit" value="<dhv:label name="global.button.completeActivity">Complete Activity</dhv:label>" onClick="this.form.dosubmit.value='true';">
  <% } else if ("cancel".equals(Completed)) { %>
  	<input type="submit" value="<dhv:label name="global.button.cancelActivity">Cancel Activity</dhv:label>" onClick="this.form.dosubmit.value='true';">
  <% } %>
  <% if ("list".equals(request.getParameter("return"))) {%>
    <input type="button" value="<dhv:label name="global.button.closeWindow">Close Window</dhv:label>" onClick="window.location.href='ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|view|popupType") %>';this.form.dosubmit.value='false';">
  <% }else{ %>
    <input type="button" value="<dhv:label name="global.button.closeWindow">Close Window</dhv:label>" onClick="window.location.href='ExternalContactsCalls.do?command=Details&id=<%= PreviousCallDetails.getId() %>&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|view|popupType") %>';this.form.dosubmit.value='false';">
  <%}%>
  <br />
  <dhv:formMessage />
   <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
  <%@ include file="call_include.jsp" %>
  <br>
  <% if ("Log".equals(Log)) { %>
  	<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="this.form.dosubmit.value='true';">
  <% } %>
  <% if ("completed".equals(Completed)) { %>
  	<input type="submit" value="<dhv:label name="global.button.completeActivity">Complete Activity</dhv:label>" onClick="this.form.dosubmit.value='true';">
  <% } else if ("cancel".equals(Completed)) { %>
  	<input type="submit" value="<dhv:label name="global.button.cancelActivity">Cancel Activity</dhv:label>" onClick="this.form.dosubmit.value='true';">
  <% } %>
  <% if ("list".equals(request.getParameter("return"))) {%>
    <input type="button" value="<dhv:label name="global.button.closeWindow">Close Window</dhv:label>" onClick="window.location.href='ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|view|popupType") %>';this.form.dosubmit.value='false';">
  <% }else{ %>
    <input type="button" value="<dhv:label name="global.button.closeWindow">Close Window</dhv:label>" onClick="window.location.href='ExternalContactsCalls.do?command=Details&id=<%= PreviousCallDetails.getId() %>&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|view|popupType") %>';this.form.dosubmit.value='false';">
  <%}%>
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="oppHeaderId" value="<%= PreviousCallDetails.getOppHeaderId() %>">
  <dhv:evaluate if="<%= PreviousCallDetails.getId() > -1 %>">
    <input type="hidden" name="parentId" value="<%= PreviousCallDetails.getId() %>">
  </dhv:evaluate>
  <%= addHiddenParams(request, "action|return|view|popupType") %>
</dhv:container>
</form>
</body>

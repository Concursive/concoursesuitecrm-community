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
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="PreviousCallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="actionSource" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript">
  function showHistory() {
    popURL('CalendarCalls.do?command=View&contactId=<%= ContactDetails.getId() %>&popup=true&source=calendar','CONTACT_HISTORY','650','500','yes','yes');
  }
</script>
<body onLoad="javascript:document.addCall.subject.focus();">
<% request.setAttribute("includeDetails", "true"); %>
<%-- <%@ include file="../contacts/contact_details_header_include.jsp" %><br>--%>
<form name="addCall" action="AccountContactsCalls.do?command=Save&auto-populate=true&actionSource=CalendarCalls" onSubmit="return doCheck(this);" method="post">
<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId()%>"/>
<dhv:container name="contacts" selected="calls" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' hideContainer="<%= isPopup(request) %>">
  <dhv:evaluate if="<%= hasText(ContactDetails.getTitle()) %>"><%= toHtml(ContactDetails.getTitle()) %><br /></dhv:evaluate>
  <dhv:evaluate if="<%= hasText(ContactDetails.getPhoneNumberList().getPrimaryPhoneNumber()) %>"><%= toHtml(ContactDetails.getPhoneNumberList().getPrimaryPhoneNumber()) %><br /></dhv:evaluate>
  <dhv:evaluate if="<%= hasText(ContactDetails.getEmailAddressList().getPrimaryEmailAddress()) %>"><%= toHtml(ContactDetails.getEmailAddressList().getPrimaryEmailAddress()) %><br /></dhv:evaluate>
  <br />
  <%-- include call add form --%>
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="this.form.dosubmit.value='true';">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();">
  [<a href="javascript:showHistory();"><dhv:label name="calendar.viewContactHistory">View Contact History</dhv:label></a>]
  <br />
  <dhv:formMessage />
  <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
  <%@ include file="../contacts/call_include.jsp" %>
  <br />
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="this.form.dosubmit.value='true';">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();">
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="contactId" value="<%= ContactDetails.getId() %>">
  <dhv:evaluate if="<%= PreviousCallDetails.getId() > -1 %>">
  <input type="hidden" name="parentId" value="<%= PreviousCallDetails.getId() %>">
  </dhv:evaluate>
  <input type="hidden" name="return" value="calendar">
  <%= addHiddenParams(request, "action|popup") %>
  <br>
  &nbsp;
</dhv:container>
</form>
</body>

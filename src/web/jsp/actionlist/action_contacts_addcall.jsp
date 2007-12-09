<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="PreviousCallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="actionSource" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:if(document.addCall.subject){document.addCall.subject.focus();}">
<% 
  String actionSourceLink = request.getParameter("actionSource");
  if (actionSourceLink == null) {
    actionSourceLink = "MyActionContacts";
  }
%>
<% if(ContactDetails.getOrgId() == -1){ %>
<form name="addCall" action="ExternalContactsCalls.do?command=Save&auto-populate=true&actionSource=<%= actionSourceLink %>" onSubmit="return doCheck(this);" method="post">
<% }else{ %>
<form name="addCall" action="AccountContactsCalls.do?command=Save&auto-populate=true&actionSource=<%= actionSourceLink %>" onSubmit="return doCheck(this);" method="post">
<% } %>
  <dhv:formMessage showSpace="false" />
  <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
  <%@ include file="../contacts/call_include.jsp" %>
  <br />
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="this.form.dosubmit.value='true';" />
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();" />
  <dhv:evaluate if='<%= "MyActionContacts".equals(request.getParameter("actionSource")) %>'>
    <input type="hidden" name="contactId" value="<%= ContactDetails.getId() %>">
  </dhv:evaluate>
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="parentId" value="<%= PreviousCallDetails.getId() %>">
</form>
</body>

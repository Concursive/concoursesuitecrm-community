<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="PreviousCallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].subject.focus();">
<% if(ContactDetails.getOrgId() == -1){ %>
<form name="addCall" action="ExternalContactsCalls.do?command=Save&auto-populate=true&actionSource=MyActionContacts" onSubmit="return doCheck(this);" method="post">
<% }else{ %>
<form name="addCall" action="AccountContactsCalls.do?command=Save&auto-populate=true&actionSource=MyActionContacts" onSubmit="return doCheck(this);" method="post">
<% } %>
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<%@ include file="../contacts/call_include.jsp" %>
<br>
<input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
<input type="button" value="Cancel" onClick="javascript:window.close();">
<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="contactId" value="<%= ContactDetails.getId() %>">
<input type="hidden" name="parentId" value="<%= PreviousCallDetails.getId() %>">
</form>
</body>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="PreviousCallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].subject.focus();">
<% if(ContactDetails.getOrgId() == -1){ %>
<form name="addCall" action="ExternalContactsCalls.do?command=Save&auto-populate=true&actionSource=MyActionContacts" onSubmit="return doCheck(this);" method="post">
<% }else{ %>
<form name="addCall" action="AccountContactsCalls.do?command=Save&auto-populate=true&actionSource=MyActionContacts" onSubmit="return doCheck(this);" method="post">
<% } %>
<dhv:evaluate if="<%= hasText((String) request.getAttribute("actionError")) %>">
<%= showError(request, "actionError") %>
</dhv:evaluate>
<%@ include file="../contacts/call_include.jsp" %>
<br>
<input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
<input type="button" value="Cancel" onClick="javascript:window.close();">
<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="contactId" value="<%= ContactDetails.getId() %>">
<input type="hidden" name="parentId" value="<%= PreviousCallDetails.getId() %>">
</form>
</body>

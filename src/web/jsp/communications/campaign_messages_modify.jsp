<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="Message" class="org.aspcfs.modules.communications.base.Message" scope="request"/>
<jsp:useBean id="clientType" class="org.aspcfs.utils.web.ClientType" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%-- Editor must go here, before the body onload --%>
<dhv:evaluate if="<%= !clientType.showApplet() %>">
<jsp:include page="../htmlarea_include.jsp" flush="true"/>
<body onload="initEditor('messageText');document.addMessage.name.focus();">
</dhv:evaluate>
<%-- Use applet instead --%>
<dhv:evaluate if="<%= clientType.showApplet() %>">
<body onload="document.addMessage.name.focus();">
</dhv:evaluate>
<script language="JavaScript" type="text/javascript" src="javascript/checkDate.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popCalendar.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    var formTest = true;
    var messageText = "";
<dhv:evaluate if="<%= clientType.showApplet() %>">
    document.addMessage.messageText.value = document.Kafenio.getDocumentBody();
</dhv:evaluate>
    return true;
  }
</script>
<form name="addMessage" action="CampaignManagerMessage.do?command=Update&auto-populate=true" method="post" onSubmit="return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do">Communications</a> >
<a href="CampaignManagerMessage.do?command=View">Message List</a> >
<a href="CampaignManagerMessage.do?command=Details&id=<%= Message.getId() %>">Message Details</a> >
Modify Message
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="hidden" name="modified" value="<%= Message.getModified() %>">
<dhv:evaluate if="<%= request.getParameter("return") != null %>">
  <input type="hidden" name="return" value="<%= request.getParameter("return") %>">
</dhv:evaluate>
<input type="submit" value="Update Message" name="Save">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=View'">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=Details&id=<%= Message.getId() %>'">
<%}%>
<br>
<%= showError(request, "actionError") %>
<%@ include file="message_include.jsp" %>
<br>
<input type="submit" value="Update Message" name="Save">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=View'">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=Details&id=<%= Message.getId() %>'">
<%}%>
</form>
</body>

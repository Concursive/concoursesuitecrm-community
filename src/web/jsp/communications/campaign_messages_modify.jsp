<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="Message" class="org.aspcfs.modules.communications.base.Message" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- HtmlArea --%>
<script type="text/javascript" src="htmlarea/htmlarea.js"></script>
<script type="text/javascript" src="htmlarea/htmlarea-lang-en.js"></script>
<script type="text/javascript" src="htmlarea/dialog.js"></script>
<style type="text/css">
@import url(htmlarea/htmlarea.css);
textarea { background-color: #fff; border: 1px solid 00f; }
</style>
<script type="text/javascript">
var editor = null;
function initEditor() {
  editor = new HTMLArea("messageText");
  editor.generate();
}
function insertHTML() {
  var html = prompt("Enter some HTML code here");
  if (html) {
    editor.insertHTML(html);
  }
}
function highlight() {
  editor.surroundHTML('<span style="background:yellow">', '</span>');
}
</script>
<%-- End HtmlArea + look at body onLoad --%>
<%
  boolean enhancedEditor = false;
  if (("ie".equals(User.getBrowserId()) && User.getBrowserVersion() >= 5.5) ||
      ("moz".equals(User.getBrowserId()) && User.getBrowserVersion() >= 1.3)) {
    enhancedEditor = true;
%>
<body onLoad="initEditor();document.forms[0].name.focus();">
<% 
  } else {
%>
<body onLoad="document.forms[0].name.focus();">
<%}%>
<form name="modMessage" action="CampaignManagerMessage.do?command=Update&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="CampaignManager.do">Communications Manager</a> >
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
<input type="submit" value="Update Message" name="Save" onclick="javascript:save();">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=View'">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=Details&id=<%= Message.getId() %>'">
<%}%>
<input type="reset" value="Reset">
<br>
<%= showError(request, "actionError") %>
<%@ include file="message_include.jsp" %>
<br>
<input type="submit" value="Update Message" name="Save" onclick="javascript:save();">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=View'">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=Details&id=<%= Message.getId() %>'">
<%}%>
<input type="reset" value="Reset">
</form>
</body>

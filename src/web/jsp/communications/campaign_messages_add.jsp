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
<form name="addMessage" method="post" action="CampaignManagerMessage.do?command=Insert&auto-populate=true">
<a href="CampaignManager.do">Communications Manager</a> >
<a href="CampaignManagerMessage.do?command=View">Message List</a> >
Add Message
<hr color="#BFBFBB" noshade>
<input type="submit" value="Save Message">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=View'">
<input type="reset" value="Reset">
<br>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>New Message</strong>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Name
    </td>
    <td>
      <input type="text" size="50" maxlength="80" name="name" value="<%= toHtmlValue(Message.getName()) %>">
      <font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  <tr>
    <td valign="top" class="formLabel">
      Internal Description
    </td>
    <td>
      <input type="text" size="50" maxlength="255" name="description" value="<%= toHtmlValue(Message.getDescription()) %>">
    </td>
  </tr>
	<tr>
    <td valign="top" class="formLabel">
      Message
    </td>
    <td valign=center>
      From: <input type="text" size="40" maxlength="255" name="replyTo" value="<%= toHtmlValue(Message.getReplyTo()) %>">
			<font color="red">*</font> <%= showAttribute(request, "replyToError") %>
      (Email address)<br>
      Subject: <input type="text" size="50" maxlength="255" name="messageSubject" value="<%= toHtmlValue(Message.getMessageSubject()) %>">
			<font color="red">*</font> <%= showAttribute(request, "messageSubjectError") %><br>
      <dhv:evaluate if="<%= !enhancedEditor %>">
        &nbsp;<br>
        <b>Plain Text</b> editing mode: html tags are not supported in message editor<br>
        <input type="hidden" name="formatLineFeeds" value="true"/>
        <textarea id="messageText" name="messageText" style="width:550; border: inset 2pt" rows="20"><%= StringUtils.toHtmlTextValue(Message.getMessageText()) %></textarea>
      </dhv:evaluate>
      <dhv:evaluate if="<%= enhancedEditor %>">
        <input type="hidden" name="formatLineFeeds" value="false"/>
        <textarea id="messageText" name="messageText" style="width:550" rows="20"><%= toString(Message.getMessageText()) %></textarea>
      </dhv:evaluate>
    </td>
  </tr>
</table>
<br>
<input type="submit" value="Save Message">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=View'">
<input type="reset" value="Reset">
</form>
</body>

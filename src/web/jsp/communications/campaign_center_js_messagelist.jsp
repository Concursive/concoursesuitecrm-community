<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.*" %>
<jsp:useBean id="messageList" class="org.aspcfs.modules.MessageList" scope="request"/>
<html>
<head>
</head>
<body onload="page_init();">
<script language="JavaScript">
function newOpt(param, value) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  return newOpt;
}
function page_init() {
  var list = parent.document.forms[0].elements['messageId'];
  list.options.length = 0;
  list.options[list.length] = newOpt("--None--", "0");
<%
  Iterator list1 = messageList.iterator();
  while (list1.hasNext()) {
    Message thisMessage = (Message)list1.next();
%>
  list.options[list.length] = newOpt("<%= thisMessage.getName() %>", "<%= thisMessage.getId() %>");
<%
  }
%>
  parent.window.frames['edit'].location.href='CampaignManagerMessage.do?command=PreviewMessage&id=-1';
}
</script>
</body>
</html>


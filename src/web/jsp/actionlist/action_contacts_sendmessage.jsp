<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="Message" class="org.aspcfs.modules.communications.base.Message" scope="request"/>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="messageList" class="org.aspcfs.modules.communications.base.MessageList" scope="request"/>
<script language="JavaScript">
  function updateMessageList() {
    document.forms['sendMessage'].elements['messageId'].selectedIndex = 0;;
    document.forms['sendMessage'].action = 'CampaignManager.do?command=MessageJSList&actionSource=MyActionContacts<%= addLinkParams(request, "popup|popupType|actionId")%>';
    document.forms['sendMessage'].submit();
  }
  function updateMessage(){
    document.forms['sendMessage'].action = 'MyActionContacts.do?command=PrepareMessage<%= addLinkParams(request, "popup|popupType|actionId") %>';
    document.forms['sendMessage'].submit();
  }
</script>
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
<form name="sendMessage" action="MyActionContacts.do?command=SendMessage&auto-populate=true&actionSource=MyActionContacts" method="post">
<dhv:evaluate if="<%= hasText((String) request.getAttribute("actionError")) %>">
<%= showError(request, "actionError") %>
</dhv:evaluate>
Start by choosing an existing message or create a new one:<br>
<SELECT SIZE="1" name="listView" onChange="javascript:updateMessageList();">
  <OPTION VALUE="my"<dhv:evaluate if="<%= "my".equals((String) request.getParameter("listView")) %>"> selected</dhv:evaluate>>My Messages</OPTION>
  <OPTION VALUE="all"<dhv:evaluate if="<%= "all".equals((String) request.getParameter("listView")) %>"> selected</dhv:evaluate>>All Messages</OPTION>
  <OPTION VALUE="hierarchy"<dhv:evaluate if="<%= "hierarchy".equals((String) request.getParameter("listView")) %>"> selected</dhv:evaluate>>Controlled Hierarchy Messages</OPTION>
  <OPTION VALUE="personal"<dhv:evaluate if="<%= "personal".equals((String) request.getParameter("listView")) %>"> selected</dhv:evaluate>>Personal Messages</OPTION>
  <OPTION VALUE="new"<dhv:evaluate if="<%= "new".equals((String) request.getParameter("listView")) %>"> selected</dhv:evaluate>>New Message</OPTION>
</SELECT>
<%if(!"new".equals(request.getParameter("listView"))){ %>
<% 
   messageList.setJsEvent("onChange=\"javascript:updateMessage();\""); 
   messageList.addItem(0, "--None--");
%>
<%= messageList.getHtmlSelect("messageId", (request.getParameter("messageId") != null ? Integer.parseInt(request.getParameter("messageId")) : -1)) %>
<% }else{ %>
  <select size="1" name="messageId">
    <option value="0">--None--</option>
  </select>  
<% } %>
<%-- include the message form from create messages --%>
<%@ include file="../communications/message_include.jsp" %>
<br>
<input type="submit" value="Send Message">
<input type="button" value="Cancel" onClick="javascript:window.close();">
<input type="reset" value="Reset"><br>
<input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
</form>
</body>


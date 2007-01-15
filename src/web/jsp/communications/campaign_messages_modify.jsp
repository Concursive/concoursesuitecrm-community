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
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="Message" class="org.aspcfs.modules.communications.base.Message" scope="request"/>
<jsp:useBean id="clientType" class="org.aspcfs.utils.web.ClientType" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="bcc" class="java.lang.String" scope="request"/>
<jsp:useBean id="cc" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%
  if (clientType.getType() == -1) {
    clientType.setParameters(request);
  }
%>
<%-- Editor must go here, before the body onload --%>
<dhv:evaluate if="<%= !clientType.showApplet() %>">
  <jsp:include page="../tinymce_include.jsp" flush="true"/>
  <script language="javascript" type="text/javascript">
    initEditor('messageText');
  </script>
</dhv:evaluate>
<body onload="document.addMessage.name.focus();">
<script language="JavaScript" type="text/javascript" src="javascript/checkDate.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popCalendar.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    try { tinyMCE.triggerSave(false); } catch(e) { }
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
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<a href="CampaignManagerMessage.do?command=View"><dhv:label name="campaign.messageList">Message List</dhv:label></a> >
<a href="CampaignManagerMessage.do?command=Details&id=<%= Message.getId() %>"><dhv:label name="accounts.MessageDetails">Message Details</dhv:label></a> >
<dhv:label name="campaign.modifyMessage">Modify Message</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="hidden" name="modified" value="<%= Message.getModified() %>">
<dhv:evaluate if='<%= request.getParameter("return") != null %>'>
  <input type="hidden" name="return" value="<%= request.getParameter("return") %>">
</dhv:evaluate>
<input type="submit" value="<dhv:label name="campaign.updateMessage">Update Message</dhv:label>" name="Save">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=View'">
	<%}%>
<%} else {%>
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=Details&id=<%= Message.getId() %>'">
<%}
boolean showBcc = false;
boolean showCc = false;
%>
<br />
<dhv:formMessage />
<%@ include file="message_include.jsp" %>
<br>
<input type="submit" value="<dhv:label name="campaign.updateMessage">Update Message</dhv:label>" name="Save">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=View'">
	<%}%>
<%} else {%>
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=Details&id=<%= Message.getId() %>'">
<%}%>
</form>
</body>

<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.utils.StringUtils" %>
<jsp:useBean id="Message" class="com.darkhorseventures.cfsbase.Message" scope="request"/>
<%@ include file="initPage.jsp" %>
<script type="text/javascript" src="/javascript/stringbuilder.js"></script>
<script type="text/javascript" src="/javascript/richedit.js"></script>
<script type="text/javascript" src="/javascript/coolbuttons.js"></script>
<script>
  function save() {
	<dhv:browser id="ie" minVersion="5.5" include="true">
    var edit = document.all.edit;
    document.all.messageText.value = edit.getHTML();
    edit.focus();
  </dhv:browser>
  }
</script>
<style>
  td.coolButton {
    font: menu;
  }
  table.coolBar {
    background: buttonface;
  }  
</style>
<a href="CampaignManager.do">Communications Manager</a> >
<a href="/CampaignManagerMessage.do?command=View">Message List</a> >
<a href="/CampaignManagerMessage.do?command=Details&id=<%= Message.getId() %>">Message Details</a> >
Modify Message
<hr color="#BFBFBB" noshade>
<form name="modMessage" action="/CampaignManagerMessage.do?command=Update&auto-populate=true" method="post">
<input type="hidden" name="id" value="<%= Message.getId() %>">
<input type="hidden" name="modified" value="<%= Message.getModified() %>">

<% if (request.getParameter("return") != null) {%>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>

<input type="submit" value="Update Message" name="Save" onclick="javascript:save();">

<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='/CampaignManagerMessage.do?command=View'">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/CampaignManagerMessage.do?command=Details&id=<%= Message.getId() %>'">
<%}%>
<input type="reset" value="Reset">
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
	<tr class="title">
  	<td colspan="2" valign="center" align="left">
    	<strong>Modify Message</strong>
  	</td>
	</tr>
  <tr>
    <td valign="center" align="right" class="formLabel">
      Name
    </td>
    <td valign="center" width="100%">
      <input type=text size=50 maxlength=80 name="name" value="<%= toHtmlValue(Message.getName()) %>">
			<font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  
  <tr>
    <td valign="top" align="right" class="formLabel">
      Internal Description
    </td>
    <td valign="top">
      <input type="text" size="50" maxlength="255" name="description" value="<%= toHtmlValue(Message.getDescription()) %>">
    </td>
  </tr>
	
	<tr>
    <td valign="top" align="right" class="formLabel">
      Message
    </td>
    <td valign=center>
      From: <input type="text" size="40" maxlength="255" name="replyTo" value="<%= toHtmlValue(Message.getReplyTo()) %>">
			<font color="red">*</font> <%= showAttribute(request, "replyToError") %>
      (Email address)<br>
      Subject: <input type="text" size="50" maxlength="255" name="messageSubject" value="<%= toHtmlValue(Message.getMessageSubject()) %>">
			<font color="red">*</font> <%= showAttribute(request, "messageSubjectError") %><br>
      &nbsp;<br>
		<dhv:browser id="ie" minVersion="5.5" include="false">
		  HTML tags are allowed in text entry<br>
      <textarea name="messageText" rows="10" cols="75" wrap="physical"><%= StringUtils.toHtmlTextValue(Message.getMessageText()) %></textarea>
    </dhv:browser>
		<dhv:browser id="ie" minVersion="5.5" include="true">
		  <input type="hidden" name="messageText" value="">
      <table cellspacing="0" id="toolBar" class="coolBar">
        <tr>
          <td class="coolButton" onclick="document.all.edit.setBold(); document.all.edit.frameWindow.focus();">
            &nbsp;<b>Bold</b>&nbsp;
          </td>
          <td class="coolButton" onclick="document.all.edit.setItalic(); document.all.edit.frameWindow.focus();">
            &nbsp;<i>Italic</i>&nbsp;
          </td>
          <td class="coolButton" onclick="document.all.edit.setUnderline(); document.all.edit.frameWindow.focus();">
            &nbsp;<u>Underline</u>&nbsp;
          </td>
          <td class="coolButton" onclick="document.all.edit.setColor('#000000'); document.all.edit.frameWindow.focus();">
            &nbsp;<font color="#000000">Black</font>&nbsp;
          </td>
          <td class="coolButton" onclick="document.all.edit.setColor('#0000FF'); document.all.edit.frameWindow.focus();">
            &nbsp;<font color="#0000FF">Blue</font>&nbsp;
          </td>
        </tr>
      </table>
      <iframe id="edit" frameborder="0" class="richEdit" style="border: 1px solid #cccccc; width: 100%; height: 100%;" onblur="return false">
         <body style="color: black; background: white;font: 8pt verdana;">
           <%= Message.getMessageText() %>
         </body>
      </iframe>
			</dhv:browser>
    </td>
  </tr>
</table>
<br>
<input type="submit" value="Update Message" name="Save" onclick="javascript:save();">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='/CampaignManagerMessage.do?command=View'">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/CampaignManagerMessage.do?command=Details&id=<%= Message.getId() %>'">
<%}%>
<input type="reset" value="Reset">
</form>
<script type="text/javascript">
  var all = document.all;
  var l = all.length;
  for (var i = 0; i < l; i++) {
    if (all[i].tagName != "INPUT" && all[i].tagName != "TEXTAREA")
      all[i].unselectable = "on";
  }
</script>

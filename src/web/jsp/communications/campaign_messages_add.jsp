<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
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
<body onLoad="javascript:document.forms[0].name.focus();">
<a href="/CampaignManagerMessage.do?command=View">Back to Message List</a><br>
<form name="addMessage" method="post" action="/CampaignManagerMessage.do?command=Insert&auto-populate=true">
<input type="submit" value="Save" onclick="javascript:save();">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/CampaignManagerMessage.do?command=View'">
<input type="reset" value="Reset">
<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>New Message</strong>
    </td>
  </tr>
  <tr>
    <td valign=center align=right class="formLabel">
      Name
    </td>
    <td valign=center width="100%">
      <input type=text size=50 maxlength=80 name="name" value="<%= toHtmlValue(Message.getName()) %>">
      <font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  <tr>
    <td valign=center align=right class="formLabel">
      Description
    </td>
    <td colspan=1 valign=center>
      <input type=text size=50 maxlength=255 name="description" value="<%= toHtmlValue(Message.getDescription()) %>">
    </td>
  </tr>
  <tr>
    <td valign=top align=right class="formLabel">
      Text
    </td>
    <td valign=center>
		<dhv:browser id="ie" minVersion="5.5" include="false">
		  HTML tags allowed in text entry<br>
      <textarea name="messageText" rows="10" cols="75" wrap="physical"><%= Message.getMessageText() %></textarea>
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
  <tr>
    <td valign=center align=right class="formLabel">
      URL
    </td>
    <td valign=center>
      <input type=text size=50 name="url" value="<%= toHtmlValue(Message.getUrl()) %>">
    </td>
  </tr>
  
  <tr>
    <td valign=center align=right class="formLabel">
      Reply To
    </td>
    <td valign=center>
      <input type=text size=50 name="replyTo" value="<%= toHtmlValue(Message.getReplyTo()) %>">
			<font color="red">*</font> <%= showAttribute(request, "replyToError") %>
    </td>
  </tr>
</table>
<br>
<input type="submit" value="Save" onclick="javascript:save();">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/CampaignManagerMessage.do?command=View'">
<input type="reset" value="Reset">
</form>
</body>
<script type="text/javascript">
  var all = document.all;
  var l = all.length;
  for (var i = 0; i < l; i++) {
    if (all[i].tagName != "INPUT" && all[i].tagName != "TEXTAREA")
      all[i].unselectable = "on";
  }
</script>

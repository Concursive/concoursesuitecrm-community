<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="AccessTypeList" class="org.aspcfs.modules.admin.base.AccessTypeList" scope="request"/>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>New Message</strong>
    </th>
  </tr>
  <dhv:evaluate if="<%= request.getParameter("actionId") == null %>">
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
    <td nowrap class="formLabel">
      Access Type
    </td>
    <td>
      <%=  AccessTypeList.getHtmlSelect("accessType", Message.getAccessType()) %>
    </td>
  </tr>
  </dhv:evaluate>
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
<dhv:evaluate if="<%= !clientType.showApplet() %>">
  <textarea id="messageText" name="messageText" style="width:550" rows="20"><%= toString(Message.getMessageText()) %></textarea>
</dhv:evaluate>
      
      <dhv:evaluate if="<%= clientType.showApplet() %>">
<input type="hidden" name="messageText" value="<%= toHtmlValue(Message.getMessageText()) %>" />
<APPLET CODEBASE="." CODE="de.xeinfach.kafenio.KafenioApplet.class" ARCHIVE="applet.jar,gnu-regexp-1.1.4.jar,config.jar,icons.jar" NAME="Kafenio" WIDTH="510" HEIGHT="365" MAYSCRIPT>
	<PARAM NAME="CODEBASE" VALUE=".">
	<PARAM NAME="CODE" VALUE="de.xeinfach.kafenio.KafenioApplet.class">
	<PARAM NAME="ARCHIVE" VALUE="applet.jar,gnu-regexp-1.1.4.jar,config.jar,icons.jar">
	<PARAM NAME="NAME" VALUE="Kafenio">
	<PARAM NAME="TYPE" VALUE="application/x-java-applet;version=1.3">
	<PARAM NAME="SCRIPTABLE" VALUE="true">
	<PARAM NAME="STYLESHEET" VALUE="css/<%= applicationPrefs.get("LAYOUT.TEMPLATE") %>.css,css/<%= applicationPrefs.get("LAYOUT.TEMPLATE") %>-10pt.css">
	<PARAM NAME="LANGCODE" VALUE="en">
	<PARAM NAME="LANGCOUNTRY" VALUE="US">
	<PARAM NAME="TOOLBAR" VALUE="true">
	<PARAM NAME="TOOLBAR2" VALUE="true">
	<PARAM NAME="MENUBAR" VALUE="true">
	<PARAM NAME="SOURCEVIEW" VALUE="false">
	<PARAM NAME="MENUICONS" VALUE="true">
	<%-- all available toolbar items: NEW,OPEN,SAVE,CUT,COPY,PASTE,UNDO,REDO,FIND,BOLD,ITALIC,UNDERLINE,STRIKE,SUPERSCRIPT,SUBSCRIPT,ULIST,OLIST,CLEARFORMATS,INSERTCHARACTER,ANCHOR,VIEWSOURCE,STYLESELECT,LEFT,CENTER,RIGHT,JUSTIFY,DEINDENT,INDENT,IMAGE,COLOR,TABLE,SAVECONTENT,DETACHFRAME,SEPARATOR --%>
	<PARAM NAME="BUTTONS" VALUE="CUT,COPY,PASTE,SEPARATOR,BOLD,ITALIC,UNDERLINE,SEPARATOR,LEFT,CENTER,RIGHT,justify,SEPARATOR,DETACHFRAME">	
	<PARAM NAME="BUTTONS2" VALUE="ULIST,OLIST,SEPARATOR,UNDO,REDO,SEPARATOR,DEINDENT,INDENT,SEPARATOR,ANCHOR,SEPARATOR,IMAGE,SEPARATOR,CLEARFORMATS,SEPARATOR,VIEWSOURCE,SEPARATOR,STRIKE,SUPERSCRIPT,SUBSCRIPT,INSERTCHARACTER,SEPARATOR,FIND,COLOR,TABLE">
	<%-- all available menuitems: <PARAM NAME="MENUITEMS" VALUE="FILE,EDIT,VIEW,FONT,FORMAT,INSERT,TABLE,FORMS,SEARCH,TOOLS,HELP,DEBUG"> --%>
	<PARAM NAME="MENUITEMS" VALUE="EDIT,FONT,FORMAT,INSERT,TABLE,SEARCH">
  <PARAM NAME="SERVLETMODE" VALUE="cgi">
<%--
	<PARAM NAME="SERVLETURL" VALUE="<%= request.getScheme() %>://<%= getServerUrl(request) %>/Portal.do?command=Images&out=text">
	<PARAM NAME="IMAGEDIR" VALUE="/media/images/">
	<PARAM NAME="FILEDIR" VALUE="/media/images/">
--%>
<%--
	<PARAM NAME="SYSTEMID" VALUE="">
	<PARAM NAME="POSTCONTENTURL" VALUE="http://www.xeinfach.de/media/projects/kafenio/demo/postTester.php">
--%>
	<PARAM NAME="CONTENTPARAMETER" VALUE="messageText">
	<PARAM NAME="OUTPUTMODE" VALUE="off">
	<PARAM NAME="BGCOLOR" VALUE="#FFFFFF">
	<PARAM NAME="DEBUG" VALUE="false">
  <dhv:evaluate if="<%= hasText(Message.getMessageText()) %>">
	  <PARAM NAME="BASE64" VALUE="true">
    <PARAM NAME="DOCUMENT" VALUE="<%= StringUtils.toBase64(Message.getMessageText()) %>">
  </dhv:evaluate>
</APPLET>
      </dhv:evaluate>
      
    </td>
  </tr>
</table>
<%= addHiddenParams(request, "popup|popupType|actionId|actionListId") %>
<dhv:evaluate if="<%= Message.getId() > 0 %>">
<input type="hidden" name="id" value="<%= Message.getId() %>">
</dhv:evaluate>

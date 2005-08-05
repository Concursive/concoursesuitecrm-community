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
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="AccessTypeList" class="org.aspcfs.modules.admin.base.AccessTypeList" scope="request"/>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="actionList.newMessage">New Message</dhv:label></strong>
    </th>
  </tr>
  <dhv:evaluate if="<%= (request.getParameter("actionId") == null) &&  (!"addressRequest".equals(request.getAttribute("messageType"))) && !(showBcc || showCc)  %>">
  <tr>
    <td class="formLabel">
      <dhv:label name="contacts.name">Name</dhv:label>
    </td>
    <td>
      <input type="text" size="50" maxlength="80" name="name" value="<%= toHtmlValue(Message.getName()) %>">
      <font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  <tr>
    <td valign="top" class="formLabel">
      <dhv:label name="campaign.initialDesctiption">Internal Description</dhv:label>
    </td>
    <td>
      <input type="text" size="50" maxlength="255" name="description" value="<%= toHtmlValue(Message.getDescription()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_validateimport.AccessType">Access Type</dhv:label>
    </td>
    <td>
      <%=  AccessTypeList.getHtmlSelect("accessType", Message.getAccessType()) %>
    </td>
  </tr>
  </dhv:evaluate>
	<tr>
    <td valign="top" class="formLabel">
      <dhv:label name="project.message">Message</dhv:label>
    </td>
    <td valign=center>
      <table cellpadding="0" cellspacing="0" class="empty">
      <tr><td nowrap>
      <dhv:label name="campaign.from">From</dhv:label>:</td><td nowrap><input type="text" size="40" maxlength="255" name="replyTo" value="<%= toHtmlValue((Message.getReplyTo() != null && !"".equals(Message.getReplyTo()) ? Message.getReplyTo():User.getUserRecord().getContact().getPrimaryEmailAddress())) %>" />
			<font color="red">*</font> <dhv:label name="campaign.emailAddress.brackets">(Email address)</dhv:label></td><td nowrap><%= showAttribute(request, "replyToError") %>
      </td></tr>
      <dhv:evaluate if="<%= showCc %>"><tr><td nowrap><dhv:label name="quotes.cc">CC</dhv:label>:</td><td nowrap><input type="text" size="40" maxlength="1024" name="cc" id="cc" value="<%= toHtmlValue((cc != null?cc:"")) %>"/> <dhv:label name="campaign.emailAddress.brackets">(Email address)</dhv:label></td><td nowrap><%= showAttribute(request, "ccError") %></td></tr></dhv:evaluate>
      <dhv:evaluate if="<%= showBcc %>"><tr><td nowrap><dhv:label name="quotes.bcc">BCC</dhv:label>:</td><td nowrap><input type="text" size="40" maxlength="255" name="bcc" id="bcc" value="<%= toHtmlValue((bcc != null?bcc:"")) %>"/> <dhv:label name="campaign.emailAddress.brackets">(Email address)</dhv:label></td><td nowrap><%= showAttribute(request, "bccError") %></td></tr></dhv:evaluate>
      <tr><td nowrap>
      <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>:</td><td nowrap><input type="text" size="50" maxlength="1024" name="messageSubject" value="<%= toHtmlValue(Message.getMessageSubject()) %>">
			<font color="red">*</font></td><td nowrap><%= showAttribute(request, "messageSubjectError") %>
      </td></tr>
      <tr><td colspan="3">
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
      </td></tr></table>
    </td>
  </tr>
</table>
<%= addHiddenParams(request, "popup|popupType|actionId|actionListId") %>
<dhv:evaluate if="<%= Message.getId() > 0 %>">
<input type="hidden" name="id" value="<%= Message.getId() %>">
</dhv:evaluate>

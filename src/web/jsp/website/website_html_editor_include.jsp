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
  - Version: $Id: message_include.jsp 12404 2005-08-05 17:37:07 +0000 (Fri, 05 Aug 2005) mrajkowski $
  - Description: 
  --%>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,org.aspcfs.utils.StringUtils" %>
<%@ page import="org.aspcfs.modules.website.base.IceletProperty" %>
<%@ page import="org.apache.commons.codec.binary.Base64" %>
<zeroio:debug value='<%= "\n\n\nJSP::the clientType.showApplet() is "+clientType.showApplet() %>'/>
<dhv:evaluate if="<%= !clientType.showApplet() %>">
  <script language="javascript" type="text/javascript">
    initEditor('<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>');
  </script>
</dhv:evaluate>
<dhv:evaluate if="<%= !clientType.showApplet() %>">
  <textarea id="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" name="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" style="width:550" rows="20"><%= toString(rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null && ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() != null? ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() : property.getDefaultValue()) %></textarea>
</dhv:evaluate>
<dhv:evaluate if="<%= clientType.showApplet() %>">
<input type="hidden" name="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" value="<%= toHtmlValue(rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null && ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() != null? ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() : property.getDefaultValue()) %>" />
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
  <dhv:evaluate if="<%= hasText(rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null && ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() != null? ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() : property.getDefaultValue()) %>">
	  <PARAM NAME="BASE64" VALUE="true">
    <PARAM NAME="DOCUMENT" VALUE="<%= Base64.encodeBase64((rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null && ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() != null? ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() : property.getDefaultValue()).getBytes("UTF8"), true) %>">
  </dhv:evaluate>
</APPLET>
</dhv:evaluate>


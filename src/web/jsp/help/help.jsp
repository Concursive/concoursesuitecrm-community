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
<%@ page import="java.util.*,org.aspcfs.modules.help.base.*" %>
<jsp:useBean id="Help" class="org.aspcfs.modules.help.base.HelpItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<html>
<head><title>Centric CRM: Help</title></head>
<FRAMESET COLS="25%,75%">
	<FRAME SRC="Help.do?command=ViewTableOfContents" name="contents">
	<FRAMESET ROWS="30%,70%">
    <FRAME SRC="Help.do?command=ViewModule&moduleId=<%=Help.getModuleId() %>" name="module">
		<FRAME SRC="Help.do?command=ViewContext&helpId=<%=Help.getId() %>" name="context">
  </FRAMESET>
</FRAMESET>
</html>

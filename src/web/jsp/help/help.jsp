<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.help.base.*" %>
<jsp:useBean id="Help" class="org.aspcfs.modules.help.base.HelpItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<html>
<head><title>Dark Horse CRM: Help</title></head>
<FRAMESET COLS="25%,75%">
	<FRAME SRC="Help.do?command=ViewTableOfContents" name="contents">
	<FRAMESET ROWS="30%,70%">
    <FRAME SRC="Help.do?command=ViewModule&moduleId=<%=Help.getModuleId() %>" name="module">
		<FRAME SRC="Help.do?command=ViewContext&helpId=<%=Help.getId() %>" name="context">
  </FRAMESET>
</FRAMESET>
</html>

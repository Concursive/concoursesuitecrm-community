<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.help.base.*" %>
<jsp:useBean id="Help" class="org.aspcfs.modules.help.base.HelpItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<html>
<head><title>Dark Horse CRM: Help</title></head>
<FRAMESET COLS="25%,75%">
	<FRAME SRC="help/help_tableof_contents.jsp" name="contents">
	<FRAMESET ROWS="30%,70%">
      		<FRAME SRC="Help.do?command=ViewModule&moduleId=<%=Help.getModuleId() %>" name="module">
		<FRAME SRC="Help.do?command=ViewContext&helpId=<%=Help.getId() %>" name="context">
      </FRAMESET>
</FRAMESET>
</html>

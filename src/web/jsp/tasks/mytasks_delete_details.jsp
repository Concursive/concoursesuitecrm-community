<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.tasks.base.*" %>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<%@ include file="../initPage.jsp" %>
<br>
<strong><center>The task "<%=Task.getDescription()%>" has been deleted.</center></strong>
<br>
<input type="button" value="OK" onClick="javascript:opener.window.location.href='MyTasks.do?command=ListTasks';javascript:window.close()">



<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*, org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%= showError(request, "actionError", false) %>
<%@ include file="../tasks/task_details_include.jsp" %>
<br>
<input type="button" value="Close" onClick="javascript:window.close();">


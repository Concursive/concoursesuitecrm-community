<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*"%>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].description.focus();">
<form name="addTask" action="MyTasks.do?command=<%= Task.getId()!=-1?"Update":"Insert" %>&id=<%= Task.getId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>&actionSource=MyActionContacts" method="post" onSubmit="return validateTask();">
<% boolean popUp = request.getParameter("popup") != null; %>
<dhv:evaluate if="<%= hasText((String) request.getAttribute("actionError")) %>">
<%= showError(request, "actionError") %>
</dhv:evaluate>
<%@ include file="../tasks/task_include.jsp" %>
<br>
<input type="submit" value="<%= Task.getId()==-1?"Save":"Update" %>">
<input type="button" value="Cancel" onClick="javascript:window.close();">
<input type="reset" value="Reset"><br>
<input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
</form>
</body>


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*, org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].description.focus();">
<form name="addTask" action="TroubleTicketTasks.do?command=Save&id=<%= Task.getId() %>&auto-populate=true" method="post" onSubmit="return validateTask();">
<input type="submit" value="<%= Task.getId() == -1 ? "Save" : "Update" %>">
<input type="button" value="Cancel" onClick="javascript:window.close();"><br>
<%= showError(request, "actionError") %>

<%@ include file="../tasks/task_form.jsp" %>

<br>
<input type="submit" value="<%= Task.getId() == -1 ? "Save" : "Update" %>">
<input type="button" value="Cancel" onClick="javascript:window.close();">
<input type="hidden" name="ticketId" value="<%= request.getParameter("ticketId") %>">
<input type="hidden" name="type" value="<%= Constants.TICKET_OBJECT %>">
<input type="hidden" name="return" value="TroubleTickets.do?command=Details&id=<%= request.getParameter("ticketId") %>">
</form>
</body>


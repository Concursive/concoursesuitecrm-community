<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*, org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].description.focus();">
<form name="addTask" action="TroubleTicketTasks.do?command=Save&id=<%= Task.getId() %>&auto-populate=true" method="post" onSubmit="return validateTask();">
<%= showError(request, "actionError") %>

<%@ include file="../tasks/task_include.jsp" %>

<br>
<input type="submit" value="<%= Task.getId() == -1 ? "Save" : "Update" %>">
<input type="button" value="Cancel" onClick="javascript:window.close();">
<input type="hidden" name="ticketId" value="<%= request.getParameter("ticketId") %>">
<input type="hidden" name="type" value="<%= Constants.TICKET_OBJECT %>">
<input type="hidden" name="return" value="TroubleTicketTasks.do?command=List&ticketId=<%= request.getParameter("ticketId") %>">
</form>
</body>


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
<%@ page import="java.util.*"%>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].description.focus();">
<form name="addTask" action="MyTasks.do?command=<%= Task.getId() != -1 ? "Update" : "Insert" %>&id=<%= Task.getId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>&actionSource=<%= request.getParameter("actionSource") %>" method="post" onSubmit="return validateTask();">
<% boolean popUp = request.getParameter("popup") != null; %>
<dhv:evaluate if="<%= hasText((String) request.getAttribute("actionError")) %>">
<%= showError(request, "actionError") %>
</dhv:evaluate>
<%@ include file="../tasks/task_include.jsp" %>
<br>
<input type="submit" value="<%= Task.getId() == -1 ? "Save" : "Update" %>">
<input type="button" value="Cancel" onClick="javascript:window.close();">
<br>
</form>
</body>


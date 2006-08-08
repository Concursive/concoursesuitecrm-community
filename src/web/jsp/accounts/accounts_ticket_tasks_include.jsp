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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*, org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<jsp:useBean id="forward" class="java.lang.String" scope="request"/>
<jsp:useBean id="addAnother" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:refreshOpener();document.addTask.description.focus();">
<script type="text/javascript">
function refreshOpener() {
  if ('<%= addAnother != null && "true".equals(addAnother.trim()) %>' == 'true') {
    try {
      opener.reopen();
    } catch (oException) {
    }
  }
}
</script>
<form name="addTask" action="AccountTicketTasks.do?command=Save&id=<%= Task.getId() %>&auto-populate=true" method="post" onSubmit="return validateTask();">
  <dhv:formMessage showSpace="false" />
  <%
    String ticketId = request.getParameter("ticketId"); 
    if (ticketId == null || "".equals(ticketId.trim()) || "-1".equals(ticketId.trim())) {
      ticketId = (String) request.getAttribute("ticketId");
    }
    if (Task.getTicketId() == -1) {
      Task.setTicketId(ticketId);
    }
  %>
  <%@ include file="../tasks/task_include.jsp" %>
  <br />
  <% if (Task.getId() == -1) { %>
    <input type="submit" value="<dhv:label name="button.save">Save</dhv:label>" />
    <input type="submit" value="<dhv:label name="button.saveAndNew">Save and New</dhv:label>" onClick="javascript:document.getElementById('addAnother').value='true';"/> 
  <%} else {%>
  <input type="submit" value="<dhv:label name="button.update">Update</dhv:label>"/>
  <%}%>
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();">
  <input type="hidden" name="addAnother" id="addAnother" value="false"/>
  <input type="hidden" name="forward" id="forward" value="<%= toHtmlValue((String) request.getAttribute("forward")) %>"/>
  <input type="hidden" name="addAnother" id="addAnother" value="false"/>
<%--  <input type="hidden" name="ticketId" value="<%= ticketId %>" /> --%>
  <input type="hidden" name="orgId" value="<%= request.getParameter("orgId") %>" />
  <input type="hidden" name="type" value="<%= Constants.TICKET_OBJECT %>" />
  <input type="hidden" name="return" value="AccountTicketTasks.do?command=List&ticketId=<%= ticketId %>" />
<% System.out.println("JSP::ticketId is "+ ticketId); %>
</form>
</body>


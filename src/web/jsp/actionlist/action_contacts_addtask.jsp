<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*"%>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].description.focus();">
<form name="addTask" action="MyTasks.do?command=<%= Task.getId()!=-1?"Update":"Insert" %>&id=<%= Task.getId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>&actionSource=MyActionContacts" method="post" onSubmit="return validateTask();">
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%>
<input type="submit" value="<%= Task.getId()==-1?"Save":"Update" %>">
<input type="button" value="Cancel" onClick="javascript:window.close();">
<input type="reset" value="Reset"><br>
<%= showError(request, "actionError") %>
<%@ include file="../tasks/task_form.jsp" %>
<br>
<input type="submit" value="<%= Task.getId()==-1?"Save":"Update" %>">
<input type="button" value="Cancel" onClick="javascript:window.close();">
<input type="reset" value="Reset"><br>
<input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
</form>
</body>


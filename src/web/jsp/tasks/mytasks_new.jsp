<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*"%>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].description.focus();">
<form name="addTask" action="MyTasks.do?command=<%= Task.getId()!=-1?"Update":"Insert" %>&id=<%= Task.getId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" method="post" onSubmit="return validateTask();">
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%>
<dhv:evaluate exp="<%= !popUp %>">
<a href="MyCFS.do?command=Home">My Home Page</a> > 
<a href="MyTasks.do?command=ListTasks">My Tasks</a> >
<%= Task.getId()==-1?"Add":"Update" %> a Task <br>
<hr color="#BFBFBB" noshade>
</dhv:evaluate>
<input type="submit" value="<%= Task.getId()==-1?"Save":"Update" %>">
<input type="hidden" name="return" value="<%= request.getParameter("return") %>">
<input type="button" value="Cancel" onClick="<%=popUp?"javascript:window.close();":"javascript:window.location.href='MyTasks.do?command=ListTasks';"%>"><br>
<%= showError(request, "actionError") %>
<%@ include file="task_form.jsp" %>
<br>
<input type="submit" value="<%= Task.getId()==-1?"Save":"Update" %>">
<input type="hidden" name="return" value="<%= request.getParameter("return") %>">
<input type="button" value="Cancel" onClick="<%=popUp?"javascript:window.close();":"javascript:window.location.href='MyTasks.do?command=ListTasks';"%>">
</form>
</body>


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<%@ include file="../initPage.jsp" %>
<dhv:evaluate exp="<%= !isPopup(request) %>">
<a href="MyCFS.do?command=Home">My Home Page</a> > 
<a href="MyTasks.do?command=ListTasks">My Tasks</a> >
Task Details<br>
<hr color="#BFBFBB" noshade>
</dhv:evaluate>
<%= showError(request, "actionError") %>
<input type="button" value="Cancel" onClick="<%= isPopup(request) ? "javascript:window.close();" : "javascript:window.location.href='MyTasks.do?command=ListTasks';"%>"><br>
<%@ include file="task_details_include.jsp" %>
<br>
<input type="hidden" name="return" value="<%= request.getParameter("return") %>">
<input type="button" value="Cancel" onClick="<%= isPopup(request) ? "javascript:window.close();" : "javascript:window.location.href='MyTasks.do?command=ListTasks';"%>">

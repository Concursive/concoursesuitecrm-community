<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<%@ include file="../initPage.jsp" %>
<dhv:evaluate exp="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="MyCFS.do?command=Home">My Home Page</a> > 
<a href="MyTasks.do?command=ListTasks">My Tasks</a> >
Task Details
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%= showError(request, "actionError") %>
<dhv:evaluate exp="<%= isPopup(request) %>"> 
<input type="button" value="Cancel" onClick="javascript:window.close();"><br>
</dhv:evaluate>
<%@ include file="task_details_include.jsp" %>
<br>
<input type="hidden" name="return" value="<%= request.getParameter("return") %>">
<dhv:evaluate exp="<%= isPopup(request) %>">
<input type="button" value="Cancel" onClick="javascript:window.close();">
</dhv:evaluate>

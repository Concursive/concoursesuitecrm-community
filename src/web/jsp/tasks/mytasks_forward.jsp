<a href="MyCFS.do?command=Home">My Home Page</a> > 
<a href="MyTasks.do?command=ListTasks">My Tasks</a> >
Forward Task
<hr color="#BFBFBB" noshade>
<form name="newMessageForm" action="MyCFSInbox.do?command=SendMessage" method="post" onSubmit="return sendMessage();">
<%@ include file="../newmessage.jsp" %>
<br>
<input type="submit" value="Send">
<input type="button" value="Cancel" onClick="javascript:window.location.href='MyTasks.do?command=ListTasks'">
</form>


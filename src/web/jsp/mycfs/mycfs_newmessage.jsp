<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="MyCFS.do?command=Home">My Home Page</a> >
<a href="MyCFSInbox.do?command=Inbox&return=1">My Mailbox</a> >
New Message
</td>
</tr>
</table>
<%-- End Trails --%>
<form name="newMessageForm" action="MyCFSInbox.do?command=SendMessage" method="post" onSubmit="return sendMessage();">
<input type="submit" value="Send">
<input type="button" value="Cancel" onClick="javascript:window.location.href='MyCFSInbox.do?command=Inbox'"><br><br>
<%@ include file="../newmessage.jsp" %>
<br>
<input type="submit" value="Send">
<input type="button" value="Cancel" onClick="javascript:window.location.href='MyCFSInbox.do?command=Inbox'">
</form>

<form name="newMessageForm" action="MyCFSInbox.do?command=SendMessage&actionSource=MyActionContacts" method="post" onSubmit="return sendMessage();">
<%@ include file="../newmessage.jsp" %>
<br>
<input type="submit" value="Send">
<input type="button" value="Cancel" onClick="javascript:window.close();">
<input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
</form>

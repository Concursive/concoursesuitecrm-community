<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= request.getParameter("contactId") %>">Contact Details</a> >
<a href="ExternalContactsCalls.do?command=View&contactId=<%=request.getParameter("contactId") %>">Calls</a> >
<a href="ExternalContactsCalls.do?command=View&contactId=<%= request.getParameter("contactId") %>&id=<%= request.getParameter("id") %>">Call Details</a> >
Forward Call<br>
<hr color="#BFBFBB" noshade>
<form name="newMessageForm" action="ExternalContactsCallsForward.do?command=SendMessage&contactId=<%= request.getParameter("contactId") %>&id=<%= request.getParameter("id") %>" method="post" onSubmit="return sendMessage();">
<%@ include file="../newmessage.jsp" %>
<br>
<input type="submit" value="Send">
<input type="button" value="Cancel" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=Details&id=<%= request.getParameter("id") %>&contactId=<%= request.getParameter("contactId") %>'">
</form>

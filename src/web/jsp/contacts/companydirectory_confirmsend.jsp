<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&contactId=<%= request.getParameter("contactId") %>">Contact Details</a> >
<a href="ExternalContactsCalls.do?command=View&contactId=<%= request.getParameter("contactId") %>&id=<%= request.getParameter("id") %>">Calls</a> >
Forward Call<br>
<hr color="#BFBFBB" noshade>
<%@ include file="../confirmsend.jsp" %>

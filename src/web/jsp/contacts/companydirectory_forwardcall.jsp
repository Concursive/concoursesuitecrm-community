<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
<a href="ExternalContactsCalls.do?command=View&contactId=<%=ContactDetails.getId()%>">Calls</a> >
Forward Call<br>
<hr color="#BFBFBB" noshade>
<%@ include file="../newmessage.jsp" %>


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="details" action="ExternalContacts.do?command=ModifyContact&id=<%= ContactDetails.getId() %>" method="post">
<dhv:evaluate exp="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do">Contacts</a> > 
<a href="ExternalContacts.do?command=SearchContacts">Search Results</a> >
Contact Details
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%@ include file="contact_details_include.jsp" %>
<input type="hidden" name="command" value="">
</form>

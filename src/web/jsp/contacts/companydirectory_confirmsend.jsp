<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="ExternalContacts.do">Contacts</a> > 
<a href="ExternalContacts.do?command=SearchContacts">Search Results</a> >
<a href="ExternalContacts.do?command=ContactDetails&contactId=<%= request.getParameter("contactId") %>">Contact Details</a> >
<a href="ExternalContactsCalls.do?command=View&contactId=<%= request.getParameter("contactId") %>&id=<%= request.getParameter("id") %>">Calls</a> >
Forward Call
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="contact_details_header_include.jsp" %>
<% String param1 = "id=" + ContactDetails.getId(); 
   String param2 = addLinkParams(request, "popup|popupType|actionId"); %>
<dhv:container name="contacts" selected="calls" param="<%= param1 %>" appendToUrl="<%= param2 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <%-- include the confirmation message --%>
      <%@ include file="../confirmsend.jsp" %>
    </td>
  </tr>
</table>

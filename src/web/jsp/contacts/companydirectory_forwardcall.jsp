<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=SearchContacts">Search Results</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>">Contact Details</a> >
<a href="ExternalContactsCalls.do?command=View&contactId=<%=ContactDetails.getId() %>">Calls</a> >
<dhv:evaluate if="<%= !"list".equals(request.getParameter("return")) %>">
  <a href="ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %>&id=<%= request.getParameter("id") %>">Call Details</a> >
</dhv:evaluate>
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
      <form name="newMessageForm" action="ExternalContactsCallsForward.do?command=SendCall&contactId=<%= request.getParameter("contactId") %>&id=<%= request.getParameter("id") %>" method="post" onSubmit="return sendMessage();">
      <input type="submit" value="Send">
      <% if("list".equals(request.getParameter("return"))){ %>
        <input type="button" value="Cancel" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=View&contactId=<%= request.getParameter("contactId") %>'">
      <% }else{ %>
        <input type="button" value="Cancel" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=Details&id=<%= request.getParameter("id") %>&contactId=<%= request.getParameter("contactId") %>'">
      <% } %>
      <br><br>
      <%@ include file="../newmessage.jsp" %>
      <br>
      <input type="submit" value="Send">
      <% if("list".equals(request.getParameter("return"))){ %>
        <input type="button" value="Cancel" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=View&contactId=<%= request.getParameter("contactId") %>'">
      <% }else{ %>
        <input type="button" value="Cancel" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=Details&id=<%= request.getParameter("id") %>&contactId=<%= request.getParameter("contactId") %>'">
      <% } %>
      </form>
    </td>
  </tr>
</table>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<dhv:evaluate if="<%= !isPopup(request) %>">
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Contacts</a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Contact Details</a> >
<a href="AccountContactsCalls.do?command=View&contactId=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Calls</a> >
<dhv:evaluate if="<%= !"list".equals(request.getParameter("return")) %>">
<a href="AccountContactsCalls.do?command=Details&id=<%= request.getParameter("id") %>&contactId=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Call Details</a> >
</dhv:evaluate>
Forward Call<br>
<hr color="#BFBFBB" noshade>
</dhv:evaluate>
<%-- include the accounts menu --%>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="contacts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<form name="newMessageForm" action="AccountContactsCalls.do?command=SendCall&contactId=<%= ContactDetails.getId() %>&id=<%= request.getParameter("id") %>" method="post" onSubmit="return sendMessage();">
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <%-- include contact menu --%>
      <% String param1 = "id=" + ContactDetails.getId(); 
      %>
        <strong><%= ContactDetails.getNameLastFirst() %>:</strong>
        [ <dhv:container name="accountscontacts" selected="calls" param="<%= param1 %>"/> ]
        <br>
        <br>
        <input type="submit" value="Send">
        <% if("list".equals(request.getParameter("return"))){ %>
        <input type="button" value="Cancel" onClick="javascript:window.location.href='AccountContactsCalls.do?command=View&contactId=<%= request.getParameter("contactId") %>'">
        <% }else{ %>
        <input type="button" value="Cancel" onClick="javascript:window.location.href='AccountContactsCalls.do?command=Details&id=<%= request.getParameter("id") %>&contactId=<%= request.getParameter("contactId") %>'">
      <% } %>
        <br><br>
        <%-- include the message form --%>
        <%@ include file="../newmessage.jsp" %>
        <br>
        <input type="submit" value="Send">
        <% if("list".equals(request.getParameter("return"))){ %>
        <input type="button" value="Cancel" onClick="javascript:window.location.href='AccountContactsCalls.do?command=View&contactId=<%= request.getParameter("contactId") %>'">
        <% }else{ %>
        <input type="button" value="Cancel" onClick="javascript:window.location.href='AccountContactsCalls.do?command=Details&id=<%= request.getParameter("id") %>&contactId=<%= request.getParameter("contactId") %>'">
      <% } %>
    </td>
  </tr>
</table>
<input type="hidden" name="id" value="<%= request.getParameter("id") %>">
</form>

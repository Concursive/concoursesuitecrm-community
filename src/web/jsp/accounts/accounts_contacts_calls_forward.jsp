<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<%
  String trailSource = request.getParameter("trailSource");
%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<% if (request.getParameter("return") == null || (request.getParameter("return") != null && "list".equals(request.getParameter("return")))) { %>
<a href="Accounts.do?command=Search">Search Results</a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard">Dashboard</a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<% if("accounts".equals(trailSource)){ %>
<a href="AccountsCalls.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Activities</a> >
<% }else{ %>
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Contacts</a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Contact Details</a> >
<a href="AccountContactsCalls.do?command=View&contactId=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Activities</a> >
<% } %>
<dhv:evaluate if="<%= !"list".equals(request.getParameter("return")) %>">
<a href="AccountContactsCalls.do?command=Details&id=<%= request.getParameter("id") %>&contactId=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "view") %>">Activity Details</a> >
</dhv:evaluate>
Forward Activity
</td>
</tr>
</table>
<%-- End Trails --%>
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
        <input type="button" value="Cancel" onClick="javascript:window.location.href='AccountContactsCalls.do?command=View&contactId=<%= request.getParameter("contactId") %><%= addLinkParams(request, "view|trailSource") %>'">
        <% }else{ %>
        <input type="button" value="Cancel" onClick="javascript:window.location.href='AccountContactsCalls.do?command=Details&id=<%= request.getParameter("id") %>&contactId=<%= request.getParameter("contactId") %><%= addLinkParams(request, "view|trailSource") %>'">
      <% } %>
        <br><br>
        <%-- include the message form --%>
        <%@ include file="../newmessage.jsp" %>
        <br>
        <input type="submit" value="Send">
        <% if("list".equals(request.getParameter("return"))){ %>
        <input type="button" value="Cancel" onClick="javascript:window.location.href='AccountContactsCalls.do?command=View&contactId=<%= request.getParameter("contactId") %><%= addLinkParams(request, "view|trailSource") %>'">
        <% }else{ %>
        <input type="button" value="Cancel" onClick="javascript:window.location.href='AccountContactsCalls.do?command=Details&id=<%= request.getParameter("id") %>&contactId=<%= request.getParameter("contactId") %><%= addLinkParams(request, "view|trailSource") %>'">
      <% } %>
    </td>
  </tr>
</table>
<input type="hidden" name="id" value="<%= request.getParameter("id") %>">
</form>

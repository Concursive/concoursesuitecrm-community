<%@ page contentType="text/plain" %>
<%@ page import="java.util.*,com.darkhorseventures.utils.*" %>
<jsp:useBean id="statusMessages" class="java.util.ArrayList" scope="request"/>
<%@ include file="initPage.jsp" %>
<?xml version="1.0" standalone="yes"?> 
<aspcfs>
<%
  Iterator i = statusMessages.iterator();
  while (i.hasNext()) {
    StatusMessage thisMessage = (StatusMessage)i.next();
%>
  <response<%= ((thisMessage.getId() > -1)?" id=\"" + thisMessage.getId() + "\"":"") %>>
    <status><%= thisMessage.getStatusCode() %></status>
    <errorText><%= toHtmlValue(thisMessage.getMessage()) %></errorText>
  </response>
<%
  }
%>
</aspcfs>

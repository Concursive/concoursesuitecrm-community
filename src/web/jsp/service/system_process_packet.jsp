<%@ page contentType="text/plain" %><%@ include file="initPage.jsp" %><?xml version="1.0" standalone="yes"?> 
<aspcfs>
  <response>
    <status><%= toHtmlValue((String)request.getAttribute("statusCode")) %></status>
    <errorText><%= toHtmlValue((String)request.getAttribute("errorText")) %></errorText>
  </response>
</aspcfs>

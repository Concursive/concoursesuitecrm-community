<%@ page contentType="text/plain" %><%@ include file="initPage.jsp" %><aspcfs>
  <response>
    <status><%= toHtmlValue((String)request.getAttribute("statusCode")) %></status>
    <errorText><%= toHtmlValue((String)request.getAttribute("errorText")) %></errorText>
  </response>
</aspcfs>

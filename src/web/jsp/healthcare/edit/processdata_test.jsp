<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="FileString" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<html>
<head>
<title>test</title>
<body>
<%=toHtml(FileString)%>
</body>
</html>

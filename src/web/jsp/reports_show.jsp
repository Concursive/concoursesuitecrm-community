<%@ include file="../initPage.jsp" %>
<jsp:useBean id="ReportText" class="java.lang.String" scope="request"/>
<%= ReportText.toString() %>
<p>
<input type="button" value="Close Window" onClick="javascript:window.close();">

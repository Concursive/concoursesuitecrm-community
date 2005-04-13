<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="initPage.jsp" %>
<jsp:useBean id="ReportText" class="java.lang.String" scope="request"/>
<%= ReportText.toString() %>
<p>
<input type="button" value="<dhv:label name="button.closeWindow" param="temp=">Close Window</dhv:label>" onClick="javascript:window.close();">

<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Task" class="com.darkhorseventures.cfsbase.Task" scope="request"/>
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>
<%@ include file="initPage.jsp" %>
<body onLoad="javascript:parent.opener.window.location.href='<%=refreshUrl%>';javascript:parent.window.close()">



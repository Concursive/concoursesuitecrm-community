<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="redirectUrl" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:window.opener.location=window.opener.location;javascript:window.location.href='<%= redirectUrl %>&popup=true';">



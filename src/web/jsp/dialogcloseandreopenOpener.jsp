<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>
<jsp:useBean id="id" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:try {parent.opener.reopenOnDelete('<%= id %>');} catch (oException){}parent.window.close();">



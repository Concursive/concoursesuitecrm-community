<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="siteId" class="java.lang.String" scope="request"/>
<%@ page import="java.util.*" %>
<body onLoad="javascript:parent.continueSetSite('<%= siteId %>', '<%= (String)request.getAttribute("item") %>');">
<zeroio:debug value="<%= "JSP::setsiteid the site id is "+ siteId %>"/>
</body>

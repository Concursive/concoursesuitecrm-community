<%@ page import="org.aspcfs.utils.StringUtils"%>
<%--
- Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
- rights reserved. This material cannot be distributed without written
- permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
- this material for internal use is hereby granted, provided that the above
- copyright notice and this permission notice appear in all copies. DARK HORSE
- VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
- IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
- IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
- PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
- INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
- EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
- ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
- DAMAGES RELATING TO THE SOFTWARE.
-
- Version: $Id:  $
- Description:
--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/portlet.tld" prefix="portlet"%>
<%@ include file="../../initPage.jsp"%>
<jsp:useBean id="ORDER_THANKYOU_MESSAGE" class="java.lang.String"
	scope="request" />
<jsp:useBean id="ORDER_ERROR_MESSAGE" class="java.lang.String"
	scope="request" />
	<jsp:useBean id="error" class="java.lang.String"
	scope="request" />
<portlet:defineObjects />
<dhv:evaluate if="<%=ORDER_THANKYOU_MESSAGE != null%>">
	<%=ORDER_THANKYOU_MESSAGE%>
</dhv:evaluate>
<%if(ORDER_ERROR_MESSAGE != null && !"".equals(ORDER_ERROR_MESSAGE)){%>
	<%=ORDER_ERROR_MESSAGE%>
	<br>
	<%=error %>
	<portlet:renderURL var="url">
		<portlet:param name="viewType" value="addCreditCard" />
	</portlet:renderURL>
	<br>
	<a href="<%=pageContext.getAttribute("url")%>">Change billing
		information</a>
<%}%>

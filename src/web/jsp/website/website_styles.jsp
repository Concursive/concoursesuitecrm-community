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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
	<tr>
		<th style="text-align:left;">
      <table align="center" width="100%" cellpadding="4" cellspacing="0" border="0"><tr><td nowrap valign="bottom" align="left">
        Styles
      </td></tr></table>
		</th>
	</tr>
  <tr>
		<td>
      <c:forEach items="${styleList}" var="style" varStatus="status">
        <c:set var="style" value="${style}"/>
        <jsp:useBean id="style" type="org.aspcfs.modules.website.base.Style" />
        <div style="float: left;width: 140px;height: 160px;padding-right: 3px;">
          <table border="0" width="140" height="160" cellpadding="0" cellspacing="0">
            <tr><td width="140" height="160" valign="top">
              <c:url value="Sites.do" var="thisURL">
                <c:param name="command" value="UpdateStyle"/>
                <c:param name="siteId" value="${param.siteId}"/>
                <c:param name="styleId" value="${style.id}"/>
                <c:param name="popup" value="true"/>
              </c:url>
              <a href="javascript:window.opener.location.href='<c:out value="${thisURL}';top.close()"/>">
              <img src="portal/styles/<%= style.getThumbnail() %>" alt="<%= style.getName() %>" /> 
              <div><c:out value="${style.name}" /></div></a>
            </td></tr>
          </table>
				</div>
      </c:forEach>
      <c:if test="${status.count == 0}">
        No styles found.
      </c:if>
    </td>
	</tr>
</table>

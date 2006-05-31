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
- Version: $Id: accounts_add.jsp 13563 2005-12-12 16:13:25Z mrajkowski $
- Description:
--%>
<%@ page import="org.aspcfs.utils.StringUtils"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="thankyou" class="java.lang.String" scope="request"/>
<jsp:useBean id="nameFirst" class="java.lang.String" scope="request"/>
<dhv:evaluate if="<%= StringUtils.hasText(thankyou) %>">
  <%= StringUtils.toHtml(thankyou) %>
</dhv:evaluate>
<dhv:evaluate if="<%= !StringUtils.hasText(thankyou) %>">
Thanks <%= StringUtils.toHtml(nameFirst) %> for contacting us.<br />
<br />
We typically respond within 24-48 hours depending on the request.
</dhv:evaluate>

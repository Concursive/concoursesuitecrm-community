<%--
- Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
- Version: $Id: Exp$
- Description:
--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="selectedDashboard" class="java.lang.String" scope="request" />
<jsp:useBean id="moduleId" class="java.lang.String" scope="request" />
<jsp:useBean id="action" class="java.lang.String" scope="request" />
<jsp:useBean id="menuName" class="java.lang.String" scope="request" />
<jsp:useBean id="ModuleBean" class="org.aspcfs.modules.beans.ModuleBean" scope="request" />

<%@ include file="initPage.jsp" %>

<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href='<%=action+"?command=Dashboards&action="+action+"&menu="+menuName%>'><dhv:label name=""><%=menuName%></dhv:label></a> > 
<dhv:label name="">Dashboards</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

<dhv:container name='<%="dashboards"+moduleId%>' selected='<%= selectedDashboard%>' object="Page" appendToUrl="<%="&action=" + action+"&menu=" + menuName%>">
<%@ include file="/admin/dashboards_include.jsp" %>
</dhv:container>
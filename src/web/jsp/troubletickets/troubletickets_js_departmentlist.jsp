<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.admin.base.User, org.aspcfs.modules.contacts.base.Contact, org.aspcfs.utils.web.HtmlOption" %>
<jsp:useBean id="resourceAssignedList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="resolvedByList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="resourceAssignedSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="resolvedBySelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<html>
<body onload="page_init();">
<script language="JavaScript">
<%
  String populateResourceAssigned = request.getParameter("populateResourceAssigned");
  String resourceAssignedDepartmentCode = request.getParameter("resourceAssignedDepartmentCode");

  String populateResolvedBy = request.getParameter("populateResolvedBy");
  String resolvedByDepartmentCode = request.getParameter("resolvedByDepartmentCode");

  String dept = request.getParameter("dept");
  String form = request.getParameter("form");
%>
function newOpt(param, value) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  return newOpt;
}
function page_init() {
<dhv:evaluate if='<%= "true".equals(populateResourceAssigned) %>'>
  <dhv:evaluate if="<%= ((resourceAssignedList.size() > 0) || (resourceAssignedDepartmentCode != null)) %>">
    var resourceAssignedWidget = parent.document.<%= form %>.assignedTo;
    resourceAssignedWidget.options.length = 0;
  <%
    Iterator resourceAssignedIterator = resourceAssignedSelect.iterator();
    while (resourceAssignedIterator.hasNext()) {
      HtmlOption option = (HtmlOption) resourceAssignedIterator.next();
      int value = Integer.parseInt(option.getValue());
      String text = option.getText();
      if (!"".equals(text.trim())){
  %>
        resourceAssignedWidget.options[resourceAssignedWidget.length] = newOpt("<%= text %>", "<%= value %>");
  <%
      }
    }
  %>
  </dhv:evaluate>
</dhv:evaluate>
<dhv:evaluate if='<%= "true".equals(populateResolvedBy) %>'>
  <dhv:evaluate if="<%= ((resolvedByList.size() > 0) || (resolvedByDepartmentCode != null)) %>">
    var resolvedByWidget = parent.document.<%= form %>.resolvedBy;
    resolvedByWidget.options.length = 0;
  <%
    Iterator resolvedByIterator = resolvedBySelect.iterator();
    while (resolvedByIterator.hasNext()) {
      HtmlOption option = (HtmlOption) resolvedByIterator.next();
      int value = Integer.parseInt(option.getValue());
      String text = option.getText();
      if (!"".equals(text.trim())){
  %>
          resolvedByWidget.options[resolvedByWidget.length] = newOpt("<%= text%>", "<%= value %>");
  <%
      }
    }
  %>
  </dhv:evaluate>
</dhv:evaluate>
}
</script>
</body>
</html>
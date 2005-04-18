<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.admin.base.User, org.aspcfs.modules.contacts.base.Contact" %>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/><html>
<body onload="page_init();">
<script language="JavaScript">
<%
  String departmentCode = request.getParameter("departmentCode");
  String form = request.getParameter("form");
%>
function newOpt(param, value) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  return newOpt;
}
function page_init() {
<dhv:evaluate if="<%= ((UserList.size() > 0) || (departmentCode != null)) %>">
  var list = parent.document.<%= form %>.assignedTo;
  list.options.length = 0;
  list.options[list.length] = newOpt(label("option.none","-- None --"), "0");
<%
  Iterator list1 = UserList.iterator();
  while (list1.hasNext()) {
    User thisUser = (User)list1.next();
    if (thisUser.getId() != 0) {
%>
  list.options[list.length] = newOpt("<%= thisUser.getContact().getValidName() %>", "<%= thisUser.getId() %>");
<%
    }
  }
%>
</dhv:evaluate>
}
</script>
</body>

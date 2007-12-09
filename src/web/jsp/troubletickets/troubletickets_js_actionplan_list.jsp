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
<%@ page import="java.util.*,org.aspcfs.modules.actionplans.base.*" %>
<jsp:useBean id="actionPlans" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<body onload="page_init();">
<script language="JavaScript">
<%
  String actionPlan = request.getParameter("actionplan");
  String form = request.getParameter("form");
%>
function newOpt(param, value) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  return newOpt;
}
function page_init() {
<dhv:evaluate if="<%= actionPlans.size() > 0 %>">
  var list = parent.document.forms['<%= form %>'].elements['actionPlanId'];
  list.options.length = 0;
  list.options[list.length] = newOpt(label("label.undetermined","Undetermined"), "0");
<%
  Iterator list1 = actionPlans.iterator();
  while (list1.hasNext()) {
    ActionPlan thisPlan = (ActionPlan)list1.next();
     if (thisPlan.getEnabled()) {
%>
  list.options[list.length] = newOpt("<%= thisPlan.getDescription() %>", "<%= thisPlan.getId() %>");
<%
  }
 }
%>
</dhv:evaluate>
}

function resetList(list) {
  list.options.length = 0;
  list.options[list.length] = newOpt(label("label.undetermined","Undetermined"), "0");
}
</script>
</body>

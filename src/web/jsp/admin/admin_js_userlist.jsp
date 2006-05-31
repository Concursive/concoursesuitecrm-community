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
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.admin.base.User, org.aspcfs.modules.contacts.base.Contact, org.aspcfs.utils.web.HtmlOption" %>
<jsp:useBean id="UserListSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<html>
<body onload="page_init();">
<script language="JavaScript">
<%
  String form = request.getParameter("form");
  String widget = request.getParameter("widget");
  String allowBlank = request.getParameter("allowBlank");
%>
function newOpt(param, value) {
  var thisOpt = parent.document.createElement("OPTION");
	thisOpt.text=param;
	thisOpt.value=value;
  return thisOpt;
}
function page_init() {
  var list = parent.document.<%= form %>.<%= widget %>;
  list.options.length = 0;
<%
  Iterator userIterator = UserListSelect.iterator();
  while (userIterator.hasNext()){
    HtmlOption option = (HtmlOption) userIterator.next();
    int value = Integer.parseInt(option.getValue());
    String text = option.getText();
%>
  list.options[list.length] = newOpt("<%= text %>", "<%= value %>");
<%
  }
%>
}
</script>
</body>
</html>
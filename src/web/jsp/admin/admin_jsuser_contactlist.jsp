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
<%@ page import="java.util.*,org.aspcfs.modules.admin.base.*,org.aspcfs.modules.contacts.base.Contact" %>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<html>
<head>
</head>
<body onload="page_init();">
<script language="JavaScript">
<%
  String typeId = request.getParameter("typeId");
%>
function newOpt(param, value) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  return newOpt;
}
function page_init() {
<dhv:evaluate exp="<%= ((ContactList.size() > 0) || (typeId != null)) %>">
  var list = parent.document.forms[0].elements['contactId'];
  list.options.length = 0;
  list.options[list.length] = newOpt("-- Please Select --", "0");
<%
  Iterator list1 = ContactList.iterator();
  while (list1.hasNext()) {
    Contact thisContact = (Contact)list1.next();
    if (!thisContact.hasAccount()) {
%>
  list.options[list.length] = newOpt("<%= thisContact.getNameLastFirst() %>", "<%= thisContact.getId() %>");
<%
    }
  }
%>
</dhv:evaluate>
}
</script>
</body>
</html>


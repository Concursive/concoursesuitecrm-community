<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="ContactList" class="com.darkhorseventures.cfsbase.ContactList" scope="request"/>
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


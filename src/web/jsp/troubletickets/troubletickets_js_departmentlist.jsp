<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.admin.base.User, org.aspcfs.modules.contacts.base.Contact" %>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/><html>
<head>
</head>
<body onload="page_init();">
<script language="JavaScript">
<%
  String departmentCode = request.getParameter("departmentCode");
%>
function newOpt(param, value) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  return newOpt;
}
function page_init() {
<dhv:evaluate exp="<%= ((UserList.size() > 0) || (departmentCode != null)) %>">
  var list = parent.document.forms[0].elements['assignedTo'];
  list.options.length = 0;
  list.options[list.length] = newOpt("-- None --", "0");
<%
  Iterator list1 = UserList.iterator();
  while (list1.hasNext()) {
    User thisUser = (User)list1.next();
%>
  list.options[list.length] = newOpt("<%= Contact.getNameLastFirst(thisUser.getContact().getNameLast(),
          thisUser.getContact().getNameFirst()) %>", "<%= thisUser.getId() %>");
<%
  }
%>
</dhv:evaluate>
}
</script>
</body>
</html>


<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="ContactList" class="com.darkhorseventures.cfsbase.ContactList" scope="request"/><html>
<head>
</head>
<body onload="page_init();">
<script language="JavaScript">
<%
  String orgId = request.getParameter("orgId");
%>
function page_init() {

  var list = parent.document.forms['addticket'].elements['contactId'];
  list.options.length = 0;
<dhv:evaluate exp="<%= (ContactList.size() == 0) %>">
  list.options[list.length] = new Option("-- None --", "-1");
</dhv:evaluate>
<%
  Iterator list1 = ContactList.iterator();
  while (list1.hasNext()) {
    Contact thisContact = (Contact)list1.next();
%>
  list.options[list.length] = new Option("<%= Contact.getNameLastFirst(thisContact.getNameLast(),
          thisContact.getNameFirst()) %>", "<%= thisContact.getId() %>");
<%
  }
%>

}
</script>
</body>
</html>


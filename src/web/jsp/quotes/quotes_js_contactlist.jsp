<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.contacts.base.Contact" %>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/><html>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:page_init();">
<script language="JavaScript">
<%
  String orgId = request.getParameter("orgId");
%>
function newOpt(param, value) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  return newOpt;
}
function page_init() {
  var list = parent.document.forms['addQuote'].elements['contactId'];
  list.options.length = 0;
<dhv:evaluate if="<%= (ContactList.size() == 0) %>">
  list.options[list.length] = newOpt(label("option.none","-- None --"), "-1");
</dhv:evaluate>
<%
  Iterator list1 = ContactList.iterator();
  while (list1.hasNext()) {
    Contact thisContact = (Contact)list1.next();
%>
  list.options[list.length] = newOpt("<%= toJavaScript(thisContact.getValidName()) %>","<%= thisContact.getId() %>");
<%
  }
%>
}
</script>
</body>

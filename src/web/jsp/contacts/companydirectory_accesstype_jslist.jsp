<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*, org.aspcfs.modules.admin.base.AccessType" %>
<jsp:useBean id="AccessTypeList" class="org.aspcfs.modules.admin.base.AccessTypeList" scope="request"/>
<html>
<head>
</head>
<body onload="page_init();">
<script language="JavaScript">
function newOpt(param, value) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  return newOpt;
}
function page_init() {
  var list = parent.document.getElementById('accessType');
  list.options.length = 0;
<%
  Iterator list1 = AccessTypeList.iterator();
  if(list1.hasNext()){
    while (list1.hasNext()) {
      AccessType thisType = (AccessType) list1.next();
      String elementText = thisType.getDescription();
  %>
    list.options[list.length] = newOpt('<%= elementText %>', '<%= thisType.getCode() %>');
  <%
    }
  }else{%>
    list.options[list.length] = newOpt("--None--", "-1");
 <%}%>
}
</script>
</body>
</html>


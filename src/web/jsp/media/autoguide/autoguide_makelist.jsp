<%@ page import="java.util.*,org.aspcfs.modules.media.autoguide.base.*" %>
<jsp:useBean id="MakeList" class="org.aspcfs.modules.media.autoguide.base.MakeList" scope="request"/>
<html>
<head>
</head>
<body onload='page_init();'>
<script language='Javascript'>
function newOpt(param, value) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  return newOpt;
}
function page_init() {
  var list = parent.document.forms['addVehicle'].elements['vehicle_makeId'];
  list.options.length = 0;
  list.options[list.length] = newOpt("--None--", -1);
  
  var list2 = parent.document.forms['addVehicle'].elements['vehicle_modelId'];
  list2.options.length = 0;
  list2.options[list2.length] = newOpt("--None--", "-1");
<%
  Iterator i = MakeList.iterator();
  while (i.hasNext()) {
    Make thisMake = (Make)i.next();
%>
  list.options[list.length] = newOpt("<%= thisMake.getName() %>", "<%= thisMake.getId() %>");
<%
  }
%>
}
</script>
</body>
</html>


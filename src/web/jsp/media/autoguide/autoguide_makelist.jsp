<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.autoguide.base.*" %>
<jsp:useBean id="MakeList" class="com.darkhorseventures.autoguide.base.MakeList" scope="request"/>
<html>
<head>
</head>
<body onload='page_init();'>
<script language='Javascript'>
function page_init() {
  var list = parent.document.forms['addVehicle'].elements['vehicle_makeId'];
  list.options.length = 0;
  list.options[list.length] = new Option("--None--", "-1");
  
  var list2 = parent.document.forms['addVehicle'].elements['vehicle_modelId'];
  list2.options.length = 0;
  list2.options[list2.length] = new Option("--None--", "-1");
<%
  Iterator i = MakeList.iterator();
  while (i.hasNext()) {
    Make thisMake = (Make)i.next();
%>
  list.options[list.length] = new Option("<%= thisMake.getName() %>", "<%= thisMake.getId() %>");
<%
  }
%>
}
</script>
</body>
</html>


<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.autoguide.base.*" %>
<jsp:useBean id="ModelList" class="com.darkhorseventures.autoguide.base.ModelList" scope="request"/>
<html>
<head>
</head>
<body onload='page_init();'>
<script language='Javascript'>
function page_init() {
  var list = parent.document.forms['addVehicle'].elements['modelId'];
  list.options.length = 0;
  list.options[list.length] = new Option("--None--", "-1");
<%
  Iterator i = ModelList.iterator();
  while (i.hasNext()) {
    Model thisModel = (Model)i.next();
%>
  list.options[list.length] = new Option("<%= thisModel.getName() %>", "<%= thisModel.getId() %>");
<%
  }
%>
}
</script>
</body>
</html>


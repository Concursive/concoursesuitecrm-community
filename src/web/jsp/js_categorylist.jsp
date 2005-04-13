<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.Category" %>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<html>
<head>
</head>
<%
  String form = request.getParameter("form");
%>
<body onload="page_init();">
<script language="JavaScript">
function newOpt(param, value) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  return newOpt;
}
function page_init() {
  var list = parent.document.<%= form %>.elements['level<%= categoryList.getCatLevel() + 1 %>'];
  resetList(list);
<%
  Iterator list = categoryList.iterator();
  while (list.hasNext()) {
    Category thisCategory = (Category) list.next();
    if (thisCategory.getEnabled()) {
%>
  list.options[list.length] = newOpt("<%= thisCategory.getDescription() %>", "<%= thisCategory.getId() %>");
<%
    }
  }
  // TODO: This is the only line that is not reusable because it assumes a
  //       total of 3 levels
  for (int k = categoryList.getCatLevel() + 2; k < 4; k++) {
%>
  resetList(parent.document.<%= form %>.elements['level<%= k %>']);
<%}%>
}
function resetList(list) {
  list.options.length = 0;
  list.options[list.length] = newOpt("Undetermined", "-1");
}
</script>
</body>
</html>


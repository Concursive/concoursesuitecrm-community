<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
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
  var level = 'level' + (parseInt('<%= request.getParameter("level")%>') + 1);
  var list = parent.document.getElementById(level);
  list.options.length = 0;
<%
  Iterator list1 = CategoryList.iterator();
  if(list1.hasNext()){
    while (list1.hasNext()) {
      TicketCategory thisCategory = (TicketCategory)list1.next();
      String elementText = thisCategory.getDescription();
      if (!(thisCategory.getEnabled())) {
        elementText += " *";
      }
  %>
    list.options[list.length] = newOpt("<%= elementText %>", "<%= thisCategory.getId() %>");
  <%
    }
  }else{%>
    list.options[list.length] = newOpt("--None--", "-1");
 <%}%>
  if(level == 'level1'){
    resetList(parent.document.getElementById('level2'));
    resetList(parent.document.getElementById('level3'));
  }else if(level == 'level2'){
    resetList(parent.document.getElementById('level3'));
  }
}
function resetList(list) {
  list.options.length = 0;
  list.options[list.length] = newOpt("--None--", "-1");
}
</script>
</body>
</html>


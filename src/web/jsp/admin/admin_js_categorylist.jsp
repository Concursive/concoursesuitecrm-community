<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryDraftList" scope="request"/>
<jsp:useBean id="ParentCategory" class="org.aspcfs.modules.troubletickets.base.TicketCategoryDraft" scope="request"/>
<html>
<head>
</head>
<body onload="page_init();">
<script language="JavaScript">
function newOpt(param, value, color) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  if(color != '-none-'){
    newOpt.style.color =color;
  }
  return newOpt;
}
function page_init() {
  var level = parseInt('<%= request.getParameter("level")%>') + 1;
  var list = parent.document.getElementById('level' + level);
  list.options.length = 0;
<%
  Iterator list1 = CategoryList.iterator();
  if(list1.hasNext()){
    while (list1.hasNext()) {
      TicketCategoryDraft thisCategory = (TicketCategoryDraft)list1.next();
      String elementText = thisCategory.getDescription();
  %>
    list.options[list.length] = newOpt('<%= elementText %>', '<%= thisCategory.getId() %>', '<%= !(thisCategory.getEnabled()) ? "Red" : (thisCategory.getActualCatId() == -1 ? "blue" : "-none-") %>');
  <%
    }
  }else{%>
    list.options[list.length] = newOpt("---------None---------", "-1");
 <%}%>
  if(level == '0'){
    resetList(parent.document.getElementById('level1'));
    resetList(parent.document.getElementById('level2'));
    resetList(parent.document.getElementById('level3'));
  }else if(level == '1'){
    resetList(parent.document.getElementById('level2'));
    resetList(parent.document.getElementById('level3'));
  }else if(level == '2'){
    resetList(parent.document.getElementById('level3'));
  }
  
  //disable the edit button if the parent is disabled
  <% if(!ParentCategory.getEnabled()){ %>
      parent.document.getElementById('edit' + level).disabled = true;
  <% } %>
}
function resetList(list) {
  list.options.length = 0;
  list.options[list.length] = newOpt("---------None---------", "-1");
}
</script>
</body>
</html>


<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="SubList1" class="com.darkhorseventures.cfsbase.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="com.darkhorseventures.cfsbase.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="com.darkhorseventures.cfsbase.TicketCategoryList" scope="request"/>
<html>
<head>
</head>
<body onload="page_init();">
<script language="JavaScript">
<%
  String catCode = request.getParameter("catCode");
  String subCat1 = request.getParameter("subCat1");
  String subCat2 = request.getParameter("subCat2"); 
%>
function page_init() {
<dhv:evaluate exp="<%= ((SubList1.size() > 0) || (catCode != null)) %>">
  var list = parent.document.forms['addticket'].elements['subCat1'];
  list.options.length = 0;
  list.options[list.length] = new Option("Undetermined", "0");
<%
  Iterator list1 = SubList1.iterator();
  while (list1.hasNext()) {
    TicketCategory thisCategory = (TicketCategory)list1.next();
%>
  list.options[list.length] = new Option("<%= thisCategory.getDescription() %>", "<%= thisCategory.getId() %>");
<%
  }
%>
  resetList(parent.document.forms['addticket'].elements['subCat2']);
  resetList(parent.document.forms['addticket'].elements['subCat3']);
</dhv:evaluate>
  
<dhv:evaluate exp="<%= ((SubList2.size() > 0) || (subCat1 != null)) %>">
  var list2 = parent.document.forms['addticket'].elements['subCat2'];
  list2.options.length = 0;
  list2.options[list2.length] = new Option("Undetermined", "0");
<%
  Iterator list2 = SubList2.iterator();
  while (list2.hasNext()) {
    TicketCategory thisCategory = (TicketCategory)list2.next();
%>
  list2.options[list2.length] = new Option("<%= thisCategory.getDescription() %>", "<%= thisCategory.getId() %>");
<%
  }
%>
  resetList(parent.document.forms['addticket'].elements['subCat3']);
</dhv:evaluate>
  
<dhv:evaluate exp="<%= ((SubList3.size() > 0) || (subCat2 != null)) %>">
  var list3 = parent.document.forms['addticket'].elements['subCat3'];
  list3.options.length = 0;
  list3.options[list3.length] = new Option("Undetermined", "0");
<%
  Iterator list3 = SubList3.iterator();
  while (list3.hasNext()) {
    TicketCategory thisCategory = (TicketCategory)list3.next();
%>
  list3.options[list3.length] = new Option("<%= thisCategory.getDescription() %>", "<%= thisCategory.getId() %>");
<%
  }
%>
</dhv:evaluate>
}

function resetList(list) {
  list.options.length = 0;
  list.options[list.length] = new Option("Undetermined", "0");
}
</script>
</body>
</html>


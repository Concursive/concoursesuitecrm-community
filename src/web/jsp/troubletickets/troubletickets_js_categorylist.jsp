<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
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
function newOpt(param, value) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  return newOpt;
}
function page_init() {
<dhv:evaluate exp="<%= ((SubList1.size() > 0) || (catCode != null)) %>">
  var list = parent.document.forms[0].elements['subCat1'];
  list.options.length = 0;
  list.options[list.length] = newOpt("Undetermined", "0");
<%
  Iterator list1 = SubList1.iterator();
  while (list1.hasNext()) {
    TicketCategory thisCategory = (TicketCategory)list1.next();
     if (thisCategory.getEnabled()) {
%>
  list.options[list.length] = newOpt("<%= thisCategory.getDescription() %>", "<%= thisCategory.getId() %>");
<%
  }
 }
%>
<dhv:include name="ticket.subCat2" none="true">
  resetList(parent.document.forms[0].elements['subCat2']);
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  resetList(parent.document.forms[0].elements['subCat3']);
</dhv:include>
</dhv:evaluate>
  
<dhv:evaluate exp="<%= ((SubList2.size() > 0) || (subCat1 != null)) %>">
  var list2 = parent.document.forms[0].elements['subCat2'];
  list2.options.length = 0;
  list2.options[list2.length] = newOpt("Undetermined", "0");
<%
  Iterator list2 = SubList2.iterator();
  while (list2.hasNext()) {
    TicketCategory thisCategory = (TicketCategory)list2.next();
    String elementText = thisCategory.getDescription();
      if (thisCategory.getEnabled()) {
%>
  list2.options[list2.length] = newOpt("<%= elementText %>", "<%= thisCategory.getId() %>");
<%
  }
 }
%>
<dhv:include name="ticket.subCat3" none="true">
  resetList(parent.document.forms[0].elements['subCat3']);
</dhv:include>
</dhv:evaluate>
  
<dhv:evaluate exp="<%= ((SubList3.size() > 0) || (subCat2 != null)) %>">
  var list3 = parent.document.forms[0].elements['subCat3'];
  list3.options.length = 0;
  list3.options[list3.length] = newOpt("Undetermined", "0");
<%
  Iterator list3 = SubList3.iterator();
  while (list3.hasNext()) {
    TicketCategory thisCategory = (TicketCategory)list3.next();
     if (thisCategory.getEnabled()) {
%>
  list3.options[list3.length] = newOpt("<%= thisCategory.getDescription() %>", "<%= thisCategory.getId() %>");
<%  
    }
  }
%>
</dhv:evaluate>
}

function resetList(list) {
  list.options.length = 0;
  list.options[list.length] = newOpt("Undetermined", "0");
}
</script>
</body>
</html>


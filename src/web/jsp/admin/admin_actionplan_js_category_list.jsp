<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.actionplans.base.*" %>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.actionplans.base.ActionPlanCategoryList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.actionplans.base.ActionPlanCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.actionplans.base.ActionPlanCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.actionplans.base.ActionPlanCategoryList" scope="request"/>
<body onload="page_init();">
<script language="JavaScript">
<%
  String reset = request.getParameter("reset");
  String catCode = request.getParameter("catCode");
  String subCat1 = request.getParameter("subCat1");
  String subCat2 = request.getParameter("subCat2");
  String form = request.getParameter("form");
%>
function newOpt(param, value) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  return newOpt;
}
function page_init() {
<dhv:evaluate if="<%= (CategoryList.size() > 0 || (reset != null && "true".equals(reset))) %>">
  var list = parent.document.forms['<%= form %>'].elements['catCode'];
  list.options.length = 0;
  list.options[list.length] = newOpt("Undetermined", "0");
<%
  Iterator list1 = CategoryList.iterator();
  while (list1.hasNext()) {
    ActionPlanCategory thisCategory = (ActionPlanCategory)list1.next();
     if (thisCategory.getEnabled()) {
%>
  list.options[list.length] = newOpt("<%= thisCategory.getDescription() %>", "<%= thisCategory.getId() %>");
<%
  }
 }
%>
<dhv:include name="ticket.subCat1" none="true">
  resetList(parent.document.forms['<%= form %>'].elements['subCat1']);
  parent.document.forms['<%= form %>'].elements['subCat1'].options.selectedIndex = 0;
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  resetList(parent.document.forms['<%= form %>'].elements['subCat2']);
  parent.document.forms['<%= form %>'].elements['subCat2'].options.selectedIndex = 0;
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  resetList(parent.document.forms['<%= form %>'].elements['subCat3']);
  parent.document.forms['<%= form %>'].elements['subCat3'].options.selectedIndex = 0;
</dhv:include>
</dhv:evaluate>
<dhv:evaluate if="<%= ((SubList1.size() > 0) || (catCode != null)) %>">
  var list = parent.document.forms['<%= form %>'].elements['subCat1'];
  list.options.length = 0;
  list.options[list.length] = newOpt("Undetermined", "0");
<%
  Iterator list1 = SubList1.iterator();
  while (list1.hasNext()) {
    ActionPlanCategory thisCategory = (ActionPlanCategory)list1.next();
     if (thisCategory.getEnabled()) {
%>
  list.options[list.length] = newOpt("<%= thisCategory.getDescription() %>", "<%= thisCategory.getId() %>");
<%
  }
 }
%>
<dhv:include name="ticket.subCat2" none="true">
  resetList(parent.document.forms['<%= form %>'].elements['subCat2']);
  parent.document.forms['<%= form %>'].elements['subCat2'].options.selectedIndex = 0;
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  resetList(parent.document.forms['<%= form %>'].elements['subCat3']);
  parent.document.forms['<%= form %>'].elements['subCat3'].options.selectedIndex = 0;
</dhv:include>
</dhv:evaluate>
  
<dhv:evaluate if="<%= ((SubList2.size() > 0) || (subCat1 != null)) %>">
  var list2 = parent.document.forms['<%= form %>'].elements['subCat2'];
  list2.options.length = 0;
  list2.options[list2.length] = newOpt("Undetermined", "0");
<%
  Iterator list2 = SubList2.iterator();
  while (list2.hasNext()) {
    ActionPlanCategory thisCategory = (ActionPlanCategory)list2.next();
    String elementText = thisCategory.getDescription();
      if (thisCategory.getEnabled()) {
%>
  list2.options[list2.length] = newOpt("<%= elementText %>", "<%= thisCategory.getId() %>");
<%
  }
 }
%>
<dhv:include name="ticket.subCat3" none="true">
  resetList(parent.document.forms['<%= form %>'].elements['subCat3']);
  parent.document.forms['<%= form %>'].elements['subCat3'].options.selectedIndex = 0;
</dhv:include>
</dhv:evaluate>
  
<dhv:evaluate if="<%= ((SubList3.size() > 0) || (subCat2 != null)) %>">
  var list3 = parent.document.forms['<%= form %>'].elements['subCat3'];
  list3.options.length = 0;
  list3.options[list3.length] = newOpt("Undetermined", "0");
<%
  Iterator list3 = SubList3.iterator();
  while (list3.hasNext()) {
    ActionPlanCategory thisCategory = (ActionPlanCategory)list3.next();
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

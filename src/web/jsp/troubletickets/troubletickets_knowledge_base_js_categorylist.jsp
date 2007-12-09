<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.login.beans.*" %>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/> 
<body onload="page_init();">
<script language="JavaScript">
<%
  String from = request.getParameter("from");
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
<dhv:evaluate if="<%= ((CategoryList.size() > 0) || (reset != null && "true".equals(reset.trim()))) %>">
  var list = parent.document.forms['<%= form %>'].elements['searchcodeCatCode'];
  list.options.length = 0;
  list.options[list.length] = newOpt("Undetermined", "0");
<%
  Iterator list1 = CategoryList.iterator();
  while (list1.hasNext()) {
    TicketCategory thisCategory = (TicketCategory)list1.next();
     if (thisCategory.getEnabled()) {
%>
  list.options[list.length] = newOpt("<%= thisCategory.getDescription() %>", "<%= thisCategory.getId() %>");
<%
  }
 }
%>
<dhv:include name="ticket.subCat1" none="true">
  resetList(parent.document.forms['<%= form %>'].elements['searchcodeSubCat1']);
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  resetList(parent.document.forms['<%= form %>'].elements['searchcodeSubCat2']);
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  resetList(parent.document.forms['<%= form %>'].elements['searchcodeSubCat3']);
</dhv:include>
</dhv:evaluate>
<dhv:evaluate if="<%= ((SubList1.size() > 0) || (catCode != null)) %>">
  var list = parent.document.forms['<%= form %>'].elements['searchcodeSubCat1'];
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
  resetList(parent.document.forms['<%= form %>'].elements['searchcodeSubCat2']);
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  resetList(parent.document.forms['<%= form %>'].elements['searchcodeSubCat3']);
</dhv:include>
</dhv:evaluate>
  
<dhv:evaluate if="<%= ((SubList2.size() > 0) || (subCat1 != null)) %>">
  var list2 = parent.document.forms['<%= form %>'].elements['searchcodeSubCat2'];
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
  resetList(parent.document.forms['<%= form %>'].elements['searchcodeSubCat3']);
</dhv:include>
</dhv:evaluate>

<dhv:evaluate if="<%= ((SubList3.size() > 0) || (subCat2 != null)) %>">
  var list3 = parent.document.forms['<%= form %>'].elements['searchcodeSubCat3'];
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
<dhv:evaluate if='<%= from != null && "kblist".equals(from.trim()) %>'>
  var catCodeElement = parent.document.forms['<%= form %>'].elements['searchcodeCatCode'];
  var subCat1Element = parent.document.forms['<%= form %>'].elements['searchcodeSubCat1']
  var subCat2Element = parent.document.forms['<%= form %>'].elements['searchcodeSubCat2']
  var subCat3Element = parent.document.forms['<%= form %>'].elements['searchcodeSubCat3']
  var site = parent.document.forms['<%= form %>'].elements['searchcodeSiteId']
  var catCode = catCodeElement.options[catCodeElement.options.selectedIndex].value;
  var subCat1 = subCat1Element.options[subCat1Element.options.selectedIndex].value;
  var subCat2 = subCat2Element.options[subCat2Element.options.selectedIndex].value;
  var subCat3 = subCat3Element.options[subCat3Element.options.selectedIndex].value;
  var siteId = -1;
  if ("<%= User.getUserRecord().getSiteId() == -1 && SiteIdList.size() > 1 %>" == "true") {
    siteId = site.options[site.options.selectedIndex].value;
  } else {
    siteId = site.value;
  }
  parent.reopen('&searchcodeSiteId='+siteId+'&searchcodeCatCode='+catCode+'&searchcodeSubCat1='+subCat1+'&searchcodeSubCat2='+subCat2+'&searchcodeSubCat3='+subCat3);
</dhv:evaluate>
}

function resetList(list) {
  list.options.length = 0;
  list.options[list.length] = newOpt("Undetermined", "0");
}
</script>
</body>

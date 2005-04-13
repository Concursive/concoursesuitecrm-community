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
  - Author(s): 
  - Version: $Id$
  - Description: 
  --%>
<%@ page import="java.util.*,org.aspcfs.modules.documents.base.*,org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="documentStoreList" class="org.aspcfs.modules.documents.base.DocumentStoreList" scope="request"/>
<body onload='page_init();'>
<script language='Javascript'>
function page_init() {
  var list = parent.document.forms['documentStoreMemberForm'].elements['selDepartment'];
  list.options.length = 0;
<%
  Iterator i = documentStoreList.iterator();
  while (i.hasNext()) {
    DocumentStore thisDocumentStore = (DocumentStore) i.next();
%>
    var newOpt = parent.document.createElement("OPTION");
    newOpt.text='<%= StringUtils.jsStringEscape(thisDocumentStore.getTitle()) %>';
    newOpt.value='<%= thisDocumentStore.getId() %>';
    list.options[list.length] = newOpt;
<%
  }
%>
}
</script>
</body>

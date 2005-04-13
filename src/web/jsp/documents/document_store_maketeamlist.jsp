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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.documents.base.*,org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="team" class="org.aspcfs.modules.documents.base.DocumentStoreTeamMemberList" scope="request"/>
<body onload='page_init();'>
<script language='Javascript'>
function page_init() {
  var list = parent.document.forms['documentStoreMemberForm'].elements['selTotalList'];
  list.options.length = 0;
<%
  Iterator i = team.iterator();
  while (i.hasNext()) {
    DocumentStoreTeamMember member = (DocumentStoreTeamMember) i.next();
%>
  if ( !(inArray(parent.document.forms['documentStoreMemberForm'].elements['selDocumentStoreList'], <%= member.getItemId() %>)) ) {
    var newOpt = parent.document.createElement("OPTION");
    newOpt.text='<dhv:username id="<%= member.getItemId() %>"/>';
    newOpt.value='<%= member.getItemId() %>';
    list.options[list.length] = newOpt;
  }
  parent.initList(<%= member.getItemId() %>);
<%
  }
%>
}
function inArray(a, s) {
	var i = 0;
	for(i=0; i < a.length; i++) {
		if (a.options[i].value == s) {
			return true;
		}
	}
	return false;
}
</script>
</body>

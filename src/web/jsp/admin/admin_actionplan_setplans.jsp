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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.actionplans.base.*,org.aspcfs.modules.admin.base.*" %>
<%@ page import="org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="planMapList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryDraftPlanMapList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:init_page();">
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script type="text/javascript">
function newOpt(param, value, color) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  if(color != '-none-'){
    newOpt.style.color =color;
  }
  return newOpt;
}
function init_page() {
  var actionList = opener.document.forms['draftCategories'].actionPlanList;
  actionList.options.length = 0;
  <%
    Iterator list = planMapList.iterator();
    if (list.hasNext()) {
      while (list.hasNext()) {
        TicketCategoryDraftPlanMap thisPlanMap = (TicketCategoryDraftPlanMap) list.next();
        String elementText = StringUtils.replacePattern(thisPlanMap.getPlan().getName(), "'", "\\\\'");
    %>
      actionList.options[actionList.length] = newOpt('<%= elementText %>', '<%= thisPlanMap.getId() %>', '<%= thisPlanMap.getPlan().getEnabled()?(thisPlanMap.getCategoryId() == -1 ? "blue" : "-none-"):"Red" %>');
    <%}
    } else {%>
      resetList(actionList);
  <%}%>
  self.close();
}
function resetList(list) {
  list.options.length = 0;
  list.options[list.length] = newOpt("---------None---------", "-1",'-none-');
}
</script>
</body>

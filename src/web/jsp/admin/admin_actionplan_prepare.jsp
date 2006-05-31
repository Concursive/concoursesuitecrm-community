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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.actionplans.base.*,org.aspcfs.modules.admin.base.*,org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="categoryPlanMapList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryDraftPlanMapList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="categoryEditor" class="org.aspcfs.modules.admin.base.CategoryEditor" scope="request"/>
<jsp:useBean id="constantId" class="java.lang.String" scope="request" />
<jsp:useBean id="categoryId" class="java.lang.String" scope="request" />
<jsp:useBean id="siteId" class="java.lang.String" scope="request" />
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<script type="text/javascript">
  var selectedIds = '';
</script>
<% 
  Iterator iter = (Iterator) categoryPlanMapList.iterator();
  while (iter.hasNext()) {
    TicketCategoryDraftPlanMap planMap = (TicketCategoryDraftPlanMap) iter.next();
%>
<script type="text/javascript">
  selectedIds = selectedIds+'|<%= planMap.getPlan().getId() %>';
</script>
<%}%>
<body onLoad="javascript:init_page();">
<script type="text/javascript">
  function init_page() {
    popActionPlansSelectMultiple('actionplans','1','<%= categoryId %>','<%= constantId %>','<%= siteId %>',selectedIds,'ActionPlans'); 
  }
</script>
</body>

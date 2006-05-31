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
<%@ page import="org.aspcfs.utils.StringUtils, java.util.*" %>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="actionStepId" class="java.lang.String" scope="request"/>
<jsp:useBean id="recordDeleted" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<html>
<script type="text/javascript">
  
  function doClose() {
    var source = '<%= request.getParameter("source") %>';
    var itemId = '<%= actionStepId %>';
    var displayId = "changefolder" + itemId;
  <%String display = Category.getFirstFieldValue();%>
    var displayString = '<%= display %>';
    if ('<%= display == null || "".equals(display.trim()) %>' == 'true') {
      displayString = label('','Folder Attached');
    }
    var deleted = 'false';
    <%if (recordDeleted != null && "true".equals(recordDeleted)) {%>
      deleted = 'true';
      displayString = label('','Attach Folder');
    <%}%>
    opener.document.getElementById('recordid'+itemId).value="<%= Category.getRecordId() %>";
    if (source == 'attachplan') {
      if (deleted == 'true') {
        opener.deleteFolder(itemId, '<%= Category.getId() %>');
      } else {
        opener.attachFolder(itemId);
      }
    }
    opener.changeDivContent(displayId, displayString);
    window.close();
  }
</script>
  <body onload="javascript:doClose();" />
</body>
</html>

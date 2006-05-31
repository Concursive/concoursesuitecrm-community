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
<%@ page import="org.aspcfs.utils.CurrencyFormat" %>
<jsp:useBean id="OppDetails" class="org.aspcfs.modules.pipeline.beans.OpportunityBean" scope="request"/>
<jsp:useBean id="ComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<html>
<script type="text/javascript">
  <%
    String display = "";
    if (OppDetails.getComponent().getId() != -1) {
      display = CurrencyFormat.getCurrencyString(
                                    OppDetails.getComponent().getGuess(), 
                                    User.getLocale(), 
                                    applicationPrefs.get("SYSTEM.CURRENCY"));
      display += " " + NumberFormat.getPercentInstance().format(OppDetails.getComponent().getCloseProb());
    } else if (ComponentDetails.getId() != -1) {
      display = CurrencyFormat.getCurrencyString(
                                    ComponentDetails.getGuess(), 
                                    User.getLocale(), 
                                    applicationPrefs.get("SYSTEM.CURRENCY"));
      display += " " + NumberFormat.getPercentInstance().format(ComponentDetails.getCloseProb());
    }
  %>
  
  function doClose() {
    var source = '<%= request.getParameter("source") %>';
    var itemId = '<%= request.getParameter("actionStepWork") %>';
    var displayId = "changeopportunity" + itemId;
    if (source == 'attachplan') {
      <% if (OppDetails.getComponent().getId() != -1) { %>
        opener.document.getElementById('opportunityid').value="<%= OppDetails.getComponent().getId() %>";
      <% } else if (ComponentDetails.getId() != -1) { %>
        opener.document.getElementById('opportunityid').value="<%= ComponentDetails.getId() %>";
      <% } %>
      opener.attachOpportunity(itemId);
    }
    opener.changeDivContent(displayId, "<%= StringUtils.jsStringEscape(display) %>");
    window.close();
  }
</script>
  <body onload="javascript:doClose();" />
</body>
</html>

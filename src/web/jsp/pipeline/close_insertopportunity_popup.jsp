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
  - Version: $Id: close_insertopportunity_popup.jsp
  - Description: 
  --%>
<%@ page import="org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="oppDetails" class="org.aspcfs.modules.pipeline.beans.OpportunityBean" scope="request"/>
<jsp:useBean id="fromPop" class="java.lang.String" scope="request"/>
<html>
  <body onLoad="javascript:doClose();">
    <script language="JavaScript" TYPE="text/javascript">
      function doClose() {
        <%
        if(fromPop!=null && !fromPop.equals("true")){
        %>
        var source = '<%= request.getParameter("source") %>';
        if (source == 'attachplan') {
          var itemId = '<%= request.getParameter("itemId") %>';
          var displayId = "changeopportunity" + itemId;
          opener.document.getElementById('opportunityid').value="<%= oppDetails.getHeader().getId() %>";
          opener.changeDivContent(displayId, "<%= StringUtils.jsStringEscape(oppDetails.getHeader().getDescription()) %>");
          opener.attachOpportunity(itemId);
        } else {
          opener.document.getElementById('opportunityLink').value="<%= oppDetails.getHeader().getId() %>";
          opener.changeDivContent("changeopportunity", "<%= StringUtils.jsStringEscape(oppDetails.getHeader().getDescription()) %>");
        }
      <%}%>    
        window.close();
      }
    </script>
  </body>
</html>

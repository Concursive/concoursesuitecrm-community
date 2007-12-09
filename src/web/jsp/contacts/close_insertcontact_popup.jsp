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
<%@ page import="org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<html>
  <body onLoad="javascript:doClose();">
    <script language="JavaScript" TYPE="text/javascript">
      function doClose() {
        var source = '<%= request.getParameter("source") %>';
        if (source == 'attachplan') {
          var itemId = '<%= request.getParameter("actionStepWork") %>';
          var displayId = "changecontact" + itemId;
          opener.document.getElementById('contactid').value="<%= ContactDetails.getId() %>";
          opener.changeDivContent(displayId, "<%= StringUtils.jsStringEscape(ContactDetails.getNameLastFirst()) %>");
          opener.attachContact(itemId);
        } else {
          opener.document.getElementById('contactLink').value="<%= ContactDetails.getId() %>";
          opener.changeDivContent("changecontact", "<%= StringUtils.jsStringEscape(ContactDetails.getNameLastFirst()) %>");
          if(source == 'sendMailHomePage') {
          <% System.out.println("source is::");  
          if ("".equals(ContactDetails.getPrimaryEmailAddress())) { %>
            opener.changeDivDisplayStyle("contactemail","");
            opener.document.getElementById('emptyEmail').value="true";
          <% } else { %>
            opener.changeDivDisplayStyle("contactemail","none");
          <%} %>
          }
        }
        window.close();
      }
    </script>
  </body>
</html>

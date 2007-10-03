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
  context.getRequest().setAttribute("fieldName", "");
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="ContactDetails"
             class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<%@ include file="../initPage.jsp" %>
<html>
<script type="text/javascript">
  function addContact(text, value, fieldName) {
    opener.insertOption(text, value, fieldName);

  }

  function doClose() {
    var source = '<%= request.getParameter("hiddensource") %>';
    if (source == 'attachplan') {
      var itemId = '<%= request.getParameter("actionStepWork") %>';
      var displayId = "changecontact" + itemId;
      opener.document.getElementById('contactid').value = "<%= ContactDetails.getId() %>";
      opener.changeDivContent(displayId, "<%= StringUtils.jsStringEscape(ContactDetails.getValidName()) %>");
      opener.attachContact(itemId);
    } else {
    <dhv:evaluate if="<%= request.getParameter("fieldName")!=null && !"".equals(request.getParameter("fieldName"))%>">
      var fieldName = '<%=request.getParameter("fieldName")%>';
    </dhv:evaluate>
    <dhv:evaluate if="<%= request.getParameter("fieldName")==null || "".equals(request.getParameter("fieldName"))%>">
      var fieldName = "contactId";
    </dhv:evaluate>
      addContact("<%= StringUtils.jsStringEscape(ContactDetails.getValidName()) %>", '<%= ContactDetails.getId() %>', fieldName);
    }
    window.close();
  }
</script>
<body onload="javascript:doClose();"/>
</html>

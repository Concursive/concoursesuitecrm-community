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
  - Version: $Id:  $
  - Description: 
  --%>
<%@ page import="org.aspcfs.utils.StringUtils,org.aspcfs.modules.contacts.base.Contact" %>
<jsp:useBean id="contact" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="actionItemWork" class="org.aspcfs.modules.actionplans.base.ActionItemWork" scope="request"/>
<%@ include file="../initPage.jsp" %>
<html>
<script type="text/javascript">
  function attachRecipient() {
    var recipientAdded = '<%= (request.getAttribute("recipientAdded") != null && "true".equals(request.getAttribute("recipientAdded"))) %>';
    if (recipientAdded == 'true') {
      var itemId = '<%= actionItemWork.getId() %>';
      var displayId = "changerecipient" + itemId;
      parent.changeDivContent(displayId, "<%= StringUtils.jsStringEscape(contact.getNameFull()) %>");
      parent.attachRecipient(itemId);
      
      var surveyURL = '<%= (request.getAttribute("surveyURL") != null)?request.getAttribute("surveyURL"):""%>';
      if (surveyURL != ""){
        parent.popSurvey(surveyURL);
      }
    } else {
      var addendum = '&notAttached=true';
      parent.reopen(addendum);
    }
  }
</script>
  <body onload="javascript:attachRecipient();" />
</body>
</html>

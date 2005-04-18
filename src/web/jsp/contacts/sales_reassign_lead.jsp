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
<%@ include file="../initPage.jsp" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="contactId" class="java.lang.String" scope="request"/>
<jsp:useBean id="next" class="java.lang.String" scope="request" />
<jsp:useBean id="from" class="java.lang.String" scope="request" />
<jsp:useBean id="listForm" class="java.lang.String" scope="request" />
<jsp:useBean id="assignStatus" class="java.lang.String" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<body onLoad="javascript:init_page();">
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<script type="text/javascript">
  function init_page() {
    if ('<%= assignStatus %>' == 'skipped') {
      if ('<%= from %>' == 'dashboard') {
        parent.location.href="Sales.do?command=Dashboard";
      } else {
        parent.location.href="Sales.do?command=Details&contactId=<%= contactId %>&nextValue=true&from=<%= from %>&listForm=<%= (listForm != null ? listForm : "") %>";
      }
    }
    var next= "<%= next.trim() %>";
    if ('<%= assignStatus.trim() %>' == 'notAssigned') {
      alert(label('cannot.assign','The lead could not be assigned. Please skip the current Lead'));
      if (next == "work") {
        window.close();
      }
    }
    if ('<%= assignStatus.trim() %>' == 'assigned') {
      if (next == "work") {
        opener.hideSpan("worklead");
        opener.showSpan("nextlead");
        var rating = opener.document.forms['details'].rating.value;
        var comments = opener.document.forms['details'].comments.value;
        window.location.href= 'Sales.do?command=WorkLead&id=<%= contactId %>&rating='+rating+'&comments='+comments+'&popup=true&listForm=<%= (listForm != null ? listForm : "") %>';
      } else if (next == "assign") {
        var test = parent.continueAssignLead();
      } else if (next == "trash") {
        var test = parent.continueTrashLead();
      } else if (next == "delete") {
        //Just perform the deletion and skip right here
        window.location.href= 'Sales.do?command=ConfirmDelete&contactId=<%= contactId %>&popup=true&from=<%= from %>&listForm=<%= (listForm != null ? listForm : "") %>';
      } else {
        alert('programming error');
      }
    }
  }
</script>
</body>

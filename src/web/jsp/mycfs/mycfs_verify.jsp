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
<jsp:useBean id="Session" class="org.aspcfs.controller.UserSession" scope="request"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></script>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function loginAlert(ipAddress){
    if (confirm(
        label("check.user.twologins.one","This application only permits a user to be logged in once\n and it appears that you are already logged in from\n the following internet address: ") + 
        ipAddress + 
        label("check.user.twologins.two",".\n\n This message could also appear if you did not previously log out\n and you are simply trying to login again from the same browser.\n\n Choose OK to continue logging in.\n Choose CANCEL to return to the login screen."))) {
      window.location.href = 'Login.do?command=LoginConfirm&override=yes';
    }else{
      window.location.href = 'Login.do?command=LoginConfirm&override=no';
    }
  }
</script>
<body onLoad="javascript:loginAlert('<%= Session.getIpAddress() %>');">


<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
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
        'This application only permits a user to be logged in once\n' +
        'and it appears that you are already logged in from\n' +
        'the following internet address: ' + ipAddress + '.\n' +
        '\n' +
        'This message could also appear if you did not previously log out\n' +
        'and you are simply trying to login again from the same browser.\n' +
        '\n' +
        'Choose OK to continue logging in.\n' +
        'Choose CANCEL to return to the login screen.')) {
      window.location.href = 'Login.do?command=LoginConfirm&override=yes';
    }else{
      window.location.href = 'Login.do?command=LoginConfirm&override=no';
    }
  }
</script>
<body onLoad="javascript:loginAlert('<%= Session.getIpAddress() %>');">


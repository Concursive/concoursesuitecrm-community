<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Session" class="org.aspcfs.controller.UserSession" scope="request"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></script>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function loginAlert(ipAddress){
    if (confirm('You are already logged in from ' + ipAddress + '.\n Do you want to log off from that machine and continue? ')) {
      window.location.href = 'Login.do?command=LoginConfirm&override=yes';
    }else{
      window.location.href = 'Login.do?command=LoginConfirm&override=no';
    }
  }
</script>
<body onLoad="javascript:loginAlert('<%= Session.getIpAddress() %>');">


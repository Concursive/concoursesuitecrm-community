<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Dialog" class="com.darkhorseventures.webutils.HtmlDialog" scope="session"/>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.UserBean" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/submit.js"></SCRIPT>
<%@ include file="initPage.jsp" %>

<link rel="stylesheet" href="css/template0<%= User.getBrowserIdAndOS() %>.css" type="text/css">
<link rel="stylesheet" href="css/template0.css" type="text/css">

<body id="body">
   <%=Dialog.getFrameHtml(1)%>
   <%if(Dialog.getSynchFrameCounter()==0){
      session.removeAttribute("Dialog");
    }%>
</body>


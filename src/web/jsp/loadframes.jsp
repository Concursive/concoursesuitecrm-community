<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Dialog" class="com.darkhorseventures.webutils.HtmlDialog" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/submit.js"></SCRIPT>
<%@ include file="initPage.jsp" %>

<body onLoad="javascript:loadFrames();" id="body">
   <%=Dialog.getFrameHtml(1)%>
   <%if(Dialog.getSynchFrameCounter()==0){
      session.removeAttribute("Dialog");
    }%>
</body>


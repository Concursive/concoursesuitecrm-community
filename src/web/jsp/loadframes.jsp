<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Dialog" class="com.darkhorseventures.webutils.HtmlDialog" scope="session"/>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.UserBean" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/submit.js"></SCRIPT>
<%@ include file="initPage.jsp" %>

<link rel="stylesheet" href="css/template0<%= User.getBrowserIdAndOS() %>.css" type="text/css">
<link rel="stylesheet" href="css/template0.css" type="text/css">

<script type="text/javascript">
function loadFrames(){
     if(document.getElementById){
     
        <% if ("true".equals((String)getServletConfig().getServletContext().getAttribute("ForceSSL"))) { %>
            parent.frames["middleframe"].document.location.href = "https://<%= request.getServerName() %>/loadmiddleframe.jsp";
            parent.frames["bottomframe"].document.location.href = "https://<%= request.getServerName() %>/loadbottomframe.jsp";
        <%} else {%>
            parent.frames["middleframe"].document.location.href = "loadmiddleframe.jsp";
            parent.frames["bottomframe"].document.location.href = "loadbottomframe.jsp";
        <%}%>
     }
   }
</script>

<body onLoad="javascript:loadFrames();" id="body">
   <%=Dialog.getFrameHtml(1)%>
   <%if(Dialog.getSynchFrameCounter()==0){
      session.removeAttribute("Dialog");
    }%>
</body>


<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.UserBean" scope="session"/>
<jsp:useBean id="Dialog" class="com.darkhorseventures.webutils.HtmlDialog" scope="session"/>

<link rel="stylesheet" href="css/template0<%= User.getBrowserIdAndOS() %>.css" type="text/css">
<link rel="stylesheet" href="css/template0.css" type="text/css">

<%@ include file="initPage.jsp" %>
   <%=Dialog.getFrameHtml(2)%>
   <%if(Dialog.getSynchFrameCounter()==0){
    session.removeAttribute("Dialog");
   }%>



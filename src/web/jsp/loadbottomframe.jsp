<jsp:useBean id="Dialog" class="com.darkhorseventures.webutils.HtmlDialog" scope="session"/>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.UserBean" scope="session"/>

<link rel="stylesheet" href="css/template0<%= User.getBrowserIdAndOS() %>.css" type="text/css">
<link rel="stylesheet" href="css/template0.css" type="text/css">

<%=Dialog.getFrameHtml(3)%>
<%if(Dialog.getSynchFrameCounter()==0){
  session.removeAttribute("Dialog");
 }%>


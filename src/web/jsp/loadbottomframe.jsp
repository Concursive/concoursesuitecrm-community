<jsp:useBean id="Dialog" class="com.darkhorseventures.webutils.HtmlDialog" scope="session"/>
<%=Dialog.getFrameHtml(3)%>
<%if(Dialog.getSynchFrameCounter()==0){
  session.removeAttribute("Dialog");
 }%>


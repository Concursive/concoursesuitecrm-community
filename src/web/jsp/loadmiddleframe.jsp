<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Dialog" class="com.darkhorseventures.webutils.HtmlDialog" scope="session"/>
<%@ include file="initPage.jsp" %>
   <%=Dialog.getFrameHtml(2)%>
   <%if(Dialog.getSynchFrameCounter()==0){
    session.removeAttribute("Dialog");
   }%>



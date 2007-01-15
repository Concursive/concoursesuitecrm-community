<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Dialog" class="org.aspcfs.utils.web.HtmlDialog" scope="session"/>
<jsp:include page="templates/cssInclude.jsp" flush="true"/>
<%@ include file="initPage.jsp" %>
<%= Dialog.getFrameHtml(2) %>
<% 
  if (Dialog.getSynchFrameCounter() == 0) {
    session.removeAttribute("Dialog");
  }
%>

<jsp:useBean id="Dialog" class="org.aspcfs.utils.web.HtmlDialog" scope="session"/>
<jsp:include page="templates/cssInclude.jsp" flush="true"/>
<%= Dialog.getFrameHtml(3) %>
<% 
  if (Dialog.getSynchFrameCounter() == 0) {
    session.removeAttribute("Dialog");
  }
%>

<jsp:useBean id="Dialog" class="org.aspcfs.utils.web.HtmlDialog" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%if(Dialog.getShowAndConfirm()){
  if(Dialog.getText().equals("")){
%>
  <html>
   <title><%=Dialog.getTitle()%></title>
    <frameset rows="50,100%,50" frameborder="0">
        <% if ("true".equals((String)getServletConfig().getServletContext().getAttribute("ForceSSL"))) { %>
          <frame name="topframe" src="https://<%= request.getServerName() %>/loadframes.jsp">
          <frame marginheight="0" name="middleframe" src="https://<%= request.getServerName() %>/loadmiddleframe.jsp">
          <frame name="bottomframe" src="https://<%= request.getServerName() %>/loadbottomframe.jsp">          
        <%} else {%>
          <frame name="topframe" src="loadframes.jsp">
          <frame marginheight="0" name="middleframe" src="loadmiddleframe.jsp">
          <frame name="bottomframe" src="loadbottomframe.jsp">
        <%}%>
    </frameset>
  </html>
<%}
else{%>
  <%=toHtml(Dialog.getText())%>
<%}
}else{%>
<html>
  <jsp:include page="templates/cssInclude.jsp" flush="true"/>
  <title><%= Dialog.getTitle() %></title>
  <body>
    <br>
    <table cellpadding="4" cellspacing="0" border="0" width="95%" bordercolorlight="#000000" bordercolor="#FFFFFF">
      <tr valign="center">
        <td>
           Are you sure you wish to permanently delete this object from CFS?
        </td>
      </tr>
      <tr><td>&nbsp;</td></tr>
      <tr><td>&nbsp;</td></tr>
      <tr><td>&nbsp;</td></tr>
      <tr><td>&nbsp;</td></tr>
      <tr align="center">
        <td>
          <input type="button" value="Ok" onClick="<%=toHtml(Dialog.getDeleteUrl())%>">
          <input type="button" value="Cancel" onClick="javascript:window.close();">
        </td>
      </tr>
     </table>
   </body>
</html>
<%}%>

<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Dialog" class="com.darkhorseventures.webutils.HtmlDialog" scope="session"/>
<%@ include file="initPage.jsp" %>

<%if(Dialog.getShowAndConfirm()){
  if(Dialog.getText().equals("")){
%>
  <html>
   <title><%=Dialog.getTitle()%></title>
    <frameset rows="20%,60%,*" frameborder="0">
      <frame name="topframe" src="loadframes.jsp">
      <frame name="middleframe" src="">
      <frame name="bottomframe" src="">
    </frameset>
  </html>
<%}
else{%>
  <%=toHtml(Dialog.getText())%>
<%}
}else{%>
<html>
  <title><%=Dialog.getTitle()%></title>
  <body>
    <br>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
      <tr valign="top">
      <td>
       <center><strong>Are you sure ?</strong></center><br><br>
      </td>
      </tr>
      <tr align="center">
      <td>
        <input type="button" value="Yes" onClick="<%=toHtml(Dialog.getDeleteUrl())%>">&nbsp;&nbsp;
        <input type="button" value="Cancel" onClick="javascript:window.close();">
        </td>
      </tr>
     </table>
 </body>
</html>
<%}%>

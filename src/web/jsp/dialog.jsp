<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Dialog" class="com.darkhorseventures.webutils.HtmlDialog" scope="request"/>
<%@ include file="initPage.jsp" %>
<body onload="javascript:window.resizeTo('<%=Dialog.getWidth()%>','<%=Dialog.getHeight()%>');javascript:document.title='<%=Dialog.getTitle()%>';">
<table cellpadding="4" cellspacing="0" border="0" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF"> 
<%if(Dialog.getShowAndConfirm()){%>
<br>
  <%if(Dialog.getText().equals("")){%>
  <strong><%=toHtml(Dialog.getHeader())%></strong><br>
  <tr class="row1">
    <%=Dialog.getRelationships().size()!=0?"The following Relationships will be deleted ":""%>
  </tr>
  <tr class="row2">
    <%=Dialog.getRelationships().size()!=0?Dialog.getRelationshipString():""%>
  </tr>
  <tr class="row1">
    <%=Dialog.getLinks().size()!=0?"The following Links will be deleted ":""%>
  </tr>
  <tr class="row2">
    <%=Dialog.getLinks().size()!=0?Dialog.getLinkString():""%>
  </tr><br>
  <tr>
  <center><%=Dialog.getButtonString()%></center>
  </tr>
  <%}
   else{%>
   <%=toHtml(Dialog.getText())%>
   <%}%>
<%}
 else{%>
 <br>
 <tr halign="center">
  <strong>Are you sure ?</strong>
 </tr><br><br>
 <tr halign="center">
   <input type="button" value="Yes" onClick="<%=toHtml(Dialog.getDeleteUrl())%>">&nbsp;&nbsp;
   <input type="button" value="Cancel" onClick="javascript:window.close();">
 </tr>
<%}%>
</table>
</body>


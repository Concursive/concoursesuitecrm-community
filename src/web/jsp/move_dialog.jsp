<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Dialog" class="org.aspcfs.utils.web.HtmlDialog" scope="session"/>
<%@ include file="initPage.jsp" %>
<html>
  <jsp:include page="templates/cssInclude.jsp" flush="true"/>
  <title><%= Dialog.getTitle() %></title>
  <body>
    <br>
    <table cellpadding="4" cellspacing="0" border="0" width="95%">
      <tr valign="center">
        <td>
          <center>
           <strong>
           <%= (Dialog.getHeader() != null && !"".equals(Dialog.getHeader())) ? Dialog.getHeader() : "" %>
          </strong>
         </center>
        </td>
      </tr>
      <tr><td>&nbsp;</td></tr>
      <tr><td>&nbsp;</td></tr>
      <tr align="center">
        <td>
          <input type="button" value="<dhv:label name="button.ok">OK</dhv:label>" onClick="javascript:window.close()">
        </td>
      </tr>
     </table>
   </body>
</html>

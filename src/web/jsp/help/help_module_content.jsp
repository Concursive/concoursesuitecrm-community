<html>
<body>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.help.base.*" %>
<jsp:useBean id="helpModule" class="org.aspcfs.modules.help.base.HelpModule" scope="request"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<%@ include file="../initPage.jsp" %>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <%-- Introduction --%>
  <tr>
    <td>
      <table cellpadding="4" cellspacing="0" width="100%" class="details">
      <tr>
       <td><h2><%= toHtml(helpModule.getModuleName())%></h2></td>
      </tr>
      <tr>
       <td><h3>Brief Description</h3></td>
      </tr>
       <tr>
         <td>
         <%=  !"".equals(toString(helpModule.getBriefDescription())) ?  toHtml(helpModule.getBriefDescription()) : "No Description available"%>
        </td>
       </tr>
     </table><br>
    </td>
  </tr>
  <tr>
    <td>
     <table cellpadding="4" cellspacing="0" width="100%" class="details">
     <tr>
       <td><h3>Module Detail Description</h3></td>
      </tr>
       <tr>
         <td>
         <%=  !"".equals(toString(helpModule.getDetailDescription())) ?  toHtml(helpModule.getDetailDescription()) : "No Description available"%>
        </td>
       </tr>
     </table><br>
    </td>
 </tr>
</table>
</body>
</html>


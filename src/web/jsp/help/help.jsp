<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.help.base.*" %>
<jsp:useBean id="HelpContents" class="org.aspcfs.modules.help.base.HelpContents" scope="request"/>
<jsp:useBean id="Help" class="org.aspcfs.modules.help.base.HelpItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr bgColor="#DEE0FA">
    <td>
      <strong>Help for this page</strong>
    </td>
  </tr>
  <tr>
    <td>
      File: <strong><%= Help.getBaseFilename().toLowerCase() %></strong>
      <hr color="#BFBFBB" noshade>
    </td>
  </tr>
  <tr>
    <td align="center">
<dhv:evaluate if="<%= Help.hasImageFile(getServletContext().getRealPath("/") + "images/help") %>">
      <img border="0" src="images/help/<%= Help.getBaseFilename() %>.png">
</dhv:evaluate>
<dhv:evaluate if="<%= !Help.hasImageFile(getServletContext().getRealPath("/") + "images/help") %>">
      Help not available for this topic.
</dhv:evaluate>
    </td>
  </tr>      
  <tr>
    <td>
      <input type="button" value="Close" onClick="javascript:window.close();">
    </td>
  </tr>
</table>

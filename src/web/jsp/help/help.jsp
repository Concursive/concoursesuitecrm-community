<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="HelpContents" class="com.darkhorseventures.cfsbase.HelpContents" scope="request"/>
<jsp:useBean id="Help" class="com.darkhorseventures.cfsbase.HelpItem" scope="request"/>
<%@ include file="initPage.jsp" %>
<table border="0" width="100%" cellspacing="0" cellpadding="4">
  <tr>
    <td bgColor="#DEE0FA">
      <font color="#000000"><b>Help for this page</b></font>
    </td>
  </tr>
  <tr>
    <td>
      Module: <b><%= toHtmlValue(Help.getModule()) %></b> 
    </td>
  </tr>
  <tr>
    <td>
      Section: <b><%= toHtmlValue(Help.getSection()) %></b>
    </td>
  </tr>
  <tr>
    <td>
      Sub-section: <b><%= toHtmlValue(Help.getSubsection()) %></b>
    </td>
  </tr>
  <tr>
    <td>
      <hr color="#BFBFBB" noshade>
    </td>
  </tr>
  <tr>
    <td>
      <%= toHtml(Help.getDescription()) %>
    </td>
  </tr>      
  <tr>
    <td>
        <input type="button" value="Close" onClick="javascript:window.close();">
    </td>
  </tr>
</table>

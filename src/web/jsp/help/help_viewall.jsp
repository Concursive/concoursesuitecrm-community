<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.*" %>
<jsp:useBean id="HelpContents" class="org.aspcfs.modules.HelpContents" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table border="0" width="100%" cellspacing="0" cellpadding="4">
  <tr>
    <td align="center" colspan="4" width="100%">
      <font size="3"><b>CFS Help</b></font><br>
      <%= toDateTimeString(new java.sql.Timestamp(new java.util.Date().getTime())) %>
    </td>
  </tr>
<%
  Iterator contents = HelpContents.iterator();
  String previousModule = "-1";
  String previousSection = "-1";
  String previousSubsection = "-1";
  while (contents.hasNext()) {
    HelpItem thisItem = (HelpItem)contents.next();
    if (thisItem.getModule() != null && !thisItem.getModule().equals(previousModule)) {
      previousSection = "-1";
      previousSubsection = "-1";
%>
  <tr>
    <td align="left" colspan="4" width="100%">
      <b><%= toHtml(thisItem.getModule().trim()) %></b>
    </td>
  </tr>
<%    
    }
    if (thisItem.getSection() != null && !thisItem.getSection().equals(previousSection)) {
      previousSubsection = "-1";
%>
  <tr>
    <td width="10">&nbsp;</td>
    <td align="left" colspan="3" width="100%">
      <b><%= toHtml(thisItem.getSection().trim()) %></b>
    </td>
  </tr>
<%
    }
    if (thisItem.getSubsection() != null && !thisItem.getSubsection().equals(previousSubsection)) {
%>
  <tr>
    <td width="10">&nbsp;</td>
    <td width="10">&nbsp;</td>
    <td align="left" colspan="2" width="100%">
      <b><%= toHtml(thisItem.getSubsection().trim()) %></b>
    </td>
  </tr>
<%
    }
%>
  <tr>
    <td width="10">&nbsp;</td>
    <td width="10">&nbsp;</td>
    <td width="10">&nbsp;</td>
    <td align="left" width="100%">
      <%= toHtml(thisItem.getDescription().trim()) %>
    </td>
  </tr>  
<%    
    previousModule = thisItem.getModule();
    previousSection = thisItem.getSection();
    previousSubsection = thisItem.getSubsection();
  }
%>
</table>
<center>(C) 2002 Dark Horse Ventures</center>
  

<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.help.base.*" %>
<jsp:useBean id="HelpContents" class="org.aspcfs.modules.help.base.HelpContents" scope="request"/>
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
<center>(C) 2002-2004 Dark Horse Ventures</center>
  

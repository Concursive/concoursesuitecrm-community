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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.actionlist.base.*, org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="History" class="org.aspcfs.modules.actionlist.base.ActionItemLogList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.body.className='row<%= request.getParameter("rowid") %>';">
<table width="100%" border="0" cellspacing="0" cellpadding="0" border="0">
<%
  int rowid = Integer.parseInt(request.getParameter("rowid"));
  Iterator j = History.iterator();
  if ( j.hasNext() ) {
  //skip first record
  j.next();
  while (j.hasNext()) {
      ActionItemLog thisLog = (ActionItemLog) j.next();
%>
    <tr class="row<%= rowid %>">
    <td>
      <%= toHtml(thisLog.getTypeString()) %>: <a href="javascript:parent.location.href='<%= thisLog.getItemLink( Integer.parseInt(request.getParameter("contactId"))) %>';"  onMouseOver="this.style.color='blue';window.status='View Details';return true;"  onMouseOut="this.style.color='black';window.status='';return true;"><%= toHtml(thisLog.getDescription()) %></a> &nbsp;[<zeroio:tz timestamp="<%= thisLog.getEntered() %>" />]
    </td>
    </tr>
<% }
}else{ %>
        <tr>
        <td class="containerBody" colspan="2" valign="center">
          No items in History.
        </td>
      </tr>
<%}%>
</table>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
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
      <%= toHtml(thisLog.getTypeString()) %>: <a href="javascript:parent.location.href='<%= thisLog.getItemLink( Integer.parseInt(request.getParameter("contactId"))) %>';"  onMouseOver="this.style.color='blue';window.status='View Details';return true;"  onMouseOut="this.style.color='black';window.status='';return true;"><%= toHtml(thisLog.getDescription()) %></a> &nbsp;[<%= toDateTimeString(thisLog.getEntered()) %>]
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

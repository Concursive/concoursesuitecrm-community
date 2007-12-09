<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.netapps.base.*" %>
<jsp:useBean id="contractExpiration" class="org.aspcfs.modules.netapps.base.ContractExpiration" scope="request"/>
<jsp:useBean id="contractExpirationLogList" class="org.aspcfs.modules.netapps.base.ContractExpirationLogList" scope="request"/>
<jsp:useBean id="ExpirationContractHistoryListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="netapps_contractexpirationlist_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
  <td>
    <a href="NetworkApplications.do">NetApp</a> >
    <% if (contractExpiration.isApproved()){%>
      <a href="NetworkApplications.do?command=Search">Contract Expiration List</a> >
    <%}else{%>
        <a href="NetworkApplicationsImports.do?command=View">View Imports</a> >
        <a href="NetworkApplicationsImports.do?command=Details&importId=<%= contractExpiration.getImportId() %>">Import Details</a> >
        <a href="NetworkApplicationsImports.do?command=ViewResults&importId=<%= contractExpiration.getImportId() %>">View Results</a> >
    <%}%>
    <a href="NetworkApplications.do?command=View&expirationId=<%=contractExpirationLogList.getExpirationId()%>">Contract Expiration Details</a> >
    Contract Expiration History
  </td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="netapps" selected="history" param='<%= "expirationId=" + contractExpirationLogList.getExpirationId() %>' style="tabs"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td class="containerBack">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="12%" colspan="3" nowrap>
      <strong>Contract Expiration Log History</strong>
    </th>
  </tr>
<%    
	Iterator i = contractExpirationLogList.iterator();

	if (i.hasNext()) {
	int rowid = 0;
  int count  =0;
		while (i.hasNext()) {
      count++;
      rowid = (rowid != 1 ? 1 : 2);
      ContractExpirationLog thisEntry = (ContractExpirationLog)i.next();
%>    
      <tr class="row<%= rowid %>">
        <td width="20%" nowrap>
          <%= toHtml(thisEntry.getEnteredByName()) %>
        </td>
        <td width="15%" nowrap>
            <zeroio:tz timestamp="<%= thisEntry.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes"/>
        </td>
        <td>
          <%= toHtml(thisEntry.getEntryText()) %>
        </td>
      </tr>
<%
    }
  } else {%>  
  <tr>
    <td class="containerBody" colspan="6">
      No history found.
    </td>
  </tr>
<%}%>
</table>
<br>
</td>
  </tr>
</table>


<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="sclList" class="org.aspcfs.modules.communications.base.SearchCriteriaListList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<form name="modForm" action="CampaignManager.do?command=Update&id=<%= Campaign.getId() %>&auto-populate=true" method="post">
<a href="CampaignManager.do?command=View">Back to Campaign List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr class="containerHeader">
    <td>
      <strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
Add Recipient | Rebuild Recipients from Groups<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Recipients</strong>
    </th>
  </tr>
<%
  Iterator i = sclList.iterator();
  int rowid = 0;
  while (i.hasNext()) {
    rowid = (rowid != 1?1:2);
    SearchCriteriaList thisList = (SearchCriteriaList)i.next();
%>  
  <tr class="containerBody">
    <td width="8" nowrap class="row<%= rowid %>">
      <input type="checkbox" name="<%= thisList.getId() %>" value="true">
    </td>
    <td nowrap class="row<%= rowid %>">
      <%= toHtml(thisList.getGroupName()) %>
    </td>
  </tr>
<%
  }
  if (sclList.size() == 0) {
%>  
  <tr class="containerBody">
    <td colspan="2">
      No recipients selected.
    </td>
  </tr>
<%  
  }
%>
</table>
<br>
<input type="submit" value="Remove Selected Recipients" name="Remove">
  </td>
  </tr>
</table>
</form>

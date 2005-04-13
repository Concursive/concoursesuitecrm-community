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
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="sclList" class="org.aspcfs.modules.communications.base.SearchCriteriaListList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<form name="modForm" action="CampaignManager.do?command=Update&id=<%= Campaign.getId() %>&auto-populate=true" method="post">
<a href="CampaignManager.do?command=View"><dhv:label name="campaign.backToCampaignList">Back to Campaign List</dhv:label></a><br>&nbsp;
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr class="containerHeader">
    <td>
      <dhv:label name="campaign.campaign.colon" param="<%= "name="+toHtml(Campaign.getName()) %>"><strong>Campaign:</strong> <%= toHtml(Campaign.getName()) %></dhv:label>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:label name="campaign.addRecipient.text">Add Recipient | Rebuild Recipients from Groups</dhv:label><br />
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="Recipients">Recipients</dhv:label></strong>
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
      <dhv:label name="campaign.noRecipientsSelected">No recipients selected.</dhv:label>
    </td>
  </tr>
<%  
  }
%>
</table>
<br>
<input type="submit" value="<dhv:label name="campaign.removeSelectedRecipients">Remove Selected Recipients</dhv:label>" name="Remove">
  </td>
  </tr>
</table>
</form>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="opportunityList" class="org.aspcfs.modules.OpportunityList" scope="request"/>
<%@ include file="../initPage.jsp" %>

<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign="center" align="left">
      <strong>Organization</strong>
    </td>
    <td valign="center" align="left">
      <strong>Description</strong>
    </td>
    <td valign="center" align="left">
      <strong>Amount</strong>
    </td>
    <td valign="center" align="left">
      <strong>Close</strong>
    </td>
  </tr>
  <!-- Paged List -->
<%
  Iterator oppList = opportunityList.iterator();
  if (oppList.hasNext()) {
    int row = 0;
    while (oppList.hasNext()) {
      row = row == 1?2:1;
      Opportunity thisOpportunity = (Opportunity)oppList.next();
%>
  <tr class="containerBody">
    <td width="20%" valign="center" nowrap class="row<%= row %>">
      <%= toHtml(thisOpportunity.getAccountName()) %>
    </td>
    <td width="40%" valign="center" class="row<%= row %>">
      <%= toHtml(thisOpportunity.getDescription()) %>
    </td>
    <td width="20%" valign="center" align="right" nowrap class="row<%= row %>">
      <%= toHtml(thisOpportunity.getGuessCurrency()) %>
    </td>
    <td width="20%" valign="center" align="right" nowrap class="row<%= row %>">
      <%= toHtml(thisOpportunity.getCloseDateString()) %>
    </td>
  </tr>
<%  
    }
  } else {
%>
  <tr>
    <td colspan="4">
      No opportunities found.
    </td>
  </tr>
<%
  }
%>
  <!-- End Paged List -->
</table>



<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="DeliveryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<a href="CampaignManager.do">Communications Manager</a> >
<a href="CampaignManager.do?command=Dashboard">Dashboard</a> >
<a href="CampaignManager.do?command=Details&id=<%= Campaign.getId() %>">Campaign Details</a> >
Schedule
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + Campaign.getId(); %>
      <dhv:container name="communications" selected="schedule" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>Delivery Options</strong>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Run Date
    </td>
    <td>
      <%= toHtml(Campaign.getActiveDateString()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Delivery Method
    </td>
    <td valign="top">
      <%= toHtml(Campaign.getDeliveryName()) %>
    </td>
  </tr>
</table>
  </td>
  </tr>
</table>

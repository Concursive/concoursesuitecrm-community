<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Campaign" class="com.darkhorseventures.cfsbase.Campaign" scope="request"/>
<jsp:useBean id="DeliveryList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<%@ include file="initPage.jsp" %>
<body onLoad="document.inputForm.activeDate.focus()">

<a href="CampaignManager.do">Communications Manager</a> >
<a href="/CampaignManager.do?command=Dashboard">Dashboard</a> >
<a href="/CampaignManager.do?command=Details&id=<%=Campaign.getId()%>">Campaign Details</a> >
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
    <td colspan=2 valign=center align=left>
      <strong>Delivery Options</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="center" nowrap>
      Run Date
    </td>
    <td width="100%">
      <%= toHtmlValue(Campaign.getActiveDateString()) %>
    </td>
  </tr>
  <tr class="containerBody">
      <td class="formLabel" valign="center" nowrap>
      Delivery Method
    </td>
    <td width="100%">
      <%=Campaign.getDeliveryName()%>
    </td>
  </tr>
  
</table>
  </td>
  </tr>
</table>
</form>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="ActiveSurvey" class="org.aspcfs.modules.communications.base.ActiveSurvey" scope="request"/>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<%@ include file="../initPage.jsp" %>
<a href="CampaignManager.do">Communications Manager</a> >
<a href="CampaignManager.do?command=Dashboard">Dashboard</a> >
Campaign Details
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td colspan="2" valign="center" align="left">
      <strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
    </td>     
  </tr>
  <tr class="containerMenu">
    <td colspan="2">
      <% String param1 = "id=" + Campaign.getId(); %>
      <dhv:container name="communications" selected="details" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr class="title">
          <td colspan="2" valign="center" align="left">
            <strong>Campaign Details </strong>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" valign="top">
            Description
          </td>
          <td width="100%">
            <%= toHtml(Campaign.getDescription()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Groups
          </td>
          <td width="100%">
            <font color='green'><%= Campaign.getGroupCount() %> selected</font>
          </td>
        </tr>
        <tr class="containerBody">
          <td valign="top" class="formLabel">
            Message
          </td>
          <td width="100%">
            <font color='green'><%= toHtml(Campaign.getMessageName()) %></font>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Schedule
          </td>
          <td width="100%">
            <font color='green'>Scheduled to run on <%= Campaign.getActiveDateString() %></font>
          </td>
        </tr>
        
        <tr class="containerBody">
          <td class="formLabel">
            Delivery
          </td>
          <td width="100%">
            <%= (Campaign.hasDetails()?"<font color='green'>" + toHtml(Campaign.getDeliveryName())  + "</font>":"<font color='red'>Not Specified</font>") %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Entered
          </td>
          <td width="100%">
            <dhv:username id="<%= Campaign.getEnteredBy() %>" /> - <%= toHtml(Campaign.getEnteredString()) %>
          </td>
        </tr>
        
        <tr class="containerBody">
          <td class="formLabel">
            Modified
          </td>
          <td width="100%">
            <dhv:username id="<%= Campaign.getModifiedBy() %>" /> - <%= toHtml(Campaign.getModifiedString()) %>
          </td>
        </tr>
	<tr class="containerBody">
          <td class="formLabel">
            Mail Merge
          </td>
          <td width="100%" nowrap>
            <%= (Campaign.hasFiles()?"<a href=\"CampaignManager.do?command=PrepareDownload&id=" + Campaign.getId() + "\">Download<br>Available</a>":"None") %>
          </td>
        </tr>
	
     </table>
   </td>
  </tr>
</table>

<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Campaign" class="com.darkhorseventures.cfsbase.Campaign" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<form name="modForm" action="/CampaignManager.do?command=Update&id=<%= Campaign.getId() %>&auto-populate=true" method="post">
<a href="/CampaignManager.do?command=View">Back to Campaign List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(Campaign.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <a href="/CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>"><font color="#0000FF">Details</font></a> |
      <a href="/CampaignManager.do?command=ViewGroups&id=<%= Campaign.getId() %>"><font color="#000000">Groups</font></a> | 
      <a href="/CampaignManager.do?command=ViewMessage&id=<%= Campaign.getId() %>"><font color="#000000">Message</font></a> | 
      <a href="/CampaignManager.do?command=ViewSchedule&id=<%= Campaign.getId() %>"><font color="#000000">Schedule</font></a>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Campaign Details</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Name
    </td>
    <td width="100%">
      <input type=text size=35 name="name" value="<%= toHtmlValue(Campaign.getName()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Description
    </td>
    <td width="100%">
      <input type=text size=55 name="description" value="<%= toHtmlValue(Campaign.getDescription()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Groups
    </td>
    <td width="100%">
      <a href="/CampaignManager.do?command=ViewGroups&id=<%= Campaign.getId() %>"><%= (Campaign.hasGroups()?"<font color='green'>" + Campaign.getGroupCount() + " selected</font>":"<font color='red'>No Groups Selected</font>") %></a>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Message
    </td>
    <td width="100%">
      <a href="/CampaignManager.do?command=ViewMessage&id=<%= Campaign.getId() %>"><%= (Campaign.hasMessage()?"<font color='green'>" + Campaign.getMessageName() + "</font>":"<font color='red'>No Message Selected</font>") %></a>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Schedule
    </td>
    <td width="100%">
      <a href="/CampaignManager.do?command=ViewSchedule&id=<%= Campaign.getId() %>"><%= (Campaign.hasDetails()?"<font color='green'>Scheduled to run on " + Campaign.getActiveDateString() + "</font>":"<font color='red'>Not Scheduled</font>") %></a>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel">
      Delivery
    </td>
    <td width="100%">
      <a href="/CampaignManager.do?command=ViewSchedule&id=<%= Campaign.getId() %>"><%= (Campaign.hasDetails()?"<font color='green'>" + toHtml(Campaign.getDeliveryName())  + "</font>":"<font color='red'>Not Specified</font>") %></a>
    </td>
  </tr>
  
<%  
  if (Campaign.isReadyToActivate()) {
%>  
  <tr class="containerBody">
    <td class="formLabel">
      Begin Processing
    </td>
    <td>
      <a href="javascript:confirmForward('/CampaignManager.do?command=Activate&id=<%= Campaign.getId() %>&notify=true&modified=<%= Campaign.getModified() %>');"><font color="red">Click to Activate</font></a>
    </td>
  </tr>
<%
  }
%>  
  <tr class="containerBody">
    <td class="formLabel">
      Entered
    </td>
    <td width="100%">
      <dhv:username id="<%= Campaign.getEnteredBy() %>" /> - <%= Campaign.getEnteredString() %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel">
      Modified
    </td>
    <td width="100%">
      <dhv:username id="<%= Campaign.getModifiedBy() %>" /> - <%= Campaign.getModifiedString() %>
    </td>
  </tr>
  
</table>
<br>
<input type='submit' value="Update Campaign Details" name="Save">
  </td>
  </tr>
</table>
</form>

<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Campaign" class="com.darkhorseventures.cfsbase.Campaign" scope="request"/>
<jsp:useBean id="MessageList" class="com.darkhorseventures.cfsbase.MessageList" scope="request"/>
<jsp:useBean id="SurveyList" class="com.darkhorseventures.cfsbase.SurveyList" scope="request"/>
<jsp:useBean id="Message" class="com.darkhorseventures.cfsbase.Message" scope="request"/>
<%@ include file="initPage.jsp" %>
<form name="modForm" action="/CampaignManager.do?command=InsertMessage&id=<%= Campaign.getId() %>" method="post">
<a href="/CampaignManager.do?command=View">Back to Campaign List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(Campaign.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + Campaign.getId(); %>      
      <dhv:container name="communications" selected="message" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td width=100% class="containerBack">
<dhv:permission name="campaign-campaigns-edit"><a href="/CampaignManager.do?command=AddMessage&id=<%= Campaign.getId() %>">Add/Manage Messages</a><br>&nbsp;</dhv:permission>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
      <strong>Selected message for this campaign</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td width=50 valign="center" nowrap class="formLabel">
      Message
    </td>
    <td width=100% valign=center>
			<% MessageList.setJsEvent("onChange=\"javascript:window.frames['edit'].location.href='/CampaignManagerMessage.do?command=PreviewMessage&id=' + this.options[this.selectedIndex].value;\""); %>
      <%= MessageList.getHtmlSelect("messageId", Campaign.getMessageId()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td width=50 valign="top" nowrap class="formLabel">
      Preview
    </td>
    <td width=100% valign=center>
      <iframe id="edit" name="edit" frameborder="0" <dhv:browser id="ns">width="100%" height="200"</dhv:browser> <dhv:browser id="ie">style="border: 1px solid #cccccc; width: 100%; height: 100%;"</dhv:browser> onblur="return false" src="/CampaignManagerMessage.do?command=PreviewMessage&id=<%= Campaign.getMessageId() %>">
        <%= Message.getMessageText() %>
      </iframe>
    </td>
  </tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
      <strong>Message Attachments</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td width=50 valign="center" nowrap class="formLabel">
      Survey
    </td>
    <td width=100% valign=center>
    <% SurveyList.setJsEvent("onChange=\"javascript:window.frames['edit1'].location.href='/CampaignManagerMessage.do?command=PreviewSurvey&preview=1&id=' + this.options[this.selectedIndex].value;\""); %>
      <%= SurveyList.getHtmlSelect("surveyId", Campaign.getSurveyId()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td width=50 valign="top" nowrap class="formLabel">
      Preview
    </td>
    <td width=100% valign=center>
      <iframe id="edit1" name="edit1" frameborder="0" <dhv:browser id="ns">width="100%" height="200"</dhv:browser> <dhv:browser id="ie">style="border: 1px solid #cccccc; width: 100%; height: 100%;"</dhv:browser> onblur="return false" src="/CampaignManagerMessage.do?command=PreviewSurvey&preview=1&id=<%= Campaign.getSurveyId() %>">
      </iframe>
    </td>
  </tr>
  
</table>

<dhv:permission name="campaign-campaigns-edit">
<br>
<input type='submit' value="Update Campaign Message" name="Save">
</dhv:permission>
  </td>
  </tr>
</table>
</form>

<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Campaign" class="com.darkhorseventures.cfsbase.Campaign" scope="request"/>
<jsp:useBean id="SurveyList" class="com.darkhorseventures.cfsbase.SurveyList" scope="request"/>
<%@ include file="initPage.jsp" %>
<form name="modForm" action="/CampaignManager.do?command=InsertAttachment&id=<%= Campaign.getId() %>" method="post">
<a href="CampaignManager.do">Communications Manager</a> >
<a href="/CampaignManager.do?command=View">Campaign List</a> >
<a href="/CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>">Campaign Details</a> >
Attachment
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr>
    <td width="100%" class="containerBack">
      <ul>
        <li>Choose a survey that will be sent to the campaign groups</li>
        <li>Click "Update Campaign Attachment" to save changes</li>
        <li>Surveys can be created or edited in the <a href="CampaignManagerAttachment.do">Create Attachments</a> utility</li>
      </ul>
<dhv:permission name="campaign-campaigns-edit">
<input type="submit" value="Update Campaign Attachments" name="Save"><br>
&nbsp;<br>
</dhv:permission>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan="2" valign="center" align="left">
      <strong>Select a survey for this campaign</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td width="50" valign="center" nowrap class="formLabel">
      Survey
    </td>
    <td width="100%" valign="center">
    <% SurveyList.setJsEvent("onChange=\"javascript:window.frames['edit1'].location.href='/CampaignManagerMessage.do?command=PreviewSurvey&preview=1&id=' + this.options[this.selectedIndex].value;\""); %>
      <%= SurveyList.getHtmlSelect("surveyId", Campaign.getSurveyId()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td width="50" valign="top" nowrap class="formLabel">
      Preview
    </td>
    <td width="100%" valign="center">
      <iframe id="edit" name="edit" frameborder="0" <dhv:browser id="ns">width="100%" height="200"</dhv:browser> <dhv:browser id="ie">style="border: 1px solid #cccccc; width: 100%; height: 100%;"</dhv:browser> onblur="return false" src="http://www.yahoo.com">
        Akhilesh Mathur 
      </iframe>
    </td>
  </tr>
  
</table>
<dhv:permission name="campaign-campaigns-edit">
<br>
<input type="submit" value="Update Campaign Attachments" name="Save">
</dhv:permission>
  </td>
  </tr>
</table>
</form>

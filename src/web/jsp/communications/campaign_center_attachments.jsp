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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="SurveyList" class="org.aspcfs.modules.communications.base.SurveyList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
function fillFrame(object){
  if(object.options[object.selectedIndex].value != -1){
    window.frames['edit'].location.href='CampaignManager.do?command=PreviewSurvey&preview=1&id=' + object.options[object.selectedIndex].value + '&inline=true';
  }else{
    window.frames['edit'].location.href='CampaignManager.do?command=PreviewSurvey&preview=0&id=' + object.options[object.selectedIndex].value + '&inline=true';
  }
}
function updateList() {
  var sel = document.forms['modForm'].elements['ListView'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "CampaignManager.do?command=SurveyJSList&form=modForm&listView=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
</script>
<body onLoad="javascript:fillFrame(document.forms['modForm'].surveyId);">
<form name="modForm" action="CampaignManager.do?command=InsertAttachment&id=<%= Campaign.getId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<a href="CampaignManager.do?command=View"><dhv:label name="campaign.campaignList">Campaign List</dhv:label></a> >
<a href="CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>"><dhv:label name="campaign.campaignDetails">Campaign Details</dhv:label></a> >
<a href="CampaignManager.do?command=ViewAttachmentsOverview&id=<%= Campaign.getId() %>"><dhv:label name="Attachments">Attachments</dhv:label></a> >
<dhv:label name="campaign.surveys">Surveys</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" style="border: 1px solid #000;">
  <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
  <tr class="containerHeader">
    <td style="border-bottom: 1px solid #000;">
      <dhv:label name="campaign.campaign.colon" param='<%= "name="+toHtml(Campaign.getName()) %>'><strong>Campaign:</strong> <%= toHtml(Campaign.getName()) %></dhv:label>
    </td>
  </tr>
  <tr>
    <td width="100%" class="containerBack">
<dhv:permission name="campaign-campaigns-edit">
<input type="submit" value="<dhv:label name="button.saveChanges">Save Changes</dhv:label>" name="Save">
</dhv:permission>
<input type="button" value="<dhv:label name="button.backToAttachmentOverview">Back to Attachment Overview</dhv:label>" onClick="javascript:window.location.href='CampaignManager.do?command=ViewAttachmentsOverview&id=<%= Campaign.getId() %>'"><br>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2" valign="center">
      <strong><dhv:label name="campaign.selectSurvey.text">Select a survey for this campaign</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td valign="center" nowrap class="formLabel">
      <dhv:label name="campaign.survey">Survey</dhv:label>
    </td>
    <td valign="center">
    <SELECT SIZE="1" name="ListView" onChange="javascript:updateList();">
      <OPTION VALUE="my"<dhv:evaluate if='<%= "my".equals((String) request.getAttribute("listView")) %>'> selected</dhv:evaluate>><dhv:label name="campaign.mySurveys">My Surveys</dhv:label></OPTION>
      <OPTION VALUE="all"<dhv:evaluate if='<%= "all".equals((String) request.getAttribute("listView")) %>'> selected</dhv:evaluate>><dhv:label name="campaign.allSurveys">All Surveys</dhv:label></OPTION>
    </SELECT>
    <% SurveyList.setJsEvent("onChange=\"javascript:fillFrame(this);\""); %>
      <%= SurveyList.getHtmlSelect("surveyId", Campaign.getSurveyId()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">
      <dhv:label name="button.preview">Preview</dhv:label>
    </td>
    <td valign="center">
      <iframe id="edit" name="edit" frameborder="0" <dhv:browser id="ie" include="false">width="100%" height="200"</dhv:browser> <dhv:browser id="ie">style="border: 1px solid #cccccc; width: 100%; height: 200;"</dhv:browser> onblur="return false" src="CampaignManager.do?command=PreviewSurvey&preview=0&id=<%= Campaign.getSurveyId() %>&inline=true">
        <dhv:label name="campaign.viewingNotSupported.text">Viewing not supported by this browser</dhv:label>
      </iframe>
    </td>
  </tr>
</table>
<br>
<dhv:permission name="campaign-campaigns-edit">
<input type="submit" value="<dhv:label name="button.saveChanges">Save Changes</dhv:label>" name="Save">
</dhv:permission>
<input type="button" value="<dhv:label name="button.backToAttachmentOverview">Back to Attachment Overview</dhv:label>" onClick="javascript:window.location.href='CampaignManager.do?command=ViewAttachmentsOverview&id=<%= Campaign.getId() %>'">
  </td>
  </tr>
</table>
</form>
</body>

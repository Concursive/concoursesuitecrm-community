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
  var url = "CampaignManager.do?command=SurveyJSList&listView=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
</script>
<body onLoad="javascript:fillFrame(document.forms['modForm'].surveyId);">
<form name="modForm" action="CampaignManager.do?command=InsertAttachment&id=<%= Campaign.getId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do">Communications</a> >
<a href="CampaignManager.do?command=View">Campaign List</a> >
<a href="CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>">Campaign Details</a> >
<a href="CampaignManager.do?command=ViewAttachmentsOverview&id=<%= Campaign.getId() %>">Attachments</a> >
Surveys
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" style="border: 1px solid #000;">
  <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
  <tr class="containerHeader">
    <td style="border-bottom: 1px solid #000;">
      <strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr>
    <td width="100%" class="containerBack">
<dhv:permission name="campaign-campaigns-edit">
<input type="submit" value="Save Changes" name="Save">
</dhv:permission>
<input type="button" value="Back to Attachment Overview" onClick="javascript:window.location.href='CampaignManager.do?command=ViewAttachmentsOverview&id=<%= Campaign.getId() %>'"><br>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2" valign="center">
      <strong>Select a survey for this campaign</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td valign="center" nowrap class="formLabel">
      Survey
    </td>
    <td valign="center">
    <SELECT SIZE="1" name="ListView" onChange="javascript:updateList();">
      <OPTION VALUE="my"<dhv:evaluate if="<%= "my".equals((String) request.getAttribute("listView")) %>"> selected</dhv:evaluate>>My Surveys</OPTION>
      <OPTION VALUE="all"<dhv:evaluate if="<%= "all".equals((String) request.getAttribute("listView")) %>"> selected</dhv:evaluate>>All Surveys</OPTION>
    </SELECT>
    <% SurveyList.setJsEvent("onChange=\"javascript:fillFrame(this);\""); %>
      <%= SurveyList.getHtmlSelect("surveyId", Campaign.getSurveyId()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">
      Preview
    </td>
    <td valign="center">
      <iframe id="edit" name="edit" frameborder="0" <dhv:browser id="ie" include="false">width="100%" height="200"</dhv:browser> <dhv:browser id="ie">style="border: 1px solid #cccccc; width: 100%; height: 200;"</dhv:browser> onblur="return false" src="CampaignManager.do?command=PreviewSurvey&preview=0&id=<%= Campaign.getSurveyId() %>&inline=true">
        Viewing not supported by this browser
      </iframe>
    </td>
  </tr>
</table>
<br>
<dhv:permission name="campaign-campaigns-edit">
<input type="submit" value="Save Changes" name="Save">
</dhv:permission>
<input type="button" value="Back to Attachment Overview" onClick="javascript:window.location.href='CampaignManager.do?command=ViewAttachmentsOverview&id=<%= Campaign.getId() %>'">
  </td>
  </tr>
</table>
</form>
</body>

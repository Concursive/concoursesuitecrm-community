<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="Survey" class="org.aspcfs.modules.communications.base.Survey" scope="request"/>
<jsp:useBean id="fileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="modForm" action="CampaignManager.do?command=InsertAttachment&id=<%= Campaign.getId() %>" method="post">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="CampaignManager.do">Communications</a> >
<a href="CampaignManager.do?command=View">Campaign List</a> >
<a href="CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>">Campaign Details</a> >
Attachments
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" style="border: 1px solid #000;">
  <tr class="containerHeader">
    <td style="border-bottom: 1px solid #000;">
      <strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr>
    <td width="100%" class="containerBack">
<input type="button" value="Back to Campaign Details" onClick="javascript:window.location.href='CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>'"><br>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Configured attachments for this campaign</strong>
    </th>     
  </tr>
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">
      Surveys
    </td>
    <td valign="top">
      <dhv:evaluate if="<%= Campaign.hasSurvey() %>">
        Attached: <%= toHtml(Survey.getName()) %><br>
      </dhv:evaluate>
      [<a href="CampaignManager.do?command=ViewAttachment&id=<%= Campaign.getId() %>">Change survey</a>]
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">
      File Attachments
    </td>
    <td valign="top">
      <dhv:evaluate if="<%= fileItemList.size() > 0 %>">
        Files Added: <%= fileItemList.size() %> file<%= (fileItemList.size() != 1?"s":"") %><br>
      </dhv:evaluate>
      [<a href="CampaignManager.do?command=ManageFileAttachments&id=<%= Campaign.getId() %>">Change file attachments</a>]
    </td>
  </tr>
</table>
&nbsp;<br>
<input type="button" value="Back to Campaign Details" onClick="javascript:window.location.href='CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>'">
  </td>
  </tr>
</table>
</form>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="Survey" class="org.aspcfs.modules.communications.base.Survey" scope="request"/>
<jsp:useBean id="fileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="modForm" action="CampaignManager.do?command=InsertAttachment&id=<%= Campaign.getId() %>" method="post">
<a href="CampaignManager.do">Communications Manager</a> >
<a href="CampaignManager.do?command=View">Campaign List</a> >
<a href="CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>">Campaign Details</a> >
Attachments
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
        <li>Choose from any of the following attachment types to include with your Message</li>
        <li>Certain attachments must be created and modified in the <a href="CampaignManagerAttachment.do">Create Attachments</a> utility</li>
      </ul>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan="2" valign="center" align="left">
      <strong>Configured attachments for this campaign</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td width="50" valign="top" nowrap class="formLabel">
      Surveys
    </td>
    <td width="100%" valign="top">
      <a href="CampaignManager.do?command=ViewAttachment&id=<%= Campaign.getId() %>">
        Change survey
      </a>
      <dhv:evaluate if="<%= Campaign.hasSurvey() %>">
        <br>Attached: <%= Survey.getName() %>&nbsp;
      </dhv:evaluate>
    </td>
  </tr>
  <tr class="containerBody">
    <td width="50" valign="top" nowrap class="formLabel">
      File Attachments
    </td>
    <td width="100%" valign="top">
      <a href="CampaignManager.do?command=ManageFileAttachments&id=<%= Campaign.getId() %>">  
        Change file attachments
      </a>
      <dhv:evaluate if="<%= fileItemList.size() > 0 %>">
        <br>Added: <%= fileItemList.size() %> file<%= (fileItemList.size() != 1?"s":"") %>
      </dhv:evaluate>
    </td>
  </tr>
</table>
&nbsp;<br>
<input type="button" value="Done" onClick="javascript:window.location.href='CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>'">
  </td>
  </tr>
</table>
</form>

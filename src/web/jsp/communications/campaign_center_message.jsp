<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Campaign" class="com.darkhorseventures.cfsbase.Campaign" scope="request"/>
<jsp:useBean id="MessageList" class="com.darkhorseventures.cfsbase.MessageList" scope="request"/>
<jsp:useBean id="Message" class="com.darkhorseventures.cfsbase.Message" scope="request"/>
<%@ include file="initPage.jsp" %>
<form name="modForm" action="/CampaignManager.do?command=InsertMessage&id=<%= Campaign.getId() %>" method="post">
<a href="CampaignManager.do">Communications Manager</a> > 
<a href="/CampaignManager.do?command=View">Campaign List</a> >
<a href="/CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>">Campaign Details</a> >
Message
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
        <li>Choose a message that will be sent to the campaign groups</li>
        <li>Click "Update Campaign Message" to save changes</li>
        <li>Messages can be created or edited in the <a href="CampaignManagerMessage.do?command=View">Create Messages</a> utility</li>
      </ul>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan="2" valign="center" align="left">
      <strong>Select a message for this campaign</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td width="50" valign="center" nowrap class="formLabel">
      Message
    </td>
    <td width="100%" valign="center">
			<% MessageList.setJsEvent("onChange=\"javascript:window.frames['edit'].location.href='/CampaignManagerMessage.do?command=PreviewMessage&id=' + this.options[this.selectedIndex].value;\""); %>
      <%= MessageList.getHtmlSelect("messageId", Campaign.getMessageId()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td width="50" valign="top" nowrap class="formLabel">
      Preview
    </td>
    <td width="100%" valign="center">
      <iframe id="edit" name="edit" frameborder="0" <dhv:browser id="ns">width="100%" height="200"</dhv:browser> <dhv:browser id="ie">style="border: 1px solid #cccccc; width: 100%; height: 100%;"</dhv:browser> onblur="return false" src="/CampaignManagerMessage.do?command=PreviewMessage&id=<%= Campaign.getMessageId() %>">
        <%= Message.getMessageText() %>
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

<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Campaign" class="com.darkhorseventures.cfsbase.Campaign" scope="request"/>
<jsp:useBean id="MessageList" class="com.darkhorseventures.cfsbase.MessageList" scope="request"/>
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
      <a href="/CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>"><font color="#000000">Details</font></a><dhv:permission name="campaign-campaigns-groups-view"> |
      <a href="/CampaignManager.do?command=ViewGroups&id=<%= Campaign.getId() %>"><font color="#000000">Groups</font></a></dhv:permission><dhv:permission name="campaign-campaigns-messages-view"> | 
      <a href="/CampaignManager.do?command=ViewMessage&id=<%= Campaign.getId() %>"><font color="#0000FF">Message</font></a></dhv:permission><dhv:permission name="campaign-campaigns-view"> | 
      <a href="/CampaignManager.do?command=ViewSchedule&id=<%= Campaign.getId() %>"><font color="#000000">Schedule</font></a></dhv:permission>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="campaign-campaigns-edit"><a href="/CampaignManager.do?command=AddMessage&id=<%= Campaign.getId() %>">Add/Manage Messages</a><br>&nbsp;</dhv:permission>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
      <strong>Selected message for this campaign</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Message
    </td>
    <td width="100%">
      <%-- MessageList.setJsEvent("onChange=\"javascript:document.frames['edit'].location.href='/CampaignManagerMessage.do?command=PreviewMessage&id=' + this.options[this.selectedIndex].value;\""); --%>
			<% MessageList.setJsEvent("onChange=\"javascript:window.frames['edit'].location.href='/CampaignManagerMessage.do?command=PreviewMessage&id=' + this.options[this.selectedIndex].value;\""); %>
      <%= MessageList.getHtmlSelect("messageId", Campaign.getMessageId()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      Preview
    </td>
    <td width="100%">
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

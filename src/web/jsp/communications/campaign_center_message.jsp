<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="MessageList" class="org.aspcfs.modules.communications.base.MessageList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function updateMessageList() {
    var sel = document.forms['modForm'].elements['ListView'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "CampaignManager.do?command=MessageJSList&listView=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
</script>
<form name="modForm" action="CampaignManager.do?command=InsertMessage&id=<%= Campaign.getId() %>" method="post">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="CampaignManager.do">Communications</a> > 
<a href="CampaignManager.do?command=View">Campaign List</a> >
<a href="CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>">Campaign Details</a> >
Message
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
<input type="submit" value="Update Campaign Message" name="Save">
<input type="submit" value="Cancel" onClick="this.form.action='CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>'"><br>
&nbsp;<br>
</dhv:permission>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Select a message for this campaign</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Message
    </td>
    <td width="100%" valign="center">
      <SELECT SIZE="1" name="ListView" onChange="javascript:updateMessageList();">
        <OPTION VALUE="my"<dhv:evaluate if="<%= "my".equals((String) request.getAttribute("listView")) %>"> selected</dhv:evaluate>>My Messages</OPTION>
        <OPTION VALUE="all"<dhv:evaluate if="<%= "all".equals((String) request.getAttribute("listView")) %>"> selected</dhv:evaluate>>All Messages</OPTION>
        <OPTION VALUE="hierarchy"<dhv:evaluate if="<%= "hierarchy".equals((String) request.getAttribute("listView")) %>"> selected</dhv:evaluate>>Controlled Hierarchy Messages</OPTION>
        <OPTION VALUE="personal"<dhv:evaluate if="<%= "personal".equals((String) request.getAttribute("listView")) %>"> selected</dhv:evaluate>>Personal Messages</OPTION>
      </SELECT>
			<% MessageList.setJsEvent("onChange=\"javascript:window.frames['edit'].location.href='CampaignManagerMessage.do?command=PreviewMessage&id=' + this.options[this.selectedIndex].value + '&inline=true';\""); %>
      <%= MessageList.getHtmlSelect("messageId", Campaign.getMessageId()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">
      Preview
    </td>
    <td valign="center">
      <iframe id="edit" name="edit" frameborder="0" <dhv:browser id="ie" include="false">width="100%" height="200"</dhv:browser> <dhv:browser id="ie">style="border: 1px solid #cccccc; width: 100%; height: 200;"</dhv:browser> onblur="return false" src="CampaignManagerMessage.do?command=PreviewMessage&id=<%= Campaign.getMessageId() %>">
        Viewing not supported by this browser
      </iframe>
    </td>
  </tr>
</table>
<dhv:permission name="campaign-campaigns-edit">
<br>
<input type="submit" value="Update Campaign Message" name="Save">
<input type="submit" value="Cancel" onClick="this.form.action='CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>'">
</dhv:permission>
  </td>
  </tr>
</table>
</form>

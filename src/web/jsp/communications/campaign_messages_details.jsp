<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="MessageDetails" class="org.aspcfs.modules.communications.base.Message" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<form name="details" action="CampaignManagerMessage.do?command=Modify&id=<%= MessageDetails.getId() %>" method="post">
<a href="CampaignManager.do">Communications Manager</a> >
<a href="CampaignManagerMessage.do?command=View">Message List</a> >
Message Details
<hr color="#BFBFBB" noshade>
<dhv:permission name="campaign-campaigns-messages-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=Modify&id=<%= MessageDetails.getId() %>';submit();"></dhv:permission>
<dhv:permission name="campaign-campaigns-messages-delete">
  <input type="button" value="Delete" onClick="javascript:popURLReturn('CampaignManagerMessage.do?command=ConfirmDelete&id=<%= MessageDetails.getId() %>&popup=true','CampaignManagerMessage.do?command=View', 'Delete_message','330','200','yes','no');">
</dhv:permission>
<dhv:permission name="campaign-campaigns-messages-edit,campaign-campaigns-messages-delete"></dhv:permission>
<dhv:permission name="campaign-campaigns-messages-add"><input type="button" value="Clone" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=Clone&id=<%= MessageDetails.getId() %>';submit();"><br>&nbsp;</dhv:permission>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2" valign="center" align="left">
      <strong>Message Details</strong>
    </td>
  </tr>
  
  <tr>
    <td width="125" class="formLabel" valign="center" align="right">
      Name
    </td>
    <td width="100%" valign="center" width="100%">
      <%= toHtml(MessageDetails.getName()) %>&nbsp; 
    </td>
  </tr>
  
  <tr>
    <td width="125" class="formLabel" valign="top" align="right">
      Internal Description
    </td>
    <td valign="top">
      <%= toHtml(MessageDetails.getDescription()) %>&nbsp; 
    </td>
  </tr>
	<tr>
    <td width="125" class="formLabel" valign="top" align="right">
      Message
    </td>
    <td valign="top">
    	<strong>From:</strong> <%= toHtml(MessageDetails.getReplyTo()) %><br>
      <strong>Subject:</strong> <%= toHtml(MessageDetails.getMessageSubject()) %><br>
      &nbsp;<br>
			<%= (MessageDetails.getMessageText()) %>
    </td>
  </tr>
</table>
<dhv:permission name="campaign-campaigns-messages-edit,campaign-campaigns-messages-delete"><br></dhv:permission>
<dhv:permission name="campaign-campaigns-messages-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=Modify&id=<%= MessageDetails.getId() %>';submit();"></dhv:permission>
<dhv:permission name="campaign-campaigns-messages-delete">
  <input type="button" value="Delete" onClick="javascript:popURLReturn('CampaignManagerMessage.do?command=ConfirmDelete&id=<%= MessageDetails.getId() %>&popup=true','CampaignManagerMessage.do?command=View', 'Delete_message','330','200','yes','no');">
</dhv:permission>
<dhv:permission name="campaign-campaigns-messages-add"><input type="button" value="Clone" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=Clone&id=<%= MessageDetails.getId() %>';submit();"></dhv:permission>
</form>


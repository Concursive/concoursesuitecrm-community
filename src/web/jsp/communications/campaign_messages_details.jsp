<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="MessageDetails" class="com.darkhorseventures.cfsbase.Message" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<a href="CampaignManager.do">Communications Manager</a> >
<a href="/CampaignManagerMessage.do?command=View">Message List</a> >
Message Details
<hr color="#BFBFBB" noshade>
<form name="details" action="/CampaignManagerMessage.do?command=Modify&id=<%= MessageDetails.getId() %>" method="post">
<dhv:permission name="campaign-campaigns-messages-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='/CampaignManagerMessage.do?command=Modify&id=<%= MessageDetails.getId() %>';submit();"></dhv:permission>
<dhv:permission name="campaign-campaigns-messages-delete"><input type="button" value="Delete" onClick="javascript:this.form.action='/CampaignManagerMessage.do?command=Delete&id=<%=MessageDetails.getId() %>';confirmSubmit(this.form);"></dhv:permission>
<dhv:permission name="campaign-campaigns-messages-edit,campaign-campaigns-messages-delete"><br>&nbsp;</dhv:permission>
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
<dhv:permission name="campaign-campaigns-messages-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='/CampaignManagerMessage.do?command=Modify&id=<%= MessageDetails.getId() %>';submit();"></dhv:permission>
<dhv:permission name="campaign-campaigns-messages-delete"><input type="button" value="Delete" onClick="javascript:this.form.action='/CampaignManagerMessage.do?command=Delete&id=<%=MessageDetails.getId() %>';confirmSubmit(this.form);"></dhv:permission>
</form>


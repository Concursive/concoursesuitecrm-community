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
<jsp:useBean id="MessageDetails" class="org.aspcfs.modules.communications.base.Message" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<form name="details" action="CampaignManagerMessage.do?command=Modify&id=<%= MessageDetails.getId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do">Communications</a> >
<a href="CampaignManagerMessage.do?command=View">Message List</a> >
Message Details
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="campaign-campaigns-messages-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=Modify&id=<%= MessageDetails.getId() %>';submit();"></dhv:permission>
<dhv:permission name="campaign-campaigns-messages-delete">
  <input type="button" value="Delete" onClick="javascript:popURLReturn('CampaignManagerMessage.do?command=ConfirmDelete&id=<%= MessageDetails.getId() %>&popup=true','CampaignManagerMessage.do?command=View', 'Delete_message','330','200','yes','no');">
</dhv:permission>
<dhv:permission name="campaign-campaigns-messages-edit,campaign-campaigns-messages-delete"></dhv:permission>
<dhv:permission name="campaign-campaigns-messages-add"><input type="button" value="Clone" onClick="javascript:this.form.action='CampaignManagerMessage.do?command=Clone&id=<%= MessageDetails.getId() %>';submit();"><br>&nbsp;</dhv:permission>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Message Details</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      Name
    </td>
    <td>
      <%= toHtml(MessageDetails.getName()) %>&nbsp; 
    </td>
  </tr>
  <tr>
    <td class="formLabel" valign="top">
      Internal Description
    </td>
    <td valign="top">
      <%= toHtml(MessageDetails.getDescription()) %>&nbsp; 
    </td>
  </tr>
	<tr>
    <td class="formLabel" valign="top">
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


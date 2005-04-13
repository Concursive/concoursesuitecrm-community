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
<jsp:useBean id="Survey" class="org.aspcfs.modules.communications.base.Survey" scope="request"/>
<jsp:useBean id="fileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/div.js"></script>
<script language="JavaScript">
  function updateAddressRequest(addAddress, id){
    if (addAddress.value == "Attach"){
      var url = "CampaignManager.do?command=UpdateAddressRequest&insert=true&id=" + id;
    }else{
      var url = "CampaignManager.do?command=UpdateAddressRequest&insert=false&id=" + id;
    }
    window.frames['server_commands'].location.href=url;
  }
  function toggleAddressUpdateRequestFields(parent){
    var response = "<%=request.getAttribute("addressRequestChanged")%>";
    var addressRequest = "<%=Campaign.getHasAddressRequest()? "Attach":"Remove"%>";
    if ("YES" == response){
      if (addressRequest == "Attach"){
        parent.document.forms['modForm'].addAddressUpdateAccess.value = "Remove";
        changeParentDivContent(parent,"changeAddressRequest","Yes &nbsp;");
      }else{
        parent.document.forms['modForm'].addAddressUpdateAccess.value = "Attach";
        changeParentDivContent(parent,"changeAddressRequest","No &nbsp;");
      }
    }
   }
</script>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:toggleAddressUpdateRequestFields(parent);">
<form name="modForm" action="CampaignManager.do?command=InsertAttachment&id=<%= Campaign.getId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<a href="CampaignManager.do?command=View"><dhv:label name="campaign.campaignList">Campaign List</dhv:label></a> >
<a href="CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>"><dhv:label name="campaign.campaignDetails">Campaign Details</dhv:label></a> >
<dhv:label name="Attachments">Attachments</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" style="border: 1px solid #000;">
  <tr class="containerHeader">
    <td style="border-bottom: 1px solid #000;">
      <dhv:label name="campaign.campaign.colon" param="<%= "name="+toHtml(Campaign.getName()) %>"><strong>Campaign:</strong> <%= toHtml(Campaign.getName()) %></dhv:label>
    </td>
  </tr>
  <tr>
    <td width="100%" class="containerBack">
<input type="button" value="<dhv:label name="button.backToCampaignDetails">Back to Campaign Details</dhv:label>" onClick="javascript:window.location.href='CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>'"><br>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="Configured attachments for this campaign">Configured attachments for this campaign</dhv:label></strong>
    </th>
  </tr>
<dhv:permission name="campaign-campaigns-surveys-view">
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">
      <dhv:label name="campaign.surveys">Surveys</dhv:label>
    </td>
    <td valign="top">
      <dhv:evaluate if="<%= Campaign.hasSurvey() %>">
        <dhv:label name="campaign.attached.colon" param="<%= "survey.name="+ toHtml(Survey.getName()) %>">Attached: <%= toHtml(Survey.getName()) %></dhv:label><br>
      </dhv:evaluate>
      [<a href="CampaignManager.do?command=ViewAttachment&id=<%= Campaign.getId() %>"><dhv:label name="campaign.changeSurvey">Change survey</dhv:label></a>]
    </td>
  </tr>
</dhv:permission>
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">
      <dhv:label name="campaign.fileAttachments">File Attachments</dhv:label>
    </td>
    <td valign="top">
      <dhv:evaluate if="<%= fileItemList.size() > 0 %>">
        <% if(fileItemList.size() != 1) {%>
          <dhv:label name="campaign.filesAdded.colon" param="<%= "number="+fileItemList.size() %>">Files Added: <%= fileItemList.size() %> files</dhv:label>
        <%} else {%>
          <dhv:label name="campaign.filesAdded.colon.file" param="<%= "number="+fileItemList.size() %>">Files Added: <%= fileItemList.size() %> file</dhv:label>
        <%}%><br />
      </dhv:evaluate>
      [<a href="CampaignManager.do?command=ManageFileAttachments&id=<%= Campaign.getId() %>"><dhv:label name="campaign.changeFileAttachments">Change file attachments</dhv:label></a>]
    </td>
  </tr>
<dhv:permission name="campaign-campaign-contact-updater-view">
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">
      <dhv:label name="campaign.addressUpdateRequired">Address update request</dhv:label>
    </td>
    <td valign="top">
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <div id="changeAddressRequest" name="changeAddressRequest"><%=Campaign.getHasAddressRequest()? "Yes":"No"%> &nbsp;</div>
          </td>
          <td>
            <input type="button" name="addAddressUpdateAccess" value="<%=Campaign.getHasAddressRequest()? "Remove":"Attach"%>" onClick="javascript:updateAddressRequest(this, <%=Campaign.getId()%>);" />
          </td>
        </tr>
      </table>
      <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0" width="0" border="0" frameborder="0"></iframe>
    </td>
  </tr>
</dhv:permission>
</table>
&nbsp;<br>
<input type="button" value="<dhv:label name="button.backToCampaignDetails">Back to Campaign Details</dhv:label>" onClick="javascript:window.location.href='CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>'">
  </td>
  </tr>
</table>
</form>

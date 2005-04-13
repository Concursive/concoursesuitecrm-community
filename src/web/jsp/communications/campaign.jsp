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
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<dhv:label name="communications.campaign.Communications">Communications</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:label name="communications.campaign.reviewchangeOptions">Review and manage campaigns with the following options:</dhv:label><br />
<dhv:permission name="campaign-campaigns-add">
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong><a href="CampaignManager.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td>
    	<dhv:label name="communications.campaign.TrackAndAnalyzeCampaigns">Track and analyze campaigns that have been activated and executed.</dhv:label><br />
      <br />
      <dhv:label name="communications.campaign.MessagesCanBeSentOut">Messages can be sent out by any combination of email, fax, or mail merge. The Dashboard shows an overview of sent messages and allows you to drill down and view recipients and any survey results.</dhv:label><br />
      <br />
      <dhv:label name="campaign.chooseAddCampaign.text" param="addCampaign=<a href=\"CampaignManager.do?command=Add\">|endCampaign=</a>">Choose <a href="CampaignManager.do?command=Add">Add Campaign</a> to create a new campaign.</dhv:label>
     </td>
  </tr>
</table>
</dhv:permission>
<dhv:permission name="campaign-campaigns-view">
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong><a href="CampaignManager.do?command=View"><dhv:label name="communications.campaign.CampaignBuilder">Campaign Builder</dhv:label></a></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td>
      <dhv:label name="communications.campaign.CreateWorkOnExistingCampaigns">Create or work on existing campaigns.</dhv:label>
      <br />
      <br />
      <dhv:label name="communications.campaign.CampaignBuilderAllowsToSelect">The Campaign Builder allows you to select groups of contacts that you would like to send a message to, as well as schedule a delivery date. Additional options are available.</dhv:label>
      <br />
      <br />
      <dhv:label name="communications.campaign.DistributionGroupsCanBeConstructed" param="buildGroups=<a href=\"CampaignManagerGroup.do?command=View\">|createMessage=<a href=\"CampaignManagerMessage.do?command=View\">|endGroups=</a>">Distribution groups can be constructed in the <a href="CampaignManagerGroup.do?command=View">Build Groups</a> utility, and messages can be written in the <a href="CampaignManagerMessage.do?command=View">Create Messages</a> utility.</dhv:label>
    </td>
  </tr>
</table>
</dhv:permission>
<dhv:permission name="campaign-campaigns-groups-view">
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong><a href="CampaignManagerGroup.do?command=View"><dhv:label name="communications.campaign.BuildGroups">Build Groups</dhv:label></a></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td>
      <dhv:label name="communications.campaign.AssembleDynamicDistributionGroups">Assemble dynamic distribution groups.</dhv:label>
      <br />
      <br />
      <dhv:label name="communications.campaign.NeedsGroupToSendMessage">Each campaign needs at least one group to send a message to. Use criteria to filter the contacts you need to reach and use them over and over again. As new contacts meet the criteria, they will be included in future campaigns.</dhv:label>
    </td>
  </tr>
</table>
</dhv:permission>
<dhv:permission name="campaign-campaigns-messages-view">
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong><a href="CampaignManagerMessage.do?command=View"><dhv:label name="communications.campaign.CreateMessages">Create Messages</dhv:label></a></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td>
      <dhv:label name="communications.campaign.ComposeMessageToReach">Compose a message to reach your audience.</dhv:label>
      <br />
      <br />
      <dhv:label name="communications.campaign.EachCampaignRequiresMessage">Each campaign requires a message that will be sent to selected groups.  Write the message once, then use it in any number of future campaigns. Modified messages will only affect future campaigns.</dhv:label>
    </td>
  </tr>
</table>
</dhv:permission>
<dhv:permission name="campaign-campaigns-surveys-view">
&nbsp;<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong><a href="CampaignManagerAttachment.do"><dhv:label name="communications.campaign.CreateAttachments">Create Attachments</dhv:label></a></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td>
      <dhv:label name="communications.campaign.InteractWithRecipients">Interact with your recipients.</dhv:label>
      <br />
      <br />
      <dhv:label name="communications.campaign.AttachmentsCanIncludeInteractiveItems">Attachments can include interactive items, like surveys, or provide additional materials like files.</dhv:label>
    </td>
  </tr>
</table>
</dhv:permission>

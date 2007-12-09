<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.communications.base.*, org.aspcfs.modules.base.Notification" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="fileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<form name="modForm" action="CampaignManager.do?command=Modify&id=<%= Campaign.getId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> > 
<a href="CampaignManager.do?command=View"><dhv:label name="campaign.campaignList">Campaign List</dhv:label></a> >
<dhv:label name="campaign.campaignDetails">Campaign Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" style="border: 1px solid #000;">
  <tr class="containerHeader">
    <td style="border-bottom: 1px solid #000;">
      <dhv:label name="campaign.campaign.colon" param='<%= "name="+toHtml(Campaign.getName()) %>'><strong>Campaign:</strong> <%= toHtml(Campaign.getName()) %></dhv:label>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<ul>
  <li><dhv:label name="campaign.selectItemToBuildCampaign.text">Select from the following items to build a campaign</dhv:label></li>
  <li><dhv:label name="campaign.itemsToSelect.text.1">Items can be worked on in any order</dhv:label></li>
  <li><dhv:label name="campaign.itemsToSelect.text.2">Campaigns will not start until each section is complete, and the campaign has been activated</dhv:label></li>
  <li><dhv:label name="campaign.itemsToSelect.text.3">Campaign items can be accessed by all users who have access to campaigns</dhv:label></li>
</ul>
      <table cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr>
          <td valign="top" align="center">
            <table cellpadding="4" cellspacing="0" width="100%" class="details">
              <tr>
                <th style="text-align: center;">
                  <strong><dhv:label name="campaign.groups.label">Group(s)</dhv:label></strong>
                </th>
              </tr>
              <tr class="containerBody">
                <td style="text-align: center;">
                  <% if(Campaign.hasGroups()) {%>
                    <font color='green'><dhv:label name="campaign.selected" param='<%= "number="+Campaign.getGroupCount() %>'><%= Campaign.getGroupCount() %> selected</dhv:label></font>
                  <%} else {%>
                    <font color='red'><dhv:label name="campaign.noGroupsSelected">No Groups Selected</dhv:label></font>
                  <%}%><br />
                  &nbsp;<br>
                  <dhv:permission name="campaign-campaigns-edit"><a href="CampaignManager.do?command=AddGroups&id=<%= Campaign.getId() %>"><dhv:label name="campaign.chooseGroups">Choose Groups</dhv:label></a><br>&nbsp;</dhv:permission>
                </td>
              </tr>
            </table>
          </td>
          <td>
            &nbsp;
          </td>
          <td valign="top" align="center">
            <table cellpadding="4" cellspacing="0" width="100%" class="details">
              <tr>
                <th style="text-align: center;">
                  <strong><dhv:label name="project.message">Message</dhv:label></strong>
                </th>
              </tr>
              <tr class="containerBody">
                <td style="text-align: center;">
                  <% if(Campaign.hasMessage()) {%>
                    <font color='green'><%= toHtml(Campaign.getMessageName()) %></font>
                  <%} else {%>
                    <font color='red'><dhv:label name="campaign.noMessageSelected">No Message Selected</dhv:label></font>
                  <%}%><br />
                  <br />
                  <dhv:permission name="campaign-campaigns-messages-view"><a href="CampaignManager.do?command=ViewMessage&id=<%= Campaign.getId() %>"><dhv:label name="campaign.chooseMessage">Choose Message</dhv:label></a><br>&nbsp;</dhv:permission>
                </td>
              </tr>
            </table>
          </td>
          <td>
            &nbsp;
          </td>
          <td valign="top" align="center">
            <table cellpadding="4" cellspacing="0" width="100%" class="details">
              <tr>
                <th style="text-align: center;">
                  <strong><dhv:label name="Attachments">Attachments</dhv:label></strong>
                </th>
              </tr>
              <tr class="containerBody">
                <td style="text-align: center;">
                  <dhv:evaluate if="<%= Campaign.hasSurvey() %>">
                    <font color="green"><dhv:label name="campaign.survey">Survey</dhv:label></font><br>
                  </dhv:evaluate>
                  <dhv:evaluate if="<%= fileItemList.size() > 0 %>">
                    <font color="green"><dhv:label name="campaign.files">Files</dhv:label></font><br>
                  </dhv:evaluate>
                  <dhv:evaluate if="<%= Campaign.getHasAddressRequest() %>">
                    <font color="green"><dhv:label name="campaign.addressUpdateRequired">Address update request</dhv:label></font><br>
                  </dhv:evaluate>
                  <dhv:evaluate if="<%= !Campaign.hasSurvey() && fileItemList.size() < 1 && !Campaign.getHasAddressRequest() %>">
                    <dhv:label name="accounts.accounts_contacts_calls_details_followup_include.None">None</dhv:label><br />
                    &nbsp;<br />
                  </dhv:evaluate>
                  <dhv:permission name="campaign-campaigns-edit"><a href="CampaignManager.do?command=ViewAttachmentsOverview&id=<%= Campaign.getId() %>"><dhv:label name="campaign.chooseOptionalAttachments" param="break=<br />">Choose optional<br />attachments</dhv:label></a></dhv:permission>
                </td>
              </tr>
            </table>
          </td>
          <td>
            &nbsp;
          </td>
          <td valign="top" align="center">
            <table cellpadding="4" cellspacing="0" width="100%" class="details">
              <tr>
                <th style="text-align: center;">
                  <strong><dhv:label name="quotes.delivery">Delivery</dhv:label></strong>
                </th>
              </tr>
              <tr class="containerBody">
                <td style="text-align: center;">
                  <% if(Campaign.hasDetails()){ %>
                    <font color="green">
                    <dhv:label name="campaign.scheduledFor" param='<%= "time="+getTime(pageContext,Campaign.getActiveDate(),Campaign.getActiveDateTimeZone(),DateFormat.SHORT,true,false,false,"&nbsp;") %>'>Scheduled for <zeroio:tz timestamp="<%= Campaign.getActiveDate() %>" dateOnly="true" timeZone="<%= Campaign.getActiveDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/></dhv:label>
                    <% if (!User.getTimeZone().equals(Campaign.getActiveDateTimeZone())) { %>
                    <br />
                    <zeroio:tz timestamp="<%= Campaign.getActiveDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
                    <% } %>
                    <%= toHtml(Campaign.getDeliveryName()) %></font><br>
                  <% }else{ %>
                    <font color="red"><dhv:label name="admin.notScheduled">Not scheduled</dhv:label></font><br>&nbsp;<br>
                  <% } %>
                  <dhv:permission name="campaign-campaigns-view"><a href="CampaignManager.do?command=ViewSchedule&id=<%= Campaign.getId() %>"><dhv:label name="campaign.chooseOptions">Choose Options</dhv:label></a><br>&nbsp;</dhv:permission>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
&nbsp;

<%  
  if (Campaign.isReadyToActivate()) {
%>  
  <dhv:permission name="campaign-campaigns-edit">
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr class="containerBack">
      <td>
        <center>
          <dhv:label name="campaign.activateCampaign.text" param="break=<br />">This campaign has been configured and can now be activated.<br />Once active, today's campaigns will begin processing in under 5 minutes and cannot be canceled.<br />Verify the campaign then</dhv:label>
          <dhv:evaluate if="<%= Campaign.getDeliveryType() !=  Notification.BROADCAST %>">
          <a href="javascript:confirmForward('CampaignManager.do?command=Activate&id=<%= Campaign.getId() %>&notify=true&modified=<%= Campaign.getModified() %>');"><font color="red"><dhv:label name="campaign.clickToActivate">Click to Activate</dhv:label></font></a>.
          </dhv:evaluate>
          <dhv:evaluate if="<%= Campaign.getDeliveryType() ==  Notification.BROADCAST %>">
          <a href="javascript:popURL('CampaignManager.do?command=BroadcastAuthenticationForm&id=<%= Campaign.getId() %>','Broadcast_Authentication','400','150');"><font color="red"><dhv:label name="campaign.clickToActivate">Click to Activate</dhv:label></font></a>.
          </dhv:evaluate>
        </center>
      </td>
    </tr>
  </table>
  &nbsp;
  </dhv:permission>
<%
  } else {
%>  
  <dhv:permission name="campaign-campaigns-edit">
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr class="containerBack">
      <td>
        <center>
          <dhv:label name="campaign.canNotActivate.text" param="break=<br />">This campaign is not ready to be activated.<br />Once all of the required items have been selected, the activate button will appear here.</dhv:label>
        </center>
      </td>
    </tr>
  </table>
  &nbsp;
  </dhv:permission>
<%
  }
%>
<br>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2" align="left">
      <strong><dhv:label name="campaign.campaignDetails">Campaign Details</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(Campaign.getDescription()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="campaign.created">Created</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= Campaign.getEnteredBy() %>" />
      <zeroio:tz timestamp="<%= Campaign.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= Campaign.getModifiedBy() %>" />
      <zeroio:tz timestamp="<%= Campaign.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
  </tr>
</table>
&nbsp;<br>
<dhv:permission name="campaign-campaigns-edit">
  <input type="button" value="<dhv:label name="campaign.renameCampaign">Rename Campaign</dhv:label>" onClick="javascript:this.form.action='CampaignManager.do?command=Modify&id=<%= Campaign.getId() %>';submit();">
</dhv:permission>
<dhv:permission name="campaign-campaigns-delete">
  <input type="button" value="<dhv:label name="campaign.deleteCampaign">Delete Campaign</dhv:label>" onClick="javascript:this.form.action='CampaignManager.do?command=Delete&id=<%= Campaign.getId() %>';confirmSubmit(document.modForm);">
</dhv:permission>
  </td>
  </tr>
</table>
</form>

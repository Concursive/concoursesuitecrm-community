<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="fileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<form name="modForm" action="CampaignManager.do?command=Modify&id=<%= Campaign.getId() %>" method="post">
<a href="CampaignManager.do">Communications Manager</a> > 
<a href="CampaignManager.do?command=View">Campaign List</a> >
Campaign Details
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" width="100%" style="border: 1px solid #000;">
  <tr class="containerHeader">
    <td style="border-bottom: 1px solid #000;">
      <strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<ul>
  <li>Select from the following items to build a campaign</li>
  <li>Items can be worked on in any order</li>
  <li>Campaigns will not start until each section is complete, and the campaign has been activated</li>
  <li>Campaign items can be accessed by all users who have access to campaigns</li>
</ul>
      <table cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr>
          <td valign="top" align="center">
            <table cellpadding="4" cellspacing="0" width="100%" class="details">
              <tr>
                <th style="text-align: center;">
                  <strong>Group(s)</strong>
                </th>
              </tr>
              <tr class="containerBody">
                <td style="text-align: center;">
                  <%= (Campaign.hasGroups()?"<font color='green'>" + Campaign.getGroupCount() + " selected</font>":"<font color='red'>No Groups Selected</font>") %><br>
                  &nbsp;<br>
                  <dhv:permission name="campaign-campaigns-edit"><a href="CampaignManager.do?command=AddGroups&id=<%= Campaign.getId() %>">Choose Groups</a><br>&nbsp;</dhv:permission>
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
                  <strong>Message</strong>
                </th>
              </tr>
              <tr class="containerBody">
                <td style="text-align: center;">
                  <%= (Campaign.hasMessage()?"<font color='green'>" + Campaign.getMessageName() + "</font>":"<font color='red'>No Message Selected</font>") %><br>
                  &nbsp;<br>
                  <dhv:permission name="campaign-campaigns-edit"><a href="CampaignManager.do?command=ViewMessage&id=<%= Campaign.getId() %>">Choose Message</a><br>&nbsp;</dhv:permission>
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
                  <strong>Attachments</strong>
                </th>
              </tr>
              <tr class="containerBody">
                <td style="text-align: center;">
                  <dhv:evaluate if="<%= Campaign.hasSurvey() %>">
                    <font color="green">Survey</font><br>
                  </dhv:evaluate>
                  <dhv:evaluate if="<%= fileItemList.size() > 0 %>">
                    <font color="green">Files</font><br>
                  </dhv:evaluate>
                  <dhv:evaluate if="<%= !Campaign.hasSurvey() && fileItemList.size() < 1 %>">
                    None<br>
                    &nbsp;<br>
                  </dhv:evaluate>
                  <dhv:permission name="campaign-campaigns-edit"><a href="CampaignManager.do?command=ViewAttachmentsOverview&id=<%= Campaign.getId() %>">Choose optional<br>attachments</a></dhv:permission>
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
                  <strong>Delivery</strong>
                </th>
              </tr>
              <tr class="containerBody">
                <td style="text-align: center;">
                  <%= (Campaign.hasDetails()?"<font color='green'>Scheduled for " + Campaign.getActiveDateString() + "<br>" + toHtml(Campaign.getDeliveryName()) + "</font><br>":"<font color='red'>Not Scheduled</font><br>&nbsp;<br>") %>
                  <dhv:permission name="campaign-campaigns-view"><a href="CampaignManager.do?command=ViewSchedule&id=<%= Campaign.getId() %>">Choose Options</a><br>&nbsp;</dhv:permission>
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
          This campaign has been configured and can now be activated.<br>
          Once active, today's campaigns will begin processing in under 5 minutes and cannot be cancelled.<br>
          Verify the campaign then
          <a href="javascript:confirmForward('CampaignManager.do?command=Activate&id=<%= Campaign.getId() %>&notify=true&modified=<%= Campaign.getModified() %>');"><font color="red">click to Activate</font></a>.
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
          This campaign is not ready to be activated.<br>
          Once all of the required items have been selected, 
          the activate button will appear here.
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
      <strong>Campaign Details</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      Description
    </td>
    <td>
      <%= toHtmlValue(Campaign.getDescription()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Created
    </td>
    <td>
      <dhv:username id="<%= Campaign.getEnteredBy() %>" /> - <%= Campaign.getEnteredString() %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel">
      Modified
    </td>
    <td>
      <dhv:username id="<%= Campaign.getModifiedBy() %>" /> - <%= Campaign.getModifiedString() %>
    </td>
  </tr>
</table>
&nbsp;<br>
<dhv:permission name="campaign-campaigns-edit">
  <input type="button" value="Rename Campaign" onClick="javascript:this.form.action='CampaignManager.do?command=Modify&id=<%= Campaign.getId() %>';submit();">
</dhv:permission>
<dhv:permission name="campaign-campaigns-delete">
  <input type="button" value="Delete Campaign" onClick="javascript:this.form.action='CampaignManager.do?command=Delete&id=<%= Campaign.getId() %>';confirmSubmit(document.modForm);">
</dhv:permission>
  </td>
  </tr>
</table>
</form>

<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Campaign" class="com.darkhorseventures.cfsbase.Campaign" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<form name="modForm" action="/CampaignManager.do?command=Modify&id=<%= Campaign.getId() %>" method="post">
Communications Manager > 
<a href="/CampaignManager.do?command=View">Campaign List</a> >
Campaign Details
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<ul>
  <li>Select from the following items to build a campaign</li>
  <li>Items can be worked on in any order</li>
  <li>Campaigns will not start until each section is complete, and the campaign has been activated</li>
</ul>
<%--
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign="center" align="left">
      <strong>Campaign Details</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td>
  --%>
      <table cellpadding="0" cellspacing="0" border="0" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr>
          <td valign="top" align="center">
            <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
              <tr class="title">
                <td align="center">
                  <strong>Group(s)</strong>
                </td>
              </tr>
              <tr class="containerBody">
                <td align="center">
                  <%= (Campaign.hasGroups()?"<font color='green'>" + Campaign.getGroupCount() + " selected</font>":"<font color='red'>No Groups Selected</font>") %><br>
                  &nbsp;<br>
                  <dhv:permission name="campaign-campaigns-edit"><a href="/CampaignManager.do?command=AddGroups&id=<%= Campaign.getId() %>">Choose Groups</a><br>&nbsp;</dhv:permission>
                </td>
              </tr>
            </table>
          </td>
          <td>
            &nbsp;
          </td>
          <td valign="top" align="center">
            <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
              <tr class="title">
                <td align="center">
                  <strong>Message</strong>
                </td>
              </tr>
              <tr class="containerBody">
                <td align="center">
                  <%= (Campaign.hasMessage()?"<font color='green'>" + Campaign.getMessageName() + "</font>":"<font color='red'>No Message Selected</font>") %><br>
                  &nbsp;<br>
                  <dhv:permission name="campaign-campaigns-edit"><a href="/CampaignManager.do?command=ViewMessage&id=<%= Campaign.getId() %>">Choose Message</a><br>&nbsp;</dhv:permission>
                </td>
              </tr>
            </table>
          </td>
          <td>
            &nbsp;
          </td>
          <td valign="top" align="center">
            <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
              <tr class="title">
                <td align="center">
                  <strong>Attachments</strong>
                </td>
              </tr>
              <tr class="containerBody">
                <td align="center">
                  <%= (Campaign.hasSurvey()?"<font color='green'>Survey</font>":"None") %><br>
                  &nbsp;<br>
                  <dhv:permission name="campaign-campaigns-edit"><a href="/CampaignManager.do?command=ViewAttachment&id=<%= Campaign.getId() %>">Choose optional<br>attachments</a></dhv:permission>
                </td>
              </tr>
            </table>
          </td>
          <td>
            &nbsp;
          </td>
          <td valign="top" align="center">
            <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
              <tr class="title">
                <td align="center">
                  <strong>Delivery</strong>
                </td>
              </tr>
              <tr class="containerBody">
                <td align="center">
                  <%= (Campaign.hasDetails()?"<font color='green'>Scheduled for " + Campaign.getActiveDateString() + "<br>" + toHtml(Campaign.getDeliveryName()) + "</font><br>":"<font color='red'>Not Scheduled</font><br>&nbsp;<br>") %>
                  <dhv:permission name="campaign-campaigns-view"><a href="/CampaignManager.do?command=ViewSchedule&id=<%= Campaign.getId() %>">Choose Options</a><br>&nbsp;</dhv:permission>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
  <%--
    </td>
  </tr>
</table>
  --%>
&nbsp;

<%  
  if (Campaign.isReadyToActivate()) {
%>  
  <dhv:permission name="campaign-campaigns-edit">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="containerBack">
      <td class="formLabel">
        <center>
          This campaign has been configured and can now be activated.<br>
          Once active, today's campaigns will begin processing in under 5 minutes and cannot be cancelled.<br>
          Verify the campaign then
          <a href="javascript:confirmForward('/CampaignManager.do?command=Activate&id=<%= Campaign.getId() %>&notify=true&modified=<%= Campaign.getModified() %>');"><font color="red">click to Activate</font></a>.
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

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2" align="left">
      <strong>Campaign Details</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      Description
    </td>
    <td width="100%">
      <%= toHtmlValue(Campaign.getDescription()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Created
    </td>
    <td width="100%">
      <dhv:username id="<%= Campaign.getEnteredBy() %>" /> - <%= Campaign.getEnteredString() %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel">
      Modified
    </td>
    <td width="100%">
      <dhv:username id="<%= Campaign.getModifiedBy() %>" /> - <%= Campaign.getModifiedString() %>
    </td>
  </tr>
</table>
&nbsp;<br>


<dhv:permission name="campaign-campaigns-edit">
  <input type="button" value="Modify Campaign Details" onClick="javascript:this.form.action='/CampaignManager.do?command=Modify&id=<%= Campaign.getId() %>';submit();">
</dhv:permission>
<dhv:permission name="campaign-campaigns-delete">
  <input type="button" value="Delete Campaign" onClick="javascript:this.form.action='/CampaignManager.do?command=Delete&id=<%= Campaign.getId() %>';confirmSubmit(document.modForm);">
</dhv:permission>
  </td>
  </tr>
</table>
</form>

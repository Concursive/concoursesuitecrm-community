<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="ActiveSurvey" class="org.aspcfs.modules.communications.base.ActiveSurvey" scope="request"/>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do">Communications</a> >
<a href="CampaignManager.do?command=Dashboard">Dashboard</a> >
Campaign Details
</td>
</tr>
</table>
<%-- End Trails --%>
<strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
<% String param1="id=" + Campaign.getId(); %>
<dhv:container name="communications" selected="details" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td class="containerBack">
      <table cellpadding="4" cellspacing="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong>Campaign Details </strong>
          </th>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" valign="top">
            Description
          </td>
          <td>
            <%= toHtml(Campaign.getDescription()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Groups
          </td>
          <td>
            <font color="green"><%= Campaign.getGroupCount() %> selected</font>
          </td>
        </tr>
        <tr class="containerBody">
          <td valign="top" class="formLabel">
            Message
          </td>
          <td>
            <font color="green"><%= toHtml(Campaign.getMessageName()) %></font>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Schedule
          </td>
          <td>
            <font color="green">Scheduled to run on <%= Campaign.getActiveDateString() %></font>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Delivery
          </td>
          <td>
            <%= (Campaign.hasDetails()?"<font color='green'>" + toHtml(Campaign.getDeliveryName())  + "</font>":"<font color='red'>Not Specified</font>") %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Entered
          </td>
          <td>
            <dhv:username id="<%= Campaign.getEnteredBy() %>" />
            -
            <dhv:tz timestamp="<%= Campaign.getEntered() %>"  dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Modified
          </td>
          <td>
            <dhv:username id="<%= Campaign.getModifiedBy() %>" />
            -
            <dhv:tz timestamp="<%= Campaign.getModified() %>"  dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

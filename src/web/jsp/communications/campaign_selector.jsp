<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.communications.base.*,com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.utils.CurrencyFormat" %>
<jsp:useBean id="campaignList" class="org.aspcfs.modules.communications.base.CampaignList" scope="request"/>
<jsp:useBean id="campaignSelectorListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="siteId" class="java.lang.String" scope="request"/>
<jsp:useBean id="displayFieldId" class="java.lang.String" scope="request"/>
<jsp:useBean id="hiddenFieldId" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popOpportunities.js"></SCRIPT>
<script language="javascript">
  function submitPage(headerId, displayValue, hiddenFieldId, displayFieldId) {
    opener.changeDivContent(displayFieldId, displayValue);
    opener.setParentHiddenField(hiddenFieldId, headerId);
    self.close();
  }
</script>
&nbsp;<br />
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="campaignSelectorListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
	<tr>
    <th width="8" align="left" nowrap>
      &nbsp;
    </th>
    <th align="left" nowrap>
      <a href="CampaignSelector.do?command=List&displayFieldId=<%= displayFieldId %>&hiddenFieldId=<%= hiddenFieldId %>&column=c.name<%= siteId != null?"&siteId="+siteId:"" %>"><strong><dhv:label name="contacts.name">Name</dhv:label></strong></a>
      <%= campaignSelectorListInfo.getSortIcon("c.name") %>
    </th>
    <th align="left" nowrap>
      <a href="CampaignSelector.do?command=List&displayFieldId=<%= displayFieldId %>&hiddenFieldId=<%= hiddenFieldId %>&column=c.active_date<%= siteId != null?"&siteId="+siteId:"" %>"><strong><dhv:label name="documents.details.startDate">Start Date</dhv:label></strong></a>
      <%= campaignSelectorListInfo.getSortIcon("c.active_date") %>
    </th>
    <th align="left" nowrap>
      <strong><dhv:label name="campaign.numberOfRecipients.symbol"># Recipients</dhv:label></strong>
    </th>
    <th align="left" nowrap>
      <a href="CampaignSelector.do?command=List&displayFieldId=<%= displayFieldId %>&hiddenFieldId=<%= hiddenFieldId %>&column=status<%= siteId != null?"&siteId="+siteId:"" %>"><strong><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></strong></a>
      <%= campaignSelectorListInfo.getSortIcon("status") %>
    </th>
    <th align="left" nowrap>
      <a href="CampaignSelector.do?command=List&displayFieldId=<%= displayFieldId %>&hiddenFieldId=<%= hiddenFieldId %>&column=active<%= siteId != null?"&siteId="+siteId:"" %>"><strong><dhv:label name="campaign.active.question">Active?</dhv:label></strong></a>
      <%= campaignSelectorListInfo.getSortIcon("active") %>
    </th>
	<%
	Iterator j = campaignList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    int count = 0;
    while (j.hasNext()) {
      count++;
      rowid = (rowid != 1?1:2);
      Campaign campaign = (Campaign)j.next();
	%>
  <tr class="row<%= rowid %>">
    <td valign="center" nowrap>
      <a href="javascript:submitPage('<%= campaign.getId() %>', '<%= toHtml(campaign.getName()) %>', '<%= request.getAttribute("hiddenFieldId") %>','<%= request.getAttribute("displayFieldId") %>');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>
    </td>
    <td valign="center" width="100%"><%= toHtml(campaign.getName()) %></td>
    <td valign="top" align="center" nowrap>
      <% if(!User.getTimeZone().equals(campaign.getActiveDateTimeZone())){%>
        <zeroio:tz timestamp="<%= campaign.getActiveDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } else { %>
        <zeroio:tz timestamp="<%= campaign.getActiveDate() %>" dateOnly="true" timeZone="<%= campaign.getActiveDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } %>
    </td>
    <td valign="top" align="center" nowrap>
      <%= campaign.getRecipientCount() %>
    </td>
    <td valign="top" nowrap>
      <%= toHtml(campaign.getStatus()) %>
    </td>
    <td valign="top" align="center" nowrap>
      <%= toHtml(campaign.getActiveYesNo()) %>
    </td>
	</tr>
	<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="6">
      <dhv:label name="campaign.noRunningCampaignsFound">No running campaigns found.</dhv:label>
    </td>
  </tr>
<%}%>
</table>
<br>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();"/>
<dhv:pagedListControl object="campaignSelectorListInfo" tdClass="row1"/>


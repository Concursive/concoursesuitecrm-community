<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Campaign" class="com.darkhorseventures.cfsbase.Campaign" scope="request"/>
<jsp:useBean id="Survey" class="com.darkhorseventures.cfsbase.Survey" scope="request"/>
<jsp:useBean id="RecipientList" class="com.darkhorseventures.cfsbase.RecipientList" scope="request"/>
<jsp:useBean id="CampaignDashboardRecipientInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<a href="/CampaignManager.do?command=Dashboard">Back to Dashboard</a>
<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2" valign="center" align="left">
      <strong>Campaign Details</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Name
    </td>
    <td width="100%">
      <%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Description
    </td>
    <td width="100%">
      <%= toHtml(Campaign.getDescription()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Groups
    </td>
    <td width="100%">
      <font color='green'><%= Campaign.getGroupCount() %> selected</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Message
    </td>
    <td width="100%">
      <font color='green'><%= Campaign.getMessageName() %></font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Schedule
    </td>
    <td width="100%">
      <font color='green'>Scheduled to run on <%= Campaign.getActiveDateString() %></font>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel">
      Delivery
    </td>
    <td width="100%">
      <%= (Campaign.hasDetails()?"<font color='green'>" + toHtml(Campaign.getDeliveryName())  + "</font>":"<font color='red'>Not Specified</font>") %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel">
      Entered
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

<% if (Survey != null && Campaign.getSurveyId() > 0) { %>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="10" valign="center" align="left">
      <strong>Survey Results</strong>
    </td>     
  </tr>
  
  <tr class="title">
    <td width="24" align=right nowrap>Count</td>
    <td>Text</td>
    <td width="24" nowrap>Avg.</td>
    <td width="24" nowrap >1</td>
    <td width="24" nowrap >2</td>
    <td width="24" nowrap >3</td>
    <td width="24" nowrap >4</td>
    <td width="24" nowrap >5</td>
    <td width="24" nowrap >6</td>
    <td width="24" nowrap >7</td>
  </tr>
  <%
	Iterator z = Survey.getItems().iterator();
	
	if ( z.hasNext() ) {
		int rowid = 0;
		int count = 0;
	
		while (z.hasNext()) {
			count++;		
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
	
		SurveyItem thisItem = (SurveyItem)z.next();
		
  %>
  
  <tr>
    <td align=right nowrap><%=count%></td>
    <td><%= toHtml(thisItem.getDescription()) %></td>
    <td width="24" nowrap><%= toHtml(thisItem.getAverageValue()) %></td>
    <td width="24" nowrap ><%= toHtml(thisItem.getResponseTotals().get(0) + "")%></td>
    <td width="24" nowrap ><%= toHtml(thisItem.getResponseTotals().get(1) + "")%></td>
    <td width="24" nowrap ><%= toHtml(thisItem.getResponseTotals().get(2) + "")%></td>
    <td width="24" nowrap ><%= toHtml(thisItem.getResponseTotals().get(3) + "")%></td>
    <td width="24" nowrap ><%= toHtml(thisItem.getResponseTotals().get(4) + "")%></td>
    <td width="24" nowrap ><%= toHtml(thisItem.getResponseTotals().get(5) + "")%></td>
    <td width="24" nowrap ><%= toHtml(thisItem.getResponseTotals().get(6) + "")%></td>
  </tr>

	<%	}
	}%>


</table>




<%}%>

&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="6" valign="center" align="left">
      <strong>List of Recipients</strong>
    </td>     
  </tr>
  <tr class="title">
    <td width="24" align=right nowrap>
      Count
    </td>
    <td>
      Name
    </td>
    <td width=100%>
      Company
    </td>
    <td valign=center width="102" nowrap>
      Sent Date
    </td>
    <!--td valign=center width="49" nowrap>
      Reply Date
    </td-->
    <td width="102" nowrap>
      Status
    </td>
  </tr>
<%
	Iterator j = RecipientList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
		int count = CampaignDashboardRecipientInfo.getCurrentOffset();
		
	  while (j.hasNext()) {
			count++;		
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
		
    Recipient thisRecipient = (Recipient)j.next();
		Contact thisContact = thisRecipient.getContact();
%>      
  <tr>
    <td align=right nowrap>
      <%= count %>
    </td>
    <td nowrap>
      <%= toHtml(thisContact.getNameLast()) %>, <%= toHtml(thisContact.getNameFirst()) %>
    </td>
    <td width=100% nowrap>
      <%= toHtml(thisContact.getCompany()) %>
    </td>
    <td width="102" nowrap>
      <%= toHtml(thisRecipient.getSentDateString()) %>
    </td>
    <!--td width="100" nowrap>
      <%= toHtml(thisRecipient.getReplyDateString()) %>
    </td-->
    <td width="102" nowrap>
      <%= toHtml(thisRecipient.getStatus()) %>
    </td>
  </tr>
  <%}%>
</table>
<br>
<dhv:pagedListControl object="CampaignDashboardRecipientInfo" tdClass="row1"/>
<%} else {%>
  <tr bgcolor="white">
    <td colspan="6" valign="center">
      No recipients found.
    </td>
  </tr>
</table>
<%}%>

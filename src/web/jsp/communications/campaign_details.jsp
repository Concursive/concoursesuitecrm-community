<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Campaign" class="com.darkhorseventures.cfsbase.Campaign" scope="request"/>
<jsp:useBean id="ActiveSurvey" class="com.darkhorseventures.cfsbase.ActiveSurvey" scope="request"/>
<jsp:useBean id="RecipientList" class="com.darkhorseventures.cfsbase.RecipientList" scope="request"/>
<jsp:useBean id="CampaignDashboardRecipientInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<script language="JavaScript" type="text/javascript" src="/javascript/popURL.js"></script>
<%@ include file="initPage.jsp" %>
<a href="CampaignManager.do">Communications Manager</a> >
<a href="/CampaignManager.do?command=Dashboard">Dashboard</a> >
Campaign Details
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2" valign="center" align="left">
      <strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
    </td>     
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
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
    <td valign="top" class="formLabel">
      Message
    </td>
    <td width="100%">
      <font color='green'><%= toHtml(Campaign.getMessage()) %></font>
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
      <dhv:username id="<%= Campaign.getEnteredBy() %>" /> - <%= toHtml(Campaign.getEnteredString()) %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel">
      Modified
    </td>
    <td width="100%">
      <dhv:username id="<%= Campaign.getModifiedBy() %>" /> - <%= toHtml(Campaign.getModifiedString()) %>
    </td>
  </tr>
</table>

<% if (Campaign.getActiveSurveyId() > 0) { %>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="10" valign="center" align="left">
      <strong>Survey Results</strong>
    </td>     
  </tr>
  
  <tr class="title">
    <td width="24" align=right nowrap>&nbsp;&nbsp;Item</td>
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
	Iterator z = ActiveSurvey.getQuestions().iterator();
	
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
      ActiveSurveyQuestion thisItem = (ActiveSurveyQuestion)z.next();
%>
  <tr>
    <td align=right nowrap><%=count%></td>
    <td><%= toHtml(thisItem.getDescription()) %></td>
    
    <% if(! (thisItem.getType() == 4)){ %>
    
    <td width="24" <%=thisItem.getType() == 1?" colspan=\"8\"":""%> align="center" nowrap>
<% 
      if (thisItem.getType() == 3 || thisItem.getType() == 1) {
%>
    <a href="javascript:popURLReturn('/CampaignManager.do?command=ShowComments&surveyId=<%=ActiveSurvey.getId()%>&questionId=<%=thisItem.getId()%>&type=<%=thisItem.getType()==1?"open":"quant"%>&popup=true','CampaignManager.do?command=Details&reset=true','Survey_Comments','500','240','yes','no');">
<%
      }
%>
    <%=thisItem.getType()==1?"View Comments":toHtml(thisItem.getAverageValue())%>
<% 
      if (ActiveSurvey.getType() == 3 || thisItem.getType() == 1) {
%>
    </a>
<%
      }
%>
    </td>
<%  
      if (thisItem.getType() != 1) { 

%>
    <td width="24" nowrap ><%= toHtml(String.valueOf(thisItem.getResponseTotals().get(0)))%></td>
    <td width="24" nowrap ><%= toHtml(thisItem.getResponseTotals().get(1) + "")%></td>
    <td width="24" nowrap ><%= toHtml(thisItem.getResponseTotals().get(2) + "")%></td>
    <td width="24" nowrap ><%= toHtml(thisItem.getResponseTotals().get(3) + "")%></td>
    <td width="24" nowrap ><%= toHtml(thisItem.getResponseTotals().get(4) + "")%></td>
    <td width="24" nowrap ><%= toHtml(thisItem.getResponseTotals().get(5) + "")%></td>
    <td width="24" nowrap ><%= toHtml(thisItem.getResponseTotals().get(6) + "")%></td>
<%
    }
%>
    <%}else{%>
    
    <td colspan="8" align="center">
      <a href="javascript:popURLReturn('/CampaignManager.do?command=ShowItems&questionId=<%=thisItem.getId()%>&popup=true','CampaignManager.do?command=Details&reset=true','Survey_Items','600','450','yes','no');">View Item Details</a>
    </td>
    
    <%}%>
  </tr>

<%	
    }
	}
%>
</table>

<%
}
%>
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
      <%= toHtml(thisContact.getNameLastFirst()) %>
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

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="RecipientList" class="org.aspcfs.modules.communications.base.RecipientList" scope="request"/>
<jsp:useBean id="CampaignDashboardRecipientInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<a href="CampaignManager.do">Communications Manager</a> >
<a href="CampaignManager.do?command=Dashboard">Dashboard</a> >
<a href="CampaignManager.do?command=Details&id=<%=Campaign.getId()%>">Campaign Details</a> >
Recipients
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td colspan="2" valign="center" align="left">
      <strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr class="containerMenu">
    <td colspan="2">
      <% String param1 = "id=" + Campaign.getId(); %>
      <dhv:container name="communications" selected="recipients" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
    <center><%= CampaignDashboardRecipientInfo.getAlphabeticalPageLinks() %></center>
    <dhv:pagedListStatus title="<%= showAttribute(request, "actionError") %>" object="CampaignDashboardRecipientInfo"/>
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
	  <tr class="row<%= rowid %>">
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
	    <td width="102" nowrap>
	      <%= toHtml(thisRecipient.getStatus()) %>
	    </td>
	  </tr>
	  <%}%>
	</table>
	<br>
	<dhv:pagedListControl object="CampaignDashboardRecipientInfo" />
	<%} else {%>
	  <tr bgcolor="white">
	    <td colspan="6" valign="center">
	      No recipients found.
	    </td>
	  </tr>
	</table>
	<%}%>
     </td>	
   </tr>
</table>

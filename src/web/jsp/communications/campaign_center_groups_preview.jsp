<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Campaign" class="com.darkhorseventures.cfsbase.Campaign" scope="request"/>
<jsp:useBean id="SCL" class="com.darkhorseventures.cfsbase.SearchCriteriaList" scope="request"/>
<jsp:useBean id="ContactList" class="com.darkhorseventures.cfsbase.ContactList" scope="request"/>
<jsp:useBean id="CampaignCenterPreviewInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<a href="/CampaignManager.do?command=View">Back to Campaign List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(Campaign.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + Campaign.getId(); %>      
      <dhv:container name="communications" selected="message" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<a href="/CampaignManager.do?command=ViewGroups&id=<%= Campaign.getId() %>">Back to Group List</a>
<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan="4" valign="center" align="left">
      <strong>List of Matching Contacts</strong>
    </td>     
  </tr>
  <tr class="title">
    <td width="8" align="right" nowrap>
      Count
    </td>
    <td width="50%" nowrap>
      Name
    </td>
    <td width="50%" nowrap>
      Company
    </td>
    <td align="center">
      Included
    </td>
  </tr>
<%
	Iterator j = ContactList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
		int count = CampaignCenterPreviewInfo.getCurrentOffset();
		
	  while (j.hasNext()) {
			count++;		
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
		
		Contact thisContact = (Contact)j.next();
%>      
  <tr class="containerBody">
    <td align="right" nowrap>
      <%= count %>
    </td>
    <td nowrap>
      <%= toHtml(thisContact.getNameLast()) %>, <%= toHtml(thisContact.getNameFirst()) %>
    </td>
    <td nowrap>
      <%= toHtml(thisContact.getCompany()) %>
    </td>
    <td align="center" nowrap>
      <dhv:permission name="campaign-campaigns-edit"><a href="/CampaignManager.do?command=ToggleRecipient&scl=<%=SCL.getId()%>&id=<%= Campaign.getId() %>&contactId=<%= thisContact.getId()%>"></dhv:permission><%= (thisContact.excludedFromCampaign()? "No" : "Yes") %><dhv:permission name="campaign-campaigns-edit"></a></dhv:permission>
    </td>
  </tr>
  <%}%>
</table>
<br>
<dhv:pagedListControl object="CampaignCenterPreviewInfo" tdClass="row1"/>
<%} else {%>
  <tr class="containerBody">
    <td colspan="4" valign="center">
      No contacts matched query.
    </td>
  </tr>
</table>
<%}%>
  </td>
  </tr>
</table>

<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="MessageList" class="com.darkhorseventures.cfsbase.MessageList" scope="request"/>
<jsp:useBean id="CampaignMessageListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<form name="listView" method="post" action="/CampaignManagerMessage.do?command=View">
<a href="/CampaignManagerMessage.do?command=Add">Add a Message</a>
<center><%= CampaignMessageListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= CampaignMessageListInfo.getOptionValue("my") %>>My Messages</option>
        <option <%= CampaignMessageListInfo.getOptionValue("all") %>>All Messages</option>
      </select>
      <%= showAttribute(request, "actionError") %>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
   <td valign=center align=left bgcolor="#DEE0FA">
      <strong>Action</strong>
    </td>
    <td width=40% valign=center align=left>
      <a href="/CampaignManagerMessage.do?command=View&column=name"><strong>Name</strong></a>
    </td>  
    <td width=60% valign=center align=left>
      <a href="/CampaignManagerMessage.do?command=View&column=description"><strong>Description</strong></a>
    </td>
    <td valign=center align=left nowrap>
      <a href="/CampaignManagerMessage.do?command=View&column=enteredby"><strong>Entered By</strong></a>
    </td>
    <td valign=center align=left>
      <a href="/CampaignManagerMessage.do?command=View&column=modified"><strong>Last Modified</strong></a>
    </td>
  </tr>
<%
	Iterator j = MessageList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
	       	while (j.hasNext()) {
		
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
		
		Message thisMessage = (Message)j.next();
%>      
  <tr class="containerBody">
    <td width=8 valign=center nowrap align="center" class="row<%= rowid %>">
      <a href="/CampaignManagerMessage.do?command=Modify&id=<%=thisMessage.getId()%>&return=list">Edit</a>|<a href="javascript:confirmDelete('/CampaignManagerMessage.do?command=Delete&id=<%=thisMessage.getId()%>');">Del</a>
    </td>
		<td width=40% valign=center class="row<%= rowid %>">
      <a href="/CampaignManagerMessage.do?command=Details&id=<%=thisMessage.getId()%>"><%= toHtml(thisMessage.getName()) %></a>
		</td>
		<td width=60% valign=center class="row<%= rowid %>">
      <%= toHtml(thisMessage.getDescription()) %>
    </td>
    <td valign="center" align="left" class="row<%= rowid %>" nowrap>
      <dhv:username id="<%= thisMessage.getEnteredBy() %>" />
    </td>
    <td valign="center" align="right" class="row<%= rowid %>" nowrap>
      <%= toHtml(thisMessage.getModifiedDateTimeString()) %>
    </td>
  </tr>
<%}%>
</table>
<br>
[<%= CampaignMessageListInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %> <%= CampaignMessageListInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>] <%= CampaignMessageListInfo.getNumericalPageLinks() %>

<%} else {%>
  <tr class="containerBody">
    <td colspan="5" valign="center">
      No messages found.
    </td>
  </tr>
</table>
<%}%>
</form>

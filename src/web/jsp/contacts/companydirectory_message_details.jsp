<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Campaign" class="com.darkhorseventures.cfsbase.Campaign" scope="request"/>
<jsp:useBean id="ContactDetails" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<jsp:useBean id="Message" class="com.darkhorseventures.cfsbase.Message" scope="request"/>
<%@ include file="initPage.jsp" %>
<a href="/ExternalContacts.do?command=ViewMessages&contactId=<%= ContactDetails.getId() %>">Back to Message List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <a href="/ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>"><font color="#000000">Details</font></a> | 
      <a href="/ExternalContacts.do?command=Fields&contactId=<%= ContactDetails.getId() %>"><font color="#000000">Folders</font></a> | 
      <a href="/ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %>"><font color="#000000">Calls</font></a> |
      <a href="/ExternalContacts.do?command=ViewMessages&contactId=<%= ContactDetails.getId() %>"><font color="#0000FF">Messages</font></a> |
      <a href="/ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>"><font color="#000000">Opportunities</font></a> 
    </td>
  </tr>
  <tr>
    <td class="containerBack">
    <br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
      <strong>Selected message</strong>
    </td>
  </tr>
	<tr class="containerBody">
    <td class="formLabel">
      Campaign
    </td>
    <td width="100%">
			<%=toHtml(Campaign.getName())%>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Message Subject
    </td>
    <td width="100%">
			<%=toHtml(Campaign.getMessageName())%>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      Message Text
    </td>
    <td valign=center>
			<%= (Message.getMessageText()) %>&nbsp; 
    </td>
  </tr>
</table>
<br>
</table>

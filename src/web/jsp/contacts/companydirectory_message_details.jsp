<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.Campaign" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<%@ include file="../initPage.jsp" %>
<a href="/ExternalContacts.do">Contacts &amp; Resources</a> > 
<a href="/ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="/ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
<a href="/ExternalContacts.do?command=ViewMessages&contactId=<%=ContactDetails.getId()%>">Messages</a> >
Message Details
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + ContactDetails.getId(); %>      
      <dhv:container name="contacts" selected="messages" param="<%= param1 %>" />
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
      Reply To
    </td>
    <td width="100%">
			<%=toHtml(Campaign.getReplyTo())%>
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
			<%= (Campaign.getMessage()) %>&nbsp; 
    </td>
  </tr>
</table>
<br>
</table>

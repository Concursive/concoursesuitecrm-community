<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<form name="addCall" action="ExternalContactsCalls.do?id=<%= CallDetails.getId() %>&contactId=<%= ContactDetails.getId() %>" method="post">
<dhv:evaluate exp="<%= !isPopup(request) %>">
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
<a href="ExternalContactsCalls.do?command=View&contactId=<%=ContactDetails.getId()%>">Calls</a> >
Call Details
<hr color="#BFBFBB" noshade>
</dhv:evaluate>
<%@ include file="contact_details_header_include.jsp" %>
<% String param1 = "id=" + ContactDetails.getId(); 
   String param2 = addLinkParams(request, "popup|popupType|actionId"); %>
<dhv:container name="contacts" selected="calls" param="<%= param1 %>" appendToUrl="<%= param2 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<dhv:permission name="contacts-external_contacts-calls-edit"><input type="submit" name="command" value="Modify"></dhv:permission>
<dhv:permission name="contacts-external_contacts-calls-delete"><input type="button" value="Delete" onClick="javascript:popURL('ExternalContactsCalls.do?command=ConfirmDelete&id=<%= CallDetails.getId() %>&contactId=<%= ContactDetails.getId()%><%= isPopup(request) ? "" : "&popup=true" %><%= addLinkParams(request, "popup|popupType|actionId") %>', 'CONFIRM_DELETE','320','200','yes','no');"></dhv:permission>
<dhv:permission name="myhomepage-inbox-view"><input type="button" name="action" value="Forward" onClick="javascript:window.location.href='ExternalContactsCallsForward.do?command=ForwardMessage&forwardType=<%= Constants.CONTACTS_CALLS %>&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %>'"></dhv:permission>
<dhv:permission name="contacts-external_contacts-calls-edit,contacts-external_contacts-calls-delete"><br>&nbsp;</dhv:permission>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Call Details</strong>  
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Type
    </td>
    <td>
      <%= toHtml(CallDetails.getCallType()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Length
    </td>
    <td>
      <%= toHtml(CallDetails.getLengthText()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Subject
    </td>
    <td>
      <%= toHtml(CallDetails.getSubject()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Notes
    </td>
    <td>
      <%= toHtml(CallDetails.getNotes()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Alert Description
    </td>
    <td>
      <%= toHtml(CallDetails.getAlertText()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Alert Date
    </td>
    <td>
      <%= toHtml(CallDetails.getAlertDateString()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Entered
    </td>
    <td>
      <%= toHtml(CallDetails.getEnteredName()) %>&nbsp;-&nbsp;<%= toHtml(CallDetails.getEnteredString()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Modified
    </td>
    <td>
      <%= toHtml(CallDetails.getModifiedName()) %>&nbsp;-&nbsp;<%= toHtml(CallDetails.getModifiedString()) %>
    </td>
  </tr>
</table>
<dhv:permission name="contacts-external_contacts-calls-edit,contacts-external_contacts-calls-delete"><br></dhv:permission>
<dhv:permission name="contacts-external_contacts-calls-edit"><input type="submit" name="command" value="Modify"></dhv:permission>
<dhv:permission name="contacts-external_contacts-calls-delete"><input type="button" value="Delete" onClick="javascript:popURL('ExternalContactsCalls.do?command=ConfirmDelete&id=<%= CallDetails.getId() %>&contactId=<%= ContactDetails.getId()%><%= isPopup(request) ? "" : "&popup=true" %><%= addLinkParams(request, "popup|popupType|actionId") %>', 'CONFIRM_DELETE','320','200','yes','no');"></dhv:permission>
<dhv:permission name="myhomepage-inbox-view"><input type="button" name="action" value="Forward" onClick="javascript:window.location.href='ExternalContactsCallsForward.do?command=ForwardMessage&forwardType=<%= Constants.CONTACTS_CALLS %>&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %>&return='+escape('ExternalContactsCalls.do?command=Details&id=<%= CallDetails.getId() %>&contactId=<%= ContactDetails.getId() %>') + '&sendUrl=' + escape('ExternalContactsCallsForward.do?command=SendMessage&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %>');"></dhv:permission>
</td>
</tr>
</table>
<%= addHiddenParams(request, "popup|popupType|actionId") %>
</form>

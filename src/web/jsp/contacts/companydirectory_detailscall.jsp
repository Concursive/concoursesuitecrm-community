<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="ContactDetails" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<jsp:useBean id="CallDetails" class="com.darkhorseventures.cfsbase.Call" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="/javascript/popURL.js"></script>
<form name="addCall" action="/ExternalContactsCalls.do?id=<%= CallDetails.getId() %>&contactId=<%= ContactDetails.getId() %>" method="post">
<a href="/ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %>">Back to Call List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + ContactDetails.getId(); %>      
      <dhv:container name="contacts" selected="calls" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
    
<dhv:permission name="contacts-external_contacts-calls-edit"><input type="submit" name="command" value="Modify"></dhv:permission>
<dhv:permission name="contacts-external_contacts-calls-delete"><input type="submit" name="command" value="Delete" onClick="javascript:return confirmAction()"></dhv:permission>
<dhv:permission name="myhomepage-inbox-view"><input type="button" name="action" value="Forward" onClick="javascript:window.location.href='ExternalContactsCallsForward.do?command=ForwardMessage&popup=true&forwardType=8&id=<%=CallDetails.getId()%>&return='+escape('ExternalContactsCalls.do?command=Details&id=<%=CallDetails.getId()%>&contactId=<%=ContactDetails.getId()%>')+'&sendUrl=ExternalContactsCallsForward.do?command=SendMessage';"></dhv:permission>
<dhv:permission name="contacts-external_contacts-calls-edit,contacts-external_contacts-calls-delete"><br>&nbsp;</dhv:permission>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Call Details</strong>  
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Type
    </td>
    <td width=100%>
      <%= toHtml(CallDetails.getCallType()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Length
    </td>
    <td>
      <%= toHtml(CallDetails.getLengthText()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Subject
    </td>
    <td>
      <%= toHtml(CallDetails.getSubject()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" width=100%>
      Notes
    </td>
    <td>
      <%= toHtml(CallDetails.getNotes()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" width=100%>
      Alert Date
    </td>
    <td>
      <%= toHtml(CallDetails.getAlertDateString()) %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Entered
    </td>
    <td>
      <%= toHtml(CallDetails.getEnteredName()) %>&nbsp;-&nbsp;<%= toHtml(CallDetails.getEnteredString()) %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Modified
    </td>
    <td>
      <%= toHtml(CallDetails.getModifiedName()) %>&nbsp;-&nbsp;<%= toHtml(CallDetails.getModifiedString()) %>
    </td>
  </tr>
</table>
<dhv:permission name="contacts-external_contacts-calls-edit,contacts-external_contacts-calls-delete"><br></dhv:permission>
<dhv:permission name="contacts-external_contacts-calls-edit"><input type="submit" name="command" value="Modify"></dhv:permission>
<dhv:permission name="contacts-external_contacts-calls-delete"><input type="submit" name="command" value="Delete" onClick="javascript:return confirmAction()"></dhv:permission>
<dhv:permission name="myhomepage-inbox-view"><input type="button" name="action" value="Forward" onClick="javascript:window.location.href='ExternalContactsCallsForward.do?command=ForwardMessage&popup=true&forwardType=8&id=<%=CallDetails.getId()%>&return='+escape('ExternalContactsCalls.do?command=Details&id=<%=CallDetails.getId()%>&contactId=<%=ContactDetails.getId()%>');"></dhv:permission>
</td>
</tr>
</table>

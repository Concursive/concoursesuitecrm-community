<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Contacts</a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Contact Details</a> >
<a href="AccountContactsCalls.do?command=View&contactId=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Calls</a> >
Call Details
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="contacts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<form name="addCall" action="AccountContactsCalls.do?command=Modify&id=<%= CallDetails.getId() %>&contactId=<%= ContactDetails.getId() %>" method="post">
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
    <% String param1 = "id=" + ContactDetails.getId(); 
    %>
        <strong><%= ContactDetails.getNameLastFirst() %>:</strong>
        [ <dhv:container name="accountscontacts" selected="calls" param="<%= param1 %>"/> ]
      <br>
      <br>
      <dhv:permission name="accounts-accounts-contacts-calls-edit"><input type="submit"  value="Modify"></dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-calls-delete"><input type="button" value="Delete" onClick="javascript:popURL('AccountContactsCalls.do?command=ConfirmDelete&id=<%= CallDetails.getId() %>&contactId=<%= ContactDetails.getId()%><%= isPopup(request) ? "" : "&popup=true" %><%= addLinkParams(request, "popup|popupType|actionId") %>', 'CONFIRM_DELETE','320','200','yes','no');"></dhv:permission>
      <dhv:evaluate exp="<%= !isPopup(request) %>">
      <dhv:permission name="myhomepage-inbox-view"><input type="button" name="action" value="Forward" onClick="javascript:window.location.href='AccountContactsCalls.do?command=ForwardCall&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'"></dhv:permission>
      </dhv:evaluate>
      <dhv:permission name="accounts-accounts-contacts-calls-edit,accounts-accounts-contacts-calls-delete"><br>&nbsp;</dhv:permission>
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
            <dhv:tz timestamp="<%= CallDetails.getAlertDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Entered
          </td>
          <td>
            <dhv:username id="<%= CallDetails.getEnteredBy() %>"/>
            -
            <dhv:tz timestamp="<%= CallDetails.getEntered() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Modified
          </td>
          <td>
            <dhv:username id="<%= CallDetails.getModifiedBy() %>"/>
            -
            <dhv:tz timestamp="<%= CallDetails.getModified() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
          </td>
        </tr>
      </table><br>
      <dhv:permission name="accounts-accounts-contacts-calls-edit"><input type="submit"  value="Modify"></dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-calls-delete"><input type="button" value="Delete" onClick="javascript:popURL('AccountContactsCalls.do?command=ConfirmDelete&id=<%= CallDetails.getId() %>&contactId=<%= ContactDetails.getId()%><%= isPopup(request) ? "" : "&popup=true" %><%= addLinkParams(request, "popup|popupType|actionId") %>', 'CONFIRM_DELETE','320','200','yes','no');"></dhv:permission>
      <dhv:evaluate exp="<%= !isPopup(request) %>">
      <dhv:permission name="myhomepage-inbox-view"><input type="button" name="action" value="Forward" onClick="javascript:window.location.href='AccountContactsCalls.do?command=ForwardCall&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'"></dhv:permission>
      </dhv:evaluate>
      <dhv:permission name="accounts-accounts-contacts-calls-edit,accounts-accounts-contacts-calls-delete"><br>&nbsp;</dhv:permission>
    </td>
  </tr>
</table>
<%= addHiddenParams(request, "popup|popupType|actionId") %>
</form>

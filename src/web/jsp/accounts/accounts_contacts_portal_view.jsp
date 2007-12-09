<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="portalUserDetails" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<form name="viewContactPortal" action="ContactsPortal.do?command=Modify&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType") %>" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> >
<dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="contacts" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:container name="accountscontacts" selected="portal" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
    <input type="hidden" name="id" value="<%=ContactDetails.getId()%>" />
    <input type="hidden" name="orgId" value="<%=ContactDetails.getOrgId()%>" />
    <input type="hidden" name="userId" value="<%=portalUserDetails.getId()%>">
    <input type="hidden" name="enabled" value="<%=portalUserDetails.getEnabled()%>">
    <% if (ContactDetails.getUserId() == -1 ){ %>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" >
      <tr>
        <td><dhv:label name="account.contact.doesNotHavePortalAccess" param='<%= "contactName="+ContactDetails.getNameLastFirst() %>'><%=ContactDetails.getNameLastFirst()%> does not have portal access</dhv:label></td>
      </tr>
      <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() %>">
        <dhv:permission name="portal-user-add">
          <tr>
            <td><a href="ContactsPortal.do?command=Add&contactId=<%=ContactDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="account.contact.grantPortalAccess">Grant Portal Access</dhv:label></td>
          </tr>
        </dhv:permission>
      </dhv:evaluate>
    </table>
    <%} else if (portalUserDetails.getRoleType() == 0) {%>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" >
      <dhv:evaluate if="<%= !portalUserDetails.getEnabled() %>">
        <tr>
          <td><dhv:label name="account.contact.hasDisabledUserAccount" param='<%= "contactName="+ContactDetails.getNameLastFirst() %>'><%=ContactDetails.getNameLastFirst()%> has a disabled user account</dhv:label></td>
        </tr>
      </dhv:evaluate>
      <dhv:evaluate if="<%= portalUserDetails.getEnabled() %>">
      <tr>
        <td><dhv:label name="account.contact.hasUserAccount" param='<%= "contactName="+ContactDetails.getNameLastFirst() %>'><%=ContactDetails.getNameLastFirst()%> has a user account</dhv:label></td>
      </tr>
      </dhv:evaluate>
    </table>
    <%}else{%>
      <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() %>">
        <dhv:permission name="portal-user-edit">
          <%if (portalUserDetails.getEnabled()){%>
            <input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='ContactsPortal.do?command=Modify&contactId=<%=ContactDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
          <%}else{%>
            <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" onClick="javascript:window.location.href='ContactsPortal.do?command=Enable&userId=<%=portalUserDetails.getId()%>&contactId=<%=ContactDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
          <%}%>
        </dhv:permission>
        <dhv:permission name="portal-user-delete">
          <dhv:evaluate if="<%= (portalUserDetails.getEnabled()) %>" >
            <input type="button" value="<dhv:label name="global.button.Disable">Disable</dhv:label>" onClick="javascript:window.location.href='ContactsPortal.do?command=Disable&userId=<%=portalUserDetails.getId()%>&contactId=<%=ContactDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
          </dhv:evaluate>
        </dhv:permission>
      </dhv:evaluate>
      <br /> <br />
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <tr>
        <th colspan="2">
          <strong><dhv:label name="contacts.details">Details</dhv:label></strong>
        </th>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accounts_contacts_portal_view.UserName">User Name</dhv:label>
        </td>
        <td>
          <%=portalUserDetails.getUsername()%>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accounts_contacts_portal_include.PortalRole">Portal Role</dhv:label>
        </td>
        <td>
          <%=portalUserDetails.getRole()%>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label>
        </td>
        <td>
          <%if (portalUserDetails.getEnabled()){%>
          <zeroio:tz timestamp="<%= portalUserDetails.getExpires() %>" dateOnly="true" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
          <%}else{%>
           <dhv:label name="accounts.portalAccessDisabled">Portal access has been disabled for this account contact.</dhv:label>
          <%}%>
        </td>
      </tr>
    </table>
      <br />
      <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() %>">
        <dhv:permission name="portal-user-edit">
          <%if (portalUserDetails.getEnabled()){%>
            <input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='ContactsPortal.do?command=Modify&contactId=<%=ContactDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
          <%}else{%>
            <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" onClick="javascript:window.location.href='ContactsPortal.do?command=Enable&userId=<%=portalUserDetails.getId()%>&contactId=<%=ContactDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
          <%}%>
        </dhv:permission>
        <dhv:permission name="portal-user-delete">
          <dhv:evaluate if="<%= (portalUserDetails.getEnabled()) %>" >
            <input type="button" value="<dhv:label name="global.button.Disable">Disable</dhv:label>" onClick="javascript:window.location.href='ContactsPortal.do?command=Disable&userId=<%=portalUserDetails.getId()%>&contactId=<%=ContactDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
        </dhv:permission>
      </dhv:evaluate>
    <%}%>
  </dhv:container>
</dhv:container>
</form>

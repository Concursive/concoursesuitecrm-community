<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
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
<form name="viewContactPortal" action="ContactsPortal.do?command=Modify&contactId=<%= ContactDetails.getId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Contacts</a> >
Contact Details
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="contacts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
    <% String param1 = "id=" + ContactDetails.getId(); 
    %>
        <strong><%= ContactDetails.getNameLastFirst() %>:</strong>
        [ <dhv:container name="accountscontacts" selected="portal" param="<%= param1 %>"/> ]
      <br/>   <br />
      <input type="hidden" name="id" value="<%=ContactDetails.getId()%>" />
      <input type="hidden" name="orgId" value="<%=ContactDetails.getOrgId()%>" />
      <input type="hidden" name="userId" value="<%=portalUserDetails.getId()%>">
      <input type="hidden" name="enabled" value="<%=portalUserDetails.getEnabled()%>">
<% if (ContactDetails.getUserId() == -1 ){ %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" >
  <tr>
    <td><%=ContactDetails.getNameLastFirst()%> does not have portal access</td>
  </tr>
  <dhv:permission name="portal-user-add">
    <tr>
      <td><a href="ContactsPortal.do?command=Add&contactId=<%=ContactDetails.getId()%>">Grant Portal Access</td>
    </tr>
  </dhv:permission>
</table>
<%}else{%>
  <dhv:permission name="portal-user-edit">
    <%if (portalUserDetails.getEnabled()){%>
      <input type="button" value="Modify" onClick="javascript:window.location.href='ContactsPortal.do?command=Modify&contactId=<%=ContactDetails.getId()%>'" />
    <%}else{%>
      <input type="button" value="Enable" onClick="javascript:window.location.href='ContactsPortal.do?command=Enable&userId=<%=portalUserDetails.getId()%>&contactId=<%=ContactDetails.getId()%>'" />
    <%}%>
  </dhv:permission>
  <dhv:permission name="portal-user-delete">
    <dhv:evaluate if="<%= (portalUserDetails.getEnabled()) %>" >
      <input type="button" value="Disable" onClick="javascript:window.location.href='ContactsPortal.do?command=Disable&userId=<%=portalUserDetails.getId()%>&contactId=<%=ContactDetails.getId()%>'" />
    </dhv:evaluate>
  </dhv:permission>
  <br /> <br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Details</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      User Name
    </td>
    <td>
      <%=portalUserDetails.getUsername()%>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Portal Role
    </td>
    <td>
      <%=portalUserDetails.getRole()%>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Expiration Date
    </td>
    <td>
      <%if (portalUserDetails.getEnabled()){%>
      <zeroio:tz timestamp="<%= portalUserDetails.getExpires() %>" dateOnly="true" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes"/>
      <%}else{%>
       <dhv:label name="accounts.portalAccessDisabled">Portal access has been disabled for this account contact.</dhv:label>
      <%}%>
    </td>
  </tr>
</table>
  <br />
  <dhv:permission name="portal-user-edit">
    <%if (portalUserDetails.getEnabled()){%>
      <input type="button" value="Modify" onClick="javascript:window.location.href='ContactsPortal.do?command=Modify&contactId=<%=ContactDetails.getId()%>'" />
    <%}else{%>
      <input type="button" value="Enable" onClick="javascript:window.location.href='ContactsPortal.do?command=Enable&userId=<%=portalUserDetails.getId()%>&contactId=<%=ContactDetails.getId()%>'" />
    <%}%>
  </dhv:permission>
  <dhv:permission name="portal-user-delete">
    <dhv:evaluate if="<%= (portalUserDetails.getEnabled()) %>" >
      <input type="button" value="Disable" onClick="javascript:window.location.href='ContactsPortal.do?command=Disable&userId=<%=portalUserDetails.getId()%>&contactId=<%=ContactDetails.getId()%>'" />
     </dhv:evaluate>
  </dhv:permission>
<%}%>
  </td>
  </tr>
</table>
</form>

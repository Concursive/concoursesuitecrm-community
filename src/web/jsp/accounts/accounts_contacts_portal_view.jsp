<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="portalUserDetails" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<form name="viewContactPortal" action="ContactsPortal.do?command=Modify&contactId=<%= ContactDetails.getId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
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
      <zeroio:tz timestamp="<%= portalUserDetails.getExpires() %>" dateOnly="true" default="&nbsp;"/>
      <%}else{%>
       Portal access has been disbled for this account contact.
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

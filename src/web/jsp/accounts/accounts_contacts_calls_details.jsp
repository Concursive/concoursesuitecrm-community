<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="ReminderTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CallResult" class="org.aspcfs.modules.contacts.base.CallResult" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<form name="addCall" action="AccountContactsCalls.do?command=Modify&id=<%= CallDetails.getId() %>&contactId=<%= ContactDetails.getId() %>" method="post">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<%
  String trailSource = request.getParameter("trailSource");
%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Accounts.do?command=Search">Search Results</a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard">Dashboard</a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<% if("accounts".equals(trailSource)){ %>
<a href="AccountsCalls.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Activities</a> >
<% }else{ %>
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Contacts</a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Contact Details</a> >
<a href="AccountContactsCalls.do?command=View&contactId=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Activities</a> >
<% } %>
Activity Details
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="contacts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <%-- include contact menu --%>
      <% String param1 = "id=" + ContactDetails.getId(); 
    %>
        <strong><%= ContactDetails.getNameLastFirst() %>:</strong>
        [ <dhv:container name="accountscontacts" selected="calls" param="<%= param1 %>"/> ]
        <br>
        <br>
      <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
      <dhv:permission name="accounts-accounts-contacts-calls-edit"><input type="button" value="Complete" onClick="javascript:window.location.href='AccountContactsCalls.do?command=Complete&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "view|trailSource") %>'"></dhv:permission>
      </dhv:evaluate>
      <% if("pending".equals(request.getParameter("view"))){ %>
        <dhv:permission name="accounts-accounts-contacts-calls-edit"><input type="submit" value="Modify"></dhv:permission>
      <%}else if(CallDetails.getStatusId() != Call.CANCELED){%>
        <dhv:permission name="accounts-accounts-contacts-calls-edit"><input type="submit" value="Modify"></dhv:permission>
      <%}%>
      <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
      <dhv:permission name="accounts-accounts-contacts-calls-delete"><input type="button" value="Cancel Pending Activity" onClick="javascript:window.location.href='AccountContactsCalls.do?command=Cancel&contactId=<%= CallDetails.getContactId() %>&id=<%= CallDetails.getId() %>&action=cancel<%= addLinkParams(request, "popup|popupType|actionId|view|trailSource") %>';"></dhv:permission></dhv:evaluate>
      <dhv:evaluate exp="<%= !isPopup(request) %>">
      <dhv:permission name="myhomepage-inbox-view"><input type="button" name="action" value="Forward" onClick="javascript:window.location.href='AccountContactsCalls.do?command=ForwardCall&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|view|trailSource") %>'"></dhv:permission>
      </dhv:evaluate>
      <dhv:permission name="accounts-accounts-contacts-calls-edit,accounts-accounts-contacts-calls-delete"><br>&nbsp;</dhv:permission>
      
      <% if("pending".equals(request.getParameter("view"))){ %>
        <dhv:evaluate if="<%= CallDetails.getAlertDate() != null %>">
        <%-- include follow up activity details --%>
        <%@ include file="accounts_contacts_calls_details_followup_include.jsp" %>
        </dhv:evaluate>
        &nbsp;
        <%-- include completed activity details --%>
        <%@ include file="accounts_contacts_calls_details_include.jsp" %>
      <% }else{ %>
        <%-- include completed activity details --%>
        <%@ include file="accounts_contacts_calls_details_include.jsp" %>
        &nbsp;
        <dhv:evaluate if="<%= CallDetails.getAlertDate() != null %>">
        <%-- include follow up activity details --%>
        <%@ include file="accounts_contacts_calls_details_followup_include.jsp" %>
        </dhv:evaluate>
      <% } %>
      
      &nbsp;
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong>Record Information</strong>  
          </th>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" valign="top" nowrap>
            Entered
          </td>
          <td>
            <dhv:username id="<%= CallDetails.getEnteredBy() %>"/>
            <zeroio:tz timestamp="<%= CallDetails.getEntered() %>" timeZone="<%= User.getTimeZone() %>"/>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Modified
          </td>
          <td>
            <dhv:username id="<%= CallDetails.getModifiedBy() %>"/>
            <zeroio:tz timestamp="<%= CallDetails.getModified() %>" timeZone="<%= User.getTimeZone() %>"/>
          </td>
        </tr>
      </table>
      <br>
      <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
      <dhv:permission name="accounts-accounts-contacts-calls-edit"><input type="button" value="Complete" onClick="javascript:window.location.href='AccountContactsCalls.do?command=Complete&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "view|trailSource") %>'"></dhv:permission>
      </dhv:evaluate>
      <% if("pending".equals(request.getParameter("view"))){ %>
        <dhv:permission name="accounts-accounts-contacts-calls-edit"><input type="submit" value="Modify"></dhv:permission>
      <%}else if(CallDetails.getStatusId() != Call.CANCELED){%>
        <dhv:permission name="accounts-accounts-contacts-calls-edit"><input type="submit" value="Modify"></dhv:permission>
      <%}%>
      <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
      <dhv:permission name="accounts-accounts-contacts-calls-delete"><input type="button" value="Cancel Pending Activity" onClick="javascript:window.location.href='AccountContactsCalls.do?command=Cancel&contactId=<%= CallDetails.getContactId() %>&id=<%= CallDetails.getId() %>&action=cancel<%= isPopup(request) ? "&popup=true" : "" %><%= addLinkParams(request, "popupType|actionId|view|") %>';"></dhv:permission></dhv:evaluate>
      <dhv:evaluate exp="<%= !isPopup(request) %>">
      <dhv:permission name="myhomepage-inbox-view"><input type="button" name="action" value="Forward" onClick="javascript:window.location.href='AccountContactsCalls.do?command=ForwardCall&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|view|trailSource") %>'"></dhv:permission>
      </dhv:evaluate>
      <dhv:permission name="accounts-accounts-contacts-calls-edit,accounts-accounts-contacts-calls-delete"><br>&nbsp;</dhv:permission>
    </td>
  </tr>
</table>
<%= addHiddenParams(request, "popup|popupType|actionId|view|trailSource") %>
</form>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="ReminderTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CallResult" class="org.aspcfs.modules.contacts.base.CallResult" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<form name="addCall" action="ExternalContactsCalls.do?command=Modify&id=<%= CallDetails.getId() %>&contactId=<%= ContactDetails.getId() %>" method="post">
<dhv:evaluate exp="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=SearchContacts">Search Results</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
<a href="ExternalContactsCalls.do?command=View&contactId=<%=ContactDetails.getId()%>">Activities</a> >
Activity Details
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%@ include file="contact_details_header_include.jsp" %>
<% String param1 = "id=" + ContactDetails.getId(); 
   String param2 = addLinkParams(request, "popup|popupType|actionId"); %>
  <dhv:container name="contacts" selected="calls" param="<%= param1 %>" appendToUrl="<%= param2 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
      <dhv:sharing primaryBean="CallDetails" secondaryBeans="ContactDetails" action="edit"><input type="button" value="Complete" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=Complete&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "view|popup|popupType|actionId") %>'"></dhv:sharing>
     </dhv:evaluate>
     <% if("pending".equals(request.getParameter("view"))){ %>
      <dhv:sharing primaryBean="CallDetails" secondaryBeans="ContactDetails" action="edit"><input type="submit" value="Modify"></dhv:sharing>
      <%}else if(CallDetails.getStatusId() != Call.CANCELED){%>
       <dhv:sharing primaryBean="CallDetails" secondaryBeans="ContactDetails" action="edit"><input type="submit" value="Modify"></dhv:sharing>
      <%}%>
      <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
      <dhv:sharing primaryBean="CallDetails" secondaryBeans="ContactDetails" action="delete"><input type="button" value="Cancel Pending Activity" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=Cancel&contactId=<%= CallDetails.getContactId() %>&id=<%= CallDetails.getId() %>&action=cancel<%= addLinkParams(request, "popup|popupType|actionId|view") %>';"></dhv:sharing></dhv:evaluate>
      <dhv:evaluate exp="<%= !isPopup(request) %>">
      <dhv:permission name="contacts-external_contacts-calls-view"><input type="button" name="action" value="Forward" onClick="javascript:window.location.href='ExternalContactsCallsForward.do?command=ForwardCall&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|view") %>'"></dhv:permission>
      </dhv:evaluate>
      <dhv:permission name="contacts-external_contacts-calls-edit,contacts-external_contacts-calls-delete"><br>&nbsp;</dhv:permission>
      
      <% if("pending".equals(request.getParameter("view"))){ %>
        <dhv:evaluate if="<%= CallDetails.getAlertDate() != null %>">
        <%-- include follow up activity details --%>
        <%@ include file="../accounts/accounts_contacts_calls_details_followup_include.jsp" %>
        </dhv:evaluate>
        &nbsp;
        <%-- include completed activity details --%>
        <%@ include file="../accounts/accounts_contacts_calls_details_include.jsp" %>
      <% }else{ %>
        <%-- include completed activity details --%>
        <%@ include file="../accounts/accounts_contacts_calls_details_include.jsp" %>
        &nbsp;
        <dhv:evaluate if="<%= CallDetails.getAlertDate() != null %>">
        <%-- include follow up activity details --%>
        <%@ include file="../accounts/accounts_contacts_calls_details_followup_include.jsp" %>
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
          <td class="formLabel" nowrap>
            Entered
          </td>
          <td>
            <dhv:username id="<%= CallDetails.getEnteredBy() %>"/>
            <zeroio:tz timestamp="<%= CallDetails.getEntered()  %>" />
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Modified
          </td>
          <td>
            <dhv:username id="<%= CallDetails.getModifiedBy() %>"/>
            <zeroio:tz timestamp="<%= CallDetails.getModified()  %>" />
          </td>
        </tr>
      </table>
      <br>
      
      <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
      <dhv:sharing primaryBean="CallDetails" secondaryBeans="ContactDetails" action="edit"><input type="button" value="Complete" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=Complete&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "view|popup|popupType|actionId") %>'"></dhv:sharing>
     </dhv:evaluate>
     <% if("pending".equals(request.getParameter("view"))){ %>
      <dhv:sharing primaryBean="CallDetails" secondaryBeans="ContactDetails" action="edit"><input type="submit" value="Modify"></dhv:sharing>
      <%}else if(CallDetails.getStatusId() != Call.CANCELED){%>
       <dhv:sharing primaryBean="CallDetails" secondaryBeans="ContactDetails" action="edit"><input type="submit" value="Modify"></dhv:sharing>
      <%}%>
      <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
      <dhv:sharing primaryBean="CallDetails" secondaryBeans="ContactDetails" action="delete"><input type="button" value="Cancel Pending Activity" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=Cancel&contactId=<%= CallDetails.getContactId() %>&id=<%= CallDetails.getId() %>&action=cancel<%= addLinkParams(request, "popup|popupType|actionId|view") %>';"></dhv:sharing></dhv:evaluate>
      <dhv:evaluate exp="<%= !isPopup(request) %>">
      <dhv:permission name="contacts-external_contacts-calls-view"><input type="button" name="action" value="Forward" onClick="javascript:window.location.href='ExternalContactsCallsForward.do?command=ForwardCall&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|view") %>'"></dhv:permission>
      </dhv:evaluate>
    </td>
  </tr>
</table>
<%= addHiddenParams(request, "popup|popupType|actionId|view") %>
</form>

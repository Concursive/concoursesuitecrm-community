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
<%-- Opportunity header 
     NOTE: If this is a popup window, the links to other modules are suppressed
--%>
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" nowrap>
      <img src="images/icons/stock_form-currency-field-16.gif" border="0" align="absmiddle">
      <strong><%= toHtml(opportunityHeader.getDescription()) %></strong>
      <%-- Show the account name --%>
      <dhv:evaluate if="<%= opportunityHeader.getAccountLink() > -1 %>">
        <br>
        <img src="images/icons/stock_account-16.gif" border="0" align="absmiddle">
        <dhv:permission name="accounts-view,accounts-accounts-view"><a href="Accounts.do?command=Details&orgId=<%= opportunityHeader.getAccountLink() %>"></dhv:permission><%= toHtml(opportunityHeader.getAccountName()) %><dhv:permission name="accounts-view,accounts-accounts-view"></a></dhv:permission>
      </dhv:evaluate>
      <%-- Show the contact's name and company name --%>
      <dhv:evaluate if="<%= opportunityHeader.getContactLink() > -1 %>">
        <br>
        <img src="images/icons/stock_bcard-16.gif" border="0" align="absmiddle">
        <dhv:evaluate if="<%= request.getParameter("popup") == null %>"><dhv:permission name="contacts-view,contacts-external_contacts-view"><a href="ExternalContacts.do?command=ContactDetails&id=<%= opportunityHeader.getContactLink() %>"></dhv:permission></dhv:evaluate><%= toHtml(opportunityHeader.getContactName()) %><dhv:evaluate if="<%= request.getParameter("popup") == null %>"><dhv:permission name="contacts-view,contacts-external_contacts-view"></a></dhv:permission></dhv:evaluate>
        <dhv:evaluate if="<%= hasText(opportunityHeader.getContactCompanyName()) %>">
          (<%= toHtml(opportunityHeader.getContactCompanyName()) %>)
        </dhv:evaluate>
      </dhv:evaluate>
    </td>
    <td valign="top" align="right" nowrap>
      <dhv:evaluate if="<%= opportunityHeader.hasFiles() %>">
        &nbsp;
        <% FileItem thisFile = new FileItem(); %>
        <%= thisFile.getImageTag() %>
      </dhv:evaluate>
    </td>
  </tr>
</table>

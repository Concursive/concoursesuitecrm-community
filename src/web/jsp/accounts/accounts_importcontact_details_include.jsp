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
<%-- Contact header --%>
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" nowrap>
      <img src="images/icons/stock_bcard-16.gif" border="0" align="absmiddle">
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
      <%-- Contact's company name --%>
      <dhv:evaluate if="<%= hasText(ContactDetails.getOrgName()) %>">
        <%-- No account, no link --%>
        <dhv:evaluate if="<%= (ContactDetails.getOrgId() == -1) %>">
          (<%= toHtml(ContactDetails.getOrgName()) %>)
        </dhv:evaluate>
        <%-- Show an account link if the account is > 0, since 0 is "My Organization" --%>
        <dhv:evaluate if="<%= (ContactDetails.getOrgId() > 0) %>">
          <dhv:permission name="accounts-accounts-view">
              (<%= toHtml(ContactDetails.getOrgName()) %>)
          </dhv:permission>
          <dhv:permission name="accounts-accounts-view" none="true">
            (<%= toHtml(ContactDetails.getOrgName()) %>)
          </dhv:permission>
        </dhv:evaluate>
      </dhv:evaluate>
      <%-- Contact's department --%>
      <dhv:evaluate if="<%= hasText(ContactDetails.getDepartmentName()) %>">
        (<%= toHtml(ContactDetails.getDepartmentName()) %>)
      </dhv:evaluate>
      <dhv:evaluate if="<%= hasText(ContactDetails.getTitle()) %>">
        <br><%= ContactDetails.getTitle() %>
      </dhv:evaluate>
    </td>
    <%-- The type of contact showing --%>
    <td align="right" valign="top" nowrap>
      <dhv:evaluate if="<%= ContactDetails.getOrgId() > 0 %>">
        <i><dhv:label name="accounts.accounts_importcontact_details_include.AccountContact">Account Contact</dhv:label></i>
      </dhv:evaluate>
      <dhv:evaluate if="<%= ContactDetails.getOrgId() == 0 %>">
        <i><dhv:label name="accounts.accounts_importcontact_details_include.Employee">Employee</dhv:label></i>
      </dhv:evaluate>
      <dhv:evaluate if="<%= ContactDetails.getOrgId() == -1 %>">
        <i><dhv:label name="accounts.accounts_importcontact_details_include.GeneralContact">General Contact</dhv:label></i>
      </dhv:evaluate>
      <%-- Contact category types, skip employees --%>
      <dhv:evaluate if="<%= ContactDetails.getOrgId() != 0 && hasText(ContactDetails.getTypesNameString()) %>">
        <br><%= ContactDetails.getTypesNameString() %>
      </dhv:evaluate>
    </td>
  </tr>
</table>

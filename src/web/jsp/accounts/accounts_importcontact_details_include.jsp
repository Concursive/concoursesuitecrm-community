<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
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
        <i>Account Contact</i>
      </dhv:evaluate>
      <dhv:evaluate if="<%= ContactDetails.getOrgId() == 0 %>">
        <i>Employee</i>
      </dhv:evaluate>
      <dhv:evaluate if="<%= ContactDetails.getOrgId() == -1 %>">
        <i>General Contact</i>
      </dhv:evaluate>
      <%-- Contact category types, skip employees --%>
      <dhv:evaluate if="<%= ContactDetails.getOrgId() != 0 && hasText(ContactDetails.getTypesNameString()) %>">
        <br><%= ContactDetails.getTypesNameString() %>
      </dhv:evaluate>
    </td>
  </tr>
</table>

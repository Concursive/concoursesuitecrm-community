<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%-- Accounts header --%>
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%">
      <h1><img src="images/icons/stock_account-16.gif" border="0" align="absmiddle">
      <strong><%= toHtml(OrgDetails.getName()) %></strong></h1>
    </td>
  </tr>
  <dhv:evaluate if="<%=hasText(OrgDetails.getTypes().valuesAsString())%>">
  <tr>
    <%-- more header info... --%>
    <td>
      <%= toHtml(OrgDetails.getTypes().valuesAsString()) %>
    </td>
  </tr>
  </dhv:evaluate>
</table>

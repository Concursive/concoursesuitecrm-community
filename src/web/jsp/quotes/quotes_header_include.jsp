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
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td width="100%" nowrap>
      <dhv:label name="quotes.header.status">Status:</dhv:label>
      <dhv:evaluate if="<%= quote.getStatusId() == -1 %>"><dhv:label name="quotes.incomplete">Incomplete</dhv:label></dhv:evaluate>
      <dhv:evaluate if="<%= quote.getStatusId() != -1 %>"><font color="red"><%= toHtml(quoteStatusList.getValueFromId(quote.getStatusId())) %></font></dhv:evaluate>
      <dhv:evaluate if="<%= quote.getExpirationDate() == null %>">
        <dhv:label name="quotes.doesNotExpire">Does not Expire</dhv:label>
      </dhv:evaluate>
      <dhv:evaluate if="<%= quote.getExpirationDate() != null %>">
        <dhv:evaluate if="<%= quote.hasExpired() %>">
          <dhv:label name="quotes.expiredOn">Expired on</dhv:label>
        </dhv:evaluate>
        <dhv:evaluate if="<%= !quote.hasExpired() %>">
          <dhv:label name="quotes.expiresOn">Expires on</dhv:label>
        </dhv:evaluate>
        <font color="red"><zeroio:tz timestamp="<%= quote.getExpirationDate() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" /></font>
      </dhv:evaluate>
    </td>
  </tr>
</table>
<br />
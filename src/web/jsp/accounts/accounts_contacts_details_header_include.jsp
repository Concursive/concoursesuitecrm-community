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
<%-- Accounts/Contacts header --%>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td width="100%" nowrap>
      <img src="images/icons/stock_bcard-16.gif" border="0" align="absmiddle">
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
      <dhv:include name="accounts-selectedList" none="true">
      <dhv:evaluate exp="<%= hasText(ContactDetails.getTitle()) %>">
        / <%= toHtml(ContactDetails.getTitle()) %>
      </dhv:evaluate>
      </dhv:include>
      [<a href="javascript:popURL('ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>&popup=true&viewOnly=true','Details','650','500','yes','yes');">View Details</a>]
      <dhv:evaluate if="<%= !isPopup(request) %>">
      <br>
      <% String param2 = "";
          if("accounts".equals(request.getParameter("trailSource"))){
            param2 = "&trailSource=accounts";
          }
      %>
       <dhv:container name="accountscontacts" selected="<%= selected %>" param="<%= param1 %>" style="tabs" appendToUrl="<%= param2 %>"/>
      </dhv:evaluate>
    </td>
    <td align="right" valign="top" nowrap>
      &nbsp;
    </td>
  </tr>
  
</table>

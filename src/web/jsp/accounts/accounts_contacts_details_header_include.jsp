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

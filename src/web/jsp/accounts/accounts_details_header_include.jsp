<%-- Accounts header --%>
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%">
      <img src="images/icons/stock_account-16.gif" border="0" align="absmiddle">
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
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

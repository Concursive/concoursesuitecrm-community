<%-- Accounts header --%>
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" nowrap>
      <img src="images/icons/stock_account-16.gif" border="0" align="absmiddle">
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
    <%-- more header info... --%>
    <td align="right" valign="top" nowrap>
      <%= toHtml(OrgDetails.getTypes().valuesAsString()) %>
    </td>
  </tr>
</table>

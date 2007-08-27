<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th>
        <dhv:label name="accounts.accounts_documents_folders_add.Folder">Folder</dhv:label>
      </th>
      <th>
        <dhv:label name="accounts.accounts_documents_folders_add.Records">Records</dhv:label>
      </th>
    </tr>
    <%
      Iterator j = CategoryList.iterator();
      if ( j.hasNext() ) {
        int rowid = 0;
        int i = 0;
        while (j.hasNext()) {
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        CustomFieldCategory thisCategory = (CustomFieldCategory) j.next();
    %>
    <tr class="row<%= rowid %>">
      <td>
        <a href="ExternalContacts.do?command=Fields&contactId=<%= ContactDetails.getId() %>&catId=<%= thisCategory.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= toHtml(thisCategory.getName()) %></a>
      </td>
      <td>
        <%= thisCategory.getNumberOfRecords() %>
      </td>
    </tr>
    <% } %>
   <% } else { %>
    <tr class="row2">
      <td colspan="2">
        <dhv:label name="accounts.accounts_fields_list.NoCustomFoldersAvailable">No custom folders available.</dhv:label>
      </td>
    </tr>
    <% } %>
   </table>
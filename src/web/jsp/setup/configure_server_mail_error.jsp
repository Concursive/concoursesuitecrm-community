<%@ include file="../initPage.jsp" %>
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      CFS Mail Test
    </th>
  </tr>
  <tr>
    <td>
      Email failed.  The email transport returned the following error:<br>
      <%= showError(request, "actionError") %>
      <input type="button" value="OK" onClick="javascript:window.close()"/>
    </td>
  </tr>
</table>

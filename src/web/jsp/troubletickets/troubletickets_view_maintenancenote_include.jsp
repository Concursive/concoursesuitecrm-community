<%--  Start details --%>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong>General Maintenance Information</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        Description of Service
      </td>
      <td>
        <%= toHtml(maintenanceDetails == null ? "" : maintenanceDetails.getDescriptionOfService()) %>
      </td>
    </tr>
  </table>
  <br />
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="4">
        <strong>Replacement Parts</strong>
      </th>
    </tr>
  <%
    if (maintenanceDetails != null){
      Iterator inumber = maintenanceDetails.getTicketReplacementPartList().iterator();
      if(inumber.hasNext()){
        int icount = 0;
        while (inumber.hasNext()) {
          ++icount;
          TicketReplacementPart thisPart = (TicketReplacementPart) inumber.next();
    %>
      <tr class="containerBody">
        <td class="formLabel" nowrap>
          Part <%= icount %>
        </td>
        <td>
          <%= toHtml(thisPart.getPartNumber()) %>
        </td>
        <td class="formLabel" nowrap>
          Description <%= icount %>
        </td>
        <td>
          <%= toHtml(thisPart.getPartDescription()) %>
        </td>
      </tr>
    <%
        }
     }else{ %>
    <tr>
      <td colspan="4" class="containerBody">
        No replacement parts specified
      </td>
    </tr>
    <%}
    }%>
</table>

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

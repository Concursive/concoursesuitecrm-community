<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
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
        <strong><dhv:label name="tickets.generalMaintenanceInfo">General Maintenance Information</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="reports.helpdesk.ticket.activity.descOfService">Description of Service</dhv:label>
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
        <strong><dhv:label name="reports.helpdesk.ticket.maintenance.replacementParts">Replacement Parts</dhv:label></strong>
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
          <dhv:label name="tickets.part" param='<%= "number="+icount %>'>Part <%= icount %></dhv:label>
        </td>
        <td>
          <%= toHtml(thisPart.getPartNumber()) %>
        </td>
        <td class="formLabel" nowrap>
          <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label> <%= icount %>
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
        <dhv:label name="tickets.noReplacementPartsSpecified">No replacement parts specified</dhv:label>
      </td>
    </tr>
    <%}
    }%>
</table>

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
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript">
  function doCheck(form) {
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  function checkForm(form) {
  
    formTest = true;
    message = "";
    if (checkNullString(form.descriptionOfService.value)) {
      message += label("check.descriptionofservice","- Description of Service is required\r\n");
      formTest = false;
    }
    var i = 1;
    var fields = form.elements;
    var parts = Array(5);
    var descriptions = Array(5);
    var partCount = 1;
    var descriptionCount = 1;
    for (i=0; i < fields.length ; i++){
      if (fields[i].name.substring(0,10).match("partNumber")){
        parts[partCount] = fields[i]; 
        partCount++;
      }
      if (fields[i].name.substring(0,15).match("partDescription")){
        descriptions[descriptionCount] = fields[i]; 
        descriptionCount++;
      }
    }
      
    for (i=1;i<partCount;i++){ 
      if ((!checkNullString(parts[i].value)) && (checkNullString(descriptions[i].value))){
        message += label("check.allitems.part.one","- Check that all items in row ")+ i +label("check.allitems.part.two"," are filled in\r\n");
        formTest = false;
      }

      if ((checkNullString(parts[i].value)) && (!checkNullString(descriptions[i].value))){
        message += label("check.allitems.part.one","- Check that all items in row ")+ i +label("check.allitems.part.two"," are filled in\r\n");
        formTest = false;
      }
    }

    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
  }
</script>
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
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
          <textarea name="descriptionOfService" cols="50" rows="3"></textarea>
          <td valign="top">
            <font color="red">*</font><%= showAttribute(request, "descriptionOfServiceError") %>
          </td>
        </tr>
      </table>
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
<%int i=1;%>
<%
  if (maintenanceDetails != null) {
    TicketReplacementPartList partList = maintenanceDetails.getTicketReplacementPartList();
    if (partList != null && partList.size() > 0) {
      Iterator iterator = (Iterator) maintenanceDetails.getTicketReplacementPartList().iterator();
      while (iterator.hasNext()) {
        TicketReplacementPart thisPart = (TicketReplacementPart) iterator.next();
%>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="tickets.part" param="<%= "number="+i %>">Part <%= i %></dhv:label>
      </td>
      <td>
        <input type="text" size="20" maxlength="50" name="partNumber<%= i %>" value="<%= toHtmlValue(thisPart.getPartNumber())%>"/> 
        <br /><%= showAttribute(request, "partNumber"+i+"Error") %>
      </td>
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label> <%= i %>
      </td>
      <td>
        <input type="text" size="55" name="partDescription<%= i %>" maxlength="100" value="<%= toHtmlValue(thisPart.getPartDescription())%>"/>
        &nbsp; <%= showAttribute(request, "partDescription"+i+"Error") %>
      </td>
    </tr>

<%      i++;
      }
    }
  }%>
<%
  for(;i<=3;i++) { %>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="tickets.part" param="<%= "number="+i %>">Part <%= i %></dhv:label>
      </td>
      <td>
        <input type="text" size="20" maxlength="50" name="partNumber<%= i %>" /> &nbsp; <%= showAttribute(request, "partNumber"+i+"Error") %>
      </td>
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label> <%= i %>
      </td>
      <td>
        <input type="text" size="55" name="partDescription<%= i %>" maxlength="100">&nbsp; <%= showAttribute(request, "partDescription"+i+"Error") %>
      </td>
    </tr>
<%}%>
  </table>

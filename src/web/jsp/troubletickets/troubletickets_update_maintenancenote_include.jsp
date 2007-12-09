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
          <textarea name="descriptionOfService" cols="50" rows="3"><%=toString(maintenanceDetails.getDescriptionOfService())%></textarea>
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
  <%
    boolean noneSelected = false;
    int icount = 0;
    if (maintenanceDetails != null){
      Iterator inumber = maintenanceDetails.getTicketReplacementPartList().iterator();
      if(inumber.hasNext()){
      while (inumber.hasNext()) {
        ++icount;
        TicketReplacementPart thisPart = (TicketReplacementPart) inumber.next();
    %>
      <tr class="containerBody">
        <input type="hidden" name="part<%= icount %>Id" value="<%= thisPart.getId() %>">
        <td class="formLabel" nowrap>
          <dhv:label name="tickets.part" param='<%= "number="+icount %>'>Part <%= icount %></dhv:label>
        </td>
        <td>
          <input type="text" size="20" maxlength="50" name="partNumber<%= icount %>" value="<%= toHtmlValue(thisPart.getPartNumber()) %>">
        </td>
        <td class="formLabel" nowrap>
          <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label> <%= icount %>
        </td>
        <td>
          <input type="text" size="55" name="partDescription<%= icount %>" value="<%= toHtmlValue(thisPart.getPartDescription()) %>" maxlength="100">
        </td>
      </tr>
    <%
       }
       ++icount;
    %>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="tickets.part" param='<%= "number="+icount %>'>Part <%= icount %></dhv:label>
      </td>
      <td>
        <input type="text" size="20" name="partNumber<%= icount %>" maxlength="50"/>
      </td>
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label> <%= icount %>
      </td>
      <td>
        <input type="text" size="55" name="partDescription<%= icount %>" maxlength="100" />
      </td>
    </tr>
  <% }else{
      noneSelected = true;
     }
    }
  %>
  <% if (noneSelected == true){ %>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="tickets.part1">Part 1</dhv:label>
      </td>
      <td>
        <input type="text" size="20" name="partNumber1" maxlength="50"/>
      </td>
      <td class="formLabel" nowrap>
        <dhv:label name="tickets.description1">Description 1</dhv:label>
      </td>
      <td>
        <input type="text" size="55" name="partDescription1" maxlength="100" />
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="tickets.part2">Part 2</dhv:label>
      </td>
      <td>
        <input type="text" size="20" name="partNumber2" maxlength="50" />
      </td>
      <td class="formLabel" nowrap>
        <dhv:label name="tickets.description2">Description 2</dhv:label>
      </td>
      <td>
        <input type="text" size="55" name="partDescription2" maxlength="100" />
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="tickets.part3">Part 3</dhv:label>
      </td>
      <td>
        <input type="text" size="20" name="partNumber3" maxlength="50" />
      </td>
      <td class="formLabel" nowrap>
        <dhv:label name="tickets.description3">Description 3</dhv:label>
      </td>
      <td>
        <input type="text" size="55" name="partDescription3" maxlength="100" />
      </td>
    </tr>
  <%}%>
  </table>
<input type="hidden" name="modified" value="<%=maintenanceDetails.getModified()%>">


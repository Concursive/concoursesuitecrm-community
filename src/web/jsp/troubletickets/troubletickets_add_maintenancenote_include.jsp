<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
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
    if (form.descriptionOfService.value == "") {
      message += "- Description of Service is required\r\n";
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
      if ((!parts[i].value == "") && (descriptions[i].value=="")){
        message += "- Check that all items in row "+ i +" are filled in\r\n";
        formTest = false;
      }

      if ((parts[i].value == "") && (!descriptions[i].value=="")){
        message += "- Check that all items in row "+ i +" are filled in\r\n";
        formTest = false;
      }
    }

    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    }
  }
</script>
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
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
          <textarea name="descriptionOfService" cols="50" rows="3"></textarea>
          <td valign="top">
            <font color="red">*</font>
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
        <strong>Replacement Parts</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        Part 1
      </td>
      <td>
        <input type="text" size="20" maxlength="50" name="partNumber1">
      </td>
      <td class="formLabel" nowrap>
        Description 1
      </td>
      <td>
        <input type="text" size="55" name="partDescription1" maxlength="100">
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        Part 2
      </td>
      <td>
        <input type="text" size="20"  maxlength="50" name="partNumber2">
      </td>
      <td class="formLabel" nowrap>
        Description 2
      </td>
      <td>
        <input type="text" size="55" name="partDescription2" maxlength="100">
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        Part 3
      </td>
      <td>
        <input type="text" size="20" maxlength="50" name="partNumber3">
      </td>
      <td class="formLabel" nowrap>
        Description 3
      </td>
      <td>
        <input type="text" size="55" name="partDescription3" maxlength="100">
      </td>
    </tr>
  </table>

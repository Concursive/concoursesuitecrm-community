<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<script language="JavaScript">
  onLoad = 1;
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
    if (form.sku.value == "") { 
      message += "- Code is required\r\n";
      formTest = false;
    }
    if (form.name.value == "") { 
      message += "- Description is required\r\n";
      formTest = false;
    }
    if (form.categoryId.value < 1){ 
      message += "- Category is required\r\n";
      formTest = false;
    }
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    }
  }
</script>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Details</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Code
    </td>
    <td>
      <input type="text" size="10" name="sku" maxlength="10" value="<%=toHtmlValue(productDetails.getSku())%>" /><font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Description
    </td>
    <td>
      <input type="text" size="50" name="name" maxlength="50" value="<%=toHtmlValue(productDetails.getName())%>" /><font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Category
    </td>
    <td>
      <%= categoryList.getHtmlSelect("categoryId", productDetails.getCategoryId())%><font color="red">*</font>
    </td>
  </tr>
  <input type="hidden" name="id" value="<%=productDetails.getId()%>" />
  <input type="hidden" name="entered" value="<%=productDetails.getEntered()%>" />
  <input type="hidden" name="modified" value="<%=productDetails.getModified()%>" />
  <input type="hidden" name="enteredBy" value="<%=productDetails.getEnteredBy()%>" />
  <input type="hidden" name="modifiedBy" value="<%=productDetails.getModifiedBy()%>" />
</table>

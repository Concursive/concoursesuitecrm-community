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

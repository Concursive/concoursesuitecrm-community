<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/div.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript">
  <%-- Dynamic combo boxes --%>
  function updateCategoryList(id) {
    var sel = document.forms['addAccountAsset'].elements['level' + id];
    var value = sel.options[sel.selectedIndex].value;
    var url = "AccountsAssets.do?command=CategoryJSList&level=" + id + "&code=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
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
    if (form.dateListed.value == "") { 
      message += "- Date Listed is required\r\n";
      formTest = false;
    }
    if (form.serialNumber.value == "") {
      message += "- Serial Number is required\r\n";
      formTest = false;
    }
    if ((!form.dateListed.value == "") && (!checkDate(form.dateListed.value))) {
      message += "- Check that Date Listed is entered correctly\r\n";
      formTest = false;
    }
    if ((!form.expirationDate.value == "") && (!checkDate(form.expirationDate.value))) { 
      message += "- Check that Expiration Date is entered correctly\r\n";
      formTest = false;
    }
    if ((!form.purchaseDate.value == "") && (!checkDate(form.purchaseDate.value))) { 
      message += "- Check that Purchase Date is entered correctly\r\n";
      formTest = false;
    }
    if (!checkNumber(form.purchaseCost.value)) { 
      message += "- Check that Purchase Cost is entered correctly\r\n";
      formTest = false;
    }
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    }
  }
</script>
<%-- start details --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Specific Information</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Vendor
    </td>
    <td>
      <input type="text" size="20" name="vendor" maxlength="30" value="<%= toHtmlValue(asset.getVendor()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Manufacturer
    </td>
    <td>
      <input type="text" size="20" name="manufacturer" maxlength="30" value="<%= toHtmlValue(asset.getManufacturer()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Serial Number
    </td>
    <td>
      <input type="text" size="20" name="serialNumber" maxlength="30" value="<%= toHtmlValue(asset.getSerialNumber()) %>">
      &nbsp;<font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Model/Version
    </td>
    <td>
      <input type="text" size="20" name="modelVersion" maxlength="30" value="<%= toHtmlValue(asset.getModelVersion()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Description
    </td>
    <td>
      <textarea name="description" rows="3" cols="50"><%= toString(asset.getDescription()) %></textarea>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Date Listed
    </td>
    <td>
      <%if (asset.getDateListed() == null){ %>
        <input type="text" size="10" name="dateListed" maxlength="10" value="<%=toHtml((String)request.getAttribute("currentDate"))%>">
      <%}else{%>
        <input type="text" size="10" name="dateListed" maxlength="10" value="<dhv:tz timestamp="<%= asset.getDateListed() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>">
      <%}%>
      <a href="javascript:popCalendar('addAccountAsset', 'dateListed');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
      &nbsp;<font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Location
    </td>
    <td>
      <input type="text" name="location"  size="50" maxlength="256" value="<%= toHtmlValue(asset.getLocation()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Asset Tag
    </td>
    <td>
      <input type="text" size="20" name="assetTag" maxlength="30" value="<%= toHtmlValue(asset.getAssetTag()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Status
    </td>
    <td>
      <%= assetStatusList.getHtmlSelect("status",asset.getStatus()) %>
    </td>
  </tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Category</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Level 1
    </td>
    <td>
      <%= categoryList1.getHtmlSelect("level1", asset.getLevel1()) %>
    </td>
	</tr>
  <tr class="containerBody">
    <td class="formLabel">
      Level 2
    </td>
    <td>
      <%= categoryList2.getHtmlSelect("level2", asset.getLevel2()) %>
    </td>
	</tr>
  <tr class="containerBody">
    <td class="formLabel">
      Level 3
    </td>
    <td>
      <%= categoryList3.getHtmlSelect("level3", asset.getLevel3()) %>
    </td>
	</tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Service Contract</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Service Contract Number
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="addServiceContract"><%= (asset.getContractId() != -1) ? asset.getServiceContractNumber() :"None Selected" %></div>
        </td>
        <td>
          <input type="hidden" name="contractId" id="contractId" value="<%= asset.getContractId() %>">
          <%= showAttribute(request, "contractIdError") %>
          [<a href="javascript:popServiceContractListSingle('contractId','addServiceContract', 'filters=all|my|disabled', <%=OrgDetails.getOrgId()%>);">Select</a>]
        </td>
      </tr>
    </table>
   </td>
  </tr>
	<tr class="containerBody">
    <td class="formLabel">
      Contact
    </td>
    <td>
      <%= contactList.getHtmlSelect("contactId", asset.getContactId() ) %>
    </td>
	</tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Service Model Options</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Response Time
    </td>
    <td>
      <%= responseModelList.getHtmlSelect("responseTime",asset.getResponseTime()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Telephone Service
    </td>
    <td>
      <%= phoneModelList.getHtmlSelect("telephoneResponseModel", asset.getTelephoneResponseModel()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Onsite Service
    </td>
    <td>
      <%= onsiteModelList.getHtmlSelect("onsiteResponseModel", asset.getOnsiteResponseModel()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Email Service Model
    </td>
    <td>
      <%= emailModelList.getHtmlSelect("emailResponseModel" ,asset.getEmailResponseModel()) %>
    </td>
  </tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Warranty Information</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Expiration Date
    </td>
    <td>
      <input type="text" size="10" name="expirationDate" maxlength="10" value="<dhv:tz timestamp="<%= asset.getExpirationDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>">
      <a href="javascript:popCalendar('addAccountAsset', 'expirationDate');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
    Inclusions
    </td>
    <td>
      <textarea name="inclusions" rows="3" cols="50"><%= toString(asset.getInclusions()) %></textarea>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Exclusions
    </td>
    <td>
      <textarea name="exclusions" rows="3" cols="50"><%= toString(asset.getExclusions()) %></textarea>
    </td>
  </tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Financial Information</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Purchase Date
    </td>
    <td>
      <input type="text" size="10" name="purchaseDate" maxlength="10" value="<dhv:tz timestamp="<%= asset.getPurchaseDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>">
      <a href="javascript:popCalendar('addAccountAsset', 'purchaseDate');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Purchase Cost
    </td>
    <td>
      <input type="text" size="10" name="purchaseCost" maxlength="10" value="<%= asset.getPurchaseCost() == -1 ? "" : "" + asset.getPurchaseCost() %>">
      <%= showAttribute(request, "purchaseCostError") %>
      </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      P.O. Number
    </td>
    <td>
      <input type="text" size="20" name="poNumber" maxlength="30" value="<%= toHtmlValue(asset.getPoNumber()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Purchased From
    </td>
    <td>
      <input type="text" size="20" name="purchasedFrom" maxlength="30" value="<%= toHtmlValue(asset.getPurchasedFrom()) %>">
    </td>
  </tr>
</table>
&nbsp;<br> 
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Other Information</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Notes
    </td>
    <td>
      <textarea name="notes" rows="3" cols="50"><%= toString(asset.getNotes()) %></textarea>
    </td>
  </tr>
</table>
<input type="hidden" name="modified" value="<%= asset.getModified() %>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>


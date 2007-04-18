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
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/div.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript">
  var materials = '';
  var currentMaterialIds = '';
  var currentMaterialQuantities = '';
<%
  Iterator iter = (Iterator) asset.getMaterials().iterator();
  while (iter.hasNext()) {
    AssetMaterial material = (AssetMaterial) iter.next();
%>
      materials = materials+'<%= material.getCode()+","+material.getQuantity() %>|';
      currentMaterialIds = currentMaterialIds + '<%= material.getCode() %>|';
      currentMaterialQuantities = currentMaterialQuantities + '<%= material.getQuantity() %>|';
<%}%>
  <%-- Dynamic combo boxes --%>
  function updateCategoryList(id) {
    var sel = document.forms['addAccountAsset'].elements['level' + id];
    var value = sel.options[sel.selectedIndex].value;
    var url = "AccountsAssets.do?command=CategoryJSList&form=addAccountAsset&orgId=<%= OrgDetails.getOrgId() %>&level=" + id + "&code=" + escape(value);
    document.getElementById('materials_elements').value = materials;
    window.frames['server_commands'].location.href=url;
  }

  function doCheck(form) {
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  
  function resetMaterials(mater) {
    var url = 'AccountsAssets.do?command=ViewMaterials&materials='+mater;
    document.forms['addAccountAsset'].materials_elements.value=mater;
    materials = mater;
    currentMaterialIds = '';
    currentMaterialQuantities = '';
    var entry = mater.split("|");
    for (i=0;i<entry.length;i++) {
      if (entry[i] != '') {
        var values = entry[i].split(",");
        if (values[0] != '') {
          currentMaterialIds = currentMaterialIds + values[0]+'|';
          currentMaterialQuantities = currentMaterialQuantities + values[1]+'|';
        }
      }
    }
    window.frames['server_list'].location.href = url;
  }

  function removeMaterial(code) {
    var copyMaterials = '';
    var entry = materials.split("|");
    for (i=0;i<entry.length;i++) {
      if (entry[i] != '') {
        var values = entry[i].split(",");
        if (values[0] != '' && values[0] != code) {
          copyMaterials = copyMaterials+ entry[i]+"|";
        }
      }
    }
    resetMaterials(copyMaterials);
  }
  
  function refreshMaterials() {
    var url = 'AccountsAssets.do?command=ViewMaterials&materials='+materials;
    document.forms['addAccountAsset'].materials_elements.value=materials;
    window.frames['server_list'].location.href = url;
  }
  
  function checkForm(form) {
    formTest = true;
    message = "";
    if (form.dateListed.value == "") { 
      message += label("data.list.required", "- Date Listed is required\r\n");
      formTest = false;
    }
    if (form.serialNumber.value == "") {
      message += label("serial.number.required", "- Serial Number is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
  }

  function resetNumericFieldValue(fieldId){
    document.getElementById(fieldId).value = -1;
  }
</script>
<%-- start details --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accountasset_include.SpecificInformation">Specific Information</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
    <dhv:label name="accounts.accountasset_include.Vendor">Vendor</dhv:label>
    </td>
    <td>
      <%= assetVendorList.getHtmlSelect("vendorCode", asset.getVendorCode()) %>
      <%= showAttribute(request, "vendorCodeError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Manufacturer">Manufacturer</dhv:label>
    </td>
    <td>
      <%= assetManufacturerList.getHtmlSelect("manufacturerCode", asset.getManufacturerCode()) %>
      <%= showAttribute(request, "manufacturerCodeError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.SerialNumber">Serial Number</dhv:label>
    </td>
    <td>
      <input type="text" size="20" name="serialNumber" maxlength="30" value="<%= toHtmlValue(asset.getSerialNumber()) %>">
      <font color="red">*</font>
      <%= showAttribute(request, "serialNumberError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.ModelVersion">Model/Version</dhv:label>
    </td>
    <td>
      <input type="text" size="20" name="modelVersion" maxlength="30" value="<%= toHtmlValue(asset.getModelVersion()) %>">
      <%= showAttribute(request, "modelVersionError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
    </td>
    <td>
      <textarea name="description" rows="3" cols="50"><%= toString(asset.getDescription()) %></textarea>
      <%= showAttribute(request, "descriptionError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.DateListed">Date Listed</dhv:label>
    </td>
    <td>
    <%if ((asset.getDateListed() == null) && (request.getAttribute("dateListedError") == null)){%>
      <zeroio:dateSelect form="addAccountAsset" field="dateListed" timeZone="<%= asset.getDateListedTimeZone() %>" showTimeZone="true" />
    <%}else{%>
      <zeroio:dateSelect form="addAccountAsset" field="dateListed" timestamp="<%= asset.getDateListed() %>" timeZone="<%= asset.getDateListedTimeZone() %>" showTimeZone="true" />
    <%}%>
      <%= showAttribute(request, "dateListedError") %>
      <font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Location">Location</dhv:label>
    </td>
    <td>
      <input type="text" name="location"  size="50" maxlength="256" value="<%= toHtmlValue(asset.getLocation()) %>">
      <%= showAttribute(request, "locationError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.AssetTag">Asset Tag</dhv:label>
    </td>
    <td>
      <input type="text" size="20" name="assetTag" maxlength="30" value="<%= toHtmlValue(asset.getAssetTag()) %>">
      <%= showAttribute(request, "assetTagError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Status">Status</dhv:label>
    </td>
    <td>
      <%= assetStatusList.getHtmlSelect("status", asset.getStatus()) %>
    </td>
  </tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accountasset_include.Category">Category</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Level1">Level 1</dhv:label>
    </td>
    <td>
      <%= categoryList1.getHtmlSelect("level1", asset.getLevel1()) %>
    </td>
	</tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Level2">Level 2</dhv:label>
    </td>
    <td>
      <%= categoryList2.getHtmlSelect("level2", asset.getLevel2()) %>
    </td>
	</tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Level3">Level 3</dhv:label>
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
	    <strong>
	    <dhv:label name="accounts.accountasset_include.ServiceContract">Service Contract</dhv:label>
	    </strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.ServiceContractNumber">Service Contract Number</dhv:label>
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="addServiceContract">
            <dhv:evaluate if="<%= asset.getContractId() != -1 %>">
              <%= toHtml(asset.getServiceContractNumber()) %>
            </dhv:evaluate>
            <dhv:evaluate if="<%= asset.getContractId() == -1 %>">
              <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
            </dhv:evaluate>
          </div>
        </td> 
        <td>
          <input type="hidden" name="contractId" id="contractId" value="<%= asset.getContractId() %>">
          <input type="hidden" name="serviceContractNumber" id="serviceContractNumber" value="<%= toHtmlValue(asset.getServiceContractNumber()) %>">
          <%= showAttribute(request, "contractIdError") %>
          [<a href="javascript:popServiceContractListSingle('contractId','addServiceContract', 'filters=all|my|disabled', <%=OrgDetails.getOrgId()%>);"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
          &nbsp; [<a href="javascript:changeDivContent('addServiceContract',label('none.selected','None Selected'));javascript:resetNumericFieldValue('contractId');"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>] 
        </td>
      </tr>
    </table>
   </td>
  </tr>
	<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label>
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
      <strong><dhv:label name="accounts.accountasset_include.ServiceModelOptions">Service Model Options</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.ResponseTime">Response Time</dhv:label>
    </td>
    <td>
      <%= responseModelList.getHtmlSelect("responseTime",asset.getResponseTime()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.TelephoneService">Telephone Service</dhv:label>
    </td>
    <td>
      <%= phoneModelList.getHtmlSelect("telephoneResponseModel", asset.getTelephoneResponseModel()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.OnsiteService">Onsite Service</dhv:label>
    </td>
    <td>
      <%= onsiteModelList.getHtmlSelect("onsiteResponseModel", asset.getOnsiteResponseModel()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.EmailServiceModel">Email Service Model</dhv:label>
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
      <strong><dhv:label name="accounts.accountasset_include.WarrantyInformation">Warranty Information</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addAccountAsset" field="expirationDate" timestamp="<%= asset.getExpirationDate() %>" timeZone="<%= asset.getExpirationDateTimeZone() %>" showTimeZone="true" />
      <%= showAttribute(request, "expirationDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="accounts.accountasset_include.Inclusions">Inclusions</dhv:label>
    </td>
    <td>
      <textarea name="inclusions" rows="3" cols="50"><%= toString(asset.getInclusions()) %></textarea>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="accounts.accountasset_include.Exclusions">Exclusions</dhv:label>
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
      <strong><dhv:label name="accounts.accountasset_include.FinancialInformation">Financial Information</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.PurchaseDate">Purchase Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addAccountAsset" field="purchaseDate" timestamp="<%= asset.getPurchaseDate() %>" timeZone="<%= asset.getPurchaseDateTimeZone() %>" showTimeZone="true" />
      <%= showAttribute(request, "purchaseDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.PurchaseCost">Purchase Cost</dhv:label>
    </td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="purchaseCost" size="15" value="<zeroio:number value="<%= asset.getPurchaseCost() %>" locale="<%= User.getLocale() %>" />">
      <%= showAttribute(request, "purchaseCostError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.PONumber">P.O. Number</dhv:label>
    </td>
    <td>
      <input type="text" size="20" name="poNumber" maxlength="30" value="<%= toHtmlValue(asset.getPoNumber()) %>">
      <%= showAttribute(request, "poNumberError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.PurchasedFrom">Purchased From</dhv:label>
    </td>
    <td>
      <input type="text" size="20" name="purchasedFrom" maxlength="30" value="<%= toHtmlValue(asset.getPurchasedFrom()) %>">
      <%= showAttribute(request, "purchasedFromError") %>
    </td>
  </tr>
</table>
<br /> 
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accountasset_include.OtherInformation">Other Information</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
    </td>
    <td>
      <textarea name="notes" rows="3" cols="50"><%= toString(asset.getNotes()) %></textarea>
    </td>
  </tr>
</table>
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.assets.listOfMaterials">List of Materials</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="accounts.assets.materials">Materials</dhv:label>
    </td>
    <td>
      <input type="button" value="<dhv:label name="button.choose">Choose</dhv:label>" onClick="javascript:popAssetMaterialsSelectMultiple('materials','1','lookup_asset_materials','<%= asset.getId() %>',currentMaterialIds, currentMaterialQuantities);" /><br />
      <iframe src="../empty.html" name="server_list" id="server_list" border="0" frameborder="0" width="100%" height="0"></iframe>
      <input type="hidden" name="materials_elements" id="materials_elements" value="" />
    </td>
  </tr>
</table>
<input type="hidden" name="modified" value="<%= asset.getModified() %>" />
<input type="hidden" name="parentId" value="<%= asset.getParentId() == -1 ?(parent != null && parent.getId() != -1 ? parent.getId():-1):asset.getParentId() %>"/>
<%= addHiddenParams(request, "popup|popupType|actionId") %>

<%-- reusable contract form --%>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContractHours.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkInt.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/div.js"></script>
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
    alertMessage = "";
    if (form.serviceContractNumber.value == "") { 
      message += "- Service Contract Number is required\r\n";
      formTest = false;
    }
    if (!checkNumber(form.contractValue.value)) { 
      message += "- Check that Contract Value is entered correcttly\r\n";
      formTest = false;
    }
    if (form.initialStartDate.value == "") { 
      message += "- Initial Contract Date is required\r\n";
      formTest = false;
    }
    
    initialStartDateValid = true;
    currentStartDateValid = true;
    currentEndDateValid = true;
    if ((!form.initialStartDate.value == "") && (!checkDate(form.initialStartDate.value))) {
      message += "- Check that Initial Contract Date is entered correctly\r\n";
      formTest = false;
      initialStartDateValid = false;
    }
    if ((!form.currentStartDate.value == "") && (!checkDate(form.currentStartDate.value))) { 
      message += "- Check that Current Contract Date is entered correctly\r\n";
      formTest = false;
      currentStartDateValid = false;
    }
    if ((!form.currentEndDate.value == "") && (!checkDate(form.currentEndDate.value))) { 
      message += "- Check that Current End Date is entered correctly\r\n";
      formTest = false;
      currentEndDateValid = false;
    }
    
    if ((!form.currentEndDate.value == "") && (currentEndDateValid)){
      if ((!form.initialStartDate.value == "") && (initialStartDateValid) && (form.currentStartDate.value == "")) {
        if (compareDates(form.currentEndDate.value,form.initialStartDate.value) == -1){ 
          message += "- Current End Date should be greater than Initial Contract Date\r\n";
          formTest = false;
        }
      }
      if ((!form.currentStartDate.value == "") && (currentStartDateValid)){ 
        if (compareDates(form.currentEndDate.value,form.currentStartDate.value) == -1){ 
          message += "- Current End Date should be greater than Current Contract Date\r\n";
          formTest = false;
        }
      }
    }
    initialStartDateValid = true;
    currentStartDateValid = true;
    currentEndDateValid = true;

    if (form.responseTime.value < 1){ 
      message += "- Response Time is required\r\n";
      formTest = false;
    }
    if (form.telephoneResponseModel.value < 1){ 
      message += "- Telephone Service is required\r\n";
      formTest = false;
    }
    if (form.onsiteResponseModel.value < 1){ 
      message += "- Onsite Service is required\r\n";
      formTest = false;
    }
    if (form.emailResponseModel.value < 1){ 
      message += "- Email Service is required\r\n";
      formTest = false;
    }
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    }else{
      var test = form.selectedList;
      if (test != null) {
        return selectAllOptions(test);
      }
    }
  }
  
  function clearAdjustment(){
    <%if (serviceContract.getId() == -1){%>
      document.getElementById('hoursRemaining').value = '';
      document.getElementById('totalHoursRemaining').value = '';
    <%}else{%>
      document.getElementById('adjustmentHours').value = '';
      changeDivContent('hours','No adjustment');
      changeDivContent('netRemainingHours','No adjustment');
    <%}%>
    document.getElementById('adjustmentReason').value = '-1';
    changeDivContent('reason','No adjustment');
    document.getElementById('adjustmentNotes').value = '';
    changeDivContent('notes','No adjustment');
  }
</script>
<%-- start details --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>General Information</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Service Contract Number
    </td>
    <td>
      <input type="text" size="7" name="serviceContractNumber" maxlength="8" value="<%= toHtmlValue(serviceContract.getServiceContractNumber()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Contract Value
    </td>
    <td>
      <input type="text" size="12" name="contractValue" maxlength="12" value="<%= ((serviceContract.getContractValue() == -1.0) ? "" : "" + serviceContract.getContractValue()) %>">
      <%= showAttribute(request, "contractValueError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Initial Contract Date
    </td>
    <td>
      <input type="text" size="10" name="initialStartDate" maxlength="10" value="<dhv:tz timestamp="<%= serviceContract.getInitialStartDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>">
      <a href="javascript:popCalendar('addServiceContract', 'initialStartDate');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
      <font color="red">*</font> <%= showAttribute(request, "initialStartDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Current Contract Date
    </td>
    <td>
      <input type="text" size="10" name="currentStartDate" maxlength="10" value="<dhv:tz timestamp="<%= serviceContract.getCurrentStartDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>">
      <a href="javascript:popCalendar('addServiceContract', 'currentStartDate');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Current End Date
    </td>
    <td>
      <input type="text" size="10" name="currentEndDate" maxlength="10" value="<dhv:tz timestamp="<%= serviceContract.getCurrentEndDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>">
      <a href="javascript:popCalendar('addServiceContract', 'currentEndDate');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Category
    </td>
    <td>
    <%= serviceContractCategoryList.getHtmlSelect("category",serviceContract.getCategory()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Type
    </td>
    <td>
    <%= serviceContractTypeList.getHtmlSelect("type",serviceContract.getType()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      Labor Categories
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <select multiple name="selectedList" id="selectedList" size="5">
            <% 
              Iterator itr = serviceContractProductList.iterator();
              if (itr.hasNext()){
                while (itr.hasNext()){
                  ServiceContractProduct scp = (ServiceContractProduct)itr.next(); 
            %>
                <option value="<%=scp.getProductId()%>"><%=toHtml(scp.getProductSku())%></option>
             <%}
             }else{%>
                <option value="-1">None Selected</option>
             <%}%>
            </select>
            <input type="hidden" name="previousSelection" value="" />
          </td>
          <td valign="top">
            &nbsp;[<a href="javascript:popProductCatalogSelectMultiple('selectedList','<%=serviceContract.getId()%>');">Select</a>]
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
      <%= contactList.getHtmlSelect("contactId", serviceContract.getContactId() ) %>
    </td>
	</tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Description
    </td>
    <td>
      <textarea name="description" rows="3" cols="50"><%= toString(serviceContract.getDescription()) %></textarea>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Billing Notes
    </td>
    <td>
      <textarea name="contractBillingNotes" rows="3" cols="50"><%= toString(serviceContract.getContractBillingNotes()) %></textarea>
    </td>
  </tr>
</table>
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Block Hour Information</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
    Total Hours Remaining
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <input type="text" disabled name="hoursRemaining" id="hoursRemaining" size="6" color="#cccccc" value="<%= (serviceContract.getId() == -1) ? "" : "" + serviceContract.getTotalHoursRemaining() %>"/>
        </td>
        <td>
          <input type="hidden" name="totalHoursRemaining" id="totalHoursRemaining" value="<%= (serviceContract.getId() == -1) ? "" : "" + serviceContract.getTotalHoursRemaining() %>"/>
          <dhv:evaluate if="<%= serviceContract.getId() == -1 %>">
          &nbsp [<a href="javascript:popContractHours('totalHoursRemaining','hoursRemaining', 'adjustmentReason','reason','adjustmentNotes','notes');">Adjust</a>]
          &nbsp [<a href="javascript:clearAdjustment();">Clear</a>]
          </dhv:evaluate>
          <dhv:evaluate if="<%= serviceContractHoursHistory.size() > 0 %>">
          &nbsp [<a href="javascript:popURL('AccountsServiceContracts.do?command=HoursHistory&id=<%= serviceContract.getId() %>&popup=true&popupType=inline','Details','650','500','yes','yes');">History</a>]
          </dhv:evaluate>&nbsp;
        </td>
        </tr>
      </table>
    </td>
  </tr>
  <dhv:evaluate if="<%= serviceContract.getId() != -1 %>">
    <tr class="containerBody">
      <td class="formLabel">
        Adjustment Hours
      </td>
      <td>
       <table cellspacing="0" cellpadding="0" border="0" class="empty">
        <tr>
          <td>
            <div id="hours">No adjustment</div>
          </td>
          <td>
            <input type="hidden" name="adjustmentHours" id="adjustmentHours" />
            &nbsp [<a href="javascript:popContractHours('adjustmentHours','hours', 'adjustmentReason','reason','adjustmentNotes','notes');">Adjust</a>]
            &nbsp [<a href="javascript:clearAdjustment();">Clear</a>]
          </td>
        </tr>
      </table>
     </td>
    </tr>
  </dhv:evaluate>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Adjustment Reason
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="reason">No adjustment</div>
        </td>
        <td>
          <input type="hidden" name="adjustmentReason" id="adjustmentReason" />
        </td>
      </tr>
    </table>
   </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Adjustment Notes
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="notes">No adjustment</div>
        </td>
        <td>
          <input type="hidden" name="adjustmentNotes" id="adjustmentNotes" />
        </td>
      </tr>
    </table>
   </td>
  </tr>
  <dhv:evaluate if="<%= serviceContract.getId() != -1 %>">
    <tr class="containerBody">
      <td class="formLabel">
        Hours after Adjustment
      </td>
      <td>
        <div id="netRemainingHours">No adjustment</div>
     </td>
    </tr>
  </dhv:evaluate>
</table>
<br />
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
      <%= responseModelList.getHtmlSelect("responseTime", serviceContract.getResponseTime()) %><font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Telephone Service
    </td>
    <td>
      <%= phoneModelList.getHtmlSelect("telephoneResponseModel", serviceContract.getTelephoneResponseModel()) %><font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
    Onsite Service
    </td>
    <td>
      <%= onsiteModelList.getHtmlSelect("onsiteResponseModel", serviceContract.getOnsiteResponseModel()) %><font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Email Service
    </td>
    <td>
      <%= emailModelList.getHtmlSelect("emailResponseModel", serviceContract.getEmailResponseModel()) %><font color="red">*</font>
    </td>
  </tr>
   <input type="hidden" name="modified" value="<%= serviceContract.getModified() %>" />
</table>
<%= addHiddenParams(request, "popup|popupType|actionId") %>


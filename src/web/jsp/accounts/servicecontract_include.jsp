<%@ page
    import="org.aspcfs.modules.servicecontracts.base.ServiceContractProduct" %>
<%@ page import="java.util.Iterator" %>
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
<jsp:useBean id="SubmiterOrgDetails"
             class="org.aspcfs.modules.accounts.base.Organization"
             scope="request"/>
<%-- reusable contract form --%>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/popLookupSelect.js?1"></script>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/popContractHours.js"></script>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/checkInt.js"></script>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/div.js"></script>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/popAccounts.js"></script>
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
      message += label("check.servicecontract.number", "- Service Contract Number is required\r\n");
      formTest = false;
    }
    if (form.initialStartDate.value == "") {
      message += label("check.init.contract.date", "- Initial Contract Date is required\r\n");
      formTest = false;
    }
    if (form.responseTime.value < 1) {
      message += label("check.response.time", "- Response Time is required\r\n");
      formTest = false;
    }
    if (form.telephoneResponseModel.value < 1) {
      message += label("check.telephone.service", "- Telephone Service is required\r\n");
      formTest = false;
    }
    if (form.onsiteResponseModel.value < 1) {
      message += label("check.onsite.service", "- Onsite Service is required\r\n");
      formTest = false;
    }
    if (form.emailResponseModel.value < 1) {
      message += label("check.email.service", "- Email Service is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      var test = form.selectedList;
      if (test != null) {
        return selectAllOptions(test);
      }
    }
  }

  function clearAdjustment() {
  <%if (serviceContract.getId() == -1){%>
    document.getElementById('hoursRemaining').value = '';
    document.getElementById('totalHoursRemaining').value = '';
  <%}else{%>
    document.getElementById('adjustmentHours').value = '';
    changeDivContent('hours', label("no.adjustment", "No adjustment"));
    changeDivContent('netRemainingHours', label("no.adjustment", "No adjustment"));
  <%}%>
    document.getElementById('adjustmentReason').value = '-1';
    changeDivContent('reason', label("no.adjustment", "No adjustment"));
    document.getElementById('adjustmentNotes').value = '';
    changeDivContent('notes', label("no.adjustment", "No adjustment"));
  }
</script>
<%-- start details --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<tr>
  <th colspan="2">
    <strong><dhv:label name="documents.details.generalInformation">General
      Information</dhv:label></strong>
  </th>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="accounts.accountasset_include.ServiceContractNumber">Service
      Contract Number</dhv:label>
  </td>
  <td>
    <input type="text" size="10" name="serviceContractNumber" maxlength="30"
           value="<%= toHtmlValue(serviceContract.getServiceContractNumber()) %>"><font
      color="red">*</font>
    <%= showAttribute(request, "serviceContractNumberError") %>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="account.sc.contractValue">Contract Value</dhv:label>
  </td>
  <td>
    <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
    <input type="text" name="contractValue" size="15"
           value="<zeroio:number value="<%= serviceContract.getContractValue() %>
    " locale="<%= User.getLocale() %>" />">
    <%= showAttribute(request, "contractValueError") %>
  </td>
</tr>

<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="account.sc.reseller">Reseller</dhv:label>
  </td>
  <td>
    <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="changesubmitter">
            <% if (SubmiterOrgDetails != null && SubmiterOrgDetails.getOrgId() != -1) {%>
            <%= toHtml(SubmiterOrgDetails.getName()) %>
            <%} else {%>
            <dhv:label name="accounts.accounts_add.NoneSelected">None
              Selected</dhv:label>
            <%}%>
          </div>
        </td>
        <td>
          <input type="hidden" name="subId" id="subId"
                 value="<%=  SubmiterOrgDetails.getOrgId() %>"/>
          &nbsp;
          <%= showAttribute(request, "submiterOrgIdError") %>
          [<a
            href="javascript:popAccountsListSingle2('subId','changesubmitter', 'showMyCompany=true&filters=all|my|disabled&orgid=<%=OrgDetails.getId() %>&reverseRelation=true');"><dhv:label
            name="accounts.accounts_add.select">Select</dhv:label></a>]
          [<a
            href="javascript:document.forms['addServiceContract'].subId.value='-1';javascript:changeDivContent('changesubmitter', label('none.selected','None Selected'));"><dhv:label
            name="button.clear">Clear</dhv:label></a>]
        </td>
      </tr>
    </table>
  </td>
</tr>

<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="account.sc.initialContractDate">Initial Contract
      Date</dhv:label>
  </td>
  <td>
    <zeroio:dateSelect form="addServiceContract" field="initialStartDate"
                       timestamp="<%= serviceContract.getInitialStartDate() %>"
                       timeZone="<%=serviceContract.getInitialStartDateTimeZone()%>"
                       showTimeZone="true"/>
    <font color="red">*</font>
    <%= showAttribute(request, "initialStartDateError") %>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="account.sc.currentContractDate">Current Contract
      Date</dhv:label>
  </td>
  <td>
    <zeroio:dateSelect form="addServiceContract" field="currentStartDate"
                       timestamp="<%= serviceContract.getCurrentStartDate() %>"
                       timeZone="<%=serviceContract.getCurrentStartDateTimeZone()%>"
                       showTimeZone="true"/>
    <%= showAttribute(request, "currentStartDateError") %>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="account.sc.currentEndDate">Current End Date</dhv:label>
  </td>
  <td>
    <zeroio:dateSelect form="addServiceContract" field="currentEndDate"
                       timestamp="<%= serviceContract.getCurrentEndDate() %>"
                       timeZone="<%=serviceContract.getCurrentEndDateTimeZone()%>"
                       showTimeZone="true"/>
    <%= showAttribute(request, "currentEndDateError") %>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label
        name="accounts.accountasset_include.Category">Category</dhv:label>
  </td>
  <td>
    <%= serviceContractCategoryList.getHtmlSelect("category", serviceContract.getCategory()) %>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
  </td>
  <td>
    <%= serviceContractTypeList.getHtmlSelect("type", serviceContract.getType()) %>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel" valign="top">
    <dhv:label name="account.sc.laborCategories">Labor Categories</dhv:label>
  </td>
  <td>
    <table border="0" cellspacing="0" cellpadding="0" class="empty">
      <tr>
        <td>
          <select multiple name="selectedList" id="selectedList" size="5">
            <%
              Iterator itr = serviceContract.getServiceContractProductList().iterator();
              if (itr.hasNext()) {
                while (itr.hasNext()) {
                  ServiceContractProduct scp = (ServiceContractProduct) itr.next();
            %>
            <option
                value="<%=scp.getProductId()%>"><%=toHtml(scp.getProductName())%>
            </option>
            <%
              }
            } else {
            %>
            <option value="-1"><dhv:label
                name="accounts.accounts_add.NoneSelected">None
              Selected</dhv:label></option>
            <%}%>
          </select>
          <input type="hidden" name="previousSelection" value=""/>
        </td>
        <td valign="top">
          &nbsp;[<a
            href="javascript:popProductCatalogSelectMultiple('selectedList','<%=serviceContract.getId()%>');"><dhv:label
            name="accounts.accounts_add.select">Select</dhv:label></a>]
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
    <%= contactList.getHtmlSelect("contactId", serviceContract.getContactId()) %>
  </td>
</tr>
<tr class="containerBody">
  <td valign="top" class="formLabel">
    <dhv:label
        name="accounts.accountasset_include.Description">Description</dhv:label>
  </td>
  <td>
    <textarea name="description" rows="3"
              cols="50"><%= toString(serviceContract.getDescription()) %>
    </textarea>
  </td>
</tr>
<tr class="containerBody">
  <td valign="top" class="formLabel">
    <dhv:label name="account.sc.billingNotes">Billing Notes</dhv:label>
  </td>
  <td>
    <textarea name="contractBillingNotes" rows="3"
              cols="50"><%= toString(serviceContract.getContractBillingNotes()) %>
    </textarea>
  </td>
</tr>
</table>
<br/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<tr>
  <th colspan="2">
    <strong><dhv:label name="account.sc.blockHourInformation">Block Hour
      Information</dhv:label></strong>
  </th>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="account.sc.totalHoursRemaining">Total Hours
      Remaining</dhv:label>
  </td>
  <td>
    <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <input type="text" disabled name="hoursRemaining" id="hoursRemaining"
                 size="6" color="#cccccc"
                 value="<%= ((serviceContract.getId() == -1) && (serviceContract.getTotalHoursRemaining() == 0))? "" : "" + serviceContract.getTotalHoursRemaining() %>"/>
        </td>
        <td>
          <input type="hidden" name="totalHoursRemaining"
                 id="totalHoursRemaining"
                 value="<%= ((serviceContract.getId() == -1) && (serviceContract.getTotalHoursRemaining() == 0)) ? "" : "" + serviceContract.getTotalHoursRemaining() %>"/>
          <dhv:evaluate if="<%= serviceContract.getId() == -1 %>">
            &nbsp [<a
              href="javascript:popContractHours('totalHoursRemaining','hoursRemaining', 'adjustmentReason','reason','adjustmentNotes','notes');"><dhv:label
              name="account.sc.adjust">Adjust</dhv:label></a>]
            &nbsp [<a href="javascript:clearAdjustment();"><dhv:label
              name="button.clear">Clear</dhv:label></a>]
          </dhv:evaluate>
          <dhv:evaluate if="<%= serviceContractHoursHistory.size() > 0 %>">
            &nbsp [<a
              href="javascript:popURL('AccountsServiceContracts.do?command=HoursHistory&id=<%= serviceContract.getId() %>&popup=true&popupType=inline','Details','650','500','yes','yes');"><dhv:label
              name="accountsassets.history.long_html">History</dhv:label></a>]
          </dhv:evaluate>&nbsp;
        </td>
      </tr>
    </table>
  </td>
</tr>
<dhv:evaluate if="<%= serviceContract.getId() != -1 %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.adjustmentHours">Adjustment Hours</dhv:label>
    </td>
    <td>
      <table cellspacing="0" cellpadding="0" border="0" class="empty">
        <tr>
          <td>
            <div id="hours">
              <% if (serviceContract.getAdjustmentHours() != 0.0) {%>
              <%= serviceContract.getAdjustmentHours() %>
              <%} else {%>
              <dhv:label name="account.sc.noAdjustment">No
                adjustment</dhv:label>
              <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="adjustmentHours" id="adjustmentHours"
                   value="<%=serviceContract.getAdjustmentHours()%>"/>
            &nbsp [<a
              href="javascript:popContractHours('adjustmentHours','hours', 'adjustmentReason','reason','adjustmentNotes','notes');"><dhv:label
              name="account.sc.adjust">Adjust</dhv:label></a>]
            &nbsp [<a href="javascript:clearAdjustment();"><dhv:label
              name="button.clear">Clear</dhv:label></a>]
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
          <div
              id="reason"><%= toHtml(hoursReasonList.getSelectedValue(serviceContract.getAdjustmentReason())) %>
          </div>
        </td>
        <td>
          <input type="hidden" name="adjustmentReason" id="adjustmentReason"
                 value="<%=serviceContract.getAdjustmentReason()%>"/>
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
          <div
              id="notes"><%=(("".equals(serviceContract.getAdjustmentNotes()) || (serviceContract.getAdjustmentNotes() == null)) ? "No adjustment" : serviceContract.getAdjustmentNotes()) %>
          </div>
        </td>
        <td>
          <input type="hidden" name="adjustmentNotes" id="adjustmentNotes"
                 value="<%=(("".equals(serviceContract.getAdjustmentNotes()) || (serviceContract.getAdjustmentNotes() == null))? "" : serviceContract.getAdjustmentNotes()) %>"/>
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
      <div id="netRemainingHours">
        <% if (serviceContract.getNetHours() != 0.0) {%>
        <%= serviceContract.getNetHours() %>
        <%} else {%>
        <dhv:label name="account.sc.noAdjustment">No adjustment</dhv:label>
        <%}%>
      </div>
      <input type="hidden" name="netHours" id="netHours"
             value="<%=((serviceContract.getNetHours() == 0.0) ? "" : "" + serviceContract.getNetHours())%>"/>
    </td>
  </tr>
</dhv:evaluate>
</table>
<br/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label
          name="accounts.accountasset_include.ServiceModelOptions">Service Model
        Options</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.ResponseTime">Response
        Time</dhv:label>
    </td>
    <td>
      <%= responseModelList.getHtmlSelect("responseTime", serviceContract.getResponseTime()) %><font
        color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.TelephoneService">Telephone
        Service</dhv:label>
    </td>
    <td>
      <%= phoneModelList.getHtmlSelect("telephoneResponseModel", serviceContract.getTelephoneResponseModel()) %><font
        color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.OnsiteService">Onsite
        Service</dhv:label>
    </td>
    <td>
      <%= onsiteModelList.getHtmlSelect("onsiteResponseModel", serviceContract.getOnsiteResponseModel()) %><font
        color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="account.sc.emailSercive">Email Service</dhv:label>
    </td>
    <td>
      <%= emailModelList.getHtmlSelect("emailResponseModel", serviceContract.getEmailResponseModel()) %><font
        color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap valign="top" class="formLabel">
      <dhv:label name="account.sc.serviceModelNotes">Service Model
        Notes</dhv:label>
    </td>
    <td>
      <textarea name="serviceModelNotes" rows="3"
                cols="50"><%= toString(serviceContract.getServiceModelNotes()) %>
      </textarea>
    </td>
  </tr>
  <input type="hidden" name="modified"
         value="<%= serviceContract.getModified() %>"/>
  <input type="hidden" name="trashedDate"
         value="<%= serviceContract.getTrashedDate() %>"/>
</table>
<%= addHiddenParams(request, "popup|popupType|actionId") %>


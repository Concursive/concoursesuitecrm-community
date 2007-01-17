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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/reportSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript">
	function checkForm(form) {
		var test = document.generate.selectedList;
		formTest = true;
		message = "";
		if (checkNullString(form.subject.value)) { 
			message += label("subject.required", "- A subject is required\r\n");
			formTest = false;
		}
		if (formTest == false) {
			alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
			return false;
		} else {
			if (test != null) {
				return selectAllOptions(document.generate.selectedList);
			} else {
				return true;
			}
		}
	}
  function update() {
    if (document.generate.type.options[document.generate.type.selectedIndex].value == 4) {
      javascript:showSpan('new0');
    } else {
      javascript:hideSpan('new0');
    }
  }
</script>
<body onLoad="javascript:document.generate.subject.focus();">
<form name="generate" action="Accounts.do?command=ExportReport" method="post" onSubmit="return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Reports"><dhv:label name="accounts.accounts_relationships_view.ExportData">Export Data</dhv:label></a> >
<dhv:label name="accounts.accounts_reports_generate.NewExport">New Export</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:formMessage showSpace="false" />
<input type="submit" value="<dhv:label name="global.button.Generate">Generate</dhv:label>" />
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Accounts.do?command=Reports';javascript:this.form.submit();" />
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="5">
      <strong><dhv:label name="accounts.accounts_relationships_view.ExportData">Export Data</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td colspan="4">
      <select name="type" onchange="javascript:update()">
        <option value="1"><dhv:label name="accounts.accounts_reports_generate.AllAccounts">All Accounts</dhv:label></option>
<dhv:permission name="accounts-accounts-contacts-view">      
        <option value="2"><dhv:label name="accounts.accounts.with.contacts">Accounts w/Contacts</dhv:label></option>
</dhv:permission>
<dhv:permission name="accounts-accounts-tickets-view">      
        <option value="3"><dhv:label name="accounts.accounts.with.tickets">Accounts w/Tickets</dhv:label></option>
</dhv:permission>
<dhv:permission name="accounts-accounts-documents-view">
<% if (CategoryList.size() > 0) {%>      
        <option value="4"><dhv:label name="accounts.accounts.with.folders">Accounts w/Folders</dhv:label></option>
<% } %>
</dhv:permission>
<dhv:permission name="accounts-accounts-opportunities-view">
        <option value="5"><dhv:label name="accounts.accounts.with.opportunities">Accounts w/Opportunities</dhv:label></option>
</dhv:permission>
      </select>
      <span name="new0" ID="new0" style="display:none">
        &nbsp;:&nbsp;
        <%= CategoryList.getHtmlSelect("catId", 0) %>
      </span>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
  <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
    </td>
    <td colspan="4">
      <input type="text" size="35" name="subject" maxlength="50">&nbsp;<font color="red">*</font>
      <%= showAttribute(request, "subjectError") %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_reports_generate.Criteria">Criteria</dhv:label>
    </td>
    <td colspan="4">
      <select name="criteria1">
      <option value="all"><dhv:label name="accounts.accounts_reports_generate.AllAccounts">All Accounts</dhv:label></option>
      <option value="my"><dhv:label name="accounts.my.accounts">My Accounts</dhv:label></option>
      <option value="levels"><dhv:label name="accounts.accounts_reports_generate.MyAccountHierarchy">My Account Hierarchy</dhv:label></option>
      </select>
    </td>
  </tr>
  <tr>
    <td nowrap valign="top" class="formLabel">
      <dhv:label name="accounts.accounts_reports_generate.SelectFields">Select fields</dhv:label><br>
      <dhv:label name="accounts.accounts_reports_generate.ToInclude">to include</dhv:label>
    </td>
    <td width="50%">
      <select size="5" multiple name="fields">
        <option value="accountNumber"><dhv:label name="organization.accountNumber">Account Number</dhv:label></option>
        <option value="url"><dhv:label name="accounts.accounts_add.WebSiteURL">Web Site URL</dhv:label></option>
        <dhv:include name="organization.ticker" none="true">
          <option value="ticker" ><dhv:label name="accounts.accounts_reports_generate.Ticker">Ticker</dhv:label></option>
        </dhv:include>
        <dhv:include name="organization.stage" none="true">
          <option value="stage" ><dhv:label name="accounts.stage">Stage</dhv:label></option>
        </dhv:include>
        <dhv:include name="organization.employees" none="true">
          <option value="employees" ><dhv:label name="accounts.accounts_reports_generate.Employees">Employees</dhv:label></option>
        </dhv:include>
        <dhv:include name="organization.contractEndDate" none="true">
          <option value="contractEndDate" ><dhv:label name="accounts.accounts_add.ContractEndDate">Contract End Date</dhv:label></option>
        </dhv:include>
        <option value="owner" ><dhv:label name="organization.owner">Account Owner</dhv:label></option>
        <option value="entered" ><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></option>
        <option value="enteredBy" ><dhv:label name="accounts.accounts_calls_list.EnteredBy">Entered By</dhv:label></option>
        <option value="modified" ><dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label></option>
        <option value="modifiedBy" ><dhv:label name="accounts.accounts_fields_list.ModifiedBy">Modified By</dhv:label></option>
        <option value="notes" ><dhv:label name="accounts.accounts_add.Notes">Notes</dhv:label></option>
        <option value="businessname2"><dhv:label name="accounts.accounts_add.business_name_two">Business Name 2</dhv:label></option>
        <option value="dunsnumber"><dhv:label name="accounts.accounts_add.duns_number">DUNS Number</dhv:label></option>
        <option value="SICDesc"><dhv:label name="accounts.accounts_add.sicDescription">SIC Description</dhv:label></option>
        <option value="revenue"><dhv:label name="accounts.accounts_add.Revenue">Revenue</dhv:label></option>
        <option value="yearstarted"><dhv:label name="accounts.accounts_add.year_started">Year Started</dhv:label></option>
        <option value="phone"><dhv:label name="accounts.accounts_add.Phone">Phone</dhv:label></option>        
        <option value="street"><dhv:label name="accounts.accounts_add.AddressLine1">Address Line 1</dhv:label></option>
        <option value="city"><dhv:label name="accounts.accounts_add.City">City</dhv:label></option> 
        <option value="state"><dhv:label name="accounts.accounts_add.StateProvince">State/Province</dhv:label></option>
        <option value="zip"><dhv:label name="accounts.accounts_add.ZipPostalCode">Zip/Postal Code</dhv:label></option>
        <option value="country"><dhv:label name="accounts.accounts_add.Country">Country</dhv:label></option>
        <option value="latitude"><dhv:label name="accounts.address.latitude">Latitude</dhv:label></option>        
        <option value="longitude"><dhv:label name="accounts.address.longitude">Longitude</dhv:label></option>
        <option value="county"><dhv:label name="accounts.address.county">County</dhv:label></option>
      </select>
    </td>
    <td width="25">
      <table width="100%" cellspacing="0" cellpadding="2" border="0">
        <tr>
          <td>
            <input type="button" value="<dhv:label name="accounts.accounts_reports_generate.AllR">All ></dhv:label>" onclick="javascript:allValues()" />
          </td>
        </tr>
        <tr>
          <td>
            <input type="button" value="<dhv:label name="accounts.accounts_reports_generate.AddR">Add ></dhv:label>" onclick="javascript:addValue()" />
          </td>
        </tr>
        <tr>
          <td>
            <input type="button" value="<dhv:label name="accounts.accounts_reports_generate.DelL">< Del</dhv:label>" onclick="javascript:removeValue()" />
          </td>
        </tr>
      </table>
    </td>
    <td align="right" width="50%">
      <select size="5" name="selectedList" multiple>
        <option value="id" ><dhv:label name="accounts.accounts_reports_generate.AccountID">Account ID</dhv:label></option>
        <option value="accountName" ><dhv:label name="organization.name">Account Name</dhv:label></option>
      </select>
    </td>
    <td width="25">
      <table width="100%" cellspacing="0" cellpadding="2" border="0">
        <tr>
          <td valign=center>
            <input type=button value="<dhv:label name="global.button.Up">Up</dhv:label>" onclick="javascript:moveOptionUp(document.generate.selectedList)">
          </td>
        </tr>
        <tr>
          <td valign=center>
            <input type=button value="<dhv:label name="global.button.Down">Down</dhv:label>" onclick="javascript:moveOptionDown(document.generate.selectedList)">
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<br />
<input type="submit" value="<dhv:label name="global.button.Generate">Generate</dhv:label>" />
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Accounts.do?command=Reports';javascript:this.form.submit();" />
</form>
</body>

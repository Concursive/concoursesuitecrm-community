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
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/reportSelect.js"></script>
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
<body onLoad="javascript:document.generate.subject.focus()">
<form name="generate" action="ExternalContacts.do?command=ExportReport" method="post" onSubmit="return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do"><dhv:label name="Contacts" mainMenuItem="true">Contacts</dhv:label></a> > 
<a href="ExternalContacts.do?command=Reports"><dhv:label name="accounts.accounts_relationships_view.ExportData">Export Data</dhv:label></a> >
<dhv:label name="accounts.accounts_reports_generate.NewExport">New Export</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="<dhv:label name="global.button.Generate">Generate</dhv:label>">
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ExternalContacts.do?command=Reports';javascript:this.form.submit();">
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
      <select name="type" onchange='update();'>
      <option value=1><dhv:label name="contact.contactListing">Contact Listing</dhv:label></option>
        <% if (CategoryList.size() > 0) {%>
          <option value="4"><dhv:label name="contact.contactsWithFolders">Contacts w/Folders</dhv:label></option>
        <% } %>
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
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_reports_generate.Criteria">Criteria</dhv:label>
    </td>
    <td colspan="4">
      <select name="criteria1">
      <option value="my"><dhv:label name="contact.myContacts">My Contacts</dhv:label></option>
      <option value="all"><dhv:label name="actionList.allContacts">All Contacts</dhv:label></option>
      <option value="hierarchy"><dhv:label name="contact.controlledHierarchyContacts">Controlled-Hierarchy Contacts</dhv:label></option>
      <option value="public"><dhv:label name="contact.publicContacts">Public Contacts</dhv:label></option>
      <option value="personal"><dhv:label name="contact.personalContacts">Personal Contacts</dhv:label></option>
      </select>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="contact.sorting">Sorting</dhv:label>
    </td>
    <td colspan="4">
      <select name="sort">
        <option value="c.namelast"><dhv:label name="accounts.accounts_add.LastName">Last Name</dhv:label></option>
        <option value="c.contact_id"><dhv:label name="contact.contactId">Contact ID</dhv:label></option>
        <option value="c.namefirst"><dhv:label name="accounts.accounts_add.FirstName">First Name</dhv:label></option>
        <option value="c.org_name"><dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label></option>
        <option value="c.title"><dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label></option>
        <option value="departmentname"><dhv:label name="project.department">Department</dhv:label></option>
        <option value="c.entered"><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></option>
        <option value="c.modified"><dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label></option>
      </select>
    </td>
  </tr>
  <tr>
    <td nowrap valign="top" class="formLabel">
      <dhv:label name="accounts.accounts_reports_generate.SelectFields">Select fields</dhv:label><br>
      <dhv:label name="accounts.accounts_reports_generate.ToInclude">to include</dhv:label>
    </td>
    <td width="50%">
      <select size="5" name="fields">
      <option value="type" ><dhv:label name="accounts.accounts_contacts_add.ContactType">Contact Type</dhv:label></option>
      <option value="nameMiddle" ><dhv:label name="accounts.accounts_add.MiddleName">Middle Name</dhv:label></option>
      <option value="title" ><dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label></option>
      <option value="department" ><dhv:label name="project.department">Department</dhv:label></option>
      <option value="entered" ><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></option>
      <option value="enteredBy" ><dhv:label name="accounts.accounts_calls_list.EnteredBy">Entered By</dhv:label></option>
      <option value="modified" ><dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label></option>
      <option value="modifiedBy" ><dhv:label name="accounts.accounts_fields_list.ModifiedBy">Modified By</dhv:label></option>
      <option value="owner" ><dhv:label name="accounts.accounts_contacts_detailsimport.Owner">Owner</dhv:label></option>
      <option value="businessEmail" ><dhv:label name="contact.businessEmail">Business Email</dhv:label></option>
      <option value="businessPhone" ><dhv:label name="contact.businessPhone">Business Phone</dhv:label></option>
      <option value="businessAddress" ><dhv:label name="contact.businessAddress">Business Address</dhv:label></option>
      <option value="city" ><dhv:label name="accounts.accounts_add.City">City</dhv:label></option>
      <option value="state" ><dhv:label name="contacts.address.state">State</dhv:label></option>
      <option value="zip" ><dhv:label name="accounts.accounts_add.ZipPostalCode">Zip/Postal Code</dhv:label></option>
      <option value="country" ><dhv:label name="accounts.accounts_add.Country">Country</dhv:label></option>
      <option value="notes" ><dhv:label name="accounts.accounts_add.Notes">Notes</dhv:label></option>
      </select>
    </td>
    <td width="25">
      <table width="100%" cellspacing="0" cellpadding="2" border="0" class="empty">
        <tr>
          <td>
            <input type="button" value="<dhv:label name="accounts.accounts_reports_generate.AllR">All ></dhv:label>" onclick="javascript:allValues()">
          </td>
        </tr>
        <tr>
          <td>
            <input type="button" value="<dhv:label name="accounts.accounts_reports_generate.AddR">Add ></dhv:label>" onclick="javascript:addValue()">
          </td>
        </tr>
        <tr>
          <td>
            <input type="button" value="<dhv:label name="accounts.accounts_reports_generate.DelL">< Del</dhv:label>" onclick="javascript:removeValue()">
          </td>
        </tr>
      </table>
    </td>
    <td align="right" width="50%">
      <select size="5" name="selectedList" multiple>
      <option value="id" ><dhv:label name="contact.contactId">Contact ID</dhv:label></option>
      <option value="nameLast" ><dhv:label name="accounts.accounts_add.LastName">Last Name</dhv:label></option>
      <option value="nameFirst" ><dhv:label name="accounts.accounts_add.FirstName">First Name</dhv:label></option>
      <option value="company" ><dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label></option>
      </select>
    </td>
    <td width="25">
	<table width="100%" cellspacing="0" cellpadding="2" border="0" class="empty">
    <tr>
      <td>
        <input type="button" value="<dhv:label name="global.button.Up">Up</dhv:label>" onclick="javascript:moveOptionUp(document.generate.selectedList)">
      </td>
    </tr>
    <tr>
      <td>
        <input type="button" value="<dhv:label name="global.button.Down">Down</dhv:label>" onclick="javascript:moveOptionDown(document.generate.selectedList)">
      </td>
    </tr>
	</table>
</td>
</tr>
</table>
<br>
<input type="submit" value="<dhv:label name="global.button.Generate">Generate</dhv:label>">
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ExternalContacts.do?command=Reports';javascript:this.form.submit();">
</form>
</body>

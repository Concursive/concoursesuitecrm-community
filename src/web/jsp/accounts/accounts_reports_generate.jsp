<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/reportSelect.js"></script>
<script language="JavaScript">
	function checkForm(form) {
		var test = document.generate.selectedList;
		formTest = true;
		message = "";
		if ((form.subject.value == "")) { 
			message += "- A subject is required\r\n";
			formTest = false;
		}
		if (formTest == false) {
			alert("Form could not be saved, please check the following:\r\n\r\n" + message);
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
<body onLoad="javascript:document.forms[0].subject.focus();">
<form name="generate" action="Accounts.do?command=ExportReport" method="post" onSubmit="return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Reports">Export Data</a> >
New Export
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="Generate">
<input type="button" value="Cancel" onClick="javascript:this.form.action='Accounts.do?command=Reports';javascript:this.form.submit();">
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="5">
      <strong>Export Data</strong>
    </th>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Type
    </td>
    <td colspan="4">
      <select name="type" onchange="javascript:update()">
        <option value="1">All Accounts</option>
<dhv:permission name="accounts-accounts-contacts-view">      
        <option value="2"><dhv:label name="accounts.accounts.with.contacts"><dhv:label name="accounts.accounts.with.contacts">Accounts w/Contacts</dhv:label></dhv:label></option>
</dhv:permission>
<dhv:permission name="accounts-accounts-tickets-view">      
        <option value="3"><dhv:label name="accounts.accounts.with.contacts">Accounts w/Tickets</dhv:label></option>
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
      Subject
    </td>
    <td colspan="4">
      <input type="text" size="35" name="subject" maxlength="50">&nbsp;<font color="red">*</font>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Criteria
    </td>
    <td colspan="4">
      <select name="criteria1">
      <option value="all">All Accounts</option>
      <option value="my"><dhv:label name="accounts.my.accounts">My Accounts</dhv:label></option>
      <option value="levels">My Account Hierarchy</option>
      </select>
    </td>
  </tr>
  <tr>
    <td nowrap valign="top" class="formLabel">
      Select fields<br>
      to include
    </td>
    <td width="50%">
      <select size="5" multiple name="fields">
        <option value="accountNumber"><dhv:label name="organization.accountNumber">Account Number</dhv:label></option>
        <option value="url">Web Site URL</option>
        <dhv:include name="organization.ticker" none="true">
          <option value="ticker" >Ticker</option>
        </dhv:include>
        <dhv:include name="organization.employees" none="true">
          <option value="employees" >Employees</option>
        </dhv:include>
        <dhv:include name="organization.contractEndDate" none="true">
          <option value="contractEndDate" >Contract End Date</option>
        </dhv:include>
        <option value="owner" ><dhv:label name="organization.owner">Account Owner</dhv:label></option>
        <option value="entered" >Entered</option>
        <option value="enteredBy" >Entered By</option>
        <option value="modified" >Modified</option>
        <option value="modifiedBy" >Modified By</option>
        <option value="notes" >Notes</option>
      </select>
    </td>
    <td width="25">
      <table width="100%" cellspacing="0" cellpadding="2" border="0">
        <tr>
          <td>
            <input type="button" value="All >" onclick="javascript:allValues()">
          </td>
        </tr>
        <tr>
          <td>
            <input type="button" value="Add >" onclick="javascript:addValue()">
          </td>
        </tr>
        <tr>
          <td>
            <input type="button" value="< Del" onclick="javascript:removeValue()">
          </td>
        </tr>
      </table>
    </td>
    <td align="right" width="50%">
      <select size="5" name="selectedList" multiple>
        <option value="id" >Account ID</option>
        <option value="accountName" ><dhv:label name="organization.name">Account Name</dhv:label></option>
      </select>
    </td>
    <td width="25">
      <table width="100%" cellspacing="0" cellpadding="2" border="0">
        <tr>
          <td valign=center>
            <input type=button value="Up" onclick="javascript:moveOptionUp(document.generate.selectedList)">
          </td>
        </tr>
        <tr>
          <td valign=center>
            <input type=button value="Down" onclick="javascript:moveOptionDown(document.generate.selectedList)">
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<br>
<input type="submit" value="Generate">
<input type="button" value="Cancel" onClick="javascript:this.form.action='Accounts.do?command=Reports';javascript:this.form.submit();">
</form>
</body>

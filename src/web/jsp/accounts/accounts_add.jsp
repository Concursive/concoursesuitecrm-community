<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="IndustryList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="AccountTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="StateSelect" class="com.darkhorseventures.webutils.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="com.darkhorseventures.webutils.CountrySelect" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/popLookupSelect.js"></script>

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
      if ((!form.alertDate.value == "") && (!checkDate(form.alertDate.value))) { 
        message += "- Check that Alert Date is entered correctly\r\n";
        formTest = false;
      }
      if ((!form.alertDate.value == "") && (!checkAlertDate(form.alertDate.value))) { 
        message += "- Check that Alert Date is on or after today's date\r\n";
        formTest = false;
      }
      if ((!form.contractEndDate.value == "") && (!checkDate(form.contractEndDate.value))) { 
        message += "- Check that Contract End Date is entered correctly\r\n";
        formTest = false;
      }
      if ((!form.alertText.value == "") && (form.alertDate.value == "")) { 
        message += "- Please specify an alert date\r\n";
        formTest = false;
      }
      if ((!form.alertDate.value == "") && (form.alertText.value == "")) { 
        message += "- Please specify an alert description\r\n";
        formTest = false;
      }
      if ((!checkPhone(form.phone1number.value)) || (!checkPhone(form.phone2number.value))) { 
        message += "- At least one entered phone number is invalid.  Make sure there are no invalid characters and that you have entered the area code\r\n";
        formTest = false;
      }
      if (formTest == false) {
        alert("Form could not be saved, please check the following:\r\n\r\n" + message);
        return false;
      } else {
        var test = document.addAccount.selectedList;
        if (test != null) {
          return selectAllOptions(document.addAccount.selectedList);
        }
      }
    }
</script>

<body onLoad="javascript:document.forms[0].name.focus();">

<form name="addAccount" action="/Accounts.do?command=Insert&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<a href="/Accounts.do">Account Management</a> > 
Add Account<br>
<hr color="#BFBFBB" noshade>
<input type="submit" value="Insert" name="Save" onClick="this.form.dosubmit.value='true';">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/Accounts.do?command=View';this.form.dosubmit.value='false';">
<input type="reset" value="Reset">
<br>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
      <strong>Add a New Account</strong>
    </td>     
  </tr>
  
   <tr>
    <td nowrap class="formLabel" valign="top">
      Account Type(s)
    </td>
	<td valign=center>
      <select multiple name="selectedList" id="selectedList" size="5">
      <option value="-1">None Selected</option>
			</select>
      <input type="hidden" name="previousSelection" value="">
      <a href="javascript:popLookupSelectMultiple('selectedList','1','lookup_account_types');">Select</a>
  </td>
  
	</tr>
  
  <tr>
    <td nowrap class="formLabel">
      Name
    </td>
    <td>
        <input type=text size=35 name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Account Number
    </td>
    <td>
      <input type=text size=50 name="accountNumber" maxlength=50>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Web Site URL
    </td>
    <td>
      <input type=text size=50 name="url" value="<%= toHtmlValue(OrgDetails.getUrl()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Industry
    </td>
    <td valign=center colspan=1>
      <%=IndustryList.getHtmlSelect("industry",0)%>
    </td>
  </tr>
  
  <dhv:include name="accounts-employees" none="true">
  <tr>
    <td nowrap class="formLabel">
      No. of Employees
    </td>
    <td valign=center colspan=1>
      <input type=text size=10 name="employees">
    </td>
  </tr>
  </dhv:include>
  
  <dhv:include name="accounts-revenue" none="true">
  <tr>
    <td nowrap class="formLabel">
      Revenue
    </td>
    <td valign=center colspan=1>
      <input type=text size=10 name="revenue">
    </td>
  </tr>
  </dhv:include>
  
        <tr>
    <td nowrap class="formLabel">
      Ticker Symbol
    </td>
    <td valign=center colspan=1>
      <input type=text size=10 name="ticker">
    </td>
  </tr>
  
          <tr>
    <td nowrap class="formLabel">
      Contract End Date
    </td>
    <td valign=center colspan=1>
      <input type=text size=10 name="contractEndDate" value="<%= toHtmlValue(OrgDetails.getContractEndDateString()) %>">
      <a href="javascript:popCalendar('addAccount', 'contractEndDate');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
  
            <tr>
    <td nowrap class="formLabel">
      Alert Description
    </td>
    <td valign=center colspan=1>
      <input type=text size=50 name="alertText" value="<%= toHtmlValue(OrgDetails.getAlertText()) %>"><br>
    </td>
  </tr>
  
   <tr>
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td valign=center colspan=1>
              <input type=text size=10 name="alertDate" value="<%= toHtmlValue(OrgDetails.getAlertDateString()) %>">
      <a href="javascript:popCalendar('addAccount', 'alertDate');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
  
  

  
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td valign=center align=left>
	    <strong>Phone Numbers</strong>
	  </td>
  </tr>
  <tr>
    <td>
      <%= OrgPhoneTypeList.getHtmlSelect("phone1type", "Main") %>
      <!--input type=text size=3 name="phone1ac" maxlength=3>-
      <input type=text size=3 name="phone1pre" maxlength=3>- -->
      <input type=text size=20 name="phone1number">&nbsp;ext.
      <input type=text size=5 name="phone1ext" maxlength=10>
    </td>
  </tr>
  <tr>
    <td>
      <%= OrgPhoneTypeList.getHtmlSelect("phone2type", "Fax") %>
      <!--input type=text size=3 name="phone2ac" maxlength=3>- 
      <input type=text size=3 name="phone2pre" maxlength=3>- -->
      <input type=text size=20 name="phone2number">&nbsp;ext.
      <input type=text size=5 name="phone2ext" maxlength=10>
    </td>
  </tr>
</table>
&nbsp;<br>  
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td valign=center align=left colspan="2">
      <strong>Addresses</strong>
    </td>
  </tr>
  <tr>
    <td>
      &nbsp;
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address1type", "Primary") %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Address Line 1
    </td>
    <td>
      <input type=text size=40 name="address1line1" maxlength=80>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Address Line 2
    </td>
    <td>
      <input type=text size=40 name="address1line2" maxlength=80>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      City
    </td>
    <td>
      <input type=text size=28 name="address1city" maxlength=80>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      State/Province
    </td>
    <td>
      <%=StateSelect.getHtml("address1state")%>
      <!--input type=text size=12 name="address1state" maxlength=80-->
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Zip/Postal Code
    </td>
    <td>
      <input type=text size=28 name="address1zip" maxlength=12>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Country
    </td>
    <td>
    <%=CountrySelect.getHtml("address1country")%>
      <!--input type=text size=28 name="address1country" maxlength=80-->
    </td>
  </tr>
  <tr><td colspan="2">&nbsp;</td></tr>
  <tr>
    <td>
      &nbsp;
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address2type", "Auxiliary") %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Address Line 1
    </td>
    <td>
      <input type=text size=40 name="address2line1" maxlength=80>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Address Line 2
    </td>
    <td>
      <input type=text size=40 name="address2line2" maxlength=80>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      City
    </td>
    <td>
      <input type=text size=28 name="address2city" maxlength=80>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      State/Province
    </td>
    <td>
      <%=StateSelect.getHtml("address2state")%>
      <!--input type=text size=12 name="address2state" maxlength=80-->
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Zip/Postal Code
    </td>
    <td>
      <input type=text size=28 name="address2zip" maxlength=12>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Country
    </td>
    <td>
    <%=CountrySelect.getHtml("address2country")%>
      <!--input type=text size=28 name="address2country" maxlength=80-->
    </td>
  </tr>
</table>
&nbsp;<br>  
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td valign=center align=left>
	    <strong>Email Addresses</strong>
	  </td>
  </tr>
  <tr>
    <td>
      <%= OrgEmailTypeList.getHtmlSelect("email1type", "Primary") %>
      <input type=text size=40 name="email1address" maxlength=255>
    </td>
  </tr>
  <tr>
    <td>
      <%= OrgEmailTypeList.getHtmlSelect("email2type", "Auxiliary") %>
      <input type=text size=40 name="email2address" maxlength=255>
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
	    <strong>Additional Details</strong>
	  </td>
  </tr>
  <tr>
    <td valign="top" nowrap class="formLabel">Notes</td>
    <td><TEXTAREA NAME='notes' ROWS=3 COLS=50><%= toString(OrgDetails.getNotes()) %></TEXTAREA></td>
  </tr>
</table>
<br>
<input type="submit" value="Insert" name="Save" onClick="this.form.dosubmit.value='true';">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/Accounts.do?command=View';this.form.dosubmit.value='false';">
<input type="reset" value="Reset">
<input type="hidden" name="dosubmit" value="true">
</form>
</body>

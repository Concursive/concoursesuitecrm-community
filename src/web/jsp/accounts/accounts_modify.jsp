<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.controller.*,com.darkhorseventures.utils.*,com.darkhorseventures.webutils.*" %>
<jsp:useBean id="IndustryList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="AccountTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="UserList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.UserBean" scope="session"/>
<jsp:useBean id="StateSelect" class="com.darkhorseventures.webutils.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="com.darkhorseventures.webutils.CountrySelect" scope="request"/>
<%@ include file="initPage.jsp" %>
<%@ include file="initPageIsManagerOf.jsp" %>
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
      
      <%
      		for (int i=1; i<=(OrgDetails.getPhoneNumberList().size()+1); i++) {
      %>
		<dhv:evaluate exp="<%=(i>1)%>">else </dhv:evaluate>if (!checkPhone(form.phone<%=i%>number.value)) { 
			message += "- At least one entered phone number is invalid.  Make sure there are no invalid characters and that you have entered the area code\r\n";
			formTest = false;
		}
      
      <%}%>
      
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
<form name="addAccount" action="/Accounts.do?command=Update&orgId=<%= OrgDetails.getOrgId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return doCheck(this);" method="post">
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%>
<dhv:evaluate exp="<%= !popUp %>">
<a href="/Accounts.do">Account Management</a> > 

<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="/Accounts.do?command=View">View Accounts</a> >
	<%} else if (request.getParameter("return").equals("dashboard")) {%>
	<a href="/Accounts.do?command=Dashboard">Dashboard</a> >
	<%}%>
<%} else {%>
<a href="/Accounts.do?command=View">View Accounts</a> >
<a href="/Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<%}%>
Modify Account<br>
<hr color="#BFBFBB" noshade>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  
  <dhv:evaluate exp="<%= !popUp %>">
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="details" param="<%= param1 %>" />
    </td>
  </tr>
  </dhv:evaluate>
  
  <tr>
    <td class="containerBack">
<input type="hidden" name="modified" value="<%= OrgDetails.getModified() %>">

<% if (request.getParameter("return") != null) {%>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>

<input type="submit" value="Update" name="Save" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='/Accounts.do?command=View';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';">
<%}%>


<input type="reset" value="Reset">
<dhv:evaluate exp="<%= popUp %>">
  <input type="button" value="Cancel" onclick="javascript:window.close();">
</dhv:evaluate>

<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
    <strong>Modify Primary Information</strong>
    </td>     
  </tr>
<dhv:evaluate exp="<%= OrgDetails.getOwner() == User.getUserId() || isManagerOf(pageContext, User.getUserId(), OrgDetails.getOwner()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Account Owner
    </td>
    <td valign=center>
      <%= UserList.getHtmlSelect("owner", OrgDetails.getOwner() ) %>
    </td>
  </tr>
</dhv:evaluate>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Account Type(s)
    </td>
  
  	<td valign=center>
      
      <select multiple name="selectedList" id="selectedList" size="5">
      <dhv:evaluate exp="<%=OrgDetails.getTypes().isEmpty()%>">
      <option value="-1">None Selected</option>
      </dhv:evaluate>
      
      <dhv:evaluate exp="<%=!(OrgDetails.getTypes().isEmpty())%>">
       <%
        Iterator i = OrgDetails.getTypes().iterator();
        
        while (i.hasNext()) {
          LookupElement thisElt = (LookupElement)i.next();
      %>
        <option value="<%=thisElt.getCode()%>"><%=thisElt.getDescription()%></option>
      <%}%>
      </dhv:evaluate>      
      </select>
      
      <input type="hidden" name="previousSelection" value="">
      <a href="javascript:popLookupSelectMultiple('selectedList','1','lookup_account_types');">Select</a>
  </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Name
    </td>
    <td>
      <input type=text size=35 name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Account Number
    </td>
    <td>
      <input type=text size=50 name="accountNumber" value="<%= toHtmlValue(OrgDetails.getAccountNumber()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      URL
    </td>
    <td>
      <input type=text size=50 name="url" value="<%= toHtmlValue(OrgDetails.getUrl()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Industry
    </td>
    <td>
      <%=IndustryList.getHtmlSelect("industry",OrgDetails.getIndustry())%>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      No. of Employees
    </td>
    <td valign=center colspan=1>
      <input type=text size=10 name="employees" value="<%= OrgDetails.getEmployees() %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Revenue
    </td>
    <td valign=center colspan=1>
      <input type=text size=10 name="revenue" value="<%= OrgDetails.getRevenue() %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Ticker Symbol
    </td>
    <td valign=center colspan=1>
      <input type=text size=10 name="ticker" value="<%= toHtmlValue(OrgDetails.getTicker()) %>">
    </td>
  </tr>
  
            <tr class="containerBody">
   <td nowrap class="formLabel">
      Contract End Date
    </td>
    <td valign=center colspan=1>
      <input type=text size=10 name="contractEndDate" value="<%= toHtmlValue(OrgDetails.getContractEndDateString()) %>">
      <a href="javascript:popCalendar('addAccount', 'contractEndDate');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
  
              <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Description
    </td>
    <td valign=center colspan=1>
      <input type=text size=50 name="alertText" value="<%= toHtmlValue(OrgDetails.getAlertText()) %>"><br>

    </td>
  </tr>
  

      
                    <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td valign=center colspan=1>
              <input type=text size=10 name="alertDate" value="<%= toHtmlValue(OrgDetails.getAlertDateStringLongYear()) %>">
      <a href="javascript:popCalendar('addAccount', 'alertDate');">Date</a> (mm/dd/yyyy)

    </td>
  </tr>
  
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left>
	    <strong>Phone Numbers</strong>
	  </td>
  </tr>
<%  
  int icount = 0;
  Iterator inumber = OrgDetails.getPhoneNumberList().iterator();
  
  while (inumber.hasNext()) {
    ++icount;
    OrganizationPhoneNumber thisPhoneNumber = (OrganizationPhoneNumber)inumber.next();
%>    
  <tr class="containerBody">
    <td>
      <input type="hidden" name="phone<%= icount %>id" value="<%= thisPhoneNumber.getId() %>">
      <%= OrgPhoneTypeList.getHtmlSelect("phone" + icount + "type", thisPhoneNumber.getType()) %>
      <!--input type=text size=3 name="phone<%= icount %>ac" maxlength=3 value="<%= toHtmlValue(thisPhoneNumber.getAreaCode()) %>">-
      <input type=text size=3 name="phone<%= icount %>pre" maxlength=3 value="<%= toHtmlValue(thisPhoneNumber.getPrefix()) %>">-
      <input type=text size=4 name="phone<%= icount %>number" maxlength=4 value="<%= toHtmlValue(thisPhoneNumber.getPostfix()) %>"> ext. -->
      
      <input type=text size=20 name="phone<%= icount %>number" value="<%= toHtmlValue(thisPhoneNumber.getNumber()) %>">&nbsp;ext.
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10" value="<%= toHtmlValue(thisPhoneNumber.getExtension()) %>">
      <input type="checkbox" name="phone<%= icount %>delete" value="on">mark to remove
    </td>
  </tr>    
<%    
  }
%>
  <tr class="containerBody">
    <td>
      <%= OrgPhoneTypeList.getHtmlSelect("phone" + (++icount) + "type", "Main") %>
      <!--input type=text size=3 name="phone<%= icount %>ac" maxlength=3>-
      <input type=text size=3 name="phone<%= icount %>pre" maxlength=3>-
      <input type=text size=4 name="phone<%= icount %>number" maxlength=4>ext. -->
      <input type=text size=20 name="phone<%= icount %>number">&nbsp;ext.
      <input type=text size=5 name="phone<%= icount %>ext" maxlength=10>
    </td>
  </tr>
</table>
&nbsp;<br>  
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left colspan="2">
      <strong>Addresses</strong>
    </td>
  </tr>
<%  
  int acount = 0;
  Iterator anumber = OrgDetails.getAddressList().iterator();
  while (anumber.hasNext()) {
    ++acount;
    OrganizationAddress thisAddress = (OrganizationAddress)anumber.next();
%>    
  <tr class="containerBody">
    <input type="hidden" name="address<%= acount %>id" value="<%= thisAddress.getId() %>">
    <td>
      &nbsp;
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address" + acount + "type", thisAddress.getType()) %>
      <input type="checkbox" name="address<%= acount %>delete" value="on">mark to remove
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 1
    </td>
    <td>
      <input type=text size=40 name="address<%= acount %>line1" maxlength=80 value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 2
    </td>
    <td>
      <input type=text size=40 name="address<%= acount %>line2" maxlength=80 value="<%= toHtmlValue(thisAddress.getStreetAddressLine2()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      City
    </td>
    <td>
      <input type=text size=28 name="address<%= acount %>city" maxlength=80 value="<%= toHtmlValue(thisAddress.getCity()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      State/Province
    </td>
    <td>
      <%=StateSelect.getHtml("address" + acount + "state", thisAddress.getState())%>
      <% StateSelect = new StateSelect(); %>
      <!--input type=text size=28 name="address<%= acount %>state" maxlength=80 value="<%= toHtmlValue(thisAddress.getState()) %>"-->
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Zip/Postal Code
    </td>
    <td>
      <input type=text size=10 name="address<%= acount %>zip" maxlength=12 value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Country
    </td>
    <td>
      <%=CountrySelect.getHtml("address" + acount + "country", thisAddress.getCountry())%>
      <% CountrySelect = new CountrySelect(); %>
      <!--input type=text size=28 name="address<%= acount %>country" maxlength=80 value="<%= toHtmlValue(thisAddress.getCountry()) %>"-->
    </td>
  </tr>
  <tr class="containerBody">
    <td colspan="2">
      &nbsp;
    </td>
  </tr> 
<%    
  }
%>
  <tr class="containerBody">
    <td>
      &nbsp;
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address" + (++acount) + "type", "Primary") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 1
    </td>
    <td>
      <input type=text size=40 name="address<%= acount %>line1" maxlength=80>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 2
    </td>
    <td>
      <input type=text size=40 name="address<%= acount %>line2" maxlength=80>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      City
    </td>
    <td>
      <input type=text size=28 name="address<%= acount %>city" maxlength=80>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      State/Province
    </td>
    <td>
      <%=StateSelect.getHtml("address" + acount + "state")%>
      <% StateSelect = new StateSelect(); %>
      <!--input type=text size=28 name="address<%= acount %>state" maxlength=80-->
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Zip/Postal Code
    </td>
    <td>
      <input type=text size=10 name="address<%= acount %>zip" maxlength=12>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Country
    </td>
    <td>
      <%=CountrySelect.getHtml("address" + acount + "country")%>
      <% CountrySelect = new CountrySelect(); %>
      <!--input type=text size=28 name="address<%= acount %>country" maxlength=80-->
    </td>
  </tr>
</table>
&nbsp;<br>  
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left>
	    <strong>Email Addresses</strong>
	  </td>
  </tr>
<%  
  int ecount = 0;
  Iterator enumber = OrgDetails.getEmailAddressList().iterator();
  while (enumber.hasNext()) {
    ++ecount;
    OrganizationEmailAddress thisEmailAddress = (OrganizationEmailAddress)enumber.next();
%>    
  <tr class="containerBody">
    <td>
      <input type="hidden" name="email<%= ecount %>id" value="<%= thisEmailAddress.getId() %>">
      <%= OrgEmailTypeList.getHtmlSelect("email" + ecount + "type", thisEmailAddress.getType()) %>
      <input type=text size=40 name="email<%= ecount %>address" maxlength=255 value="<%= toHtmlValue(thisEmailAddress.getEmail()) %>">
      <input type="checkbox" name="email<%= ecount %>delete" value="on">mark to remove
    </td>
  </tr>
<%    
  }
%>
  <tr class="containerBody">
    <td>
      <%= OrgEmailTypeList.getHtmlSelect("email" + (++ecount) + "type", "Primary") %>
      <input type=text size=40 name="email<%= ecount %>address" maxlength=255>
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left colspan="2">
      <strong>Additional Details</strong>
    </td>
  </tr>  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Notes
    </td>
    <td>
      <TEXTAREA NAME='notes' ROWS=3 COLS=50><%= toString(OrgDetails.getNotes()) %></TEXTAREA>
    </td>
  </tr>
</table>
<br>
<input type="submit" value="Update" name="Save" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='/Accounts.do?command=View';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';">
<%}%>
<input type="reset" value="Reset">
<dhv:evaluate exp="<%= popUp %>">
  <input type="button" value="Cancel" onclick="javascript:window.close();">
</dhv:evaluate>
<input type="hidden" name="dosubmit" value="true">
  </td>
  </tr>
</table>
</form>

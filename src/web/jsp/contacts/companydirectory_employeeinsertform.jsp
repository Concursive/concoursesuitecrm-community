<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.modules.contacts.base.ContactTypeList" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
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
    if ((!checkPhone(form.phone1number.value)) || (!checkPhone(form.phone2number.value)) || (!checkPhone(form.phone3number.value)) ) { 
      message += "- At least one entered phone number is invalid.  Make sure there are no invalid characters and that you have entered the area code\r\n";
      formTest = false;
    }
    if ((!checkEmail(form.email1address.value)) || (!checkEmail(form.email2address.value))){
      message += "- At least one entered email address is invalid.  Make sure there are no invalid characters\r\n";
      formTest = false;
    }
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    } else {
      var test = document.addEmployee.selectedList;
      if (test != null) {
        return selectAllOptions(document.addEmployee.selectedList);
      }
    }
  }
  function update(countryObj, stateObj) {
  var country = document.forms['addEmployee'].elements[countryObj].value;
   if(country == "UNITED STATES" || country == "CANADA"){
      hideSpan('state2' + stateObj);
      showSpan('state1' + stateObj);
   }else{
      hideSpan('state1' + stateObj);
      showSpan('state2' + stateObj);
    }
  }
</script>
<body onLoad="javascript:document.forms[0].nameFirst.focus();">
  <form name="addEmployee" action="CompanyDirectory.do?command=Save&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<dhv:evaluate exp="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CompanyDirectory.do?command=ListEmployees">Employees</a> >
<a href="CompanyDirectory.do?command=ListEmployees">View Employees</a> >
Add Employee
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
  <input type="hidden" name="empid" value="<%= ContactDetails.getId() %>">
  <input type="hidden" name="id" value="<%= ContactDetails.getId() %>">
  <dhv:evaluate exp="<%= !isPopup(request) %>">
    <input type="submit" value="Save" name="Save" onClick="this.form.dosubmit.value='true';">
    <input type="submit" value="Save & New" onClick="this.form.saveAndNew.value='true';this.form.dosubmit.value='true';">
    <input type="submit" value="Cancel" onClick="javascript:this.form.action='CompanyDirectory.do?command=ListEmployees';this.form.dosubmit.value='false';">
    <input type="hidden" name="dosubmit" value="true">
    </dhv:evaluate>
<br>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Add an Employee Record</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      First Name
    </td>
    <td>
      <input type="text" size="35" name="nameFirst" value="<%= toHtmlValue(ContactDetails.getNameFirst()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
    Middle Name
    </td>
    <td>
      <input type="text" size="35" name="nameMiddle" value="<%= toHtmlValue(ContactDetails.getNameMiddle()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Last Name
    </td>
    <td>
      <input type="text" size="35" name="nameLast" value="<%= toHtmlValue(ContactDetails.getNameLast()) %>">
      <font color="red">*</font> <%= showAttribute(request, "nameLastError") %>
    </td>
  </tr>
  <tr>
      <td class="formLabel" nowrap>Department</td>
      <td>
        <%= DepartmentList.getHtmlSelect("department", ContactDetails.getDepartment()) %>
      </td>
   </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Title
    </td>
    <td>
      <input type="text" size="35" name="title" value="<%= toHtmlValue(ContactDetails.getTitle()) %>">
    </td>
  </tr>
</table>
&nbsp;<br>  
<%--  include basic contact form --%>
<%@ include file="../contacts/contact_include.jsp" %>
<br>
  <input type="submit" value="Save" name="Save" onClick="this.form.dosubmit.value='true';">
  <dhv:evaluate exp="<%= !isPopup(request) %>">
  <input type="submit" value="Save & New" onClick="this.form.saveAndNew.value='true';this.form.dosubmit.value='true';">
  </dhv:evaluate>
  <% if(isPopup(request)){ %>
    <input type="submit" value="Cancel" onClick="javascript:window.close();">
  <% }else{ %>
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='CompanyDirectory.do?command=ListEmployees';this.form.dosubmit.value='false';">
  <input type="hidden" name="dosubmit" value="true">
  <%}%>
  <%= addHiddenParams(request, "popup|source") %>
</form>
</body>

<%@ include file="initPage.jsp" %>

<script language="JavaScript">
  function checkForm(form) {
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
        return true;
      }
    }
</script>

<body onLoad="javascript:document.forms[0].subject.focus();">
<form name="generate" action="/ExternalContacts.do?command=ExportReport" method="post" onSubmit="return checkForm(this);">
<input type="submit" value="Generate">
<input type="button" value="Cancel" onClick="javascript:this.form.action='/ExternalContacts.do?command=Reports';javascript:this.form.submit();">
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
      <strong>Generate a New Report</strong>
    </td>     
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Type
    </td>
    <td>
      <select name="type">
      <option value=1>Contact Listing</option>
      </select>
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Subject
    </td>
    <td>
      <input type=text size=35 name="subject" maxlength=50>
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Criteria
    </td>
    <td>
      <select name="criteria1">
      <option value="my">My Contacts</option>
      <option value="all">All Contacts</option>
      </select>
    </td>
  </tr>
  
  <tr>
    <td nowrap valign=top class="formLabel">
      Included Fields
    </td>
    <td>
      <select size=5 multiple name="fields">
      <option value="id" selected>Contact ID</option>
      <option value="type" selected>Contact Type</option>
      <option value="nameLast" selected>Last Name</option>
      <option value="nameMiddle" selected>Middle Name</option>
      <option value="nameFirst" selected>First Name</option>
      <option value="company" selected>Company</option>
      <option value="title" selected>Title</option>
      <option value="department" selected>Department</option>
      <option value="entered" selected>Entered</option>
      <option value="enteredBy" selected>Entered By</option>
      <option value="modified" selected>Modified</option>
      <option value="modifiedBy" selected>Modified By</option>
      <option value="owner" selected>Owner</option>
      <option value="businessEmail" selected>Business Email</option>
      <option value="businessPhone" selected>Business Phone</option>
      <option value="businessAddress" selected>Business Address</option>
      <option value="city" selected>City</option>
      <option value="state" selected>State</option>
      <option value="zip" selected>Zip</option>
      <option value="country" selected>Country</option>
      <option value="notes" selected>Notes</option>
      </select>
    </td>
  </tr>
  
</table>
<br>
<input type="submit" value="Generate">
<input type="button" value="Cancel" onClick="javascript:this.form.action='/ExternalContacts.do?command=Reports';javascript:this.form.submit();">
</form>
</body>

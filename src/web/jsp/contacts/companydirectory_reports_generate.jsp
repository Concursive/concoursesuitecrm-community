<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/reportSelect.js"></script>
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
</script>

<body onLoad="javascript:document.forms[0].subject.focus();">
<form name="generate" action="/ExternalContacts.do?command=ExportReport" method="post" onSubmit="return checkForm(this);">
<input type="submit" value="Generate">
<input type="button" value="Cancel" onClick="javascript:this.form.action='/ExternalContacts.do?command=Reports';javascript:this.form.submit();">
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=4 valign=center align=left>
      <strong>Generate a New Report</strong>
    </td>     
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Type
    </td>
    <td colspan=3>
      <select name="type">
      <option value=1>Contact Listing</option>
      </select>
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Subject
    </td>
    <td colspan=3>
      <input type=text size=35 name="subject" maxlength=50>
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Criteria
    </td>
    <td colspan=3>
      <select name="criteria1">
      <option value="my">My Contacts</option>
      <option value="all">All Contacts</option>
      </select>
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Sorting
    </td>
    <td colspan=3>
      <select name="sort">
      <option value="c.namelast">Last Name</option>
      <option value="c.contact_id">Contact ID</option>
      <option value="type_name">Contact Type</option>
      <option value="c.namefirst">First Name</option>
      <option value="company">Company</option>
      <option value="c.title">Title</option>
      <option value="departmentname">Department</option>
      <option value="c.entered">Entered</option>
      <option value="c.modified">Modified</option>
      </select>
    </td>
  </tr>
  
  <tr>
    <td nowrap valign=top class="formLabel">
      Select fields to include
    </td>
    <td width=50%>
      <select size=5 name="fields">
      <option value="type" >Contact Type</option>
      <option value="nameMiddle" >Middle Name</option>
      <option value="title" >Title</option>
      <option value="department" >Department</option>
      <option value="entered" >Entered</option>
      <option value="enteredBy" >Entered By</option>
      <option value="modified" >Modified</option>
      <option value="modifiedBy" >Modified By</option>
      <option value="owner" >Owner</option>
      <option value="businessEmail" >Business Email</option>
      <option value="businessPhone" >Business Phone</option>
      <option value="businessAddress" >Business Address</option>
      <option value="city" >City</option>
      <option value="state" >State</option>
      <option value="zip" >Zip</option>
      <option value="country" >Country</option>
      <option value="notes" >Notes</option>
      </select>
     </td>
      <td width=25>
      <table width=100% cellspacing=0 cellpadding=2 border=0>
      <tr><td valign=center>
      <input type="button" value="All >" onclick="javascript:allValues()">
      </td></tr>
      
      <tr><td valign=center>
      <input type="button" value="Add >" onclick="javascript:addValue()">
      </td></tr>
      
      <tr><td valign=center>
      <input type="button" value="< Del" onclick="javascript:removeValue()">
      </td></tr>
      </table>
      </td>
      
      <td width=50%>
      <select size=5 name="selectedList" multiple>
      <option value="id" >Contact ID</option>
      <option value="nameLast" >Last Name</option>
      <option value="nameFirst" >First Name</option>
      <option value="company" >Company</option>
      </select>
      
    </td>
  </tr>
  
</table>
<br>
<input type="submit" value="Generate">
<input type="button" value="Cancel" onClick="javascript:this.form.action='/ExternalContacts.do?command=Reports';javascript:this.form.submit();">
</form>
</body>

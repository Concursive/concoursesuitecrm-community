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
      Report Type
    </td>
    <td>
      <select name="type">
      <option value=1>Contact Listing</option>
      </select>
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Report Subject
    </td>
    <td>
      <input type=text size=35 name="subject" maxlength=50>
    </td>
  </tr>
  
  <!--tr>
    <td nowrap valign=top class="formLabel">
      Fields (select to exclude)
    </td>
    <td>
      <select size=5 multiple name="fields">
      <option value="1">Account Name</option>
      <option value="2">Account No.</option>
      <option value="3">URL</option>
      <option value="4">Ticker</option>
      <option value="5">Employees</option>
      <option value="6">Entered</option>
      <option value="7">Entered By</option>
      <option value="8">Modified</option>
      <option value="9">Modified By</option>
      <option value="10">Owner</option>
      <option value="11">Contract End Date</option>
      <option value="12">Notes</option>
      </select>
    </td>
  </tr-->
  
</table>
<br>
<input type="submit" value="Generate">
<input type="button" value="Cancel" onClick="javascript:this.form.action='/ExternalContacts.do?command=Reports';javascript:this.form.submit();">
</form>
</body>

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
<form name="generate" action="/Leads.do?command=ExportReport" method="post" onSubmit="return checkForm(this);">
<input type="submit" value="Generate">
<input type="button" value="Cancel" onClick="javascript:this.form.action='/Leads.do?command=Reports';javascript:this.form.submit();">
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
      <option value=1>Opportunities Listing</option>
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
  
</table>
<br>
<input type="submit" value="Generate">
<input type="button" value="Cancel" onClick="javascript:this.form.action='/Leads.do?command=Reports';javascript:this.form.submit();">
</form>
</body>

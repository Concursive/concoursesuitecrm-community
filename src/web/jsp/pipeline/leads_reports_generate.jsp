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
      Type
    </td>
    <td>
      <select name="type">
      <option value=1>Opportunities Listing</option>
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
      <option value="my">My Opportunities</option>
      <option value="all">All Opportunities</option>
      </select>
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Sorting
    </td>
    <td>
      <select name="sort">
      <option value="description">Description</option>
      <option value="opp_id">Opportunity ID</option>
      <option value="lowvalue">Low Amount</option>
      <option value="guessvalue">Best Guess Amount</option>
      <option value="highvalue">High Amount</option>
      <option value="closeprob">Prob. of Close</option>
      <option value="closedate">Revenue Start</option>
      <option value="terms">Terms</option>
      <option value="alertdate">Alert Date</option>
      <option value="commission">Commission</option>
      <option value="entered">Entered</option>
      <option value="modified">Modified</option>
      </select>
    </td>
  </tr>
  
  <tr>
    <td nowrap valign=top class="formLabel">
      Included Fields
    </td>
    <td>
      <select size=5 multiple name="fields">
      <option value="id" selected>Opportunity ID</option>
      <option value="description" selected>Description</option>
      <option value="contact" selected>Contact/Organization</option>
      <option value="owner" selected>Owner</option>
      <option value="amount1" selected>Low Amount</option>
      <option value="amount2" selected>Best Guess Amount</option>
      <option value="amount3" selected>High Amount</option>
      <option value="stageName" selected>Stage Name</option>
      <option value="stageDate" selected>Stage Date</option>
      <option value="probability" selected>Prob. of Close</option>
      <option value="revenueStart" selected>Revenue Start</option>
      <option value="terms" selected>Terms</option>
      <option value="alertDate" selected>Alert Date</option>
      <option value="commission" selected>Commission</option>
      <option value="entered" selected>Entered</option>
      <option value="enteredBy" selected>Entered By</option>
      <option value="modified" selected>Modified</option>
      <option value="modifiedBy" selected>Modified By</option>
      </select>
      (CTRL+click to select/de-select)
    </td>
  </tr>
  
</table>
<br>
<input type="submit" value="Generate">
<input type="button" value="Cancel" onClick="javascript:this.form.action='/Leads.do?command=Reports';javascript:this.form.submit();">
</form>
</body>

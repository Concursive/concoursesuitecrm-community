<jsp:useBean id="CategoryList" class="com.darkhorseventures.cfsbase.CustomFieldCategoryList" scope="request"/>
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
    
    function ShowSpan(thisID)
{
	isNS4 = (document.layers) ? true : false;
	isIE4 = (document.all && !document.getElementById) ? true : false;
	isIE5 = (document.all && document.getElementById) ? true : false;
	isNS6 = (!document.all && document.getElementById) ? true : false;
	
	if (isNS4){
	elm = document.layers[thisID];
	}
	else if (isIE4) {
	elm = document.all[thisID];
	}
	else if (isIE5 || isNS6) {
	elm = document.getElementById(thisID);
	elm.style.visibility="visible";
	}
	
	return true;
   
}

function update(){
	if (document.generate.type.selectedIndex == 3) {
		javascript:ShowSpan('new0');
	} else {
		javascript:HideSpan('new0');
	}
}

function HideSpan(thisID)
{
	isNS4 = (document.layers) ? true : false;
	isIE4 = (document.all && !document.getElementById) ? true : false;
	isIE5 = (document.all && document.getElementById) ? true : false;
	isNS6 = (!document.all && document.getElementById) ? true : false;

	if (isNS4){
	elm = document.layers[thisID];
	}
	else if (isIE4) {
	elm = document.all[thisID];
	}
	else if (isIE5 || isNS6) {
	elm = document.getElementById(thisID);
	elm.style.visibility="hidden";
	}
	
	return true;
   
}
    
	function HideSpans()
	{
	isNS = (document.layers) ? true : false;
	isIE = (document.all) ? true : false;
	
	if( (isIE) )
	{
	//document.all.new0.style.visibility="hidden";
	//document.all.new1.style.visibility="hidden";
	//document.all.new2.style.visibility="hidden";
	//document.all.new3.style.visibility="hidden";
	}
	else if( (isNS) )
	{
	document.new0.visibility="hidden";
	document.new1.visibility="hidden";
	document.new2.visibility="hidden";
	document.new3.visibility="hidden";
	}
	
	return true;
	
	}
</script>

<body onLoad="javascript:HideSpans();javascript:document.forms[0].subject.focus();">
<form name="generate" action="/Accounts.do?command=ExportReport" method="post" onSubmit="return checkForm(this);">
<input type="submit" value="Generate">
<input type="button" value="Cancel" onClick="javascript:this.form.action='/Accounts.do?command=Reports';javascript:this.form.submit();">
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
      <select name="type" onchange='update();'>
      <option value="1">All Accounts</option>
      <option value="2">Accounts w/Contacts</option>
      <option value="3">Accounts w/Tickets</option>
      <option value="4">Accounts w/Folders</option>
      <option value="5">Accounts w/Opportunities</option>
      </select>
      
      <span name="new0" ID="new0" style="position:relative; visibility:hidden">&nbsp;:&nbsp;
	<% if (CategoryList.size() > 0) {%>
	<%=CategoryList.getHtmlSelect("catId", 0)%>
	<%}%>
      </span>
      
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
      <option value="my">My Data</option>
      <option value="all">All Data</option>
      </select>
    </td>
  </tr>
  
  <tr>
    <td nowrap valign=top class="formLabel">
      Included Fields
    </td>
    <td>
      <select size=5 multiple name="fields">
      <option value="id" selected>Account ID</option>
      <option value="accountName" selected>Account Name</option>
      <option value="accountNumber" selected>Account No.</option>
      <option value="url" selected>URL</option>
      <option value="ticker" selected>Ticker</option>
      <option value="employees" selected>Employees</option>
      <option value="entered" selected>Entered</option>
      <option value="enteredBy" selected>Entered By</option>
      <option value="modified" selected>Modified</option>
      <option value="modifiedBy" selected>Modified By</option>
      <option value="owner" selected>Owner</option>
      <option value="contractEndDate" selected>Contract End Date</option>
      <option value="notes" selected>Notes</option>
      </select>
      (CTRL+click to select/de-select)
    </td>
  </tr>
  
</table>
<br>
<input type="submit" value="Generate">
<input type="button" value="Cancel" onClick="javascript:this.form.action='/Accounts.do?command=Reports';javascript:this.form.submit();">
</form>
</body>

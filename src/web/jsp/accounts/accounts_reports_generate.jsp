<jsp:useBean id="CategoryList" class="com.darkhorseventures.cfsbase.CustomFieldCategoryList" scope="request"/>
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
    <td colspan=5 valign=center align=left>
      <strong>Generate a New Report</strong>
    </td>     
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Type
    </td>
    <td colspan=4>
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
    <td colspan=4>
      <input type=text size=35 name="subject" maxlength=50>
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Criteria
    </td>
    <td colspan=4>
      <select name="criteria1">
      <option value="all">All Accounts</option>
      <option value="my">My Accounts</option>
      <option value="levels">My Account Hierarchy</option>
      </select>
    </td>
  </tr>
  
  <tr>
    <td nowrap valign=top class="formLabel">
      Select fields to include
    </td>
    <td width=50%>
      <select size=5 multiple name="fields">
      <option value="accountNumber" >Account No.</option>
      <option value="url" >URL</option>
      <option value="ticker" >Ticker</option>
      <option value="employees" >Employees</option>
      <option value="o.entered" >Entered</option>
      <option value="o.enteredBy" >Entered By</option>
      <option value="o.modified" >Modified</option>
      <option value="o.modifiedBy" >Modified By</option>
      <option value="o.owner" >Owner</option>
      <option value="contractEndDate" >Contract End Date</option>
      <option value="o.notes" >Notes</option>
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
      
      <td align=right width=50%>
      <select size=5 name="selectedList" multiple>
      <option value="id" >Account ID</option>
      <option value="accountName" >Account Name</option>
      </select>
      </td>
      
      <td width=25>
	<table width=100% cellspacing=0 cellpadding=2 border=0>
	<tr><td valign=center>
	<input type=button value="Up" onclick="javascript:moveOptionUp(document.generate.selectedList)">
	</td></tr>
	<tr><td valign=center>
	<input type=button value="Down" onclick="javascript:moveOptionDown(document.generate.selectedList)">
	</td></tr>
	</table>
      </td>
  </tr>
  
</table>
<br>
<input type="submit" value="Generate">
<input type="button" value="Cancel" onClick="javascript:this.form.action='/Accounts.do?command=Reports';javascript:this.form.submit();">
</form>
</body>

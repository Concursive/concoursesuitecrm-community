<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/reportSelect.js"></script>
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
<form name="generate" action="TroubleTickets.do?command=ExportReport" method="post" onSubmit="return checkForm(this);">
<a href="TroubleTickets.do">Tickets</a> > 
<a href="TroubleTickets.do?command=Reports">Reports</a> >
Generate New Report<br>
<hr color="#BFBFBB" noshade>
<input type="submit" value="Generate">
<input type="button" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Reports';javascript:this.form.submit();">
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="5">
      <strong>Generate a New Report</strong>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Type
    </td>
    <td colspan="4">
      <select name="type">
        <option value="1">Ticket Listing</option>
      </select>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Subject
    </td>
    <td colspan="4">
      <input type="text" size="35" name="subject" maxlength=50>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Criteria
    </td>
    <td colspan="4">
      <select name="criteria1">
        <option value="assignedToMe">Tickets Assigned to Me</option>
        <option value="unassigned">Open/Unassigned</option>
        <option value="createdByMe">Tickets Created by Me</option>
      </select>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Sorting
    </td>
    <td colspan="4">
      <select name="sort">
        <option value="t.ticketid">Ticket ID</option>
        <option value="t.org_id">Organization Name</option>
      </select>
    </td>
  </tr>
  <tr>
    <td nowrap valign="top" class="formLabel">
      Select fields to include
    </td>
    <td width="50%">
      <select size="5" name="fields">
        <option value="entered" >Entered</option>
        <option value="enteredBy" >Entered By</option>
        <option value="modified" >Modified</option>
        <option value="modifiedBy" >Modified By</option>
        <option value="closed" >Date Closed</option>
        <option value="priority" >Priority</option>
        <option value="severity" >Severity</option>
        <option value="department" >Department</option>
        <option value="source" >Ticket Source</option>
        <option value="solution" >Solution</option>
        <option value="assignedTo" >Assigned To</option>
        <option value="category" >Category</option>
      </select>
     </td>
     <td width="25">
       <table width="100%" cellspacing="0" cellpadding="2" border="0">
         <tr>
           <td>
             <input type="button" value="All >" onclick="javascript:allValues()">
           </td>
         </tr>
         <tr>
           <td>
             <input type="button" value="Add >" onclick="javascript:addValue()">
           </td>
         </tr>
         <tr>
           <td>
             <input type="button" value="< Del" onclick="javascript:removeValue()">
           </td>
         </tr>
      </table>
    </td>
    <td align="right" width="50%">
      <select size="5" name="selectedList" multiple>
        <option value="ticketid" >Ticket ID</option>
        <option value="organization" >Organization Name</option>
        <option value="problem" >Issue</option>
      </select>
    </td>
    <td width="25">
      <table width="100%" cellspacing="0" cellpadding="2" border="0">
        <tr>
          <td>
            <input type="button" value="Up" onclick="javascript:moveOptionUp(document.generate.selectedList)">
          </td>
        </tr>
        <tr>
          <td>
            <input type="button" value="Down" onclick="javascript:moveOptionDown(document.generate.selectedList)">
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<br>
<input type="submit" value="Generate">
<input type="button" value="Cancel" onClick="javascript:this.form.action='ExternalContacts.do?command=Reports';javascript:this.form.submit();">
</form>
</body>

<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/reportSelect.js"></script>
<script language="JavaScript">
	function checkForm(form) {
		var test = document.generate.selectedList;
		formTest = true;
		message = "";
		if (checkNullString(form.subject.value)) { 
			message += label("subject.required", "- A subject is required\r\n");
			formTest = false;
		}
		if (formTest == false) {
			alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
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
<body onLoad="javascript:document.generate.subject.focus();">
<form name="generate" action="TroubleTickets.do?command=ExportReport" method="post" onSubmit="return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTickets.do"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> > 
<a href="TroubleTickets.do?command=Reports"><dhv:label name="accounts.accounts_relationships_view.ExportData">Export Data</dhv:label></a> >
<dhv:label name="accounts.accounts_reports_generate.NewExport">New Export</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="<dhv:label name="global.button.Generate">Generate</dhv:label>">
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='TroubleTickets.do?command=Reports';javascript:this.form.submit();">
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="5">
      <strong><dhv:label name="accounts.accounts_relationships_view.ExportData">Export Data</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td colspan="4">
      <select name="type">
        <option value="1"><dhv:label name="tickets.ticketListing">Ticket Listing</dhv:label></option>
      </select>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
    </td>
    <td colspan="4">
      <input type="text" size="35" name="subject" maxlength=50>&nbsp;<font color="red">*</font>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accounts_reports_generate.Criteria">Criteria</dhv:label>
    </td>
    <td colspan="4">
      <select name="criteria1">
        <option value="assignedToMe"><dhv:label name="tickets.assigned.to.me">Tickets Assigned to Me</dhv:label></option>
        <option value="unassigned"><dhv:label name="tickets.openUnassigned">Open/Unassigned</dhv:label></option>
        <option value="createdByMe"><dhv:label name="tickets.created.by.me">Tickets Created by Me</dhv:label></option>
      </select>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="tickets.sorting">Sorting</dhv:label>
    </td>
    <td colspan="4">
      <select name="sort">
        <option value="t.ticketid"><dhv:label name="reports.myhomepage.tasks.ticketId">Ticket ID</dhv:label></option>
        <option value="o.name"><dhv:label name="accounts.accounts_add.OrganizationName">Organization Name</dhv:label></option>
      </select>
    </td>
  </tr>
  <tr>
    <td nowrap valign="top" class="formLabel">
      <dhv:label name="tickets.selectFieldsToInclude">Select fields to include</dhv:label>
    </td>
    <td width="50%">
      <select size="5" name="fields">
        <option value="location"><dhv:label name="accounts.accountasset_include.Location">Location</dhv:label></option>
        <option value="entered" ><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></option>
        <option value="enteredBy" ><dhv:label name="accounts.accounts_calls_list.EnteredBy">Entered By</dhv:label></option>
        <option value="modified" ><dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label></option>
        <option value="modifiedBy" ><dhv:label name="accounts.accounts_fields_list.ModifiedBy">Modified By</dhv:label></option>
        <option value="closed" ><dhv:label name="reports.helpdesk.ticketsDepartment.dateClosed">Date Closed</dhv:label></option>
        <option value="priority" ><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label></option>
        <option value="severity" ><dhv:label name="project.severity">Severity</dhv:label></option>
        <option value="department" ><dhv:label name="project.department">Department</dhv:label></option>
        <option value="source" ><dhv:label name="tickets.source">Ticket Source</dhv:label></option>
        <option value="solution" ><dhv:label name="accounts.accounts_asset_history.Resolution">Resolution</dhv:label></option>
        <option value="assignedTo" ><dhv:label name="project.resourceAssigned">Resource Assigned</dhv:label></option>
        <option value="category" ><dhv:label name="accounts.accountasset_include.Category">Category</dhv:label></option>
        <option value="contact" ><dhv:label name="reports.myhomepage.tasks.contactName">Contact Name</dhv:label></option>
        <option value="comment" ><dhv:label name="ticket.issueNotes">Issue Notes</dhv:label></option>
        <option value="assignmentDate"><dhv:label name="account.ticket.assignmentDate">Assignment Date</dhv:label></option>
        <option value="estimatedResolutionDate"><dhv:label name="ticket.estimatedResolutionDate">Estimated Resolution Date</dhv:label></option>
        <option value="resolutionDate"><dhv:label name="ticket.resolutionDate">Resolution Date</dhv:label></option>
      </select>
     </td>
     <td width="25">
       <table width="100%" cellspacing="0" cellpadding="2" border="0" class="empty">
         <tr>
           <td>
             <input type="button" value="<dhv:label name="accounts.accounts_reports_generate.AllR">All ></dhv:label>" onclick="javascript:allValues()">
           </td>
         </tr>
         <tr>
           <td>
             <input type="button" value="<dhv:label name="accounts.accounts_reports_generate.AddR">Add ></dhv:label>" onclick="javascript:addValue()">
           </td>
         </tr>
         <tr>
           <td>
             <input type="button" value="<dhv:label name="accounts.accounts_reports_generate.DelL">< Del</dhv:label>" onclick="javascript:removeValue()">
           </td>
         </tr>
      </table>
    </td>
    <td align="right" width="50%">
      <select size="5" name="selectedList" multiple>
        <option value="ticketid" ><dhv:label name="reports.myhomepage.tasks.ticketId">Ticket ID</dhv:label></option>
        <option value="organization" ><dhv:label name="accounts.accounts_add.OrganizationName">Organization Name</dhv:label></option>
        <option value="problem"><dhv:label name="ticket.issue">Issue</dhv:label></option>
      </select>
    </td>
    <td width="25">
      <table width="100%" cellspacing="0" cellpadding="2" border="0" class="empty">
        <tr>
          <td>
            <input type="button" value="<dhv:label name="global.button.Up">Up</dhv:label>" onclick="javascript:moveOptionUp(document.generate.selectedList)">
          </td>
        </tr>
        <tr>
          <td>
            <input type="button" value="<dhv:label name="global.button.Down">Down</dhv:label>" onclick="javascript:moveOptionDown(document.generate.selectedList)">
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<br>
<input type="submit" value="<dhv:label name="global.button.Generate">Generate</dhv:label>">
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='TroubleTickets.do?command=Reports';javascript:this.form.submit();">
</form>
</body>

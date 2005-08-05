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
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/reportSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
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
<form name="generate" action="LeadsReports.do?command=Export" method="post" onSubmit="return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Leads.do"><dhv:label name="pipeline.pipeline">Pipeline</dhv:label></a> > 
<a href="LeadsReports.do?command=ExportList"><dhv:label name="accounts.accounts_relationships_view.ExportData">Export Data</dhv:label></a> > 
<dhv:label name="accounts.accounts_reports_generate.NewExport">New Export</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%-- Display viewpoint info --%>
<dhv:evaluate if="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <dhv:label name="pipeline.viewpoint.colon" param="<%= "username="+PipelineViewpointInfo.getVpUserName() %>"><b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b></dhv:label><br />
  &nbsp;<br>
</dhv:evaluate>
<%-- Begin the container contents --%>
<input type="submit" value="<dhv:label name="global.button.Generate">Generate</dhv:label>">
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='LeadsReports.do?command=ExportList';javascript:this.form.submit();">
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr class="containerBody">
    <th colspan="5">
      <strong><dhv:label name="accounts.accounts_relationships_view.ExportData">Export Data</dhv:label></strong>
    </th>     
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td colspan="4">
      <select name="type">
      <option value="1"><dhv:label name="pipeline.componentListing">Component Listing</dhv:label></option>
      </select>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
  <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
    </td>
    <td colspan="4">
      <input type="text" size="35" name="subject" maxlength="50">&nbsp;<font color="red">*</font>
      <%= showAttribute(request, "subjectError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_reports_generate.Criteria">Criteria</dhv:label>
    </td>
    <td colspan="4">
      <select name="criteria1">
      <option value="my"><dhv:label name="pipeline.myOpportunities">My Opportunities</dhv:label></option>
      <option value="all"><dhv:label name="pipeline.allOpportunities">All Opportunities</dhv:label></option>
      </select>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="tickets.sorting">Sorting</dhv:label>
    </td>
    <td colspan="4">
      <select name="sort">
      <option value="oc.description"><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></option>
      <option value="oc.id"><dhv:label name="pipeline.opportunityId">Opportunity ID</dhv:label></option>
      <option value="oc.lowvalue"><dhv:label name="pipeline.lowAmount">Low Amount</dhv:label></option>
      <option value="oc.guessvalue"><dhv:label name="pipeline.bestGuessAmount">Best Guess Amount</dhv:label></option>
      <option value="oc.highvalue"><dhv:label name="pipeline.highAmount">High Amount</dhv:label></option>
      <option value="oc.closeprob"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.ProbOfClose">Prob. of Close</dhv:label></option>
      <option value="oc.closedate"><dhv:label name="pipeline.revenueStart">Revenue Start</dhv:label></option>
      <option value="oc.terms"><dhv:label name="pipeline.terms">Terms</dhv:label></option>
      <option value="oc.alertdate"><dhv:label name="accounts.accounts_add.AlertDate">Alert Date</dhv:label></option>
      <option value="oc.commission"><dhv:label name="reports.pipeline.commission">Commission</dhv:label></option>
      <option value="oc.entered"><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></option>
      <option value="oc.modified"><dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label></option>
      </select>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap valign="top" class="formLabel">
      <dhv:label name="accounts.accounts_reports_generate.SelectFields">Select fields</dhv:label><br>
      <dhv:label name="accounts.accounts_reports_generate.ToInclude">to include</dhv:label>
    </td>
    <td width="50%">
      <select size="5" multiple name="fields">
      <option value="organization"><dhv:label name="documents.details.organization">Organization</dhv:label></option>
      <option value="contact"><dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label></option>
      <option value="type"><dhv:label name="pipeline.types">Type(s)</dhv:label></option>
      <option value="owner" ><dhv:label name="accounts.accounts_contacts_detailsimport.Owner">Owner</dhv:label></option>
      <option value="amount1" ><dhv:label name="pipeline.lowAmount">Low Amount</dhv:label></option>
      <option value="amount2" ><dhv:label name="pipeline.bestGuessAmount">Best Guess Amount</dhv:label></option>
      <option value="amount3" ><dhv:label name="pipeline.highAmount">High Amount</dhv:label></option>
      <option value="stageName" ><dhv:label name="pipeline.stageName">Stage Name</dhv:label></option>
      <option value="stageDate" ><dhv:label name="pipeline.stageDate">Stage Date</dhv:label></option>
      <option value="probability" ><dhv:label name="accounts.accounts_contacts_oppcomponent_add.ProbOfClose">Prob. of Close</dhv:label></option>
      <option value="revenueStart" ><dhv:label name="pipeline.revenueStart">Revenue Start</dhv:label></option>
      <option value="terms" ><dhv:label name="pipeline.terms">Terms</dhv:label></option>
      <option value="alertDate" ><dhv:label name="accounts.accounts_add.AlertDate">Alert Date</dhv:label></option>
      <option value="commission" ><dhv:label name="reports.pipeline.commission">Commission</dhv:label></option>
      <option value="entered" ><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></option>
      <option value="enteredBy" ><dhv:label name="accounts.accounts_calls_list.EnteredBy">Entered By</dhv:label></option>
      <option value="modified" ><dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label></option>
      <option value="modifiedBy" ><dhv:label name="accounts.accounts_fields_list.ModifiedBy">Modified By</dhv:label></option>
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
    <td width="50%" align="right">
      <select size="5" name="selectedList" multiple>
      <option value="id" ><dhv:label name="pipeline.opportunityId">Opportunity ID</dhv:label></option>
      <option value="description" ><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></option>
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
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='LeadsReports.do?command=ExportList';javascript:this.form.submit();">
<%-- End container contents --%>
</form>
</body>

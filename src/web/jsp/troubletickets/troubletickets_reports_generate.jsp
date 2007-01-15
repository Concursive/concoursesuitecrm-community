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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="subject" class="java.lang.String" scope="request"/>
<jsp:useBean id="exportListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/reportSelect.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/div.js"></script>
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
  function updateCategoryList() {
    var site = document.forms['generate'].elements['searchcodeSiteId'];
    var siteId = -1;
    if ("<%= User.getUserRecord().getSiteId() == -1 && SiteIdList.size() > 1 %>" == "true") {
      siteId = site.options[site.options.selectedIndex].value;
    } else {
      siteId = site.value;
    }
    var url = 'KnowledgeBaseManager.do?command=CategoryJSList&form=generate&reset=true&siteId='+siteId;
    document.forms['generate'].searchcodeCatCode.value = 0;
    document.forms['generate'].searchcodeSubCat1.value = 0;
    document.forms['generate'].searchcodeSubCat2.value = 0;
    document.forms['generate'].searchcodeSubCat3.value = 0;
    window.frames['server_commands'].location.href=url;
  }
  function updateSubList1() {
    var sel = document.forms['generate'].elements['searchcodeCatCode'];
    var value = sel.options[sel.selectedIndex].value;
    var site = document.forms['generate'].elements['searchcodeSiteId'];
    var siteId = -1;
    if ("<%= User.getUserRecord().getSiteId() == -1 && SiteIdList.size() > 1 %>" == "true") {
      siteId = site.options[site.options.selectedIndex].value;
    } else {
      siteId = site.value;
    }
    var url = "KnowledgeBaseManager.do?command=CategoryJSList&form=generate&catCode=" + escape(value)+'&siteId='+siteId;
    if (value == '0') {
      categoryId = -1;
    } else {
      categoryId = value;
    }
    if (value == '0') {
      document.forms['generate'].searchcodeSubCat1.value = 0;
      document.forms['generate'].searchcodeSubCat2.value = 0;
      document.forms['generate'].searchcodeSubCat3.value = 0;
    }
    window.frames['server_commands'].location.href=url;
  }
  function updateSubList2() {
    var sel = document.forms['generate'].elements['searchcodeSubCat1'];
    var value = sel.options[sel.selectedIndex].value;
    var site = document.forms['generate'].elements['searchcodeSiteId'];
    var siteId = -1;
    if ("<%= User.getUserRecord().getSiteId() == -1 && SiteIdList.size() > 1 %>" == "true") {
      siteId = site.options[site.options.selectedIndex].value;
    } else {
      siteId = site.value;
    }
    var url = "KnowledgeBaseManager.do?command=CategoryJSList&form=generate&subCat1=" + escape(value)+'&siteId='+siteId;
    if (value == '0') {
      categoryId = -1;
    } else {
      categoryId = value;
    }
    if (value == '0') {
      document.forms['generate'].searchcodeSubCat2.value = 0;
      document.forms['generate'].searchcodeSubCat3.value = 0;
    }
    window.frames['server_commands'].location.href=url;
  }
<dhv:include name="ticket.subCat2" none="true">
  function updateSubList3() {
    var sel = document.forms['generate'].elements['searchcodeSubCat2'];
    var value = sel.options[sel.selectedIndex].value;
    var site = document.forms['generate'].elements['searchcodeSiteId'];
    var siteId = -1;
    if ("<%= User.getUserRecord().getSiteId() == -1 && SiteIdList.size() > 1 %>" == "true") {
      siteId = site.options[site.options.selectedIndex].value;
    } else {
      siteId = site.value;
    }
    var url = "KnowledgeBaseManager.do?command=CategoryJSList&form=generate&subCat2=" + escape(value)+'&siteId='+siteId;
    if (value == '0') {
      categoryId = -1;
    } else {
      categoryId = value;
    }
    if (value == '0') {
      document.forms['searchKb'].searchcodeSubCat3.value = 0;
    }
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  function updateSubList4() {
    var sel = document.forms['generate'].elements['searchcodeSubCat3'];
    var value = sel.options[sel.selectedIndex].value;
    var site = document.forms['generate'].elements['searchcodeSiteId'];
    var siteId = -1;
    if ("<%= User.getUserRecord().getSiteId() == -1 && SiteIdList.size() > 1 %>" == "true") {
      siteId = site.options[site.options.selectedIndex].value;
    } else {
      siteId = site.value;
    }
    var url = "KnowledgeBaseManager.do?command=CategoryJSList&form=generate&subCat3=" + escape(value)+'&siteId='+siteId;
    if (value == '0') {
      categoryId = -1;
    } else {
      categoryId = value;
    }
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
  
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
      <input type="text" size="35" name="subject" maxlength=50 value="<%= toHtmlValue(subject) %>" />&nbsp;<font color="red">*</font>
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
        <option value="allTickets"><dhv:label name="tickets.all">All Tickets</dhv:label></option>
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
  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>">
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="admin.user.site">Site</dhv:label>
    </td>
    <td colspan="4">
      <dhv:evaluate if="<%= User.getUserRecord().getSiteId() == -1 %>">
        <%= SiteIdList.getHtmlSelect("searchcodeSiteId", (exportListInfo.getSearchOptionValue("searchcodeSiteId") != null? exportListInfo.getSearchOptionValue("searchcodeSiteId"):String.valueOf(User.getUserRecord().getSiteId()))) %>
        <%= showAttribute(request, "searchcodeSiteIdError") %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= User.getUserRecord().getSiteId() > -1 %>">
        <%= toHtml(SiteIdList.getSelectedValue(User.getUserRecord().getSiteId())) %>
        <input type="hidden" id="searchcodeSiteId" name="searchcodeSiteId" value="<%= User.getUserRecord().getSiteId() %>"/>
      </dhv:evaluate>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="searchcodeSiteId" id="searchcodeSiteId" value="-1" />
  </dhv:evaluate>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="tickets.ticketCategoryLevel1">Ticket Category Level 1</dhv:label>
    </td>
    <td colspan="4">
      <%= CategoryList.getHtmlSelect("searchcodeCatCode", exportListInfo.getSearchOptionValue("searchcodeCatCode")) %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="tickets.ticketCategoryLevel2">Ticket Category Level 2</dhv:label>
    </td>
    <td colspan="4">
      <%= SubList1.getHtmlSelect("searchcodeSubCat1", exportListInfo.getSearchOptionValue("searchcodeSubCat1")) %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="tickets.ticketCategoryLevel3">Ticket Category Level 3</dhv:label>
    </td>
    <td colspan="4">
      <%= SubList2.getHtmlSelect("searchcodeSubCat2", exportListInfo.getSearchOptionValue("searchcodeSubCat2")) %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="tickets.ticketCategoryLevel4">Ticket Category Level 4</dhv:label>
    </td>
    <td colspan="4">
      <%= SubList3.getHtmlSelect("searchcodeSubCat3", exportListInfo.getSearchOptionValue("searchcodeSubCat3")) %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="tickets.enteredDateBetween">Entered Date between</dhv:label>
    </td>
    <td colspan="4">
      <zeroio:dateSelect form="generate" field="searchdateEnteredDateStart" timestamp='<%= exportListInfo.getSearchOptionValue("searchdateEnteredDateStart") %>' />
      &nbsp;<dhv:label name="admin.and.lowercase">and</dhv:label> <%=showAttribute(request,"searchdateEnteredDateStartError")%><br>
      <zeroio:dateSelect form="generate" field="searchdateEnteredDateEnd" timestamp='<%= exportListInfo.getSearchOptionValue("searchdateEnteredDateEnd") %>' />
      &nbsp;<%=showAttribute(request,"searchdateEnteredDateEndError")%>
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
        <option value="closed" ><dhv:label name="tickets.closedDate">Closed Date</dhv:label></option>
        <option value="priority" ><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label></option>
        <option value="severity" ><dhv:label name="project.severity">Severity</dhv:label></option>
        <option value="department" ><dhv:label name="project.department">Department</dhv:label></option>
        <option value="source" ><dhv:label name="tickets.source">Ticket Source</dhv:label></option>
        <option value="solution" ><dhv:label name="accounts.accounts_asset_history.Resolution">Resolution</dhv:label></option>
        <option value="assignedTo" ><dhv:label name="project.resourceAssigned">Resource Assigned</dhv:label></option>
        <option value="category" ><dhv:label name="accounts.accountasset_include.Category">Category</dhv:label></option>
        <option value="subcategory1" ><dhv:label name="tickets.subCategory1">Subcategory 1</dhv:label></option>
        <option value="subcategory2" ><dhv:label name="tickets.subCategory2">Subcategory 2</dhv:label></option>
        <option value="subcategory3" ><dhv:label name="tickets.subCategory3">Subcategory 3</dhv:label></option>
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
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</body>

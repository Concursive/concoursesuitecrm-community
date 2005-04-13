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
<%@ page import="java.text.DateFormat" %>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript">
  function updateSubList1() {
    var sel = document.forms['addticket'].elements['catCode'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets.do?command=CategoryJSList&form=addticket&catCode=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  function updateSubList2() {
    var sel = document.forms['addticket'].elements['subCat1'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets.do?command=CategoryJSList&form=addticket&subCat1=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
<dhv:include name="ticket.subCat2" none="true">
  function updateSubList3() {
    var sel = document.forms['addticket'].elements['subCat2'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets.do?command=CategoryJSList&form=addticket&subCat2=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
  function updateUserList() {
    var sel = document.forms['addticket'].elements['departmentCode'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets.do?command=DepartmentJSList&form=addticket&departmentCode=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  function updateContactList() {
  <dhv:include name="ticket.contact" none="true">
    var sel = document.forms['addticket'].elements['orgId'];
    var value = document.forms['addticket'].orgId.value;
    var url = "TroubleTickets.do?command=OrganizationJSList&orgId=" + escape(value);
    window.frames['server_commands'].location.href=url;
  </dhv:include>
  }
  function checkForm(form){
    formTest = true;
    message = "";
    <dhv:include name="ticket.contact" none="true">
    if (form.contactId.value == "-1") { 
      message += label("check.ticket.contact.entered","- Check that a Contact is selected\r\n");
      formTest = false;
    }
    </dhv:include>
    if (form.problem.value == "") { 
      message += label("check.issue.required","- Issue is required\r\n");
      formTest = false;
    }
    <dhv:include name="ticket.resolution" none="true">
    if (form.closeNow.checked && form.solution.value == "") { 
      message += label("check.ticket.resolution.atclose","- Resolution needs to be filled in when closing a ticket\r\n");
      formTest = false;
    }
    </dhv:include>
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      return true;
    }
  }
  //used when a new contact is added
  function insertOption(text,value,optionListId){
   var obj = document.forms['addticket'].contactId;
   insertIndex= obj.options.length;
   obj.options[insertIndex] = new Option(text,value);
   obj.selectedIndex = insertIndex;
  }
  function changeDivContent(divName, divContents) {
    if(document.layers){
      // Netscape 4 or equiv.
      divToChange = document.layers[divName];
      divToChange.document.open();
      divToChange.document.write(divContents);
      divToChange.document.close();
    } else if(document.all){
      // MS IE or equiv.
      divToChange = document.all[divName];
      divToChange.innerHTML = divContents;
    } else if(document.getElementById){
      // Netscape 6 or equiv.
      divToChange = document.getElementById(divName);
      divToChange.innerHTML = divContents;
    }
    //when the content of any of the select items changes, do something here
    <dhv:include name="ticket.contact" none="true">
    if(document.forms['addticket'].orgId.value != '-1'){
      updateContactList();
    }
    </dhv:include>
    //reset the sc and asset
    if (divName == 'changeaccount') {
      <dhv:include name="ticket.contractNumber" none="true">
      changeDivContent('addServiceContract',label('none.selected','None Selected'));
      resetNumericFieldValue('contractId');
      </dhv:include>
      <dhv:include name="ticket.contractNumber" none="true">
      changeDivContent('addAsset',label('none.selected','None Selected'));
      resetNumericFieldValue('assetId');
      </dhv:include>
      <dhv:include name="ticket.laborCategory" none="true">
      changeDivContent('addLaborCategory',label('none.selected','None Selected'));
      resetNumericFieldValue('productId');
      </dhv:include>
    }
  }
  
  function addNewContact(){
    <dhv:permission name="accounts-accounts-contacts-add">
      var acctPermission = true;
    </dhv:permission>
    <dhv:permission name="accounts-accounts-contacts-add" none="true">
      var acctPermission = false;
    </dhv:permission>
    <dhv:permission name="contacts-internal_contacts-add">
      var empPermission = true;
    </dhv:permission>
    <dhv:permission name="contacts-internal_contacts-add" none="true">
      var empPermission = false;
    </dhv:permission>
    var orgId = document.forms['addticket'].orgId.value;
    if(orgId == -1){
      alert(label("select.account.first",'You have to select an Account first'));
      return;
    }
    if(orgId == '0'){
      if (empPermission) {
        popURL('CompanyDirectory.do?command=Prepare&container=false&popup=true&source=troubletickets', 'New_Employee','600','550','yes','yes');
      } else {
        alert(label('no.permission.addemployees','You do not have permission to add employees'));
        return;
      }
    }else{
      if (acctPermission) {
        popURL('Contacts.do?command=Prepare&container=false&popup=true&source=troubletickets&orgId=' + document.forms['addticket'].orgId.value, 'New_Contact','600','550','yes','yes');
      } else {
        alert(label("no.permission.addcontacts","You do not have permission to add contacts"));
        return;
      }
    }
  }
  
 function resetNumericFieldValue(fieldId){
  document.getElementById(fieldId).value = -1;
 }
 
 function setAssignedDate(){
  resetAssignedDate();
  if (document.forms['addticket'].assignedTo.value > 0){
    document.forms['addticket'].assignedDate.value = document.forms['addticket'].currentDate.value;
  }
 }

 function resetAssignedDate(){
    document.forms['addticket'].assignedDate.value = '';
 }
</script>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="tickets.add">Add a new Ticket</dhv:label></strong>
    </th>
	</tr>
	<tr>
    <td class="formLabel">
      <dhv:label name="tickets.source">Ticket Source</dhv:label>
    </td>
    <td>
      <%= SourceList.getHtmlSelect("sourceCode",  TicketDetails.getSourceCode()) %>
    </td>
	</tr>	
	<% if(!"true".equals(request.getParameter("contactSet"))){ %>
  <tr>
    <td class="formLabel">
      <dhv:label name="ticket.organizationLink">Organization</dhv:label>
    </td>
    <td>
      <table cellspacing="0" cellpadding="0" border="0" class="empty">
        <tr>
          <td>
            <div id="changeaccount">
              <% if(TicketDetails.getOrgId() != -1) {%>
                <%= toHtml(TicketDetails.getCompanyName()) %>
              <%} else {%>
                <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
              <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="orgId" id="orgId" value="<%=  TicketDetails.getOrgId() %>">
            &nbsp;<font color="red">*</font>
            <%= showAttribute(request, "orgIdError") %>
            [<a href="javascript:popAccountsListSingle('orgId','changeaccount', 'showMyCompany=true&filters=all|my|disabled');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>	
  <dhv:include name="ticket.contact" none="true">
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label>
    </td>
    <td valign="center">
	<% if (TicketDetails == null || TicketDetails.getOrgId() == -1 || ContactList.size() == 0) { %>
      <%= ContactList.getEmptyHtmlSelect("contactId") %>
	<%} else {%>
      <%= ContactList.getHtmlSelect("contactId", TicketDetails.getContactId() ) %>
	<%}%>
      <font color="red">*</font><%= showAttribute(request, "contactIdError") %>
      [<a href="javascript:addNewContact();"><dhv:label name="account.createNewContact">Create New Contact</dhv:label></a>] 
    </td>
	</tr>
  </dhv:include>
  <dhv:include name="ticket.contractNumber" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.ServiceContractNumber">Service Contract Number</dhv:label>
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="addServiceContract">
            <% if(TicketDetails.getContractId() != -1) {%>
              <%= toHtml(TicketDetails.getServiceContractNumber()) %>
            <%} else {%>
              <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
            <%}%>
          </div>
        </td>
        <td>
          <input type="hidden" name="contractId" id="contractId" value="<%=  TicketDetails.getContractId() %>">
          <input type="hidden" name="serviceContractNumber" id="serviceContractNumber" value="<%= TicketDetails.getServiceContractNumber() %>">
          &nbsp;
          <%= showAttribute(request, "contractIdError") %>
          [<a href="javascript:popServiceContractListSingle('contractId','addServiceContract', 'filters=all|my|disabled');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
          &nbsp [<a href="javascript:changeDivContent('addServiceContract',label('none.selected','None Selected'));javascript:resetNumericFieldValue('contractId');javascript:changeDivContent('addAsset',label('none.selected','None Selected'));javascript:resetNumericFieldValue('assetId');javascript:changeDivContent('addLaborCategory',label('none.selected','None Selected'));javascript:resetNumericFieldValue('productId');"><dhv:label name="button.clear">Clear</dhv:label></a>] 
        </td>
      </tr>
    </table>
   </td>
  </tr>
  </dhv:include>
  <dhv:include name="ticket.asset" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.asset">Asset</dhv:label>
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="addAsset">
            <% if(TicketDetails.getAssetId() != -1) {%>
              <%= toHtml(TicketDetails.getAssetSerialNumber()) %>
            <%} else {%>
              <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
            <%}%>
          </div>
        </td>
        <td>
          <input type="hidden" name="assetId" id="assetId" value="<%=  TicketDetails.getAssetId() %>">
          <input type="hidden" name="assetSerialNumber" id="assetSerialNumber" value="<%=  TicketDetails.getAssetSerialNumber() %>">
          &nbsp;
          <%= showAttribute(request, "assetIdError") %>
          [<a href="javascript:popAssetListSingle('assetId','addAsset', 'filters=allassets|undercontract','contractId','addServiceContract');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
          &nbsp [<a href="javascript:changeDivContent('addAsset',label('none.selected','None Selected'));javascript:resetNumericFieldValue('assetId');"><dhv:label name="button.clear">Clear</dhv:label></a>] 
        </td>
      </tr>
    </table>
   </td>
  </tr>
  </dhv:include>
  <dhv:include name="ticket.laborCategory" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.laborCategory">Labor Category</dhv:label>
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="addLaborCategory">
            <% if(TicketDetails.getProductId() != -1) {%>
              <%= toHtml(TicketDetails.getProductName()) %>
            <%} else {%>
              <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
            <%}%>
          </div>
        </td>
        <td>
          <input type="hidden" name="productId" id="productId" value="<%=  TicketDetails.getProductId() %>">
          <input type="hidden" name="productSku" id="productSku" value="<%=  TicketDetails.getProductSku() %>">
          &nbsp;
          <%= showAttribute(request, "productIdError") %>
          [<a href="javascript:popProductListSingle('productId','addLaborCategory', 'filters=all|my|disabled');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
          &nbsp [<a href="javascript:changeDivContent('addLaborCategory',label('none.selected','None Selected'));javascript:resetNumericFieldValue('productId');"><dhv:label name="button.clear">Clear</dhv:label></a>] 
        </td>
      </tr>
    </table>
   </td>
  </tr>
  </dhv:include>
  <% }else{ %>
    <input type="hidden" name="orgId" value="<%= toHtmlValue(request.getParameter("orgId")) %>">
    <input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
  <% } %>
</table>
<br>
<a name="categories"></a> 
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_add.Classification">Classification</dhv:label></strong>
    </th>
	</tr>
	<tr>
    <td valign="top" class="formLabel">
      <dhv:label name="ticket.issue">Issue</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="problem" cols="55" rows="8"><%= toString(TicketDetails.getProblem()) %></textarea>
            <input type="hidden" name="refresh" value="-1">
            <input type="hidden" name="close" value="">
          </td>
          <td valign="top">
            <font color="red">*</font> <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
      </table>
    </td>
	</tr>
  <dhv:include name="ticket.location" none="true">
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="accounts.accountasset_include.Location">Location</dhv:label>
    </td>
    <td>
      <input type="text" name="location" value="<%= toHtmlValue(TicketDetails.getLocation()) %>" size="50" maxlength="256" />
    </td>
  </tr>
  </dhv:include>
<dhv:include name="ticket.catCode" none="true">
	<tr>
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Category">Category</dhv:label>
    </td>
    <td>
      <%= CategoryList.getHtmlSelect("catCode", TicketDetails.getCatCode()) %>
    </td>
	</tr>
</dhv:include>
<dhv:include name="ticket.subCat1" none="true">
	<tr>
    <td class="formLabel" nowrap>
      <dhv:label name="account.ticket.subLevel1">Sub-level 1</dhv:label>
    </td>
    <td>
      <%= SubList1.getHtmlSelect("subCat1", TicketDetails.getSubCat1()) %>
    </td>
	</tr>
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
	<tr>
    <td class="formLabel" nowrap>
      <dhv:label name="account.ticket.subLevel2">Sub-level 2</dhv:label>
    </td>
    <td>
      <%= SubList2.getHtmlSelect("subCat2", TicketDetails.getSubCat2()) %>
    </td>
	</tr>
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
	<tr>
    <td class="formLabel" nowrap>
      <dhv:label name="account.ticket.subLevel3">Sub-level 3</dhv:label>
    </td>
    <td>
      <%= SubList3.getHtmlSelect("subCat3", TicketDetails.getSubCat3()) %>
    </td>
	</tr>
</dhv:include>
<dhv:include name="ticket.severity" none="true">
	<tr>
    <td class="formLabel">
      <dhv:label name="project.severity">Severity</dhv:label>
    </td>
    <td>
      <%= SeverityList.getHtmlSelect("severityCode",  TicketDetails.getSeverityCode()) %>
    </td>
	</tr>
</dhv:include>
</table>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="project.assignment">Assignment</dhv:label></strong>
    </th>
	</tr>
<dhv:include name="ticket.priority" none="true">
	<tr>
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label>
    </td>
    <td>
      <%= PriorityList.getHtmlSelect("priorityCode", TicketDetails.getPriorityCode()) %>
    </td>
	</tr>
</dhv:include>
	<tr>
    <td class="formLabel">
      <dhv:label name="project.department">Department</dhv:label>
    </td>
    <td>
      <%= DepartmentList.getHtmlSelect("departmentCode", TicketDetails.getDepartmentCode()) %>
    </td>
	</tr>
	<tr>
    <td class="formLabel">
      <dhv:label name="project.resourceAssigned">Resource Assigned</dhv:label>
    </td>
    <td>
      <% UserList.setJsEvent("onChange=\"javascript:setAssignedDate();\"");%>
      <%= UserList.getHtmlSelect("assignedTo", TicketDetails.getAssignedTo() ) %>
    </td>
	</tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="account.ticket.assignmentDate">Assignment Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="assignedDate" timestamp="<%= TicketDetails.getAssignedDate() %>"  timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="true" />
      <%= showAttribute(request, "assignedDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="ticket.estimatedResolutionDate">Estimated Resolution Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="estimatedResolutionDate" timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>"  showTimeZone="true" />
      <%= showAttribute(request, "estimatedResolutionDateError") %>
    </td>
  </tr>
	<tr>
    <td valign="top" class="formLabel">
      <dhv:label name="ticket.issueNotes">Issue Notes</dhv:label>
    </td>
    <td>
      <textarea name="comment" cols="55" rows="5"><%= toString(TicketDetails.getComment()) %></textarea>
    </td>
	</tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_asset_history.Resolution">Resolution</dhv:label></strong>
    </th>
	</tr>
  <dhv:include name="ticket.cause" none="true">
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="account.ticket.cause">Cause</dhv:label>
    </td>
    <td>
      <textarea name="cause" cols="55" rows="8"><%= toString(TicketDetails.getCause()) %></textarea>
    </td>
  </tr>
  </dhv:include>
	<tr>
    <td valign="top" class="formLabel">
      <dhv:label name="ticket.resolution">Resolution</dhv:label>
    </td>
    <td>
      <dhv:include name="ticket.resolution" none="true">
      <textarea name="solution" cols="55" rows="8"><%= toString(TicketDetails.getSolution()) %></textarea><br />
      </dhv:include>
      <input type="checkbox" name="closeNow" value="true" <%= TicketDetails.getCloseIt() ? " checked" : ""%>><dhv:label name="tickets.ticket.close">Close ticket</dhv:label>
      <%--
      <br>
      <input type="checkbox" name="kbase" value="true">Add this solution to Knowledge Base
      --%>
    </td>
	</tr>
  <dhv:include name="ticket.resolution.date" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="ticket.resolutionDate">Resolution Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="resolutionDate" timestamp="<%= TicketDetails.getResolutionDate() %>"  timeZone="<%= TicketDetails.getResolutionDateTimeZone() %>"  showTimeZone="true" />
      <%= showAttribute(request, "resolutionDateError") %>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="ticket.feedback" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.serviceExpectation.question">Have our services met or exceeded your expectations?</dhv:label>
    </td>
    <td>
      <input type="radio" name="expectation" value="1" <%= (TicketDetails.getExpectation() == 1) ? " checked" : "" %>><dhv:label name="account.yes">Yes</dhv:label>
      <input type="radio" name="expectation" value="0" <%= (TicketDetails.getExpectation() == 0) ? " checked" : "" %>><dhv:label name="account.no">No</dhv:label>
      <input type="radio" name="expectation" value="-1" <%= (TicketDetails.getExpectation() == -1) ? " checked" : "" %>><dhv:label name="account.undecided">Undecided</dhv:label>
    </td>
  </tr>
  </dhv:include>
</table>
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>

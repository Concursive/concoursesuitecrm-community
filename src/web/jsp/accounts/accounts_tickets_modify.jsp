<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  -
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="OrgDetails"
             class="org.aspcfs.modules.accounts.base.Organization"
             scope="request"/>
<jsp:useBean id="SubmiterOrgDetails"
             class="org.aspcfs.modules.accounts.base.Organization"
             scope="request"/>
<jsp:useBean id="TicketDetails"
             class="org.aspcfs.modules.troubletickets.base.Ticket"
             scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="CategoryList"
             class="org.aspcfs.modules.troubletickets.base.TicketCategoryList"
             scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList"
             scope="request"/>
<jsp:useBean id="resolvedUserList"
             class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="SubList1"
             class="org.aspcfs.modules.troubletickets.base.TicketCategoryList"
             scope="request"/>
<jsp:useBean id="SubList2"
             class="org.aspcfs.modules.troubletickets.base.TicketCategoryList"
             scope="request"/>
<jsp:useBean id="SubList3"
             class="org.aspcfs.modules.troubletickets.base.TicketCategoryList"
             scope="request"/>
<jsp:useBean id="actionPlans"
             class="org.aspcfs.modules.actionplans.base.ActionPlanList"
             scope="request"/>
<jsp:useBean id="insertActionPlan" class="java.lang.String" scope="request"/>
<jsp:useBean id="SubmitterContact"
             class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ContactList"
             class="org.aspcfs.modules.contacts.base.ContactList"
             scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
             scope="session"/>
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect"
             scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone"
             scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/popLookupSelect.js?1"></script>
<script language="JavaScript">
function updateSubList1() {
  var sel = document.forms['details'].elements['catCode'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=CategoryJSList&form=details&catCode=" + escape(value) + '&orgId=<%= OrgDetails.getOrgId() %>';
  window.frames['server_commands'].location.href = url;
}
function updateSubList2() {
  var sel = document.forms['details'].elements['subCat1'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=CategoryJSList&form=details&subCat1=" + escape(value) + '&orgId=<%= OrgDetails.getOrgId() %>';
  window.frames['server_commands'].location.href = url;
}
<dhv:include name="ticket.subCat2" none="true">
function updateSubList3() {
  var sel = document.forms['details'].elements['subCat2'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=CategoryJSList&form=details&subCat2=" + escape(value) + '&orgId=<%= OrgDetails.getOrgId() %>';
  window.frames['server_commands'].location.href = url;
}
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
function updateSubList4() {
  var sel = document.forms['details'].elements['subCat3'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=CategoryJSList&form=details&subCat3=" + escape(value) + '&orgId=<%= OrgDetails.getOrgId() %>';
  window.frames['server_commands'].location.href = url;
}
</dhv:include>
function updateUserList() {
  var sel = document.forms['details'].elements['departmentCode'];
  var value = sel.options[sel.selectedIndex].value;
  var orgSite = document.forms['details'].elements['orgSiteId'].value;
  var url = "TroubleTickets.do?command=DepartmentJSList&form=details&dept=Assigned&orgSiteId=" + orgSite + "&populateResourceAssigned=true&resourceAssignedDepartmentCode=" + escape(value);
  window.frames['server_commands'].location.href = url;
}
function updateResolvedByUserList() {
  var sel = document.forms['details'].elements['resolvedByDeptCode'];
  var value = sel.options[sel.selectedIndex].value;
  var orgSite = document.forms['details'].elements['orgSiteId'].value;
  var url = "TroubleTickets.do?command=DepartmentJSList&form=details&dept=Resolved&orgSiteId=" + orgSite + "&populateResolvedBy=true&resolvedByDepartmentCode=" + escape(value);
  window.frames['server_commands'].location.href = url;
}
function changeDivContent(divName, divContents) {
  if (document.layers) {
    // Netscape 4 or equiv.
    divToChange = document.layers[divName];
    divToChange.document.open();
    divToChange.document.write(divContents);
    divToChange.document.close();
  } else if (document.all) {
    // MS IE or equiv.
    divToChange = document.all[divName];
    divToChange.innerHTML = divContents;
  } else if (document.getElementById) {
    // Netscape 6 or equiv.
    divToChange = document.getElementById(divName);
    divToChange.innerHTML = divContents;
  }
}
function resetNumericFieldValue(fieldId) {
  document.getElementById(fieldId).value = -1;
}
function checkForm(form) {
  formTest = true;
  message = "";
  if (form.problem.value == "") {
    message += label("check.ticket.issue.entered", "- Check that Issue is entered\r\n");
    formTest = false;
  }
<dhv:include name="ticket.resolution" none="true">
  if (form.closeNow.checked && form.solution.value == "") {
    message += label("check.ticket.resolution.atclose", "- Resolution needs to be filled in when closing a ticket\r\n");
    formTest = false;
  }
</dhv:include>
<dhv:include name="ticket.actionPlans" none="true">
  if (form.insertActionPlan.checked && form.assignedTo.value <= 0) {
    message += label("check.ticket.assignToUser", "- Please assign the ticket to create the related action plan.\r\n");
    formTest = false;
  }
  if (form.insertActionPlan.checked && form.actionPlanId.value <= 0) {
    message += label("check.actionplan", "- Please select an action plan to be inserted.\r\n");
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

function setAssignedDate() {
  resetAssignedDate();
  if (document.forms['details'].assignedTo.value > 0) {
    document.forms['details'].assignedDate.value = document.forms['details'].currentDate.value;
  }
}

function resetAssignedDate() {
  document.forms['details'].assignedDate.value = '';
}

function setField(formField, thisValue, thisForm) {
  var frm = document.forms[thisForm];
  var len = document.forms[thisForm].elements.length;
  var i = 0;
  for (i = 0; i < len; i++) {
    if (frm.elements[i].name.indexOf(formField) != -1) {
      if (thisValue) {
        frm.elements[i].value = "1";
      } else {
        frm.elements[i].value = "0";
      }
    }
  }
}

function selectUserGroups() {
  var siteId = document.forms['details'].orgSiteId.value;
  if ('<%= OrgDetails.getOrgId() %>' != '-1') {
    popUserGroupsListSingle('userGroupId', 'changeUserGroup', '&userId=<%= User.getUserRecord().getId() %>&siteId=' + siteId);
  } else {
    alert(label("select.account.first", 'You have to select an Account first'));
    return;
  }
}

function popKbEntries() {
  var siteId = '<%= OrgDetails.getSiteId() %>';
  var form = document.forms['details'];
  var catCode = form.elements['catCode'];
  var catCodeValue = catCode.options[catCode.selectedIndex].value;
  if (catCodeValue == '0') {
    alert(label('', 'Please select a category first'));
    return;
  }
  var subCat1 = form.elements['subCat1'];
  var subCat1Value = subCat1.options[subCat1.options.selectedIndex].value;
<dhv:include name="ticket.subCat2" none="true">
  var subCat2 = form.elements['subCat2'];
  var subCat2Value = subCat2.options[subCat2.options.selectedIndex].value;
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  var subCat3 = form.elements['subCat3'];
  var subCat3Value = subCat3.options[subCat3.options.selectedIndex].value;
</dhv:include>
  var url = 'KnowledgeBaseManager.do?command=Search&popup=true&searchcodeSiteId=' + siteId + '&searchcodeCatCode=' + catCodeValue;
  url = url + '&searchcodeSubCat1=' + subCat1Value;
<dhv:include name="ticket.subCat2" none="true">
  url = url + '&searchcodeSubCat2=' + subCat2Value;
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  url = url + '&searchcodeSubCat3=' + subCat3Value;
</dhv:include>
  popURL(url, 'KnowledgeBase', '600', '550', 'yes', 'yes');
}
</script>
<body>
<form name="details"
      action="AccountTickets.do?command=UpdateTicket&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>"
      onSubmit="return checkForm(this);" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
  <%-- Trails --%>
  <table class="trails" cellspacing="0">
    <tr>
      <td>
        <a href="Accounts.do"><dhv:label
            name="accounts.accounts">Accounts</dhv:label></a> >
        <a href="Accounts.do?command=Search"><dhv:label
            name="accounts.SearchResults">Search Results</dhv:label></a> >
        <a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label
            name="accounts.details">Account Details</dhv:label></a> >
        <a href="Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label
            name="accounts.tickets.tickets">Tickets</dhv:label></a> >
        <% if (request.getParameter("return") == null) {%>
        <a href="AccountTickets.do?command=TicketDetails&id=<%=TicketDetails.getId()%>"><dhv:label
            name="accounts.tickets.details">Ticket Details</dhv:label></a> >
        <%}%>
        <dhv:label name="accounts.tickets.modify">Modify Ticket</dhv:label>
      </td>
    </tr>
  </table>
  <%-- End Trails --%>
</dhv:evaluate>
<%boolean isPortalUserShowLink = true;%>
<dhv:evaluate if="<%= User.getUserRecord().isPortalUser()%>">
  <dhv:evaluate
      if="<%= TicketDetails.getOrgId()!= TicketDetails.getSubmitterId()%>">
    <% isPortalUserShowLink = false; %>
  </dhv:evaluate>
</dhv:evaluate>

<iframe src="empty.html" name="server_commands" id="server_commands"
        style="visibility:hidden" height="0"></iframe>
<dhv:container name="accounts" selected="tickets" object="OrgDetails"
               param='<%= "orgId=" + OrgDetails.getOrgId() %>'
               appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<dhv:container name="accountstickets" selected="details" object="TicketDetails"
               param='<%= "id=" + TicketDetails.getId() %>'
               appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<%@ include file="accounts_ticket_header_include.jsp" %>
<dhv:evaluate if="<%= !TicketDetails.isTrashed() %>">
  <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
    <dhv:permission name="accounts-accounts-tickets-edit">
      <input type="submit"
             value="<dhv:label name="button.reopen">Reopen</dhv:label>"
             onClick="javascript:this.form.action='AccountTickets.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
    </dhv:permission>
    <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>'>
      <input type="submit"
             value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
             onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'"/>
    </dhv:evaluate>
    <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>'>
      <input type="submit"
             value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
             onClick="javascript:this.form.action='AccountTickets.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'"/>
    </dhv:evaluate>
  </dhv:evaluate>
  <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>">
    <dhv:permission name="accounts-accounts-tickets-edit">
      <input type="submit"
             value="<dhv:label name="global.button.update">Update</dhv:label>"
             onClick="return checkForm(this.form)"/>
    </dhv:permission>
    <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>'>
      <input type="submit"
             value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
             onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'"/>
    </dhv:evaluate>
    <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>'>
      <input type="submit"
             value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
             onClick="javascript:this.form.action='AccountTickets.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'"/>
    </dhv:evaluate>
    <%= showAttribute(request, "closedError") %>
  </dhv:evaluate>
</dhv:evaluate>
<br/>
<dhv:formMessage/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<tr>
  <th colspan="2">
    <strong><dhv:label name="accounts.tickets.information">Ticket
      Information</dhv:label></strong>
  </th>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="accounts.tickets.source">Ticket Source</dhv:label>
  </td>
  <td>
    <%= SourceList.getHtmlSelect("sourceCode", TicketDetails.getSourceCode()) %>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="tickets.ticketState">Ticket State</dhv:label>
  </td>
  <td>
    <%= ticketStateList.getHtmlSelect("stateId", TicketDetails.getStateId()) %>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="accounts.accountasset_include.SubmitterContact">Submitter
      Contact</dhv:label>
  </td>
  <td>
    <% if (TicketDetails.getThisContact() == null) {%>
    <%= ContactList.getHtmlSelect("contactId", 0) %>
    <%} else {%>
    <%= ContactList.getHtmlSelect("contactId", TicketDetails.getContactId()) %>
    <%}%>
    <font color="red">*</font> <%= showAttribute(request, "contactIdError") %>
  </td>
</tr>

<tr class="containerBody">
  <td class="formLabel">
    <dhv:label
        name="accounts.accountasset_include.Organization">Organization</dhv:label>
  </td>
  <td>
    <dhv:evaluate if="<%= isPortalUserShowLink %>">
      <a href="javascript:popURL('Accounts.do?command=Details&orgId=<%= SubmiterOrgDetails.getOrgId() %>&popup=true&viewOnly=true','AccountDetails','650','500','yes','yes');"><%= toHtml(SubmiterOrgDetails.getName()) %>
      </a>
    </dhv:evaluate>
    <dhv:evaluate if="<%= !isPortalUserShowLink %>">
      <%= toHtml(SubmiterOrgDetails.getName()) %>
    </dhv:evaluate>
  </td>
</tr>

<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="accounts.accountasset_include.OrganizationContact">Organization
      Contact</dhv:label>
  </td>
  <td>
    <dhv:evaluate if="<%= isPortalUserShowLink %>">
      <a href="javascript:popURL('ExternalContacts.do?command=ContactDetails&id=<%= SubmitterContact.getId() %>&popup=true&popupType=inline','Details','650','500','yes','yes');"><%= toHtml(SubmitterContact.getNameFull()) %>
      </a>
    </dhv:evaluate>
    <dhv:evaluate if="<%= !isPortalUserShowLink %>">
      <%= toHtml(SubmitterContact.getNameFull()) %>
    </dhv:evaluate>
  </td>
</tr>

<dhv:include name="ticket.contractNumber" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.ServiceContractNumber">Service
        Contract Number</dhv:label>
    </td>
    <td>
      <table cellspacing="0" cellpadding="0" border="0" class="empty">
        <tr>
          <td>
            <div id="addServiceContract">
              <% if (TicketDetails.getContractId() != -1) {%>
              <%= toHtml(TicketDetails.getServiceContractNumber()) %>
              <%} else {%>
              <dhv:label name="accounts.accounts_add.NoneSelected">None
                Selected</dhv:label>
              <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="contractId" id="contractId"
                   value="<%= TicketDetails.getContractId() %>">
            <input type="hidden" name="serviceContractNumber"
                   id="serviceContractNumber"
                   value="<%= TicketDetails.getServiceContractNumber() %>">
            &nbsp;
            <%= showAttribute(request, "contractIdError") %>
            [<a
              href="javascript:popServiceContractListSingle('contractId','addServiceContract', 'filters=all|my|disabled', <%= TicketDetails.getSubmitterId() %>);"><dhv:label
              name="accounts.accounts_add.select">Select</dhv:label></a>]
            &nbsp [<a
              href="javascript:changeDivContent('addServiceContract',label('none.selected','None Selected'));javascript:resetNumericFieldValue('contractId');javascript:changeDivContent('addAsset',label('none.selected','None Selected'));javascript:resetNumericFieldValue('assetId');javascript:changeDivContent('addLaborCategory',label('none.selected','None Selected'));javascript:resetNumericFieldValue('productId');"><dhv:label
              name="button.clear">Clear</dhv:label></a>]
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
              <% if (TicketDetails.getAssetId() != -1) {%>
              <%= toHtml(TicketDetails.getAssetSerialNumber()) %>
              <%} else {%>
              <dhv:label name="accounts.accounts_add.NoneSelected">None
                Selected</dhv:label>
              <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="assetId" id="assetId"
                   value="<%=  TicketDetails.getAssetId() %>">
            <input type="hidden" name="assetSerialNumber" id="assetSerialNumber"
                   value="<%=  TicketDetails.getAssetSerialNumber() %>">
            &nbsp;
            <%= showAttribute(request, "assetIdError") %>
            [<a
              href="javascript:popAssetListSingle('assetId','addAsset', 'filters=allassets|undercontract','contractId','addServiceContract', <%= TicketDetails.getSubmitterId() %>);"><dhv:label
              name="accounts.accounts_add.select">Select</dhv:label></a>]
            &nbsp [<a
              href="javascript:changeDivContent('addAsset',label('none.selected','None Selected'));javascript:resetNumericFieldValue('assetId');"><dhv:label
              name="button.clear">Clear</dhv:label></a>]
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
              <% if (TicketDetails.getProductId() != -1) {%>
              <%= toHtml(TicketDetails.getProductName()) %>
              <%} else {%>
              <dhv:label name="accounts.accounts_add.NoneSelected">None
                Selected</dhv:label>
              <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="productId" id="productId"
                   value="<%=  TicketDetails.getProductId() %>">
            <input type="hidden" name="productSku" id="productSku"
                   value="<%=  TicketDetails.getProductSku() %>">
            &nbsp;
            <%= showAttribute(request, "productIdError") %>
            [<a
              href="javascript:popProductListSingle('productId','addLaborCategory', 'filters=all|my|disabled');"><dhv:label
              name="accounts.accounts_add.select">Select</dhv:label></a>]
            &nbsp [<a
              href="javascript:changeDivContent('addLaborCategory',label('none.selected','None Selected'));javascript:resetNumericFieldValue('productId');"><dhv:label
              name="button.clear">Clear</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
</dhv:include>
</table>
<br/>
<a name="categories"></a>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_add.Classification">Classification</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="ticket.issue">Issue</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="problem" cols="55"
                      rows="8"><%= toString(TicketDetails.getProblem()) %>
            </textarea>
          </td>
          <td valign="top">
            <font
                color="red">*</font> <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label
          name="accounts.accountasset_include.Location">Location</dhv:label>
    </td>
    <td>
      <input type="text" name="location"
             value="<%= toHtmlValue(TicketDetails.getLocation()) %>" size="50"
             maxlength="256"/>
    </td>
  </tr>
  <dhv:include name="ticket.defect" none="true">
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="tickets.defects.defect">Defect</dhv:label>
      </td>
      <td><%= defectSelect.getHtml("defectId", TicketDetails.getDefectId()) %>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="ticket.catCode" none="true">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label
            name="accounts.accountasset_include.Category">Category</dhv:label>
      </td>
      <td>
        <%= CategoryList.getHtmlSelect("catCode", TicketDetails.getCatCode()) %>
        <input type="checkbox" name="autoSetFields" id="autoSetFields"
               value="true"/> <dhv:label
          name="tickets.automaticallyPopulateAssignment.text">Automatically
        populate assignment based on categories</dhv:label>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="ticket.subCat1" none="true">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="account.ticket.subLevel1">Sub-level 1</dhv:label>
      </td>
      <td>
        <%= SubList1.getHtmlSelect("subCat1", TicketDetails.getSubCat1()) %><dhv:permission
          name="tickets-knowledge-base-view">&nbsp;<a
        href="javascript:popKbEntries();"><dhv:label
          name="tickets.knowledgebase.displayKBforSelectedCategories.text">Display
        Knowledge Base for selected Categories</dhv:label></a></dhv:permission>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="ticket.subCat2" none="true">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="account.ticket.subLevel2">Sub-level 2</dhv:label>
      </td>
      <td>
        <%= SubList2.getHtmlSelect("subCat2", TicketDetails.getSubCat2()) %>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="ticket.subCat3" none="true">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="account.ticket.subLevel3">Sub-level 3</dhv:label>
      </td>
      <td>
        <%= SubList3.getHtmlSelect("subCat3", TicketDetails.getSubCat3()) %>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="ticket.actionplans" none="true">
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="sales.actionPlan">Action Plan</dhv:label>
      </td>
      <td>
        <%= actionPlans.getHtmlSelect("actionPlanId", TicketDetails.getActionPlanId()) %>
        <input type="checkbox" name="insertActionPlan"
               value="true" <%= insertActionPlan != null && "true".equals(insertActionPlan) ? "checked" : "" %>/>&nbsp;<dhv:label
          name="actionPlan.activateThisPlan">Activate this plan</dhv:label>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="ticket.severity" none="true">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="project.severity">Severity</dhv:label>
      </td>
      <td>
        <%= SeverityList.getHtmlSelect("severityCode", TicketDetails.getSeverityCode()) %>
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
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label
          name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label>
    </td>
    <td>
      <%= PriorityList.getHtmlSelect("priorityCode", TicketDetails.getPriorityCode()) %>
    </td>
  </tr>
</dhv:include>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="project.department">Department</dhv:label>
  </td>
  <td>
    <%= DepartmentList.getHtmlSelect("departmentCode", TicketDetails.getDepartmentCode()) %>
  </td>
</tr>
<tr class="containerBody">
  <td nowrap class="formLabel">
    <dhv:label name="project.resourceAssigned">Resource Assigned</dhv:label>
  </td>
  <td>
    <% UserList.setJsEvent("onChange=\"javascript:setAssignedDate();\"");%>
    <%= UserList.getHtmlSelect("assignedTo", TicketDetails.getAssignedTo()) %>
  </td>
</tr>
<dhv:include name="tickets.userGroup" none="true">
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="usergroup.assignedGroup">Assigned Group</dhv:label>
    </td>
    <td>
      <table cellspacing="0" cellpadding="0" border="0" class="empty">
        <tr>
          <td>
            <div id="changeUserGroup">
              <dhv:evaluate if="<%= TicketDetails.getUserGroupId() != -1 %>">
                <%= toHtml(TicketDetails.getUserGroupName()) %>
              </dhv:evaluate>
              <dhv:evaluate if="<%= TicketDetails.getUserGroupId() == -1 %>">
                <dhv:label name="accounts.accounts_add.NoneSelected">None
                  Selected</dhv:label>
              </dhv:evaluate>
            </div>
          </td>
          <td>
            <input type="hidden" name="userGroupId" id="userGroupId"
                   value="<%= TicketDetails.getUserGroupId() %>"/> &nbsp;
            [<a href="javascript:selectUserGroups();"><dhv:label
              name="accounts.accounts_add.select">Select</dhv:label></a>] &nbsp;
            [<a
              href="javascript:document.forms['details'].userGroupId.value='-1';javascript:changeDivContent('changeUserGroup', label('none.selected','None Selected'));"><dhv:label
              name="button.clear">Clear</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
</dhv:include>
<tr class="containerBody">
  <td nowrap class="formLabel">
    <dhv:label name="account.ticket.assignmentDate">Assignment Date</dhv:label>
  </td>
  <td>
    <zeroio:dateSelect form="details" field="assignedDate"
                       timestamp="<%= TicketDetails.getAssignedDate() %>"
                       timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>"
                       showTimeZone="true"/>
    <%= showAttribute(request, "assignedDateError") %>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="tickets.escalationLevel">Escalation Level</dhv:label>
  </td>
  <td>
    <%= EscalationList.getHtmlSelect("escalationLevel", TicketDetails.getEscalationLevel()) %>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="ticket.estimatedResolutionDate">Estimated Resolution
      Date</dhv:label>
  </td>
  <td>
    <zeroio:dateSelect form="details" field="estimatedResolutionDate"
                       timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>"
                       timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>"
                       showTimeZone="true"/>
    <%= showAttribute(request, "estimatedResolutionDateError") %>
  </td>
</tr>
<tr class="containerBody">
  <td valign="top" class="formLabel">
    <dhv:label name="ticket.issueNotes">Issue Notes</dhv:label>
  </td>
  <td>
    <table border="0" cellspacing="0" cellpadding="0" class="empty">
      <tr>
        <td valign="top">
          <textarea name="comment" cols="55"
                    rows="5"><%= toString(TicketDetails.getComment()) %>
          </textarea><br/>
          <dhv:label name="tickets.noteAddedtoTicketHistory.brackets">(This note
            is added to the ticket history. Previous notes for this ticket are
            listed under the history tab.)</dhv:label>
        </td>
      </tr>
    </table>
  </td>
</tr>
</table>
<br/>
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
        <textarea name="cause" cols="55"
                  rows="8"><%= toString(TicketDetails.getCause()) %>
        </textarea>
        <dhv:include name="ticket.causeId" none="true"><br/>
          <%= causeList.getHtmlSelect("causeId", TicketDetails.getCauseId()) %>
        </dhv:include>
      </td>
    </tr>
  </dhv:include>
    <%-- Ticket Resolved By Information --%>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="project.department">Department</dhv:label>
    </td>
    <td>
      <%= resolvedByDeptList.getHtmlSelect("resolvedByDeptCode", TicketDetails.getResolvedByDeptCode()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="ticket.resolvedby">Resolved By</dhv:label>
    </td>
    <td>
      <%= resolvedUserList.getHtmlSelect("resolvedBy", TicketDetails.getResolvedBy()) %>
      <br/>
      <input type="checkbox" name="chk1" value="true"
             onclick="javascript:setField('resolvable',document.details.chk1.checked,'details');" <%= TicketDetails.getResolvable() ? " checked" : ""%>><dhv:label
        name="tickets.resolvable">Resolvable</dhv:label>
      <input type="hidden" name="resolvable" value="">
    </td>
  </tr>
    <%-- Ticket Resolved By Information --%>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="ticket.resolution">Resolution</dhv:label>
    </td>
    <td>
      <dhv:include name="ticket.resolution" none="true">
        <textarea name="solution" cols="55"
                  rows="8"><%= toString(TicketDetails.getSolution()) %>
        </textarea><br/></dhv:include>
      <dhv:include name="ticket.resolutionId" none="true">
        <%= resolutionList.getHtmlSelect("resolutionId", TicketDetails.getResolutionId()) %>
        <br/>
      </dhv:include>
      <input type="checkbox" name="closeNow"
             value="true" <%= TicketDetails.getCloseIt() ? " checked" : ""%>><dhv:label
        name="tickets.ticket.close">Close ticket</dhv:label>
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
        <zeroio:dateSelect form="details" field="resolutionDate"
                           timestamp="<%= TicketDetails.getResolutionDate() %>"
                           timeZone="<%= TicketDetails.getResolutionDateTimeZone() %>"
                           showTimeZone="true"/>
        <%= showAttribute(request, "resolutionDateError") %>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="ticket.feedback" none="true">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="account.serviceExpectation.question">Have our services
          met or exceeded your expectations?</dhv:label>
      </td>
      <td>
        <input type="radio" name="expectation"
               value="1" <%= (TicketDetails.getExpectation() == 1) ? " checked" : "" %>><dhv:label
          name="account.yes">Yes</dhv:label>
        <input type="radio" name="expectation"
               value="0" <%= (TicketDetails.getExpectation() == 0) ? " checked" : "" %>><dhv:label
          name="account.no">No</dhv:label>
        <input type="radio" name="expectation"
               value="-1" <%= (TicketDetails.getExpectation() == -1) ? " checked" : "" %>><dhv:label
          name="account.undecided">Undecided</dhv:label>
      </td>
    </tr>
  </dhv:include>
</table>
&nbsp;<br>
<dhv:evaluate if="<%= !TicketDetails.isTrashed() %>">
  <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
    <dhv:permission name="accounts-accounts-tickets-edit">
      <input type="submit"
             value="<dhv:label name="button.reopen">Reopen</dhv:label>"
             onClick="javascript:this.form.action='AccountTickets.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
    </dhv:permission>
    <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>'>
      <input type="submit"
             value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
             onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'"/>
    </dhv:evaluate>
    <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>'>
      <input type="submit"
             value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
             onClick="javascript:this.form.action='AccountTickets.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'"/>
    </dhv:evaluate>
  </dhv:evaluate>
  <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>">
    <dhv:permission name="accounts-accounts-tickets-edit">
      <input type="submit"
             value="<dhv:label name="global.button.update">Update</dhv:label>"
             onClick="return checkForm(this.form)"/>
    </dhv:permission>
    <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>'>
      <input type="submit"
             value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
             onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'"/>
    </dhv:evaluate>
    <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>'>
      <input type="submit"
             value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
             onClick="javascript:this.form.action='AccountTickets.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'"/>
    </dhv:evaluate>
    <%= showAttribute(request, "closedError") %>
  </dhv:evaluate>
</dhv:evaluate>
<input type="hidden" name="modified" value="<%= TicketDetails.getModified() %>">
<input type="hidden" name="orgId" value="<%=TicketDetails.getOrgId()%>">
<input type="hidden" name="orgSiteId" id="orgSiteId"
       value="<%=  TicketDetails.getOrgSiteId() %>"/>
<input type="hidden" name="id" value="<%= TicketDetails.getId() %>">
<input type="hidden" name="companyName"
       value="<%= toHtml(TicketDetails.getCompanyName()) %>">
<input type="hidden" name="statusId"
       value="<%=  TicketDetails.getStatusId() %>"/>
<input type="hidden" name="trashedDate"
       value="<%=  TicketDetails.getTrashedDate() %>"/>
<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="currentDate"
       value="<%=  request.getAttribute("currentDate") %>"/>
</dhv:container>
</dhv:container>
</form>
</body>

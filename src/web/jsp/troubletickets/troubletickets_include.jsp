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
    var url = "TroubleTickets.do?command=CategoryJSList&catCode=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  function updateSubList2() {
    var sel = document.forms['addticket'].elements['subCat1'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets.do?command=CategoryJSList&subCat1=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
<dhv:include name="ticket.subCat2" none="true">
  function updateSubList3() {
    var sel = document.forms['addticket'].elements['subCat2'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets.do?command=CategoryJSList&subCat2=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
  function updateUserList() {
    var sel = document.forms['addticket'].elements['departmentCode'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets.do?command=DepartmentJSList&departmentCode=" + escape(value);
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
      message += "- Check that a Contact is selected\r\n";
      formTest = false;
    }
    </dhv:include>
    if (form.problem.value == "") { 
      message += "- <dhv:label name="ticket.issue">Issue</dhv:label> is required\r\n";
      formTest = false;
    }
    <dhv:include name="ticket.resolution" none="true">
    if (form.closeNow.checked && form.solution.value == "") { 
      message += "- Resolution needs to be filled in when closing a ticket\r\n";
      formTest = false;
    }
    </dhv:include>
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
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
      changeDivContent('addServiceContract','None Selected');
      resetNumericFieldValue('contractId');
      </dhv:include>
      <dhv:include name="ticket.contractNumber" none="true">
      changeDivContent('addAsset','None Selected');
      resetNumericFieldValue('assetId');
      </dhv:include>
      <dhv:include name="ticket.laborCategory" none="true">
      changeDivContent('addLaborCategory','None Selected');
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
      alert('<dhv:label name="accounts.alert.invalidAccount">You have to select an Account first</dhv:label>');
      return;
    }
    if(orgId == '0'){
      if (empPermission) {
        popURL('CompanyDirectory.do?command=Prepare&popup=true&source=troubletickets', 'New_Employee','600','550','yes','yes');
      } else {
        alert('You do not have permission to add employees');
        return;
      }
    }else{
      if (acctPermission) {
        popURL('Contacts.do?command=Prepare&popup=true&source=troubletickets&orgId=' + document.forms['addticket'].orgId.value, 'New_Contact','600','550','yes','yes');
      } else {
        alert('You do not have permission to add contacts');
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
            <div id="changeaccount"><%= TicketDetails.getOrgId() != -1 ? TicketDetails.getCompanyName() : "None Selected" %></div>
          </td>
          <td>
            <input type="hidden" name="orgId" id="orgId" value="<%=  TicketDetails.getOrgId() %>">
            &nbsp;<font color="red">*</font>
            <%= showAttribute(request, "orgIdError") %>
            [<a href="javascript:popAccountsListSingle('orgId','changeaccount', 'showMyCompany=true&filters=all|my|disabled');">Select</a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>	
  <dhv:include name="ticket.contact" none="true">
  <tr>
    <td class="formLabel">
      Contact
    </td>
    <td valign="center">
	<% if (TicketDetails == null || TicketDetails.getOrgId() == -1 || ContactList.size() == 0) { %>
      <%= ContactList.getEmptyHtmlSelect("contactId") %>
	<%} else {%>
      <%= ContactList.getHtmlSelect("contactId", TicketDetails.getContactId() ) %>
	<%}%>
      <font color="red">*</font><%= showAttribute(request, "contactIdError") %>
      [<a href="javascript:addNewContact();">Create New Contact</a>] 
    </td>
	</tr>
  </dhv:include>
  <dhv:include name="ticket.contractNumber" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      Service Contract Number
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="addServiceContract"><%= (TicketDetails.getContractId() != -1) ? TicketDetails.getServiceContractNumber() :"None Selected" %></div>
        </td>
        <td>
          <input type="hidden" name="contractId" id="contractId" value="<%=  TicketDetails.getContractId() %>">
          <input type="hidden" name="serviceContractNumber" id="serviceContractNumber" value="<%= TicketDetails.getServiceContractNumber() %>">
          &nbsp;
          <%= showAttribute(request, "contractIdError") %>
          [<a href="javascript:popServiceContractListSingle('contractId','addServiceContract', 'filters=all|my|disabled');">Select</a>]
          &nbsp [<a href="javascript:changeDivContent('addServiceContract','None Selected');javascript:resetNumericFieldValue('contractId');javascript:changeDivContent('addAsset','None Selected');javascript:resetNumericFieldValue('assetId');javascript:changeDivContent('addLaborCategory','None Selected');javascript:resetNumericFieldValue('productId');">Clear</a>] 
        </td>
      </tr>
    </table>
   </td>
  </tr>
  </dhv:include>
  <dhv:include name="ticket.asset" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      Asset
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="addAsset"><%= (TicketDetails.getAssetId() != -1) ? TicketDetails.getAssetSerialNumber() :"None Selected" %></div>
        </td>
        <td>
          <input type="hidden" name="assetId" id="assetId" value="<%=  TicketDetails.getAssetId() %>">
          <input type="hidden" name="assetSerialNumber" id="assetSerialNumber" value="<%=  TicketDetails.getAssetSerialNumber() %>">
          &nbsp;
          <%= showAttribute(request, "assetIdError") %>
          [<a href="javascript:popAssetListSingle('assetId','addAsset', 'filters=allassets|undercontract','contractId','addServiceContract');">Select</a>]
          &nbsp [<a href="javascript:changeDivContent('addAsset','None Selected');javascript:resetNumericFieldValue('assetId');">Clear</a>] 
        </td>
      </tr>
    </table>
   </td>
  </tr>
  </dhv:include>
  <dhv:include name="ticket.laborCategory" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      Labor Category
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="addLaborCategory"><%= (TicketDetails.getProductId() != -1) ? TicketDetails.getProductSku() :"None Selected" %></div>
        </td>
        <td>
          <input type="hidden" name="productId" id="productId" value="<%=  TicketDetails.getProductId() %>">
          <input type="hidden" name="productSku" id="productSku" value="<%=  TicketDetails.getProductSku() %>">
          &nbsp;
          <%= showAttribute(request, "productIdError") %>
          [<a href="javascript:popProductListSingle('productId','addLaborCategory', 'filters=all|my|disabled');">Select</a>]
          &nbsp [<a href="javascript:changeDivContent('addLaborCategory','None Selected');javascript:resetNumericFieldValue('productId');">Clear</a>] 
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
      <strong>Classification</strong>
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
      Location
    </td>
    <td>
      <input type="text" name="location" value="<%= toHtmlValue(TicketDetails.getLocation()) %>" size="50" maxlength="256" />
    </td>
  </tr>
  </dhv:include>
<dhv:include name="ticket.catCode" none="true">
	<tr>
    <td class="formLabel">
      Category
    </td>
    <td>
      <%= CategoryList.getHtmlSelect("catCode", TicketDetails.getCatCode()) %>
    </td>
	</tr>
</dhv:include>
<dhv:include name="ticket.subCat1" none="true">
	<tr>
    <td class="formLabel" nowrap>
      Sub-level 1
    </td>
    <td>
      <%= SubList1.getHtmlSelect("subCat1", TicketDetails.getSubCat1()) %>
    </td>
	</tr>
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
	<tr>
    <td class="formLabel" nowrap>
      Sub-level 2
    </td>
    <td>
      <%= SubList2.getHtmlSelect("subCat2", TicketDetails.getSubCat2()) %>
    </td>
	</tr>
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
	<tr>
    <td class="formLabel" nowrap>
      Sub-level 3
    </td>
    <td>
      <%= SubList3.getHtmlSelect("subCat3", TicketDetails.getSubCat3()) %>
    </td>
	</tr>
</dhv:include>
<dhv:include name="ticket.severity" none="true">
	<tr>
    <td class="formLabel">
      Severity
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
      <strong>Assignment</strong>
    </th>
	</tr>
<dhv:include name="ticket.priority" none="true">
	<tr>
    <td class="formLabel">
      Priority
    </td>
    <td>
      <%= PriorityList.getHtmlSelect("priorityCode", TicketDetails.getPriorityCode()) %>
    </td>
	</tr>
</dhv:include>
	<tr>
    <td class="formLabel">
      Department
    </td>
    <td>
      <%= DepartmentList.getHtmlSelect("departmentCode", TicketDetails.getDepartmentCode()) %>
    </td>
	</tr>
	<tr>
    <td class="formLabel">
      Resource Assigned
    </td>
    <td>
      <% UserList.setJsEvent("onChange=\"javascript:setAssignedDate();\"");%>
      <%= UserList.getHtmlSelect("assignedTo", TicketDetails.getAssignedTo() ) %>
    </td>
	</tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Assignment Date
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="assignedDate" timestamp="<%= TicketDetails.getAssignedDate() %>" />
      <%= showAttribute(request, "assignedDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="ticket.estimatedResolutionDate">Estimated Resolution Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="estimatedResolutionDate" timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>" />
      <%= TimeZoneSelect.getSelect("estimatedResolutionDateTimeZone", TicketDetails.getEstimatedResolutionDateTimeZone()).getHtml() %>
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
      <strong>Resolution</strong>
    </th>
	</tr>
  <dhv:include name="ticket.cause" none="true">
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Cause
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
      Resolution Date
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="resolutionDate" timestamp="<%= TicketDetails.getResolutionDate() %>" />
      <%= showAttribute(request, "resolutionDateError") %>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="ticket.feedback" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      Have our services met or exceeded your expectations?
    </td>
    <td>
      <input type="radio" name="expectation" value="1" <%= (TicketDetails.getExpectation() == 1) ? " checked" : "" %>>Yes
      <input type="radio" name="expectation" value="0" <%= (TicketDetails.getExpectation() == 0) ? " checked" : "" %>>No
      <input type="radio" name="expectation" value="-1" <%= (TicketDetails.getExpectation() == -1) ? " checked" : "" %>>Undecided
    </td>
  </tr>
  </dhv:include>
</table>
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>

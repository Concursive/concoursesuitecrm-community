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
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
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
  function updateSubList3() {
    var sel = document.forms['addticket'].elements['subCat2'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets.do?command=CategoryJSList&subCat2=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  function updateUserList() {
    var sel = document.forms['addticket'].elements['departmentCode'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets.do?command=DepartmentJSList&departmentCode=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  function updateContactList() {
    var sel = document.forms['addticket'].elements['orgId'];
    var value = document.forms['addticket'].orgId.value;
    var url = "TroubleTickets.do?command=OrganizationJSList&orgId=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  function checkForm(form){
    formTest = true;
    message = "";
    
    if (form.contactId.value == "-1") { 
      message += "- Check that a Contact is selected\r\n";
      formTest = false;
    }
    if (form.problem.value == "") { 
      message += "- Check that <dhv:label name="tickets-problem">Issue</dhv:label> is entered\r\n";
      formTest = false;
    }
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
    //
    if(document.forms['addticket'].orgId.value != '-1'){
      updateContactList();
    }
	}
  
  function addNewContact(){
    var orgId = document.forms['addticket'].orgId.value;
    if(orgId == -1){
        alert('You  have to select an Account first');
        return;
    }
    if(orgId == '0'){
      popURL('CompanyDirectory.do?command=Prepare&popup=true&source=troubletickets', 'New_Employee','500','600','yes','yes');
    }else{
      popURL('Contacts.do?command=Prepare&popup=true&source=troubletickets&orgId=' + document.forms['addticket'].orgId.value, 'New_Contact','500','600','yes','yes');
    }
  }
</script>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
	<tr class="title">
    <td colspan="2">
      <strong>Add a new Ticket</strong>
    </td>
	</tr>
	<tr>
    <td class="formLabel">
      Ticket Source
    </td>
    <td>
      <%= SourceList.getHtmlSelect("sourceCode",  TicketDetails.getSourceCode()) %>
    </td>
	</tr>	
	<% if(!"true".equals(request.getParameter("contactSet"))){ %>
  <tr>
    <td class="formLabel">
      Organization
    </td>
    <td>
      <table cellspacing="0" cellpadding="0" border="0">
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
  <% }else{ %>
    <input type="hidden" name="orgId" value="<%= toHtmlValue(request.getParameter("orgId")) %>">
    <input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
  <% } %>
</table>
<br>
<a name="categories"></a> 
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>Classification</strong>
    </td>
	</tr>
	<tr>
    <td valign="top" class="formLabel">
      <dhv:label name="tickets-problem">Issue</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <textarea name="problem" cols="55" rows="3"><%= toString(TicketDetails.getProblem()) %></textarea>
            <input type="hidden" name="refresh" value="-1">
          </td>
          <td valign="top">
            <font color="red">*</font> <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
      </table>
    </td>
	</tr>
<dhv:include name="tickets-code" none="true">
	<tr>
    <td class="formLabel">
      Category
    </td>
    <td>
      <%= CategoryList.getHtmlSelect("catCode", TicketDetails.getCatCode()) %>
    </td>
	</tr>
</dhv:include>
<dhv:include name="tickets-subcat1" none="true">
	<tr>
    <td class="formLabel" nowrap>
      Sub-level 1
    </td>
    <td>
      <%= SubList1.getHtmlSelect("subCat1", TicketDetails.getSubCat1()) %>
      <input type="hidden" name="close" value="">
    </td>
	</tr>
</dhv:include>
<dhv:include name="tickets-subcat2" none="true">
	<tr>
    <td class="formLabel" nowrap>
      Sub-level 2
    </td>
    <td>
      <%= SubList2.getHtmlSelect("subCat2", TicketDetails.getSubCat2()) %>
    </td>
	</tr>
</dhv:include>
<dhv:include name="tickets-subcat3" none="true">
	<tr>
    <td class="formLabel" nowrap>
      Sub-level 3
    </td>
    <td>
      <%= SubList3.getHtmlSelect("subCat3", TicketDetails.getSubCat3()) %>
    </td>
	</tr>
</dhv:include>
</table>
<br>
<a name="department"></a> 
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
	<tr class="title">
    <td colspan="2">
      <strong>Assignment</strong>
    </td>
	</tr>
<dhv:include name="tickets-severity" none="true">
	<tr>
    <td class="formLabel">
      Severity
    </td>
    <td>
      <%= SeverityList.getHtmlSelect("severityCode",  TicketDetails.getSeverityCode()) %>
    </td>
	</tr>
</dhv:include>
<dhv:include name="tickets-priority" none="true">
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
      Assign To
    </td>
    <td>
      <%= UserList.getHtmlSelect("assignedTo", TicketDetails.getAssignedTo() ) %>
    </td>
	</tr>
	<tr>
    <td valign="top" class="formLabel">
      Entry Comments
    </td>
    <td>
      <textarea name="comment" cols="55" rows="3"><%= toString(TicketDetails.getComment()) %></textarea>
    </td>
	</tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
	<tr class="title">
    <td colspan="2">
      <strong>Resolution</strong>
    </td>
	</tr>
	<tr>
    <td valign="top" class="formLabel">
      Solution
    </td>
    <td>
      <textarea name="solution" cols="55" rows="3"><%= toString(TicketDetails.getSolution()) %></textarea><br>
      <input type="checkbox" name="closeNow">Close ticket<br>
      <input type="checkbox" name="kbase">Add this solution to Knowledge Base
    </td>
	</tr>
</table>
<%= addHiddenParams(request, "popup|popupType|actionId") %>

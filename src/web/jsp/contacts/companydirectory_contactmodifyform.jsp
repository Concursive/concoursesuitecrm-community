<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.modules.contacts.base.ContactTypeList" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript">
  function doCheck(form) {
  <% if(ContactDetails.getOrgId() == -1){ %>
    if(document.forms['addContact'].contactcategory[0].checked && document.forms['addContact'].contactcategory[0].value == '3'){
      document.forms['addContact'].action = 'CompanyDirectory.do?command=InsertEmployee&auto-populate=true&popup=true';
    }
  <% } %>
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  function checkForm(form) {
    formTest = true;
    message = "";
<%
    for (int i=1; i<=(ContactDetails.getPhoneNumberList().size()+1); i++) {
%>
		<dhv:evaluate exp="<%=(i>1)%>">else </dhv:evaluate>if (!checkPhone(form.phone<%=i%>number.value)) { 
			message += "- At least one entered phone number is invalid.  Make sure there are no invalid characters and that you have entered the area code\r\n";
			formTest = false;
		}
<%
    }
    if(ContactDetails.getOrgId() == -1){
%>

    if(document.forms[0].contactcategory[1].checked && document.forms[0].orgId.value == '-1') {
       message += "- Make sure you select an account.\r\n";
			 formTest = false;
    }
<%
  }
%>
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    } else {
      var test = document.forms[0].selectedList;
      if (test != null) {
        return selectAllOptions(document.forms[0].selectedList);
      }
    }
  }
  function update(countryObj, stateObj) {
  var country = document.forms['addContact'].elements[countryObj].value;
   if(country == "UNITED STATES" || country == "CANADA"){
      hideSpan('state2' + stateObj);
      showSpan('state1' + stateObj);
   }else{
      hideSpan('state1' + stateObj);
      showSpan('state2' + stateObj);
    }
  }
  
  function setCategoryPopContactType(selectedId, contactId){
    var category = 'general';
    if(document.addContact.contactcategory[1].checked){
      category = 'accounts';
    }
    popContactTypeSelectMultiple(selectedId, category, contactId); 
  }
</script>
<body onLoad="javascript:document.forms[0].nameFirst.focus();">
<%
  boolean popUp = false;
  String entity = "contact";
  if(request.getParameter("popup")!=null){
    popUp = true;
  }
  if("account".equals(request.getParameter("entity"))){
    entity = "account";
  }else if("employee".equals(request.getParameter("entity"))){
    entity = "employee";
  }

  if("account".equals(entity)){
%> 
  <form name="addContact" action="Contacts.do?command=Save&action=Modify&auto-populate=true&orgId=<%=ContactDetails.getOrgId()%>" method="post">
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Contacts</a> >
<% if (request.getParameter("return") == null) {%>
	<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>">Contact Details</a> >
<%}%>
Modify Contact<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="contacts" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
    <input type="submit" value="Update" name="Save" onClick="return checkForm(this.form)">
    <% if (request.getParameter("return") != null) {%>
      <% if (request.getParameter("return").equals("list")) {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='Contacts.do?command=View&orgId=<%= ContactDetails.getOrgId() %>'">
      <%}%>
    <%} else {%>
    <input type="submit" value="Cancel" onClick="javascript:this.form.action='Contacts.do?command=Details&id=<%= ContactDetails.getId() %>'">
    <%}%>
<% }else if("contact".equals(entity)){ %>
  <form name="addContact" action="ExternalContacts.do?command=Save&auto-populate=true" onSubmit="return doCheck(this);" method="post">
  <a href="ExternalContacts.do">General Contacts</a> > 
  <% if (request.getParameter("return") != null) {%>
    <% if (request.getParameter("return").equals("list")) {%>
    <a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
    <%}%>
  <%} else {%>
  <a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
  <a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
  <%}%>
  Modify Contact<br>
  <hr color="#BFBFBB" noshade>
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + ContactDetails.getId(); %>      
      <dhv:container name="contacts" selected="details" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <input type="submit" value="Update" name="Save" onClick="this.form.dosubmit.value='true';">
  <% if (request.getParameter("return") != null) {%>
    <% if (request.getParameter("return").equals("list")) {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContacts.do?command=ListContacts';this.form.dosubmit.value='false';">
    <%}%>
  <%} else {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
  <%}
 }else{ %>
  <form name="addContact" action="CompanyDirectory.do?command=Save&auto-populate=true" onSubmit="return doCheck(this);" method="post">
  <a href="MyCFS.do?command=Home">My Home Page</a> > 
  <% if (request.getParameter("return") != null) {%>
    <% if (request.getParameter("return").equals("list")) {%>
    <a href="CompanyDirectory.do?command=ListEmployees">View Employees</a> >
    <%}%>
  <% }else{ %>
  <a href="CompanyDirectory.do?command=ListEmployees">View Employees</a> >
  <a href="CompanyDirectory.do?command=EmployeeDetails&empid=<%=ContactDetails.getId()%>">Employee Details</a> >
  <% } %>
  Modify Employee<br>
  <hr color="#BFBFBB" noshade>
  <input type="submit" value="Update" name="Save" onClick="this.form.dosubmit.value='true';">
  <% if (request.getParameter("return") != null) {%>
    <% if (request.getParameter("return").equals("list")) {%>
    <input type="submit" value="Cancel" onClick="javascript:this.form.action='CompanyDirectory.do?command=ListEmployees';this.form.dosubmit.value='false';">
    <% } %>
  <% }else{ %>
    <input type="submit" value="Cancel" onClick="javascript:this.form.action='CompanyDirectory.do?command=EmployeeDetails&empid=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
  <% } %>
<% } %>
<input type=reset value="Reset">
<br>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong><%= "employee".equals(entity) ? "Add a New Employee Record" : "Add a New Contact" %></strong>
    </td>     
  </tr>
  <% if("contact".equals(entity)){ %>
    <tr class="containerBody">
    <td class="formLabel" nowrap>
      Reassign To
    </td>
    <td>
      <%= UserList.getHtmlSelect("owner", ContactDetails.getOwner() ) %>
    </td>
  </tr>
    <tr class="containerBody">
    <td class="formLabel" nowrap>
      Contact Category
    </td>
    <td>
      <% if(!"employee".equals(entity)){ %>
     <dhv:evaluate if="<%= ContactDetails.getOrgId() == -1 %>">
        <input type="radio" name="contactcategory" value="1" onclick="javascript:document.forms[0].orgId.value = '-1';" <%= ContactDetails.getOrgId() == -1 ? " checked":""%>>General Contact<br>
     </dhv:evaluate>
      <% }else{ %>
        <input type="radio" name="contactcategory" value="3" onclick="javascript:document.forms[0].orgId.value = '-1';" <%= ContactDetails.getOrgId() == -1 ? " checked":""%>>Employee<br>
      <% } %>
      <table cellspacing="0" cellpadding="0" border="0">
          <tr>
            <td>
              <input type="radio" name="contactcategory" value="2" <%= ContactDetails.getOrgId() > -1 ? " checked":""%>>
            </td>
            <td>
              Associated with Account: &nbsp;
            </td>
            <td>
              <div id="changeaccount"><%= ContactDetails.getOrgId() > -1 ? ContactDetails.getCompany() : "None Selected"%></div>
              <input type="hidden" name="orgId" id="orgId" value="<%= ContactDetails.getOrgId() %>">
            </td>
            <dhv:evaluate if="<%= ContactDetails.getOrgId() == -1 %>">
            <td>
              &nbsp;[<a href="javascript:popAccountsListSingle('orgId','changeaccount');">Select</a>]&nbsp;
            </td>
            </dhv:evaluate>
          </tr>
       </table>
    </td>
  </tr>
  <% } %>
  <dhv:evaluate if="<%= !"employee".equals(entity) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Contact Type(s)
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <select multiple name="selectedList" id="selectedList" size="5">
          <%if(request.getAttribute("TypeList") != null){ %>
            <dhv:lookupHtml listName="TypeList" lookupName="ContactTypeList"/>
          <% }else{ %>
               <dhv:evaluate exp="<%= ContactDetails.getTypes().isEmpty() %>">
                  <option value="-1">None Selected</option>
                </dhv:evaluate>
                <dhv:evaluate exp="<%= !ContactDetails.getTypes().isEmpty() %>">
              <%
                Iterator i = ContactDetails.getTypes().iterator();
                while (i.hasNext()) {
                LookupElement thisElt = (LookupElement)i.next();
              %>
                <option value="<%= thisElt.getCode() %>"><%= thisElt.getDescription() %></option>
              <% } %>
              </dhv:evaluate>
           <% } %>
        </select>
            <input type="hidden" name="previousSelection" value="">
            <input type="hidden" name="category" value="<%= request.getParameter("category") %>">
          </td>
          <td valign="top">
            <% if("account".equals(entity)){ %>
              &nbsp;[<a href="javascript:popContactTypeSelectMultiple('selectedList', 'accounts', <%= ContactDetails.getId() %>);">Select</a>]
            <% }else{ %>
            <%if(ContactDetails.getOrgId() == -1){%>
              &nbsp;[<a href="javascript:setCategoryPopContactType('selectedList', <%= ContactDetails.getId() %>);">Select</a>]
            <% }else{ %>
              &nbsp;[<a href="javascript:popContactTypeSelectMultiple('selectedList', 'accounts', <%= ContactDetails.getId() %>);">Select</a>]
           <% } %>
           <% } %>
	   <%= showAttribute(request, "personalContactError") %>	
          </td>
        </tr>
      </table>
     </td>
  </tr>
   </dhv:evaluate>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      First Name
    </td>
    <td>
      <input type="text" size="35" name="nameFirst" value="<%= toHtmlValue(ContactDetails.getNameFirst()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
    Middle Name
    </td>
    <td>
      <input type="text" size="35" name="nameMiddle" value="<%= toHtmlValue(ContactDetails.getNameMiddle()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Last Name
    </td>
    <td>
      <input type="text" size="35" name="nameLast" value="<%= toHtmlValue(ContactDetails.getNameLast()) %>">
      <font color="red">*</font> <%= showAttribute(request, "nameLastError") %>
    </td>
  </tr>
  <%-- Check if a user is being added --%>
  <% if("employee".equals(entity)){ %>
  <tr>
      <td class="formLabel" nowrap>Department</td>
      <td>
        <%= DepartmentList.getHtmlSelect("department", ContactDetails.getDepartment()) %>
      </td>
   </tr>
  <% }else if("contact".equals(entity)){ %>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        Company
      </td>
      <td>
        <input type="text" size="35" name="company" value="<%= toHtmlValue(ContactDetails.getCompany()) %>">
        <font color="red">-</font> <%= showAttribute(request, "lastcompanyError") %>
      </td>
    </tr>
  <% } %>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Title
    </td>
    <td>
      <input type="text" size="35" name="title" value="<%= toHtmlValue(ContactDetails.getTitle()) %>">
    </td>
  </tr>
</table>
&nbsp;<br>  

<%--  include basic contact form --%>
<%@ include file="../contacts/contact_form.jsp" %>

<br>
<% if("account".equals(entity)){ %>
   <input type="submit" value="Update" name="Save" onClick="return checkForm(this.form)">
    <% if (request.getParameter("return") != null) {%>
      <% if (request.getParameter("return").equals("list")) {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='Contacts.do?command=View&orgId=<%= ContactDetails.getOrgId() %>'">
      <%}%>
    <%} else {%>
    <input type="submit" value="Cancel" onClick="javascript:this.form.action='Contacts.do?command=Details&id=<%= ContactDetails.getId() %>'">
    <%}%>
  <input type="reset" value="Reset">
  <input type="hidden" name="owner" value="<%= ContactDetails.getOwner() %>">
  </td>
  </tr>
</table>
<% }else if("contact".equals(entity)){ %>
  <input type="submit" value="Update" name="Save" onClick="this.form.dosubmit.value='true';">
  <% if (request.getParameter("return") != null) {%>
    <% if (request.getParameter("return").equals("list")) {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContacts.do?command=ListContacts';this.form.dosubmit.value='false';">
    <%}%>
  <% }else{ %>
    <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
  <% } %>
  <input type="reset" value="Reset">
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="primaryContact" value="<%= ContactDetails.getPrimaryContact() %>">
    </td>
   </tr>
  </table>
  <% }else{ %>
    <input type="submit" value="Update" name="Save" onClick="this.form.dosubmit.value='true';">
  <% if (request.getParameter("return") != null) {%>
    <% if (request.getParameter("return").equals("list")) {%>
    <input type="submit" value="Cancel" onClick="javascript:this.form.action='CompanyDirectory.do?command=ListEmployees';this.form.dosubmit.value='false';">
    <%}%>
  <%} else {%>
    <input type="submit" value="Cancel" onClick="javascript:this.form.action='CompanyDirectory.do?command=EmployeeDetails&empid=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
  <%}%>
  <input type="reset" value="Reset">
  <input type="hidden" name="empid" value="<%= ContactDetails.getId() %>">
  <input type="hidden" name="orgId" id="orgId" value="<%= ContactDetails.getOrgId() %>">
<% } %>
<input type="hidden" name="entity" value="<%= toHtmlValue(request.getParameter("entity")) %>">
<input type="hidden" name="id" value="<%= ContactDetails.getId() %>">
<input type="hidden" name="primaryContact" value="<%=ContactDetails.getPrimaryContact()%>">
<input type="hidden" name="modified" value="<%= ContactDetails.getModified() %>">
<% if (request.getParameter("return") != null) {%>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<% } %>
</form>
</body>	

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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*, org.aspcfs.modules.admin.base.AccessType" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.modules.contacts.base.ContactTypeList" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AccessTypeList" class="org.aspcfs.modules.admin.base.AccessTypeList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript">
  function doCheck(form) {
    <% if("adduser".equals(request.getParameter("source"))){ %>
    if(document.forms['addContact'].contactcategory[0].checked && document.forms['addContact'].contactcategory[0].value == '3'){
      document.forms['addContact'].action = 'CompanyDirectory.do?command=Save&auto-populate=true&popup=true';
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
    if(document.forms[0].contactcategory[1].checked && document.forms[0].orgId.value == '-1') {
       message += "- Make sure you select an account.\r\n";
			 formTest = false;
    }
    if ((!checkPhone(form.phone1number.value)) || (!checkPhone(form.phone2number.value)) || (!checkPhone(form.phone3number.value)) ) { 
      message += "- At least one entered phone number is invalid.  Make sure there are no invalid characters and that you have entered the area code\r\n";
      formTest = false;
    }
    if ((!checkEmail(form.email1address.value)) || (!checkEmail(form.email2address.value))){
      message += "- At least one entered email address is invalid.  Make sure there are no invalid characters\r\n";
      formTest = false;
    }
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    } else {
      var test = document.addContact.selectedList;
      if (test != null) {
        return selectAllOptions(document.addContact.selectedList);
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
  
  function updateCategoryInfo(category){
    if(category == "general"){
      document.forms[0].orgId.value = '-1';
      var url = "ExternalContacts.do?command=AccessTypeJSList&category=" + <%= AccessType.GENERAL_CONTACTS %>;
      window.frames['server_commands'].location.href=url;
    }else if(category == "account"){
      var url = "ExternalContacts.do?command=AccessTypeJSList&category=" + <%= AccessType.ACCOUNT_CONTACTS %>;
      window.frames['server_commands'].location.href=url;
    }else if(category == "employee"){
      document.forms[0].orgId.value = '-1';
      var url = "ExternalContacts.do?command=AccessTypeJSList&category=" + <%= AccessType.EMPLOYEES %>;
      window.frames['server_commands'].location.href=url;
    }
  }
  
  function selectAccount(){
   document.forms['addContact'].contactcategory[1].checked='t';
   updateCategoryInfo('account');
   popAccountsListSingle('orgId','changeaccount');
  }
</script>
<body onLoad="javascript:document.forms[0].nameFirst.focus();">
  <form name="addContact" action="ExternalContacts.do?command=Save&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<dhv:evaluate exp="<%= !isPopup(request)  || isInLinePopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="ExternalContacts.do">Contacts</a> > 
    Add Contact
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
  <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
  <dhv:evaluate exp="<%= !isPopup(request) %>">
  <input type="submit" value="Save & New" onClick="this.form.saveAndNew.value='true';this.form.dosubmit.value='true';">
  </dhv:evaluate>
  <input type="submit" value="Cancel" onClick="<%= isPopup(request) && !isInLinePopup(request) ? "javascript:window.close();" : "javascript:this.form.action='ExternalContacts.do?command=SearchContacts';this.form.dosubmit.value='false';" %>">
<br />
<dhv:formMessage />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Add a Contact</strong>
    </th>
  </tr>
    <tr class="containerBody">
    <td class="formLabel" nowrap>
      Contact Category
    </td>
    <td>
      <% if("adduser".equals(request.getParameter("source"))){ %>
      	<input type="radio" name="contactcategory" value="3" onChange="javascript:updateCategoryInfo('employee');" <%= ContactDetails.getOrgId() == -1 ? " checked" : ""%>><dhv:label name="employees.employee">Employee</dhv:label><br>
	<input type="hidden" name="source" value="<%= request.getParameter("source") %>">
      <% }else{ %>
        <input type="radio" name="contactcategory" value="1" onChange="javascript:updateCategoryInfo('general');" <%= ContactDetails.getOrgId() == -1 ? " checked":""%>>General Contact<br>
      <% } %>
      <dhv:permission name="accounts-accounts-contacts-add">
      <table cellspacing="0" cellpadding="0" border="0" class="empty">
          <tr>
            <td>
              <input type="radio" name="contactcategory" value="2" onChange="javascript:updateCategoryInfo('account');" <%= ContactDetails.getOrgId() > -1 ? " checked":""%>>
            </td>
            <td>
              Permanently associate with Account: &nbsp;
            </td>
            <td>
              <div id="changeaccount"><%= ContactDetails.getOrgId() > -1 ? ContactDetails.getCompany() : "None Selected"%></div>
            </td>
            <td>
              <input type="hidden" name="orgId" id="orgId" value="<%= ContactDetails.getOrgId() %>">
              &nbsp;[<a href="javascript:selectAccount();" onMouseOver="window.status='Select an Account';return true;" onMouseOut="window.status='';return true;">Select</a>]&nbsp;
            </td>
          </tr>
       </table>
       </dhv:permission>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Contact Type(s)
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <select multiple name="selectedList" id="selectedList" size="5">
               <dhv:lookupHtml listName="TypeList" lookupName="ContactTypeList"/>
            </select>
            <input type="hidden" name="previousSelection" value="">
            <input type="hidden" name="category" value="<%= request.getParameter("category") %>">
          </td>
          <td valign="top">
            <%-- Check for cloned contact in case of Contacts --%>
            <% if(request.getParameter("id") == null) {%>
              [<a href="javascript:setCategoryPopContactType('selectedList', <%= ContactDetails.getId() %>);">Select</a>]
            <%}else{%>
                [<a href="javascript:setCategoryPopContactType('selectedList', <%= request.getParameter("id") %>);">Select</a>]
            <% } %>
              <%= showAttribute(request, "personalContactError") %>
          </td>
        </tr>
      </table>
     </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Access Type
    </td>
    <td>
      <% 
          HtmlSelect thisSelect = AccessTypeList.getHtmlSelectObj(ContactDetails.getAccessType());
          thisSelect.addAttribute("id", "accessType");
      %>
      <%=  thisSelect.getHtml("accessType") %>
      <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
    </td>
  </tr>
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
  <% if("adduser".equals(request.getParameter("source"))){ %>
  <tr>
      <td class="formLabel" nowrap>Department</td>
      <td>
        <%= DepartmentList.getHtmlSelect("department", ContactDetails.getDepartment()) %>
      </td>
   </tr>
  <% } %>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        Company
      </td>
      <td>
        <input type="text" size="35" name="company" value="<%= toHtmlValue(ContactDetails.getCompany()) %>">
        <font color="red">-</font> <%= showAttribute(request, "lastcompanyError") %>
      </td>
    </tr>
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
<%@ include file="contact_include.jsp" %>
<br>
  <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
  <dhv:evaluate exp="<%= !isPopup(request) %>">
  <input type="submit" value="Save & New" onClick="this.form.saveAndNew.value='true';this.form.dosubmit.value='true';">
  <input type="hidden" name="dosubmit" value="true">
  </dhv:evaluate>
  <input type="submit" value="Cancel" onClick="<%= (isPopup(request)  && !isInLinePopup(request)) ? "javascript:window.close();" : "javascript:this.form.action='ExternalContacts.do?command=SearchContacts';this.form.dosubmit.value='false';" %>">
  <input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="source" value="<%= toHtmlValue(request.getParameter("source")) %>">
<%= addHiddenParams(request, "popup|popupType|actionId") %>
</form>
</body>

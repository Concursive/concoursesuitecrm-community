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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*, org.aspcfs.modules.admin.base.AccessType" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.modules.contacts.base.ContactTypeList" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AccessTypeList" class="org.aspcfs.modules.admin.base.AccessTypeList" scope="request"/>
<jsp:useBean id="SalutationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/setSalutation.js"></script>
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

    if (document.addContact.company.value != "" && checkNullString(document.addContact.company.value)) {
       message += label("check.company.blanks", "- Please enter a valid company name.\r\n");
			 formTest = false;
    }
    if (form.nameLast.value != "" && checkNullString(form.nameLast.value)) {
       message += label("check.name.last.blanks", "- Please enter a valid last name.\r\n");
			 formTest = false;
    }
    if(document.addContact.contactcategory[0] && document.addContact.contactcategory[1].checked && document.addContact.orgId.value == '-1') {
       message += label("sure.select.account", "- Make sure you select an account.\r\n");
			 formTest = false;
    }
<dhv:include name="contact.phoneNumbers" none="true">
    if ((!checkPhone(form.phone1number.value)) || (!checkPhone(form.phone2number.value)) || (!checkPhone(form.phone3number.value)) || (checkNullString(form.phone1number.value) && !checkNullString(form.phone1ext.value)) || (checkNullString(form.phone2number.value) && !checkNullString(form.phone2ext.value)) || (checkNullString(form.phone3number.value) && !checkNullString(form.phone3ext.value))) {
      message += label("check.phone", "- At least one entered phone number is invalid.  Make sure there are no invalid characters and that you have entered the area code\r\n");
      formTest = false;
    }
    if ((checkNullString(form.phone1ext.value) && form.phone1ext.value != "") || (checkNullString(form.phone2ext.value) && form.phone2ext.value != "") || (checkNullString(form.phone3ext.value) && form.phone3ext.value != "")) {
      message += label("check.phone.ext","- Please enter a valid phone number extension\r\n");
      formTest = false;
    }
</dhv:include>
<dhv:include name="contact.emailAddresses" none="true">
    if ((!checkEmail(form.email1address.value)) || (!checkEmail(form.email2address.value)) || (!checkEmail(form.email3address.value))){
      message += label("check.email", "- At least one entered email address is invalid.  Make sure there are no invalid characters\r\n");
      formTest = false;
    }
</dhv:include>
<dhv:include name="contact.textMessageAddresses" none="true">
    if ((!checkEmail(form.textmessage1address.value)) || (!checkEmail(form.textmessage1address.value)) || (!checkEmail(form.textmessage1address.value))){
      message += label("check.textmessage", "- At least one entered text message address is invalid.  Make sure there are no invalid characters\r\n");
      formTest = false;
    }
</dhv:include>
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      var test = document.addContact.selectedList;
      if (test != null) {
        return selectAllOptions(document.addContact.selectedList);
      }
    }
  }
  function update(countryObj, stateObj, selectedValue) {
    var country = document.forms['addContact'].elements[countryObj].value;
    var url = "ExternalContacts.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=addContact&stateObj=address"+stateObj+"state";
    window.frames['server_commands'].location.href=url;
  }

  function continueUpdateState(stateObj, showText) {
    if(showText == 'true'){
      hideSpan('state1' + stateObj);
      showSpan('state2' + stateObj);
    } else {
      hideSpan('state2' + stateObj);
      showSpan('state1' + stateObj);
    }
  }

  function setCategoryPopContactType(selectedId, contactId){
    var category = 'general';
    if(document.addContact.contactcategory[0] && document.addContact.contactcategory[1].checked){
      category = 'accounts';
    }
    popContactTypeSelectMultiple(selectedId, category, contactId);
  }

  function updateCategoryInfo(category){
    if(category == "general"){
      document.addContact.orgId.value = '-1';
      var url = "ExternalContacts.do?command=AccessTypeJSList&category=" + <%= AccessType.GENERAL_CONTACTS %>;
      window.frames['server_commands'].location.href=url;
    }else if(category == "account"){
      var url = "ExternalContacts.do?command=AccessTypeJSList&category=" + <%= AccessType.ACCOUNT_CONTACTS %>;
      window.frames['server_commands'].location.href=url;
    }else if(category == "employee"){
      document.addContact.orgId.value = '-1';
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
<body onLoad="javascript:document.addContact.nameFirst.focus();">
  <form name="addContact" action="ExternalContacts.do?command=Save&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<dhv:evaluate if="<%= !isPopup(request)  || isInLinePopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="ExternalContacts.do"><dhv:label name="Contacts" mainMenuItem="true">Contacts</dhv:label></a> >
  <dhv:label name="accounts.AddContact">Add Contact</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:formMessage showSpace="false"/>
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="this.form.dosubmit.value='true';">
<dhv:evaluate if="<%= !isPopup(request) %>"><input type="submit" value="<dhv:label name="button.saveAndNew">Save & New</dhv:label>" onClick="this.form.saveAndNew.value='true';this.form.dosubmit.value='true';"></dhv:evaluate>
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick='<%= isPopup(request) && !isInLinePopup(request) ? "javascript:window.close();" : "window.location.href=\"ExternalContacts.do?command=SearchContacts\";this.form.dosubmit.value=\"false\";" %>'>
<br /><br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_contacts_list.AddAContact">Add a Contact</dhv:label></strong>
    </th>
  </tr>
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.site">Site</dhv:label>
    </td>
    <td>
      <dhv:evaluate if="<%= User.getSiteId() == -1 %>" >
        <%= SiteList.getHtmlSelect("siteId",ContactDetails.getSiteId()) %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= User.getSiteId() != -1 %>" >
         <%= SiteList.getSelectedValue(User.getSiteId()) %>
        <input type="hidden" name="siteId" value="<%=User.getSiteId()%>" >
      </dhv:evaluate>
      <%= showAttribute(request, "siteIdError") %>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= SiteList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="contact.contactCategory">Contact Category</dhv:label>
    </td>
    <td>
      <% if("adduser".equals(request.getParameter("source"))){ %>
      	<input type="radio" name="contactcategory" value="3" onChange="javascript:updateCategoryInfo('employee');" <%= ContactDetails.getOrgId() == -1 ? " checked" : ""%>><dhv:label name="employees.employee">Employee</dhv:label><br>
        <input type="hidden" name="source" value="<%= request.getParameter("source") %>">
      <% }else{ %>
        <input type="radio" name="contactcategory" value="1" onChange="javascript:updateCategoryInfo('general');" <%= ContactDetails.getOrgId() == -1 ? " checked":""%>><dhv:label name="accounts.accounts_importcontact_details_include.GeneralContact">General Contact</dhv:label><br>
      <% } %>
      <dhv:permission name="accounts-accounts-contacts-add">
      <table cellspacing="0" cellpadding="0" border="0" class="empty">
          <tr>
            <td>
              <input type="radio" name="contactcategory" value="2" onChange="javascript:updateCategoryInfo('account');" <%= ContactDetails.getOrgId() > -1 ? " checked":""%>>
            </td>
            <td>
              <dhv:label name="contact.associateWithAccount.colon">Associate with Account:</dhv:label> &nbsp;
            </td>
            <td>
              <div id="changeaccount">
                <% if(ContactDetails.getOrgId() > -1) {%>
                  <%= toHtml(ContactDetails.getCompany()) %>
                <%} else {%>
                  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
                <%}%>
              </div>
            </td>
            <td>
              <input type="hidden" name="orgId" id="orgId" value="<%= ContactDetails.getOrgId() %>">
              &nbsp;[<a href="javascript:selectAccount();" onMouseOver="window.status='Select an Account';return true;" onMouseOut="window.status='';return true;"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]&nbsp;
            </td>
          </tr>
       </table>
       </dhv:permission><dhv:permission name="accounts-accounts-contacts-add" none="true">
         <input type="hidden" name="orgId" id="orgId" value="-1" />
      </dhv:permission>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_add.ContactTypes">Contact Type(s)</dhv:label>
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
              [<a href="javascript:setCategoryPopContactType('selectedList', <%= ContactDetails.getId() %>);"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
            <%}else{%>
                [<a href="javascript:setCategoryPopContactType('selectedList', <%= request.getParameter("id") %>);"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
            <% } %>
              <%= showAttribute(request, "personalContactError") %>
          </td>
        </tr>
      </table>
     </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_validateimport.AccessType">Access Type</dhv:label>
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
  <dhv:include name="contact-salutation" none="true">
     <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_add.Salutation">Salutation</dhv:label>
      </td>
      <td>
        <% SalutationList.setJsEvent("onchange=\"javascript:fillSalutation('addContact');\"");%>
        <%= SalutationList.getHtmlSelect("listSalutation",ContactDetails.getNameSalutation()) %> 
        <input type="hidden" size="35" name="nameSalutation" value="<%= toHtmlValue(ContactDetails.getNameSalutation()) %>">
      </td>
    </tr>
  </dhv:include>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.FirstName">First Name</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="nameFirst" value="<%= toHtmlValue(ContactDetails.getNameFirst()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
    <dhv:label name="accounts.accounts_add.MiddleName">Middle Name</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="nameMiddle" value="<%= toHtmlValue(ContactDetails.getNameMiddle()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.LastName">Last Name</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="nameLast" value="<%= toHtmlValue(ContactDetails.getNameLast()) %>">
      <font color="red">*</font> <%= showAttribute(request, "nameLastError") %>
    </td>
  </tr>
  <dhv:include name="contact.additionalNames" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.additionalNames">Additional Names</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="additionalNames" value="<%= toHtmlValue(ContactDetails.getAdditionalNames()) %>">
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="contact.nickname" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.nickname">Nickname</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="nickname" value="<%= toHtmlValue(ContactDetails.getNickname()) %>">
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="contact.birthday" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.dateOfBirth">Birthday</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addContact" field="birthDate" timestamp="<%= ContactDetails.getBirthDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false"/>
      <%= showAttribute(request, "birthDateError") %>
    </td>
  </tr>
  </dhv:include>
  <%-- Check if a user is being added --%>
  <% if("adduser".equals(request.getParameter("source"))){ %>
  <tr>
      <td class="formLabel" nowrap><dhv:label name="project.department">Department</dhv:label></td>
      <td>
        <%= DepartmentList.getHtmlSelect("department", ContactDetails.getDepartment()) %>
      </td>
   </tr>
  <% } %>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label>
      </td>
      <td>
        <input type="text" size="35" name="company" value="<%= toHtmlValue(ContactDetails.getCompany()) %>">
        <font color="red">-</font> <%= showAttribute(request, "lastcompanyError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label>
      </td>
      <td>
        <input type="text" size="35" name="title" value="<%= toHtmlValue(ContactDetails.getTitle()) %>">
      </td>
    </tr>
    <dhv:include name="contact.role" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_add.Role">Role</dhv:label>
      </td>
      <td>
        <input type="text" size="35" name="role" value="<%= toHtmlValue(ContactDetails.getRole()) %>">
      </td>
    </tr>
    </dhv:include>
</table>
&nbsp;<br>
<%--  include basic contact form --%>
<%@ include file="contact_include.jsp" %>
<br>
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="this.form.dosubmit.value='true';">
  <dhv:evaluate if="<%= !isPopup(request) %>">
  <input type="submit" value="<dhv:label name="button.saveAndNew">Save & New</dhv:label>" onClick="this.form.saveAndNew.value='true';this.form.dosubmit.value='true';">
  <input type="hidden" name="dosubmit" value="true">
  </dhv:evaluate>
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick='<%= isPopup(request) && !isInLinePopup(request) ? "javascript:window.close();" : "window.location.href=\"ExternalContacts.do?command=SearchContacts\";this.form.dosubmit.value=\"false\";" %>'>
  <input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="source" value="<%= toHtmlValue(request.getParameter("source")) %>">
<%= addHiddenParams(request, "popup|popupType|actionId") %>
</form>
</body>

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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.modules.contacts.base.ContactTypeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="SalutationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactTypeList2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/setSalutation.js"></script>
<script language="JavaScript">
  function doCheck(form) {
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  function checkForm(form) {
    formTest = true;
    message = "";
    if (checkNullString(form.nameLast.value)){
      message += label("check.lastname", "- Last name is a required field\r\n");
      formTest = false;
    }
<dhv:include name="contact.phoneNumbers" none="true">
<%
    for (int i=1; i<=(ContactDetails.getPhoneNumberList().size() <3?3:ContactDetails.getPhoneNumberList().size()+1); i++) { %>
		<dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if (!checkPhone(form.phone<%=i%>number.value) || (checkNullString(form.phone<%=i%>number.value) && !checkNullString(form.phone<%=i%>ext.value))) {
			message += label("check.phone", "- At least one entered phone number is invalid.  Make sure there are no invalid characters and that you have entered the area code\r\n");
			formTest = false;
		}
<%  }
    for (int i=1; i <= (ContactDetails.getPhoneNumberList().size() <3?3:ContactDetails.getPhoneNumberList().size()+1); i++) { %>
    <dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if ((checkNullString(form.phone<%= i %>ext.value) && form.phone<%= i %>ext.value != "")) {
      message += label("check.phone.ext","- Please enter a valid phone number extension\r\n");
      formTest = false;
    }
<%  } %>
</dhv:include>
<dhv:include name="contact.emailAddresses" none="true">
<%  for (int i=1; i<=(ContactDetails.getEmailAddressList().size() <3?3:ContactDetails.getEmailAddressList().size()+1); i++) { %>
    <dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if (!checkEmail(form.email<%=i%>address.value)) {
      message += label("check.email", "- At least one entered email address is invalid.  Make sure there are no invalid characters\r\n");
      formTest = false;
    }
<%  } %>
</dhv:include>
<dhv:include name="contact.textMessageAddresses" none="true">
<%  for (int i=1; i<=(ContactDetails.getTextMessageAddressList().size() <3?3:ContactDetails.getTextMessageAddressList().size()+1); i++) { %>
    <dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if (!checkEmail(form.textmessage<%=i%>address.value)) {
      message += label("check.textmessage", "- At least one entered text message address is invalid.  Make sure there are no invalid characters\r\n");
      formTest = false;
    }
<%  } %>
</dhv:include>
<%  if(ContactDetails.getOrgId() == -1){%>
    if(document.addContact.contactcategory[1].checked && document.addContact.orgId.value == '-1') {
       message += label("sure.select.account", "- Make sure you select an account.\r\n");
			 formTest = false;
    }
<%  }%>
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
    if(document.addContact.contactcategory[1].checked){
      category = 'accounts';
    }
    popContactTypeSelectMultiple(selectedId, category, contactId); 
  }
</script>
<body onLoad="javascript:document.addContact.listSalutation.focus();">
<%
  boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }
%>
  <form name="addContact" action="Contacts.do?command=Save&action=Modify&auto-populate=true&orgId=<%=ContactDetails.getOrgId()%><%= isPopup(request)?"&popup=true":"" %>" method="post">
<dhv:evaluate if="<%= !popUp %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> >
<% if (request.getParameter("return") == null) {%>
	<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<%}%>
<dhv:label name="accounts.accounts_contacts_modify.ModifyContact">Modify Contact</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="contacts" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' hideContainer="<%= isPopup(request) || !OrgDetails.getEnabled() || OrgDetails.isTrashed()%>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:container name="accountscontacts" selected="details" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' hideContainer="<%= isPopup(request) || !OrgDetails.getEnabled() || OrgDetails.isTrashed() || !ContactDetails.getEnabled() || ContactDetails.isTrashed()%>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
    <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="return checkForm(this.form)">
    <% if (request.getParameter("return") != null) {%>
      <% if (request.getParameter("return").equals("list")) {%>
       <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Contacts.do?command=View&orgId=<%= ContactDetails.getOrgId() %>'">
      <%}%>
    <%} else if (isPopup(request)) {%>
      <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();" />
    <%} else {%>
      <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Contacts.do?command=Details&id=<%= ContactDetails.getId() %>'">
    <%}%>
    <br />
    <%= showError(request, "actionError") %>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <tr>
        <th colspan="2">
          <strong><dhv:label name="accounts.accounts_contacts_modify.ModifyContact">Modify Contact</dhv:label></strong>
        </th>
      </tr>
  <dhv:include name="contact-types" none="true">
      <tr class="containerBody">
        <td nowrap class="formLabel" valign="top">
          <dhv:label name="accounts.accounts_contacts_add.ContactTypes">Contact Type(s)</dhv:label>
        </td>
        <td>
          <table border="0" cellspacing="0" cellpadding="0" class="empty">
            <tr>
              <td>
                <select multiple name="selectedList" id="selectedList" size="5">
              <%if(request.getAttribute("TypeList") != null){ %>
                <dhv:lookupHtml listName="TypeList" lookupName="ContactTypeList"/>
              <% }else{ %>
                   <dhv:evaluate if="<%= ContactDetails.getTypes().isEmpty() %>">
                      <option value="-1"><dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label></option>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= !ContactDetails.getTypes().isEmpty() %>">
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
                &nbsp;[<a href="javascript:popContactTypeSelectMultiple('selectedList', 'accounts', <%= ContactDetails.getId() %>);"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
                <%= showAttribute(request, "personalContactError") %>
              </td>
            </tr>
          </table>
         </td>
      </tr>
  </dhv:include>
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
      <dhv:include name="contact-middlename" none="true">
        <tr class="containerBody">
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_add.MiddleName">Middle Name</dhv:label>
          </td>
          <td>
            <input type="text" size="35" name="nameMiddle" value="<%= toHtmlValue(ContactDetails.getNameMiddle()) %>">
          </td>
        </tr>
      </dhv:include>
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
      <tr class="containerBody">
        <td nowrap class="formLabel">
          <dhv:label name="accounts.accounts_add.nickname">Nickname</dhv:label>
        </td>
        <td>
          <input type="text" size="35" name="nickname" value="<%= toHtmlValue(ContactDetails.getNickname()) %>">
        </td>
      </tr>
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
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="contact.source">Source</dhv:label>
    </td>
    <td>
      <%= SourceList.getHtmlSelect("source",ContactDetails.getSource()) %> 
    </td>
  </tr>
 </table>
    <br />
    <%--  include basic contact form --%>
    <%@ include file="../contacts/contact_include.jsp" %>
    <br />
    <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="return checkForm(this.form)">
    <% if (request.getParameter("return") != null) {%>
      <% if (request.getParameter("return").equals("list")) {%>
      <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Contacts.do?command=View&orgId=<%= ContactDetails.getOrgId() %>'">
      <%}%>
    <%} else if (isPopup(request)) {%>
      <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();" />
    <%} else {%>
      <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Contacts.do?command=Details&id=<%= ContactDetails.getId() %>'">
    <%}%>
    <input type="hidden" name="owner" value="<%= ContactDetails.getOwner() %>">
    <input type="hidden" name="id" value="<%= ContactDetails.getId() %>">
    <input type="hidden" name="leadStatus" value="<%= ContactDetails.getLeadStatus() %>" />
    <input type="hidden" name="conversionDate" value="<%=ContactDetails.getConversionDate()%>">
    <input type="hidden" name="assignedDate" value="<%=ContactDetails.getAssignedDate()%>">
    <input type="hidden" name="leadTrashedDate" value="<%=ContactDetails.getLeadTrashedDate()%>">
    <input type="hidden" name="primaryContact" value="<%=ContactDetails.getPrimaryContact()%>">
    <input type="hidden" name="orgName" value="<%= OrgDetails.getName() %>">
    <input type="hidden" name="siteId" value="<%= OrgDetails.getSiteId() %>">
    <input type="hidden" name="modified" value="<%= ContactDetails.getModified() %>">
    <input type="hidden" name="actionStepWork" value="<%= toHtmlValue(request.getParameter("actionStepWork")) %>">
		<input type="hidden" name="dunsType" value="<%= ContactDetails.getDunsType() %>">
		<input type="hidden" name="dunsNumber" value="<%= ContactDetails.getDunsNumber() %>">
		<input type="hidden" name="businessNameTwo" value="<%= ContactDetails.getBusinessNameTwo() %>">
		<input type="hidden" name="sicDescription" value="<%= ContactDetails.getSicDescription() %>">
		<input type="hidden" name="revenue" value="<%= ContactDetails.getRevenue() %>">
		<input type="hidden" name="potential" value="<%= ContactDetails.getPotential() %>">
		<input type="hidden" name="yearStarted" value="<%= ContactDetails.getYearStarted() %>">
    <% if (request.getParameter("return") != null) {%>
      <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
    <% } %>
  </dhv:container>
</dhv:container>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</form>
</body>

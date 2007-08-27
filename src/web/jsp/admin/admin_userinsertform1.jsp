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
<jsp:useBean id="UserRecord" class="org.aspcfs.modules.admin.base.User"
             scope="request"/>
<jsp:useBean id="RoleList" class="org.aspcfs.modules.admin.base.RoleList"
             scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList"
             scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="APP_SIZE" class="java.lang.String" scope="application"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
             scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
        SRC="javascript/checkDate.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
        SRC="javascript/popCalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
        src="javascript/popContacts.js?v=20070827"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
        SRC="javascript/submit.js"></SCRIPT>
<script type="text/javascript">
  function checkForm(form) {
    formTest = true;
    message = "";
    if ((form.contactId.value == "-1")) {
      message += label("check.ticket.contact.entered", "- Check that a Contact is selected\r\n");
      formTest = false;
    }
    if ((form.username.value == "")) {
      message += label("check.username", "- Check that a Username is entered\r\n");
      formTest = false;
    }
    if (!form.generatePassword.checked) {
      if ((form.password1.value == "") || (form.password2.value == "") || (form.password1.value != form.password2.value)) {
        message += label("check.bothpasswords.match", "- Check that both Passwords are entered correctly\r\n");
        formTest = false;
      }
    }
    if (form.roleId.value == "-1") {
      message += label("check.role.selected", "- Check that a Role is selected\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      return true;
    }
  }
  function updateReportsToList() {
    var sel = document.forms['addUser'].elements['siteId'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "Users.do?command=ReportsToJSList&form=addUser&widget=managerId&siteId=" + escape(value);
    window.frames['server_commands'].location.href = url;
  }
  
  function updatePassword(form) {
    var password1 = form.password1;
    var password2 = form.password2;
    var generatePasswd = form.generatePassword;
    if (generatePasswd.checked) {
      password1.value="";
      password2.value="";
      password1.disabled=true;
      password2.disabled=true;
    } else {
      password1.disabled=false;
      password2.disabled=false;
    }
  }
</script>

<form name="addUser" action="Users.do?command=AddUser&auto-populate=true"
      onSubmit="return checkForm(this);" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
      <dhv:label name="admin.addUser">Add User</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= hasText(APP_SIZE) %>">
  <table class="note" cellspacing="0">
    <tr>
      <th><img src="images/icons/stock_about-16.gif" border="0"
               align="absmiddle"/></th>
      <td><dhv:label name="admin.licenseLimit.text"
                     param='<%= "appsize="+APP_SIZE %>'>The installed license
        limits this system to <%= APP_SIZE %> active users.</dhv:label></td>
    </tr>
  </table>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<dhv:formMessage showSpace="false"/>
<tr>
  <th colspan="2">
    <strong><dhv:label name="admin.addUserAccount">Add a user
      account</dhv:label></strong>
  </th>
</tr>
<tr>
  <td class="formLabel">
    <dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label>
  </td>
  <td>
    <table border="0" cellspacing="0" cellpadding="4" class="empty">
      <tr>
        <td valign="top" nowrap>
          <div id="changecontact">
            <% if (UserRecord.getContactId() != -1) {%>
            <%= toHtml(UserRecord.getContact().getNameLastFirst()) %>
            <%} else {%>
            <dhv:label name="accounts.accounts_add.NoneSelected">None
              Selected</dhv:label>
            <%}%>
          </div>
        </td>
        <td valign="top" width="100%" nowrap>
          <font color="red">
            *</font><%= showAttribute(request, "contactIdError") %>
          [<a
            href="javascript:popContactsListSingle('contactLink','changecontact','&listView=employees<%= User.getUserRecord().getSiteId() == -1?"&includeAllSites=true&siteId=-1":"&mySiteOnly=true&siteId="+User.getUserRecord().getSiteId() %>&nonUsersOnly=true&reset=true&filters=accountcontacts|employees');"><dhv:label name="admin.selectContact">Select Contact</dhv:label></a>]
          <input type="hidden" name="contactId" id="contactLink"
                 value="<%= UserRecord.getContactId() %>">
          [<a
            href="javascript:popURL('ExternalContacts.do?command=Prepare&popup=true&source=adduser', 'New_Contact','600','550','yes','yes');"><dhv:label name="admin.createContact">Create new contact</dhv:label></a>]
        </td>
      </tr>
    </table>
  </td>
</tr>
<tr>
  <td class="formLabel">
    <dhv:label name="admin.uniqueUsername">Unique Username</dhv:label>
  </td>
  <td>
    <input type="text" name="username"
           value="<%= toHtmlValue(UserRecord.getUsername()) %>"><font color=red>
    *</font>
    <%= showAttribute(request, "usernameError") %>
  </td>
</tr>
<tr>
  <td class="formLabel">
    <dhv:label name="">Generate new Password</dhv:label>
  </td>
  <td>
    <table border="0" cellspacing="0" cellpadding="4" class="empty">
      <tr>
        <td valign="top" nowrap>
          <input type="checkbox" name="generatePassword" id="generatePassword" value="true" <%= request.getAttribute("generatePassword") != null?"checked":"" %> onClick="javascript:updatePassword(this.form);" />
        </td>
        <td valign="top">
          <dhv:label name="">A new password will be created and emailed to the user. Contact email address is required for this option.</dhv:label>
        </td>
      </tr>
    </table>
  </td>
</tr>
<tr>
  <td class="formLabel">
    <dhv:label name="admin.password">Password</dhv:label>
  </td>
  <td>
    <input type="password" name="password1"><font color=red>*</font>
    <%= showAttribute(request, "password1Error") %>
  </td>
</tr>
<tr>
  <td class="formLabel">
    <dhv:label name="admin.passwordAgain">Password (again)</dhv:label>
  </td>
  <td>
    <input type="password" name="password2"><font color=red>*</font>
    <%= showAttribute(request, "password2Error") %>
  </td>
</tr>
<tr>
  <td class="formLabel">
    <dhv:label name="project.role">Role</dhv:label>
  </td>
  <td>
    <%= RoleList.getHtmlSelect("roleId", UserRecord.getRoleId()) %><font
      color="red">*</font>
    <%= showAttribute(request, "roleError") %>
  </td>
</tr>
<tr>
  <td nowrap class="formLabel">
    <dhv:label name="admin.user.site">Site</dhv:label>
  </td>
  <td>
    <dhv:evaluate if="<%=User.getSiteId() == - 1%>">
      <% SiteIdList.setJsEvent("onChange=\"javascript:updateReportsToList();\""); %>
      <%= SiteIdList.getHtmlSelect("siteId", UserRecord.getSiteId()) %>
      <%= showAttribute(request, "siteIdError") %>
    </dhv:evaluate>
    <dhv:evaluate if="<%=User.getSiteId() != - 1%>">
      <%= SiteIdList.getSelectedValue(User.getSiteId()) %>
      <input type="hidden" name="siteId" value="<%=User.getSiteId()%>">
    </dhv:evaluate>
  </td>
</tr>
<tr>
  <td class="formLabel">
    <dhv:label name="admin.reportsTo">Reports To</dhv:label>
  </td>
  <td>
    <%= UserList.getHtmlSelect("managerId", UserRecord.getManagerId()) %>
    <%= showAttribute(request, "managerIdError") %>
  </td>
</tr>
<tr>
  <td class="formLabel">
    <dhv:label name="admin.access">Access</dhv:label>
  </td>
  <td>
    <input type="checkbox" name="hasWebdavAccess"
           value="ON"<%= UserRecord.getHasWebdavAccess() ? " checked" : "" %>>
    <dhv:label name="user.allow.webdavAccess">Allow Webdav access</dhv:label>
    <br/>
    <input type="checkbox" name="hasHttpApiAccess"
           value="ON"<%= UserRecord.getHasHttpApiAccess() ? " checked" : "" %>>
    <dhv:label name="user.allow.httpApiAccess">Allow HTTP-API access</dhv:label>
  </td>
</tr>
<dhv:permission name="demo-view">
  <tr>
    <td class="formLabel">
      <dhv:label name="admin.aliasUser">Alias User</dhv:label>
    </td>
    <td>
      <%= UserList.getHtmlSelect("alias", UserRecord.getAlias()) %>
    </td>
  </tr>
</dhv:permission>
<tr>
  <td class="formLabel">
    <dhv:label name="admin.expireDate">Expire Date</dhv:label>
  </td>
  <td>
    <zeroio:dateSelect form="addUser" field="expires"
                       timestamp="<%= UserRecord.getExpires() %>"/>
    <%= showAttribute(request, "expiresError") %>
  </td>
</tr>
</table>
<br>
<input type="submit"
       value="<dhv:label name="admin.addUser">Add User</dhv:label>">
<input type="button"
       value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
       onClick="javascript:this.form.action='Users.do?command=ListUsers';this.form.submit()">
<iframe src="empty.html" name="server_commands" id="server_commands"
        style="visibility:hidden" height="0"></iframe>
</form>
<script type="text/javascript">
updatePassword(document.forms['addUser']);
</script>


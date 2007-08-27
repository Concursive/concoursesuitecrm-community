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
<%@ page import="java.text.DateFormat, org.aspcfs.modules.accounts.base.*" %>
<%@ page import="org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.login.beans.*" %>
<jsp:useBean id="history" class="org.aspcfs.modules.accounts.base.OrganizationHistory" scope="request" />
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js?v=20070827"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<SCRIPT language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></script>
<script language="JavaScript" type="text/javascript">
  function checkForm(form) {
    formTest = true;
    message = "";
    if (checkNullString(form.description.value)) { 
      message += label("description.required","- Check that description is entered\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
</script>
<body onLoad="javascript:document.addNote.description.focus();">
<form name="addNote" action="AccountsHistory.do?command=SaveNote&auto-populate=true" method="post" onSubmit="return checkForm(this);">
  <dhv:formMessage showSpace="false"/>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="qa.note">Note</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top"><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></td>
    <td width="100%">
      <table cellpadding="0" cellspacing="0" width="100%" class="empty"><tr><td>
      <textarea name="description" rows="6" cols="60"><%= toString(history.getDescription()) %></textarea></td><td valign="top" align="left" width="100%">
      <font color="red">*</font> <%= showAttribute(request, "descriptionError") %></td></tr></table>
    </td>
  </tr>
</table>
<br />
<% if (history.getId() == -1) { %>
<input type="submit" value="<dhv:label name="button.save">Save</dhv:label>" />
<%} else {%>
<input type="submit" value="<dhv:label name="button.update">Update</dhv:label>"/>
<%}%>
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();">
<input type="hidden" name="orgId" value="<%= OrgDetails.getId() %>" />
<input type="hidden" name="id" value="<%= history.getId() %>" />
<input type="hidden" name="linkObjectId" value="<%= OrganizationHistory.NOTE %>" />
<input type="hidden" name="linkItemId" value="-1" />
<input type="hidden" name="type" value="<dhv:label name="accounts.accountHistory.note">Note</dhv:label>" />
<% if (history.getId() != -1) { %>
<input type="hidden" name="status" value="<dhv:label name="button.insert">Insert</dhv:label>" />
<% } else { %>
<input type="hidden" name="status" value="<dhv:label name="global.button.update">Update</dhv:label>" />
<% } %>
<input type="hidden" name="enabled" value="true" />
<%= addHiddenParams(request, "popup|popupType|actionId") %> 
<input type="hidden" name="onlyWarnings" value="<%=(history.getOnlyWarnings()?"on":"off")%>" />
</form>
</body>

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
<jsp:useBean id="SCL" class="org.aspcfs.modules.communications.base.SearchCriteriaList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<SCRIPT LANGUAGE="JavaScript">
function checkForm(form) {
  formTest = true;
  message = "";
  if (checkNullString(form.groupName.value)) {
    message += label("check.group.name","- Group Name is required\r\n");
    formTest = false;
  }
  saveValues();
  if (form.searchCriteriaText.value == "") {
    message += label("check.criteria","- Criteria is required\r\n");
    formTest = false;
  }  
  if (formTest == false) {
    alert(label("check.campaign.criteria","Criteria could not be processed, please check the following:\r\n\r\n") + message);
    return false;
  } 
  return true;
}
</SCRIPT>
<body onLoad="javascript:document.searchForm.groupName.focus();javascript:updateOperators()">
<form name="searchForm" method="post" action="CampaignManagerGroup.do?command=Insert&auto-populate=true" onSubmit="return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<a href="CampaignManagerGroup.do?command=View"><dhv:label name="campaign.viewGroups">View Groups</dhv:label></a> >
<dhv:label name="admin.addGroup">Add a Group</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>">
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='CampaignManagerGroup.do?command=View'">
<input type="button" value="<dhv:label name="button.preview">Preview</dhv:label>" onClick="javascript:popPreview()">
<br />
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <dhv:label name="campaign.enterNewContactGroup.text">Begin by entering a name for the new contact group</dhv:label>
    </th>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="admin.groupName">Group Name</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="groupName" value="<%= toHtmlValue(SCL.getGroupName()) %>"><font color="red">*</font> <%= showAttribute(request, "groupNameError") %>
    </td>
  </tr>
</table>
&nbsp;<br />
<%-- include jsp for contact criteria --%>
<%@ include file="group_criteria_include.jsp" %>
<br />
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>">
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='CampaignManagerGroup.do?command=View'">
<input type="button" value="<dhv:label name="button.preview">Preview</dhv:label>" onClick="javascript:popPreview()">
</form>
</body>

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
<SCRIPT LANGUAGE="JavaScript">
function checkForm(form) {
  formTest = true;
  message = "";
  if (form.groupName.value == "") {
    message += "- Group Name is required\r\n";
    formTest = false;
  }
  if (formTest == false) {
    alert("Criteria could not be processed, please check the following:\r\n\r\n" + message);
    return false;
  } 
  saveValues();
  return true;
}
</SCRIPT>
<body onLoad="javascript:document.forms[0].groupName.focus()">
<form name="searchForm" method="post" action="CampaignManagerGroup.do?command=Insert&auto-populate=true" onSubmit="return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do">Communications</a> >
<a href="CampaignManagerGroup.do?command=View">View Groups</a> >
Add a Group
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="Save">
<input type="button" value="Cancel" onClick="javascript:window.location.href='CampaignManagerGroup.do?command=View'">
<input type="button" value="Preview" onClick="javascript:popPreview()">
<br>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      Begin by entering a name for the new contact group
    </th>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Group Name
    </td>
    <td>
      <input type="text" size="40" name="groupName" value="<%= toHtmlValue(SCL.getGroupName()) %>"><font color="red">*</font> <%= showAttribute(request, "groupNameError") %>
    </td>
  </tr>
</table>
<%-- include jsp for contact criteria --%>
<%@ include file="group_criteria_include.jsp" %>
&nbsp;<br>
<input type="submit" value="Save">
<input type="button" value="Cancel" onClick="javascript:window.location.href='CampaignManagerGroup.do?command=View'">
<input type="button" value="Preview" onClick="javascript:popPreview()">
</form>
</body>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="SCL" class="org.aspcfs.modules.communications.base.SearchCriteriaList" scope="request"/>
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
<a href="CampaignManager.do">Communications Manager</a> >
<a href="CampaignManagerGroup.do?command=View">View Groups</a> >
Add a Group
<hr color="#BFBFBB" noshade>
<input type="submit" value="Save">
<input type="button" value="Cancel" onClick="javascript:window.location.href='CampaignManagerGroup.do?command=View'">
<input type="button" value="Preview" onClick="javascript:popPreview()">
<br>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      Begin by entering a name for the new contact group
    </td>
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
&nbsp;<br>

<%-- include jsp for contact criteria --%>
<%@ include file="group_criteria_form.jsp" %>

&nbsp;<br>
<input type="submit" value="Save">
<input type="button" value="Cancel" onClick="javascript:window.location.href='CampaignManagerGroup.do?command=View'">
<input type="button" value="Preview" onClick="javascript:popPreview()">
</form>
</body>

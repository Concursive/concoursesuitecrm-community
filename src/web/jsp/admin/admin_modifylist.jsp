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
<jsp:useBean id="SelectedList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="moduleId" class="java.lang.String" scope="request"/>
<jsp:useBean id="SubTitle" class="java.lang.String" scope="request"/>
<jsp:useBean id="category" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/editListForm.js"></script>
<script language="JavaScript" type="text/javascript">
  function doCheck() {
    if (document.modifyList.dosubmit.value == "false") {
      return true;
    }
    var test = document.modifyList.selectedList;
    if (test != null) {
      return selectAllOptions(document.modifyList.selectedList);
    }
  }
</script>
<body onLoad="javascript:document.forms['modifyList'].newValue.focus();">
<form name="modifyList" method="post" action="Admin.do?command=UpdateList" onSubmit="return doCheck();">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%=moduleId%>"><%=PermissionCategory.getCategory()%></a> >
<a href="Admin.do?command=EditLists&moduleId=<%=moduleId%>"><dhv:label name="admin.lookupLists">Lookup Lists</dhv:label></a> > 
<dhv:label name="admin.editList">Edit List</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="3">
      <strong><%= toHtml(SubTitle) %></strong>
    </th>
  </tr>
  <tr>
    <td width="50%">
      <table width="100%" cellspacing="0" cellpadding="2" border="0" class="empty">
        <tr>
          <td valign="center">
            <dhv:label name="admin.newOption">New Option</dhv:label>
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="text" name="newValue" value="" size="25" maxlength="300">
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="button" name="addButton" value="<dhv:label name="accounts.accounts_reports_generate.AddR">Add ></dhv:label>" onclick="javascript:addValues()">
          </td>
        </tr>
      </table>
    </td>
    <td width="25">
      <table width="100%" cellspacing="0" cellpadding="2" border="0" class="empty">
        <tr>
          <td valign="center">
            <input type="button" value="<dhv:label name="global.button.Up">Up</dhv:label>" onclick="javascript:moveOptionUp(document.modifyList.selectedList)">
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="button" value="<dhv:label name="global.button.Down">Down</dhv:label>" onclick="javascript:moveOptionDown(document.modifyList.selectedList)">
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="button" value="<dhv:label name="button.remove">Remove</dhv:label>" onclick="javascript:removeValues()">
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="button" value="<dhv:label name="button.sort">Sort</dhv:label>" onclick="javascript:sortSelect(document.modifyList.selectedList)">
          </td>
        </tr>
      </table>
    </td>
    <td width="50%">
      <%= SelectedList.getHtmlSelect("selectedList",0) %>
    </td>
  </tr>
  <tr>
    <td colspan="3">
      <input type="hidden" name="selectNames" value="">
      <input type="hidden" name="moduleId" value="<%= moduleId %>">
      <input type="hidden" name="dosubmit" value="true">
      <input type="hidden" name="tableName" value="<%= SelectedList.getTableName() %>">
      <input type="hidden" name="category" value="<%= category %>">
      <input type="submit" value="<dhv:label name="button.saveChanges">Save Changes</dhv:label>" onClick="javascript:this.form.dosubmit.value='true';">
      <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='Admin.do?command=EditLists'">
    </td>
  </tr>
</table>
</form>

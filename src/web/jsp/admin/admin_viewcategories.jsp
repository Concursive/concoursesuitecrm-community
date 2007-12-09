<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.*,org.aspcfs.modules.troubletickets.base.*, java.util.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="categoryEditor" class="org.aspcfs.modules.admin.base.CategoryEditor" scope="request"/>
<jsp:useBean id="departmentSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/> 
<jsp:useBean id="userSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="draftAssignment" class="org.aspcfs.modules.troubletickets.base.TicketCategoryDraftAssignment" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="siteId" class="java.lang.String" scope="request"/>
<%
  HashMap selectedCategories = (HashMap) request.getSession().getAttribute("selectedCategories" + categoryEditor.getConstantId());
%>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js?v=20070827"></script>
<script language="JavaScript" type="text/javascript" src="javascript/confirmDelete.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></SCRIPT>
<form name="draftCategories" action="AdminCategories.do?command=&moduleId=<%= PermissionCategory.getId() %>&constantId=<%= request.getParameter("constantId") %>">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> > 
<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
<a href="AdminCategories.do?command=Show&moduleId=<%= PermissionCategory.getId() %>&constantId=<%= request.getParameter("constantId") %>"><dhv:label name="product.Categories">Categories</dhv:label></a> >
<dhv:label name="product.editor">Editor</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<script type="text/javascript">
function reopen() {
  var siteId = document.getElementById("siteId").value;
  var url = 'AdminCategories.do?command=View&moduleId=<%= PermissionCategory.getId() %>&constantId=<%= request.getParameter("constantId") %>&siteId='+siteId+'&popup=<%= isPopup(request) %>';
  window.location.href=url;
}

function changeDivContent(divName, divContents) {
<dhv:permission name="admin-sysconfig-categories-edit">
  if(document.layers){
    // Netscape 4 or equiv.
    divToChange = document.layers[divName];
    divToChange.document.open();
    divToChange.document.write(divContents);
    divToChange.document.close();
  } else if(document.all){
    // MS IE or equiv.
    divToChange = document.all[divName];
    divToChange.innerHTML = divContents;
  } else if(document.getElementById){
    // Netscape 6 or equiv.
    divToChange = document.getElementById(divName);
    divToChange.innerHTML = divContents;
  }
  //update the draftAssignment with the user group value
  var siteId = document.getElementById("siteId").value;
  if (divName == 'changeUserGroup') {
    var form = document.forms['draftCategories'];
    var categoryIdValue = form.categoryId.value;
    var userGroupValue = form.userGroupId.value;
    if (form.updateUserGroup.value == 'true') {
      form.updateUserGroup.value = false;
      var url='AdminCategories.do?command=UpdateAssignment&moduleId=<%= PermissionCategory.getId() %>&siteId='+siteId+'&constantId=<%= request.getParameter("constantId") %>&userGroupId='+userGroupValue+'&categoryId='+categoryIdValue;
      window.frames['server_commands'].location.href=url;
    }
  } else if (divName == 'changeowner') {
    var form = document.forms['draftCategories'];
    var assignedTo = form.assignedTo.value;
    var categoryIdValue = form.categoryId.value;
    var updateAssignedTo = form.updateAssignedTo.value;
    if (form.updateAssignedTo.value == 'true') {
      form.updateAssignedTo.value = false;
      var url='AdminCategories.do?command=UpdateAssignment&moduleId=<%= PermissionCategory.getId() %>&siteId='+siteId+'&constantId=<%= request.getParameter("constantId") %>&assignedTo='+assignedTo+'&categoryId='+categoryIdValue;
      window.frames['server_commands'].location.href=url;
    }
  }
</dhv:permission>
}

function updateDepartment() {
<dhv:permission name="admin-sysconfig-categories-edit">
  var siteId = document.getElementById("siteId").value;
  var form = document.forms['draftCategories'];
  var deptId = form.departmentId.options[form.departmentId.selectedIndex].value;
  var categoryIdValue = form.categoryId.value;
  if (categoryIdValue != '-1') {
    var url='AdminCategories.do?command=UpdateAssignment&moduleId=<%= PermissionCategory.getId() %>&siteId='+siteId+'&constantId=<%= request.getParameter("constantId") %>&departmentId='+deptId+'&assignedTo=-1&categoryId='+categoryIdValue;
    window.frames['server_commands'].location.href=url;
    form.updateAssignedTo.value = false;
    changeDivContent('changeowner', label('none.selected','None Selected'));
  } else {
    alert(label('','Please select a category'));
  }
</dhv:permission>
}

function loadCategories(level) {
  var siteId = document.getElementById("siteId").value;
  var url = "";
  if (document.getElementById('level' + level).options.selectedIndex > -1) {
    var categoryId =  document.getElementById('level' + level).options[document.getElementById('level' + level).options.selectedIndex].value;
    if (level <= <%= categoryEditor.getMaxLevels() - 1 %> && categoryId != -1){
      url = 'AdminCategories.do?command=CategoryJSList&moduleId=<%= PermissionCategory.getId() %>&siteId='+siteId+'&constantId=<%= request.getParameter("constantId") %>&categoryId='+ categoryId + '&level=' + level+'<%= isPopup(request)?"&popup=true":"" %>';
      window.frames['server_commands'].location.href=url;
      if (level < <%= categoryEditor.getMaxLevels() - 1 %>) {
        processButtons(level);
      }
    }
    <dhv:evaluate if="<%= PermissionCategory.getId() == 8 %>">
    if (level == <%= categoryEditor.getMaxLevels() -1 %> && categoryId != -1) {
      url = 'AdminCategories.do?command=CategoryJSList&moduleId=<%= PermissionCategory.getId() %>&siteId='+siteId+'&constantId=<%= request.getParameter("constantId") %>&categoryId=' + categoryId + '&level=' + level+'<%= isPopup(request)?"&popup=true":"" %>';
      window.frames['server_commands'].location.href=url;
    }
    <dhv:permission name="admin-actionplans-view">
    if (level <= <%= categoryEditor.getMaxLevels() -1 %> && categoryId != -1) {
      document.getElementById('map').disabled = false;
    }
    </dhv:permission>
    </dhv:evaluate>
  }
}

function loadTopCategories() {
  var siteId = document.getElementById("siteId").value;
  var url = 'AdminCategories.do?command=CategoryJSList&constantId=<%= request.getParameter("constantId") %>&siteId='+siteId+'&moduleId=<%= PermissionCategory.getId() %>&categoryId=-1&level=-1<%= isPopup(request)?"&popup=true":"" %>';
  window.frames['server_commands'].location.href=url;
  <dhv:evaluate if="<%= PermissionCategory.getId() == 8 %>">
    document.getElementById('map').disabled = true;
  </dhv:evaluate>
  for (i = 1; i < <%= categoryEditor.getMaxLevels() %>; i++){
    document.getElementById('edit' + i).disabled = true;
    <dhv:evaluate if="<%= PermissionCategory.getId() == 8 %>">
    <dhv:permission name="admin-sysconfig-categories-edit"><dhv:permission name="admin-actionplans-view">
    document.getElementById('map').disabled = true;
    </dhv:permission></dhv:permission></dhv:evaluate>
  }
}

function editCategory(level) {
  var siteId = document.getElementById("siteId").value;
  var categoryId = -1;
  var tmpLevel = parseInt(level) -1;
  if(tmpLevel > -1){
    if (document.getElementById('level' + tmpLevel).selectedIndex > -1) {
      categoryId =  document.getElementById('level' + tmpLevel).options[document.getElementById('level' + tmpLevel).selectedIndex].value;
    }
  }
  if (tmpLevel == -1 || categoryId > -1) {
    var url = 'AdminCategories.do?command=Modify&constantId=<%= request.getParameter("constantId") %>&siteId='+siteId+'&moduleId=<%= PermissionCategory.getId() %>&categoryId=' + categoryId + '&level=' + level+'&constantId=<%= request.getParameter("constantId") %>&popup=true';
    popURL(url, 'Modify_Category','540','250','yes','no');
  }
}

function mapCategory(level){
  var siteId = document.getElementById("siteId").value;
  var categoryId = -1;
  var tmpLevel = parseInt(level);
  if (document.getElementById('level'+tmpLevel).selectedIndex > -1) {
    categoryId =  document.getElementById('level'+tmpLevel).options[document.getElementById('level'+tmpLevel).selectedIndex].value;
  }
  if (categoryId > -1) {
    var url = 'AdminCategories.do?command=AddMapping&constantId=<%= request.getParameter("constantId") %>&siteId='+siteId+'&moduleId=<%= PermissionCategory.getId() %>&categoryId=' + categoryId + '&level=' + level+'&popup=true';
    popURL(url, 'Map_Category','600','400','yes','yes');
  }
}

function editPlans() {
  var siteId = document.getElementById("siteId").value;
  var categoryId = document.getElementById('categoryId').value;
  if (categoryId != '' && categoryId != '-1') {
    var url = 'AdminCategories.do?command=AddMapping&constantId=<%= request.getParameter("constantId") %>&siteId='+siteId+'&moduleId=<%= PermissionCategory.getId() %>&categoryId=' + categoryId +'&popup=true';
    popURL(url, 'Map_Category','600','400','yes','yes');
  }
}

function resetCategories() {
  var siteId = document.getElementById('siteId').value;
  confirmReset('AdminCategories.do?command=Reset&constantId=<%= request.getParameter("constantId") %>&moduleId=<%= PermissionCategory.getId() %>&popup=<%= isPopup(request)?"true":"" %>&siteId='+siteId, label('confirm.looseChanges','You will lose all the changes made to the draft. Proceed ?'));
}

function confirmReset(url, msg) {
  if (confirm(msg)) {
    window.location = url;
  }
}

function processButtons(level){
  if(document.getElementById('level' + level).selectedIndex != -1){
    for (i = (parseInt(level) + 1); i < <%= categoryEditor.getMaxLevels() %>; i++){
      document.getElementById('edit' + i).disabled = true;
    }
  }
  document.getElementById('edit' + (parseInt(level) + 1)).disabled = false;
}

function activate(){
 var siteId = document.getElementById("siteId").value;
 var catList = document.getElementById('level0');
  if(catList.length > 0 && catList.options[0].value != -1){
    confirmForward('AdminCategories.do?command=Activate&constantId=<%= request.getParameter("constantId") %>&siteId='+siteId+'&moduleId=<%= PermissionCategory.getId() %><%= isPopup(request)?"&popup=true":"" %>');
  } else {
    alert(label("alert.noentriestoactivate",'No entries to activate'));
  }
}
</script>
<strong><dhv:label name="categories.categoriesForSite">Categories for Site</dhv:label></strong>&nbsp;
<% if(User.getUserRecord().getSiteId() == -1) { %>
<% SiteIdList.setJsEvent("onChange=\"javascript:reopen();\" id=\"siteId\""); %>
<%= SiteIdList.getHtmlSelect("siteId", (siteId != null && !"".equals(siteId.trim())?Integer.parseInt(siteId):User.getUserRecord().getSiteId())) %>
<%} else {%>
<input type="hidden" name="siteId" id="siteId" value="<%= User.getUserRecord().getSiteId() %>"/>
<%= SiteIdList.getSelectedValue(User.getUserRecord().getSiteId()) %>
<%}%>
<% String param1 = "moduleId=" + PermissionCategory.getId(); %>
<% String param2 = "constantId=" + request.getParameter("constantId"); %>
<% String param3 = "popup=" + (isPopup(request)?"true":"false"); %>
<% String param4 = "siteId="+(siteId != null?siteId:String.valueOf(User.getUserRecord().getSiteId())); %>
<dhv:container name="categories" selected="draft categories" param='<%= param1 + "|" + param2 + "|" + param3 + "|" + param4 %>' style="tabs">
  <table border="0" cellpadding="2" cellspacing="0" class="empty">
    <tr>
      <td align="center">
        <dhv:label name="admin.level1">Level 1</dhv:label><br>
        <% int value = ((selectedCategories.get(new Integer(0)) != null) ? ((Integer) selectedCategories.get(new Integer(0))).intValue() : -1);
        categoryEditor.getTopCategoryList().getCatListSelect().setSelectSize(10);
        categoryEditor.getTopCategoryList().setHtmlJsEvent("onChange=\"javascript:loadCategories('0');\"");
        categoryEditor.getTopCategoryList().getCatListSelect().addAttribute("style", "width: 150px");
        %>
        <%= categoryEditor.getTopCategoryList().getHtmlSelect("level0", value) %><br />
        <dhv:permission name="admin-sysconfig-categories-edit"><input type="button" value="<dhv:label name="button.edit">Edit</dhv:label>" id="edit0" onClick="javascript:editCategory('0');"></dhv:permission>
      </td>
<%-- Variably draw the rest of the editors --%>
<%
   for (int k = 1; k < categoryEditor.getMaxLevels(); k++) {
     TicketCategoryDraftList thisSubList = (TicketCategoryDraftList) request.getAttribute("SubList" + k);
     if (thisSubList == null) {
       thisSubList = new TicketCategoryDraftList();
     }
%>
      <td align="center">
        <dhv:label name="admin.level" param='<%= "level="+ (k+1) %>'>Level <%= k + 1 %></dhv:label><br>
<%
        value = ((selectedCategories.get(new Integer(k)) != null) ? ((Integer) selectedCategories.get(new Integer(k))).intValue() : -1);
        thisSubList.getCatListSelect().setSelectSize(10);
        thisSubList.getCatListSelect().addAttribute("onChange", "javascript:loadCategories('" + k + "');");
        thisSubList.getCatListSelect().addAttribute("style", "width: 150px");
%>
        <%= thisSubList.getHtmlSelect("level" + k, value) %><br>
        <dhv:permission name="admin-sysconfig-categories-edit"><input type="button" value="<dhv:label name="button.edit">Edit</dhv:label>" id="edit<%= k %>" onClick="javascript:editCategory('<%= k %>');" disabled></dhv:permission>
      </td>
<%
   }
%>
    </tr>
<dhv:evaluate if="<%= PermissionCategory.getId() == 8 %>">
    <tr>
      <td colspan="<%= categoryEditor.getMaxLevels() %>" width="100%">
      <table border="0" cellpadding="2" cellspacing="0" width="100%" class="details">
        <tr><th colspan="2"><strong><dhv:label name="project.assignment">Assignment</dhv:label></strong></th></tr>
        <tr class="containerBody">
          <td class="formLabel" valign="top" nowrap><dhv:label name="project.department">Department</dhv:label></td>
          <td><%= departmentSelect.getHtmlSelect("departmentId", draftAssignment.getDepartmentId()) %></td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" valign="top" nowrap><dhv:label name="project.resourceAssigned">Resource Assigned</dhv:label></td>
          <td>
            <table class="empty">
              <tr>
                <td>
                  <div id="changeowner">
                  <%if (draftAssignment.getAssignedTo() > 0) { %>
                    <dhv:username id="<%= draftAssignment.getAssignedTo() %>"/>
                  <% }else{ %>
                    <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
                  <%}%>
                  </div>
                </td>
                <td>
                  <input type="hidden" name="updateAssignedTo" id="updateAssignedTo" value="false"/>
                  <input type="hidden" name="assignedTo" id="assignedTo" value="<%= draftAssignment.getAssignedTo() %>">
                  &nbsp;[<a href="javascript:if (document.forms['draftCategories'].categoryId.value!='-1') {document.forms['draftCategories'].updateAssignedTo.value='true';popContactsListSingle('assignedTo','changeowner', 'listView=employees&usersOnly=true&departmentId='+document.getElementById('departmentId').options[document.getElementById('departmentId').selectedIndex].value+'&reset=true&siteId='+document.getElementById('siteId').value);} else {alert(label('','Please select a category'));}"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
                  [<a href="javascript:if (document.forms['draftCategories'].categoryId.value!='-1') {document.forms['draftCategories'].assignedTo.value='-1';document.forms['draftCategories'].updateAssignedTo.value='true';changeDivContent('changeowner', label('none.selected','None Selected'));} else {alert(label('','Please select a category'));}"><dhv:label name="button.clear">Clear</dhv:label></a>]
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" valign="top" nowrap><dhv:label name="usergroup.assignedGroup">Assigned Group</dhv:label></td>
          <td>
            <table cellspacing="0" cellpadding="0" border="0" class="empty">
              <tr>
                <td>
                  <div id="changeUserGroup">
                    <dhv:evaluate if="<%= draftAssignment.getUserGroupId() != -1 %>">
                      <%= toHtml(draftAssignment.getUserGroupName()) %>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= draftAssignment.getUserGroupId() == -1 %>">
                      <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
                    </dhv:evaluate>
                  </div>
                </td>
                <td>
                  <input type="hidden" name="categoryId" id="categoryId" value="<%= draftAssignment.getCategoryId() %>"/>
                  <input type="hidden" name="updateUserGroup" id="updateUserGroup" value="false" />
                  <input type="hidden" name="userGroupId" id="userGroupId" value="<%= draftAssignment.getUserGroupId() %>"/> &nbsp;
                  [<a href="javascript:if (document.forms['draftCategories'].categoryId.value!='-1') {document.forms['draftCategories'].updateUserGroup.value='true';popUserGroupsListSingle('userGroupId','changeUserGroup', '&userId=<%= User.getUserRecord().getId() %>&siteId='+document.getElementById('siteId').value);} else {alert(label('','Please select a category'));}"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>] &nbsp;
                  [<a href="javascript:if (document.forms['draftCategories'].categoryId.value!='-1') {document.forms['draftCategories'].userGroupId.value='-1';document.forms['draftCategories'].updateUserGroup.value='true';changeDivContent('changeUserGroup', label('none.selected','None Selected'));} else {alert(label('','Please select a category'));}"><dhv:label name="button.clear">Clear</dhv:label></a>]
                  <%= showAttribute(request, "userGroupError") %>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" valign="top" nowrap><dhv:label name="sales.actionPlans">Action Plans</dhv:label></td>
          <td>
            <table border="0" cellpadding="2" cellspacing="0" width="100%" class="empty"><tr>
              <td><select name="actionPlanList" id="actionPlanList" size="4"><option value="-1"><dhv:label name="calendar.none.4dashes">-- None --</dhv:label></option></select> &nbsp;
              </td><td valign="top" align="left" width="100%"><dhv:permission name="admin-actionplans-view">
                <input type="button" value="<dhv:label name="button.map">Map</dhv:label>" id="map" onClick="javascript:editPlans();" disabled>
              </dhv:permission></td>
            </tr></table>
          </td>
        </tr>
      </table>
      </td>
    </tr>
</dhv:evaluate>
</table>
</dhv:container>
<dhv:permission name="admin-sysconfig-categories-edit"><br>
<input type="button" value="<dhv:label name="button.revertToActiveList">Revert to Active List</dhv:label>" onClick="javascript:resetCategories();">
<input type="button" value="<dhv:label name="button.activateNow">Activate Now</dhv:label>" onClick="javascript:activate();"></dhv:permission>
<%-- script to enable edit buttons if any categories are selected --%>
<script>
<%
  HashMap categories  = categoryEditor.getCategoryList();
  boolean done = false;
  TicketCategoryDraft thisCat = null;
  for (int k = 0; k < categoryEditor.getMaxLevels(); k++) {
    if (selectedCategories.get(new Integer(k)) != null) {
    thisCat = (TicketCategoryDraft) categories.get((Integer) (selectedCategories.get(new Integer(k))));
    if(thisCat.getParentCode() != 0){
    TicketCategoryDraft parentCat = (TicketCategoryDraft) categories.get(new Integer(thisCat.getParentCode()));
    if(!parentCat.getEnabled()){
      if (PermissionCategory.getId() == 8) {
    %>
<dhv:permission name="admin-actionplans-view">
<%--      document.getElementById('map').disabled = true; --%>
</dhv:permission>
    <%}%>
      document.getElementById('edit' + <%= k %>).disabled = true;
  <%}else{
      if (PermissionCategory.getId() == 8) {%>
<dhv:permission name="admin-actionplans-view">
<%--    document.getElementById('map').disabled = false; --%>
</dhv:permission>
    <%}%>
      document.getElementById('edit' + <%= k %>).disabled = false;
    <% } 
     }
    }else{
    if(thisCat != null && !thisCat.getEnabled()){ %>
      document.getElementById('edit' + <%= k %>).disabled = true;
    <% }else{ %>
      document.getElementById('edit' + <%= k %>).disabled = false;
    <% }
      break;
    }
 }
%>
</script>
<dhv:formMessage />
</form>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

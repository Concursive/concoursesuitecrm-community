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
<%@ page import="java.util.*,org.aspcfs.modules.actionplans.base.*,org.aspcfs.modules.admin.base.*" %>
<jsp:useBean id="actionPlan" class="org.aspcfs.modules.actionplans.base.ActionPlan" scope="request"/>
<jsp:useBean id="durationTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.actionplans.base.ActionPlanCategoryList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.actionplans.base.ActionPlanCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.actionplans.base.ActionPlanCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.actionplans.base.ActionPlanCategoryList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/> 
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="constantId" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">

  function reopen(extension) {
    window.location.href='ActionPlanEditor.do?command=AddPlan&moduleId=<%= permissionCategory.getId() %>&constantId=<%= constantId %>'+extension;
  }

  function checkForm(form) {
    return true;
  }
  function updateCatCode() {
    var siteId = document.forms['addActionPlan'].siteId.value;
    var url = "ActionPlanEditor.do?command=CategoryJSList&form=addActionPlan&siteId="+siteId+"&reset=true";
    window.frames['server_commands'].location.href=url;
  }
  
  function updateSubList1() {
    var sel = document.forms['addActionPlan'].elements['catCode'];
    var value = sel.options[sel.selectedIndex].value;
    var siteId = document.forms['addActionPlan'].siteId.value;
    var url = "ActionPlanEditor.do?command=CategoryJSList&form=addActionPlan&catCode=" + escape(value) + '&siteId='+siteId;
    window.frames['server_commands'].location.href=url;
  }
  
  function updateSubList2() {
    var sel = document.forms['addActionPlan'].elements['subCat1'];
    var value = sel.options[sel.selectedIndex].value;
    var siteId = document.forms['addActionPlan'].siteId.value;
    var url = "ActionPlanEditor.do?command=CategoryJSList&form=addActionPlan&subCat1=" + escape(value) + '&siteId='+siteId;
    window.frames['server_commands'].location.href=url;
  }
  
  function updateSubList3() {
    var sel = document.forms['addActionPlan'].elements['subCat2'];
    var value = sel.options[sel.selectedIndex].value;
    var siteId = document.forms['addActionPlan'].siteId.value;
    var url = "ActionPlanEditor.do?command=CategoryJSList&form=addActionPlan&subCat2=" + escape(value) + '&siteId='+siteId;
    window.frames['server_commands'].location.href=url;
  }
</script>
<form name="addActionPlan" action="ActionPlanEditor.do?command=SavePlan&auto-populate=true" method="post" onSubmit="return checkForm(this);">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="enabled" value="<%= actionPlan.getEnabled()?"true":"false" %>">
<input type="hidden" name="id" value="<%= actionPlan.getId() %>"/>
<input type="hidden" name="linkObjectId" value="<%= constantId %>"/>
<input type="hidden" name="constantId" value="<%= constantId %>"/>
<input type="hidden" name="moduleId" value="<%= permissionCategory.getId() %>"/>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
  <a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
  <a href="Admin.do?command=ConfigDetails&moduleId=<%= permissionCategory.getId() %>"><%= toHtml(permissionCategory.getCategory()) %></a> >
  <a href="ActionPlanEditor.do?command=ListEditors&moduleId=<%= permissionCategory.getId() %>"><dhv:label name="actionPlan.actionPlanEditors">Action Plan Editors</dhv:label></a> >
  <a href="ActionPlanEditor.do?command=ListPlans&moduleId=<%= permissionCategory.getId() %>&constantId=<%= constantId %>"><dhv:label name="sales.actionPlans">Action Plans</dhv:label></a> >
  <dhv:evaluate if="<%= actionPlan.getId() == -1 %>">
    <dhv:label name="actionPlan.addActionPlan">Add Action Plan</dhv:label>
  </dhv:evaluate><dhv:evaluate if="<%= actionPlan.getId() != -1 %>">
    <a href="ActionPlanEditor.do?command=PlanDetails&planId=<%= actionPlan.getId() %>&moduleId=<%= permissionCategory.getId() %>&constantId=<%= constantId %>&return=list"><dhv:label name="actionPlan.actionPlanDetails">Action Plan Details</dhv:label></a> >
    <dhv:label name="actionPlan.modifyActionPlan">Modify Action Plan</dhv:label>
  </dhv:evaluate>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:formMessage showSpace="true"/>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr><th colspan="2"><strong>
  <dhv:evaluate if="<%= actionPlan.getId() == -1 %>">
    <dhv:label name="actionPlan.addActionPlan">Add Action Plan</dhv:label>
  </dhv:evaluate><dhv:evaluate if="<%= actionPlan.getId() != -1 %>">
    <dhv:label name="actionPlan.modifyActionPlan">Modify Action Plan</dhv:label>
  </dhv:evaluate>
  </strong></th>
  <tr class="containerBody">
    <td class="formLabel" valign="top"><dhv:label name="quotes.productName">Name</dhv:label></td>
    <td><input type="text" name="name" value="<%= toHtmlValue(actionPlan.getName()) %>" size="58" maxlength="255"/><font color="red">*</font>
        <%= showAttribute(request, "nameError") %><input type="hidden" name="refresh" value="-1">
    </td>
  </tr>
  <tr>
    <td class="formLabel" valign="top"><dhv:label name="product.description">Description</dhv:label></td>
    <td><textarea name="description" id="description" cols="35" rows="4" ><%= toString(actionPlan.getDescription()) %></textarea>
    &nbsp;<%= showAttribute(request, "descriptionError") %>
    </td>
  </tr>
  <tr>
    <td class="formLabel" valign="top"><dhv:label name="documents.details.approved">Approved</dhv:label></td>
    <td><input type="checkbox" name="justApproved" value="true" <%= actionPlan.getApproved() != null?"checked":"" %> /></td>
  </tr>
<dhv:include name="actionplan.catCode" none="true">
	<tr>
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Category">Category</dhv:label>
    </td>
    <td>
      <%= CategoryList.getHtmlSelect("catCode", actionPlan.getCatCode()) %>
    </td>
	</tr>
</dhv:include>
<dhv:include name="actionplan.subCat1" none="true">
	<tr>
    <td class="formLabel" nowrap>
      <dhv:label name="account.ticket.subLevel1">Sub-level 1</dhv:label>
    </td>
    <td>
      <%= SubList1.getHtmlSelect("subCat1", actionPlan.getSubCat1()) %>
    </td>
	</tr>
</dhv:include>
<dhv:include name="actionplan.subCat2" none="true">
	<tr>
    <td class="formLabel" nowrap>
      <dhv:label name="account.ticket.subLevel2">Sub-level 2</dhv:label>
    </td>
    <td>
      <%= SubList2.getHtmlSelect("subCat2", actionPlan.getSubCat2()) %>
    </td>
	</tr>
</dhv:include>
<dhv:include name="actionplan.subCat3" none="true">
	<tr>
    <td class="formLabel" nowrap>
      <dhv:label name="account.ticket.subLevel3">Sub-level 3</dhv:label>
    </td>
    <td>
      <%= SubList3.getHtmlSelect("subCat3", actionPlan.getSubCat3()) %>
    </td>
	</tr>
</dhv:include>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="admin.user.site">Site</dhv:label>
    </td>
    <td>
      <dhv:evaluate if="<%= (actionPlan.getId() == -1 && User.getUserRecord().getSiteId() == -1) %>">
        <% SiteIdList.setJsEvent("onChange=\"javascript:updateCatCode();\" id=\"siteId\""); %>
        <%= SiteIdList.getHtmlSelect("siteId", actionPlan.getSiteId()) %>
        <%= showAttribute(request, "siteIdError") %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= actionPlan.getId() > -1 || User.getUserRecord().getSiteId() > -1 %>">
        <%= SiteIdList.getSelectedValue(User.getUserRecord().getSiteId() > -1?User.getUserRecord().getSiteId():actionPlan.getSiteId()) %>
        <input type="hidden" name="siteId" value="<%= actionPlan.getId() > -1 ? actionPlan.getSiteId():User.getUserRecord().getSiteId() %>" />
      </dhv:evaluate>
    </td>
  </tr>
</table>
<br />
<input type="submit" value="<dhv:label name="button.save">Save</dhv:label>"/>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="window.location.href='ActionPlanEditor.do?command=ListPlans&moduleId=<%= permissionCategory.getId() %>&constantId=<%= constantId %>';"/>
<br />
</form>
<%-- Set the default cursor location in the form --%>
<script type="text/javascript">
  document.addActionPlan.name.focus();
</script>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>


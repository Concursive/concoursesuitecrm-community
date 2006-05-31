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
<%@ page import="java.util.*,org.aspcfs.modules.actionplans.base.*,org.aspcfs.modules.admin.base.*,org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="actionPlanSelectorInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="actionPlanListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="baseList" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="mappedActionPlanList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryDraftPlanMapList" scope="request"/>
<jsp:useBean id="selectedElements" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="finalElements" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="DisplayFieldId" class="java.lang.String" scope="request"/>
<jsp:useBean id="type" class="java.lang.String" scope="request"/>
<jsp:useBean id="ticketCategoryDraft" class="org.aspcfs.modules.troubletickets.base.TicketCategoryDraft" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.actionplans.base.ActionPlanCategoryList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.actionplans.base.ActionPlanCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.actionplans.base.ActionPlanCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.actionplans.base.ActionPlanCategoryList" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="categoryEditor" class="org.aspcfs.modules.admin.base.CategoryEditor" scope="request"/>
<jsp:useBean id="siteId" class="java.lang.String" scope="request"/>
<jsp:useBean id="selectedSiteId" class="java.lang.String" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<% HashMap selectedCategories = (HashMap) request.getSession().getAttribute("selectedCategories" + categoryEditor.getConstantId());%>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/confirmDelete.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script type="text/javascript">
  function reopen(addition) {
    var url = 'AdminCategories.do?command=PopupSelector&constantId=<%= request.getParameter("constantId") %>&categoryId=<%= request.getAttribute("categoryId") %>';
    window.location.href = url + addition;
  }
  function updateSubList1() {
    var sel = document.forms['elementListView'].elements['catCode'];
    var site = document.forms['elementListView'].selectedSiteId;
    var siteId = site.options[site.options.selectedIndex].value;
    var value = sel.options[sel.options.selectedIndex].value;
    document.forms['elementListView'].searchcodeCatCode.value = value;
    if (value == '0') {
      document.forms['elementListView'].searchcodeSubCat1.value = 0;
      document.forms['elementListView'].searchcodeSubCat2.value = 0;
      document.forms['elementListView'].searchcodeSubCat3.value = 0;
    }
    var url = "ActionPlanEditor.do?command=CategoryJSList&form=elementListView&siteId="+siteId+"&catCode=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  
  function updateSubList2() {
    var sel = document.forms['elementListView'].elements['subCat1'];
    var value = sel.options[sel.selectedIndex].value;
    var site = document.forms['elementListView'].selectedSiteId;
    var siteId = site.options[site.options.selectedIndex].value;
    document.forms['elementListView'].searchcodeSubCat1.value = value;
    if (value == '0') {
      document.forms['elementListView'].searchcodeSubCat2.value = 0;
      document.forms['elementListView'].searchcodeSubCat3.value = 0;
    }
    var url = "ActionPlanEditor.do?command=CategoryJSList&form=elementListView&siteId="+siteId+"&subCat1=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  
  function updateSubList3() {
    var sel = document.forms['elementListView'].elements['subCat2'];
    var value = sel.options[sel.selectedIndex].value;
    var site = document.forms['elementListView'].selectedSiteId;
    var siteId = site.options[site.options.selectedIndex].value;
    document.forms['elementListView'].searchcodeSubCat2.value = value;
    if (value == '0') {
      document.forms['elementListView'].searchcodeSubCat3.value = 0;
    }
    var url = "ActionPlanEditor.do?command=CategoryJSList&form=elementListView&siteId="+siteId+"&subCat2=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }

  function updateSubList4() {
    var sel = document.forms['elementListView'].elements['subCat3'];
    var value = sel.options[sel.selectedIndex].value;
    document.forms['elementListView'].searchcodeSubCat3.value = value;
  }

  function selectPlan(planId) {
    url = 'AdminCategories.do?command=SaveMapping&constantId=<%= request.getParameter("constantId") %>&selectedCategoryId=<%= request.getAttribute("categoryId") %>&planId='+planId+'&popup=true';
    window.location.href = url;
  }

function loadCategories(level) {
  var url = "";
  var categoryId =  document.getElementById('level' + level).options[document.getElementById('level' + level).selectedIndex].value;
  if (level == '0') {
    document.forms['elementListView'].searchcodeCatCode.value = categoryId;
    document.forms['elementListView'].searchcodeSubCat1.value = 0;
    document.forms['elementListView'].searchcodeSubCat2.value = 0;
    document.forms['elementListView'].searchcodeSubCat3.value = 0;
  } else if (level == '1') {
    document.forms['elementListView'].searchcodeSubCat1.value = categoryId;
    document.forms['elementListView'].searchcodeSubCat2.value = 0;
    document.forms['elementListView'].searchcodeSubCat3.value = 0;
  } else if (level == '2') {
    document.forms['elementListView'].searchcodeSubCat2.value = categoryId;
    document.forms['elementListView'].searchcodeSubCat3.value = 0;
  } else if (level == '3') {
    document.forms['elementListView'].searchcodeSubCat3.value = categoryId;
  }
  if (level < <%= categoryEditor.getMaxLevels() - 1 %> && categoryId != -1){
      url = "AdminCategories.do?command=CategoryJSList&constantId=<%= PermissionCategory.MULTIPLE_CATEGORY_ACTIONPLAN %>&categoryId=" + categoryId + '&level=' + level;
      window.frames['server_commands'].location.href=url;
  }
}

function loadTopCategories() {
  var url = 'AdminCategories.do?command=CategoryJSList&constantId=<%= request.getParameter("constantId") %>&categoryId=-1&level=-1';
  window.frames['server_commands'].location.href=url;
}
</script>
<%-- DISPLAY THE LIST OF MAPPED CATEGORIES HERE--%>
<strong><dhv:label name="tickets.ticketCategoryMappedActionPlans.colon">Mapped Action Plans for Category:</dhv:label></strong>&nbsp;<%= toHtml(ticketCategoryDraft.getDescription()) %>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th nowrap>
      <strong><dhv:label name="quotes.productName">Name</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="documents.details.approved">Approved</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="admin.activeDate">Active Date</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="documents.details.archived">Archived</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="actionPlan.archivedDate">Archived Date</dhv:label></strong>
    </th>
  </tr>
<% if (mappedActionPlanList.size() > 0) {
    int rowid = 0;
    int count = 0;
    Iterator iterator = (Iterator) mappedActionPlanList.iterator();
    while (iterator.hasNext()) {
      TicketCategoryDraftPlanMap map = (TicketCategoryDraftPlanMap) iterator.next();
      ActionPlan mappedPlan = map.getPlan();
      count++;
      rowid = (rowid != 1?1:2);
%>
  <tr class="row<%= rowid + ((selectedElements.get(new Integer(mappedPlan.getId()))!= null)?"hl":"") %>">
    <td valign="top" width="100%">
      <%= toHtml(mappedPlan.getName()) %>
      <input type="hidden" name="hiddenelementid<%= count %>" value="<%= mappedPlan.getId() %>">
    </td>
    <td valign="top" align="center" nowrap>
      <%if(mappedPlan.getApproved() != null) { %>
        <dhv:label name="account.yes">Yes</dhv:label>
      <%} else {%>
        <dhv:label name="account.no">No</dhv:label>
      <%}%>
    </td>
    <td valign="top" align="center" nowrap>
      <zeroio:tz timestamp="<%= mappedPlan.getApproved() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
    </td>
    <td valign="top" align="center" nowrap>
      <%if(mappedPlan.getEnabled()) { %>
        <dhv:label name="account.no">No</dhv:label>
      <%} else {%>
        <dhv:label name="account.yes">Yes</dhv:label>
      <%}%>
    </td>
    <td valign="top" align="center" nowrap>
      <zeroio:tz timestamp="<%= mappedPlan.getArchiveDate() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
    </td>
  </tr>
<%}
  } else { %>
<tr>
    <td class="containerBody" valign="center" colspan="5">
      <dhv:label name="actionPlans.noMappedActionPlansFound.text">No mapped action plans found.</dhv:label>
    </td>
  </tr>
<%}%>
</table>
<br />
<%-- END DISPLAY --%>
<%-- Begin Form --%>
<% if (!"true".equalsIgnoreCase(request.getParameter("finalsubmit"))) { %>
<form name="elementListView" method="post" action="AdminCategories.do?command=PopupSelector&constantId=<%= request.getParameter("constantId") %>&categoryId=<%= request.getAttribute("categoryId") %>">
<input type="hidden" name="siteId"  value="<%= siteId %>" />
<input type="hidden" name="searchcodeCatCode" value="<%= actionPlanSelectorInfo.getSearchOptionValue("searchcodeCatCode") %>" />
<input type="hidden" name="searchcodeSubCat1" value="<%= actionPlanSelectorInfo.getSearchOptionValue("searchcodeSubCat1") %>" />
<input type="hidden" name="searchcodeSubCat2" value="<%= actionPlanSelectorInfo.getSearchOptionValue("searchcodeSubCat2") %>" />
<input type="hidden" name="searchcodeSubCat3" value="<%= actionPlanSelectorInfo.getSearchOptionValue("searchcodeSubCat3") %>" />
<table border="0" cellpadding="2" cellspacing="0" class="empty">
<tr>
  <td align="left" colspan="5">
    <strong><dhv:label name="categories.switchToSite">Switch to Site</dhv:label></strong>&nbsp;
    <select name="selectedSiteId" id="selectedSiteId" onChange="javascript:reopen('&siteId=<%= siteId %>&selectedSiteId='+this.options[this.options.selectedIndex].value);">
      <option value="-1" <%= selectedSiteId != null && selectedSiteId.equals("-1")? "selected":"" %>><dhv:label name="tickets.nonSite">Non-Site</dhv:label></option>
      <dhv:evaluate if="<%= siteId != null && !"".equals(siteId) && !"-1".equals(siteId) %>">
        <option value="<%= siteId %>"  <%= selectedSiteId != null && !selectedSiteId.equals("-1")? "selected":"" %>><%= SiteIdList.getSelectedValue(Integer.parseInt(siteId)) %></option>
      </dhv:evaluate>
    </select>
  </td>
  </tr><tr>
  <td align="center">
      <%= CategoryList.getHtmlSelect("catCode", Integer.parseInt(actionPlanSelectorInfo.getSearchOptionValue("searchcodeCatCode") != null && !"".equals(actionPlanSelectorInfo.getSearchOptionValue("searchcodeCatCode"))?actionPlanSelectorInfo.getSearchOptionValue("searchcodeCatCode"):"0")) %>
  </td>
<%-- Variably draw the rest of the editors --%>
  <td align="center">
      <%= SubList1.getHtmlSelect("subCat1", Integer.parseInt(actionPlanSelectorInfo.getSearchOptionValue("searchcodeSubCat1") != null && !"".equals(actionPlanSelectorInfo.getSearchOptionValue("searchcodeSubCat1"))?actionPlanSelectorInfo.getSearchOptionValue("searchcodeSubCat1"):"0")) %>
  </td>
  <td align="center">
      <%= SubList2.getHtmlSelect("subCat2", Integer.parseInt(actionPlanSelectorInfo.getSearchOptionValue("searchcodeSubCat2")!= null && !"".equals(actionPlanSelectorInfo.getSearchOptionValue("searchcodeSubCat2"))?actionPlanSelectorInfo.getSearchOptionValue("searchcodeSubCat2"):"0")) %>
  </td>
  <td align="center">
      <%= SubList3.getHtmlSelect("subCat3", Integer.parseInt(actionPlanSelectorInfo.getSearchOptionValue("searchcodeSubCat3")!= null && !"".equals(actionPlanSelectorInfo.getSearchOptionValue("searchcodeSubCat3"))?actionPlanSelectorInfo.getSearchOptionValue("searchcodeSubCat3"):"0")) %>
  </td>
  <td align="center">
    <input type="button" value="<dhv:label name="button.search">Search</dhv:label>" onClick="document.elementListView.finalsubmit.value='false';document.elementListView.submit();"/>
  </td>
</tr>
</table>
<%--<dhv:pagedListAlphabeticalLinks object="actionPlanSelectorInfo"/></center></dhv:include><br /> --%>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="actionPlanSelectorInfo" showHiddenParams="true" form="elementListView"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th align="center">
      &nbsp;
    </th>
    <th nowrap>
      <strong><a href="javascript:document.elementListView.finalsubmit.value='false';document.elementListView.column.value='ap.plan_name';document.elementListView.submit();"><dhv:label name="quotes.productName">Name</dhv:label></a></strong>
      <%= actionPlanSelectorInfo.getSortIcon("ap.plan_name") %>
    </th>
    <th nowrap>
      <strong><dhv:label name="documents.details.approved">Approved</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><a href="javascript:document.elementListView.finalsubmit.value='false';document.elementListView.column.value='ap.approved';document.elementListView.submit();"><dhv:label name="admin.activeDate">Active Date</dhv:label></a></strong>
      <%= actionPlanSelectorInfo.getSortIcon("ap.approved") %>
    </th>
    <th nowrap>
      <strong><a href="javascript:document.elementListView.finalsubmit.value='false';document.elementListView.column.value='ap.enabled';document.elementListView.submit();"><dhv:label name="documents.details.archived">Archived</dhv:label></a></strong>
      <%= actionPlanSelectorInfo.getSortIcon("ap.enabled") %>
    </th>
    <th nowrap>
      <strong><a href="javascript:document.elementListView.finalsubmit.value='false';document.elementListView.column.value='ap.archive_date';document.elementListView.submit();"><dhv:label name="actionPlan.archivedDate">Archived Date</dhv:label></a></strong>
      <%= actionPlanSelectorInfo.getSortIcon("ap.archive_date") %>
    </th>
  </tr>
<%if (baseList.size() > 0) {
    int rowid = 0;
    int count = 0;
    Iterator iterator = (Iterator) baseList.iterator();
    while (iterator.hasNext()) {
      ActionPlan plan = (ActionPlan) iterator.next();
      count++;
      rowid = (rowid != 1?1:2);
%>
  <tr class="row<%= rowid + ((selectedElements.get(new Integer(plan.getId()))!= null)?"hl":"") %>">
    <td valign="center" nowrap>
      <input type="checkbox" name="checkelement<%= count %>" value=<%= plan.getId() %><%= ((selectedElements.get(new Integer(plan.getId()))!= null)?" checked":"") %> onClick="highlight(this,'<%= User.getBrowserId() %>');">
    </td>
    <td valign="top" width="100%">
      <%= toHtml(plan.getName()) %>
      <input type="hidden" name="hiddenelementid<%= count %>" value="<%= plan.getId() %>">
    </td>
    <td valign="top" align="center" nowrap>
      <%if(plan.getApproved() != null) { %>
        <dhv:label name="account.yes">Yes</dhv:label>
      <%} else {%>
        <dhv:label name="account.no">No</dhv:label>
      <%}%>
    </td>
    <td valign="top" align="center" nowrap>
      <zeroio:tz timestamp="<%= plan.getApproved() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
    </td>
    <td valign="top" align="center" nowrap>
      <%if(plan.getEnabled()) { %>
        <dhv:label name="account.no">No</dhv:label>
      <%} else {%>
        <dhv:label name="account.yes">Yes</dhv:label>
      <%}%>
    </td>
    <td valign="top" align="center" nowrap>
      <zeroio:tz timestamp="<%= plan.getArchiveDate() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
    </td>
  </tr>
<%
    }
  } else { %>
<tr>
    <td class="containerBody" valign="center" colspan="6">
      <dhv:label name="actionPlan.noActionPlansFound.text">No action plans found.</dhv:label>
    </td>
  </tr>
<%}%>
</table>
<br />
<input type="hidden" name="column" value="">
<input type="hidden" name="finalsubmit" value="false">
<input type="hidden" name="rowcount" value="0">
<input type="hidden" name="displayFieldId" value="<%= DisplayFieldId %>">
<dhv:pagedListControl object="actionPlanSelectorInfo" tdClass="row1"/><br />
<input type='button' value="<dhv:label name="button.done">Done</dhv:label>" onClick="javascript:document.elementListView.finalsubmit.value='true';document.elementListView.submit();">
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();">
[<a href="javascript:SetChecked(1,'checkelement','elementListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>]
[<a href="javascript:SetChecked(0,'checkelement','elementListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>]
<br />
</form>
<%}%>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>


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
  - Version: $Id: accounts_action_plan_work_add.jsp
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.actionplans.base.*, org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="actionPlanList" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="actionPlanWork" class="org.aspcfs.modules.actionplans.base.ActionPlanWork" scope="request"/>
<jsp:useBean id="orgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js?v=20070827"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function checkForm(form) {
    formTest = true;
    message = "";
    if (form.managerId.value == -1) {
      message += label("check.actionplan.managerId", "- Plan Manager is a required field\r\n");
      formTest = false;
    }
    if (form.assignedTo.value == -1) {
      message += label("check.actionplan.assignedTo", "- Plan Assignee is a required field\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%= orgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="AccountActionPlans.do?command=View&orgId=<%= orgDetails.getOrgId() %>"><dhv:label name="sales.actionPlans">Action Plans</dhv:label></a> >
<dhv:label name="actionPlan.addActionPlan">Add Action Plan</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="actionplans" object="orgDetails" param='<%= "orgId=" + orgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<form name="addActionPlan" method="post" action="AccountActionPlans.do?command=Insert&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>" onSubmit="return checkForm(this);">
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="return checkForm(this.form)">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AccountActionPlans.do?command=View&orgId=<%= orgDetails.getOrgId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'"/>
  &nbsp;<br /><br />
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="actionPlan.addActionPlan">Add Action Plan</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="sales.actionPlan">Action Plan</dhv:label>
      </td>
      <td>
        <%= actionPlanList.getHtmlSelect("actionPlan", actionPlanWork.getActionPlanId()) %> <font color="red">*</font> <%= showAttribute(request, "actionPlanError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <dhv:label name="actionList.assignTo">Assign To</dhv:label>
      </td>
      <td>
        <table class="empty">
          <tr class="containerBody">
            <td>
              <div id="changeowner">
              <%  if (actionPlanWork.getAssignedTo() > 0) { %>
                <dhv:username id="<%= actionPlanWork.getAssignedTo() %>" lastFirst="true"/>
              <%  } else { %>
                <dhv:username id="<%= User.getUserId() %>" lastFirst="true"/>
              <%  } %>
              </div>
            </td>
            <td>
              <%  if (actionPlanWork.getAssignedTo() > 0) { %>
              <input type="hidden" name="assignedTo" id="assignedTo" value="<%= actionPlanWork.getAssignedTo() %>">
              <%  } else { %>
	              <input type="hidden" name="assignedTo" id="assignedTo" value="<%= User.getUserId() %>">
              <%  } %>
              &nbsp;[<a href="javascript:popContactsListSingle('assignedTo','changeowner', 'listView=employees&siteId=<%= orgDetails.getSiteId() %>&usersOnly=true&searchcodePermission=accounts-action-plans-view,myhomepage-action-plans-view&reset=true');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
              &nbsp; [<a href="javascript:changeDivContent('changeowner',label('none.selected','None Selected'));javascript:resetNumericFieldValue('assignedTo');"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
              <font color="red">*</font> <%= showAttribute(request, "assignedToError") %>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <dhv:label name="actionPlan.planManager">Plan Manager</dhv:label>
      </td>
      <td>
        <table class="empty">
          <tr class="containerBody">
            <td>
              <div id="changeplanmanager">
              <% if (actionPlanWork.getManagerId() > 0) { %>
                <dhv:username id="<%= actionPlanWork.getManagerId() %>" lastFirst="true"/>
              <% } else { %>
                 <dhv:username id="<%= User.getUserRecord().getId() %>" lastFirst="true"/>
              <% } %>
              </div>
            </td>
            <td>
              <input type="hidden" name="managerId" id="managerId" value="<%= ((actionPlanWork.getManagerId() > 0) ? actionPlanWork.getManagerId() : User.getUserRecord().getId()) %>">
              &nbsp;[<a href="javascript:popContactsListSingle('managerId','changeplanmanager', 'listView=employees&siteId=<%= orgDetails.getSiteId() %>&usersOnly=true&hierarchy=<%= User.getUserId() %>&searchcodePermission=accounts-action-plans-view,myhomepage-action-plans-view&reset=true');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
              &nbsp; [<a href="javascript:changeDivContent('changeplanmanager',label('none.selected','None Selected'));javascript:resetNumericFieldValue('managerId');"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
              <font color="red">*</font> <%= showAttribute(request, "managerIdError") %>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  &nbsp;<br />
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="return checkForm(this.form)">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AccountActionPlans.do?command=View&orgId=<%= orgDetails.getOrgId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'"/>
  <input type="hidden" name="orgId" value="<%= orgDetails.getOrgId() %>"/>
</form>
</dhv:container>

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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.utils.web.*,org.aspcfs.modules.actionplans.base.*,org.aspcfs.modules.admin.base.*" %>
<jsp:useBean id="stepActions" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="elements" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="constantId" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function checkForm(form) {
    return true;
  }

</script>
<form name="addActionPlan" action="ActionPlanEditor.do?command=SaveMappings" method="post" onSubmit="return checkForm(this);">
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
  <dhv:label name="actionPlan.configureMappings">Configure Mappings</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:formMessage showSpace="true"/>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr><th colspan="2"><strong>
    <dhv:label name="actionPlan.configureMappings">Configure Mappings</dhv:label>
  </strong></th>
<%
  int rowid = 0;
  if (stepActions.size() > 0) {%>
<%  Iterator iterator = (Iterator) stepActions.iterator();
    while (iterator.hasNext()) {
      LookupElement lookupElement = (LookupElement) iterator.next();
      rowid = (rowid != 1 ? 1 : 2);
      if (elements.get(new Integer(lookupElement.getId())) != null) {
%>
  <tr class="row<%= rowid %>hl">
      <td><input type="checkbox" name="element<%= lookupElement.getId() %>" value="<%= ((Integer)elements.get(new Integer(lookupElement.getId()))).intValue() %>" checked/></td>
      <td><%= toHtml(lookupElement.getDescription()) %></td>
  </tr>
    <%} else {%>
  <tr class="row<%= rowid %>">
      <td><input type="checkbox" name="element<%= lookupElement.getId() %>" value="-1"/></td>
      <td><%= toHtml(lookupElement.getDescription()) %></td>
  </tr>
    <%}
    }
  } else {%>
  <tr class="row<%= rowid %>">
      <td colspan="2"><dhv:label name="actionPlan.noElementsAvailable.text">No elements available for mapping</dhv:label></td>
  </tr>
<%}%>
</table>
<br />
<dhv:evaluate if="<%= stepActions.size() > 0 %>">
<input type="submit" value="<dhv:label name="button.save">Save</dhv:label>"/>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="window.location.href='ActionPlanEditor.do?command=ListPlans&moduleId=<%= permissionCategory.getId() %>&constantId=<%= constantId %>';"/>
</dhv:evaluate>
<br />
</form>
<%-- Set the default cursor location in the form --%>
<script type="text/javascript">
  document.addActionPlan.name.focus();
</script>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>


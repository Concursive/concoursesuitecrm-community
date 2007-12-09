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
<%@ page import="java.util.*,org.aspcfs.modules.actionplans.base.*,org.aspcfs.modules.admin.base.*" %>
<jsp:useBean id="actionPlan" class="org.aspcfs.modules.actionplans.base.ActionPlan" scope="request"/>
<jsp:useBean id="actionPhase" class="org.aspcfs.modules.actionplans.base.ActionPhase" scope="request"/>
<jsp:useBean id="actionPhaseSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="durationTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="nextPhaseId" class="java.lang.String" scope="request" />
<jsp:useBean id="previousPhaseId" class="java.lang.String" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function checkForm(form) {
    return true;
  }
</script>
<form name="addActionPhase" action="ActionPhaseEditor.do?command=SavePhase&&planId=<%= actionPlan.getId() %>&auto-populate=true" method="post" onSubmit="return checkForm(this);">
<input type="hidden" name="id" value="<%= actionPhase.getId() %>"/>
<input type="hidden" name="planId" value="<%= actionPlan.getId() %>"/>
<dhv:evaluate if='<%= nextPhaseId != null && !"".equals(nextPhaseId) %>'>
  <input type="hidden" name="nextPhaseId" value="<%= nextPhaseId %>"/>
</dhv:evaluate>
<dhv:evaluate if='<%= previousPhaseId != null && !"".equals(previousPhaseId) %>'>
  <input type="hidden" name="previousPhaseId" value="<%= previousPhaseId %>"/>
</dhv:evaluate>
<dhv:formMessage showSpace="false"/>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr><th colspan="2"><strong>
  <dhv:evaluate if="<%= actionPhase.getId() == -1 %>">
    <dhv:label name="actionPlan.addActionPhase">Add Action Phase</dhv:label>
  </dhv:evaluate><dhv:evaluate if="<%= actionPhase.getId() != -1 %>">
    <dhv:label name="actionPlan.modifyActionPhase">Modify Action Phase</dhv:label>
  </dhv:evaluate>
  </strong></th>
  <tr class="containerBody">
    <td class="formLabel" valign="top"><dhv:label name="quotes.productName">Name</dhv:label></td>
    <td><input type="text" name="name" value="<%= toHtmlValue(actionPhase.getName()) %>" size="58" maxlength="255"/><font color="red">*</font>
        <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  <tr>
    <td class="formLabel" valign="top"><dhv:label name="product.description">Description</dhv:label></td>
    <td><textarea name="description" id="description" cols="35" rows="4" ><%= toString(actionPhase.getDescription()) %></textarea>&nbsp;<%= showAttribute(request, "descriptionError") %>
    </td>
  </tr>
  <tr>
    <td class="formLabel" valign="top"><dhv:label name="sales.actionPlans.stepsRandomExecution.text">Steps can execute in random order</dhv:label></td>
    <td><input type="checkbox" name="random" value="true" <%= actionPhase.getRandom()?"checked":"" %> /></td>
  </tr>
  <tr>
    <td class="formLabel" valign="top"><dhv:label name="global.button.Enable">Enable</dhv:label></td>
    <td><input type="checkbox" name="enabled" value="true" <%= actionPhase.getEnabled()?"checked":"" %> /></td>
  </tr>
  <tr>
    <td class="formLabel" valign="top"><dhv:label name="accounts.actionplans.globalphase">Global Phase</dhv:label></td>
    <td><input type="checkbox" name="global" value="true" <%= actionPhase.getGlobal() ? "checked" : "" %> /></td>
  </tr>
</table>
<br />
<input type="hidden" name="parentId" value="<%= actionPhase.getParentId() %>" />
<input type="submit" value="<dhv:label name="button.save">Save</dhv:label>"/>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="self.close();"/>
<br />
</form>
<%-- Set the default cursor location in the form --%>
<script type="text/javascript">
  document.addActionPhase.name.focus();
</script>

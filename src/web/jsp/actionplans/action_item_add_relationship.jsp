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
  - Version: $Id: action_item_add_relationship.jsp
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.actionplans.base.*, org.aspcfs.modules.base.Constants"%>
<%@ page import="org.aspcfs.modules.admin.base.*, org.aspcfs.modules.accounts.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="actionItemWork" class="org.aspcfs.modules.actionplans.base.ActionItemWork" scope="request"/>
<jsp:useBean id="accounts" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="stepRelationOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="selectedList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="orgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="display" class="java.lang.String" scope="request"/>
<jsp:useBean id="status" class="java.lang.String" scope="request"/>
<jsp:useBean id="linkRelation" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="Javascript">
  function submitForm(form) {
    form.action = 'ActionPlans.do?command=AttachRelation';
    form.submit();
  }
  
  function setActionPlanDetails() {
    var itemId = '<%= actionItemWork.getId() %>';
    var displayId = "changerelation" + itemId;
    opener.document.getElementById('relationid').value="<%= linkRelation %>";
    opener.changeDivContent(displayId, "<%= toHtml(display) %>");
    if ('<%= linkRelation %>' != -1) {
      opener.attachRelation(itemId);
    } else {
      opener.resetAttachment(itemId);
    }
    self.close();
  }
</script>
<%
  Iterator p = selectedList.iterator();
  String previousSelection = "";
  while (p.hasNext()) {
    String id = (String) p.next();
    if (!"".equals(previousSelection)) {
      previousSelection = previousSelection + "|";
    }
    previousSelection = previousSelection + id;
  }
%>
<dhv:evaluate if="<%= status != null && "true".equals(status) %>">
  <body onLoad="javascript:setActionPlanDetails();">
</dhv:evaluate>
<dhv:evaluate if="<%= status != null && !"true".equals(status) %>">
<dhv:container name="accounts" selected="documents" object="orgDetails" param="<%= "orgId=" + orgDetails.getOrgId() %>" hideContainer="true">
<table class="note" cellspacing="0">
  <tr class="containerBody">
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><strong><%= toHtml(actionItemWork.getStepDescription()) %></strong> </td>
  </tr>
</table>
<form name="addStepRelation" method="post" action="ActionPlans.do?command=AddRelation">
<center><%= stepRelationOrgListInfo.getAlphabeticalPageLinks("setFieldSubmit","addStepRelation") %></center>
<input type="hidden" name="letter">
<table width="100%" border="0">
  <tr>
    <td align="right">
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="stepRelationOrgListInfo" showHiddenParams="true" enableJScript="true" form="addStepRelation" showRefresh="false"/>
    </td>
  </tr>
</table>  
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th align="center">
      &nbsp;
    </th>
    <th nowrap>
      <dhv:label name="reports.accounts.name">Name</dhv:label>
    </th>
    <th nowrap>
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label></a>
    </th>
  </tr>
<%
  Iterator i = accounts.iterator();
  if (i.hasNext()) {
    int rowid = 0;
    int count = 0;
    while (i.hasNext()) {
      count++;
      rowid = (rowid != 1?1:2);
      Organization thisOrg = (Organization) i.next();
%>      
      <tr class="row<%= rowid %>">
        <td valign="center" align="center" width="8">
          <input type="checkbox" name="item<%= count %>" id="item<%= count %>" value="<%= thisOrg.getOrgId() %>" <%= (selectedList.indexOf(String.valueOf(thisOrg.getOrgId())) != -1 ? " checked" : "") %> onClick="highlight(this,'<%= User.getBrowserId() %>');">
          <input type="hidden" name="hiddenItemId<%= count %>" value="<%= thisOrg.getOrgId() %>">
          <input type="hidden" name="hiddenItemValue<%= count %>" value="<%= thisOrg.getName() %>">
        </td>
        <td width="60%">
          <%= toHtml(thisOrg.getName()) %>
        </td>
        <td>
          <dhv:evaluate if="<%= thisOrg.getTypes().size() > 0 %>">
            <%= toHtml(thisOrg.getTypes().valuesAsString()) %>
          </dhv:evaluate>
          &nbsp;
        </td>
      </tr>
<%
    }
  } else {
%>  
<tr>
    <td class="containerBody" valign="center" colspan="6">
      <dhv:label name="accounts.search.notFound.period">No accounts found.</dhv:label>
    </td>
  </tr>
<%  
  }
%>
</table>
<br />
<input type="button" value="<dhv:label name="accounts.accounts_relationships_add.AddRelationship">Add Relationship</dhv:label>" onClick="javascript:submitForm(this.form);">
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();">
<input type="hidden" name="previousSelection" value="<%= previousSelection %>"/>
<input type="hidden" name="rowcount" value="0">
<input type="hidden" name="planWorkId" value="<%= actionItemWork.getPlanWork().getId() %>">
<input type="hidden" name="actionStepWork" value="<%= actionItemWork.getId() %>">
<input type="hidden" name="orgId" value="<%= orgDetails.getId() %>">
<a href="javascript:SetChecked(1,'item','addStepRelation','<%= User.getBrowserId() %>');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>
<a href="javascript:SetChecked(0,'item','addStepRelation','<%= User.getBrowserId() %>');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>
</form>
</dhv:container>
</dhv:evaluate>

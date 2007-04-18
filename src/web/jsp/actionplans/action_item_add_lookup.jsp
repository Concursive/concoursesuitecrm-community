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
  - Version: $Id: action_item_add_lookup.jsp
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.actionplans.base.*, org.aspcfs.modules.base.Constants"%>
<%@ page import="org.aspcfs.modules.admin.base.*" %>
<jsp:useBean id="actionItemWork" class="org.aspcfs.modules.actionplans.base.ActionItemWork" scope="request"/>
<jsp:useBean id="itemList" class="org.aspcfs.modules.actionplans.base.ActionStepLookupList" scope="request"/>
<jsp:useBean id="selectionList" class="org.aspcfs.modules.actionplans.base.ActionItemWorkSelectionList" scope="request"/>
<jsp:useBean id="orgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="display" class="java.lang.String" scope="request"/>
<jsp:useBean id="status" class="java.lang.String" scope="request"/>
<jsp:useBean id="linkSelection" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></script>
<script language="JavaScript" type="text/javascript">
  function doCheck() {
    var i = 0;
    var count = '<%= itemList.size() %>';
    var sel = false;
    for (i=1; i <= count; ++i) {
      var obj = 'checkelement' + i;
      if (document.getElementById(obj).checked) {
        sel = true;
      }
    }
    if (sel == false) {
      alert (label("required.oneiteminlist","You must have at least one item in this list."));
      return false;
    }
    return true;
  }
  
  function setActionPlanDetails() {
    var itemId = '<%= actionItemWork.getId() %>';
    var displayId = "changeselection" + itemId;
    opener.document.getElementById('selectionid').value="<%= linkSelection %>";
    opener.changeDivContent(displayId, "<%= toHtml(display) %>");
    opener.attachSelection(itemId);
    self.close();
  }
</script>
<dhv:evaluate if='<%= status != null && "true".equals(status) %>'>
  <body onLoad="javascript:setActionPlanDetails();">
</dhv:evaluate>
<dhv:evaluate if='<%= status != null && !"true".equals(status) %>'>
<dhv:container name="accounts" selected="documents" object="orgDetails" param='<%= "orgId=" + orgDetails.getOrgId() %>' hideContainer="true" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<table class="note" cellspacing="0">
  <tr class="containerBody">
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><strong><%= toHtml(actionItemWork.getStepDescription()) %></strong> <dhv:label name="actionplans.multipleSelections.text">allows multiple selections to be attached</dhv:label></td>
  </tr>
</table>
<form name="actionStep" action="ActionPlans.do?command=AttachSelection" method="post" onSubmit="return doCheck();">
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
<%
  int count = 0;
  int rowid = 0;
  Iterator items = itemList.iterator();
  while (items.hasNext()) {
    count++;
    rowid = (rowid != 1?1:2);
    ActionStepLookup thisItem = (ActionStepLookup) items.next();
%>
    <tr class="row<%= rowid + ((selectionList.hasSelection(thisItem.getId()))? "hl" : "") %>">
      <td align="center" width="8">
        <input type="checkbox" name="checkelement<%= count %>" id="checkelement<%= count %>" value=<%= thisItem.getId() %><%= ((selectionList.hasSelection(thisItem.getId())) ? " checked":"") %> onClick="highlight(this,'<%= User.getBrowserId() %>');">
      </td>
      <td valign="center">
        <%= toHtml(thisItem.getDescription()) %>
        <input type="hidden" name="hiddenelementid<%= count %>" value="<%= thisItem.getId() %>">
        <input type="hidden" name="elementvalue<%= count %>" value="<%= toHtml(thisItem.getDescription()) %>">
      </td>
    </tr>
<%
  }
%>
</table>
&nbsp;<br />
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" name="Save"/>
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="self.close();" />
<input type="hidden" name="orgId" value="<%= orgDetails.getOrgId() %>" />
<input type="hidden" name="itemWorkId" value="<%= actionItemWork.getId() %>" />
</form>
</dhv:container>
</dhv:evaluate>

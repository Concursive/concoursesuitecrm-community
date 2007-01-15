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
<%@ page import="java.util.*,org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="OptionList" class="org.aspcfs.modules.products.base.ProductOptionList" scope="request"/>
<jsp:useBean id="OptionListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SelectedOptions" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="FinalOptions" class="org.aspcfs.modules.products.base.ProductOptionList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popProductCatalogs.js"></script>
<%
  Iterator p = SelectedOptions.iterator();
  String previousSelection = "";
  while (p.hasNext()) {
    String catId = (String) p.next();
    if (!"".equals(previousSelection)) {
      previousSelection = previousSelection + "|";
    }
    previousSelection = previousSelection + catId;
  }
%>
<%
  if (!"true".equals(request.getParameter("finalsubmit"))) {
%>
<%-- Navigating the contact list, not the final submit --%>
<form name="optionListView" method="post" action="ProductOptionSelector.do?command=ListProductOptions">
  <input type="hidden" name="letter">
  <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="OptionListInfo" showHiddenParams="true" enableJScript="true" form="optionListView"/>
  <input type="button" value="Create New Option" onclick="javascript:window.location.href='ProductOptions.do?command=Add&action=ProductCatalogOptions.do?command=List&popup=true&moduleId=<%= request.getParameter("moduleId") %>&catalogId=<%= request.getParameter("catalogId") %>';"/><br /><br />
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th align="center" width="8">
        &nbsp;
      </th>
      <th>
        <strong><dhv:label name="contacts.name">Name</dhv:label></strong>
      </th>
    </tr>
<%
	Iterator j = OptionList.iterator();
	if (j.hasNext()) {
		int rowid = 0;
		int count = 0;
    while (j.hasNext()) {
			count++;
      rowid = (rowid != 1 ? 1 : 2);
      ProductOption thisOption = (ProductOption) j.next();
      String optionId = String.valueOf(thisOption.getId());
%>
    <tr class="row<%= rowid+(SelectedOptions.indexOf(optionId) != -1 ? "hl" : "") %>">
      <td align="center" nowrap width="8">
<% 
  if ("list".equals(request.getParameter("listType"))) { 
%>
     <input type="checkbox" name="option<%= count %>" value="<%= thisOption.getId() %>" <%= (SelectedOptions.indexOf(optionId) != -1 ? " checked" : "") %> onClick="highlight(this,'<%= User.getBrowserId() %>');">
<%} else {%>
     <a href="javascript:document.optionListView.finalsubmit.value = 'true';setFieldSubmit('rowcount','<%= count %>','optionListView');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>
<%}%>
        <input type="hidden" name="hiddenOptionId<%= count %>" value="<%= thisOption.getId() %>">
      </td>
      <td nowrap>
          <%= toHtml(thisOption.getName()) %>
      </td>
    </tr>
<%
    }
  } else {
%>
    <tr>
      <td class="containerBody" colspan="4">
        <dhv:label name="quotes.noOptionsMatchedQuery">No options matched query.</dhv:label>
      </td>
    </tr>
<%}%>
    <input type="hidden" name="optionId" value="<%= request.getParameter("optionId") %>">
    <input type="hidden" name="catalogId" value="<%= request.getParameter("catalogId") %>">
    <input type="hidden" name="moduleId" value="<%= request.getParameter("moduleId") %>">
    <input type="hidden" name="finalsubmit" value="false">
    <input type="hidden" name="rowcount" value="0">
    <input type="hidden" name="displayFieldId" value="<%= toHtmlValue(request.getParameter("displayFieldId")) %>">
    <input type="hidden" name="hiddenFieldId" value="<%= toHtmlValue(request.getParameter("hiddenFieldId")) %>">
    <input type="hidden" name="listType" value="<%= toHtmlValue(request.getParameter("listType")) %>">
    <input type="hidden" name="previousSelection" value="<%= previousSelection %>"/>
  </table>
<% if("list".equals(request.getParameter("listType"))){ %>
  <input type="button" value="<dhv:label name="button.done">Done</dhv:label>" onClick="javascript:setFieldSubmit('finalsubmit','true','optionListView');">
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
  <a href="javascript:SetChecked(1,'option','optionListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>
  <a href="javascript:SetChecked(0,'option','optionListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>
<%}else{%>
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
<%}%>
</form>
<%} else { %>
<%-- The final submit --%>
<%
  if (!"true".equals(request.getParameter("setParentList"))) {
%>
  <%-- Parent need not be set. Just call the requested action and close the window --%>
  <body onLoad="javascript:opener.window.location.href='ProductCatalogOptions.do?command=AddOptionMappings&moduleId=<%= request.getParameter("moduleId") %>&catalogId=<%= request.getParameter("catalogId") %>&finalElements=' + finalElements;window.close(); ">
  <script>finalElements = new Array();</script>
  <%
  Iterator m = FinalOptions.iterator();
  int cnt = -1;
  while (m.hasNext()) {
    cnt++;
    ProductOption thisOption = (ProductOption) m.next();
%>
  <script>
    finalElements[<%= cnt %>] = "<%= thisOption.getId() %>";
  </script>
<%	
  }
%>
  </body>
<%  
  } else {
%>
  <body onLoad="javascript:setParentList(optionIds, optionNames, '<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>','<%= User.getBrowserId() %>');window.close()">
  <script>optionIds = new Array();optionNames = new Array();</script>
<%
  Iterator i = FinalOptions.iterator();
  int count = -1;
  while (i.hasNext()) {
    count++;
    ProductOption thisOption = (ProductOption) i.next();
%>
  <script>
    optionIds[<%= count %>] = "<%= thisOption.getId() %>";
    optionNames[<%= count %>] = "<%= toJavaScript(thisOption.getName()) %>";
  </script>
<%	
  }
%>
  </body>
<%
  }
}
%>

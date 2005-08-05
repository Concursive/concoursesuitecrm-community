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
<%@ page import="java.util.*,org.aspcfs.modules.products.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="productList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="parentCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="ProductCatalogSelectorInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="selectedElements" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="DisplayFieldId" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="javascript">
  function submitForm(categoryId) {
    document.elementListView.action = 'ProductsCatalog.do?command=PopupSelector&categoryId=' + categoryId;
    document.elementListView.submit();
  }
</script>
<%@ include file="../initPage.jsp" %>
<%
  Iterator p = selectedElements.keySet().iterator();
  String previousSelection = "";
  String previousSelectionDisplay = "";
  while (p.hasNext()) {
    int catId = ((Integer) p.next()).intValue();
    String value = (String) selectedElements.get(new Integer(catId));
    if (!"".equals(previousSelection)) {
      previousSelection = previousSelection + "|";
    }
    if (!"".equals(previousSelectionDisplay)) {
      previousSelectionDisplay = previousSelectionDisplay + "|";
    }
    previousSelection = previousSelection + catId;
    previousSelectionDisplay = previousSelectionDisplay + value;
  }
%>
<% if (!"true".equalsIgnoreCase(request.getParameter("finalsubmit"))) { %>
<form name="elementListView" method="post" action="ProductsCatalog.do?command=PopupSelector">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="trails">
    <td>
      <% String link = ""; %>
      <dhv:productCategoryHierarchy link="<%= link %>" displayJS="true" />
    </td>
  </tr>
</table>
<%
  int rowid = 0;
  int count = 0;
%>
<dhv:evaluate if="<%= categoryList.size() > 0 %>">
&nbsp;<br />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="100%"><strong><dhv:label name="product.name">Name</dhv:label></strong></th>
  </tr>
<%
  Iterator i = categoryList.iterator();
  while (i.hasNext()) {
    ProductCategory thisCategory = (ProductCategory) i.next();
    rowid = (rowid != 1 ? 1 : 2);
    count++;
%>
  <tr>
    <td class="row<%= rowid %>" width="100%">
      <img src="images/stock_folder-23.gif" border="0" align="absmiddle"/>
      <a href="javascript:submitForm('<%= thisCategory.getId() %>');"><%= toHtml(thisCategory.getName()) %></a>
    </td>
  </tr>
<%
  }
%>
</dhv:evaluate>
<%-- Show the list of products to choose from --%>
<input type="hidden" name="letter">
<table width="100%" border="0">
  <tr>
      <td align="right">
        <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ProductCatalogSelectorInfo" showHiddenParams="true" enableJScript="true" form="elementListView"/>
      </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="8">
      &nbsp;
    </th>
    <th width="50%">
      <dhv:label name="product.name">Name</dhv:label>
    </th>
    <th width="50%">
      <dhv:label name="products.SKU">SKU</dhv:label>
    </th>
  </tr>
<%
  Iterator j = productList.iterator();
  if ( j.hasNext() ) {
    rowid = 0;
    count = 0;
    while (j.hasNext()) {
      count++;
      rowid = (rowid != 1?1:2);
      ProductCatalog thisElt = (ProductCatalog)j.next();
      if ( thisElt.getActive() || (!thisElt.getActive() && (selectedElements.get(new Integer(thisElt.getId()))!= null)) ) {
%>
  <tr class="row<%= rowid+((selectedElements.get(new Integer(thisElt.getId()))!= null)?"hl":"") %>">
    <td align="center">
<% 
     if ("list".equals(request.getParameter("listType"))) { 
%>
      <input type="checkbox" name="checkelement<%= count %>" value=<%= thisElt.getId() %><%= ((selectedElements.get(new Integer(thisElt.getId()))!= null)?" checked":"") %> onClick="highlight(this,'<%= User.getBrowserId() %>');">
<%} else {%>
     <a href="javascript:document.elementListView.finalsubmit.value = 'true';setFieldSubmit('rowcount','<%= count %>','elementListView');">Select</a>
<%}%>     
    </td>
    <td valign="center">
      <%= toHtml(thisElt.getName()) %>
      <input type="hidden" name="hiddenelementid<%= count %>" value="<%= thisElt.getId() %>">
      <input type="hidden" name="elementvalue<%= count %>" value="<%= toHtml(thisElt.getName()) %>">
    </td>
    <td>
      <%= toHtml(thisElt.getSku()) %>
    </td>
  </tr>
<%
      } else {
        count--;
      }
    }
    
    /*Removing the elements that are in view*/
    Iterator itr = productList.iterator();
    while (itr.hasNext()){
      ProductCatalog thisElt = (ProductCatalog)itr.next();
      if (selectedElements.containsKey(new Integer(thisElt.getId()))){
        selectedElements.remove(new Integer(thisElt.getId()));
      }
    }
    /*Creating hidden objects for the items not in this page*/
    Iterator itr1 = selectedElements.keySet().iterator();
    if (itr1.hasNext()){
      while (itr1.hasNext()){
        count++;
        int id = ((Integer)itr1.next()).intValue();%>
      <input type="hidden" name="checkelement<%= count %>" value="<%= id %>" />
      <input type="hidden" name="hiddenelementid<%= count %>" value="<%= id %>" />
      <input type="hidden" name="elementvalue<%= count %>" value="<%= toHtml((String)selectedElements.get(new Integer(id))) %>" />
<%    
      }
    }
  } else {
%>
      <tr class="containerBody">
        <td colspan="3">
          <dhv:label name="product.noProductsFound">Products not found.</dhv:label>
        </td>
      </tr>
<%
  }
%>
</table>
&nbsp;<br/ >
<input type="hidden" name="finalsubmit" value="false">
<input type="hidden" name="rowcount" value="0">
<input type="hidden" name="contractId" value="<%= toHtmlValue(request.getParameter("contractId")) %>">
<input type="hidden" name="displayFieldId" value="<%= DisplayFieldId %>">
<input type="hidden" name="previousSelection" value="<%= previousSelection %>"/>
<input type="hidden" name="previousSelectionDisplay" value="<%= previousSelectionDisplay %>"/>
<input type="hidden" name="hiddenFieldId" value="<%= toHtmlValue(request.getParameter("hiddenFieldId")) %>">
<input type="hidden" name="listType" value="<%= toHtmlValue(request.getParameter("listType")) %>">
<% if("list".equals(request.getParameter("listType"))){ %>
<input type="button" value="<dhv:label name="button.done">Done</dhv:label>" onClick="javascript:document.elementListView.finalsubmit.value='true';document.elementListView.submit();">
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
[<a href="javascript:SetChecked(1,'checkelement','elementListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>]
[<a href="javascript:SetChecked(0,'checkelement','elementListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>]
<%}else{%>
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
<%}%>
<br>
&nbsp;<br>
</form>
<%
  } else {
%>
<%-- Save the selected items to the parent form, then close the window --%>
<body OnLoad="javascript:setParentList(selectedIds, selectedValues,'<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>','<%= User.getBrowserId() %>');window.close();">
  <script>selectedValues = new Array();selectedIds = new Array();</script>
<%
    Set s = selectedElements.keySet();
    Iterator i = s.iterator();
    int count = -1;
    while (i.hasNext()) {
      count++;
      Object id = i.next();
      Object st = selectedElements.get(id);
      String value = st.toString();
%>
  <script>
    selectedValues[<%= count %>] = '<%= value %>';
    selectedIds[<%= count %>] = '<%= id %>';
  </script>
<%
    }
%>
</body>
<%
  }
%>

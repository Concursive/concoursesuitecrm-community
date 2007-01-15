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
  - Version: $Id: $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.website.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="parentCategory" class="org.aspcfs.modules.website.base.PortfolioCategory" scope="request"/>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.website.base.PortfolioCategoryList" scope="request"/>
<jsp:useBean id="itemList" class="org.aspcfs.modules.website.base.PortfolioItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<script type="text/javascript">
var thisCategoryId = -1;
var thisParentId = -1;
var thisItemId = -1;
var menu_init = false;
</script>
<%@ include file="portfolio_list_item_menu.jsp" %>
<%@ include file="portfolio_list_category_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');

  function clearForm(form) {
  <%for(int i=0;i<categoryList.size();i++) {
      PortfolioCategory category = (PortfolioCategory) categoryList.get(i);
  %>
    document.getElementById('category<%= category.getId() %>').value = '';
  <%}%>
  <%for(int i=0;i<itemList.size();i++) {
      PortfolioItem item = (PortfolioItem) itemList.get(i);
  %>
    document.getElementById('item<%= item.getId() %>').value = '';
  <%}%>
  }

  function showValues(form) {
    <%HashMap categoryPositionMap = categoryList.getCategoryPositionsAsHashMap();
      HashMap itemPositionMap = itemList.getItemPositionsAsHashMap();
    %>
    <%for(int i=0;i<categoryList.size();i++) {
        PortfolioCategory category = (PortfolioCategory) categoryList.get(i);
    %>
      var categoryPosition = document.getElementById('category<%= category.getId() %>');
      if (checkPositionValue(categoryPosition.value)) {
        categoryPosition.value= '<%= (String) categoryPositionMap.get(String.valueOf(category.getId())) %>';
      }
    <%}%>
    <%for(int i=0;i<itemList.size();i++) {
        PortfolioItem item = (PortfolioItem) itemList.get(i);
    %>
      var itemPosition = document.getElementById('item<%= item.getId() %>');
      if (checkPositionValue(itemPosition.value)) {
        itemPosition.value = '<%= (String) itemPositionMap.get(String.valueOf(item.getId())) %>';
      }
    <%}%>
  }
  
  function checkForm(form) {
    formTest = true;
    message = "";
    <%for(int i=0;i<categoryList.size();i++) {
        PortfolioCategory category = (PortfolioCategory) categoryList.get(i);
    %>
      var categoryPosition = document.getElementById('category<%= category.getId() %>').value;
      if (!checkPositionValue(categoryPosition)) {
        formTest == false;
        message = label("","- Please make sure to leave the category position entries blank or enter a whole number\r\n");
      }
    <%}%>
    <%for(int i=0;i<itemList.size();i++) {
        PortfolioItem item = (PortfolioItem) itemList.get(i);
    %>
      var itemPosition = document.getElementById('item<%= item.getId() %>').value;
      if (!checkPositionValue(itemPosition)) {
        formTest == false;
        message = label("","- Please make sure to leave the example position entries blank or enter a whole number\r\n");
      }
    <%}%>
    if (formTest == false) {
      alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
  
  function checkPositionValue(position) {
    var result = true;
    if (!checkNullString(position)) {
      if (!checkNaturalNumber(position)) {
        result = false;
      }
      if (checkNaturalNumber(position) == "0") {
        result = false;
      }
    }
    return result;
  }
  
  function reopen(){
    window.location.href='PortfolioEditor.do?command=List&categoryId=<%= categoryList.getParentId() %>';
  }
</script>
<form name="categoryList" action="PortfolioEditor.do?command=Reorder&categoryId=<%= categoryList.getParentId() %>" method="POST" onSubmit="javascript:return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Website.do"><dhv:label name="website.website">Website</dhv:label></a> >
      <dhv:label name="website.portfolio.setupExamplesOfPastWork">Setup Examples of Past Work</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<%-- Category Trails --%>
<table border="0" cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td>
      <dhv:productCategoryHierarchy link="PortfolioEditor.do?command=List" />
    </td>
  </tr>
</table>
<br />
<%-- End Category Trails --%>
<dhv:evaluate if="<%= categoryList.getParentId() == -1 %>">
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="website.portfolio.list.note.text">Your website visitors will become more knowledgeable about your products
    and services by viewing a showcase or gallery of your work. Organize your work examples by category to group common examples.
    You can have as many categories as you want, and you can have categories within categories.</dhv:label></td>
  </tr>
</table>
</dhv:evaluate>
<%-- Begin the container contents --%>
<dhv:evaluate if="<%= parentCategory != null && parentCategory.getId() > -1 %>">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2"><strong><dhv:label name="">Category Details</dhv:label></strong></th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">Name</td>
    <td width="100%"><%= toHtml(parentCategory.getName()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">Description</td>
    <td width="100%"><%= toHtml(parentCategory.getDescription()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">Enabled</td>
    <td width="100%">
      <%if (parentCategory.getEnabled()){%>
        <dhv:label name="">Yes</dhv:label>
      <%} else {%>
        <dhv:label name="">No</dhv:label>
      <%}%>
    </td>
  </tr>
</table><br />
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" width="100%" class="empty"><tr>
<td><dhv:permission name="website-portfolio-add"><a href="PortfolioEditor.do?command=AddCategory&parentId=<%= categoryList.getParentId() %>&positionId=<%= categoryList.getLastCategoryId() %>"><dhv:label name="website.portfolio.addCategory">Add Category</dhv:label></a></dhv:permission></td>
</tr></table>
<%-- TODO:: modify the code to display the break properly based on the permissions --%>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th nowrap><strong><dhv:label name="website.portfolio.position">Position</dhv:label></strong></th>
    <th width="8">
      &nbsp;
    </th>
    <th width="100%"><strong><dhv:label name="quotes.productName">Name</dhv:label></strong></th>
    <th nowrap><strong><dhv:label name="website.portfolio.categoryEntries">Category Entries</dhv:label></strong></th>
    <th nowrap><strong><dhv:label name="product.enabled">Enabled</dhv:label></strong></th>
  </tr>
<%
  int rowid = 0;
  int i = 0;
  if (categoryList != null) {
    Iterator j = categoryList.iterator();
    if ( j.hasNext() ) {
      for (int counter = 1;j.hasNext();counter++) {
      PortfolioCategory thisCategory = (PortfolioCategory)j.next();
      i++;
      rowid = (rowid != 1 ? 1 : 2);
%>
  <tr class="row<%= rowid %>">
    <td valign="center" nowrap><input type="text" name="category<%= thisCategory.getId() %>" id="category<%= thisCategory.getId() %>" size="3" maxlength="6" align="right" value="<%= counter %>"/></td>
    <td valign="center" nowrap>
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenuCategory('select<%= i %>','menuCategory','<%= thisCategory.getId() %>','<%= thisCategory.getParentId() %>');"
       onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuCategory');">
       <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td valign="top" width="100%">
      <a href="PortfolioEditor.do?command=List&categoryId=<%=thisCategory.getId()%>"><%= thisCategory.getName() %></a>
    </td>
    <td valign="top" nowrap>
      <dhv:evaluate if="<%= thisCategory.getChildCategories() != null && thisCategory.getChildCategories().size() > 0 %>">
        <dhv:evaluate if="<%= thisCategory.getChildCategories().size() > 1 %>">
          <dhv:label name="website.portfolio.numberOfCategories" param='<%= "categories="+thisCategory.getChildCategories().size() %>'><%= thisCategory.getChildCategories().size() %> Categories</dhv:label>
        </dhv:evaluate>
        <dhv:evaluate if="<%= thisCategory.getChildCategories().size() == 1 %>">
          <dhv:label name="website.portfolio.oneCategory">1 Category</dhv:label>
        </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisCategory.getItems() != null && thisCategory.getItems().size() > 0 %>">
        <dhv:evaluate if="<%= thisCategory.getChildCategories() != null && thisCategory.getChildCategories().size() > 0 %>"><br /></dhv:evaluate>
        <dhv:evaluate if="<%= thisCategory.getItems().size() > 1 %>">
          <dhv:label name="website.portfolio.numberOfItems" param='<%= "items="+thisCategory.getItems().size() %>'><%= thisCategory.getItems().size() %> Items</dhv:label>
        </dhv:evaluate>
        <dhv:evaluate if="<%= thisCategory.getItems().size() == 1 %>">
          <dhv:label name="website.portfolio.oneItem">1 Item</dhv:label>
        </dhv:evaluate>
      </dhv:evaluate>&nbsp;
    </td>
    <td valign="top" nowrap>
      <% if (thisCategory.getEnabled()) { %>
        <dhv:label name="">Yes</dhv:label>
      <%} else {%>
        <dhv:label name="">No</dhv:label>
      <%}%>
    </td>
  </tr>
<%    }
    }
  } 
  if (categoryList == null || categoryList.size() == 0) { %>
  <tr class="row<%= rowid %>">
    <td valign="top" colspan="5"><dhv:label name="">No categories exist in the current category</dhv:label></td>
  </tr>
<%}%>
</table>
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="empty"><tr>
<td><dhv:permission name="website-portfolio-add"><a href="PortfolioItemEditor.do?command=AddItem&categoryId=<%= itemList.getCategoryId() %>&positionId=<%= itemList.getLastItemId() %>"><dhv:label name="website.portfolio.addItem">Add Item</dhv:label></a><br /></dhv:permission></td>
</tr></table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th nowrap><strong><dhv:label name="website.portfolio.position">Position</dhv:label></strong></th>
    <th width="8">
      &nbsp;
    </th>
    <th width="100%"><strong><dhv:label name="quotes.itemName">Item Name</dhv:label></strong></th>
    <th nowrap><strong><dhv:label name="product.enabled">Enabled</dhv:label></strong></th>
  </tr>
<%
  rowid = 0;
  if (itemList != null) {
    Iterator j = itemList.iterator();
    if ( j.hasNext() ) {
      for (int counter = 1;j.hasNext();counter++) {
      PortfolioItem thisItem = (PortfolioItem) j.next();
      i++;
      rowid = (rowid != 1 ? 1 : 2);
%>
  <tr class="row<%= rowid %>">
    <td valign="center" nowrap><input type="text" name="item<%= thisItem.getId() %>" id="item<%= thisItem.getId() %>" size="3" maxlength="6" align="right" value="<%= counter %>"/></td>
    <td valign="center" nowrap>
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenuItem('select<%= i %>','menuItem','<%= thisItem.getId() %>','<%= thisItem.getCategoryId() %>');"
       onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuItem');">
       <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td valign="top" width="100%">
      <a href="PortfolioItemEditor.do?command=ItemDetails&categoryId=<%=thisItem.getCategoryId()%>&itemId=<%=thisItem.getId()%>"><%= thisItem.getName() %></a>
    </td>
    <td valign="top" nowrap>
      <% if (thisItem.getEnabled()) { %>
        <dhv:label name="">Yes</dhv:label>
      <%} else {%>
        <dhv:label name="">No</dhv:label>
      <%}%>
    </td>
  </tr>
<%    }
    }
  }
  if (itemList == null || itemList.size() == 0) { %>
  <tr class="row<%= rowid %>">
    <td valign="top" colspan="4"><dhv:label name="">No items exist in the current category</dhv:label></td>
  </tr>
<%}%>
</table>
<br />
<%if ((categoryList != null && categoryList.size() > 1) || (itemList != null && itemList.size() > 1)) { %>
<input type="submit" value='<dhv:label name="button.updateOrder">Update Order</dhv:label>'/>
<input type="button" value='<dhv:label name="">Clear Positions</dhv:label>' onClick="javascript:clearForm(this.form);"/>
<input type="button" value='<dhv:label name="">Reset Positions</dhv:label>' onClick="javascript:showValues(this.form);"/>
<%}%>
</form>

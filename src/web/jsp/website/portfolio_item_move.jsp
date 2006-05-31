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
  - Version: $Id:  $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.website.base.*" %>
<jsp:useBean id="item" class="org.aspcfs.modules.website.base.PortfolioItem" scope="request"/>
<jsp:useBean id="categoryHierarchy" class="org.aspcfs.modules.website.base.PortfolioCategoryHierarchy" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table cellpadding="0" cellspacing="0" width="100%" border="0">
  <tr>
    <td>
      <dhv:label name="">Select the destination category for the item</dhv:label>:<br>
      <%= toHtml(item.getName()) %>
    </td>
  </tr>
</table>
&nbsp;<br />
<table cellpadding="0" cellspacing="0" width="100%" border="1" rules="cols">
  <tr class="section">
    <td valign="top" width="100%">
      <img alt="" src="images/tree7o.gif" border="0" align="absmiddle" height="16" width="19"/>
      <img alt="" src="images/icons/stock_open-16-19.gif" border="0" align="absmiddle" height="16" width="19"/>
      <a href="PortfolioItemEditor.do?command=UpdateMove&itemId=<%= item.getId() %>&popup=true&categoryId=-1"><dhv:label name="product.topCategories">Top Categories</dhv:label></a>
      <dhv:evaluate if="<%= (item.getCategoryId() == -1) %>">
      <dhv:label name="website.portfolio.currentCategory.parenthesis">(current category)</dhv:label>
      </dhv:evaluate>
    </td>
  </tr>
<%
  int rowid = 0;
  Iterator i = categoryHierarchy.getHierarchy().iterator();
  while (i.hasNext()) {
    rowid = (rowid != 1?1:2);
    PortfolioCategory thisCategory = (PortfolioCategory) i.next();
%>
  <tr class="row<%= rowid %>">
    <td valign="top">
    <% for(int j=1;j<thisCategory.getLevel();j++){ %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%}%>
      <img border="0" src="images/treespace.gif" align="absmiddle" height="16" width="19">
      <img alt="" src="images/tree7o.gif" border="0" align="absmiddle" height="16" width="19"/>
      <img border="0" src="images/icons/stock_open-16-19.gif" align="absmiddle">
      <a href="PortfolioItemEditor.do?command=UpdateMove&itemId=<%= item.getId() %>&categoryId=<%= thisCategory.getId() %>&popup=true"><%= toHtml(thisCategory.getName()) %></a>
      <dhv:evaluate if="<%= item.getCategoryId() == thisCategory.getId() %>">
      <dhv:label name="website.portfolio.currentCategory.parenthesis">(current category)</dhv:label>
      </dhv:evaluate>
    </td>
  </tr>
<%
  }
%>
</table>
&nbsp;<br />
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()" />

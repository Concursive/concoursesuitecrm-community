<%--
- Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/portlet.tld" prefix="portlet" %>
<%@ page import="org.aspcfs.utils.StringUtils"%>
<jsp:useBean id="introduction" class="java.lang.String" scope="request"/>
<jsp:useBean id="productCatalog" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="parentCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="SHOW_SKU" class="java.lang.String" scope="request"/>
<jsp:useBean id="SKU_TEXT" class="java.lang.String" scope="request"/>
<jsp:useBean id="SHOW_PRICE" class="java.lang.String" scope="request"/>
<jsp:useBean id="PRICE_TEXT" class="java.lang.String" scope="request"/>
<jsp:useBean id="SHOW_PRICE_SAVINGS" class="java.lang.String" scope="request"/>
<jsp:useBean id="ORIGINAL_PRICE_TEXT" class="java.lang.String" scope="request"/>
<jsp:useBean id="PRICE_SAVINGS_TEXT" class="java.lang.String" scope="request"/>
<%@ include file="../../initPage.jsp" %>
<%@ include file="../../initPopupMenu.jsp" %>
<portlet:defineObjects/>
<dhv:evaluate if="<%= StringUtils.hasText(introduction) %>">
  <table cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr>
      <td>
        <%= StringUtils.toHtml(introduction) %>
      </td>
    </tr>
  </table>
  <br />
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <%-- Product Header --%>
  <tr>
		<th colspan="2">
			<dhv:pagedListStatus title="<%= StringUtils.toHtml(productCatalog.getName()) %>" object="productCatalogListInfo"/>
		</th>
  </tr>
  <tr>
    <td colspan="2" style="text-align:left;" nowrap>
			<portlet:renderURL portletMode="view" var="url">
				<portlet:param name="viewType" value="summary"/>
				<portlet:param name="categoryId" value="<%= String.valueOf(parentCategory.getId()) %>"/>
				<portlet:param name="page" value="<%= String.valueOf((String) request.getAttribute("page")) %>"/>
			</portlet:renderURL>
		&nbsp;[<a href="<%= pageContext.getAttribute("url") %>">Back to Items</a>]
		</td>
	</tr>
  <%-- Product Details --%>
  <tr>
		<td valign="top" nowrap>
      <dhv:evaluate if="<%= productCatalog.getLargestImageId() > -1 %>">
        <dhv:fileItemImage id="<%= productCatalog.getLargestImageId() %>" path="products" thumbnail="false" name="<%=  productCatalog.getName() %>" />
      </dhv:evaluate>
		</td>
    <td valign="top" width="100%">
      <dhv:evaluate if="<%= "true".equals(SHOW_SKU) %>">
        <dhv:evaluate if="<%= StringUtils.hasText(productCatalog.getSku()) %>">
          <strong><%= toHtml(SKU_TEXT) %><%= StringUtils.toHtml(productCatalog.getSku()) %></strong><br />
          <br />
        </dhv:evaluate>
      </dhv:evaluate>
      <strong><%= StringUtils.toHtml(productCatalog.getName()) %></strong><br />
      <br />
      <%= StringUtils.toHtml(productCatalog.getLongDescription()) %><br />
      <br />
      <%-- show price: must be greater than 0 --%>
      <dhv:evaluate if="<%= productCatalog.getActivePrice() != null && productCatalog.getActivePrice().getPriceAmount() > 0 %>">
        <dhv:evaluate if="<%= "true".equals(SHOW_PRICE_SAVINGS) %>">
          <dhv:evaluate if="<%= productCatalog.getActivePrice().getMsrpAmount() > productCatalog.getActivePrice().getPriceAmount() && productCatalog.getActivePrice().getMsrpAmount() > 0 %>">
            <%= toHtml(ORIGINAL_PRICE_TEXT) %> <zeroio:currency value="<%= productCatalog.getActivePrice().getMsrpAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= applicationPrefs.get("SYSTEM.LANGUAGE") %>" default="&nbsp;"/><br />
            <%= toHtml(PRICE_SAVINGS_TEXT) %> <zeroio:currency value="<%= (productCatalog.getActivePrice().getPriceAmount() - productCatalog.getActivePrice().getMsrpAmount()) %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= applicationPrefs.get("SYSTEM.LANGUAGE") %>" default="&nbsp;" allowNegative="true"/><br />
            <br />
          </dhv:evaluate>
          <strong><%= toHtml(PRICE_TEXT) %><zeroio:currency value="<%= productCatalog.getActivePrice().getPriceAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= applicationPrefs.get("SYSTEM.LANGUAGE") %>" default="&nbsp;"/></strong><br />
          <br />
        </dhv:evaluate>
      </dhv:evaluate>
      <%-- show options --%>

    </td>
  </tr>
</table>

<%@ page import="org.aspcfs.utils.StringUtils"%>
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
<%@ page import="java.io.*, java.util.*, org.aspcfs.modules.products.base.*" %>
<%@ page import="org.aspcfs.utils.web.PagedListInfo"%>
<jsp:useBean id="introduction" class="java.lang.String" scope="request"/>
<jsp:useBean id="parentCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="productCategoryList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="productCatalogList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="searchResults" class="java.lang.String" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="PRODUCT_SEARCH" class="java.lang.String" scope="request"/>
<jsp:useBean id="SHOW_PRICE" class="java.lang.String" scope="request"/>
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
  <TR>
    <td>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="productCatalogListCategory">
        <TR>
          <td nowrap>
            <dhv:evaluate if="<%= "true".equals(searchResults) %>">
              Search Results
            </dhv:evaluate>
            <dhv:evaluate if="<%= !"true".equals(searchResults) %>">
              <dhv:evaluate if="<%= hasText(parentCategory.getName()) %>">
                <%= StringUtils.toHtml(parentCategory.getName()) %>
              </dhv:evaluate>
              <dhv:evaluate if="<%= parentCategory.getId() != Integer.parseInt((String)request.getAttribute("preferredCategoryId")) %>">
                <portlet:renderURL var="parentUrl">
                  <portlet:param name="viewType" value="summary"/>
                  <portlet:param name="categoryId" value="<%= String.valueOf(parentCategory.getParentId()) %>"/>
                </portlet:renderURL>
                <a href="<%= pageContext.getAttribute("parentUrl") %>">Back to Previous Category</a><br />
              </dhv:evaluate>
              <dhv:evaluate if="<%= productCategoryList.size() > 0 %>">
<%
  Iterator categoryIterator = productCategoryList.iterator();
  while (categoryIterator.hasNext()) {
    ProductCategory productCategory = (ProductCategory)categoryIterator.next();
%>
                <portlet:renderURL var="categoryUrl">
                  <portlet:param name="viewType" value="summary"/>
                  <portlet:param name="categoryId" value="<%= String.valueOf(productCategory.getId())%>"/>
                </portlet:renderURL>
                <a href="<%= pageContext.getAttribute("categoryUrl") %>"><%=  toHtml(productCategory.getName()) %></a>
<%
    if (categoryIterator.hasNext()) {
%>
                ::
<%
    }
  }
%>
              </dhv:evaluate>
            </dhv:evaluate>
          </td>
          <dhv:evaluate if="<%= "true".equals(PRODUCT_SEARCH)%>">
            <td align="right" nowrap>
              <portlet:renderURL portletMode="view" var="searchUrl">
                <portlet:param name="viewType" value="search"/>
              </portlet:renderURL>
              <a href="<%= pageContext.getAttribute("searchUrl") %>">Search</a>
            </td>
          </dhv:evaluate>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <th>
      <dhv:pagedListStatus label="Items" title="<%= ((PagedListInfo)renderRequest.getPortletSession().getAttribute("productCatalogListInfo")).getMaxRecords() + " item" + (((PagedListInfo)renderRequest.getPortletSession().getAttribute("productCatalogListInfo")).getMaxRecords() == 1 ? "" : "s") + " found" %>" object="productCatalogListInfo" tableClass="productCatalogListStatus" />
    </th>
  </tr>
<dhv:evaluate if="<%= productCatalogList.size() > 0 %>">
  <%-- Display the products --%>
  <tr>
    <td>
<%
	Iterator productIterator = productCatalogList.iterator();
  int offset = productCatalogList.getPagedListInfo().getCurrentOffset();
  while (productIterator.hasNext()) {
    ProductCatalog productCatalog = (ProductCatalog)productIterator.next();
	%>
			<portlet:renderURL var="url">
				<portlet:param name="page" value="<%= String.valueOf(offset%10) %>"/>
				<portlet:param name="viewType" value="details"/>
				<portlet:param name="productId" value="<%= String.valueOf(productCatalog.getId()) %>"/>
				<portlet:param name="categoryId" value="<%= String.valueOf(parentCategory.getId()) %>"/>
				<portlet:param name="offset" value="<%= String.valueOf(offset) %>"/>
				<portlet:param name="searchResults" value="<%= searchResults %>"/>
			</portlet:renderURL>
				<div style="float: left;width: 140px;height: 160px;padding-right: 3px;">
          <table border="0" width="140" height="160" cellpadding="0" cellspacing="0" class="productCatalogThumbnail">
            <tr>
							<td width="140" height="160" valign="top">
								<a href="<%= pageContext.getAttribute("url") %>">
                  <dhv:evaluate if="<%= productCatalog.getThumbnailImageId() > -1 %>"><dhv:fileItemImage id="<%=  productCatalog.getThumbnailImageId() %>" path="products" thumbnail="true" name="<%=  productCatalog.getName() %>" /></dhv:evaluate>
                  <dhv:evaluate if="<%= productCatalog.getThumbnailImageId() == -1 && productCatalog.getLargestImageId() > -1 %>"><dhv:fileItemImage id="<%=  productCatalog.getLargestImageId() %>" path="products" thumbnail="true" name="<%=  productCatalog.getName() %>" /></dhv:evaluate>
									<div><%=  StringUtils.toHtml(productCatalog.getName()) %></div></a>
                  <dhv:evaluate if="<%= "true".equals(SHOW_PRICE) %>">
                    <dhv:evaluate if="<%= productCatalog.getActivePrice() != null && productCatalog.getActivePrice().getPriceAmount() > 0 %>">
                      <zeroio:currency value="<%= productCatalog.getActivePrice().getPriceAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= applicationPrefs.get("SYSTEM.LANGUAGE") %>" default="&nbsp;"/>
                    </dhv:evaluate>
                  </dhv:evaluate>
              </td>
						</tr>
          </table>
				</div>
	<%
    offset++;
  }
 %>
		</td>
	</tr>
  </dhv:evaluate>
</table>

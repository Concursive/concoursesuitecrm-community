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
<%@ page import="java.util.HashMap" %>
<jsp:useBean id="introduction" class="java.lang.String" scope="request"/>
<jsp:useBean id="productCatalog" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="parentCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="SHOW_SKU" class="java.lang.String" scope="request"/>
<jsp:useBean id="ADD_QUOTE" class="java.lang.String" scope="request"/>
<jsp:useBean id="SKU_TEXT" class="java.lang.String" scope="request"/>
<jsp:useBean id="SHOW_PRICE" class="java.lang.String" scope="request"/>
<jsp:useBean id="PRICE_TEXT" class="java.lang.String" scope="request"/>
<jsp:useBean id="SHOW_PRICE_SAVINGS" class="java.lang.String" scope="request"/>
<jsp:useBean id="ORIGINAL_PRICE_TEXT" class="java.lang.String" scope="request"/>
<jsp:useBean id="PRICE_SAVINGS_TEXT" class="java.lang.String" scope="request"/>
<jsp:useBean id="PRODUCT_SEARCH" class="java.lang.String" scope="request"/>
<jsp:useBean id="INCLUDE_EMAIL" class="java.lang.String" scope="request"/>
<jsp:useBean id="previousPage" class="java.lang.String" scope="request"/>
<jsp:useBean id="offset" class="java.lang.String" scope="request"/>
<jsp:useBean id="searchResult" class="java.lang.String" scope="request"/>
<%@ include file="../../initPage.jsp" %>
<%@ include file="../../initPopupMenu.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript">
  function showEmailSpan(columnId) {
		<%-- TODO: use portlet instance name for uniqueness --%>
    showSpan('emailSpan');
		hideSpan('emailSpanLink');
	}
</script>
<portlet:defineObjects/>
<% HashMap quotes = (HashMap)request.getSession().getAttribute("CartBean"); %>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <TR>
    <td colspan="2">
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="productCatalogListCategory">
        <TR>
          <td nowrap>
            <dhv:evaluate if="<%= "searchResult".equals(previousPage) %>">
              Search Results
            </dhv:evaluate>
            <dhv:evaluate if="<%= !"searchResult".equals(previousPage) %>">
              <dhv:evaluate if="<%= hasText(parentCategory.getName()) %>">
                <%= StringUtils.toHtml(parentCategory.getName()) %><br />
              </dhv:evaluate>
            </dhv:evaluate>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <%-- Product Header --%>
  <tr>
		<th colspan="2">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <TR>
          <td nowrap>
            <portlet:renderURL portletMode="view" var="url">
              <portlet:param name="viewType" value="<%=previousPage%>"/>
              <portlet:param name="categoryId" value="<%= String.valueOf(parentCategory.getId()) %>"/>
              <portlet:param name="page" value="<%= String.valueOf((String) request.getAttribute("page")) %>"/>
            </portlet:renderURL>
            <dhv:evaluate if="<%= "searchResult".equals(previousPage) %>">
              <a href="<%= pageContext.getAttribute("url") %>">Back to Search Results</a>
            </dhv:evaluate>
            <dhv:evaluate if="<%= !"searchResult".equals(previousPage) %>">
              <a href="<%= pageContext.getAttribute("url") %>">Back to Category</a>
            </dhv:evaluate>
          </td>
          <td nowrap align="right">
            <dhv:pagedListStatus object="productCatalogListInfo"/>
          </td>
        </tr>
      </table>
    </th>
  </tr>
  <%-- Optional cart information --%>
  <dhv:evaluate if="<%= "true".equals(ADD_QUOTE) %>">
    <dhv:evaluate if="<%= quotes != null && quotes.containsKey(String.valueOf(productCatalog.getId())) %>">
      <tr>
        <td colspan="2">
          <dhv:label name="quote.ProductQuoted">This item has been added to your cart.  You can add more items or submit the selected items to a sales representative from the cart page.</dhv:label>
        </td>
      </tr>
    </dhv:evaluate>
  </dhv:evaluate>
  <%-- Product Details --%>
  <tr>
    <dhv:evaluate if="<%= productCatalog.getLargestImageId() > -1 %>">
      <td valign="top" nowrap>
        <dhv:fileItemImage id="<%= productCatalog.getLargestImageId() %>" path="products" thumbnail="false" name="<%=  productCatalog.getName() %>" />
		</td>
    </dhv:evaluate>
    <td valign="top" width="100%" <dhv:evaluate if="<%= productCatalog.getLargestImageId() == -1 %>">colspan="2"</dhv:evaluate>>
      <dhv:evaluate if="<%= "true".equals(SHOW_SKU) %>">
        <dhv:evaluate if="<%= StringUtils.hasText(productCatalog.getSku()) %>">
          <strong><%= toHtml(SKU_TEXT) %><%= StringUtils.toHtml(productCatalog.getSku()) %></strong><br />
          <br />
        </dhv:evaluate>
      </dhv:evaluate>
      <strong><%= StringUtils.toHtml(productCatalog.getName()) %></strong><br />
      <br />
      <dhv:evaluate if="<%= hasText(productCatalog.getLongDescription()) %>">
        <%= StringUtils.toHtml(productCatalog.getLongDescription()) %><br />
        <br />
      </dhv:evaluate>
      <%-- show price: must be greater than 0 --%>
      <dhv:evaluate if="<%= "true".equals(SHOW_PRICE) %>">
        <dhv:evaluate if="<%= productCatalog.getActivePrice() != null && productCatalog.getActivePrice().getPriceAmount() > 0 %>">
          <dhv:evaluate if="<%= "true".equals(SHOW_PRICE_SAVINGS) %>">
            <dhv:evaluate if="<%= productCatalog.getActivePrice().getMsrpAmount() > productCatalog.getActivePrice().getPriceAmount() && productCatalog.getActivePrice().getMsrpAmount() > 0 %>">
              <%= toHtml(ORIGINAL_PRICE_TEXT) %> <zeroio:currency value="<%= productCatalog.getActivePrice().getMsrpAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= applicationPrefs.get("SYSTEM.LANGUAGE") %>" default="&nbsp;"/><br />
              <%= toHtml(PRICE_SAVINGS_TEXT) %> <zeroio:currency value="<%= (productCatalog.getActivePrice().getPriceAmount() - productCatalog.getActivePrice().getMsrpAmount()) %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= applicationPrefs.get("SYSTEM.LANGUAGE") %>" default="&nbsp;" allowNegative="true"/><br />
              <br />
            </dhv:evaluate>
          </dhv:evaluate>
          <strong><%= toHtml(PRICE_TEXT) %><zeroio:currency value="<%= productCatalog.getActivePrice().getPriceAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= applicationPrefs.get("SYSTEM.LANGUAGE") %>" default="&nbsp;"/></strong><br />
          <br />
        </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= hasText(productCatalog.getShippingTimeName()) %>">
        <dhv:label name="product.productShipTime">Product Ship Time</dhv:label>:
        <%= toHtml(productCatalog.getShippingTimeName()) %><br />
        <br />
      </dhv:evaluate>
      <%-- show options --%>
    </td>
  </tr>
</table>
<%-- show email option --%>
<dhv:evaluate if="<%= "true".equals(ADD_QUOTE) %>">
  <dhv:evaluate if="<%= quotes == null || !quotes.containsKey(String.valueOf(productCatalog.getId())) %>">
  <form name="addQuote" action="<portlet:actionURL/>" method="POST">
    <input type="hidden" name="actionType" value="quote"/>
    <input type="hidden" name="forwardpage" value="<%= String.valueOf((String) request.getAttribute("page"))%>"/>
    <input type="hidden" name="forwardviewType" value="details"/>
    <input type="hidden" name="forwardproductId" value="<%= String.valueOf(productCatalog.getId()) %>"/>
    <input type="hidden" name="forwardcategoryId" value="<%= String.valueOf(parentCategory.getId()) %>"/>
    <input type="hidden" name="forwardoffset" value="<%=offset%>"/>
    <input type="hidden" name="forwardsearchResult" value="<%= searchResult %>"/>
    <input type="submit" name="Add Quote" value="Receive a quote for this item"/>
   </form>
  </dhv:evaluate>
</dhv:evaluate>
<dhv:evaluate if="<%= "true".equals(INCLUDE_EMAIL) %>">
  <span name="emailSpanLink" id="emailSpanLink">
    <input type="button" name="Send Email" value="Email this item to a friend" onclick="javascript:showEmailSpan();" />
  </span>
  <span name="emailSpan" id="emailSpan" style="display:none">
	<form name="emailForm" action="<portlet:actionURL />" method="post" >
    <table cellpadding="4" cellspacing="0" width="100%" class="details">
			<tr>
				<th colspan="2">
					Complete the following form to email this item to a friend...
				</th>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel">
					Your Name
				</td>
				<td width="100%">
					<input type="text" size="35" maxlength="80" name="yourName" value="" />
				</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">
					Your email address
				</td>
				<td>
					<input type="text" size="35" maxlength="80" name="yourEmailAddress" value="" />
				</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">
					Your friend's address
				</td>
				<td>
					<input type="text" size="35" maxlength="80" name="yourFriendsAddress" value="" />
				</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">
					Comments
				</td>
				<td>
					 <textarea name="comments" cols="55" rows="8"></textarea>
				</td>
			</tr>
      <TR>
        <td>&nbsp;</td>
        <td><input type="submit" name="Submit" value="Submit" /></td>
      </tr>
      <input type="hidden" name="actionType" value="sendEmail" />
      <input type="hidden" name="productURL" value="<%=  request.getAttribute("productURL") %>" />
			<input type="hidden" name="productId" value="<%=  productCatalog.getId() %>" />
			<input type="hidden" name="categoryId" value="<%=  parentCategory.getId() %>" />
			<input type="hidden" name="page" value="<%= String.valueOf((String) request.getAttribute("page")) %>" />
			<input type="hidden" name="offset" value="<%= offset %>" />
			<input type="hidden" name="viewType" value="details" />
		</table>
	</form>
  </span>
</dhv:evaluate>


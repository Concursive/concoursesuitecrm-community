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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*, org.aspcfs.modules.products.base.*, org.aspcfs.utils.web.*" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="ProductOption" class="org.aspcfs.modules.products.base.ProductOption" scope="request"/>
<jsp:useBean id="PricingList" class="org.aspcfs.modules.products.base.ProductOptionValuesList" scope="request"/>
<jsp:useBean id="ResultMap" class="java.util.TreeMap" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="product_option_pricing_list_menu.jsp" %> 
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
			<a href="ProductCatalogEditor.do?command=Options&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.editor">Editor</dhv:label></a> >
			<a href="ProductOptions.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.searchOptions">Search Options</dhv:label></a> >
      <a href="ProductOptions.do?command=Search&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
      <a href="ProductOptions.do?command=Details&moduleId=<%= PermissionCategory.getId() %>&optionId=<%= ProductOption.getId() %>"><dhv:label name="product.optionDetails">Option Details</dhv:label></a> >
      <dhv:label name="product.pricings">Pricings</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<%@ include file="product_option_header_include.jsp" %>
<% String param1 = "optionId=" + ProductOption.getId(); %>
<% String param2 = "moduleId=" + PermissionCategory.getId(); %>
<dhv:container name="productoptions" selected="values" object="ProductOption" param="<%= param1 + "|" + param2 %>">
  <dhv:evaluate if="<%= ProductOption.getHasMultiplier() %>">
    <a href="ProductOptionPricings.do?command=Add&moduleId=<%= PermissionCategory.getId() %>&optionId=<%= ProductOption.getId() %>"><dhv:label name="product.addNewMultiplierValue">Add new multiplier - value</dhv:label></a>
  </dhv:evaluate>
  <dhv:evaluate if="<%= !ProductOption.getHasMultiplier() %>">
    <a href="ProductOptionPricings.do?command=Add&moduleId=<%= PermissionCategory.getId() %>&optionId=<%= ProductOption.getId() %>"><dhv:label name="product.addNewPrice">Add new price</dhv:label></a>
  </dhv:evaluate>
 <br>&nbsp;
  <%
    Iterator results = ResultMap.keySet().iterator();
    while (results.hasNext()) {
      Integer result = (Integer) results.next();
  %>
      <table align="center" width="100%" cellpadding="4" cellspacing="0" border="0">
        <tr>
          <th nowrap valign="bottom" align="left" class="pagedListTab"><%= toHtml((String) ResultMap.get(result)) %></th>
          <td nowrap width="100%" valign="bottom" align="left">&nbsp;</td>
        </tr>
      </table>
      <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
        <tr>
          <th width="8">&nbsp;</th>
          <dhv:evaluate if="<%= ProductOption.getHasRange() %>">
            <th><dhv:label name="product.rangeMin">Range Min</dhv:label></th>
            <th><dhv:label name="product.rangeMax">Range Max</dhv:label></th>
          </dhv:evaluate>
          <dhv:evaluate if="<%= ProductOption.getHasMultiplier() %>">
            <th><dhv:label name="product.value">Value</dhv:label></th>
            <th><dhv:label name="product.multiplier">Multiplier</dhv:label></th>
            <th><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></th>
          </dhv:evaluate>
          <dhv:evaluate if="<%= !ProductOption.getHasMultiplier() %>">
            <th><dhv:label name="product.msrp">MSRP</dhv:label></th>
            <th><dhv:label name="quotes.Price">Price</dhv:label></th>
            <th><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></th>
          </dhv:evaluate>
        </tr>
        <%
          Iterator j = PricingList.iterator();
          int rowid = 0;
          int i = 0;
          boolean exists = false;
          while (j.hasNext()) {
            i++;
            rowid = (rowid != 1 ? 1 : 2);
            ProductOptionValues thisPricing = (ProductOptionValues) j.next();
            if (result.intValue() == thisPricing.getResultId()) {
              exists = true;
        %>

        <tr class="containerBody">
          <td width="8" valign="center" nowrap class="row<%= rowid %>">
            <% int status = -1;%>
            <%-- <% status = thisPricing.getEnabled() %> --%>
            <%-- Use the unique id for opening the menu, and toggling the graphics --%>
            <a href="javascript:displayMenu('select<%= i %>','menuPricing', '<%= thisPricing.getId() %>', '<%= status %>');"
            onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuPricing');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
          </td>
          <dhv:evaluate if="<%= ProductOption.getHasRange() %>">
            <td class="row<%= rowid %>"><%= thisPricing.getRangeMin() %></td>
            <td class="row<%= rowid %>"><%= thisPricing.getRangeMax() %></td>
          </dhv:evaluate>
          <dhv:evaluate if="<%= ProductOption.getHasMultiplier() %>">
            <td class="row<%= rowid %>"><%= thisPricing.getValue() %></td>
            <td class="row<%= rowid %>"><%= thisPricing.getMultiplier() %></td>
            <td class="row<%= rowid %>"><%= toHtml(thisPricing.getDescription()) %></td>
          </dhv:evaluate>
          <dhv:evaluate if="<%= !ProductOption.getHasMultiplier() %>">
            <%-- <td class="row<%= rowid %>"><%= toHtml(thisPricing.getMsrpCurrencyName()) %></td> --%>
            <td class="row<%= rowid %>">
              <zeroio:currency value="<%= thisPricing.getMsrpAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/>
            </td>
            <%-- <td class="row<%= rowid %>"><%= toHtml(thisPricing.getPriceCurrencyName()) %></td> --%>
            <td class="row<%= rowid %>">
              <zeroio:currency value="<%= thisPricing.getPriceAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/>
            </td>
            <td class="row<%= rowid %>"><%= toHtml(thisPricing.getDescription()) %></td>
          </dhv:evaluate>
        </tr>
      <%
           }
         }
         if (!exists) {
      %>
         <tr class="containerBody">
          <td colspan="6"><dhv:label name="product.noValueConfigured">No value configured</dhv:label></td>
         </tr>
      <% } %>
      </table>
      &nbsp;<br />
    <%
     }
    %>
</dhv:container>
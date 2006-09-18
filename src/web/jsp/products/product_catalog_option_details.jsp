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
<%@ page import="java.util.*, org.aspcfs.modules.products.base.*, org.aspcfs.modules.products.configurator.*" %>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="productCatalog" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="productCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="productOption" class="org.aspcfs.modules.products.base.ProductOption" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="propertyList" class="org.aspcfs.modules.products.configurator.OptionPropertyList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="configName" class="java.lang.String" scope="request"/>
<jsp:useBean id="allowMultiplePrices" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<form name="paramForm" action="ProductCatalogOptions.do?command=LoadConfigurator" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
		<a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
			<dhv:label name="product.editor">Editor</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<% String param1 = "productId=" + productCatalog.getId(); %>
<% String param2 = "moduleId=" + permissionCategory.getId(); %>
<% String param3 = "categoryId=" + productCategory.getId(); %>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="abovetab">
    <td>
      <% String link = "ProductCatalogEditor.do?command=List&moduleId=" + permissionCategory.getId(); %>
      <dhv:productCategoryHierarchy link="<%= link %>" showLastLink="true"/> > 
      <a href="ProductCatalogs.do?command=Details&productId=<%= productCatalog.getId() %>&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>"><dhv:label name="product.productDetails">Product Details</dhv:label></a> >
      <a href="ProductCatalogOptions.do?command=List&productId=<%= productCatalog.getId() %>&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>"><dhv:label name="product.options">Options</dhv:label></a> >
      <dhv:label name="product.optionDetails">Option Details</dhv:label>
    </td>
  </tr>
</table>
<br />
<dhv:container name="productcatalogs" selected="options" object="productCatalog" param="<%= param1 + "|" + param2 + "|" + param3 %>">
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td>
    	<strong><%= toHtml(productOption.getLabel()) %></strong><br/><br/>
      <dhv:evaluate if="<%= !productCatalog.isTrashed() %>">
        <dhv:permission name="product-catalog-edit">
          <input type="submit" value="<dhv:label name="button.modify">Modify</dhv:label>">
        </dhv:permission>
        <dhv:permission name="product-catalog-delete">
          <input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('ProductCatalogOptions.do?command=ConfirmDelete&optionId=<%= productOption.getId() %>&moduleId=<%= permissionCategory.getId() %>&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>&popup=true', 'ProductCatalogOptions.do?command=List', 'Delete_productoption', '330', '200', 'yes', 'no');"/>
        </dhv:permission>
        <dhv:permission name="product-catalog-edit,product-catalog-delete">
          &nbsp;<br /><br />
        </dhv:permission>
      </dhv:evaluate>
      <input type="hidden" name="optionId" value="<%= productOption.getId() %>">
      <input type="hidden" name="productId" value="<%= productCatalog.getId() %>">
      <input type="hidden" name="categoryId" value="<%= productCategory.getId() %>">
      <input type="hidden" name="moduleId" value="<%= permissionCategory.getId() %>">
      <input type="hidden" name="configId" value="<%= productOption.getConfiguratorId() %>">
      <input type="hidden" name="action" value="modify">
      <table cellpadding="4" cellspacing="0" width="100%" class="details">
        <tr>
        <th colspan="2">
          <%
            String params = "optionName=" + toHtml(configName) ;
          %>
          <strong><dhv:label name="product.addNewOption" param="<%=params%>"><%=toHtml(configName)%> Option Properties</dhv:label></strong>
        </th>
      </tr>
      <%
        int count = 0;
        Iterator i = propertyList.iterator();
        while (i.hasNext()) {
          OptionProperty property = (OptionProperty) i.next();
          //Show only the parameters that require input from the user
      %>
          <dhv:evaluate if="<%= (property.getIsForPrompting()) && (property.getType() == property.SIMPLE_PROPERTY) %>"><% ++count; %>
            <tr class="containerBody">
              <td class="formLabel"><%= toHtml(property.getDisplay()) %></td>
              <td class="empty">
                  <dhv:evaluate if="<%= property.getName().startsWith("boolean_") %>">
                    <% if ("true".equals(property.getValue())) { %> 
                          <dhv:label name="account.yes">Yes</dhv:label>
                    <% } else { %>
                          <dhv:label name="account.no">No</dhv:label>
                    <% } %>
                  </dhv:evaluate>
                  <dhv:evaluate if="<%= !property.getName().startsWith("boolean_") %>">
                    <%= toHtml(property.getValue()) %>
                  </dhv:evaluate>
                  &nbsp;&nbsp;<font color="green"><%= toHtml(property.getNote()) %></font>
               </td>
            </tr>
          </dhv:evaluate>
      <%
        }
      %>
      <dhv:evaluate if="<%= count == 0 %>">
        <tr class="containerBody">
          <td colspan="2"><dhv:label name="product.noPropertiesRequired">No Properties required.</dhv:label></td>
        </tr>
      </dhv:evaluate>
    </table>
    &nbsp;<br />
    <dhv:evaluate if="<%= !("true".equals(allowMultiplePrices)) %>">
    <table cellpadding="4" cellspacing="0" width="100%" class="details">
      <tr>
        <th colspan="2">
          <strong><dhv:label name="product.optionPriceAdjustment">Option Price Adjustment</dhv:label></strong>
        </th>
      </tr>
      <%
        int count1 = 0;
        Iterator j = propertyList.iterator();
        while (j.hasNext()) {
          OptionProperty property = (OptionProperty) j.next();
          //Show only the parameters that require input from the user
      %>
          <dhv:evaluate if="<%= (property.getIsForPrompting()) && (property.getType() == property.BASEADJUST_PROPERTY) %>"><% ++count1; %>
            <tr class="containerBody">
              <td class="formLabel"><%= toHtml(property.getDisplay()) %></td>
              <td class="empty"  valign="top" nowrap>
                <table cellspacing="1" cellpadding="1" border="0">
                  <tr>
                    <td class="empty">
                        <zeroio:currency value="<%= Double.parseDouble(property.getValue()) %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>  
                        &nbsp;&nbsp;<font color="green"><%= toHtml(property.getNote()) %></font>
                     </td>
                  </tr>
                </table>
              </td>
            </tr>
          </dhv:evaluate>
      <%
        }
      %>
      <dhv:evaluate if="<%= count1 == 0 %>">
        <tr class="containerBody">
          <td colspan="2"><dhv:label name="product.noPropertiesRequired">No Properties required.</dhv:label></td>
        </tr>
      </dhv:evaluate>
    </table>
    </dhv:evaluate>
    <%-- Lookup List configurator --%>
    <dhv:evaluate if="<%= "true".equals(allowMultiplePrices) %>">
    <table cellpadding="4" cellspacing="0" width="100%" class="details">
      <tr>
        <th><strong><dhv:label name="quotes.item">Item</dhv:label></strong></th>
        <th width="55%"><strong><dhv:label name="product.description">Description</dhv:label></strong></th>
        <th width="40%" nowrap><strong><dhv:label name="product.optionPriceAdjustment">Option Price Adjustment</dhv:label></strong></th>
        <th><strong><dhv:label name="product.enabled">Enabled</dhv:label></strong></th>
      </tr>
      <%
        int counter = 0;
        int rowcount = 0;
        Iterator k = propertyList.iterator();
        while (k.hasNext()) {
          OptionProperty property = (OptionProperty) k.next();
          //Show only the parameters that require input from the user
          if (!property.getIsForPrompting() || property.getType() == property.SIMPLE_PROPERTY) {
             continue;
          }
          // do not display properties with default values
          ++counter;
          if ((counter+2)%3 == 0) {
            ++rowcount;
          }
      %>
     <dhv:evaluate if="<%= (counter+2)%3 == 0 %>">
      <tr class="containerBody">
          <td class="formLabel" align="center"><%= "" + rowcount + "." %></td>
    </dhv:evaluate>
          <dhv:evaluate if="<%= (property.getIsForPrompting() && property.getType() == property.BASEADJUST_PROPERTY) %>">
              <td class="empty"  align="right" valign="top" nowrap>
                <table cellspacing="1" cellpadding="1" border="0">
                  <tr>
                    <td align="right"><zeroio:currency value="<%= Double.parseDouble(property.getValue()) %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/></td>
                  </tr>
                </table>
              </td>
          </dhv:evaluate>
          <dhv:evaluate if="<%= (property.getIsForPrompting() && property.getType() == property.LOOKUP_PROPERTY) %>">
            <dhv:evaluate if="<%= property.getName().startsWith("boolean_") %>">
                <% if ("true".equals(property.getValue())) { %> 
                      <td class="empty" width="100%" align="center"><dhv:label name="account.yes">Yes</dhv:label></td>
                <% } else { %>
                      <td class="empty" width="100%" align="center"><dhv:label name="account.no">No</dhv:label></td>
                <% } %>
            </dhv:evaluate>
            <dhv:evaluate if="<%= !property.getName().startsWith("boolean_") %>">
                <td class="empty" width="100%"><%= toHtml(property.getValue()) %></td>
            </dhv:evaluate>
          </dhv:evaluate>
    <dhv:evaluate if="<%= counter%3 == 0 %>">
      </tr>
    </dhv:evaluate>
      <%
        }
      %>
      <dhv:evaluate if="<%= counter%3 != 0 %>">
      <%
        while (counter%3 != 0) {
          ++counter;
      %>
        <td>&nbsp;</td>
      <%
        }
      %>
      </tr>
    </dhv:evaluate>
    <dhv:evaluate if="<%= counter == 0 %>">
        <tr class="containerBody">
          <td colspan="4"><dhv:label name="product.noPropertiesRequired">No Properties required.</dhv:label></td>
        </tr>
    </dhv:evaluate>
    </table>
    </dhv:evaluate>
    <%-- End lookup list --%>
    <br />
    <table cellpadding="4" cellspacing="0" width="100%" class="details">
      <tr>
        <th colspan="2">
          <strong><dhv:label name="product.option.availability">Availability</dhv:label></strong>
        </th>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          <dhv:label name="product.enabled">Enabled</dhv:label>
        </td>
        <td>
          <% if(productOption.getEnabled()) {%>
            <dhv:label name="account.yes">Yes</dhv:label>
          <%} else {%>
            <dhv:label name="account.no">No</dhv:label>
          <%}%>
        </td>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          <dhv:label name="documents.details.startDate">Start Date</dhv:label>
        </td>
        <td>
          <zeroio:tz timestamp="<%= productOption.getStartDate() %>" />  
        </td>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          <dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label>
        </td>
        <td>
          <zeroio:tz timestamp="<%= productOption.getEndDate() %>" />
        </td>
      </tr>
    </table>
    <br />
    <dhv:evaluate if="<%= !productCatalog.isTrashed() %>">
      <dhv:permission name="product-catalog-edit">
        <input type="submit" value="<dhv:label name="button.modify">Modify</dhv:label>">
      </dhv:permission>
      <dhv:permission name="product-catalog-delete">
			  <input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('ProductCatalogOptions.do?command=ConfirmDelete&optionId=<%= productOption.getId() %>&moduleId=<%= permissionCategory.getId() %>&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>&popup=true', 'ProductCatalogOptions.do?command=List', 'Delete_productoption', '330', '200', 'yes', 'no');"/>
      </dhv:permission>
    </dhv:evaluate>
		</td>
	</tr>
</table>
</dhv:container>
</form>

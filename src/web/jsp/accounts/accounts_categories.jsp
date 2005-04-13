<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="searchListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table cellspacing="0" class="trails">
  <tr>
    <td>
      <a href="ProductsCatalog.do?command=AccountProductCategories&quoteId=<%= quote.getId() %>"><dhv:label name="product.productCatalog">Product Catalog</dhv:label></a> > 
      <dhv:label name="account.listOfCategories">List of Categories</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<table>
  <tr>
    <td class="containerBack">
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="product.productCatalog">Product Catalog</dhv:label></strong>
          </th>
        </tr>
      <%
      Iterator categoryIterator = categoryList.iterator();
      while(categoryIterator.hasNext()){
        ProductCategory category = (ProductCategory) categoryIterator.next();
      %>
        <tr class="containerBody">
          <td nowrap>
            <a href="ProductsCatalog.do?command=AccountProducts&quoteId=<%= quote.getId() %>&categoryId=<%= category.getId() %>"><%= category.getName() %></a>
          </td>
          <td>
            <%= toHtml(category.getShortDescription()) %><br />
          </td>
        </tr>
      <%
      }
      %>
      </table>
    </td>
  </tr>
</table>

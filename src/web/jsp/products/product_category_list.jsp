<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="searchListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      Product Catalog 
    </td>
  </tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Product Catalog</strong>
    </th>
  </tr>
  <%

  Iterator categoryIterator = categoryList.iterator();
  while(categoryIterator.hasNext()){
    ProductCategory category = (ProductCategory) categoryIterator.next();
  %>
    <tr>
      <td class="formLabel">
        <a href="ProductsCatalog.do?command=ProductList&id=<%= category.getId() %>&hiddenFieldId=<%= request.getAttribute("hiddenFieldId") %>&displayFieldId=<%= request.getAttribute("displayFieldId") %>"><%= category.getName() %></a>
      </td>
      <td>
        <%= toHtml(category.getShortDescription()) %><br />
      </td>
    </tr>
  <%
  }
  %>
</table>
</form>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="category" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="trails" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="javascript">
  function submitPage(productId, displayValue, hiddenFieldId, displayFieldId) {
    opener.changeDivContent(displayFieldId, displayValue);
    opener.setParentList(hiddenFieldId, productId);
    self.close();
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="ProductsCatalog.do?command=CategoryList&hiddenFieldId=<%= request.getAttribute("hiddenFieldId") %>&displayFieldId=<%= request.getAttribute("displayFieldId") %>">Product Catalog</a> >
      <%
        trails.remove(0);
        Iterator j = trails.iterator();
        while (j.hasNext()) {
          ProductCategory tmp = (ProductCategory) j.next();
      %>
          <a href="ProductsCatalog.do?command=ProductList&id=<%= tmp.getId() %>&hiddenFieldId=<%= request.getAttribute("hiddenFieldId") %>&displayFieldId=<%= request.getAttribute("displayFieldId") %>"><%= toHtml(tmp.getName()) %></a> >  
      <%
        }
      %>  
      <%= toHtml(category.getName()) %>
    </td>
  </tr>
</table>
<%-- End Trails --%>  <tr>
<dhv:evaluate if="<%= category.getChildList().size() > 0 %>">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong>Product Catalog</strong>
      </th>
    </tr>
    <%
    Iterator i = category.getChildList().iterator();
    while (i.hasNext()){
      ProductCategory childCategory = (ProductCategory) i.next();
    %>
      <tr>
        <td class="formLabel">
          <a href="ProductsCatalog.do?command=ProductList&id=<%= childCategory.getId() %>&hiddenFieldId=<%= request.getAttribute("hiddenFieldId") %>&displayFieldId=<%= request.getAttribute("displayFieldId") %>"><%= childCategory.getName() %></a>
        </td>
        <td>
          <%= toHtml(childCategory.getShortDescription()) %><br />
        </td>
      </tr>
    <%
    }
    %>
  </table>
</dhv:evaluate>
<dhv:evaluate if="<%= category.getProductList().size() > 0 %>">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>Product Name</th>
    <th>Product Description</th>
  </tr>
  <%
    Iterator productIterator = category.getProductList().iterator();
    while(productIterator.hasNext()){
      ProductCatalog product = (ProductCatalog) productIterator.next();
  %>
  <tr>
    <td class="formLabel"><a href="javascript:submitPage('<%= product.getId() %>', '<%= product.getCategoryName() + ", " + toHtml(product.getName()) %>', '<%= request.getAttribute("hiddenFieldId") %>','<%= request.getAttribute("displayFieldId") %>');"><%= toHtml(product.getName()) %></td>
    <td><%= (product.getShortDescription().equals("") || product.getShortDescription() == null) ? "-" : toHtml(product.getShortDescription()) %>&nbsp;</td>
  </tr>
  <%
    }
  %>
</table>
</dhv:evaluate>
</form>


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.products.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="productDetails" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
</script>
<form name="viewProduct" action="ProductsCatalog.do?command=ModifyProduct&productId=<%=productDetails.getId()%>&return=view" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ProductsCatalog.do?command=ListAllProducts">Products</a> >
<a href="ProductsCatalog.do?command=ListAllProducts">View Products</a> >
Product Details
</td>
</tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td>
      <dhv:permission name="product-catalog-product-edit"><input	type="submit" value="Modify" /></dhv:permission>
      <dhv:permission name="product-catalog-product-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('ProductsCatalog.do?command=ConfirmDeleteProduct&productId=<%=productDetails.getId()%>&popup=true','ProductsCatalog.do?command=ViewProduct&productId=<%=productDetails.getId()%>', 'Delete_product','330','250','yes','no');"></dhv:permission>
      <br /> <br />
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
          <tr>
            <th colspan="2">
              <strong>Details</strong>
            </th>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              Code
            </td>
            <td>
              <%=toHtml(productDetails.getSku())%>
            </td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              Description
            </td>
            <td>
              <%=toHtml(productDetails.getName())%>
            </td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              Category
            </td>
            <td>
              <%= toHtml(productDetails.getCategoryName()) %>
            </td>
          </tr>
        </table>
      <br />
      <dhv:permission name="product-catalog-product-edit"><input	type="submit" value="Modify" /></dhv:permission>
      <dhv:permission name="product-catalog-product-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('ProductsCatalog.do?command=ConfirmDeleteProduct&productId=<%=productDetails.getId()%>&popup=true','ProductsCatalog.do?command=ViewProduct&productId=<%=productDetails.getId()%>', 'Delete_product','330','250','yes','no');"></dhv:permission>
  </td>
  </tr>
</table>
</form>

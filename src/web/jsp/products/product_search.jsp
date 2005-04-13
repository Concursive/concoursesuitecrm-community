<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="productListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="category" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkInt.js"></script>
<script language="JavaScript">
  function clearForm() {
    document.forms['searchCategoryNumber'].categoryId.value="";
  }
  
  function doCheck() {
    if(document.forms['searchCategoryNumber'].categoryId.value == ""){
      document.forms['searchCategoryNumber'].action = document.forms['searchCategoryNumber'].action + '&searchAll=true';
    } else {
      var result = checkInt( document.forms['searchCategoryNumber'].id.value);
      if(!result){
        alert(label("check.number.invalid",'Incorrect format! Please enter a number'));
        return false;
      }
    }
    return true;
  }
</script>
<body onLoad="javascript:document.forms['searchCategoryNumber'].categoryId.focus();">
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Products.do"><dhv:label name="product.catalog">Catalog</dhv:label></a> > 
      <dhv:label name="button.search">Search</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<%= showError(request, "actionError", false) %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="product.searchCategoriesByNumber">Search Categories By Number</dhv:label></strong>
    </th>
  </tr>
<form name="searchCategoryNumber" action="Products.do?command=Search" method="post" onSubmit="return doCheck();">
  <tr>
    <td class="formLabel">
      <dhv:label name="product.categoryNumber.symbol">Category #</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="categoryId" value="<%= productListInfo.getSearchOptionValue("categoryId") %>"/>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <input type="submit" value="<dhv:label name="button.search">Search</dhv:label>"/>&nbsp;<input type="reset" value="<dhv:label name="button.reset">Reset</dhv:label>" onClick="clearForm();"/>
    </td>
  </tr>
</form>
</table>
</body>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*,org.aspcfs.utils.*" %>
<%@ page import="org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:init_page();">
<script type="text/javascript">
  function init_page() {
    var categories = '';
<%
  Iterator iter = (Iterator) categoryList.iterator();
  while (iter.hasNext()) {
    ProductCategory category =(ProductCategory)iter.next();
%>
    categories = categories+'<%= category.getId()+"|" %>';
<%}%>
    if ('<%= isPopup(request) %>'== 'true') {
      opener.resetCategories(categories);
      self.close();
    } else {
      parent.resetCategories(categories);
    }
  }
</script>
</body>

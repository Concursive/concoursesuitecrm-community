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
 --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="java.util.*,org.aspcfs.modules.products.base.*"%>
<%@ page import="com.sun.org.apache.bcel.internal.generic.NEW"%>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request" />
<jsp:useBean id="categoryHierarchy" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request" />
<jsp:useBean id="productCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request" />
<jsp:useBean id="productCatalog" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request" />
<jsp:useBean id="action" class="java.lang.String" scope="request" />
<jsp:useBean id="parentId" class="java.lang.String" scope="request" />
<jsp:useBean id="returnAction" class="java.lang.String" scope="request" />
  <%String path = "Portal.do?command=ListProductMultiCategories&productId="+productCatalog.getId()+"&moduleId="+permissionCategory.getId()+"&categoryId="+productCatalog.getCategoryId();%>
  <dhv:productCategoryTree items="categoryHierarchy" checked="checkedList" parentItemProperty="parentId" path="<%=path%>"></dhv:productCategoryTree>
<script language='javascript' type='text/javascript'>
    if (window.parent.loadNode != null && window.parent != self)
    {
        elem = document.getElementById('ul_<%=parentId%>'); 
        if (elem && elem.innerHTML)
        {
            window.parent.loadNode('<%=parentId%>', elem);
            location.replace("javascript:'';");
        }
    }
</script>

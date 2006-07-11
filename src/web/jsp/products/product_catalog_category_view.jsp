<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/ypSlideOutMenusC.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/preloadImages.js"></script>
<script language="JavaScript" type="text/javascript">
  loadImages('select');
  var thisCode = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, code) {
    thisCode = code;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuCategories", "down", 0, 0, 170, getHeight("menuCategoriesTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
</script>
<body onLoad="resizeIframe()" bgcolor="#FFFFFF" LEFTMARGIN="0" MARGINWIDTH="0" TOPMARGIN="0" MARGINHEIGHT="0">
<div id="menuCategoriesContainer" class="menu">
  <div id="menuCategoriesContent">
    <table id="menuCategoriesTable" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="removeCategory(thisCode);">
        <th valign="top">
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.remove">Remove</dhv:label>
        </td>
      </tr>
    </table>
  </div>
</div>
<script language="JavaScript" type="text/javascript" src="javascript/iframe.js"></script>
<script type="text/javascript">
  function resizeIframe() {
    parent.document.getElementById('server_list').height = getHeight("categoriesTable")+10;
  }
  function removeCategory(id) {
    parent.removeCategory(id);
  }
</script>
<table id="categoriesTable" cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th nowrap>&nbsp;</th>
    <th width="100%">Category</th>
  </tr>
<%
  if (categoryList != null && categoryList.size() > 0) {
  int rowid = 0;
  int i = 0;
  Iterator iter = (Iterator) categoryList.iterator();
  while (iter.hasNext()) {
   ProductCategory cat = (ProductCategory)iter.next();
    i++;
    rowid = (rowid != 1 ? 1 : 2);
%>
  <tr class="row<%= rowid %>">
    <td valign="center" nowrap>
      <a href="javascript:displayMenu('select<%= i %>', 'menuCategories', <%= cat.getId() %>);"
       onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuCategories');">
       <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%" valign="top" nowrap>
			<%if(cat.getFullPath()!=null){
			ProductCategoryList fullPath = cat.getFullPath();
			Iterator fullPathIter = fullPath.iterator();
			while (fullPathIter.hasNext()) {
			 ProductCategory pc = (ProductCategory)fullPathIter.next();
			%>
			<%=pc.getName()%><%if(fullPathIter.hasNext()){%> > <%}%>
		<%}
		}%>
		</td>
  </tr>
<%} } else {%>
  <tr>
    <td colspan="3" valign="top"><dhv:label name="products.detail.noCategoriesPresent.text">No categories present for the product</dhv:label></td>
  </tr>
<%}%>
</table>
</body>

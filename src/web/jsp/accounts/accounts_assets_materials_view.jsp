<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.io.*, java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.assets.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,java.text.DateFormat" %>
<jsp:useBean id="materialMap" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="materialTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
      new ypSlideOutMenu("menuMaterials", "down", 0, 0, 170, getHeight("menuMaterialsTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
</script>
<body onLoad="resizeIframe()" bgcolor="#FFFFFF" LEFTMARGIN="0" MARGINWIDTH="0" TOPMARGIN="0" MARGINHEIGHT="0">
<div id="menuMaterialsContainer" class="menu">
  <div id="menuMaterialsContent">
    <table id="menuMaterialsTable" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="removeMaterial(thisCode);">
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
    parent.document.getElementById('server_list').height = getHeight("materialsTable")+10;
  }
  function removeMaterial(id) {
    parent.removeMaterial(id);
  }
</script>
<table id="materialsTable" cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th nowrap>&nbsp;</th>
    <th width="100%"><dhv:label name="accounts.assets.material">Material</dhv:label></th>
    <th nowrap><dhv:label name="quotes.quantity">Quantity</dhv:label></th>
  </tr>
<%
  if (materialMap != null && materialMap.keySet().size() > 0) {
  int rowid = 0;
  int i = 0;
  Iterator iter = (Iterator) materialMap.keySet().iterator();
  while (iter.hasNext()) {
    Integer key = (Integer) iter.next();
    String value = (String) materialMap.get(key);
    i++;
    rowid = (rowid != 1 ? 1 : 2);
%>
  <tr class="row<%= rowid %>">
    <td valign="center" nowrap>
      <a href="javascript:displayMenu('select<%= i %>', 'menuMaterials', <%= key.intValue() %>);"
       onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuMaterials');">
       <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%" valign="top" nowrap>
      <%= toHtml(materialTypeList.getSelectedValue(key.intValue())) %>
    </td>
    <td nowrap>
      <dhv:evaluate if="<%= value != null && !"".equals(value.trim()) && java.lang.Math.round(Float.parseFloat(value.trim())) >= 0 %>">
        <%= value %>
      </dhv:evaluate>
    </td>
  </tr>
<%} } else {%>
  <tr>
    <td colspan="3" valign="top"><dhv:label name="accounts.assets.noMaterialsPresent.text">No materials present for the asset</dhv:label></td>
  </tr>
<%}%>
</table>
</body>

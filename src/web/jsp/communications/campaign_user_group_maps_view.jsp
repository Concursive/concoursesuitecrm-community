<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.io.*, java.util.*,org.aspcfs.modules.communications.base.*,org.aspcfs.utils.web.*,java.text.DateFormat" %>
<jsp:useBean id="userGroupMaps" class="java.util.HashMap" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/ypSlideOutMenusC.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/preloadImages.js"></script>
<script language="JavaScript" type="text/javascript">
  loadImages('select');
  var thisUserGroupId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, ugId) {
    thisUserGroupId = ugId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuUserGroup", "down", 0, 0, 170, getHeight("menuUserGroupTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
</script>
<body onLoad="resizeIframe()" bgcolor="#FFFFFF" LEFTMARGIN="0" MARGINWIDTH="0" TOPMARGIN="0" MARGINHEIGHT="0">
<div id="menuUserGroupContainer" class="menu">
  <div id="menuUserGroupContent">
    <table id="menuUserGroupTable" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="removeGroup(thisUserGroupId);">
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
    parent.document.getElementById('server_list').height = getHeight("userGroupsTable")+10;
  }
  function removeGroup(id) {
    parent.removeGroup(id);
  }
</script>
<table id="userGroupsTable" cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th nowrap>&nbsp;</th>
    <th width="100%"><dhv:label name="usergroups.userGroupName">User Group Name</dhv:label></th>
  </tr>
<%
  if (userGroupMaps != null && userGroupMaps.keySet().size() > 0) {
  int rowid = 0;
  int i = 0;
  Iterator iter = (Iterator) userGroupMaps.keySet().iterator();
  while (iter.hasNext()) {
    Integer key = (Integer) iter.next();
    CampaignUserGroupMap value = (CampaignUserGroupMap) userGroupMaps.get(key);
    i++;
    rowid = (rowid != 1 ? 1 : 2);
%>
  <tr class="row<%= rowid %>">
    <td valign="center" nowrap>
      <a href="javascript:displayMenu('select<%= i %>', 'menuUserGroup', <%= key.intValue() %>);"
       onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuUserGroup');">
       <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%" valign="top" nowrap>
      <%= toHtml(value.getGroupName()) %>
    </td>
  </tr>
<%} } else {%>
  <tr>
    <td colspan="2" valign="top"><dhv:label name="usergroups.noUserGroupsSelected">No User Groups selected</dhv:label></td>
  </tr>
<%}%>
</table>
</body>

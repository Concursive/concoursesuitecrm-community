<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisCatId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, catId, status) {
    thisCatId = catId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuMapping", "down", 0, 0, 170, getHeight("menuMappingTable"));
    }
	  return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  function removeMapping() {
    confirmDelete('ProductCategories.do?command=RemoveMapping&categoryId=<%= ProductCategory.getId() %>&cat2=' + thisCatId + '&moduleId=<%= PermissionCategory.getId() %>');
  }
</script>
<div id="menuMappingContainer" class="menu">
  <div id="menuMappingContent">
    <table id="menuMappingTable" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="removeMapping()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="button.remove">Remove</dhv:label>
        </td>
      </tr>
    </table>
  </div>
</div>

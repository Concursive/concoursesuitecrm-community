<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.*,org.aspcfs.modules.troubletickets.base.*, java.util.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="categoryEditor" class="org.aspcfs.modules.admin.base.CategoryEditor" scope="request"/>
<%
  HashMap selectedCategories = (HashMap) request.getSession().getAttribute("selectedCategories" + categoryEditor.getConstantId());
%>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/confirmDelete.js"></script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do">Admin</a> > 
<a href="Admin.do?command=Config">Configure Modules</a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
<a href="AdminCategories.do?command=Show&moduleId=<%= PermissionCategory.getId() %>&constantId=<%= request.getParameter("constantId") %>">Categories</a> >
Editor
</td>
</tr>
</table>
<%-- End Trails --%>
<script type="text/javascript">
function loadCategories(level) {
  var url = "";
  var categoryId =  document.getElementById('level' + level).options[document.getElementById('level' + level).selectedIndex].value;
  if (level < <%= categoryEditor.getMaxLevels() - 1 %> && categoryId != -1){
      url = "AdminCategories.do?command=CategoryJSList&constantId=<%= request.getParameter("constantId") %>&categoryId=" + categoryId + '&level=' + level;
      window.frames['server_commands'].location.href=url;
      processButtons(level);
  }
}

function loadTopCategories() {
  var url = "AdminCategories.do?command=CategoryJSList&constantId=<%= request.getParameter("constantId") %>&categoryId=-1&level=-1";
  window.frames['server_commands'].location.href=url;
  for (i = 1; i < <%= categoryEditor.getMaxLevels() %>; i++){
    document.getElementById('edit' + i).disabled = true;
  }
}

function editCategory(level){
  var categoryId = -1;
  var tmpLevel = parseInt(level) -1;
  if(tmpLevel > -1){
    if (document.getElementById('level' + tmpLevel).selectedIndex > -1) {
      categoryId =  document.getElementById('level' + tmpLevel).options[document.getElementById('level' + tmpLevel).selectedIndex].value;
    }
  }
  if (tmpLevel == -1 || categoryId > -1) {
    var url = "AdminCategories.do?command=Modify&constantId=<%= request.getParameter("constantId") %>&categoryId=" + categoryId + '&level=' + level;
    popURL(url, 'Modify_Category','540','250','yes','no');
  }
}

function confirmReset(url, msg) {
  if (confirm(msg)) {
    window.location = url;
  }
}

function processButtons(level){
  if(document.getElementById('level' + level).selectedIndex != -1){
    for (i = (parseInt(level) + 1); i < <%= categoryEditor.getMaxLevels() %>; i++){
      document.getElementById('edit' + i).disabled = true;
    }
   }
    document.getElementById('edit' + (parseInt(level) + 1)).disabled = false;
}

function activate(){
 var catList = document.getElementById('level0');
  if(catList.length > 0 && catList.options[0].value != -1){
    confirmForward('AdminCategories.do?command=Activate&constantId=<%= request.getParameter("constantId") %>&moduleId=<%= PermissionCategory.getId() %>');
  }else{
    alert('No entries to activate');
  }
}

</script>
<strong>Categories</strong>
<% String param1 = "moduleId=" + PermissionCategory.getId(); %>
<% String param2 = "constantId=" + request.getParameter("constantId"); %>
<dhv:container name="categories" selected="draft categories" param="<%= param1 + "|" + param2 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack" align="center">
      <table border="0" cellpadding="2" cellspacing="0" class="empty">
        <tr>
          <td align="center">
            Level 1<br>
            <% int value = ((selectedCategories.get(new Integer(0)) != null) ? ((Integer) selectedCategories.get(new Integer(0))).intValue() : -1); 
            categoryEditor.getTopCategoryList().getCatListSelect().setSelectSize(10);
            categoryEditor.getTopCategoryList().setHtmlJsEvent("onChange=\"javascript:loadCategories('0');\"");
            categoryEditor.getTopCategoryList().getCatListSelect().addAttribute("style", "width: 150px");
            %>
            <%= categoryEditor.getTopCategoryList().getHtmlSelect("level0", value) %><br>
            <dhv:permission name="admin-sysconfig-categories-edit"><input type="button" value="Edit" id="edit0" onClick="javascript:editCategory('0');"></dhv:permission>
          </td>
<%-- Variably draw the rest of the editors --%>
<%
   for (int k = 1; k < categoryEditor.getMaxLevels(); k++) {
     TicketCategoryDraftList thisSubList = (TicketCategoryDraftList) request.getAttribute("SubList" + k);
     if (thisSubList == null) {
       thisSubList = new TicketCategoryDraftList();
     }
%>
          <td align="center">
            Level <%= k + 1 %><br>
<%
            value = ((selectedCategories.get(new Integer(k)) != null) ? ((Integer) selectedCategories.get(new Integer(k))).intValue() : -1);
            thisSubList.getCatListSelect().setSelectSize(10);
            thisSubList.getCatListSelect().addAttribute("onChange", "javascript:loadCategories('" + k + "');");
            thisSubList.getCatListSelect().addAttribute("style", "width: 150px");
%>
            <%= thisSubList.getHtmlSelect("level" + k, value) %><br>
            <dhv:permission name="admin-sysconfig-categories-edit"><input type="button" value="Edit" id="edit<%= k %>" onClick="javascript:editCategory('<%= k %>');" disabled></dhv:permission>
          </td>
<%
   }
%>
        </tr>
      </table>
    </td>
  </tr>
</table>
<dhv:permission name="admin-sysconfig-categories-edit"><br>
<input type="button" value="Revert to Active List" onClick="javascript:confirmReset('AdminCategories.do?command=Reset&constantId=<%= request.getParameter("constantId") %>&moduleId=<%= PermissionCategory.getId() %>', 'You will lose all the changes made to the draft. Proceed ?');">
<input type="button" value="Activate Now" onClick="javascript:activate();"></dhv:permission>
<%-- script to enable edit buttons if any categories are selected --%>
<script>
<%
  HashMap categories  = categoryEditor.getCategoryList();
  boolean done = false;
  TicketCategoryDraft thisCat = null;
  for (int k = 0; k < categoryEditor.getMaxLevels(); k++) {
    if (selectedCategories.get(new Integer(k)) != null) {
    thisCat = (TicketCategoryDraft) categories.get((Integer) (selectedCategories.get(new Integer(k))));
    if(thisCat.getParentCode() != 0){
    TicketCategoryDraft parentCat = (TicketCategoryDraft) categories.get(new Integer(thisCat.getParentCode()));
    if(!parentCat.getEnabled()){
    %>
      document.getElementById('edit' + <%= k %>).disabled = true;
    <% }else{ %>
      document.getElementById('edit' + <%= k %>).disabled = false;
    <% } 
     }
    }else{
    if(thisCat != null && !thisCat.getEnabled()){
    %>
      document.getElementById('edit' + <%= k %>).disabled = true;
    <% }else{ %>
      document.getElementById('edit' + <%= k %>).disabled = false;
    <% }
      break;
    }
 }
%>
</script>
<%= showError(request, "actionError") %><iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.*,org.aspcfs.modules.troubletickets.base.*, java.util.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="CategoryEditor" class="org.aspcfs.modules.admin.base.CategoryEditor" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryDraftList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryDraftList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryDraftList" scope="request"/>
<jsp:useBean id="selectedCategories" class="java.util.HashMap" scope="session"/>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/confirmDelete.js"></script>
<a href="Admin.do">Setup</a> > 
<a href="Admin.do?command=Config">Configure Modules</a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
Categories
<br>
<hr color="#BFBFBB" noshade>

<script type="text/javascript">
function loadCategories(level) {
  var url = "";
  var categoryId =  document.getElementById('level' + level).options[document.getElementById('level' + level).selectedIndex].value;
  
  if(level < 3 && categoryId != -1){
      url = "AdminCategories.do?command=CategoryJSList&categoryId=" + categoryId + '&level=' + level;
      window.frames['server_commands'].location.href=url;
      processButtons(level);
  }
}

function loadTopCategories() {
  var url = "AdminCategories.do?command=CategoryJSList&categoryId=-1&level=-1";
  window.frames['server_commands'].location.href=url;
  processButtons('0');
}

function editCategory(level){
  var categoryId = -1;
  var tmpLevel = parseInt(level) -1;
  
  if(tmpLevel > -1){
    categoryId =  document.getElementById('level' + tmpLevel).options[document.getElementById('level' + tmpLevel).selectedIndex].value;
  }
  var url = "AdminCategories.do?command=Modify&categoryId=" + categoryId + '&level=' + level;
  popURL(url, 'Modify_Category','540','250','yes','no');
}

function confirmReset(url, msg) {
  if (confirm(msg)) {
    window.location = url;
  }
}

function processButtons(level){
  if(document.getElementById('level' + level).selectedIndex != -1){
    for(i = (parseInt(level) + 1); i < 4; i++){
      document.getElementById('edit' + i).disabled = true;
    }
   }
    document.getElementById('edit' + (parseInt(level) + 1)).disabled = false;
}

function activate(){
 var catList = document.getElementById('level0');
  if(catList.length > 0 && catList.options[0].value != -1){
    confirmForward('AdminCategories.do?command=Activate&moduleId=<%= PermissionCategory.getId() %>');
  }else{
    alert('No entries to activate');
  }
}

</script>
<%--  This jsp is currently ticket specific but can be extended to make it generic  --%>
<strong>Categories</strong>
<% String param1 = "moduleId=" + PermissionCategory.getId();   %>
<dhv:container name="categories" selected="draft categories" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack" align="center">
      <table border="0" cellpadding="2" cellspacing="0" class="empty">
        <tr>
          <td align="center">
            Level 1<br>
            <% int value = ((selectedCategories.get(new Integer(0)) != null) ? ((Integer) selectedCategories.get(new Integer(0))).intValue() : -1); 
            CategoryEditor.getTopCategoryList().getCatListSelect().setSelectSize(10);
            CategoryEditor.getTopCategoryList().setHtmlJsEvent("onChange=\"javascript:loadCategories('0');\"");
            CategoryEditor.getTopCategoryList().getCatListSelect().addAttribute("style", "width: 150px");
            %>
            
            <%= CategoryEditor.getTopCategoryList().getHtmlSelect("level0", value) %><br>
            <input type="button" value="Edit" id="edit0" onClick="javascript:editCategory('0');">
          </td>
          <td align="center">
            Level 2 <br>
            <% value = ((selectedCategories.get(new Integer(1)) != null) ? ((Integer) selectedCategories.get(new Integer(1))).intValue() : -1);
            SubList1.getCatListSelect().setSelectSize(10);
            SubList1.getCatListSelect().addAttribute("style", "width: 150px");
            %>
            <%= SubList1.getHtmlSelect("level1", value) %><br>
            <input type="button" value="Edit" id="edit1" onClick="javascript:editCategory('1');" disabled>
          </td>
          <td align="center">
            Level 3<br>
            <% value = ((selectedCategories.get(new Integer(2)) != null) ? ((Integer) selectedCategories.get(new Integer(2))).intValue() : -1);
            SubList2.getCatListSelect().setSelectSize(10);
            SubList2.getCatListSelect().addAttribute("style", "width: 150px");
            %>
            <%= SubList2.getHtmlSelect("level2", value) %><br>
            <input type="button" value="Edit" id="edit2" onClick="javascript:editCategory('2');" disabled>
          </td>
          <td align="center">
            Level 4<br>
            <%
              SubList3.getCatListSelect().setSelectSize(10);
              SubList3.getCatListSelect().addAttribute("style", "width: 150px");
            %>
            <%= SubList3.getHtmlSelect("level3", -1) %><br>
            <input type="button" value="Edit" id="edit3" onClick="javascript:editCategory('3');" disabled>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<br>
<input type="button" value="Revert to Active List" onClick="javascript:confirmReset('AdminCategories.do?command=Reset&moduleId=<%= PermissionCategory.getId() %>', 'You will lose all the changes made to the draft. Proceed ?');">
<input type="button" value="Activate Now" onClick="javascript:activate();">
<%-- script to enable edit buttons if any categories are selected --%>
<script>
<%
  HashMap categories  = CategoryEditor.getCategoryList();
  boolean done = false;
  TicketCategoryDraft thisCat = null;
  for (int k = 0; k < 4; k++) {
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


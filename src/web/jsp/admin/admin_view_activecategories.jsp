<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.troubletickets.base.*, java.util.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="TopCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Admin.do">Setup</a> > 
<a href="Admin.do?command=Config">Configure Modules</a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
Categories
</td>
</tr>
</table>
<%-- End Trails --%>
<script type="text/javascript">
function loadCategories(level) {
var categoryId = -1;
  if(level < 3){
    categoryId =  document.getElementById('level' + level).options[document.getElementById('level' + level).selectedIndex].value;
  }
  if(categoryId > -1){
    var url = "AdminCategories.do?command=ActiveCatJSList&categoryId=" + categoryId + '&level=' + level;
    window.frames['server_commands'].location.href=url;
  }
}
</script>
<%--  This jsp is currently ticket specific but can be extended to make it generic  --%>
<strong>Categories</strong>
<% String param1 = "moduleId=" + PermissionCategory.getId();   %>
<dhv:container name="categories" selected="active categories" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack" align="center">
      <table border="0" cellpadding="2" cellspacing="0" class="empty">
        <tr>
          <td align="center">
            Level 1<br>
            <% 
            TopCategoryList.getCatListSelect().setSelectSize(10);
            TopCategoryList.setIncludeDisabled(true);
            TopCategoryList.setHtmlJsEvent("onChange=\"javascript:loadCategories('0');\"");
            TopCategoryList.getCatListSelect().addAttribute("style", "width: 150px");
            %>
            <%= TopCategoryList.getHtmlSelect("level0", -1) %>
          </td>
          <td align="center">
            Level 2 <br>
            <select name="level1" id="level1" size="10" onChange="javascript:loadCategories('1');" style="width: 150px">
              <option value="-1">---------None---------</option>
            </select>
          </td>
          <td align="center">
            Level 3<br>
            <select name="level2" id="level2" size="10" onChange="javascript:loadCategories('2');" style="width: 150px">
              <option value="-1">---------None---------</option>
            </select>
          </td>
          <td align="center">
            Level 4<br>
            <select name="level3" id="level3" size="10" onChange="javascript:loadCategories('3');" style="width: 150px">
              <option value="-1">---------None---------</option>
            </select>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<%= showError(request, "actionError") %><iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>


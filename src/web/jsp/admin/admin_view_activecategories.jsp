<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.troubletickets.base.*, java.util.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="categoryEditor" class="org.aspcfs.modules.admin.base.CategoryEditor" scope="request"/>
<jsp:useBean id="categoryList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
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
var categoryId = -1;
  if(level < <%= categoryEditor.getMaxLevels() - 1 %>){
    categoryId =  document.getElementById('level' + level).options[document.getElementById('level' + level).selectedIndex].value;
  }
  if(categoryId > -1){
    var url = "AdminCategories.do?command=ActiveCatJSList&constantId=<%= request.getParameter("constantId") %>&categoryId=" + categoryId + '&level=' + level;
    window.frames['server_commands'].location.href=url;
  }
}
</script>
<strong>Categories</strong>
<% String param1 = "moduleId=" + PermissionCategory.getId(); %>
<% String param2 = "constantId=" + request.getParameter("constantId"); %>
<dhv:container name="categories" selected="active categories" param="<%= param1 + "|" + param2 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack" align="center">
      <table border="0" cellpadding="2" cellspacing="0" class="empty">
        <tr>
          <td align="center">
            Level 1<br>
            <% 
            categoryList.setSelectSize(10);
            categoryList.setJsEvent("onChange=\"javascript:loadCategories('0');\"");
            categoryList.addAttribute("id", "level0");
            categoryList.addAttribute("style", "width: 150px");
            %>
            <%= categoryList.getHtml("level0", -1) %>
          </td>
<%
  for (int k = 1; k < categoryEditor.getMaxLevels(); k++) {
%>
          <td align="center">
            Level <%= k + 1 %><br>
            <select name="level<%= k %>" id="level<%= k %>" size="10" onChange="javascript:loadCategories('<%= k %>');" style="width: 150px">
              <option value="-1">---------None---------</option>
            </select>
          </td>
<%}%>
        </tr>
      </table>
    </td>
  </tr>
</table>
<%= showError(request, "actionError") %><iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>


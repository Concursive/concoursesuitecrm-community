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
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> > 
<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
<a href="AdminCategories.do?command=Show&moduleId=<%= PermissionCategory.getId() %>&constantId=<%= request.getParameter("constantId") %>"><dhv:label name="product.Categories">Categories</dhv:label></a> >
<dhv:label name="product.editor">Editor</dhv:label>
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
<dhv:container name="categories" selected="active categories" param="<%= param1 + "|" + param2 %>" style="tabs">
  <table border="0" cellpadding="2" cellspacing="0" class="empty">
    <tr>
      <td align="center">
        <dhv:label name="admin.level1">Level 1</dhv:label><br />
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
          <option value="-1"><dhv:label name="admin.none.label">---------None---------</dhv:label></option>
        </select>
      </td>
<%}%>
    </tr>
  </table>
</dhv:container>
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

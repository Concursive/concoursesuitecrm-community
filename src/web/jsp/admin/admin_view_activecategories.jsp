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
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/> 
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="siteId" class="java.lang.String" scope="request"/>
<dhv:evaluate if="<%= !isPopup(request) %>">
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
</dhv:evaluate>
<script type="text/javascript">
function loadCategories(level) {
  var categoryId = -1;
  var siteId = document.getElementById('siteId').value;
  if(level < <%= categoryEditor.getMaxLevels() - 1 %>){
    categoryId =  document.getElementById('level' + level).options[document.getElementById('level' + level).selectedIndex].value;
  }
  if(categoryId > -1){
    var url = "AdminCategories.do?command=ActiveCatJSList&constantId=<%= request.getParameter("constantId") %>&categoryId=" + categoryId + '&level=' + level+'&siteId='+siteId+'<%= isPopup(request)?"&popup=true":"" %>';
    window.frames['server_commands'].location.href=url;
  }
}

function reopen() {
  var siteId = document.getElementById("siteId").value;
  var url = 'AdminCategories.do?command=ViewActive&moduleId=<%= PermissionCategory.getId() %>&constantId=<%= request.getParameter("constantId") %>&siteId='+siteId+'&popup=<%= isPopup(request) %>';
  window.location.href=url;
}
</script>
<strong><dhv:label name="categories.categoriesForSite">Categories for Site</dhv:label></strong>&nbsp;
<% if(User.getUserRecord().getSiteId() == -1) { %>
<% SiteIdList.setJsEvent("onChange=\"javascript:reopen();\" id=\"siteId\""); %>
<%= SiteIdList.getHtmlSelect("siteId", (siteId != null && !"".equals(siteId.trim())?Integer.parseInt(siteId):User.getUserRecord().getSiteId())) %>
<%} else {%>
<input type="hidden" name="siteId" id="siteId" value="<%= (siteId != null && !"".equals(siteId.trim())?Integer.parseInt(siteId):User.getUserRecord().getSiteId()) %>"/>
<%= SiteIdList.getSelectedValue(User.getUserRecord().getSiteId()) %>
<%}%>
<% String param1 = "moduleId=" + PermissionCategory.getId(); %>
<% String param2 = "constantId=" + request.getParameter("constantId"); %>
<% String param3 = "popup=" + (isPopup(request)?"true":"false"); %>
<% String param4 = "siteId="+(siteId != null?siteId:String.valueOf(User.getUserRecord().getSiteId())); %>
<dhv:container name="categories" selected="active categories" param='<%= param1 + "|" + param2 + "|" + param3 +"|" + param4 %>' style="tabs">
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
        <dhv:label name="admin.level" param='<%= "level="+ (k+1) %>'>Level <%= k + 1 %></dhv:label><br>
        <select name="level<%= k %>" id="level<%= k %>" size="10" onChange="javascript:loadCategories('<%= k %>');" style="width: 150px">
          <option value="-1"><dhv:label name="calendar.none.4dashes">---------None---------</dhv:label></option>
        </select>
      </td>
<%}%>
    </tr>
  </table>
</dhv:container>
  <dhv:evaluate if="<%= isPopup(request) %>">
    <br /><input type="button" value="<dhv:label name="button.close">Close</dhv:label>" onClick="javascript:self.close();"/>
  </dhv:evaluate>
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

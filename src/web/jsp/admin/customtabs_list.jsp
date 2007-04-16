<%-- 
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: admin_fields_folder_list.jsp 11310 2005-04-13 20:05:00Z mrajkowski $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page import="java.util.*,java.text.DateFormat,java.text.*,org.aspcfs.modules.admin.base.*,org.aspcfs.modules.website.base.*"%>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="customTabList" class="org.aspcfs.modules.website.base.PageList" scope="request" />
<jsp:useBean id="containerMenuList" class="org.aspcfs.modules.base.ContainerMenuList" scope="request" />
<jsp:useBean id="customTabListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="pageGroup" class="org.aspcfs.modules.website.base.PageGroup" scope="session"/>

<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="customtab_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<!--  LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popProductCategories.js"></SCRIPT> -->
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>

	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td>
				<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
				<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
				<a href="Admin.do?command=ConfigDetails&moduleId=<%=permissionCategory.getId()%>"><%=toHtml(permissionCategory.getCategory())%></a> >
				<dhv:label name="portlet.customtab.admin.CustomTabs">Custom Tabs</dhv:label>
			</td>
		</tr>
	</table>
	<dhv:permission name="admin-sysconfig-customtab-add">
		<a href="AdminCustomTabs.do?command=AddCustomTab&moduleId=<%=permissionCategory.getId()%>&previousId=<%=customTabList.size() > 0 ? ((Page) customTabList.get(customTabList.size() - 1)).getId() : -1%>&pageGroupId=<%=pageGroup.getId() %>">Add	Custom Tab</a>
	</dhv:permission>
<form name="customTabs" action="AdminCustomTabs.do?command=List&moduleId=<%=permissionCategory.getId()%>" method="post">	
	<dhv:formMessage showSpace="false" />	
	<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="customTabListInfo"/>
	<table cellpadding="3" cellspacing="0" width="100%" class="pagedList">
		<tr>
			<th align="center">
				&nbsp;
			</th>
			<th width="30%">
				<strong><dhv:label name="portlet.customtab.admin.CustomTabName">Custom Tab Name</dhv:label>
				</strong>
			</th>
			<th width="50%">
				<strong><dhv:label name="portlet.customtab.admin.CustomTabNotes">Custom Tab Notes</dhv:label>
				</strong>
			</th>
			<th width="25%">
				<strong><dhv:label name="portlet.customtab.admin.ContainerMenu">Container Menu</dhv:label>
				</strong>
			</th>
			<th nowrap><strong><dhv:label name="portlet.customtab.admin.Enabled">Enabled</dhv:label></strong></th>
		</tr>
	  <%	  	
	  	int rowid = 0;
	  	int count = 0;
	    Iterator j = customTabList.iterator();
	    if(!j.hasNext()){
	    	%> <tr class="row2"><td colspan="7"> <dhv:label name="product.noItemsMsg">No items to display.</dhv:label></td></tr> <%
	    }
	    while (j.hasNext()) {
	      Page thisCustomTab = (Page) j.next();
	      rowid = (rowid != 1 ? 1 : 2);
	      count++;
	  %>
		<tr  class="row<%= rowid %>">
		    <td align="center" nowrap>
		      <a href="javascript:displayMenu('select<%= count %>', 'menuCustomTab', <%= thisCustomTab.getId() %>, <%=thisCustomTab.getPreviousPageId() %>, <%=thisCustomTab.getNextPageId() %>,<%= thisCustomTab.getLinkContainerId() %>);"
		        onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuCustomTab');">
		        <img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
		    </td>		    
		    <td>
				<a href="javascript:popURL('AdminCustomTabs.do?command=ConfigureCustomTab&customtabId=<%= thisCustomTab.getId() %>&moduleId=<%= permissionCategory.getId() %>&containerId=<%=thisCustomTab.getLinkContainerId()%>&popup=true','CustomtabConfigure',700,500,'yes','yes')"><%= toHtml(thisCustomTab.getName()) %></a>
		    </td>
		    <td>
		    	<%= toHtml(thisCustomTab.getNotes()) %>
		    </td>
		    <td>
		    	<%= toHtml((String) containerMenuList.getValueFromId(thisCustomTab.getLinkContainerId())) %>
		    </td>
		    <td>
		      <% if (thisCustomTab.getEnabled()) { %>
		          <dhv:label name="account.yes">Yes</dhv:label>
		      <% } else { %>
		          <dhv:label name="account.no">No</dhv:label>
		      <% } %>
		    </td>		    
		</tr>
		<%} %>
  	</table>  	
<br>  	
</form>
<dhv:pagedListControl object="customTabListInfo"/>


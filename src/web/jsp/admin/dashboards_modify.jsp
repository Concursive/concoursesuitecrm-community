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
<%@ page import="java.util.*,java.text.DateFormat"%>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="dashboard" class="org.aspcfs.modules.website.base.Page"
	scope="request" />

<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>
<body onLoad="javascript:document.Dashboard.name.focus();">
	<table class="trails" cellspacing="0">
		<tr>
			<td>
				<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
				<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
				<a href="Admin.do?command=ConfigDetails&moduleId=<%=permissionCategory.getId()%>"><%=toHtml(permissionCategory.getCategory())%></a> >
				<a href="AdminDashboards.do?command=List&moduleId=<%=permissionCategory.getId()%>">Dashboards</a> >
				<dhv:evaluate if="<%= dashboard.getId() > -1 %>">
					<dhv:label name="portlet.customtab.admin.ModifyDashboard">Modify Dashboard</dhv:label>
				</dhv:evaluate>
				<dhv:evaluate if="<%= dashboard.getId() == -1 %>">
					<dhv:label name="portlet.customtab.admin.AddDashboard">Add Dashboard</dhv:label>
				</dhv:evaluate>
			</td>
		</tr>
	</table>

<dhv:formMessage showSpace="false" />
<form name="Dashboard"
	action="AdminDashboards.do?command=Save&moduleId=<%=permissionCategory.getId()%>&auto-populate=true"
	method="post">
	<table cellpadding="3" cellspacing="0" width="100%" class="pagedList">
		<tr>
			<th colspan="2" align="left">
				&nbsp;
				<strong>						
						<dhv:evaluate if="<%= dashboard.getId() > -1 %>">
							<dhv:label 	name="portlet.customtab.admin.ModifyDashboard">Modify Dashboard</dhv:label>
						</dhv:evaluate>
						<dhv:evaluate if="<%= dashboard.getId() == -1 %>">
							<dhv:label 	name="portlet.customtab.admin.AddDashboard">Add Dashboard</dhv:label>
						</dhv:evaluate>

			</th>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel">
				<dhv:label name="">Name</dhv:label>
			</td>
			<td>
				<table border="0" cellpadding="0" cellspacing="0" class="empty">
					<tr>
						<td valign="top">
							<input type="text" size="35" maxlength="300" name="name"
								value="<%=toHtmlValue(dashboard.getName())%>">
							<font color="red">*</font>
						</td>
						<td valign="top" nowrap>
							&nbsp;
							<%=showAttribute(request, "nameError")%>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr class="containerBody">
			<td class="formLabel">
				<dhv:label name="product.enabled">Enabled</dhv:label>
			</td>
			<td>
				<input type="checkbox" name="enabled" value="true"
					<%=dashboard.getEnabled() ? "checked" : ""%> />
				(<dhv:label name="portlet.dashboard.admin.IfVisible">indicates if the Dashboard is visible</dhv:label>)
			</td>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel">
				<dhv:label name="">Notes</dhv:label>
			</td>
			<td>
				<TEXTAREA NAME="notes" ROWS="3" COLS="50"><%=toString(dashboard.getNotes())%></TEXTAREA>
			</td>
		</tr>
	</table>
  <dhv:evaluate if="<%= dashboard.getId() == -1 %>">
	  <input type="submit" value="<dhv:label name="button.save">Save</dhv:label>" name="Save"/>
  </dhv:evaluate>
  <dhv:evaluate if="<%= dashboard.getId() > -1 %>">
	  <input type="submit" value="<dhv:label name="button.update">Update</dhv:label>" name="Save"/>
  </dhv:evaluate>
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AdminDashboards.do?command=List&moduleId=<%=permissionCategory.getId()%>';"/>
  <input type="hidden" name="id"  value="<%=dashboard.getId()%>"/>		  
  <input type="hidden" name="position"  value="<%=dashboard.getPosition()%>"/>
  <input type="hidden" name="previousPageId"  value="<%=request.getParameter("previousId")%>"/>
  <input type="hidden" name="nextPageId"  value="<%=request.getParameter("nextId") %>"/>
  <input type="hidden" name="activePageVersionId"  value="<%=dashboard.getActivePageVersionId() %>"/>
  <input type="hidden" name="constructionPageVersionId"  value="<%=dashboard.getConstructionPageVersionId() %>"/>
</form>
</body>


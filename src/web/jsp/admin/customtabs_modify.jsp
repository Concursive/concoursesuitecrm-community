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
<jsp:useBean id="customtab" class="org.aspcfs.modules.website.base.Page" scope="request" />
<jsp:useBean id="containerMenuList" class="org.aspcfs.modules.base.ContainerMenuList" scope="request" />
<jsp:useBean id="previousLinkContainerId" class="java.lang.String" scope="request" />

<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>
<body onLoad="javascript:document.CustomTab.name.focus();">
	<table class="trails" cellspacing="0">
		<tr>
			<td>
				<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
				<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
				<a href="Admin.do?command=ConfigDetails&moduleId=<%=permissionCategory.getId()%>"><%=toHtml(permissionCategory.getCategory())%></a> >
				<a href="AdminCustomTabs.do?command=List&moduleId=<%=permissionCategory.getId()%>">Custom Tabs</a> >
				<dhv:evaluate if="<%= customtab.getId() > -1 %>">
					<dhv:label name="portlet.customtab.admin.ModifyCustomTab">Modify Custom Tab</dhv:label>
				</dhv:evaluate>
				<dhv:evaluate if="<%= customtab.getId() == -1 %>">
					<dhv:label name="portlet.customtab.admin.AddCustomTab">Add Custom Tab</dhv:label>
				</dhv:evaluate>
			</td>
		</tr>
	</table>
<dhv:formMessage showSpace="false" />
<form name="CustomTab"
	action="AdminCustomTabs.do?command=Save&moduleId=<%=permissionCategory.getId()%>&auto-populate=true"
	method="post">
	<table cellpadding="3" cellspacing="0" width="100%" class="pagedList">
		<tr>
			<th colspan="2" align="left">
				&nbsp;
				<strong>
						<dhv:evaluate if="<%= customtab.getId() > -1 %>">
							<dhv:label 	name="portlet.customtab.admin.ModifyCustomTab">Modify Custom Tab</dhv:label>
						</dhv:evaluate>
						<dhv:evaluate if="<%= customtab.getId() == -1 %>">
							<dhv:label 	name="portlet.customtab.admin.AddCustomTab">Add Custom Tab</dhv:label>
						</dhv:evaluate>
				</strong>
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
							<input type="text" size="35" maxlength="300" name="name" value="<%=toHtmlValue(customtab.getName())%>">
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
				<input type="checkbox" name="enabled" value="true"<%=customtab.getEnabled() ? "checked" : ""%> />
				(<dhv:label name="portlet.customtab.admin.IfVisible">indicates if the Custom Tab is visible</dhv:label>)
			</td>
		</tr>
		<tr class="containerBody">
			<td class="formLabel">
				<dhv:label name="portlet.customtab.admin.ContainerMenu">Container Menu</dhv:label>
			</td>
			<td>
				<%= containerMenuList.getHtmlSelect("linkContainerId", customtab.getLinkContainerId())%>
			</td>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel">
				<dhv:label name="">Notes</dhv:label>
			</td>
			<td>
				<TEXTAREA NAME="notes" ROWS="3" COLS="50"><%=toString(customtab.getNotes())%></TEXTAREA>
			</td>
		</tr>
	</table>
<dhv:evaluate if="<%= customtab.getId() > -1 %>">
  <input type="submit" value="<dhv:label name="button.update">Update</dhv:label>" name="Save"/>
</dhv:evaluate><dhv:evaluate if="<%= customtab.getId() == -1 %>">
  <input type="submit" value="<dhv:label name="button.save">Save</dhv:label>" name="Save"/>
</dhv:evaluate>
	<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AdminCustomTabs.do?command=List&moduleId=<%=permissionCategory.getId()%>';"/>
  <input type="hidden" name="id"  value="<%=customtab.getId()%>"/>		  
  <input type="hidden" name="position"  value="<%=customtab.getPosition()%>"/>
  <input type="hidden" name="previousPageId"  value="<%=request.getParameter("previousId")%>"/>
  <input type="hidden" name="nextPageId"  value="<%=request.getParameter("nextId") %>"/>
  <input type="hidden" name="activePageVersionId"  value="<%=customtab.getActivePageVersionId() %>"/>
  <input type="hidden" name="previousLinkContainerId"  value="<%=previousLinkContainerId %>"/>
  <input type="hidden" name="constructionPageVersionId"  value="<%=customtab.getConstructionPageVersionId() %>"/>
 
</form>


<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.admin.base.*" %>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="CustomField" class="org.aspcfs.modules.base.CustomField" scope="request"/>
<jsp:useBean id="ModId" class="java.lang.String" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<body<% if (CustomField.getName() == null) { %> onLoad="document.forms[0].name.focus();"<%}%>>
<form name="details" action="AdminFields.do?command=AddField&modId=<%= ModId %>&catId=<%= Category.getId() %>&grpId=<%= (String)request.getParameter("grpId") %>&auto-populate=true" method="post">
<a href="Admin.do">Admin</a> >
<a href="Admin.do?command=Config">Configure Modules</a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%=ModId%>"><%= PermissionCategory.getCategory() %></a> >
<a href="AdminFieldsFolder.do?command=ListFolders&modId=<%= ModId %>">Custom Folders</a> > 
<a href="AdminFieldsGroup.do?command=ListGroups&modId=<%= ModId %>&catId=<%= Category.getId() %>">Folder</a> >
New Field<br />
<dhv:formMessage />
<br />
<%
  CategoryList.setJsEvent("ONCHANGE=\"javascript:document.forms[0].submit();\"");
%>
<strong>Module:</strong> <%= toHtml(PermissionCategory.getCategory()) %><br />
<strong>Folder:</strong> <%= toHtml(Category.getName()) %><br />
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      Add a Custom Field
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Field Label
    </td>
    <td>
      <input type="text" name="name" maxlength="200" value="<%= toHtmlValue(CustomField.getName()) %>"><font color="red">*</font>
      <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Field Type
    </td>
    <td>
      <%= CustomField.getHtmlSelect("type", "onChange=\"javascript:document.forms[0].submit();\"") %><font color="red">*</font>
      <%= showAttribute(request, "typeError") %>
    </td>
  </tr>
<%        
  if (CustomField.getLengthRequired()) {
%>        
  <tr class="containerBody">
    <td class="formLabel">
      Field Length
    </td>
    <td>
      <input type="text" name="maxLength" maxlength="3" value="<%= CustomField.getParameter("maxLength") %>" size="5"><font color="red">*</font>
      <%= showAttribute(request, "maxLengthError") %>
    </td>
  </tr>
<%      
  }
  if (CustomField.getLookupListRequired()) {
%>        
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Lookup List
    </td>
    <td>
      Enter values to appear in the selection list, each on a separate line.<br>
      <textarea cols="50" rows="8" name="lookupListText" wrap="SOFT"><%= toString(CustomField.getLookupList()) %></textarea>
      <%= showAttribute(request, "lookupListError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Display Type
    </td>
    <td>
      Drop-down box
      List box
      Multiple selection list box
    </td>
  </tr>
<%      
  }
%>
  <tr class="containerBody">
    <td class="formLabel">
      Additional Text to Display
    </td>
    <td>
      <input type="text" name="additionalText" maxlength="255" width="50" value="<%= toHtmlValue(CustomField.getAdditionalText()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Required at Data Entry
    </td>
    <td>
      <input type="checkbox" value="ON" name="required" <%= CustomField.getRequired()?"checked":"" %>>
    </td>
  </tr>
</table>
&nbsp;<br>
<input type="hidden" name="groupId" value="<%= (String)request.getParameter("grpId") %>">
<input type="submit" value="Save" onClick="javascript:this.form.action='AdminFields.do?command=InsertField&modId=<%= ModId %>&catId=<%= Category.getId() %>&grpId=<%= (String)request.getParameter("grpId") %>&auto-populate=true'">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='AdminFieldsGroup.do?command=ListGroups&modId=<%= ModId %>&catId=<%= Category.getId() %>'">
</form>
</body>

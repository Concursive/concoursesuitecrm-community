<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Category" class="com.darkhorseventures.cfsbase.CustomFieldCategory" scope="request"/>
<jsp:useBean id="ModId" class="java.lang.String" scope="request"/>
<jsp:useBean id="PermissionCategory" class="com.darkhorseventures.cfsbase.PermissionCategory" scope="request"/>
<%@ include file="initPage.jsp" %>
<body onLoad="document.forms[0].name.focus();">
<form name="details" action="AdminFieldsFolder.do?command=UpdateFolder&modId=<%= ModId %>&catId=<%= Category.getId() %>&auto-populate=true" method="post">
<a href="Admin.do">Setup</a> >
<a href="Admin.do?command=Config">Configure Modules</a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%=ModId%>">Configuration Options</a> >
<a href="AdminFieldsFolder.do?command=ListFolders&modId=<%= ModId %>">Custom Folders</a> > 
Existing Folder<br>
<hr color="#BFBFBB" noshade>

<% if (request.getAttribute("actionError") != null) { %>
  <%= showError(request, "actionError") %>
<%}%>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong>Module: <%=PermissionCategory.getCategory()%></strong>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      
      <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr class="title">
          <td colspan="2">
            <strong>Update an Existing Folder</strong>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Folder Name
          </td>
          <td>
            <input type="text" name="name" maxlength="200" value="<%= toHtmlValue(Category.getName()) %>"><font color="red">*</font>
            <%= showAttribute(request, "nameError") %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Records
          </td>
          <td>
            <input type="checkbox" value="ON" name="allowMultipleRecords" <%= Category.getAllowMultipleRecords()?"checked":"" %>>folder can have multiple records
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Read Only
          </td>
          <td>
            <input type="checkbox" value="ON" name="readOnly" <%= Category.getReadOnly()?"checked":"" %>>folder is read only by all users
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Enabled
          </td>
          <td>
            <input type="checkbox" value="ON" name="enabled" <%= Category.getEnabled()?"checked":"" %>>folder is visible by users
          </td>
        </tr>
      </table>
      &nbsp;<br>
      <input type="hidden" name="moduleId" value="<%= ModId %>">
      <input type="hidden" name="categoryId" value="<%= Category.getId() %>">
      <input type="submit" value="Update">
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='/AdminFieldsFolder.do?command=ListFolders&modId=<%= ModId %>'">
    </td>
  </tr>
</table>
</form>
</body>

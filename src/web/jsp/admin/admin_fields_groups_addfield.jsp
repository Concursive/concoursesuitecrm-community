<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="CategoryList" class="com.darkhorseventures.cfsbase.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="com.darkhorseventures.cfsbase.CustomFieldCategory" scope="request"/>
<jsp:useBean id="CustomField" class="com.darkhorseventures.cfsbase.CustomField" scope="request"/>
<jsp:useBean id="ModId" class="java.lang.String" scope="request"/>
<jsp:useBean id="PermissionCategory" class="com.darkhorseventures.cfsbase.PermissionCategory" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></script>
<body<% if (CustomField.getName() == null) { %> onLoad="document.forms[0].name.focus();"<%}%>>
<form name="details" action="/AdminFields.do?command=AddField&modId=<%= ModId %>&catId=<%= Category.getId() %>&grpId=<%= (String)request.getParameter("grpId") %>&auto-populate=true" method="post">
<a href="/Admin.do">Setup</a> >
<a href="/Admin.do?command=Config">System Configuration</a> >
<a href="/Admin.do?command=ConfigDetails&moduleId=<%=ModId%>">Configuration Options</a> >
<a href="/AdminFieldsFolder.do?command=ListFolders&modId=<%= ModId %>">Custom Folders</a> > 
<a href="/AdminFieldsGroup.do?command=ListGroups&modId=<%= ModId %>&catId=<%= Category.getId() %>">Folder</a> >
New Field<br>
&nbsp;<br>
<%
  CategoryList.setJsEvent("ONCHANGE=\"javascript:document.forms[0].submit();\"");
%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong>Module: <%=PermissionCategory.getCategory()%></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <strong>Folder: <%= toHtml(Category.getName()) %></strong>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <a href="/AdminFieldsGroup.do?command=ListGroups&modId=<%= ModId %>&catId=<%= Category.getId() %>">Back to Folder</a><br>
      &nbsp;<br>
      
      <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr class="title">
          <td colspan="2">
            Add a Custom Field
          </td>
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
            <textarea cols="50" rows="8" name="lookupList" wrap="SOFT"><%= CustomField.getLookupList() %></textarea>
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
      <input type="submit" value="Save" onClick="javascript:this.form.action='/AdminFields.do?command=InsertField&modId=<%= ModId %>&catId=<%= Category.getId() %>&grpId=<%= (String)request.getParameter("grpId") %>&auto-populate=true'">
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='/AdminFieldsGroup.do?command=ListGroups&modId=<%= ModId %>&catId=<%= Category.getId() %>'">
    </td>
  </tr>
</table>
</form>
</body>

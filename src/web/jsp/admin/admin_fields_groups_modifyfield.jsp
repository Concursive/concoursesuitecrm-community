<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.webutils.LookupList" %>
<jsp:useBean id="CategoryList" class="com.darkhorseventures.cfsbase.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="com.darkhorseventures.cfsbase.CustomFieldCategory" scope="request"/>
<jsp:useBean id="CustomField" class="com.darkhorseventures.cfsbase.CustomField" scope="request"/>
<jsp:useBean id="ModId" class="java.lang.String" scope="request"/>
<jsp:useBean id="PermissionCategory" class="com.darkhorseventures.cfsbase.PermissionCategory" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></script>
<script language="JavaScript" type="text/javascript" src="/javascript/editListForm.js"></script>
<script language="JavaScript" type="text/javascript">
  function doCheck() {
    if (document.modifyList.dosubmit.value == "false") {
      return true;
    }
    var test = document.modifyList.selectedList;
    if (test != null) {
      return selectAllOptions(document.modifyList.selectedList);
    }
  }
</script>
<body<% if (CustomField.getName() == null) { %> onLoad="document.forms[0].name.focus();"<%}%>>
<form name="modifyList" action="/AdminFields.do?command=ModifyField&modId=<%= ModId %>&catId=<%= Category.getId() %>&grpId=<%= (String)request.getParameter("grpId") %>&auto-populate=true" onSubmit="return doCheck();" method="post">
<a href="/Admin.do">Setup</a> >
<a href="/Admin.do?command=Config">System Configuration</a> >
<a href="/Admin.do?command=ConfigDetails&moduleId=<%=ModId%>">Configuration Options</a> >
<a href="/AdminFieldsFolder.do?command=ListFolders&modId=<%= ModId %>">Custom Folders</a> > 
<a href="/AdminFieldsGroup.do?command=ListGroups&modId=<%= ModId %>&catId=<%= Category.getId() %>">Folder</a> >
Existing Field<br>
<hr color="#BFBFBB" noshade>
<% if (request.getAttribute("actionError") != null) { %>
<%= showError(request, "actionError") %>
<%}%>
<%
  CategoryList.setJsEvent("ONCHANGE=\"javascript:this.form.dosubmit.value='false';document.forms[0].submit();\"");
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
          LookupList SelectedList = (LookupList)CustomField.getElementData();
          SelectedList.setSelectSize(8);
          SelectedList.setMultiple(true);
%>
        <tr class="containerBody">
          <td valign="top" class="formLabel">
            Lookup List
          </td>
          <td>
            <%= showAttribute(request, "lookupListError") %>
            <table cellpadding="4" cellspacing="0" border="0" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
              <tr>
                <td width=50%>
                  <table width=100% cellspacing=0 cellpadding=2 border=0>
                    <tr><td valign=center>
                      New Option
                    </td></tr>
                    <tr><td valign=center>
                      <input type="text" name="newValue" value="" size=25  maxlength=125>
                    </td></tr>
                    <tr><td valign=center>
                      <input type="button" value="Add >" onclick="javascript:addValues()">
                    </td></tr>
                  </table>
                </td>
                <td width=25>
                  <table width=100% cellspacing=0 cellpadding=2 border=0>
                    <tr><td valign=center>
                      <input type=button value="Up" onclick="javascript:moveOptionUp(document.modifyList.selectedList)">
                    </td></tr>
                    <tr><td valign=center>
                      <input type=button value="Down" onclick="javascript:moveOptionDown(document.modifyList.selectedList)">
                    </td></tr>
                    <tr><td valign=center>
                      <input type="button" value="Remove" onclick="javascript:removeValues()">
                    </td></tr>
                    <tr><td valign=center>
                      <input type="button" value="Sort" onclick="javascript:sortSelect(document.modifyList.selectedList)">
                    </td></tr>
                  </table>
                </td>
                <td width=50%><%= SelectedList.getHtmlSelect("selectedList",0) %></td>
                <input type=hidden name="selectNames" value="">
              </tr>
            </table>
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
      <input type="hidden" name="id" value="<%= (String)request.getParameter("id") %>">
      <input type="hidden" name="groupId" value="<%= (String)request.getParameter("grpId") %>">
      <input type="hidden" name="dosubmit" value="true">
      <input type="submit" value="Update" onClick="javascript:this.form.action='/AdminFields.do?command=UpdateField&modId=<%= ModId %>&catId=<%= Category.getId() %>&grpId=<%= (String)request.getParameter("grpId") %>&auto-populate=true';this.form.dosubmit.value='true';">
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='/AdminFieldsGroup.do?command=ListGroups&modId=<%= ModId %>&catId=<%= Category.getId() %>';this.form.dosubmit.value='false';">
    </td>
  </tr>
</table>
</form>
</body>

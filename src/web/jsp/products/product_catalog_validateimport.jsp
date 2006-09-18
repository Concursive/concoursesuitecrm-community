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
  - Version: $Id: product_catalog_validateimport.jsp 13561 2005-12-09 21:55:24 +0000 (Пт, 09 дек 2005) partha $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.StringUtils,org.aspcfs.utils.web.*,org.aspcfs.apps.transfer.reader.mapreader.*" %>
<jsp:useBean id="ImportValidator" class="org.aspcfs.modules.products.base.ProductImportValidate" scope="request"/>
<jsp:useBean id="ImportDetails" class="org.aspcfs.modules.products.base.ProductCatalogImport" scope="request"/>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"></jsp:useBean>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="AccessTypeList" class="org.aspcfs.modules.admin.base.AccessTypeList" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="ZipFileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<script language="JavaScript" type="text/javascript" src="javascript/spanDisplay.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/tasks.js"></script>
<script language="JavaScript">
  fields = new Array();
  function checkPrompt(field){
    thisField = document.getElementById(field);

   var groupSelector = document.getElementById(field + 'groupId');
   groupSelector.options.selectedIndex = 0;
   if(thisField.value != null && ((thisField.value.indexOf('productCatalogPricing.') > -1) || (thisField.value.indexOf('productCategory.') > -1))){

      /* get the next available group id if it is a new set of phone number or address*/
      if(thisField.value == 'productCatalogPricing.priceAmount' || thisField.value == 'productCategory.name'){
      var dependency = 'productCatalogPricing.priceAmount';
        if(thisField.value == 'productCategory.name'){
          dependency = 'productCategory.name';
        }
        availableGroupIds = new Array();
        for(i = 1; i < 11; i++){
          availableGroupIds[i -1] = i;
        }

        for(i = 0; i < fields.length; i++){
          tmpField = fields[i];
          var mappedValue = document.getElementById(tmpField).value;
          if(mappedValue != null && mappedValue == dependency && field != tmpField){
            availableGroupIds[document.getElementById(tmpField + 'groupId').value - 1] = -1;
          }
        }

        for(i = 0; i < 10; i++){
          if(availableGroupIds[i] != -1){
            groupSelector.options.selectedIndex = i;
            break;
          }
        }
       }
      /* change the drop down entries */
    }
  }
</script>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
  <td>
    <a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
    <a href="ProductCatalogImports.do?command=View&moduleId=<%=permissionCategory.getId()%>"><dhv:label name="products.viewImports">View Imports</dhv:label></a> >
    <% if(!"list".equals(request.getParameter("return"))){ %>
    <a href="ProductCatalogImports.do?command=Details&moduleId=<%=permissionCategory.getId()%>&importId=<%= ImportDetails.getId() %>"><dhv:label name="products.importDetails">Import Details</dhv:label></a> >
    <% } %>
    <dhv:label name="products.processImport">Process Import</dhv:label>
  </td>
</tr>
</table>

<%-- End Trails --%>
<form method="post" name="inputForm" action="ProductCatalogImports.do?command=Validate&moduleId=<%=permissionCategory.getId()%>">
<input type="submit" value="<dhv:label name="global.button.ProcessNow">Process Now</dhv:label>">
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ProductCatalogImports.do?command=View&moduleId=<%=permissionCategory.getId()%>';this.form.dosubmit.value='false';">
<br />
<%= showError(request, "actionError") %>
<%-- General Import Properties --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="products.importProperties">Import Properties</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="product.name">Name</dhv:label>
  </td>
  <td class="containerBody">
    <%= toString(ImportDetails.getName()) %>
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="products.file">File</dhv:label>
  </td>
  <td class="containerBody">
    <%= FileItem.getClientFilename() %>
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="products.fileSize">File Size</dhv:label>
  </td>
  <td class="containerBody">
    <%= FileItem.getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
  </td>
  </tr>
  <%if(ZipFileItem!=null && ZipFileItem.getId()!=-1){%>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="products.images">Images</dhv:label>
  </td>
  <td class="containerBody">
    <%= ZipFileItem.getClientFilename() %>
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="products.imageFileSize">Images File Size</dhv:label>
  </td>
  <td class="containerBody">
    <%= ZipFileItem.getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
  </td>
  </tr>
<%}%>

  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="products.entered">Entered</dhv:label>
  </td>
  <td class="containerBody">
    <dhv:username id="<%= ImportDetails.getEnteredBy() %>"/>
    <zeroio:tz timestamp="<%= ImportDetails.getEntered()  %>" />
  </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="products.modified">Modified</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= ImportDetails.getModifiedBy() %>"/>
      <zeroio:tz timestamp="<%= ImportDetails.getModified()  %>" />
    </td>
  </tr>
</table><br>
<%-- Preview of File to be imported --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="products.first5linesImportfile.text">First 5 lines of the import file</dhv:label></strong>
    </th>
  </tr>
  <%
     PropertyMap propertyMap = ImportValidator.getPropertyMap();
     Iterator  i = ImportValidator.getSampleRecords().iterator();
     int lineCount = 1;
     while(i.hasNext()){
     String line = (String) i.next();
  %>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="products.line">Line</dhv:label> <%= lineCount++ %>
  </td>
  <td class="containerBody">
    <input type="text" value="<%= toHtml(line) %>" READONLY size="70">
  </td>
  </tr>
  <% } %>
</table><br>
<%-- Errors/Warnings --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <strong><dhv:label name="products.generalErrorsWarnings">General Errors/Warnings</dhv:label></strong>
    </th>
  </tr>
  <%
  ArrayList generalErrors = ImportValidator.getGeneralErrors();
  if(generalErrors != null){
  Iterator ge =  generalErrors.iterator();
   while(ge.hasNext()){
  %>
  <tr>
    <td class="containerBody" align="left">
       <img src="images/error.gif" border="0" align="absmiddle"/>&nbsp;<%= toString((String) ge.next()) %>
    </td>
  </tr>
  <% }
   }else{
  %>
  <tr>
    <td class="containerBody" align="left">
      <dhv:label name="products.noErrorsWarningsFound.text">No errors/warnings found.</dhv:label>
    </td>
  </tr>
 <% } %>
</table><br>
<%-- Field Mappings --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="3">
      <strong><dhv:label name="products.fieldMappings">Field Mappings</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <th>
      <strong><dhv:label name="products.field">Field</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="products.mapsTo">Maps to...</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="products.errorsWarnings">Errors/Warnings</dhv:label></strong>
    </th>
  </tr>
  <%
  HashMap fieldErrors = ImportValidator.getFieldErrors();
  LinkedHashMap fieldMappings = ImportValidator.getFieldMappings();
  Iterator f =  fieldMappings.keySet().iterator();
   while(f.hasNext()){
   String field = (String) f.next();
   Property mappedProperty = (Property) fieldMappings.get(field);
  %>
  <script>fields[fields.length] = '<%= field %>';</script>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
     <%= toString(field) %>
  </td>
  <td  width="100" class="containerBody" nowrap>
    <table class="empty">
        <tr>
          <td>
            <%= propertyMap.getHtmlSelect(field, mappedProperty) %>
          </td>
          <td nowrap>
          <%-- Grouping of dependencies --%>
            <%
              String groupType = null;
              if(mappedProperty != null){
                //Set the group type for drop down display only if property has groups
                String id = mappedProperty.getDependencyName();
                if(propertyMap.hasGroups(id)){
                  DependencyMap dMap = propertyMap.getDependencyMap(id);
                  if(dMap != null){
                    groupType = dMap.getDisplayName();
                  }
                }
              }
            %>
            <span name="<%= field %>groupIdspan" ID="<%= field %>groupIdspan" style="<%= (mappedProperty != null && mappedProperty.getGroupId() > 0 && propertyMap.hasGroups(mappedProperty.getDependencyName())) ? "" : " display:none" %>">
            is part of <%= propertyMap.getGroupHtmlSelect(field + "groupId", mappedProperty != null ? mappedProperty.getGroupId() : -1, groupType) %>
            </span>

         </td>
        </tr>
    </table>
  </td>
  <td class="containerBody">
    <% if(fieldErrors != null && fieldErrors.containsKey(field)){ %>
    <img src="images/error.gif" border="0" align="absmiddle" />
    <%= (String) fieldErrors.get(field) %>
    <% }else{ %>
    &nbsp;
    <% } %>
  </td>
  </tr>
  <% } %>
</table><br />
<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="importId" value="<%= ImportDetails.getId() %>">
<input type="submit" value="<dhv:label name="global.button.ProcessNow">Process Now</dhv:label>" />
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ProductCatalogImports.do?command=View&moduleId=<%=permissionCategory.getId()%>';this.form.dosubmit.value='false';" />
</form>

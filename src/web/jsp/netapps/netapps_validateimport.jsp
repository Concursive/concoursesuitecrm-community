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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.StringUtils,org.aspcfs.utils.web.*,org.aspcfs.apps.transfer.reader.mapreader.*" %>
<jsp:useBean id="ImportValidator" class="org.aspcfs.modules.netapps.base.ContractExpirationImportValidate" scope="request"/>
<jsp:useBean id="ImportDetails" class="org.aspcfs.modules.netapps.base.ContractExpirationImport" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="AccessTypeList" class="org.aspcfs.modules.admin.base.AccessTypeList" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/spanDisplay.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/tasks.js"></script>
<script language="JavaScript">
  fields = new Array();
  function checkPrompt(field){ }
</script>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
  <td>
    <a href="NetworkApplications.do">NetApp</a> >
    <% if("list".equals(request.getParameter("return"))){ %>
    <a href="NetworkApplicationsImports.do?command=View">View Imports</a> >
    <% }else{ %>
    <a href="NetworkApplicationsImports.do?command=View">View Imports</a> >
    <a href="NetworkApplicationsImports.do?command=Details&importId=<%= ImportDetails.getId() %>">Import Details</a> >
    <% } %>
    Process Import
  </td>
</tr>
</table>

<%-- End Trails --%>
<form method="post" name="inputForm" action="NetworkApplicationsImports.do?command=Validate">
<input type="submit" value="Process">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='NetworkApplicationsImports.do?command=View';this.form.dosubmit.value='false';">
<br />
<dhv:formMessage />
<%-- General Import Properties --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Import Properties</strong>
    </th>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    Name
  </td>
  <td class="containerBody">
    <%= toString(ImportDetails.getName()) %>
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    File
  </td>
  <td class="containerBody">
    <%= FileItem.getClientFilename() %>
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    File Size
  </td>
  <td class="containerBody">
    <%= FileItem.getRelativeSize() %> k&nbsp;
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    Entered
  </td>
  <td class="containerBody">
    <dhv:username id="<%= ImportDetails.getEnteredBy() %>"/>
    <zeroio:tz timestamp="<%= ImportDetails.getEntered()  %>" />
  </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Modified
    </td>
    <td>
      <dhv:username id="<%= ImportDetails.getModifiedBy() %>"/>
      <zeroio:tz timestamp="<%= ImportDetails.getModified()  %>" />
    </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    Access Type
  </td>
  <td class="containerBody">
    <%= AccessTypeList.getHtmlSelect("accessType", ImportDetails.getAccessTypeId()) %>
  </td>
  </tr>
</table><br>
<%-- Preview of File to be imported --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>First 5 lines of the import file</strong>
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
    Line <%= lineCount++ %> 
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
      <strong>General Errors/Warnings</strong>
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
       No errors/warnings found.
    </td>
  </tr>
 <% } %>
</table><br>
<%-- Field Mappings --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="3">
      <strong>Field Mappings</strong>
    </th>
  </tr>
  <tr>
    <th>
      <strong>Field</strong>
    </th>
    <th>
      <strong>Maps to...</strong>
    </th>
    <th>
      <strong>Errors/Warnings</strong>
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
  <script>fields[fields.length] = '<%= field %>'</script>
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
    <img src="images/error.gif" border="0" align="absmiddle"/>&nbsp;<%= (String) fieldErrors.get(field) %>
    <% }else{ %>
    &nbsp;
    <% } %>
  </td>
  </tr>
  <% } %>
</table><br>
<input type="hidden" name="importId" value="<%= ImportDetails.getId() %>">
<input type="submit" value="Process">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='NetworkApplicationsImports.do?command=View';this.form.dosubmit.value='false';">
</form>

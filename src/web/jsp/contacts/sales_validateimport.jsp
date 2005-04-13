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
<jsp:useBean id="ImportValidator" class="org.aspcfs.modules.contacts.base.ContactImportValidate" scope="request"/>
<jsp:useBean id="ImportDetails" class="org.aspcfs.modules.contacts.base.ContactImport" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="AccessTypeList" class="org.aspcfs.modules.admin.base.AccessTypeList" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/spanDisplay.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/tasks.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/submit.js"></script>
<script language="JavaScript">
  fields = new Array();
  function checkPrompt(field){
    thisField = document.getElementById(field);
    if(thisField.value != null && thisField.value == 'contactEmail.email'){
      showSpan(field + 'emailtypespan');
    }else{
      hideSpan(field + 'emailtypespan');
    }
    
    if(thisField.value != null && thisField.value == 'contactPhone.number'){
      showSpan(field + 'phonetypespan');
    }else{
      hideSpan(field + 'phonetypespan');
    }
    
    if(thisField.value != null && thisField.value == 'contactAddress.streetAddressLine1'){
      showSpan(field + 'addresstypespan');
    }else{
      hideSpan(field + 'addresstypespan');
    }
    
   var groupSelector = document.getElementById(field + 'groupId');
   groupSelector.options.selectedIndex = 0;
   if(thisField.value != null && ((thisField.value.indexOf('contactPhone.') > -1) || (thisField.value.indexOf('contactAddress.') > -1) || (thisField.value.indexOf('contactEmail.') > -1))){
      
    if(thisField.value.indexOf('contactAddress.') > -1 || thisField.value.indexOf('contactPhone.') > -1){
      showSpan(field + 'groupIdspan');
    }else{
      hideSpan(field + 'groupIdspan');
    }
      
      /* get the next available group id if it is a new set of phone number or address*/
      if(thisField.value == 'contactPhone.number' || thisField.value == 'contactAddress.streetAddressLine1' || thisField.value == 'contactEmail.email'){
        var dependency = 'contactAddress.streetAddressLine1';
        if(thisField.value == 'contactPhone.number'){
          dependency = 'contactPhone.number';
        }
        if(thisField.value == 'contactEmail.email'){
          dependency = 'contactEmail.email';
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
        var groupDisplay = "Address";
        if(thisField.value.indexOf('contactPhone.') > -1){
          groupDisplay = "Phone";
        }
        if(thisField.value.indexOf('contactEmail.') > -1){
          groupDisplay = "Email";
        }
        for(i = 1; i < 11; i++){
          groupSelector.options[i-1].text = groupDisplay + i;
        }
    }else{
      hideSpan(field + 'groupIdspan');
    }
  }

  function resetNumericFieldValue(fieldId){
    document.getElementById(fieldId).value = -1;
  }

</script>
<%@ include file="../initPage.jsp" %>
<form method="post" name="inputForm" action="SalesImports.do?command=Validate">
  <input type="hidden" name="accessType" value="<%= AccessTypeList.getCode(AccessType.PUBLIC) %>" />
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
  <td>
    <a href="Sales.do"><dhv:label name="Leads" mainMenuItem="true">Leads</dhv:label></a> >
    <% if("list".equals(request.getParameter("return"))){ %>
    <a href="SalesImports.do?command=View"><dhv:label name="accounts.ViewImports">View Imports</dhv:label></a> >
    <% }else{ %>
    <a href="SalesImports.do?command=View"><dhv:label name="accounts.ViewImports">View Imports</dhv:label></a> >
    <a href="SalesImports.do?command=Details&importId=<%= ImportDetails.getId() %>"><dhv:label name="accounts.ImportDetails">Import Details</dhv:label></a> >
    <% } %>
    <dhv:label name="accounts.accounts_contacts_validateimport.ProcessImport">Process Import</dhv:label>
  </td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="<dhv:label name="accounts.accounts_contacts_detailsimport.Process">Process</dhv:label>">
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='SalesImports.do?command=View';this.form.dosubmit.value='false';">
<br />
<dhv:formMessage />
<%-- General Import Properties --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_contacts_validateimport.ImportProperties">Import Properties</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="contacts.name">Name</dhv:label>
  </td>
  <td class="containerBody">
    <%= toString(ImportDetails.getName()) %>
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
  </td>
  <td class="containerBody">
    <%= FileItem.getClientFilename() %>
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="contacts.companydirectory_confirm_importupload.FileSize">File Size</dhv:label>
  </td>
  <td class="containerBody">
    <%= FileItem.getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
  </td>
  <td class="containerBody">
    <dhv:username id="<%= ImportDetails.getEnteredBy() %>"/>
    <zeroio:tz timestamp="<%= ImportDetails.getEntered()  %>" />
  </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= ImportDetails.getModifiedBy() %>"/>
      <zeroio:tz timestamp="<%= ImportDetails.getModified()  %>" />
    </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="accounts.accounts_contacts_detailsimport.Owner">Owner</dhv:label>
  </td>
  <td class="containerBody">
    <table class="empty">
        <tr>
          <td>
            <div id="changeowner">
            <% if(ImportDetails.getOwner() > 0){ %>
              <dhv:username id="<%= ImportDetails.getOwner() %>"/>
            <% }else{ %>
              <dhv:username id="<%= User.getUserId() %>"/>
            <% } %>
            </div>
            </td>
          <td>
            <input type="hidden" name="owner" id="ownerid" value="<%= ImportDetails.getOwner() == -1 ? User.getUserId() : ImportDetails.getOwner() %>">
            &nbsp;[<a href="javascript:popContactsListSingle('ownerid','changeowner', 'listView=employees&usersOnly=true&reset=true');"><dhv:label name="accounts.accounts_contacts_validateimport.ChangeOwner">Change Owner</dhv:label></a>]
            &nbsp; [<a href="javascript:changeDivContent('changeowner',label('label.any','Any'));javascript:resetNumericFieldValue('ownerid');"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
         </td>
        </tr>
    </table>
  </td>
  </tr>
      <%-- <%= AccessTypeList.getHtmlSelect("accessType", ImportDetails.getAccessTypeId()) %> --%>
</table><br>
<%-- Preview of File to be imported --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_contacts_validateimport.First5linesImportfile">First 5 lines of the import file</dhv:label></strong>
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
    <dhv:label name="accounts.accounts_contacts_validateimport.Line">Line</dhv:label> <%= lineCount++ %> 
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
      <strong><dhv:label name="accounts.accounts_contacts_validateimport.GeneralErrorsWarnings">General Errors/Warnings</dhv:label></strong>
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
      <dhv:label name="accounts.accounts_contacts_validateimport.NoErrorsWarningsFound">No errors/warnings found.</dhv:label>
    </td>
  </tr>
 <% } %>
</table><br>
<%-- Field Mappings --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="3">
      <strong><dhv:label name="accounts.accounts_contacts_validateimport.FieldMappings">Field Mappings</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <th>
      <strong><dhv:label name="accounts.accounts_contacts_validateimport.Field">Field</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="accounts.accounts_contacts_validateimport.MapsTo">Maps to...</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="accounts.accounts_contacts_validateimport.ErrorsWarnings">Errors/Warnings</dhv:label></strong>
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
            <span name="<%= field %>emailtypespan" ID="<%= field %>emailtypespan" style="<%= (mappedProperty != null && mappedProperty.getName().equals("email")) ? "" : " display:none" %>">
            <% 
             Property fieldType = null;
             String defaultType = null;
             if(mappedProperty != null && mappedProperty.getName().equals("email")){
               fieldType = propertyMap.getProperty(field, "contactEmail.type", mappedProperty.getGroupId());
               ArrayList thisList = null;
               if(field.indexOf("_") != -1){
                thisList = StringUtils.toArrayList(field, "_");
               }else{
                thisList = StringUtils.toArrayList(field, " ");
               }
               defaultType = (String) thisList.get(0);
             }
            %>
            of type <%= ContactEmailTypeList.getHtmlSelect(field + "_hiddenemailtype", (fieldType != null && !"".equals(fieldType.getDefaultValue()) ? toString(fieldType.getDefaultValue()) : toString(defaultType))) %>
            </span>
            <span name="<%= field %>phonetypespan" ID="<%= field %>phonetypespan" style="<%= (mappedProperty != null && mappedProperty.getName().equals("number")) ? "" : " display:none" %>">
            <% 
             fieldType = null;
             defaultType = null;
             if(mappedProperty != null && mappedProperty.getName().equals("number")){
               fieldType = propertyMap.getProperty(field, "contactPhone.type", mappedProperty.getGroupId());
               ArrayList thisList = null;
               if(field.indexOf("_") != -1){
                thisList = StringUtils.toArrayList(field, "_");
               }else{
                thisList = StringUtils.toArrayList(field, " ");
               }
               defaultType = (String) thisList.get(0);
             }
            %>
            of type <%= ContactPhoneTypeList.getHtmlSelect(field + "_hiddenphonetype", (fieldType != null && !"".equals(StringUtils.toString(fieldType.getDefaultValue())) ? toString(fieldType.getDefaultValue()) : toString(defaultType))) %>
            </span>
            <span name="<%= field %>addresstypespan" ID="<%= field %>addresstypespan" style="<%= (mappedProperty != null && mappedProperty.getName().equals("streetAddressLine1")) ? "" : " display:none" %>">
            <% 
             fieldType = null;
             defaultType = null;
             if(mappedProperty != null && mappedProperty.getName().equals("streetAddressLine1")){
               fieldType = propertyMap.getProperty(field, "contactAddress.type", mappedProperty.getGroupId());
               ArrayList thisList = null;
               if(field.indexOf("_") != -1){
                thisList = StringUtils.toArrayList(field, "_");
               }else{
                thisList = StringUtils.toArrayList(field, " ");
               }
               defaultType = (String) thisList.get(0);
             }
            %>
            of type <%= ContactAddressTypeList.getHtmlSelect(field + "_hiddenaddresstype", (fieldType != null && !"".equals(fieldType.getDefaultValue()) ? toString(fieldType.getDefaultValue()) : toString(defaultType))) %>
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
<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="importId" value="<%= ImportDetails.getId() %>">
<input type="submit" value="<dhv:label name="accounts.accounts_contacts_detailsimport.Process">Process</dhv:label>">
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='SalesImports.do?command=View';this.form.dosubmit.value='false';">
</form>

<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%-- reusable opportunity form --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.pipeline.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="environmentSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="competitorsSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="compellingEventSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="budgetSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="BusTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="closeProbSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="UnitTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="accessTypeList" class="org.aspcfs.modules.admin.base.AccessTypeList" scope="request"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" src="javascript/popContacts.js?v=20070827"></script>
<script type="text/javascript">
  var accessTypeCodeArray = new Array();
  var accessTypeRuleArray = new Array();
<%  Iterator iter = (Iterator) accessTypeList.iterator();
    for(int i=0;iter.hasNext();i++) {
      AccessType accessType = (AccessType) iter.next();
%>
      accessTypeCodeArray[<%= i %>] = '<%= accessType.getCode() %>';
      accessTypeRuleArray[<%= i %>] = '<%= accessType.getRuleId() %>';
<%  } %>

  function getAccessTypeFilters() {
    var access = document.getElementById('<%= opportunityHeader.getId() > 0 ? "accessType" : "header_accessType" %>');
    var accessTypeIsSelect = 'false';
    var accessType = '';
    var result = '';
    <dhv:include name="opportunity.singleComponent" none="true">
      accessTypeIsSelect = '<%= opportunityHeader.getId() == -1 || !allowMultiple %>';
    </dhv:include><dhv:include name="opportunity.singleComponent">
      accessTypeIsSelect = 'false';
    </dhv:include>
    if (accessTypeIsSelect == 'true') {
      accessType = access.options[access.options.selectedIndex].value;
    } else {
      accessType = access.value;
    }
    for (j = 0; j<accessTypeCodeArray.length;j++) {
      if (accessTypeCodeArray[j] == accessType) {
        if (accessTypeRuleArray[j] == '<%= AccessType.CONTROLLED_HIERARCHY %>') {
          result = '&hierarchy=<%= User.getUserId() %>';
        }
        return result;
      }
    }
    return result;
  }
    
  function changeDivContent(divName, divContents) {
    if(document.layers){
      // Netscape 4 or equiv.
      divToChange = document.layers[divName];
      divToChange.document.open();
      divToChange.document.write(divContents);
      divToChange.document.close();
    } else if(document.all){
      // MS IE or equiv.
      divToChange = document.all[divName];
      divToChange.innerHTML = divContents;
    } else if(document.getElementById){
      // Netscape 6 or equiv.
      divToChange = document.getElementById(divName);
      divToChange.innerHTML = divContents;
    }
  }
  function resetSelections() {
    changeDivContent('changeaccount',label('none.selected','None Selected'));
    resetNumericFieldValue('<%= opportunityHeader.getId() > 0 && allowMultiple ? "accountLink" : "header_accountLink" %>');
    changeDivContent('changecontact',label('none.selected','None Selected'));
    resetNumericFieldValue('<%= opportunityHeader.getId() > 0 && allowMultiple ? "contactLink" : "header_contactLink" %>');
    changeDivContent('changemanager',label('none.selected','None Selected'));
    resetNumericFieldValue('managerid');
    changeDivContent('changeowner',label('none.selected','None Selected'));
    resetNumericFieldValue('ownerid');
  }
  
  function getSiteId() {
    var site = document.getElementById('siteId');
    var result = '';
    <dhv:include name="opportunity.singleComponent" none="true">
      result = '<%= opportunityHeader.getId() == -1 && User.getUserRecord().getSiteId() == -1 && ContactDetails.getId() == -1 && OrgDetails.getOrgId() == -1 %>';
    </dhv:include><dhv:include name="opportunity.singleComponent">
      result = '<%= (opportunityHeader.getId() == -1 || !allowMultiple) && User.getUserRecord().getSiteId() == -1 && ContactDetails.getId() == -1 && OrgDetails.getOrgId() == -1 %>';
    </dhv:include>
    if (result == 'true') {
      try {
        result = site.options[site.options.selectedIndex].value;
      } catch(ex) {
        result = site.value;
      }
    } else {
      result = site.value;
    }
    return result;
  }
<dhv:include name="opportunity.singleComponent">
<dhv:evaluate if="<%= opportunityHeader.getId() == -1 %>">
  function updateHeaderFields(form) {
    form.<%= opportunityHeader.getId() > 0 ? "description" : "header_description"%>.value = form.<%= opportunityHeader.getId() > 0 ? "description" : "component_description" %>.value;
    form.<%= (opportunityHeader.getId() > -1 ? "manager" : "header_manager") %>.value = form.<%= (opportunityHeader.getId() > 0 ? "owner" : "component_owner") %>.value;
  }
</dhv:evaluate>
</dhv:include>

  function resetNumericFieldValue(fieldId){
    document.getElementById(fieldId).value = -1;
  }
</script>
<%--  
<script type="text/javascript">
  function updateUserList(select) {
    alert('the selected option value is '+select.options[select.selectedIndex].value+' == '+'<%= accessTypeList.getCode(AccessType.PUBLIC) %>');
    if (select.options[select.selectedIndex].value == '<%= accessTypeList.getCode(AccessType.PUBLIC) %>') {
      hideSpan("controlledHierarchy");
      showSpan("publicHierarchy");
      hideSpan("controlledHierarchy2");
      showSpan("publicHierarchy2");
    } else {
      showSpan("controlledHierarchy");
      hideSpan("publicHierarchy");
      showSpan("controlledHierarchy2");
      hideSpan("publicHierarchy2");
    }
  }
</script>
--%>
<%
    String entity = "pipeline";
    if("contact".equals(request.getParameter("entity"))){
        entity = "contact";
    } else if("account".equals(request.getParameter("entity"))){
        entity = "account";
    }
%>
<dhv:include name="opportunity.singleComponent" none="true">
<dhv:evaluate if="<%= opportunityHeader.getId() > -1 && allowMultiple %>">
<input type="hidden" name="siteId" id="siteId" value="<%= opportunityHeader.getSiteId() > -1 ? opportunityHeader.getSiteId(): (ContactDetails.getId() > -1? ContactDetails.getSiteId(): (OrgDetails.getOrgId() > -1? OrgDetails.getSiteId() : User.getUserRecord().getSiteId())) %>" />
<input type="hidden" name="accessType" id="accessType" value="<%= (opportunityHeader.getAccessType() != -1?opportunityHeader.getAccessType():accessTypeList.getDefaultItem()) %>" />
</dhv:evaluate>
<dhv:evaluate if="<%= opportunityHeader.getId() == -1 || !allowMultiple %>">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></strong>
    </th>
  </tr>
  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.site">Site</dhv:label>
    </td>
    <td>
    <dhv:evaluate if="<%= User.getUserRecord().getSiteId() == -1 && opportunityHeader.getId() == -1 && ContactDetails.getId() == -1 && OrgDetails.getOrgId() == -1 %>" >
      <% SiteIdList.setJsEvent("id=\"siteId\" onChange=\"resetSelections();\""); %>
      <%= SiteIdList.getHtmlSelect("header_siteId", opportunityHeader.getSiteId() > -1 ? opportunityHeader.getSiteId(): (ContactDetails.getId() > -1? ContactDetails.getSiteId(): (OrgDetails.getOrgId() > -1? OrgDetails.getSiteId() : User.getUserRecord().getSiteId()))) %>
    </dhv:evaluate>
    <dhv:evaluate if="<%=User.getUserRecord().getSiteId() != -1 || opportunityHeader.getId() > -1 || ContactDetails.getId() > -1 || OrgDetails.getOrgId() > -1 %>" >
      <input type="hidden" name="header_siteId" id="siteId" value="<%= opportunityHeader.getSiteId() > -1 ? opportunityHeader.getSiteId(): (ContactDetails.getId() > -1? ContactDetails.getSiteId(): (OrgDetails.getOrgId() > -1? OrgDetails.getSiteId() : User.getUserRecord().getSiteId())) %>" />
      <%= SiteIdList.getSelectedValue(opportunityHeader.getSiteId() > -1 ? opportunityHeader.getSiteId(): (ContactDetails.getId() > -1? ContactDetails.getSiteId(): (OrgDetails.getOrgId() > -1? OrgDetails.getSiteId() : User.getUserRecord().getSiteId()))) %>
    </dhv:evaluate>
    </td>
  </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="pipeline.opportunityDescription">Opportunity Description</dhv:label>
    </td>
    <td>
      <input type="text" size="50" maxlength="80" name="<%= opportunityHeader.getId() > 0 ? "description" : "header_description"%>" value="<%= toHtmlValue(opportunityHeader.getDescription()) %>">
      <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_validateimport.AccessType">Access Type</dhv:label>
    </td>
    <td>
      <%
          HtmlSelect thisSelect = accessTypeList.getHtmlSelectObj(opportunityHeader.getAccessType() != -1?opportunityHeader.getAccessType():accessTypeList.getDefaultItem());
          thisSelect.addAttribute("id", opportunityHeader.getId() > 0 ? "accessType" : "header_accessType");
          //thisSelect.addAttribute("onChange", "updateUserList(this);");
      %>
      <%=  thisSelect.getHtml(opportunityHeader.getId() > 0 ? "accessType" : "header_accessType") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="reports.contacts.manager">Manager</dhv:label>
    </td>
    <td valign="center">
      <table class="empty">
        <tr>
          <td>
            <div id="changemanager">
            <%if(opportunityHeader.getManager() > -1){ %>
              <dhv:username id="<%= opportunityHeader.getManager() %>"/>
            <% }else{ %>
               <dhv:username id="<%= User.getUserId() %>" />
            <%}%>
            </div>
          </td>
          <td>
            <input type='hidden' name='<%= (opportunityHeader.getId() > -1 ? "manager" : "header_manager") %>' id='managerid' value='<%= opportunityHeader.getManager()== -1? User.getUserId():opportunityHeader.getManager() %>'><font color="red">*</font>
            <% String managerFilters = "listView=employees&usersOnly=true&searchcodePermission=pipeline-opportunities-edit,accounts-accounts-contacts-opportunities-edit,accounts-accounts-opportunities-edit,contacts-external_contacts-opportunities-edit&reset=true"; %>
<%--        <dhv:evaluate if="<%= accessTypeList.getRuleId(opportunityHeader.getAccessType() != -1?opportunityHeader.getAccessType():accessTypeList.getDefaultItem()) == AccessType.CONTROLLED_HIERARCHY %>">
              <% managerFilters = "listView=employees&usersOnly=true&searchcodePermission=pipeline-opportunities-edit,accounts-accounts-contacts-opportunities-edit,accounts-accounts-opportunities-edit,contacts-external_contacts-opportunities-edit&reset=true"; %>
            </dhv:evaluate> --%>
            &nbsp;[<a href="javascript:popContactsListSingle('managerid','changemanager', '<%= managerFilters %>&siteId='+getSiteId()+getAccessTypeFilters());"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
            &nbsp; [<a href="javascript:changeDivContent('changemanager',label('none.selected','None Selected'));javascript:resetNumericFieldValue('managerid');"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
          </td>
          <td><%= showAttribute(request, "managerError") %>
              <%= showWarningAttribute(request, "managerWarning") %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<br />
</dhv:evaluate>
</dhv:include>
<dhv:include name="opportunity.singleComponent">
<dhv:evaluate if="<%= opportunityHeader.getId() > -1 %>">
<input type="hidden" name="siteId" id="siteId" value="<%= opportunityHeader.getSiteId() > -1 ? opportunityHeader.getSiteId(): (ContactDetails.getId() > -1? ContactDetails.getSiteId(): (OrgDetails.getOrgId() > -1? OrgDetails.getSiteId() : User.getUserRecord().getSiteId())) %>" />
<input type="hidden" name="accessType" id="accessType" value="<%= (opportunityHeader.getAccessType() != -1?opportunityHeader.getAccessType():accessTypeList.getDefaultItem()) %>" />
</dhv:evaluate>
<dhv:evaluate if="<%= opportunityHeader.getId() == -1 %>">
  <input type="hidden" name="header_description" value="<%= toHtmlValue(opportunityHeader.getDescription()) %>" />
  <input type="hidden" name="header_accessType" id="header_accessType" value="<%= (opportunityHeader.getAccessType() != -1?opportunityHeader.getAccessType():accessTypeList.getDefaultItem()) %>" />
  <input type='hidden' name='header_manager' id='managerid' value='<%= opportunityHeader.getManager()== -1? User.getUserId():opportunityHeader.getManager() %>'>
  <input type="hidden" name="header_siteId" id="siteId" value="<%= opportunityHeader.getSiteId() > -1 ? opportunityHeader.getSiteId(): (ContactDetails.getId() > -1? ContactDetails.getSiteId(): (OrgDetails.getOrgId() > -1? OrgDetails.getSiteId() : User.getUserRecord().getSiteId())) %>" />
</dhv:evaluate>
</dhv:include>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
 <dhv:include name="pipeline-descriptionAssign" none="true">
  <tr>
    <th colspan="2">
      <strong>
<% if(ComponentDetails.getId() > 0) {%>
  <dhv:label name="opp.updateAComponent">Update a Component</dhv:label>
<%} else {%>
  <dhv:label name="opp.addAComponent">Add a Component</dhv:label>
<%}%></strong>
    </th>
  </tr>
  </dhv:include>
  <dhv:include name="pipeline-compDescription" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="pipeline.componentDescription">Component Description</dhv:label>
    </td>
    <td>
      <input type="text" size="50" maxlength="80" name="<%= opportunityHeader.getId() > 0 ? "description" : "component_description" %>" value="<%= toHtmlValue(ComponentDetails.getDescription()) %>">
      <font color="red">*</font> <%= showAttribute(request, "componentDescriptionError") %>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="opportunity.assignTo" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="pipeline.assignTo">Assign To</dhv:label>
    </td>
    <td valign="center">
      <table class="empty">
        <tr>
          <td>
            <div id="changeowner">
            <% if(ComponentDetails.getOwner() > 0) { %>
              <dhv:username id="<%= ComponentDetails.getOwner() %>" />
            <% } else if (opportunityHeader.getManager() != -1) { %>
              <dhv:username id="<%= opportunityHeader.getManager() %>" />
            <% } else { %>
               <dhv:username id="<%= User.getUserId() %>" />
            <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="<%= (opportunityHeader.getId() > 0 ? "owner" : "component_owner") %>" id="ownerid" value="<%= ComponentDetails.getOwner() == -1 ? (opportunityHeader.getManager() != -1? opportunityHeader.getManager():User.getUserId()) : ComponentDetails.getOwner() %>"/><font color="red">*</font>
            <% String filters = "listView=employees&usersOnly=true&searchcodePermission=pipeline-opportunities-edit,accounts-accounts-contacts-opportunities-edit,accounts-accounts-opportunities-edit,contacts-external_contacts-opportunities-edit&reset=true"; %>
<%--        <dhv:evaluate if="<%= accessTypeList.getRuleId(opportunityHeader.getAccessType() != -1?opportunityHeader.getAccessType():accessTypeList.getDefaultItem()) == AccessType.CONTROLLED_HIERARCHY %>">
              <%  filters = "listView=employees&usersOnly=true&searchcodePermission=pipeline-opportunities-edit,accounts-accounts-contacts-opportunities-edit,accounts-accounts-opportunities-edit,contacts-external_contacts-opportunities-edit&reset=true"; %>
            </dhv:evaluate> --%>
            &nbsp;[<a href="javascript:popContactsListSingle('ownerid','changeowner', '<%= filters %>&siteId='+getSiteId()+getAccessTypeFilters());"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
            &nbsp; [<a href="javascript:changeDivContent('changeowner',label('none.selected','None Selected'));javascript:resetNumericFieldValue('ownerid');"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
          </td>
          <td><%= showAttribute(request, "ownerError") %>
              <%= showWarningAttribute(request, "ownerWarning") %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="opportunity.assignTo">
    <input type="hidden" name="<%= (opportunityHeader.getId() > 0 ? "owner" : "component_owner") %>" id="ownerid" value="<%= ComponentDetails.getOwner() == -1 ? User.getUserId() : ComponentDetails.getOwner() %>" />
  </dhv:include>
  <dhv:include name="opportunity.componentTypes" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="pipeline.componentTypes.break">Component<br />Type(s)</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <select multiple name="selectedList" id="selectedList" size="5">
              <%if(request.getAttribute("TypeList") != null){ %>
               <dhv:lookupHtml listName="TypeList" lookupName="TypeSelect"/>
              <% }else{ %>
               <dhv:evaluate if="<%= ComponentDetails.getTypes().isEmpty() %>">
                  <option value="-1"><dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label></option>
                </dhv:evaluate>
                <dhv:evaluate if="<%= !ComponentDetails.getTypes().isEmpty() %>">
              <%
                Iterator i = ComponentDetails.getTypes().iterator();
                while (i.hasNext()) {
                LookupElement thisElt = (LookupElement)i.next();
              %>
                <option value="<%= thisElt.getCode() %>"><%= thisElt.getDescription() %></option>
              <%}%>
              </dhv:evaluate>
            <% } %> 
            </select>
            <input type="hidden" name="previousSelection" value="" />
          </td>
          <td valign="top">
            &nbsp;[<a href="javascript:popLookupSelectMultiple('selectedList','1','lookup_opportunity_types');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="pipeline-associateWith" none="true">
  <% if("pipeline".equals(entity)){ %>
  <dhv:evaluate if="<%= opportunityHeader.getId() == -1%>">
  <tr class="containerBody">
    <td nowrap valign="top" class="formLabel">
      <dhv:label name="account.opportunities.associateWith">Associate With</dhv:label>
    </td>
    <td>
      <table cellspacing="0" cellpadding="0" border="0" class="empty">
          <tr>
              <td>
                <input type="radio" name="opp_type" value="org"  onclick="javascript:document.forms['opportunityForm'].header_contactLink.value = '-1';" <dhv:evaluate if='<%=opportunityHeader.getAccountLink() > -1 || "org".equals(request.getParameter("opp_type")) %>'>checked</dhv:evaluate>>
              </td>
              <td>
                <dhv:label name="account.account.colon">Account:</dhv:label>&nbsp;
              </td>
              <td>
                <div id="changeaccount">
                  <% if(opportunityHeader.getAccountLink() != -1) {%>
                    <%= toHtml(opportunityHeader.getAccountName()) %>
                  <%} else {%>
                    <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
                  <%}%>
                </div>
              </td>
              <td>
                <input type="hidden" name="header_accountLink" id="header_accountLink" value="<%= opportunityHeader.getAccountLink() %>">&nbsp;<font color="red">*</font> <%= showAttribute(request, "acctContactError") %>
                &nbsp;[<a href=<%= "javascript:document.forms['opportunityForm'].opp_type[0].checked='t';popAccountsListSingle('header_accountLink','changeaccount','siteId='+getSiteId()+'&thisSiteIdOnly=true');" %> 
                onMouseOver="window.status='Select an Account';return true;" onMouseOut="window.status='';return true;">
                <dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
              </td>
            </tr>
       </table>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <input type="radio" name="opp_type" value="contact" onclick=<%= "\"javascript:document.forms['opportunityForm'].header_accountLink.value = '-1';\" " %> 
            <dhv:evaluate if='<%= opportunityHeader.getContactLink() > -1 || "contact".equals(request.getParameter("opp_type"))%>'>checked</dhv:evaluate>>
          </td>
          <td>
            <dhv:label name="account.contact.colon">Contact:</dhv:label>&nbsp;
          </td>
          <td>
            <div id="changecontact">
              <% if(!String.valueOf(opportunityHeader.getContactLink()).equals("-1")) {%>
                &nbsp;<%= toHtml(opportunityHeader.getContactName()) %>
              <%} else {%>
                <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
              <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="header_contactLink" id="header_contactLink" 
            value='<%= opportunityHeader.getContactLink() == -1 ? -1 : opportunityHeader.getContactLink() %>'>
            &nbsp;[<a href=<%= "\"javascript:document.forms['opportunityForm']." + "opp_type[1]" + ".checked='t';popContactsListSingle('" + (opportunityHeader.getId() > 0 ? "contactLink" : "header_contactLink" ) + "','changecontact','reset=true&mySiteOnly=true&siteId='+getSiteId()+'&filters=mycontacts|accountcontacts');\" "%> onMouseOver="window.status='Select a Contact';return true;" onMouseOut="window.status='';return true;"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= opportunityHeader.getId() > 0 %>">
    <input type="hidden" name="contactLink" id="contactLink" value="<%= opportunityHeader.getContactLink() == -1 ? -1 : opportunityHeader.getContactLink() %>">
    <input type="hidden" name="accountLink" id="accountLink" value="<%= opportunityHeader.getAccountLink() %>">
  </dhv:evaluate>
  <% }else if("contact".equals(entity)){ %>
    <input type="hidden" name="<%= opportunityHeader.getId() > 0 && allowMultiple ? "accountLink" : "header_accountLink" %>" value="-1">
    <input type="hidden" name="<%= opportunityHeader.getId() > 0 && allowMultiple ? "contactLink" : "header_contactLink" %>" value="<%= ContactDetails.getId() %>">
  <% }else if("account".equals(entity)){ %>
    <input type="hidden" name="<%= opportunityHeader.getId() > 0 && allowMultiple ? "accountLink" : "header_accountLink" %>" value="<%= OrgDetails.getOrgId() %>">
    <input type="hidden" name="<%= opportunityHeader.getId() > 0 && allowMultiple ? "contactLink" : "header_contactLink" %>" value="-1">
  <% } %>
  </dhv:include>
  <dhv:include name="opportunity.additionalNotes,pipeline-additionalNotes" none="true">
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.AdditionalNotes">Additional Notes</dhv:label></td>
    <td><TEXTAREA NAME="<%= opportunityHeader.getId() > 0 ? "notes" : "component_notes" %>" ROWS="3" COLS="50"><%= toString(ComponentDetails.getNotes()) %></TEXTAREA></td>
  </tr>  
  </dhv:include>
  <dhv:include name="opportunity.source" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="contact.source">Source</dhv:label>
      </td>
      <td>
        <% BusTypeList.setDefaultKey(ComponentDetails != null ? ComponentDetails.getType() : "");%>
        <%= BusTypeList.getHtml(opportunityHeader.getId() > 0 ? "type" : "component_type") %>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="opportunity.highEstimate" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_oppcomponent_add.HighEstimate">High Estimate</dhv:label>
    </td>
    <td>
      <dhv:evaluate if="<%= ComponentDetails.getHigh() == 0 %>">
        <dhv:include name="opportunity.highEstimateDisabled" none="true">
          <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
          <input type="text" name="<%= opportunityHeader.getId() > 0 ? "high" : "component_high" %>" size="15" value="<zeroio:number value="<%= OrgDetails.getPotential() %>" locale="<%= User.getLocale() %>" />" />
        </dhv:include>
        <dhv:include name="opportunity.highEstimateDisabled">
          <input type="hidden" name="<%= opportunityHeader.getId() > 0 ? "high" : "component_high" %>" value="<%= OrgDetails.getPotential() %>" /><zeroio:currency value="<%= OrgDetails.getPotential() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/>
        </dhv:include>
      </dhv:evaluate>
      <dhv:evaluate if="<%= ComponentDetails.getHigh() != 0 %>">
        <dhv:include name="opportunity.highEstimateDisabled" none="true">
          <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
          <input type="text" name="<%= opportunityHeader.getId() > 0 ? "high" : "component_high" %>" size="15" value="<zeroio:number value="<%= ComponentDetails.getHigh() %>" locale="<%= User.getLocale() %>" />" />
        </dhv:include>
        <dhv:include name="opportunity.highEstimateDisabled">
          <input type="hidden" name="<%= opportunityHeader.getId() > 0 ? "high" : "component_high" %>" value="<%= ComponentDetails.getHigh() %>" />
          <zeroio:currency value="<%= ComponentDetails.getHigh() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/>
        </dhv:include>
      </dhv:evaluate>
      <%= showAttribute(request, "highError") %>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="opportunity.lowEstimate" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_oppcomponent_add.LowEstimate">Low Estimate</dhv:label>
    </td>
    <td>
      <table class="empty">
        <tr><td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <dhv:evaluate if="<%= ComponentDetails.getLow() == 0 %>">
        <input type="text" name="<%= opportunityHeader.getId() > 0 ? "low" : "component_low" %>" size="15" value="<zeroio:number value="<%= OrgDetails.getPotential() %>" locale="<%= User.getLocale() %>" />" />
      </dhv:evaluate>
      <dhv:evaluate if="<%= ComponentDetails.getLow() != 0 %>">
        <input type="text" name="<%= opportunityHeader.getId() > 0 ? "low" : "component_low" %>" size="15" value="<zeroio:number value="<%= ComponentDetails.getLow() %>" locale="<%= User.getLocale() %>" />">
      </dhv:evaluate>
      </td><td>
      <dhv:include name="opportunity.lowEstimateCanNotBeZero"><font color="red">*</font></dhv:include>
      <%= showAttribute(request, "lowError") %>
      </td>
      </tr></table>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="opportunity.lowEstimate">
    <input type="hidden" name="<%= opportunityHeader.getId() > 0 ? "low" : "component_low" %>" value="<%= ComponentDetails.getLow() %>">
  </dhv:include>
  <dhv:include name="pipeline-custom1Integer" none="true">
    <input type="hidden" name="<%= opportunityHeader.getId() > 0 ? "custom1Integer" : "header_custom1Integer" %>" value="<%= opportunityHeader.getCustom1Integer() %>">
  </dhv:include>
  <dhv:include name="pipeline-bestGuessEstimate" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="pipeline.bestGuessEstimate">Best Guess Estimate</dhv:label>
    </td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="<%= opportunityHeader.getId() > 0 ? "guess" : "component_guess" %>" size="15" value="<zeroio:number value="<%= ComponentDetails.getGuess() %>" locale="<%= User.getLocale() %>" />">
      <font color="red">*</font>
      <%= showAttribute(request, "guessError") %>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="pipeline-bestGuessEstimate">
    <input type="hidden" name="<%= opportunityHeader.getId() > 0 ? "guess" : "component_guess" %>" value="<%= ComponentDetails.getGuess() %>">
  </dhv:include>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_oppcomponent_add.ProbOfClose">Prob. of Close</dhv:label>
    </td>
    <td>
      <dhv:evaluate if="<%= closeProbSelect == null || closeProbSelect.size() == 0 %>">
        <input type="text" size="5" name="<%= opportunityHeader.getId() > 0 ? "closeProb" : "component_closeProb" %>" value="<%= ComponentDetails.getCloseProbValue() %>">%
      </dhv:evaluate>
      <dhv:evaluate if="<%= closeProbSelect != null && closeProbSelect.size() != 0 %>">
        <% closeProbSelect.setDefaultKey(ComponentDetails != null ? Integer.parseInt(ComponentDetails.getCloseProbValue()) : -1);%>
        <%= closeProbSelect.getHtml(opportunityHeader.getId() > 0 ? "closeProb" : "component_closeProb") %>
      </dhv:evaluate>
      <font color="red">*</font> <%= showAttribute(request, "closeProbError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_oppcomponent_add.EstCloseDate">Est. Close Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="opportunityForm" field='<%= opportunityHeader.getId() > 0 ? "closeDate" : "component_closeDate" %>' timestamp="<%= ComponentDetails.getCloseDate() %>" timeZone="<%= ComponentDetails.getCloseDateTimeZone() %>" showTimeZone="true" />
      <font color="red">*</font> <%= showAttribute(request, "closeDateError") %>
    </td>
  </tr>
  <dhv:include name="opportunity.termsAndUnits" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_oppcomponent_add.EstTerm">Est. Term</dhv:label>
    </td>
    <td>
      <input type="text" size="5" name="<%= opportunityHeader.getId() > 0 ? "terms" : "component_terms" %>" value="<%= toHtmlValue(ComponentDetails.getTermsString()) %>">
      <% UnitTypeList.setDefaultKey(ComponentDetails.getUnits() != null && !"".equals(ComponentDetails.getUnits().trim()) ? ComponentDetails.getUnits() : ""); %>
      <%= UnitTypeList.getHtml((opportunityHeader.getId() > 0 ? "units" : "component_units")) %>
      <font color="red">*</font>
      <%= showAttribute(request, "termsError") %>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="opportunity.termsAndUnits">
    <input type="hidden" name="<%= opportunityHeader.getId() > 0 ? "terms" : "component_terms" %>" value="1" />
    <input type="hidden" name="<%= opportunityHeader.getId() > 0 ? "units" : "component_units" %>" value="W" />
  </dhv:include>
  <dhv:include name="opportunity.currentStage" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_oppcomponent_details.CurrentStage">Current Stage</dhv:label>
    </td>
    <td>
      <%= StageList.getHtmlSelect((opportunityHeader.getId() > 0 ? "stage" : "component_stage"),ComponentDetails.getStage()) %>
      <input type="checkbox" name="<%= opportunityHeader.getId() > 0 ? "closeNow" : "component_closeNow" %>" value="true" <%= ComponentDetails.getCloseIt() ? " checked" : ""%>><dhv:label name="pipeline.closeThisComponent">Close this component</dhv:label>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="opportunity.currentStage">
    <input type="hidden" name="<%= opportunityHeader.getId() > 0 ? "stage" : "component_stage" %>" value="<%= StageList.getIdFromLevel(10) %>" />
    <input type="hidden" name="<%= opportunityHeader.getId() > 0 ? "closeNow" : "component_closeNow" %>" value="<%= ComponentDetails.getCloseIt() ? "true":"false" %>" />
  </dhv:include>
  <dhv:include name="opportunity.environment" none="true">
  <% if (environmentSelect.getEnabledElementCount() > 0) { %>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="pipeline.environment">Environment</dhv:label>
      </td>
      <td>
        <%= environmentSelect.getHtmlSelect((opportunityHeader.getId() > 0 ? "environment" : "component_environment"),ComponentDetails.getEnvironment()) %>
      </td>
    </tr>
  <%}%> 
  </dhv:include>
  <dhv:include name="opportunity.competitors" none="true">
  <% if (competitorsSelect.getEnabledElementCount() > 0) { %>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="pipeline.competitors">Competitors</dhv:label>
      </td>
      <td>
        <%= competitorsSelect.getHtmlSelect((opportunityHeader.getId() > 0 ? "competitors" : "component_competitors"),ComponentDetails.getCompetitors()) %>
      </td>
    </tr>
  <%}%> 
  </dhv:include>
  <dhv:include name="opportunity.compellingEvent" none="true">
  <% if (compellingEventSelect.getEnabledElementCount() > 0) { %>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="pipeline.compellingEvent">Compelling Event</dhv:label>
      </td>
      <td>
        <%= compellingEventSelect.getHtmlSelect((opportunityHeader.getId() > 0 ? "compellingEvent" : "component_compellingEvent"),ComponentDetails.getCompellingEvent()) %>
      </td>
    </tr>
  <%}%> 
</dhv:include>
<dhv:include name="opportunity.budget" none="true">
  <% if (budgetSelect.getEnabledElementCount() > 0) { %>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="project.budget">Budget</dhv:label>
      </td>
      <td>
        <%= budgetSelect.getHtmlSelect((opportunityHeader.getId() > 0 ? "budget" : "component_budget"),ComponentDetails.getBudget()) %>
      </td>
    </tr>
  <%}%> 
  </dhv:include>
  <dhv:include name="opportunity.estimatedCommission" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_oppcomponent_details.EstCommission">Est. Commission</dhv:label>
    </td>
    <td>
      <input type="text" size="5" name="<%= opportunityHeader.getId() > 0 && allowMultiple ? "commission" : "component_commission" %>" value="<%= ComponentDetails.getCommissionValue() %>">%
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="opportunity.alertDescription" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AlertDescription">Alert Description</dhv:label>
    </td>
    <td>
      <input type="text" maxlength="100" size="50" name="<%= opportunityHeader.getId() > 0 ? "alertText" : "component_alertText" %>" value="<%= toHtmlValue(ComponentDetails.getAlertText()) %>"><br />
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="opportunity.alertDate" none="true">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AlertDate">Alert Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="opportunityForm" field='<%= opportunityHeader.getId() > 0 ? "alertDate" : "component_alertDate" %>' timestamp="<%= ComponentDetails.getAlertDate() %>" timeZone="<%= ComponentDetails.getAlertDateTimeZone() %>" showTimeZone="true" />
      <%= showAttribute(request, "alertDateError") %><%= showWarningAttribute(request, "alertDateWarning") %>
    </td>
  </tr>
  </dhv:include>
</table>

<input type="hidden" name="<%= opportunityHeader.getId() > 0 ? "onlyWarnings" : "component_onlyWarnings" %>" value="<%=(ComponentDetails.getOnlyWarnings()?"on":"off")%>" />
<%--<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>">--%>
<input type="hidden" name="orgId" value="<%=request.getParameter("orgId")%>">
  <%= addHiddenParams(request, "popup|popupType|actionId") %>
<%--
<script type="text/javascript">
  updateUserList(document.getElementById('<%= opportunityHeader.getId() > 0 ? "accessType" : "header_accessType" %>'));
</script> --%>

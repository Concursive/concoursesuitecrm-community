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
<%-- reusable opportunity form --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.pipeline.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="BusTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="UnitTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<%
    String entity = "pipeline";
    if("contact".equals(request.getParameter("entity"))){
        entity = "contact";
    } else if("account".equals(request.getParameter("entity"))){
        entity = "account";
    }
%>
<dhv:evaluate if="<%= opportunityHeader.getId() == -1 %>">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></strong>
    </th>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
    </td>
    <td>
      <input type="text" size="50" maxlength="80" name="<%= opportunityHeader.getId() > 0 ? "description" : "header_description"%>" value="<%= toHtmlValue(opportunityHeader.getDescription()) %>">
      <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
    </td>
  </tr>  
</table>
<br />
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
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
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="pipeline.componentDescription">Component Description</dhv:label>
    </td>
    <td>
      <input type="text" size="50" maxlength="80" name="<%= opportunityHeader.getId() > 0 ? "description" : "component_description" %>" value="<%= toHtmlValue(ComponentDetails.getDescription()) %>">
      <font color="red">*</font> <%= showAttribute(request, "componentDescriptionError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="actionList.assignTo">Assign To</dhv:label>
    </td>
    <td valign="center">
      <%= UserList.getHtmlSelect((opportunityHeader.getId() > 0 ? "owner" : "component_owner"), ComponentDetails.getOwner()) %>
      <%= showAttribute(request, "ownerError") %>
      <%= showWarningAttribute(request, "ownerWarning") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="accounts.accounts_contacts_opps_details.Component">Component</dhv:label><br />
      <dhv:label name="pipeline.types">Type(s)</dhv:label>
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
            <input type="hidden" name="previousSelection" value="">
          </td>
          <td valign="top">
            &nbsp;[<a href="javascript:popLookupSelectMultiple('selectedList','1','lookup_opportunity_types');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
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
                <input type="radio" name="<%= opportunityHeader.getId() > 0 ? "type" : "opp_type" %>" value="org"  onclick=<%= "\"javascript:document.forms['opportunityForm']." + (opportunityHeader.getId() > 0 ? "contactLink" : "header_contactLink")  + ".value = '-1';\" " %><dhv:evaluate if="<%=opportunityHeader.getAccountLink() > -1 || "org".equals(request.getParameter(opportunityHeader.getId() > 0 ? "type" : "opp_type")) %>">checked</dhv:evaluate>>
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
                <input type="hidden" name="<%= opportunityHeader.getId() > 0 ? "accountLink" : "header_accountLink" %>" id="<%= opportunityHeader.getId() > 0 ? "accountLink" : "header_accountLink" %>" value="<%= opportunityHeader.getAccountLink() %>">&nbsp;<font color="red">*</font> <%= showAttribute(request, "acctContactError") %>
                &nbsp;[<a href="<%= "javascript:document.forms['opportunityForm']." + (opportunityHeader.getId() > 0 ? "type[0]" : "opp_type[0]") + ".checked='t';popAccountsListSingle('" + (opportunityHeader.getId() > 0 ? "accountLink" : "header_accountLink" ) + "','changeaccount');" %>" onMouseOver="window.status='Select an Account';return true;" onMouseOut="window.status='';return true;"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
              </td>
            </tr>
       </table>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <input type="radio" name="<%= opportunityHeader.getId() > 0 ? "type" : "opp_type"%>" value="contact" onclick=<%= "\"javascript:document.forms['opportunityForm']." + (opportunityHeader.getId() > 0 ? "accountLink" : "header_accountLink")  + ".value = '-1';\" " %> <dhv:evaluate if="<%= opportunityHeader.getContactLink() > -1 || "contact".equals(request.getParameter(opportunityHeader.getId() > 0 ? "type" : "opp_type"))%>">checked</dhv:evaluate>>
          </td>
          <td>
            <dhv:label name="account.contact.colon">Contact:</dhv:label>&nbsp;
          </td>
          <td>
            <div id="changecontact">
              <% if(!String.valueOf(opportunityHeader.getContactLink()).equals("-1")) {%>
                <%= toHtml("&nbsp;" + opportunityHeader.getContactName()) %>
              <%} else {%>
                <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
              <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="<%= opportunityHeader.getId() > 0 ? "contactLink" : "header_contactLink" %>" id="<%= opportunityHeader.getId() > 0 ? "contactLink" : "header_contactLink" %>" value="<%= opportunityHeader.getContactLink() == -1 ? -1 : opportunityHeader.getContactLink() %>">
            &nbsp;[<a href=<%= "\"javascript:document.forms['opportunityForm']." + (opportunityHeader.getId() > 0 ? "type[1]" : "opp_type[1]") + ".checked='t';popContactsListSingle('" + (opportunityHeader.getId() > 0 ? "contactLink" : "header_contactLink" ) + "','changecontact','reset=true&filters=mycontacts|accountcontacts');\" "%> onMouseOver="window.status='Select a Contact';return true;" onMouseOut="window.status='';return true;"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  </dhv:evaluate>
  <% }else if("contact".equals(entity)){ %>
    <input type="hidden" name="<%= opportunityHeader.getId() > 0 ? "contactLink" : "header_contactLink" %>" value="<%= ContactDetails.getId() %>">
  <% }else if("account".equals(entity)){ %>
    <input type="hidden" name="<%= opportunityHeader.getId() > 0 ? "accountLink" : "header_accountLink" %>" value="<%= OrgDetails.getOrgId() %>">
  <% } %>
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.AdditionalNotes">Additional Notes</dhv:label></td>
    <td><TEXTAREA NAME="<%= opportunityHeader.getId() > 0 ? "notes" : "component_notes" %>" ROWS="3" COLS="50"><%= toString(ComponentDetails.getNotes()) %></TEXTAREA></td>
  </tr>  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="contact.source">Source</dhv:label>
    </td>
    <td>
      <% BusTypeList.setDefaultKey(ComponentDetails != null ? ComponentDetails.getType() : "");%>
      <%= BusTypeList.getHtml(opportunityHeader.getId() > 0 ? "type" : "component_type") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_oppcomponent_add.ProbOfClose">Prob. of Close</dhv:label>
    </td>
    <td>
      <input type="text" size="5" name="<%= opportunityHeader.getId() > 0 ? "closeProb" : "component_closeProb" %>" value="<%= ComponentDetails.getCloseProbValue() %>">%
      <font color="red">*</font> <%= showAttribute(request, "closeProbError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_oppcomponent_add.EstCloseDate">Est. Close Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="opportunityForm" field="<%= opportunityHeader.getId() > 0 ? "closeDate" : "component_closeDate" %>" timestamp="<%= ComponentDetails.getCloseDate() %>" timeZone="<%= ComponentDetails.getCloseDateTimeZone() %>" showTimeZone="true" />
      <font color="red">*</font> <%= showAttribute(request, "closeDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_oppcomponent_add.LowEstimate">Low Estimate</dhv:label>
    </td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="<%= opportunityHeader.getId() > 0 ? "low" : "component_low" %>" size="15" value="<zeroio:number value="<%= ComponentDetails.getLow() %>" locale="<%= User.getLocale() %>" />">
      <%= showAttribute(request, "lowError") %>
    </td>
  </tr>
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
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_oppcomponent_add.HighEstimate">High Estimate</dhv:label>
    </td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="<%= opportunityHeader.getId() > 0 ? "high" : "component_high" %>" size="15" value="<zeroio:number value="<%= ComponentDetails.getHigh() %>" locale="<%= User.getLocale() %>" />">
      <%= showAttribute(request, "highError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_oppcomponent_add.EstTerm">Est. Term</dhv:label>
    </td>
    <td>
      <input type="text" size="5" name="<%= opportunityHeader.getId() > 0 ? "terms" : "component_terms" %>" value="<%= toHtmlValue(ComponentDetails.getTermsString()) %>">
      <%= UnitTypeList.getHtml((opportunityHeader.getId() > 0 ? "units" : "component_units"), (ComponentDetails.getUnits() != null ? ComponentDetails.getUnits() : "")) %>
      <font color="red">*</font>
      <%= showAttribute(request, "termsError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_oppcomponent_details.CurrentStage">Current Stage</dhv:label>
    </td>
    <td>
      <%= StageList.getHtmlSelect((opportunityHeader.getId() > 0 ? "stage" : "component_stage"),ComponentDetails.getStage()) %>
      <input type="checkbox" name="<%= opportunityHeader.getId() > 0 ? "closeNow" : "component_closeNow" %>" value="true" <%= ComponentDetails.getCloseIt() ? " checked" : ""%>><dhv:label name="pipeline.closeThisComponent">Close this component</dhv:label>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_oppcomponent_details.EstCommission">Est. Commission</dhv:label>
    </td>
    <td>
      <input type="text" size="5" name="<%= opportunityHeader.getId() > 0 ? "commission" : "component_commission" %>" value="<%= ComponentDetails.getCommissionValue() %>">%
      <input type="hidden" name="orgId" value="<%=request.getParameter("orgId")%>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AlertDescription">Alert Description</dhv:label>
    </td>
    <td>
      <input type="text" maxlength="100" size="50" name="<%= opportunityHeader.getId() > 0 ? "alertText" : "component_alertText" %>" value="<%= toHtmlValue(ComponentDetails.getAlertText()) %>"><br>
    </td>
  </tr>
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AlertDate">Alert Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="opportunityForm" field="<%= opportunityHeader.getId() > 0 ? "alertDate" : "component_alertDate" %>" timestamp="<%= ComponentDetails.getAlertDate() %>" timeZone="<%= ComponentDetails.getAlertDateTimeZone() %>" showTimeZone="true" />
      <%= showAttribute(request, "alertDateError") %><%= showWarningAttribute(request, "alertDateWarning") %>
    </td>
  </tr>
</table>

<input type="hidden" name="<%= opportunityHeader.getId() > 0 ? "onlyWarnings" : "component_onlyWarnings" %>" value="<%=(ComponentDetails.getOnlyWarnings()?"on":"off")%>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>

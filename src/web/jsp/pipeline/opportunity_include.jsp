<%-- reusable opportunity form --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="BusTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="UnitTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
  <%
    String entity = "pipeline";
    if("contact".equals(request.getParameter("entity"))){
        entity = "contact";
    }else if("account".equals(request.getParameter("entity"))){
        entity = "account";
    }
%>
<dhv:evaluate if="<%= opportunityHeader.getId() == -1 %>">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>Opportunity details</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Description
    </td>
    <td>
      <input type="text" size="50" name="<%= opportunityHeader.getId() > 0 ? "description" : "header_description"%>" value="<%= toHtmlValue(opportunityHeader.getDescription()) %>">
      <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
    </td>
  </tr>  
</table>
&nbsp;<br>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong><%= (ComponentDetails.getId() > 0 ? "Update" : "Add") %> a Component</strong>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Component Description
    </td>
    <td>
      <input type="text" size="50" name="<%= opportunityHeader.getId() > 0 ? "description" : "component_description" %>" value="<%= toHtmlValue(ComponentDetails.getDescription()) %>">
      <font color="red">*</font> <%= showAttribute(request, "componentDescriptionError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Assign To
    </td>
    <td valign="center">
      <%= UserList.getHtmlSelect((opportunityHeader.getId() > 0 ? "owner" : "component_owner"), ComponentDetails.getOwner()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Component<br>Type(s)
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <select multiple name="selectedList" id="selectedList" size="5">
              <%if(request.getAttribute("TypeList") != null){ %>
               <dhv:lookupHtml listName="TypeList" lookupName="TypeSelect"/>
              <% }else{ %>
               <dhv:evaluate exp="<%= ComponentDetails.getTypes().isEmpty() %>">
                  <option value="-1">None Selected</option>
                </dhv:evaluate>
                <dhv:evaluate exp="<%= !ComponentDetails.getTypes().isEmpty() %>">
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
            &nbsp;[<a href="javascript:popLookupSelectMultiple('selectedList','1','lookup_opportunity_types');">Select</a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <% if("pipeline".equals(entity)){ %>
  <dhv:evaluate if="<%= opportunityHeader.getId() == -1%>">
  <tr class="containerBody">
    <td nowrap valign="top" class="formLabel">
      Associate With
    </td>
    <td>
      <table cellspacing="0" cellpadding="0" border="0">
          <tr>
              <td>
                <input type="radio" name="<%= opportunityHeader.getId() > 0 ? "type" : "opp_type" %>" value="org"  onclick=<%= "\"javascript:document.forms['opportunityForm']." + (opportunityHeader.getId() > 0 ? "contactLink" : "header_contactLink")  + ".value = '-1';\" " %><dhv:evaluate exp="<%=(opportunityHeader.getAccountLink() > -1)%>">checked</dhv:evaluate>>
              </td>
              <td>
                Account:&nbsp;
              </td>
              <td>
                <div id="changeaccount"><%= opportunityHeader.getAccountLink() != -1 ? opportunityHeader.getAccountName() : "None Selected" %></div>
              </td>
              <td>
                <input type="hidden" name="<%= opportunityHeader.getId() > 0 ? "accountLink" : "header_accountLink" %>" id="<%= opportunityHeader.getId() > 0 ? "accountLink" : "header_accountLink" %>" value="<%= opportunityHeader.getAccountLink() %>">&nbsp;<font color="red">*</font> <%= showAttribute(request, "acctContactError") %>
                &nbsp;[<a href="<%= "javascript:document.forms['opportunityForm']." + (opportunityHeader.getId() > 0 ? "type[0]" : "opp_type[0]") + ".checked='t';popAccountsListSingle('" + (opportunityHeader.getId() > 0 ? "accountLink" : "header_accountLink" ) + "','changeaccount');" %>" onMouseOver="window.status='Select an Account';return true;" onMouseOut="window.status='';return true;">Select</a>]
              </td>
            </tr>
       </table>
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <input type="radio" name="<%= opportunityHeader.getId() > 0 ? "type" : "opp_type"%>" value="contact" onclick=<%= "\"javascript:document.forms['opportunityForm']." + (opportunityHeader.getId() > 0 ? "accountLink" : "header_accountLink")  + ".value = '-1';\" " %> <dhv:evaluate if="<%= opportunityHeader.getContactLink() > -1 %>">checked</dhv:evaluate>>
          </td>
          <td>
            Contact:&nbsp;
          </td>
          <td>
            <div id="changecontact"><%= String.valueOf(opportunityHeader.getContactLink()).equals("-1")?"None Selected":"&nbsp;" + opportunityHeader.getContactName()%></div>
          </td>
          <td>
            <input type="hidden" name="<%= opportunityHeader.getId() > 0 ? "contactLink" : "header_contactLink" %>" id="<%= opportunityHeader.getId() > 0 ? "contactLink" : "header_contactLink" %>" value="<%= opportunityHeader.getContactLink() == -1 ? -1 : opportunityHeader.getContactLink() %>">
            &nbsp;[<a href=<%= "\"javascript:document.forms['opportunityForm']." + (opportunityHeader.getId() > 0 ? "type[1]" : "opp_type[1]") + ".checked='t';popContactsListSingle('" + (opportunityHeader.getId() > 0 ? "contactLink" : "header_contactLink" ) + "','changecontact','reset=true&filters=mycontacts|accountcontacts');\" "%> onMouseOver="window.status='Select a Contact';return true;" onMouseOut="window.status='';return true;">Select</a>]
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
    <td valign="top" nowrap class="formLabel">Additional Notes</td>
    <td><TEXTAREA NAME="<%= opportunityHeader.getId() > 0 ? "notes" : "component_notes" %>" ROWS="3" COLS="50"><%= toString(ComponentDetails.getNotes()) %></TEXTAREA></td>
  </tr>  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Source
    </td>
    <td>
      <% BusTypeList.setDefaultKey(ComponentDetails != null ? ComponentDetails.getType() : "");%>
      <%= BusTypeList.getHtml(opportunityHeader.getId() > 0 ? "type" : "component_type") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Prob. of Close
    </td>
    <td>
      <input type="text" size="5" name="<%= opportunityHeader.getId() > 0 ? "closeProb" : "component_closeProb" %>" value="<%= ComponentDetails.getCloseProbValue() %>">%
      <font color="red">*</font> <%= showAttribute(request, "closeProbError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Close Date
    </td>
    <td>
      <input type="text" size="10" name="<%= opportunityHeader.getId() > 0 ? "closeDate" : "component_closeDate" %>" value="<%= toHtmlValue(ComponentDetails.getCloseDateString()) %>">
      <a href="javascript:popCalendar('opportunityForm', '<%= opportunityHeader.getId() > 0 ? "closeDate" : "component_closeDate" %>');">Date</a> (mm/dd/yyyy)
      <font color="red">*</font> <%= showAttribute(request, "closeDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Low Estimate
    </td>
    <td>
      <input type="text" size="10" name="<%= opportunityHeader.getId() > 0 ? "low" : "component_low" %>" value="<%= toHtmlValue(ComponentDetails.getLowAmount()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Best Guess Estimate
    </td>
    <td>
      <input type="text" size="10" name="<%= opportunityHeader.getId() > 0 ? "guess" : "component_guess" %>" value="<%= toHtmlValue(ComponentDetails.getGuessAmount()) %>">
      <font color="red">*</font> <%= showAttribute(request, "guessError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      High Estimate
    </td>
    <td>
      <input type="text" size="10" name="<%= opportunityHeader.getId() > 0 ? "high" : "component_high" %>" value="<%= toHtmlValue(ComponentDetails.getHighAmount()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Term
    </td>
    <td>
      <input type="text" size="5" name="<%= opportunityHeader.getId() > 0 ? "terms" : "component_terms" %>" value="<%= toHtmlValue(ComponentDetails.getTermsString()) %>">
      <%= UnitTypeList.getHtml((opportunityHeader.getId() > 0 ? "units" : "component_units"), (ComponentDetails.getUnits() != null ? ComponentDetails.getUnits() : "")) %>
      <font color="red">*</font> <%= showAttribute(request, "termsError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Current Stage
    </td>
    <td>
      <%= StageList.getHtmlSelect((opportunityHeader.getId() > 0 ? "stage" : "component_stage"),ComponentDetails.getStage()) %>
      <input type="checkbox" name="<%= opportunityHeader.getId() > 0 ? "closeNow" : "component_closeNow" %>" <%= ComponentDetails.getCloseIt() ? " checked" : ""%>>Close this component
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Commission
    </td>
    <td>
      <input type="text" size="5" name="<%= opportunityHeader.getId() > 0 ? "commission" : "component_commission" %>" value="<%= ComponentDetails.getCommissionValue() %>">%
      <input type="hidden" name="orgId" value="<%=request.getParameter("orgId")%>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Description
    </td>
    <td>
      <input type="text" size="50" name="<%= opportunityHeader.getId() > 0 ? "alertText" : "component_alertText" %>" value="<%= toHtmlValue(ComponentDetails.getAlertText()) %>"><br>
    </td>
  </tr>
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td>
      <input type="text" size="10" name="<%= opportunityHeader.getId() > 0 ? "alertDate" : "component_alertDate" %>" value="<%= toHtmlValue(ComponentDetails.getAlertDateString()) %>">
      <a href="javascript:popCalendar('opportunityForm', '<%= opportunityHeader.getId() > 0 ? "alertDate" : "component_alertDate" %>');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
</table>
<%= addHiddenParams(request, "popup|popupType|actionId") %>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="BusTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="UnitTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="OppDetails" class="org.aspcfs.modules.pipeline.beans.OpportunityBean" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].header_description.focus();">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript" src="javascript/popContacts.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript" src="javascript/popAccounts.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
function doCheck(form) {
  if (form.dosubmit.value == "false") {
    return true;
  } else {
    return(checkForm(form));
  }
}
function checkForm(form) {
  formTest = true;
  message = "";
  selected = -1;
  for (i=0; i<form.opp_type.length; i++) {
    if (form.opp_type[i].checked) {
      selected = i;
    }
  }
  if (selected == -1) {
    message += "- Please select an opportunity type (account or contact)\r\n";
    formTest = false;
  }
  if ((!form.component_closeDate.value == "") && (!checkDate(form.component_closeDate.value))) { 
    message += "- Check that Est. Close Date is entered correctly\r\n";
    formTest = false;
  }
  if ((!form.component_alertDate.value == "") && (!checkDate(form.component_alertDate.value))) { 
    message += "- Check that Alert Date is entered correctly\r\n";
    formTest = false;
  }
  if ((!form.component_alertDate.value == "") && (!checkAlertDate(form.component_alertDate.value))) { 
    message += "- Check that Alert Date is on or after today's date\r\n";
    formTest = false;
  }
  if ((!form.component_alertText.value == "") && (form.component_alertDate.value == "")) { 
    message += "- Please specify an alert date\r\n";
    formTest = false;
  }
  if ((!form.component_alertDate.value == "") && (form.component_alertText.value == "")) { 
    message += "- Please specify an alert description\r\n";
    formTest = false;
  }
  if (formTest == false) {
    alert("Form could not be saved, please check the following:\r\n\r\n" + message);
    return false;
  } else {
    var test = document.addOpportunity.selectedList;
    if (test != null) {
      return selectAllOptions(document.addOpportunity.selectedList);
    }
  }
}
</script>
<form name="addOpportunity" action="Leads.do?command=InsertOpp&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<a href="Leads.do">Pipeline Management</a> > 
Add Opportunity<br>
<hr color="#BFBFBB" noshade>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="0" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr>
    <td>
<input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='Leads.do?command=ViewOpp';this.form.dosubmit.value='false';">
<input type="reset" value="Reset">
<br>
<%= showError(request, "actionError") %>
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
      <input type="text" size="50" name="header_description" value="<%= toHtmlValue(OppDetails.getHeader().getDescription()) %>">
      <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
    </td>
  </tr>  
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>Add a Component</strong>
    </td>     
  </tr>  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Assign To
    </td>
    <td valign="center">
      <%= UserList.getHtmlSelect("component_owner", OppDetails.getComponent().getOwner()) %>
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
              <dhv:lookupHtml listName="TypeList" lookupName="TypeSelect"/> 
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
  <tr class="containerBody">
    <td nowrap valign="top" class="formLabel">
      Associate With
    </td>
    <td>
      <table cellspacing="0" cellpadding="0" border="0">
          <tr>
              <td>
                <input type="radio" name="opp_type" value="org" onclick="javascript:document.forms['addOpportunity'].header_contactLink.value = '-1';" <dhv:evaluate exp="<%=(OppDetails.getHeader().getAccountLink() > -1)%>">checked</dhv:evaluate>>
              </td>
              <td>
                Account:&nbsp;
              </td>
              <td>
                <div id="changeaccount"><%= OppDetails.getHeader().getAccountLink() != -1 ? OppDetails.getHeader().getAccountName() : "None Selected" %></div>
              </td>
              <td>
                <input type="hidden" name="header_accountLink" id="header_accountLink" value="<%= OppDetails.getHeader().getAccountLink() %>">
                &nbsp;[<a href="javascript:document.forms['addOpportunity'].opp_type[0].checked='t';popAccountsListSingle('header_accountLink','changeaccount');" onMouseOver="window.status='Select an Account';return true;" onMouseOut="window.status='';return true;">Select</a>]&nbsp;<font color="red">*</font> <%= showAttribute(request, "acctContactError") %>
              </td>
            </tr>
       </table>
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <input type="radio" name="opp_type" value="contact" onclick="javascript:document.forms['addOpportunity'].header_accountLink.value = '-1';" <dhv:evaluate exp="<%=(OppDetails.getHeader().getContactLink() > -1)%>">checked</dhv:evaluate>>
          </td>
          <td>
            Contact:&nbsp;
          </td>
          <td>
            <div id="changecontact"><%= String.valueOf(OppDetails.getHeader().getContactLink()).equals("-1")?"None Selected":"&nbsp;" + OppDetails.getHeader().getContactName()%></div>
          </td>
          <td>
            <input type="hidden" name="header_contactLink" id="header_contactLink" value="<%= OppDetails.getHeader().getContactLink() == -1?-1:OppDetails.getHeader().getContactLink() %>">
            &nbsp;[<a href="javascript:document.forms['addOpportunity'].opp_type[1].checked='t';popContactsListSingle('header_contactLink','changecontact','reset=true&filters=mycontacts|accountcontacts');" onMouseOver="window.status='Select a Contact';return true;" onMouseOut="window.status='';return true;">Select</a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Component Description
    </td>
    <td>
      <input type="text" size="50" name="component_description" value="<%= toHtmlValue(OppDetails.getComponent().getDescription()) %>">
      <font color="red">*</font> <%= showAttribute(request, "componentDescriptionError") %>
    </td>
  </tr>  
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">Additional Notes</td>
    <td><TEXTAREA NAME="component_notes" ROWS="3" COLS="50"><%= toString(OppDetails.getComponent().getNotes()) %></TEXTAREA></td>
  </tr>  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Source
    </td>
    <td>
      <% BusTypeList.setDefaultKey(OppDetails.getComponent() != null ? OppDetails.getComponent().getType() : "");%>
      <%= BusTypeList.getHtml("component_type") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Prob. of Close
    </td>
    <td>
      <input type="text" size="5" name="component_closeProb" value="<%= OppDetails.getComponent().getCloseProbValue() %>">%
      <font color="red">*</font> <%= showAttribute(request, "closeProbError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Close Date
    </td>
    <td>
      <input type="text" size="10" name="component_closeDate" value="<%= toHtmlValue(OppDetails.getComponent().getCloseDateString()) %>">
      <a href="javascript:popCalendar('addOpportunity', 'component_closeDate');">Date</a> (mm/dd/yyyy)
      <font color="red">*</font> <%= showAttribute(request, "closeDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Low Estimate
    </td>
    <td>
      <input type="text" size="10" name="component_low" value="<%= toHtmlValue(OppDetails.getComponent().getLowAmount()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Best Guess Estimate
    </td>
    <td>
      <input type="text" size="10" name="component_guess" value="<%= toHtmlValue(OppDetails.getComponent().getGuessAmount()) %>">
      <font color="red">*</font> <%= showAttribute(request, "guessError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      High Estimate
    </td>
    <td>
      <input type="text" size="10" name="component_high" value="<%= toHtmlValue(OppDetails.getComponent().getHighAmount()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Term
    </td>
    <td>
      <input type="text" size="5" name="component_terms" value="<%= toHtmlValue(OppDetails.getComponent().getTermsString()) %>">
      <%= UnitTypeList.getHtml("component_units", (OppDetails.getComponent().getUnits() != null ? OppDetails.getComponent().getUnits() : "")) %>
      <font color="red">*</font> <%= showAttribute(request, "termsError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Current Stage
    </td>
    <td>
      <%= StageList.getHtmlSelect("component_stage",OppDetails.getComponent().getStage()) %>
      <input type="checkbox" name="component_closeNow" <%= OppDetails.getComponent().getCloseIt() ? " checked" : ""%>>Close this component
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Commission
    </td>
    <td>
      <input type="text" size="5" name="component_commission" value="<%= OppDetails.getComponent().getCommissionValue() %>">%
      <input type="hidden" name="orgId" value="<%=request.getParameter("orgId")%>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Description
    </td>
    <td>
      <input type="text" size="50" name="component_alertText" value="<%= toHtmlValue(OppDetails.getComponent().getAlertText()) %>"><br>
    </td>
  </tr>
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td>
      <input type="text" size="10" name="component_alertDate" value="<%= toHtmlValue(OppDetails.getComponent().getAlertDateString()) %>">
      <a href="javascript:popCalendar('addOpportunity', 'component_alertDate');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
</table>
&nbsp;
<br>
<input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='Leads.do?command=ViewOpp';this.form.dosubmit.value='false';">
<input type="reset" value="Reset">
<input type="hidden" name="dosubmit" value="true">
    </td>
  </tr>
</table>
</form>
</body>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="contactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="oppDetails" class="org.aspcfs.modules.pipeline.beans.OpportunityBean" scope="request"/>
<jsp:useBean id="stageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="busTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="unitTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].header_description.focus();">
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript">
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
<form name="addOpportunity" action="ExternalContactsOpps.do?command=InsertOpp&contactId=<%= contactDetails.getId() %>&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= contactDetails.getId() %>">Contact Details</a> >
<a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= contactDetails.getId() %>">Opportunities</a> >
Add Opportunity<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(contactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + contactDetails.getId(); %>      
      <dhv:container name="contacts" selected="opportunities" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=ViewOpps&contactId=<%= contactDetails.getId() %>';this.form.dosubmit.value='false';">
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
    <td class="formLabel" nowrap>
      Description
    </td>
    <td>
      <input type="text" size="50" name="header_description" value="<%= toHtmlValue(oppDetails.getHeader().getDescription()) %>">
      <font color=red>*</font> <%= showAttribute(request, "descriptionError") %>
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
    <td class="formLabel" nowrap>
      Assign To
    </td>
    <td valign="center">
      <%= UserList.getHtmlSelect("component_owner") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      Component<br>Type(s)
    </td>
    <td valign="center">
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <select multiple name="selectedList" id="selectedList" size="5">
              <option value="-1">None Selected</option>
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
    <td class="formLabel" nowrap>
      Component Description
    </td>
    <td>
      <input type="text" size="50" name="component_description" value="<%= toHtmlValue(oppDetails.getComponent().getDescription()) %>">
      <font color=red>*</font> <%= showAttribute(request, "componentDescriptionError") %>
    </td>
  </tr>  
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">Additional Notes</td>
    <td><TEXTAREA NAME="component_notes" ROWS="3" COLS="50"><%= toString(oppDetails.getComponent().getNotes()) %></TEXTAREA></td>
  </tr>  
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Source
    </td>
    <td>
      <% busTypeList.setSelectName("component_type");%>
      <%= busTypeList.getHtml() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Prob. of Close
    </td>
    <td>
      <input type="text" size="5" name="component_closeProb" value="<%= oppDetails.getComponent().getCloseProbValue() %>">%
      <font color=red>*</font> <%= showAttribute(request, "closeProbError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Est. Close Date
    </td>
    <td>
      <input type="text" size="10" name="component_closeDate" value="<%= toHtmlValue(oppDetails.getComponent().getCloseDateString()) %>">
      <a href="javascript:popCalendar('addOpportunity', 'component_closeDate');">Date</a> (mm/dd/yyyy)
      <font color=red>*</font> <%= showAttribute(request, "closeDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Low Estimate
    </td>
    <td>
      <input type="text" size="10" name="component_low" value="<%= toHtmlValue(oppDetails.getComponent().getLowAmount()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Best Guess Estimate
    </td>
    <td>
      <input type="text" size="10" name="component_guess" value="<%= toHtmlValue(oppDetails.getComponent().getGuessAmount()) %>">
      <font color=red>*</font> <%= showAttribute(request, "guessError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      High Estimate
    </td>
    <td>
      <input type="text" size="10" name="component_high" value="<%= toHtmlValue(oppDetails.getComponent().getHighAmount()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Est. Term
    </td>
    <td>
      <input type="text" size="5" name="component_terms" value="<%= toHtmlValue(oppDetails.getComponent().getTermsString()) %>">
      <% unitTypeList.setSelectName("component_units");%>
      <%= unitTypeList.getHtml() %>
      <font color="red">*</font> <%= showAttribute(request, "termsError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Current Stage
    </td>
    <td>
      <%= stageList.getHtmlSelect("component_stage", oppDetails.getComponent().getStage()) %>
      <input type="checkbox" name="closeNow">Close this component
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Est. Commission
    </td>
    <td>
      <input type="text" size="5" name="component_commission" value="<%= oppDetails.getComponent().getCommissionValue() %>">%
      <input type="hidden" name="header_contactLink" value="<%=request.getParameter("contactId")%>">
      <input type="hidden" name="contactId" value="<%=request.getParameter("contactId")%>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Alert Description
    </td>
    <td valign="center">
      <input type="text" size="50" name="component_alertText" value="<%= toHtmlValue(oppDetails.getComponent().getAlertText()) %>"><br>
    </td>
  </tr>
   <tr class="containerBody">
    <td class="formLabel" nowrap>
      Alert Date
    </td>
    <td valign="center">
      <input type="text" size="10" name="component_alertDate" value="<%= toHtmlValue(oppDetails.getComponent().getAlertDateString()) %>">
      <a href="javascript:popCalendar('addOpportunity', 'component_alertDate');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
</table>
&nbsp;
<br>
  <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=ViewOpps&contactId=<%= contactDetails.getId() %>';this.form.dosubmit.value='false';">
  <input type="reset" value="Reset">
  <input type="hidden" name="dosubmit" value="true">
  </td>
</tr>
</table>
</form>
</body>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="contactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="oppComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="stageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="busTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="unitTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].description.focus();">
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
      if ((!form.closeDate.value == "") && (!checkDate(form.closeDate.value))) { 
        message += "- Check that Est. Close Date is entered correctly\r\n";
        formTest = false;
      }
      if ((!form.alertDate.value == "") && (!checkDate(form.alertDate.value))) { 
        message += "- Check that Alert Date is entered correctly\r\n";
        formTest = false;
      }
      if ((!form.alertDate.value == "") && (!checkAlertDate(form.alertDate.value))) { 
        message += "- Check that Alert Date is on or after today's date\r\n";
        formTest = false;
      }
      if ((!form.alertText.value == "") && (form.alertDate.value == "")) { 
        message += "- Please specify an alert date\r\n";
        formTest = false;
      }
      if ((!form.alertDate.value == "") && (form.alertText.value == "")) { 
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
<form name="addOpportunity" action="ExternalContactsOppComponents.do?command=InsertOppComponent&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= contactDetails.getId() %>">Contact Details</a> >
<a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= contactDetails.getId() %>">Opportunities</a> >
<a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= contactDetails.getId() %>">Opportunity Details</a> >
Add Component<br>
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
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= contactDetails.getId() %>';this.form.dosubmit.value='false';">
<input type="reset" value="Reset">
<br>
<%= showError(request, "actionError") %>  

  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong><%= toHtml(opportunityHeader.getDescription()) %></strong>
    </td>     
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Assign To
    </td>
    <td valign="center">
      <%= UserList.getHtmlSelect("owner", oppComponentDetails.getOwner() ) %>
    </td>
  </tr>
  
  <tr class="containerBody">
  <td class="formLabel" valign="top">
    Component<br>Type(s)
  </td>
  <td valign=center>
    <select multiple name="selectedList" id="selectedList" size="5">
    <option value="-1">None Selected</option>
    </select>
    <input type="hidden" name="previousSelection" value="">
    <a href="javascript:popLookupSelectMultiple('selectedList','1','lookup_opportunity_types');">Select</a>
  </td>
  </tr>    
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Component Description
    </td>
    <td width="100%">
      <input type="text" size="50" name="description" value="<%= toHtmlValue(oppComponentDetails.getDescription()) %>">
      <font color="red">*</font> <%= showAttribute(request, "componentDescriptionError") %>
    </td>
  </tr>  
  
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">Additional Notes</td>
    <td><TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(oppComponentDetails.getNotes()) %></TEXTAREA></td>
  </tr>  

  <tr class="containerBody">
    <td nowrap class="formLabel">
      Source
    </td>
    <td>
      <%= busTypeList.getHtml() %>
    </td>
  </tr>

  <tr class="containerBody">
    <td nowrap class="formLabel">
      Prob. of Close
    </td>
    <td>
      <input type=text size=5 name="closeProb" value="<%= oppComponentDetails.getCloseProbValue() %>">%
      <font color=red>*</font> <%= showAttribute(request, "closeProbError") %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Close Date
    </td>
    <td>
      <input type=text size=10 name="closeDate" value="<%= toHtmlValue(oppComponentDetails.getCloseDateString()) %>">
      <a href="javascript:popCalendar('addOpportunity', 'closeDate');">Date</a> (mm/dd/yyyy)
      <font color=red>*</font> <%= showAttribute(request, "closeDateError") %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Low Estimate
    </td>
    <td>
      <input type=text size=10 name="low" value="<%= toHtmlValue(oppComponentDetails.getLowAmount()) %>">
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Best Guess Estimate
    </td>
    <td>
      <input type=text size=10 name="guess" value="<%= toHtmlValue(oppComponentDetails.getGuessAmount()) %>">
      <font color=red>*</font> <%= showAttribute(request, "guessError") %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      High Estimate
    </td>
    <td>
      <input type=text size=10 name="high" value="<%= toHtmlValue(oppComponentDetails.getHighAmount()) %>">
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Term
    </td>
    <td>
      <input type="text" size="5" name="terms" value="<%= toHtmlValue(oppComponentDetails.getTermsString()) %>">
      <%= unitTypeList.getHtml() %>
      <font color="red">*</font> <%= showAttribute(request, "termsError") %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Current Stage
    </td>
    <td>
      <%= stageList.getHtmlSelect("stage", oppComponentDetails.getStage()) %>
      <input type="checkbox" name="closeNow">Close this component
    </td>
  </tr>
    
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Commission
    </td>
    <td>
      <input type="text" size="5" name="commission" value="<%= oppComponentDetails.getCommissionValue() %>">%
      <input type="hidden" name="contactLink" value="<%=request.getParameter("contactId")%>">
      
      <input type="hidden" name="headerId" value="<%= opportunityHeader.getId() %>">
      <input type="hidden" name="id" value="<%= opportunityHeader.getId() %>">
      <input type="hidden" name="contactId" value="<%=request.getParameter("contactId")%>">
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Description
    </td>
    <td valign=center>
      <input type="text" size="50" name="alertText" value="<%= toHtmlValue(oppComponentDetails.getAlertText()) %>"><br>
    </td>
  </tr>
  
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td valign="center">
      <input type="text" size="10" name="alertDate" value="<%= toHtmlValue(oppComponentDetails.getAlertDateString()) %>">
      <a href="javascript:popCalendar('addOpportunity', 'alertDate');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>

</table>
&nbsp;
<br>
  <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=DetailsOpp&id=<%= opportunityHeader.getId() %>&contactId=<%= contactDetails.getId() %>';this.form.dosubmit.value='false';">
  <input type="reset" value="Reset">
  <input type="hidden" name="dosubmit" value="true">
  </td>
</tr>
</table>
</form>
</body>

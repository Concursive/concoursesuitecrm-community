<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.beans.OpportunityBean" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="OppDetails" class="org.aspcfs.modules.pipeline.beans.OpportunityBean" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="BusTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="UnitTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].header_description.focus();">
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/popLookupSelect.js"></script>
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
<form name="addOpportunity" action="Opportunities.do?command=Insert&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<a href="/Accounts.do">Account Management</a> > 
<a href="/Accounts.do?command=View">View Accounts</a> >
<a href="/Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="/Opportunities.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Opportunities</a> >
Add Opportunity<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="opportunities" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';">
<input type="reset" value="Reset">
<br>
<%= showError(request, "actionError") %>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Opportunity details</strong>
    </td>     
  </tr>

  <tr class="containerBody">
    <td nowrap class="formLabel">
      Description
    </td>
    <td width="100%">
      <input type=text size=50 name="header_description" value="<%= toHtmlValue(OppDetails.getHeader().getDescription()) %>">
      <font color=red>*</font> <%= showAttribute(request, "descriptionError") %>
    </td>
  </tr>
  
    </table>
&nbsp;<br>
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Add a Component</strong>
    </td>     
  </tr>

  <tr class="containerBody">
  <td nowrap class="formLabel" valign="top">
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
      <input type=text size=50 name="component_description" value="<%= toHtmlValue(OppDetails.getComponent().getDescription()) %>">
      <font color=red>*</font> <%= showAttribute(request, "componentDescriptionError") %>
    </td>
  </tr>  
  
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">Additional Notes</td>
    <td><TEXTAREA NAME='component_notes' ROWS=3 COLS=50><%= toString(OppDetails.getComponent().getNotes()) %></TEXTAREA></td>
  </tr>  

  <tr class="containerBody">
    <td nowrap class="formLabel">
      Source
    </td>
    <td>
      <% BusTypeList.setSelectName("component_type");%>
      <%= BusTypeList.getHtml() %>
    </td>
  </tr>

  <tr class="containerBody">
    <td nowrap class="formLabel">
      Prob. of Close
    </td>
    <td>
      <input type=text size=5 name="component_closeProb" value="<%= OppDetails.getComponent().getCloseProbValue() %>">%
      <font color=red>*</font> <%= showAttribute(request, "closeProbError") %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Close Date
    </td>
    <td>
      <input type=text size=10 name="component_closeDate" value="<%= toHtmlValue(OppDetails.getComponent().getCloseDateString()) %>">
      <a href="javascript:popCalendar('addOpportunity', 'component_closeDate');">Date</a> (mm/dd/yyyy)
      <font color=red>*</font> <%= showAttribute(request, "closeDateError") %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Low Estimate
    </td>
    <td>
      <input type=text size=10 name="component_low" value="<%= toHtmlValue(OppDetails.getComponent().getLowAmount()) %>">
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Best Guess Estimate
    </td>
    <td>
      <input type=text size=10 name="component_guess" value="<%= toHtmlValue(OppDetails.getComponent().getGuessAmount()) %>">
      <font color=red>*</font> <%= showAttribute(request, "guessError") %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      High Estimate
    </td>
    <td>
      <input type=text size=10 name="component_high" value="<%= toHtmlValue(OppDetails.getComponent().getHighAmount()) %>">
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Term
    </td>
    <td>
      <input type=text size=5 name="component_terms" value="<%= toHtmlValue(OppDetails.getComponent().getTermsString()) %>">
      <% UnitTypeList.setSelectName("component_units");%>
      <%= UnitTypeList.getHtml() %>
      <font color="red">*</font> <%= showAttribute(request, "termsError") %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Current Stage
    </td>
    <td>
      <%=StageList.getHtmlSelect("component_stage",OppDetails.getComponent().getStage())%>
      <input type=checkbox name="closeNow">Close this component
    </td>
  </tr>
    
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Commission
    </td>
    <td>
      <input type=text size=5 name="component_commission" value="<%= OppDetails.getComponent().getCommissionValue() %>">%
      <input type=hidden name="header_accountLink" value="<%=request.getParameter("orgId")%>">
      <input type=hidden name="orgId" value="<%=request.getParameter("orgId")%>">
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Description
    </td>
    <td valign=center colspan=1>
      <input type=text size=50 name="component_alertText" value="<%= toHtmlValue(OppDetails.getComponent().getAlertText()) %>"><br>
    </td>
  </tr>
  
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td valign=center colspan=1>
      <input type=text size=10 name="component_alertDate" value="<%= toHtmlValue(OppDetails.getComponent().getAlertDateString()) %>">
      <a href="javascript:popCalendar('addOpportunity', 'component_alertDate');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
  
</table>
&nbsp;
<br>
<input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';">
<input type="reset" value="Reset">
<input type="hidden" name="dosubmit" value="true">
    </td>
  </tr>
</table>
</form>
</body>

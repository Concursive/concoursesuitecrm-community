<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="contactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="oppComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="busTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="stageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="unitTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="userList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
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
      var test = document.updateOpp.selectedList;
      if (test != null) {
        return selectAllOptions(document.updateOpp.selectedList);
      }
    }
  }
</SCRIPT>
<form name="updateOpp" action="ExternalContactsOppComponents.do?command=UpdateComponent&contactId=<%= contactDetails.getId() %>&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= contactDetails.getId() %>">Contact Details</a> >
<a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= contactDetails.getId() %>">Opportunities</a> >
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	  <a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= oppComponentDetails.getId() %>&contactId=<%= contactDetails.getId() %>">Opportunity Details</a> >
  <%}%>
<%} else {%>
<a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= oppComponentDetails.getHeaderId() %>&contactId=<%= contactDetails.getId() %>">Opportunity Details</a> >
<a href="ExternalContactsOppComponents.do?command=DetailsComponent&id=<%= oppComponentDetails.getId() %>&contactId=<%= contactDetails.getId() %>">Component Details</a> >
<%}%>
Modify Component<br>
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
<input type="hidden" name="id" value="<%= oppComponentDetails.getId() %>">
<input type="hidden" name="headerId" value="<%= oppComponentDetails.getHeaderId() %>">
<input type="hidden" name="modified" value="<%= oppComponentDetails.getModified() %>">
<% if (request.getParameter("return") != null) {%>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= oppComponentDetails.getHeaderId() %>&contactId=<%= contactDetails.getId() %>';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOppComponents.do?command=DetailsComponent&id=<%= oppComponentDetails.getId() %>&contactId=<%= contactDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>
<input type="reset" value="Reset">
<br>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong><%= oppComponentDetails.getDescription() %></strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Reassign To
    </td>
    <td>
      <%= userList.getHtmlSelect("owner", oppComponentDetails.getOwner() ) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Opportunity<br>Type(s)
    </td>
  	<td>
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <select multiple name="selectedList" id="selectedList" size="5">
            <dhv:evaluate exp="<%= oppComponentDetails.getTypes().isEmpty() %>">
              <option value="-1">None Selected</option>
            </dhv:evaluate>
            
            <dhv:evaluate exp="<%= !(oppComponentDetails.getTypes().isEmpty()) %>">
<%
              Iterator i = oppComponentDetails.getTypes().iterator();
              while (i.hasNext()) {
                LookupElement thisElt = (LookupElement)i.next();
%>
              <option value="<%=thisElt.getCode()%>"><%=thisElt.getDescription()%></option>
            <%}%>
            </dhv:evaluate>      
            </select>
          </td>
          <td valign="top">
            <input type="hidden" name="previousSelection" value="">
            &nbsp;[<a href="javascript:popLookupSelectMultiple('selectedList','1','lookup_opportunity_types');">Select</a>]
          </td>
        </tr>
      </table>
    </td>
  </tr> 
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Description
    </td>
    <td valign=center>
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
      <input type="text" size="5" name="closeProb" value="<%= oppComponentDetails.getCloseProbValue() %>">%
      <font color="red">*</font> <%= showAttribute(request, "closeProbError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Close Date
    </td>
    <td>
      <input type="text" size="10" name="closeDate" value="<%= toHtmlValue(oppComponentDetails.getCloseDateString()) %>">
      <a href="javascript:popCalendar('updateOpp', 'closeDate');">Date</a> (mm/dd/yyyy)
      <font color="red">*</font> <%= showAttribute(request, "closeDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Low Estimate
    </td>
    <td>
      <input type="text" size="10" name="low" value="<%= oppComponentDetails.getLowAmount() %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Best Guess Estimate
    </td>
    <td valign=center>
      <input type="text" size="10" name="guess" value="<%= oppComponentDetails.getGuessAmount() %>">
      <font color="red">*</font> <%= showAttribute(request, "guessError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      High Estimate
    </td>
    <td>
      <input type="text" size="10" name="high" value="<%= oppComponentDetails.getHighAmount() %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Term
    </td>
    <td>
      <input type="text" size="5" name="terms" value="<%= oppComponentDetails.getTermsString() %>">
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
      <input type="checkbox" name="closeNow" <dhv:evaluate if="<%= oppComponentDetails.getClosed() != null %>">checked</dhv:evaluate>>
      Closed 
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Commission
    </td>
    <td>
      <input type="text" size="5" name="commission" value="<%= oppComponentDetails.getCommissionValue() %>">%
      <input type="hidden" name="accountLink" value="<%=request.getParameter("orgId")%>">
      <input type="hidden" name="orgId" value="<%=request.getParameter("orgId")%>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Description
    </td>
    <td>
      <input type="text" size="50" name="alertText" value="<%= toHtmlValue(oppComponentDetails.getAlertText()) %>"><br>
    </td>
  </tr>
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td>
      <input type="text" size="10" name="alertDate" value="<%= toHtmlValue(oppComponentDetails.getAlertDateStringLongYear()) %>">
      <a href="javascript:popCalendar('updateOpp', 'alertDate');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
</table>
&nbsp;
<br>
<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= oppComponentDetails.getHeaderId() %>&contactId=<%= contactDetails.getId() %>';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOppComponents.do?command=DetailsComponent&id=<%= oppComponentDetails.getId() %>&contactId=<%= contactDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>
<input type="reset" value="Reset">
<input type="hidden" name="dosubmit" value="true">
  </td>
  </tr>
  </table>
</form>

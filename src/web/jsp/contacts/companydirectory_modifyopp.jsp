<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.webutils.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<jsp:useBean id="OppDetails" class="com.darkhorseventures.cfsbase.Opportunity" scope="request"/>
<jsp:useBean id="BusTypeList" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<jsp:useBean id="StageList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="UnitTypeList" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<jsp:useBean id="UserList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<%@ include file="initPage.jsp" %>
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
<form name="updateOpp" action="ExternalContactsOpps.do?command=UpdateOpp&contactId=<%= ContactDetails.getId() %>&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<a href="ExternalContacts.do">Contacts &amp; Resources</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	  <a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%=ContactDetails.getId()%>">Opportunities</a> >
  <%}%>
<%} else {%>
<a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%=ContactDetails.getId()%>">Opportunities</a> >
<a href="ExternalContactsOpps.do?command=DetailsOpp&id=<%=OppDetails.getId()%>&contactId=<%=ContactDetails.getId()%>">Opportunity Details</a> >
<%}%>
Modify Opportunity<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + ContactDetails.getId(); %>      
      <dhv:container name="contacts" selected="opportunities" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<input type="hidden" name="id" value="<%= OppDetails.getId() %>">
<input type="hidden" name="modified" value="<%= OppDetails.getModified() %>">

<% if (request.getParameter("return") != null) {%>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>

<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=DetailsOpp&id=<%= OppDetails.getId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>
<input type="reset" value="Reset">
<br>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">

<tr class="title">
  <td colspan=2 valign=center align=left>
    <strong><%=OppDetails.getDescription()%></strong>
      <% if (OppDetails.hasFiles()) { %>
      <% FileItem thisFile = new FileItem(); %>
      <%= thisFile.getImageTag()%>
      <%}%>         
  </td>     
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Reassign To
  </td>
  <td valign=center>
    <%= UserList.getHtmlSelect("owner", OppDetails.getOwner() ) %>
  </td>
</tr>

  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Opportunity<br>Type(s)
    </td>
  
  	<td valign=center>
      
      <select multiple name="selectedList" id="selectedList" size="5">
      <dhv:evaluate exp="<%=OppDetails.getTypes().isEmpty()%>">
      <option value="-1">None Selected</option>
      </dhv:evaluate>
      
      <dhv:evaluate exp="<%=!(OppDetails.getTypes().isEmpty())%>">
       <%
        Iterator i = OppDetails.getTypes().iterator();
        
        while (i.hasNext()) {
          LookupElement thisElt = (LookupElement)i.next();
      %>
        <option value="<%=thisElt.getCode()%>"><%=thisElt.getDescription()%></option>
      <%}%>
      </dhv:evaluate>      
      </select>
      
      <input type="hidden" name="previousSelection" value="">
      <a href="javascript:popLookupSelectMultiple('selectedList','1','lookup_opportunity_types');">Select</a>
  </td>
  </tr> 

<tr class="containerBody">
  <td nowrap class="formLabel">
    Description
  </td>
  <td valign=center>
    <input type=text size=50 name="description" value="<%= toHtmlValue(OppDetails.getDescription()) %>">
    <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
  </td>
</tr>

  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">Additional Notes</td>
    <td><TEXTAREA NAME='notes' ROWS=3 COLS=50><%= toString(OppDetails.getNotes()) %></TEXTAREA></td>
  </tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Source
  </td>
  <td valign=center>
    <%= BusTypeList.getHtml() %>
  </td>
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Prob. of Close
  </td>
  <td valign=center>
    <input type=text size=5 name="closeProb" value="<%= OppDetails.getCloseProbValue() %>">%
    <font color="red">*</font> <%= showAttribute(request, "closeProbError") %>
  </td>
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Est. Close Date
  </td>
  <td valign=center>
    <input type=text size=10 name="closeDate" value="<%= toHtmlValue(OppDetails.getCloseDateString()) %>">
    <a href="javascript:popCalendar('updateOpp', 'closeDate');">Date</a> (mm/dd/yyyy)
    <font color="red">*</font> <%= showAttribute(request, "closeDateError") %>
  </td>
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Low Estimate
  </td>
  <td valign=center>
    <input type=text size=10 name="low" value="<%= OppDetails.getLowAmount() %>">
  </td>
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Best Guess Estimate
  </td>
  <td valign=center>
    <input type=text size=10 name="guess" value="<%= OppDetails.getGuessAmount() %>">
    <font color="red">*</font> <%= showAttribute(request, "guessError") %>
  </td>
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    High Estimate
  </td>
  <td valign=center>
    <input type=text size=10 name="high" value="<%= OppDetails.getHighAmount() %>">
  </td>
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Est. Term
  </td>
  <td valign=center>
    <input type=text size=5 name="terms" value="<%= OppDetails.getTermsString() %>">
    <%= UnitTypeList.getHtml() %>
    <font color="red">*</font> <%= showAttribute(request, "termsError") %>
  </td>
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Current Stage
  </td>
  <td valign=center>
     <%=StageList.getHtmlSelect("stage",OppDetails.getStage())%>
          <input type=checkbox name="closeNow"
      
      <% if (OppDetails.getClosed() != null) {%>
       		checked
      <%}%>
      
      >Closed 
  </td>
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Est. Commission
  </td>
  <td valign=center>
    <input type=text size=5 name="commission" value="<%= OppDetails.getCommissionValue() %>">%
    <input type=hidden name="accountLink" value="<%=request.getParameter("orgId")%>">
    <input type=hidden name="orgId" value="<%=request.getParameter("orgId")%>">
  </td>
</tr>


  <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Description
    </td>
    <td valign=center colspan=1>
      <input type=text size=50 name="alertText" value="<%= toHtmlValue(OppDetails.getAlertText()) %>"><br>
    </td>
  </tr>
  
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td valign=center colspan=1>
              <input type=text size=10 name="alertDate" value="<%= toHtmlValue(OppDetails.getAlertDateStringLongYear()) %>">
      <a href="javascript:popCalendar('addOpportunity', 'alertDate');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
</table>
&nbsp;
<br>
<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=DetailsOpp&id=<%= OppDetails.getId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>
<input type="reset" value="Reset">
<input type="hidden" name="dosubmit" value="true">
  </td>
  </tr>
  </table>
</form>

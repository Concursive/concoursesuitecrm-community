<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OpportunityDetails" class="com.darkhorseventures.cfsbase.Opportunity" scope="request"/>
<jsp:useBean id="BusTypeList" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<jsp:useBean id="StageList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="UnitTypeList" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<jsp:useBean id="UserList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/checkDate.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
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
        return true;
      }
    }
</SCRIPT>
<form name="updateOpp" action="/Leads.do?command=UpdateOpp&auto-populate=true" method="post" onSubmit="return checkForm(this);">

<a href="/Leads.do">Pipeline Management</a> > 
<% if (request.getParameter("return") == null) { %>
	<a href="/Leads.do?command=ViewOpp">View Opportunities</a> >
	<a href="/Leads.do?command=DetailsOpp&id=<%=OpportunityDetails.getId()%>">Opportunity Details</a> >
<%} else {%>
	<% if (request.getParameter("return").equals("list")) { %>
		<a href="/Leads.do?command=ViewOpp">View Opportunities</a> >
	<%}%>
<%}%>
Modify Opportunity<br>
<hr color="#BFBFBB" noshade>


<a href="Leads.do?command=ViewOpp">Back to Opportunities List</a><br>&nbsp;


<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OpportunityDetails.getDescription()) %></strong>&nbsp;
      	<dhv:evaluate exp="<%=(OpportunityDetails.getAccountEnabled() && OpportunityDetails.getAccountLink() > -1)%>">
        <dhv:permission name="accounts-view,accounts-accounts-view">[ <a href="/Accounts.do?command=Details&orgId=<%=OpportunityDetails.getAccountLink()%>">Go to this Account</a> ]</dhv:permission>
	</dhv:evaluate>
	  
	<dhv:evaluate exp="<%=(OpportunityDetails.getContactLink() > -1)%>">
	<dhv:permission name="contacts-view,contacts-external_contacts-view">[ <a href="/ExternalContacts.do?command=ContactDetails&id=<%=OpportunityDetails.getContactLink()%>">Go to this Contact</a> ]</dhv:permission>
	</dhv:evaluate>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + OpportunityDetails.getId(); %>      
      <dhv:container name="opportunities" selected="details" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">


<input type="hidden" name="id" value="<%= OpportunityDetails.getId() %>">
<input type="hidden" name="modified" value="<%= OpportunityDetails.getModified() %>">

<% if (request.getParameter("return") != null) {%>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>

<input type="submit" value="Update">

    <% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='/Leads.do?command=ViewOpp'">
	<%}%>
    <%} else {%>
    	<input type="submit" value="Cancel" onClick="javascript:this.form.action='/Leads.do?command=DetailsOpp&id=<%= OpportunityDetails.getId() %>'">
<%}%>
    
<input type="reset" value="Reset">
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">

<tr bgcolor="#DEE0FA">
  <td colspan=2 valign=center align=left>
    <strong>Modify Primary Information</strong>
  </td>     
</tr>

<tr class="containerBody">
<td nowrap class="formLabel">
Reassign To
</td>
<td colspan=1 valign=center>
<%= UserList.getHtmlSelect("owner", OpportunityDetails.getOwner() ) %>
</td>
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Description
  </td>
  <td colspan=1 valign=center>
    <input type=text size=35 name="description" value="<%= toHtmlValue(OpportunityDetails.getDescription()) %>">
    <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
  </td>
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Type of Business
  </td>
  <td colspan=1 valign=center>
    <%= BusTypeList.getHtml() %>
  </td>
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Prob. of Close
  </td>
  <td colspan=1 valign=center>
    <input type=text size=5 name="closeProb" value="<%= OpportunityDetails.getCloseProbValue() %>">%
    <font color="red">*</font> <%= showAttribute(request, "closeProbError") %>
  </td>
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Est. Close Date
  </td>
  <td valign=center>
    <input type=text size=10 name="closeDate" value="<%= toHtmlValue(OpportunityDetails.getCloseDateString()) %>">
    <a href="javascript:popCalendar('updateOpp', 'closeDate');">Date</a> (mm/dd/yyyy)
    <font color="red">*</font> <%= showAttribute(request, "closeDateError") %>
  </td>
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Est. Terms
  </td>
  <td colspan=1 valign=center>
    <input type=text size=5 name="terms" value="<%= OpportunityDetails.getTerms() %>">
    <%= UnitTypeList.getHtml() %>
    <font color="red">*</font> <%= showAttribute(request, "termsError") %>
  </td>
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Low Estimate
  </td>
  <td colspan=1 valign=center>
    <input type=text size=10 name="low" value="<%= OpportunityDetails.getLowAmount() %>">
  </td>
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Best Guess Estimate
  </td>
  <td colspan=1 valign=center>
    <input type=text size=10 name="guess" value="<%= OpportunityDetails.getGuessAmount() %>">
  </td>
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    High Estimate
  </td>
  <td colspan=1 valign=center>
    <input type=text size=10 name="high" value="<%= OpportunityDetails.getHighAmount() %>">
  </td>
</tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Current Stage
  </td>
  <td colspan=1 valign=center>
    <%=StageList.getHtmlSelect("stage",OpportunityDetails.getStage())%>
          <input type=checkbox name="closeNow"
      
      <% if (OpportunityDetails.getClosed() != null) {%>
       		checked
      <%}%>
      
      >Closed 
  </td>
</tr>
	
<tr class="containerBody">
  <td nowrap class="formLabel">
    Est. Commission
  </td>
  <td colspan=1 valign=center>
    <input type=text size=5 name="commission" value="<%= OpportunityDetails.getCommissionValue() %>">%
    <input type=hidden name="accountLink" value="<%=request.getParameter("orgId")%>">
    <input type=hidden name="orgId" value="<%=request.getParameter("orgId")%>">
  </td>
</tr>

  <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Description
    </td>
    <td valign=center colspan=1>
      <input type=text size=50 name="alertText" value="<%= toHtmlValue(OpportunityDetails.getAlertText()) %>"><br>
    </td>
  </tr>

<tr class="containerBody">
  <td nowrap class="formLabel">
    Alert Date
  </td>
  <td colspan=1 valign=center>
    <input type=text size=10 name="alertDate" value="<%= toHtmlValue(OpportunityDetails.getAlertDateStringLongYear()) %>">
    <a href="javascript:popCalendar('updateOpp', 'alertDate');">Date</a> (mm/dd/yyyy)
  </td>
</tr>
</table>
&nbsp;
<br>
<input type="submit" value="Update">
    <% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='/Leads.do?command=ViewOpp'">
	<%}%>
    <%} else {%>
    	<input type="submit" value="Cancel" onClick="javascript:this.form.action='/Leads.do?command=DetailsOpp&id=<%= OpportunityDetails.getId() %>'">
<%}%>
<input type="reset" value="Reset">
</td></tr>
</table>
</form>
</body>

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
      if (formTest == false) {
        alert("Form could not be saved, please check the following:\r\n\r\n" + message);
        return false;
      } else {
        return true;
      }
    }
</SCRIPT>
<form name="updateOpp" action="/Leads.do?command=UpdateOpp&auto-populate=true" method="post" onSubmit="return checkForm(this);">
<a href="Leads.do?command=ViewOpp">Back to Opportunities List</a><br>&nbsp;


<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OpportunityDetails.getDescription()) %></strong>&nbsp;&nbsp;
      
      	<% 
	  if (OpportunityDetails.getAccountLink() != -1) { %>
	  	[ <a href="/Accounts.do?command=Details&orgId=<%=OpportunityDetails.getAccountLink()%>">Go to this Account</a> ]
	  <%} else { %>
	  	[ <a href="/ExternalContacts.do?command=ContactDetails&id=<%=OpportunityDetails.getContactLink()%>">Go to this Contact</a> ]
	  <%}%>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <a href="/Leads.do?command=DetailsOpp&id=<%= OpportunityDetails.getId() %>"><font color="#0000FF">Details</font></a> | 
      <a href="/LeadsCalls.do?command=View&oppId=<%= OpportunityDetails.getId() %>"><font color="#000000">Calls</font></a> |
      <a href="LeadsDocuments.do?command=View&oppId=<%= OpportunityDetails.getId() %>"><font color="#000000">Documents</font></a>
    </td>
  </tr>
  <tr>
    <td class="containerBack">


<input type="hidden" name="id" value="<%= OpportunityDetails.getId() %>">
<input type="hidden" name="modified" value="<%= OpportunityDetails.getModified() %>">

<input type="submit" value="Update">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/Leads.do?command=DetailsOpp&id=<%= OpportunityDetails.getId() %>'">
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
    Alert Date
  </td>
  <td colspan=1 valign=center>
    <input type=text size=10 name="alertDate" value="<%= toHtmlValue(OpportunityDetails.getAlertDateString()) %>">
    <a href="javascript:popCalendar('updateOpp', 'alertDate');">Date</a> (mm/dd/yyyy)
  </td>
</tr>
</table>
&nbsp;
<br>
<input type="submit" value="Update">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/Leads.do?command=DetailsOpp&id=<%= OpportunityDetails.getId() %>'">
<input type="reset" value="Reset">
</td></tr>
</table>
</form>
</body>

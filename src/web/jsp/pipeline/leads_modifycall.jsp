<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OpportunityDetails" class="com.darkhorseventures.cfsbase.Opportunity" scope="request"/>
<jsp:useBean id="CallDetails" class="com.darkhorseventures.cfsbase.Call" scope="request"/>
<jsp:useBean id="CallTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>

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
</script>

<form name="addCall" action="/LeadsCalls.do?command=Update&id=<%= CallDetails.getId() %>&oppId=<%= OpportunityDetails.getId() %>&auto-populate=true" onSubmit="return doCheck(this);" method="post">

<a href="/Leads.do">Pipeline Management</a> > 
<a href="/Leads.do?command=ViewOpp">View Opportunities</a> >
<a href="/Leads.do?command=DetailsOpp&id=<%=OpportunityDetails.getId()%>">Opportunity Details</a> >
<a href="/LeadsCalls.do?command=View&oppId=<%=OpportunityDetails.getId()%>">Calls</a> >
<% if (request.getParameter("return") == null) { %>
	<a href="/LeadsCalls.do?command=Details&id=<%=CallDetails.getId()%>&oppId=<%=OpportunityDetails.getId()%>">Call Details</a> >
<%}%>
Modify Call<br>
<hr color="#BFBFBB" noshade>
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
  
        <% if (OpportunityDetails.hasFiles()) { %>
      <% FileItem thisFile = new FileItem(); %>
      <%= thisFile.getImageTag()%>
      <%}%>    
  
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + OpportunityDetails.getId(); %>      
      <dhv:container name="opportunities" selected="calls" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<input type="hidden" name="modified" value="<%= CallDetails.getModified() %>">
<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">

<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='/LeadsCalls.do?command=View&oppId=<%=OpportunityDetails.getId()%>';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:form.action='/LeadsCalls.do?command=Details&id=<%= CallDetails.getId() %>&oppId=<%= OpportunityDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>

<input type="reset" value="Reset">
<br>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Call Details</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Type
    </td>
    <td width=100%>
      <%= CallTypeList.getHtmlSelect("callTypeId", CallDetails.getCallTypeId()) %>
    </td>
  </tr>
 
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Length
    </td>
    <td>
      <input type=text size=5 name="length" value="<%= toHtmlValue(CallDetails.getLengthString()) %>"> minutes  <%= showAttribute(request, "lengthError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Subject
    </td>
    <td>
      <input type=text size=50 name="subject" value="<%= toHtmlValue(CallDetails.getSubject()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel" width=100%>
      Notes
    </td>
    <td>
      <TEXTAREA NAME='notes' ROWS=3 COLS=50><%= toString(CallDetails.getNotes()) %></TEXTAREA>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel" width="100%">
      Alert Description
    </td>
    <td>
      <input type=text size=50 name="alertText" value="<%= toHtmlValue(CallDetails.getAlertText()) %>">
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel" width=100%>
      Alert Date
    </td>
    <td>
      <input type=text size=10 name="alertDate" value="<%= toHtmlValue(CallDetails.getAlertDateStringLongYear()) %>"> 
      <a href="javascript:popCalendar('addCall', 'alertDate');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
  
  <!--tr class="containerBody">
    <td align=center colspan=2>
      <i>Created <%= toHtml(CallDetails.getEnteredString()) %> by <%= toHtml(CallDetails.getEnteredName()) %> - 
      Modifed <%= toHtml(CallDetails.getModifiedString()) %> by <%= toHtml(CallDetails.getModifiedName()) %></i>
    </td>
  </tr-->
</table>
<br>
<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">

<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='/LeadsCalls.do?command=View&oppId=<%=OpportunityDetails.getId()%>';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:form.action='/LeadsCalls.do?command=Details&id=<%= CallDetails.getId() %>&oppId=<%= OpportunityDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>

<input type="reset" value="Reset">
<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="contactId" value="<%=CallDetails.getContactId()%>">

<% if (request.getParameter("return") != null) {%>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>

</td>
</tr>
</table>

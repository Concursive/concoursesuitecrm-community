<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.OpportunityHeader,com.zeroio.iteam.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="OppDetails" class="org.aspcfs.modules.pipeline.beans.OpportunityBean" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].header_description.focus();">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript" src="javascript/popContacts.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
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
  alertMessage = "";
  if ((!form.component_closeDate.value == "") && (!checkDate(form.component_closeDate.value))) { 
    message += "- Check that Est. Close Date is entered correctly\r\n";
    formTest = false;
  }
  if (form.component_low.value != "" && form.component_low.value != "" && (parseInt(form.component_low.value) > parseInt(form.component_high.value))) { 
    message += "- Low Estimate cannot be higher than High Estimate\r\n";
    formTest = false;
  }
  if ((!form.component_alertDate.value == "") && (!checkDate(form.component_alertDate.value))) { 
    message += "- Check that Alert Date is entered correctly\r\n";
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
  if (!checkNumber(form.component_commission.value)) { 
    message += "- Commission entered is invalid\r\n";
    formTest = false;
  }
  if ((!form.component_alertDate.value == "") && (!checkAlertDate(form.component_alertDate.value))) { 
      alertMessage += "Alert Date is before today's date\r\n";
  }
  if (formTest == false) {
    alert("Form could not be saved, please check the following:\r\n\r\n" + message);
    return false;
  } else {
    if(alertMessage != ""){
       return confirmAction(alertMessage);
    }else{
      var test = document.opportunityForm.selectedList;
      if (test != null) {
        return selectAllOptions(document.opportunityForm.selectedList);
      }
    }
  }
}
</script>

<%
  OpportunityHeader opportunityHeader = OppDetails.getHeader();
	OpportunityComponent ComponentDetails = OppDetails.getComponent();
%>

<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Accounts.do?command=Search">Search Results</a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard">Dashboard</a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Contacts</a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Contact Details</a> >
<a href="AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>">Opportunities</a> >
Add Opportunity
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%-- include the account header --%>
<%@ include file="accounts_details_header_include.jsp" %>
<%-- load the accounts menu --%> 
<dhv:container name="accounts" selected="contacts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<%-- actual opportunity add form --%>
<form name="opportunityForm" action="AccountContactsOpps.do?command=Save&contactId=<%= ContactDetails.getId() %>&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
    <% String param1 = "id=" + ContactDetails.getId(); 
    %>
        <strong><%= ContactDetails.getNameLastFirst() %>:</strong>
        [ <dhv:container name="accountscontacts" selected="opportunities" param="<%= param1 %>"/> ]
      <br>
      <br>
      
      <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>';this.form.dosubmit.value='false';">
      <br>
      <%= showError(request, "actionError") %>
      <%--  include basic opportunity form --%>
      <%@ include file="../pipeline/opportunity_include.jsp" %>
      &nbsp;
      <br>
      <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>';this.form.dosubmit.value='false';">
      <input type="hidden" name="dosubmit" value="true">
      <input type="hidden" name="actionSource" value="AccountContactsOpps">
   </td>
 </tr>
</table>
</form>
</body>



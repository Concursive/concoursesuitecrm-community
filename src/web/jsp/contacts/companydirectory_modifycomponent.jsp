<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*,org.aspcfs.utils.web.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OpportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="ComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
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
    var test = document.opportunityForm.selectedList;
    if (test != null) {
      return selectAllOptions(document.opportunityForm.selectedList);
    }
  }
}
</SCRIPT>
<form name="opportunityForm" action="ExternalContactsOppComponents.do?command=SaveComponent&contactId=<%= ContactDetails.getId() %>&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<dhv:evaluate exp="<%= !isPopup(request) %>">
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>">Contact Details</a> >
<a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>">Opportunities</a> >
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	  <a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= ComponentDetails.getId() %>&contactId=<%= ContactDetails.getId() %>">Opportunity Details</a> >
  <%}%>
<%}else  {%>
<a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= ComponentDetails.getHeaderId() %>&contactId=<%= ContactDetails.getId() %>">Opportunity Details</a> >
<a href="ExternalContactsOppComponents.do?command=DetailsComponent&id=<%= ComponentDetails.getId() %>&contactId=<%= ContactDetails.getId() %>">Component Details</a> >
<%}%>
Modify Component<br>
<hr color="#BFBFBB" noshade>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + contactDetails.getId(); 
          String param2 = addLinkParams(request, "popup|popupType|actionId"); %>
      <dhv:container name="contacts" selected="opportunities" param="<%= param1 %>" appendToUrl="<%= param2 %>"/>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
 <% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= ComponentDetails.getHeaderId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
	<%}%>
 <% }else{ %>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOppComponents.do?command=DetailsComponent&id=<%= ComponentDetails.getId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
 <% } %>

  <input type="reset" value="Reset">
<dhv:evaluate exp="<%= isPopup(request)  && !isInLinePopup(request) %>">
  <input type="button" value="Cancel" onclick="javascript:window.close();">
</dhv:evaluate>
<br>
<%= showError(request, "actionError") %>

<%--  include basic opportunity form --%>
<%@ include file="../pipeline/opportunity_form.jsp" %>

&nbsp;
<br>

<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= ComponentDetails.getHeaderId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsOppComponents.do?command=DetailsComponent&id=<%= ComponentDetails.getId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>
<input type="reset" value="Reset">
<dhv:evaluate exp="<%= isPopup(request)  && !isInLinePopup(request) %>">
  <input type="button" value="Cancel" onclick="javascript:window.close();">
</dhv:evaluate>
<input type="hidden" name="dosubmit" value="true">
<%-- End container contents --%>
<dhv:evaluate exp="<%= !isPopup(request)  || isInLinePopup(request) %>">
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- End container --%>
<input type="hidden" name="id" value="<%= ComponentDetails.getId() %>">
<input type="hidden" name="headerId" value="<%= ComponentDetails.getHeaderId() %>">
<input type="hidden" name="modified" value="<%= ComponentDetails.getModified() %>">
<input type="hidden" name="actionSource" value="ExternalContactsOppComponents">
<dhv:evaluate if="<%= request.getParameter("return") != null %>">
  <input type="hidden" name="return" value="<%= request.getParameter("return") %>">
</dhv:evaluate>
<%= addHiddenParams(request, "popup|popupType|actionId") %>
</form>


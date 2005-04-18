<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*,org.aspcfs.utils.web.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="ComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
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
  if (form.low.value != "" && form.low.value != "" && (parseInt(form.low.value) > parseInt(form.high.value))) { 
    message += label("low.estimate", "- Low Estimate cannot be higher than High Estimate\r\n");
    formTest = false;
  }
  if ((!checkNullString(form.alertText.value)) && (checkNullString(form.alertDate.value))) { 
    message += label("specify.alert.date", "- Please specify an alert date\r\n");
    formTest = false;
  }
  if ((!checkNullString(form.alertDate.value)) && (checkNullString(form.alertText.value))) { 
    message += label("specify.alert.description", "- Please specify an alert description\r\n");
    formTest = false;
  }
  if (!checkNumber(form.commission.value)) { 
    message += label("commission.entered.invalid", "- Commission entered is invalid\r\n");
    formTest = false;
  }
  
  if (formTest == false) {
    alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
    return false;
  } else {
    var test = document.opportunityForm.selectedList;
    if (test != null) {
      return selectAllOptions(document.opportunityForm.selectedList);
    }
  }
}

function reopenOpportunity(id) {
  if (id == '<%= opportunityHeader.getId() %>') {
      if ('<%= opportunityHeader.getComponentCount() %>' == '0' || '<%= opportunityHeader.getComponentCount() %>' == '1' || '<%= opportunityHeader.getComponentCount() %>' == '-1') {
        scrollReload('ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %><%= isPopup(request)?"&popup=true":"" %>');
      } else {
        scrollReload('ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %><%= isPopup(request)?"&popup=true":"" %>');
      }
    return id;
  } else {
    return '<%= opportunityHeader.getId() %>';
  }
}
</SCRIPT>
<form name="opportunityForm" action="ExternalContactsOppComponents.do?command=SaveComponent&contactId=<%= ContactDetails.getId() %>&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> >
<a href="ExternalContacts.do?command=SearchContacts"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> >
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	  <a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= ComponentDetails.getId() %>&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> >
  <%}%>
<%}else  {%>
<a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= ComponentDetails.getHeaderId() %>&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> >
<a href="ExternalContactsOppComponents.do?command=DetailsComponent&id=<%= ComponentDetails.getId() %>&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.ComponentDetails">Component Details</dhv:label></a> >
<%}%>
<dhv:label name="accounts.accounts_contacts_oppcomponent_modify.ModifyComponent">Modify Component</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="contacts" selected="opportunities" object="ContactDetails" param="<%= "id=" + ContactDetails.getId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
  <img src="images/icons/stock_form-currency-field-16.gif" border="0" align="absmiddle">
  <strong><%= toHtml(opportunityHeader.getDescription()) %></strong>
  <% FileItem thisFile = new FileItem(); %>
  <dhv:evaluate if="<%= opportunityHeader.hasFiles() %>">
    <%= thisFile.getImageTag() %>
  </dhv:evaluate>
  <br />
  <br />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="this.form.dosubmit.value='true';" />
  <dhv:evaluate if="<%= !(isPopup(request)  && !isInLinePopup(request)) %>">
   <% if (request.getParameter("return") != null) {%>
    <% if (request.getParameter("return").equals("list")) {%>
    <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= ComponentDetails.getHeaderId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';" />
    <%}%>
   <% }else{ %>
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ExternalContactsOppComponents.do?command=DetailsComponent&id=<%= ComponentDetails.getId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';" />
   <% } %>
  </dhv:evaluate>
  <dhv:evaluate if="<%= isPopup(request)  && !isInLinePopup(request) %>">
    <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onclick="javascript:this.form.action='ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= ComponentDetails.getHeaderId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';" />
  </dhv:evaluate>
  <br />
  <dhv:formMessage />
  <%--  include basic opportunity form --%>
  <%@ include file="../pipeline/opportunity_include.jsp" %>
  <br />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="this.form.dosubmit.value='true';" />
  <dhv:evaluate if="<%= !(isPopup(request)  && !isInLinePopup(request)) %>">
  <% if (request.getParameter("return") != null) {%>
    <% if (request.getParameter("return").equals("list")) {%>
    <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= ComponentDetails.getHeaderId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';" />
    <%}%>
  <%} else {%>
    <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ExternalContactsOppComponents.do?command=DetailsComponent&id=<%= ComponentDetails.getId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';" />
  <%}%>
  </dhv:evaluate>
  <dhv:evaluate if="<%= isPopup(request)  && !isInLinePopup(request) %>">
    <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onclick="javascript:this.form.action='ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= ComponentDetails.getHeaderId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';" />
  </dhv:evaluate>
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="id" value="<%= ComponentDetails.getId() %>">
  <input type="hidden" name="headerId" value="<%= ComponentDetails.getHeaderId() %>">
  <input type="hidden" name="modified" value="<%= ComponentDetails.getModified() %>">
  <input type="hidden" name="actionSource" value="ExternalContactsOppComponents">
  <dhv:evaluate if="<%= request.getParameter("return") != null %>">
    <input type="hidden" name="return" value="<%= request.getParameter("return") %>">
  </dhv:evaluate>
  <%= addHiddenParams(request, "popup|popupType|actionId") %>
</dhv:container>
</form>

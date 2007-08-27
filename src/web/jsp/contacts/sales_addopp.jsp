<%-- 
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: companydirectory_addopp.jsp 21070 2007-04-18 19:07:15Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.pipeline.base.*" %>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="OppDetails" class="org.aspcfs.modules.pipeline.beans.OpportunityBean" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="from" class="java.lang.String" scope="request" />
<jsp:useBean id="listForm" class="java.lang.String" scope="request" />
<%@ include file="../initPage.jsp" %>
<dhv:include name="opportunity.singleComponent">
  <body onLoad="javascript:document.opportunityForm.component_description.focus();">
</dhv:include>
<dhv:include name="opportunity.singleComponent" none="true">
  <body onLoad="javascript:document.opportunityForm.header_description.focus();">
</dhv:include>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript" src="javascript/popContacts.js?v=20070827"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<%if (isPopup(request)) {%>
<script language="JavaScript" type="text/javascript" src="javascript/scrollReload.js"></script>
<%}%>
<%
  OpportunityHeader opportunityHeader = OppDetails.getHeader();
	OpportunityComponent ComponentDetails = OppDetails.getComponent();
  boolean allowMultiple = allowMultipleComponents(pageContext, OpportunityComponent.MULTPLE_CONFIG_NAME, "multiple");
%>
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
  <dhv:include name="opportunity.singleComponent">
    <dhv:evaluate if="<%= opportunityHeader.getId() == -1 %>">
      updateHeaderFields(form);
    </dhv:evaluate>
  </dhv:include>
  <dhv:include name="opportunity.lowEstimateCanNotBeZero" none="true">
  if (form.component_low.value != "" && form.component_low.value != "" && (parseInt(form.component_low.value) > parseInt(form.component_high.value))) { 
    message += label("low.estimate", "- Low Estimate cannot be higher than High Estimate\r\n");
    formTest = false;
  }
  </dhv:include>
  <dhv:include name="opportunity.alertDescription opportunity.alertDate,pipeline-alertdate" none="true">
  if ((!checkNullString(form.component_alertText.value)) && (checkNullString(form.component_alertDate.value))) { 
    message += label("specify.alert.date", "- Please specify an alert date\r\n");
    formTest = false;
  }
  if ((!checkNullString(form.component_alertDate.value)) && (checkNullString(form.component_alertText.value))) { 
    message += label("specify.alert.description", "- Please specify an alert description\r\n");
    formTest = false;
  }
  </dhv:include>
  <dhv:include name="opportunity.estimatedCommission,pipeline-commission" none="true">
  if (!checkNumber(form.component_commission.value)) { 
    message += label("commission.entered.invalid", "- Commission entered is invalid\r\n");
    formTest = false;
  }
  </dhv:include>
  if (formTest == false) {
    alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
    return false;
  } else {
    if(alertMessage != ""){
       return confirmAction(alertMessage);
    }else{
  <dhv:include name="opportunity.componentTypes" none="true">
      var test = document.opportunityForm.selectedList;
      if (test != null) {
        return selectAllOptions(document.opportunityForm.selectedList);
      }
  </dhv:include>
    }
  }
}
</script>
<form name="opportunityForm" action="SalesOpportunities.do?command=Save&contactId=<%= ContactDetails.getId() %>&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>" onSubmit="return doCheck(this);" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Sales.do"><dhv:label name="Leads" mainMenuItem="true">Leads</dhv:label></a> >
<% if (from != null && "list".equals(from) && !"dashboard".equals(from)) { %>
  <a href="Sales.do?command=List&listForm=<%= (listForm != null? listForm:"") %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%}%>
<a href="Sales.do?command=Details&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>"><dhv:label name="sales.leadDetails">Lead Details</dhv:label></a> >
<a href="SalesOpportunities.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> >
<dhv:label name="accounts.accounts_contacts_oppcomponent_add.AddOpportunity">Add Opportunity</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="leads" selected="opportunities" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' hideContainer='<%="true".equals(request.getParameter("actionplan")) %>'appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>'>
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="this.form.dosubmit.value='true';">
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='SalesOpportunities.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>';this.form.dosubmit.value='false';">
  <br />
  <dhv:formMessage />
  <%@ include file="../pipeline/opportunity_include.jsp" %>
  <br />
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="this.form.dosubmit.value='true';">
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='SalesOpportunities.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>';this.form.dosubmit.value='false';">
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="actionSource" value="SalesOpportunities">
  <input type="hidden" name="source" value="<%= toHtmlValue(request.getParameter("source")) %>">
  <input type="hidden" name="actionStepWork" value="<%= toHtmlValue(request.getParameter("actionStepWork")) %>">
  
</dhv:container>
</form>
</body>

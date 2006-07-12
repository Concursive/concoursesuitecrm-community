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
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.OpportunityHeader,com.zeroio.iteam.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="OppDetails" class="org.aspcfs.modules.pipeline.beans.OpportunityBean" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="addQuote" class="java.lang.String" scope="request"/>
<%
  OpportunityHeader opportunityHeader = OppDetails.getHeader();
	OpportunityComponent ComponentDetails = OppDetails.getComponent();
  boolean allowMultiple = allowMultipleComponents(pageContext, OpportunityComponent.MULTPLE_CONFIG_NAME, "multiple");
%>
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
<SCRIPT LANGUAGE="JavaScript" type="text/javascript" src="javascript/popContacts.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
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
  <dhv:include name="opportunity.lowEstimateCanNotBeZero,pipeline-lowEstimate" none="true">
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
  if (form.header_manager.value == -1) {
    message += label("check.valid.manager", "- Please select a valid user to manage the opportunity\r\n");
    formTest = false;
  }
  if (form.component_owner.value == -1) {
    message += label("check.valid.owner", "- Please select a valid user to assign the component\r\n");
    formTest = false;
  }
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
<form name="opportunityForm" action="AccountContactsOpps.do?command=Save&contactId=<%= ContactDetails.getId() %>&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<a href="AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> >
<dhv:label name="accounts.accounts_contacts_oppcomponent_add.AddOpportunity">Add Opportunity</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="contacts" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>">
  <dhv:container name="accountscontacts" selected="opportunities" object="ContactDetails" param="<%= "id=" + ContactDetails.getId() %>">
    <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="this.form.dosubmit.value='true';" />
    <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>';this.form.dosubmit.value='false';" />
    <br />
    <dhv:formMessage />
    <br />
    <%--  include basic opportunity form --%>
    <%@ include file="../pipeline/opportunity_include.jsp" %>
    <br />
    <dhv:permission name="accounts-accounts-contacts-opportunities-quotes-add">
      <dhv:evaluate if="<%= !isPopup(request) %>">
        <table border="0">
          <tr>
            <td valign="top" >
              <strong><dhv:label name="pipeline.addAQuoteQuestion">Add a quote?</dhv:label></strong>
            </td>
            <td>
              <input type="checkbox" name="addQuote" value="true" <%= request.getAttribute("addQuote") != null && "true".equals((String) request.getAttribute("addQuote"))? "CHECKED":"" %>/>
            </td>
          </tr>
        </table>
      </dhv:evaluate>
    </dhv:permission>
    <br />
    <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="this.form.dosubmit.value='true';" />
    <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>';this.form.dosubmit.value='false';" />
    <input type="hidden" name="dosubmit" value="true">
    <input type="hidden" name="actionSource" value="AccountContactsOpps">
  </dhv:container>
</dhv:container>
</form>
</body>

<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
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
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></script>
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
  <dhv:include name="opportunity.lowEstimateCanNotBeZero" none="true">
  if (form.low.value != "" && form.low.value != "" && (parseInt(form.low.value) > parseInt(form.high.value))) { 
    message += label("low.estimate", "- Low Estimate cannot be higher than High Estimate\r\n");
    formTest = false;
  }
  </dhv:include>
  <dhv:include name="opportunity.alertDescription opportunity.alertDate,pipeline-alertdate" none="true">
  if ((!checkNullString(form.alertText.value)) && (checkNullString(form.alertDate.value))) { 
    message += label("specify.alert.date", "- Please specify an alert date\r\n");
    formTest = false;
  }
  if ((!checkNullString(form.alertDate.value)) && (checkNullString(form.alertText.value))) { 
    message += label("specify.alert.description", "- Please specify an alert description\r\n");
    formTest = false;
  }
  </dhv:include>
  <dhv:include name="opportunity.estimatedCommission,pipeline-commission" none="true">
  if (!checkNumber(form.commission.value)) { 
      message += label("commission.entered.invalid", "- Commission entered is invalid\r\n");
      formTest = false;
    }
  </dhv:include>
  if (formTest == false) {
    alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
    return false;
  } else {
  <dhv:include name="opportunity.componentTypes" none="true">
    if(alertMessage != "") {
      return confirmAction(alertMessage);
    } else {
      var test = document.opportunityForm.selectedList;
      if (test != null) {
        return selectAllOptions(document.opportunityForm.selectedList);
      }
    }
  </dhv:include>
  }
}
</SCRIPT>
<script type="text/javascript">
function reopenOpportunity(id) {
  if (id == '<%= opportunityHeader.getId() %>') {
    scrollReload('Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %><%= isPopup(request)?"&popup=true":"" %>');
    return id;
  } else {
    return '<%= opportunityHeader.getId() %>';
  }
}
</script>
<%
   boolean popUp = false;
   if(request.getParameter("popup")!=null){
     popUp = true;
   }
   boolean allowMultiple = allowMultipleComponents(pageContext, OpportunityComponent.MULTPLE_CONFIG_NAME, "multiple");
%>
<form name="opportunityForm" action="OpportunitiesComponents.do?command=SaveComponent&orgId=<%= OrgDetails.getId() %>&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Opportunities.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> >
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	  <a href="Opportunities.do?command=Details&headerId=<%= ComponentDetails.getHeaderId() %>&orgId=<%= OrgDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> >
  <%}%>
<%} else {%>
<a href="Opportunities.do?command=Details&headerId=<%= ComponentDetails.getHeaderId() %>&orgId=<%= OrgDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> >
<a href="OpportunitiesComponents.do?command=DetailsComponent&id=<%= ComponentDetails.getId() %>&orgId=<%= OrgDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.ComponentDetails">Component Details</dhv:label></a> >
<%}%>
<dhv:label name="accounts.accounts_contacts_oppcomponent_modify.ModifyComponent">Modify Component</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="opportunities" hideContainer='<%="true".equals(request.getParameter("actionplan")) %>' object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>'>
  <img src="images/icons/stock_form-currency-field-16.gif" border="0" align="absmiddle">
  <strong><%= toHtml(opportunityHeader.getDescription()) %></strong>
  <% FileItem thisFile = new FileItem(); %>
  <dhv:evaluate if="<%= opportunityHeader.hasFiles() %>">
    <%= thisFile.getImageTag("-23") %>
  </dhv:evaluate>
  <br />
  <br />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="this.form.dosubmit.value='true';" />
  <dhv:evaluate if="<%= !popUp %>">
<% if ("list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Opportunities.do?command=Details&headerId=<%= ComponentDetails.getHeaderId() %>&orgId=<%= OrgDetails.getId() %>';this.form.dosubmit.value='false';" />
<%} else {%>
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='OpportunitiesComponents.do?command=DetailsComponent&id=<%= ComponentDetails.getId() %>&orgId=<%= OrgDetails.getId() %>';this.form.dosubmit.value='false';" />
<%}%>
  </dhv:evaluate>
  <dhv:evaluate if="<%= popUp %>">
    <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onclick="javascript:window.close();" />
  </dhv:evaluate>
  <input type="hidden" name="id" value="<%= ComponentDetails.getId() %>">
  <input type="hidden" name="headerId" value="<%= ComponentDetails.getHeaderId() %>">
  <input type="hidden" name="modified" value="<%= ComponentDetails.getModified() %>">
  <dhv:evaluate if='<%= request.getParameter("return") != null %>'>
    <input type="hidden" name="return" value="<%= request.getParameter("return") %>">
  </dhv:evaluate>
  <br />
  <dhv:formMessage />
  <br />
  <%--  include basic opportunity form --%>
  <%@ include file="../pipeline/opportunity_include.jsp" %>
  <br>
  <input type="hidden" name="orgId" value="<%= request.getParameter("orgId") %>" />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="this.form.dosubmit.value='true';" />
  <dhv:evaluate if="<%= !popUp %>">
  <% if ("list".equals(request.getParameter("return"))) {%>
        <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Opportunities.do?command=Details&headerId=<%= ComponentDetails.getHeaderId() %>&orgId=<%= OrgDetails.getId() %>';this.form.dosubmit.value='false';" />
  <%} else {%>
        <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='OpportunitiesComponents.do?command=DetailsComponent&id=<%= ComponentDetails.getId() %>&orgId=<%= OrgDetails.getId() %>';this.form.dosubmit.value='false';" />
  <%}%>
  </dhv:evaluate>
  <dhv:evaluate if="<%= popUp %>">
    <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onclick="javascript:window.close();" />
  </dhv:evaluate>
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="source" value="<%= toHtmlValue(request.getParameter("source")) %>">
  <input type="hidden" name="actionStepWork" value="<%= toHtmlValue(request.getParameter("actionStepWork")) %>">
</dhv:container>
</form>


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
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.opportunityForm.description.focus();">
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
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

function reopenOpportunity(id) {
  if (id == '<%= opportunityHeader.getId() %>') {
    scrollReload('Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %><%= isPopup(request)?"&popup=true":"" %>');
    return id;
  } else {
    return '<%= opportunityHeader.getId() %>';
  }
}
</script>
<form name="opportunityForm" action="OpportunitiesComponents.do?command=SaveComponent&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> >
<a href="Opportunities.do?command=Details&headerId=<%= opportunityHeader.getId() %>&orgId=<%= OrgDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> >
<dhv:label name="accounts.accounts_opportunities_addoppcomponent.AddComponent">Add Component</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="accounts" selected="opportunities" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>">
  <img src="images/icons/stock_form-currency-field-16.gif" border="0" align="absmiddle">
  <strong><%= toHtml(opportunityHeader.getDescription()) %></strong>
  <% FileItem thisFile = new FileItem(); %>
  <dhv:evaluate if="<%= opportunityHeader.hasFiles() %>">
    <%= thisFile.getImageTag("-23") %>
  </dhv:evaluate>
  <br />
  <br />
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="this.form.dosubmit.value='true';">
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Opportunities.do?command=Details&headerId=<%= opportunityHeader.getId() %>&orgId=<%= OrgDetails.getId() %>';this.form.dosubmit.value='false';">
  <br />
  <dhv:formMessage />
  <%--  include basic opportunity form --%>
  <%@ include file="../pipeline/opportunity_include.jsp" %>
  <br />
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="this.form.dosubmit.value='true';" />
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Opportunities.do?command=Details&headerId=<%= opportunityHeader.getId() %>&orgId=<%= OrgDetails.getId() %>';this.form.dosubmit.value='false';" />
  <input type="hidden" name="orgId" value="<%=request.getParameter("orgId")%>" />
  <input type="hidden" name="dosubmit" value="true" />
  <input type="hidden" name="headerId" value="<%= opportunityHeader.getId() %>" />
</dhv:container>
</form>
</body>

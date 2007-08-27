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
<%@ page import="org.aspcfs.modules.pipeline.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="OppDetails" class="org.aspcfs.modules.pipeline.beans.OpportunityBean" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.opportunityForm.header_description.focus();">
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript" src="javascript/popContacts.js?v=20070827"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
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
  <dhv:include name="opportunity.alertDescription opportunity.alertDate" none="true" all="true">
  if ((!checkNullString(form.component_alertText.value)) && (checkNullString(form.component_alertDate.value))) { 
    message += label("specify.alert.date", "- Please specify an alert date\r\n");
    formTest = false;
  }
  if ((!checkNullString(form.component_alertDate.value)) && (checkNullString(form.component_alertText.value))) { 
    message += label("specify.alert.description", "- Please specify an alert description\r\n");
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
<form name="opportunityForm" action="ExternalContactsOpps.do?command=Save&contactId=<%= ContactDetails.getId() %>&actionSource=MyActionContacts&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<dhv:formMessage showSpace="false"/>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<%--  include basic opportunity form --%>
<%@ include file="../pipeline/opportunity_include.jsp" %>
<br />
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="this.form.dosubmit.value='true';" />
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();" />
<input type="hidden" name="dosubmit" value="true">
</form>
</body>

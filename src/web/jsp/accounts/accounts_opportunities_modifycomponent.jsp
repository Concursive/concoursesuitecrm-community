<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*,org.aspcfs.utils.web.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="ComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
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
  if ((!form.closeDate.value == "") && (!checkDate(form.closeDate.value))) { 
    message += "- Check that Est. Close Date is entered correctly\r\n";
    formTest = false;
  }
  if (form.low.value != "" && form.low.value != "" && (parseInt(form.low.value) > parseInt(form.high.value))) { 
    message += "- Low Estimate cannot be higher than High Estimate\r\n";
    formTest = false;
  }
  if ((!form.alertDate.value == "") && (!checkDate(form.alertDate.value))) { 
    message += "- Check that Alert Date is entered correctly\r\n";
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
  if (!checkNumber(form.commission.value)) { 
      message += "- Commission entered is invalid\r\n";
      formTest = false;
    }
  if ((!form.alertDate.value == "") && (!checkAlertDate(form.alertDate.value))) { 
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
</SCRIPT>
<%
   boolean popUp = false;
   if(request.getParameter("popup")!=null){
     popUp = true;
   }
%>
<form name="opportunityForm" action="OpportunitiesComponents.do?command=SaveComponent&orgId=<%= OrgDetails.getId() %>&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="Opportunities.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Opportunities</a> >
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	  <a href="Opportunities.do?command=Details&headerId=<%= ComponentDetails.getHeaderId() %>&orgId=<%= OrgDetails.getId() %>">Opportunity Details</a> >
  <%}%>
<%} else {%>
<a href="Opportunities.do?command=Details&headerId=<%= ComponentDetails.getHeaderId() %>&orgId=<%= OrgDetails.getId() %>">Opportunity Details</a> >
<a href="OpportunitiesComponents.do?command=DetailsComponent&id=<%= ComponentDetails.getId() %>&orgId=<%= OrgDetails.getId() %>">Component Details</a> >
<%}%>
Modify Component
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="opportunities" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <%-- Begin container content --%>
      <img src="images/icons/stock_form-currency-field-16.gif" border="0" align="absmiddle">
      <strong><%= toHtml(opportunityHeader.getDescription()) %></strong>
      <% FileItem thisFile = new FileItem(); %>
      <dhv:evaluate if="<%= opportunityHeader.hasFiles() %>">
        <%= thisFile.getImageTag() %>
      </dhv:evaluate>
      <br>
      <br>
      <input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
<% if ("list".equals(request.getParameter("return"))) {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='Opportunities.do?command=Details&headerId=<%= ComponentDetails.getHeaderId() %>&orgId=<%= OrgDetails.getId() %>';this.form.dosubmit.value='false';">
<%} else {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='OpportunitiesComponents.do?command=DetailsComponent&id=<%= ComponentDetails.getId() %>&orgId=<%= OrgDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>
<input type="hidden" name="id" value="<%= ComponentDetails.getId() %>">
<input type="hidden" name="headerId" value="<%= ComponentDetails.getHeaderId() %>">
<input type="hidden" name="modified" value="<%= ComponentDetails.getModified() %>">
<dhv:evaluate if="<%= request.getParameter("return") != null %>">
  <input type="hidden" name="return" value="<%= request.getParameter("return") %>">
</dhv:evaluate>
  <input type="reset" value="Reset">
<dhv:evaluate exp="<%= popUp %>">
  <input type="button" value="Cancel" onclick="javascript:window.close();">
</dhv:evaluate>
<br>
<%= showError(request, "actionError") %>

<%--  include basic opportunity form --%>
<%@ include file="../pipeline/opportunity_include.jsp" %>

&nbsp;
<br>

<input type="hidden" name="orgId" value="<%= request.getParameter("orgId") %>">
<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
<% if ("list".equals(request.getParameter("return"))) {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='Opportunities.do?command=Details&headerId=<%= ComponentDetails.getHeaderId() %>&orgId=<%= OrgDetails.getId() %>';this.form.dosubmit.value='false';">
<%} else {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='OpportunitiesComponents.do?command=DetailsComponent&id=<%= ComponentDetails.getId() %>&orgId=<%= OrgDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>
<input type="reset" value="Reset">
<dhv:evaluate exp="<%= popUp %>">
  <input type="button" value="Cancel" onclick="javascript:window.close();">
</dhv:evaluate>
<input type="hidden" name="dosubmit" value="true">
<%-- End container contents --%>
<dhv:evaluate if="<%= !popUp %>">
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- End container --%>
</form>


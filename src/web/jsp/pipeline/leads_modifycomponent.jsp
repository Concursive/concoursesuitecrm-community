<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*,org.aspcfs.utils.web.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OpportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="ComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
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
<%
    boolean popUp = false;
   if(request.getParameter("popup")!=null){
     popUp = true;
   }
%>
<form name="opportunityForm" action="LeadsComponents.do?command=SaveComponent&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return doCheck(this);" method="post">
<dhv:evaluate if="<%= !popUp %>">
<a href="Leads.do">Pipeline Management</a> > 
<a href="Leads.do?command=ViewOpp">View Opportunities</a> >
<% if ("list".equals(request.getParameter("return"))) {%>
  <a href="Leads.do?command=DetailsOpp&headerId=<%= ComponentDetails.getHeaderId() %>">Opportunity Details</a> > 
<%} else if (request.getParameter("return") != null) {%>
  <a href="Leads.do?command=DetailsOpp&headerId=<%= ComponentDetails.getHeaderId() %>">Opportunity Details</a> > 
  <a href="LeadsComponents.do?command=DetailsComponent&id=<%= ComponentDetails.getId() %>">Component Details</a> >
<%}%>
Modify Component<br>
<hr color="#BFBFBB" noshade>
</dhv:evaluate>
<dhv:evaluate if="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
</dhv:evaluate>
<%-- Begin container --%>
<dhv:evaluate if="<%= !popUp %>">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OpportunityHeader.getDescription()) %></strong>&nbsp;
      <dhv:evaluate exp="<%= (OpportunityHeader.getAccountEnabled() && OpportunityHeader.getAccountLink() > -1) %>">
        <dhv:permission name="accounts-view,accounts-accounts-view">[ <a href="Accounts.do?command=Details&orgId=<%= OpportunityHeader.getAccountLink() %>">Go to this Account</a> ]</dhv:permission>
      </dhv:evaluate>
      <dhv:evaluate exp="<%= OpportunityHeader.getContactLink() > -1 %>">
        <dhv:permission name="contacts-view,contacts-external_contacts-view">[ <a href="ExternalContacts.do?command=ContactDetails&id=<%= OpportunityHeader.getContactLink() %>">Go to this Contact</a> ]</dhv:permission>
      </dhv:evaluate>
      <dhv:evaluate if="<%= OpportunityHeader.hasFiles() %>">
        <% FileItem thisFile = new FileItem(); %>
        <%= thisFile.getImageTag()%>
      </dhv:evaluate>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + OpportunityHeader.getId(); %>      
      <dhv:container name="opportunities" selected="details" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
</dhv:evaluate>
<%-- Begin the container contents --%>
<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
<% 
  if (request.getParameter("return") != null) {
	  if (request.getParameter("return").equals("list")) {
%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='Leads.do?command=ViewOpp';this.form.dosubmit.value='false';">
<%
    } else if (request.getParameter("return").equals("details")) {
%>
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='Leads.do?command=DetailsOpp&headerId=<%= ComponentDetails.getHeaderId() %>';this.form.dosubmit.value='false';">
<%
    }
  } else {
%>
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='LeadsComponents.do?command=DetailsComponent&id=<%= ComponentDetails.getId() %>';this.form.dosubmit.value='false';">
<%
  }
%>
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
<%@ include file="opportunity_form.jsp" %>

&nbsp;
<br>
<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='Leads.do?command=ViewOpp';this.form.dosubmit.value='false';">
	<%} else if(request.getParameter("return").equals("details")) {%>
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='Leads.do?command=DetailsOpp&headerId=<%= ComponentDetails.getHeaderId() %>';this.form.dosubmit.value='false';">
 <%}
 } else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='LeadsComponents.do?command=DetailsComponent&id=<%= ComponentDetails.getId() %>';this.form.dosubmit.value='false';">
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


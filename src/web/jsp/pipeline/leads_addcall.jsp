<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*,org.aspcfs.modules.contacts.base.Call,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="CallTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].subject.focus();">
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
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
        return true;
      }
    }
  }
</script>
<form name="addCall" action="LeadsCalls.do?command=Insert&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Leads.do">Pipeline</a> > 
<a href="Leads.do?command=ViewOpp">View Components</a> >
<a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>">Opportunity Details</a> >
<a href="LeadsCalls.do?command=View&headerId=<%= opportunityHeader.getId() %>">Calls</a> >
Add a Call
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
</dhv:evaluate>
<%-- Begin container --%>
<%@ include file="leads_details_header_include.jsp" %>
<% String param1 = "id=" + opportunityHeader.getId(); %>      
<dhv:container name="opportunities" selected="calls" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<%-- Begin the container contents --%>
      <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='LeadsCalls.do?command=View&headerId=<%= opportunityHeader.getId() %>';this.form.dosubmit.value='false';">
      <input type="reset" value="Reset">
      <br>
      <%= showError(request, "actionError") %>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
          <th colspan="2">
            <strong>Log a New Call</strong>
          </th>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            Type
          </td>
          <td>
            <%= CallTypeList.getHtmlSelect("callTypeId", CallDetails.getCallTypeId()) %>
          </td>
        </tr>
<% if (opportunityHeader.getContactLink() == -1) { %>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            Contact
          </td>
          <td valign="center">
	<% if (opportunityHeader.getAccountLink() == -1 || ContactList.size() == 0) { %>
            <%= ContactList.getEmptyHtmlSelect("contactId") %>
	<%} else {%>
            <%= ContactList.getHtmlSelect("contactId", CallDetails.getContactId() ) %>
	<%}%>
          </td>
        </tr>
  <%} else {%>
          <input type="hidden" name="contactId" value="<%= opportunityHeader.getContactLink() %>">
  <%}%>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            Subject
          </td>
          <td>
            <input type="text" size="50" name="subject" value="<%= toHtmlValue(CallDetails.getSubject()) %>">
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel" valign="top">
            Notes
          </td>
          <td>
            <TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(CallDetails.getNotes()) %></TEXTAREA>
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            Length
          </td>
          <td>
            <input type="text" size="5" name="length" value="<%= toHtmlValue(CallDetails.getLengthString()) %>"> minutes  <%= showAttribute(request, "lengthError") %>
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            Alert Description
          </td>
          <td valign="center">
            <input type="text" size="50" name="alertText" value="<%= toHtmlValue(CallDetails.getAlertText()) %>"><br>
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            Alert Date
          </td>
          <td>
            <input type="text" size="10" name="alertDate" value="<%= toHtmlValue(CallDetails.getAlertDateString()) %>"> 
            <a href="javascript:popCalendar('addCall', 'alertDate');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
          </td>
        </tr>
      </table>
      <br>
      <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='LeadsCalls.do?command=View&headerId=<%= opportunityHeader.getId() %>';this.form.dosubmit.value='false';">
      <input type="reset" value="Reset">
      <input type="hidden" name="dosubmit" value="true">
      <input type="hidden" name="oppHeaderId" value=<%= opportunityHeader.getId() %>>
      <input type="hidden" name="headerId" value=<%= opportunityHeader.getId() %>>
<%-- End container contents --%>
    </td>
  </tr>
</table>
<%-- End container --%>
</form>
</body>

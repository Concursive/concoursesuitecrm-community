<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OpportunityDetails" class="org.aspcfs.modules.Opportunity" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="LeadsCallList" class="org.aspcfs.modules.contacts.base.CallList" scope="request"/>
<jsp:useBean id="LeadsCallListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="CallTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<dhv:permission name="pipeline-opportunities-calls-add">
<body onLoad="javascript:document.forms[0].subject.focus();">
</dhv:permission>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>

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
        return true;
      }
    }
</script>

<form name="addCall" action="/LeadsCalls.do?command=Insert&auto-populate=true" onSubmit="return doCheck(this);" method="post">

<a href="/Leads.do">Pipeline Management</a> > 
<a href="/Leads.do?command=ViewOpp">View Opportunities</a> >
<a href="/Leads.do?command=DetailsOpp&id=<%=OpportunityDetails.getId()%>">Opportunity Details</a> >
Calls<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OpportunityDetails.getDescription()) %></strong>&nbsp;
      	<dhv:evaluate exp="<%=(OpportunityDetails.getAccountEnabled() && OpportunityDetails.getAccountLink() > -1)%>">
        <dhv:permission name="accounts-view,accounts-accounts-view">[ <a href="/Accounts.do?command=Details&orgId=<%=OpportunityDetails.getAccountLink()%>">Go to this Account</a> ]</dhv:permission>
	</dhv:evaluate>
	  
	<dhv:evaluate exp="<%=(OpportunityDetails.getContactLink() > -1)%>">
	<dhv:permission name="contacts-view,contacts-external_contacts-view">[ <a href="/ExternalContacts.do?command=ContactDetails&id=<%=OpportunityDetails.getContactLink()%>">Go to this Contact</a> ]</dhv:permission>
	</dhv:evaluate>
  
        <% if (OpportunityDetails.hasFiles()) { %>
      <% FileItem thisFile = new FileItem(); %>
      <%= thisFile.getImageTag()%>
      <%}%>   
  
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + OpportunityDetails.getId(); %>      
      <dhv:container name="opportunities" selected="calls" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<%= showError(request, "actionError") %>
<dhv:permission name="pipeline-opportunities-calls-add">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Log a New Call</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Type
    </td>
    <td>
      <%= CallTypeList.getHtmlSelect("callTypeId", CallDetails.getCallTypeId()) %>
    </td>
  </tr>
  
  <% if ( OpportunityDetails.getContactLink() == -1 ) { %>
  <tr class="containerBody">
	<td nowrap class="formLabel">
	Contact
	</td>
	<td colspan=1 valign=center>
	<% if (OpportunityDetails == null || OpportunityDetails.getAccountLink() == -1 || ContactList.size() == 0) { %>
		<%= ContactList.getEmptyHtmlSelect("contactId") %>
	<%} else {%>
		<%= ContactList.getHtmlSelect("contactId", CallDetails.getContactId() ) %>
	<%}%>
	</td>
  </tr>
  <%} else {%>
  	<input type=hidden name=contactId value="<%=OpportunityDetails.getContactLink()%>">
  <%}%>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Subject
    </td>
    <td>
      <input type=text size=50 name="subject" value="<%= toHtmlValue(CallDetails.getSubject()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Notes
    </td>
    <td>
      <TEXTAREA NAME='notes' ROWS=3 COLS=50><%= toString(CallDetails.getNotes()) %></TEXTAREA>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Length
    </td>
    <td>
      <input type=text size=5 name="length" value="<%= toHtmlValue(CallDetails.getLengthString()) %>"> minutes  <%= showAttribute(request, "lengthError") %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Description
    </td>
    <td valign=center colspan=1>
      <input type=text size=50 name="alertText" value="<%= toHtmlValue(CallDetails.getAlertText()) %>"><br>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td>
      <input type=text size=10 name="alertDate" value="<%= toHtmlValue(CallDetails.getAlertDateString()) %>"> 
      <a href="javascript:popCalendar('addCall', 'alertDate');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
</table>
    
<input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
<input type="reset" value="Reset">
<input type="hidden" name="dosubmit" value="true">
<br>
&nbsp;
</dhv:permission>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">

  <tr class="title">
  <dhv:permission name="pipeline-opportunities-calls-edit,pipeline-opportunities-calls-delete">
    <td valign=center align=left>
      <strong>Action</strong>
    </td>
  </dhv:permission>  
    <td valign=center align=left>
      <strong>Subject</strong>
    </td>
  
    <td valign=center align=left>
      <strong>Type</strong>
    </td>
    
    <td valign=center align=center>
      <strong>Length</strong>
    </td>
    
    <td valign=center align=center>
      <strong>Date</strong>
    </td>
  </tr>

<%
	Iterator j = LeadsCallList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
	    while (j.hasNext()) {
		
        if (rowid != 1) {
          rowid = 1;
        } else {
          rowid = 2;
        }
		
		Call thisCall = (Call)j.next();
%>      
    <tr class="containerBody">
      <dhv:permission name="pipeline-opportunities-calls-edit,pipeline-opportunities-calls-delete">
      <td width=8 valign=center nowrap class="row<%= rowid %>">
          <dhv:permission name="pipeline-opportunities-calls-edit"><a href="/LeadsCalls.do?command=Modify&id=<%= thisCall.getId() %>&oppId=<%= OpportunityDetails.getId()%>&return=list">Edit</a></dhv:permission><dhv:permission name="pipeline-opportunities-calls-edit,pipeline-opportunities-calls-delete" all="true">|</dhv:permission><dhv:permission name="pipeline-opportunities-calls-delete"><a href="javascript:confirmDelete('/LeadsCalls.do?command=Delete&id=<%= thisCall.getId() %>&oppId=<%= OpportunityDetails.getId()%>');">Del</a></dhv:permission>
      </td>
      </dhv:permission>
      
      <td width="100%" valign=center class="row<%= rowid %>">
        <a href="/LeadsCalls.do?command=Details&id=<%= thisCall.getId() %>&oppId=<%= OpportunityDetails.getId() %>">
        <%= toHtml(thisCall.getSubject()) %>
        </a>
      </td>
      <td valign=center nowrap class="row<%= rowid %>">
        <%= toHtml(thisCall.getCallType()) %>
      </td>
      
      <td align=center valign=center nowrap class="row<%= rowid %>">
        <%= toHtml(thisCall.getLengthText()) %>
      </td>
      
      <td valign=center nowrap class="row<%= rowid %>">
        <%= toHtml(thisCall.getEnteredString()) %>
      </td>
    </tr>
<%}%>
	
	</table>
<%} else {%>
		<tr class="containerBody">
      <td colspan=5 valign=center>
        No calls found.
      </td>
    </tr>
  </table>
<%}%>
</td>
</tr>
</table>
<input type=hidden name=oppId value=<%=OpportunityDetails.getId()%>>
</form>
</body>

<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OpportunityDetails" class="com.darkhorseventures.cfsbase.Opportunity" scope="request"/>
<jsp:useBean id="CallDetails" class="com.darkhorseventures.cfsbase.Call" scope="request"/>
<jsp:useBean id="LeadsCallList" class="com.darkhorseventures.cfsbase.CallList" scope="request"/>
<jsp:useBean id="LeadsCallListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<jsp:useBean id="CallTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="ContactList" class="com.darkhorseventures.cfsbase.ContactList" scope="request"/>
<%@ include file="initPage.jsp" %>
<body onLoad="javascript:document.forms[0].subject.focus();">
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<form name="addCall" action="/LeadsCalls.do?command=Insert&auto-populate=true" method="post">
<a href="/Leads.do?command=ViewOpp">Back to Opportunities List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OpportunityDetails.getDescription()) %></strong>&nbsp;&nbsp;
      
      	<% 
	  if (OpportunityDetails.getAccountLink() != -1) { %>
	  	[ <a href="/Accounts.do?command=Details&orgId=<%=OpportunityDetails.getAccountLink()%>">Go to this Account</a> ]
	  <%} else { %>
	  	[ <a href="/ExternalContacts.do?command=ContactDetails&id=<%=OpportunityDetails.getContactLink()%>">Go to this Contact</a> ]
	  <%}%>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <a href="/Leads.do?command=DetailsOpp&id=<%= OpportunityDetails.getId() %>"><font color="#000000">Details</font></a> | 
      <a href="/LeadsCalls.do?command=View&oppId=<%= OpportunityDetails.getId() %>"><font color="#0000FF">Calls</font></a> |
      <a href=""><font color="#000000">Documents</font></a>
   </td>
  </tr>
  <tr>
    <td class="containerBack">
<%= showAttribute(request, "actionError") %>
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
      <TEXTAREA NAME='notes' ROWS=3 COLS=50><%= toHtmlValue(CallDetails.getNotes()) %></TEXTAREA>
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
      Alert Date
    </td>
    <td>
      <input type=text size=10 name="alertDate" value="<%= toHtmlValue(CallDetails.getAlertDate()) %>"> 
      <a href="javascript:popCalendar('addCall', 'alertDate');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
</table>
    
<input type="submit" value="Save">
<input type="reset" value="Reset">
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">

  <tr class="title">
    <td valign=center align=left>
      <strong>Action</strong>
    </td>
  
    <td valign=center align=center>
      <strong>Date</strong>
    </td>
    
    <td valign=center align=left>
      <strong>Type</strong>
    </td>
    
    <td valign=center align=left>
      <strong>Subject</strong>
    </td>
    
    <td valign=center align=center>
      <strong>Length</strong>
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
      <td width=8 valign=center nowrap class="row<%= rowid %>">
        <a href="/LeadsCalls.do?command=Modify&id=<%= thisCall.getId() %>&oppId=<%= OpportunityDetails.getId() %>">Edit</a>|<a href="javascript:confirmDelete('/LeadsCalls.do?command=Delete&id=<%= thisCall.getId() %>&oppId=<%= OpportunityDetails.getId() %>');">Del</a></td>
      <td valign=center nowrap class="row<%= rowid %>">
        <a href="/LeadsCalls.do?command=Details&id=<%= thisCall.getId() %>&oppId=<%= OpportunityDetails.getId() %>">
        <%= toHtml(thisCall.getEntered()) %>
        </a>
      </td>
      <td valign=center nowrap class="row<%= rowid %>">
        <%= toHtml(thisCall.getCallType()) %>
      </td>
      <td width="100%" valign=center class="row<%= rowid %>">
        <%= toHtml(thisCall.getSubject()) %>
      </td>
      <td align=center valign=center nowrap class="row<%= rowid %>">
        <%= toHtml(thisCall.getLengthText()) %>
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

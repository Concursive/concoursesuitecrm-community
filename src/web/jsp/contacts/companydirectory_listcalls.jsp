<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="ContactDetails" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<jsp:useBean id="CallDetails" class="com.darkhorseventures.cfsbase.Call" scope="request"/>
<jsp:useBean id="CallList" class="com.darkhorseventures.cfsbase.CallList" scope="request"/>
<jsp:useBean id="CallListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<jsp:useBean id="CallTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<%@ include file="initPage.jsp" %>
<dhv:permission name="contacts-external_contacts-calls-add">
<body onLoad="javascript:document.forms[0].subject.focus();">
</dhv:permission>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<form name="addCall" action="/ExternalContactsCalls.do?command=Insert&contactId=<%= ContactDetails.getId() %>&auto-populate=true" method="post">
<a href="/ExternalContacts.do?command=ListContacts">Back to Contact List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + ContactDetails.getId(); %>      
      <dhv:container name="contacts" selected="calls" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<%= showAttribute(request, "actionError") %>
<dhv:permission name="contacts-external_contacts-calls-add">
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
      <input type=text size=10 name="alertDate" value="<%= toHtmlValue(CallDetails.getAlertDateString()) %>"> 
      <a href="javascript:popCalendar('addCall', 'alertDate');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
</table>
    
<input type="submit" value="Save">
<input type="reset" value="Reset">
<br>
&nbsp;
</dhv:permission>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">

  <tr class="title">
  <dhv:permission name="contacts-external_contacts-calls-edit,contacts-external_contacts-calls-delete">
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
	Iterator j = CallList.iterator();
	
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
      <dhv:permission name="contacts-external_contacts-calls-edit,contacts-external_contacts-calls-delete">
      <td width=8 valign=center nowrap class="row<%= rowid %>">
          <dhv:permission name="contacts-external_contacts-calls-edit"><a href="/ExternalContactsCalls.do?command=Modify&id=<%= thisCall.getId() %>&contactId=<%= ContactDetails.getId()%>&return=list">Edit</a></dhv:permission><dhv:permission name="contacts-external_contacts-calls-edit,contacts-external_contacts-calls-delete" all="true">|</dhv:permission><dhv:permission name="contacts-external_contacts-calls-delete"><a href="javascript:confirmDelete('/ExternalContactsCalls.do?command=Delete&id=<%= thisCall.getId() %>&contactId=<%= ContactDetails.getId()%>');">Del</a></dhv:permission>
      </td>
      </dhv:permission>
      <td width="100%" valign=center class="row<%= rowid %>">
        <a href="/ExternalContactsCalls.do?command=Details&id=<%= thisCall.getId() %>&contactId=<%= ContactDetails.getId() %>">
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
</body>

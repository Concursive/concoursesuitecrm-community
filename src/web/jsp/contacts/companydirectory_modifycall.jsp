<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="ContactDetails" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<jsp:useBean id="CallDetails" class="com.darkhorseventures.cfsbase.Call" scope="request"/>
<jsp:useBean id="CallTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<form name="addCall" action="/ExternalContactsCalls.do?command=Update&id=<%= CallDetails.getId() %>&contactId=<%= ContactDetails.getId() %>&auto-populate=true" method="post">
<a href="/ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %>">Back to Call List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <a href="/ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>"><font color="#000000">Details</font></a> | 
            <a href="/ExternalContacts.do?command=Fields&contactId=<%= ContactDetails.getId() %>"><font color="#000000">Folders</font></a> | 
      <a href="/ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %>"><font color="#0000FF">Calls</font></a> | 
      <a href="/ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>"><font color="#000000">Opportunities</font></a> 
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<input type="hidden" name="modified" value="<%= CallDetails.getModified() %>">
<input type="submit" value="Update">
<input type="submit" value="Cancel" onClick="javascript:form.action='/ExternalContactsCalls.do?command=Details&id=<%= CallDetails.getId() %>&contactId=<%= ContactDetails.getId() %>'">
<input type="reset" value="Reset">
<br><%= showAttribute(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Call Details</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Type
    </td>
    <td width=100%>
      <%= CallTypeList.getHtmlSelect("callTypeId", CallDetails.getCallTypeId()) %>
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
      Subject
    </td>
    <td>
      <input type=text size=50 name="subject" value="<%= toHtmlValue(CallDetails.getSubject()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" width=100%>
      Notes
    </td>
    <td>
      <TEXTAREA NAME='notes' ROWS=3 COLS=50><%= (CallDetails.getNotes()) %></TEXTAREA>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" width=100%>
      Alert Date
    </td>
    <td>
      <input type=text size=10 name="alertDate" value="<%= toHtmlValue(CallDetails.getAlertDateString()) %>"> 
      <a href="javascript:popCalendar('addCall', 'alertDate');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
  
  <tr class="containerBody">
    <td align=center colspan=2>
      <i>Created <%= toHtml(CallDetails.getEnteredString()) %> by <%= toHtml(CallDetails.getEnteredName()) %> - 
      Modifed <%= toHtml(CallDetails.getModifiedString()) %> by <%= toHtml(CallDetails.getModifiedName()) %></i>
    </td>
  </tr>
</table>
<br>
<input type="submit" value="Update">
<input type="submit" value="Cancel" onClick="javascript:form.action='/ExternalContactsCalls.do?command=Details&id=<%= CallDetails.getId() %>&contactId=<%= ContactDetails.getId() %>'">
<input type="reset" value="Reset">
</td>
</tr>
</table>

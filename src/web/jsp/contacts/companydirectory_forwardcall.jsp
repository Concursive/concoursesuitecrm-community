<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="NoteDetails" class="org.aspcfs.modules.mycfs.base.CFSNote" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script type="text/javascript" src="javascript/stringbuilder.js"></script>
<script type="text/javascript" src="javascript/richedit.js"></script>
<script>
  function save() {
	<dhv:browser id="ie" minVersion="5.5" include="true">
    var edit = document.all.edit;
    document.all.msgBody.value = edit.getHTML();
    edit.focus();
  </dhv:browser>
  }
</script>
<form name="addCall" action="ExternalContactsCalls.do?command=Forward&id=<%= CallDetails.getId() %>&contactId=<%= ContactDetails.getId() %>" method="post">
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
    <input type="submit" value="Forward" onclick="javascript:save();">
    <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsCalls.do?command=Details&id=<%=CallDetails.getId()%>&contactId=<%=ContactDetails.getId()%>'">
<br>
<%= showError(request, "actionError") %>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Distribution information</strong>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Send To
    </td>
    <td width=100%>
      <% if (request.getParameter("sentTo") == null) { %>
      <%= UserList.getHtmlSelect("sentTo") %>
      <%} else {%>
      <%= UserList.getHtmlSelect("sentTo", Integer.parseInt(request.getParameter("sentTo"))) %>
      <%}%>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Forward Subject
    </td>
    <td width=100%>
      <input type=text name="fwdsubject" size=40>
      <font color="red">*</font> <%= showAttribute(request, "messageSubjectError") %>
    </td>
  </tr>
  
</table>

<br>&nbsp;

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Snapshot</strong>
    </td>
  </tr>
  
    <tr class="containerBody">

	<td colspan=2 width=100%>
		<dhv:browser id="ie" minVersion="5.5" include="true">
		  <input type="hidden" name="msgBody" value="">
	<iframe id="edit" frameborder="0" class="richEdit" style="border: 1px solid #cccccc; width: 100%; height: 100%;" onblur="return false">
	<body style="color: black; background: white; font: 8pt verdana;">
	<link rel="stylesheet" href="css/template0ie.css" type="text/css">
	<link rel="stylesheet" href="css/template0.css" type="text/css">
	<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
	<tr class="title">
	<td colspan=2 valign=center align=left>
	<strong>Forward: Call Details</strong>
	</td>
	</tr>
	<tr class="containerBody">
	<td nowrap class="formLabel">
	Type
	</td>
	<td width=100%>
	<%= toHtml(CallDetails.getCallType()) %>
	</td>
	</tr>
	<tr class="containerBody">
	<td nowrap class="formLabel">
	Length
	</td>
	<td>
	<%= toHtml(CallDetails.getLengthText()) %>
	</td>
	</tr>
	<tr class="containerBody">
	<td nowrap class="formLabel">
	Subject
	</td>
	<td>
	<%= toHtml(CallDetails.getSubject()) %>
	</td>
	</tr>
	<tr class="containerBody">
	<td nowrap class="formLabel">
	Notes
	</td>
	<td>
	<%= toHtml(CallDetails.getNotes()) %>
	</td>
	</tr>
	
	<tr class="containerBody">
	<td nowrap class="formLabel">
	Entered
	</td>
	<td>
	<%= toHtml(CallDetails.getEnteredName()) %>&nbsp;-&nbsp;<%= toHtml(CallDetails.getEnteredString()) %>
	</td>
	</tr>
	
	<tr class="containerBody">
	<td nowrap class="formLabel">
	Modified
	</td>
	<td>
	<%= toHtml(CallDetails.getModifiedName()) %>&nbsp;-&nbsp;<%= toHtml(CallDetails.getModifiedString()) %>
	</td>
	</tr>
	
	
	
	</table>
	</body>
	</iframe>
			</dhv:browser>
    </td>
  </tr>
  

  
</table>
<br>
<input type="submit" value="Forward" onclick="javascript:save();">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsCalls.do?command=Details&id=<%=CallDetails.getId()%>&contactId=<%=ContactDetails.getId()%>'">
</td>
</tr>
</table>
</form>




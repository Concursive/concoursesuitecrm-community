<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="NoteDetails" class="org.aspcfs.modules.mycfs.base.CFSNote" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<script type="text/javascript" src="/javascript/stringbuilder.js"></script>
<script type="text/javascript" src="/javascript/richedit.js"></script>
<script>
  function save() {
	<dhv:browser id="ie" minVersion="5.5" include="true">
    var edit = document.all.edit;
    document.all.msgBody.value = edit.getHTML();
    edit.focus();
  </dhv:browser>
  }
</script>
<form name="addCall" action="MyCFSInbox.do?command=Forward&id=<%= NoteDetails.getId() %>" method="post">
<a href="MyCFSInbox.do?command=Inbox">Back to Inbox</a>
<p>

    <input type="submit" value="Forward" onclick="javascript:save();">
    <input type="submit" value="Cancel" onClick="javascript:this.form.action='MyCFSInbox.do?command=CFSNoteDetails&id=<%=NoteDetails.getId()%>'">

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
      <input type=text name="fwdsubject" size=40 value="Fwd: <%= NoteDetails.getSubject()%>" >
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
        <jsp:include page="../templates/cssInclude.jsp" flush="true"/>
        <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr class="title">
          <td colspan=2 valign=center align=left>
            <strong>History: <%= toHtml(NoteDetails.getEnteredDateTimeString()) %></strong>
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            From
          </td>
          <td>
            <%= toHtml(NoteDetails.getSentName()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            To
          </td>
          <td width="100%">
            <%= toHtml(User.getNameFirstLast()) %>&nbsp;
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            Subject
          </td>
          <td width=100%>
            <%= toHtml(NoteDetails.getSubject()) %>&nbsp;
          </td>
        </tr>
        </table>
        <br>
        <%=NoteDetails.getBody()%>			
      </body>
      </iframe>
      </dhv:browser>
    </td>
  </tr>
</table>
<br>
<input type="submit" value="Forward" onclick="javascript:save();">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='MyCFSInbox.do?command=CFSNoteDetails&id=<%=NoteDetails.getId()%>'">
</form>

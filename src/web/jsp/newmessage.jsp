<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*, org.aspcfs.modules.contacts.base.Contact" %>
<jsp:useBean id="Note" class="org.aspcfs.modules.mycfs.base.CFSNote" scope="request"/>
<jsp:useBean id="returnUrl" class="java.lang.String" scope="request"/>
<jsp:useBean id="sendUrl" class="java.lang.String" scope="request"/>
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script type="text/javascript">
function sendMessage() {
  formTest = true;
  message = "";
  if(document.forms[0].listView.options[0].value == "none"){
    message += "- Select at least one recipient\r\n";
    formTest = false;
  }
  if(document.forms[0].subject.value == ""){
    message += "- Enter a subject\r\n";
    formTest = false;
  }
  if(document.forms[0].body.value == ""){
    message += "- Enter a message in the body\r\n";
    formTest = false;
  }
  if (formTest) {
    return true;
  } else {
    alert("Form could not be saved, please check the following:\r\n\r\n" + message);
    return false;
  }
}
</script>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>New CFS Message</strong>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Recipient(s)
    </td>
    <td>
      <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>
            <select size="3" name="listView" id="listViewId" multiple>
              <%
              if(Recipient.getId() > 0){
              %>
                <option value="<%= Recipient.getId() %>" selected><%= Recipient.getNameLastFirst() %></option>
              <%}else{%>
                <option value="none" selected>None Selected</option>
              <%}%>
            </select>
          </td>
          <td valign="top">
            [<a href="javascript:popContactsListMultiple('listViewId','1', 'reset=true');">Add Recipients</a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Subject
    </td>
    <td>
      <input type="text" name="subject" value="<%=Note.getSubject().equals("")?"":Note.getSubject()%>" size="50">
      <font color="red">*</font> <%= showAttribute(request, "messageSubjectError") %>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Options
    </td>
    <td>
      <input type="checkbox" name="mailrecipients">
      Email copy to recipient(s)
    </td> 
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Body
    </td>
    <td>
      <textarea name="body" rows="10" COLS="60" value="body"><%= toString(Note.getBody()) %></textarea>
    </td>
  </tr>
</table>
<input type="hidden" name="noteId" value="<%= Note.getId() %>">
<%= addHiddenParams(request, "popup|popupType|actionId") %>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*, org.aspcfs.modules.contacts.base.Contact" %>
<jsp:useBean id="Note" class="org.aspcfs.modules.mycfs.base.CFSNote" scope="request"/>
<jsp:useBean id="returnUrl" class="java.lang.String" scope="request"/>
<jsp:useBean id="sendUrl" class="java.lang.String" scope="request"/>
<jsp:useBean id="forwardType" class="java.lang.String" scope="request"/>
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script type="text/javascript">
function sendMessage() {
  formTest = true;
  message = "";
  if(document.newMessageForm.listView.options[0].value == "none"){
    message += label("select.onerecipient","- Select at least one recipient\r\n");
    formTest = false;
  }
  if(checkNullString(document.newMessageForm.subject.value)){
    message += label("check.subject","- Enter a subject\r\n");
    formTest = false;
  }
  if(checkNullString(document.newMessageForm.body.value)){
    message += label("check.message","- Enter a message in the body\r\n");
    formTest = false;
  }
  if (formTest) {
    return true;
  } else {
    alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
    return false;
  }
}
</script>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="actionList.newMessage">New Message</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="calendar.recipients">Recipient(s)</dhv:label>
    </td>
    <td>
      <table border="0" cellpadding="0" cellspacing="0" class="empty">
        <tr>
          <td>
            <select size="3" name="listView" id="listViewId" multiple>
              <%
              if(Recipient.getId() > 0){
              %>
                <option value="<%= Recipient.getId() %>" selected><%= Recipient.getNameLastFirst() %></option>
              <%}else{%>
                <option value="none" selected><dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label></option>
              <%}%>
            </select>
          </td>
          <td valign="top">
            [<a href="javascript:popContactsListMultiple('listViewId','1', 'reset=true');"><dhv:label name="calendar.addRecipients">Add Recipients</dhv:label></a>]<font color="red">*</font>
            <%= showAttribute(request, "contactsError") %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
  <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
    </td>
    <td>
      <input type="text" name="subject" value="<%=Note.getSubject().equals("")?"":Note.getSubject()%>" size="50">
      <font color="red">*</font> <%= showAttribute(request, "messageSubjectError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="product.options">Options</dhv:label>
    </td>
    <td>
      <input type="checkbox" name="mailrecipients" value="true">
      <dhv:label name="calendar.emailCopyToRecipients">Email copy to recipient(s)</dhv:label>
    </td> 
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="calendar.body">Body</dhv:label>
    </td>
    <td>
      <table border="0" class="empty">
        <tr>
          <td>
            <textarea name="body" rows="10" COLS="60" value="body"><%= toString(Note.getBody()) %></textarea>
          </td>
          <td valign="top">
            <font color="red">*</font> <%= showAttribute(request, "bodyError") %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<input type="hidden" name="noteId" value="<%= Note.getId() %>">
<input type="hidden" name="forwardType" value="<%= forwardType %>">
<%= addHiddenParams(request, "popup|popupType|actionId") %>

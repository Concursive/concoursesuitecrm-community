<%@ page import="java.util.*,org.aspcfs.modules.*" %>
<jsp:useBean id="Note" class="org.aspcfs.modules.CFSNote" scope="request"/>
<jsp:useBean id="returnUrl" class="java.lang.String" scope="request"/>
<jsp:useBean id="sendUrl" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<br>
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
<form name="newMessageForm" action=<%=sendUrl%> method="post" onSubmit="return sendMessage();">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>New CFS Message</strong>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap valign="top" class="formLabel">
      Recipient(s)
      </td>
    <td align="left">
      <select size="3" name="listView" id="listViewId" multiple>
        <option  value = "none" selected>None Selected</option>
      </select>
      <a href="javascript:popContactsListMultiple('listViewId','1');">Add Recipients</a>
     </td>
 </tr>
  

  
   <tr class="containerBody">
  <td nowrap class="formLabel">
      Subject
  </td>
  
  <td width=100%>
      <input type=text name="subject" value="<%=Note.getSubject().equals("")?"":Note.getSubject()%>" size=50>
      <font color="red">*</font> <%= showAttribute(request, "messageSubjectError") %>
  </td>
  
  </tr>
  
  <tr>
    <td nowrap class="formLabel" valign="top">
      Options
    </td>
    <td width=100%>
      <input type=checkbox name="mailrecipients">
      Email copy to recipient(s)<br>
      </td> 
   </tr>


  <tr class="containerBody">
    <td nowrap valign="top" class="formLabel">
      Body
    </td>
    <td width=100%>
      <textarea name="body" rows="5" COLS="50" value="body"><%= toString(Note.getBody()) %></textarea>
    </td>
  </tr>
</table>
  <input type=hidden name="return" value="<%=returnUrl%>">

<br>
<input type="submit" value="Send">
<input type="button" value="Cancel" onClick="javascript:window.location.href='<%=returnUrl%>'">
</form>



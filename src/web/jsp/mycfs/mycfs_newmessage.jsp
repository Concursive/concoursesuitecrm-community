<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Note" class="com.darkhorseventures.cfsbase.CFSNote" scope="request"/>
<jsp:useBean id="returnUrl" class="java.lang.String" scope="request"/>
<jsp:useBean id="sendUrl" class="java.lang.String" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="/javascript/popURL.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/submit.js"></script>
<br>

<form name="newMessageForm" action=<%=sendUrl%> method="post" onSubmit="return sendMessage();">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>New CFS Message</strong>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap valign="top" class="formLabel">
      Recipients
      </td>
    <td align="left">
      <select size="1" name="listView">
        <option value = "none" selected>None Selected</option>
      </select>
      <a href="javascript:popURLReturn('/MyCFSInbox.do?command=ContactList&popup=true&flushtemplist=true', 'MyCFSInbox.do?command=NewMessage', 'Inbox_message','700','450','yes','no');">Add Recipients</a>
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
      <%--<input type=checkbox name="savecopy"> Save copy to outbox--%>
      </td> 
   </tr>


  <tr class="containerBody">
    <td nowrap valign="top" class="formLabel">
      Body
    </td>
    <td width=100%>
      <textarea name="body" rows=10 style="width:100%;" value="body"><%=Note.getBody().equals("")?"":Note.getBody()%></textarea>
    </td>
  </tr>
</table>
  <input type=hidden name="return" value="<%=returnUrl%>">

<br>
<input type="submit" value="Send">
<input type="button" value="Cancel" onClick="javascript:window.location.href='<%=returnUrl%>'">
</form>



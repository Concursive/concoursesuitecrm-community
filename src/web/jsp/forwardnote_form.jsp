<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="NoteDetails" class="com.darkhorseventures.cfsbase.CFSNote" scope="request"/>
<jsp:useBean id="CallDetails" class="com.darkhorseventures.cfsbase.Call" scope="request"/>
<jsp:useBean id="UserList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<%@ include file="initPage.jsp" %>
<form action="/ForwardNote.do?command=Forward&auto-populate=true" method=POST>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>CFS Message Details</strong>
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
      Subject
    </td>
    <td width=100%>
      <input type=text name="subject" value="<%= toHtmlValue(NoteDetails.getSubject()) %>" size=50>
      <font color="red">*</font> <%= showAttribute(request, "messageSubjectError") %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap valign="top" class="formLabel">
      Text
    </td>
    <td width=100%>
      <textarea name="body" rows=10 style="width:100%;"><%= NoteDetails.getBody() %></textarea>
    </td>
  </tr>
</table>
<br>
<input type="submit" value="Send">
<input type="button" value="Cancel" onClick="javascript:window.close();">
</form>

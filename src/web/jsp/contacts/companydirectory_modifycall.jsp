<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="addCall" action="ExternalContactsCalls.do?command=Update&id=<%= CallDetails.getId() %>&contactId=<%= ContactDetails.getId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return doCheck(this);" method="post">
<dhv:evaluate exp="<%= !isPopup(request) %>">
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
<a href="ExternalContactsCalls.do?command=View&contactId=<%=ContactDetails.getId()%>">Calls</a> >
 <%if (request.getParameter("return") == null) { %>
	<a href="ExternalContactsCalls.do?command=Details&id=<%=CallDetails.getId()%>&contactId=<%=ContactDetails.getId()%>">Call Details</a> >
<%}%>
Modify Call<br>
<hr color="#BFBFBB" noshade>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <%@ include file="contact_details_header_include.jsp" %>
    </td>
  </tr>
  <dhv:evaluate if="<%= (!isPopup(request) || isInLinePopup(request)) %>"> 
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + ContactDetails.getId(); 
          String param2 = addLinkParams(request, "popup|popupType|actionId"); %>
      <dhv:container name="contacts" selected="calls" param="<%= param1 %>" appendToUrl="<%= param2 %>"/>
    </td>
  </tr>
  </dhv:evaluate>
  <tr>
  <td class="containerBack">
    <input type="hidden" name="modified" value="<%= CallDetails.getModified() %>">
    <% if (request.getParameter("return") != null) {%>
          <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
    <%}%>
    <input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
    <dhv:evaluate exp="<%= !isPopup(request) %>">
	<% if ("list".equals(request.getParameter("return"))) {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
	<%}else {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsCalls.do?command=Details&id=<%= CallDetails.getId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>
</dhv:evaluate>
  <dhv:evaluate exp="<%= isPopup(request)  && !isInLinePopup(request) %>">
    <input type="button" value="Cancel" onclick="javascript:window.close();"> 
  </dhv:evaluate>
    <input type="reset" value="Reset">
<br>
<%= showError(request, "actionError") %>

<%@ include file="call_include.jsp" %>

<br>
<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
<dhv:evaluate exp="<%= !isPopup(request) || isInLinePopup(request) %>">
	<% if ("list".equals(request.getParameter("return"))) {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
	<%}else {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContactsCalls.do?command=Details&id=<%= CallDetails.getId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>
</dhv:evaluate>
<dhv:evaluate exp="<%= isPopup(request)  && !isInLinePopup(request) %>">
  <input type="button" value="Cancel" onclick="javascript:window.close();"> 
</dhv:evaluate>
<input type="reset" value="Reset">
<input type="hidden" name="dosubmit" value="true">
</td>
</tr>
</table>
</form>

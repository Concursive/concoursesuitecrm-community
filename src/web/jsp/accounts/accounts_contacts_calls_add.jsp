<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="PreviousCallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%
  String trailSource = request.getParameter("trailSource");
%>
<body onLoad="javascript:document.forms[0].callTypeId.focus();">
<form name="addCall" action="AccountContactsCalls.do?command=Save&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Accounts.do?command=Search">Search Results</a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard">Dashboard</a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<% if("accounts".equals(trailSource)){ %>
<a href="AccountsCalls.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Activities</a> >
<% }else{ %>
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Contacts</a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Contact Details</a> >
<a href="AccountContactsCalls.do?command=View&contactId=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Activities</a> >
<% } %>
<% if(PreviousCallDetails.getId() > 0 && !"cancel".equals(request.getParameter("action"))){ %>
  <% if (!"list".equals(request.getParameter("return"))){ %>
    <a href="AccountContactsCalls.do?command=Details&id=<%= (PreviousCallDetails.getId() > -1 ? PreviousCallDetails.getId() : CallDetails.getId()) %>&contactId=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "view|trailSource") %>">Activity Details</a> >
  <% } %>
  Complete Activity
<% }else if(PreviousCallDetails.getId() > 0 && "cancel".equals(request.getParameter("action"))){ %>
  <% if (!"list".equals(request.getParameter("return"))){ %>
    <a href="AccountContactsCalls.do?command=Details&id=<%= (PreviousCallDetails.getId() > -1 ? PreviousCallDetails.getId() : CallDetails.getId()) %>&contactId=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "view|trailSource") %>">Activity Details</a> >
  <% } %>
  Cancel Activity
<% }else{ %>
Add Activity
<% } %>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="contacts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <%-- include contact menu --%>
      <% String param1 = "id=" + ContactDetails.getId(); 
    %>
        <strong><%= ContactDetails.getNameLastFirst() %>:</strong>
        [ <dhv:container name="accountscontacts" selected="calls" param="<%= param1 %>"/> ]
        <br>
        <br>
      <%-- include call add form --%>
      <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
      <% if ("list".equals(request.getParameter("return"))) {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
      <% }else{ %>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountContactsCalls.do?command=Details&id=<%= PreviousCallDetails.getId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';"> 
      <%}%>
      <br />
      <%= !"&nbsp;".equals(showError(request, "actionError").trim())? showError(request, "actionError"):showWarning(request, "actionWarning")%><iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
      <%@ include file="../contacts/call_include.jsp" %>
      &nbsp;
      <br>
      <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
      <% if ("list".equals(request.getParameter("return"))) {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
      <% }else{ %>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountContactsCalls.do?command=Details&id=<%= PreviousCallDetails.getId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';"> 
      <%}%>
      <input type="hidden" name="dosubmit" value="true">
      <input type="hidden" name="contactId" value="<%= ContactDetails.getId() %>">
      <dhv:evaluate if="<%= PreviousCallDetails.getId() > -1 %>">
      <input type="hidden" name="parentId" value="<%= PreviousCallDetails.getId() %>">
      </dhv:evaluate>
      <%= addHiddenParams(request, "action|return|trailSource|view") %>
      <br>
      &nbsp;
    </td>
  </tr>
</table>
</form>
</body>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OpportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/> 
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].description.focus();">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Accounts.do?command=Search">Search Results</a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard">Dashboard</a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Contacts</a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Contact Details</a> >
<a href="AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>">Opportunities</a> >
<% if ("list".equals(request.getParameter("return"))){ %>
<a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>">Opportunity Details</a> >
<% } %>
Modify Opportunity
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
    <% String param1 = "id=" + ContactDetails.getId(); 
    %>
        <strong><%= ContactDetails.getNameLastFirst() %>:</strong>
        [ <dhv:container name="accountscontacts" selected="opportunities" param="<%= param1 %>"/> ]
      <br>
      <br>
      <%-- actual form --%>
      <form name="modifyOpp" action="AccountContactsOpps.do?command=UpdateOpp&contactId=<%= ContactDetails.getId() %>&auto-populate=true" method="post">
      <input type="hidden" name="headerId" value="<%= OpportunityHeader.getId() %>">
      <input type="hidden" name="modified" value="<%= OpportunityHeader.getModified() %>">
      <% if (request.getParameter("return") != null) {%>
            <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
      <%}%>
            <input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
      <% if (request.getParameter("return") != null) {%>
        <% if (request.getParameter("return").equals("list")) {%>
            <input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
        <%}%>
      <%} else {%>
            <input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountContactsOpps.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
      <%}%>
      <br>
      <%= showError(request, "actionError") %>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><%= OpportunityHeader.getDescription() %></strong>
          </th>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            Description
          </td>
          <td>
            <input type="text" size="50" name="description" value="<%= toHtmlValue(OpportunityHeader.getDescription()) %>">
            <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
          </td>
        </tr>
      </table>
      &nbsp;
      <br>
      <input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
      <% if (request.getParameter("return") != null) {%>
        <% if (request.getParameter("return").equals("list")) {%>
        <input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
        <%}%>
      <%} else {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountContactsOpps.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
      <%}%>
      <input type="hidden" name="dosubmit" value="true">
      <%= addHiddenParams(request, "popup|popupType|actionId") %>
      </form>
   </td>
 </tr>
</table>
</body>


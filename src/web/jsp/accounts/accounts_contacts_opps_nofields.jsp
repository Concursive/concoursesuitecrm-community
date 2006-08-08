<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="OpportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%
  boolean allowMultiple = allowMultipleComponents(pageContext, OpportunityComponent.MULTPLE_CONFIG_NAME, "multiple");
  String trailSource = request.getParameter("trailSource");
%>
<form name="details" action="AccountContactsOpps.do?command=Fields&headerId=<%= OpportunityHeader.getId() %>" method="post">
<dhv:evaluate exp="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<% if("accounts".equals(trailSource)){ %>
<a href="AccountContactsOpps.do?command=ViewContactOpps&orgId=<%=OrgDetails.getOrgId()%>">Opportunities</a> >
<% }else{ %>
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Contacts</a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Contact Details</a> >
<a href="AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>">Opportunities</a> >
<dhv:evaluate if="<%= allowMultiple %>">
<a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "trailSource") %>">Opportunity Details</a> >
</dhv:evaluate>
<% } %>
<a href="AccountContactsOpps.do?command=Fields&headerId=<%=OpportunityHeader.getId()%><%= addLinkParams(request, "trailSource") %>">Folders</a> >
Record Details
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%-- include the account header --%>
<%@ include file="accounts_details_header_include.jsp" %>
<%-- include the accounts menu --%>
<% if("accounts".equals(trailSource)){ %>
<dhv:container name="accounts" selected="contactopportunities" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs" appendToUrl="<%= "&trailSource=accounts"+addLinkParams(request, "popup|popupType|actionId") %>"/>
<% }else{ %>
<dhv:container name="accounts" selected="contacts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>"/>
<% } %>
<%-- actual opportunity add form --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <%-- include contact menu --%>
      <% String param1 = "id=" + ContactDetails.getId(); String selected = "opportunities"; %>
      <%@ include file="accounts_contacts_details_header_include.jsp" %>
      <table cellpadding="4" cellspacing="0" border="0" width="100%">
      <tr>
        <td class="containerBack">
          <%-- include opp menu --%>
          <%-- include opportunity menu --%>
            <%--  TODO: Fix this broken stuff 2005-11-03
          <% param1 = "id=" + OpportunityHeader.getId(); selected = "folders";  String param2 = "&contactId=" + ContactDetails.getId() + ("accounts".equals(trailSource) ? "&trailSource=accounts" : ""); %>
          <%@ include file="accounts_contacts_opps_header_include.jsp" %>
            &nbsp;<br>
            There are currently no custom folders configured for this module.<br>
            Custom folders can be configured by an administrator.<br>
            &nbsp;
            --%>
        </td>
      </tr>
    </table>
  </td> 
  </tr>
</table
<%= addHiddenParams(request, "popup|popupType|actionId|trailSource") %>>
</form>

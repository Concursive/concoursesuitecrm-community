<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.HtmlSelect" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="relationshipTypeSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="objectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="objectSubSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do">Contacts</a> > 
<a href="ExternalContacts.do?command=SearchContacts">Search Results</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
<a href="ExternalContactsPrototype.do?module=ExternalContacts&include=contacts/companydirectory_relationships_view.jsp&contactId=<%=ContactDetails.getId()%>">Relationships</a> >
Build Relationship
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="contact_details_header_include.jsp" %>
<% String param1 = "id=" + ContactDetails.getId(); %>
<dhv:container name="contacts" selected="relationships" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <%= toHtml(ContactDetails.getNameFull()) %> (Subject) is a/an: <%= relationshipTypeSelect.getHtml() %> (Relationship Type)<br>
      &nbsp;<br>
      &nbsp;&nbsp;&nbsp;&nbsp;<%= objectSelect.getHtml() %> (Object Type)<br>
      &nbsp;<br>
      &nbsp;&nbsp;&nbsp;&nbsp;<%= objectSubSelect.getHtml() %> (Object Sub-Type)<br>
      &nbsp;<br>
      Choose specific object:<br>
      <iframe src="ExternalContactsPrototype.do?include=companydirectory_relationships_viewopportunities.jsp&inline=true" frameborder="0" <dhv:browser id="ie" include="false">width="100%" height="150"</dhv:browser> <dhv:browser id="ie">style="border: 1px solid #cccccc; width: 100%; height: 200;"</dhv:browser>>
        browser doesn't support this view
      </iframe>
      <input type="BUTTON" value="Save" onclick="window.location.href='ExternalContactsPrototype.do?module=ExternalContacts&include=contacts/companydirectory_relationships_view.jsp&contactId=<%= ContactDetails.getId() %>'">
      <input type="BUTTON" value="Cancel" onclick="window.location.href='ExternalContactsPrototype.do?module=ExternalContacts&include=contacts/companydirectory_relationships_view.jsp&contactId=<%= ContactDetails.getId() %>'">
    </td>
  </tr>
</table>


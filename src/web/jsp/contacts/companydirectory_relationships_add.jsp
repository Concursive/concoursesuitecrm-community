<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.*,org.aspcfs.utils.web.HtmlSelect" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="relationshipTypeSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="objectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="objectSubSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<a href="ExternalContacts.do">Contacts &amp; Resources</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
<a href="ExternalContactsPrototype.do?module=ExternalContacts&include=companydirectory_relationships_view.jsp&contactId=<%=ContactDetails.getId()%>">Relationships</a> >
Build Relationship
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + ContactDetails.getId(); %>
      <dhv:container name="contacts" selected="relationships" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <%= toHtml(ContactDetails.getNameFull()) %> (Subject) is a/an: <%= relationshipTypeSelect.getHtml() %> (Relationship Type)<br>
      &nbsp;<br>
      &nbsp;&nbsp;&nbsp;&nbsp;<%= objectSelect.getHtml() %> (Object Type)<br>
      &nbsp;<br>
      &nbsp;&nbsp;&nbsp;&nbsp;<%= objectSubSelect.getHtml() %> (Object Sub-Type)<br>
      &nbsp;<br>
      Choose specific object:<br>
      <iframe src="ExternalContactsPrototype.do?include=companydirectory_relationships_viewopportunities.jsp" frameborder="0" <dhv:browser id="ns">width="100%" height="150"</dhv:browser> <dhv:browser id="ie">style="border: 1px solid #cccccc; width: 100%; height: 200;"</dhv:browser>>
        browser doesn't support this view
      </iframe>
      <input type="BUTTON" value="Save" onclick="window.location.href='ExternalContactsPrototype.do?module=ExternalContacts&include=companydirectory_relationships_view.jsp&contactId=<%= ContactDetails.getId() %>'">
      <input type="BUTTON" value="Cancel" onclick="window.location.href='ExternalContactsPrototype.do?module=ExternalContacts&include=companydirectory_relationships_view.jsp&contactId=<%= ContactDetails.getId() %>'">
    </td>
  </tr>
</table>


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="howDirectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
Relationships
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
<dhv:permission name="demo-add"><a href="ExternalContactsPrototype.do?module=ExternalContacts&include=companydirectory_relationships_add.jsp&contactId=<%= ContactDetails.getId() %>">Build New Relationship</a></dhv:permission><br>
&nbsp;<br>
<input type="BUTTON" value="Relationship with..." onclick="javascript:window.location.href='ExternalContactsPrototype.do?module=ExternalContacts&include=companydirectory_relationships_view.jsp&contactId=<%= ContactDetails.getId() %>'">
<input type="BUTTON" value="...reached by" onclick="javascript:window.location.href='ExternalContactsPrototype.do?module=ExternalContacts&include=companydirectory_relationships_view2.jsp&contactId=<%= ContactDetails.getId() %>'">
<input type="BUTTON" value="Build Group" onclick=""><br>
&nbsp;<br>
How Direct? <%= howDirectSelect.getHtml() %><br>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign="center" align="left">
      <strong>Relationships</strong>
    </td>
    <td valign="center" align="left">
      <strong>Index</strong>
    </td>
  </tr>
  <!-- Paged List -->
  <tr class="containerBody">
    <td width="100%" valign="center" class="row1">
      <a href="#">Name</a> (Spouse) to <%= toHtml(ContactDetails.getNameFull()) %>
    </td>
    <td width="10" valign="center" align="right" nowrap class="row1">
      1.00
    </td>
  </tr>
  <tr class="row2">
    <td width="100%" valign="center">
      <a href="#">Name</a> (Friend) to <%= toHtml(ContactDetails.getNameFull()) %>
    </td>
    <td width="10" valign="center" align="right" nowrap>
      .90
    </td>
  </tr>
  <tr class="row1">
    <td width="100%" valign="center">
      <a href="#">Name</a> (Co-Worker) to <%= toHtml(ContactDetails.getNameFull()) %>
    </td>
    <td width="10" valign="center" align="right" nowrap>
      .60
    </td>
  </tr>
  <!-- End Paged List -->
</table>
</td>
</tr>
</table>


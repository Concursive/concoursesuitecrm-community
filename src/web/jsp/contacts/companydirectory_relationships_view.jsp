<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="relationshipTypeSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
Relationships
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <%@ include file="contact_details_header_include.jsp" %>
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
Relationship Type: <%= relationshipTypeSelect.getHtml() %><br>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="demo-edit,demo-delete">
    <td valign="center" align="left">
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td valign="center" align="left">
      <strong>Subject</strong>
    </td>
    <td valign="center" align="left">
      <strong>Relationship</strong>
    </td>
    <td valign="center" align="left">
      <strong>Object</strong>
    </td>
  </tr>
  <!-- Paged List -->
  <tr class="containerBody">
    <dhv:permission name="demo-edit,demo-delete">
      <td width="8" valign="center" nowrap class="row1">
        <dhv:permission name="demo-edit"><a href="ExternalContactsPrototype.do?module=ExternalContacts&contactId=<%= ContactDetails.getId() %>&return=list">Edit</a></dhv:permission><dhv:permission name="demo-edit,demo-delete" all="true">|</dhv:permission><dhv:permission name="demo-delete"><a href="javascript:confirmDelete('ExternalContactsPrototype.do?contactId=<%= ContactDetails.getId() %>');">Del</a></dhv:permission>
      </td>
    </dhv:permission>
    <td width="40%" valign="center" class="row1">
      <%= toHtml(ContactDetails.getNameFull()) %> ...
    </td>
    <td width="20%" valign="center" nowrap class="row1">
      Influencer of
    </td>
    <td width="20%" valign="center" nowrap class="row1">
      Enterprise CFS for 2003 (Opp)
    </td>
  </tr>
  <tr class="row2">
    <dhv:permission name="demo-edit,demo-delete">
      <td width="8" valign="center" nowrap>
        <dhv:permission name="demo-edit"><a href="ExternalContactsPrototype.do?module=ExternalContacts&contactId=<%= ContactDetails.getId() %>&return=list">Edit</a></dhv:permission><dhv:permission name="demo-edit,demo-delete" all="true">|</dhv:permission><dhv:permission name="demo-delete"><a href="javascript:confirmDelete('ExternalContactsPrototype.do?contactId=<%= ContactDetails.getId() %>');">Del</a></dhv:permission>
      </td>
    </dhv:permission>
    <td width="40%" valign="center">
      <%= toHtml(ContactDetails.getNameFull()) %> ...
    </td>
    <td width="20%" valign="center" nowrap>
      Author of
    </td>
    <td width="20%" valign="center" nowrap>
      Proposal XYZ (Opp)
    </td>
  </tr>
  <!-- End Paged List -->
</table>
</td>
</tr>
</table>


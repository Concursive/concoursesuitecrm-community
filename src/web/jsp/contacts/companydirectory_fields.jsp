<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="ContactDetails" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<jsp:useBean id="CategoryList" class="com.darkhorseventures.cfsbase.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="com.darkhorseventures.cfsbase.CustomFieldCategory" scope="request"/>
<%@ include file="initPage.jsp" %>
<form name="details" action="/ExternalContacts.do?command=Fields&contactId=<%= ContactDetails.getId() %>" method="post">
<a href="/ExternalContacts.do?command=ListContacts">Back to Contact List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <a href="/ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>"><font color="#000000">Details</font></a><dhv:permission name="contacts-external_contacts-folders-view"> | 
      <a href="/ExternalContacts.do?command=Fields&contactId=<%= ContactDetails.getId() %>"><font color="#0000FF">Folders</font></a></dhv:permission><dhv:permission name="contacts-external_contacts-calls-view"> | 
      <a href="/ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %>"><font color="#000000">Calls</font></a></dhv:permission><dhv:permission name="contacts-external_contacts-messages-view"> |
      <a href="/ExternalContacts.do?command=ViewMessages&contactId=<%= ContactDetails.getId() %>"><font color="#000000">Messages</font></a></dhv:permission><dhv:permission name="contacts-external_contacts-opportunities-view"> |
      <a href = "/ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>"><font color="#000000">Opportunities</font></a></dhv:permission> 
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<a href="/ExternalContacts.do?command=Fields&contactId=<%= ContactDetails.getId() %>&catId=<%= Category.getId() %>">Back to <%= Category.getName() %> Records</a><br>
&nbsp;<br>
<dhv:permission name="contacts-external_contacts-folders-edit">
<input type="submit" value="Modify" onClick="javascript:this.form.action='/ExternalContacts.do?command=ModifyFields&contactId=<%= ContactDetails.getId() %>&catId=<%= (String)request.getAttribute("catId") %>&recId=<%= Category.getRecordId() %>'"><br>
&nbsp;<br>
</dhv:permission>

<%
  Iterator groups = Category.iterator();
  while (groups.hasNext()) {
    CustomFieldGroup thisGroup = (CustomFieldGroup)groups.next();
%>    
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
	    <strong><%= thisGroup.getName() %></strong>
	  </td>
  </tr>
<%  
  Iterator fields = thisGroup.iterator();
  if (fields.hasNext()) {
    while (fields.hasNext()) {
      CustomField thisField = (CustomField)fields.next();
%>    
    <tr class="containerBody">
      <%-- Do not use toHtml() here, it's done by CustomField --%>
      <td valign="top" nowrap class="formLabel">
        <%= thisField.getNameHtml() %>
      </td>
      <td valign="top" width="100%">
        <%= thisField.getValueHtml() %>
      </td>
    </tr>
<%    
    }
  } else {
%>
    <tr class="containerBody">
      <td colspan=2>
        <font color="#9E9E9E">No fields available.</font>
      </td>
    </tr>
<%}%>
</table>
&nbsp;
<%}%>
<dhv:permission name="contacts-external_contacts-folders-edit">
<br>
<input type="submit" value="Modify" onClick="javascript:this.form.action='/ExternalContacts.do?command=ModifyFields&contactId=<%= ContactDetails.getId() %>&catId=<%= (String)request.getAttribute("catId") %>&recId=<%= Category.getRecordId() %>'">
</dhv:permission>
</td></tr>
</table>
</form>

<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="Record" class="org.aspcfs.modules.base.CustomFieldRecord" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<form name="details" action="ExternalContacts.do?command=Fields&contactId=<%= ContactDetails.getId() %>" method="post">
<dhv:evaluate exp="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do">Contacts</a> > 
<a href="ExternalContacts.do?command=SearchContacts">Search Results</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
<dhv:evaluate if="<%= (Category.getAllowMultipleRecords() && Record == null) %>">
  List of Folder Records
</dhv:evaluate>
<dhv:evaluate if="<%= (Category.getAllowMultipleRecords() && Record != null) %>">
<a href="ExternalContacts.do?command=Fields&contactId=<%=ContactDetails.getId()%>&catId=<%= Category.getId() %>">List of Folder Records</a> >
  Folder Record Details
</dhv:evaluate>
<dhv:evaluate if="<%= (!Category.getAllowMultipleRecords()) %>">
  Folder Record Details
</dhv:evaluate>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%@ include file="contact_details_header_include.jsp" %>
<% String param1 = "id=" + ContactDetails.getId(); 
   String param2 = addLinkParams(request, "popup|popupType|actionId"); %>
<dhv:container name="contacts" selected="folders" param="<%= param1 %>" appendToUrl="<%= param2 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<% if (!Category.getAllowMultipleRecords()) {
     CategoryList.setJsEvent("ONCHANGE=\"javascript:document.forms[0].submit();\"");
%>
  Folder: <%= CategoryList.getHtmlSelect("catId", (String)request.getAttribute("catId")) %><%= (Category.getReadOnly()?"&nbsp;<img border='0' valign='absBottom' src='images/lock.gif' alt='Folder is read-only'>":"") %><br>
  &nbsp;<br>
  This folder can have only one record...<br>
  &nbsp;<br>
<% } %>
<dhv:evaluate exp="<%= (!Category.getReadOnly()) %>">
<dhv:permission name="contacts-external_contacts-folders-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='ExternalContacts.do?command=ModifyFields&contactId=<%= ContactDetails.getId() %>&catId=<%= (String)request.getAttribute("catId") %>&recId=<%= Category.getRecordId() %>';submit();"></dhv:permission>
<dhv:permission name="contacts-external_contacts-folders-delete"><input type="button" value="Delete Folder Record" onClick="javascript:this.form.action='ExternalContacts.do?command=DeleteFields&contactId=<%= ContactDetails.getId() %>&catId=<%= (String)request.getAttribute("catId") %>&recId=<%= Category.getRecordId() %>';confirmSubmit(this.form);"></dhv:permission>
<dhv:permission name="contacts-external_contacts-folders-edit,contacts-external_contacts-folders-delete">
<br>&nbsp;<br>
</dhv:permission>
</dhv:evaluate>
<%
  Iterator groups = Category.iterator();
  while (groups.hasNext()) {
    CustomFieldGroup thisGroup = (CustomFieldGroup)groups.next();
%>    
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2">
	    <strong><%= thisGroup.getName() %></strong>
	  </th>
  </tr>
<%  
  Iterator fields = thisGroup.iterator();
  if (fields.hasNext()) {
    while (fields.hasNext()) {
      CustomField thisField = (CustomField)fields.next();
%>    
    <tr class="containerBody">
      <%-- Do not use toHtml() here, it's done by CustomField --%>
      <td valign="top" class="formLabel" nowrap>
        <%= thisField.getNameHtml() %>
      </td>
      <td valign="top">
        <%= thisField.getValueHtml() %>
      </td>
    </tr>
<%    
    }
  } else {
%>
    <tr class="containerBody">
      <td colspan="2">
        <font color="#9E9E9E">No fields available.</font>
      </td>
    </tr>
<%}%>
</table>
&nbsp;
<%}%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Record Information</strong>
    </th>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Entered
    </td>
    <td>
      <dhv:username id="<%= Record.getEnteredBy() %>" />
      <zeroio:tz timestamp="<%= Record.getEntered()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Modified
    </td>
    <td>
      <dhv:username id="<%= Record.getModifiedBy() %>" />
      <zeroio:tz timestamp="<%= Record.getModified()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
    </td>
  </tr>
</table>
&nbsp;
<br>
<dhv:evaluate exp="<%= (!Category.getReadOnly()) %>">
<dhv:permission name="contacts-external_contacts-folders-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='ExternalContacts.do?command=ModifyFields&contactId=<%= ContactDetails.getId() %>&catId=<%= (String)request.getAttribute("catId") %>&recId=<%= Category.getRecordId() %>';submit();"></dhv:permission>
<dhv:permission name="contacts-external_contacts-folders-delete"><input type="button" value="Delete Folder Record" onClick="javascript:this.form.action='ExternalContacts.do?command=DeleteFields&contactId=<%= ContactDetails.getId() %>&catId=<%= (String)request.getAttribute("catId") %>&recId=<%= Category.getRecordId() %>';confirmSubmit(this.form);"></dhv:permission>
</dhv:evaluate>
</td></tr>
</table>
<%= addHiddenParams(request, "popup|popupType|actionId") %>
</form>

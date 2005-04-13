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
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="session"/>
<jsp:useBean id="ProjectListSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="session"/>
<jsp:useBean id="finalContacts" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="Filters" class="org.aspcfs.modules.base.FilterList" scope="request"/>
<table width="100%" border="0">
  <tr>
    <td>
      <select size="1" name="listView" onChange="javascript:setFieldSubmit('listFilter1','-1','contactListView');">
      <%
        Iterator filters = Filters.iterator();
        while(filters.hasNext()){
        Filter thisFilter = (Filter) filters.next();
      %>
          <option <%= ContactListInfo.getOptionValue(thisFilter.getValue()) %>><%= thisFilter.getDisplayName() %></option>
      <%}%>
       </select>
<% 
  if (ContactListInfo.getListView().equals("employees")) {
    DepartmentList.setSelectSize(1); 
    DepartmentList.setJsEvent("onchange=\"javascript:document.contactListView.submit();\"");
%>
        <%= DepartmentList.getHtmlSelect("listFilter1",ContactListInfo.getFilterKey("listFilter1")) %>
<%
  } else if (ContactListInfo.getListView().equals("myprojects")) {
    ProjectListSelect.setSelectSize(1);  
    ProjectListSelect.setJsEvent("onchange=\"javascript:document.contactListView.submit();\"");
%>
    <%= ProjectListSelect.getHtml("listFilter1",ContactListInfo.getFilterKey("listFilter1")) %>
<%} else{ %>
      <select size="1" name="temp">
        <option value="0"><dhv:label name="calendar.none.4dashes">--None--</dhv:label></option>
      </select>
<%}%>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") + showAttribute(request, "oneContactRequired") %>" object="ContactListInfo" showHiddenParams="true" enableJScript="true" form="contactListView"/>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th align="center" width="8">
      &nbsp;
    </th>
    <th>
      <dhv:label name="contacts.name">Name</dhv:label>
    </th>
    <th>
      <dhv:label name="accounts.accounts_add.Email">Email</dhv:label>
    </th>
    <th>
      <dhv:label name="accounts.accounts_contacts_add.ContactType">Contact Type</dhv:label>
    </th>
  </tr>
<%
	Iterator j = ContactList.iterator();
	if (j.hasNext()) {
		int rowid = 0;
		int count = 0;
    while (j.hasNext()) {
			count++;
      rowid = (rowid != 1?1:2);
      Contact thisContact = (Contact)j.next();
      int thisContactId = thisContact.getId();
      if("true".equals(request.getParameter("usersOnly"))){
        thisContactId = thisContact.getUserId();
      }
%>
  <tr class="row<%= rowid+((selectedContacts.get(new Integer(thisContactId))!= null)?"hl":"") %>">
    <td align="center" nowrap width="8">
<% 
  if ("list".equals(request.getParameter("listType"))) { %>  
      <input type="checkbox" name="checkcontact<%= count %>" value=<%= thisContactId %><%= ((selectedContacts.get(new Integer(thisContactId))!= null)?" checked":"") %> onClick="highlight(this,'<%=User.getBrowserId()%>');">
<%} else {%>
      <a href="javascript:document.contactListView.finalsubmit.value = 'true';setFieldSubmit('rowcount','<%= count %>','contactListView');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>
<%}%>
      <input type="hidden" name="hiddencontactid<%= count %>" value="<%= thisContactId %>">
      <input type="hidden" name="hiddenname<%= count %>" value="<%= toHtml(thisContact.getValidName()) %>">
    </td>
    <td nowrap>
      <%= toHtml(thisContact.getNameFull()) %>
    </td>
<%
      String email ="",emailType ="";
      int size   = thisContact.getEmailAddressList().size();
      Iterator i = thisContact.getEmailAddressList().iterator();
      if (size < 2){
        if (i.hasNext()){
          EmailAddress thisAddress = (EmailAddress)i.next();
          email     =  thisAddress.getEmail();
          emailType =  thisAddress.getTypeName();
        }
        if (!email.equals("")){
%>
    <td nowrap><%= toHtml(email) %> (<%= toHtml(emailType) %>)</td>
<%  
        } else {
%>
    <td><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.None">None</dhv:label></td>
<%      
        }
      } else { 
%>
    <td nowrap>
      <select size="1" name="contactemail<%= count %>">
<%
        while (i.hasNext()) {
          EmailAddress thisAddress = (EmailAddress)i.next();
          email     =  thisAddress.getEmail();
          emailType =  thisAddress.getTypeName();
          String selectedEmail = "";
          if((selectedContacts.get(new Integer(thisContactId))!= null)){
            selectedEmail = (String)selectedContacts.get(new Integer(thisContactId));
          }
          if (!email.equals("")) {
%>
        <option value="<%=email%>" <%=((selectedEmail.equals(email))?" selected":"")%>><%=toHtml(email)%> (<%=toHtml(emailType)%>)</option>
<%
          }
        }
%>
      </select>
    </td>
    <%}%>
    <td>
      <%= toHtml(thisContact.getTypesNameString()) %>
      <input type="hidden" name="contactemail<%= count %>" value="<%= toHtmlValue(email) %>">
    </td>
  </tr>
<%
    }
  } else {
%>
  <tr>
    <td class="containerBody" colspan="4">
      <dhv:label name="campaign.noContactsMatchedQuery">No contacts matched query.</dhv:label>
    </td>
  </tr>
<%}%>
  <input type="hidden" name="finalsubmit" value="false">
  <input type="hidden" name="rowcount" value="0">
  <input type="hidden" name="displayFieldId" value="<%= toHtmlValue(request.getParameter("displayFieldId")) %>">
  <input type="hidden" name="hiddenFieldId" value="<%= toHtmlValue(request.getParameter("hiddenFieldId")) %>">
  <input type="hidden" name="listType" value="<%= toHtmlValue(request.getParameter("listType")) %>">
  <input type="hidden" name="usersOnly" value="<%= toHtmlValue(request.getParameter("usersOnly")) %>">
  <input type="hidden" name="hierarchy" value="<%= toHtmlValue(request.getParameter("hierarchy")) %>">
  <input type="hidden" name="nonUsersOnly" value="<%= toHtmlValue(request.getParameter("nonUsersOnly")) %>">
  <input type="hidden" name="campaign" value="<%= toHtmlValue(request.getParameter("campaign")) %>">
  <input type="hidden" name="filters" value="<%= toHtmlValue(request.getParameter("filters")) %>">
  <input type="hidden" name="displayType" value="<%= toHtmlValue(request.getParameter("displayType")) %>">
</table>

<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
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
    DepartmentList.setJsEvent("onchange=\"javascript:document.forms[0].submit();\"");
%>
        <%= DepartmentList.getHtmlSelect("listFilter1",ContactListInfo.getFilterKey("listFilter1")) %>
<%
  } else if (ContactListInfo.getListView().equals("myprojects")) {
    ProjectListSelect.setSelectSize(1);  
    ProjectListSelect.setJsEvent("onchange=\"javascript:document.forms[0].submit();\"");
%>
    <%= ProjectListSelect.getHtml("listFilter1",ContactListInfo.getFilterKey("listFilter1")) %>
<%} else{ %>
      <select size="1" name="temp">
        <option value="0">--None--</option>
      </select>
<%}%>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ContactListInfo" showHiddenParams="true" enableJScript="true"/>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th align="center" width="8">
      &nbsp;
    </th>
    <th>
      Name
    </th>
    <th>
      Email
    </th>
    <th>
      Contact Type
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
      <a href="javascript:document.contactListView.finalsubmit.value = 'true';setFieldSubmit('rowcount','<%= count %>','contactListView');">Select</a>
<%}%>
      <input type="hidden" name="hiddencontactid<%= count %>" value="<%= thisContactId %>">
      <input type="hidden" name="hiddenname<%= count %>" value="<%= toHtml(thisContact.getValidName()) %>">
    </td>
    <td nowrap>
      <%= toHtml(thisContact.getValidName()) %>
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
    <td>None</td>
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
      No contacts matched query.
    </td>
  </tr>
<%}%>
  <input type="hidden" name="finalsubmit" value="false">
  <input type="hidden" name="rowcount" value="0">
  <input type="hidden" name="displayFieldId" value="<%= toHtmlValue(request.getParameter("displayFieldId")) %>">
  <input type="hidden" name="hiddenFieldId" value="<%= toHtmlValue(request.getParameter("hiddenFieldId")) %>">
  <input type="hidden" name="listType" value="<%= toHtmlValue(request.getParameter("listType")) %>">
  <input type="hidden" name="usersOnly" value="<%= toHtmlValue(request.getParameter("usersOnly")) %>">
  <input type="hidden" name="nonUsersOnly" value="<%= toHtmlValue(request.getParameter("nonUsersOnly")) %>">
  <input type="hidden" name="campaign" value="<%= toHtmlValue(request.getParameter("campaign")) %>">
  <input type="hidden" name="filters" value="<%= toHtmlValue(request.getParameter("filters")) %>">
  <input type="hidden" name="displayType" value="<%= toHtmlValue(request.getParameter("displayType")) %>">
</table>

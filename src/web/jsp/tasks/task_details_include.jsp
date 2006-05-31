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
<%@ page import="java.text.DateFormat" %>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EstimatedLOETypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketTaskCategoryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="tasks.taskDetails">Task Details</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(Task.getDescription()) %>
    </td>
  </tr>
  <dhv:evaluate if="<%= ticketTaskCategoryList != null && ticketTaskCategoryList.size() > 0 %>">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accountasset_include.Category">Category</dhv:label>
      </td>
      <td>
        <%= toHtml(ticketTaskCategoryList.getSelectedValue(Task.getTicketTaskCategoryId())) %>
      </td>
    </tr>
  </dhv:evaluate>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_calls_list.DueDate">Due Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= Task.getDueDate() %>" dateOnly="true" timeZone="<%= Task.getDueDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(Task.getDueDateTimeZone())){%>
      <br>
      <zeroio:tz timestamp="<%= Task.getDueDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel"><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label></td>
    <td>
      <%= PriorityList.getSelectedValue(Task.getPriority()) %>
    </td>
  </tr>
  <tr class="containerBody"> 
    <td class="formLabel"><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></td>
    <td>
      <% if(Task.getComplete()){ %>
        <img src="images/box-checked.gif">
      <% }else{ %>
        <dhv:label name="tasks.pending">Pending</dhv:label>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody"> 
    <td class="formLabel">
      <dhv:label name="tasks.sharing">Sharing</dhv:label>
    </td>
    <td>
<% if(Task.getSharing()==1) {%>
  <dhv:label name="tasks.personal.lowercase">personal</dhv:label>
<%} else {%>
  <dhv:label name="tasks.public.lowercase">public</dhv:label>
<%}%>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="accounts.accounts_contacts_calls_list.AssignedTo">Assigned To</dhv:label>
    </td>
    <td>
         <dhv:username id="<%= Task.getOwner() %>"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="tasks.estimatedLOE">Estimated LOE</dhv:label>
    </td>
    <td>
      <dhv:evaluate if="<%= (Task.getEstimatedLOE() != -1.0) %>">
        <%=Task.getEstimatedLOE()%>&nbsp;<%= toHtml(EstimatedLOETypeList.getSelectedValue(Task.getEstimatedLOEType())) %>
      </dhv:evaluate>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel"><dhv:label name="accounts.accounts_add.Notes">Notes</dhv:label></td>
    <td>
      <%= !"".equals(toHtml(Task.getNotes())) ?  toHtml(Task.getNotes()) : "&nbsp;"%>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="tasks.linkContact">Link Contact</dhv:label>
    </td>
    <td>
      <% if(Task.getContactName()!=null) {%>
        <%= toHtml(Task.getContactName()) %>
      <%} else {%>
        <dhv:label name="accounts.accounts_contacts_calls_details_followup_include.None">None</dhv:label>
      <%}%>
    </td>
  </tr>
</table>

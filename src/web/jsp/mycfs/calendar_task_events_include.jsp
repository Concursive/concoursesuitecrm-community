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
<%-- draws the task events for a specific day --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.tasks.base.Task,org.aspcfs.modules.base.Constants" %>
<%
  TaskEventList taskEventList = (TaskEventList) thisDay.get(category);
%>
<%-- include pending tasks --%>
<dhv:evaluate if="<%= taskEventList.getPendingTasks().size() > 0 %>">
<table border="0" id="pendingtaskdetails<%= toFullDateString(thisDay.getDate()) %>" width="100%">
  <%-- title row --%>
  <tr>
    <td>&nbsp;</td>
    <td colspan="3" nowrap class="eventName">
      <img border="0" src="images/box.gif" align="absmiddle" title="Incomplete Tasks" />
      <dhv:label name="calendar.incompleteTasks">Incomplete Tasks</dhv:label>
      (<%= taskEventList.getPendingTasks().size() %>)
    </td>
  </tr>
  <%-- include task details --%>
  <%
    Iterator j = taskEventList.getPendingTasks().iterator();
    if(j.hasNext()){
  %>
    <tr>
      <th>
        &nbsp;
      </th>
      <th class="weekSelector" width="100%">
        <strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong>
      </th>
      <th class="weekSelector" nowrap>
        <strong><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label></strong>
      </th>
      <th class="weekSelector" nowrap>
        <strong><dhv:label name="ticket.age">Age</dhv:label></strong>
      </th>
    </tr>
  <%  
      while(j.hasNext()){
      Task pendingTask = (Task) j.next();
      menuCount++;
    %>
    <tr>
     <td>
       <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <a href="javascript:displayTaskMenu('select-arrow<%= menuCount %>','menuTask', '<%= Constants.TASKS %>', '<%=  pendingTask.getId() %>', '<%= pendingTask.getContactId() %>', '<%= pendingTask.getTicketId() %>', '<%= ((pendingTask.getContactId()!=-1) && pendingTask.getContact().getEmployee())?"yes":"no" %>');"
       onMouseOver="over(0, <%= menuCount %>)" onmouseout="out(0, <%= menuCount %>);hideMenu('menuTask');"><img 
        src="images/select-arrow.gif" name="select-arrow<%= menuCount %>" id="select-arrow<%= menuCount %>" align="absmiddle" border="0" /></a>
     </td>
     <td nowrap>
       <%= toHtml(pendingTask.getDescription()) %>
     </td>
     <td nowrap>
       <%= pendingTask.getPriority() %>
     </td>
     <td nowrap>
       <%= pendingTask.getAge() %> 
     </td>
    </tr>
   <% }
   } %>
</table>
</dhv:evaluate>


<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
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
<table border="0">
  <tr>
    <td colspan="6" nowrap>
      <%-- event name --%>
      <img border="0" src="images/box.gif" align="texttop" title="Tasks"><a href="javascript:changeImages('pendingtasksimage<%=toFullDateString(thisDay.getDate()) %>','images/arrowdown.gif','images/arrowright.gif');javascript:switchStyle(document.getElementById('pendingtaskdetails<%=toFullDateString(thisDay.getDate()) %>'));" onMouseOver="window.status='View Details';return true;" onMouseOut="window.status='';return true;"><img src="<%= firstEvent ? "images/arrowdown.gif" : "images/arrowright.gif"%>" name="pendingtasksimage<%=toFullDateString(thisDay.getDate())%>" id="<%= firstEvent ? "0" : "1"%>" border="0" title="Click To View Details">Incomplete Tasks</a>&nbsp;(<%= taskEventList.getPendingTasks().size() %>)
    </td>
  </tr>
</table>
<table border="0" id="pendingtaskdetails<%= toFullDateString(thisDay.getDate()) %>" style="<%= firstEvent ? "display:" : "display:none"%>">
  <%-- include task details --%>
  <%
    Iterator j = taskEventList.getPendingTasks().iterator();
    if(j.hasNext()){
  %>
    <tr>
      <th>
        &nbsp
      </th>
      <th class="weekSelector" width="100%">
        <strong>Description</strong>
      </th>
      <th class="weekSelector" nowrap>
        <strong>Priority</strong>
      </th>
      <th class="weekSelector" nowrap>
        <strong>Age</strong>
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
       <a href="javascript:displayTaskMenu('select<%= menuCount %>','menuTask', '<%= Constants.TASKS %>', '<%=  pendingTask.getId() %>', '<%= pendingTask.getContactId() %>', '<%= ((pendingTask.getContactId()!=-1) && pendingTask.getContact().getEmployee())?"yes":"no" %>');"
       onMouseOver="over(0, <%= menuCount %>)" onmouseout="out(0, <%= menuCount %>);hideMenu('menuTask');"><img src="images/select.gif" name="select<%= menuCount %>" id="select<%= menuCount %>" align="absmiddle" border="0"></a>
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


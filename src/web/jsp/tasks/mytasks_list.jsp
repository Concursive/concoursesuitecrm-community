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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.tasks.base.*,org.aspcfs.modules.base.EmailAddress,org.aspcfs.modules.base.PhoneNumber, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="TaskList" class="org.aspcfs.modules.tasks.base.TaskList" scope="request"/>
<jsp:useBean id="TaskListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="mytasks_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');

  function changeCurrentImages(id, one, two) {
    var img = document[id];
    if (img.id == '1') {
      img.id = "0";
      window.frames['server_commands'].location.href = one+'&imageId='+id;
    } else {
      img.id = "1";
      window.frames['server_commands'].location.href = two+'&imageId='+id;
    }
  }
  
  function reopen() {
    window.location.href = 'MyTasks.do';
  }
  
  function getSharing(chk2) {
    if (chk2 == true) {
      return '1';
    }
    return '0';
  }

</script>
<dhv:permission name="myhomepage-tasks-add">
 <body onLoad="javascript:document.forms['addTask'].description.focus();">
</dhv:permission>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
<dhv:label name="myitems.tasks">Tasks</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="myhomepage-tasks-add">
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong><dhv:label name="tasks.quicklyAddTask">Quickly Add a Task</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <form name="addTask" action="MyTasks.do?command=Insert&auto-populate=true" method="post" onSubmit="return validateTask();">
    <td nowrap>
      <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>&nbsp;
      <input type="text" name="description" value="" size="30">
      <font color="red">*</font>
      <input type="hidden" id="owner" name="owner" value="<%= User.getUserRecord().getId() %>">
      <input type="hidden" name="priority" value="1">
      <input type="checkbox" name="chk2" value="true" onclick="javascript:setField('sharing',document.addTask.chk2.checked,'addTask');">
      <dhv:label name="tasks.personal">Personal</dhv:label>
      <input type="hidden" name="sharing" value="">
      <input type="button" value="<dhv:label name="button.moreFields">More Fields...</dhv:label>"
        onClick="javascript:window.location.href='MyTasks.do?command=New&auto-populate=true&description='+ document.addTask.description.value+'&sharing='+getSharing(document.addTask.chk2.checked);"/>
      <input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>">
    </td>
  </form>
  </tr>
</table>
<br />
<a href="javascript:window.location.href='MyTasks.do?command=New'"><dhv:label name="tasks.addAdvancedTask">Add an Advanced Task</dhv:label></a><br>
<br />
</dhv:permission>
<form name="taskListView" method="post" action="MyTasks.do?command=ListTasks">
<table width="100%" border="0">
  <tr>
    <td>
      <select size="1" name="listFilter1" onChange="javascript:document.taskListView.submit();">
        <option value="my" <%= TaskListInfo.getFilterValue("listFilter1").equalsIgnoreCase("taskstome")?" selected":"" %>><dhv:label name="tasks.myTasks">My Tasks</dhv:label></option>
        <option value="tasksbyme" <%= TaskListInfo.getFilterValue("listFilter1").equalsIgnoreCase("tasksbyme")?" selected":"" %>><dhv:label name="tasks.tasksAssignedByMe">Tasks Assigned By Me</dhv:label></option>
      </select>
     <% if (!TaskListInfo.getFilterValue("listFilter1").equalsIgnoreCase("all")) { %>
       <select size="1" name="listFilter2" onChange="javascript:document.taskListView.submit();">
        <option value="false" <%= TaskListInfo.getFilterValue("listFilter2").equalsIgnoreCase("false")?" selected":"" %>><dhv:label name="calendar.incompleteTasks">Incomplete Tasks</dhv:label></option>
        <option value="true" <%= TaskListInfo.getFilterValue("listFilter2").equalsIgnoreCase("true")?" selected":"" %>><dhv:label name="tasks.completedTasks">Completed Tasks</dhv:label></option>
        <option value="all" <%= TaskListInfo.getFilterValue("listFilter2").equalsIgnoreCase("all")?" selected":"" %>><dhv:label name="tasks.anyStatus">Any Status</dhv:label></option>
      </select>
      <%}%>
    </td>
    <td>
      <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="TaskListInfo"/>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
 <tr>
    <th align="center" nowrap>
      &nbsp;
    </th>
    <th align="center" nowrap>
      <strong><a href="MyTasks.do?command=ListTasks&column=t.priority"><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label></a></strong>
      <%= TaskListInfo.getSortIcon("t.priority") %>
    </th>
    <th width="100%" nowrap>
      <strong><a href="MyTasks.do?command=ListTasks&column=t.description"><dhv:label name="ticket.task">Task</dhv:label></a></strong>
      <%= TaskListInfo.getSortIcon("t.description") %>
    </th>
    <% if (TaskListInfo.getFilterValue("listFilter1").equalsIgnoreCase("tasksbyme")) { %>
    <th align="center" nowrap>
      <strong><dhv:label name="accounts.accounts_contacts_calls_list.AssignedTo">Assigned To</dhv:label></strong>
    </th>
    <%}%>
    <th align="center" nowrap>
      <strong><a href="MyTasks.do?command=ListTasks&column=t.duedate"><dhv:label name="accounts.accounts_calls_list.DueDate">Due Date</dhv:label></a></strong>
      <%= TaskListInfo.getSortIcon("t.duedate") %>
    </th>
    <%if(TaskListInfo.getFilterValue("listFilter2").equalsIgnoreCase("true")){%>
    <th align="center" nowrap>
      <strong><a href="MyTasks.do?command=ListTasks&column=t.completedate"><dhv:label name="ticket.task.completeDate">Complete Date</dhv:label></a></strong>
      <%= TaskListInfo.getSortIcon("t.completedate") %>
    </th>
    <%}%>
    <th align="center" nowrap>
      <strong><a href="MyTasks.do?command=ListTasks&column=t.entered"><dhv:label name="ticket.age">Age</dhv:label></a></strong>
      <%= TaskListInfo.getSortIcon("t.entered") %>
    </th>
   </tr>
 <%
	Iterator j = TaskList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
		int count = 0;
	  while (j.hasNext()) {
			count++;		
			rowid = (rowid != 1?1:2);
      Task thisTask = (Task)j.next();
%>
  <tr class="row<%= rowid %>">
    <td align="center" valign="top">
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
      <a href="javascript:displayMenu('select<%= count %>','menuTask', '<%= Constants.TASKS %>', '<%=  thisTask.getId() %>', '<%= thisTask.getTicketId() %>','<%= thisTask.getContactId() %>', '<%= thisTask.getOwner() %>');"
       onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuTask');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td nowrap align="center" valign="top">
<% if(thisTask.getPriority() != -1) {%>
  <%= toHtml((new Integer(thisTask.getPriority())).toString()) %>
<%} else {%>
  <dhv:label name="account.na">N/A</dhv:label>
<%}%>
    </td>
    <td>
    <table cellpadding="0" cellspacing="0" class="empty">
      <tr <%= thisTask.getComplete()?"class=\"strike\"":"class=\"\""%> id="complete<%=count%>">
        <td valign="top">
        <% boolean hasAuthority = false; %> 
        <dhv:hasAuthority owner="<%= thisTask.getOwner() %>">
          <% hasAuthority = true; %>
        </dhv:hasAuthority>
      <%
            if (thisTask.getComplete()) {
      %>
                <dhv:evaluate if="<%= hasAuthority %>">
                 <dhv:permission name="myhomepage-tasks-edit">
                    <a href="javascript:changeCurrentImages('image<%= count %>','MyTasks.do?command=ProcessImage&return=list&id=box.gif|gif|'+<%= thisTask.getId() %>+'|0','MyTasks.do?command=ProcessImage&return=list&id=box-checked.gif|gif|'+<%= thisTask.getId() %>+'|1');javascript:switchClass('complete<%=count%>');"  onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;">
                    <img src="images/box-checked.gif" name="image<%= count %>" id="1" border="0" title="Click to change" valign="absmiddle"></a>
                  </dhv:permission>
                </dhv:evaluate>
                <dhv:evaluate if="<%= !hasAuthority %>">
                  <a href="javascript:alert(label('status.change.requirement','Status can be changed only by the user who the task is assigned to'));">
                  <img src="images/box-checked.gif" name="image<%= count %>" id="1" border="0" title="Click to change" valign="absmiddle"></a>
                </dhv:evaluate>
      <% 
            } else {
      %>
                <dhv:evaluate if="<%= hasAuthority %>">
                  <dhv:permission name="myhomepage-tasks-edit">
                    <a href="javascript:changeCurrentImages('image<%= count %>','MyTasks.do?command=ProcessImage&return=list&id=box.gif|gif|'+<%= thisTask.getId() %>+'|0','MyTasks.do?command=ProcessImage&return=list&id=box-checked.gif|gif|'+<%= thisTask.getId() %>+'|1');javascript:switchClass('complete<%=count%>');" onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><img src="images/box.gif" name="image<%= count %>" id="0" border="0" title="Click to change">
                    </a>
                  </dhv:permission>
                </dhv:evaluate>
                <dhv:evaluate if="<%= !hasAuthority %>">
                  <a href="javascript:alert(label('status.change.requirement','Status can be changed only by the user who the task is assigned to'));" onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><img src="images/box.gif" name="image<%= count %>" id="0" border="0" valign="absmiddle"></a>
                </dhv:evaluate>
      <%
            }
      %>
        </td>
        <td valign="top">
<% 
      if (thisTask.getHasLinks()) { 
%>
          <a href="javascript:changeImages('detailsimage<%= count %>','images/arrowdown.gif','images/arrowright.gif');javascript:switchStyle('taskdetails<%=count%>','style','span');"><img src="images/arrowright.gif" name="detailsimage<%=count%>" id="1" border="0" title="Click To View Details"></a>
<%
      }
%>
        </td>
        <td valign="top">
          <a href="MyTasks.do?command=Details&id=<%= thisTask.getId() %>"><%= thisTask.getDescription()!=null?thisTask.getDescription():"" %></a>&nbsp; 
          <% if(thisTask.getContactId()!=-1) {%>
            <% if(!thisTask.getContact().getEmployee()){ %>
              [<a href="ExternalContacts.do?command=ContactDetails&id=<%= thisTask.getContact().getId() %>" title="<%=  thisTask.getContact().getValidName() %>"><font color="green"><dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label></font></a>]
            <% }else{ %>
              [<a href="CompanyDirectory.do?command=EmployeeDetails&empid=<%= thisTask.getContact().getId() %>" title="<%=  thisTask.getContact().getValidName() %>"><font color="green"><dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label></font></a>]
            <% } %>
          <%}%>
          <dhv:evaluate if="<%= ((thisTask.getType() != Task.GENERAL) && (thisTask.getLinkDetails().getLinkItemId() != -1)) %>">
            [<a href="<%= thisTask.getLinkDetails().getLink() %>"><font color="orange"><%= thisTask.getLinkDetails().getDisplayNameFull() %></font></a>]
          </dhv:evaluate>
        </td>
      </tr>
      <% if(thisTask.getHasLinks()){ %>
      <tr style="display:none">
        <td></td>
        <td></td>
        <td>
          <span style="visibility:hidden" id="taskdetails<%= count %>">
            <table class="empty">
              <tr>
                <td><dhv:label name="account.contactInformation.colon">Contact Information:</dhv:label> </td>
              </tr>
              <ul>
              <tr>
                <td>
                  <li>&nbsp;<dhv:label name="account.name.colon">Name:</dhv:label> &nbsp;<%= thisTask.getContact().getNameFull() %></li>
                </td>
              </tr>
              <tr>
                <td>
                  <li>&nbsp;<dhv:label name="account.emails.colon">Email(s):</dhv:label>
<%
                  Iterator i = thisTask.getContact().getEmailAddressList().iterator();
                  while (i.hasNext()) {
                    EmailAddress thisAddress = (EmailAddress)i.next(); %>
                    &nbsp;<%= toHtml(thisAddress.getEmail()) %>(<%= thisAddress.getTypeName() %>)&nbsp;&nbsp;
                  <%}%>
                  </li>
                </td>
              </tr>
              <tr>
                <td>
                  <li>&nbsp;<dhv:label name="account.phones.colon">Phone(s):</dhv:label>
                  <%
                    i = thisTask.getContact().getPhoneNumberList().iterator();
                    while (i.hasNext()) {
                      PhoneNumber phoneNumber = (PhoneNumber)i.next(); %>
                      &nbsp;<%= phoneNumber.getPhoneNumber()%>(<%=phoneNumber.getTypeName()%>)<br />
                    <%}%>
                  </li>
                </td>
              </tr>
              </ul>
            </table>
          </span>
        </td>
     </tr>
    <%}%>
    </table>
    </td>
    <%if(TaskListInfo.getFilterValue("listFilter1").equalsIgnoreCase("tasksbyme")){%>
      <td nowrap align="center" valign="top">
      <%if(thisTask.getOwner() > 0){ %>
        <dhv:username id="<%= thisTask.getOwner() %>"/>
      <% }else{ %>
        <dhv:label name="account.na">N/A</dhv:label>
      <% } %>
    </td>
    <%}%>
    <td nowrap align="center" valign="top">
      <% if(!User.getTimeZone().equals(thisTask.getDueDateTimeZone())) { %>
      <zeroio:tz timestamp="<%= thisTask.getDueDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="-NA-"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisTask.getDueDate() %>" dateOnly="true" timeZone="<%= thisTask.getDueDateTimeZone() %>" showTimeZone="true" default="-NA-"/>
      <% } %>
    </td>
    <% if(TaskListInfo.getFilterValue("listFilter2").equalsIgnoreCase("true")){ %>
      <td nowrap align="center" valign="top">
        <zeroio:tz timestamp="<%= thisTask.getCompleteDate() %>" dateOnly="true" default="-NA-"/>
      </td>
    <%}%>
      <td nowrap align="center" valign="top">
        <%= thisTask.getAgeString() %>
      </td>
    </tr>
    <%
      }
    } else {%>
      <tr>
        <td class="containerBody" colspan="7" valign="center">
          <dhv:label name="tasks.noTasksFoundInView">No tasks found in this view.</dhv:label>
        </td>
      </tr>
      <%}%>
    </table>
  </form>
  &nbsp;<br>
  <dhv:pagedListControl object="TaskListInfo" tdClass="row1"/>
<dhv:permission name="myhomepage-tasks-add">
  </body>
</dhv:permission>
<input type="hidden" name="ownerid" id="ownerid" value="-1"/>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>


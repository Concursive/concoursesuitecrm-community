<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.tasks.base.*,org.aspcfs.modules.base.EmailAddress, org.aspcfs.modules.base.Constants" %>
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
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<body onLoad="javascript:document.forms['addTask'].description.focus();">
<form name="addTask" action="MyTasks.do?command=Insert&auto-populate=true" method="post" onSubmit="return validateTask();">
<a href="MyCFS.do?command=Home">My Home Page</a> > My Tasks<br>
<hr color="#BFBFBB" noshade>
 <dhv:permission name="myhomepage-tasks-add">
 <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Quickly Add a Task</strong>
    </th>
  </tr>
  <tr>
    <td nowrap>
      Description&nbsp;
      <input type="text" name="description" value="" size="30">
      <font color="red">*</font>
      <input type="hidden" name="owner" value="<%= User.getUserRecord().getId() %>">
      <input type="hidden" name="priority" value="1">
      <input type="checkbox" name="chk2" onclick="javascript:setField('sharing',document.addTask.chk2.checked,'addTask');">
      Personal
      <input type="hidden" name="sharing" value="">
      <input type="submit" value="Insert">
    </td>
  </tr>
 </table>
</form>
<a href="javascript:window.location.href='MyTasks.do?command=New'">Add an Advanced Task</a><br>
</dhv:permission>
<br>
<form name="taskListView" method="post" action="MyTasks.do?command=ListTasks">
<!-- Make sure that when the list selection changes previous selected entries are saved -->
<table width="100%" border="0">
  <tr>
    <td>
      <select size="1" name="listFilter1" onChange="javascript:document.taskListView.submit();">
        <option value="my" <%= TaskListInfo.getFilterValue("listFilter1").equalsIgnoreCase("taskstome")?" selected":"" %>>My Tasks</option>
        <option value="tasksbyme" <%= TaskListInfo.getFilterValue("listFilter1").equalsIgnoreCase("tasksbyme")?" selected":"" %>>Tasks Assigned By Me</option>
      </select>
     <% if (!TaskListInfo.getFilterValue("listFilter1").equalsIgnoreCase("all")) { %>
       <select size="1" name="listFilter2" onChange="javascript:document.taskListView.submit();">
        <option value="false" <%= TaskListInfo.getFilterValue("listFilter2").equalsIgnoreCase("false")?" selected":"" %>>Incomplete Tasks</option>
        <option value="true" <%= TaskListInfo.getFilterValue("listFilter2").equalsIgnoreCase("true")?" selected":"" %>>Completed Tasks</option>
        <option value="all" <%= TaskListInfo.getFilterValue("listFilter2").equalsIgnoreCase("all")?" selected":"" %>>Any Status</option>
      </select>
      <%}%>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="TaskListInfo"/>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
 <tr>
    <th align="center" nowrap>
      <strong>Action</strong>
    </th>
    <th align="center" nowrap>
      <strong><a href="MyTasks.do?command=ListTasks&column=t.priority">Priority</a></strong>
      <%= TaskListInfo.getSortIcon("t.priority") %>
    </th>
    <th width="100%" nowrap>
      <strong><a href="MyTasks.do?command=ListTasks&column=t.description">Task</a></strong>
      <%= TaskListInfo.getSortIcon("t.description") %>
    </th>
    <% if (TaskListInfo.getFilterValue("listFilter1").equalsIgnoreCase("tasksbyme")) { %>
    <th align="center" nowrap>
      <strong>Assigned To</strong>
    </th>
    <%}%>
    <th align="center" nowrap>
      <strong><a href="MyTasks.do?command=ListTasks&column=t.duedate">Due Date</a></strong>
      <%= TaskListInfo.getSortIcon("t.duedate") %>
    </th>
    <%if(TaskListInfo.getFilterValue("listFilter2").equalsIgnoreCase("true")){%>
    <th align="center" nowrap>
      <strong><a href="MyTasks.do?command=ListTasks&column=t.completedate">Complete Date</a></strong>
      <%= TaskListInfo.getSortIcon("t.completedate") %>
    </th>
    <%}%>
    <th align="center" nowrap>
      <strong><a href="MyTasks.do?command=ListTasks&column=t.entered">Age</a></strong>
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
      <a href="javascript:displayMenu('menuTask', '<%= Constants.TASKS %>', '<%=  thisTask.getId() %>');"
       onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>)"><img src="images/select.gif" name="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td nowrap align="center" valign="top">
      <%= thisTask.getPriority()==-1?"-NA-":(new Integer(thisTask.getPriority())).toString() %>
    </td>
    <td>
    <table cellpadding="0" cellspacing="0" class="empty">
      <tr <%= thisTask.getComplete()?"class=\"strike\"":"class=\"\""%> id="complete<%=count%>">
        <td>
        <% boolean hasAuthority = false; %> 
        <dhv:hasAuthority owner="<%= thisTask.getOwner() %>">
          <% hasAuthority = true; %>
        </dhv:hasAuthority>
      <%
            if (thisTask.getComplete()) {
      %>
                <dhv:evaluate if="<%= hasAuthority %>">
                 <dhv:permission name="myhomepage-tasks-edit">
                    <a href="javascript:changeImages('image<%= count %>','MyTasks.do?command=ProcessImage&id=box.gif|gif|'+<%= thisTask.getId() %>+'|0','MyTasks.do?command=ProcessImage&id=box-checked.gif|gif|'+<%= thisTask.getId() %>+'|1');javascript:switchClass('complete<%=count%>');"  onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;">
                    <img src="images/box-checked.gif" name="image<%= count %>" id="1" border="0" title="Click to change"></a>
                  </dhv:permission>
                </dhv:evaluate>
                <dhv:evaluate if="<%= !hasAuthority %>">
                  <a href="javascript:alert('Status can be changed only by the user who the task is assigned to');">
                  <img src="images/box-checked.gif" name="image<%= count %>" id="1" border="0" title="Click to change"></a>
                </dhv:evaluate>
      <% 
            } else {
      %>
                <dhv:evaluate if="<%= hasAuthority %>">
                  <dhv:permission name="myhomepage-tasks-edit">
                    <a href="javascript:changeImages('image<%= count %>','MyTasks.do?command=ProcessImage&id=box.gif|gif|'+<%= thisTask.getId() %>+'|1','MyTasks.do?command=ProcessImage&id=box-checked.gif|gif|'+<%= thisTask.getId() %>+'|1');javascript:switchClass('complete<%=count%>');" onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><img src="images/box.gif" name="image<%= count %>" id="0" border="0" title="Click to change">
                    </a>
                  </dhv:permission>
                </dhv:evaluate>
                <dhv:evaluate if="<%= !hasAuthority %>">
                  <a href="javascript:alert('Status can be changed only by the user who the task is assigned to');" onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><img src="images/box.gif" name="image<%= count %>" id="0" border="0"></a>
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
          <a href="MyTasks.do?command=Modify&id=<%= thisTask.getId() %>"><%= thisTask.getDescription()!=null?thisTask.getDescription():"" %></a>&nbsp; <%=(thisTask.getContactId()==-1)?"":"[<a href=\"ExternalContacts.do?command=ContactDetails&id="+ thisTask.getContact().getId() +"\" title=\""+ thisTask.getContact().getNameLastFirst() +"\"><font color=\"green\">Contact</font></a>]"%>
        </td>
        <dhv:evaluate if="<%= thisTask.getType() != Task.GENERAL %>">
          <td valign="top">
            &nbsp; [<a href="<%= thisTask.getLinkDetails().getLink() %>"><font color="orange"><%= thisTask.getLinkDetails().getDisplayNameFull() %></font></a>]
          </td>
        </dhv:evaluate>
      </tr>
      <% if(thisTask.getHasLinks()){ %>
      <tr style="display:none">
        <td></td>
        <td></td>
        <td>
          <span style="visibility:hidden" id="taskdetails<%= count %>">
            <table class="empty">
              <tr>
                <td>Contact Information: </td>
              </tr>
              <ul>
              <tr>
                <td>
                  <li>&nbsp;Name: &nbsp;<%= thisTask.getContact().getNameLastFirst() %></li>
                </td>
              </tr>
              <tr>
                <td>
                  <li>&nbsp;Email(s):
<%
                  Iterator i = thisTask.getContact().getEmailAddressList().iterator();
                  while (i.hasNext()) {
                    EmailAddress thisAddress = (EmailAddress)i.next(); %>
                    &nbsp;<%=thisAddress.getEmail()%>(<%= thisAddress.getTypeName() %>)&nbsp;&nbsp;
                  <%}%>
                  </li>
                </td>
              </tr>
              <tr>
                <td>
                  <li>&nbsp;Phone(s): 
                    &nbsp;<%= thisTask.getContact().getPhoneNumber("Business").equals("")?"":thisTask.getContact().getPhoneNumber("Business")+" (W)<br>"%><%= thisTask.getContact().getPhoneNumber("Home").equals("")?"":thisTask.getContact().getPhoneNumber("Home")+" (H)<br>"%><%=thisTask.getContact().getPhoneNumber("Mobile").equals("")?"":thisTask.getContact().getPhoneNumber("Mobile")+" (M)<br>"%>
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
        -NA-
      <%}%>
    </td>
    <%}%>
    <td nowrap align="center" valign="top">
      <%= thisTask.getDueDateString().equals("")?"-NA-":thisTask.getDueDateString() %>
    </td>
    <% if(TaskListInfo.getFilterValue("listFilter2").equalsIgnoreCase("true")){ %>
      <td nowrap align="center" valign="top">
        <%= thisTask.getCompleteDateString().equals("")?"-NA-":thisTask.getCompleteDateString() %>
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
        <td class="containerBody" colspan="6" valign="center">
          No tasks found in this view.
        </td>
      </tr>
      <%}%>
    </table>
  </form>
  &nbsp;<br>
  <dhv:pagedListControl object="TaskListInfo" tdClass="row1"/>
</body>


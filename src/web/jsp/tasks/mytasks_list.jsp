<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.tasks.base.*" %>
<jsp:useBean id="TaskList" class="org.aspcfs.modules.tasks.base.TaskList" scope="request"/>
<jsp:useBean id="TaskListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/tasks.js"></SCRIPT>
<body onLoad="javascript:document.forms['addTask'].description.focus();">
<form name="addTask" action="MyTasks.do?command=Insert&auto-populate=true" method="post" onSubmit="return validateTask();">
<a href="MyCFS.do?command=Home">My Home Page</a> > My Tasks<br>
<hr color="#BFBFBB" noshade>
 <table cellpadding="4" cellspacing="0" border="1" width="45%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left colspan="3">
      <strong>Quickly Add a Task</strong>
    </td>
  </tr>
  <tr>
  <td nowrap align="left" width="25%">
      Description&nbsp;
      <input type=text name="description" value="" size=30>
      <font color="red">*</font>
      <input type=hidden name="owner" value="<%=User.getUserRecord().getContact().getId()%>">
      <input type="hidden" name="priority" value="1">
      </td>
      <td nowrap align="left" width="20%">
        <input type=checkbox name="chk2"  onclick="javascript:setField('sharing',document.addTask.chk2.checked,'addTask');">
        Personal&nbsp;
        <input type=hidden name="sharing" value="">
        &nbsp;&nbsp;
      </td>
      <td nowrap align="left" width="5%">
        <input type="submit" value="Insert">
      </td>
  </tr>
 </table>
</form>

<a href="javascript:window.location.href='MyTasks.do?command=New'">Add an Advanced Task</a><br>
<br>

<form name="taskListView" method="post" action="MyTasks.do?command=ListTasks">
<!-- Make sure that when the list selection changes previous selected entries are saved -->
<table width="100%" border="0">
  <tr>
    <td align="left">
      <select size="1" name="listFilter1" onChange="javascript:document.taskListView.submit();">
        <option value="my" <%=TaskListInfo.getFilterValue("listFilter1").equalsIgnoreCase("taskstome")?" selected":""%>>My Tasks</option>
        <option value="tasksbyme" <%=TaskListInfo.getFilterValue("listFilter1").equalsIgnoreCase("tasksbyme")?" selected":""%>>Tasks Assigned By Me</option>
      </select>
     <%if(!TaskListInfo.getFilterValue("listFilter1").equalsIgnoreCase("all")){%>
       <select size="1" name="listFilter2" onChange="javascript:document.taskListView.submit();">
        <option value="false" <%=TaskListInfo.getFilterValue("listFilter2").equalsIgnoreCase("false")?" selected":""%>>Incomplete Tasks</option>
        <option value="true" <%=TaskListInfo.getFilterValue("listFilter2").equalsIgnoreCase("true")?" selected":""%>>Completed Tasks</option>
        <option value="all" <%=TaskListInfo.getFilterValue("listFilter2").equalsIgnoreCase("all")?" selected":""%>>Any Status</option>
      </select>
      <%}%>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="TaskListInfo"/>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
   <tr class="title">
    <td align=center width="8%" nowrap>
      <strong>Action</strong>
    </td>
    <td align=center width="8%" nowrap>
      <strong><a href="MyTasks.do?command=ListTasks&column=t.priority">Priority</a></strong>
      <%= TaskListInfo.getSortIcon("t.priority") %>
    </td>
     <td align=left width="68%" nowrap>
      <strong><a href="MyTasks.do?command=ListTasks&column=t.description">Task</a></strong>
      <%= TaskListInfo.getSortIcon("t.description") %>
    </td>
    <%if(TaskListInfo.getFilterValue("listFilter1").equalsIgnoreCase("tasksbyme")){%>
      <td align=center width="9%" nowrap>
      <strong>Assigned To</strong>
    </td>
    <%}%>
    <td align=center width="9%" nowrap>
      <strong><a href="MyTasks.do?command=ListTasks&column=t.duedate">Due Date</a></strong>
      <%= TaskListInfo.getSortIcon("t.duedate") %>
    </td>
    <%if(TaskListInfo.getFilterValue("listFilter2").equalsIgnoreCase("true")){%>
    <td align=center width="9%" nowrap>
      <strong><a href="MyTasks.do?command=ListTasks&column=t.completedate">Complete Date</a></strong>
      <%= TaskListInfo.getSortIcon("t.completedate") %>
    </td>
    <%}%>
    <td align=center width="7%" nowrap>
      <strong><a href="MyTasks.do?command=ListTasks&column=t.entered">Age</a></strong>
      <%= TaskListInfo.getSortIcon("t.entered") %>
    </td>
   </tr>

   <%
	Iterator j = TaskList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
		int count = 0;
		
	  while (j.hasNext()) {
			count++;		
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
	 	
		Task thisTask = (Task)j.next();
   
   %>      
  <tr class="row<%= rowid %>">
    <td align="center" valign="top">
          <a href='javascript:window.location.href="MyTasksForward.do?command=ForwardMessage&forwardType=15&id=<%=thisTask.getId()%>&return=" +escape("MyTasks.do?command=ListTasks")+ "&sendUrl="+ escape("MyTasksForward.do?command=SendMessage");'>Fwd</a>|<a href="javascript:popURLReturn('/MyTasks.do?command=ConfirmDelete&id=<%=thisTask.getId()%>','MyTasks.do?command=ListTasks', 'Delete_message','320','200','yes','no');">Del</a>
    </td>
        <td nowrap align="center" valign="top">
      <%= thisTask.getPriority()==-1?"-NA-":(new Integer(thisTask.getPriority())).toString() %>
    </td>
    <td>
    <table cellpadding="0" cellspacing="0">
    <tr <%=thisTask.getComplete()?"class=\"strike\"":"class=\"\""%> id="complete<%=count%>" nowrap >
    <td>
      <%if(thisTask.getComplete()){
      %>
        <a href="javascript:changeImages('image<%=count%>','/MyTasks.do?command=ProcessImage&id=box.gif|gif|'+<%=thisTask.getId()%>+'|0','/MyTasks.do?command=ProcessImage&id=box-checked.gif|gif|'+<%=thisTask.getId()%>+'|1');javascript:switchClass('complete<%=count%>');" onMouseOver="this.style.color='blue';window.status='View Details';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><img src="images/box-checked.gif" name="image<%=count%>" id="1" border=0 title="Click to change"></a>
      <%}
      else{
      %>
        <a href="javascript:changeImages('image<%=count%>','/MyTasks.do?command=ProcessImage&id=box.gif|gif|'+<%=thisTask.getId()%>+'|1','/MyTasks.do?command=ProcessImage&id=box-checked.gif|gif|'+<%=thisTask.getId()%>+'|1');javascript:switchClass('complete<%=count%>');"><img src="images/box.gif" name="image<%=count%>" id="0" border=0 title="Click to change"></a>
      <%}
      int contactId = thisTask.getContactId();
      String contactName = thisTask.getContactName();
      int ticketId = thisTask.getTicketId();
      %>
    </td>
    <td>
      <%if(thisTask.getHasLinks()){%>
        <a href="javascript:changeImages('detailsimage<%=count%>','images/arrowdown.gif','images/arrowright.gif');javascript:switchStyle('taskdetails<%=count%>','style','span');"><img src="images/arrowright.gif" name="detailsimage<%=count%>" id="1" border=0 title="Click To View Details"></a>
      <%}%>
    </td>
    <td>
        <a href="MyTasks.do?command=Modify&id=<%=thisTask.getId()%>"><%= thisTask.getDescription()!=null?thisTask.getDescription():"" %></a>&nbsp; <%=(thisTask.getContactId()==-1)?"":"[ <a href=\"ExternalContacts.do?command=ContactDetails&id="+ thisTask.getContact().getId() +"\" title=\""+ thisTask.getContact().getNameLastFirst() +"\"><font color=\"green\">C</font></a> ]"%> <%=(thisTask.getTicketId()==-1)?"":"[ <a href=\"TroubleTickets.do?command=Details&id="+ ticketId +"\"><font color=\"orange\">T</font></a> ]"%><br>
    </td>
    </tr>
      <%if(thisTask.getHasLinks()){%>
      <tr style="display:none">
          <td></td><td></td>
          <td>
            <span style="visibility:hidden" id="taskdetails<%=count%>">
              <table>
                <tr><td>-Contact Info: </td></tr>
                <ul>
                <tr><td><li>&nbsp;Name : &nbsp;<%=thisTask.getContact().getNameLastFirst()%></li></td></tr>
                <tr><td><li>&nbsp;Email(s) : 
                  <%
                  Iterator i = thisTask.getContact().getEmailAddressList().iterator();
                  while (i.hasNext()) {
                    EmailAddress thisAddress = (EmailAddress)i.next();%>
                    &nbsp;<%=thisAddress.getEmail()%>(<%=thisAddress.getTypeName()%>)&nbsp;&nbsp;
                  <%}%>
                 </li></td></tr>
                 <tr><td><li>&nbsp;Phone(s) : 
                    &nbsp;<%=thisTask.getContact().getPhoneNumber("Business").equals("")?"":thisTask.getContact().getPhoneNumber("Business")+"[B]  | "%><%=thisTask.getContact().getPhoneNumber("Home").equals("")?"":thisTask.getContact().getPhoneNumber("Home")+"[H]  | "%><%=thisTask.getContact().getPhoneNumber("Mobile").equals("")?"":thisTask.getContact().getPhoneNumber("Mobile")+"[M]"%>
                 </li></td></tr>
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
      <%= thisTask.getOwnerName().equals("")?"-NA-":thisTask.getOwnerName() %>
    </td>
    <%}%>
    <td nowrap align="center" valign="top">
      <%= thisTask.getDueDateString().equals("")?"-NA-":thisTask.getDueDateString() %>
    </td>
    
    <%if(TaskListInfo.getFilterValue("listFilter2").equalsIgnoreCase("true")){%>
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
    }
    else {%>
      <tr bgcolor="white">
        <td colspan="6" valign="center">
          No tasks found in this view.
        </td>
      </tr>
      <%}%>
    </table>
  </form>
  <dhv:pagedListControl object="TaskListInfo" tdClass="row1"/>
</body>
    

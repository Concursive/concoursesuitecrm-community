<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="TaskList" class="com.darkhorseventures.cfsbase.TaskList" scope="request"/>
<jsp:useBean id="TaskListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.UserBean" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="/javascript/submit.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/images.js"></SCRIPT>
<body onLoad="javascript:document.forms['addTask'].description.focus();">
<br>
<a href='javascript:window.location.href="MyTasks.do?command=New"'>Add A Task</a>
<br>
<br>
<form name="taskListView" method="post" action="/MyTasks.do?command=ListTasks">
<!-- Make sure that when the list selection changes previous selected entries are saved -->
<table width="20%" border="0">
  <tr>
    <td align="left">
      <select size="1" name="listFilter1" onChange="javascript:document.taskListView.submit();">
        <option value="taskstome" <%=TaskListInfo.getFilterValue("listFilter1").equalsIgnoreCase("taskstome")?" selected":""%>>My Tasks</option>
        <option value="tasksbyme" <%=TaskListInfo.getFilterValue("listFilter1").equalsIgnoreCase("tasksbyme")?" selected":""%>>Tasks Assigned By Me</option>
        <option value="all" <%=TaskListInfo.getFilterValue("listFilter1").equalsIgnoreCase("all")?" selected":""%>>All Tasks</option>
      </select>
     </td>
     <%if(!TaskListInfo.getFilterValue("listFilter1").equalsIgnoreCase("all")){%>
     <td>
       <select size="1" name="listFilter2" onChange="javascript:document.taskListView.submit();">
        <option value="false" <%=TaskListInfo.getFilterValue("listFilter2").equalsIgnoreCase("false")?" selected":""%>>InComplete Tasks</option>
        <option value="true" <%=TaskListInfo.getFilterValue("listFilter2").equalsIgnoreCase("true")?" selected":""%>>Completed Tasks</option>
        <option value="all" <%=TaskListInfo.getFilterValue("listFilter2").equalsIgnoreCase("all")?" selected":""%>>All</option>
      </select>
      </td>
      <%}%>
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
    </td>
     <td align=left width="68%" nowrap>
      <strong><a href="MyTasks.do?command=ListTasks&column=t.description">Task</a></strong>
      <%= TaskListInfo.getSortIcon("t.description") %>
    </td>
    <td align=center width="9%" nowrap>
      <strong><a href="MyTasks.do?command=ListTasks&column=t.duedate">Due Date</a></strong>
      <%= TaskListInfo.getSortIcon("t.duedate") %>
    </td>
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
    <td align=center>
          <a href='javascript:window.location.href="/MyTasksForward.do?command=ForwardMessage&forwardType=15&id=<%=thisTask.getId()%>&return=" +escape("MyTasks.do?command=ListTasks")+ "&sendUrl="+ escape("MyTasksForward.do?command=SendMessage");'>Fwd</a>|<a href="javascript:popURLReturn('/MyTasks.do?command=ConfirmDelete&id=<%=thisTask.getId()%>','MyTasks.do?command=ListTasks', 'Delete_message','320','200','yes','no');">Del</a>
    </td>
    
    <td nowrap align=center>
      <%= thisTask.getPriority()==-1?"-NA-":(new Integer(thisTask.getPriority())).toString() %>
    </td>
    <td <%=thisTask.getComplete()?"class=\"strike\"":"class=\"\""%> id="complete<%=count%>" nowrap >
    <%if(thisTask.getComplete()){
    %>
      <a href="javascript:changeImages('image<%=count%>',<%=thisTask.getId()%>);javascript:switchClass('complete<%=count%>');"><img src="images/box-checked.gif" name="image<%=count%>" id="1" border=0 title="Click to change"></a> 
    <%}
    else{
      %>
      <a href="javascript:changeImages('image<%=count%>',<%=thisTask.getId()%>);javascript:switchClass('complete<%=count%>');"><img src="images/box.gif" name="image<%=count%>" id="0" border=0 title="Click to change"></a>
    <%}
    int contactId = thisTask.getContactId();
    String contactName = thisTask.getContactName();
    int ticketId = thisTask.getTicketId();
      %>
      <a href="MyTasks.do?command=New&id=<%=thisTask.getId()%>"><%= thisTask.getDescription() %></a>&nbsp; <%=(thisTask.getContactId()==-1)?"":"[ <a href=\"ExternalContacts.do?command=ContactDetails&id="+ contactId +"\" title=\""+ contactName +"\"><font color=\"green\">C</font></a> ]"%> <%=(thisTask.getTicketId()==-1)?"":"[ <a href=\"TroubleTickets.do?command=Details&id="+ ticketId +"\"><font color=\"orange\">T</font></a> ]"%>
    </td>
    <td nowrap align=center>
      <%= thisTask.getDueDateString().equals("")?"-NA-":thisTask.getDueDateString() %>
    </td>
    <td nowrap align=center>
      <%= thisTask.getAgeString() %>
    </td>
    </tr>
    
    <%
      }
    }
    else {%>
      <tr bgcolor="white">
        <td colspan="4" valign="center">
          No contacts matched query.
        </td>
      </tr>
      <%}%>
      </table>
      </form>
      <br>
      <dhv:pagedListControl object="TaskListInfo" tdClass="row1"/>
      <br><br>
      
<form name="addTask" action="/MyTasks.do?command=Insert&auto-populate=true" method="post">
 <table cellpadding="4" cellspacing="0" border="1" width="45%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left colspan="3">
      <strong>Quickly Add A Task</strong>
    </td>
  </tr>
  <tr>
  <td nowrap align="left" width="25%">
      Title&nbsp;
      <input type=text name="description" value="" size=30>
      <font color="red">*</font>
      <input type=hidden name="owner" value="<%=User.getUserRecord().getContact().getId()%>">
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
<br>
</body>
    
    



<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="TaskList" class="com.darkhorseventures.cfsbase.TaskList" scope="request"/>
<jsp:useBean id="TaskListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.UserBean" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="/javascript/submit.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/images.js"></SCRIPT>

<br>
<a href='javascript:window.location.href="MyTasks.do?command=New"'>Add Task</a>
<br>
<br>
<form name="taskListView" method="post" action="/MyTasks.do?command=ListTasks">
<!-- Make sure that when the list selection changes previous selected entries are saved -->

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
   <tr class="title">
    <td align=center width="8%">
      <strong>Action</strong>
    </td>
    <td align=center width="8%">
    <strong><a href="MyTasks.do?command=ListTasks&column=t.priority">Priority</a></strong>
    <%= TaskListInfo.getSortIcon("t.priority") %>
    </td>
    </td>
     <td align=left width="68%">
      <strong><a href="MyTasks.do?command=ListTasks&column=t.description">Task</a></strong>
      <%= TaskListInfo.getSortIcon("t.description") %>
    </td>
    <td align=center width="9%">
      <strong><a href="MyTasks.do?command=ListTasks&column=t.duedate">Due Date</a></strong>
      <%= TaskListInfo.getSortIcon("t.duedate") %>
    </td>
    <td align=center width="7%">
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
    <td nowrap align=center>
          <a href="/MyTasksForward.do?command=ForwardMessage&forwardType=15&id=<%=thisTask.getId()%>&return=/MyTasks.do?command=ListTasks&sendUrl=/MyTasksForward.do?command=SendMessage">Fwd</a>|<a href="javascript:popURLReturn('/MyTasks.do?command=ConfirmDelete&id=<%=thisTask.getId()%>','MyTasks.do?command=ListTasks', 'Delete_message','225','110','yes','no');">Del</a>
    </td>
    
    <td nowrap align=center>
      <%= thisTask.getPriority()==-1?"-NA-":(new Integer(thisTask.getPriority())).toString() %>
    </td>
    <td <%=thisTask.getComplete()?"class=\"strike\"":""%> id="complete<%=count%>" nowrap >
    <%if(thisTask.getComplete()){
    %>
      <a href="javascript:changeImages('image<%=count%>',<%=thisTask.getId()%>);"><img src="images/box-checked.gif" name="image<%=count%>" id="1" border=0 title="Done..Click to change"></a> 
    <%}
    else{
      %>
      <a href="javascript:changeImages('image<%=count%>',<%=thisTask.getId()%>);"><img src="images/box.gif" name="image<%=count%>" id="0" border=0 title="Pending..Click to change"></a>
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
<table cellpadding="4" cellspacing="0" border="1" width="35%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Quick Task Add</strong>
    </td>
  </tr>
  <tr>
  <td nowrap align="left">
      Title
  
  
      &nbsp;<input type=text name="description" value="" size=30>
      <font color="red">*</font>
      <input type=hidden name="owner" value="<%=User.getUserRecord().getContact().getId()%>">
      
      &nbsp;<input type="submit" value="Insert"> 
      </td>
  
  </tr>
</table>
</form>
<br>
 
    
    



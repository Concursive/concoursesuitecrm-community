<%@ page import="java.util.*,org.aspcfs.modules.tasks.base.*,org.aspcfs.modules.base.EmailAddress, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="TaskList" class="org.aspcfs.modules.tasks.base.TaskList" scope="request"/>
<jsp:useBean id="TicketTaskListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></SCRIPT>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
   <tr class="title">
   <td  colspan="7">
      <strong>Tasks created for the Ticket</strong> [<a  href="javascript:popURL('TroubleTicketTasks.do?command=Add&ticketId=<%= TicketDetails.getId() %>&popup=true','Task','550','650','yes','yes');">Add a Task</a>]
   </td>
   </tr>
   <tr class="title">
    <td align="center" nowrap>
      <strong>Action</strong>
    </td>
    <td align="center" nowrap>
      <strong><a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>&column=t.priority">Priority</a></strong>
      <%= TicketTaskListInfo.getSortIcon("t.priority") %>
    </td>
     <td width="100%" nowrap>
      <strong><a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>&column=t.description">Task</a></strong>
      <%= TicketTaskListInfo.getSortIcon("t.description") %>
    </td>
    <td align="center" nowrap>
      <strong>Assigned To</strong>
    </td>
    <td align="center" nowrap>
      <strong><a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>&column=t.duedate">Due Date</a></strong>
      <%= TicketTaskListInfo.getSortIcon("t.duedate") %>
    </td>
    <td align="center" nowrap>
      <strong><a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>&column=t.completedate">Complete Date</a></strong>
      <%= TicketTaskListInfo.getSortIcon("t.completedate") %>
    </td>
    <td align="center" nowrap>
      <strong><a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>&column=t.entered">Age</a></strong>
      <%= TicketTaskListInfo.getSortIcon("t.entered") %>
    </td>
   </tr>
 <%
	Iterator j = TaskList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
		int count = 0;
	  while (j.hasNext()) {
			count++;		
			rowid = (rowid != 1 ? 1 : 2);
      Task thisTask = (Task) j.next();
%>
  <tr class="row<%= rowid %>">
    <td align="center" valign="top">
      <%-- <a href="javascript:window.location.href='MyTasksForward.do?command=ForwardMessage&forwardType=<%= Constants.TASKS %>&id=<%=thisTask.getId()%>&return=' + escape('MyTasks.do?command=ListTasks') + '&sendUrl='+ escape('MyTasksForward.do?command=SendMessage');">Fwd</a>|--%><a href="javascript:popURL('TroubleTicketTasks.do?command=ConfirmDelete&id=<%= thisTask.getId() %>&popup=true', 'Delete_message','320','200','yes','no');">Del</a>
    </td>
    <td nowrap align="center" valign="top">
      <%= thisTask.getPriority() == -1 ? "-NA-" : (new Integer(thisTask.getPriority())).toString() %>
    </td>
    <td>
    <table cellpadding="0" cellspacing="0">
      <tr <%= thisTask.getComplete()?"class=\"strike\"":"class=\"\""%> id="complete<%=count%>">
        <td>
<% 
      if (thisTask.getComplete()) {
%>
          <a href="javascript:changeImages('image<%= count %>','MyTasks.do?command=ProcessImage&id=box.gif|gif|'+<%= thisTask.getId() %>+'|0','MyTasks.do?command=ProcessImage&id=box-checked.gif|gif|'+<%= thisTask.getId() %>+'|1');javascript:switchClass('complete<%=count%>');" onMouseOver="this.style.color='blue';window.status='View Details';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><img src="images/box-checked.gif" name="image<%= count %>" id="1" border="0" title="Click to change"></a>
<% 
      } else {
%>
          <a href="javascript:changeImages('image<%= count %>','MyTasks.do?command=ProcessImage&id=box.gif|gif|'+<%= thisTask.getId() %>+'|1','MyTasks.do?command=ProcessImage&id=box-checked.gif|gif|'+<%= thisTask.getId() %>+'|1');javascript:switchClass('complete<%=count%>');"><img src="images/box.gif" name="image<%= count %>" id="0" border="0" title="Click to change"></a>
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
          <a href="javascript:popURL('TroubleTicketTasks.do?command=Modify&ticketId=<%= TicketDetails.getId() %>&id=<%= thisTask.getId() %>&popup=true','CFS_Task','600','420','yes','yes');"><%= thisTask.getDescription()!=null?thisTask.getDescription():"" %></a>&nbsp; <%=(thisTask.getContactId()==-1)?"":"[<a href=\"ExternalContacts.do?command=ContactDetails&id="+ thisTask.getContact().getId() +"\" title=\""+ thisTask.getContact().getNameLastFirst() +"\"><font color=\"green\">C</font></a>]"%>
        </td>
      </tr>
      <% if(thisTask.getHasLinks()){ %>
      <tr style="display:none">
        <td></td>
        <td></td>
        <td>
          <span style="visibility:hidden" id="taskdetails<%= count %>">
            <table>
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
    <td nowrap align="center" valign="top">
      <%if(thisTask.getOwner() > 0){ %>
        <dhv:username id="<%= thisTask.getOwner() %>"/>
      <% }else{ %>
        -NA-
      <%}%>
    </td>
    <td nowrap align="center" valign="top">
      <%= thisTask.getDueDateString().equals("")?"-NA-":thisTask.getDueDateString() %>
    </td>
      <td nowrap align="center" valign="top">
        <%= thisTask.getCompleteDateString().equals("")?"-NA-":thisTask.getCompleteDateString() %>
      </td>
      <td nowrap align="center" valign="top">
        <%= thisTask.getAgeString() %>
      </td>
    </tr>
    <%
      }
    } else { 
   %>
      <tr>
        <td class="containerBody" colspan="6" valign="center">
             <font color="#9E9E9E" colspan="3">No Task Entries.</font>
        </td>
      </tr>
  <%}%>
</table>


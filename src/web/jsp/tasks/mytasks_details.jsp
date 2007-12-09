<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.tasks.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<%@ include file="../initPage.jsp" %>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
<a href="MyTasks.do?command=ListTasks"><dhv:label name="myitems.tasks">Tasks</dhv:label></a> >
<dhv:label name="tasks.taskDetails">Task Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:formMessage showSpace="false"/>
<% if (hasAuthority(pageContext, String.valueOf(Task.getOwner()))) { %>
 <input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='MyTasks.do?command=Modify&id=<%= Task.getId() %>&return=details';"> 
<% } %>
 <input type="button" value="<dhv:label name="accounts.accounts_calls_list_menu.Forward">Forward</dhv:label>" onClick="javascript:window.location.href='MyTasksForward.do?command=ForwardMessage&forwardType=<%= Constants.TASKS %>&id=<%= Task.getId() %>&return=details';"> 
<% if (hasAuthority(pageContext, String.valueOf(Task.getOwner()))) { %>
 <input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('MyTasks.do?command=ConfirmDelete&id=<%= Task.getId() %>&popup=true','MyTasks.do?command=ListTasks', 'Delete_task','320','200','yes','no');"><br /> 
<% } %>
<dhv:evaluate if="<%= !isPopup(request) %>"><br /></dhv:evaluate>
<dhv:evaluate if="<%= isPopup(request) %>">
 <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();"><br />
</dhv:evaluate>
<%@ include file="task_details_include.jsp" %>
<br />
<input type="hidden" name="return" value="<%= request.getParameter("return") %>">
<dhv:evaluate if="<%= !isPopup(request) %>">
<% if (hasAuthority(pageContext, String.valueOf(Task.getOwner()))) { %>
<input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='MyTasks.do?command=Modify&id=<%= Task.getId() %>&return=details';"> 
<% } %>
<input type="button" value="<dhv:label name="accounts.accounts_calls_list_menu.Forward">Forward</dhv:label>" onClick="javascript:window.location.href='MyTasksForward.do?command=ForwardMessage&forwardType=<%= Constants.TASKS %>&id=<%= Task.getId() %>&return=details';"> 
<% if (hasAuthority(pageContext, String.valueOf(Task.getOwner()))) { %>
<input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('MyTasks.do?command=ConfirmDelete&id=<%= Task.getId() %>&popup=true','MyTasks.do?command=ListTasks', 'Delete_task','320','200','yes','no');"><br />&nbsp;
<% } %>
</dhv:evaluate>
<dhv:evaluate if="<%= isPopup(request) %>">
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();">
</dhv:evaluate>

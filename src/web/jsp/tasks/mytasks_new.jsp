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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*"%>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.addTask.description.focus();">
<form name="addTask" action="MyTasks.do?command=<%= Task.getId()!=-1?"Update":"Insert" %>&id=<%= Task.getId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %><%= (request.getParameter("return") != null?"&return="+request.getParameter("return"):"") %>" method="post" onSubmit="return validateTask();">
<%boolean popUp = false;
  if(request.getParameter("popup")!=null) {
    popUp = true;
  }%>
<dhv:evaluate if="<%= !popUp %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
<a href="MyTasks.do?command=ListTasks"><dhv:label name="myitems.tasks">Tasks</dhv:label></a> >
<% if(Task.getId()==-1) {%>
  <dhv:label name="quickactions.addTask">Add a Task</dhv:label>
<%} else {%>
  <dhv:label name="tasks.updateATask">Update a Task</dhv:label>
<%}%>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<% if(Task.getId()==-1) {%>
  <input type="submit" value="<dhv:label name="button.save">Save</dhv:label>"/>
  <input type="submit" value="<dhv:label name="button.saveAndNew">Save and New</dhv:label>" onClick="javascript:document.getElementById('addAnother').value='true';"/> 
<%} else {%>
  <input type="submit" value="<dhv:label name="button.update">Update</dhv:label>">
<%}%>
<% if(popUp) { %>
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();" /><br>
<% } else if (request.getAttribute("return") != null && "details".equals((String) request.getAttribute("return"))){ %>
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='MyTasks.do?command=Details&id=<%= Task.getId() %>';" /><br />
<% } else { %>
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='MyTasks.do?command=ListTasks';" /><br />
<% } %>
<dhv:formMessage />
<%@ include file="task_include.jsp" %>
<input type="hidden" name="addAnother" id="addAnother" value="false"/>
<br>
<% if(Task.getId()==-1) {%>
  <input type="submit" value="<dhv:label name="button.save">Save</dhv:label>">
  <input type="submit" value="<dhv:label name="button.saveAndNew">Save and New</dhv:label>" onClick="javascript:document.getElementById('addAnother').value='true';"/> 
<%} else {%>
  <input type="submit" value="<dhv:label name="button.update">Update</dhv:label>">
<%}%>
<input type="hidden" name="return" value="<%= request.getParameter("return") %>">
<% if(popUp) { %>
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();" /><br>
<% } else if (request.getAttribute("return") != null && "details".equals((String) request.getAttribute("return"))){ %>
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='MyTasks.do?command=Details&id=<%= Task.getId() %>';" /><br />
<% } else { %>
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='MyTasks.do?command=ListTasks';" /><br />
<% } %>
</form>
</body>


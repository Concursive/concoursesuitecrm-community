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
<%@ page import="java.util.*"%>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].description.focus();">
<form name="addTask" action="MyTasks.do?command=<%= Task.getId()!=-1?"Update":"Insert" %>&id=<%= Task.getId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" method="post" onSubmit="return validateTask();">
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%>
<dhv:evaluate exp="<%= !popUp %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home">My Home Page</a> > 
<a href="MyTasks.do?command=ListTasks">Tasks</a> >
<%= Task.getId()==-1?"Add":"Update" %> a Task
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<input type="submit" value="<%= Task.getId()==-1?"Save":"Update" %>">
<input type="button" value="Cancel" onClick="<%=popUp?"javascript:window.close();":"javascript:window.location.href='MyTasks.do?command=ListTasks';"%>"><br />
<dhv:formMessage />
<%@ include file="task_include.jsp" %>
<br>
<input type="submit" value="<%= Task.getId()==-1?"Save":"Update" %>">
<input type="hidden" name="return" value="<%= request.getParameter("return") %>">
<input type="button" value="Cancel" onClick="<%=popUp?"javascript:window.close();":"javascript:window.location.href='MyTasks.do?command=ListTasks';"%>">
</form>
</body>


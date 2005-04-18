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
<jsp:useBean id="TaskId" class="java.lang.String" scope="request"/>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="actionList.myHomePage">My Home Page</dhv:label></a> > 
<a href="MyTasks.do?command=ListTasks"><dhv:label name="myitems.tasks">Tasks</dhv:label></a> >
<dhv:label name="tasks.forwardTask">Forward Task</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<form name="newMessageForm" action="MyTasksForward.do?command=SendMessage&actionSource=MyTasksForward&id=<%= TaskId %>" method="post" onSubmit="return sendMessage();">
<input type="submit" value="<dhv:label name="global.button.send">Send</dhv:label>">
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='MyTasks.do?command=ListTasks'"><br><br>
<%@ include file="../newmessage.jsp" %>
<br>
<input type="submit" value="<dhv:label name="global.button.send">Send</dhv:label>">
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='MyTasks.do?command=ListTasks'">
</form>


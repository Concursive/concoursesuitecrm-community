<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<%@ include file="../initPage.jsp" %>
<dhv:evaluate exp="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home">My Home Page</a> > 
<a href="MyTasks.do?command=ListTasks">Tasks</a> >
Task Details
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:formMessage />
<dhv:evaluate exp="<%= isPopup(request) %>"> 
<input type="button" value="Cancel" onClick="javascript:window.close();"><br>
</dhv:evaluate>
<%@ include file="task_details_include.jsp" %>
<br>
<input type="hidden" name="return" value="<%= request.getParameter("return") %>">
<dhv:evaluate exp="<%= isPopup(request) %>">
<input type="button" value="Cancel" onClick="javascript:window.close();">
</dhv:evaluate>

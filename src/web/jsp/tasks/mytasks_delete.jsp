<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.tasks.base.*" %>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<jsp:useBean id="DependencyList" class="java.util.HashMap" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<%
  Set s = DependencyList.keySet();
  Iterator i = s.iterator();
  if (i.hasNext()) {
		int rowid = 0;
		int count = 0;
%>
<br>
This task is linked to :
<br>
<form name="deleteView" method="post" action="MyTasks.do?command=Delete&id=<%=Task.getId()%>">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
   <tr>
    <th align="center" width="8%">
      Dependency
    </th>
    <th align="center" width="8%">
      Count
    </th>
   </tr>
<%
  while (i.hasNext()) {
    count++;
    if (rowid != 1) {
      rowid = 1;
    } else {
      rowid = 2;
    }
    Object dependencyName = i.next();
    Object number = DependencyList.get(dependencyName);
%>
    <tr class="row<%= rowid %>">
      <td nowrap align="center"><%= toHtml(dependencyName.toString()) %></td>
      <td nowrap align="center"><%= ((Integer)number).intValue() %></td>
    </tr>
<%
  }%>
  </table>
  <input type="submit" value="Delete All">
  <input type="button" value="Hide All" onClick="javascript:setFieldSubmit('action','hide','taskDeleteView')">
  <input type="button" value="Cancel" onClick="javascript:window.close()">
</form>
  <% } else{ %>
  <br>
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <td align="center">
        <strong>The task "<%=Task.getDescription()%>" has been deleted.</strong>
      </td>
    </tr>
    <br>
    <tr>
      <td align="right">
        <input type="button" value="OK" onClick="javascript:opener.window.location.href='MyTasks.do?command=ListTasks';javascript:window.close()">
      </td>
    </tr>
    </table>
  <%}%>




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
%>
<br>
<dhv:label name="tasks.taskLinkedTo.colon">This task is linked to :</dhv:label>
<br>
<form name="deleteView" method="post" action="MyTasks.do?command=Delete&id=<%=Task.getId()%>">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
   <tr>
    <th align="center" width="8%">
      <dhv:label name="tasks.dependency">Dependency</dhv:label>
    </th>
    <th align="center" width="8%">
      <dhv:label name="campaign.count">Count</dhv:label>
    </th>
   </tr>
<%
  while (i.hasNext()) {
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
  <input type="submit" value="<dhv:label name="button.deleteAll">Delete All</dhv:label>">
  <input type="button" value="<dhv:label name="button.hideAll">Hide All</dhv:label>" onClick="javascript:setFieldSubmit('action','hide','taskDeleteView')">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
</form>
  <% } else{ %>
  <br>
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <td align="center">
      <% String temp = "taskDescription="+Task.getDescription(); %>
        <strong><dhv:label name="tasks.taskHasBeenDeleted.text" param="<%= temp %>">The task "<%=Task.getDescription()%>" has been deleted.</dhv:label></strong>
      </td>
    </tr>
    <br>
    <tr>
      <td align="right">
        <input type="button" value="<dhv:label name="button.ok">OK</dhv:label>" onClick="javascript:opener.window.location.href='MyTasks.do?command=ListTasks';javascript:window.close()">
      </td>
    </tr>
    </table>
  <%}%>




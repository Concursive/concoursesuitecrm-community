<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.modules.tasks.base.TaskCategory" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.tasks.base.TaskCategoryList" scope="request"/>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table cellpadding="0" cellspacing="0" width="100%" border="0">
<tr>
  <td>
    Select a list to move the item to:<br>
    <img border="0" src="images/box<%= Task.getComplete()?"-checked":"" %>.gif" alt="" align="absmiddle">
    <%= toHtml(Task.getDescription()) %>
  </td>
</tr>
</table>
&nbsp;<br>
<table cellpadding="0" cellspacing="0" width="100%" border="1" rules="cols">
  <tr class="section">
    <td>
      <strong>Lists</strong>
    </td>
  </tr>
<%
  int rowid = 0;
  Iterator i = categoryList.iterator();
  while (i.hasNext()) {
    rowid = (rowid != 1?1:2);
    TaskCategory thisCategory = (TaskCategory) i.next();
%>    
  <tr class="row<%= rowid %>">
    <td valign="top">
      <img border="0" src="images/icons/stock_list_enum-16.gif" align="absmiddle">
      <a href="ProjectManagementLists.do?command=SaveMove&pid=<%= Project.getId() %>&cid=<%= thisCategory.getId() %>&id=<%= Task.getId() %>&popup=true&return=ProjectLists&param=<%= Project.getId() %>&param2=<%= Task.getCategoryId() %>"><%= toHtml(thisCategory.getDescription()) %></a>
      <dhv:evaluate if="<%= Task.getCategoryId() == thisCategory.getId() %>">
      (current folder)
      </dhv:evaluate>
    </td>
  </tr>
<%    
  }
%>
</table>
&nbsp;<br>
<input type="button" value="Cancel" onClick="javascript:window.close()">

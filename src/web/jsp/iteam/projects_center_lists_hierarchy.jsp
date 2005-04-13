<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
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
    <dhv:label name="project.selectListToMoveItem">Select a list to move the item to:</dhv:label><br>
    <img border="0" src="images/box<%= Task.getComplete()?"-checked":"" %>.gif" alt="" align="absmiddle">
    <%= toHtml(Task.getDescription()) %>
  </td>
</tr>
</table>
&nbsp;<br>
<table cellpadding="0" cellspacing="0" width="100%" border="1" rules="cols">
  <tr class="section">
    <td>
      <strong><dhv:label name="project.lists">Lists</dhv:label></strong>
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
      <dhv:label name="accounts.accounts_documents_file_move.CurrentFolder">(current folder)</dhv:label>
      </dhv:evaluate>
    </td>
  </tr>
<%    
  }
%>
</table>
<br />
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">

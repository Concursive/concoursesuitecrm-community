<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.modules.tasks.base.TaskCategory" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.tasks.base.TaskCategoryList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table border='0' width='100%'  bgcolor='#000000' cellspacing='0' cellpadding='0'>
  <tr>
    <td width="100%" bgcolor="#39686e">
      <font color="#FFFFFF">&nbsp;<b>Lists</b></font>
    </td>
  </tr>
</table>
   
<table border="0" width="100%" cellpadding="0" cellspacing="0">
  <tr bgcolor="#808080">
    <td width="5" nowrap>&nbsp;</td>
    <td nowrap><font color='#FFFFFF'>&lt;Action&gt;</font></td>
    <td width="100%" nowrap><font color='#FFFFFF'>&lt;Category&gt;</font></td>
    <td nowrap><font color='#FFFFFF'>&lt;Items&gt;</font></td>
    <td nowrap><font color='#FFFFFF'>&lt;Latest Post&gt;</font></td>
  </tr>
<%    
  String bgColorVar = " bgColor=\"#E4E4E4\"";
  Iterator i = categoryList.iterator();
  while (i.hasNext()) {
    TaskCategory thisCategory = (TaskCategory)i.next();
%>    
  <tr<%= bgColorVar %>>
    <td width="5" valign="top" nowrap>&nbsp;</td>
    <td valign="top" align="center" nowrap><a href="ProjectManagementListsCategory.do?command=AddCategory&pid=<%= Project.getId() %>&cid=<%= thisCategory.getId() %>">Edit</a>|Del</td>
    <td width="100%" valign="top" align="left">
      <img border="0" src="images/folder.gif" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Lists&pid=<%= Project.getId() %>&cid=<%= thisCategory.getId() %>"><%= toHtml(thisCategory.getDescription()) %></a>
    </td>
    <td valign="top" align="center" nowrap><%= thisCategory.getTaskCount() %></td>
    <td valign="top" align="left" nowrap><%= toDateTimeString(thisCategory.getLastTaskEntered()) %></td>
  </tr>
<%    
    if (bgColorVar.equals(" bgColor=\"#E4E4E4\"")) {
      bgColorVar = "";
    } else {
      bgColorVar = " bgColor=\"#E4E4E4\"";
    }
  }
%>
</table>
&nbsp;<br>
<hr color='#000000' width='100%' noshade size='1'>
<br>
[<a href="ProjectManagementListsCategory.do?command=AddCategory&pid=<%= Project.getId() %>">Add a Category to this List</a>]

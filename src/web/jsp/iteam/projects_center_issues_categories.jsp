<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table border='0' width='100%'  bgcolor='#000000' cellspacing='0' cellpadding='0'>
  <tr>
    <td width='100%' bgcolor='#003399'>
      <font color='#FFFFFF'>&nbsp;<b>Issues</b></font>
    </td>
  </tr>
</table>
   
<table border='0' width='100%' cellpadding='0' cellspacing='0'>
  <tr>
    <td width='5' bgcolor='#808080' nowrap>&nbsp;</td>
    <td width="100%" bgcolor='#808080' nowrap><font color='#FFFFFF'>&lt;Category&gt;</font></td>
    <td align='left' bgcolor='#808080' nowrap><font color='#FFFFFF'>&lt;Posts&gt;</font></td>
    <td bgcolor='#808080' nowrap><font color='#FFFFFF'>&lt;Threads&gt;</font></td>
    <td bgcolor='#808080' nowrap><font color='#FFFFFF'>&lt;Latest Post&gt;</font></td>
  </tr>
<%    
  String bgColorVar = " bgColor='#E4E4E4'";
	IssueCategoryList issueCategoryList = Project.getIssueCategories();
  Iterator i = issueCategoryList.iterator();
  while (i.hasNext()) {
  
    IssueCategory thisCategory = (IssueCategory)i.next();
%>    
  <tr<%= bgColorVar %>>
    <td width='5' valign='top' nowrap>&nbsp;</td>
    <td width="100%" valign='top' align='left'>
      <img border="0" src="images/folder.gif" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Issues&pid=<%= thisCategory.getProjectId() %>&cid=<%= thisCategory.getId() %>"><%= toHtml(thisCategory.getDescription()) %></a>
    </td>
    <td valign='top' align='center' nowrap><%= ((thisCategory.getPostCount()==0)?"-":""+thisCategory.getPostCount()) %></td>
    <td valign='top' align='center' nowrap><%= ((thisCategory.getThreadCount()==0)?"-":""+thisCategory.getThreadCount()) %></td>
    <td valign='top' align='left' nowrap><%= thisCategory.getLatestPostDateTimeString() %><br><%= toHtml(thisCategory.getModifiedBy()) %></td>
  </tr>
<%    
    if (bgColorVar.equals(" bgColor='#E4E4E4'")) {
      bgColorVar = "";
    } else {
      bgColorVar = " bgColor='#E4E4E4'";
    }
  }
%>
</table>
&nbsp;<br>
<hr color='#000000' width='100%' noshade size='1'>
  

<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<table border='0' width='100%' cellspacing='0' cellpadding='0'>
  <tr>
    <td width='100%' bgcolor='#FF2200'>
      <b><font color='#FFFFFF'>&nbsp;File Sharing</font></b>
    </td>
  </tr>
</table>
   
<table border='0' width='100%' bgcolor="#FFFFFF" cellpadding='0' cellspacing='0' class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor='#808080'>
    <td width="10" align="center"><font color="#FFFFFF">&lt;Action&gt;</font></td>
    <td><font color='#FFFFFF'>&lt;Item&gt;</font></td>
    <td>&nbsp;</td>
    <td><font color='#FFFFFF'>&lt;Size&gt;</font></td>
    <td><font color='#FFFFFF'>&lt;Version&gt;</font></td>
    <td><font color='#FFFFFF'>&lt;Submitted&gt;</font></td>
    <td><font color='#FFFFFF'>&lt;Sent By&gt;</font></td>
  </tr>
<%    
  String bgColorVar = " bgColor='#E4E4E4'";
  FileItemList files = Project.getFiles();
  Iterator i = files.iterator();
  while (i.hasNext()) {
    FileItem thisFile = (FileItem)i.next();
%>    
  <tr<%= bgColorVar %>>
    <td valign="middle" align="center" nowrap>
      <a href="ProjectManagementFiles.do?command=Download&pid=<%= Project.getId() %>&fid=<%= thisFile.getId() %>">Download</a><br>
      <a href="ProjectManagementFiles.do?command=Modify&pid=<%= Project.getId() %>&fid=<%= thisFile.getId() %>">Edit</a>|<a href="javascript:confirmDelete('ProjectManagementFiles.do?command=Delete&pid=<%= Project.getId() %>&fid=<%= thisFile.getId() %>');">Del</a>&nbsp;
    </td>
    <td valign="top">
      <%= thisFile.getImageTag() %><a href="ProjectManagementFiles.do?command=Details&pid=<%= Project.getId() %>&fid=<%= thisFile.getId() %>"><%= toHtml(thisFile.getClientFilename()) %></a><br>
      <i><%= toHtml(thisFile.getSubject()) %></i>
    </td>
    <td align="center" valign="middle">
      [<a href="ProjectManagementFiles.do?command=AddVersion&pid=<%= Project.getId() %>&fid=<%= thisFile.getId() %>">Add Version</a>]
    </td>
    <td align="right" valign="middle">
      <%= thisFile.getRelativeSize() %> k&nbsp;
    </td>
    <td align="center" valign="middle">
      <%= thisFile.getVersion() %>&nbsp;
    </td>
    <td valign="middle" nowrap>
      <%= thisFile.getModifiedDateTimeString() %>
    </td>
    <td valign="middle">
      <%= toHtml(thisFile.getEnteredByString()) %>
    </td>
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
<font size='2'>&nbsp;<br></font>
<hr color='#000000' width='100%' noshade size='1'>
<br>
<font color='#000000'>
  [<a href="ProjectManagementFiles.do?command=Add&pid=<%= Project.getId() %>&folderId=<%= Project.getFiles().getFolderId() %>" style="text-decoration:none;color:black;" onMouseOver="this.style.color = 'blue';" onMouseOut="this.style.color = 'black';">Submit File</a>]
</font>
  

<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%-- Note: When cancel is submitted, the encoding of the form has to be processed differently
     or the id can be added to the action --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<%--
<jsp:useBean id="User" class="com.zeroio.controller.User" scope="session"/>
--%>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <zeroio:folderHierarchy/>
    </td>
  </tr>
</table>
<br />
<img src="images/error.gif" border="0" align="absmiddle"/>
<font color="red">This account's file size limit has been exceeded.</a><br />
<br />
<%--
<dhv:evaluate if="<%= User.getAccountSize() > -1 %>">
--%>
<table class="note" cellspacing="0">
<tr>
  <th>
    <img src="images/icons/stock_about-16.gif" border="0" align="absmiddle" />
  </th>
  <td>
    Maintain your files by deleting older versions of the same file, and by deleting
    outdated or unused files.<%--<br />
    This user account is limited to <%= User.getAccountSize() %> MB.<br />
    This account is currently using <%= User.getCurrentAccountSizeInMB() %> MB.--%>
  </td>
</tr>
</table>
<br />
<%--
</dhv:evaluate>
--%>
<input type="button" value="OK" onClick="window.location.href='ProjectManagement.do?command=ProjectCenter&section=File_Library&pid=<%= Project.getId() %>&folderId=<%= request.getParameter("folderId") %>';">

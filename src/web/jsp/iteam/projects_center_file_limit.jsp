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

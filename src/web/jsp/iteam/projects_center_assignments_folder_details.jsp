<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<%@ page import="com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.utils.web.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="assignmentFolder" class="com.zeroio.iteam.base.AssignmentFolder" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2">
      <strong>Activity Folder</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">Name</td>
    <td valign="top" nowrap>
      <%= toHtml(assignmentFolder.getName()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">Details</td>
    <td>
      <%= toHtml(assignmentFolder.getDescription()) %>
    </td>
  </tr>
</table>
<br />
<input type="button" value="Close" onClick="javascript:window.close()">

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
<%@ page import="java.util.*,org.aspcfs.modules.tasks.base.*,com.zeroio.iteam.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="category" class="org.aspcfs.modules.tasks.base.TaskCategory" scope="request"/>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2">
      <strong>List Item</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Description</td>
    <td>
      <img border="0" src="images/box<%= Task.getComplete()?"-checked":"" %>.gif" alt="" align="absmiddle">
      <%= toHtml(Task.getDescription()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Priority</td>
    <td>
      <%= Task.getPriority() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">Notes</td>
    <td>
      <%= toHtml(Task.getNotes()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">Entered</td>
    <td>
      <dhv:username id="<%= Task.getEnteredBy() %>"/>
      <zeroio:tz timestamp="<%= Task.getEntered() %>"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">Modified</td>
    <td>
      <dhv:username id="<%= Task.getModifiedBy() %>"/>
      <zeroio:tz timestamp="<%= Task.getModified() %>"/>
    </td>
  </tr>
</table>
<dhv:evaluate if="<%= "true".equals(request.getParameter("popup")) %>">
<br>
<input type="button" value="Close" onClick="javascript:window.close()">
</dhv:evaluate>

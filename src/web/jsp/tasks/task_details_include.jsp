<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat" %>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EstimatedLOETypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Task Details</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Description
    </td>
    <td>
      <%= toHtmlValue(Task.getDescription()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Due Date
    </td>
    <td>
      <zeroio:tz timestamp="<%= Task.getDueDate() %>" dateOnly="true" timeZone="<%= Task.getDueDateTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(Task.getDueDateTimeZone())){%>
      <br>
      <zeroio:tz timestamp="<%= Task.getDueDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">Priority</td>
    <td>
      <%= PriorityList.getSelectedValue(Task.getPriority()) %>
    </td>
  </tr>
  <tr class="containerBody"> 
    <td class="formLabel">Status</td>
    <td>
      <% if(Task.getComplete()){ %>
        <img src="images/box-checked.gif">
      <% }else{ %>
        Pending
      <% } %>
    </td>
  </tr>
  <tr class="containerBody"> 
    <td class="formLabel">
      Sharing
    </td>
    <td>
      <%= ((Task.getSharing()==1) ? "personal" : "public") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Assigned To
    </td>
    <td>
         <dhv:username id="<%= Task.getOwner() %>"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Estimated LOE
    </td>
    <td>
      <dhv:evaluate if="<%= (Task.getEstimatedLOE() != -1.0) %>">
        <%=Task.getEstimatedLOE()%>&nbsp;<%= toHtml(EstimatedLOETypeList.getSelectedValue(Task.getEstimatedLOEType())) %>
      </dhv:evaluate>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">Notes</td>
    <td>
      <%= !"".equals(toHtml(Task.getNotes())) ?  toHtml(Task.getNotes()) : "&nbsp;"%>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Link Contact
    </td>
    <td>
         <%= Task.getContactName()!=null?Task.getContactName():"None"%>
    </td>
  </tr>
</table>

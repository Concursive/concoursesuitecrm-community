<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
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

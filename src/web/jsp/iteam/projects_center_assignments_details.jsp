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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<%@ page import="com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.utils.web.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="Assignment" class="com.zeroio.iteam.base.Assignment" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StatusPercentList" class="com.zeroio.iteam.base.HtmlPercentList" scope="request"/>
<%@ include file="../initPage.jsp" %>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <strong>Activity Details</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td valign="top" nowrap class="formLabel">Description</td>
      <td valign="top" nowrap><%= toHtml(Assignment.getRole()) %></td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">Priority</td>
      <td valign="top"><%= toHtml(PriorityList.getValueFromId(Assignment.getPriorityId())) %></td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">Assigned To</td>
      <td valign="top"><dhv:username id="<%= Assignment.getUserAssignedId() %>"/></td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">Status</td>
      <td>
        <%= toHtml(StatusList.getValueFromId(Assignment.getStatusId())) %> (<%= toHtml(StatusPercentList.getValueFromId(Assignment.getPercentComplete())) %>)
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">Keywords</td>
      <td valign="top">
        <%= toHtml(Assignment.getTechnology()) %>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" valign="top" nowrap>Level of Effort</td>
      <td>
        <table border="0" cellspacing="0" cellpadding="0" class="empty">
          <tr>
            <td align="right">
              Estimated:
            </td>
            <td>
              <%= Assignment.getEstimatedLoeString() %>
            </td>
          </tr>
          <tr>
            <td align="right">
              Actual:
            </td>
            <td>
              <%= Assignment.getActualLoeString() %>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">Due Date</td>
      <td valign="top"><zeroio:tz timestamp="<%= Assignment.getDueDate() %>" dateOnly="true"/></td>
    </tr>
  </table>
<dhv:evaluate if="<%= isPopup(request) %>">
  <br>
  <input type="button" value="Close" onClick="javascript:window.close()"/>
</dhv:evaluate>

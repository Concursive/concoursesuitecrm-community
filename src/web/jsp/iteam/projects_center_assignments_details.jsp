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

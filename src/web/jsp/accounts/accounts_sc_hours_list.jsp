<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.utils.web.*,java.util.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,java.text.DateFormat" %>
<jsp:useBean id="serviceContractHoursHistory" class="org.aspcfs.modules.servicecontracts.base.ServiceContractHoursList" scope="request"/>
<jsp:useBean id="serviceContractHoursHistoryInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="serviceContractHoursReasonList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
</script>
<%-- Trails --%>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="serviceContractHoursHistoryInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="10%" nowrap>
      <strong><dhv:label name="account.sc.adjustment">Adjustment</dhv:label></strong>
    </th>
    <th width="30%" >
      <strong><dhv:label name="account.sc.reason">Reason</dhv:label></strong>
    </th>
    <th width="50%" >
      <strong><dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label></strong>
    </th>
    <th width="10%" nowrap>
      <b><a href="AccountsServiceContracts.do?command=HoursHistory&id=<%=serviceContractHoursHistory.getContractId()%>&popup=true&popupType=inline&column=modified">Date</a></b>
      <%= serviceContractHoursHistoryInfo.getSortIcon("modified") %>
    </th>
  </tr>
  
  <% 
    Iterator itr = serviceContractHoursHistory.iterator();
    if (itr.hasNext()){
      int rowid = 0;
      int i = 0;
      while (itr.hasNext()){
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        ServiceContractHours thisHours = (ServiceContractHours)itr.next();
    %>
    <tr class="row<%= rowid %>">
      <td width="10%" valign="top" align="right">
        <%= thisHours.getAdjustmentHours() %>
      </td>
      <td width="30%" valign="top" nowrap>
        <%= toHtml(serviceContractHoursReasonList.getSelectedValue(thisHours.getAdjustmentReason())) %>
      </td>
      <td width="50%"  >
      <%= toHtml(thisHours.getAdjustmentNotes()) %>
      </td>
      <td width="10%" valign="top">
        <zeroio:tz timestamp="<%= thisHours.getModified() %>" dateOnly="true" default="&nbsp;"/>
      </td>
    </tr>
    <%  
      }
    }else{
    %>
    <tr class="containerBody">
      <td colspan="4">
        <dhv:label name="account.sc.noHistoryOfHours">No history of hours</dhv:label>
      </td>
    </tr>
    <%
    }
    %>
</table>
  <br>
  <dhv:pagedListControl object="serviceContractHoursHistoryInfo"/>
</td>
</tr>
</table>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.utils.web.*,java.util.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,java.text.DateFormat" %>
<jsp:useBean id="serviceContractHoursHistory" class="org.aspcfs.modules.servicecontracts.base.ServiceContractHoursList" scope="request"/>
<jsp:useBean id="serviceContractHoursHistoryInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="serviceContractHoursReasonList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="sc_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="serviceContractHoursHistoryInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="10%" nowrap>
      <strong>Adjustment</strong>
    </th>
    <th width="30%" >
      <strong>Reason</strong>
    </th>
    <th width="50%" >
      <strong>Notes</strong>
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
      <td width="10%" align="right">
        <%= thisHours.getAdjustmentHours() %>
      </td>
      <td width="30%" nowrap>
        <%= toHtml(serviceContractHoursReasonList.getSelectedValue(thisHours.getAdjustmentReason())) %>
      </td>
      <td width="50%"  >
      <%= toHtml(thisHours.getAdjustmentNotes()) %>
      </td>
      <td width="10%" >
        <dhv:tz timestamp="<%=thisHours.getModified()%>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
      </td>
    </tr>
    <%  
      }
    }else{
    %>
    <tr class="containerBody">
      <td colspan="4">
        No history of hours
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

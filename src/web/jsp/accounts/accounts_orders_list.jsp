<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*, org.aspcfs.utils.web.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.orders.base.*,org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="OrderList" class="org.aspcfs.modules.orders.base.OrderList" scope="request"/>
<jsp:useBean id="OrderListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="statusSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="typeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_orders_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/executeFunction.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/moveContact.js"></script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<script language="JavaScript">
  function reopen(){
    window.location.href='AccountOrders.do?command=View&orgId=<%= OrgDetails.getOrgId() %>';
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
Orders
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="accounts" selected="orders" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="OrderListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
<tr>
  <th width="8">
    &nbsp;
  </th>
  <th nowrap >
    <strong><a href="AccountOrders.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=oe.order_id">Order Number</a></strong>
    <%= OrderListInfo.getSortIcon("oe.order_id") %>
  </th>
    <th nowrap>
      <strong><a href="AccountOrders.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=oe.entered">Order Date</a></strong>
      <%= OrderListInfo.getSortIcon("oe.entered")%>
    </th>
    <th>
      <strong><a href="AccountOrders.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=oe.order_type_id">Type</a></strong>
      <%= OrderListInfo.getSortIcon("oe.order_type_id")%>
    </th>
    <th>
      <strong>Description</strong>
    </th>
    <th nowrap>
      <strong><a href="AccountOrders.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=oe.status_id">Current Status</a></strong>
      <%= OrderListInfo.getSortIcon("oe.status_id")%>
    </th>
    <th nowrap>
      <strong><a href="AccountOrders.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=oe.status_date">Status Date</a></strong>
      <%= OrderListInfo.getSortIcon("oe.status_date")%>
    </th>
</tr>
<%
	Iterator j = OrderList.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    Order thisOrder = (Order)j.next();
%>      
		<tr class="containerBody">
      <td valign="center" nowrap class="row<%= rowid %>">
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('menuOrder', '<%= OrgDetails.getOrgId() %>', '<%= thisOrder.getId() %>');"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
      <td valign="center" class="row<%= rowid %>">
        <a href="AccountOrders.do?command=Details&id=<%=thisOrder.getId()%>">Order #<%= thisOrder.getId() %></a>
      </td>
      <td nowrap class="row<%= rowid %>">
        <dhv:tz timestamp="<%= thisOrder.getEntered() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
      </td>
      <td nowrap class="row<%= rowid %>">
        <%= toHtml(typeSelect.getValueFromId(thisOrder.getOrderTypeId())) %>
      </td>
      <td class="row<%= rowid %>" width="70%">
        <%= toHtml(thisOrder.getDescription()) %>
      </td>
      <td nowrap class="row<%= rowid %>" width="30%">
        <%= toHtml(statusSelect.getValueFromId(thisOrder.getStatusId())) %>
      </td>
      <td nowrap class="row<%= rowid %>">
        <dhv:tz timestamp="<%= thisOrder.getModified() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
      </td>
    </tr>
<% } %>
<%} else {%>
		<tr class="containerBody">
      <td colspan="7">
        No orders found.
      </td>
    </tr>
<%}%>
	</table>
	<br>
  <dhv:pagedListControl object="OrderListInfo"/>
</dhv:container>
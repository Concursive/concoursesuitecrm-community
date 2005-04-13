<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.orders.base.*" %>
<jsp:useBean id="orderList" class="org.aspcfs.modules.orders.base.OrderList" scope="request" />
<jsp:useBean id="searchOrderListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="typeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statusSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %> 
<%@ include file="orders_list_menu.jsp" %> 
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<script language="JavaScript">
  function reopen(){
    window.location.href='Orders.do?command=Search';
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Orders.do">Orders</a> > 
      <dhv:label name="accounts.SearchResults">Search Results</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<%--
<a href="Orders.do?command=Add">Add an Order</a>
--%>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="searchOrderListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      &nbsp;
    </th>
    <th nowrap>
      <strong><a href="Orders.do?command=Search&column=oe.order_id"> Order<br />Number</a></strong>
      <%= searchOrderListInfo.getSortIcon("oe.order_id")%>
    </th>
    <th nowrap>
      <strong><a href="Orders.do?command=Search&column=oe.entered"> Order Date</a></strong>
      <%= searchOrderListInfo.getSortIcon("oe.entered")%>
    </th>
    <th nowrap>
      <strong><a href="Orders.do?command=Search&column=oe.org_id">Organization</a></strong>
      <%= searchOrderListInfo.getSortIcon("oe.org_id")%>
    </th>
    <th nowrap>
      <strong><a href="Orders.do?command=Search&column=oe.order_type_id">Type</a></strong>
      <%= searchOrderListInfo.getSortIcon("oe.order_type_id")%>
    </th>
    <th>
      <strong>Description</strong>
    </th>
    <th nowrap>
      <strong><a href="Orders.do?command=Search&column=oe.status_id">Current Status</a></strong>
      <%= searchOrderListInfo.getSortIcon("oe.status_id")%>
    </th>
    <th nowrap>
      <strong><a href="Orders.do?command=Search&column=oe.status_date">Status Date</a></strong>
      <%= searchOrderListInfo.getSortIcon("oe.status_date")%>
    </th>
  </tr>
<%
	Iterator j = orderList.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    Order thisOrder = (Order)j.next();
    i++;
    rowid = (rowid != 1 ? 1 : 2);    
%>      
  <tr class="row<%= rowid %>">
    <td width="8" valign="top" nowrap>
      <a href="javascript:displayMenu('menuOrders', '<%= thisOrder.getId() %>');"
      onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
		<td valign="top" nowrap>
      <a href="Orders.do?command=Details&id=<%=thisOrder.getId()%>">Order #<%= thisOrder.getId() %></a>
		</td>
		<td valign="top" nowrap>
        <dhv:tz timestamp="<%= thisOrder.getEntered() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
    </td>
		<td valign="top" width="30%">
      <%= toHtml(thisOrder.getName()) %>
    </td>
		<td valign="top" nowrap>
      <%= toHtml(typeSelect.getValueFromId(thisOrder.getOrderTypeId())) %>
    </td>
		<td valign="top" width="70%">
      <%= toHtml(thisOrder.getDescription()) %>
    </td>
		<td valign="top" nowrap>
      <%= toHtml(statusSelect.getValueFromId(thisOrder.getStatusId())) %>
    </td>
		<td valign="top" nowrap>
      <dhv:tz timestamp="<%= thisOrder.getModified() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
    </td>
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="5">
      No  Orders found for the specified search parameters. <a href="Orders.do?command=SearchForm"><dhv:label name="accounts.accounts_list.ModifySearch">Modify Search</dhv:label></a>. 
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="searchOrderListInfo" tdClass="row1"/>


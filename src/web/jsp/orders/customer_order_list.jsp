<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.orders.base.*,org.aspcfs.modules.products.actions.*,org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="orderList" class="org.aspcfs.modules.orders.base.OrderList" scope="request" />
<jsp:useBean id="orderListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="typeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statusSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="ProductsAndServices.do">Products And Services</a> > 
      Order History
    </td>
  </tr>
</table>
<%-- End Trails --%>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="orderListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th nowrap>
      <strong><a href="OrderHistory.do?command=View&column=oe.order_id"> Order Number</a></strong>
      <%= orderListInfo.getSortIcon("oe.order_id")%>
    </th>
    <th nowrap>
      <strong><a href="OrderHistory.do?command=View&column=oe.entered"> Order Date</a></strong>
      <%= orderListInfo.getSortIcon("oe.entered")%>
    </th>
    <th>
      <strong>Description</strong>
    </th>
    <th nowrap>
      <strong><a href="OrderHistory.do?command=View&column=oe.status_id">Current Status</a></strong>
      <%= orderListInfo.getSortIcon("oe.status_id")%>
    </th>
    <th nowrap>
      <strong><a href="OrderHistory.do?command=View&column=oe.status_date">Status Date</a></strong>
      <%= orderListInfo.getSortIcon("oe.status_date")%>
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
		<td valign="top" width="10%" nowrap>
      <a href="OrderHistory.do?command=Details&id=<%= thisOrder.getId() %>">Order #<%= thisOrder.getId() %></a>
		</td>
		<td valign="top" width="20%" nowrap>
        <dhv:tz timestamp="<%= thisOrder.getEntered() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
    </td>
		<td valign="top" width="30%">
      <%= toHtml(thisOrder.getDescription()) %>
    </td>
		<td valign="top" width="10%" nowrap>
      <%= toHtml(statusSelect.getValueFromId(thisOrder.getStatusId())) %>
    </td>
		<td valign="top" width="10%" nowrap>
      <dhv:tz timestamp="<%= thisOrder.getModified() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
    </td>
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="5">
      You do not have any orders.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="orderListInfo" tdClass="row1"/>


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="orderProduct" class="org.aspcfs.modules.orders.base.OrderProduct" scope="request"/>
<jsp:useBean id="order" class="org.aspcfs.modules.orders.base.Order" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<%@ include file="../../initPage.jsp" %>
<script type="text/javascript">
  function closeMe() {
    opener.location.href="AccountsProducts.do?command=Add&orgId=<%= order.getOrgId() %>&orderItemId=<%= orderProduct.getId() %>";
    self.close();
  }
</script>
<table cellspacing="0">
  <tr>
    <td nowrap><strong>Customer Product</strong></td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      Action
    </th>
    <th>
      File Name
    </th>
    <th>
      Size
    </th>
    <th>
      Version
    </th>
  </tr>
<%
if( customerProduct.getFileItemList().size() > 0 ) {
  Iterator i = customerProduct.getFileItemList().iterator();
  FileItem targetFile = null;
  while (i.hasNext()) {
    targetFile = (FileItem) i.next();
%>
  <tr>
    <td nowrap align="left">
      <a href="Publish.do?command=CustomerProductDownload&adId=<%= customerProduct.getId() %>&fileId=<%= targetFile.getId() %>&version=1.0"> download</a>
    </td>
    <td nowrap align="left">
      <%= targetFile.getClientFilename() %>
    </td>
    <td nowrap align="left">
      <%= targetFile.getSize() %>
    </td>
    <td nowrap align="left">
      <%= targetFile.getVersion() %>
    </td>
  </tr>
<%
  }
} else {
%>
  <tr>
    <td colspan="4">
      No Customer Product Found. Please <a href="#" onClick="closeMe();">Add a Customer Product</a>
    </td>
  </tr>
<%
}
%>
</table>


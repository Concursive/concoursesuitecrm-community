<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*, org.aspcfs.utils.web.*,java.util.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,java.text.DateFormat" %>
<jsp:useBean id="productList" class="org.aspcfs.modules.servicecontracts.base.ServiceContractProductList" scope="request"/>
<jsp:useBean id="finalProducts" class="org.aspcfs.modules.servicecontracts.base.ServiceContractProductList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="selectedList" class="java.util.ArrayList" scope="session"/>
<jsp:useBean id="selectedProductList" class="java.util.ArrayList" scope="session"/>
<jsp:useBean id="ServiceContractProductListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<%
  if (!"true".equals(request.getParameter("finalsubmit"))) {
     String source = request.getParameter("source");
%>
<%-- Navigating the contact list --%>
<br>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ServiceContractProductListInfo" showHiddenParams="true" enableJScript="true" />
<br>

<form name="productListView" method="post" action="ProductSelector.do?command=ListProducts&contractId=<%=request.getParameter("contractId")%>">
<!-- Make sure that when the list selection changes previous selected entries are saved -->
  <input type="hidden" name="letter">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th width="20%">
      <strong>Code</strong>
    </th>
    <th width="80%">
      <strong>Description</strong>
    </th>
  </tr>
  
  <% 
    Iterator itr = productList.iterator();
    if (itr.hasNext()){
      int rowid = 0;
      int i = 0;
      while (itr.hasNext()){
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        ServiceContractProduct thisContractProduct = (ServiceContractProduct)itr.next();
        String id = String.valueOf(thisContractProduct.getId());
        String hiddenProductId = String.valueOf(thisContractProduct.getProductId());
        
    %>      
    <tr class="row<%= rowid+(selectedProductList.indexOf(hiddenProductId) != -1 ? "hl" : "") %>">
      <td align="center" nowrap width="8">
        <a href="javascript:document.productListView.finalsubmit.value = 'true';javascript:setFieldSubmit('rowcount','<%= i %>','productListView');">Add</a>
        <input type="hidden" name="hiddenId<%= i %>" value="<%= id %>" />
        <input type="hidden" name="hiddenProductId<%= i %>" value="<%= hiddenProductId %>" />
      </td>
      <td width="20%">
        <%=toHtml(thisContractProduct.getProductSku())%>
      </td>
      <td width="20%">
        <%=toHtml(thisContractProduct.getProductName())%>
      </td>
    </tr>
    <%  
      }
    }else{
    %>
    <tr>
      <td colspan="6">
        No products found.
      </td>
    </tr>
  <%}%>
  <input type="hidden" name="finalsubmit" value="false" />
  <input type="hidden" name="listType" value="single" />
  <input type="hidden" name="rowcount" value="0">
  <input type="hidden" name="hiddenFieldId" value="<%= toHtmlValue(request.getParameter("hiddenFieldId")) %>" />
  <input type="hidden" name="displayFieldId" value="<%= toHtmlValue(request.getParameter("displayFieldId")) %>">
  </table>
  <br>
 <dhv:pagedListControl object="ServiceContractProductListInfo" /> 

<% if("list".equals(request.getParameter("listType"))){ %>
  <input type="button" value="Done" onClick="javascript:setFieldSubmit('finalsubmit','true','productListView');">
  <input type="button" value="Cancel" onClick="javascript:window.close()">
  <a href="javascript:SetChecked(1,'serviceContract','productListView','<%=User.getBrowserId()%>');">Check All</a>
  <a href="javascript:SetChecked(0,'serviceContract','productListView','<%=User.getBrowserId()%>');">Clear All</a>
<%}else{%>
  <input type="button" value="Cancel" onClick="javascript:window.close()">
<%}%>
</form>

<%} else { %>
<%-- The final submit --%>
  <body OnLoad="javascript:setParentList(selectedValues, selectedIds, '<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>');window.close()">
  <script>selectedIds = new Array();selectedValues = new Array();</script>
  <%
  Iterator i = finalProducts.iterator();
  int count = -1;
  while (i.hasNext()) {
    count++;
    ServiceContractProduct thisContractProduct = (ServiceContractProduct) i.next();
%>
    <script>
    selectedIds[<%= count %>] = "<%= thisContractProduct.getProductId() %>";
    selectedValues[<%= count %>] = "<%= toJavaScript(thisContractProduct.getProductSku()) %>";
  </script>
<%	
  }
%>
  </body>
  
<%	
      session.removeAttribute("selectedProducts");
  }
%>


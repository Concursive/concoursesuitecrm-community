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
%>
<%-- Navigating the contact list --%>
<br>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="ServiceContractProductListInfo" showHiddenParams="true" enableJScript="true" form="productListView"/>
<br>

<form name="productListView" method="post" action="ProductSelector.do?command=ListProducts&contractId=<%=request.getParameter("contractId")%>">
<!-- Make sure that when the list selection changes previous selected entries are saved -->
  <input type="hidden" name="letter">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      &nbsp;
    </th>
    <th width="20%">
      <strong><dhv:label name="product.code">Code</dhv:label></strong>
    </th>
    <th width="80%">
      <strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong>
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
        <a href="javascript:document.productListView.finalsubmit.value = 'true';javascript:setFieldSubmit('rowcount','<%= i %>','productListView');"><dhv:label name="button.add">Add</dhv:label></a>
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
        <dhv:label name="calendar.noProductsFound">No products found.</dhv:label>
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
  <input type="button" value="<dhv:label name="button.done">Done</dhv:label>" onClick="javascript:setFieldSubmit('finalsubmit','true','productListView');">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
  <a href="javascript:SetChecked(1,'serviceContract','productListView','<%=User.getBrowserId()%>');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>
  <a href="javascript:SetChecked(0,'serviceContract','productListView','<%=User.getBrowserId()%>');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>
<%}else{%>
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
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


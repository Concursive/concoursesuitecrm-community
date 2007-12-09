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
  - Author(s): 
  - Version: $Id$
  - Description:
  --%>
<script type="text/javascript">
var showGrandTotal = '<%= quote.getShowTotal() %>';
var showSubtotal = '<%= quote.getShowSubtotal() %>';
var rowCounter = 0;
  function printQuote(id) {
    window.location.href = 'Quotes.do?command=PrintQuote&id=' + id + '&display=' + showGrandTotal +'&subTotal='+ showSubtotal;
  }

  function generateClone() {
    popURL('Quotes.do?command=CloneForm&quoteId=<%= quote.getId() %>&version=<%= version %>','Close','500','400','yes','yes');
  }
  
  function closeQuote() {
    popURL('Quotes.do?command=Close&quoteId=<%= quote.getId() %>','Close','500','400','yes','yes');
  }

  function emailQuote(id) {
    popURL('Quotes.do?command=Email&quoteId='+id,'Email','600','400','yes','yes');
  }

  function setShowSubtotal(tag) {
    var value = tag.checked;
    var url = 'Quotes.do?command=ChangeShowSubtotal&quoteId=<%= quote.getId() %>&showSubtotal='+value;
    window.frames['server_commands'].location.href=url;
  }

  function changeShowSubtotal(show) {
    if (show == 'true') {
      var initial = document.getElementById('quoteItems').colSpan;
      document.getElementById('quoteItems').colSpan = initial +1;
      showSubtotal = 'true';
      if ('<%= quote.getProductList().size() != 0 %>'=='true') {
        document.getElementById('quoteItemChoose').colSpan = initial +1;
      } 
      <dhv:permission name="quotes-quotes-edit">
      else {
        document.getElementById('quoteItemEmpty').colSpan = initial +1;
      }
      </dhv:permission>
      for (i=0;i <= rowCounter;i++) {
        var thisElement = document.getElementById('subTotal'+i);
        thisElement.style.visibility = "visible";
        showSpan('subTotal'+i);
      }
    } else if (show == 'false') {
      var initial = document.getElementById('quoteItems').colSpan;
      document.getElementById('quoteItems').colSpan = initial -1;
      showSubtotal = 'false';
      if ('<%= quote.getProductList().size() != 0 %>'=='true') {
        document.getElementById('quoteItemChoose').colSpan = initial -1;
      } 
      <dhv:permission name="quotes-quotes-edit">
      else {
        document.getElementById('quoteItemEmpty').colSpan = initial -1;
      }
      </dhv:permission>
      for (i=0;i <= rowCounter;i++) {
        var thisElement = document.getElementById('subTotal'+i);
        thisElement.style.visibility = "hidden";
        hideSpan('subTotal'+i);
      }
    }
  }
  
  function setShowTotal(tag) {
    var value = tag.checked;
    var url = 'Quotes.do?command=ChangeShowTotal&quoteId=<%= quote.getId() %>&showTotal='+value;
    window.frames['server_commands'].location.href=url;
  }
  
  function changeShowTotal(show) {
    if (show == 'true') {
      showGrandTotal = 'true';
      showSpan('grandTotal');
    } else if (show == 'false') {
      showGrandTotal = 'false';
      hideSpan('grandTotal');
    }
  }
</script>

<script type="text/javascript">
currentConditionIDs = '';
currentConditionNames = '';
currentRemarkIDs = '';
currentRemarkNames = '';
</script>
<%
  if (quote.getConditions().size() != 0) {
  int counter = 0;
  Iterator it = (Iterator) quote.getConditions().iterator();
  while (it.hasNext()) {
    QuoteCondition condition = (QuoteCondition) it.next();
    counter++;
%>
  <script type="text/javascript">
  currentConditionIDs = currentConditionIDs+'|';
  currentConditionNames = currentConditionNames + '|';
  currentConditionIDs = currentConditionIDs+ '<%= condition.getConditionId() %>';
  currentConditionNames = currentConditionNames + '<%= StringUtils.jsEscape(condition.getConditionName()) %>';
  </script>
<%}}%>
<%
  if (quote.getRemarks().size() != 0) {
  int counter = 0;
  Iterator it = (Iterator) quote.getRemarks().iterator();
  while (it.hasNext()) {
    QuoteRemark remark = (QuoteRemark) it.next();
    counter++;
%>
  <script type="text/javascript">
  currentRemarkIDs = currentRemarkIDs+'|';
  currentRemarkNames = currentRemarkNames + '|';
  currentRemarkIDs = currentRemarkIDs+ '<%= remark.getRemarkId() %>';
  currentRemarkNames = currentRemarkNames + '<%= StringUtils.jsEscape(remark.getRemarkName()) %>';
  </script>
<%}}%>


<% if(quote.getIssuedDate() != null || quote.getTicketId() != -1 || quote.getParentId() != -1 || order.getId() != -1 || quote.getHeaderId() != -1){ %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="4">
      <strong><dhv:label name="quotes.quoteDetails">Quote Details</dhv:label></strong>
    </th>
  </tr>
  <% if(quote.getIssuedDate() != null){ %>
  <tr class="containerBody" valign="top">
    <td class="formLabel" nowrap><dhv:label name="quotes.quoteIssuedDate">Issued Date</dhv:label></td>
    <td width="100%">
      <zeroio:tz timestamp="<%= quote.getIssuedDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
    </td>
  </tr>
  <%} if(quote.getTicketId() != -1){ %>
  <tr class="containerBody">
    <td class="formLabel" valign="top" nowrap><dhv:label name="quotes.quoteRelatedTicket">Related Ticket</dhv:label></td>
    <td width="100%">
    <dhv:permission name="<%= ticketPermission %>">
      <a href="<%= ticketLink %>"><dhv:label name="tickets.symbol.number" param='<%= "number="+quote.getTicketId() %>'>Ticket #<%= quote.getTicketId() %></dhv:label></a>
    </dhv:permission><dhv:permission name="<5= ticketPermission %>" none="true">
      <dhv:label name="tickets.symbol.number" param='<%= "number="+quote.getTicketId() %>'>Ticket #<%= quote.getTicketId() %></dhv:label>
    </dhv:permission>
    </td>
  </tr>
  <% } %>
<% if (quote.getParentId() != -1) { %>
  <tr class="containerBody">
    <td class="formLabel" valign="top" nowrap><dhv:label name="quotes.quoteNextVersion">Next Version</dhv:label></td>
    <td width="100%"><a href="<%= quoteNextVersionLink %>"><dhv:label name="quotes.symbol.number" param='<%= "number="+quote.getGroupId() %>'>Quote #<%= quote.getGroupId() %></dhv:label></a></td>
  </tr>
<% } if (order.getId() != -1) { %>
  <tr class="containerBody">
    <td class="formLabel" valign="top" nowrap><dhv:label name="quotes.quoteGeneratedOrder">Generated Order</dhv:label></td>
    <td width="100%">
    <dhv:permission name="<%= orderPermission %>">
    <a href="<%= orderLink %>"><dhv:label name="orders.symbol.number">Order #</dhv:label><%= order.getId() %></a>
    </dhv:permission><dhv:permission name="<%= orderPermission %>" none="true">
      <dhv:label name="orders.symbol.number">Order #</dhv:label><%= order.getId() %>
    </dhv:permission>
    </td>
  </tr>
  <% } %>
  <% if (quote.getHeaderId() != -1) { %>
  <tr class="containerBody">
    <td class="formLabel" valign="top" nowrap><dhv:label name="quotes.opportunity">Opportunity</dhv:label></td>
    <td width="100%">
    <dhv:permission name="<%= opportunityPermission %>">
      <dhv:evaluate if="<%= opportunity.isTrashed() %>" >
        <%= toHtml(opportunity.getDescription()) %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= !opportunity.isTrashed() %>" >
        <a href="<%= opportunityLink %>"><%= toHtml(opportunity.getDescription()) %></a>
      </dhv:evaluate>
    </dhv:permission>
    <dhv:permission name="<%= opportunityPermission %>" none="true">
      <%= toHtml(opportunity.getDescription()) %>
    </dhv:permission>
    </td>
  </tr>
  <% } %>
</table>
&nbsp;<br />
<%}%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="quotes.quotePreparedFor">Quote prepared for</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top" nowrap><dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label></td>
    <td width="100%">
      <dhv:evaluate if="<%= quote.getContact().getEmployee() %>">
        <%= toHtml(quote.getContact().getNameFull()+(!quote.getContact().getEnabled() || quote.getContact().isTrashed() ? " (X)":"")) %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= !quote.getContact().getEmployee() %>">
        <dhv:permission name="<%= contactPermission %>">
          <a href="javascript:popURL('ExternalContacts.do?command=ContactDetails&id=<%= quote.getContact().getId() %>&popup=true&popupType=inline','Details','650','500','yes','yes');"><strong><%= toHtml(quote.getContact().getNameFull()+(!quote.getContact().getEnabled() || quote.getContact().isTrashed() ? " (X)":"")) %></strong></a>
        </dhv:permission>
        <dhv:permission name="<%= contactPermission %>" none="true">
          <strong><%= toHtml(quote.getContact().getNameFull()) %></strong>
        </dhv:permission>
      </dhv:evaluate>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="ticket.organizationLink">Organization</dhv:label>
    </td>
    <td width="100%">
      <%= toHtml(quote.getName()) %>
    </td>
  </tr>
<%if (quote.getEmailAddress() != null && !"".equals(quote.getEmailAddress())) {%>
  <tr class="containerBody">
    <td class="formLabel" valign="top" nowrap><dhv:label name="accounts.accounts_add.Email">Email</dhv:label></td>
    </td>
    <td valign="top">
      <a href="mailto:<%= toHtml(quote.getEmailAddress()) %>"><%= toHtml(quote.getEmailAddress()) %></a>
    </td>
  </tr>
<%}%>
<%if (quote.getPhoneNumber() != null && !"".equals(quote.getPhoneNumber())) {%>
  <tr class="containerBody">
    <td class="formLabel" valign="top" nowrap><dhv:label name="quotes.phoneNumber">Phone Number</dhv:label></td>
    <td valign="top">
      <%= toHtml(quote.getPhoneNumber()) %>
    </td>
  </tr>
<%}%>
<%if (quote.getFaxNumber() != null && !"".equals(quote.getFaxNumber())) {%>
  <tr class="containerBody">
    <td class="formLabel" valign="top" nowrap><dhv:label name="quotes.faxNumber">Fax Number</dhv:label></td>
    <td valign="top">
      <%= toHtml(quote.getFaxNumber()) %>
    </td>
  </tr>
<%}%>
<%if (quote.getAddress() != null && !"".equals(quote.getAddress())) {%>
  <tr class="containerBody">
    <td class="formLabel" valign="top" nowrap><dhv:label name="quotes.address">Address</dhv:label></td>
    <td valign="top">
      <%= toHtml(quote.getAddress()) %>
    </td>
  </tr>
<%}%>
	<dhv:evaluate if="<%= fileItem.getId() != -1 %>">
	<tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="quotes.logo">Logo</dhv:label>
    </td>
    <td width="100%">
    	<%= toHtml(fileItem.getSubject()) %>
    </td>
  </tr>
	</dhv:evaluate>
</table>
&nbsp;
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th colspan="<%= 8+showAction %>" name="quoteItems" id="quoteItems">
      <strong><dhv:label name="quotes.listOfItems">List of Items</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
<% int rowCounter = 0; %>
<dhv:evaluate if="<%= (quote.getClosed() == null) && (!quote.isTrashed()) %>" >
   <dhv:evaluate if="<%=(!quote.getLock())%>" >
  <dhv:permission name="<%= quoteEditPermission %>">
      <td width="8" valign="middle">
        &nbsp;
      </td>
  </dhv:permission>
</dhv:evaluate>
</dhv:evaluate>
 <td width="8" valign="middle">
      <strong><dhv:label name="accounts.accounts_documents_details.Item">Item</dhv:label></strong>
    </td>
    <td valign="middle">
      <strong><dhv:label name="quotes.squ">SKU</dhv:label></strong>
    </td>
    <td valign="middle" nowrap>
      <strong><dhv:label name="quotes.itemName">Item Name</dhv:label></strong>
    </td>
    <td valign="middle" nowrap>
      <strong><dhv:label name="quotes.itemDescription">Item Description</dhv:label></strong>
    </td>
    <td valign="middle" align="center">
      <strong><dhv:label name="quotes.quantity">Quantity</dhv:label></strong>
    </td>
    <td valign="middle" align="center">
      <strong><dhv:label name="quotes.unitPrice" param="break=<br />">Unit<br />Price</dhv:label></strong>
    </td>
    <td valign="middle" align="center">
      <strong><dhv:label name="quotes.estimatedDelivery" param="break=<br />">Estimated<br />Delivery</dhv:label></strong>
    </td>
<%--    <dhv:evaluate if="<%= quote.getShowSubtotal() %>"> --%>
    <td valign="middle" align="center" id="subTotal<%= rowCounter++ %>"><span name="subTotal<%= rowCounter %>" id="subTotal<%= rowCounter %>" <%= (quote.getShowSubtotal() == true) ? "" : "style=\"display:none\"" %>><strong><dhv:label name="quotes.subTotal">Sub-Total</dhv:label></strong></span></td>
<%--    </dhv:evaluate> --%>
  </tr>
<% 
int i = 0;
if(quote.getProductList().size() != 0){
  int rowid = 0;
  Iterator quoteProducts = (Iterator) quote.getProductList().iterator();
  while(quoteProducts.hasNext()){
    QuoteProduct product = (QuoteProduct) quoteProducts.next();
    ProductCatalog productCatalog = productList.getProductFromId(product.getProductId());
    ProductCatalogPricing price = productCatalog.getActivePrice();
    QuoteProductOptionList optionsList = product.getProductOptionList();
    Iterator options = (Iterator) optionsList.iterator();
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    rowCounter++;
%>
  <tr class="row<%= rowid %>">
<dhv:evaluate if="<%= (quote.getClosed() == null) && (!quote.isTrashed()) %>" >
    <dhv:evaluate if="<%=(!quote.getLock())%>" >
  <dhv:permission name="<%= quoteEditPermission %>">
      <td <%= (optionsList.size() > 0 ? "rowspan=\"" + (optionsList.size() + 2) + "\"" : "") %> width="8" valign="top" nowrap class="row<%= rowid %>">
        <a href="javascript:displayMenu('select<%= i %>','menuQuoteProduct', '<%= quote.getId() %>', '<%= product.getId() %>', '<%= location %>', '<%= orgId %>');"
        onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuQuoteProduct');">
        <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0" /></a>
      </td>
  </dhv:permission>
</dhv:evaluate>
</dhv:evaluate>
    <td <%= (optionsList.size() > 0 ? "rowspan=\"" + (optionsList.size() + 2) + "\"" : "") %> width="8" valign="top" align="right" nowrap><%= i %>.</td>
    <td nowrap valign="top"><%= toHtml(productCatalog.getSku()) %></td>
    <td valign="top"><%= toHtml(productCatalog.getName()) %></td>
    <td valign="top"><%= toHtml(product.getComment()) %></td>
    <td width="8" valign="top" align="right"><%= product.getQuantity() %></td>
    <td nowrap valign="top" align="right">
      <zeroio:currency value="<%= product.getPriceAmount() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/>
    </td>
    <td nowrap valign="top" align="center"><%= toHtml(product.getEstimatedDelivery()) %></td>
    <td nowrap valign="top" align="right" id="subTotal<%= rowCounter++ %>"><span name="subTotal<%= rowCounter %>" id="subTotal<%= rowCounter %>" <%= (quote.getShowSubtotal() == true) ? "" : "style=\"display:none\"" %>><zeroio:currency value="<%= (product.getTotalPrice()) %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/></span></td>
  </tr>
<%
  if (options.hasNext()) {
    rowCounter++;
%>
  <tr class="row<%= rowid %>">
    <td width="20%" colspan="2"><strong><dhv:label name="product.option">Option</dhv:label></strong></td>
    <td width="15%" colspan="2"><strong><dhv:label name="product.value">Value</dhv:label></strong></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td align="right" id="subTotal<%= rowCounter++ %>"><span name="subTotal<%= rowCounter %>" id="subTotal<%= rowCounter%>" <%= (quote.getShowSubtotal() == true) ? "" : "style=\"display:none\"" %>>&nbsp;</span></td>
  </tr>
  <%
      while(options.hasNext()){
        QuoteProductOption option = (QuoteProductOption) options.next();
        ProductOption productOption = optionList.getOptionFromId(option.getOptionId());
        rowCounter++;
   %>
        <tr class="row<%= rowid %>">
          <td colspan="2" class="empty"><%= toHtml(productOption.getLabel()) %></td>
          <td colspan="2"><%= toHtml(option.getQuoteUserInput()) %></td>
          <td align="right"><zeroio:currency value="<%= option.getQuotePriceAdjust() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/></td>
          <td>&nbsp;</td>
          <td align="right" id="subTotal<%= rowCounter++ %>"><span name="subTotal<%= rowCounter %>" id="subTotal<%= rowCounter%>" <%= (quote.getShowSubtotal() == true) ? "" : "style=\"display:none\"" %>>&nbsp;</span></td>
        </tr>
   <% } %>
<% } %>
<% } %>
<script type="text/javascript">
rowCounter = '<%= rowCounter %>';
</script>
  <tr class="containerBody" >
  <td colspan="<%= 8+showAction %>" name="quoteItemChoose" id="quoteItemChoose">
  <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><dhv:permission name="<%= quoteEditPermission %>">
<td align="left" valign="top" width="100%">
<dhv:evaluate if="<%= (quote.getClosed() == null) && (!quote.isTrashed()) %>" >
  <dhv:evaluate if="<%=(!quote.getLock())%>" >
  <input type="button" value="<dhv:label name="button.choose">Choose</dhv:label>" onClick="javascript:popURL('ProductsCatalog.do?command=Categories&amp;quoteId=<%= quote.getId() %>','Products','600','500','yes','yes');" />
  <dhv:permission name="product-catalog-product-add"><input type="button" value="<dhv:label name="button.create">Create</dhv:label>" onClick="javascript:popURL('QuotesProducts.do?command=CreateForm&quoteId=<%= quote.getId() %>','QuoteItem','500','400','yes','yes');"/></dhv:permission> &nbsp;
  </dhv:evaluate>
  <input type="checkbox" name="showTotals" id="showTotals" value="0" <%= quote.getShowTotal()?"CHECKED":"" %> onClick="javascript:setShowTotal(this);" /> <dhv:label name="quotes.showGrandTotalPrice">Show the grand total price</dhv:label>
  <input type="checkbox" name="showSubtotals" id="showSubtotals" value="0" <%= quote.getShowSubtotal()?"CHECKED":"" %> onClick="javascript:setShowSubtotal(this);" /> <dhv:label name="quotes.showSubTotalPrice">Show the sub-total price</dhv:label>
</dhv:evaluate>&nbsp;
    </td></dhv:permission><dhv:permission name="<%= quoteEditPermission %>" none="true"><td width="100%"></td></dhv:permission>
    <td nowrap align="right">&nbsp;
      <span name="grandTotal" id="grandTotal" <%= (quote.getShowTotal() == true) ? "" : "style=\"display:none\"" %>>
        <strong>
          <zeroio:currency value="<%= quote.getGrandTotal() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
        </strong>
      </span>
    </td>
  </tr>
  </table></td></tr>
  <% } else { %>
<dhv:permission name="<%= quoteEditPermission %>">
  <tr class="containerBody">
    <td colspan="<%= 8+showAction %>" name="quoteItemEmpty" id="quoteItemEmpty">
    <dhv:evaluate if="<%= (quote.getClosed() == null) && (!quote.isTrashed()) %>" >
     <dhv:evaluate if="<%=(!quote.getLock())%>" >
      <input type="button" value="<dhv:label name="button.choose">Choose</dhv:label>" onClick="javascript:popURL('ProductsCatalog.do?command=Categories&amp;quoteId=<%= quote.getId() %>','Products','500','400','yes','yes');" />
      <dhv:permission name="product-catalog-product-add"><input type="button" value="<dhv:label name="button.create">Create</dhv:label>" onClick="javascript:popURL('QuotesProducts.do?command=CreateForm&quoteId=<%= quote.getId() %>','QuoteItem','500','400','yes','yes');" /></dhv:permission>
     </dhv:evaluate>
    </dhv:evaluate>&nbsp;
    </td>
  </tr>
</dhv:permission>
<% } %>
</table>
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
<dhv:evaluate if="<%= (quote.getClosed() == null) && (!quote.isTrashed()) %>" >
  <dhv:evaluate if="<%= quote.getRemarks().size() != 0 %>">
   <dhv:evaluate if="<%=(!quote.getLock())%>" >
    <dhv:permission name="<%= quoteEditPermission %>">
        <th width="8">&nbsp;</th>
    </dhv:permission>
  </dhv:evaluate>
</dhv:evaluate>
</dhv:evaluate>
    <th width="100%">
      <strong><dhv:label name="quotes.remarks">Remarks</dhv:label></strong> &nbsp; &nbsp; 
    </th>
  </tr>
<%
  if (quote.getRemarks().size() != 0) {
  int rowid = 0;
  int counter = 0;
  Iterator it = (Iterator) quote.getRemarks().iterator();
  while (it.hasNext()) {
    ++counter;
    i++;
    QuoteRemark remark = (QuoteRemark) it.next();
    rowid = (rowid != 1 ? 1 : 2);
%>
  <tr class="row<%= rowid %>">
<dhv:evaluate if="<%= (quote.getClosed() == null) && (!quote.isTrashed()) %>" >
   <dhv:evaluate if="<%=(!quote.getLock())%>" >
  <dhv:permission name="<%= quoteEditPermission %>">
      <td width="8" valign="top" nowrap>
        <a href="javascript:displayMenuCondition('select<%= i %>','menuQuoteCondition', '<%= quote.getId() %>', '<%= remark.getRemarkId() %>', '<%= location %>', 'false','<%= orgId %>', '<%= contactId %>', '<%= headerId %>');"
        onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuQuoteCondition');">
        <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0" /></a>
      </td>
  </dhv:permission>
</dhv:evaluate>
</dhv:evaluate>
    <td valign="top" width="100%">
      <%= counter %>.
      <%= toHtml(remark.getRemarkName()) %>
    </td>
  </tr>
<%}
} else {%>
  <tr class="containerBody">
    <td colspan="<%= 1+showAction %>"><dhv:label name="quotes.noRemarks">No remarks found for the quote.</dhv:label></td>
  </tr>
<%}%>
<dhv:evaluate if="<%= (quote.getClosed() == null) && (!quote.isTrashed()) %>" >
 <dhv:evaluate if="<%=(!quote.getLock())%>" >
  <dhv:permission name="<%= quoteEditPermission %>">
    <tr class="containerBody">
      <td colspan="2">
        <input type="button" value="<dhv:label name="button.choose">Choose</dhv:label>" onClick="javascript:quoteAnchor='quote_remarks';popQuoteConditionSelectMultiple('remarks','1','lookup_quote_remarks','<%= quote.getId() %>',currentRemarkIDs, currentRemarkNames,'Remarks');" /> 
        <input type="button" value="<dhv:label name="button.create">Create</dhv:label>" onClick="javascript:quoteAnchor='quote_remarks';popURL('QuotesConditions.do?command=AddRemark&quoteId=<%= quote.getId() %>','Remark','575','200','yes','yes');" /><br />
      </td>
    </tr>
  </dhv:permission>
</dhv:evaluate>
</dhv:evaluate>
</table>
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
<dhv:evaluate if="<%= (quote.getClosed() == null) && (!quote.isTrashed()) %>" >
  <dhv:evaluate if="<%= quote.getConditions().size() != 0 %>">
  <dhv:evaluate if="<%=(!quote.getLock())%>" >
    <dhv:permission name="<%= quoteEditPermission %>">
        <th width="8">&nbsp;</th>
    </dhv:permission>
  </dhv:evaluate>
</dhv:evaluate>
</dhv:evaluate>
    <th width="100%">
      <strong><dhv:label name="quotes.termsAndConditions">Terms and Conditions</dhv:label></strong>
    </th>
  </tr>
<%
  if (quote.getConditions().size() != 0) {
  int rowid = 0;
  int counter = 0;
  Iterator it = (Iterator) quote.getConditions().iterator();
  while (it.hasNext()) {
    QuoteCondition condition = (QuoteCondition) it.next();
    rowid = (rowid != 1 ? 1 : 2);
    i++;
    ++counter;
%>
  <tr class="row<%= rowid %>">
<dhv:evaluate if="<%= (quote.getClosed() == null) && (!quote.isTrashed()) %>" >
    <dhv:evaluate if="<%=(!quote.getLock())%>" >
  <dhv:permission name="<%= quoteEditPermission %>">
      <td width="8" valign="top" nowrap>
        <a href="javascript:displayMenuCondition('select<%= i %>','menuQuoteCondition', '<%= quote.getId() %>', '<%= condition.getConditionId() %>','<%= location %>','true','<%= orgId %>','<%= contactId %>','<%= headerId %>');"
        onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuQuoteCondition');">
        <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0" /></a>
      </td>
  </dhv:permission>
</dhv:evaluate>
</dhv:evaluate>
    <td valign="top" width="100%">
      <%= counter %>.
      <%= toHtml(condition.getConditionName()) %>
    </td>
  </tr>
<%}
} else {%>
  <tr class="containerBody">
    <td colspan="<%= 1+showAction %>"><dhv:label name="quotes.noTermsAndConditionsFound.text">No terms and conditions found for the quote.</dhv:label></td>
  </tr>
<%}%>
<dhv:evaluate if="<%= (quote.getClosed() == null) && (!quote.isTrashed()) %>" >
  <dhv:evaluate if="<%=(!quote.getLock())%>" >
  <dhv:permission name="<%= quoteEditPermission %>">
    <tr class="containerBody">
      <td colspan="2">
        <input type="button" value="<dhv:label name="button.choose">Choose</dhv:label>" onClick="javascript:quoteAnchor='quote_conditions';popQuoteConditionSelectMultiple('conditions','1','lookup_quote_condition','<%= quote.getId() %>',currentConditionIDs, currentConditionNames,'Terms and Conditions');"/> 
        <input type="button" value="<dhv:label name="button.create">Create</dhv:label>" onClick="javascript:quoteAnchor='quote_conditions';popURL('QuotesConditions.do?command=Add&quoteId=<%= quote.getId() %>','Condition','575','200','yes','yes');"/><br />
      </td>
    </tr>
  </dhv:permission>
</dhv:evaluate>
</dhv:evaluate>
</table>
<br />
<%if (quote.getNotes() != null && !"".equals(quote.getNotes())) {%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="4">
      <strong><dhv:label name="quotes.internalNotes">Internal Notes</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td><%= toHtml(quote.getNotes()) %></td>
  </tr>
</table>
<br />
<%}%>

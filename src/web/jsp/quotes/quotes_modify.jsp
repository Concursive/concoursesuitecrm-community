<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="quoteStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="quoteBean" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="quoteNoteList" class="org.aspcfs.modules.quotes.base.QuoteNoteList" scope="request"/>
<jsp:useBean id="quoteProductList" class="org.aspcfs.modules.quotes.base.QuoteProductList" scope="request"/>
<jsp:useBean id="productList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="optionList" class="org.aspcfs.modules.products.base.ProductOptionList" scope="request"/>
<jsp:useBean id="productOptionValuesList" class="org.aspcfs.modules.products.base.ProductOptionValuesList" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<%@ include file="../initPage.jsp" %>
<form method="post" name="form_notes" action="Quotes.do?command=CustomerQuoteDecision&quoteId=<%= quote.getId() %>&auto-populate=true<%= addLinkParams(request, "popup") %>" onSubmit="return checkComplete();">
<script type="text/javascript">
  var complete = true;
  
  function checkComplete(){
    return complete;
  }
     
  function doReject() {
    if (confirm('Rejecting this quote will remove it from your list and no further work will be performed by AdsJet.com\r\n' +
        '\r\n' +
        'Are you sure you want to reject this quote?')) {
      document.forms['form_notes'].action = document.forms['form_notes'].action + '&value=REJECT';
      document.forms['form_notes'].submit();
    }
  }
  
  function doSendMail(thisNote) {
    if(thisNote == ""){
      alert('No notes entered');
      complete = false;
      return;
    }
    if (confirm('Sending the following reply to the Designer at AdsJet.com\r\n' +
        '\r\n' +thisNote+'\r\n'+
        'Are you sure you want to send this note?')) {
      document.forms['form_notes'].action = document.forms['form_notes'].action + '&value=NOTES';
      document.forms['form_notes'].submit();
    }
  }
  function doSubmit() {
    document.forms['form_notes'].action = document.forms['form_notes'].action + '&value=CONTINUE';
    document.forms['form_notes'].submit();
  }
</script>
<%-- Hide the trails and top buttons if a popup --%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table cellspacing="0" class="trails">
  <tr>
    <td>
      My Homepage > 
      Approve a Quote
    </td>
  </tr>
</table>
<%-- End Trails --%>
<input type="submit" value="Continue" onClick="checkType(this.value);submit();"/>
<input type="button" value="Cancel" onClick="checkType(this.value);submit();"/>
<input type="button" value="Reject" onClick="checkType(this.value);submit();"/>
<input type="button" value="Send Reply" onClick="checkType(this.value);submit();"/>
<br />
</dhv:evaluate>
<table cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong>Quote Details</strong>
          </th>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            Details:
          </td>
          <td>
            <%= toHtml(quote.getShortDescription()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            Special Notes:
          </td>
          <td>
            <%= toHtml(quote.getNotes()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel"> 
            Quote Status:
          </td>
          <td>
            <%= toHtml(quoteStatusList.getValueFromId(quote.getStatusId())) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap align="left" class="formLabel"><strong>Total Price </strong></td>
          <td><strong> $<%= (int)quote.getGrandTotal()%></strong></td>
        </tr>
<%
  if(quote.getExpirationDate() != null) {
%>
        <tr>
          <td>
            Quote expiration date
          </td>
          <td>
            <dhv:tz timestamp="<%= quote.getExpirationDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
          </td>
        </tr>
<%
  }
%>
      </table>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="6">
            <strong>List of Products</strong>
          </th>
        </tr>
        <tr>
          <td>
            <strong>Product Id</strong>
          </td>
          <td>
            <strong>Quantity</strong>
          </td>
          <td>
            <strong>Product Price </strong>
          </td>
          <td>
            <strong>Recurring Price</strong>
          </td>
          <td align="right">
            <strong>Total</strong>
          </td>
        </tr>
      <%
      if(quote.getProductList().size() != 0){
        Iterator quoteProducts = (Iterator) quote.getProductList().iterator();
        while(quoteProducts.hasNext()){
          QuoteProduct product = (QuoteProduct) quoteProducts.next();
          ProductCatalog productCatalog = productList.getProductFromId(product.getProductId());
          QuoteProductOptionList optionsList = product.getProductOptionList();
          Iterator options = (Iterator) optionsList.iterator();
      %>
        <tr class="containerBody">
          <td nowrap align="left"><%= toHtml(productCatalog.getCategoryName()+" "+productCatalog.getName()) %></td>
          <td nowrap align="left"><%= product.getQuantity() %></td>
          <td nowrap align="left">$<%= (int)product.getPriceAmount() %></td>
          <td nowrap align="left"><%= (int) product.getRecurringAmount() %></td>
          <td nowrap align="right">$<%= (int)product.getTotalPrice() %></td>
        </tr>
      <%
          while(options.hasNext()){
            QuoteProductOption option = (QuoteProductOption) options.next();
            ProductOption productOption = optionList.getOptionFromId(option.getProductOptionId());
            ProductOptionValues value = productOptionValuesList.getValueFromId(option.getIntegerValue());
      %>
        <tr class="containerBody">
          <td nowrap align="left">&nbsp;&nbsp;&nbsp;<%= toHtml(value.getDescription()) %></td>
          <td nowrap align="left">&nbsp;</td>
          <td nowrap align="left">$<%= (int)option.getPriceAmount() %></td>
          <td nowrap align="left"><%= (int) product.getRecurringAmount() %></td>
          <td nowrap align="right">&nbsp;</td>
        </tr>
      <%    
          }
        }
      }
      %>
        <tr>
          <td nowrap colspan="6" align="right"><strong>Grand Total Price = $<%= (int)quote.getGrandTotal()%></strong></td>
        </tr>
      </table>
      <br />
      <table cellpadding="3" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
          <th colspan="6">
            <strong>Notes</strong>
          </th>
        </tr>
        <tr>
          <td>
            <strong>Date</strong>
          </td>
          <td>
            <strong>Entered By</strong>
          </td>
          <td>
            <strong>Details</strong>
          </td>
        </tr>
<%
  if(quoteNoteList.size() > 0 ) {
    int rowid=0;
    int i=0;
    Iterator iterator = (Iterator) quoteNoteList.iterator();
    while(iterator.hasNext()){
      QuoteNote quoteNote = (QuoteNote) iterator.next();
      i++;
      rowid = ( rowid != 1 ? 1:2 );
%>
        <tr>
          <td width="30%" class="row<%= rowid %>">
            <dhv:tz timestamp="<%= quoteNote.getEntered() %>" dateOnly="false" dateFormat="<%= DateFormat.SHORT %>"/>
          </td>
          <td width="20%" class="row<%= rowid %>">
            <dhv:username id="<%= quoteNote.getEnteredBy() %>" />
          </td>
          <td width="50%" class="row<%= rowid %>">
            <%= toHtml(quoteNote.getNotes()) %>
          </td>
        </tr>
<%
    }
  }
%>
      <tr>
        <td colspan="3" class="containerBody"><strong>Enter a new note</strong></td>
      </tr>
        <tr>
          <td colspan="3">
            <textarea name="notes" rows="5" cols="65"></textarea>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<br />
<% if((int)quote.getGrandTotal() != 0 ){%>
  <input type="submit" value="Continue with order" onClick="doSubmit();" />
<%}%>
<input type="submit" value="Reject quote" onClick="doReject();"/>
<input type="button" value="Review this quote later" onClick="window.close();"/>
<input type="submit" value="Send Reply" onClick="doSendMail(document.forms['form_notes'].notes.value);"/>
</form>

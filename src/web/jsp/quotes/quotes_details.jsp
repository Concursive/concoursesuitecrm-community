<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.*,java.text.DateFormat,org.aspcfs.modules.products.base.*, org.aspcfs.modules.base.Constants" %>
<%@ page import="org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="order" class="org.aspcfs.modules.orders.base.Order" scope="request"/>
<jsp:useBean id="quoteStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="quoteDeliveryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%--<jsp:useBean id="quoteBean" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/> --%>
<jsp:useBean id="quoteProductList" class="org.aspcfs.modules.quotes.base.QuoteProductList" scope="request"/>
<jsp:useBean id="productList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="optionList" class="org.aspcfs.modules.products.base.ProductOptionList" scope="request"/>
<jsp:useBean id="opportunity" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="version" class="java.lang.String" scope="request"/>
<jsp:useBean id="canPrint" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="fileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="quotes_products_list_menu.jsp" %>
<%@ include file="quotes_conditions_list_menu.jsp" %>
<body onLoad="javascript:changeShowSubtotal('<%= quote.getShowSubtotal() %>');if('<%= canPrint %>' == 'true'){printQuote('<%= quote.getId() %>');}">
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');

  function checkType(value) {
    if (value == "Save for later") {
      document.forms['addProduct'].action = 'Quotes.do?command=Save&quoteId=<%= quote.getId() %>&version=<%= version %>&auto-populate=true';
      document.forms['addProduct'].submit();
    } else if (value == "Submit") {
      if (confirm(label("confirm.quotesubmit",'Are you sure you want to submit this quote for review by the selected contact?'))) {
        document.forms['addProduct'].action = 'Quotes.do?command=Submit&version=<%= version %>&quoteId=<%= quote.getId() %>&auto-populate=true';
        document.forms['addProduct'].submit();
      }
    } else if (value == "Create Order") {
      document.forms['addProduct'].action = 'Orders.do?command=CreateOrder&quoteId=<%= quote.getId() %>';
      document.forms['addProduct'].submit();
    }
  }
  function generateVersion() {
    if (confirm(label("verify.quote.newversion","Are you sure you want to create a new Version of this Quote?"))) {
      window.location.href='Quotes.do?command=AddVersion&quoteId=<%= quote.getId() %>&version=<%= version %>';
    }
  }

  function reopen(){
    scrollReload('Quotes.do?command=Details&quoteId=<%= quote.getId() %>&version=<%= version %>');
  }

  function reopenId(id){
    window.location.href= 'Quotes.do?command=Details&quoteId='+id;
  }
  
  function reopenAndPrint(canPrint){
    scrollReload('Quotes.do?command=Details&quoteId=<%= quote.getId() %>&version=<%= version %>&canPrint='+canPrint);
  }

</script>
<form method="post" name="addProduct" action="Quotes.do?">
<%  int showAction = quote.getClosed() == null?1:0; %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Quotes.do"><dhv:label name="dependency.quotes">Quotes</dhv:label></a> >
      <a href="Quotes.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
      <dhv:evaluate if="<%=  (version != null && !"".equals(version)) %>" > 
        <a href="Quotes.do?command=Search&version=<%= quote.getId() %>"><dhv:label name="quotes.versionResults">Version Results</dhv:label></a> >
      </dhv:evaluate>
      <dhv:label name="quotes.quoteDetails">Quote Details</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<% String param1 = "quoteId=" + quote.getId() + "|version="+version; %>
<dhv:container name="quotes" selected="details" object="quote" param="<%= param1 %>">
  <%@ include file="quotes_header_include.jsp" %>
  <%String status = quoteStatusList.getValueFromId(quote.getStatusId());%>
  <dhv:evaluate if="<%= quote.isTrashed() %>">
    <dhv:permission name="quotes-quotes-delete"><input type="button" value="<dhv:label name="button.restore">Restore</dhv:label>" onClick="javascript:window.location.href='Quotes.do?command=Restore&version=<%= version %>&quoteId=<%= quote.getId() %><%= addLinkParams(request, "viewSource") %>';"></dhv:permission>
  </dhv:evaluate>
  <dhv:evaluate if="<%= !quote.isTrashed() %>">
    <dhv:evaluate if="<%= (quote.getClosed() == null)%>">
      <dhv:permission name="quotes-quotes-edit"><input type="button" value="<dhv:label name="button.submit">Submit</dhv:label>" onClick="javascript:popURL('Quotes.do?command=Submit&quoteId=<%= quote.getId() %>','Submit','500','400','yes','yes');"/></dhv:permission>
      <dhv:permission name="quotes-quotes-edit"><input type="button" value="<dhv:label name="button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='Quotes.do?command=ModifyForm&version=<%= version %>&quoteId=<%= quote.getId() %>';"/></dhv:permission>
    </dhv:evaluate>
    <dhv:permission name="quotes-quotes-add"><input type="button" value="<dhv:label name="button.clone">Clone</dhv:label>" onClick="generateClone();"/></dhv:permission>
    <dhv:permission name="quotes-quotes-add"><input type="button" value="<dhv:label name="button.addVersion">Add Version</dhv:label>" onClick="generateVersion();"/></dhv:permission>
    <input type="button" value="<dhv:label name="global.button.Print">Print</dhv:label>" onClick="javascript:printQuote('<%= quote.getId() %>');"/>
    <dhv:permission name="quotes-quotes-delete"><input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('Quotes.do?command=ConfirmDelete&version=<%= version %>&quoteId=<%= quote.getId() %>&popup=true','Quotes.do?command=Search', 'Delete_Quote','330','200','yes','no');"/></dhv:permission>
    <dhv:permission name="quotes-quotes-edit"><input type="button" value="<dhv:label name="button.close">Close</dhv:label>" onClick="javascript:closeQuote();"/></dhv:permission>
    <dhv:permission name="quotes-quotes-edit" none="true"><% showAction = 0; %></dhv:permission>
  </dhv:evaluate>
  <br />
  <br />
<%
// set the Permissions
String ticketPermission = "tickets-tickets-view";
String orderPermission = "orders-view";
String opportunityPermission = "pipeline-opportunities-view";
String contactPermission = "contacts-external_contacts-view";
String quoteEditPermission = "quotes-quotes-edit";
// set the Links
String ticketLink = "TroubleTickets.do?command=Details&id="+ quote.getTicketId();
String quoteNextVersionLink = "Quotes.do?command=Details&quoteId="+ quote.getParentId() +"&version="+ (version!=null?version:"");
String orderLink = "Orders.do?command=Details&id="+ order.getId();
String opportunityLink = "Leads.do?command=DetailsOpp&headerId="+ quote.getHeaderId() +"&viewSource=dashboard&reset=true&fromQuoteDetails=true";
String contactLink = "ExternalContacts.do?command=ContactDetails&id="+ quote.getContactId();
// set the other items
String secondId = "";
String location = "quotes";
%>
  <%@ include file="../quotes/quotes_details_include.jsp" %>
  <dhv:evaluate if="<%= quote.isTrashed() %>">
    <dhv:permission name="quotes-quotes-delete"><input type="button" value="<dhv:label name="button.restore">Restore</dhv:label>" onClick="javascript:window.location.href='Quotes.do?command=Restore&version=<%= version %>&quoteId=<%= quote.getId() %><%= addLinkParams(request, "viewSource") %>';"></dhv:permission>
  </dhv:evaluate>
  <dhv:evaluate if="<%= !quote.isTrashed() %>">
    <dhv:evaluate if="<%= (quote.getClosed() == null) %>">
      <dhv:permission name="quotes-quotes-edit"><input type="button" value="<dhv:label name="button.submit">Submit</dhv:label>" onClick="javascript:popURL('Quotes.do?command=Submit&quoteId=<%= quote.getId() %>','Submit','500','400','yes','yes');"/></dhv:permission>
      <dhv:permission name="quotes-quotes-edit"><input type="button" value="<dhv:label name="button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='Quotes.do?command=ModifyForm&version=<%= version %>&quoteId=<%= quote.getId() %>';"/></dhv:permission>
    </dhv:evaluate>
    <dhv:permission name="quotes-quotes-add"><input type="button" value="<dhv:label name="button.clone">Clone</dhv:label>" onClick="generateClone();"/></dhv:permission>
    <dhv:permission name="quotes-quotes-add"><input type="button" value="<dhv:label name="button.addVersion">Add Version</dhv:label>" onClick="generateVersion();"/></dhv:permission>
    <input type="button" value="<dhv:label name="global.button.Print">Print</dhv:label>" onClick="javascript:printQuote('<%= quote.getId() %>');"/>
    <dhv:permission name="quotes-quotes-delete"><input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('Quotes.do?command=ConfirmDelete&version=<%= version %>&quoteId=<%= quote.getId() %>&popup=true','Quotes.do?command=Search', 'Delete_Quote','330','200','yes','no');"/></dhv:permission>
    <dhv:permission name="quotes-quotes-edit"><input type="button" value="<dhv:label name="button.close">Close</dhv:label>" onClick="javascript:closeQuote();"/></dhv:permission>
  </dhv:evaluate>
  <iframe src="../empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</dhv:container>
</form>

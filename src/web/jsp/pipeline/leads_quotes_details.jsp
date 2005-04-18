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
<%@ page import="java.util.*,java.text.*,java.text.DateFormat,org.aspcfs.modules.products.base.*" %>
<%@ page import="org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="order" class="org.aspcfs.modules.orders.base.Order" scope="request"/>
<jsp:useBean id="quoteStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="quoteDeliveryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="quoteProductList" class="org.aspcfs.modules.quotes.base.QuoteProductList" scope="request"/>
<jsp:useBean id="productList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="optionList" class="org.aspcfs.modules.products.base.ProductOptionList" scope="request"/>
<jsp:useBean id="productOptionValuesList" class="org.aspcfs.modules.products.base.ProductOptionValuesList" scope="request"/>
<jsp:useBean id="version" class="java.lang.String" scope="request"/>
<jsp:useBean id="canPrint" class="java.lang.String" scope="request"/>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
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
<%@ include file="../quotes/quotes_products_list_menu.jsp" %>
<%@ include file="../quotes/quotes_conditions_list_menu.jsp" %>
<body onLoad="javascript:changeShowSubtotal('<%= quote.getShowSubtotal() %>');if('<%= canPrint %>' == 'true'){printQuote('<%= quote.getId() %>');}">
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');

  function generateVersion() {
    if (confirm(label("verify.quote.newversion","Are you sure you want to create a new Version of this Quote?"))) {
      window.location.href='LeadsQuotes.do?command=AddVersion&quoteId=<%= quote.getId() %>&version=<%= version %><%= addLinkParams(request, "viewSource") %>';
    }
  }

  function reopen(){
    scrollReload('LeadsQuotes.do?command=Details&quoteId=<%= quote.getId() %>&version=<%= version %><%= addLinkParams(request, "viewSource") %>');
  }

  function reopenId(id){
    window.location.href= 'LeadsQuotes.do?command=Details&quoteId='+id +'<%= addLinkParams(request, "viewSource") %>';
  }
  
  function reopenAndPrint(canPrint){
    scrollReload('LeadsQuotes.do?command=Details&quoteId=<%= quote.getId() %>&version=<%= version %>&canPrint='+canPrint+'<%= addLinkParams(request, "viewSource") %>');
  }

</script>
<script type="text/javascript">
function reopenOpportunity(id) {
  if (id == '<%= opportunityHeader.getId() %>') {
    if ('<%= "dashboard".equals(request.getParameter("viewSource")) %>' == 'true') {
      scrollReload('Leads.do?command=Dashboard');
    } else {
      scrollReload('Leads.do?command=Search');
    }
    return id;
  } else {
    return '<%= opportunityHeader.getId() %>';
  }
}
</script>
<form method="post" name="addProduct" action="Quotes.do?">
<% int showAction = quote.getClosed() == null?1:0; %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Leads.do"><dhv:label name="pipeline.pipeline">Pipeline</dhv:label></a> >
<% if ("dashboard".equals(request.getParameter("viewSource"))){ %>
	<a href="Leads.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<% }else{ %>
	<a href="Leads.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<% } %>
<a href="Leads.do?command=DetailsOpp&headerId=<%= quote.getHeaderId() %>&reset=true<%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> > 
<a href="LeadsQuotes.do?command=QuoteList&headerId=<%= opportunityHeader.getId() %>&reset=true<%= addLinkParams(request, "viewSource") %>"><dhv:label name="dependency.quotes">Quotes</dhv:label></a> >
<dhv:label name="quotes.quoteDetails">Quote Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <dhv:label name="pipeline.viewpoint.colon" param="<%= "username="+PipelineViewpointInfo.getVpUserName() %>"><b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b></dhv:label><br />
  &nbsp;<br>
</dhv:evaluate>
<dhv:container name="opportunities" selected="quotes" object="opportunityHeader" param="<%= "id=" + opportunityHeader.getId() %>" appendToUrl="<%= addLinkParams(request, "viewSource") %>">
  <dhv:container name="opportunitiesQuotes" selected="details" object="quote" param="<%= "quoteId=" + quote.getId() + "|version=" + version %>" appendToUrl="<%= addLinkParams(request, "viewSource") %>">
    <%String status = quoteStatusList.getValueFromId(quote.getStatusId());%>
    <%@ include file="../quotes/quotes_header_include.jsp" %>
    <% if(quote.getClosed() == null){%>
      <dhv:permission name="pipeline-opportunities-edit"><input type="button" value="<dhv:label name="global.button.submit">Submit</dhv:label>" onClick="javascript:popURL('Quotes.do?command=Submit&quoteId=<%= quote.getId() %>','Submit','500','400','yes','yes');"/></dhv:permission>
      <dhv:permission name="pipeline-opportunities-edit"><input type="button" value="<dhv:label name="button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='LeadsQuotes.do?command=ModifyForm&version=<%= version %>&quoteId=<%= quote.getId() %><%= addLinkParams(request, "viewSource") %>';"/></dhv:permission>
    <%}%>
    <dhv:permission name="pipeline-opportunities-add"><input type="button" value="<dhv:label name="button.clone">Clone</dhv:label>" onClick="generateClone();"/></dhv:permission>
    <dhv:permission name="pipeline-opportunities-add"><input type="button" value="<dhv:label name="button.addVersion">Add Version</dhv:label>" onClick="generateVersion();"/></dhv:permission>
    <input type="button" value="<dhv:label name="global.button.Print">Print</dhv:label>" onClick="javascript:printQuote('<%= quote.getId() %>');"/>
    <dhv:permission name="pipeline-opportunities-delete"><input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('LeadsQuotes.do?command=ConfirmDelete&version=<%= version %>&quoteId=<%= quote.getId() %>&popup=true','Quotes.do?command=Search', 'Delete_Quote','330','200','yes','no');"/></dhv:permission>
    <dhv:permission name="pipeline-opportunities-edit"><input type="button" value="<dhv:label name="button.close">Close</dhv:label>" onClick="javascript:closeQuote();"/></dhv:permission>
    <dhv:permission name="pipeline-opportunities-edit" none="true"><% showAction = 0; %></dhv:permission>
    <br /> <br />
<%
// set the Permissions
String ticketPermission = "tickets-tickets-view";
String orderPermission = "orders-view";
String opportunityPermission = "pipeline-opportunities-view";
String contactPermission = "contacts-external_contacts-view";
String quoteEditPermission = "pipeline-opportunities-edit";
// set the Links
String ticketLink = "TroubleTickets.do?command=Details&id="+ quote.getTicketId();
String quoteNextVersionLink = "LeadsQuotes.do?command=Details&quoteId="+ quote.getParentId() +"&version="+ (version!=null?version:"");
String orderLink = "Orders.do?command=Details&id="+ order.getId();
String opportunityLink = "Leads.do?command=DetailsOpp&headerId="+ quote.getHeaderId() +"&viewSource=dashboard&reset=true";
String contactLink = "ExternalContacts.do?command=ContactDetails&id="+ quote.getContactId();
// set the other items
String secondId = ""+opportunityHeader.getId();
String location = "opportunitiesQuotes";
OpportunityHeader opportunity = opportunityHeader;
%>
    <%@ include file="../quotes/quotes_details_include.jsp" %>
    <% if(quote.getClosed() == null){%>
      <dhv:permission name="pipeline-opportunities-edit"><input type="button" value="<dhv:label name="global.button.submit">Submit</dhv:label>" onClick="javascript:popURL('Quotes.do?command=Submit&quoteId=<%= quote.getId() %>','Submit','500','400','yes','yes');"/></dhv:permission>
      <dhv:permission name="pipeline-opportunities-edit"><input type="button" value="<dhv:label name="button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='LeadsQuotes.do?command=ModifyForm&version=<%= version %>&quoteId=<%= quote.getId() %><%= addLinkParams(request, "viewSource") %>';"/></dhv:permission>
    <%}%>
    <dhv:permission name="pipeline-opportunities-add"><input type="button" value="<dhv:label name="button.clone">Clone</dhv:label>" onClick="generateClone();"/></dhv:permission>
    <dhv:permission name="pipeline-opportunities-add"><input type="button" value="<dhv:label name="button.addVersion">Add Version</dhv:label>" onClick="generateVersion();"/></dhv:permission>
    <input type="button" value="<dhv:label name="global.button.Print">Print</dhv:label>" onClick="javascript:printQuote('<%= quote.getId() %>');"/>
    <dhv:permission name="pipeline-opportunities-delete"><input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('LeadsQuotes.do?command=ConfirmDelete&version=<%= version %>&quoteId=<%= quote.getId() %>&popup=true','Quotes.do?command=Search', 'Delete_Quote','330','200','yes','no');"/></dhv:permission>
    <dhv:permission name="pipeline-opportunities-edit"><input type="button" value="<dhv:label name="button.close">Close</dhv:label>" onClick="javascript:closeQuote();"/></dhv:permission>
    <iframe src="../empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
  </dhv:container>
</dhv:container>
</form>

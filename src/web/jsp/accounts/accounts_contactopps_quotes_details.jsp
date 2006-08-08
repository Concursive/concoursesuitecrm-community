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
<%@ page import="java.util.*,java.text.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="order" class="org.aspcfs.modules.orders.base.Order" scope="request"/>
<jsp:useBean id="quoteBean" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="quoteStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="quoteDeliveryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="quoteProductList" class="org.aspcfs.modules.quotes.base.QuoteProductList" scope="request"/>
<jsp:useBean id="opportunity" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="productList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="optionList" class="org.aspcfs.modules.products.base.ProductOptionList" scope="request"/>
<jsp:useBean id="version" class="java.lang.String" scope="request"/>
<jsp:useBean id="canPrint" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="fileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/scrollReload.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/executeFunction.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/moveContact.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="../quotes/quotes_products_list_menu.jsp" %>
<%@ include file="../quotes/quotes_conditions_list_menu.jsp" %>
<body onLoad="javascript:changeShowSubtotal('<%= quote.getShowSubtotal() %>');if('<%= canPrint %>' == 'true'){printQuote('<%= quote.getId() %>');}">
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  
  function checkType(value) {
    if (value == "Save") {
      document.forms['addProduct'].action = 'AccountContactsOppQuotes.do?command=Save&quoteId=<%= quote.getId() %>&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quote.getHeaderId() %>&version=<%= version %>&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>';
      document.forms['addProduct'].submit();
    } else if (value == "Create Order") {
      document.forms['addProduct'].action = 'Orders.do?command="CreateOrder&quoteId=<%= quote.getId() %>&orgId=<%= OrgDetails.getOrgId() %>&version=<%= version %><%= addLinkParams(request, "popup|popupType|actionId") %>';
      document.forms['addProduct'].submit();
    }
  }

  function generateVersion() {
    if (confirm(label("verify.quote.newversion","Are you sure you want to create a new Version of this Quote?"))) {
      window.location.href='AccountContactsOppQuotes.do?command=AddVersion&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quote.getHeaderId() %>&quoteId=<%= quote.getId() %>&version=<%= version %><%= addLinkParams(request, "popup|popupType|actionId") %>';
    }
  }
  
  function reopen() {
    scrollReload('AccountContactsOppQuotes.do?command=Details&quoteId=<%= quote.getId() %>&version=<%= version %>&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quote.getHeaderId() %><%= addLinkParams(request, "popup|popupType|actionId") %>');
  }

  function reopenId(id) {
    window.location.href= 'AccountContactsOppQuotes.do?command=Details&quoteId='+id+'&version=<%= version %>&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quote.getHeaderId() %><%= addLinkParams(request, "popup|popupType|actionId") %>';
  }
  
  function reopenAndPrint(canPrint) {
    scrollReload('AccountContactsOppQuotes.do?command=Details&quoteId=<%= quote.getId() %>&version=<%= version %>&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quote.getHeaderId() %>&canPrint='+canPrint+'<%= addLinkParams(request, "popup|popupType|actionId") %>');
  }

</script>
<%
  boolean allowMultipleQuote = allowMultipleQuote(pageContext);
  boolean allowMultipleVersion = allowMultipleVersion(pageContext);
%>
<form method="post" name="addProduct" action="AccountContactsOppQuotes.do?command=Details&quoteId=<%= quote.getId() %>&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quote.getHeaderId() %>&version=<%= version %><%= addLinkParams(request, "popup|popupType|actionId") %>">
<%   int showAction = quote.getClosed() == null?1:0; %>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> > 
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> > 
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> > 
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> > 
<a href="AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> > 
<a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= opportunity.getId() %>&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>"><dhv:label name="">Opportunity Details</dhv:label></a> > 
<% if (version != null && !"".equals(version)) { %> 
<a href="AccountContactsOppQuotes.do?command=View&orgId=<%=OrgDetails.getOrgId()%>&contactId=<%= ContactDetails.getId() %>&headerId=<%=opportunity.getId() %>&version="><dhv:label name="accounts.accounts_quotes_list.Quotes">Quotes</dhv:label></a> > 
<a href="AccountContactsOppQuotes.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quote.getHeaderId() %>&version=<%= version %>"><dhv:label name="quotes.versionList">Version List</dhv:label></a> >
<% } %>
<dhv:label name="quotes.quoteDetails">Quote Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="contacts" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
  <dhv:container name="accountscontacts" selected="opportunities" object="ContactDetails" param="<%= "id=" + ContactDetails.getId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
    <dhv:container name="accountcontactopportunities" selected="quotes" object="opportunity" param="<%= "headerId=" + quote.getHeaderId() + "|" + "orgId=" + OrgDetails.getOrgId() + "|" + "contactId=" + ContactDetails.getId()%>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
    <dhv:label name="accounts.accountasset_include.DescriptionColon">Description:</dhv:label>&nbsp;<%= toHtml(quote.getShortDescription()) %><br />
    <%String status = quoteStatusList.getValueFromId(quote.getStatusId());%>
    <%@ include file="../quotes/quotes_header_include.jsp" %>
    <% if(quote.getClosed() == null){ %>
        <dhv:permission name="accounts-accounts-contacts-opportunities-quotes-edit"><input type="button" value="<dhv:label name="button.submit">Submit</dhv:label>" onClick="javascript:popURL('AccountContactsOppQuotes.do?command=Submit&quoteId=<%= quote.getId() %>&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quote.getHeaderId() %>','Submit','500','400','yes','yes');"/></dhv:permission>
        <dhv:evaluate if="<%=(!quote.getLock())%>" >
          <dhv:permission name="accounts-quotes-edit"><input type="button" value="<dhv:label name="button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='AccountContactsOppQuotes.do?command=ModifyForm&version=<%= version %>&quoteId=<%= quote.getId() %>&orgId=<%= quote.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quote.getHeaderId() %><%= addLinkParams(request, "popup|popupType|actionId") %>';"/></dhv:permission>
        </dhv:evaluate>
    <%}%>
    <dhv:evaluate if="<%=(!quote.getLock())%>" >
      <dhv:permission name="accounts-accounts-contacts-opportunities-quotes-add">
        <dhv:evaluate if="<%= allowMultipleQuote %>" >    
          <input type="button" value="<dhv:label name="button.clone">Clone</dhv:label>" onClick="generateClone();"/>
        </dhv:evaluate>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-quotes-add">
        <dhv:evaluate if="<%= allowMultipleVersion %>" >    
          <input type="button" value="<dhv:label name="button.addVersion">Add Version</dhv:label>" onClick="generateVersion();"/>
        </dhv:evaluate>
      </dhv:permission>
    </dhv:evaluate>
    <input type="button" value="<dhv:label name="global.button.Print">Print</dhv:label>" onClick="javascript:printQuote('<%= quote.getId() %>');"/>
    <dhv:evaluate if="<%=(!quote.getLock())%>" >
      <dhv:permission name="accounts-accounts-contacts-opportunities-quotes-delete"><input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('AccountContactsOppQuotes.do?command=ConfirmDelete&version=<%= version %>&quoteId=<%= quote.getId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quote.getHeaderId() %>&popup=true','AccountContactsOppQuotes.do?command=View', 'Delete_Quote','330','200','yes','no');"/></dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-quotes-edit"><input type="button" value="<dhv:label name="button.close">Close</dhv:label>" onClick="javascript:closeQuote();"/></dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-quotes-edit" none="true"><% showAction = 0; %></dhv:permission>
    </dhv:evaluate>
    <br /> <br />
<%
// set the Permissions
String ticketPermission = "accounts-accounts-tickets-view";
String orderPermission = "accounts-orders-view";
String opportunityPermission = "accounts-accounts-opportunities-view";
String contactPermission = "accounts-accounts-contacts-view";
String quoteEditPermission = "accounts-accounts-contacts-opportunities-quotes-edit";
// set the Links
String ticketLink = "AccountTickets.do?command=TicketDetails&id="+ quote.getTicketId()+ addLinkParams(request, "popup|popupType|actionId");
String quoteNextVersionLink = "AccountContactsOppQuotes.do?command=Details&quoteId="+ quote.getParentId() +"&orgId="+OrgDetails.getOrgId()+"&version="+(version!=null?version:"")+ addLinkParams(request, "popup|popupType|actionId");
String orderLink = "AccountOrders.do?command=Details&id="+ order.getId()+ addLinkParams(request, "popup|popupType|actionId");
String opportunityLink = "AccountContactsOppComponents.do?command=DetailsOpp&headerId="+ quote.getHeaderId() + "&contactId=" + ContactDetails.getId() +"&orgId="+ OrgDetails.getOrgId() +"&reset=true"+ addLinkParams(request, "popup|popupType|actionId");
String contactLink = "Contacts.do?command=Details&id="+ quote.getContactId()+ addLinkParams(request, "popup|popupType|actionId");
// set the other items
String orgId = ""+OrgDetails.getOrgId();
String contactId = ""+quote.getContactId();
String headerId = ""+quote.getHeaderId();
String location = "accountsContactsOppsQuotes";
%>
    <%@ include file="../quotes/quotes_details_include.jsp" %>
    <% if(quote.getClosed() == null){ %>
      <dhv:permission name="accounts-accounts-contacts-opportunities-quotes-edit"><input type="button" value="<dhv:label name="button.submit">Submit</dhv:label>" onClick="javascript:popURL('AccountContactsOppQuotes.do?command=Submit&quoteId=<%= quote.getId() %>&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quote.getHeaderId() %>','Submit','500','400','yes','yes');"/></dhv:permission>
      <dhv:evaluate if="<%=(!quote.getLock())%>" >
        <dhv:permission name="accounts-quotes-edit"><input type="button" value="<dhv:label name="button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='AccountContactsOppQuotes.do?command=ModifyForm&version=<%= version %>&quoteId=<%= quote.getId() %>&orgId=<%= quote.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quote.getHeaderId() %><%= addLinkParams(request, "popup|popupType|actionId") %>';"/></dhv:permission>
      </dhv:evaluate>
    <%}%>
    <dhv:evaluate if="<%=(!quote.getLock())%>" >
      <dhv:permission name="accounts-accounts-contacts-opportunities-quotes-add">
        <dhv:evaluate if="<%= allowMultipleQuote %>" >    
          <input type="button" value="<dhv:label name="button.clone">Clone</dhv:label>" onClick="generateClone();"/>
        </dhv:evaluate>
       </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-quotes-add">
        <dhv:evaluate if="<%= allowMultipleVersion %>" >    
          <input type="button" value="<dhv:label name="button.addVersion">Add Version</dhv:label>" onClick="generateVersion();"/>
        </dhv:evaluate>
      </dhv:permission>
    </dhv:evaluate>
    <input type="button" value="<dhv:label name="global.button.Print">Print</dhv:label>" onClick="javascript:printQuote('<%= quote.getId() %>');"/>
    <dhv:evaluate if="<%=(!quote.getLock())%>" >
      <dhv:permission name="accounts-accounts-contacts-opportunities-quotes-delete"><input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('AccountContactsOppQuotes.do?command=ConfirmDelete&version=<%= version %>&quoteId=<%= quote.getId() %>&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quote.getHeaderId() %>&popup=true<%= isPopup(request) ? "&popupType=inline":"" %>','AccountContactsOppQuotes.do?command=View', 'Delete_Quote','330','200','yes','no');"/></dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-quotes-edit"><input type="button" value="<dhv:label name="button.close">Close</dhv:label>" onClick="javascript:closeQuote();"/></dhv:permission>
    </dhv:evaluate>
    </dhv:container>
  </dhv:container>
</dhv:container>
<iframe src="../empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</form>
